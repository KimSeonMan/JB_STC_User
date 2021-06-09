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
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type="text/javascript" src="/js/tscp/mngr/siteMng.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container">
			<div class="lnb"></div><!-- lnb -->

			<div class="subContent"><!-- subContent -->
				<ul class="location"></ul>
				<h3 class="subTitle"></h3>
				<table class="sTable01">
					<tr>
						<th>사이트명</th>
						<td>
							<input type="text" id="SEARCH_TEXT" class="inp"/>
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">관련사이트 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="siteMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="siteMngDelete" class="btn02 CRUDBtn">삭제</a>
					</div>
				</div>
				<form id="listTable">
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="siteMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">관련사이트 관리 등록</p>
			
			<div class="cont">
				<form id="siteMngForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="SITE_NO" id="SITE_NO" value="1"/>
				<input type="hidden" name="CNTER_CD" id="CNTER_CD" value=""/>
				 
					<table class="formTable t01 mt20" >
						<tr>
							
							<th>사이트순번<mark>*</mark></th>
							<td colspan="3">
								<input type="text" name="SITE_SEQ" id="SITE_SEQ" class="inp" maxlength="3" oninput="javascript:maxLengthCheck(this);"/>
							</td>
						</tr>
						<tr>
							<th>사이트명<mark>*</mark></th>
							<td>
								<input type="text" name="SITE_NAME" id="SITE_NAME" class="inp" maxlength="15"/>
							</td>
							<th>링크URL<mark>*</mark></th>
							<td>
								<input type="text" name="LINKED_URL" id="LINKED_URL" class="inp" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<th>첨부파일<mark>*</mark></th>
							<td colspan="3">
								<div><a href="javascript:fileAdd();" id="newAttachFile" class="btn02">파일첨부 </a></div>
								<div id="attachNameZone">
									<ul id="attachNameUl"></ul>
								</div>
								<div id="attachFileZone" style="display:none;">
									<ul id="attachFileUl"></ul>
								</div>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
					<a href="javascript:void(0)" id="siteMngAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="siteMngUpdate" class="btn03 CRUDBtn">수정</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>