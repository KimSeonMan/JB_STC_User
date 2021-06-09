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
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/bookAbleSetting.js"></script>
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
							<option value="/view/mngr/stndSettingMng/bookAbleSetting.do" selected="selected">예약 가능시간 기준</option>  
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">예약 가능시간 기준</p>
 
			<table class="sTable01 mt20">
				<colgroup> 
					<col width="200" /> 
					<col width="" />  
				</colgroup>
				<tbody id="tableTbody">
					<tr> 
						<th >당일즉시<span>*</span></th>
						<td>
							<input type="text" class="inp w60 inp-float" id="SCTN_BEGIN_TIME_1"/>
							<span class="etxt">~</span> 
							<input type="text" class="inp w60 inp-float" id="SCTN_END_TIME_1"/>
							<span class="etxt">시</span>
							<input type="hidden" id="BEFFAT_RESVE_POSBL_DE_1"/>
							<input type="hidden" id="RCEPT_SE_CD_1"/>
						</td>  
					</tr>
					<tr>  
						<th >사전예약<span>*</span></th>
						<td >
							<input type="hidden" id="RCEPT_SE_CD_2"/>
							<input type="text" class="inp w60 inp-float" id="SCTN_BEGIN_TIME_2"/>
							<span class="etxt">~</span> 
							<input type="text" class="inp w60 inp-float" id="SCTN_END_TIME_2" />
							<span class="etxt inp-float">시</span>
							<input type="text" class="inp w60 ml20 inp-float" id="BEFFAT_RESVE_POSBL_DE_2"/>
							<span class="etxt">일전 예약 시 가능</span>
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