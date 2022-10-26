$(document).ready(function(){

	$('.myPassword').keyup(function() {
		if(checkPassword($(this).val(), $(this).data("info"))){
			$('#registraButton').attr("disabled", false);
		}
	});

	$('#formRegistration').submit(function() {

		if(!checkUsername($('#username').val())) {
			$('#erroreMsg').html("");
			$('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato username non valido, inserire solo numeri e lettere</div></div>');
			return false;
		}

		if(!checkName($('#name').val())) {
			$('#erroreMsg').html("");
			$('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato nome non valido, inserire solo lettere</div></div>');
			return false;
		}

		if(!checkName($('#surname').val())) {
			$('#erroreMsg').html("");
			$('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Formato cognome non valido, inserire solo lettere</div></div>');
			return false;
		}

		if(!checkEmail($('#email').val())) {
			$('#erroreMsg').html("");
			$('#erroreMsg').append('<div class="row"><div class="alert alert-danger col-xs-12">Inserire una mail valida, rispettando il formato mail@dominio.it</div></div>');
			return false;
		}

	});

});