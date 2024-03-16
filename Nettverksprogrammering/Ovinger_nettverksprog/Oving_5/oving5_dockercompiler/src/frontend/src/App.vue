<template>
    <div id="main">
        <h1>&#128011; &#128230; &#128187;
            Compile some code with Docker
            &#128187; &#128230; &#128011;</h1>

        <BaseTextArea
                id="code-input"
                v-model="codeInput"
                label="Code"
                placeholder="Enter code here"
        />
        <div id="buttons">
            <LanguageRadioGroup @languageChange="handleLanguageChange"/>
            <div id="compiler-btn" @click="compileAndRun()">Compile and run</div>
        </div>
        <BaseTextArea
                id="code-output"
                v-model="codeOutput"
                label="Output"
                disabled="true"
                whiteText="true"
                readonly
        />
    </div>
</template>

<script setup>
import BaseTextArea from "@/components/BaseTextArea.vue";
import apiClient from "@/services/CompilerService.js";
import { ref } from "vue";
import LanguageRadioGroup from "@/components/LanguageRadioGroup.vue";


const codeInput = ref("");
let codeOutput = ref("");
let chosenLanguage = "java";

async function compileAndRun() {

    if(codeInput.value === "") {
        alert("You must enter code before compiling!")
        return;
    }

    codeOutput.value = "Loading...";
    const sourceCodeObj = {
        sourceCode: codeInput.value,
        language: chosenLanguage
    }
    const response = await apiClient.postSourceCode(sourceCodeObj);
    const data = response.data.output;
    if (data) codeOutput.value = data;
}

function handleLanguageChange(newLanguage) {
    chosenLanguage = newLanguage;
}
</script>


<style scoped>
#main {
    display: flex;
    flex-flow: column;
    gap: 10px;
    margin: auto;
    max-width: 900px;
    justify-content: center;
    align-items: center;
}

#buttons {
    display: flex;
    flex-flow: row;
    align-items: center;
    gap: 15px;
    justify-content: space-between;
    width: 600px;
}

#compiler-btn {
    background-color: dodgerblue;
    border-radius: 10px;
    color: white;
    font-size: 1.5rem;
    margin: auto;
    padding: 10px;
    width: 250px;
    text-align: center;
}

#compiler-btn:hover {
    background-color: #1e70ff;
    cursor: pointer;
}

#compiler-btn:active {
    background-color: #1e50ff;
}

#code-output {
    color: white;
}
</style>
