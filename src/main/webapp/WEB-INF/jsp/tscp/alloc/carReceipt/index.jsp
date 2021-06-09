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
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0d275dc10b1bb95131478c3be6433bd6&libraries=services"></script>
	<script type="text/javascript" src="/js/tscp/cntrl/map.js"></script>
	<script type="text/javascript" src="/js/tscp/alloc/carReceipt.js"></script>
	
	
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
	
				<p class="etcTitle">예약 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="receiptOpen" class="btn02 CRUDBtn">예약접수</a>
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
	
	<div class="dialog" id="receiptPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">예약접수</p>
			
			<div class="cont">
				<form id="receiptForm">
					<table class="formTable t01" >
						<tr>
							<th>배차상태<mark>*</mark></th>
							<td>접수</td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>아이디<mark>*</mark></th>
							<td>
								<div class="searchBox">
									<input type="text" id="USER_ID2" maxlength="10" disabled="disabled"/>
									<a href="javascript:userSearchPopup('10')"><img src="/img/ico/ico_search.png" /></a>
								</div>	 
							</td>
							<th>예약자명<mark>*</mark></th>
							<td>
								<input type="text" class="inp" id="NAME2" maxlength="15" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<th>출발지<mark>*</mark></th>
							<td>
								<div class="searchBox">
									<input type="text" id="DEPT_DESC1" maxlength="50" disabled="disabled" value=""/>
									<input type="hidden" id="DEPT_X1"/>
									<input type="hidden" id="DEPT_Y1"/>
									<a href="javascript:$carReceipt.openMap();"><img src="/img/ico/ico_search.png" /></a>
								</div>	 
							</td>
							<th>목적지<mark>*</mark></th>
							<td>
								<div class="searchBox">
									<input type="text" id="DEST_DESC1" maxlength="50" disabled="disabled" value=""/>
									<input type="hidden" id="DEST_X1"/>
									<input type="hidden" id="DEST_Y1"/>
									<a href="javascript:carReceipt.openMap();"><img src="/img/ico/ico_search.png" /></a>
								</div>	 
							</td>
						</tr>
						<tr>
							<th>예약일자<mark>*</mark></th>
							<td colspan="3">
								<input type="text" id="ST_DT2" class="inp datepicker" style="width:60px;" disabled="disabled" />
								<input type="text" id="ST_HH2" class="inp w20" maxlength="2" placeholder="14" oninput="javascript:changeChkTime('H',this);"/>
								<span>:</span>
								<input type="text" id="ST_MM2" class="inp w20" maxlength="2" placeholder="00" oninput="javascript:changeChkTime('M',this);"/> 
							</td>
						</tr>
						<tr>
							<th>이동목적<mark>*</mark></th>
							<td>
								<select class="select" id="PURPOSE_CD2"></select>
							</td>
							<th>탑승인원<mark>*</mark></th>
							<td>
								<input type="number" class="inp" id="PASSENSERS2" maxlength="2" placeholder="이동보조원 포함"/>
							</td>
						</tr>
						<tr>
							<th>예상이동거리(km)</th>
							<td>
								<input type="number" class="inp" id="EXP_TRAVEL_DISTANCE2" maxlength="5" placeholder="3차 자동연산 개발예정"/>
							</td>
							<th>예상소요시간(hr)</th>
							<td>
								<input type="number" class="inp" id="EXP_TRAVEL_HOURS2" maxlength="3" placeholder="3차 자동연산 개발예정"/>
							</td>
						</tr>
						<tr>
							<th>예상운임(원)</th>
							<td>
								<input type="number" class="inp" id="EXP_SERICE_FEE2" maxlength="10" placeholder="3차 자동연산 개발예정"/>
							</td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3">
								<textarea class="inp" id="REMARK" style="height:80px;" maxlength="200"></textarea>
							</td>
						</tr>
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('receiptForm').reset();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="carReceiptAdd" class="btn03">등록</a>
				</div>
			</div>  
		</div>
	</div>

	<div class="dialog" id="allocPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">배정 가능 목록</p>
			
			<div class="cont">
				<form id="allocForm"></form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide(); document.getElementById('allocForm').reset();" class="btn03">닫기</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="userSearchPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:$('#userSearchPopup').hide(); document.getElementById('listTablePop').reset();" class="popClose2"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">회원조회</p>
			
			<div class="cont">
				<form id="listTablePop">
				<table class="sTable01">
					<tr>
						<th>구분</th>
						<td>
							<select id="USER_GBN_CD_POP" class="select" disabled="disabled">
								<option value="">선택</option>
							</select>
						</td>
						<th>아이디</th>
						<td>
							<input type="text" class="inp" id="USER_ID_POP" />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" class="inp" id="NAME_POP" />
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="userSearchList_pop" class="btn01">찾기</a></td>
					</tr>
				</table>
				
				<table class="listTable t01" id="userSearchTable"></table>
				<div class="paging" id="paging2" style="margin-bottom: 0px;"></div>
				</form>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="mapSearchPopUp" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:$('#mapSearchPopUp').hide(); document.getElementById('mapTablePop').reset();" class="popClose2"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="mapPopTitle">지도조회</p>
			
			<div class="cont">
				<form id="mapTablePop">
				<!-- <table class="sTable01">
					
				</table> -->
				
				<div class="gpsCont" style="height:520px;">
					<div class="gpsLeft" style="width:300px;">
						<div class="resultArea" style="height:100%;width:100%;">
							<div class="tit" id="resultAreaSubject">지역검색<div style="position:absolute;margin-top:-40px;margin-left:90px">&nbsp;<a href='javascript:$carReceipt.saveDirection();' class='btn02'>저장</a></div></div>
							<table class="searhForm">
								<tr>
									<th>
										지역검색
									</th>
									<td>
										<div class="searchBox">
											<input type="text" name="searchWord" id="searchWord">
											<a href="javascript:$carReceipt.searchAddr(1);"><img src="/img/ico/ico_search01.png" /></a>
										</div>
									</td>
								</tr>
								
								
								<tr>
									<th>
										출발지 : 
									</th>
									<td>
										<input type="text" name="startAddr" id="startAddr" readOnly/>
									</td>
								</tr>
								
								<tr>
									<th>
										도착지 : 
									</th>
									<td>
										<input type="text" name="endAddr" id="endAddr" readOnly/>
									</td>
								</tr>
								
								<tr>
									<th>
										<span id="resultSubject"></span>
									</th>
									<td>
										<span id="resultKeyWord"></span>
									</td>
								</tr>
								
							</table>
							
							<div id="gpsTable">
							
							</div>
							<div class="paging" id="mapPageList" style=""></div>
						</div>
					</div>	
					<div class="gpsMap">
						<div id="gMapArea" style="width:100%;height:100%;"></div>
					</div>
				</div>
				
				</form>
				
				<!-- <div class="btnbox">
					<a href="javascript:$('#mapSearchPopUp').hide();" class="btn03">닫기</a>
				</div> -->
			</div>  
			
		</div>
	</div>
</body>
</html>