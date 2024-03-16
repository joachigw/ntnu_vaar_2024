const os = require('os');
const net = require("net");
const crypto = require("crypto")

// Stores active connections and broadcast messages
const connections = [];
const messages = [];


// WebSocket server
const wsServer = net.createServer((connection) => {
    console.log("\u001b[32mClient connected\u001b[0m");

    connection.on("data", (data) => {
        if (data.toString().split("\n")[0].includes("GET / HTTP")) {

            // Retrieve client-key and generate server-key
            let clientKeyBase64 = findKey(data.toString());
            let serverKeyBase64 = generateServerKeyBase64(clientKeyBase64);

            console.log("   ->Found client key: \u001b[32m" + clientKeyBase64 + "\u001b[0m");
            console.log("   ->Generated server key: \u001b[32m" + serverKeyBase64 + "\u001b[0m\n");

            // Send server-handshake
            const serverHandshake = `HTTP/1.1 101 Switching Protocols\r\n` +
                    `Upgrade: websocket\r\n` +
                    `Connection: Upgrade\r\n` +
                    `Sec-WebSocket-Accept: ${serverKeyBase64}\r\n\r\n`;

            connection.write(serverHandshake);

            connections.push(connection);

            if (messages.length > 0) {
                for (let i = 0; i < messages.length; i++) {
                    connection.write(createWebSocketFrame(messages[i]));
                }
            }
        }
        else {

            // Validates that the incoming WebSocket frame is of type "text" (129 in decimal, 0x81 in hexadecimal)
            // Does not continue if it is not of type "text"
            if (data[0] !== 129) {
                console.error("\u001b[31mText was not received. Throwing frame...\u001b[0m\n");
                return;
            }

            // Interprets values from the incoming WebSocket frame
            let msgLen = data[1] & 127;
            let maskStart = 2;
            let dataStart = maskStart + 4;
            let bytes = [];
            let msg;

            // Reads the text based on the WebSocket frame's data bytes
            for (let i = dataStart; i < dataStart + msgLen; i++) {
                let byte = data[i] ^ data[maskStart + ((i - dataStart) % 4)];
                bytes.push(byte);
            }

            msg = Buffer.from(bytes).toString("utf8");

            // Attempts to create a WebSocket frame and broadcast it to all the connected clients
            try {
                const sendMsg = JSON.parse(msg).text;
                const sendId = JSON.parse(msg).id;
                messages.push(sendMsg);
                const msgToClient = createWebSocketFrame(JSON.stringify({ id:sendId ,text: sendMsg }));
                for (let i = 0; i < connections.length; ++i) {
                    connections[i].write(msgToClient);
                }
            }
            catch (error) {
                console.error("\u001b[31mError in WebSocket: " + error + "\u001b[0m");
            }
        }
    });

    connection.on("end", () => {
        console.log("\u001b[33mClient disconnected on port " + connection.remotePort + "\u001b[0m");
    });

});

// Catches any errors that the server encounters
wsServer.on("error", (error) => {
    console.error(error);
});

// Configures the server to listen to incoming traffic on the local IPv4 of the device on port 3001
wsServer.listen(3001, getLocalIP(),() => {
    console.log("WebSocket server listening on port \u001b[32m3001\u001b[0m on IP-adr \u001b[32m" +
            wsServer.address().address + "\u001b[0m");
});


/**
 * Creates a frame in format specified in RFC6455.
 * @param message The message to encapsulate.
 * @returns {Buffer} The frame.
 */
function createWebSocketFrame(message) {
    const msg = Buffer.from(message);
    const msgSize = msg.length;

    const header = Buffer.alloc(2);
    header.writeUInt8(0x81, 0);
    header.writeUInt8(msgSize, 1);

    return Buffer.concat([header, msg]);
}

/**
 * Finds the client WebSocket key from a client WebSocket upgrade message.
 * @param data The message from the client.
 * @returns {string} The client key.
 */
function findKey(data) {
    let key = "none";

    // Retrieve the WebSocket key
    const lines = data.toString().split("\n");
    for (let i = 0; i < lines.length; ++i) {
        let line = lines[i];
        if (line.includes("Sec-WebSocket-Key")) {
            key = line.split(": ")[1].trim();
        }
    }
    return key;
}


/**
 * Generates the server's WebSocket accept key used for server's WebSocket handshake message.
 * @param clientKey The client key.
 * @returns {string} The WebSocket accept key.
 */
function generateServerKeyBase64(clientKey) {
    const rfcString = '258EAFA5-E914-47DA-95CA-C5AB0DC85B11';
    return crypto.createHash("sha1").update(clientKey + rfcString).digest("base64");
}

/**
 * Retrieves the IPv4 address of the device this server is being run on in dot-decimal notation.
 * @returns {string} the IPv4 address
 */
function getLocalIP() {
    const interfaces = os.networkInterfaces();
    for (const name in interfaces) {
        for (const intrfc of interfaces[name]) {
            const {address, family, internal} = intrfc;
            if (family === 'IPv4' && !internal) {
                return address;
            }
        }
    }
    return "127.0.0.1";
}