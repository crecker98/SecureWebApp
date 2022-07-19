<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.soriani.securewebapp.web.login.GestoreSessioneLogin" %>

<%@ include file="/condivisi/jsp/tagSup.jsp" %>
    
<%
String username = GestoreSessioneLogin.getUsernameLogin(request);
username = username != null ? username : "";
%>

<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xs-12 col-6">
                <div class="card shadow-lg o-hidden border-0 my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h4 class="text-dark mb-4">Benvenuto in SecureWebApp!</h4>
                                    </div>
                                    <%if(GestoreSessioneLogin.getMessaggioErrore(request) != null){ %>
                            		<div class="row">
                            			<div class="alert alert-danger col-xs-12">
                            				<%=GestoreSessioneLogin.getMessaggioErrore(request) %>
                            			</div>
                            		</div>
                            		<%} %>
                                    <form class="user" id="loginForm" method="post" action="Login">
                                    	<input type="hidden" name="operazione" value="login" />
                                        <div class="mb-3">
                                        	<input value="<%= username %>" class="form-control form-control-user" required type="text"  aria-describedby="usernameHelp" placeholder="Inserisci l'username..." name="username">
                                        </div>
                                        <div class="mb-3">
                                        	<input class="form-control form-control-user" required type="password" id="exampleInputPassword" placeholder="Password" name="password">
                                        </div>
                                        <div class="mb-3">
                                            <div class="custom-control custom-checkbox small">
                                                <div class="form-check">
                                                	<input class="form-check-input custom-control-input" type="checkbox" value="true" name="remember" id="formCheck-1">
                                                	<label class="form-check-label custom-control-label" for="formCheck-1">Ricordami</label>
                                                </div>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary d-block btn-user w-100" type="submit">Accedi</button>
                                        <hr>
                                        <a href="<%=pagina%>?operazione=registration" style="text-decoration:none" >
                                        	<button class="btn btn-primary btn-google d-block btn-user w-100" type="button" >Registrati subito !</button>
                                        </a>
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
   	
</body>

</html>