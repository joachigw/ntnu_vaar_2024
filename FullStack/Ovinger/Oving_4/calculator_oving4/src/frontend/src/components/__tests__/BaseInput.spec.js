import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import BaseInput from "../BaseInput.vue";

describe("Base input field", () => {
  it("renders html correctly", () => {
    const wrapper = mount(BaseInput);
    expect(wrapper.html()).toMatchSnapshot();
  });

  it("renders label, field and placeholder correctly", () => {
    const wrapper = mount(BaseInput, {
      props: {
        label: "testLabel",
      },
    });
    expect(wrapper.find("label").exists()).toBe(true);
    expect(wrapper.find("label").text()).toBe("testLabel");
    expect(wrapper.find("input").attributes("placeholder")).toBe("testLabel");
  });

  it("renders without label when prop isn't specified", () => {
    const wrapper = mount(BaseInput);
    expect(wrapper.find("label").exists()).toBe(false);
    expect(wrapper.find("input").attributes("placeholder")).toBe("");
  });

  it("binds modelValue prop correctly", async () => {
    const wrapper = mount(BaseInput, {
      props: {
        modelValue: "testValue",
      },
    });
    expect(wrapper.find("input").element.value).toBe("testValue");
    await wrapper.setProps({ modelValue: "Updated" });
    expect(wrapper.find("input").element.value).toBe("Updated");
  });

  it("emits update:modelValue event", async () => {
    const wrapper = mount(BaseInput);
    await wrapper.find("input").setValue("testValue");
    expect(wrapper.emitted("update:modelValue")).toBeTruthy();
    expect(wrapper.emitted("update:modelValue")[0][0]).toBe("testValue");
  });
});
