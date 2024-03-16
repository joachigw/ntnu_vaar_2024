<template>
    <label v-if="props.label"> {{ props.label }}</label>
    <input
        v-bind="$attrs"
        :value="props.modelValue"
        :placeholder="props.label"
        @input="$emit('update:modelValue', $event.target.value)"
    />
</template>

<script setup>
const props = defineProps({
    label: {
        type: String,
        default: "",
    },
    modelValue: {
        type: [String, Number],
        default: "",
    },
});
</script>

<style scoped>
label {
    font-size: 1.25rem;
}

input {
    border-radius: 10px;
    color: black;
    font-size: 1rem;
    padding: 1rem;
}
</style>
