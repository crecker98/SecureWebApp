<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

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
                                    <h3 class="text-dark mb-4">Proposta caricata con successo!</h3>
                                </div>
                                <form class="user">
                                    <hr>
                                    <a href="<%= request.getContextPath() %>/user/UploadProposta" style="text-decoration:none" ><button class="btn btn-primary btn-google d-block btn-user w-100" type="button" >Torna indietro</button></a>

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