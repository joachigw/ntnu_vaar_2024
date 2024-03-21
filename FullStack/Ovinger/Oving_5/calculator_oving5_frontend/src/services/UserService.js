import axios from "axios";
import {useUserStore} from "@/store/userStore.js";


/**
 * Configuration of HTTP-requests to be sent to backend.
 * @type {axios.AxiosInstance}
 */
const userApiClient = axios.create({
    baseURL: "http://localhost:8080/",
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

// Flag used for JWT refreshing
let isRefreshing = false;

// JSON Web Token user for client verification
let token = "";

export default {

    async refreshToken(existingToken) {
        if (isRefreshing) {
            return {
                success: false,
                message: "Token refresh is already in progress",
            };
        }

        isRefreshing = true;

        try {
            console.log("Current token: " + existingToken.token);

            userApiClient.defaults.headers.common["Authorization"] = `Bearer ${existingToken.token}`;

            const response = await userApiClient.post('/api/auth/refresh', existingToken);

            if (response.status === 200) {
                isRefreshing = false;

                token = response.data;

                userApiClient.defaults.headers.common["Authorization"] = `Bearer ${token}`;

                return { success: true, token };
            } else {
                isRefreshing = false;
                return {
                    success: false,
                    message: "Failed to refresh token",
                };
            }
        } catch (error) {
            isRefreshing = false;
            return {
                success: false,
                message: "An error occurred while refreshing the token",
            };
        }
    },



    /**
     * Post request for registration of a new user.
     * @param user JSON object with username and password to create a new user with.
     * @returns {Promise<{result: string, success: boolean}|{success: boolean, message: string}|{data: any, success: boolean}>}
     */
    async registerUser(user) {
        if (user === null) {
            return {
                success: false,
                message: "The passed user is null!",
            }
        }

        try {
            const response = await userApiClient.post("/api/auth/register", {
                username: user.username,
                password: user.password
            });

            // Status 200 = OK
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
            // Status 401 = Unauthorized
            if (error.response.status === 401) {
                return {
                    success: false,
                    message: "This username already exists!",
                }
            }

            // Status 403 = Forbidden
            else if (error.response.status === 403) {
                return {
                    success: false,
                    message: "Unauthorized access!",
                }
            }
            return {success: false, result: "An unexpected error occurred, please try again later."};
        }
    },


    /**
     * Post request for user log in.
     * @param user JSON object with username and password to log in with.
     * @returns {Promise<{result: string, success: boolean}|{success: boolean, user: *}|{success: boolean, message: string}>}
     */
    async loginUser(user) {
        if (user === null) {
            return {
                success: false,
                message: "The passed user is null!",
            }
        }

        const userStore = useUserStore();

        // Own apiClient to ensure correct header-format on login requests
        const loginApiClient = axios.create({
            baseURL: "http://localhost:8080/",
            withCredentials: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        });

        try {
            const response = await loginApiClient.post("/api/auth/login", {
                username: user.username,
                password: user.password
            });

            if (response.status === 200) {
                const data = response.data;
                token = data.jwt;

                userApiClient.defaults.headers.common["Authorization"] = `Bearer ${token}`;

                if (data && typeof data !== "undefined") {
                    userStore.user.token = token;
                    return { success: true, data };

                } else {
                    return {
                        success: false,
                        message: "Invalid response format or missing username/password property.",
                    };
                }
            } else {
                return {
                    success: false,
                    message: "Failed to verify login against backend.",
                };
            }
        } catch (error) {
            // Status 401 = Unauthorized
            if (error.response.status === 401) {
                return {
                    success: false,
                    message: "The username and/or password is incorrect, or the user does not exist.",
                }
            }

            // Status 403 = Forbidden
            else if (error.response.status === 403) {
                return {
                    success: false,
                    message: "Unauthorized access!",
                }
            }
            return {success: false, result: "An unexpected error occurred, please try again later."};
        }
    },


    /**
     * Post request for fetching a logged in user's persisted expressions.
     * @param user JSON object with username and password to retrieve expressions for, for verification purposes.
     * @returns {Promise<{result: *, success: boolean}|{data: *, success: boolean}|{success: boolean, message: string}>}
     */
    async fetchExpressions(user) {
        if (user === null) {
            return {
                success: false,
                message: "The passed user is null!",
            }
        }

        try {

            // Use currently stored user's JWT if present
            const userStore = useUserStore();
            if (userStore.user.token) token = userStore.user.token;

            userApiClient.defaults.headers.common["Authorization"] = `Bearer ${token}`;

            const response = await userApiClient.post("/api/expression/user", {
                username: user.username,
                password: user.password
            });

            if (response.status === 200) {
                const data = response.data.expressionList;

                if (data && typeof data !== "undefined") {
                    return { success: true, data };

                } else {
                    return {
                        success: false,
                        message: "Invalid response format or missing username/password property.",
                    };
                }
            } else {
                return {
                    success: false,
                    message: "Failed to verify login against backend.",
                };
            }
        } catch (error) {

            // Attempt to resolve issue by refreshing JWT
            if (error.response.status === 401 || error.response.status === 403) {
                const refreshResponse = await this.refreshToken(user);

                if (refreshResponse.success) {
                    await this.fetchExpressions(user);
                } else {
                    return {success: false, message: "Failed to refresh token."};
                }
            }
        }
    }
}
