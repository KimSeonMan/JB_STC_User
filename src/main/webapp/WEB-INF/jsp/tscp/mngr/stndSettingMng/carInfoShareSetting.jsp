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
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/carInfoShareSetting.js"></script>
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
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do" selected="selected">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">차량정보공유</p>
 
			<table class="sTable01 mt20">
				<colgroup> 
					<col width="200" /> 
					<col width="" />  
				</colgroup>
				<tbody id="tableTbody">
					<tr> 
						<th>이동지원센터</th>
						<td>
							<input type="radio" name="" id="" />
							<label for="">전체</label> 
							<input type="radio" name="" id="" class="ml20" />
							<label for="">공동배차차량만</label>
						</td>  
					</tr>
					<tr> 
						<th>경기도광역이동지원센터</th>
						<td>
							<input type="radio" name="" id="" />
							<label for="">전체</label> 
							<input type="radio" name="" id="" class="ml20" />
							<label for="">공동배차차량만</label>
						</td>  
					</tr>
					<tr> 
						<th>의왕지역이동지원센터</th>
						<td>
							<input type="radio" name="" id="" />
							<label for="">전체</label> 
							<input type="radio" name="" id="" class="ml20" />
							<label for="">공동배차차량만</label>
						</td>  
					</tr>
					<tr> 
						<th>의정부지역이동지원센터</th>
						<td>
							<input type="radio" name="" id="" />
							<label for="">전체</label> 
							<input type="radio" name="" id="" class="ml20" />
							<label for="">공동배차차량만</label>
						</td>  
					</tr>
				</tbody>
			</table>
			
			<p class="ptxt01">*센터 및 MOU 중인 광역/지역센터에 대한 차량정보공유를 설정한다. </p>

			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01" id="basicSave">등록</a>
			</div>

			
			  
			 
		 

		</div>
		 

	</div><!-- end : container -->
	 
	<div class="footer"><!-- start : footer -->  
		<jsp:include page="/WEB-INF/jsp/common/includeFooter.jsp"></jsp:include>
	</div><!-- end : footer -->  

</div><!-- end : wrapper-->

 

</body>
</html> 