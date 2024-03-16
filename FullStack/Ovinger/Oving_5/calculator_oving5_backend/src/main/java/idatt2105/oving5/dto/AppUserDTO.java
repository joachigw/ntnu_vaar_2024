package idatt2105.oving5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUserDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("validUser")
    private boolean validUser;

    public AppUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public boolean getValidUser() {
        return validUser;
    }
}
