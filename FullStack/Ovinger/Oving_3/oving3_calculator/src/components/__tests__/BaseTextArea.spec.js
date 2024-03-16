import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import BaseTextArea from "../BaseTextArea.vue";

describe("Base text area", () => {
  it("renders html correctly", () => {
    const wrapper = mount(BaseTextArea);
    expect(wrapper.html()).toMatchSnapshot();
  });

  it("should render label, placeholder and text area correctly", () => {
    const wrapper = mount(BaseTextArea, {
      props: {
        label: "testLabel",
      },
    });
    expect(wrapper.find("label").exists()).toBe(true);
    expect(wrapper.find("label").text()).toBe("testLabel");
    expect(wrapper.find("textarea").attributes("placeholder")).toBe(
      "testLabel"
    );
  });

  it("renders without label when prop isn't specified", () => {
    const wrapper = mount(BaseTextArea);
    expect(wrapper.find("label").exists()).toBe(false);
    expect(wrapper.find("textarea").attributes("placeholder")).toBe("");
  });

  it("binds modelValue prop correctly", async () => {
    const wrapper = mount(BaseTextArea, {
      props: {
        modelValue: "testValue",
      },
    });
    expect(wrapper.find("textarea").element.value).toBe("testValue");
    await wrapper.setProps({ modelValue: "Updated" });
    expect(wrapper.find("textarea").element.value).toBe("Updated");
  });

  it("emits update:modelValue event", async () => {
    const wrapper = mount(BaseTextArea);
    await wrapper.find("textarea").setValue("testValue");
    expect(wrapper.emitted("update:modelValue")).toBeTruthy();
    expect(wrapper.emitted("update:modelValue")[0][0]).toBe("testValue");
  });
});
