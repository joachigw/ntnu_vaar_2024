package oving5_dockercompiler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputDTO {
    @JsonProperty("output")
    private String output;

    public OutputDTO(String output) {
        this.output = output;
    }

    public OutputDTO() {
    }

    public String getOutput() {
        return output;
    }
}
