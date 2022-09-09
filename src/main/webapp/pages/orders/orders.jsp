<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
														<td class="text-center"><c:out value="${od.orderClient.phone}"></c:out><a href="https://api.whatsapp.com/send?phone=${od.orderClient.getPhoneWhats()}" class="mdi mdi-whatsapp"
														style="margin-left: 5px"></a></td>
														<td class="text-center"><c:out value="${od.dateString}"></c:out></td>
														

														<td class="text-center">
															<div class="badge badge-outline-success">Ver</div>
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

					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../javascript.jsp" />
	</div>
	
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