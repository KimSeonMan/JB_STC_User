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
	<script type="text/javascript" src="/js/tscp/userSiteTemplate/carUse.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>
		<!-- body -->
		<div class="container">
			<div class="location"></div>
			<div class="contents"><!-- subContent -->
				<div class="subTitle">
				<h2>${pageTitle}</h2>
				
			</div>

			<div class="selectForm" style="display:none"><!-- style="display:none" -->
				<label for="">지역이동지원센터 선택</label>
				<input type="hidden" name="cnterCd" id="cnterCd" value="">
				<select id='SCH_CNTER_CD' class='select'>
					<option  value="NAT-1-001" >이동지원센터</option>
				</select>
			</div>
			<div id="cnttData" > <!-- style="display:none" -->
			${contentsInfo.CNTNTS_CTNT }
			</div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
</body>
</html>