window.openModal = function(baseUrl, modalUri, modalId)
{
	$.ajax({
		type: 'GET',
		url : baseUrl + modalUri,
		success : function(data) {
			$('#modalWrapper').html(data);
			$('#' + modalId).modal();
			$('#' + modalId).modal('show');
		}
	});
}

function closeFormModal(modal) {
	var modal = $(modal)
	$(modal).modal('hide');
	$('.modal-backdrop').remove();

	$('#modalWrapper').empty();
	$(modal).modal('show');
}

var redirect = function(contentId)
{
	return function(data, textStatus, jqXHR)
	{
		var uri = jqXHR.getResponseHeader("Location");
		window.location.replace(uri);
	};
}

var displayModal = function(contentId)
{
	return function(data, textStatus, jqXHR)
	{
		if (jqXHR && jqXHR.status == 201)
		{
			return; // handled by statusCode:201 fn.
		}
		
		if (data)
		{
			$('#modalWrapper').html(data);
			closeFormModal($('#' + contentId + 'Modal'));
		} 
		else
		{
			// ???
		}
	};
}

var displayError = function(contentId)
{
	return function(jqXHR, textStatus, errorThrown)
	{
		
	};
};

window.installAsyncModalForm = function(contentId, options)
{
	options = options || {};
	
	var modalId = '#' + contentId + 'Modal';
	var formId = '#' + contentId + 'Form';
	
	var asyncFormHandler2 = function(event)
	{
		if(typeof options.submit == 'function')
		{
			var ret = options.submit.apply(this, arguments);
			
			if(ret === false) 
				return false;
			
			if(event.isDefaultPrevented()) 
				return;
			
			delete options.submit;
		}
		
		event.preventDefault();
		
		var $modal = $(modalId);
		var $form = $modal.find(formId);
		
		var ajaxOptions = $.extend({
			type: 'POST',
			url: $form.attr('action'),
			data: $form.serialize(),
			headers: {'X-No-Redirect': true},
			success: displayModal(contentId),
			statusCode: {
				//"201 Created"  "202 Accepted"  "204 No Content"
				201: redirect(contentId)
			},
			error: displayError(contentId)
		}, options);
		
		$.ajax(ajaxOptions);
	};
	
	jQuery(function($) {
		var $form = $(modalId).find(formId);
		$form.on('submit', asyncFormHandler2);
		
		$(modalId).on('hide.bs.modal', function (e) {
			$('#modalWrapper').empty();
		})
	});
}