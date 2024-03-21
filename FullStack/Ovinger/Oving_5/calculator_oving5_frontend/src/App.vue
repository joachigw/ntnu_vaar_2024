<template>
    <div id="app">
        <nav>
            <RouterLink to="/">Calculator</RouterLink>
            <RouterLink to="/contact">Contact us</RouterLink>
            <RouterLink to="/login">Log in</RouterLink>
            <RouterLink to="/register">Register new user</RouterLink>
        </nav>
        <RouterView />
    </div>
</template>

<script setup>
import { onMounted, onUnmounted, watch } from "vue";
import { useUserStore } from "@/store/userStore.js";

const userStore = useUserStore();
let intervalId = null;

// Interval to check for the necessity of a new JWT
const startInterval = () => {
    if (!intervalId && userStore.user.token) {
        intervalId = setInterval(async () => {
            console.log("Attempting to refresh token...");
            let existingToken = userStore.user.token;

            await userStore.refreshToken();

            if (existingToken === userStore.user.token) {
                console.log("Token did not need to be refreshed just yet.");
            } else {
                console.log("New token: " + userStore.user.token);
            }
        }, 5000);
    }
};

const stopInterval = () => {
    if (intervalId) {
        clearInterval(intervalId);
        intervalId = null;
    }
};

// Only starts the interval if there is a token stored in the user
onMounted(() => {
    if (userStore.user.token) {
        startInterval();
    }

    // Creates a new interval if the old and new token do not match
    // Ensures that only intervals for authenticated users are running
    watch(() => userStore.user.token, (newToken, oldToken) => {
        if (newToken !== oldToken) {
            stopInterval();
            startInterval();
        }
    });
});

onUnmounted(stopInterval);

</script>



<style scoped>
#app {
    color: #2c3e50;
    text-align: center;
    display: flex;
    flex-flow: column;
    gap: 10px;
}
nav {
    width: 100%;
    font-size: 24px;
    text-align: center;
}

nav a.router-link-exact-active {
    color: var(--color-text);
}

nav a.router-link-exact-active:hover {
    background-color: transparent;
}

nav a {
    display: inline-block;
    padding: 20px 20px;
    border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
    border: 0;
}

hr {
    border-left: 1px solid var(--color-border);
}
</style>
