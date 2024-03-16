import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import CalculatorLog from "../CalculatorLog.vue";

describe("Calculator log", () => {
  it("renders html correctly", () => {
    const wrapper = mount(CalculatorLog);
    expect(wrapper.html()).toMatchSnapshot();
  });

  it("adds a new log item when a new item is added to the log array", async () => {
    const wrapper = mount(CalculatorLog, {
      props: {
        log: ["1+1=2", "1+2=3"],
      },
    });

    expect(wrapper.findAll("li").length).toBe(2);
    await wrapper.setProps({ log: [...wrapper.props().log, "2+2=5"] });
    expect(wrapper.findAll("li").length).toBe(3);
    expect(wrapper.findAll("li").at(2).text()).toBe("2+2=5");
  });

  it("emits 'clearLogClick' event when the 'Clear log' button is clicked", async () => {
    const log = ["1+1=2", "1+2=3"];
    const wrapper = mount(CalculatorLog, {
      props: { log: log },
    });

    await wrapper.find("#clear-log-btn").trigger("click");
    expect(wrapper.emitted("clearLogClick")).toBeTruthy();
  });
});
