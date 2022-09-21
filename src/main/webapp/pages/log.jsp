<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-BR">

<!-- Cabeçalho da página -->
<jsp:include page="components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da página -->
		<jsp:include page="components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabeçalho -->
			<jsp:include page="components/navbar.jsp"></jsp:include>

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
							<!-- Inicio paginação -->
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
							 		String url = request.getContextPath() + "/log?page=" + (i * 10);
							 		
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
							<!-- Final paginação -->
						</div>
					</div>
					<jsp:include page="components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="components/javascript.jsp" />
	</div>
</body>
</html>