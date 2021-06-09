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
<script type="text/javascript" src="/js/tscp/login/find.js"></script>       
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
					<a href="javascript:void(0)">아이디/비밀번호찾기</a>  
				</li>
			</ul>
		</div>


		<div class="contents">
			<div class="subTitle">
				<h2>아이디/비밀번호 찾기</h2>
				<p>아이디 또는 비밀번호를 회원가입 시 입력한 휴대폰으로 받아보실 수 있습니다.</p>
			</div>

			<div class="findBox">
				<div class="findForm fl">
					<h3>회원 아이디를 잊으셨나요?</h3>
					<div class="form">
						<input type="text" id="findIdName" placeholder="성명" />
						<input type="text" id="findIdBirthDay" maxlength=8 placeholder="생년월일(ex.19900101)" />
					</div>
					<div class="btn">
						<a href="javascript:void(0)" class="btn01" id="cancle1">취소</a> 
						<a href="javascript:void(0)" class="btn02" id="findeId">아이디 찾기</a> 
					</div>
				</div>

				<div class="findForm fr">
					<h3>회원 비밀번호를 잊으셨나요?</h3>
					<div class="form">
						<input type="text" id="findPwId"  placeholder="아이디" />
						<input type="text" id="findPwName" placeholder="성명" />
						<input type="text" id="findPwBirthDay" maxlength=8 placeholder="생년월일(ex.19900101)" />
					</div>
					<div class="btn">
						<a href="javascript:void(0)" class="btn01" id="cancle2">취소</a> 
						<a href="javascript:void(0)" class="btn02" id="findPw">비밀번호 찾기</a> 
					</div>
				</div>
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