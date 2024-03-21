<template>
    <span v-if="userStore.user.username">Logged in as: {{ userStore.user.username }}</span>
    <button v-if="userStore.user.username" @click="logOut">Log out</button>
    <div class="app-wrapper">
        <div class="calculator-wrapper">
            <CalculatorDisplay id="display" :displayValue="displayValue" />
            <CalculatorNumpad @numpadClick="handleNumpadBtnClicked" />
        </div>
        <CalculatorLog
                :log="results"
                @clearLogClick="handleClearLogBtnClicked"
        />
    </div>
</template>

<script setup>
import CalculatorDisplay from "../components/CalculatorDisplay.vue";
import CalculatorLog from "../components/CalculatorLog.vue";
import CalculatorNumpad from "../components/CalculatorNumpad.vue";
import calculatorApiClient from "../services/CalculatorService.js";
import { useUserStore } from "@/store/userStore.js";
import {ref, onBeforeMount, reactive} from "vue";
import userApiClient from "@/services/UserService.js";

const userStore = useUserStore();

let previous = 0;
let current = 0;
let operator = null;
let operatorFunction = null;
let operatorActive = false;
let displayValue = ref("0");
let sumPressed = false;
let hasComma = false;
let result = 0;
let results = ref([]);


function setPrevious(value) {
    previous = operatorFunction(parseFloat(current), parseFloat(previous));
    current = "";
    operatorActive = true;
    displayValue.value += `${value}`;
    hasComma = false;
}

function handleNumberClicked(value) {
    if (displayValue.value === "0") {
        displayValue.value = `${value}`;
        current = value;
    } else {
        displayValue.value += `${value}`;
        current += `${value}`;
    }
    operatorActive = false;
}

function handleOperatorClicked(value) {
    switch (value) {
        case "+": {
            operator = `${value}`;
            operatorFunction = (a, b) => a + b;
            setPrevious(value);
            break;
        }
        case "-": {
            operator = `${value}`;
            operatorFunction = (a, b) => a - b;
            setPrevious(value);
            break;
        }
        case "x": {
            operator = `${value}`;
            if (previous === 0) previous = 1;
            operatorFunction = (a, b) => a * b;
            setPrevious(value);
            break;
        }
        case "/": {
            operator = `${value}`;
            if (previous === 0) previous = 1;
            operatorFunction = (a, b) => a / b;
            setPrevious(value);
            break;
        }
        case ".": {
            if (hasComma) {
                break;
            }
            current = `${current}.`;
            displayValue.value = `${displayValue.value}.`;
            hasComma = true;
            break;
        }
        case "=": {
            sum();
            break;
        }
        case "AC": {
            allClear();
            break;
        }
        case "ANS": {
            ans();
            break;
        }
        case "DEL": {
            del();
            break;
        }
    }
}

function handleNumpadBtnClicked(value) {
    if (sumPressed === true) {
        allClear();
        sumPressed = false;
    }
    if (typeof value === "number") handleNumberClicked(value);
    else if (typeof value === "string") {
        if (operatorActive) return;
        handleOperatorClicked(value);
    } else {
        alert("There was an error parsing the value of the button!");
        console.log(value);
    }
}

function handleClearLogBtnClicked() {
    results.value = [];
}

function allClear() {
    previous = 0;
    current = 0;
    operator = null;
    operatorFunction = null;
    operatorActive = false;
    hasComma = false;
    displayValue.value = "0";
}

function ans() {
    current = result;
    displayValue.value = `${current}`;
}

function del() {
    if (displayValue.value.length > 1) {
        current = `${current}`.substring(0, current.length - 1);
        displayValue.value = `${displayValue.value}`.substring(
                0,
                displayValue.value.length - 1
        );
    } else if (displayValue.value.length === 1) {
        current = 0;
        displayValue.value = "0";
    }
}

async function sum() {

    // Process common syntax errors eagerly
    if ((previous === 0 || current === 0 || current === "0") && operator === "/") {
        alert("You cannot divide with 0! Resetting...");
        allClear();
        return;
    }
    if (displayValue.value === "0") {
        alert("You must enter a valid expression.");
        return;
    }
    if (operator === null) {
        alert("You must select an operator before calculating.");
        return;
    }

    // Process expression with REST Spring Boot
    const expression = {
        firstNumber: previous,
        secondNumber: current,
        operator: operator
    };

    const expressionPost = await calculatorApiClient.postExpression(expression, userStore.user);
    const firstNumber = expressionPost.data.firstNumber;
    const secondNumber = expressionPost.data.secondNumber;
    const eOperator = expressionPost.data.operator;
    const eResult = expressionPost.data.result;

    if (eResult !== "") displayValue.value = String(result);
    else displayValue.value = "0";

    sumPressed = true;
    results.value =[...results.value, `${firstNumber} ${eOperator} ${secondNumber} = ${eResult}`];
}

function logOut() {
    userStore.logOut();
    results.value = [];
}

// Fetch stored user if any and expressions if any
onBeforeMount(async () => {
    if (userStore.user && userStore.user.username !== null) {
        const response = await userApiClient.fetchExpressions(userStore.user);
        if (response.data !== undefined && (response.data.length > 0)) {
            for (let i = 0; i < response.data.length; ++i) {
                const expression = response.data[i];
                const logItem = `${expression.firstNumber} ${expression.operator} ${expression.secondNumber} = ${expression.result}`;
                results.value = [...results.value, logItem];
            }
        }
    }
});
</script>

<style scoped>
@media (width >= 820px) {
    .app-wrapper {
        display: flex;
        flex-flow: row nowrap;
        gap: 10px;
    }
}

@media (width < 820px) {
    .app-wrapper {
        display: flex;
        flex-flow: column;
        gap: 10px;
    }
}

.calculator-wrapper {
    border-radius: 10px;
    display: flex;
    flex-flow: column;
    width: 400px;
}

button {
    color: black;
}
</style>
