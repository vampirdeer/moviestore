<%@page import="com.ezen.movie.MovieVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>     
<link rel="stylesheet" href="${pageContext.request.contextPath}/star-rating/css/star-rating.min.css" media="all" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/star-rating/themes/krajee-svg/theme.min.css" media="all" type="text/css"/>
<script src="${pageContext.request.contextPath}/star-rating/js/star-rating.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/star-rating/themes/krajee-svg/theme.min.js" type="text/javascript"></script>
<style>
	.btn_rud{
		display:flex;
		gap:5px;
		justify-content: center;
	}
	th{
		width:100px;	
	}
	td{
		text-align: left;
	}
</style>
<main>
<h2>영화 상세</h2>
<form action="movie?cmd=edit" method="post"  enctype="multipart/form-data" id="uploadForm" name="uploadForm" >
	<input type="hidden" value="${vo.mno}" name="mno" id="mno">
	<table class="table table-sm table-bordered">
		<tr>
			<th>영화제목</th>
			<td class="disp" >${vo.title}</td>
			<td class="edit" style="display:none;">
				<input type="text" size="120" maxlength="50" name="title" id="title" 
			    value="${vo.title}" placeholder="영화명입력" required>
			</td>
		</tr>
        <tr>
			<th>감독</th>
			<td  class="disp" >${vo.director}</td>
			<td class="edit" style="display:none;">
				<input type="text" size="120"  maxlength="30" name="director" 
				value="${vo.director}" id="director" placeholder="감독명입력">
			</td>
		</tr>
        <tr>
			<th>주연</th>
			<td class="disp" >${vo.actor}</td>
			<td class="edit" style="display:none;">
			 <input type="text" size="120"  maxlength="30" name="actor" 
			 value="${vo.actor}" id="actor" placeholder="주연명입력">
			</td>
		</tr>
        <tr>
			<th>가격</th>
			<td class="disp" ><fmt:formatNumber value="${vo.price}" type="currency" currencySymbol="\\"> </fmt:formatNumber></td>
			<td class="edit" style="display:none;">
				<input type="text" size="120"  maxlength="7" name="price" id="price" 
				  value="${vo.price}" onkeydown="inputNum(this)" placeholder="가격입력" required>
			</td>
		</tr>
        <tr>
			<th>영화내용</th>
			<td class="disp" >
			<% 
				MovieVO vo=(MovieVO)request.getAttribute("vo");
				if(vo!=null){
					String content=vo.getContent();
					if(content!=null)
						out.write(content.replaceAll("\r\n", "<br>"));
				}
			%>
			</td>
			<td class="edit" style="display:none;">
				<textarea name="content" id="content" cols="119" rows="10" 
					maxlength="1000">${vo.content}</textarea>
			</td>
		</tr>
		<tr>
			<th>영화 이미지</th>
			<td class="disp" >
				<c:if test="${vo.saveFilename!=null}">
					<img src="movie?cmd=imgDown&upload=${vo.savePath}&saveFname=${vo.saveFilename}&originFname=${vo.srcFilename}" alt="" height="300px">
				</c:if>				
			</td>
			<td class="edit" style="display:none;">
					<div>
						<c:if test="${vo.saveFilename!=null}">
							기존 파일명 : ${vo.srcFilename}
						</c:if>
					</div><br>
					<div class="form-group row">
						<label for="file" class="col-sm-2 col-form-label">파일첨부</label>
						<div class="col-sm-10">
							<input type="file" name="file" id="file">
							<small class="text-muted">(파일크기 : 2MB / 이미지 파일만 가능)</small>
							<small id="file" class="text-info"></small>
						</div>
					</div>						
				</td>
		</tr>
		<tr>
			<th>평균 별점</th>
			<td> 
				<input id="avgscore" name="avgscore" value="${avgScore}" class="rating rating-loading" data-size="sm" readonly="readonly"> &nbsp;&nbsp;&nbsp; 평가자 인원수 : <span id="span-cnt">${cnt}</span> 
			</td>
		</tr>
	</table>
	<!-- 별점 저장 시작 -->
	<c:if test="${sessionScope.mvo!=null}">
	<div>
	<label for="input-1" class="control-label">별점 저장하기</label>	
<table>
	<tr>
		<td width="300px">
			<input id="score" name="score" class="rating rating-loading" data-size="sm" data-min="0" data-max="5" data-step="0.5" required="required">
		</td>
		<td>
			평가글 남기기 : <input type="text" maxlength="100" id="cmt" name="cmt" size="50" required="required">
			<button type="button" onclick="saveStar()" class="btn btn-secondary">저장하기</button>		
			</td>
	</tr>
</table>
	</div>
	</c:if>
	<!-- 별점 저장 끝 -->
	<br>
	<div class="btn_rud">
		<button type="button" id="btnList" onclick="location.href='movie?cmd=list&page=${page}&searchword=${searchword}&searchtype=${searchtype}'" class="btn btn-success" >영화목록</button>
		<c:if test="${sessionScope.mvo.grade=='a'}">
			<button type="button" id="btnEdit" onclick="movieEdit()" class="btn btn-warning" >영화수정</button>
	 		<button type="button" id="btnDelete" onclick="movieDelete()" class="btn btn-danger" >영화삭제</button> 
	 		<button type="submit" id="btnSave" onclick="movieSave()" class="btn btn-primary" style="display:none;">영화저장</button> 
	 		<button type="reset" id="btnCancle" onclick="movieCancle()" class="btn btn-info" style="display:none;">수정취소</button>
 		</c:if> 
    </div>
	</form>
	<br>
	<input type="hidden" name="page" id="page" value="0">
	<table id="tbl_star">
		
	</table>
	<button type="button" id="btn_next" style="display:none" onclick="getStar()">더보기</button>
</main>
<script type="text/javascript">
/*=============================================
* ready fun 호출
*=============================================*/
$(document).ready(function(){
	$("#page").val(0);
	getStar();
});

/*=============================================
* 스타레이팅 초기화
*=============================================*/
function newStar(){
	console.log("newStar");
	// loding 중인 별점을 보여주는 작업
	let ratingStar=$(".rating-loading");
	if(ratingStar.length){//별점보여주기가 1개 이상 있으면
		ratingStar.removeClass("rating-loading").addClass("rating-loading").rating();
	}
	
	//재생성시 크기가 중간 사이즈 고정되는 버그로 작은사이즈로 일괄 변경 추가
	$(".rating-container").removeClass("rating-md rating-sm");
	$(".rating-container").addClass("rating-sm");
}
/*=============================================
*	댓글 가져오기 수행 ajax : doAjax(url, param, callback)
*	- 보내는 데이터 : mno, page
*	- 받아오는 데이터 : json(더보기 버튼 활성화 여부,출력할 데이터(배열))	
*=============================================*/
function getStar(){
		let url="movie?cmd=slist";
		let param={"mno":$("#mno").val(),
				   "page":$("#page").val()*1+1
				   };
		//증가한 페이지를 적용
		$("#page").val($("#page").val()*1+1);
		console.log(param);
		doAjax(url, param, getStarAfter);
}
/*=============================================
*	댓글 가져오기 수행 후 해야 할 일
*	- 받아오는 데이터 : json를 table 태그 내에 출력	
*=============================================*/
function getStarAfter(data){
	console.log("getStarAfter");	
	if(data=="err"){
 		// 표시할 자료 없음
 	}else{
	 		console.log(data);	 
	 		//data 배열에 있는 값을 tbl_star 에 html 태그로 조립해서 출력 
	 		let starList=data.arr;
	 		console.log(starList);
	 		let html="";
	 		for(let vo of starList){//js foreach
	 			html+='<tr>';
	 			html+='<td>';
	 			html+='<input id="score" name="score" value='+vo.score+' class="rating rating-loading" data-size="sm" readonly="readonly">';
	 			html+='</td>';
	 			html+='<td>'+vo.id+' 님 : '+vo.cmt+'</td>';
	 			html+='</tr>';	 			
	 		}//for
	 		$("#tbl_star").append(html);
	 		//let next=data.next;//true, false
	 		//console.log(next);
	 		if(data.next){//더보기 버튼을 보여주기
	 			$("#btn_next").css("display","block");
	 		}else $("#btn_next").css("display","none");
	 		// loding 중인 별점을 보여주는 작업
	 		newStar();
 	}
}

/*===================================================
Ajax 별점과 commemt  저장
common.js doAjaxHtml(url, param, callback) 호출
===================================================*/
function saveStar(){
	let cmt=$("#cmt").val().trim();
	if(cmt==""){
		alert("평가글을 입력하세요");
		$("#cmt").focus();
	}
	let url="movie?cmd=ssave";//서블릿 매핑 주소
	let param={"id":"${sessionScope.mvo.id}",
			   "mno":document.getElementById("mno").value,
			   "score":$("#score").val(),
			   "cmt":cmt
			   };
	console.log(param);
	doAjaxHtml(url, param, saveStarAfter);
}
/*===================================================
Ajax 별점과 commemt  저장 완료 후 수행할 함수
callback에 의해서 호출
===================================================*/
function saveStarAfter(data){
	console.log("saveStarAfter");
	console.log(data);
	let retData=JSON.parse(data);//string->json data변환
	// 가져온 데이터(수정된 평균값,개수) 설정
	//$("#avgscore").val(retData.avgScore);
	//$("#avgscore").rating("reset");
	$("#avgscore").rating("destroy");
	$("#avgscore").val(retData.avgScore);
	$("#avgscore").rating("create");
	//document.getElementById("avgscore").value=retData.avgScore;
	//$("#span-cnt").html(data.cnt);
	let spanCnt=document.getElementById("span-cnt");
	console.log("data.cnt="+retData.cnt);
	spanCnt.innerHTML=retData.cnt;
	// 별점목록을 다시 1페이지를 보여준다. 내가 등록한 것이 제일 위에 있다.
	// 테이블에 현재 보여주는 별목록 제거
	$("#tbl_star").html("");
	$("#page").val(0);

	getStar();
	// 페이지의 등록된 값 초기화
	//$("#score").rating("reset");
	$("#score").rating("destroy");
	$("#score").val(0);
	$("#score").rating("create");
	
	$("#cmt").val("");
	$("#cmt").focus();
	
	newStar();
}

	//$(".disp") : 제이쿼리
/*=============================================
* 영화수정버튼은 클릭했을때
*=============================================*/	
	function movieEdit(){
		$(".disp").css("display","none");
		$(".edit").css("display","block");
		//버튼
		$("#btnEdit").css("display","none");
		$("#btnDelete").css("display","none");
		$("#btnSave").css("display","block");
		$("#btnCancle").css("display","block");
	
					
	}
	//영화 삭제
	function movieDelete(){
		if(confirm("영화삭제를 수행 하시겠습니까?")){
			location.href="movie?cmd=del&mno=${vo.mno}";
		}
	}
	//영화저장
	function movieSave(){
		//document.querySelector("#uploadForm")// 폼태그의 요소가져오기 
		document.querySelector("#uploadForm").submit();
	}
	function movieCancle(){
		$(".disp").css("display","block");
		$(".edit").css("display","none");
		//버튼
		$("#btnEdit").css("display","block");
		$("#btnDelete").css("display","block");
		$("#btnSave").css("display","none");
		$("#btnCancle").css("display","none");
	}
</script>
<%@ include file="../include/footer.jsp" %>