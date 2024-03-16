import axios from "axios";

const userApiClient = axios.create({
    baseURL: "http://localhost:8080/",
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

export default {
    async postExpression(expression) {
        try {
            const response = await userApiClient.post(
                    "/api/calculator/login",
                    expression
            );

            if (response.status === 200) {
                const data = response.data;

                if (data && typeof data.result !== "undefined") {
                    const result = data.result;
                    return { success: true, result };
                } else {
                    return {
                        success: false,
                        message:
                                "Invalid response format or missing result property.",
                    };
                }
            } else {
                return {
                    success: false,
                    message: "Failed to pass expression to backend.",
                };
            }
        } catch (error) {
            console.error("Error: " + error);
            const result = error.response.data;
            return { success: false, result };
        }
    },
};
