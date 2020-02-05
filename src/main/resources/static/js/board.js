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
	
	return {
		add : add
	};
})();