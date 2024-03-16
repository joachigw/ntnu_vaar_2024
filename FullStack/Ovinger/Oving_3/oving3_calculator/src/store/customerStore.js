import { defineStore } from "pinia";

export const useCustomerStore = defineStore("token", {
    state: () => ({
        customer: {
            name: null,
            email: null,
        },
    }),

    actions: {
        async saveCustomerInStore(name, email) {
            try {
                this.customer.name = name;
                this.customer.email = email;
            } catch (err) {
                console.log(err);
            }
        },
    },
});
