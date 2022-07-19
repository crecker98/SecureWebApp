<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.soriani.securewebapp.web.registration.GestoreSessioneRegistration" %>
<%@ page import="java.util.HashMap" %>

<%
	HashMap<String, String> form = GestoreSessioneRegistration.getRegistrationForm(request);
%>

<%@ include file="/condivisi/jsp/tagSup.jsp" %>

<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xs-12 col-6">
        <div class="card shadow-lg  o-hidden border-0 my-5">
            <div class="card-body p-0">
                <div class="row">
                	<div class="col-lg-12">
                		<div class="p-5">
                            <div class="text-center">
                                <h4 class="text-dark mb-4">Crea un account!</h4>
                            </div>
                            <%if(GestoreSessioneRegistration.getMessaggioErrore(request) != null){ %>
                            <div class="row">
                            	<div class="alert alert-danger col-xs-12">
                            		<%=GestoreSessioneRegistration.getMessaggioErrore(request) %>
                            	</div>
                            </div>
                            <%} %>
                            <form class="user" method="post" action="<%=pagina%>" enctype="multipart/form-data">
                            	<input type="hidden" name="operazione" value="registration" />
                                <div class="row mb-3">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                    	<input class="form-control form-control-user" required type="text" id="nome" value="<%=form.get("nome") %>" placeholder="Nome *" name="nome">
                                    	<small style="padding:1rem">Inserisci il tuo nome</small>
                                    </div>
                                    <div class="col-sm-6">
                                    	<input class="form-control form-control-user" type="text" required id="cognome" value="<%=form.get("cognome") %>" placeholder="Cognome *" name="cognome">
                                    	<small style="padding:1rem">Inserisci il tuo cognome</small>
                                    </div>
                                </div>
                                <div class="mb-3">
                                	<input class="form-control form-control-user" type="text" id="username" required value="<%=form.get("username") %>"  placeholder="Username *" name="username">
                                	<small style="padding:1rem">Inserisci il tuo username (non sono ammessi caratteri speciali)</small>
                                </div>
                                <div class="row mb-3">
                                	<div class="col-sm-12">
                                    	<input class="form-control form-control-user" accept=".png,.jpeg,.jpg" type="file" id="immagine_profilo" placeholder="immagine del profilo"  name="immagine_profilo">
                                    	<small style="padding:1rem">Carica la tua immagine del profilo(.png,.jpeg,.jpg)</small>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                    	<input class="form-control form-control-user myPassword" data-info="first" autocomplete="off" required type="password"  placeholder="Password *" name="password">
                                    	<div class="progress">
  											<div class="" id="progressFirst" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div>
										</div>	
                                    </div>
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                    	<input class="form-control form-control-user myPassword" data-info="second" autocomplete="off" type="password" required placeholder="Ripeti Password *" name="ripeti_password">
                                    	<div class="progress">
  											<div class="" id="progressSecond" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div>
										</div>
                                    </div>
                                </div>
								<div class="row">
									<div class="col-sm-12">
										<ul style="font-size: 0.875em;">
											<li>La password deve contenere almeno 8 caratteri</li>
									    	<li>La password deve contenere almeno una lettera maiuscola</li>
									    	<li>La password deve contenere almeno un numero</li>
									    	<li>La password deve contenere almeno un carattere speciale ($,@,!,%,*,#,?,&)</li>
  										</ul>
									</div>
								</div>
								<div class="row">
									<p>(*) campi obbligatori</p>
								</div>
                                <button class="btn btn-primary d-block btn-user w-100" type="submit">Registra Account</button>
                                <hr>
                                <a href="<%= pagina %>?operazione=login" style="text-decoration:none" ><button class="btn btn-primary btn-google d-block btn-user w-100" type="button" >Hai già un account? Accedi</button></a>
                                
                            </form>
                            
                        </div>
                	</div>
                    
                </div>
            </div>
        </div>
    </div>
    </div>
    </div>
    
    
    <%@ include file="/condivisi/jsp/tagInf.jsp" %>
    
    <script src="registration/js/registration.js"></script>
    
</body>

</html>    