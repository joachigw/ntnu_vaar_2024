<template>
    <div id="radio-buttons">
        <label class="container">
            Java
            <input type="radio" checked="checked" name="language" @change="$emit('languageChange', 'java')">
            <span class="checkmark"></span>
        </label>

        <label class="container">
            Python
            <input type="radio" name="language" @change="$emit('languageChange', 'python')">
            <span class="checkmark"></span>
        </label>

        <label class="container">
            C
            <input type="radio" name="language" @change="$emit('languageChange', 'c')">
            <span class="checkmark"></span>
        </label>

        <label class="container">
            C++
            <input type="radio" name="language" @change="$emit('languageChange', 'cpp')">
            <span class="checkmark"></span>
        </label>
    </div>
</template>

<script setup>

</script>

<style scoped>
#radio-buttons {
    display: flex;
    flex-wrap: wrap;
}

.container {
    color: black;
    font-size: 1.25rem;
    flex-basis: calc(50%);
}
</style>