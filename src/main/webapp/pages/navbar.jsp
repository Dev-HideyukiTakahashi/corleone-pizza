<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<nav class="navbar p-0 fixed-top d-flex flex-row">
	<div
		class="navbar-brand-wrapper d-flex d-lg-none align-items-center justify-content-center">
		<a class="navbar-brand brand-logo-mini"
			href="<%=request.getContextPath()%>/pages/main.jsp"><img
			src="<%=request.getContextPath()%>/assets/images/logo-mini.svg"
			alt="logo" /></a>
	</div>
	<div class="navbar-menu-wrapper flex-grow d-flex align-items-stretch">
		<button class="navbar-toggler navbar-toggler align-self-center"
			type="button" data-toggle="minimize">
			<span class="mdi mdi-menu"></span>
		</button>
		<ul class="navbar-nav w-100">
			<li class="nav-item w-100">
				<form class="nav-link mt-2 mt-md-0 d-none d-lg-flex search">
					<input type="text" class="form-control"
						placeholder="Procurar produtos">
				</form>
			</li>
		</ul>
		<ul class="navbar-nav navbar-nav-right">
			<li class="nav-item dropdown d-none d-lg-block"><a
				class="nav-link btn btn-success create-new-button"
				id="createbuttonDropdown" data-toggle="dropdown"
				aria-expanded="false" href="#">+ Novo Pedido</a>
				<div
					class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
					aria-labelledby="createbuttonDropdown">
					<h6 class="p-3 mb-0">Novo Pedido</h6>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item" href="<%=request.getContextPath()%>/pages/clients/register.jsp">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle" >
								<i class="mdi mdi-account-plus text-primary"></i>
							</div>
						</div>

						<div class="preview-item-content">
							<p class="preview-subject ellipsis mb-1">Cliente Novo</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-account-box text-info"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject ellipsis mb-1">Cliente Registrado</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-lead-pencil text-danger"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject ellipsis mb-1">Feedback</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<p class="p-3 mb-0 text-center"></p>
				</div></li>





			<li class="nav-item nav-settings d-none d-lg-block"><a
				class="nav-link" href="#"> <i class="mdi mdi-pizza"></i>
			</a></li>

			<li class="nav-item dropdown border-left"><a
				class="nav-link count-indicator dropdown-toggle"
				id="messageDropdown" href="#" data-toggle="dropdown"
				aria-expanded="false"> <i class="mdi mdi-email"></i> <span
					class="count bg-success"></span>
			</a>
				<div
					class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
					aria-labelledby="messageDropdown">
					<h6 class="p-3 mb-0">Contato Suporte</h6>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<img
								src="<%=request.getContextPath()%>/assets/images/faces/suporte.png"
								alt="suporte" class="rounded-circle profile-pic">
						</div>
						<div class="preview-item-content">
							<p class="preview-subject ellipsis mb-1">Suporte respondeu
								sua mensagem</p>
							<p class="text-muted mb-0">1 Minuto atrás</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<img
								src="<%=request.getContextPath()%>/assets/images/faces/suporte.png"
								alt="image" class="rounded-circle profile-pic">
						</div>
						<div class="preview-item-content">
							<p class="preview-subject ellipsis mb-1">Suporte respondeu
								sua mensagem</p>
							<p class="text-muted mb-0">5 Horas atrás</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<div class="dropdown-divider"></div>
					<p class="p-3 mb-0 text-center">2 novas mensagens</p>
				</div></li>
			<li class="nav-item dropdown border-left"><a
				class="nav-link count-indicator dropdown-toggle"
				id="notificationDropdown" href="#" data-toggle="dropdown"> <i
					class="mdi mdi-bell"></i> <span class="count bg-danger"></span>
			</a>
				<div
					class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
					aria-labelledby="notificationDropdown">
					<h6 class="p-3 mb-0">Notificações</h6>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-calendar text-success"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject mb-1">Lembrete</p>
							<p class="text-muted ellipsis mb-0">Renovar o estoque de
								refrigerantes em 1 dia</p>
						</div>
					</a>

					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-calendar text-success"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject mb-1">Lembrete</p>
							<p class="text-muted ellipsis mb-0">Entregador não vem em 1
								dia</p>
						</div>
					</a>

					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-calendar text-success"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject mb-1">Lembrete</p>
							<p class="text-muted ellipsis mb-0">Programar promoção de
								pizza feriado</p>
						</div>
					</a>


					<div class="dropdown-divider"></div>
					<p class="p-3 mb-0 text-center"></p>
				</div></li>
			<li class="nav-item dropdown"><a class="nav-link"
				id="profileDropdown" href="#" data-toggle="dropdown">
					<div class="navbar-profile">
						<img class="img-xs rounded-circle"
							src="<%=request.getContextPath()%>/assets/images/faces/face.jpg"
							alt="">
						<p class="mb-0 d-none d-sm-block navbar-profile-name"><%=session.getAttribute("adminLogin") %></p>
						<i class="mdi mdi-menu-down d-none d-sm-block"></i>
					</div>
			</a>
				<div
					class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
					aria-labelledby="profileDropdown">
					<h6 class="p-3 mb-0">Profile</h6>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-settings text-success"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject mb-1">Configurações</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item preview-item" href="<%=request.getContextPath()%>/login?action=logout">
						<div class="preview-thumbnail">
							<div class="preview-icon bg-dark rounded-circle">
								<i class="mdi mdi-logout text-danger"></i>
							</div>
						</div>
						<div class="preview-item-content">
							<p class="preview-subject mb-1">Log out</p>
						</div>
					</a>
					<div class="dropdown-divider"></div>
					<p class="p-3 mb-0 text-center"></p>
				</div></li>
		</ul>
		<button
			class="navbar-toggler navbar-toggler-right d-lg-none align-self-center"
			type="button" data-toggle="offcanvas">
			<span class="mdi mdi-format-line-spacing"></span>
		</button>
	</div>
</nav>