var replyMainAddress="";
var boardBno;
var pageNum;
function replyInit(){
	let replyEditoro;
	ClassicEditor.create( document.querySelector( '#editor-reply-edit' ), {
		toolbar: [ 'bold', 'italic', 'link' ]
	})
	.then(function (editor) {
		replyEditoro = editor;
	})
	.catch(function (error) {
		console.log( error );
	} );
	var replyContent = $(".reply-content");
	var replyPaging = $(".pagination");
	//reply 처음 불러오기
	replyManager.getList(boardBno,{page:0},replyContent,replyPaging);
	
	$(".reply-edit .btn-success").on("click",function(e){
		//답글 작성 버튼
		var saveData = {
			replier:"user00",
			reply:replyEditoro.getData(),
			csrf:csrf,
			parent_rno:null,
			isdelete:'N'
		};
		replyManager.add(saveData,boardBno,function(result){
			alert("성공하였습니다!");
			replyManager.getList(boardBno,{page:0},replyContent,replyPaging);
			replyEditoro.setData("");
		})
	});
	$(".reply-edit .btn-primary").on("click",function(e){
		//답글 작성 버튼
		replyEditoro.setData("");
	});
}
function createReplyModifyEdit(rno){
	var str = "";
	str += "<div class='panel-body rereply-edit text-center' data-rno='"+rno+"'>";
	str += "	<div>";
	str += "		<table>";
	str += "			<tr>";
	str += "				<td>댓글수정</td>";
	str += "			</tr>";
	str += "		</table>";
	str += "	</div>";
	str += "	<div class='editor-reply' id='editor-reply-modify-edit'></div>";
	str += "	<div>";
	str += "		<button class='btn btn-success'>수정</button>";
	str += "		<button class='btn btn-primary'>취소</button>";
	str += "	</div>";
	str += "</div>";
	return str;
}
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
	//댓글창 만들기
	var str = "";
	var replies = [];
	for(var i=0;i<result.result.content.length;i++){
	var c = result.result.content[i];
	replies.push(c.reply);
	//console.log(c.parent_rno);
	str += "<hr>";
	if(c.parent_rno == null){
	str += "<div class='reply-content-main' data-idx='"+i+"' data-rno='"+c.rno+"' data-parent_rno='"+c.parent_rno+"'>";
	}else{
	str += "<div class='rereply-content-main' data-idx='"+i+"' data-rno='"+c.rno+"' data-parent_rno='"+c.parent_rno+"'>";	
	}
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
	if(c.isdelete == "N"){
	str += "<div class='reply-btns' data-replier='"+c.replier+"' data-idx='"+i+"' data-rno='"+c.rno+"' data-parent_rno='"+c.parent_rno+"'>";
	str += "	<button class='btn btn-primary'>수정</button>";
	str += "	<button class='btn btn-danger'>삭제</button>";
	str += "</div>";
	}
	}
	str += "<hr>";
	replyContent.html(str);
	for(let i=0;i<replies.length;i++){
		//댓글창에 에디트 추가
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
	//댓글창 만들기
	$(".reply-btns .btn-primary").on("click",function(e){
		//댓글수정버튼 클릭
		$(".rereply-edit").remove();
		var rno = $(this).closest("div").data("rno");
		var idx = $(this).closest("div").data("idx");
		var parent_rno = $(this).closest("div").data("parent_rno");
		$(this).closest("div").after(createReplyModifyEdit(rno));
		let rereplyEditoro;
		ClassicEditor.create( document.querySelector( '#editor-reply-modify-edit' ), {
			toolbar: [ 'bold', 'italic', 'link' ]
		})
		.then(function (editor) {
			rereplyEditoro = editor;
			rereplyEditoro.setData(replies[idx]);
		})
		.catch(function (error) {
			console.log( error );
		});	
		$(".rereply-edit .btn-success").on("click",function(e){
			var modifyData = {
				rno:rno,
				reply:rereplyEditoro.getData(),
				csrf:csrf
			};
			replyManager.modify(modifyData,boardBno,function(result){
				alert("수정하였습니다!");
				replyManager.getList(boardBno,{page:pageNum},replyContent,replyPaging);
			});
		});
		$(".rereply-edit .btn-primary").on("click",function(e){
			$(".rereply-edit").remove();
		});
	});
	$(".reply-btns .btn-danger").on("click",function(e){
		//댓글삭제버튼 클릭
		if(confirm("정말로 삭제하시겠습니까?")){
			var rno = $(this).closest("div").data("rno");
			console.log(rno);
			var deleteData = {
					rno:rno,
					csrf:csrf
			};
			replyManager.deleteReply(deleteData,function(result){
				alert("삭제하였습니다.");
				replyManager.getList(boardBno,{page:pageNum},replyContent,replyPaging);
			});
		}
	});
	//대댓글 스타일 바꾸기
	$(".rereply-content-main").css("backgroundColor","gray");
	$(".rereply-content-main").css("marginLeft","30px");
	//대댓글 스타일 바꾸기
	$(".reply-content-main").on("click",function(e){
		//대댓글을 달게 해주기
		let rereplyEditoro;
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
			var saveData = {
				replier:"user00",
				reply:rereplyEditoro.getData(),
				csrf:csrf,
				parent_rno:rno,
				isdelete:'N'
			};
			replyManager.add(saveData,boardBno,function(result){
				alert("댓글을 달았습니다!");
				if(replies.length == 10)
					//현재 페이지 번호에 댓글이 10개 이상이었으면 다음 페이지로 가라
					pageNum++;
				replyManager.getList(boardBno,{page:pageNum},replyContent,replyPaging);
			})
		});
		$(".rereply-edit .btn-primary").on("click",function(e){
			$(".rereply-edit").remove();
		});
		ClassicEditor.create( document.querySelector( '#editor-rereply-edit' ), {
			toolbar: [ 'bold', 'italic', 'link' ]
		})
		.then(function (editor) {
			rereplyEditoro = editor;
		})
		.catch(function (error) {
			console.log( error );
		} );
		//alert(idx);
	});
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
	var add = function(obj,bno,callback){
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
	var deleteReply = function(obj,callback){
		console.log(obj);
		$.ajax({
			type:'delete',
			url:"/"+replyMainAddress+"/reply/delete/"+obj.rno,
			beforeSend:function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName,obj.csrf.token);
			},
			success:callback,
			error:function(error){
				console.log(error);
			}
		});
	}
	var modify = function(obj,bno,callback){
		console.log("modify...");
		console.log(obj);
		$.ajax({
			type:'put',
			url:"/"+replyMainAddress+"/reply/modify/"+bno,
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
		add:add,
		deleteReply:deleteReply,
		modify:modify
	}
})();