package idatt2105.oving5.dto;

import idatt2105.oving5.model.User;

public class LoginResponseDTO {

    private String username;
    private String password;
    private String jwt;

    public LoginResponseDTO() {
        super();
    }

    public LoginResponseDTO(User user, String jwt) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
