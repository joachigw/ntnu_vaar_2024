import { createRouter, createWebHistory } from "vue-router";
import CalculatorView from "@/views/CalculatorView.vue";
import FormView from "@/views/FormView.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";

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
        {
            path: "/login",
            component: LoginView,
        },
        {
            path: "/register",
            component: RegisterView,
        }
    ],
});
