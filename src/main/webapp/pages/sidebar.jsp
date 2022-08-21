<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<nav class="sidebar sidebar-offcanvas" id="sidebar">
	<div
		class="sidebar-brand-wrapper d-none d-lg-flex align-items-center justify-content-center fixed-top">
		<a class="sidebar-brand brand-logo" href="<%=request.getContextPath()%>/pages/main.jsp"><img
			src="<%=request.getContextPath()%>/assets/images/logo.png" alt="logo" /></a>
		<a class="sidebar-brand brand-logo-mini" href="<%=request.getContextPath()%>/pages/main.jsp"><img
			src="<%=request.getContextPath()%>/assets/images/logo-mini.svg"
			alt="logo" /></a>
	</div>
	<ul class="nav">
		<li class="nav-item profile">
			<div class="profile-desc">
				<div class="profile-pic">
					<div class="count-indicator">
						<img class="img-xs rounded-circle "
							src="<%=request.getContextPath()%>/assets/images/faces/face.jpg"
							alt=""><span class="count bg-success"></span>
					</div>
					<div class="profile-name">
						<h5 class="mb-0 font-weight-normal">Don Corleone</h5>
						<span>Administrador</span>
					</div>
				</div>
			</div>
		</li>
		<li class="nav-item nav-category"><span class="nav-link">M
				E N U</span></li>
		<li class="nav-item menu-items"><a class="nav-link"
			href="index.html"> <span class="menu-icon"> <i
					class="mdi mdi-speedometer"></i>
			</span> <span class="menu-title">Dashboard</span>
		</a></li>

		<li class="nav-item menu-items"><a class="nav-link"
			data-toggle="collapse" href="#ui-basic" aria-expanded="false"
			aria-controls="ui-basic"> <span class="menu-icon"> <i
					class="mdi mdi-account-multiple"></i>
			</span> <span class="menu-title">Clientes</span> <i class="menu-arrow"></i>
		</a>
			<div class="collapse" id="ui-basic">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/pages/clientes/cadastrar.jsp">Cadastrar</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Atualizar</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Buscar</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Deletar</a></li>
				</ul>
			</div></li>

		<li class="nav-item menu-items"><a class="nav-link"
			data-toggle="collapse" href="#ui-basic2" aria-expanded="false"
			aria-controls="ui-basic2"> <span class="menu-icon"> <i
					class="mdi mdi-pizza"></i>
			</span> <span class="menu-title">Pizza</span><i class="menu-arrow"></i>
		</a>
			<div class="collapse" id="ui-basic2">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/buttons.html">Cardápio</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Incluir Pizza</a></li>
				</ul>
			</div></li>

		<li class="nav-item menu-items"><a class="nav-link"
			data-toggle="collapse" href="#ui-basic3" aria-expanded="false"
			aria-controls="ui-basic3"> <span class="menu-icon"> <i
					class="mdi mdi-cup"></i>
			</span> <span class="menu-title">Bebidas</span><i class="menu-arrow"></i>
		</a>
			<div class="collapse" id="ui-basic3">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/buttons.html">Refrigerantes</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Sucos</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Vinhos</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages/ui-features/dropdowns.html">Incluir Bebida</a></li>
				</ul>
			</div></li>
		<li class="nav-item menu-items"><a class="nav-link"
			href="pages/tables/basic-table.html"> <span class="menu-icon">
					<i class="mdi mdi-book-open-page-variant"></i>
			</span> <span class="menu-title">Pedidos</span>
		</a></li>
		<li class="nav-item menu-items"><a class="nav-link"
			href="pages/charts/chartjs.html"> <span class="menu-icon">
					<i class="mdi mdi-motorbike"></i>
			</span> <span class="menu-title">Entregadores</span>
		</a></li>
		<li class="nav-item menu-items"><a class="nav-link"
			href="pages/charts/chartjs.html"> <span class="menu-icon">
					<i class="mdi mdi-calendar"></i>
			</span> <span class="menu-title">Calendário</span>
		</a></li>
		<li class="nav-item menu-items"><a class="nav-link"
			href="pages/charts/chartjs.html"> <span class="menu-icon">
					<i class="mdi mdi-lead-pencil"></i>
			</span> <span class="menu-title">Feedback</span>
		</a></li>
	</ul>
</nav>