import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8080/",
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

export default {
    async postSourceCode(sourceCode) {
        try {
            const response = await apiClient.post(
                    "/api/compiler/compile-and-run-in-docker",
                    sourceCode
            );

            if (response.status === 200) {
                const data = response.data;

                if (data && typeof data !== "undefined") {

                    return { success: true, data };
                } else {
                    console.error("Error in response: ", response.data);
                    return {
                        success: false,
                        message: "Invalid response format or missing the 'output' property.",
                    };
                }
            } else {
                return {
                    success: false,
                    message: "Failed to pass source code to backend.",
                };
            }
        } catch (error) {
            console.error("Error: " + error)
            const result = error.response.data;
            return { success: false, result };
        }
    },
};
