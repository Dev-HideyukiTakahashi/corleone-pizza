<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Sessão expirou</title>
<!-- plugins:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/vendors/mdi/css/materialdesignicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/vendors/css/vendor.bundle.base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
	<!-- End layout styles -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/images/favicon.png" />
</head>
<body>
	<div class="container-scroller">
		<div class="container-fluid page-body-wrapper full-page-wrapper">
			<div
				class="content-wrapper d-flex align-items-center text-center bg-primary">
				<div class="row flex-grow">
					<div class="col-lg-6 mx-auto text-white">
					
						<div class="row row mt-5">
							<div class="col-12 text-center mt-xl-2">
								<h2>Sua sessão expirou!</h2>
								<h3 class="font-weight-light">Faça o login novamente.</h3>
							</div>
						</div>
						
						<div class="row mt-5">
							<div class="col-12 text-center mt-xl-2">
								<a class="text-white font-weight-medium" href="pages/main.jsp">Página de login</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- content-wrapper ends -->
		</div>
		<!-- page-body-wrapper ends -->
	</div>
	<!-- container-scroller -->
	<!-- plugins:js -->
	<script src="<%=request.getContextPath()%>/assets/vendors/js/vendor.bundle.base.js"></script>
	<!-- endinject -->
	<!-- Plugin js for this page -->
	<!-- End plugin js for this page -->
	<!-- inject:js -->
	<script src="<%=request.getContextPath()%>/assets/js/off-canvas.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/hoverable-collapse.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/misc.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/settings.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/todolist.js"></script>
	<!-- endinject -->
</body>
</html>