package idatt2105.oving5.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="app_user")
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "appuser_id_sequence",
            sequenceName = "appuser_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appuser_id_sequence"
    )
    private Integer id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "appUser")
    private List<Expression> expressionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
