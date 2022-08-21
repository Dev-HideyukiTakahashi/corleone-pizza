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
					<div class="col-12 grid-margin stretch-card">
						<div class="card">
							<div class="card-body">
								<h4 class="card-title">Cadastro de cliente</h4>
								<p class="card-description">Preencha todos os campos</p>
								<form class="forms-sample">
									<div class="form-group">
										<label for="exampleInputName1">Nome</label> <input type="text"
											class="form-control text-light" 
											placeholder="Nome">
									</div>
									<div class="form-group">
										<label for="exampleInputTelefone">Telefone</label> <input
											type="email" class="form-control text-light" id="exampleInputTelefone"
											placeholder="Telefone">
									</div>
									<div class="form-group">
										<label for="exampleInputEmail">Password</label> <input
											type="email" class="form-control text-light" 
											id="exampleInputEmail3" placeholder="Email">
									</div>
									<div class="form-group">
										<label for="exampleInputEndereço">Endereço</label> <input type="text"
											class="form-control text-light" 
											placeholder="Endereço">
									</div>
									<div class="form-group">
										<label for="exampleTextarea1">Ponto de referência</label>
										<textarea class="form-control text-light" id="exampleTextarea1" rows="4"></textarea>
									</div>
									<button type="submit" class="btn btn-primary mr-2">Submit</button>
									<button class="btn btn-dark">Cancel</button>
								</form>
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



















