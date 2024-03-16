import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import CalculatorNumpad from "../CalculatorNumpad.vue";
import CalculatorButton from "../CalculatorButton.vue";

describe("Calculator numpad", () => {
  it("renders html correctly", () => {
    const wrapper = mount(CalculatorNumpad);
    expect(wrapper.html()).toMatchSnapshot();
  });

  it("renders a button for each item in the buttons array", () => {
    const wrapper = mount(CalculatorNumpad);
    const buttons = wrapper.findAllComponents(CalculatorButton);
    expect(buttons).toHaveLength(wrapper.vm.buttons.length);
  });

  it('emits "numpadClick" event when a button is clicked', async () => {
    const wrapper = mount(CalculatorNumpad);
    const wrapperButton = wrapper.findComponent(CalculatorButton);
    await wrapperButton.trigger("click");
    expect(wrapperButton.emitted("btnClick")).toBeTruthy();
    expect(wrapper.emitted("numpadClick")).toBeTruthy();
  });
});
