package idatt2105.oving5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="expression")
public class Expression {

    @Id
    @SequenceGenerator(
            name="expression_id_sequence",
            sequenceName = "expression_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "expression_id_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private Double firstNumber;

    @Column(nullable = false)
    private Double secondNumber;

    @Column(nullable = false)
    private String operator;

    @Column(nullable = false)
    private Double result;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "app_user_id")
    private User appUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public Double getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(Double secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public User getAppUser() {
        return appUser;
    }

    public void assignAppUser(User appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", firstNumber='" + firstNumber + '\'' +
                ", secondNumber='" + secondNumber + '\'' +
                ", operator='" + operator + '\'' +
                ", appUser=" + appUser +
                '}';
    }
}
