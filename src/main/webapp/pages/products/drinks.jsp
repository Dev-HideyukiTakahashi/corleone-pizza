<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

			<div class="main-panel">
				<div class="content-wrapper">
				
					<form action="<%=request.getContextPath()%>/drink" id="form-drink"></form>					
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
															
							<div class="form-group">
								<h4 class="card-title">Todos Bebidas da Casa</h4>
								<div>
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; " placeholder='Buscar por...' type='text'>
								</div>
							</div>
							<!-- Checando se o usuário logado é userAdmin -->
							<c:if test="${isAdmin}"><button class="btn-inverse-success" style="width: 25%" id="btn-insert">Adicionar Bebida</button></c:if>
							
								<div class="table-responsive">
									<table id="drink-table" class="table table-striped" style="margin-top: 15px ">
										<thead>
											<tr>
												<th class="text-center">Código</th>
												<th>Nome</th>
												<th>Descrição</th>
												<th>Preço</th>
												<th>Pedir</th>
											</tr>
										</thead>
										<tbody id="drink-found">
											<c:forEach items="${drinkData}" var="dd">
												<tr>
													<td class="text-primary text-center"><c:out value="${dd.prodCode}"></c:out></td>
													<td class="text-secondary"><button class="mdi mdi-lead-pencil" onclick="modelView(${dd.prodCode}, 'name');" style="margin-right: 10px"></button><c:out value="${dd.prodName}"></c:out></td>
													<td class="text-secondary"><c:if test="${isAdmin}"><button class="mdi mdi-lead-pencil" onclick="modelView(${dd.prodCode}, 'description');" style="margin-right: 10px"></button></c:if><c:out value="${dd.prodDescription}"></c:out></td>
													<td class="text-success"><c:if test="${isAdmin}"><button class="mdi mdi-lead-pencil" onclick="modelView(${dd.prodCode}, 'price');" style="margin-right: 10px"></button></c:if><c:out value="${dd.prodPrice}"></c:out></td>
													<td class='py-1'><a href='<%=request.getContextPath()%>/order?action=checkout&prodCode=${dd.prodCode}'><img src='<%=request.getContextPath()%>/assets/images/favicon.png' alt='Pedir' /></a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../components/javascript.jsp" />
	</div>

	
	<!-- Modal update Modal -->
	<div class="modal fade" id="update-modal" tabindex="-1" aria-labelledby="update-modal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success" id="updateModal-name"></h5>
	        <input type="hidden" id="request-field">
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	        <input id="updateModal-input" class="form-control text-light" value="" autocomplete="off"></input>
	      </div>
	      <div class="modal-footer">
	      	<p style="margin-right: 15px">Cod: <span id="updateModal-code" class="text-warning"></span> </p>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="updateData();" id="btn-modal">Salvar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal insert Modal -->
	<div class="modal fade" id="insert-modal" tabindex="-1" aria-labelledby="insert-modal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success">Adicionar nova Bebida</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	      	<label class="text-secondary" style="margin-top: 15px">Nome</label> 
	        <input class="form-control text-light" autocomplete="off" id="insertModal-name"></input>
	      	<label class="text-secondary" style="margin-top: 15px">Descrição</label> 
	        <input class="form-control text-light" autocomplete="off" id="insertModal-description"></input>
	      	<label class="text-secondary" style="margin-top: 15px">Preço</label> 
	        <input class="form-control text-light" autocomplete="off" type="number" id="insertModal-price"></input>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="insertData();" id="btn2-modal">Salvar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- Script para atualização do campo de bebidas-->
	<script type="text/javascript">
		function updateData() {

			let descriptionUpdate = $("#updateModal-input").val();
			let requestField	  = $("#request-field").val();
			let codeUpdate		  = $("#updateModal-code").text();
			let nameUpdate		  = $("#updateModal-name").text();

			let urlAction 		  = document.getElementById('form-drink').action;
			
			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					description 	  : descriptionUpdate,
					code			  : codeUpdate,
					updateData		  : requestField,
				},
			    success : function(response) 
			    {
		    		$('#update-modal').modal('hide');
		    		
		    		// Recarrega a página
		    		location.reload();
		    		
		    		
				},
			}).fail(function(xhr, status, errorThrown) {
				alert("Erro desconhecido ao alterar dados")
			});
		}
	</script>
	
	<!-- Script para inserir um novo de pizza -->
	<script type="text/javascript">
		function insertData() {
			
			let nameInsert 		  = $("#insertModal-name").val();
			let descriptionInsert = $("#insertModal-description").val();
			let priceInsert 	  = $("#insertModal-price").val();
			
			let urlAction 		  = document.getElementById('form-drink').action;
			
			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					action			   : "insert",
					newName 	 	   : nameInsert,
					newDescription     : descriptionInsert,
					newPrice 	       : priceInsert,
				},
			    success : function(response) 
			    {
		    		$('#insert-modal').modal('hide');
		    		
		    		// Recarrega a página
		    		location.reload();
				},
			}).fail(function(xhr, status, errorThrown) {
				alert("Erro desconhecido ao inserir dados")
			});
		}
	</script>
	
	<!-- Script para requisição dos dados para atualização da pizza no modal -->
	<script type="text/javascript">
	
		function modelView(code, value){
			let urlAction = document.getElementById('form-drink').action;
			$.ajax
			({

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
					let json = JSON.parse(response);
					
					
					// Mostrando o modal com os dados da requisição
					if(value === 'name')
					{
						$('#update-modal').modal('show');
						$('#updateModal-input').val(json.prodName);
						$('#updateModal-name').text(json.prodName);
						$('#updateModal-code').text(json.prodCode);
						$('#request-field').val('updateName');

					}
					else if(value === 'description')
					{
						$('#update-modal').modal('show');
						$('#updateModal-input').val(json.prodDescription);
						$('#updateModal-name').text(json.prodName);
						$('#updateModal-code').text(json.prodCode);
						$('#request-field').val('updateDescription');
					}
					else if(value === 'price')
					{
						$('#update-modal').modal('show');
						$('#updateModal-input').val(json.prodPrice);
						$('#updateModal-input').prop('type', 'number');
						$('#updateModal-name').text(json.prodName);
						$('#updateModal-code').text(json.prodCode);
						$('#request-field').val('updatePrice');
					}
				},
			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao atualizar dados');
			});	
		}
	</script>
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#update-modal').modal('hide');
		$('#insert-modal').modal('hide');
	}
	</script>
	
	<!-- Script para busca com highlights -->
	<script src="https://cdn.jsdelivr.net/mark.js/8.6.0/mark.min.js"></script>
	<script type="text/javascript">
	// cria uma instância definindo o elemento onde será "marcada" as palavras.
	var instance = new Mark(document.getElementById('drink-found'))

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
	
	
	
	<!-- Script 'listener' para insert-data (abrir o modal) -->
	<script type="text/javascript">
	var btn = document.querySelector("#btn-insert");
	btn.addEventListener("click", function() {
	    
		$('#insert-modal').modal('show');
	});
	
	</script>
</body>
</html>