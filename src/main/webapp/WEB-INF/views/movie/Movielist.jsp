<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<style>
	#pagediv,#searchdiv{
		display:flex;
		justify-content: center;
	}
	#top-button{
		display:flex;
		justify-content: right;
		padding:10px;
	}
</style>
<main>
	<h2>도서 목록</h2>
	<div id="top-button">
		<c:if test="${sessionScope.mvo.grade=='a'}">
			<a href="movie?cmd=new"><button type="button" class="btn btn-primary">영화등록</button></a>
		</c:if>
	</div>
	<table class="table table-sm table-bordered">
		<tr>
			<th>순서</th>
			<th>영화명</th>
			<th>감독</th>
			<th>주연</th>
			<th>가격</th>
			<th>등록일자</th>
		</tr>
		<c:if test="${list==null}">
			<tr>
				<td colspan="6">검색된 영화가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach items="${list}" var="movie" varStatus="sts">
			<tr>
				<td>${sts.count}</td>
				<td><a href="movie?cmd=view&mno=${movie.mno}&page=${pVo.page}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}">${movie.title}</a></td>
				<td>${movie.director}</td>
				<td>${movie.actor}</td>
				<td><fmt:formatNumber value="${movie.price}" type="currency" currencySymbol="\\"> </fmt:formatNumber>  </td>
				<td>${movie.regdate}</td>
			</tr>	
		</c:forEach>
	 </table>
	 <div id="pagediv">
		 <!-- 페이지 -->	 
	     <nav aria-label="Standard pagination example">
	          <ul class="pagination">
		         <c:if test="${pVo.prev}">
		            <li class="page-item">
		              <a class="page-link" href="movie?cmd=list&page=${pVo.beginPage-1}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}" aria-label="Previous">
		                <span aria-hidden="true">&laquo;</span>
		              </a>
		            </li>   
	            </c:if>        
	           <c:forEach begin="${pVo.beginPage}" end="${pVo.endPage}" var="i">
			 		<c:choose>
			 			<c:when test="${i!=pVo.page}"><li class="page-item"><a class="page-link" href="movie?cmd=list&page=${i}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}">${i}</a></li></c:when>
			 			<c:otherwise> <li class="page-item"><a class="page-link" style="font-weigth:bold;color:black">${i}</a></li></c:otherwise>	 		
			 		</c:choose>		 			 	
		 		</c:forEach> 
		 		<c:if test="${pVo.next}">
		           <li class="page-item">
		              <a class="page-link" href="movie?cmd=list&page=${pVo.endPage+1}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}" aria-label="Next">
		                <span aria-hidden="true">&raquo;</span>
		              </a>
		            </li>
	            </c:if>
	          </ul>
	    </nav><!-- paging end -->
    </div>
        <!--  검색 -->
    <div id="searchdiv">	 	
		<form action="movie?cmd=list" method="post">
	        <select name="searchtype" id="searchtype">
	            <option value="title" checked>영화명</option>
	            <option value="director">감독명</option>
	            <option value="actor">주연</option>
	        </select>
	        <input type="text" size="20" name="searchword" id="searchword" >
	        <button onclick="return searchFun()">검 색</button> &nbsp;	        
	    </form>		

 	</div>		
 </main>
<script type="text/javascript">
	function searchFun() {
		//id searchword에 값을 가져온다
		let searchword=document.querySelector("#searchword");
		//값 trim 수행 빈것이면 alert 포커스 searchword return false
		if(searchword.value.trim()===""){
			//alert("검색어를 입력하세요.");
			searchword.value="";
			//searchword.focus();
			//return false;
			
		}
		//검색어가 있으면 return true
		return true;
	}
</script>
<%@ include file="../include/footer.jsp" %>