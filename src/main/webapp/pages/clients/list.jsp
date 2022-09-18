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
	
					<form action="<%=request.getContextPath()%>/search" id="form-search"></form>
						<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
																
								<div class="form-group">							
									<h4 class="card-title">Todos Clientes Cadastrados</h4>
									<span class="text-success h6" style="margin-left: 1%">${clientDataSize} registros de clientes</span>	
									<!-- Input para busca com highlights -->
									<input id='query' class="form-control text-secondary" autocomplete="off" style="width: 50%; " placeholder='Buscar por...' type='text'>
									
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>Nome</th>
													<th>Telefone</th>
													<th>Endereço</th>
													<th>Referência</th>
													<th>Email</th>
												</tr>
											</thead>
											<!-- Tag que está recebendo o HighLights -->
											<tbody id="client-view">
											<!-- Recebendo do /ServletSearch o método 'clientSearchAll()', enviando a resposta pelo request.setAttribute  -->
												<c:forEach items="${clientData}" var="cd">
													<tr>
														<td class="text-secondary"><c:out value="${cd.name}"></c:out> </td>
														<td class="text-warning"><c:out   value="${cd.phone}"></c:out> </td>
														<td class="text-secondary"><c:out value="${cd.adress}"></c:out> </td>
														<td class="text-secondary"><c:out value="${cd.reference}"></c:out> </td>
														<td class="text-secondary"><c:out value="${cd.email}"></c:out> </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
			<jsp:include page="../components/javascript.jsp" />
		</div>
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