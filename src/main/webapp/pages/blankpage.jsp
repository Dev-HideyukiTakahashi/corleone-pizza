<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<!-- Cabe�alho da p�gina -->
<jsp:include page="head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
			<jsp:include page="navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">

					AQUI COLOCAR O CORPO DA P�GINA

					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="javascript.jsp" />
	</div>
</body>
</html>