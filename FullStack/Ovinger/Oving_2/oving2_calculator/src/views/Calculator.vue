<script setup>
import CalculatorDisplay from "../components/CalculatorDisplay.vue";
import CalculatorLog from "../components/CalculatorLog.vue";
import CalculatorNumpad from "../components/CalculatorNumpad.vue";
</script>

<template>
    <div class="app-wrapper">
        <div class="calculator-wrapper">
            <CalculatorDisplay :displayValue="displayValue" />
            <CalculatorNumpad @numpadClick="handleNumpadBtnClicked" />
        </div>
        <CalculatorLog
            :log="results"
            @clearLogClick="handleClearLogBtnClicked"
        />
    </div>
</template>

<script>
export default {
    data() {
        return {
            previous: 0,
            current: 0,
            operator: null,
            operatorFunction: null,
            operatorActive: false,
            displayValue: "0",
            sumPressed: false,
            hasComma: false,
            result: 0,
            results: [
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
            ],
        };
    },
    methods: {
        setPrevious(value) {
            this.previous = this.operatorFunction(
                parseFloat(this.current),
                parseFloat(this.previous)
            );
            console.log("Previous set to " + this.previous);
            this.current = "";
            this.operatorActive = true;
            this.displayValue += `${value}`;
            this.hasComma = false;
        },

        checkOperatorActive() {
            if (this.operatorActive) {
                alert("Operator already active! (" + this.operator + ")");
            }
        },

        handleNumberClicked(value) {
            if (this.displayValue == "0") {
                this.displayValue = `${value}`;
                this.current = value;
            } else {
                this.displayValue += `${value}`;
                this.current += `${value}`;
            }
            this.operatorActive = false;
            console.log("Current set to " + this.current);
            console.log("Display set to show " + this.displayValue);
            console.log("Operator active set to false");
        },

        handleOperatorClicked(value) {
            switch (value) {
                case "+": {
                    this.operator = `${value}`;
                    this.operatorFunction = (a, b) => a + b;
                    this.setPrevious(value);
                    console.log("+ was pressed, operator active");
                    break;
                }
                case "-": {
                    this.operator = `${value}`;
                    this.operatorFunction = (a, b) => a - b;
                    this.setPrevious(value);
                    console.log("- was pressed, operator active");
                    break;
                }
                case "x": {
                    this.operator = `${value}`;
                    if (this.previous == 0) {
                        this.previous = 1;
                    }
                    this.operatorFunction = (a, b) => a * b;
                    this.setPrevious(value);
                    console.log("x was pressed, operator active");
                    break;
                }
                case "/": {
                    this.operator = `${value}`;
                    if (this.previous == 0) {
                        this.previous = 1;
                    }
                    this.operatorFunction = (a, b) => a / b;
                    this.setPrevious(value);
                    console.log("/ was pressed, operator active");
                    break;
                }
                case ".": {
                    if (this.hasComma) {
                        break;
                    }
                    this.current = `${this.current}${"."}`;
                    this.displayValue = `${this.displayValue}${"."}`;
                    this.hasComma = true;
                    break;
                }
                case "=": {
                    this.sum();
                    break;
                }
                case "AC": {
                    this.aclear();
                    break;
                }
                case "ANS": {
                    this.ans();
                    break;
                }
                case "DEL": {
                    this.del();
                    break;
                }
            }
        },

        handleNumpadBtnClicked(value) {
            if (this.sumPressed) {
                this.aclear();
                this.sumPressed = false;
            }
            if (typeof value === "number") {
                this.handleNumberClicked(value);
            } else if (typeof value === "string") {
                if (this.operatorActive) {
                    return;
                }
                this.handleOperatorClicked(value);
            } else {
                alert("There was an error parsing the value of the button!");
                return;
            }
        },

        handleClearLogBtnClicked() {
            this.results = [];
        },

        aclear() {
            this.previous = 0;
            this.current = 0;
            this.operator = null;
            this.operatorFunction = null;
            this.operatorActive = false;
            this.hasComma = false;
            this.displayValue = "0";
            console.log("AC pressed, everything has been reset");
        },
        ans() {
            this.current = this.result;
            this.displayValue = `${this.current}`;
            console.log(
                "ANS pressed, current set to result (" + this.current + ")"
            );
        },
        del() {
            if (`${this.current}`.length > 1) {
                this.current = `${this.current}`.substring(
                    0,
                    this.current.length - 1
                );
                this.displayValue = `${this.current}`;
            } else {
                this.current = 0;
                this.displayValue = `${this.current}`;
            }
            console.log("DEL pressed, current changed to " + `${this.current}`);
        },
        sum() {
            console.log("Sum from " + this.current + " and " + this.previous);
            if (
                (this.previous === 0 ||
                    this.current === 0 ||
                    this.current === "0") &&
                this.operator === "/"
            ) {
                alert(
                    "You cannot divide with 0! Resetting... (operator: " +
                        this.operator +
                        ")"
                );
                this.aclear();
                return;
            }
            this.result = this.operatorFunction(
                parseFloat(this.previous),
                parseFloat(this.current)
            );
            this.displayValue = `${this.result}`;
            this.sumPressed = true;
            console.log("= pressed, result set to " + this.result);
            console.log("Adding " + this.result + " to the result array");
            this.results.push(
                `${this.previous}${this.operator}${this.current}${" = "}${
                    this.result
                }`
            );
            console.log(this.results);
        },
        logResult() {},
    },
};
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
</style>
