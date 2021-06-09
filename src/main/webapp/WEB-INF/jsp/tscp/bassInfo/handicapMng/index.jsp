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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/js/tscp/bassInfo/hadicapMng.js"></script>
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
						<th>이름</th>
						<td>
							<input type="text" id="NAME" class="inp"/>
						</td>
						<th>주소</th>
						<td>
							<input type="text" id="ADDRESS" class="inp"/>
						</td>
					</tr>
					<tr>
						<th>보조인유무</th>
						<td>
							<select id="SUPPORTER_YN" class="select" >
								<option value="">선택</option>
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">이용자 정보 조회 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr  btn">
						<a href="javascript:void(0)" id="hadicapMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="hadicapMngDelete" class="btn02 CRUDBtn">삭제</a>
						<a href="javascript:void(0)" id="hadicapMngExcel" class="btn02 CRUDBtn">Excel저장</a>
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
	
	<div class="dialog" id="hadicapMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0); document.getElementById('hadicapMngForm').reset();" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">이용자 기본정보 관리</p>
			
			<div class="cont">
				<form id="hadicapMngForm">
				<input type="hidden" id="USER_ID2" />
				<input type="hidden" id="TOTAL_CNT" />
					<table class="formTable t01 mt20" >
						<tr>
							<th>이름<mark>*</mark></th>
							<td>
								<input type="text" id="NAME2" class="inp" maxlength="15"/>
							</td>
							<th>복지카드번호<mark>*</mark></th>
							<td>
								<input type="text" id="WELFAIR_CARD_NO2" class="inp" maxlength="14" placeholder="123456-1234567"/>
							</td>
						</tr>
						<tr>
							<th>생년월일<mark>*</mark></th>
							<td>
								<input type="text" id="BIRTH_DATE2" class="inp" maxlength="8" oninput="javascript:maxLengthCheck(this);" placeholder="19800616"/>
							</td>  
							<th>SMS수신여부<mark>*</mark></th>
							<td>
								<select class="select" id="SMS_RECEIVE_YN2">
									<option value="N" selected>아니오</option>
									<option value="Y">예</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>연락처</th>
							<td>	
								<select id="PHONE1" class="select w56"></select>
								<span>-</span>
								<input type="text" id="PHONE2" class="inp w35" maxlength="4" oninput="javascript:maxLengthCheck(this);"/>
								<span>-</span>
								<input type="text" id="PHONE3" class="inp w35" maxlength="4" oninput="javascript:maxLengthCheck(this);"/>
							</td>
							<th>휴대폰<mark>*</mark></th>
							<td>
								<select id="MOBILE1" class="select w56"></select>
								<span>-</span>
								<input type="text" id="MOBILE2" class="inp w35" maxlength="4" oninput="javascript:maxLengthCheck(this);"/>
								<span>-</span>
								<input type="text" id="MOBILE3" class="inp w35" maxlength="4" oninput="javascript:maxLengthCheck(this);"/>
							</td>  
						</tr>
						<tr>
							<th>우편번호<mark>*</mark></th>
							<td colspan="3">
								<input type="text" id="ZIP2" class="inp w100" maxlength="6" disabled="disabled"/>
								<a href="#" id="zipSearch" class="btn01">우편번호검색</a>
							</td>
						</tr>
						<tr>
							<th rowspan="2">주소<mark>*</mark></th>
							<td colspan="3">
								<input type="text" id="ADDRESS2" class="inp" maxlength="30" disabled="disabled"/>
							</td>
						</tr>
						<tr> 
							<td colspan="3"> 
								<input type="text" id="ADDRESSDTL2" class="inp" maxlength="20"/>
							</td> 
						</tr>
						<tr>
							<th>이메일<mark>*</mark></th>
							<td colspan="3">
								<input type="text" id="EMAIL1" class="inp w150" maxlength="30"/>
								<span>@</span>
								<input type="text" id="EMAIL2" class="inp w150" maxlength="20"/>
								<select class="select w150 ml5" id="EMAIL3" onchange="changeEmail(this.value);"></select>
							</td>
						</tr>
						<tr>
							<th>장애종류1<mark>*</mark></th>
							<td>
								<select class="select" id="DISABLE_TYPE1_CD2"></select>
							</td>
							<th>장애종류2<mark>*</mark></th>
							<td>
								<select class="select" id="DISABLE_TYPE2_CD2"></select>
							</td>
						</tr>
						<tr>
							<th>보조인유무<mark>*</mark></th>
							<td>
								<select class="select" id="SUPPORTER_YN2">
									<option value="N" selected>아니오</option>
									<option value="Y">예</option>
								</select>
							</td>
							<th>의사소통<mark>*</mark></th>
							<td>
								<select class="select" id="COMMUNICALBE_YN2">
									<option value="N" selected>아니오</option>
									<option value="Y">예</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>도움여부<mark>*</mark></th>
							<td>
								<select class="select" id="HELPER_YN2">
									<option value="N" selected>아니오</option>
									<option value="Y">예</option>
								</select>
							</td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>휠체어<mark>*</mark></th>
							<td>
								<select class="select" id="WHEELCHAIR_CD2" onchange="javascript:changeWheechairCD(this.value);"></select>
							</td>
							<th>휠체어이용기간<mark>*</mark></th>
							<td>
								<input type="text" id="WHEELCHAIR_USE_YEAR2" class="inp" maxlength="2" oninput="javascript:maxLengthCheck(this);" placeholder="년단위로 숫자만 기입(ex:10)"/>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('hadicapMngForm').reset();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="hadicapMngAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="hadicapMngUpdate" class="btn03 CRUDBtn">수정</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>