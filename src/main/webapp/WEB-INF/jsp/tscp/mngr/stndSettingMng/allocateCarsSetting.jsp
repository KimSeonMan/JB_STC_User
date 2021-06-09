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
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/allocateCarsSetting.js"></script>
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<!-- <link rel="stylesheet" href="/publishCss/css/default.css" type="text/css" /> --> 
</head>
<body>
<div class="wrapper">
	<!-- header // -->
	<jsp:include page="/view/common/includeHeader"></jsp:include>
	<div class="container"><!-- start : container --> 
		<div class="lnb"></div><!-- lnb -->

		<div class="acticle">
<!-- 			<div class="subLocation"> -->
<!-- 				<h3>기준설정 관리</h3> -->
<!-- 				<ul> -->
<!-- 					<li><img src="/publishCss/img/ico/ico_home.png" /></li> -->
<!-- 					<li>관리자 관리</li>  -->
<!-- 					<li class="last">기준설정 관리</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
			<ul class="location"></ul>
			<h3 class="subTitle"></h3>

			<table class="sTable01">
				<colgroup>
					<col width="120" />
					<col width="*" /> 
				</colgroup>
				<tr>
					<th>구분</th>
					<td>
						<select class="long" onchange="if( this.value ) window.location.href = this.value;">
							<option value="/view/mngr/stndSettingMng/allocateCarsSetting.do" selected="selected">배차관련 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/rankOfBarriersSetting.do">센터이용 가능 장애등급 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/chargeForUsingSetting.do">이용요금 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/bookAbleSetting.do">예약 가능시간 기준</option>  
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">배차관련 설정 정보</p>

			<div class="boardHeader01">
				<span class="t01">공통</span>
				<span class="t02">* 필수입력 항목</span>
			</div>
			<table class="sTable01">
				<colgroup>
					<col width="140" />
					<col width="160" /> 
					<col width="" /> 
				</colgroup>
				<tr>
					<th>왕복허용여부<span>*</span></th>
					<td class="br">
						<select id="ROUNDTRIP_PERM_AT" name="ROUNDTRIP_PERM_AT">
							<option value="Y" >예</option>
							<option value="N" selected>아니요</option>
						</select>
					</td>
					<td class="bl">* 왕복으로 차량 예약요청이 가능한 가?</td>
				</tr> 
				<tr>
					<th>목적지소요시간(분)</th>
					<td class="br"><input type="text" id="ALOC_REQRE_TIME" name="ALOC_REQRE_TIME" class="inp" placeholder="분단위로 숫자만 입력하세요" value="${carSetting.aloc_reqre_time }"/> </td>
					<td class="bl">* 왕복 예약요청이 가능하다면 이용자가 목적지 도착 후 소요시간을 제한할 것인가?
						<br />* 입력값 없으면 소요시간 제한이 없음으로 판단
					</td>
				</tr> 
				<tr>
					<th>차량검색반경(km)<span>*</span></th>
					<td class="br"><input type="text" id="VHCLE_SEARCH_RADIUS" name="VHCLE_SEARCH_RADIUS" class="inp" placeholder="km단위로 숫자만 입력하세요" value="${carSetting.vhcle_search_radius }"/> </td>
					<td class="bl">* 자동배차 시 예약요청 출발지 차량 검색 반경(km)</td>
				</tr> 
				<tr>
					<th>사전예약시간간격<span>*</span></th>
					<td class="br"><input type="text" id="BEFFAT_RESVE_TIME_INTRVL" name="BEFFAT_RESVE_TIME_INTRVL" class="inp" placeholder="분단위로 숫자만 입력하세요" value="${carSetting.beffat_resve_time_intrvl }"/> </td>
					<td class="bl">* 사전예약 시 예약 간 시간 간격(분)</td>
				</tr> 
			</table>
			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01" id="basicSave">등록</a>
			</div>

			<div class="boardHeader01">
				<span class="t01">광역 간 차량이동 기준</span>
				<span class="t02">* 필수입력 항목</span>
			</div>
			<table class="sTable01">
				<colgroup>
					<col width="140" />
					<col width="160" /> 
					<col width="" /> 
				</colgroup>
				<tr>
					<th>차량이동가능여부<span>*</span></th>
					<td class="br">
						<select id="VHCLE_MVMN_POSBL_AT" name="VHCLE_MVMN_POSBL_AT" >
							<option value="Y">예</option>
							<option value="N" selected>아니요</option>
						</select>
					</td>
					<td class="bl">* 출발지(성남) ~ 목적지(인천) 성남차량으로 이동 가능한 가?</td>
				</tr> 
				<tr>
					<th>가능한 거리(km)</th>
					<td class="br"><input type="text" id="POSBL_DSTNC" name="POSBL_DSTNC" class="inp" placeholder="숫자만 입력하세요 . Ex)10" value="${carSetting.posbl_dstnc }"/> </td>
					<td class="bl">* 출발~도착지 거리로 차량이동가능여부를 제한할 것인가?
						<br />* 입력값 없으면 거리제한 없음으로 판단
					</td>
				</tr> 
				<tr>
					<th>타지자체 회원사용<span>*</span></th>
					<td class="br">
						<select id="WDR_SCTN_OTHINST_MBER_USE_POSBL_AT" name="WDR_SCTN_OTHINST_MBER_USE_POSBL_AT">
							<option value="Y">예</option>
							<option value="N" selected>아니요</option>
						</select>
					</td>
					<td class="bl">* 출발지(성남) ~ 목적지(인천) 이용자(용인)일 경우 성남차량으로 이동 가능한 가?</td>
				</tr>   
			</table>

			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01" id="WDR_SCTNSave">등록</a>
			</div>
			
			<div class="boardHeader01">
				<span class="t01">지역 내 차량이동 기준</span>
				<span class="t02">* 필수입력 항목</span>
			</div>
			<table class="sTable01">
				<colgroup>
					<col width="140" />
					<col width="160" /> 
					<col width="" /> 
				</colgroup>
				<tr>
					<th>타지자체 회원사용<span>*</span></th>
					<td class="br">
						<select id="WHTHRC_OTHINST_MBER_USE_POSBL_AT" name="WHTHRC_OTHINST_MBER_USE_POSBL_AT">
							<option value="Y">예</option>
							<option value="N" selected>아니요</option>
						</select>
					</td>
					<td class="bl">* 출발지(성남) ~ 목적지(성남) 이용자(용인)일 경우 차량 이용 가능한 가?</td>
				</tr>    
			</table>

			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01" id="WHTHRCSave">등록</a>
			</div>
		</div>
		 

	</div><!-- end : container -->
	 
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>

</div><!-- end : wrapper-->


</body>
</html> 