<template>
    <label v-if="props.label"> {{ props.label }}</label>
    <textarea
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

textarea {
    border-radius: 10px;
    color: black;
    font-family: "Helvetica", sans-serif;
    font-size: 1rem;
    padding: 10px;
    height: 180px;
}
</style>
