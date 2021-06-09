<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>이동지원센터</title>
 
<script type="text/javascript" src="/js/common/includeSource.js"></script>

<!-- <link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> -->
<script type="text/javascript" src="/js/tscp/LocInfoSystem/locmap.js"></script>
<!-- <script type="text/javascript" src="/publishCss/js/ui.js"></script>     -->
<!-- <script type="text/javascript" src="/publishCss/js/common.js"></script>   -->
<!-- <link rel="stylesheet" href="/publishCss/css/default.css" type="text/css" /> --> 

<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=ko&key=AIzaSyAYhjFtveQfAWal67geeWdQUKbMFJMXqoY"></script> -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0d275dc10b1bb95131478c3be6433bd6&libraries=services"></script>
</head>
<body>
<div class="wrapper full"><!-- start : wrapper -->

 	<!-- header -->
 	<jsp:include page="/view/common/includeHeader"></jsp:include>
	
	<div class="container"><!-- start : container --> 
		
		<div class="mLeft">
			<h2>위치 관제</h2>
			<div class="cont">
			<form:form name="frm" id="frm" modelAttribute="command" method="post">
			<form:hidden id="pageNo" path="page.pageNo"/>
				<p class="subj01">배차현황</p>
				<table class="sTable01">
					<colgroup>
						<col width="100" />
						<col width="" />
					</colgroup>
					<tr>
						<th>센터구분</th>
						<td><select class="select"><option>선택</option></select></td>
					</tr>
					<tr>
						<th>운전자상태</th>
						<td>
							<form:select path="drverSttusCd" class="select">
								<form:option value="ALL">선택</form:option>
								<form:options items="${drverSttusCd}"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<th>운전자명</th>
						<td>
							<form:input type="text" class="inp" path="drver_name"/>
							<a href="javascript:void(0);" id="btnSearch" class="btn01" style="float:right !important;">찾기</a>
						</td>
					</tr>
				</table>
				<table class="listTable mt20">
					<colgroup>
						<col width="60" />
						<col width="60" />
						<col width="80" />
						<col width="" />
						<col width="80" />
					</colgroup>
					<tr>
						<th>소속센터</th>
						<th>운전자명</th>
						<th>목적지</th>
						<th>차량번호</th>
						<th>운전자상태</th>
					</tr>
					<c:choose>
						<c:when test="${empty dataSet }">
							<tr>
								<td colspan="5" align="center"> No Data </td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="data" items="${dataSet.locInfoDomainList }" varStatus="status">
								<c:if test="${data.wheelchair_fixing_sttus_cd == '10'}">
									<tr class="cell01">
								</c:if>
								<c:if test="${data.wheelchair_fixing_sttus_cd == '00'}">
									<tr>
								</c:if>
										<td>${data.cnter_nm }</td>
										<td><a href="javascript:$locmap.ui.moveCenter('${data.lat}','${data.lon}','${status.index}')"  class="link">${data.mber_nm }</a></td>
										<td>${data.aloc_adres }</td>
										<td>${data.vhcle_no }</td>
										<td>${data.drver_sttus_nm }</td>
									</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="paging mt10">
				<jsp:include page="/view/common/page"></jsp:include>
				</div>

				<p class="subj01">범례 <span>휠체어고정장치 이상</span></p>
				</form:form>
			</div>
		</div>
		
		<div class="mRight">
			<div class="status" id = "em"> 
			<c:choose>
				<c:when test="${empty emrgncy }"></c:when>
				<c:otherwise>
					<p><strong>[긴급호출]</strong> 회원구분 : ${emrgncy.mber_se_nm}/ 이용자명 : ${emrgncy.mber_name} / 소속센터 : ${emrgncy.cnter_nm}</p>
					<a href="javascript:void(0)" id="btnMoveEmrgncy" class="btn01">현재위치</a>
					<a href="javascript:void(0)" class="btn01">확인</a>
				</c:otherwise>
			</c:choose>
			</div>
			<div class="mapArea">

				<div id="map_canvas" style="width:100%;height:100%;"></div>
				
				<a href="javascript:void(0)" class="mapToggle"><img src="/img/btn/btn_toggle01.png" /></a>
				<div class="mapLengend">
					<div class="mlheader">
						<span>범례(명)</span>
						<a href="javascript:void(0)" onclick="layerClose('.mapLengend')"><img src="/img/btn/btn_close02.png" /></a>
					</div>
					<ul>
						<li><a href="javascript:void(0)"><img src="/img/ico/ico_legendList01.png" /></a></li>
						<li><a href="javascript:void(0)"><img src="/img/ico/ico_legendList02.png" /></a></li>
						<li><a href="javascript:void(0)"><img src="/img/ico/ico_legendList03.png" /></a></li>
						<li><a href="javascript:void(0)"><img src="/img/ico/ico_legendList04.png" /></a></li>
					</ul>
				</div>
			</div>
		</div>

	</div><!-- end : container -->
 

<script type="text/javascript">

$(document).ready(function(){
	var level = 3;
	if("${dataSet.total}">0){
		var varlat = "${dataSet.lcCrdntX}";
		var varlon = "${dataSet.lcCrdntY}";
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
		settingMap2();
	} else {
		var varlat = 33.450701;
		var varlon = 126.570667;
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
	}
	
	// 이벤트.
	initEvent();
});

// function autoRefresh_div()
// {
// 	var currentLocation = window.location;
// 	$("#status").load(currentLocation + ' #status');
// }
// setInterval('autoRefresh_div()', 3000);


function settingMap2(){
	<c:forEach items="${dataSet.locInfoDomainList}" var="locLc" varStatus="status">
	var popUpHtml =	'<div class="mapInfo" style="z-index:20000;top:-230px;left:-135px;">';
	popUpHtml +=		'<ul>';
	popUpHtml +=			'<li>';
	popUpHtml +=				'<span class="t01">운전자명 :</span>';
	popUpHtml +=				'<span class="t02">' + "${locLc.drver_nm}" +'</span>';
	popUpHtml +=			'</li>';
	popUpHtml +=			'<li>';
	popUpHtml +=				'<span class="t01">차량번호 :</span>';
	popUpHtml +=				'<span class="t02">' + "${locLc.vhcle_no}" +'</span>';
	popUpHtml +=			'</li>';
	popUpHtml +=			'<li>';
	popUpHtml +=				'<span class="t01">이용자명 :</span>';
	popUpHtml +=				'<span class="t02">' + "${locLc.handi_mber_nm}" +'</span>';
	popUpHtml +=			'</li>';
	popUpHtml +=			'<li>';
	popUpHtml +=				'<span class="t01">장애종류 및 등급 :</span>';
	popUpHtml +=				'<span class="t02">' + "${locLc.trobl}" +'</span>';
	popUpHtml +=			'</li>';
	popUpHtml +=			'<li>';
	popUpHtml +=				'<span class="t01">소속센터 :</span>';
	popUpHtml +=				'<span class="t02">' + "${locLc.cnter_nm}" +'</span>';
	popUpHtml +=			'</li>';
	popUpHtml +=		'</ul>';
	popUpHtml +=	'</div>';
	
			var iwContent = '<div style="padding:5px;">'+ "${locLc.vhcle_no}" + '</div>';
			
			var lat = "${locLc.lat}";
			var lon = "${locLc.lon}";
			var index = "${status.index}";
			$locmap.ui.createMarker(lat, lon);
			$locmap.ui.contentPopup(popUpHtml, index);
			$locmap.ui.createInfoWindow(lat, lon, iwContent, index);
	</c:forEach>
};

/**
 * 이벤트 세팅.
 */
var initEvent = function() {
	 
	// 검색.
	$("#btnSearch").on("click", function() {
		goPage(1); // 검색하면 1페이지로 이동.
	});
	
	$("#btnMoveEmrgncy").on("click", function(){
		moveCenter();
	});
};	
var goPage = function(pageNo){
 	$("#frm #pageNo").val(pageNo);
 	$("#frm").action = "<c:url value='/view/locInfo/locInfo.do'/>";
 	$("#frm").submit();
 };
 
 var moveCenter = function(){
		var lon = "${emrgncy.lon}";
		var lat = "${emrgncy.lat}";
		var mber_se = "${emrgncy.mber_se_nm}";
		var mber_nm = "${emrgncy.mber_name}";
		var cnter_nm = "${emrgncy.cnter_nm}";
	 var popUpHtml =	'<div class="mapInfo" style="left:400px;top:100px;">';
		popUpHtml +=		'<ul>';
		popUpHtml +=			'<li>';
		popUpHtml +=				'<span class="t01">회원구분 :</span>';
		popUpHtml +=				'<span class="t02">' + mber_se +'</span>';
		popUpHtml +=			'</li>';
		popUpHtml +=			'<li>';
		popUpHtml +=				'<span class="t01">이용자명 :</span>';
		popUpHtml +=				'<span class="t02">' + mber_nm +'</span>';
		popUpHtml +=			'</li>';
		popUpHtml +=			'<li>';
		popUpHtml +=				'<span class="t01">소속센터 :</span>';
		popUpHtml +=				'<span class="t02">' + cnter_nm +'</span>';
		popUpHtml +=			'</li>';
		popUpHtml +=		'</ul>';
		popUpHtml +=	'</div>';
	 
	 
	 $locmap.ui.moveEmrgncyCenter(lat, lon, popUpHtml);
 };

</script>
</div><!-- end : wrapper-->
</body>
</html>