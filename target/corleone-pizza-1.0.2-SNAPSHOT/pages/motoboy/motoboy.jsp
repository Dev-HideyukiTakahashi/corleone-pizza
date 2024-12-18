<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html lang="en">

<!-- Cabe�alho da p�gina -->
<jsp:include page="../components/head.jsp"></jsp:include>

<body>

	<div class="container-scroller">

		<!-- Navegador lateral da p�gina -->
		<jsp:include page="../components/sidebar.jsp"></jsp:include>

		<div class="container-fluid page-body-wrapper">

			<!-- Navbar do cabe�alho -->
			<jsp:include page="../components/navbar.jsp"></jsp:include>


			<div class="main-panel">
				<div class="content-wrapper">

					<div class="row ">
						<div class="col-12 grid-margin">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">Entregadores</h4>
									<button class="btn-inverse-success" onclick="insertModal()"
										style="width: 15%; margin-bottom: 15px" id="btn-insert">+
										Entregador</button>
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th class="text-center">Nome</th>
													<th class="text-center">Telefone</th>
													<th>Endere�o</th>
													<th class="text-center">Excluir</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${motoboyData}" var="md">
													<tr>
														<td hidden=""><input id="id" type="hidden" value="${md.motoboyId}"/></td>
														
														<td id="name-motoboy" class="text-center text-secondary">
															<c:out value="${md.motoboyName}"></c:out>
														</td>

														<td class="text-center text-warning">
															<button class="mdi mdi-lead-pencil"
																onclick="modalView('${md.motoboyId},phone');"
																style="margin-right: 10px"></button> <c:out
																value="${md.motoboyPhone}">
															</c:out>
														</td>

														<td class="text-secondary"><button
																class="mdi mdi-lead-pencil"
																onclick="modalView('${md.motoboyId},adress');"
																style="margin-right: 10px"></button>
															<c:out value="${md.motoboyAdress}"></c:out></td>
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

					<jsp:include page="../components/footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<jsp:include page="../components/javascript.jsp" />
	</div>
</body>


<!-- Modal insert Modal -->
<div class="modal fade" id="insert-modal" tabindex="-1"
	aria-labelledby="insert-modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 style="margin-left: 3px" class="modal-title text-success">Adicionar
					novo sabor</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close" onclick="closeModal();"></button>
			</div>
			<div class="modal-body">
				<label class="text-secondary" style="margin-top: 15px">Nome</label>
				<input class="form-control text-light" autocomplete="off"
					id="insertModal-name"></input> <label class="text-secondary"
					style="margin-top: 15px">Endere�o</label> <input
					class="form-control text-light" autocomplete="off"
					id="insertModal-adress"></input> <label class="text-secondary"
					style="margin-top: 15px">Telefone</label> <input
					class="form-control text-light" autocomplete="off" type="number"
					id="insertModal-phone"></input>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
				<button type="button" class="btn btn-primary"
					onclick="insertData();" id="btn2-modal">Salvar</button>
			</div>
		</div>
	</div>
</div>


<!-- Modal update Modal -->
<div class="modal fade" id="update-modal" tabindex="-1"
	aria-labelledby="update-modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 style="margin-left: 3px" class="modal-title text-success"
					id="updateModal-name"></h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close" onclick="closeModal();"></button>
			</div>
			<div class="modal-body">
				<input id="updateModal-input" class="form-control text-light"
					value="" autocomplete="off"></input> <input id="type" type="hidden" />
				<input id="id-update" type="hidden" />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal" onclick="closeModal();">Cancelar</button>
				<button type="button" class="btn btn-primary"
					onclick="updateData();" id="btn-modal">Salvar</button>
			</div>
		</div>
	</div>
</div>

<!-- Script para atualizar dados do motoboy -->
<script type="text/javascript">
	function updateData() {
		
		let value = $('#updateModal-input').val();
		let type  = $('#type').val();
		let id  = $('#id-update').val();

		$.ajax
		({
			method : "GET",
			url    : <%=request.getContextPath()%>/ + "motoboy?action=update",
			data   : 
			{
				type 	      : type,
				value		  : value,
				id			  : id,
			},
		    success : function() 
		    {
	    		$('#update-modal').modal('hide');
	    		window.location.href = "<%=request.getContextPath()%>/motoboy?action=listAll"
			},
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro desconhecido ao atualizar dados")
		});
	}
	
	</script>

<!-- Script para mostrar modal insert -->
<script type="text/javascript">
		function insertModal() {
			$('#insert-modal').modal('show');
			$('#updateModal-name').text('Novo entregador');
		}
		
		function insertData() {
			
			let nameInsert      = $('#insertModal-name').val();
			let phoneInsert     = $('#insertModal-phone').val();
			let adressInsert    = $('#insertModal-adress').val();
			let urlAction 		= '<%=request.getContextPath()%>/motoboy';
				
			$.ajax
			({
				method : "POST",
				url    : urlAction,
				data   : 
				{
					name 	      : nameInsert,
					phone		  : phoneInsert,
					adress		  : adressInsert,
				},
			    success : function() 
			    {
		    		$('#insert-modal').modal('hide');
		    		
		    		// Recarrega a p�gina
		    		location.reload();
				},
			}).fail(function(xhr, status, errorThrown) {
				alert("Erro desconhecido ao inserir dados")
			});
			
		}
	</script>


<!-- Script para mostrar modal update -->
<script>
	function modalView(obj) {
	
		let array = obj.split(',');
		let id    = array[0];
		let value = array[1];
		
		if(value === "adress"){
			
			$('#update-modal').modal('show');
			$('#updateModal-name').text('Atualizar Endere�o');
			$('#type').val('adress');
			$('#id-update').val(id);
		}
		else if(value === "phone"){
			
			$('#update-modal').modal('show');
			$('#updateModal-name').text('Atualizar Telefone');
			$('#type').val('phone');
			$('#id-update').val(id);
		}
	}
	</script>

<!-- Script para fechar a janela modal -->
<script type="text/javascript">
	function closeModal() {
		$('#update-modal').modal('hide');
		$('#insert-modal').modal('hide');
	}
	</script>


<!-- Deletar motoboy -->
<script type="text/javascript">
function deleteMotoboy(id) {
	
	let response = confirm("Deseja excluir?")
	
	if(response){
		window.location.href = <%=request.getContextPath()%>/+ "motoboy?action=del&name=" + id;
		}

	}
</script>
</html>