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
	<script type="text/javascript" src="/js/tscp/mngr/menuMng.js"></script>
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
						<th>메뉴레벨</th>
						<td>
							<select id="MENU_LEVEL" class="select" >
								<option value="">선택</option>
								<option value="1">대메뉴</option>
								<option value="2">소메뉴</option>
							</select>
						</td>
						<th>사용구분</th>
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
	
				<p class="etcTitle">메뉴 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="menuMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="menuMngDelete" class="btn02 CRUDBtn">사용중지</a>
						<a href="javascript:void(0)" id="menuMngAccessOpen" class="btn02 CRUDBtn">메뉴권한</a>
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
	
	<div class="dialog" id="menuMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0); document.getElementById('menuMngForm').reset();" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">메뉴관리 등록</p>
			
			<div class="cont">
				<form id="menuMngForm">
					<table class="formTable t01" >
						<tr>
							<th>메뉴레벨 <span style="color:red;">*</span></th>
							<td>
								<select id="MENU_LEVEL2" class="select" >
									<option value="2" selected>소메뉴</option>
									<option value="1">대메뉴</option>
								</select>
							</td>
							<th>상위메뉴ID <span style="color:red;">*</span></th>
							<td id="area1"></td>
						</tr>
						<tr>
							<th>하위메뉴ID <span style="color:red;">*</span></th>
							<td>
								<input type="text" id="MENU_ID2" class="inp" maxlength="15" placeholder="alloc301"/>
							</td>
							<th>메뉴명<span style="color:red;">*</span></th>
							<td>
								<input type="text" id="MENU_NAME2" class="inp" maxlength="25" placeholder="차량 예약"/>
							</td>
						</tr>
						<tr>
							<th>메뉴순번 <span style="color:red;">*</span></th>
							<td>
								<input type="text" id="MENU_SEQ2" class="inp" maxlength="3" placeholder="301" oninput="javascript:maxLengthCheck(this);"/>
							</td>
							<th>사용여부<span style="color:red;">*</span></th>
							<td>
								<select id="USE_YN2" class="select" >
									<option value="N" selected>N</option>
									<option value="Y">Y</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>컨텐츠URL</th>
							<td>
								<input type="text" id="CTNT_URL2" class="inp" maxlength="50" placeholder="/view/alloc/carAlloc.do"/>
							</td>
							<th>메뉴구분</th>
							<td>
								<select id="MENU_GUBN2" class="select">
									<option value="O" selected>운영서비스</option>
									<option value="U">대민서비스</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('menuMngForm').reset();" class="btn01">닫기</a>
					<a href="javascript:void(0);" id="menuMngAdd" class="btn01">등록</a>
					<a href="javascript:void(0);" id="menuMngUpdate" class="btn01">수정</a>
					<a href="javascript:void(0);" id="menuMngDelete2" class="btn01">삭제</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="menuMngAccessPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">메뉴권한 관리</p>
			
			<div class="cont">
				<form id="menuMngAccessForm">
					<input type="hidden" id="MENU_ID3"/>
					<table class="listTable" id="menuMngAccessList"></table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('menuMngAccessForm').reset();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="menuMngAccessAdd" class="btn03 CRUDBtn">등록/변경</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>