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
	<script type="text/javascript" src="/js/tscp/login/memberShip.js"></script>
</head>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container">
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
						<a href="javascript:void(0)">회원가입</a>  
					</li>
				</ul>
			</div>
	
			<div class="contents"><!-- contents -->
				<div class="subTitle bg01">
					<h2>회원가입</h2>
				</div>
			
				<div class="joinStep">
					<ul>
						<li class="s01">
							<p class="t01">STEP.01</p>
							<p class="t02">회원가입</p>
						</li>
						<li class="s02">
							<p class="t01">STEP.02</p>
							<p class="t02">센터선택</p>
						</li>
						<li class="s03">
							<p class="t01">STEP.03</p>
							<p class="t02">이용약관</p>
						</li>
						<li class="s04">
							<p class="t01">STEP.04</p>
							<p class="t02">정보입력</p>
						</li>
						<li class="s05">
							<p class="t01">STEP.05</p>
							<p class="t02">승인요청</p>
						</li>
					</ul>
				</div>
				
				<div class="selectForm">
					<label for="">소속된 지역이동지원센터 선택</label>
					<select id='CNTER_CD' class='select'>
					</select>
				</div> 
				
				<div class="joinSubj">
					<h3>회원약관</h3>
					<div class="joinChk">
						<input type="checkbox" id="allCheck" />
						<label for="">전체약관에 동의합니다.</label>
					</div>
				</div>
				<div class="agreeBox" id="agreeBox" style="white-space: pre-wrap">
					<br />
				</div>
	
				<div class="joinEtc"> 
					<div class="joinChk">
						<input type="checkbox" id="agreeCk01" />
						<label for="">위 서비스 약관에 동의합니다. (필수)</label>
					</div>
				</div>
	
				<div class="joinSubj">
					<h3>개인정보수집 및 이용목적에 관한 동의</h3>
				</div>

				<div class="agreeBox" id="agreeBox2" style="white-space: pre-wrap">
					<br />
				</div>
	
				<div class="joinEtc"> 
					<div class="joinChk">
						<input type="checkbox" id="agreeCk02" />
						<label for="">위 서비스 약관에 동의합니다. (필수)</label>
					</div>
				</div>

				<div class="joinSubj">
					<h3>민감정보 수집 동의</h3>
				</div>

				<div class="agreeBox" id="agreeBox3" style="white-space: pre-wrap">
					<br />
				</div>

				<div class="joinEtc">
					<div class="joinChk">
						<input type="checkbox" id="agreeCk03" />
						<label for="">위 서비스 약관에 동의합니다. (필수)</label>
					</div>
				</div>

				<div class="joinSubj">
					<h3>개인정보 제공 동의</h3>
				</div>

				<div class="agreeBox" id="agreeBox4" style="white-space: pre-wrap">
					<br />
				</div>

				<div class="joinEtc">
					<div class="joinChk">
						<input type="checkbox" id="agreeCk04" />
						<label for="">위 서비스 약관에 동의합니다. (필수)</label>
					</div>
				</div>

				<div class="joinSubj">
					<h3>개인정보 제3자 제공 동의</h3>
				</div>

				<div class="agreeBox" id="agreeBox5" style="white-space: pre-wrap">
					<br />
				</div>

				<div class="joinEtc">
					<div class="joinChk">
						<input type="checkbox" id="agreeCk05" />
						<label for="">위 서비스 약관에 동의합니다. (필수)</label>
					</div>
				</div>
	
				<div class="btnBox">
					<a href="javascript:void(0)" class="btnType01" id="agreeCancle">취소</a>
					<a href="javascript:void(0)" class="btnType02" id="agreeChk">다음</a>
				</div>	
			</div><!-- end contents -->
		</div>
		
		<!-- footer// -->
		<jsp:include page="/view/common/includeFooter"></jsp:include>
	</div>
</body>
</html>	