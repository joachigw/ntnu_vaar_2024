import { createRouter, createWebHistory } from "vue-router";
import CalculatorView from "../views/CalculatorView.vue";
import FormView from "../views/FormView.vue";

export default createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component: CalculatorView,
        },
        {
            path: "/contact",
            component: FormView,
        },
    ],
});
