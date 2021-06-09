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
	<script type="text/javascript" src="/js/tscp/system/stcMng.js"></script>
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<!-- <link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> --> 
	
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
						<select class="select" id="searchType">
							<option value="">선택</option>
							<option value="CNTER_NM">센터명</option>
							<option value="CNTER_CD">센터코드</option>
							<option value="UPPER_CNTER_CD">상위센터코드</option>
						</select>
					</td> 
					<th>MOU여부</th>
					<td>
						<select class="select" id="searchMou">
							<option value="">선택</option>
							<option value="Y">예</option>
							<option value="N">아니오</option>
						</select>
					</td> 
				</tr> 
				<tr>
					<th>검색어</th>
					<td colspan="3">
						<input type="text" class="inp" id="searchText" style="float: left;"/> 
						<a href="javascript:void(0)" class="btn01" id="search">찾기</a>
					</td> 
				</tr>
			</table>

			<p class="tit01">STC관리 현황  목록</p>

			<div class="boardHeader">
				<span>검색목록: <a id="listTotal"></a></span>
				<div class="btn"> 
					<a href="javascript:void(0)" id="addBtn">등록</a> 
					<a href="javascript:void(0)" id="delBtn">삭제</a>
				</div>
			</div>

			 <form id="listTable">
				<table class="listTable" id="list"></table>
			</form>	
			
			<div class="paging" id="paging"></div>
		</div>
		 

	</div><!-- end : container -->
	 
	<div class="footer"><!-- start : footer -->  
		<jsp:include page="/WEB-INF/jsp/common/includeFooter.jsp"></jsp:include>
	</div><!-- end : footer -->  
	
	
	<div class="dialog" id="stcPopUp" style="display:block;">
		<div class="popbox">
			<div class="pHeader">
				<a href="javascript:void(0)" class="popClose"><img src="/img/ico/ico_close.png" id="closeImg"/></a>
				<p class="popTitle">STC 등록</p>
			</div>
			<div class="pCont"> 
				<form id="stcForm">
					<div class="sthBox">
						<span class="t01">기본정보</span>
						<span class="t02">*필수입력 항목</span>
					</div>
					<table class="sTable01 ptype">
						<input type="hidden" id="CNTER_CD_orgin" />
						<colgroup>
							<col width="120" />
							<col width="" />
							<col width="120" />
							<col width="" />
						</colgroup> 
						<tr>
							<th>레벨<span>*</span></th>
							<td>
								<select class="select" id="LEVEL" name="LEVEL" >
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3" selected>3</option>
								</select>
							</td>
							<th>대표번호<span>*</span></th>
							<td><input type="text" id="TELNO" name="TELNO" class="inp" placeholder="ex) 1666-0420" /></td>
						</tr>  
						<tr>
							<th>상위센터코드<span>*</span></th>
							<td>
								<select class="select" id="UPPER_CNTER_CD" name="UPPER_CNTER_CD">
									<option value="">선택</option>
								</select>
							</td>
							<th>센터코드<span>*</span></th>
							<td><input type="text" id="CNTER_CD" name="CNTER_CD" class="inp" placeholder="" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>센터명(한글)<span>*</span></th>
							<td><input type="text" id="CNTER_NM" name="CNTER_NM" class="inp" placeholder="ex) 의정부지역이동지원센터" /></td>
							<th>지역명(영문)<span>*</span></th>
							<td><input type="text" id="AREA_NM_EN" name="AREA_NM_EN" class="inp" placeholder="ex) Uijeongbu  City" /></td>
						</tr>
						<tr>
							<th>MOU여부<span>*</span></th>
							<td>
								<select class="select" id="mouYn">
									<option value="Y">예</option>
									<option value="N" selected>아니오</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>우편번호<span>*</span></th>
							<td colspan="3">
								<input type="text" class="inp t03" id='ZIP_CODE' readonly="readonly"/>
								<a href="javascript:void(0)" class="btn02" id="searchPost" onclick="sample6_execDaumPostcode()">우편번호 검색</a>
							</td> 
						</tr>
						<tr>
							<th>주소<span>*</span></th>
							<td colspan="3">
								<input type="text" class="inp" id="ADRES" readonly="readonly"/>
								<input type="text" class="inp" id="ADRES_DETAIL"/>
							</td> 
						</tr>
						<tr>
							<th>관리지역<span>*</span></th>
							<td colspan="3">
								<input type="text" class="inp" id="MANAGE_AREA" name="MANAGE_AREA"/>
							</td> 
						</tr>
					</table>
				</form>
				<div class="btnBox"> 
					<a href="javascript:void(0)" class="btn01" id="addStc">등록</a>
					<a href="javascript:void(0)" class="btn01" id="editDetail">STC 정보 수정</a>
					<a href="javascript:void(0)" class="btn01" id="deleteDetail">삭제</a>
					<a href="javascript:void(0)" class="btn01" id="editStc">삭제</a>
					<a href="javascript:void(0)" class="btn01" id="canclePopup">취소</a>
				</div> 
				
				<ul class="etxtList">
					<li>* 대표번호 / 지역명(영문) / 우편번호 / 주소는 메인화면에 정보로 표출됩니다. 정확히 입력해주세요</li>
					<li>관리지역이 여러 곳일 경우 , 구분해서 입력 ex) 경기도 용인시, 경기도 수원시 </li>
				</ul>
			</div>
		</div>
	</div>

</div><!-- end : wrapper-->

 
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
</html> 