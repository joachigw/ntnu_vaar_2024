package idatt2105.oving5.model;

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

    private String firstNumber;
    private String secondNumber;
    private String operator;

    @ManyToOne
    @JoinColumn(name="appuser_id")
    private AppUser appUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "firstNumber='" + firstNumber + '\'' +
                ", secondNumber='" + secondNumber + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
