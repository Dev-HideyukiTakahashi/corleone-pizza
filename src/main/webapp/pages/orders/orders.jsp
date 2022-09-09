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
											<tbody>
												<c:forEach items="${orderData}" var="od">
													<tr>
														<td class="text-center"><c:out value="${od.orderCode }"></c:out></td>
														<td class="text-center"><c:out value="${od.orderClient.name}"></c:out></td>
														<td class="text-center"><c:out value="${od.getTotal()}"></c:out></td>
														<td class="text-center"><c:out value="${od.orderClient.phone}"></c:out></td>
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
</body>
</html>