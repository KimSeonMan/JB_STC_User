<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type="text/javascript" src="/js/tscp/stats/carLocUseStats/carLocUseStats.js"></script>
	<!-- <script src="/js/common/common.js"></script> -->
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
				
				<p class="etcTitle">운행 현황 통계</p> 
				<table class="sTable01">
					<tr>
						<th>기간구분</th>
						<td>
							<select id="dateStandard" class="inp">
								<option value="day">일별</option>
								<option value="mon">월별</option>
							</select>
						</td>
						<th>
							통계구분
						</th>
						<td>
							<select id="statsStandard" class="inp">
								<option value="date">기간별</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th>검색일</th>
						<td colspan="2">
							<input type="text" id="ST_DT" class="inp datepicker" style="width:120px;" disabled="disabled" /> 
							<span>~</span>
							<input type="text" id="EN_DT" class="inp datepicker" style="width:120px;" disabled="disabled" /> 
						</td>
						<td class="ar"><a href="javascript:$carLocUseStats.ui.searchUseStat();" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">운행 현황 통계목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0);" class="btn02">목록보기</a>
						<a href="javascript:$carLocUseStats.ui.showGraph1();" class="btn02">금일 이용자 현황</a>
						<a href="javascript:$carLocUseStats.ui.showGraph2();" class="btn02">일일 배차현황</a>
						<a href="javascript:$carLocUseStats.ui.downLoadExcel();" class="btn02">엑셀다운로드</a>
					</div>
				</div>
				<form id="listTable">
					
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="graph1" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">금일 이용자 현황</p>
			
			<div class="cont">
				<form id="contentsMngAccessForm">
					<div class="cont" id="mainCharts01"></div>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
				</div>
			</div>  
		</div>
	</div>
	
		<div class="dialog" id="graph2" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">일일 배차현황</p>
			
			<div class="cont">
				<form id="contentsMngAccessForm">
					<div class="cont" id="mainCharts02"></div>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
				</div>
			</div>  
		</div>
	</div>
	
	
</body>
</html>