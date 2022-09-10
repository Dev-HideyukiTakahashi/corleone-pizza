<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Contact Support</title>
<!-- plugins:css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/vendors/mdi/css/materialdesignicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/vendors/css/vendor.bundle.base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/style.css">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/assets/images/favicon.png" />
</head>
<body>
	<div class="container-scroller">
		<div class="container-fluid page-body-wrapper full-page-wrapper">
			<div
				class="content-wrapper d-flex align-items-center text-center error-page bg-info">
				<div class="row flex-grow">

					<div class="col-lg-12 mx-auto text-white">
						<div class="col-md-6 offset-md-3 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Mensagem para suporte</h4>
									<p class="card-description">Informe os dados para retorno da solicitação</p>
									<form class="forms-sample needs-validation" >
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<span
														class="input-group-text bg-primary text-white mdi mdi-account">
													</span>
												</div>
												<input type="text" class="form-control text-secondary"
													placeholder="Nome" aria-label="Name" name="name"
													aria-describedby="Name" autocomplete="off" required>
											</div>
										</div>
										
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<span
														class="input-group-text bg-primary text-white mdi mdi-email">
													</span>
												</div>
												<input type="email" class="form-control text-secondary"
													placeholder="Email" aria-label="Email" name="email"
													aria-describedby="Email" autocomplete="off" required="required">
											</div>
										</div>
										
										<div class="form-group"	>
											<div class="input-group">
												<div class="input-group-prepend">
													<span
														class="input-group-text bg-primary text-white mdi mdi-cellphone-android">
													</span>
												</div>
												<input type="number" class="form-control text-secondary" name="phone"
													aria-label="Phone" placeholder="Telefone" autocomplete="off" required="required">
											</div>
										</div>
	
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<button
														class="btn btn-sm btn-outline-primary dropdown-toggle"
														type="button" data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false">Assunto</button>
													<div class="dropdown-menu">
														<a class="dropdown-item" href="#" onclick="fillField('Esqueceu a senha')">Esqueceu
															a senha</a> <a class="dropdown-item" href="#" onclick="fillField('Cadastrar novo admin')">Cadastrar
															novo admin</a> <a class="dropdown-item" href="#" onclick="fillField('Bugs e correções')">Bugs e
															correções</a><a class="dropdown-item" href="#" onclick="fillField('Implementar nova função')">Implementar
															nova função</a>
														<div role="separator" class="dropdown-divider"></div>
														<a class="dropdown-item" href="#" onclick="fillField('Bater um papo')">Bater um papo</a>
													</div>
												</div>
												<input type="text" class="form-control text-secondary" id="subject"
													aria-label="Assunto" name="fill">
											</div>
										</div>
										
										<div class="form-group">
	                       			  		<label for="exampleTextarea1">Mensagem</label>
	                       					<textarea class="form-control text-secondary" id="exampleTextarea1" rows="5" name="msg" required="required"></textarea>
	                      				</div>
					                    <button type="submit" class="btn btn-primary mr-2">Enviar</button>
					                    <a href="index.jsp" class="btn btn-dark text-primary">Cancelar</a>



									</form>
								</div>
							</div>
							<!-- final card -->
						</div>
						<div class="row mt-5">
							<div class="col-12 text-center mt-xl-2">
								<a class="text-white font-weight-medium" href="index.jsp">Voltar
									ao início</a>
							</div>
						</div>
						<div class="row mt-5">
							<div class="col-12 mt-xl-2">
								<p class="text-white font-weight-medium text-center">
									<a
										href="https://github.com/Dev-HideyukiTakahashi/corleone-pizza"
										class="text-white" target="_blank"
										style="text-decoration: underline;"><br />GitHub</a> para
									repositório do projeto.
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- content-wrapper ends -->
		</div>
		<!-- page-body-wrapper ends -->
	</div>

	<script type="text/javascript">
		function fillField(msg) {
			$('#subject').val(msg);
		}
	</script>
	<script src="assets/vendors/js/vendor.bundle.base.js"></script>
	<script src="assets/js/off-canvas.js"></script>
	<script src="assets/js/hoverable-collapse.js"></script>
	<script src="assets/js/misc.js"></script>
	<script src="assets/js/settings.js"></script>
	<script src="assets/js/todolist.js"></script>


</body>
</html>