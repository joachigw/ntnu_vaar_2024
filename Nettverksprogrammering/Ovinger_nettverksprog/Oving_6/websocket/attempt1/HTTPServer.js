const os = require('os');
const net = require("net");
const fs = require("fs");

// HTTP server that responds with an HTML file which uses the WebSocket server
const httpServer = net.createServer((connection) => {
    connection.on("data", () => {
        fs.readFile('index.html', 'utf8', (err, htmlContent) => {
            if (err) {
                console.error("\u001b[31mError reading HTML file: " + err + "\u001b[0m");
                return;
            }

            // Send the HTML content as a response to the client
            let response = "HTTP/1.1 200 OK\r\nContent-Length: " + htmlContent.length + "\r\n\r\n" + htmlContent;
            connection.write(response);
        });
    });
});

// Configures the server to listen to incoming traffic on the local IPv4 of the device on port 3000
httpServer.listen(3000, getLocalIP(), () => {
    console.log("HTTP server listening on port \u001b[32m3000\u001b[0m on IP-adr\u001b[32m",
            httpServer.address().address, "\u001b[0m");
});

/**
 * Retrieves the IPv4 address of the device this server is being run on in dot-decimal notation.
 * @returns {string} The IPv4 address
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