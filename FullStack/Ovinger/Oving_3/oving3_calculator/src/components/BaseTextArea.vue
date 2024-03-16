<template>
    <label v-if="label"> {{ label }}</label>
    <textarea
        v-bind="$attrs"
        :value="modelValue"
        :placeholder="label"
        @input="$emit('update:modelValue', $event.target.value)"
    />
</template>

<script>
export default {
    props: {
        label: {
            type: String,
            default: "",
        },
        modelValue: {
            type: [String, Number],
            default: "",
        },
    },
};
</script>

<style scoped>
label {
    font-size: 1.25rem;
}

textarea {
    border-radius: 10px;
    color: black;
    font-family: "Helvetica", sans-serif;
    font-size: 1rem;
    padding: 10px;
    height: 180px;
}
</style>
