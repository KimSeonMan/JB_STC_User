<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type="text/javascript" src="/js/tscp/mngr/smsHistoryMng.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.table2excel.js"></script>
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> 
	<title>이동지원센터</title>
</head>
<body>
<div class="wrapper">
	<!-- header // -->
	<jsp:include page="/view/common/includeHeader"></jsp:include>
	<div class="container"><!-- start : container --> 
		<div class="lnb"></div><!-- lnb -->

		<div class="acticle">
			<ul class="location"></ul>
			<h3 class="subTitle"></h3>

			<table class="sTable01">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="120" />
					<col width="" />
				</colgroup> 
				<tr>
					<th>구분</th>
					<td>
						<select class="select" id="searchSMS_TYPE">
							<option value="">선택</option>
						</select>
					</td> 
					<th>이름</th>
					<td><input type="text" class="inp" id="searchNM"/></td> 
				</tr> 
				<tr>
					<th>검색일</th>
					<td colspan="3">
						<input type="text" class="inp datepicker inp-float" id="searchStartDate"/>
						<span class="etxt">~</span>
						<input type="text" class="inp datepicker inp-float" id="searchEndDate"/>
						<a href="javascript:void(0)" class="btn01" id="searchBtn">찾기</a>
					</td> 
				</tr>
			</table>

			<p class="tit01">SMS발송이력 목록</p>

			<div class="boardHeader">
				<div class="fl"></div>
				<div class="fr btn">
					<a href="javascript:void(0)" id="excelDown" class="btn02 CRUDBtn">Excel저장</a>
				</div>
			</div>
			<form id="listTable">
				<input type="hidden" id="TOTAL_CNT" />
				<table class="listTable"></table>
			</form>
			<div class="paging"></div>

		</div>
		 

	</div><!-- end : container -->
	 
	<div class="footer"><!-- start : footer -->  
		<jsp:include page="/WEB-INF/jsp/common/includeFooter.jsp"></jsp:include>
	</div><!-- end : footer -->  
	
</div><!-- end : wrapper-->
<!-- 	엑셀 다운로드용 -->
	<div id="excelDiv" style="display:block;">
		<table id="excelTable"></table>
	</div>
 
 

</body>
</html> 