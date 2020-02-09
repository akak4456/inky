var boardManager = (function(){
	var add = function(obj, callback){
		console.log("add...");
		console.log("boardData...");
		console.log(obj);
		$.ajax({
			type:'post',
			url:'/community/write',
			data:JSON.stringify(obj),
			beforeSend:function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName,obj.csrf.token);
			},
			contentType:"application/json",
			success:callback,
			error:function(error){
				console.log(error);
			}
		});
	};
	var modify = function(obj,callback){
		console.log("modify...");
		console.log("boardData...");
		console.log(obj);
		$.ajax({
			type:'put',
			url:'/community/modify/'+obj.bno,
			data:JSON.stringify(obj),
			beforeSend:function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName,obj.csrf.token);
			},
			contentType:"application/json",
			success:callback,
			error:function(error){
				console.log(error);
			}
		});
	}
	var submitData = function(editoro){
		const editorData = editoro.getData();
		//console.log(editorData);
		var images = $("figure img");
		console.log(images.length);
		var fileForm = [];
		for(var i=0;i<images.length;i++){
			var imageSrc = $(images.get(i)).attr('src');
			//console.log(imageSrc);
			var firstIdx = imageSrc.indexOf('/',1);
			var lastIdx = imageSrc.lastIndexOf('/');
			//console.log(firstIdx+" "+lastIdx);
			var uploadPath = imageSrc.substring(firstIdx+1,lastIdx);
			var uploadFileName = imageSrc.substring(lastIdx+1);
			//console.log(uploadPath+" "+uploadFileName);
			var fForm = {"uploadPath":uploadPath,"uploadFileName":uploadFileName};
			fileForm.push(fForm);
		}
		var saveData = {
			userid:$("input[name='writer']").val(),
			kind:$("input[name='kind']").val(),
			title:$("input[name='title']").val(),
			content:editorData,
			fileForm:fileForm,
			csrf:csrf
		};
		return saveData;
	}
	var deleteBoard = function(obj,callback){
		console.log("deleteBoard...");
		$.ajax({
			type:"delete",
			url:"/community/delete/"+obj.bno,
			beforeSend:function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName,obj.csrf.token);
			},
			success:callback
		});
	}
	return {
		add : add,
		modify: modify,
		deleteBoard:deleteBoard,
		submitData: submitData
	};
})();