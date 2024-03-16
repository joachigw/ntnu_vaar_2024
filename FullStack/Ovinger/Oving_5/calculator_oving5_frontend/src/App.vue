<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
    <div id="app">
        <nav>
            <RouterLink to="/">Calculator</RouterLink>
            <RouterLink to="/contact">Contact us</RouterLink>
            <RouterLink to="/login">Log in</RouterLink>
        </nav>
        <RouterView />
    </div>
</template>

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
