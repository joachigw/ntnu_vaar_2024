<template>
    <div class="display">
        {{ displayValue }}
    </div>
</template>

<script>
export default {
    props: {
        displayValue: {
            type: String,
            default: "0",
            required: false,
        },
        computed: {
            display() {
                return this.displayValue === "" ? "0" : this.displayValue;
            },
        },
    },
};
</script>

<style>
@font-face {
    font-family: "calculator";
    src: url("../assets/Calculator.ttf");
}
.display {
    background-color: #a8c64e;
    border: 0.5px solid #333;
    border-bottom: 0;
    border-radius: 10px 10px 0 0;
    color: black;
    font-family: "calculator";
    font-size: 2rem;
    line-height: 300%;
    text-align: center;
    user-select: none;
}
</style>
