<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.soriani.securewebapp.web.sessione.GestoreSessione" %>

<%
String pagina = GestoreSessione.getCasoDUso(request);
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><%=pagina%> | SecureWebApp</title>
    <link rel="icon" href="condivisi/assets/img/icon.png">
    <link rel="stylesheet" href="condivisi/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="condivisi/assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="condivisi/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="condivisi/assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="condivisi/assets/bootstrap/css/datatables.min.css">
</head>