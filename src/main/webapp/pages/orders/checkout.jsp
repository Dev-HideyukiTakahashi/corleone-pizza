<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<!-- Cabe�alho da p�gina -->
<jsp:include page="../head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="../sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
			<jsp:include page="../navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">

					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Checkout</h4>
								<p class="card-description">Confirma��o de pedido</p>
								<div class="table-responsive">
									<table class="table table-dark">
										<thead>
											<tr>
												<th>Produto</th>
												<th>Observa��es</th>
												<th>Valor</th>
												<th>Excluir</th>
											</tr>
										</thead>
										<tbody class="text-secondary">
											<tr>
												<td>Mussarela</td>
												<td>Sem cebola</td>
												<td>R$ 39,99</td>
												<td>Button</td>
											</tr>
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
</body>
</html>