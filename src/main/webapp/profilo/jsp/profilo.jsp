<%@ page import="com.soriani.securewebapp.web.profilo.GestoreSessioneProfilo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/condivisi/jsp/header.jsp"%>

<div class="container-fluid">
	<h3 class="text-dark mb-4">Profilo</h3>
	<div class="row mb-3" >
		<div class="col-lg-4">
			<div class="card mb-3 ">
				<div class="card-body text-center shadow">
					<img class="rounded-circle mb-3 mt-4"
						src="data:image/jpg;base64,<%= Base64.getEncoder().encodeToString(utente.getImmagineProfilo()) %>" width="160" height="160">
					<form action="<%=GestoreSessioneProfilo.getCasoDUso(request)%>" class="mb-3">
						<input type="hidden" name="operazione" value="updatePhoto">
						<label for="foto" class="btn btn-primary btn-sm">Cambia foto</label>
						<input type="file" accept=".png,.jpeg,.jpg" hidden="" id="foto"  >
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
							<form>
								<div class="row">
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="username"><strong>Username</strong></label><input
												class="form-control" type="text" id="username"
												placeholder="user.name" value="<%=utente.getUsername()%>" name="username">
										</div>
									</div>
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="name"><strong>Nome
													</strong></label><input class="form-control" type="text" id="name"
												 name="name" value="<%=utente.getNome()%>">
										</div>
									</div>
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="surname"><strong>Cognome</strong></label><input class="form-control" type="text" id="surname" value="<%=utente.getCognome()%>" name="surname">
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
				<form class="card-body">
					<div class="row mb-3">
						<div class="col-sm-6 mb-3 mb-sm-0">
                            <label class="form-label" for="username"><strong>Password</strong></label>
							<input class="form-control form-control-user myPassword" data-info="first" autocomplete="off" required type="password"  placeholder="Password *" name="password">
							<div class="progress">
								<div class="" id="progressFirst" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div>
							</div>
						</div>
						<div class="col-sm-6 mb-3 mb-sm-0">
                            <label class="form-label" for="username"><strong>Ripeti password</strong></label>
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
					<button class="btn btn-primary d-block btn-user w-100" type="submit">Cambia Password</button>
				</form>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="text-primary fw-bold m-0">Caricamenti</h6>
				</div>
				<div class="card-body">
					<h4 class="small fw-bold">
						Server migration<span class="float-end">20%</span>
					</h4>
					<div class="progress progress-sm mb-3">
						<div class="progress-bar bg-danger" aria-valuenow="20"
							aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
							<span class="visually-hidden">20%</span>
						</div>
					</div>
					<h4 class="small fw-bold">
						Sales tracking<span class="float-end">40%</span>
					</h4>
					<div class="progress progress-sm mb-3">
						<div class="progress-bar bg-warning" aria-valuenow="40"
							aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
							<span class="visually-hidden">40%</span>
						</div>
					</div>
					<h4 class="small fw-bold">
						Customer Database<span class="float-end">60%</span>
					</h4>
					<div class="progress progress-sm mb-3">
						<div class="progress-bar bg-primary" aria-valuenow="60"
							aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
							<span class="visually-hidden">60%</span>
						</div>
					</div>
					<h4 class="small fw-bold">
						Payout Details<span class="float-end">80%</span>
					</h4>
					<div class="progress progress-sm mb-3">
						<div class="progress-bar bg-info" aria-valuenow="80"
							aria-valuemin="0" aria-valuemax="100" style="width: 80%;">
							<span class="visually-hidden">80%</span>
						</div>
					</div>
					<h4 class="small fw-bold">
						Account setup<span class="float-end">Complete!</span>
					</h4>
					<div class="progress progress-sm mb-3">
						<div class="progress-bar bg-success" aria-valuenow="100"
							aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
							<span class="visually-hidden">100%</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/condivisi/jsp/footer.jsp"%>
<script src="profilo/js/profilo.js"></script>