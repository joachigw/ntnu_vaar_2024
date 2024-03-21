<template>
    <form @submit.prevent="submitLogin" id="login-form">
        <BaseInput
            id="username"
            v-model="user.username"
            label="Username"
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
            v-model="user.password"
            label="Password"
            class="field"
            type="password"
        />
        <span
                v-for="error in v$.password.$errors"
                :key="error.$uid"
                class="field-error"
        >{{ error.$message }}
        </span>

        <button id="submit-btn" type="submit" :disabled="hasErrors">Log in</button>
    </form>

    <span id="response-message" v-if="responseMessage">{{ responseMessage }}</span>

<!--    <h3>Forgot password</h3>-->
<!--    <h3>Register new user</h3>-->
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

const user = reactive({
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

const v$ = useVuelidate(rules, user);

async function submitLogin() {
    const result = await v$.value.$validate();
    if (result) {
        const response = await userApiClient.loginUser(user);
        if (response.success) {
            await userStore.saveUserInStore(response.data);
            v$.value.$reset();
            resetInputFields();
            setResponseMessage("Successfully logged in!");
            await router.push("/");
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
