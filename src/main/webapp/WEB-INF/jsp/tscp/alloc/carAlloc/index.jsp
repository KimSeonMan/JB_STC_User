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
	<script type="text/javascript" src="/js/tscp/alloc/carAlloc.js"></script>
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
				
				<p class="etcTitle">배차 현황</p> 
				<table class="formTable mt10" id="area1"></table>
	
				<table class="sTable01">
					<tr>
						<th>예약자이름</th>
						<td>
							<div class="searchBox">
								<input type="text" id="NAME" />
							</div>
						</td>
						<th>배차상태</th>
						<td>
							<select id="ALLOC_STAT_CD" class="select">
								<option value="">선택</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>예약일</th>
						<td colspan="2">
							<input type="text" id="ST_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
							<span>~</span>
							<input type="text" id="EN_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
						</td>
						<td class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">배차 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
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
	
	<div class="dialog" id="carAllocPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">배차상세 정보</p>
			
			<div class="cont">
				<form id="carAllocForm">
					<table class="formTable t01" >
						<tr>
							<th>배차상태</th>
							<td id="val1"></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>회원ID</th>
							<td id="val2"></td>
							<th>예약자명</th>
							<td id="val3"></td>
						</tr>
						<tr>
							<th>출발지</th>
							<td id="val4"></td>
							<th>목적지</th>
							<td id="val5"></td>
						</tr>
						<tr>
							<th>예약일자</th>
							<td id="val6"></td>
							<th>이동목적</th>
							<td id="val7"></td>
						</tr>
						<tr>
							<th>차량번호</th>
							<td id="val8"></td>
							<th>탑승인원</th>
							<td id="val9"></td>
						</tr>
						<tr>
							<th>승차일시</th>
							<td id="val10"></td>
							<th>하차일시</th>
							<td id="val11"></td>
						</tr>
						<tr>
							<th>운전자ID</th>
							<td id="val12"></td>
							<th>운전자명</th>
							<td id="val13"></td>
						</tr>
						<tr>
							<th>예상이동거리(km)</th>
							<td id="val14"></td>
							<th>예상소요시간(hr)</th>
							<td id="val15"></td>
						</tr>
						<tr>
							<th>예상운임(원)</th>
							<td id="val16"></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>이동거리(km)</th>
							<td id="val17"></td>
							<th>소요시간(hr)</th>
							<td id="val18"></td>
						</tr>
						<tr>
							<th>운임(원)</th>
							<td id="val19"></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3" id="val20"></td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('carAllocForm').reset();" class="btn03">닫기</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>