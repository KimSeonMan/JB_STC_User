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
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/memberStipulationSetting.js"></script>
	<script src='/js/plugins/ckeditor/ckeditor.js'></script>
	<link rel="stylesheet" href="/publishCss/css/default.css" type="text/css" /> 
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
							<option value="/view/mngr/stndSettingMng/chargeForUsingSetting.do">이용요금 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/bookAbleSetting.do">예약 가능시간 기준</option>  
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do" selected="selected">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">회원약관정보</p>

			<p class="agreeSubj">회원약관</p>
			<textarea class="agreeBox" id="MBER_CN" cols="113">OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
				OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
				OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
			</textarea>

			<p class="agreeSubj">개인정보, 위치정보 수집·이용 및 제공에 관한 동의</p>
			<textarea class="agreeBox" id="OTHER_CN" cols="113">OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
				OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
				OOO이동지원센터 서비스 에서는 고객의 개인정보를 중요시 하며, OOO 이동지원센터 서비스및 특별교통수단의 원활한 서비스 제공
				<br />「개인정보보호법」 제15조, 제17조 및 제22조에 따라 아래와 같이 귀하의 개인정보 수집 및 이용, 제공에 관한 동의를 얻고자 합니다.다음의 
				<br />사항에 대해 충분히 읽어 보신 후, 동의 여부를 체크, 서명하여 주십시오.

				<br /><br />1. 개인정보 수집 ? 이용 목적
			</textarea> 

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