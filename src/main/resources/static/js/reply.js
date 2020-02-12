var replyMainAddress="";
function showList(result,replyContent,replyPaging){
	//댓글 채우기
	var str = "";
	var replies = [];
	for(var i=0;i<result.result.content.length;i++){
	var c = result.result.content[i];
	replies.push(c.reply);
	str += "<hr>";
	str += "<div>";
	str += "	<div class='replier text-left'>";
	str += "		<table>";
	str += "			<tr>";
	str += "				<td>";
	str += "					<span>"+c.replier+"</span>";
	str += "				</td>";
	str += "			</tr>";
	str += "			<tr>";
	str += "				<td>";
	str += "					"+c.replydate;
	str += "				</td>";
	str += "			</tr>";
	str += "		</table>";
	str += "	</div>";
	str += "	<div class='editor-reply' id='editor-reply"+i+"'></div>";
	str += "</div>";
	}
	str += "<hr>";
	replyContent.html(str);
	for(let i=0;i<replies.length;i++){
		//console.log(replies[i]);
		ClassicEditor
	        .create( document.querySelector( '#editor-reply'+i ) ,{
	        	toolbar:[]
	        })
	        .catch( error => {
	            console.error( error );
	        } )
			.then(function(editor){
				editor.isReadOnly = true;
				editor.setData(replies[i]);
				//editor.data.set(replies[i]);
			});
	}
	//페이지 번호 매기기
	str = "";
	if(result.prevPage != null){
	str += "<li class='page-item'>";
	str += "	<a href='#'>이전</a>";
	str += "</li>";
	}
	for(var i=0;i<result.pageList.length;i++){
	var p = result.pageList[i];
	str += "<li class='page-item'>";
	str += "	<a href='#'>"+(p.pageNumber+1)+"</a>";
	str += "</li>";
	}
	if(result.nextPage != null){
	str += "<li class='page-item'>";
	str += "	<a href='#'>다음</a>";
	str += "</li>";
	}
	replyPaging.html(str);
}
var replyManager = (function(){
	var getList =function(bno,sendData,replyContent,replyPaging){
		console.log("getListReply..."+bno);
		$.getJSON("/"+replyMainAddress+"/reply/list/"+bno,sendData,function(result){
			console.log(result);
			showList(result,replyContent,replyPaging);
		})
	};
	var add = function(user,parent_rno,bno,editoro,callback){
		var obj = {
			replier:user,
			reply:editoro.getData(),
			csrf:csrf,
			parent_rno:parent_rno
		};
		console.log("add...");
		console.log(obj);
		$.ajax({
			type:'post',
			url:"/"+replyMainAddress+"/reply/write/"+bno,
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
	return {
		getList:getList,
		add:add
	}
})();