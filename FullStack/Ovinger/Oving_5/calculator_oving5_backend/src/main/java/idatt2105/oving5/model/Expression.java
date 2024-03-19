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

    @Column(nullable = false)
    private String firstNumber;

    @Column(nullable = false)
    private String secondNumber;

    @Column(nullable = false)
    private String operator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "app_user_id")
    private User appUser;


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
