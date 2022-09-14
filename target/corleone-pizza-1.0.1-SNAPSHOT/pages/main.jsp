<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

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
					<div class="row">
						<div class="col-12 grid-margin stretch-card">
							<div class="card corona-gradient-card">
								<div class="card-body py-0 px-0 px-sm-3">
									<div class="row align-items-center">
										<div class="col-4 col-sm-3 col-xl-2">
											<img
												src="<%=request.getContextPath()%>/assets/images/dashboard/promocao.png"
												class="gradient-corona-img img-fluid" alt="">
											<!-- Group126@2x -->
										</div>
										<div class="col-5 col-sm-7 col-xl-8 p-0">
											<h4 class="mb-1 mb-sm-0">Promoção liberada</h4>
											<p class="mb-0 font-weight-normal d-none d-sm-block">Compre
												2 pizzas e receba um refrigerante de brinde.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xl-3 col-sm-6 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-9">
											<div class="d-flex align-items-center align-self-start">
												<h3 class="mb-0">R$29,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:19</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<a href="<%=request.getContextPath()%>/order?action=checkout&prodCode=19" class="text-success"><span class="mdi mdi-arrow-top-right icon-item"></span></a>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Mussarela</h6>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-sm-6 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-9">
											<div class="d-flex align-items-center align-self-start">
												<h3 class="mb-0">R$39,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:9</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<a href="<%=request.getContextPath()%>/order?action=checkout&prodCode=9" class="text-success"><span class="mdi mdi-arrow-top-right icon-item"></span></a>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Calabresa</h6>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-sm-6 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-9">
											<div class="d-flex align-items-center align-self-start">
												<h3 class="mb-0">R$39,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:14</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<a href="<%=request.getContextPath()%>/order?action=checkout&prodCode=14" class="text-success"><span class="mdi mdi-arrow-top-right icon-item"></span></a>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Frango e
										Catupiry</h6>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-sm-6 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-9">
											<div class="d-flex align-items-center align-self-start">
												<h3 class="mb-0">R$49,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:24</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<a href="<%=request.getContextPath()%>/order?action=checkout&prodCode=24" class="text-success"><span class="mdi mdi-arrow-top-right icon-item"></span></a>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Portuguesa</h6>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-4 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title" style="text-align: center">Histórico
										de pagamentos</h4>
									<canvas id="transaction-history" class="transaction-chart"></canvas>
									<div
										class="bg-gray-dark d-flex d-md-block d-xl-flex flex-row py-3 px-4 px-md-3 px-xl-4 rounded mt-3">
										<div class="text-md-center text-xl-left">
											<h6 class="mb-1">Dinheiro</h6>
											<p class="text-muted mb-0">09 Set 2022, 21:12</p>
										</div>
										<div
											class="align-self-center flex-grow text-right text-md-center text-xl-right py-md-2 py-xl-0">
											<h6 class="font-weight-bold mb-0">R$ 59,00</h6>
										</div>
									</div>
									<div
										class="bg-gray-dark d-flex d-md-block d-xl-flex flex-row py-3 px-4 px-md-3 px-xl-4 rounded mt-3">
										<div class="text-md-center text-xl-left">
											<h6 class="mb-1">Cartão de crédito</h6>
											<p class="text-muted mb-0">09 Set 2022, 21:33</p>
										</div>
										<div
											class="align-self-center flex-grow text-right text-md-center text-xl-right py-md-2 py-xl-0">
											<h6 class="font-weight-bold mb-0">R$ 89,99</h6>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="col-md-12 col-xl-8 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Anotações do dia</h4>
									<div class="add-items d-flex">
										<input type="text" class="form-control todo-list-input"
											placeholder="Digite o texto">
										<button class="add btn btn-primary todo-list-add-btn">Add</button>
									</div>
									<div class="list-wrapper">
										<ul
											class="d-flex flex-column-reverse text-white todo-list todo-list-custom">
											<li>
												<div class="form-check form-check-primary">
													<label class="form-check-label"/> <input
														class="checkbox" type="checkbox"> Exemplo: Faltou
														ingrediente palmito para pizza portuguesa - dia 23/05 
												</div> <i class="remove mdi mdi-close-box"></i>
											</li>
											<li>
												<div class="form-check form-check-primary">
													<label class="form-check-label"/> <input
														class="checkbox" type="checkbox"> Exemplo: O
														entregador estava sem troco no pedido 221 
												</div> <i class="remove mdi mdi-close-box"></i>
											</li>
										</ul>
									</div>
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