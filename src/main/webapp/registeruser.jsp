<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
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
				class="content-wrapper d-flex align-items-center error-page bg-info auth login-bg">
				<div class="row flex-grow">

					<div class="col-lg-12 mx-auto text-white">
						<!-- Inicio do corpo formulário para cadastrar usuário -->
						<div class="col-md-6 offset-md-3 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">

									<h4 class="card-title">Cadastro de Novo Funcionário</h4>
									<p class="card-description">Preencha todos os campos</p>

									<form class="forms-sample" id="form-newuser" method="post"
										action="<%=request.getContextPath()%>/login">

										<div class="form-group">
											<label for="exampleInputName">Nome*</label> <input
												type="text" autocomplete="off"
												class="form-control text-light" placeholder="Nome Completo"
												id="name">
										</div>

										<div class="form-group">
											<label for="exampleInputPhone">Telefone*</label> <input
												type="text" class="form-control text-light"
												autocomplete="off" id="phone" placeholder="Telefone"
												name="phone">
										</div>

										<div class="form-group">
											<label for="exampleInputEmail">Email*</label> <input
												type="email" class="form-control text-light"
												autocomplete="off" id="email" placeholder="Email"
												name="email">
										</div>

										<div class="form-group">
											<label for="exampleInputName">Login*</label> <input
												type="text" autocomplete="off"
												class="form-control text-light"
												placeholder="Login de usuário" name="login" id="login">
										</div>

										<div class="form-group">
											<label for="exampleInputName">Senha*</label> <input
												type="password" autocomplete="off"
												class="form-control text-light"
												placeholder="Senha de usuário" name="password" id="password">
										</div>

										<div class="form-group">
											<label for="exampleInputName">Partner</label> <input
												type="text" autocomplete="off"
												class="form-control text-light"
												placeholder="Filial do usuário" name="partner" id="partner">
										</div>

										<button type="button" class="btn btn-primary mr-2" id="submit"
											onclick="submitUser()">Cadastrar</button>
										<button type="button" class="btn btn-dark"
											onclick="cleanForm();">Limpar</button>
									</form>
								</div>
							</div>
						</div>
						<div class="row mt-5">
							<div class="col-12 text-center mt-xl-2")>
								<button class="btn-primary"  style="border-radius: 0.1875rem; padding: 0.375rem 0.75rem"><a class="text-secondary"  href="index.jsp" style="text-decoration: none">Voltar
									ao início</a></button>
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
			<!-- Final do corpo formulário para cadastrar novo usuário -->


		</div>
		<!-- page-body-wrapper ends -->
	</div>
	
		<!-- Modal -->
	<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Cadastro de cliente</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body" >
	        <h4 id="modal-msg" class="text-success"></h4>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- Script para acionar o click submit quando apertar ENTER -->
	<script type="text/javascript">
	
	$(document).keyup(function(event){
		if (event.keyCode === 13) {
	  	$('#submit').click();
	  }
	});
	</script>

	<!-- Script para comunicar dados ao backend, encaminha os dados para o ServletRegister -->
	<script type="text/javascript">	
		function submitUser() {

			let userName      = $("#name").val();
			let userPhone     = $("#phone").val();
			let userEmail     = $("#email").val();
			let userLogin     = $("#login").val();
			let userPassword  = $("#password").val();
			let userPartner   = $("#partner").val();
			
			let urlAction 	  = document.getElementById('form-newuser').action;

			if(userName.trim() === '' || userPhone.trim() === '' || userEmail.trim() === '' || userLogin.trim() === '' || userPassword.trim() === '')
			{
	    		let msg = "Preencha todos campos obrigatórios ***";
	    		$('#registerModal').modal('show');
	    		$("#modal-msg").text(msg);
	    		document.getElementById('modal-msg').classList.add('text-danger');
			}
			else
			{
				$.ajax({
					method : "POST",
					url    : urlAction,
					data   : 
					{
						newName      : userName,
						newPhone     : userPhone,
						newEmail     : userEmail,
						newLogin     : userLogin,
						oldPassword  : userPassword,
						newPartner   : userPartner,
						action		 : "insert",
					},
				    success : function(response) 
				    {
				    	if(response === "registrado")
			    		{
				    		let msg = "Funcionário registrado com sucesso!";
				    		$('#registerModal').modal('show');
				    		$("#modal-msg").text(msg);
				    		document.getElementById('modal-msg').classList.remove('text-danger');
							cleanForm();
			    		}
				    	else{
				    		let msg = "Já existe funcionário com esse login";
				    		$('#registerModal').modal('show');
				    		$("#modal-msg").text(msg);
				    		document.getElementById('modal-msg').classList.add('text-danger');
				    	}

					},
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado ao cadastrar cliente.');
				});
			}
	}
	</script>

	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#registerModal').modal('hide');
	}
	</script>

	<!-- Script para limpar o formulário -->
	<script type="text/javascript">
	
		function cleanForm() 
		{
			var elements = document.getElementById('form-newuser').elements;
	
			for (p = 0; p < elements.length; p++) 
			{
				elements[p].value = '';
			}
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