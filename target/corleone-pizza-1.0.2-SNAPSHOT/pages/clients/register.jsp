<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<!-- Cabe�alho da p�gina -->
<jsp:include page="../components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="../components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
			<jsp:include page="../components/navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">
					<!-- Inicio do corpo formul�rio para cadastrar cliente -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title">Cadastro de cliente</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample" id="form-register"
									action="<%=request.getContextPath()%>/register" >
									
									<div class="form-group">
										<label for="exampleInputName">Nome*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Nome Completo" name="name" id="name">
									</div>
									
									<div class="form-group">
										<label for="exampleInputPhone">Telefone*</label> 
										<input
											type="number" class="form-control text-light"
											autocomplete="off" id="phone" 
											placeholder="Telefone - Apenas n�meros" name="phone" >
									</div>
									
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> 
										<input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" 
											placeholder="Email" name="email">
									</div>
									
									<div class="form-group">
										<label for="exampleInputAdress">Endere�o*</label> 
										<input
											type="text" class="form-control text-light" 
											autocomplete="off"
											placeholder="Endere�o" name="adress" id="adress">
									</div>
									
									<div class="form-group">
										<label for="exampleReference">Ponto de refer�ncia</label>
										<textarea class="form-control text-light" autocomplete="off"
											rows="4" name="reference" id="reference"></textarea>
									</div>
									
									<button type="button" class="btn btn-primary mr-2" id="submit" onclick="submitClient()">Cadastrar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								<!-- Final do corpo formul�rio para cadastrar cliente -->
							</div>
						</div>
					</div>
					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="../components/javascript.jsp" />
	
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
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Fechar</button>
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
		function submitClient() {

			let nameUser      = $("#name").val();
			let phoneUser     = $("#phone").val();
			let emailUser     = $("#email").val();
			let adressUser    = $("#adress").val();
			let referenceUser = $("#reference").val();
			let urlAction 	  = document.getElementById('form-register').action;
			if(nameUser.trim() === '' || phoneUser.trim() === '' || adressUser.trim() === '')
			{
	    		let msg = "Preencha todos campos obrigat�rios*, telefone apenas n�meros";
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
						name 	  : nameUser,
						phone     : phoneUser,
						email 	  : emailUser,
						adress 	  : adressUser,
						reference : referenceUser,
					},
				    success : function(response) 
				    {
				    	if(response === "success")
				    	{
				    		let msg = "Cliente registrado com sucesso!";
				    		$('#registerModal').modal('show');
				    		$("#modal-msg").text(msg);
				    		document.getElementById('modal-msg').classList.remove('text-danger');
							cleanForm();
				    	}
				    	else
				    	{
				    		let msg = "J� existe um cliente com esse telefone.";
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

	<!-- Script para limpar o formul�rio -->
	<script type="text/javascript">
	
		function cleanForm() 
		{
			var elements = document.getElementById('form-register').elements;
	
			for (p = 0; p < elements.length; p++) 
			{
				elements[p].value = '';
			}
		}
	</script>

</body>
</html>
