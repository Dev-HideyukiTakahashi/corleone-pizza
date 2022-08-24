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

					<form action="<%=request.getContextPath()%>/update" id="form-search"></form>
					<!-- Inicio do corpo da página de exclusão -->
					<div class="col-md-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Buscar cliente por nº de telefone</h4>
								
								<div class="form-group">
									<button class='btn-inverse-info' disabled="disabled">Telefone</button>
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
								<h4 class="card-title">Qual cliente deseja excluir</h4>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Nome</th>
												<th>Telefone</th>
												<th>Endereço</th>
												<th>Referência</th>
												<th class='text-center'>Excluir</th>
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
					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../javascript.jsp" />
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Deletar Cliente</h5>
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
	  	$('#btn-search').click();
	  }
	});
	
	</script>


	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
	<script type="text/javascript">
		function searchAjax() 
		{
			
			let fieldValue    = $("#field").val();
			let urlAction 	  = document.getElementById('form-search').action;
			
			if(fieldValue.trim() === '' || fieldValue === 'undefined' || fieldValue === null)
			{
	    		let msg = "Preencha o campo de busca com telefone do cliente.";
	    		$('#deleteModal').modal('show');
	    		document.getElementById('modal-msg').classList.add('text-danger');
	    		$("#modal-msg").text(msg);			
			}
			
			if (fieldValue != null && fieldValue != undefined) 
			{
				$.ajax({
				method  : "get",
				url     : urlAction,
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
						("<tr><td>"+ json[p].name + "</td><td>" + json[p].phone +"</td><td>" + json[p].adress + "</td><td>"+ json[p].reference + "</td><td class='py-1 text-center'><a href='#' onclick='deleteAjax(" + json[p].phone +");'><img src='../../assets/images/file-icons/delete.png' alt='Excluir' /></a></td></tr>");
					}	
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			}
		}
	</script>


	
	<script type="text/javascript">
	function deleteAjax(jsonPhone) 
	{
		let urlAction = document.getElementById('form-search').action;
		let option = confirm("Tem certeza que deseja excluir?"); //Pop up boolean de confirmação deletar
		
		if(option)
		{
			$.ajax({

				method : "get",
				url : urlAction,
				data : 
				{
					phone : jsonPhone,
				},				
			})
			
			$('#clientFound > tr').text('');
			$('#field').text('');
			$('#searchResult').text("Cliente excluído com sucesso do registro.");
		}
	}
	</script>
	
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#deleteModal').modal('hide');
	}
	</script>
	
</body>
</html>