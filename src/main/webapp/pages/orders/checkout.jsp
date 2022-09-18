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
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Confirmação de pedido*</h4>
								<div class="col-lg-12 stretch-card mb-3">
									<a class="nav-link btn btn-success create-new-button"
										href="<%=request.getContextPath()%>/PizzaController?prodType=pizza">
										+ Pizza</a> <a class="nav-link btn btn-primary create-new-button"
										style="margin-left: 10px;"
										href="<%=request.getContextPath()%>/drink?prodType=drink">
										+ Bebida</a>
								</div>
								<div class="table-responsive">
									<table class="table table-dark">
										<thead>
											<tr>
												<th>Produto</th>
												<th>Descrição</th>
												<th>Valor</th>
												<th>Excluir</th>
											</tr>
										</thead>
										<tbody class="text-secondary">
											<c:forEach items="${products}" var="p">
												<tr>
													<td id="product-name"><c:out value="${p.prodName}"></c:out></td>
													<td><c:out value="${p.prodDescription}"></c:out></td>
													<td><c:out value="${p.prodPrice}"></c:out></td>
													<td><a
														href="<%=request.getContextPath()%>/order?action=checkout&delete=delete&delCode=${p.prodCode}"><img
															src="<%=request.getContextPath()%>/assets/images/file-icons/delete.png"
															alt='Deletar' /></a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Confirmação de cliente*</h4>
								<div class="col-lg-12 stretch-card mb-3">
									<a class="nav-link btn btn-primary create-new-button"
										style="margin-left: 10px;"
										href="<%=request.getContextPath()%>/pages/clients/find.jsp">
										+ Buscar Cliente</a>
								</div>
								<div class="table-responsive">
									<table class="table table-dark">
										<thead>
											<tr>
												<th>Nome</th>
												<th>Telefone</th>
												<th>Endereço</th>
												<th>Referência</th>
											</tr>
										</thead>
										<tbody class="text-secondary" id="client-data">
											<tr>
												<td id="client-name">${client.name}</td>
												<td>${client.phone}</td>
												<td>${client.adress}</td>
												<td>${client.reference}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<label for="exampleTextarea1" style="margin-top: 15px">Observações</label>
								<input class="form-control" name="comments" id="comments">
								<form action="<%=request.getContextPath()%>/order?action=final"
									id="form-final"></form>
							</div>
						</div>
					</div>
					<div class="col-lg-12 stretch-card">
						<a class="nav-link btn btn-success create-new-button"
							onclick="submitOrder()" aria-expanded="false">
							Finalizar Pedido</a>
					</div>

					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../components/javascript.jsp" />
	</div>


	<!-- Modal -->
	<div class="modal fade" id="errorModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="errorModal">Confirmação de pedido</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close" onclick="closeModal();"></button>
				</div>
				<div class="modal-body">
					<h4 id="modal-msg" class="text-success"></h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal" onclick="closeModal();">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	function submitOrder() {
		
		let commentsView    = $('#comments').val();
		let urlAction 	    = document.getElementById('form-final').action;
		let clientName      = $('#client-name').text();
		let productName     = $('#product-name').text();
		
		if(clientName === null || clientName === '' || productName === null || productName === '')
		{
			let msg = "Preencha todos campos obrigatórios* para realizar um pedido!";
    		$('#errorModal').modal('show');
    		$("#modal-msg").text(msg);
    		document.getElementById('modal-msg').classList.add('text-danger');
		}
		else
		{
			$.ajax({
				method : "GET",
				url    : urlAction,
				data   : 
				{
					comments 	  : commentsView,
				},
			    success : function() 
			    {
					alert("Pedido realizado com sucesso!")
			    	window.location.href = "<%=request.getContextPath()%>/pages/main.jsp";
				},
				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado, tente novamente');
				});

		}
		
		
		}
	</script>
	
	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#errorModal').modal('hide');
	}
	</script>
</body>
</html>