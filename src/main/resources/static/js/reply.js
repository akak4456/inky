var replyMainAddress="";
var boardBno;
var pageNum;
function createReplyEdit(userid){
	var str = "";
	str += "<div class='panel-body rereply-edit text-center'>";
	str += "	<div>";
	str += "		<table>";
	str += "			<tr>";
	str += "				<td>"+userid+"</td>";
	str += "			</tr>";
	str += "		</table>";
	str += "	</div>";
	str += "	<div class='editor-reply' id='editor-rereply-edit'></div>";
	str += "	<div>";
	str += "		<button class='btn btn-success'>등록</button>";
	str += "		<button class='btn btn-primary'>취소</button>";
	str += "	</div>";
	str += "</div>";
	return str;
}
function showList(result,replyContent,replyPaging){
	//댓글 채우기
	var str = "";
	var replies = [];
	for(var i=0;i<result.result.content.length;i++){
	var c = result.result.content[i];
	replies.push(c.reply);
	str += "<hr>";
	str += "<div class='reply-content-main' data-idx='"+i+"' data-rno='"+c.rno+"' data-parent_rno='"+c.parent_rno+"'>";
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
	$(".reply-content-main").on("click",function(e){
		$(".rereply-edit").remove();
		//기존의 대댓글 에디트 창 삭제
		var idx = $(this).data("idx");
		var rno = $(this).data("rno");
		var parent_rno = $(this).data("parent_rno");
		console.log(idx);
		console.log(rno);
		console.log(parent_rno);
		$(this).after(createReplyEdit("user00"));
		$(".rereply-edit .btn-success").on("click",function(e){
			
		});
		$(".rereply-edit .btn-primary").on("click",function(e){
			$(".rereply-edit").remove();
		});
		ClassicEditor.create( document.querySelector( '#editor-rereply-edit' ), {
			toolbar: [ 'bold', 'italic', 'link' ]
		})
		.then(function (editor) {
			replyEditoro = editor;
		})
		.catch(function (error) {
			console.log( error );
		} );
		//alert(idx);
	})
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
	str += "	<a href='"+(result.prevPage.pageNumber+1)+"'>이전</a>";
	str += "</li>";
	}
	for(var i=0;i<result.pageList.length;i++){
	var p = result.pageList[i];
	var active = '';
	if(p.pageNumber+1==pageNum)
	active = 'active';
	str += "<li class='page-item "+active+"'>";
	str += "	<a href='"+(p.pageNumber+1)+"'>"+(p.pageNumber+1)+"</a>";
	str += "</li>";
	}
	if(result.nextPage != null){
	str += "<li class='page-item'>";
	str += "	<a href='"+(result.nextPage.pageNumber+1)+"'>다음</a>";
	str += "</li>";
	}
	replyPaging.html(str);
	$("li.page-item a").on("click",function(e){
		//댓글 페이지 번호 클릭시
		e.preventDefault();
		pageNum = $(this).attr('href');
		replyManager.getList(boardBno,{page:pageNum},replyContent,replyPaging);
	});
}
var replyManager = (function(){
	var getList =function(bno,sendData,replyContent,replyPaging){
		console.log("getListReply..."+bno);
		$.getJSON("/"+replyMainAddress+"/reply/list/"+bno,sendData,function(result){
			console.log(result);
			pageNum = result.currentPageNum;
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