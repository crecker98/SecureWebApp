<%@ page import="java.util.ArrayList" %>
<%@ page import="com.soriani.securewebapp.business.PropostaProgettuale" %>
<%@ page import="com.soriani.securewebapp.web.home.GestoreSessioneHome" %>
<%@ include file="/condivisi/jsp/header.jsp" %>
<%
    ArrayList<PropostaProgettuale> proposte = GestoreSessioneHome.getProposte(request);
%>
<div class="container-fluid">
	<h3 class="text-dark mb-4">Proposte progettuali</h3>
                    <div class="card shadow">
                        <div class="card-body">
                            
                            <div class="table-responsive table mt-2"  role="grid" aria-describedby="dataTable_info">
                                <table class="table my-0" id="tableHome">
                                    <thead>
                                        <tr>
                                            <th>Nome</th>
                                            <th>Categoria</th>
                                            <th>Data Aggiunta</th>
                                            <th>Descrizione</th>
                                            <th>Utente</th>
                                            <th>File</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            for(PropostaProgettuale proposta : proposte) {

                                                %>
                                            <tr>
                                                <td><%= proposta.getNome() %></td>
                                                <td><%= proposta.getCategoria().getDescrizione() %></td>
                                                <td><%= proposta.getDataAggiungta() %></td>
                                                <td><%= proposta.getDescrizione() %></td>
                                                <td><%= proposta.getUtente().getUsername() %></td>
                                                <td><a download="<%= proposta.getNome() %>.txt" href="text/plain;base64,<%= Base64.getEncoder().encodeToString(proposta.getFile()) %>"></a><i class="fa fa-download"></i></td>
                                            </tr>
                                        <%
                                            }
                                        %>
                                        
                                        
                                    </tbody>
                                </table>
                            </div>
                           
                        </div>
                    </div>
</div>



<%@ include file="/condivisi/jsp/footer.jsp" %>
<script src="home/js/home.js"></script>