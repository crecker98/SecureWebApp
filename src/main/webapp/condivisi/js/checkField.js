function checkDescription(string) {

    var regex = /^\S.*(?:\r?\n\S.*)*$/u;

    var lines = string.split('\n');
    for(var i = 0; i < lines.length; i++)
    {
        if(!lines[i].match(regex)) {
            return false;
        }
    }

    return true;

}

function checkUsername(string) {

    var regex = new RegExp("^[a-zA-Z0-9]+$");
    return regex.test(string);

}

function checkEmail(string) {

    var regex = new RegExp(/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/);
    return regex.test(string);

}

function checkName(string) {

    var regex = new RegExp("^([ \u00c0-\u01ffa-zA-Z'\\-])+$");
    return regex.test(string);

}

function checkPassword(pwd, info) {

    var result = false;

    var progressBar = $('#progressFirst');
    if(info === "second") {
        progressBar = $('#progressSecond');
    }


    // Reset if password length is zero
    if (pwd.length === 0) {
        progressBar.html("");
        progressBar.val("0");
        progressBar.removeClass();
        progressBar.css("width", 0);
        return;
    }

    // Check progress
    var prog = [/[$@$!%*#?&]/, /[A-Z]/, /[0-9]/, /[a-z]/]
        .reduce((memo, test) => memo + test.test(pwd), 0);

    // Length must be at least 8 chars
    if(prog > 2 && pwd.length > 7){
        prog++;
    }

    // Display it
    var progress = "";
    var strength = "";
    var color = "";
    switch (prog) {
        case 0:
        case 1:
        case 2:
            strength = "Debole";
            progress = "25";
            color = "bg-danger";
            break;
        case 3:
            strength = "Discreta";
            progress = "50";
            color = "bg-warning";
            break;
        case 4:
            strength = "Ottima";
            progress = "75";
            color = "bg-info";
            break;
        case 5:
            strength = "Eccellente";
            progress = "100";
            color = "bg-success";
            result = true;
            break;
    }
    progressBar.removeClass();
    progressBar.html(strength);
    progressBar.css("width", progress + "%");
    progressBar.addClass("progress-bar progress-bar-striped progress-bar-animated " + color);

    return result;

}