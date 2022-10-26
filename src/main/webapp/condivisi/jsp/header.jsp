<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.soriani.securewebapp.web.condivisi.GestoreSessione" %>
<%@ page import="com.soriani.securewebapp.business.Utente" %>
<%@ page import="java.util.Base64" %>

<%@ include file="/condivisi/jsp/tagSup.jsp" %>

<%
Utente utente = GestoreSessione.getUtenteLoggato(request);
%>

<body id="page-top">
	
	<noscript>
		<style>
			.layout-container { display:none; }
			body { opacity:1; }
		</style>
		<div class="alert alert-danger m-5">
			Per utilizzare l'applicazione è necessario che sia abilitato javascript
		</div>
	</noscript>
	
    <div id="wrapper">
        <nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">
            <div class="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#">
                    <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-lock"></i></div>
                    <div class="sidebar-brand-text mx-3"><span>SecureWebApp</span></div>
                </a>
                <hr class="sidebar-divider my-0">
                <ul class="navbar-nav text-light" id="accordionSidebar">
                    <li class="nav-item"><a class="nav-link <%= request.getRequestURI().contains("home") ? "active" : "" %>" href="<%=request.getContextPath()%>/Home"><i class="fas fa-table"></i><span>Progetti</span></a></li>
                    <%
                    if(GestoreSessione.getUtenteLoggato(request) != null) {
                    %>
                    <li class="nav-item"><a class="nav-link <%= request.getRequestURI().contains("uploadProposta") ? "active" : "" %>" href="<%=request.getContextPath()%>/user/UploadProposta"><i class="fas fa-upload"></i><span>Carica Proposta</span></a></li>
                    <li class="nav-item"><a class="nav-link <%= request.getRequestURI().contains("profilo") ? "active" : "" %>" href="<%=request.getContextPath()%>/user/Profilo"><i class="fas fa-user"></i><span>Profilo</span></a></li>
                    <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/user/Logout?operazione=Logout"><i class="fas fa-sign-out-alt"></i><span>Logout</span></a></li>
                    <% }else { %>
                    <li class="nav-item"><a class="nav-link <%= request.getRequestURI().contains("login") ? "active" : "" %>" href="Login"><i class="fas fa-upload"></i><span>Login</span></a></li>
                    <% } %>
                </ul>
                <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
            </div>
        </nav>
        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <nav class="navbar navbar-light navbar-expand bg-white shadow mb-4 topbar static-top">
                    <div class="container-fluid"><button class="btn btn-link d-md-none rounded-circle me-3" id="sidebarToggleTop" type="button"><i class="fas fa-bars"></i></button>
                        <ul class="navbar-nav flex-nowrap ms-auto">
                            <div class="d-none d-sm-block topbar-divider"></div>
                            <li class="nav-item no-arrow">
                                <%
                                    if(GestoreSessione.getUtenteLoggato(request) != null) {
                                %>
                                <div class="nav-item dropdown no-arrow">
                                	<a class="dropdown-toggle nav-link" aria-expanded="false" data-bs-toggle="dropdown" href="#">
                                		<span class="d-none d-lg-inline me-2 text-gray-600 small"><%= utente.getUsername() %></span>
                                		<img class="border rounded-circle img-profile" src="data:image/jpg;base64,<%= Base64.getEncoder().encodeToString(utente.getImmagineProfilo()) %>">
                                	</a>
                                </div>
                                <% } %>
                            </li>
                        </ul>
                    </div>
                </nav>
