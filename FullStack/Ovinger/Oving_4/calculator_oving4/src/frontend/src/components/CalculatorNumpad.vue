<script setup>
import CalculatorButton from "./CalculatorButton.vue";

//prettier-ignore
let buttons = ["AC", "ANS", "DEL", "+", 1, 2, 3, "-", 4, 5, 6, "x", 7, 8, 9, "/", 0, ".", "="];
</script>

<template>
  <div class="numpad">
    <CalculatorButton
      v-for="(button, index) in buttons"
      :value="button"
      :key="index"
      @btnClick="$emit('numpadClick', button)"
    />
  </div>
</template>

<style>
.numpad {
  display: flex;
  flex-flow: row wrap;
}
</style>
