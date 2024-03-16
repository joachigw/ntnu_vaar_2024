<template>
    <form @submit.prevent="submitLogin" id="login-form">
        <BaseInput
            id="username"
            v-model="user.username"
            label="Username"
            class="field"
        />

        <BaseInput
            id="password"
            v-model="user.password"
            label="Password"
            class="field"
            type="password"
        />
    </form>
    <h3>Forgot password</h3>
    <h3>Register new user</h3>
</template>

<script setup>
import { computed, reactive, ref } from "vue";
import { email, helpers, minLength, required } from "@vuelidate/validators";
import { useUserStore } from "@/store/userStore.js";
import BaseInput from "@/components/BaseInput.vue";
import useVuelidate from "@vuelidate/core";
import customerApiClient from "@/services/CustomerService.js";

const userStore = useUserStore();

const user = reactive({
    username: "",
    password: "",
});

const rules = computed(() => {
    return {
        name: {
            required: helpers.withMessage("This field is required.", required),
            minLength: minLength(2),
        },
        email: {
            required: helpers.withMessage("This field is required.", required),
            email: helpers.withMessage(
                    "Please enter a valid email address.",
                    email
            ),
        },
        message: {
            required: helpers.withMessage("This field is required.", required),
            minLength: minLength(5),
        },
    };
});

const v$ = useVuelidate(rules, user);

async function submitLogin() {
    const result = await v$.value.$validate();
    if (result) {
        const response = await customerApiClient.postCustomer(user);
        if (response.success) {
            await userStore.saveUserInStore(
                    user.username,
                    user.password
            );
            v$.value.$reset();
            resetInputFields();
            setResponseMessage("Form submitted successfully!");
        } else {
            setResponseMessage(
                    "An error occurred. Please try again later.\nError details:\n" +
                    response.message
            );
        }
    }
}

const hasErrors = computed(() => {
    return Object.keys(v$.value.$errors).length > 0;
});

const responseMessage = ref("");
function setResponseMessage(message) {
    responseMessage.value = message;
}

function resetInputFields() {
    user.username = "";
    user.password = "";
}
</script>

<style scoped>
#login-form {
    display: flex;
    flex-flow: column;
    padding: 30px;
}

:deep(label) {
    margin: 10px 0 10px 0;
}
</style>
