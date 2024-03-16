import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import CalculatorButton from "../CalculatorButton.vue";

describe("Calculator button", () => {
  it("renders html correctly", () => {
    const wrapper = mount(CalculatorButton);
    expect(wrapper.html()).toMatchSnapshot();
  });

  it("displays the correct value", () => {
    const wrapper = mount(CalculatorButton, {
      props: {
        value: 6,
      },
    });
    expect(wrapper.find("div").exists()).toBe(true);
    expect(+wrapper.find("div").text()).toBe(6);
  });

  it("emits btnClick event correctly", async () => {
    const wrapper = mount(CalculatorButton, {
      props: {
        value: "testValue",
      },
    });
    await wrapper.find("div").trigger("click");
    expect(wrapper.emitted("btnClick")).toBeTruthy();
    expect(wrapper.emitted("btnClick")[0][0]).toBe("testValue");

    await wrapper.setProps({ value: 6 });
    await wrapper.find("div").trigger("click");
    expect(wrapper.emitted("btnClick")).toBeTruthy();
    expect(wrapper.emitted("btnClick")[1][0]).toBe(6);
  });
});
