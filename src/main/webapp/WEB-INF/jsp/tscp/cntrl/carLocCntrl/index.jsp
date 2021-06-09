<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="fullType">
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0d275dc10b1bb95131478c3be6433bd6&libraries=services"></script>
	<script type="text/javascript" src="/js/tscp/cntrl/map.js"></script>
	<script type="text/javascript" src="/js/tscp/cntrl/carLocCntrl/carLocCntrlMap.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container2">
			<div class="gpsHeader">
				<div class="fl">
					<!-- <img src="/img/ico/ico_weather01.png" /> -->
					<!-- <span>대전광역시 서구 둔산2동</span> -->
				</div>
				<div class="fr btn">
					<span>대중교통정보</span>
					<div class="switchBtn">
						<a href="javascript:void(0)">on</a>
						<a href="javascript:void(0)">off</a>
					</div>
				</div>
			</div>
			
			<div class="gpsCont">
				<div class="gpsLeft"><!-- gpsLeft  -->
					<div class="glTop">
						<span>위치정보 관제</span>
						<select class="select">
							<option value="11">서울특별시</option>
							<option value="25">대전광역시</option>
							<option value="22">대구광역시</option>
							<option value="21">부산광역시</option>
							<option value="24">광주광역시</option>
						</select>
						<select class="select">
							<option value="01">나비콜</option>
							<option value="02">강남콜</option>
						</select>
					</div>
					<div class="icoArea">
						<ul>
							<li><a href="javascript:void(0)" class="ico01">실시간차량<br />관제</a></li>
							<li><a href="javascript:alert('준비중입니다.')" class="ico03">길찾기<br /></a></li>
						</ul>
					</div>
					<div class="resultArea">
						<p class="tit" id="resultAreaSubject">실시간 차량 위치관제</p>
						<table class="searhForm">
							<tr>
								<th>검색대상</th>
								<td>
									<select class="select" id="searchTarget">
										<option value="all">전체</option>
										<option value="car">차량</option>
										<option value="supporter">이동보조원</option>
										<option value="user">이동약자</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>지역검색</th>
								<td>
									<select class="select" id="searchLocation">
										<option value="11">서울특별시</option>
										<option value="25">대전광역시</option>
										<option value="22">대구광역시</option>
										<option value="21">부산광역시</option>
										<option value="24">광주광역시</option>
									</select>
								</td>
							</tr>
							<tr>
								<th><span id="searchKeySpan">차량번호</span></th>
								<td>
									<div class="searchBox">
										<input type="text" name="searchWord" id="searchWord"/>
										<a href="javascript:void(0)"><img src="/img/ico/ico_search01.png" /></a> 
									</div>
								</td>
							</tr>
						</table>
						<div class="btnbox">
							<a href="javascript:$carLocCntrlMap.searchCntrlList('all');" class="btn01">검색</a>
						</div>
	
						<div class="resultText" id="resultText" style="display:none;"></div>
						<ul class="resultTabs" id="selectResultTabs">
							<li class="on" id="car"><a href="javascript:void(0)">차량</a></li>
							<li id="user"><a href="javascript:void(0)">이용자</a></li>
							<li id="support"><a href="javascript:void(0)">이동보조원</a></li>
						</ul>
						<table class="gpsTable" id="gpsTable">
							
						</table>
						<div class="paging" id="pageList"></div>
					</div>
					<div class="leftToggle">
						<a href="javascript:void(0)"><img src="/img/ico/ico_left.png" /></a>
					</div>
				</div><!-- end gpsLeft  -->
				
				<div class="gpsMap"><!-- gpsMap  -->
					<!-- <img src="/img/etc/etc_testmap.jpg" width="100%" height="100%" /> -->
					<div id="gMapArea" style="width:100%;height:100%;"></div>
					<!--레이어1-->
					<%-- <div class="infoLayer" style="left:50%;top:100px;z-index:10000">
						<div class="rela">
							<p class="subj">운행차량 정보(2016.12.15/17시20분)</p>
							<a href="javascript:void(0)" class="btnClose" onclick="layerClose('.infoLayer')"><img src="/img/ico/ico_close01.png" /></a>
							<table class="layerTable">
								<colgroup>
									<col width="60" />
									<col width="" />
									<col width="60" />
									<col width="" />
								</colgroup>
								<tr>
									<th>차량위치</th>
									<td colspan="3"><input type="text" name="" class="inp" /></td>
								</tr>
								<tr>
									<th>위치좌표</th>
									<td><input type="text" name="" class="inp" /></td>
									<th>&nbsp;</th>
									<td><input type="text" name="" class="inp" /></td>
								</tr>
								<tr>
									<th>자동차번호</th>
									<td><input type="text" name="" class="inp" /></td>
									<th>속도</th>
									<td><input type="text" name="" class="inp" /></td>
								</tr>
								<tr>
									<th>운전자명</th>
									<td><input type="text" name="" class="inp" /></td>
									<th>연락처</th>
									<td><input type="text" name="" class="inp" /></td>
								</tr>
							</table>
							<div class="btnbox">
								<a href="javascript:void(0)" class="btn01">상세정보 보기</a>
								<a href="javascript:void(0)" class="btn01">배차현황 보기</a>
							</div>
						</div>
					</div> --%>
					<!-- end 레이어1-->
	
					<!--범례-->
					<div class="legendLayer" style="z-index:10000">
						<div class="rela">
							<p class="subj">범례</p>
							<a href="javascript:void(0)" class="btnClose" onclick="layerClose('.legendLayer')"><img src="/img/ico/ico_close01.png" /></a>
							<ul class="legendList">
								<li class="ico01 on"><a href="javascript:void(0)">차량(공차)</a></li>
								<li class="ico02"><a href="javascript:void(0)">차량(실차)</a></li>
								<li class="ico03"><a href="javascript:void(0)">이동 보조원</a></li>
								<li class="ico04"><a href="javascript:void(0)">이용자</a></li>
							</ul>
						</div>
					</div>
					<!-- end 범례-->
				</div><!-- end gpsMap  -->
			</div>
		</div>
		
		<!-- footer// -->
		<jsp:include page="/view/common/includeFooter"></jsp:include>
	</div>
</body>
</html>