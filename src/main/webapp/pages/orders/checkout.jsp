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

					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Confirmação de pedido</h4>
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
												<th>Observações</th>
												<th>Valor</th>
												<th>Excluir</th>
											</tr>
										</thead>
										<tbody class="text-secondary">
											<c:forEach items="${products}" var="p">
												<tr>
													<td><c:out value="${p.prodName}"></c:out></td>
													<td><c:out value="${p.prodName}"></c:out></td>
													<td><c:out value="${p.prodPrice}"></c:out></td>
													<td class="text-center"><a
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
								<h4 class="card-title">Confirmação de cliente</h4>
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
										<tbody class="text-secondary">
											<tr>
												<td>${client.name}</td>
												<td>${client.phone}</td>
												<td>${client.adress}</td>
												<td>${client.reference}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12 stretch-card">
						<a class="nav-link btn btn-success create-new-button"
							id="createbuttonDropdown" aria-expanded="false" href="#">
							Finalizar Pedido</a>
					</div>

					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../javascript.jsp" />
	</div>
</body>
</html>