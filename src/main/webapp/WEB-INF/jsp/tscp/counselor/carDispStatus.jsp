<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>이동지원센터</title>
<script type="text/javascript" src="/js/common/includeSource.js"></script>

<link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" /> 
<script type="text/javascript" src="/js/tscp/LocInfoSystem/locmap.js"></script>
<script type="text/javascript" src="/publishCss/js/ui.js"></script>    
<script type="text/javascript" src="/publishCss/js/common.js"></script>  
<script type="text/javascript" src="//dapi.kakao.com/maps/maps3.js?apikey=0d275dc10b1bb95131478c3be6433bd6&libraries=services"></script>
</head>
<body>
<div class="wrapper full"><!-- start : wrapper -->
<jsp:include page="/view/common/includeHeader"></jsp:include>
<div class="container"><!-- start : container --> 
		
		<div class="mLeft">
			<h2>예약신청 현황</h2>
			<div class="cont">
			<form:form name="frm" id="frm" modelAttribute="command" method="post">
			<form:hidden id="pageNo" path="page.pageNo"/>
				<p class="subj01">예약신청 현황</p>
				<table class="sTable01">
					<colgroup>
						<col width="100" />
						<col width="" />
					</colgroup>
					<tr>
						<th>접수구분</th>
						<td>
							<form:select path="rceptSeCd" class="select">
								<form:option value="ALL">선택</form:option>
								<form:options items="${rceptSeCd}"/>
							</form:select>
					</tr> 
					<tr>
						<th>예약자명</th>
						<td>
							<form:input type="text" class="inp" path="mber_name"/>
							<a href="javascript:void(0)" id="btnSearch" class="btn01">찾기</a>
						</td>
					</tr>
				</table>

				<table class="listTable mt20"> 
					<colgroup>
						<col width="" />
						<col width="" />
						<col width="70" />
						<col width="" />
						<col width="" />
						<col width="70" />
					</colgroup>
					<tr>
						<th>예약자명</th>
						<th>접수<br />구분</th>
						<th>예약일시</th>
						<th>이동<br />유형</th>
						<th>대기시간<br />(분)</th>
						<th>접수<br />상태</th>
					</tr>
					<c:choose>
						<c:when test="${empty dataSet.carDisStatusDomainList }">
							<tr>
								<td colspan="6" align="center"> 등록된 예약이 없습니다. </td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="data" items="${dataSet.carDisStatusDomainList }" varStatus="status">
								<tr>
									<td><a href="javascript:showMberInfo()">${data.mber_nm }</a></td>
									<td>${data.rcept_se_nm }</td>
									<td>${data.resve_date}<br/>${data.resve_time }</td>
									<td>${data.mvmn_ty_nm }</td>
									<td>50</td>
									<td><a href="javascript:showData('${data.mber_id}','${data.resve_dt}', '${data.cnterCd }')" class="btn01" >신청</a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="paging" id="paging"></div>
		</form:form>
		<form:form name="frm_detail" id="frm_detail" modelAttribute="command" method="post">
			<form:hidden path="resve_dt" id="resve_dt"/>
			<form:hidden path="mber_id" id="mber_id"/>
			<form:hidden path="start_lc_crdnt_x" id="start_lc_crdnt_x"/>
			<form:hidden path="start_lc_crdnt_y" id="start_lc_crdnt_y"/>
			<form:hidden path="arvl_lc_crdnt_x" id="arvl_lc_crdnt_x"/>
			<form:hidden path="arvl_lc_crdnt_y" id="arvl_lc_crdnt_y"/>
				<p class="subj01">예약신청 상세</p>
				<div class="reqRadio">
					<input type="radio" name="rcept_se_cd" id="rcept_se_cd" value="10"/>
					<label for="10">당일즉시</label>
					<input type="radio" name="rcept_se_cd" id="rcept_se_cd" value="20" class="ml20" />
					<label for="20">사전예약</label>
				</div>

				<table class="sTable01">
					<colgroup>
						<col width="100" />
						<col width="" />
					</colgroup>
					<tr>
						<th>접수상태<span>*</span></th>
							<td>
							<form:select path="caralcSttusCd" class="select full" id="caralc_sttus_cd">
									<form:option value="ALL">선택</form:option>
									<form:options items="${caralcSttusCd}"/>
							</form:select>
							</td>
					</tr> 
					<tr>
						<th>담당센터<span>*</span></th>
						<td>
							<form:select path="cnterCd" class="select full" id="cnter_cd">
								<form:option value="ALL">선택</form:option>
								<form:options items="${cnterCd}"/>
							</form:select>						
						</td>
						
					</tr> 
					<tr>
						<th>예약일시<span>*</span></th>
						<td>
							<form:input type="text" class="inp datepicker"  path="resve_date" id="resve_date"/>
							<form:input type="text" class="inp w20 ml5" path="resve_time" id="resve_time" />
							<span class="etxt">:</span>
							<form:input type="text" class="inp w20" path="resve_min" id="resve_min"/>
						</td>
					</tr> 
					<tr>
						<th>출발지<span>*</span></th>
						<td>
							<input type="text" class="inp full01" id="strtpnt_adres" path="strtpnt_adres"/>
							<a href="javascript:void(0)" class="btnSearch01"><img src="/img/btn/btn_search01.png" /></a>
						</td>
					</tr> 
					<tr>
						<th>목적지<span>*</span></th>
						<td>
							<input type="text" class="inp full01" id="aloc_adres" path="aloc_adres"/>
							<a href="javascript:void(0)" class="btnSearch01"><img src="/img/btn/btn_search01.png" /></a>
						</td>
					</tr> 
					<tr>
						<th>왕복여부<span>*</span></th>
						<td>
							<form:select path="roundtrip_at" id="roundtrip_at" class="select full">
								<form:option value="N">아니오</form:option>
								<form:option value="Y">예</form:option>
							</form:select>
						</td>
					</tr> 
					<tr>
						<th>이동목적<span>*</span></th>
						<td>
							<form:select path="mvmn_purps_cd" class="select full" id="mvmn_purps_cd">
								<form:option value="ALL">선택</form:option>
								<form:options items="${mvmn_purps_cd}"/>
							</form:select>	
						</td>
					</tr> 
					<tr>
						<th>휠체어구분<span>*</span></th>
						<td>
							<form:select path="wheelchair_se_cd" class="select full" id="wheelchair_se_cd">
								<form:option value="ALL">선택</form:option>
								<form:options items="${wheelchair_se_cd}"/>
							</form:select>	
						</td>
					</tr> 
					<tr>
						<th>탑승인원<span>*</span></th>
						<td><form:input type="text" class="inp full" path="brdng_nmpr" id="brdng_nmpr"/></td>
					</tr> 
					<tr>
						<th>비고</th>
						<td><form:textarea class="textarea" path="rm" id="rm"></form:textarea></td>
					</tr> 
				</table>
				
				<div class="btnBox">
					<a href="javascript:car_Resrv()" class="btn01">예약수정</a>
					<a href="javascript:car_Result()" class="btn01">배차가능 차량현황</a>
					<a href="javascript:cnter_info()" class="btn01">센터별 기준정보</a>
				</div>

 				<div class="etcItem">
					<p class="subj01">오프라인</p>
					<a href="javascript:car_request()" class="btn01">예약요청</a>
				</div>
			</form:form>
			</div>			
		</div>
		
		
		<div class="mRight">
			<div id = "em"> 
				<c:choose>
					<c:when test="${empty emrgncy }"></c:when>
					<c:otherwise>
						<div class="status">
								<p><strong>[긴급호출]</strong> 회원구분 : ${emrgncy.mber_se_nm}/ 이용자명 : ${emrgncy.mber_name} / 소속센터 : ${emrgncy.cnter_nm}</p>
								<a href="javascript:void(0)" id="btnMoveEmrgncy" class="btn01">현재위치</a>
								<a href="javascript:void(0)" class="btn01">확인</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="mapArea">
				<a href="javascript:void(0)" class="mapToggle"><img src="/img/btn/btn_toggle01.png" /></a>
								
				<div class="mapInfo" style="left:400px;top:100px;">
					
				</div>

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
				<div id="map_canvas" style="width:100%;height:100%;"></div>
			</div>
		</div>

	</div><!-- end : container -->
	 
	<div class="footer"><!-- start : footer -->  
	</div><!-- end : footer -->  

</div><!-- end : wrapper-->
<div class="wPopup" id="cnterInfoPopup" style="display:block;">
<div class="popbox"> 
	<div class="pHeader">
		<a href="javascript:void(0)" onclick="layerClose('.wPopup')"><img src="/img/btn/btn_close02.png" /></a>
		<span>센터별 기준정보</span> 
	</div>
	<div class="pCont">
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>센터구분</th>
				<td>
					<form:select path="cnterCd" class="select" id="cnter_cd">
						<form:option value="ALL">선택</form:option>
						<form:options items="${cnterCd}"/>
					</form:select>	
				</td>
			</tr> 
		</table>

		<p class="pSubj">공통</p>
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>왕복허용여부<span>*</span></th>
				<td id="val1"></td>
			</tr> 
			<tr>
				<th>목적지소요시간(분)</th>
				<td id="val2"></td>
			</tr> 
			<tr>
				<th>차량검색반경(km)<span>*</span></th>
				<td id="val3"></td>
			</tr> 
			<tr>
				<th>사전예약시간간격 <span>*</span></th>
				<td id="val4"></td>
			</tr> 
		</table>

		<p class="pSubj">광역 간 차량이동 기준</p>
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>차량이동가능여부<span>*</span></th>
				<td id="val5"></td>
			</tr> 
			<tr>
				<th>가능한 거리(km)<span>*</span></th>
				<td id="val6"></td>
			</tr> 
			<tr>
				<th>타지자체 회원사용<span>*</span></th>
				<td id="val7"></td>
			</tr>  
		</table>

		<p class="pSubj">지역 내 차량이동 기준</p>
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>타지자체 회원사용<span>*</span></th>
				<td id="val8"></td>
			</tr>   
		</table>

		<div class="btnBox">  
			<a href="javascript:void(0)"  onclick="layerClose('.wPopup')" class="btn01">닫기</a>
		</div> 
	</div>
	</div>
</div>

<div class="wPopup" id="carResultPopup" style="display:block; height:auto;">
<div class="popbox"> 
	<div class="pHeader">
		<a href="javascript:void(0)" onclick="layerClose('.wPopup')"><img src="/img/btn/btn_close02.png" /></a>
		<span>배차가능 차량현황</span> 
	</div>
	<div class="pCont">
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>차량유형</th>
				<td><select class="select"><option>선택</option></select></td>
			</tr>
			<tr>
				<th>운전자상태</th>
				<td><select class="select"><option>선택</option></select></td>
			</tr>
			<tr>
				<th>출발지 기준 반경(km)</th>
				<td>
					<input type="radio" id="" />
					<label for="">5km</label>
					<input type="radio" id="" class="ml10" />
					<label for="">10km</label>
					<input type="radio" id="" class="ml10" />
					<label for="">20km</label>
					<input type="radio" id="" class="ml10" />
					<label for="">30km</label>
				</td>
			</tr>
		</table>

		<table class="listTable mt20" id="carTable"> 
		</table>

		<div class="btnBox"> 
<!-- 			<a href="javascript:void(0)" class="btn01">자동배차</a> -->
			<a href="javascript:void(0)" onclick="layerClose('.wPopup')" class="btn01">닫기</a>
		</div> 
	</div>
	</div>
</div>

<div class="wPopup" id="carRequestPopup" style="display:block;">
	<div class="popbox"> 
		<div class="pHeader">
			<span>차량 예약요청(오프라인 사용자)</span> 
		</div>
		<div class="pCont">
			<form:form name="frm_carRequest" id="frm_carRequest" modelAttribute="command" method="post">
			<form:hidden path="cnter_cd" id="cnter_cd"/>
			<form:hidden path="mber_id" id="mber_id"/>
			<form:hidden path="start_lc_crdnt_x" id="start_lc_crdnt_x"/>
			<form:hidden path="start_lc_crdnt_y" id="start_lc_crdnt_y"/>
			<form:hidden path="arvl_lc_crdnt_x" id="arvl_lc_crdnt_x"/>
			<form:hidden path="arvl_lc_crdnt_y" id="arvl_lc_crdnt_y"/>
			 <dl class="toggleList">
				<dt><a href="javascript:void(0)">이동약자 정보 조회</a></dt>
				<dd>
				<table class="sTable01">
					<colgroup>
						<col width="100" />
						<col width="" />
					</colgroup>
					<tr>
						<th>센터구분</th>
						<td>
							<form:select path="search_cnter_cd" class="select" id="search_cnter_cd">
								<form:options items="${cnterCd}"/>
							</form:select>
						</td>
					</tr> 
					<tr>
						<th>예약자명</th>
						<td>
							<input type="text" class="inp full01" id="search_mber_name" path="search_mber_name"/>
							<a href="javascript:void(0)" id="btnSearch" class="btn01">찾기</a>
						</td>
					</tr>
				</table>
				
				<table class="listTable mt20" id="mberTable"> 
				</table>
				</dd>
				<dt class="on"><a href="javascript:void(0)">차량 예약요청</a></dt>
				<dd class="on">
					<div class="reqRadio">
						<input type="radio" name="sel_rcept_se_cd" id="sel_rcept_se_cd" value="10"/>
						<label for="10">당일즉시</label>
						<input type="radio" name="sel_rcept_se_cd" id="sel_rcept_se_cd" value="20" class="ml20" />
						<label for="20">사전예약</label>
	
						<input type="checkbox" name="rbtrnsp_trnsit_at" id="rbtrnsp_trnsit_at" class="ml20" />
						<label for="">대중교통 환승 여부</label>
					</div>
					<p class="reqTxt">* 광역 간 이동 시 대중교통(버스터미널, 기차 등)을 이용하셔서 출발/목적지로 지정하셔야 합니다.</p>
					
					
					<div class="sthBox mt30">
						<span class="t01">예약 1</span> 
					</div>
					<table class="sTable01">
						<colgroup>
							<col width="150" />
							<col width="" />
						</colgroup>
						<tr>
							<th>예약자명<span>*</span></th>
<!-- 							<td><input type="text" class="inp full" /></td> -->
							<td id="mber_nm"></td>
						</tr>   
						<tr>
							<th>예약일시<span>*</span></th>
							<td>
							<form:input type="text" class="inp datepicker"  path="sel_resve_date" id="sel_resve_date"/>
							<form:input type="text" class="inp w20 ml5" path="sel_resve_time" id="sel_resve_time" />
							<span class="etxt">:</span>
							<form:input type="text" class="inp w20" path="sel_resve_min" id="sel_resve_min"/>
							</td>
						</tr> 
						<tr>
							<th>출발지<span>*</span></th>
							<td>
								<input type="text" class="inp full01" id="sel_strtpnt_adres" path="sel_strtpnt_adres" />
								<a href="javascript:goSearch()" class="btnSearch01"><img src="/publishCss/img/btn/btn_search01.png" /></a>
							</td>
						</tr> 
						<tr>
							<th>목적지<span>*</span>
<!-- 							<br /><span class="etc">(대중교통 출발지)</span> -->
							</th>
							<td>
								<input type="text" class="inp full01" id="sel_aloc_adres" path="sel_aloc_adres" />
								<a href="javascript:goSearch()" class="btnSearch01"><img src="/publishCss/img/btn/btn_search01.png" /></a>
							</td>
						</tr>  
						<tr>
							<th>기존 출발/목적지</th>
							<td>
								<p class="reqTxt01">* 기존 출발/목적지 사용 시 체크하세요</p>
								<div class="itemBox">
									<p class="ckbox">
										<input type="checkbox" id="" />
										<label for="">출발지</label>
									</p>
									<select class="select full"><option>서울시 잠실 가락동 271번지</option></select>
								</div>
								<div class="itemBox">
									<p class="ckbox">
										<input type="checkbox" id="" />
										<label for="">도착지</label>
									</p>
									<select class="select full"><option>서울시 잠실 가락동 271번지</option></select>
								</div>
							</td>
						</tr>  
					</table>
				
<!-- 					<div class="sthBox mt30"> -->
<!-- 						<span class="t01">예약 2</span>  -->
<!-- 					</div> -->
<!-- 					<table class="sTable01"> -->
<%-- 						<colgroup> --%>
<%-- 							<col width="150" /> --%>
<%-- 							<col width="" /> --%>
<%-- 						</colgroup> --%>
<!-- 						<tr> -->
<!-- 							<th>예약자명<span>*</span></th> -->
<!-- 							<td><input type="text" class="inp full" /></td> -->
<!-- 						</tr>    -->
<!-- 						<tr> -->
<!-- 							<th>예약일시<span>*</span></th> -->
<!-- 							<td> -->
<!-- 								<input type="text" class="inp datepicker" /> -->
<!-- 								<input type="text" class="inp w20 ml5" /> -->
<!-- 								<span class="etxt">:</span> -->
<!-- 								<input type="text" class="inp w20" /> -->
<!-- 							</td> -->
<!-- 						</tr>  -->
<!-- 						<tr> -->
<!-- 							<th>출발지<span>*</span><br /><span class="etc">(대중교통 출발지)</span></th> -->
<!-- 							<td> -->
<!-- 								<input type="text" class="inp full01" /> -->
<!-- 								<a href="javascript:void(0)" class="btnSearch01"><img src="../../include/img/btn/btn_search01.png" /></a> -->
<!-- 							</td> -->
<!-- 						</tr>  -->
<!-- 						<tr> -->
<!-- 							<th>목적지<span>*</span></th> -->
<!-- 							<td> -->
<!-- 								<input type="text" class="inp full01" /> -->
<!-- 								<a href="javascript:void(0)" class="btnSearch01"><img src="../../include/img/btn/btn_search01.png" /></a> -->
<!-- 							</td> -->
<!-- 						</tr>   -->
<!-- 						<tr> -->
<!-- 							<th>기존 출발/목적지</th> -->
<!-- 							<td> -->
<!-- 								<p class="reqTxt01">* 기존 출발/목적지 사용 시 체크하세요</p> -->
<!-- 								<div class="itemBox"> -->
<!-- 									<p class="ckbox"> -->
<!-- 										<input type="checkbox" id="" /> -->
<!-- 										<label for="">출발지</label> -->
<!-- 									</p> -->
<!-- 									<select class="select full"><option>서울시 잠실 가락동 271번지</option></select> -->
<!-- 								</div> -->
<!-- 								<div class="itemBox"> -->
<!-- 									<p class="ckbox"> -->
<!-- 										<input type="checkbox" id="" /> -->
<!-- 										<label for="">도착지</label> -->
<!-- 									</p> -->
<!-- 									<select class="select full"><option>서울시 잠실 가락동 271번지</option></select> -->
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr>   -->
<!-- 					</table> -->
	
					<div class="sthBox mt30">
						<span class="t01">부가 정보</span> 
					</div>
					<table class="sTable01">
						<colgroup>
							<col width="150" />
							<col width="" />
						</colgroup>
						<tr>
						<th>이동목적<span>*</span></th>
						<td>
							<form:select path="sel_mvmn_purps_cd" class="select full" id="sel_mvmn_purps_cd">
								<form:option value="ALL">선택</form:option>
								<form:options items="${mvmn_purps_cd}"/>
							</form:select>	
						</td>
					</tr> 
					<tr>
						<th>휠체어구분<span>*</span></th>
						<td>
							<form:select path="sel_wheelchair_se_cd" class="select full" id="sel_wheelchair_se_cd">
								<form:option value="ALL">선택</form:option>
								<form:options items="${wheelchair_se_cd}"/>
							</form:select>	
						</td>
					</tr>
						<tr>
							<th>탑승인원<span>*</span></th>
							<td><input type="text" class="inp full" id="sel_brdng_nmpr"/></td>
						</tr> 
						<tr>
							<th>비고</th>
							<td><textarea class="textarea" id="sel_rm" ></textarea></td>
						</tr>   
					</table>
				</dd> 
			</dl>
	
			<div class="btnBox">  
				<a href="javascript:car_request_offline()" class="btn01">예약요청</a>
				<a href="javascript:void(0)" onclick="layerClose('.wPopup')" class="btn01">닫기</a>
			</div> 
			</form:form>
		</div>
	</div>
</div>
<div class="wPopup" id="searchPopup" style="display:block;">
<div class="popbox"> 
	<div class="pHeader">
		<span>출발/목적지 조회</span> 
	</div>
	<div class="pCont">
		<table class="sTable01">
			<colgroup>
				<col width="150" />
				<col width="" />
			</colgroup>
			<tr>
				<th>지역검색</th>
				<td>
					<input type="text" class="inp" />
					<a href="javascript:void(0)" class="btn01">찾기</a>
				</td>
			</tr> 
		</table>

		<table class="listTable mt20"> 
			<colgroup>
				<col width="" />
				<col width="150" /> 
			</colgroup> 
			<tr> 
				<td class="al">국립경찰병원<br />서울 송파구 가락동 58</td> 
				<td>
					<a href="javascript:void(0)" class="btn01">출발지</a>
					<a href="javascript:void(0)" class="btn01">목적지</a>
				</td>
			</tr> 
			<tr> 
				<td class="al">국립경찰병원<br />서울 송파구 가락동 58</td> 
				<td>
					<a href="javascript:void(0)" class="btn01">출발지</a>
					<a href="javascript:void(0)" class="btn01">목적지</a>
				</td>
			</tr> 
			<tr> 
				<td class="al">국립경찰병원<br />서울 송파구 가락동 58</td> 
				<td>
					<a href="javascript:void(0)" class="btn01">출발지</a>
					<a href="javascript:void(0)" class="btn01">목적지</a>
				</td>
			</tr> 
		</table>
		
		<div class="paging mt10">
			<a href="#" class="first">처음</a>
			<a href="#" class="prev">이전</a>
			<a href="#">1</a>
			<a href="#">2</a>
			<a href="#">3</a>
			<a href="#">4</a>
			<a href="#" class="next">다음</a>
			<a href="#" class="last">마지막</a>
		</div>

		<div class="btnBox">  
			<a href="javascript:void(0)" onclick="closePopUp()" class="btn01">닫기</a>
		</div> 
	</div>
</div>
</div>

<div class="dialog" id="mberInfoPopup" style="display:block">
	<div class="pop">
		<div class="pHeader">
			<span>이용자정보 상세보기</span>
			<a href="javascript:void(0)"><img src="/publishCss/img/btn/btn_close01.png" /></a>
		</div>
		<div class="pCont">
			
			<div class="sthBox">
				<span class="t01">기본정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="120" />
					<col width="" />
				</colgroup>
				<tr>
					<th>센터구분<span>*</span></th>
					<td><select class="select"><option>의왕지역이동지원센터</option></select></td>
					<th>회원구분<span>*</span></th>
					<td>이동약자</td>
				</tr>
				<tr>
					<th>이름<span>*</span></th>
					<td><input type="text" class="inp" /></td>
					<th>국적<span>*</span></th>
					<td>내국인</td>
				</tr>
				<tr>
					<th>아이디<span>*</span></th>
					<td>handicap01</td>
					<th>SMS수신여부<span>*</span></th>
					<td><select class="select"><option>예</option></select></td>
				</tr>
				<tr>
					<th>성별<span>*</span></th>
					<td>남자</td>
					<th>생년월일<span>*</span></th>
					<td>1990-11-11</td>
				</tr>
				<tr>
					<th>연락처<span>*</span></th>
					<td>
						<select class="select t01"><option>02</option></select>
						<input type="text" class="inp t01" />
						<span class="etxt01">-</span>
						<input type="text" class="inp t01" />
					</td>
					<th>휴대폰<span>*</span></th>
					<td>
						<select class="select t01"><option>010</option></select>
						<input type="text" class="inp t01" />
						<span class="etxt01">-</span>
						<input type="text" class="inp t01" />
					</td>
				</tr>
				<tr>
					<th>우편번호<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03" />
						<a href="javascript:void(0)" class="btn02">우편번호 검색</a>
					</td> 
				</tr>
				<tr>
					<th>주소<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t02" />
						<input type="text" class="inp t04" />
					</td> 
				</tr>
				<tr>
					<th>이메일<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03" />
						<span class="etxt01">@</span>
						<input type="text" class="inp t03" />
						<select class="select t03"><option>직적집역</option></select>
					</td> 
				</tr>
			</table>

			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>장애종류</th>
					<td><select class="select"><option>안면장애</option></select></td>
					<th>장애등급<span>*</span></th>
					<td><input type="text" class="inp" /></td>
				</tr>
				<tr>
					<th>휠체어구분<span>*</span></th>
					<td><select class="select"><option>없음</option></select></td>
					<th>휠체어이용기간<span>*</span></th>
					<td><input type="text" class="inp" /></td>
				</tr>
				<tr>
					<th>보조인유무</th>
					<td><input type="text" class="inp" /></td>
					<th>의사소통가능여부</th>
					<td><select class="select"><option>예</option></select></td>
				</tr>
				<tr>
					<th>도움필여여부</th>
					<td><input type="text" class="inp" /></td>
					<th>첨파부일</th>
					<td><a href="javascript:void(0)" class="link">장애인증명서.jpg</a></td>
				</tr> 
			</table>

			<div class="btnBox">
				<a href="javascript:void(0)" class="btn01">회원정보수정</a>
				<a href="javascript:void(0)" class="btn01">탈퇴</a>
				<a href="javascript:void(0)" class="btn01">취소</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#cnterInfoPopup").hide();
	$("#carResultPopup").hide();
	$("#carRequestPopup").hide();
	$("#searchPopup").hide();
	$("#mberInfoPopup").hide();
	var level = 3;
	if("${dataSet.total}">0){
		var varlat = "${dataSet.lat}";
		var varlon = "${dataSet.lon}";
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
	} else {
		var varlat = 33.450701;
		var varlon = 126.570667;
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
	}	
	// 이벤트.
	initEvent();
});

var closePopUp = function(){
	$("#searchPopup").hide();
};

var initEvent = function() {
	 
	// 검색.
	$("#btnSearch").on("click", function() {
		goPage(1); // 검색하면 1페이지로 이동.
	});
	
	$("#frm_carRequest #search_cnter_cd").on("change", function(){
		getCnterMberList(this.value);
	});
	
	$("#btnSearch01").on("click", function(){
		goSearch();
	});
	
};	

var goSearch = function(){
	$("#searchPopup").show();
};

var goPage = function(pageNo){
 	$("#frm #pageNo").val(pageNo);
 	$("#frm").action = "<c:url value='/view/counselor/carDispStatus.do'/>";
 	$("#frm").submit();
 };
 
 var getCnterMberList=function(value){
	 var param = {"cnter_cd":value};
	 $.ajax({
			type:'GET',
			url : "/view/counselor/getMberList.do",
			async:true,
			data: param,
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			success: function(data){
				var result = data.list;
				var html = "";
				html += "<colgroup>";
				html += "<col width=''/>";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>소속센터</th>";
				html += "<th>이름</th>";
				html += "<th>생년월일</th>";
				html += "<th>휴대폰</th>";
				html += "<th>장애종류</th>";
				html += "<th>장애등급</th>";
				html += "</tr> ";
				if(result.length==0){
					html += "<tr><td colspan='6'>등록된 회원이 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.length; i++){
						html +="<tr>";
						html += "<td>"+result[i].cnter_name+"</td>";
						html += "<td><a href=\"javascript:setting_mber(\'"+result[i].cnter_cd+"\',\'"+result[i].mber_id+"\',\'"+result[i].mber_nm+"\')\">"+result[i].mber_nm+"</a></td>";
						html += "<td>"+result[i].brthdy+"</td>";
						html += "<td>"+result[i].mbtlnum+"</td>";
						html += "<td>"+result[i].trobl_knd_cd+"</td> ";
						html += "<td>"+result[i].trobl_grad+"</td> ";
						html += "</tr>";
					}
				}
				$("#frm_carRequest #mberTable").html(html);
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
	            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
	        }  
		});
 }

 function setting_mber(cnter_cd, mber_id, mber_nm){
	 $("#frm_carRequest #cnter_cd").val(cnter_cd);
	 $("#frm_carRequest #mber_id").val(cnter_cd);
	 $("#frm_carRequest #mber_nm").html(mber_nm);
}

function car_request_offline(){
	if(validation()){
		var cnter_cd =  $("#frm_carRequest #cnter_cd").val();
		var rcept_se_cd = $("input[type=radio][name=sel_rcept_se_cd]:checked").val();
		var mber_id = $("#frm_carRequest #mber_id").val();
		var resve_date = $("#frm_carRequest #sel_resve_date").val();
		var resve_time = $("#frm_carRequest #sel_resve_time").val();
		var resve_min = $("#frm_carRequest #sel_resve_min").val();
		var startpnt_adres = $("#frm_carRequest #sel_strtpnt_adres").val();
		var aloc_adres = $("#frm_carRequest #sel_aloc_adres").val();
		var start_lc_crdnt_x = $("#frm_carRequest #start_lc_crdnt_x").val();
		var start_lc_crdnt_y = $("#frm_carRequest #start_lc_crdnt_y").val();
		var arvl_lc_crdnt_x = $("#frm_carRequest #arvl_lc_crdnt_x").val();
		var arvl_lc_crdnt_y = $("#frm_carRequest #arvl_lc_crdnt_y").val();
		var mvmn_purps_cd = $("#frm_carRequest #sel_mvmn_purps_cd").val();
		var wheelchair_se_cd = $("#frm_carRequest #sel_wheelchair_se_cd").val();
		var brdng_nmpr = $("#frm_carRequest #sel_brdng_nmpr").val();
		var rm = $("#frm_carRequest #sel_rm").val();
		
		var param = {"cnter_cd":cnter_cd,
				"rcept_se_cd":rcept_se_cd,
				"mber_id":mber_id,
				"resve_date":resve_date,
				"resve_time":resve_time,
				"resve_min":resve_min,
				"strtpnt_adres":strtpnt_adres,
				"aloc_adres":aloc_adres,
				"start_lc_crdnt_x":start_lc_crdnt_x,
				"start_lc_crdnt_y":start_lc_crdnt_y,
				"arvl_lc_crdnt_x":arvl_lc_crdnt_x,
				"arvl_lc_crdnt_y":arvl_lc_crdnt_y,
				"mvmn_purps_cd":mvmn_purps_cd,
				"wheelchair_se_cd":wheelchair_se_cd,
				"brdng_nmpr":brdng_nmpr,
				"rm":rm};
		
		$.ajax({
			type:'GET',
			url : "/view/counselor/reservation.do",
			async:true,
			data: param,
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			success: function(data){
				
// 				alert("예약 되었습니다.")
// 				$("#carResultPopup").hide();
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
	            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
	        }  
		});
		
	}
}

function validation(){
	if($("input[type=radio][name=sel_rcept_se_cd]:checked").val() == "undefined"){
		alert("예약 구분을 선택해 주세요.");
		$("#frm_carRequest #sel_rcept_se_cd").focus();
		return false;
	}
	if($("#frm_carRequest #mber_id").val() == ""){
		alert("예약자를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_resve_date").val() == ""){
		alert("예약 일시를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_resve_time").val() == ""){
		alert("예약 일시를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_resve_min").val() == ""){
		alert("예약 일시를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_strtpnt_adres").val() == ""){
		alert("출발지를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_aloc_adres").val() == ""){
		alert("목적지를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_mvmn_purps_cd").val() == ""){
		alert("이동목적를 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_wheelchair_se_cd").val() == ""){
		alert("휠체어 구분을 선택하세요.");
		return false;
	}
	if($("#frm_carRequest #sel_brdng_nmpr").val() == ""){
		alert("탑승인원을 선택하세요.");
		return false;
	}
	return true;
}

function car_request(){
	getCnterMberList($("#frm_carRequest #search_cnter_cd").val());
	$("#carRequestPopup").show();
}

function car_Resrv(){

	var rcept_se_cd = $("input[type=radio][name=rcept_se_cd]:checked").val();
	var cnter_cd = $("#frm_detail #cnter_cd").val();
	var caralcSttusCd = $("#frm_detail #caralc_sttus_cd").val();
	var resve_date = $("#frm_detail #resve_date").val();
	var resve_time = $("#frm_detail #resve_time").val();
	var resve_min = $("#frm_detail #resve_min").val();
	var strtpnt_adres = $("#frm_detail #strtpnt_adres").val();
	var start_lc_crdnt_x = $("#frm_detail #start_lc_crdnt_x").val();
	var start_lc_crdnt_y = $("#frm_detail #start_lc_crdnt_y").val();
	var aloc_adres = $("#frm_detail #aloc_adres").val();
	var arvl_lc_crdnt_x = $("#frm_detail #arvl_lc_crdnt_x").val();
	var arvl_lc_crdnt_y = $("#frm_detail #arvl_lc_crdnt_y").val();
	var roundtrip_at = $("#frm_detail #roundtrip_at").val();
	var mvmn_purps_cd = $("#frm_detail #mvmn_purps_cd").val();
	var wheelchair_se_cd = $("#frm_detail #wheelchair_se_cd").val();
	var brdng_nmpr = $("#frm_detail #brdng_nmpr").val();
	var rm = $("#frm_detail #rm").val();
	var resve_dt = $("#frm_detail #resve_dt").val();
	var mber_id = $("#frm_detail #mber_id").val();

	var param = {"cnter_cd":cnter_cd,
			"caralcSttusCd":caralcSttusCd,
			"resve_date":resve_date,
			"resve_time":resve_time,
			"resve_min":resve_min,
			"strtpnt_adres":strtpnt_adres,
			"start_lc_crdnt_x":start_lc_crdnt_x,
			"start_lc_crdnt_y":start_lc_crdnt_y,
			"aloc_adres":aloc_adres,
			"arvl_lc_crdnt_x":arvl_lc_crdnt_x,
			"arvl_lc_crdnt_y":arvl_lc_crdnt_y,
			"roundtrip_at":roundtrip_at,
			"mvmn_purps_cd":mvmn_purps_cd,
			"wheelchair_se_cd":wheelchair_se_cd,
			"brdng_nmpr":brdng_nmpr,
			"rm":rm,
			"resve_dt":resve_dt,
			"rcept_se_cd":rcept_se_cd,
			"mber_id":mber_id};
	
	$.ajax({
		type:'GET',
		url : "/view/counselor/carDispUpdate.do",
		async:true,
		data: param,
		contentType: "application/json; charset=utf-8",
		dataType:"json",
		success: function(data){
			alert(data.msg);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
        }  
	});

}

function car_Result(){
	var cnter_cd = $("#frm_detail #cnter_cd").val();
	var resve_dt = $("#frm_detail #resve_dt").val();
	var mber_id = $("#frm_detail #mber_id").val();
	var param = {"cnter_cd":cnter_cd,
				"rcept_se_cd":"20",
				"resve_dt":resve_dt,
				"mber_id":mber_id};
	
		$.ajax({
			type:'GET',
			url : "/view/counselor/carDisp.do",
			async:true,
			data: param,
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			success: function(data){
				var result = data.list;
				var mberId = data.mber_id;
				var cnterCd = data.cnter_cd;
				var resveDt = data.resve_dt;
				var html = "";
				html += "<colgroup>";
				html += "<col width='70'/>";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>순위</th>";
				html += "<th>소속센터</th>";
				html += "<th>차량번호</th>";
				html += "<th>운전자상태</th>";
				html += "<th>차량유형</th>";
				html += "<th>도착가능 시간(분)</th>";
				html += "<th>배차가능</th>";
				html += "</tr> ";
				if(data.count==0){
					html += "<tr><td colspan='7'>배차 가능한 차량이 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.length; i++){
						var num = i+1;
						html +="<tr>";
						html +="<td>"+num+"</td>";
						html += "<td>"+result[i].cnter_name+"</td>";
						html += "<td>"+result[i].vhcle_no+"</td>";
						html += "<td>"+result[i].drver_sttus_name+"</td>";
						html += "<td>"+result[i].vhcle_ty_name+"</td>";
						html += "<td>"+result[i].min+"</td> ";
						html += "<td><a href=\"javascript:car_matching(\'"+result[i].vhcle_no+"\',\'"+mberId+"\',\'"+cnterCd+"\',\'"+resveDt+"\')\" class='btn01'>배차</a></td>";
						html += "</tr>";
					}
				}
				$("#carTable").html(html);
				$("#carResultPopup").show();
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
	            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
	        }  
		});
	
};
function car_matching(vhcle_no, mber_id, cnter_cd, resve_dt){
	var param = {"mber_id":mber_id,
				"cnter_cd":cnter_cd,
				"resve_dt":resve_dt,
				"vhcle_no":vhcle_no};
	$.ajax({
		type:'GET',
		url : "/view/counselor/carMatching.do",
		async:true,
		data: param,
		contentType: "application/json; charset=utf-8",
		dataType:"json",
		success: function(data){
			alert(data.msg);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
        }  
	});
}
function cnter_info(){
	var cnter_cd = $("#frm_detail #cnter_cd").val();
	if(cnter_cd != null){
		var param = {"cnter_cd":cnter_cd};
		$.ajax({
			type:'GET',
			url : "/view/counselor/caralcSetupInfo.do",
			async:true,
			data: param,
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			success: function(data){
				$("#val1").html(data.roundtrip_perm_at);
				$("#val2").html(data.aloc_reqre_time);
				$("#val3").html(data.vhcle_search_radius);
				$("#val4").html(data.beffat_resve_time_intrvl);
				$("#val5").html(data.vhcle_mvmn_posbl_at);
				$("#val6").html(data.posbl_dstnc);
				$("#val7").html(data.wdr_sctn_othinst_mber_use_posbl_at);
				$("#val8").html(data.whthrc_othinst_mber_use_posbl_at);
				
				$("#cnterInfoPopup").show();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
	            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
	        }  
		});
	}
};
 
 
function showData(mber_id, resve_dt, cnter_cd){
	var param = {
			"mber_id":mber_id,
			"resve_dt":resve_dt,
			"cnter_cd":cnter_cd
	};
	
	$.ajax({
		type:'GET',
		url : "/view/counselor/DetailData.do",
		async:true,
		data: param,
		contentType: "application/json; charset=utf-8",
		dataType:"json",
		success: function(data){
			$("input:radio[name='rcept_se_cd']:radio[value='"+data.rcept_se_cd+"']").prop("checked", true);
			$("#frm_detail #caralc_sttus_cd").val(data.caralc_sttus_cd);
			$("#frm_detail #cnter_cd").val(data.cnter_cd);
			$("#frm_detail #resve_dt").val(data.resve_dt);
			$("#frm_detail #resve_date").val(data.resve_date);
			$("#frm_detail #resve_time").val(data.resve_time);
			$("#frm_detail #resve_min").val(data.resve_min);
			$("#frm_detail #strtpnt_adres").val(data.strtpnt_adres);
			$("#frm_detail #start_lc_crdnt_x").val(data.start_lc_crdnt_x);
			$("#frm_detail #start_lc_crdnt_y").val(data.start_lc_crdnt_y);
			$("#frm_detail #aloc_adres").val(data.aloc_adres);
			$("#frm_detail #arvl_lc_crdnt_x").val(data.arvl_lc_crdnt_x);
			$("#frm_detail #arvl_lc_crdnt_y").val(data.arvl_lc_crdnt_y);
			$("#frm_detail #roundtrip_at").val(data.roundtrip_at);
			$("#frm_detail #mvmn_purps_cd").val(data.mvmn_purps_cd);
			$("#frm_detail #wheelchair_se_cd").val(data.wheelchair_se_cd);
			$("#frm_detail #brdng_nmpr").val(data.brdng_nmpr);
			$("#frm_detail #rm").val(data.rm);
			$("#frm_detail #mber_id").val(data.mber_id);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
            alert("Status: " + textStatus); alert("Error: " + errorThrown); 
        }  
	});
};

</script>
</body>
</html>