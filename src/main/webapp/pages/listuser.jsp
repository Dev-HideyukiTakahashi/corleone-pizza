<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

	<!-- Cabe�alho da p�gina -->
	<jsp:include page="head.jsp"></jsp:include>
	<body>
	
	<div class="container-scroller">
	
		<!-- Navegador lateral da p�gina -->
		<jsp:include page="sidebar.jsp"></jsp:include>
	
		<div class="container-fluid page-body-wrapper">
	
			<!-- Navbar do cabe�alho -->
			<jsp:include page="navbar.jsp"></jsp:include>
	
	
			<div class="main-panel">
				<div class="content-wrapper">
	
					<form action="<%=request.getContextPath()%>/search" id="form-search"></form>
						<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
																
								<div class="form-group">							
									<h4 class="card-title">Todos Clientes Cadastrados</h4>
									<span class="text-success h6" style="margin-left: 1%">${userDataSize} registros de usu�rios.</span>	
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" style="width: 50%; " placeholder='Buscar por...' type='text'>
									
									<div class="table-responsive" id="teste">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>ID</th>
													<th>Nome</th>
													<th>Telefone</th>
													<th>Email</th>
													<th>Login</th>
													<th>Senha</th>
													<th>Filial</th>
												</tr>
											</thead>
											<!-- Tag que est� recebendo o HighLights -->
											<tbody id="client-view">
											<!-- Recebendo do /ServletSearch o m�todo 'usuarioDao.consultaUsuarioList()', enviando a resposta pelo request.setAttribute  -->
												<c:forEach items="${userData}" var="ud">
													<tr>
														<td class="text-secondary"><c:out value="${ud.id}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.adminName}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.phone}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.email}"></c:out> </td>
														<td class="text-warning"><c:out value="${ud.login}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.password}"></c:out> </td>
														<td class="text-secondary"><c:out value="${ud.partner}"></c:out> </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="footer.jsp"></jsp:include>
				</div>
			</div>
			<jsp:include page="javascript.jsp" />
		</div>
	</div>
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