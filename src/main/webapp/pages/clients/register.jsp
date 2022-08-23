<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<!-- Cabeçalho da página -->
<jsp:include page="../head.jsp"></jsp:include>


<body>


	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="../sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="../navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">
					<!-- Inicio formulário para cadastrar cliente -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">

							<div class="card-body">
								<h4 class="card-title">Cadastro de cliente</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample needs-validation" id="form-register"
									action="<%=request.getContextPath()%>/register" >
									<div class="form-group">
										<label for="exampleInputName">Nome*</label> <input type="text"
											autocomplete="off" class="form-control text-light" required="required"
											placeholder="Nome" name="name" id="name"
											value="${clientData.name}">
									</div>
									<div class="form-group">
										<label for="exampleInputPhone">Telefone*</label> <input
											type="number" class="form-control text-light"
											autocomplete="off" id="phone" value="${clientData.phone}"
											placeholder="Telefone" name="phone" >
									</div>
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> <input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" value="${clientData.email}"
											placeholder="Email" name="email">
									</div>
									<div class="form-group">
										<label for="exampleInputAdress">Endereço*</label> <input
											type="text" class="form-control text-light" 
											value="${clientData.adress}" autocomplete="off"
											placeholder="Endereço" name="adress" id="adress">
									</div>
									<div class="form-group">
										<label for="exampleReference">Ponto de referência</label>
										<textarea class="form-control text-light" autocomplete="off"
											rows="4" name="reference" id="reference"></textarea>
									</div>
									<button type="button" class="btn btn-primary mr-2" id="submit" onclick="submitClient()">Cadastrar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								<!-- Final formulário para cadastrar cliente -->
							</div>
						</div>
					</div>
					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="../javascript.jsp" />
	
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

	<!-- Script para comunicar dados ao backend, encaminha os dados para o ServletRegister -->
	<script type="text/javascript">	
		function submitClient() {

			let nameUser      = $("#name").val();
			let phoneUser     = $("#phone").val();
			let emailUser     = $("#email").val();
			let adressUser    = $("#adress").val();
			let referenceUser = $("#reference").val();
			let urlAction 	  = document.getElementById('form-register').action;
			
			if(nameUser.trim() === '' || phoneUser.trim() === '' || adressUser.trim() === ''){
	    		let msg = "Preencha todos campos obrigatórios ***";
	    		$('#registerModal').modal('show');
	    		$("#modal-msg").text(msg);
	    		document.getElementById('modal-msg').classList.add('text-danger');
			}else
			{
				$.ajax({
					method : "POST",
					url    : urlAction,
					data   : 
					{
						name 	  : nameUser,
						phone     : phoneUser,
						email 	  : emailUser,
						adress 	  : adressUser,
						reference : referenceUser,
					},
				    success : function(response) {
				    	if(response === "registrado"){
				    		let msg = "Cliente registrado com sucesso!";
				    		$('#registerModal').modal('show');
				    		$("#modal-msg").text(msg);
				    		document.getElementById('modal-msg').classList.remove('text-danger');
							cleanForm();
				    	}
				    	else{
				    		let msg = "Já existe um cliente com esse telefone.";
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
	
		function cleanForm() {
			var elements = document.getElementById('form-register').elements;
	
			for (p = 0; p < elements.length; p++) {
				elements[p].value = '';
			}
		}
	</script>


</body>
</html>



















