var memberManager = (function(){
	var profileFile = null;
	var findpw = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/findpw',
			data : JSON.stringify(sendData),
			contentType : "application/json",
			success : successCallback||function(result) {
				alert(result);
				location.href="/login";
			},
			error : errorCallback||function(error) {
				console.log(error);
				alert(error.responseText);
			}
		});
	};
	var memberDelete = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:"delete",
			url:'/member/delete/'+sendData.uid,
			success:successCallback||function(result){
				alert(result);
				location.href="/login";
			},
			error:errorCallback||function(error){
				alert(error.responseText);
				console.log(error);
			}
		});
	};
	var getFileForm = function(){
		var fForm = [];
		if (profileFile != null) {
			var firstIdx = profileFile.indexOf('/',1);
			var lastIdx = profileFile.lastIndexOf('/');
			var uploadPath = profileFile.substring(firstIdx + 1, lastIdx);
			var uploadfileName = profileFile.substring(lastIdx + 1);
			fForm.push({
				uploadPath : uploadPath,
				uploadFileName : uploadfileName
			});
		}
		return fForm;
	};
	var setInitProfile = function(uploads){
		if(uploads != null&&uploads.length > 0){
			profileFile = "/fileget/"+uploads[0].uploadPath+"/"+uploads[0].uploadFileName;
			$(".profile-img").html("<img src='"+profileFile+"'/><button class='btn btn-primary cancel-btn'><i class='fas fa-times'></i></button>");
			$(".cancel-btn").on("click",function(e) {
				profileFile = null;
				$(".profile-img img").remove();
				$(this).remove();
			});
		}
	}
	var fileUpload = function(sendData,successCallback,errorCallback){
		console.log(sendData);
		$.ajax({
			url : "/profileUpload",
			type : "POST",
			data : new FormData(sendData.formObj[0]),
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			success : successCallback||function(data) {
					console.log(data);
					profileFile = data;
					$(".profile-img").html("<img src='"+data+"'/><button class='btn btn-primary cancel-btn'><i class='fas fa-times'></i></button>");
					$(".cancel-btn").on("click",function(e) {
						profileFile = null;
						$(".profile-img img").remove();
						$(this).remove();
					});
			},
			error : errorCallback||function(error) {
				console.log(error);
			}
		});
	};
	var chkid = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/checkid',
			data : sendData.uid,
			contentType : "text/plain",
			success : successCallback||function(result) {
				alert(result);
			},
			error : errorCallback||function(result) {
				alert(result.responseText);
			}
		});
	};
	var chkemail = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/sendEmail',
			data : sendData.uemail,
			contentType : "text/plain",
			success : successCallback||function(result) {
				alert(result);
			},
			error : errorCallback||function(result) {
				alert("이메일을 보내지 못했습니다. 이메일 주소가 맞지 않거나 이미 존재하는 이메일일 수 있습니다.");
				$("#chkcodeForm").remove();
			}
		});
	};
	var chkcode = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/checkEmail',
			data : JSON.stringify(sendData),
			contentType : "application/json",
			success : successCallback||function(result) {
				alert(result);
				$("#chkcodeForm").remove();
			},
			error : errorCallback||function(result) {
				alert(result.responseText);
			}
		});
	};
	var join = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/join',
			data : JSON.stringify(sendData),
			contentType : "application/json",
			success : successCallback||function(result) {
				alert(result);
				location.href="/login";
			},
			error : errorCallback||function(result) {
			alert(result.responseText);
			}
		});
	};
	var modify = function(sendData,successCallback,errorCallback){
		$.ajax({
			type : 'post',
			url : '/member/modify',
			data : JSON.stringify(sendData),
			contentType : "application/json",
			success : successCallback||function(result) {
				alert(result);
				location.href="/login";
			},
			error : errorCallback||function(result) {
			alert(result);
			}
		});
	};
	return {
		findpw:findpw,
		memberDelete:memberDelete,
		fileUpload:fileUpload,
		setInitProfile:setInitProfile,
		chkid:chkid,
		chkemail:chkemail,
		chkcode:chkcode,
		join:join,
		modify:modify,
		getFileForm:getFileForm
	};
})();