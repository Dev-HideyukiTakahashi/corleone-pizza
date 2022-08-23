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
										class="form-control text-primary" id="select"
										style="width: 20%; font-size: 16px">
										<option>Telefone</option>
										<option>Nome</option>

									</select>
								</div>
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control text-light"
											placeholder="Campo para busca" aria-label="Campo para busca"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-sm btn-primary" type="button">Buscar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Final do formulário de busca -->
					<!-- Inicio formulário para cadastrar cliente -->
					<div class="col-12 grid-margin stretch-card">
						<div class="card">

							<div class="card-body">
								<span class="text-success" id="registerSucess">${registerSucess}</span>
								<span class="text-danger">${registerFail}</span>
								<h4 class="card-title">Atualizar cadastro de cliente</h4>
								<p class="card-description">Preencha todos os campos</p>

								<form class="forms-sample" method="post" id="form-register"
									action="<%=request.getContextPath()%>/register">
									<div class="form-group">
										<label for="exampleInputName">Nome</label> <input type="text"
											autocomplete="off" class="form-control text-light"
											placeholder="${clientUpdate.name}" name="name" id="name"
											value="${clientData.name}">
									</div>
									<div class="form-group">
										<label for="exampleInputPhone">Telefone</label> <input
											type="text" class="form-control text-light"
											autocomplete="off" id="phone" value="${clientData.phone}"
											placeholder="Telefone" name="phone" required="required">
									</div>
									<div class="form-group">
										<label for="exampleInputEmail">Email</label> <input
											type="email" class="form-control text-light"
											autocomplete="off" id="email" value="${clientData.email}"
											placeholder="Email" name="email">
									</div>
									<div class="form-group">
										<label for="exampleInputAdress">Endereço</label> <input
											type="text" class="form-control text-light"
											value="${clientData.adress}" autocomplete="off"
											placeholder="Endereço" name="adress" id="adress">
									</div>
									<div class="form-group">
										<label for="exampleReference">Ponto de referência</label>
										<textarea class="form-control text-light" autocomplete="off"
											rows="4" name="reference" id="reference"></textarea>
									</div>
									<button type="submit" class="btn btn-primary mr-2" id="submit">Cadastrar</button>
									<button type="button" class="btn btn-dark"
										onclick="cleanForm();">Limpar</button>
								</form>
								<!-- Final formulário para cadastrar cliente -->

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