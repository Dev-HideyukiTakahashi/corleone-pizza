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
				
					<form action="<%=request.getContextPath()%>/products" id="form-pizza"></form>					</form>
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
															
							<div class="form-group">
								<h4 class="card-title">Todos Sabores de Pizza</h4>
	
								<button class="btn-info" type="submit"  id="submit"
								onclick="searchAjax();" style="width: 245px">Listar</button>
							</div>			
							<span id="update-success" class="text-success"></span>
								<div class="table-responsive">
									<table id="pizza-table" class="table table-striped" style="margin-top: 15px ">
										<thead>
											<tr>
												<th class="text-center">Código</th>
												<th class="text-center">Nome</th>
												<th class="text-center">Descrição</th>
												<th class="text-center">Preço</th>
											</tr>
										</thead>
										<tbody id="pizza-found">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../javascript.jsp" />
	</div>

	
	<!-- Modal -->
	<div class="modal fade" id="updateDescriptionPizza" tabindex="-1" aria-labelledby="updateDescriptionPizza" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success" id="updatePizza-name"></h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	        <input id="updatePizza-input" class="form-control text-light" value="" autocomplete="off"></input>
	      </div>
	      <div class="modal-footer">
	      	<p style="margin-right: 15px">Cod: <span id="updatePizza-code" class="text-warning"></span> </p>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="updatePizza();">Salvar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="updatePricePizza" tabindex="-1" aria-labelledby="updatePricePizza" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success" id="updatePrice-name"></h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	        <input type="number" id="updatePrice-input" class="form-control text-light" value="" autocomplete="off"></input>
	      </div>
	      <div class="modal-footer">
	      	<p style="margin-right: 15px">Cod: <span id="updatePrice-code" class="text-warning"></span> </p>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="updatePrice();">Salvar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- Script para requisição de busca -->
	<script type="text/javascript">
		function searchAjax() 
		{
			let urlAction = document.getElementById('form-pizza').action;
			
			// Limpando possível cache nos resultados da busca
				$.ajax({

				method  : "get",
				url     : urlAction,
				data    : {prodType : 'Pizza'},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json = JSON.parse(response);
					
					// Limpando possível cache nos resultados da busca
					$('#pizza-found > tr').remove();
					
						

					// Populando os campos do resultado de busca
					for(let p = 0; p < json.length; p ++)
					{
						$('#pizza-found').append
						('<tr><td class="text-primary text-center">'+ json[p].prodCode + '</td><td class="text-secondary text-center" id="">' + json[p].prodName +'</td><td class="text-secondary text-center">' + json[p].prodDescription +  '<button class="mdi mdi-lead-pencil" onclick="updateDescriptionData('+json[p].prodCode+')" style="margin-left: 10px"></button></td><td class="text-success text-center">'+ json[p].prodPriceFormatter + '<button class="mdi mdi-lead-pencil" onclick="updatePriceData('+json[p].prodCode+')" style="margin-left: 10px"></button></td></tr>');
					}
					
					//Algoritmo para ordernar a tabela;
					orderTable();
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});	
			
		}
	</script>
	
	<!-- Script para requisição dos dados para atualização da pizza no modal -->
	<script type="text/javascript">
	
		function updateDescriptionData(code){
			
			let urlAction = document.getElementById('form-pizza').action;
			
			$.ajax({

				method  : "get",
				url     : urlAction,
				data    : 
				{
					code : code,
				    type : 'pizzaCode',
					},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json		= JSON.parse(response);
					
					$('#updateDescriptionPizza').modal('show');
					$('#updatePizza-input').val(json.prodDescription);
					$('#updatePizza-name').text(json.prodName);
					$('#updatePizza-code').text(json.prodCode);
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});	
		}
	</script>
	
	<!-- Script para requisição dos dados para atualização do preço no modal -->
	<script type="text/javascript">
	
		function updatePriceData(code){
			
			let urlAction = document.getElementById('form-pizza').action;
			
			$.ajax({

				method  : "get",
				url     : urlAction,
				data    : 
				{
					code : code,
				    type : 'priceCode',
					},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json		= JSON.parse(response);
					
					$('#updatePricePizza').modal('show');
					$('#updatePrice-input').val(json.prodPrice);
					$('#updatePrice-name').text(json.prodName);
					$('#updatePrice-code').text(json.prodCode);
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});	
		}
	</script>
	
	
	<!-- Script para atualização da descrição da pizza -->
	<script type="text/javascript">
		function updatePizza() {

			let descriptionUpdate = $("#updatePizza-input").val();
			let codeUpdate		  = $("#updatePizza-code").text();
			let nameUpdate		  = $("#updatePizza-name").text();
			let urlAction 		  = document.getElementById('form-pizza').action;

			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					description 	  : descriptionUpdate,
					code			  : codeUpdate,
					updateData		  : "updatePizza",
				},
			    success : function(response) 
			    {
			    	if(response === "atualizado")
			    	{
			    		$('#update-success').text("Descrição da pizza " + nameUpdate + " atualizada");
			    		document.getElementById('update-success').classList.remove('text-danger');
			    		$('#updateDescriptionPizza').modal('hide');
						searchAjax();
			    	}
			    	else
			    	{
			    		$('#update-success').text("Descrição não atualizada, tente novamente.")
			    	}
				},
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao cadastrar cliente.');
			});
		}
	</script>
	
	
	<!-- Script para atualização da descrição da pizza -->
	<script type="text/javascript">
		function updatePrice() {

			let descriptionUpdate = $("#updatePrice-input").val();
			let codeUpdate		  = $("#updatePrice-code").text();
			let nameUpdate		  = $("#updatePrice-name").text();
			let urlAction 		  = document.getElementById('form-pizza').action;

			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					description 	  : descriptionUpdate,
					code			  : codeUpdate,
					updateData		  : "updatePrice",
				},
			    success : function(response) 
			    {
			    	if(response === "atualizado")
			    	{
			    		$('#update-success').text("Descrição da pizza " + nameUpdate + " atualizada");
			    		document.getElementById('update-success').classList.remove('text-danger');
			    		$('#updatePricePizza').modal('hide');
						searchAjax();
			    	}
			    	else
			    	{
			    		$('#update-success').text("Descrição não atualizada, tente novamente.")
			    	}
				},
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao cadastrar cliente.');
			});
		}
	</script>
	
	
	<!-- Script para ordernar a tabela por ordem de código -->
	<script type="text/javascript">
	function orderTable() {
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("pizza-table");
		  switching = true;
		  while (switching) {
		    switching = false;
		    rows = table.rows;
		    for (i = 1; i < (rows.length - 1); i++) {
		      shouldSwitch = false;
		      x = rows[i].getElementsByTagName("TD")[0];
		      y = rows[i + 1].getElementsByTagName("TD")[0];
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }
		  
		}
	</script>
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#updateData').modal('hide');
	}
	</script>
	
	
</body>
</html>