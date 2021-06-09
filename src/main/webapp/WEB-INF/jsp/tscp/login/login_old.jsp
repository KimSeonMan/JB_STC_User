<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> 
<!-- script type="text/javascript" src="/publishCss/js/ui.js"></script -->    
<!-- script type="text/javascript" src="/publishCss/js/common.js"></script -->   

</head> 
<body>       

<div class="loginBox">
	<div class="loginForm">
		<h1><span>이동지원센터<br>Mobility support center service</span></h1>
		<div class="item">
			<label for="">아이디</label>
			<input type="text" id="user_id" />
		</div>
		<div class="item">
			<label for="">비밀번호</label>
			<input type="password" id="pwd" />
		</div>
		<a href="javascript:void(0)" class="btnLogin" id="login">로그인</a>
		<ul class="linkList">
			<li>
				<input type="checkbox" id="autoLogin" />
				<label for="">아이디저장</label>
			</li>
			<li><a href="/view/login/find.do">아이디/비밀번호 찾기</a></li>
			
		</ul>
	</div>

</div>

 
 
  
</body>
</html> 