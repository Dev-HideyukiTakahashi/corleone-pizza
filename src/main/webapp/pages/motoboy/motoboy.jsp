<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html lang="pt-BR">

<!-- Cabe�alho da p�gina -->
<jsp:include page="../head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="../sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
			<jsp:include page="../navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">

					<div class="row ">
						<div class="col-12 grid-margin">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Entregadores</h4>
									<button class="btn-inverse-success"
										style="width: 15%; margin-bottom: 15px" id="btn-insert">+
										Entregador</button>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th class="text-center">Nome</th>
													<th class="text-center">Telefone</th>
													<th class="text-center">Endere�o</th>
													<th class="text-center">Excluir</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${motoboyData}" var="md">
													<tr>
														<td id="name-motoboy" class="text-center text-secondary">
														<c:out value="${md.motoboyName}"></c:out></td>
														
														<td class="text-center text-warning">
														<c:out
																value="${md.motoboyPhone}">
																</c:out><button class="mdi mdi-lead-pencil" onclick="modelView('${md.motoboyName},phone');" style="margin-left: 10px"></button>
																</td>
																
														<td class="text-center text-secondary"><c:out
																value="${md.motoboyAdress}"></c:out><button class="mdi mdi-lead-pencil" onclick="modelView('${md.motoboyName},adress');" style="margin-left: 10px"></button></td>
														<td class='py-1 text-center'><a
															onclick="deleteMotoboy('${md.motoboyName}')"><img
																src='<%=request.getContextPath()%>/assets/images/file-icons/delete.png'
																alt='Excluir' /></a></td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</div>
								</div>
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

	<!-- Modal update Modal -->
	<div class="modal fade" id="update-modal" tabindex="-1" aria-labelledby="update-modal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5  style="margin-left:3px" class="modal-title text-success" id="updateModal-name"></h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal();"></button>
	      </div>
	      <div class="modal-body">
	        <input id="updateModal-input" class="form-control text-light" value="" autocomplete="off"></input>
	        <input id="type" type="hidden"/>
	        <input id="name" type="hidden"/>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
	        <button type="button" class="btn btn-primary" onclick="updateData();" id="btn-modal">Salvar</button>
	      </div>
	    </div>
	  </div>
	</div>


	<script type="text/javascript">
	function updateData() {
		
		let value = $('#updateModal-input').val();
		let type  = $('#type').val();
		let name  = $('#name').val();

		window.location.href = "<%=request.getContextPath()%>/motoboy?action=update&value=" +value+"&type="+type+"&name="+name ;
	}
	
	</script>


	<!-- Script para mostrar modal -->
	<script>
	function modelView(obj) {
	
		let array = obj.split(',');
		let name  = array[0];
		let value = array[1];
		
		if(value === "adress"){
			
			$('#update-modal').modal('show');
			$('#updateModal-name').text('Atualizar Endere�o');
			$('#type').val('adress');
			$('#name').val(name);
		}
		else if(value === "phone"){
			
			$('#update-modal').modal('show');
			$('#updateModal-name').text('Atualizar Telefone');
			$('#type').val('phone');
			$('#name').val(name);
		}
		
		
	}
	</script>

	<!-- Script para fechar a janela modal -->
	<script type="text/javascript">
	function closeModal() {
		$('#update-modal').modal('hide');
	}
	</script>




<!-- Deletar motoboy -->
<script type="text/javascript">
function deleteMotoboy(name) {
	
	let response = confirm("Deseja excluir?")
	
	if(response){
		window.location.href = "<%=request.getContextPath()%>/motoboy?action=del&name=" + name;
	}
	
	
}
</script>
</html>