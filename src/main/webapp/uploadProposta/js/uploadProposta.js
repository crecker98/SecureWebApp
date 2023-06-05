$(document).ready(function(){

    $('#formProposta').submit(function() {

        if(!checkName($('#nomeProposta').val())) {
            $('#erroreMsg').html("");
            $('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato nome proposta non valido, inserire solo lettere</div></div>');
            return false;
        }

        if(!checkDescription($('#descrizione').val())) {
            $('#erroreMsg').html("");
            $('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato descrizione non valido, inserire solo lettere e numeri</div></div>');
            return false;
        }

    });

});