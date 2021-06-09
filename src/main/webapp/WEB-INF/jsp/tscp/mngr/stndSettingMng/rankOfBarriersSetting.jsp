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
	<script type="text/javascript" src="/js/tscp/mngr/stndSettingMng/rankOfBarriersSetting.js"></script>
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
							<option value="/view/mngr/stndSettingMng/rankOfBarriersSetting.do" selected="selected">센터이용 가능 장애등급 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/chargeForUsingSetting.do">이용요금 설정 정보</option>  
							<option value="/view/mngr/stndSettingMng/bookAbleSetting.do">예약 가능시간 기준</option>  
							<option value="/view/mngr/stndSettingMng/memberStipulationSetting.do">회원약관정보</option>  
							<option value="/view/mngr/stndSettingMng/carInfoShareSetting.do">차량정보공유</option>
						</select>
					</td> 
				</tr> 
			</table>

			<p class="tit01">센터이용 가능 장애등급 설정 정보</p>

			<div class="boardHeader01">
				<div class="t01">
					<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_plus.png" id="plusBtn"/></a>
					<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_mius.png" id="minusBtn"/></a>
				</div>
				<span class="t02">* 필수입력 항목</span>
			</div>
			<table class="sTable01">
				<colgroup>
					<col width="60" />
					<col width="110" /> 
					<col width="" />
					<col width="110" />
					<col width="" />
				</colgroup>
				<tbody id="tableTbody">
					<tr>
						<th rowspan="2">1</th>
						<th>장애종류<span>*</span></th>
						<td><select class="select"><option>지적장애</option></select></td>
						<th>장애등급<span>*</span></th>
						<td><input type="text" class="inp" placeholder="장애등급" /></td>
					</tr>
					<tr> 
						<th>기타<span>*</span></th>
						<td colspan="3">
							<input type="checkbox" id="" />
							<label for="">휠체어 사용 시 가능</label>
							<input type="checkbox" id="" class="ml20" />
							<label for="">보호자 동반 시 가능</label>
						</td> 
					</tr>
					
					<tr id="tr2">
						<th rowspan="2">2</th>
						<th>장애종류<span>*</span></th>
						<td><select class="select"><option>지적장애</option></select></td>
						<th>장애등급<span>*</span></th>
						<td><input type="text" class="inp" placeholder="아이디" /></td>
					</tr>
					<tr> 
						<th>기타<span>*</span></th>
						<td colspan="3">
							<input type="checkbox" id="" />
							<label for="">휠체어 사용 시 가능</label>
							<input type="checkbox" id="" class="ml20" />
							<label for="">보호자 동반 시 가능</label>
						</td> 
					</tr>
					
					<tr id="tr3">
						<th rowspan="2">3</th>
						<th>장애종류<span>*</span></th>
						<td><select class="select"><option>지적장애</option></select></td>
						<th>장애등급<span>*</span></th>
						<td><input type="text" class="inp" placeholder="아이디" /></td>
					</tr>
					<tr> 
						<th>기타<span>*</span></th>
						<td colspan="3">
							<input type="checkbox" id="" />
							<label for="">휠체어 사용 시 가능</label>
							<input type="checkbox" id="" class="ml20" />
							<label for="">보호자 동반 시 가능</label>
						</td> 
					</tr>
				</tbody>
			</table>

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