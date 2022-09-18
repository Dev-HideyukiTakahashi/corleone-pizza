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

					<div class="row ">
						<div class="col-12 grid-margin">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Pedidos</h4>
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; " autocomplete="off" placeholder='Buscar por...' type='text'>
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr>
													<th class="text-center">Código</th>
													<th class="text-center">Nome Cliente</th>
													<th class="text-center">Valor</th>
													<th class="text-center">Telefone</th>
													<th class="text-center">Data</th>
													<th class="text-center">Detalhes</th>
												</tr>
											</thead>
											<tbody id="client-view">
												<c:forEach items="${orderData}" var="od">
													<tr>
														<td class="text-center"><c:out value="${od.orderCode }"></c:out></td>
														<td class="text-center"><c:out value="${od.orderClient.name}"></c:out></td>
														<td class="text-center"><c:out value="${od.getTotal()}"></c:out></td>
														<td class="text-center"><c:out value="${od.orderClient.phone}"></c:out><a href="https://api.whatsapp.com/send?phone=${od.orderClient.getPhoneWhats()}" target="_blank" class="mdi mdi-whatsapp"
														style="margin-left: 5px"></a></td>
														<td class="text-center"><c:out value="${od.dateString}"></c:out></td>
														<td class="text-center">
															<div class="badge badge-outline-success"><a href="#" class="text-success" style="text-decoration: none" onclick="modalView(${od.orderCode})">Ver</a></div>
														</td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</div>
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
	
	
	<!-- Modal com detalhes do pedido -->
	<div class="modal fade " id="order-view" tabindex="-1" aria-labelledby="order-view" aria-hidden="true" >
	  <div class="modal-dialog modal-lg" >
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success">Detalhes do pedido</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	      	<table class="table table-bordered text-secondary" >
	      		<tbody id="table-order">
	      		</tbody>
			</table>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	      </div>
	    </div>
	  </div>
	</div>
  </div>
	
	
	
	<!-- Script para abrir modal com detalhes do pedido -->
	<script type="text/javascript">
	function modalView(orderCode) {
		
		let urlAction = "<%=request.getContextPath()%>/order";
		
		$.ajax({
			method  : "GET",
			url     : urlAction,
			data    : 
			{
				code  : orderCode,
				action: "finalView",
			},
			success : function(response) 
			{
				// Convertendo o envio do argumento de ServletSearch para JSON
				let json = JSON.parse(response);
				
				console.log(json.orderClient.reference);
				// Limpando possível cache nos resultados da busca
				$('#table-order > tr').text('');
				
				$('#table-order').append
				("<tr><td><span class='text-warning'>Dados do Cliente </span></td></tr>").append
				("<tr><td><span>Nome: </span>" + json.orderClient.name + "</td><td><span>Pedido nº: </span>" + json.orderCode+ "</td></tr>").append
				("<tr><td><span>Endereço: </span>"+ json.orderClient.adress + "</td><td><span>Data: </span>" + json.date + "</td></tr>").append
				("<tr><td><span>Referência: </span>"+ json.orderClient.reference + "</td><td><span>Telefone: </span>"+ json.orderClient.phone + "</td></tr>").append
				("<tr><td><span class='text-warning'>Dados do Pedido </span></td></tr>");
				for(let i = 0; i < json.products.length; i ++){
					$('#table-order').append
					("<tr><td><span>Produto: </span>"+ json.products[i].prodName + "</td><td><span>Preço: </span>"+ json.products[i].prodPrice + "</td></tr>");
				}
				$('#table-order').append
				("<tr><td><span>Observações: </span>"+ json.comments + "<td><span>Total: </span>"+ json.total+ "</td></tr>");	
				
				$('#order-view').modal('show');
			}

			}).fail(function(xhr, status, errorThrown) {
				alert('Erro inesperado ao detalhar o pedido');
			});
		

		

	}
	</script>
	
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#order-view').modal('hide');
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
</body>
</html>