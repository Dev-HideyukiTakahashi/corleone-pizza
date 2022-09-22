<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<!-- Cabeçalho da página -->
<jsp:include page="../components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="../components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="../components/navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">

					AQUI COLOCAR O CORPO DA PÁGINA

					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../components/javascript.jsp" />
	</div>
</body>
</html>