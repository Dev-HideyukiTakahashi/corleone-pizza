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
									<h4 class="card-title">Entregadores</h4>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th class="text-center">Nome</th>
													<th class="text-center">Telefone</th>
													<th class="text-center">Endereço</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${motoboyData}" var="md">
													<tr>
														<td class="text-center text-secondary"><c:out value="${md.motoboyName }"></c:out></td>
														<td class="text-center text-warning"><c:out value="${md.motoboyPhone}"></c:out></td>
														<td class="text-center text-secondary"><c:out value="${md.motoboyAdress}"></c:out></td>
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