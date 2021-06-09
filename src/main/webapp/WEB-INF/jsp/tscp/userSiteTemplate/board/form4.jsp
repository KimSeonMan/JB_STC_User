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
	<script type="text/javascript" src="/js/tscp/userSiteTemplate/form4.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container">

			<div class="location"></div>
			
			<div class="contents"><!-- subContent -->
				<div class="subTitle bg01"></div>
				<table class="sTable01">
					<colgroup>
						<col width="120" />
						<col width="" />
					</colgroup>
					<tr>
						<th>조회조건</th>
						<td >
							<select id="SEARCH_KEY" class="select">
								<option value="">선택</option>
								<option value="A">제목</option>
								<option value="B">작성자</option>
								<option value="C">내용</option>
							</select>
							<input type="text" class="inp w250 ml5" id="SEARCH_TEXT" />
						</td>
					</tr> 
					<tr>
						<th>등록일</th>
						<td>
							<input type="text" id="ST_DT" class="inp datepicker" disabled="disabled" /> 
							<input type="text" id="EN_DT" class="inp datepicker ml20" disabled="disabled" /> 
							<a href="javascript:void(0)" id="searchList" class="btn01 t01">찾기</a>
						</td>
					</tr> 
				</table>
	
				<form id="listTable">
				<input type="hidden" id="GUBN" value="${contentsInfo.CNTNTS_TYPE2_CD }" /> 
				<input type="hidden" id="NOTICE_CLS_CD" value="${contentsInfo.CNTNTS_CLS_CD }" />
					<table class="listTable">
					</table>
					<ul class="photoList"></ul>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="noticePopup" style="display:block;">
		<div class="pop"> 
			<div class="pHeader">
				<span>${fileInfoList.MENU_NAME } 상세</span>
				<a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img src="/img/btn/btn_close01.png" /></a>
			</div>
			<div class="pCont">
				<form id="noticeForm" method="post" enctype="multipart/form-data">
					<p class="etcTitle" id="val1"></p>
					<table class="sTable01 ptype" id="noticeDetail" >
						<colgroup>
							<col width="100" />
							<col width="" />
							<col width="100" />
							<col width="" />
							<col width="100" />
							<col width="" />
						</colgroup>
						<tr>
							<th>작성자</th>
							<td id="val2"></td>
							<th>작성일</th>
							<td id="val3"></td>
							<th>조회</th>
							<td id="val4"></td>
						</tr> 
						<tr> 
							<td class="cont" colspan="6">
								<div class="contImg"></div>
								<div class="contText" id="val5"></div>
							</td>
						</tr> 
					</table>
				</form>
			</div>  
		</div>
	</div>
</body>
</html>