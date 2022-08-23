<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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

					<!-- Inicio do formulário de busca -->
					<div class="col-md-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Localizar Cliente</h4>
								<div class="form-group">
									<label for="select">Selecione o campo da busca</label> <select
										class="form-control text-primary" id="select" name="select"
										style="width: 20%; font-size: 16px">
										<option value="phoneOption">Telefone</option>
										<option value="nameOption">Nome</option>

									</select>
								</div>
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control text-light" id="field"
											placeholder="Campo para busca" aria-label="field"
											required="required" aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-sm btn-primary" type="button"
												onclick="searchAjax();">Buscar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Final do formulário de busca -->
					<!-- Inicio da tabela de busca -->
					<div class="col-lg-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Resultado da busca</h4>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Nome</th>
												<th>Telefone</th>
												<th>Endereço</th>
												<th>Referência</th>
												<th>Pedir</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>Cliente Teste</td>
												<td>11-942542595</td>
												<td>Rua testadora dos teste 85</td>
												<td>Próximo a rua esperança que não morra o sistema</td>
												<td class="py-1"><a href="#"> <img
														src="<%=request.getContextPath()%>/assets/images/favicon.png"
														alt="image" /></a></td>
											</tr>
											<tr>
												<td>Cliente Teste 2</td>
												<td>11-942542595</td>
												<td>Rua dos testes de sistema 22</td>
												<td>Depois da esquina do teste que deu ruim</td>
												<td class="py-1"><a href="#"> <img
														src="<%=request.getContextPath()%>/assets/images/favicon.png"
														alt="image" /></a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- Final da tabela de busca -->
					<jsp:include page="../footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../javascript.jsp" />
	</div>

	<!-- Script para checar a escolha do filtro para busca -->
	<script type="text/javascript">
		function checkSelect() {
			let field = $("#field").val();
			let select = $("#select").val();

			if (field.trim() === '' || field === undefined || field === null) {

				alert("Preencha o campo de busca.");
			} else {
				if (select == "phoneOption") {
					return "phoneOption";
				} else if (select == "nameOption") {
					return "nameOption";
				}
			}

		}
	</script>

	<script type="text/javascript">
		function searchAjax() {
			let field = $("#field").val();
			let select = checkSelect();

			if (select != null || select != undefined) {
				$.ajax({

				method : "get",
				url : "corleone-pizza/search",
				data : ,
				success : function(response) {

					document.getElementById('searchSuccess').textContent = response;
				}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro inesperado na busca do cliente.');
				});
			}

		}
	</script>
</body>
</html>