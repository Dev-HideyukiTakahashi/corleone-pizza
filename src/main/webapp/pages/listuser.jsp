<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	
					<form action="<%=request.getContextPath()%>/login" id="form-search"></form>
						<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
																
								<div class="form-group">							
									<h4 class="card-title">Todos Clientes Cadastrados</h4>
									<span class="text-success h6" style="margin-left: 1%">${userDataSize} registros de usu�rios</span>	
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; margin-bottom: 10px " placeholder='Buscar por...' type='text'>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>Nome</th>
													<th>Telefone</th>
													<th>Email</th>
													<th>Login</th>
													<th>Senha</th>
													<th>Filial</th>
													<th>Atualizar</th>
													<th>Deletar</th>
												</tr>
											</thead>
											<!-- Tag que est� recebendo o HighLights -->
											<tbody id="client-view">
											<!-- Recebendo do /ServletSearch o m�todo 'usuarioDao.consultaUsuarioList()', enviando a resposta pelo request.setAttribute  -->
												<c:forEach items="${userData}" var="ud">
													<tr>
														<td class="text-secondary"><c:out value="${ud.adminName}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.phone}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.email}"></c:out> </td>
														<td class="text-warning"><c:out   value="${ud.login}"></c:out> </td>
														<td class="text-danger"><c:out    value="${ud.password}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.partner}"></c:out> </td>
														<td class="text-center"><a href='#form-newuser' onclick="updateData(${ud.id})" ><img src="<%=request.getContextPath()%>/assets/images/file-icons/update.png" alt='Atualizar' /></a></td>
														<td class="text-center"><a onclick="deleteUser(${ud.id})"><img src="<%=request.getContextPath()%>/assets/images/file-icons/delete.png" alt='Deletar' /></a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Inicio do corpo formul�rio para cadastrar usu�rio -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title">Cadastro de Novo Funcion�rio</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample" id="form-newuser" method="post"
									action="<%=request.getContextPath()%>/login" >
									
									<div class="form-group">
										<label for="exampleInputName">ID</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											readonly="readonly" id="id" style="background-color: #191c24"
											>
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Nome*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Nome Completo" id="name" 
											>
									</div>
									
									<div class="form-group">
										<label for="exampleInputPhone">Telefone*</label> 
										<input
											type="text" class="form-control text-light"
											autocomplete="off" id="phone" 
											placeholder="Telefone" name="phone" >
									</div>
									
									<div class="form-group">
										<label for="exampleInputEmail">Email*</label> 
										<input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" 
											placeholder="Email" name="email">
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Login*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Login de usu�rio" name="login" id="login">
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Senha*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Senha de usu�rio" name="password" id="password"
>
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Partner</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Filial do usu�rio" name="partner" id="partner">
									</div>
									
									<button type="button" class="btn btn-primary mr-2" id="submit" onclick="updateUser()">Atualizar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								
							</div>
						</div>
					</div>
					<!-- Final do corpo formul�rio para cadastrar novo usu�rio -->
					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
			<jsp:include page="javascript.jsp" />
		</div>
	</div>
	
	
	<!-- Modal Delete -->
	<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title text-success" id="exampleModalLabel">Deletar Usu�rio</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body" >
	        <h4 id="modal-msg" class="text-secondary"></h4>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success"   data-bs-dismiss="modal" id="btn-delete">Sim</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Fechar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal Update -->
	<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Atualiza��o de Funcion�rio</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body" >
	        <h4 class="text-success">Funcion�rio atualizado com sucesso!</h4>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" ><a href="<%=request.getContextPath()%>/login?action=searchList">Close</a></button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Script para deletar os dados do usu�rio selecionado -->
	<script type="text/javascript">

	function deleteUser(id) {
		if(id != 1)
		{
			$("#deleteModal").modal('show');
			$("#btn-delete").show();
			document.getElementById('modal-msg').classList.remove('text-danger');
			$("#modal-msg").text("Deseja apagar o usu�rio " + id + " ?");
			let urlAction = document.getElementById('form-search').action;
			$(document).on("click", "#btn-delete", function()
					{	
						$.ajax({
		
							method : "get",
							url : urlAction,
							data : 
							{
								idRequest    : id,
								action		 : "delete",
							},	
							success: function name() {
								$("#deleteModal").modal('hide');
								location.reload();
							},
						}).fail(function(xhr, status, errorThrown) {
							alert('Erro inesperado ao cadastrar cliente.');
						});
					});
		}
		else{
			$("#deleteModal").modal('show');
			$("#btn-delete").hide();
			document.getElementById('modal-msg').classList.add('text-danger');
			$("#modal-msg").text("N�o � possivel apagar o admin do sistema");
		}
	}
	</script>
	
	
	<!-- Script para popular o formul�rio com os dados do usu�rio selecionado -->
	<script type="text/javascript">
		function updateData(id) {
			
			let urlAction = document.getElementById('form-search').action;
			
			$.ajax({
				
				method : "get",
				url : urlAction,
				data : 
				{
					idRequest    : id,
					action		 : "search",
				},	
				success: function (response) {
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json = JSON.parse(response);
					
					$('#id').val(json.id);
					$('#name').val(json.adminName);
					$('#phone').val(json.phone);
					$('#email').val(json.email);
					$('#login').val(json.login);
					$('#password').val(json.password);
					$('#partner').val(json.partner);
				},
			})
		}
		
		
	</script>
	
	
	<script type="text/javascript">
		function updateUser() {
			
			let userId        = $("#id").val();
			let userName      = $("#name").val();
			let userPhone     = $("#phone").val();
			let userEmail     = $("#email").val();
			let userLogin     = $("#login").val();
			let userPassword  = $("#password").val();
			let userPartner   = $("#partner").val();
			
			let urlAction = document.getElementById('form-search').action;
			
			$.ajax({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					newId        : userId,
					newName      : userName,
					newPhone     : userPhone,
					newEmail     : userEmail,
					newLogin     : userLogin,
					newPassword  : userPassword,
					newPartner   : userPartner,
					action		 : "update",
				},
			    success : function(response) 
			    {
		    		$('#registerModal').modal('show');
		    		
				},
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao cadastrar cliente.');
			});
			
		}
	</script>
	
	<!-- Script para busca com highlights -->
	<script src="https://cdn.jsdelivr.net/mark.js/8.6.0/mark.min.js"></script>
	<script type="text/javascript">
		// cria uma inst�ncia definindo o elemento onde ser� "marcada" as palavras.
		var instance = new Mark(document.getElementById('client-view'))
	
		function highlight(word){
		  instance.unmark({
		    done: function(){
		      instance.mark(word)
		    }
		  })
		}
	
		document
		  .getElementById('query')
		  .addEventListener('input', function(){
		    highlight(this.value)
		})
	
		</script>	
		
	
	</body>
</html>