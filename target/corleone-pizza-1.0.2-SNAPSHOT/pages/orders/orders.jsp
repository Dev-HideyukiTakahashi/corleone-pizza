<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<!-- Cabe�alho da p�gina -->
<jsp:include page="../components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="../components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
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
									<br/>
									<form method="GET" >
										<div style="display: flex; gap: 5px;">
											<input name="dateBegin" id="dateBegin" type="date" class="form-control" style="width: 15%; background-color: #6c7293;">
											<input name="dateFinal" id="dateFinal" type="date" class="form-control" style="width: 15%; background-color: #6c7293;">
											<button type="button" class="btn btn-primary" onclick="report()" style="margin-left: 10px">Relat�rio</button>
										</div>
									</form>
									<br/>
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr>
													<th class="text-center">C�digo</th>
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
														<td class="text-center"><c:out value="${od.getDate()}"></c:out></td>
														<td class="text-center">
															<div class="badge badge-outline-success"><a href="#" class="text-success" style="text-decoration: none" onclick="modalView(${od.orderCode})">Ver</a></div>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							<!-- Inicio pagina��o -->
							<nav aria-label="Page navigation example">
							  <ul class="pagination justify-content-center">
							    <li>
							      <a class="" style="padding: 0.2rem 0.5rem;line-height: 1.25;color: #007bff;
							      background-color: #000000;border-radius: ;border: 1px solid #dee2e6;display: block;
							      text-decoration: none; border-top-left-radius: 0.25rem;
   								  border-bottom-left-radius: 0.25rem;">
							        <span aria-hidden="true">&laquo;</span>
							        <span class="sr-only">Previous</span>
							      </a>
							    </li>
							  <%
							  	int totalPages = (int) request.getAttribute("totalPages");
							  	int numberPage = (int) request.getAttribute("numberPage");
							 	for(int i = 0; i < totalPages; i++)
							 	{
							 		String url = request.getContextPath() + "/order?action=listAll&page=" + (i * 10);
							 		
							 		if(numberPage == (i*10)){
							 			out.print("<li class=\"page-item\"><a class=\"page-link\" style=\"padding: 0.2rem 0.5rem; background-color: #dee2e6 \">"+(i + 1)+"</a></li>");
							 		}
							 		else{
							 			out.print("<li class=\"page-item\"><a class=\"page-link\" style=\"padding: 0.2rem 0.5rem\" href=\""+url+"\">"+(i + 1)+"</a></li>");
							 		}
							 		
							 	}
							 	if(totalPages != 0)
							  %>
							    <li>
							      <a class="" style="padding: 0.2rem 0.5rem;line-height: 1.25;color: #007bff;
							      background-color: #000000;border-radius: ;border: 1px solid #dee2e6;display: block;
							      text-decoration: none; border-top-right-radius: 0.25rem;
   								  border-bottom-right-radius: 0.25rem;">
							        <span>&raquo;</span>
							      </a>
							    </li>
							  </ul>
							</nav>
							<!-- Final pagina��o -->
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
	
	<!-- Script para gerar rel�torio -->>
	<script type="text/javascript">
		function report(){
			let dateBegin = $('#dateBegin').val();
			let dateFinal = $('#dateFinal').val();

			if(dateBegin === '' || dateFinal === ''){
				alert("Informe data in�cio e final do filtro!")
			}
			else{
				let url   = <%=request.getContextPath()%>/ + 'order?action=report&dateBegin='+dateBegin+'&dateFinal='+dateFinal;
				window.open(url ,'_blank');
			}
		}
	</script>
	
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
				
				// Limpando poss�vel cache nos resultados da busca
				$('#table-order > tr').text('');
				
				$('#table-order').append
				("<tr><td><span class='text-warning'>Dados do Cliente </span></td></tr>").append
				("<tr><td><span>Nome: </span>" + json.orderClient.name + "</td><td><span>Pedido n�: </span>" + json.orderCode+ "</td></tr>").append
				("<tr><td><span>Endere�o: </span>"+ json.orderClient.adress + "</td><td><span>Data: </span>" + json.date + "</td></tr>").append
				("<tr><td><span>Refer�ncia: </span>"+ json.orderClient.reference + "</td><td><span>Telefone: </span>"+ json.orderClient.phone + "</td></tr>").append
				("<tr><td><span class='text-warning'>Dados do Pedido </span></td></tr>");
				for(let i = 0; i < json.products.length; i ++){
					$('#table-order').append
					("<tr><td><span>Produto: </span>"+ json.products[i].prodName + "</td><td><span>Pre�o: </span>"+ json.products[i].prodPrice + "</td></tr>");
				}
				$('#table-order').append
				("<tr><td><span>Observa��es: </span>"+ json.comments + "<td><span>Total: </span>"+ json.total+ "</td></tr>").append	
				("<tr><td><span>Entrega: </span>"+ json.orderMotoboy.motoboyName + "<td></tr>");	
				
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
		// cria uma inst�ncia definindo o elemento onde ser� "marcada" as palavras.
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