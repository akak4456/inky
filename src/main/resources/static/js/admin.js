var adminManager = (function(){
	var upgradeToAdmin = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:'post',
			url:'/admin/upgradeToAdmin/'+sendData.upgradeId,
			success:successCallback||function(result){
				console.log(result);
				alert(result);
			},
			error:errorCallback||function(error){
				console.log(error);
				alert(error);
			}
		});
	};
	var removeFromAdmin = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:'delete',
			url:'/admin/removeFromAdmin/'+sendData.removeId,
			success:successCallback||function(result){
				console.log(result);
				alert(result);
			},
			error:errorCallback||function(error){
				console.log(error);
				alert(error);
			}
		});
	}
	return {
		upgradeToAdmin : upgradeToAdmin,
		removeFromAdmin : removeFromAdmin
		
		
	};
})();