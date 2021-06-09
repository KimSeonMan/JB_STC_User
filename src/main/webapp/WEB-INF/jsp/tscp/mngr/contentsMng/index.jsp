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
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<script type="text/javascript" src="/js/tscp/mngr/contentsMng.js"></script>
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
						<th>유형</th>
						<td>
							<select id="CNTNTS_TYPE2_CD" class="select" >
								<option value="">선택</option>
							</select>
						</td>
						<th>사용여부</th>
						<td>
							<select id="USE_YN" class="select" >
								<option value="">선택</option>
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>메뉴명</th>
						<td>
							<input type="text" id="MENU_NAME" class="inp"/>
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">정보컨텐츠 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="contentsMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="contentsMngDelete" class="btn02 CRUDBtn">삭제</a>
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
	
	<div class="dialog" id="contentsMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">정보컨텐츠 관리 등록</p>
			
			<div class="cont">
				<form id="contentsMngForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="CNTNTS_NO2" id="CNTNTS_NO2"/>
					<table class="formTable2 t01 mt20" >
						<tr>
							<th>메뉴명<mark>*</mark></th>
							<td id="area3"></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>컨텐츠유형<mark>*</mark></th>
							<td colspan="3">
								<select name="CNTNTS_TYPE1_CD2" id="CNTNTS_TYPE1_CD2" class="select w100" onchange="javascript:changeType1(this.value);"></select>&nbsp;&nbsp;
								<select name="CNTNTS_TYPE2_CD2" id="CNTNTS_TYPE2_CD2" class="select w150" onchange="javascript:changeGubn('CNTNTS_CLS_CD2',this.value,'CNTNTS_CLS_CD2');"></select>
								<select name="CNTNTS_CLS_CD2" id="CNTNTS_CLS_CD2" class="select w150" ></select>
							</td>
						</tr>
						<tr>
							<th>파일첨부여부<mark>*</mark></th>
							<td>
								<select name="FILE_YN2" id="FILE_YN2" class="select" >
									<option value="N" selected>N</option>
									<option value="Y">Y</option>
								</select>
							</td>
							<th>파일첨부개수</th>
							<td>
								<input type="number" name="FILE_CNT2" id="FILE_CNT2" class="inp" maxlength="2" placeholder="최대 5개까지 가능"/>
							</td>
						</tr>
						<tr>
							<th>파일최대용량</th>
							<td>
								<input type="number" name="FILE_MAX_QTY2" id="FILE_MAX_QTY2" class="inp" maxlength="4" placeholder="파일 개당 최대 2048kb"/>
							</td>
							<th>파일확장자</th>
							<td>
								<input type="text" name="FILE_EXT2" id="FILE_EXT2" class="inp" maxlength="100" placeholder="ex) jpg,png"/>
							</td>
						</tr>
						<tr id="area1">
							<th>
								컨텐츠내용<br/>
								<span style="color:red;" name="area2" id="area2"></span> 
								<span>&nbsp;/&nbsp;4000byte</span>
							</th>
							<td colspan="3">
								<!-- <textarea rows="3" id="CNTNTS_CTNT2" class="inp h100" maxlength="4000" placeholder="최대 4000byte" oninput="javascript:byteCheck(this.value);"></textarea>  -->
								<!-- <textarea rows="20" cols="100" class="input_use16" id="POST_CONTENT" style="resize:none" maxlength="4000"></textarea> -->
								<!-- <textarea id="POST_CONTENT" ></textarea> -->
								<textarea rows="3" name="CNTNTS_CTNT2" id="CNTNTS_CTNT2" class="inp" maxlength="4000" placeholder="최대 4000byte"></textarea>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
					<a href="javascript:void(0)" id="contentsMngAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="contentsMngUpdate" class="btn03 CRUDBtn">수정</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="contentsMngAccessPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">메뉴권한 관리</p>
			
			<div class="cont">
				<form id="contentsMngAccessForm">
					<input type="hidden" id="MENU_ID3"/>
					<table class="listTable" id="contentsMngAccessList"></table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('contentsMngAccessForm').reset();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="contentsMngAccessAdd" class="btn03 CRUDBtn">등록/변경</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>