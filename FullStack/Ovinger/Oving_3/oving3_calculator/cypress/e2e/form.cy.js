describe("Form field validity and submission testing", () => {
  beforeEach(() => {
    cy.visit("/contact");
  });

  it("disables submit button if any fields are invalid", () => {
    const fields = ["#name-input", "#email-input", "#message-input"];
    const fieldInputs = ["j", "invalid@mail", "inv"];

    for (let i = 0; i < fields.length; ++i) {
      cy.get(fields[i]).type(fieldInputs[i]);
      cy.get("#submit-btn").click();
      cy.get("#submit-btn").should("be.disabled");
      cy.reload();
    }
  });

  it("receives submission confirmation if all fields are valid", () => {
    const fields = ["#name-input", "#email-input", "#message-input"];
    const fieldInputs = ["Joachim", "valid@mail.com", "Valid message!"];

    for (let i = 0; i < fields.length; ++i) {
      cy.get(fields[i]).type(fieldInputs[i]);
    }

    cy.get("#submit-btn").click();

    cy.get("#response-message").should("be.visible");
    cy.get("#response-message").should(
      "have.text",
      "Form submitted successfully!"
    );
  });
});
