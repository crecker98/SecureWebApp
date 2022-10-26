<%@ page import="com.soriani.securewebapp.web.profilo.GestoreSessioneProfilo" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/condivisi/jsp/header.jsp"%>

<%

	HashMap<String, Double> categorie = GestoreSessioneProfilo.getCategories(request);

%>

<div class="container-fluid">
	<h3 class="text-dark mb-4">Profilo</h3>
	<div class="row mb-3" id="erroreMsg"></div>
	<div class="row mb-3">
		<% if(GestoreSessioneProfilo.getMessaggioErrore(request) != null){ %>
		<div class="row">
			<div class="alert alert-danger col-xs-12">
				<%=GestoreSessioneProfilo.getMessaggioErrore(request) %>
			</div>
		</div>
		<% } %>
		<% if(GestoreSessioneProfilo.getMessaggioRiuscita(request) != null){ %>
		<div class="row">
			<div class="alert alert-success col-xs-12">
				<%=GestoreSessioneProfilo.getMessaggioRiuscita(request) %>
			</div>
		</div>
		<% } %>
		<div class="col-lg-4">
			<div class="card mb-3 ">
				<div class="card-body text-center shadow">
					<img class="rounded-circle mb-3 mt-4"
						src="data:image/jpg;base64,<%= Base64.getEncoder().encodeToString(utente.getImmagineProfilo()) %>" width="160" height="160">
					<form id="formFoto" method="post" action="<%=pagina%>" class="mb-3" enctype="multipart/form-data">
						<input type="hidden" name="operazione" value="updatePhoto">
						<label for="foto" class="btn btn-primary btn-sm">Cambia foto</label>
						<input type="file" accept=".png,.jpeg,.jpg" hidden name="fotoProfilo" id="foto"  >
					</form>
				</div>
			</div>
		</div>
		<div class="col-lg-8" >
			<div class="row">
				<div class="col">
					<div class="card shadow mb-3">
						<div class="card-header py-3">
							<p class="text-primary m-0 fw-bold">Informazioni</p>
						</div>
						<div class="card-body">
							<form id="formInfo" action="<%=pagina%>" method="post">
								<input type="hidden" name="operazione" value="updateInfo">
								<div class="row">
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="username"><strong>Username</strong></label><input
												class="form-control" type="text" id="username"
												 value="<%=utente.getUsername()%>" name="username">
										</div>
									</div>
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="email"><strong>Email</strong></label><input
												class="form-control" type="email" id="email"
												 value="<%=utente.getEmail()%>" name="email">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="name"><strong>Nome
													</strong></label><input class="form-control" type="text" id="name"
												 name="nome" value="<%=utente.getNome()%>">
										</div>
									</div>
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="surname"><strong>Cognome</strong></label><input class="form-control" type="text" id="surname" value="<%=utente.getCognome()%>" name="cognome">
										</div>
									</div>
								</div>
								<div class="mb-3">
									<button class="btn btn-primary btn-sm" type="submit" style="width: 100%">Save
										Settings</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-lg-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="text-primary fw-bold m-0">Password</h6>
				</div>
				<form method="post" action="<%=pagina%>" class="card-body">
					<div class="row mb-3">
						<div class="col-sm-6 mb-3 mb-sm-0">
                            <label class="form-label" ><strong>Password</strong></label>
							<input class="form-control form-control-user myPassword" data-info="first" autocomplete="off" required type="password"  placeholder="Password *" name="password">
							<div class="progress">
								<div class="" id="progressFirst" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div>
							</div>
						</div>
						<div class="col-sm-6 mb-3 mb-sm-0">
                            <label class="form-label"><strong>Ripeti password</strong></label>
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
					<input type="hidden" name="operazione" value="updatePassword">
					<button class="btn btn-primary d-block btn-user w-100" id="submitPwd" disabled type="submit">Cambia Password</button>
				</form>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="text-primary fw-bold m-0">Proposte progettuali caricate</h6>
				</div>
				<div class="card-body">
					<%
						if(categorie != null){
							for(String key : categorie.keySet()) {

					%>
							<h4 class="small fw-bold">
								<%=key%><span class="float-end"><%= categorie.get(key)%>%</span>
							</h4>
							<div class="progress progress-sm mb-3">
								<div class="progress-bar bg-success" aria-valuenow="<%= categorie.get(key)%>"
									 aria-valuemin="0" aria-valuemax="100" style="width: <%= categorie.get(key)%>%;">
									<span class="visually-hidden"><%= categorie.get(key)%>%</span>
								</div>
							</div>
					<%
							}
						}else {
					%>
					<h4 class="small fw-bold">
						Nessuna proposta ancora caricata
					</h4>
					<% } %>

				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/condivisi/jsp/footer.jsp"%>
<script src="<%=request.getContextPath()%>/condivisi/js/checkField.js"></script>
<script src="<%=request.getContextPath()%>/profilo/js/profilo.js"></script>