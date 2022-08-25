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
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
															
							<div class="form-group">							
							<h4 class="card-title">Todos ClientesCadastrados</h4>
							<button class="btn-inverse-info" type="submit"  id="submit"
								onclick="searchAjax();" style="width: 25%; padding: 4.5px">Listar</button>
								<span id="countResult" class="text-success h6" style="margin-left: 20px" ></span>
							</div>				
							<!-- Input para busca com highlights -->
							<input id='query' class="form-control text-secondary" style="width: 50%; " placeholder='Buscar por...' type='text'>
							
								<div class="table-responsive" id="teste">
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
										<tbody id="client-found">
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


	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
	<script type="text/javascript">
		function searchAjax() 
		{
			let urlAction = document.getElementById('form-search').action;
			
				$.ajax({

				method  : "get",
				url     : urlAction,
				data    : {action : 'searchList'},
				success : function(response) 
				{
					// Convertendo o envio do argumento de ServletSearch para JSON
					let json = JSON.parse(response);
					
					// Limpando possível cache nos resultados da busca
					$('#client-found > tr').remove();
					
					// Informando a quantidade de resultados obtidos
					if(json.length > 0)
					{
						document.getElementById('countResult').classList.remove('text-danger');
						$('#countResult').text(json.length + " registros de cliente.");
					}

					// Populando os campos do resultado de busca
					for(let p = 0; p < json.length; p ++)
					{
						$('#client-found').append
						("<tr><td class='text-secondary'>"+ json[p].name + "</td><td class='text-secondary'>" + json[p].phone +"</td><td class='text-secondary'>" + json[p].adress + "</td><td class='text-secondary'>"+ json[p].reference + "</td><td class='text-secondary'>"+json[p].email+"</td></tr>");
					}	
					
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			
		}
	</script>

	
	<!-- Script para busca com highlights -->
	<script src="https://cdn.jsdelivr.net/mark.js/8.6.0/mark.min.js"></script>
	<script type="text/javascript">
	// cria uma instância definindo o elemento onde será "marcada" as palavras.
	var instance = new Mark(document.getElementById('client-found'))

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