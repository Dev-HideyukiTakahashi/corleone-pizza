<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<!-- Cabeçalho da página -->
<jsp:include page="../components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="../components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="../components/navbar.jsp"></jsp:include>

			<div class="main-panel"  id="top">
				<div class="content-wrapper">

					<!-- Inicio do corpo da página de exclusão -->
					<div class="col-md-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Buscar cliente por nº de telefone</h4>
								
								<div class="form-group">
									<button class='btn-inverse-info' disabled="disabled" style= "padding: 4.5px;">Telefone</button>
								</div>
								
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control text-light" id="field"
											placeholder="Campo para busca" aria-label="field" autocomplete="off"
											required="required" aria-describedby="basic-addon2">
										<div class="input-group-append" >
											<button class="btn btn-sm btn-primary" type="button"  id="btn-search"
												onclick="searchAjax();">Buscar</button>
										</div>
									</div>
								</div>
									<span id="searchResult" class="text-success h6"></span>
							</div>
						</div>
					</div>
					<!-- Final do corpo da página de exclusão -->
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Confirmar dados do cliente</h4>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Nome</th>
												<th>Telefone</th>
												<th>Endereço</th>
												<th>Referência</th>
												<th class='text-center'>Atualizar</th>
											</tr>
										</thead>
										<tbody id="clientFound">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- Final da tabela de busca -->
					<!-- Inicio formulário para cadastrar cliente -->
					<div class="col-12 grid-margin stretch-card" id="register-form">
						<div class="card">

							<div class="card-body">
								<span class="text-success" id="registerSucess">${registerSucess}</span>
								<span class="text-danger">${registerFail}</span>
								<h4 class="card-title">Atualizar cadastro de cliente</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample" method="post" id="form-register"
									action="<%=request.getContextPath()%>/update">
									
									<input type="hidden" id="current-phone">
									<input type="hidden" id="id">
									
									<div class="form-group">
										<label for="exampleInputName">Nome*</label> 
										<input type="text"
											autocomplete="off" class="form-control text-light"
											placeholder="Nome" name="name" id="name" 
											>
									</div>
									
									<div class="form-group">
										<label for="exampleInputPhone">Telefone*</label> 
										<input
											type="number" class="form-control text-light" 
											autocomplete="off" id="phone" 
											placeholder="Telefone - Apenas números" name="phone" required="required">
									</div>
									
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> 
										<input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" 
											placeholder="Email" name="email">
									</div>
									
									<div class="form-group">
										<label for="exampleInputAdress">Endereço*</label> 
										<input
											type="text" class="form-control text-light"
											autocomplete="off" 
											placeholder="Endereço" name="adress" id="adress">
									</div>
									
									<div class="form-group">
										<label for="exampleReference">Ponto de referência</label>
										<textarea class="form-control text-light" autocomplete="off"
											rows="4" name="reference" id="reference"></textarea>
									</div>
									
									<button type="button" class="btn btn-primary mr-2" id="submit" data-bs-dismiss="modal" onclick="updateAjax();">Atualizar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
							</div>
						</div>
					</div>
					<!-- Final formulário para cadastrar cliente -->
					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../components/javascript.jsp" />
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Atualizar Cadastro de Cliente</h5>
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
	  	$('#btn-search').click();
	  }
	});
	
	</script>


	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
	<script type="text/javascript">
		function searchAjax() 
		{
			let fieldValue    = $("#field").val();
			
			if(fieldValue.trim() === '' || fieldValue === 'undefined' || fieldValue === null)
			{
	    		let msg = "Preencha o campo de busca com telefone do cliente.";
	    		$('#updateModal').modal('show');
	    		document.getElementById('modal-msg').classList.add('text-danger');
	    		$("#modal-msg").text(msg);			
			}
			
			if (fieldValue != null && fieldValue != undefined) 
			{
				$.ajax({
				method  : "get",
				url     : <%=request.getContextPath()%>/ + "update",
				data    : 
				{
					field : fieldValue,
				},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json = JSON.parse(response);
					
					// Limpando possível cache nos resultados da busca
					$('#clientFound > tr').text('');
					
					// Informando a quantidade de resultados obtidos
					if(json.length > 0){
						document.getElementById('searchResult').classList.remove('text-danger');
						$('#searchResult').text("Cliente localizado no registro.");
					}
					else{
						document.getElementById('searchResult').classList.add('text-danger');
						$('#searchResult').text("Cliente não localizado no registro.");
					}
					
					// Populando os campos do resultado de busca, deletar por nº telefone
					for(let p = 0; p < json.length; p ++)
					{
						$('#clientFound').append
						("<tr><td>"+ json[p].name + "</td><td>" + json[p].phone +"</td><td>" + json[p].adress + "</td><td>"+ json[p].reference + "</td><td class='py-1 text-center'><a href='#register-form' onclick='updateData("+json[p].phone+")'><img src='"+<%=request.getContextPath()%>/+"assets/images/file-icons/update.png' alt='Atualizar' /></a></td></tr>");
					}	
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			}
		}
	</script>

	<script type="text/javascript">
		function updateData(phone){

			$.ajax({
				method  : "get",
				url     : <%=request.getContextPath()%>/ + "search",
				data    : 
				{
					field : phone,
					select: "phoneOption",
				},
				success : function(response) 
				{
					let json = JSON.parse(response);
					
					document.getElementById("id").value = json[0].id;
					document.getElementById("name").value = json[0].name;
					document.getElementById("phone").value = json[0].phone;
					document.getElementById("current-phone").value = json[0].phone;
					document.getElementById("email").value = json[0].email;
					document.getElementById("adress").value = json[0].adress;
					document.getElementById("reference").value = json[0].reference;
				}
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
		}
	</script>

	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
	<script type="text/javascript">
	function updateAjax() 
	{
		let idUser		  = $("#id").val();
		let nameUser      = $("#name").val();
		let phoneUser     = $("#phone").val();
		let phone    	  = $("#current-phone").val();
		let emailUser     = $("#email").val();
		let adressUser    = $("#adress").val();
		let referenceUser = $("#reference").val();
		let urlAction 	  = document.getElementById('form-register').action;
		
		if(nameUser.trim() === '' || phoneUser.trim() === '' || adressUser.trim() === '')
		{
    		let msg = "Preencha todos campos obrigatórios*, telefone apenas números";
    		$('#updateModal').modal('show');
    		$("#modal-msg").text(msg);
    		document.getElementById('modal-msg').classList.add('text-danger');
		}
		{
			$.ajax
			({
				method : "POST",
				url    : <%=request.getContextPath()%>/ + "update",
				data   : 
				{
					id			 : idUser,
					name 	     : nameUser,
					newPhone     : phoneUser,
					phone        : phone,
					email 	     : emailUser,
					adress 	     : adressUser,
					reference    : referenceUser,
				},
			    success : function(response) 
			    {
			    	if(response === "success")
			    	{
			    		let msg = "Cliente atualizado com sucesso!";
			    		$('#updateModal').modal('show');
			    		$("#modal-msg").text(msg);
			    		document.getElementById('modal-msg').classList.remove('text-danger');
			    		cleanForm();
			    		window.location.href = "#top"
			    		
			    	}
			    	else
			    	{
			    		let msg = "Telefone já cadastrado em sistema";
			       		$('#updateModal').modal('show');
			    		$("#modal-msg").text(msg);
			    		document.getElementById('modal-msg').classList.add('text-danger');
			    		updateData(phone);
			    	}
				},
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao cadastrar cliente.');
			});
		}
	}
	</script>
	
	<!-- Script para limpar o formulário -->
	<script type="text/javascript">
	
		function cleanForm() 
		{
			var elements = document.getElementById('form-register').elements;
	
			for (p = 0; p < elements.length; p++) 
			{
				elements[p].value = '';
			}
			
			document.getElementById('field').value = '';
			document.getElementById('searchResult').innerText = '';
			$('#clientFound > tr').text('');
			
		}
	</script>
	
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#updateModal').modal('hide');
	}
	</script>
	
	<!-- Script para dar um refresh na página "f5" -->
	<script type="text/javascript">
	var btn = document.querySelector("#btn-modal");
	btn.addEventListener("click", function() {
	    location.reload();
	});
	
	</script>
	
</body>
</html>