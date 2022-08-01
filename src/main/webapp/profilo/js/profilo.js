/**
 *
 */

$(document).ready(function(){
    $('.myPassword').keyup(function() {

        var progressBar = $('#progressFirst');
        if($(this).data("info") === "second") {
            progressBar = $('#progressSecond');
        }

        var pwd = $(this).val();


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
                break;
        }
        progressBar.removeClass();
        progressBar.html(strength);
        progressBar.css("width", progress + "%");
        progressBar.addClass("progress-bar progress-bar-striped progress-bar-animated " + color);

    });
});