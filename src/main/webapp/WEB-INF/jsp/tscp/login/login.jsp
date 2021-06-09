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
<div class="wrapper"><!-- start : wrapper -->

	<header>
		<!-- Top Include -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>
	</header>

 
	
	<div class="container"><!-- start : container --> 
		<div class="lnb"></div><!-- lnb -->
		<div class="location">
			<ul>
				<li class="home">홈</li>
				<li>
					<a href="javascript:void(0)">Account</a> 
					<ul>
						<%if(user_id == null){%>
						<li><a href="/view/memberShipAgree.do">회원가입</a> </li> 
						<li><a href="javascript:loginProcess();">로그인</a> </li>
						<li><a href="/view/login/find.do">아이디/비밀번호찾기</a> </li>
						<%}else{ %>
						<li><a href="/view/myPage.do">마이페이지</a> </li>
						<%}%>
					</ul>
				</li>
				<li class="last">
					<a href="javascript:void(0)">로그인</a>  
				</li>
			</ul>
		</div>


		<div class="contents">
			<div class="subTitle">
				<h2>로그인</h2>
				<p>회원이 아니신 분은 회원으로 가입하신 후 이용해주세요.</p>
			</div>

			<div class="loginBox">
				<h3>전북이동지원센터<span>에 오신 것을 환영합니다.</span></h3>
				<input type="text" id="user_id"/>
				<input type="password" id="pwd"/>
				<div class="loginChk">
					<input type="checkbox" id="autoLogin" />
					<label for="">아이디 저장</label>
				</div>
				<a href="javascript:void(0)" class="btn01" id="login">로그인</a>
				<ul>
					<li><a href="/view/memberShipAgree.do">회원가입</a></li>
					<li><a href="/view/login/find.do">아이디/비밀번호 찾기</a></li>
				</ul>
				<p class="etc">상담 및 문의. ${cnterInfo.telno} 063-227-0002</p>
			</div>
		</div>





	</div><!-- end : container -->

	<!-- footer// -->
    <footer id="footer">
    	<!-- Bottom Include -->
		<jsp:include page="/view/common/includeFooter"></jsp:include>
    </footer>

</div><!-- end : wrapper-->



</body>
</html> 