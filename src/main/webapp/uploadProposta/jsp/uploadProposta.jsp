<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.soriani.securewebapp.business.GRPCategoria" %>
<%@ page import="com.soriani.securewebapp.business.Categoria" %>
<%@ page import="com.soriani.securewebapp.web.uploadProposta.GestoreSessioneUploadProposta" %>
<%@ page import="java.util.HashMap" %>

<%
GRPCategoria grpCategoria = GestoreSessioneUploadProposta.getGRPCategorie(request);
HashMap<String, String> form = GestoreSessioneUploadProposta.getFormUpload(request);

%>

<%@ include file="/condivisi/jsp/header.jsp"%>

<div class="container-fluid">
	<h3 class="text-dark mb-4">Carica proposta progettuale</h3>
	<div class="row mb-3">
		<div class="col-lg-12">
			<div class="row">
				<div class="col">
					<div class="card shadow mb-3">
						<div class="card-header py-3">
							<p class="text-primary m-0 fw-bold">Proposta</p>
						</div>
						<div class="card-body">
							<%if(GestoreSessioneUploadProposta.getMessaggioErrore(request) != null){ %>
							<div class="row">
                            	<div class="alert alert-danger col">
                            		<%= GestoreSessioneUploadProposta.getMessaggioErrore(request) %>
                            	</div>
                            </div>
                            <%} %>
							<form action="<%= GestoreSessioneUploadProposta.getCasoDUso(request)%>" method="post" enctype="multipart/form-data">
								<input type="hidden" name="operazione" value="insertProposta">
								<div class="row">
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="nome"><strong>Nome proposta</strong></label><input
												class="form-control" required type="text" id="nome" value="<%= form.get("NomeProposta") %>"
												placeholder="Nome proposta" name="nome">
												<small>Inserisci il nome della proposta progettuale</small>
										</div>
									</div>
									<div class="col">
										<div class="mb-3">
											<label class="form-label" for="email"><strong>Categoria</strong></label>
											<select class="form-control" id="email" required name="cod_categoria">
											<option value="" selected disabled>Seleziona una categoria</option>
												<%
												for(Categoria categoria: grpCategoria){
													%> <option <%= Integer.parseInt(form.get("Categoria")) == categoria.getCodice() ? "selected" : "" %> value="<%= categoria.getCodice() %>"><%= categoria.getDescrizione() %></option> 
												<% } %>
											</select>
												<small>Seleziona una categoria per la proposta progettuale</small>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-4">
										<div class="mb-3">
											<label class="form-label" for="descrizione"><strong>Descrizione</strong></label>
											<textarea class="form-control" name="descrizione" ><%= form.get("Descrizione") %></textarea>
										</div>
									</div>
									<div class="col-4">
										<div class="mb-3">
											<label class="form-label" for="first_name"><strong>File</strong></label><input required accept=".txt" class="form-control" type="file" name="file_proposta">
										</div>
									</div>
								</div>
								<div class="mb-3">
									<button class="btn btn-primary btn-md" style="width:100%" type="submit">Invia proposta</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>






	<%@ include file="/condivisi/jsp/footer.jsp"%>