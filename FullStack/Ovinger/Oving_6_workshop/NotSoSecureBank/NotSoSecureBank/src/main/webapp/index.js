$(document).ready(function() {
    // Log out
    $.ajax({
        url: 'webresources/session',
        type: 'DELETE'
    });

    var wrongPassword = false;
    var locked = null;

    // Log in
    $("#loginForm").submit(function(event) {
        event.preventDefault();
        $("#wrongPasswordAlert").addClass("hide");
        $("#accountLockedAlert").addClass("hide");

        if(locked != null) {
            var diff = new Date().getTime() - locked.getTime();
            if(diff/1000 < 60) {
                $("#accountLockedAlert").removeClass("hide");
                locked = new Date();
                return;
            } else {
                locked = null;
            }
        }

        $.ajax({
            url: 'webresources/session',
            type: 'POST',
            data: JSON.stringify({
                email: $("#inputEmail").val(),
                password: $("#inputPassword").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            complete: function(jqXHR, textStatus) {
                switch (jqXHR.status) {
                    case 200:
                        document.cookie = "testcookie=sensitive data";
                        window.location.href="mypage.html";
                        break;
                    case 401:
                        if(wrongPassword) {
                            $("#accountLockedAlert").removeClass("hide");
                            locked = new Date();
                            wrongPassword = false;
                        } else {
                            $("#wrongPasswordAlert").removeClass("hide");
                            wrongPassword = true;
                        }
                        break;
                    default:
                        window.location.href="error.html";
                        break;
                }
            }
        });
    });
});