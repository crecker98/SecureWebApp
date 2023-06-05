</div>
<footer class="bg-white sticky-footer">
	<div class="container my-auto">
		<div class="text-center my-auto copyright">
			<span>Copyright � SecureWebApp 2022</span>
		</div>
	</div>
</footer>
</div>

</div>

<%@ include file="/condivisi/jsp/tagInf.jsp" %>

</body>

</html>
<script>
	var secondsBeforeExpire = ${pageContext.session.maxInactiveInterval};
	var timeToDecide = 30; // Give client 15 seconds to choose.
	setTimeout(function() {
		alert('La tua sessione scadrà in ' + timeToDecide + ' secondi!')
	}, (secondsBeforeExpire - timeToDecide) * 1000);
</script>
<% if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) { %>
	<script>alert("Sessione scaduta necessario effettuare di nuovo il login!")</script>
<% } %>