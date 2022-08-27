<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Corleone Pizza</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/vendors/mdi/css/materialdesignicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/vendors/css/vendor.bundle.base.css">
	<!-- Layout styles -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
	<!-- End layout styles -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/images/favicon.png" />
<STYLE type="text/css">
.erro-login {
	font-size: 16px;
	font-family: "Rubik", sans-serif;
	color: #f44336;
	text-align: center;
	margin-top: 5px;
}
</STYLE>
</head>
<body>
	<div class="container-scroller">
		<div class="container-fluid page-body-wrapper full-page-wrapper">
			<div class="row w-100 m-0">
				<div
					class="content-wrapper full-page-wrapper d-flex align-items-center auth login-bg">
					<div class="card col-lg-4 mx-auto">
						<div class="card-body px-5 py-5">
							<h3 class="card-title text-left mb-3">Login</h3>
							<form action="<%=request.getContextPath()%>/login" method="post"
								class="needs-validation" novalidate>
							<input type="hidden" value="<%=request.getParameter("url")%>"
									name="url" /> 
								<div class="form-group">
									<label for="validationCustom01" class="form-label">Usuário
										*</label> <input type="text" class="form-control p_input" name="login"
										id="validationCustom01" required>
								</div>
								<div class="form-group">
									<label>Senha *</label> <input type="text"
										class="form-control p_input" name="password">
								</div>
								<div
									class="form-group d-flex align-items-center justify-content-between">
									<div class="form-check"></div>
									<a href="<%=request.getContextPath()%>/contact.jsp" class="forgot-pass">Esqueci minha senha</a>
								</div>
								<div class="text-center">
									<button type="submit"
										class="btn btn-primary btn-block enter-btn">Login</button>
								</div>
								<p class="sign-up">
									Ainda não tem uma conta?<a href="<%=request.getContextPath()%>/contact.jsp"> Cadastrar</a>
								</p>
								<H4 class="erro-login">${msg}</H4>
							</form>
						</div>
					</div>
				</div>
				<!-- content-wrapper ends -->
			</div>
			<!-- row ends -->
		</div>
		<!-- page-body-wrapper ends -->
	</div>
	<!-- container-scroller -->
	<!-- plugins:js -->
	<script src="assets/vendors/js/vendor.bundle.base.js"></script>
	<!-- endinject -->
	<!-- Plugin js for this page -->
	<!-- End plugin js for this page -->
	<!-- inject:js -->
	<script src="assets/js/off-canvas.js"></script>
	<script src="assets/js/hoverable-collapse.js"></script>
	<script src="assets/js/misc.js"></script>
	<script src="assets/js/settings.js"></script>
	<script src="assets/js/todolist.js"></script>
	<!-- endinject -->
</body>
</html>