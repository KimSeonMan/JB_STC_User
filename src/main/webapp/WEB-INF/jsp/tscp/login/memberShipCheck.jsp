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
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/js/tscp/login/memberShip.js"></script>
</head>
<body>
<div class="wrapper">
	<!-- header // -->
	<jsp:include page="/view/common/includeHeader"></jsp:include>

	<!-- body -->
	<div class="container">
		<div class="lnb"></div><!-- lnb -->
		<div class="location">
			<ul>
				<li class="home">홈</li>
				<li>
					<a href="javascript:void(0)">Account</a> 
					<ul>
						<%if(user_id == null){%>""
						<li><a href="/view/memberShipAgree.do">회원가입</a> </li> 
						<li><a href="javascript:loginProcess();">로그인</a> </li>
						<li><a href="/view/login/find.do">아이디/비밀번호찾기</a> </li>
						<%}else{ %>
						<li><a href="/view/myPage.do">마이페이지</a> </li>
						<%}%>
					</ul>
				</li>
				<li class="last">
					<a href="javascript:void(0)">회원가입</a>  
				</li>
			</ul>
		</div>
		
		<div class="contents">
			<div class="subTitle">
				<h2>회원가입</h2>
			</div>

			<p class="joinTxt topLine">아래 내용을 확인하신 후 "동의합니다"에 체크해주세요.</p>
			<div class="agreeBox" id="agreeBox" style="white-space: pre-wrap">
			<br />${CN}
			</div>
			
			<div class="joinEtc"> 
				<div class="joinChk">
					<input type="checkbox" id="agreeCk03" />
					<label for="">위 내용에 동의합니다. (필수)</label>
				</div>
			</div>
				
			<div class="btnBox">
				<a href="javascript:void(0)" class="btnType02" id="centerHaveChk">다음</a>
			</div>
			
		</div>
		

	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
</div>

</body>
</html>	