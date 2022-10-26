$(document).ready(function(){

    $('#loginForm').submit(function() {

        if(!checkUsername($('#username').val())) {
            $('#erroreMsg').html("");
            $('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato username non valido, inserire solo numeri e lettere</div></div>');
            return false;
        }

    });

});