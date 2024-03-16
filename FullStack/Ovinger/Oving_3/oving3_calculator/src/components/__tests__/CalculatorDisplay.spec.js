import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import CalculatorDisplay from "../CalculatorDisplay.vue";

describe("Calculator display", () => {
  it("renders html correctly", () => {
    const wrapper = mount(CalculatorDisplay);
    expect(wrapper.html()).toMatchSnapshot;
  });

  it("binds displayValue prop correctly", async () => {
    const wrapper = mount(CalculatorDisplay, {
      props: {
        displayValue: "testValue",
      },
    });
    expect(wrapper.find("div").text()).toBe("testValue");
    await wrapper.setProps({ displayValue: "Updated" });
    expect(wrapper.find("div").text()).toBe("Updated");
  });
});
