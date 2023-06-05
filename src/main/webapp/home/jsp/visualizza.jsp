<%@ page import="com.soriani.securewebapp.web.home.GestoreSessioneHome" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ include file="/condivisi/jsp/tagSup.jsp" %>
<%
    byte[] proposta = GestoreSessioneHome.getVisualizza(request);
    String p = new String(proposta, StandardCharsets.UTF_8);
%>
<%= !p.equals("") ? p : "<h1>Errore nell'apertura del file</h1>"%>

<%@ include file="/condivisi/jsp/tagInf.jsp" %>
