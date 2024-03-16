const runExpression = (...expression) => {
    expression.forEach((button_type) => {
        cy.contains("div", button_type).click();
    });
};

describe("Calculator expressions testing", () => {
    beforeEach(() => {
        cy.visit("/");
    });

    it("displays the correct result", () => {
        runExpression(1, "+", 2, "=");
        cy.get("#display").contains("3");
    });

    it("deletes the last number/operator in the display", () => {
        runExpression(1, "+", 2, 3, "DEL");
        cy.get("#display").contains("1+2");
        runExpression(1, "+", "DEL");
        cy.get("#display").contains("1");
    });

    it("logs the result in the log", () => {
       runExpression(1, "+", 2, "=");
        cy.get("#log-list").children("li").contains("1 + 2 = 3");
    })

    it("clears the log when the clear button is pressed", () => {
        runExpression(1, "+", 2, "=");
        cy.get("#clear-log-btn").click();
        cy.get("#log-list").children("li").should("not.exist");
    })
});
