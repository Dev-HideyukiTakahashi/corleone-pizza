<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-BR">

<!-- Cabeçalho da página -->
<jsp:include page="head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="main-panel">
				<div class="content-wrapper">

					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Relatório de logs</h4>
								<p class="card-description">
									Log dos registros com alterações de nome do produto.
								</p>
								<div class="table-responsive">
									<table class="table table-dark">
										<thead>
											<tr>
												<th>Data</th>
												<th>Log</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${logs}" var="l">
												<tr>
													<td><c:out value="${l.date}"></c:out></td>
													<td style="white-space: pre-wrap;line-height: 1.5"><c:out value="${l.field}"></c:out></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="javascript.jsp" />
	</div>
</body>
</html>