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
						<a href="<%=request.getContextPath()%>/pages/clientes/cadastrar.jsp">CLICA AQUI</a>
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
												<h3 class="mb-0">R$39,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:1</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<span class="mdi mdi-arrow-top-right icon-item"></span>
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
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:2</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<span class="mdi mdi-arrow-top-right icon-item"></span>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Portuguesa</h6>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-sm-6 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<div class="col-9">
											<div class="d-flex align-items-center align-self-start">
												<h3 class="mb-0">R$46,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:3</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<span class="mdi mdi-arrow-top-right icon-item"></span>
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
												<h3 class="mb-0">R$46,99</h3>
												<p class="text-success ml-2 mb-0 font-weight-medium">cod:12</p>
											</div>
										</div>
										<div class="col-3">
											<div class="icon icon-box-success ">
												<span class="mdi mdi-arrow-top-right icon-item"></span>
											</div>
										</div>
									</div>
									<h6 class="text-muted font-weight-normal">Mussarela</h6>
								</div>
							</div>
						</div>
					</div>

					<div class="row ">
						<div class="col-12 grid-margin">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Status do pedido</h4>
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr>
													<th class="text-center">Whatssap</th>
													<th class="text-center">Nome Cliente</th>
													<th class="text-center">Pedido</th>
													<th class="text-center">Valor</th>
													<th class="text-center">Telefone</th>
													<th>Forma de pagamento</th>
													<th class="text-center">Data</th>
													<th class="text-center">Status</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><a href="#" class="mdi mdi-whatsapp"
														style="padding: 15px 30px"></a></td>
													<td><span class="pl-2">Francisco Jeremias Silva</span></td>
													<td class="text-center">231</td>
													<td >R$ 50,00</td>
													<td >11-952544854</td>
													<td>Cartão de crédito</td>
													<td>22/07/2022</td>
													<td>
														<div class="badge badge-outline-success">Finalizado</div>
													</td>
												</tr>
												<tr>
													<td><a href="#" class="mdi mdi-whatsapp"
														style="padding: 15px 30px"></a></td>
													<td><span class="pl-2">Butico Garcia</span></td>
													<td class="text-center">232</td>
													<td>R$ 89,99</td>
													<td>11-40874855</td>
													<td>Dinheiro</td>
													<td>25/07/2022</td>
													<td>
														<div class="badge badge-outline-warning">A Caminho</div>
													</td>
												</tr>
												<tr>
													<td><a href="#" class="mdi mdi-whatsapp"
														style="padding: 15px 30px"></a></td>
													<td><span class="pl-2">Francisco Jeremias Silva</span></td>
													<td class="text-center">233</td>
													<td>R$ 39,99</td>
													<td>11-915487574</td>
													<td>Cartão de débito</td>
													<td>26/07/2022</td>
													<td>
														<div class="badge badge-outline-danger">Em Preparo</div>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
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
											<h6 class="mb-1">Transfer to Paypal</h6>
											<p class="text-muted mb-0">07 Jan 2019, 09:12AM</p>
										</div>
										<div
											class="align-self-center flex-grow text-right text-md-center text-xl-right py-md-2 py-xl-0">
											<h6 class="font-weight-bold mb-0">$236</h6>
										</div>
									</div>
									<div
										class="bg-gray-dark d-flex d-md-block d-xl-flex flex-row py-3 px-4 px-md-3 px-xl-4 rounded mt-3">
										<div class="text-md-center text-xl-left">
											<h6 class="mb-1">Tranfer to Stripe</h6>
											<p class="text-muted mb-0">07 Jan 2019, 09:12AM</p>
										</div>
										<div
											class="align-self-center flex-grow text-right text-md-center text-xl-right py-md-2 py-xl-0">
											<h6 class="font-weight-bold mb-0">$593</h6>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-xl-8 grid-margin stretch-card">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Anotações</h4>
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
													<label class="form-check-label"> <input
														class="checkbox" type="checkbox"> Exemplo: Faltou
														ingrediente palmito para pizza portuguesa - dia 23/05 
												</div> <i class="remove mdi mdi-close-box"></i>
											</li>
											<li>
												<div class="form-check form-check-primary">
													<label class="form-check-label"> <input
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