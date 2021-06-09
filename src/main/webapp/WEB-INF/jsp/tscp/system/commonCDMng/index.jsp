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
	<script type="text/javascript" src="/js/tscp/system/commonCDMng.js"></script>
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
						<th>코드ID</th>
						<td><input type="text" class="inp w100" id="cont1" /></td>
						<th>코드명</th>
						<td><input type="text" class="inp w100" id="cont2" /></td>
						<td class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>  
					</tr>
				</table>
				
				<div class="tableLeft">
					<div class="etcTitle">공통코드
						<div class="fr btn">
							<a href="javascript:void(0)" id="cdAOpen" class="btn02 CRUDBtn">신규</a> 
<!-- 							<a href="javascript:void(0)" id="cdADelete" class="btn02 CRUDBtn">삭제</a> -->
						</div>
					</div> 
					<div class="codehbox">
						<form id="listTableA">
							<table class="listTable" id="listA"></table>
						</form>	
					</div>
	
					<div class="paging" id="pagingA"></div>
				</div>
	
				<div class="tableRight">
					<div class="etcTitle">공통코드 상세
						<div class="fr btn">
							<a href="javascript:void(0)" id="cdBOpen" class="btn02 CRUDBtn">신규</a> 
<!-- 							<a href="javascript:void(0)" id="cdBDelete" class="btn02 CRUDBtn">삭제</a> -->
						</div>
					</div> 
					<div class="codehbox">
						<form id="listTableB">
						<input type="hidden" id="CD_ID" />
							<table class="listTable" id="listB"></table>
						</form>
					</div>
	
					<div class="paging" id="pagingB"></div>
				</div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="commonCDPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle">공통코드 등록</p>
			
			<div class="cont">
				<form id="commonCDForm">
				<input type="hidden" name="CD_GUBN" id="CD_GUBN"/>
				<input type="hidden" name="CD_CHECK_YN" id="CD_CHECK_YN" value="N"/>
				<input type="hidden" name="CD_ID3" id="CD_ID3"/>
					<table class="formTable t01 mt20" >
						<colgroup>
							<col width="90" />
							<col width="" />
							<col width="90" />
							<col width="" />  
						</colgroup>
						<tr id="area1">
							<th>코드ID<mark>*</mark></th>
							<td>
								<input type="text" name="CD_ID2" id="CD_ID2" class="inp w80" maxlength="20"/>
								<a href="#" id="idCheckA" class="btn01">중복조회</a>
							</td>
							<th>코드명<mark>*</mark></th>
							<td>
								<input type="text" name="CD_DESC2" id="CD_DESC2" class="inp" maxlength="30"/>
							</td>
						</tr>
						<tr id="area2">
							<th>코드ID<mark>*</mark></th>
							<td id="val1"></td>
							<th>코드명<mark>*</mark></th>
							<td id="val2"></td>
						</tr>
						<tr id="area3">
							<th>코드값<mark>*</mark></th>
							<td>
								<input type="text" name="CD_VAL2" id="CD_VAL2" class="inp w80" maxlength="5"/>
								<a href="#" id="idCheckB" class="btn01">중복조회</a>
							</td>
							<th>코드값명<mark>*</mark></th>
							<td>
								<input type="text" name="CD_VAL_DESC2" id="CD_VAL_DESC2" class="inp" maxlength="30"/>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="commonCDAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="commonCDUpdate" class="btn03 CRUDBtn">수정</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>