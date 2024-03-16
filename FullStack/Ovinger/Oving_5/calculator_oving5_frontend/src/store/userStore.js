import { defineStore } from "pinia";

export const useUserStore = defineStore("token", {
    state: () => ({
        user: {
            username: null,
            password: null,
        },
    }),

    actions: {
        async saveUserInStore(name, email) {
            try {
                this.user.username = name;
                this.user.password = email;
            } catch (err) {
                console.log(err);
            }
        },
    },
});
