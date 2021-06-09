<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String sessionId = (String) session.getAttribute("sessionId");
	String user_id = (String) session.getAttribute("user_id");
	
	HttpSession http_session = request.getSession();		
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type="text/javascript" src="/js/tscp/login/login.js"></script>
</head>
<body>
<div class="wrapper">
	<div class="loginHeader"> 
		<h1 class="loginLogo">지역이동지원센터 서비스 <span>로그인</span></h1>  
		<div class="loginText"><span>지역이동지원센터</span>에 오신것을<br />환영합니다.</div> 
	</div> 
	
	<div class="loginCont"><!-- container -->
		<div class="loginForm">
			<div class="item">
				<label class="label" for="loginid">아이디</label>
				<input type="text" class="inp" id="user_id" />
				<div class="ckbox">
					<input type="checkbox" id="autoLogin" />
					<label for="autoLogin">아이디저장</label>
				</div>
			</div>
			<div class="item">
				<label class="label" for="loginpw">비밀번호</label>
				<input type="password" class="inp" id="pwd" />
				<a href="javascript:void(0)" class="btn01" id="login">로그인</a>
			</div>
			<div class="item">
				<ul class="loginLinkList">
					<li><a href="javascript:void(0)">아이디 찾기</a></li>
					<li><a href="javascript:void(0)">비밀번호 찾기</a></li>
					<li><a href="javascript:void(0)" id="user_create">회원가입</a></li>
				</ul>
			</div>
		</div>
	</div><!-- end  container -->
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
</div>
</body>
</html>