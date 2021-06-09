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
	<script type="text/javascript" src="/js/tscp/userSiteTemplate/form3.js"></script>
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
				
				<fieldset>
					<span class="label">검색</span>
					<select class="select" id="SEARCH_KEY">
						<option value="">선택</option>
						<option value="A">제목</option>
						<option value="B">작성자</option>
						<option value="C">내용</option>
					</select>
					<input type="text" class="inp" id="SEARCH_TEXT">
					<button type="button" class="btn01" id="searchList">조회</button> 
				</fieldset> 
				
				<div class="boardHeader">
					<p>검색목록: 총 <span id="listCnt"></span>건</p>
					<div class="btn">
						<!-- <a href="javascript:void(0)" class="btnType01">등록</a> -->
						<!--<a href="javascript:void(0)" class="btnType02">삭제</a> -->
					</div>
				</div>
				
				<form id="listTable">
				<input type="hidden" id="GUBN" value="${contentsInfo.CNTNTS_TYPE2_CD }" /> 
				<input type="hidden" id="NOTICE_CLS_CD" value="${contentsInfo.CNTNTS_CLS_CD }" />
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="noticePopup" style="display:block">
		<div class="pop">
			<div class="pHeader">
				<span>${noticeList.MENU_NAME } 상세</span>
				<a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img src="/img/btn/btn_close01.png" /></a>
			</div>
			<div class="pCont"> 
				<form id="noticeForm" method="post" enctype="multipart/form-data">
				<div class="pActicle">
					<p class="etcTitle" id="val1"></p>
					 <table class="sTable01 ptype">
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
								<div class="contText" id="val5" style="padding-top:10px;padding-bottom:10px;"></div>
							</td>
						</tr>
						<tr>
						<th>첨부파일 다운로드</th>
						<td colspan="5">
							<div class="fc">
								<ul id="downloadUl"></ul>
							</div>
						</td>
						</tr>
					</dl> 
					</table>
				</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>