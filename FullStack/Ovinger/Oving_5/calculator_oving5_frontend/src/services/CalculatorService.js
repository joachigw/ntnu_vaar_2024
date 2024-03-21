import axios from "axios";

const calculatorApiClient = axios.create({
    baseURL: "http://localhost:8080/",
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

export default {
    async postExpression(expression, user) {
        const data = {
            expression: expression,
            user: user
        }
        try {
            const response = await calculatorApiClient.post(
                "/api/calculator/calculate",
                data
            );

            if (response.status === 200) {
                const data = response.data;

                if (data && typeof data.result !== "undefined") {
                    return { success: true, data };
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
            const result = error.response.data;
            return { success: false, result };
        }
    },
};
