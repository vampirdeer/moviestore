<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<main>
	<div style="height:20px"></div>
	<form action="reviewer?cmd=create" method="post">
		<div class="box-body">	
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">아이디</label>
				<div class="col-sm-10">
				<input type="text" name="userid" id="userid" size="22" required
				class="form-control" maxlength='20'  placeholder="아이디 입력">										
				</div>
			</div>
			<div style="height:20px"></div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">패스워드</label>
				<div class="col-sm-10">
				<input type="password" name="pwd" id="pwd" size="22" required
				class="form-control" maxlength='20'  placeholder="패스워드 입력">										
				</div>
			</div>				
			<div style="height:20px"></div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">이름</label>
				<div class="col-sm-10">
				<input type="text" name="name" id="name" size="22" required
				class="form-control" maxlength='20'  placeholder="이름 입력">										
				</div>
			</div>		
			<div style="height:20px"></div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">이메일</label>
				<div class="col-sm-10">
				<input type="text" name="email" id="email" size="22" required
				class="form-control" maxlength='30'  placeholder="이메일 입력">										
				</div>
			</div>		
			<div style="height:20px"></div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">전화번호</label>
				<div class="col-sm-10">
				<input type="text" name="phone" id="phone" size="22" required
				class="form-control" maxlength='20'  placeholder="전화번호 입력">										
				</div>
			</div>		
			<div style="height:20px"></div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label">등급</label>
				<div class="col-sm-10">
				<input type="radio" name="grade" id="grade" value="c" >일반회원
				<input type="radio" name="grade" id="grade" value="a" >관리자	
				</div>
			</div>	
			<div style="height:20px"></div>
			<div class="form-group text-center">
				<button type="submit" class="btn btn-primary">생 성</button>
				<button type="reset" class="btn btn-danger">초 기 화</button>
			</div>
			<div style="height:20px"></div>
			<div>${message}</div>
		</div>
	</form>
</main>
<%@ include file="../include/footer.jsp" %>

