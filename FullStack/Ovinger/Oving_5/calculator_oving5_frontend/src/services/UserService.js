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
    async postUser(user) {
        try {
            const response = await userApiClient.post(
                    "/api/appusers/",
                    user
            );

            if (response.status === 200) {
                const data = response.data;

                if (data && typeof data !== "undefined") {
                    return { success: true, data };
                } else {
                    return {
                        success: false,
                        message: "Invalid response format or missing result property.",
                    };
                }
            } else {
                return {
                    success: false,
                    message: "Failed to pass user to backend.",
                };
            }
        } catch (error) {
            console.error("Error: " + error);
            const result = error.response.data;
            return { success: false, result };
        }
    },
};
