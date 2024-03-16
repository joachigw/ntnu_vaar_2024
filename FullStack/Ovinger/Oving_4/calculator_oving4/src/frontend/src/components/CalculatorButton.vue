<template>
  <div :class="['btn', buttonClass]" @click="$emit('btnClick', props.value)">
    {{ value }}
  </div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  value: {
    type: [String, Number],
    required: true,
  },
});

const buttonClass = computed(() => {
  return {
    "zero-button": props.value === 0,
    "comma-button": props.value === ".",
    "equals-button": props.value === "=",
    "non-number-button":
      !/\d/.test(props.value) &&
      typeof props.value === "string" &&
      props.value.length === 1,
  };
});
</script>

<style>
.btn {
  background-color: #e9e9e9;
  border: 0.5px solid #333;
  color: black;
  font-size: 1.5rem;
  flex: 1 0 25%;
  padding: 25px;
  text-align: center;
  user-select: none;
}

.btn:hover {
  background-color: #e1e1e1;
  cursor: pointer;
}

.btn:active {
  background-color: #d4d4d4;
}

.zero-button {
  flex: 2 0 50%;
  border-bottom-left-radius: 10px;
}

.equals-button {
  border-bottom-right-radius: 10px;
}

.non-number-button {
  background-color: #ffbf00;
}

.non-number-button:hover {
  background-color: #ffac1c;
}

.non-number-button:active {
  background-color: #ffa500;
}
</style>
