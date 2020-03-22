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
	
	var blockUser = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:'post',
			url:'/admin/blockUser/'+sendData.blockId,
			success:successCallback||function(result){
				console.log(result);
				alert(result);
			},
			error:errorCallback||function(error){
				console.log(error)
			}
		});
	}
	var unblockUser = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:'post',
			url:'/admin/unblockUser/'+sendData.unblockId,
			success:successCallback||function(result){
				console.log(result);
				alert(result);
			},
			error:errorCallback||function(error){
				console.log(error)
			}
		});
	}
	var removeAllReport = function(sendData,successCallback,errorCallback){
		$.ajax({
			type:'delete',
			url:'/admin/report/deleteAll',
			success:function(result){
				alert(result);
			},
			error:function(error){
				console.log(error);
			}
		});
	}
	return {
		upgradeToAdmin : upgradeToAdmin,
		removeFromAdmin : removeFromAdmin,
		blockUser:blockUser,
		removeAllReport:removeAllReport,
		unblockUser:unblockUser
	};
})();