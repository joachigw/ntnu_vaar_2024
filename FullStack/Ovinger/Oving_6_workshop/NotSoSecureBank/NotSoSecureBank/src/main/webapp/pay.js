$(document).ready(function() {
    var loggedOnEmail;
    var confirm = false;

    getLoggedOnUser(function(data) {
        loggedOnEmail = data.email;
        $("#logout").html(data.firstName + " " + data.lastName + ": logg ut");
    });

    // Betal
    $("#payForm").submit(function(event) {
        event.preventDefault();
        $("#needConfirmationAlert").addClass("hide");
        $("#exeededMaxAlert").addClass("hide");
        $("#invalidAmountAlert").addClass("hide");

        var amount = Number($("#amount").val());
        if(amount >= 10000 && amount < 100000 && !confirm) {
            $("#needConfirmationAlert").removeClass("hide");
            confirm = true;
            return;
        } else if(amount >= 100000) {
            $("#exeededMaxAlert").removeClass("hide");
            return;
        }
        confirm = false;

        $.ajax({
            url: 'webresources/user/' + loggedOnEmail + '/transaction',
            type: 'POST',
            data: JSON.stringify({
                toEmail: $("#email").val(),
                fromEmail: loggedOnEmail,
                text: $("#description").val(),
                amount: $("#amount").val(),
                transactionTime: new Date().toJSON()
            }),
            contentType: 'application/json; charset=utf-8',
            complete: function(jqXHR, textStatus) {
                switch (jqXHR.status) {
                    case 204:
                        window.location.href="account.html";
                        break;
                    case 400:
                        $("#invalidAmountAlert").removeClass("hide");
                        break;
                    default:
                        window.location.href="error.html";
                        break;
                }
            }
        });
    });
});