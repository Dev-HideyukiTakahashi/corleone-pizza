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
					<!-- Inicio do corpo formulário para atualizar cadastro de usuário -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title" >Configurações da conta</h4>
								<p class="card-description">Foto do perfil</p>

								<form class="forms-sample" id="form-newuser" method="post" enctype="multipart/form-data"
									action="<%=request.getContextPath()%>/login" >
									
									<input id="id" type="hidden" value="${userSettings.id}" name="newId"/>
									<input type="hidden" name="action" value="update"/>
									
									<!-- UPLOAD DE FOTO -->
									<div class="form-group form-default input-group mb-4">
										<div class="input-group-prepend">
											<c:if
												test="${userSettings.photo != '' && userSettings.photo != null}">
												<a> <img alt="Imagem user"
													src="${userSettings.photo}"  width="200px"
													id="fotoembase64"></a>
											</c:if>
											<!-- Se não tem foto, usar a foto padrão -->
											<c:if
												test="${userSettings.photo == '' || userSettings.photo == null}">
												<img alt="Imagem user"
													src="assets/images/faces/logo.png" width="200px"
													id="fotoembase64">
											</c:if>
										</div>
										<input type="file" id="filefoto" 
											onchange="visualizarImg('fotoembase64', 'filefoto')"
											accept="image/*" class="form-control-file"
											name="filePhoto"
											style="margin-top: 15px; margin-left: 5px">
									</div>
									<!-- UPLOAD DE FOTO -->
									
									
									<div class="form-group">
										<label>Nome*</label> 
										<input type="text" name="newName"
											autocomplete="off" class="form-control text-light" 
											placeholder="Nome Completo" id="name" value="${userSettings.adminName}">
									</div>
									
									<div class="form-group">
										<label>Telefone*</label> 
										<input
											type="text" class="form-control text-light" name="newPhone"
											autocomplete="off" id="phone" value="${userSettings.phone}" 
											placeholder="Telefone" >
									</div>
									
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> 
										<input
											type="email" class="form-control text-light" name="newEmail"
											autocomplete="off" id="email" value="${userSettings.email}" 
											placeholder="Email">
									</div>
									
									<div class="form-group">
										<label>Senha*</label> 
										<input type="password" name="oldPassword"
											autocomplete="off" class="form-control text-light" 
											placeholder="Digite a senha atual" id="password">
									</div>
									
									<div class="form-group">
										<label>Nova Senha*</label> 
										<input type="password" name="newPassword"
											autocomplete="off" class="form-control text-light" 
											placeholder="Digite a nova senha ou confirme a sua atual" id="new-password">
									</div>
									
									<div class="form-group">
										<label for="exampleInputName">Partner</label> 
										<input type="text" value="${userSettings.partner}" 
											autocomplete="off" class="form-control text-light" 
											placeholder="Filial do usuário" name="newPartner" id="partner">
									</div>
									
									<button type="submit" class="btn btn-primary mr-2" id="submit" >Atualizar</button>
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
	
	<jsp:include page="javascript.jsp" />
	
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

	<!-- Script para atualizar a foto dinamicamente quando selecionar uma nova -->
	<script type="text/javascript">
		function visualizarImg(fotoembase64, filefoto) {
			let preview = document.getElementById('fotoembase64') // campo img do html
			let fileUser = document.getElementById('filefoto').files[0] // pode retornar varios, pegar o primeiro
			let reader = new FileReader();
			reader.onloadend = function() {
				preview.src = reader.result; // carrega a foto na tela
			};

			if (fileUser) {
				reader.readAsDataURL(fileUser); // Preview da imagem
			} else {
				preview.src = '';
			}
		}
	</script>
	
</body>
</html>
