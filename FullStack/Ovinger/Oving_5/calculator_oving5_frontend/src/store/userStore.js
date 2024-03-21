import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import userApiClient from "@/services/UserService.js";

export const useUserStore = defineStore("token", {
    state: () => ({
        user: {
            username: null,
            password: null,
            token: null
        },
    }),

    persist: {
        storage: sessionStorage,
    },

    actions: {
        async saveUserInStore(user) {
            try {
                this.user.username = user.username;
                this.user.password = user.password;
                this.user.token = user.jwt;
            } catch (err) {
                console.log(err);
            }
        },

        async refreshToken() {
            try {
                const decodedToken = jwtDecode(this.user.token);
                const currentTime = Date.now() / 1000;

                // Refresh token when there is at least 286 seconds left (allows for two refresh attempts, testing)
                if (decodedToken.exp < currentTime + 286) {
                    const existingToken = { token: this.user.token };
                    const response = await userApiClient.refreshToken(existingToken);

                    if (response.success) {
                        this.user.token = response.token.token;
                    }
                }
            } catch (err) {
                console.log(err);
            }
        },

        logOut() {
            this.user.username = null;
            this.user.password = null;
            this.user.token = null;
        }
    },
});
