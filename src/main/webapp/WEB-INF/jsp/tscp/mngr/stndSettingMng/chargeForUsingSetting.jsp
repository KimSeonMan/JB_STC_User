<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/chargeForUsingSetting.js"></script>
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> 
	<title>이동지원센터</title>
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
<!-- 					<li><img src="/publishCss/img/ico/ico_navi.png" /></li> -->
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
							<option value="/view/mngr/stndSettingMng/allocateCarsSetting.do">배차관련 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/rankOfBarriersSetting.do">센터이용 가능 장애등급 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/chargeForUsingSetting.do" selected="selected">이용요금 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/bookAbleSetting.do">예약 가능시간 기준</option>  
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">이용요금 설정 정보</p>

			<div class="boardHeader01"> 
				<span class="t02">* 필수입력 항목</span>
			</div>
			<table class="sTable01">
				<colgroup> 
					<col width="110" /> 
					<col width="" /> 
					<col width="" />
				</colgroup>
				<tbody id="tableTbody">
					<tr> 
						<th>기본요금<span>*</span></th>
						<td class="br">
							<input type="hidden" id="SCTN_BEGIN_DSTNC_0" />
							<input type="hidden" id="SN" />
							<input type="text" class="inp w60 inp-float" id="SCTN_END_DSTNC_0"/>
							<span class="etxt">km</span> 
							<input type="text" class="inp w60 ml20 inp-float" id="CYCHG_0"/>
							<span class="etxt">원</span>
						</td> 
						<td class="bl ar">
							<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_plus.png" /></a>
							<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_mius.png" /></a>
						</td>
					</tr>
					<tr> 
						<th rowspan="2">추가요금<span>*</span></th>
						<td colspan="2">
							<input type="text" class="inp w60 inp-float" />
							<span class="etxt">~</span> 
							<input type="text" class="inp w60 inp-float" />
							<span class="etxt">km</span>
							<input type="text" class="inp w60 ml20 inp-float" />
							<span class="etxt">원/1km</span>
						</td>  
					</tr>
					<tr>  
						<td colspan="2">
							<input type="text" class="inp w60 inp-float" />
							<span class="etxt">~</span> 
							<input type="text" class="inp w60 inp-float" />
							<span class="etxt">km</span>
							<input type="text" class="inp w60 ml20 inp-float" />
							<span class="etxt">원/1km</span>
						</td>  
					</tr>
					<tr> 
						<th>시외요금<span>*</span></th>
						<td colspan="2">
							<input type="text" class="inp w60 inp-float" />
							<span class="etxt" id="CNTRYSIDE">%</span>  
						</td>  
					</tr>
				</tbody>
			</table>

			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01" id="saveBtn">등록</a>
			</div>


			
			  
			 
		 

		</div>
		 

	</div><!-- end : container -->
	 
	<div class="footer"><!-- start : footer -->  
		<jsp:include page="/WEB-INF/jsp/common/includeFooter.jsp"></jsp:include>
	</div><!-- end : footer -->  

</div><!-- end : wrapper-->

 

</body>
</html> 