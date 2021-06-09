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
	<script type="text/javascript" src="/js/tscp/bassInfo/carMng.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.table2excel.js"></script>
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
						<th>센터구분</th>
						<td>
							<select id="searchCNTER_CD" class="select" >
								<option value="">선택</option>
							</select>
						</td>
						<th>공동배차차량여부</th>
						<td>
							<select id="searchCOPERTN_CARALC_AT" class="select" >
								<option value="">선택</option>
								<option value="Y">예</option>
								<option value="N">아니오</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>예약차량여부</th>
						<td>
							<select id="searchRESVE_VHCLE_AT" class="select" >
								<option value="">선택</option>
								<option value="Y">예</option>
								<option value="N">아니오</option>
							</select>
						</td>
						<th>차량유형</th>
						<td>
							<select id="searchVHCLE_TY_CD" class="select" >
								<option value="">선택</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>차량번호</th>
						<td>
							<input type="text" id="searchVHCLE_NO" class="inp"/>
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">차량 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="carMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="carMngDelete" class="btn02 CRUDBtn">삭제</a>
						<a href="javascript:void(0)" id="carMngExcel" class="btn02 CRUDBtn">Excel저장</a>
					</div>
				</div>
				<form id="listTable">
				<input type="hidden" id="TOTAL_CNT" />
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="carMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0); document.getElementById('carMngForm').reset();" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">차량정보 관리</p>
			
			<div class="pCont">
			
				<div class="sthBox">
					<span class="t01">기본정보</span>
					<span class="t02">*필수입력 항목</span>
				</div>
				<form id="carMngForm">
					<table class="formTable t01 mt20" >
						<input type="hidden" id="CNTER_CD" >
						<tr>
							<th>공동배차여부<mark>*</mark></th>
							<td id="area3">
								<select id="COPERTN_CARALC_AT" class="select" >
									<option value="">선택</option>
									<option value="Y">예</option>
									<option value="N">아니오</option>
								</select>
							</td>
							<th>예약차량여부<mark>*</mark></th>
							<td>
								<select id="RESVE_VHCLE_AT" class="select" >
									<option value="">선택</option>
									<option value="Y">예</option>
									<option value="N">아니오</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>차량번호<mark>*</mark></th>
							<td>
								<input type="text" id="VHCLE_NO" class="inp" maxlength="25" style="width:calc( 57% - 22px ); "/>
								<a href="javascript:void(0)" class="btn02" style="float: none; overflow: visible;" id="checkCarNumber">차량번호 중복검사</a>
							</td>  
							<th>연식</th>
							<td>
								<input type="text" id="YRIDNW" class="inp" placeholder="숫자만 입력하세요. Ex) 2015" />
<!-- 								<input type="number" id="MAX_PASSENGERS2" class="inp" maxlength="2" placeholder="9" oninput="javascript:maxLengthCheck(this);"/> -->
							</td>
						</tr>
						<tr>
							<th>모델명</th>
							<td>
								<input type="text" id="MODL_NM" class="inp"  />
							</td>  
							<th>최대탑승인원<mark>*</mark></th>
							<td>
								<input type="text" id="MXMM_BRDNG_NMPR" class="inp" maxlength="10" placeholder="숫자만 입력하세요. Ex) 7"/>
							</td>
						</tr>
						<tr>
							<th>차량유형코드<mark>*</mark></th>
							<td>
								<select id="VHCLE_TY_CD" class="select" >
								</select>
							</td>  
							<th>제조사</th>
							<td>
								<input type="text" id="MAKR" class="inp" />
							</td>
						</tr>
						<tr>
							<th>차대번호</th>
							<td colspan="3">
								<input type="text" id="VIN" class="inp" />
							</td>  
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3">
								<input type="text" id="RM" class="inp" placeholder="기타 특이사항이 있으시면 입력해주세요."/>
							</td>  
						</tr>
					</table>
				</form>
				

				<div class="btnBox" align="center">
					<a href="javascript:void(0);" id="carMngAdd" class="btn01 CRUDBtn">등록</a>
					<a href="javascript:void(0);" id="carMngUpdate" class="btn01 CRUDBtn" style="width: 130px;">차량정보 수정</a>
					<a href="javascript:void(0);" id="carMngDeleteModal" class="btn01 CRUDBtn">삭제</a>
					<a href="javascript:$('.dialog').hide(); document.getElementById('carMngForm').reset();" class="btn01">닫기</a>

				</div>
			</div>  
		</div>
	</div>
<!-- 	엑셀 다운로드용 -->
	<div id="excelDiv" style="display:block;">
		<table id="excelTable"></table>
	</div>
	
</div>
</body>
</html>