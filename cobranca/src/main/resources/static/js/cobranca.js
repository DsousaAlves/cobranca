//var data;
//var dataFormatada;
//	data = new Date();
//	dataFormatada = data.getDate()+"/"+data.getMonth()+"/"+data.getFullYear();
$(function() {
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({allowNegative: true, thousands:'.', decimal:','});
	
	$('.js-atualizar-status').on('click', function(event) {
		event.preventDefault();
		var button = $(event.currentTarget);
		var urlReceber = button.attr('href');
		var response = $.ajax({
			url : urlReceber,
			method : 'PUT'
		});
		
		response.done(function(e) {
			var id = button.data('id');
			$('[data-role='+id+']').html('<label class="label label-success">'+ e +'</label>');
			button.hide();
		});
		
		response.fail();
		
		
	});

	
});//carregamento da página

$('#modalExcluir').on(
		'show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);
			var idTitulo = button.data('id');
			var desc = button.data('desc');
			var modal = $(this);
			var form = modal.find('form');
			var url = form.data('url');

			if (!url.endsWith('/')) {
				url += '/';
			}
			form.attr('action', url + idTitulo);

			modal.find('.modal-body span').html(
					'Você tem certeza que deseja excluir o título <strong>'
							+ desc + '</strong>?');

});


//$('#dataVencimento').datepicker({
//	    startDate: new String(dataFormatada)
//});