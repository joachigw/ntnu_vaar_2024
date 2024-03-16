<template>
    <label v-if="props.label" :style="{ color: textColor }"> {{ props.label }}</label>
    <textarea
            v-bind="$attrs"
            :value="props.modelValue"
            :placeholder="props.placeholder"
            @input="$emit('update:modelValue', $event.target.value)"
            :style="{ color: textColor }"
    />
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
    label: {
        type: String,
        default: "",
    },
    placeholder: {
        type: String,
        default: ""
    },
    modelValue: {
        type: [String, Number],
        default: "",
    },
    whiteText: {
        type: String
    }
});

const textColor = computed(() => props.whiteText ? "white" : "black");
</script>

<style scoped>
label {
    font-size: 1.25rem;
}

textarea {
    border: none;
    overflow: auto;
    outline: none;
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;

    background: rgba(255, 255, 255, 0.25);
    border-radius: 10px;
    font-family: "Helvetica", sans-serif;
    font-size: 1rem;
    height: 300px;
    padding: 15px;
    resize: none;
    width: 600px;
}
</style>