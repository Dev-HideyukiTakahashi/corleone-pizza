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

					<form action="<%=request.getContextPath()%>/search" id="form-search"></form>					</form>
					<!-- Inicio do corpo com campos busca -->
					<div class="col-md-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
							
								<h4 class="card-title">Localizar Cliente</h4>
								
								<div class="form-group">
									<label for="select">Selecione o campo da busca</label> 
									<select
										class="form-control text-primary" id="select" name="select"
										style="width: 20%; font-size: 16px">
										<option value="phoneOption">Telefone</option>
										<option value="nameOption">Nome</option>
									</select>
								</div>
								
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control text-light" id="field"
											placeholder="Campo para busca" aria-label="field" autocomplete="off"
											required="required" aria-describedby="basic-addon2">
										<div class="input-group-append" >
											<button class="btn btn-sm btn-primary" type="button"  id="submit"
												onclick="searchAjax();">Buscar</button>
										</div>
									</div>
								</div>
									<!-- Contador de resultados impresso na tela -->
									<span id="countResult" class="text-success h6"></span>
							</div>
						</div>
					</div>
					<!-- Final do corpo com campos de busca -->
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Resultado da busca</h4>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Nome</th>
												<th>Telefone</th>
												<th>Endereço</th>
												<th>Referência</th>
												<th>Email</th>
											</tr>
										</thead>
										<tbody id="clientFound">
										<!-- O resutado (no caso <td>) vem por uma requisição AJAX -->
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
	<div class="modal fade" id="findModal" tabindex="-1" aria-labelledby="findModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Procurar Cliente nos Registros</h5>
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

	<!-- Script para checar qual foi a escolha de filtro no campo de busca -->
	<script type="text/javascript">
		function checkSelect() 
		{
			let field  = $("#field").val();
			let select = $("#select").val();
			
			if (field.trim() === '' || field === undefined || field === null)  // Checando se o campo de busca foi preenchido
			{
	    		let msg = "Preencha o campo de busca, com nome ou telefone.";
	    		$('#findModal').modal('show');
	    		document.getElementById('modal-msg').classList.add('text-danger');
	    		$("#modal-msg").text(msg);	
	    		
			} 
			else 
			{
				if (select == "phoneOption") {return "phoneOption";} 
				else if (select == "nameOption") {return "nameOption";}
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

	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
	<script type="text/javascript">
		function searchAjax() 
		{
			let fieldValue    = $("#field").val();
			let selectValue   = checkSelect();
			let urlAction 	  = document.getElementById('form-search').action;
			
			if (selectValue != null || selectValue != undefined && fieldValue != null && fieldValue != undefined) 
			{
				$.ajax({

				method  : "get",
				url     : urlAction,
				data    : 
				{
					field : fieldValue,
					select: selectValue,
				},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json = JSON.parse(response);
					
					// Limpando possível cache nos resultados da busca
					$('#clientFound > tr').text('');
					
					// Informando a quantidade de resultados obtidos
					if(json.length > 0)
					{
						document.getElementById('countResult').classList.remove('text-danger');
						$('#countResult').text(json.length + " registros de cliente.");
					}
					else
					{
						document.getElementById('countResult').classList.add('text-danger');
						$('#countResult').text(json.length + " registros de cliente.");
					}

					// Populando os campos do resultado de busca
					for(let p = 0; p < json.length; p ++)
					{
						$('#clientFound').append
						("<tr><td>"+ json[p].name + "</td><td>" + json[p].phone +"</td><td>" + json[p].adress + "</td><td>"+ json[p].reference + "</td><td>"+json[p].email+"</td></tr>");
					}	
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			}
		}
	</script>
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#findModal').modal('hide');
	}
	</script>
</body>
</html>