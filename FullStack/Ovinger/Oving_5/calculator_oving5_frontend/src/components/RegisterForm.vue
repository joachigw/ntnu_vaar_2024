<template>
    <form @submit.prevent="submitRegister" id="register-form">
        <BaseInput
                id="username"
                v-model="newUser.username"
                label="New username"
                class="field"
        />
        <span
                v-for="error in v$.username.$errors"
                :key="error.$uid"
                class="field-error"
        >{{ error.$message }}
        </span>

        <BaseInput
                id="password"
                v-model="newUser.password"
                label="New password"
                class="field"
                type="password"
        />
        <input type="checkbox" @click="togglePasswordVisibility()">Show Password
        <span
                v-for="error in v$.password.$errors"
                :key="error.$uid"
                class="field-error"
        >{{ error.$message }}
        </span>

        <button id="submit-btn" type="submit" :disabled="hasErrors">Register</button>
    </form>

    <span id="response-message" v-if="responseMessage">{{ responseMessage }}</span>
</template>

<script setup>
import { computed, reactive, ref } from "vue";
import { helpers, minLength, required } from "@vuelidate/validators";
import { useUserStore } from "@/store/userStore.js";
import BaseInput from "@/components/BaseInput.vue";
import useVuelidate from "@vuelidate/core";
import userApiClient from "@/services/UserService.js";
import router from "@/router/index.js";

const userStore = useUserStore();

const newUser = reactive({
    username: "",
    password: "",
});

const rules = computed(() => {
    return {
        username: {
            required: helpers.withMessage("This field is required.", required),
            minLength: minLength(5),
        },
        password: {
            required: helpers.withMessage("This field is required.", required),
            minLength: minLength(5),
        },
    };
});

const v$ = useVuelidate(rules, newUser);

async function submitRegister() {
    const result = await v$.value.$validate();
    if (result) {
        const response = await userApiClient.registerUser(newUser);
        if (response.success) {
            await userStore.saveUserInStore(
                    newUser.username,
                    newUser.password
            );
            v$.value.$reset();
            resetInputFields();
            setResponseMessage("Successfully registered!");
            await router.push("/login")
        }
        else if (response.success === false) {
            setResponseMessage(response.message);
        }
        else {
            setResponseMessage("An error occurred. Please try again later.\nError details:\n" + response.message);
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
    newUser.username = "";
    newUser.password = "";
}

function togglePasswordVisibility() {
    const passwordField = document.getElementById("password");
    if (passwordField.type === "password") {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
}
</script>

<style scoped>
#register-form {
    display: flex;
    flex-flow: column;
    padding: 30px;
}

:deep(label) {
    margin: 10px 0 10px 0;
}

#submit-btn {
    background-color: green;
    border-radius: 10px;
    color: white;
    font-size: 1.5rem;
    margin-top: 30px;
    padding: 10px;
}

#submit-btn:hover {
    background-color: #007700;
}

#submit-btn:disabled {
    background-color: gray;
}

.field-error {
    color: red;
    font-size: 1.25rem;
}

#response-message {
    color: green;
    font-size: 1.25rem;
}
</style>
