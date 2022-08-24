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

								<div class="table-responsive">
									<table class="table table-striped" style="margin-top: 15px ">
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
	
	
	<!-- Script para requisição com back-end com AJAX e JACKSON na resposta para receber o JSON -->
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
						("<tr><td class='text-primary text-center'>"+ [p] + "</td><td class='text-secondary text-center'>" + json[p].prodName +"</td><td class='text-secondary text-center'>" + json[p].prodDescription +  "<a class='mdi mdi-lead-pencil' style='margin-left: 10px'></a></td><td class='text-success text-center'>"+ json[p].prodPriceFormatter + "<a class='mdi mdi-lead-pencil' style='margin-left: 10px'></a></td></tr>");
					}	
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			
		}
	</script>
	
</body>
</html>