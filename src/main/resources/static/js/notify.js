var notifyManager = (function(){
	var add = function(obj, callback,errorCallback){
		$.ajax({
			type:'post',
			url:'/admin/notify/write',
			data:JSON.stringify(obj),
			contentType:"application/json",
			success:callback ||function(result){
				alert("공지를 올렸습니다!");
				window.location.href = "/admin/main";
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	};
	var modify = function(obj,callback,errorCallback){
		console.log("modify...");
		console.log("boardData...");
		console.log(obj);
		$.ajax({
			type:'put',
			url:'/'+boardMainAddress+'/modify/'+obj.bno,
			data:JSON.stringify(obj),
			contentType:"application/json",
			success:callback||function(result){
				alert(result);
				var url = "/"+boardMainAddress+"/getOne/"+bno;
				console.log(url);
				formObj.find("[name='dest']").val(loc);
				formObj.attr("action",url);
				formObj.submit();
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	}
	var getFileForm = function(editoro){
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
		return fileForm;
	}
	var deleteBoard = function(obj,callback,errorCallback){
		console.log("deleteBoard...");
		$.ajax({
			type:"delete",
			url:'/'+boardMainAddress+'/delete/'+obj.bno,
			data:obj.userid,
			success:callback||function(result){
				alert(result);
				var url = "/"+boardMainAddress+"/list";
				console.log(url);
				formObj.find("[name='dest']").val(loc);
				formObj.attr("action",url);
				formObj.submit();
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	}
	var upRecommendcnt = function(obj,callback,errorCallback){
		console.log(boardMainAddress);
		console.log("upRecommend.....");
		$.ajax({
			type:'post',
			url:'/'+boardMainAddress+'/recommend/up',
			data:JSON.stringify(obj),
			contentType:"application/json",
			success:callback||function(result){
				alert(result);
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	}
	var downRecommendcnt = function(obj,callback,errorCallback){
		console.log("downRecommend.....");
		$.ajax({
			type:'post',
			url:'/'+boardMainAddress+'/recommend/down',
			data:JSON.stringify(obj),
			contentType:"application/json",
			success:callback||function(result){
				alert(result);
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	}
	return {
		add : add,
		modify: modify,
		deleteBoard:deleteBoard,
		getFileForm: getFileForm,
		upRecommendcnt:upRecommendcnt,
		downRecommendcnt:downRecommendcnt
	};
})();