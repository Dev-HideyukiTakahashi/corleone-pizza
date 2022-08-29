<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	
					<form action="<%=request.getContextPath()%>/login" id="form-search"></form>
						<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
																
								<div class="form-group">							
									<h4 class="card-title">Todos Clientes Cadastrados</h4>
									<span class="text-success h6" style="margin-left: 1%">${userDataSize} registros de usuários</span>	
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; margin-bottom: 10px " placeholder='Buscar por...' type='text'>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>ID</th>
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
											<!-- Tag que está recebendo o HighLights -->
											<tbody id="client-view">
											<!-- Recebendo do /ServletSearch o método 'usuarioDao.consultaUsuarioList()', enviando a resposta pelo request.setAttribute  -->
												<c:forEach items="${userData}" var="ud">
													<tr>
														<td class="text-secondary" id="id-select"><c:out value="${ud.id}"></c:out> </td>
														<td class="text-secondary" id="id-name"><c:out value="${ud.adminName}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.phone}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.email}"></c:out> </td>
														<td class="text-warning"><c:out   value="${ud.login}"></c:out> </td>
														<td class="text-danger"><c:out    value="${ud.password}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.partner}"></c:out> </td>
														<td class="text-center"><a href='#form-newuser' id='btn-update'><img src="<%=request.getContextPath()%>/assets/images/file-icons/update.png" alt='Atualizar' /></a></td>
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
					<!-- Inicio do corpo formulário para cadastrar usuário -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title">Cadastro de Novo Funcionário</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample" id="form-newuser" method="post"
									action="<%=request.getContextPath()%>/login" >
									
									<div class="form-group">
										<label for="exampleInputName">ID</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											readonly="readonly" id="id"
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
											placeholder="Login de usuário" name="login" id="login">
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Senha*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Senha de usuário" name="password" id="password"
>
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Partner</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light" 
											placeholder="Filial do usuário" name="partner" id="partner">
									</div>
									
									<button type="button" class="btn btn-primary mr-2" id="submit" onclick="submitUser()">Cadastrar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								
							</div>
						</div>
					</div>
					<!-- Final do corpo formulário para cadastrar novo usuário -->
					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
			<jsp:include page="javascript.jsp" />
		</div>
	</div>
	
	
	<!-- Modal -->
	<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title text-success" id="exampleModalLabel">Deletar Usuário</h5>
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
	
	
	<script type="text/javascript">

	function deleteUser(id) {
		if(id != 1)
		{
			$("#deleteModal").modal('show');
			$("#btn-delete").show();
			document.getElementById('modal-msg').classList.remove('text-danger');
			$("#modal-msg").text("Deseja apagar o usuário " + id + " ?");
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
			$("#modal-msg").text("Não é possivel apagar o admin do sistema");
		}

		
	}

	</script>
	<!-- Script para busca com highlights -->
	<script src="https://cdn.jsdelivr.net/mark.js/8.6.0/mark.min.js"></script>
	<script type="text/javascript">
		// cria uma instância definindo o elemento onde será "marcada" as palavras.
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
		
		
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#deleteModal').modal('hide');
	}
	</script>
	
	</body>
</html>