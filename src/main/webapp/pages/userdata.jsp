<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<!-- Cabeçalho da página -->
<jsp:include page="head.jsp"></jsp:include>


<body>


	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">
					<!-- Inicio do corpo formulário para atualizar cadastro de usuário -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title">Configurações da conta</h4>
								<p class="card-description">Atualize os dados de cadastro</p>

								<form class="forms-sample" id="form-newuser" method="post"
									action="<%=request.getContextPath()%>/login" >
									
									<input id="id" type="hidden" value="${userSettings.id}" />
									
									<div class="form-group">
										<label>Nome*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Nome Completo" id="name" value="${userSettings.adminName}">
									</div>
									
									<div class="form-group">
										<label>Telefone*</label> 
										<input
											type="text" class="form-control text-light"
											autocomplete="off" id="phone" value="${userSettings.phone}" 
											placeholder="Telefone" name="phone" >
									</div>
									
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> 
										<input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" value="${userSettings.email}" 
											placeholder="Email" name="email">
									</div>
									
									<div class="form-group">
										<label>Senha*</label> 
										<input type="password"
											autocomplete="off" class="form-control text-light" 
											placeholder="Digite a senha atual" name="password" id="password">
									</div>
									
									<div class="form-group">
										<label>Nova Senha*</label> 
										<input type="password"
											autocomplete="off" class="form-control text-light" 
											placeholder="Digite a nova senha" name="password" id="new-password">
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Partner</label> 
										<input type="text" value="${userSettings.partner}" 
											autocomplete="off" class="form-control text-light" 
											placeholder="Filial do usuário" name="partner" id="partner">
									</div>
									
									<button type="button" class="btn btn-primary mr-2" id="submit" onclick="updateUser()">Atualizar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								<!-- Final do corpo formulário para atualizar cadastro de usuário -->
							</div>
						</div>
					</div>
					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</div>
	
	
	<!-- Modal Update -->
	<div class="modal fade" id="update-modal" tabindex="-1" aria-labelledby="update-modal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Atualização de Funcionário</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body" >
	        <h4 class="text-success"  id="register-msg"></h4>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal()">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<jsp:include page="javascript.jsp" />
	
	
	<!-- Script de update -->
	<script type="text/javascript">
		function updateUser() {
			
			let userId       	 = $("#id").val();
			let userName     	 = $("#name").val();
			let userPhone    	 = $("#phone").val();
			let userEmail    	 = $("#email").val();
			let userPassword 	 = $("#password").val();
			let userNewPassword  = $("#new-password").val();
			let userPartner 	 = $("#partner").val();
			
			let urlAction = document.getElementById('form-newuser').action;
			
			if(userName.trim() === '' || userPhone.trim() === '' || userPassword.trim() === '' || userNewPassword.trim() === '')
			{
	    		let msg = "Os campos não podem estar em branco ***";
	    		$('#update-modal').modal('show');
	    		$("#register-msg").text(msg);
	    		document.getElementById('register-msg').classList.add('text-danger');
			}
			else{
				$.ajax({
					method : "POST",
					url    : urlAction,
					data   : 
					{
						newId        : userId,
						newName      : userName,
						newPhone     : userPhone,
						newEmail     : userEmail,
						oldPassword  : userPassword,
						newPassword  : userNewPassword,
						newPartner   : userPartner,
						action		 : "update",
					},
				    success : function(response) 
				    {
				    	if(response === "atualizado"){
				    		$('#update-modal').modal('show');
				    		document.getElementById('register-msg').classList.remove('text-danger');
				       		$('#register-msg').text('Funcionário atualizado com sucesso!');
				    	}
				    	else if (response === "passNot"){
				    		$('#update-modal').modal('show');
				    		$('#register-msg').text('Senha atual incorreta');
				    		document.getElementById('register-msg').classList.add('text-danger');
				    	}
				    	else{
				    		$('#update-modal').modal('show');
				    		$('#register-msg').text('Erro ao atualizar dados de funcionário.');
				    		document.getElementById('register-msg').classList.add('text-danger');
				    	}

			    		
					},
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado ao cadastrar cliente.');
				});
				
			}
		}
	</script>
	
	<!-- Script para acionar o click submit quando apertar ENTER -->
	<script type="text/javascript">
	
	$(document).keyup(function(event){
		if (event.keyCode === 13) {
	  	$('#submit').click();
	  }
	});
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

	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#update-modal').modal('hide');
	}
	</script>
</body>
</html>
