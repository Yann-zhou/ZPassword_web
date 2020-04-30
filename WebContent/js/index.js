var json;
$(document).ready(function(){
	var url, username, password;
	$.post("ajax.jsp",
			{
			    signal:"8",
			    message:'["'+id+'"]'
			},
			function(data,status){
				json=JSON.parse(data);
				if(json.message=='QuerySuccess'){
					for(var i=0;i<json.content[1].length;i++){
						var insert='<li class="list-group-item list-index-item '
						if(i==0){
							insert+='list-index-selected';
							addList(json.content[1][i]);
						}
						insert+='" id="'+json.content[1][i][0]+'">'+
						'<image class="list-index-icon" src="http://statics.dnspod.cn/proxy_favicon/_/favicon?domain='+json.content[1][i][1]+'" onerror="this.src=\'img/error.png\'"></div>'+
						//'<div class="list-index-id">'+json.content[1][i][0]+'</div>'+
						'<div class="list-index-url">'+json.content[1][i][1]+'</div>'+
						'<div class="list-index-username">'+json.content[1][i][2]+'</div>'+
						'</li>'
						$('#list-index').append(insert);
					}
				}
			}
	);

	$('#list-index').on('click', '.list-index-item', function(){
		for(var i=0;i<json.content[1].length;i++){
			if(json.content[1][i][0]==$(this).attr('id')){
				addList(json.content[1][i]);
				$('.list-index-item').removeClass('list-index-selected');
				$(this).addClass('list-index-selected');
				url=$('#list-url-string').attr('value');
				username=$('#list-username-string').attr('value');
				password=$('#list-password-string').attr('value');
				$('#list-content-btn-cancel').trigger("click");
				$('.close').trigger("click");
			}
		}
	});
	
	
	
	//搜索框
	$('#list-search').on('input', function() {
		console.log($('#list-search').val());
		if($('#list-search').val()!=''){
			$('#list-index').empty();
			for(var i=0;i<json.content[1].length;i++){
				for(var j=0;j<4;j++){
					if(json.content[1][i][j].toString().indexOf($('#list-search').val())!=-1){
						var insert='<li class="list-group-item list-index-item '+
						'" id="'+json.content[1][i][0]+'">'+
						'<image class="list-index-icon" src="http://statics.dnspod.cn/proxy_favicon/_/favicon?domain='+json.content[1][i][1]+'" onerror="this.src=\'img/error.png\'"></div>'+
						'<div class="list-index-url">'+json.content[1][i][1]+'</div>'+
						'<div class="list-index-username">'+json.content[1][i][2]+'</div>'+
						'</li>'
						$('#list-index').append(insert);
						break;
					}
				}
			}
		}else{
			$('#list-index').empty();
			for(var i=0;i<json.content[1].length;i++){
				var insert='<li class="list-group-item list-index-item '+
				'" id="'+json.content[1][i][0]+'">'+
				'<image class="list-index-icon" src="http://statics.dnspod.cn/proxy_favicon/_/favicon?domain='+json.content[1][i][1]+'" onerror="this.src=\'img/error.png\'"></div>'+
				'<div class="list-index-url">'+json.content[1][i][1]+'</div>'+
				'<div class="list-index-username">'+json.content[1][i][2]+'</div>'+
				'</li>'
				$('#list-index').append(insert);
			}
		}
	});
	
	$('#user-btn-delete').click(function(){
		$('.close').trigger("click");
		$('#user-delete-box').show();
	});
	
	$('#user-btn-password').click(function(){
		$('.close').trigger("click");
		$('#user-pass-box').show();
	});

	$('#list-content-btn-refresh').click(function(){
		location.reload();
	});
	
	$('#list-content-btn-add').click(function(){
		$('.close').trigger("click");
		$('#record-add-box').show();
	});
	
	$('#list-content-btn-edit').click(function(){
		$('#list-content-btn-refresh').hide();
		$('#list-content-btn-add').hide();
		$('#list-content-btn-edit').hide();
		$('#list-content-btn-cancel').show();
		$('#list-content-btn-save').show();
		$('#list-content-btn-delete').show();
		$('.close').trigger("click");
		
		url=$('#list-url-string').attr('value');
		username=$('#list-username-string').attr('value');
		password=$('#list-password-string').attr('value');
		$('#list-url-string').html('<input id="edit-input-url" type="text" value="'+url+'">');
		$('#list-username-string').html('<input id="edit-input-username" type="text" value="'+username+'">');
		$('#list-password-string').html('<input id="edit-input-password" type="text" value="'+password+'">');
	});
	
	$('#list-content-btn-cancel').click(function(){
		$('#list-content-btn-refresh').show();
		$('#list-content-btn-add').show();
		$('#list-content-btn-edit').show();
		$('#list-content-btn-cancel').hide();
		$('#list-content-btn-save').hide();
		$('#list-content-btn-delete').hide();
		$('.close').trigger("click");
		$('#list-url-string').html(url);
		$('#list-username-string').html(username+'<input type="button" class="copy-btn" value="复制" onclick="copyContent(1)"/>');
		$('#list-password-string').html(password+'<input type="button" class="copy-btn" value="复制" onclick="copyContent(2)"/>');
	});
	
	$('#list-content-btn-save').click(function(){
		$('#list-content-btn-refresh').show();
		$('#list-content-btn-add').show();
		$('#list-content-btn-edit').show();
		$('#list-content-btn-cancel').hide();
		$('#list-content-btn-save').hide();
		$('#list-content-btn-delete').hide();
		$('.close').trigger("click");
		
		$.post("ajax.jsp",
				{
				    signal:"7",
				    message:'[["'+id+'", "'+url+'", "'+username+'", "'+password+'"],["'+$('#edit-input-url').val()+'", "'+$('#edit-input-username').val()+'", "'+$('#edit-input-password').val()+'"]]'
				},
				function(data,status){
					json=JSON.parse(data);
					if(json.message=='UpdateRecordSuccess'){
						showMessage('alert-success', '修改成功，即将刷新...');
						setTimeout(function(){location.reload();}, 1500);
					}
				}
		);
	});
	
	$('#list-content-btn-delete').click(function(){
		$('.close').trigger("click");
		$.post("ajax.jsp",
				{
				    signal:"6",
				    message:'["'+id+'", "'+url+'", "'+username+'"]'
				},
				function(data,status){
					json=JSON.parse(data);
					if(json.message=='DeleteRecordSuccess'){
						showMessage('alert-success', '删除成功，即将刷新...');
						setTimeout(function(){location.reload();}, 1500);
					}
				}
		);
	});
	
	$('.close').click(function(){
		$('#record-add-box').hide();
		$('#user-delete-box').hide();
		$('#user-pass-box').hide();
		$('#record-add-box-url').val('');
		$('#record-add-box-username').val('');
		$('#record-add-box-password').val('');
	});
	
	$('#record-add-box-submit').click(function(){
		$.post("ajax.jsp",
				{
				    signal:"5",
				    message:'["'+id+'", "'+$('#record-add-box-url').val()+'", "'+$('#record-add-box-username').val()+'", "'+$('#record-add-box-password').val()+'"]'
				},
				function(data,status){
					json=JSON.parse(data);
					if(json.message=='InsertRecordSuccess'){
						showMessage('alert-success', '添加成功，即将刷新...');
						setTimeout(function(){location.reload();}, 1500);
					}
				}
		);
	});
	
	$('#user-delete-submit').click(function(){
		$.post("ajax.jsp",
				{
				    signal:"3",
				    message:'["", "'+id+'"]'
				},
				function(data,status){
					json=JSON.parse(data);
					if(json.message=='DeleteSuccess'){
						showMessage('alert-success', '删除成功！请重新登陆');
						setTimeout(function(){$('#user-btn-logout').trigger("click");}, 1500);
					}
				}
		);
	});
	
	$('#user-btn-logout').click(function(){
		$.post("logout.jsp",
				{
					
				},
				function(data,status){
						showMessage("alert-success", "已退出，即将刷新...");
						setTimeout(function(){location.reload();}, 1500);
				}
		);
	});
	
});

function addList(list){
	var div = $('#list-content');
	div.empty();
	var list_content = '<form>'+
	'<div class="list-header">'+
	'<image class="list-icon" src="http://statics.dnspod.cn/proxy_favicon/_/favicon?domain='+list[1]+'" onerror="this.src=\'img/error.png\'">'+
	'<div class="list-url" id="list-url-string" value="'+list[1]+'">'+list[1]+'</div>'+
	'</div>'+
	'<div class="list-body">'+
	'<div class="list-username"><p class="list-label">用户名：</p><div class="list-info" id="list-username-string" value="'+list[2]+'">'+list[2]+'<input type="button" class="copy-btn" value="复制" onclick="copyContent(1)"/></div></div>'+
	'<div class="list-password"><p class="list-label">密码：</p><div class="list-info" id="list-password-string" value="'+list[3]+'">'+list[3]+'<input type="button" class="copy-btn" value="复制" onclick="copyContent(2)"/></div></div>'+
	'</div>'+
	'</form>';
	div.append(list_content);
}

function copyContent(s){
	const input = document.createElement('input');
    input.setAttribute('readonly', 'readonly');
    if(s==1)
    	input.setAttribute('value', $('#list-username-string').attr('value'));
    else
    	input.setAttribute('value', $('#list-password-string').attr('value'));
    document.body.appendChild(input);
	input.setSelectionRange(0, 9999);
	if (document.execCommand('Copy')) {
		document.execCommand('Copy');
	}
    document.body.removeChild(input);
}

function showMessage(type, text){
	var messageBox = $('#message');
	messageBox.show()
	var createMessage = document.createElement('div');
	createMessage.setAttribute('class', 'alert '+type);
	createMessage.setAttribute('role', 'alert');
	createMessage.innerHTML=text;
	messageBox.append(createMessage);
	setTimeout(function(){messageBox.fadeOut(500)}, 1000);
	setTimeout(function(){createMessage.remove()}, 1500);
}