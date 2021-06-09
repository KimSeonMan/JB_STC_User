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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/js/tscp/carUseResve/recptSttus.js"></script>
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
				<table class="sTable01">
					<colgroup>
						<col width="120" />
						<col width="" />
					</colgroup>
					<tr>
						<th>배차상태</th>
						<td>
							<select id="CARALC_STTUS_CD" class="select">
								<option value="">선택</option>
								<option value="10">예약신청</option>
								<option value="30">처리중</option>
								<option value="70">배차완료</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>예약일</th>
						<td >
							<input type="text" id="ST_DT" class="inp datepicker" disabled="disabled" /> 
							<input type="text" id="EN_DT" class="inp datepicker ml20" disabled="disabled" /> 
							<a href="javascript:void(0)" id="searchList" class="btn01 t01">찾기</a>
						</td>
					</tr>
				</table>
	
				<p class="tit01">차량요청 목록</p>
				<div class="boardHeader">
					<p>검색목록: 총 <span id="totalCnt"></span>건</p>
					<div class="btn">
						<a href="javascript:void(0)" id="receiptOpen" class="btnType01 CRUDBtn">즉시콜 신청하기</a>
						<a href="javascript:void(0)" id="carReceiptDelete" class="btnType02 CRUDBtn">취소</a>
					</div>
				</div>
				<form id="listTable">
					<table class="listTable">
                        <colgroup>
                            <col width='60' />
                            <col width='80' />
                            <col width='180' />
                            <col width='220' />
                            <col width='220' />
                            <col width='' />
                            <col width='' />
                            <col width='' />
                            <col width='' />
                        </colgroup>
                        <thead>
                            <tr>
                                <th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>
                                <th>No</th>
                                <th>예약일시</th>
                                <th>출발지</th>
                                <th>목적지</th>
                                <th>이동유형</th>
                                <th>보조인수</th>
                                <th>구분</th>
                                <th>배차상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr><td colspan='9'>조회중입니다.</td></tr>
                        </tbody>
                    </table>
				</form>
				<!-- <div class="btn">
						<a href="javascript:void(0)" id="carReceiptAllDelete" class="btnType02 CRUDBtn" style="margin-left: 1150px;margin-top: 15px;">취소일괄삭제</a>
				</div> -->
			<div class="paging"></div></div>
		</div><!-- end subContent -->
	</div><!-- end  container -->
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	<div class="dialog" id="receiptPopup" style="display:block;">
		<div class="pop" id="pop1">
			<div class="pHeader">
				<span>차량 즉시콜</span>
				<a href="javascript:void(0)"><img src="/img/btn/btn_close01.png" onclick="hidePopup()" class="closeBtn"/></a>
			</div>

			<div class="pCont">
				<form id="receiptForm">
					<div class="reqRadio01">
						<a href="javascript:void(0)" class="btnRadio" id="nowDay">즉시콜</a>
						<a href="javascript:void(0)" class="btnRadio" id="bookDay">사전예약</a>
						<%--<a href="javascript:void(0)" class="btnChk" id="transCheck">대중교통 환승 여부</a>--%>
					</div>
					<!-- ======================================================================================================= -->
					<div class="pActicle"  id="receipForm1" style="display: none;"> 
						<table class="sTable01 t01" >
							<colgroup>
								<col width="120" />
								<col width="" />
								<col width="160" />
								<col width="" />
							</colgroup> 
							<tr>
								<th>출발지<span>*</span></th>
								<td>
									<input type="text" id="DEPT_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEPT_FULL_DESC1"/>
									<input type="hidden" id="DEPT_X1"/>
									<input type="hidden" id="DEPT_Y1"/>
									<input type="hidden" id="CHRG_CNTER_CD1"/>
									<input type="hidden" id="GRP_ID1"/>
									<a href="javascript:void(0)" class="searchAddress1"><img src="/img/ico/ico_search01.png" class="searchAddress1"/></a>
								</td>
								<th>목적지<span>*</span></th><!-- <br />(대중교통 출발지) -->
								<td>
									<input type="text" id="DEST_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEST_FULL_DESC1"/>
									<input type="hidden" id="DEST_X1"/>
									<input type="hidden" id="DEST_Y1"/>
									<a href="javascript:void(0)" class="searchAddress1"><img src="/img/ico/ico_search01.png" class="searchAddress1"/></a>
								</td>
							</tr>
							<tr>
								<th>기존 출발/<br />목적지</th>
								<td colspan="3">
									<div class="itemForm">
										<p class="box01"><span>* 기존 출발/목적지 사용 시 체크하세요.</span></p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk startAddrSet1" >출발지</a>
											<select class="select searchStartList1" >

											</select>
										</div>
									</div>
									<div class="itemForm">
										<p class="box01">
											<span>* 출발/목적지 바꾸기</span>
											<a href="javascript:void(0)"><img src="/img/ico/ico_change01.png" class="changeStartEnd1"/></a>
										</p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk endAddrSet1" >목적지</a>
											<select class="select searchEndList1" >
							
											</select>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>이동목적<span>*</span></th>
								<td colspan="3"><select class="select" id="MVMN_PURPS_CD"><option>선택</option></select></td>
								<%--<th>왕복여부<span>*</span></th>--%>
								<%--<td>--%>
									<%--<select class="select" id="ROUNDTRIP_AT">--%>
										<%--<option value="Y">예</option>--%>
										<%--<option value="N" selected>아니오</option>--%>
									<%--</select>--%>
								<%--</td>--%>
							</tr> 
							<tr>
								<th>비고</th>
								<td colspan="3"><textarea class="textarea" id="RM" placeholder="기타 특이사항이 있으시면 입력해주세요."></textarea></td> 
							</tr>
						</table>
			 			<ul class="petcList">
							<li class="cr" style="color:#444444; font-size:30px;">탑승 이용자 정보 </li>
							<div style="float:right;">
								<a class="btnType02 " id="plusHand" href="javascript:void(0)" onClick="javaScript:$recptSttus.plusHand('1')" style="width:50px; height:30px; line-height:30px;cursor:pointer;">+</a>
								<a class="btnType02" id="minusHand" href="javascript:void(0)" onClick="javaScript:$recptSttus.minusHand('1')" style="width:50px; height:30px; line-height:30px;cursor:pointer;">-</a>
							</div>
						</ul>
						
						<table class="sTable01 ptype" id="addHandInfo">
							<colgroup>
								<col width="10%">
								<col width="20%">
								<col width="32%">
								<col width="20%">
								<col width="18%">
							</colgroup>
							<tr>
								<th>구분</th>
								<th>이용자성함</th>
								<th>연락처</th>
								<th>휠체어 구분</th>
								<th>활동보조인수</th>
							</tr>
							<tr id="hand1" name="hand1">
								<td>1 (본인)</td>
								<td id="MBER_NM"></td>
								<td id="MBTLNUM"></td>
								<td><select class="select" id="WHEELCHAIR_SE_CD"><option>선택</option></select></td>
								<td><select class="select" id="SUPPORT_NUM"></select></td>
							</tr>
						</table>
						
						<div class="btnBox"> 
							<a href="javascript:$recptSttus.recptSttusAdd()" class="btnType01">저장</a>
							<a href="javascript:void(0)" onclick="hidePopup()" class="btnType02">취소</a>
						</div>
						<ul class="petcList">
							<li class="cr">* 필수입력 항목</li>
							<!-- <li class="cr">* 왕복은 동일차량으로 출발지 – 목적지 – 출발지 경로로 운행 / 왕복은 목적지에서 소요시간이 <span id="alocRequireTime">00</span>분 이내만 예약 가능</li>
							<li>* 왕복으로 차량예약 시 배차가 지연될 수 있습니다.</li> -->
						</ul>
					</div>

					<div class="dialog" id="timePop" style="display:block;">
						<div id="pop0" style="left: 50%; right: 50%; width: 600px;margin-left:-300px;position:fixed;top:100px;"></div>
					</div>

					<!-- ======================================================================================================= -->
					<div class="pActicle" id="receipForm2" style="display: none;"> 
						<table class="sTable01 ptype">
							<colgroup>
								<col width="120" />
								<col width="" />
								<col width="160" />
								<col width="" />
							</colgroup> 
							<tr>
								<th>예약일시<span>*</span></th>
								<td colspan="3">
									<input type="text" class="inp" id="recptDate1" readonly/> 
									<!-- <input type="text" class="inp w20 ml10" id="recptHour1" maxlength="2"/> -->
									<select id="recptHour1" style="width: 70px; height:40px;margin-left:10px;">
										<c:forEach var="index" begin="0" end="9" step="1">
											<option value="0${index}">${index}</option>
										</c:forEach>
										<c:forEach var="index" begin="10" end="23" step="1">
											<option value="${index}">${index}</option>
										</c:forEach>
									</select>
									<span class="etxt" style=" float: none;">:</span>
									<select id="recptMin1" style="width: 70px; height:40px;">
											<option value="00">00</option>
										<c:forEach var="index" begin="10" end="50" step="10">
											<option value="${index}">${index}</option>
										</c:forEach>
										<!-- <option value="0">00</option>
										<option value="15">15</option>
										<option value="30">30</option>
										<option value="45">45</option> -->
									</select>
								</td> 
							</tr> 
							<tr>
								<th>출발지<span>*</span></th>
								<td>
									<input type="text" id="DEPT_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEPT_FULL_DESC1"/>
									<input type="hidden" id="DEPT_X1"/>
									<input type="hidden" id="DEPT_Y1"/>
									<input type="hidden" id="CHRG_CNTER_CD1"/>
									<a href="javascript:void(0)" class="searchAddress2"><img src="/img/ico/ico_search01.png" class="searchAddress2" /></a>
								</td>
								<th>목적지<span>*</span><br />(대중교통 출발지)</th>
								<td>
									<input type="text" id="DEST_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEST_FULL_DESC1"/>
									<input type="hidden" id="DEST_X1"/>
									<input type="hidden" id="DEST_Y1"/>
									<a href="javascript:void(0)" class="searchAddress2"><img src="/img/ico/ico_search01.png" class="searchAddress2"/></a>
								</td>
							</tr> 
							<tr>
								<th>기존 출발/<br />목적지</th>
								<td colspan="3">
									<div class="itemForm">
										<p class="box01"><span>* 기존 출발/목적지 사용 시 체크하세요.</span></p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk startAddrSet1">출발지</a>
											<select class="select searchStartList1">

											</select>
										</div>
									</div>
									<div class="itemForm">
										<p class="box01">
											<span>* 출발/목적지 바꾸기</span>
											<a href="javascript:void(0)"><img src="/img/ico/ico_change01.png" class="changeStartEnd1"/></a>
										</p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk endAddrSet1">목적지</a>
											<select class="select searchEndList1" >
							
											</select>
										</div>
									</div>
								</td> 
							</tr> 
							<tr>
								<th>이동목적<span>*</span></th>
								<td colspan="3"><select class="select" id="MVMN_PURPS_CD"><option>선택</option></select></td>
								<%--<th>왕복여부<span>*</span></th>--%>
								<%--<td>--%>
									<%--<select class="select" id="ROUNDTRIP_AT">--%>
										<%--<option value="Y">예</option>--%>
										<%--<option value="N" selected>아니오</option>--%>
									<%--</select>--%>
								<%--</td>--%>
							</tr> 
							<tr>
								<th>비고</th>
								<td colspan="3"><textarea class="textarea" id="RM" placeholder="기타 특이사항이 있으시면 입력해주세요."></textarea></td> 
							</tr>
						</table> 
			 			
			 			<ul class="petcList">
							<li class="cr" style="color:#444444; font-size:30px;">탑승 이용자 정보 </li>
							<div style="float:right;">
								<a class="btnType02 " id="plusHand" href="javascript:void(0)" onClick="javaScript:$recptSttus.plusHand('2')" style="width:50px; height:30px; line-height:30px;cursor:pointer;">+</a>
								<a class="btnType02" id="minusHand" href="javascript:void(0)" onClick="javaScript:$recptSttus.minusHand('2')" style="width:50px; height:30px; line-height:30px;cursor:pointer;">-</a>
							</div>
						</ul>
						
						<table class="sTable01 ptype" id="addHandInfo">
							<colgroup>
								<col width="10%">
								<col width="20%">
								<col width="32%">
								<col width="20%">
								<col width="18%">
							</colgroup>
							<tr>
								<th>구분</th>
								<th>이용자성함</th>
								<th>연락처</th>
								<th>휠체어 구분</th>
								<th>활동보조인수</th>
							</tr>
							<tr id="hand1" name="hand1">
								<td>1 (본인)</td>
								<td id="MBER_NM"></td>
								<td id="MBTLNUM"></td>
								<td><select class="select" id="WHEELCHAIR_SE_CD"><option>선택</option></select></td>
								<td><select class="select" id="SUPPORT_NUM"></select></td>
							</tr>
						</table>
		
						<div class="btnBox"> 
							<a href="javascript:$recptSttus.recptSttusAdd()" class="btnType01">저장</a>
							<a href="javascript:void(0)" onclick="hidePopup()" class="btnType02">취소</a>
						</div>
						
						<ul class="petcList">
							<li class="cr">* 필수입력 항목</li>
							<li class="cr">* 왕복은 동일차량으로 출발지 – 목적지 – 출발지 경로로 운행 / 왕복은 목적지에서 소요시간이 <span id="alocRequireTime">00</span>분 이내만 예약 가능</li>
							<li>* 왕복으로 차량예약 시 배차가 지연될 수 있습니다.</li>
						</ul>
					</div>
					
					<!-- ======================================================================================================= -->
					<div class="pActicle" id="receipForm3" style="display: none;">
						<p class="ptxt02">* 광역 간 이동 시 대중교통(버스터미널, 기차 등)을 이용하셔서 출발/목적지로 지정하셔야 합니다.</p>
						<p class="ptxt02">* 예약1과 예약2의 예약 날짜는 동일해야 신청이 가능합니다.</p>
						<p class="tit02">예약 1</p>
						<table class="sTable01 ptype">
							<colgroup>
								<col width="120" />
								<col width="" />
								<col width="160" />
								<col width="" />
							</colgroup>
							<tr>
								<th>예약일시<span>*</span></th>
								<td colspan="3">
									<input type="text" class="inp" id="recptDate2" readonly/> 
									<select id="recptHour2" style="width: 70px; height:40px; margin-left:10px;">
										<c:forEach var="index" begin="0" end="23" step="1">
											<option value="${index}">${index}</option>
										</c:forEach>
									</select>									
									<span class="etxt" style=" float: none;">:</span>
									<select id="recptMin2" style="width: 70px; height:40px;">
										<option value="0">00</option>
										<option value="15">15</option>
										<option value="30">30</option>
										<option value="45">45</option>
									</select>
								</td> 
							</tr> 
							<tr>
								<th>출발지<span>*</span></th>
								<td>
									<input type="text" id="DEPT_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEPT_FULL_DESC1"/>
									<input type="hidden" id="DEPT_X1"/>
									<input type="hidden" id="DEPT_Y1"/>
									<input type="hidden" id="CHRG_CNTER_CD1"/>
									<a href="javascript:void(0)" class="searchAddress3"><img src="/img/ico/ico_search01.png" class="searchAddress3"/></a>
								</td>
								<th>목적지<span>*</span><br />(대중교통 출발지)</th>
								<td>
									<input type="text" id="DEST_DESC1" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEST_FULL_DESC1"/>
									<input type="hidden" id="DEST_X1"/>
									<input type="hidden" id="DEST_Y1"/>
									<a href="javascript:void(0)" class="searchAddress3"><img src="/img/ico/ico_search01.png" class="searchAddress3"/></a>
								</td>
							</tr> 
							<tr>
								<th>기존 출발/<br />목적지</th>
								<td colspan="3">
									<div class="itemForm">
										<p class="box01"><span>* 기존 출발/목적지 사용 시 체크하세요.</span></p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk startAddrSet1">출발지</a>
											<select class="select searchStartList1" >

											</select>
										</div>
									</div>
									<div class="itemForm">
										<p class="box01">
											<span>* 출발/목적지 바꾸기</span>
											<a href="javascript:void(0)"><img src="/img/ico/ico_change01.png" class="changeStartEnd1"/></a>
										</p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk endAddrSet1">목적지</a>
											<select class="select searchEndList1">
							
											</select>
										</div>
									</div>
								</td> 
							</tr> 
						</table>
		
						<p class="tit02">예약 2</p>
						<table class="sTable01 ptype">
							<colgroup>
								<col width="120" />
								<col width="" />
								<col width="160" />
								<col width="" />
							</colgroup>
							<tr>
								<th>예약일시<span>*</span></th>
								<td colspan="3">
									<input type="text" class="inp" id="recptDate3" readonly/> 
									<select id="recptHour3" style="width: 70px; height:40px; margin-left:10px;">
										<c:forEach var="index" begin="0" end="23" step="1">
											<option value="${index}">${index}</option>
										</c:forEach>
									</select>									
									<span class="etxt" style=" float: none;">:</span>
									<select id="recptMin3" style="width: 70px; height:40px;">
										<option value="0">00</option>
										<option value="15">15</option>
										<option value="30">30</option>
										<option value="45">45</option>
									</select>
								</td> 
							</tr> 
							<tr>
								<th>출발지<span>*</span></th>
								<td>
									<input type="text" id="DEPT_DESC2" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEPT_FULL_DESC2"/>
									<input type="hidden" id="DEPT_X2"/>
									<input type="hidden" id="DEPT_Y2"/>
									<input type="hidden" id="CHRG_CNTER_CD2"/>
									<a href="javascript:void(0)" class="searchAddress4"><img src="/img/ico/ico_search01.png" class="searchAddress4"/></a>
								</td>
								<th>목적지<span>*</span><br />(대중교통 출발지)</th>
								<td>
									<input type="text" id="DEST_DESC2" class="inp p03" maxlength="50" disabled="disabled" value="" placeholder="출발지 선택 시 자동 입력"/>
									<input type="hidden" id="DEST_FULL_DESC2"/>
									<input type="hidden" id="DEST_X2"/>
									<input type="hidden" id="DEST_Y2"/>
									<a href="javascript:void(0)" class="searchAddress4"><img src="/img/ico/ico_search01.png" class="searchAddress4"/></a>
								</td>
							</tr> 
							<tr>
								<th>기존 출발/<br />목적지</th>
								<td colspan="3">
									<div class="itemForm">
										<p class="box01"><span>* 기존 출발/목적지 사용 시 체크하세요.</span></p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk startAddrSet2">출발지</a>
											<select class="select searchStartList2" >

											</select>
										</div>
									</div>
									<div class="itemForm">
										<p class="box01">
											<span>* 출발/목적지 바꾸기</span>
											<a href="javascript:void(0)"><img src="/img/ico/ico_change01.png" class="changeStartEnd2"/></a>
										</p>
										<div class="box02">
											<a href="javascript:void(0)" class="btnChk endAddrSet2">목적지</a>
											<select class="select searchEndList2" >
							
											</select>
										</div>
									</div>
								</td> 
							</tr> 
						</table>
						 
		
						<p class="tit02">부가정보</p>
						<table class="sTable01 ptype">
							<colgroup>
								<col width="120" />
								<col width="" />
								<col width="140" />
								<col width="" />
							</colgroup>
							<tr>
								<th>이동목적<span>*</span></th>
								<td colspan="3"><select class="select" id="MVMN_PURPS_CD"><option>선택</option></select></td>
								<%--<th>왕복여부<span>*</span></th>--%>
								<%--<td>--%>
									<%--<select class="select" id="ROUNDTRIP_AT">--%>
										<%--<option value="Y">예</option>--%>
										<%--<option value="N" selected>아니오</option>--%>
									<%--</select>--%>
								<%--</td>--%>
							</tr> 
							<tr>
								<th>비고</th>
								<td colspan="3"><textarea class="textarea" id="RM" placeholder="기타 특이사항이 있으시면 입력해주세요."></textarea></td> 
							</tr>
						</table>
			 
		
						<div class="btnBox"> 
							<a href="javascript:$recptSttus.recptSttusAdd()" class="btnType01">저장</a>
							<a href="javascript:void(0)" onclick="hidePopup()" class="btnType02">취소</a>
						</div>
		
						<ul class="petcList">
							<li>* 필수입력 항목</li>
							<li>* 광역 간 이동 시 왕복운행이 안됩니다. 추가로 차량 예약요청을 해주세요.</li>
							<li>* 광역 간 이동 시 예약요청이 2건 발생합니다. </li>
						</ul>
					</div>
					<!-- ======================================================================================================= -->
					<div class="pActicle" id="receipForm4" >
						<input type="hidden" id="startFullAddr"/>
						<input type="hidden" id="endFullAddr"/>
						<fieldset class="mt40">
							<span class="label">주소 검색</span>
							<input type="text" class="inp" id="searchWord" style="ime-mode:active;">
							<button type="button" class="btn01" id="searchWordBtn">조회</button> 
						</fieldset> 
						<table class="sTable01 ptype">
							<colgroup>
								<col width="90">
								<col width="310"> 
								<col width="90">
								<col width="310"> 
							</colgroup>
							<tr>
								<th>출발지 주소</th>
								<td><input type="text" class="inp" id="startAddr" readonly style="cursor:default; border:none"/></td>
								<th>목적지 주소</th>
								<td><input type="text" class="inp" id="endAddr" readonly style="cursor:default; border:none"/></td>
							</tr>  
						</table>
						<div class="btnBox ar">
							<a href="javascript:$recptSttus.saveDirection();" class="btnType01">저장</a>
							<a href="javascript:void(0)" class="btnType02" id="form4Close">취소</a>
						</div>
							<p class="tit02">지역검색 목록</p>
							<p class="ptxt02">* 최대 30건만 조회됩니다.</p>
							<a class="btnType01 searchAddr" id="searchAddr">주소검색</a>
							<a class="btnType01 searchAddr" style="display:none;" id="searchAddrE">목적지</a>
							<a class="btnType01 searchAddr" style="display:none;" id="searchAddrS">출발지</a>
						<table class="listTable">
							<colgroup> 
								<col width="250" />
								<col width="" /> 
								<col width="250" /> 
							</colgroup>
							<tr><th>명칭</th><th>주소</th><th>선택</th></tr>
							<tr name='noData'><td colspan='3'>지역검색 목록에 데이터가 없습니다. 주소검색으로 찾아주세요.</td></tr>
						</table>
						<div class="paging"></div>
					</div>
				</div>
			</form>
		</div>  
	</div>


	<input type="hidden" id="formNo" />
	<input type="hidden" id="inputNo" />
	<input type="hidden" id="MVMN_TY_CD1" />
	<input type="hidden" id="MVMN_TY_CD2" />
	<input type="hidden" id="EXPECT_REQRE_TIME1" />
	<input type="hidden" id="EXPECT_REQRE_TIME2" />
	<input type="hidden" id="EXPECT_MVMN_DSTNC1" />
	<input type="hidden" id="EXPECT_MVMN_DSTNC2" />
	<input type="hidden" id="EXPECT_CYCHG1" />
	<input type="hidden" id="EXPECT_CYCHG2" />
	<input type="hidden" id="START_CNTER_CD" />
	<input type="hidden" id="STRTPNT_FULL_ADRES "/>
	<input type="hidden" id="ALOC_FULL_ADRES "/>

	<%--KOBUS예약_요청폼--%>
	<input type="hidden" id="STATUS" value="${STATUS}"/>
	<input type="hidden" id="JOIN_TYPE" value="${JOIN_TYPE}"/>
	<input type="hidden" id="RESVE_NO" value="${RESVE_NO}"/>
	<input type="hidden" id="RUN_TYPE" value="${RUN_TYPE}"/>
	<input type="hidden" id="BOARDING_TIME" value="${BOARDING_TIME}"/>
	<input type="hidden" id="REQUIRE_TIME" value="${REQUIRE_TIME}"/>
</body>
</html>