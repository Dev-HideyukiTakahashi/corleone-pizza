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
				
					<form action="<%=request.getContextPath()%>/pizza" id="form-pizza"></form>					</form>
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
															
							<div class="form-group">
								<h4 class="card-title">Todos Sabores de Pizza</h4>
								<div style="display: flex; width: 100%; justify-content: space-between;">
								    <button class="btn-inverse-info" type="submit" 
									onclick="searchAjax();" style="width: 25%">Listar</button>
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; " placeholder='Buscar por...' type='text'>
								</div>
							</div>			
							<button style="width: 25%; padding: 4.5px" class="btn-inverse-success" type="submit"  id="submit"
								onclick="searchAjax();">Novo Sabor</button>
							<span id="update-success" class="text-success"></span>
								<div class="table-responsive">
									<table id="pizza-table" class="table table-striped" style="margin-top: 15px ">
										<thead>
											<tr>
												<th>Código</th>
												<th>Nome</th>
												<th>Descrição</th>
												<th>Preço</th>
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
	
	
	<!-- Modal -->
	<div class="modal fade" id="updateNamePizza" tabindex="-1" aria-labelledby="updateNamePizza" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success" id="updateName-name"></h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	        <input id="updateName-input" class="form-control text-light" value="" autocomplete="off"></input>
	      </div>
	      <div class="modal-footer">
	      	<p style="margin-right: 15px">Cod: <span id="updateName-code" class="text-warning"></span> </p>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="updateName();">Salvar</button>
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
						('<tr><td class="text-primary text-center">'+ json[p].prodCode + '</td><td class="text-secondary"><button class="mdi mdi-lead-pencil" onclick="updateNameData('+json[p].prodCode+')" style="margin-right: 10px"></button>' + json[p].prodName +'</td><td class="text-secondary"><button class="mdi mdi-lead-pencil" onclick="updateDescriptionData('+json[p].prodCode+')" style="margin-right: 10px"></button>' + json[p].prodDescription +  '</td><td class="text-success">'+ json[p].prodPriceFormatter + '<button class="mdi mdi-lead-pencil" onclick="updatePriceData('+json[p].prodCode+')" style="margin-left: 10px"></button></td></tr>');
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
				    type : 'search',
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
	
		<!-- Script para requisição dos dados para atualização da pizza no modal -->
	<script type="text/javascript">
	
		function updateNameData(code){
			
			let urlAction = document.getElementById('form-pizza').action;
			
			$.ajax({

				method  : "get",
				url     : urlAction,
				data    : 
				{
					code : code,
				    type : 'search',
					},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json		= JSON.parse(response);
					
					$('#updateNamePizza').modal('show');
					$('#updateName-input').val(json.prodName);
					$('#updateName-name').text(json.prodName);
					$('#updateName-code').text(json.prodCode);
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
				    type : 'search',
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
	
	
	<!-- Script para atualização da preço da pizza -->
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
			    		$('#update-success').text("Preço da pizza " + nameUpdate + " atualizada");
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
	
	
	
	<!-- Script para atualização da name da pizza -->
	<script type="text/javascript">
		function updateName() {

			let descriptionUpdate = $("#updateName-input").val();
			let codeUpdate		  = $("#updateName-code").text();
			let nameUpdate		  = $("#updateName-name").text();
			let urlAction 		  = document.getElementById('form-pizza').action;

			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					description 	  : descriptionUpdate,
					code			  : codeUpdate,
					updateData		  : "updateName",
				},
			    success : function(response) 
			    {
			    	if(response === "atualizado")
			    	{
			    		console.log("SUCCESSSSSSs")
			    		$('#update-success').text("Nome da pizza " + nameUpdate + " atualizada");
			    		document.getElementById('update-success').classList.remove('text-danger');
			    		$('#updateNamePizza').modal('hide');
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
		$('#updatePricePizza').modal('hide');
		$('#updateNamePizza').modal('hide');
		$('#updateDescriptionPizza').modal('hide');
	}
	</script>
	
	<!-- Script para busca com highlights -->
	<script src="https://cdn.jsdelivr.net/mark.js/8.6.0/mark.min.js"></script>
	<script type="text/javascript">
	// cria uma instância definindo o elemento onde será "marcada" as palavras.
	var instance = new Mark(document.getElementById('pizza-found'))

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