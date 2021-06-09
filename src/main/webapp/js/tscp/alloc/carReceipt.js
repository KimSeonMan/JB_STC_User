/**
 * 차량예약현황 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.carAlloc = W.$carReceipt || {};
	$(document).ready(function() {
		codeDetailSearch("ALLOC_STAT_CD", "ALLOC_STAT_CD"); //배차상태코드
		codeDetailSearch("USER_GBN_CD_POP", "USER_GBN_CD"); //회원구분코드
		
		//엔터키
		$('#NAME').on('keypress', function(e) {
			if (e.which == 13) {
				$carReceipt.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#ALLOC_STAT_CD").change(function(){
			$carReceipt.list(1);
		});
		
		$("#searchList").click(function(){
			$carReceipt.list(1);
		});
		
		//접수예약 팝업
		$("#receiptOpen").click(function(){
			document.getElementById('receiptForm').reset();
			codeDetailSearch("PURPOSE_CD2", "PURPOSE_CD"); //이동목적코드
			$("#receiptPopup").show();
		});
		
		//접수예약 등록
		$("#carReceiptAdd").click(function(){
			$carReceipt.carReceiptAdd();
		});
		
		//회원정보 조회 팝업
		$("#userSearchList_pop").click(function(){
			$common.list(1, $("#USER_GBN_CD_POP").val()); 
		});
		
		//엔터키
		$('#NAME_POP').on('keypress', function(e) {
			if (e.which == 13) {
				$common.list(1,'10');
			}
		});
		
		//엔터키
		$('#USER_ID_POP').on('keypress', function(e) {
			if (e.which == 13) {
				$common.list(1,'10');
			}
		});
		$('#searchWord').on('keypress', function(e) {
			if (e.which == 13) {
				$carReceipt.searchAddr(1);
			}
		});
		
		$carReceipt.list(1); //목록 조회
	});
	
	$carReceipt = {
		totalCnt : 0,
		currentPageIndex : 1,
		
		x_coor : 33.450701,
		y_coor : 126.570667,
		
		map : {
			ui : $map.ui,
			ajax : $map.ajax,
			event : $map.event,
			util : $map.util
		},
		
		searchAddrList : new Array(),
		startAddrObject : null,
		endAddrObject : null,
		
		//맵생성
		openMap : function(){
			$("#mapSearchPopUp").show();
			
			
			var level = 3;	
			var browser = navigator.appName;
			if(browser == "Netscape"){
				$carReceipt.x_coor = 37.4950952443;
				$carReceipt.y_coor = 127.1224273159;
				$carReceipt.map.ui.drawMap("gMapArea",$carReceipt.x_coor, $carReceipt.y_coor,level);			
			}else{
				if(navigator.geolocation){
					navigator.geolocation.getCurrentPosition(function(position) {
						$carReceipt.x_coor = position.coords.latitude;
						$carReceipt.y_coor = position.coords.longitude;
						/*x_coor = 37.4950952443;
						y_coor = 127.1224273159;*/
						
						$carReceipt.map.ui.drawMap("gMapArea",$carReceipt.x_coor, $carReceipt.y_coor,level);
					});
				}else{
					$carReceipt.map.ui.drawMap("gMapArea",$carReceipt.x_coor, $carReceipt.y_coor,level);
				}
			}
			
			
		},
		
		//주소검색
		searchAddr : function(page){
			$carReceipt.searchAddrList = new Array();
			$carReceipt.map.ui.removeAllMarker();
			$carReceipt.map.ui.removeAllOverLay();
			
			var searchWord = $("#searchWord").val();
			var senders = new tscp.searchAddrList.api();
			senders.addParam("addr",searchWord);
			senders.addParam("x_coor",$carReceipt.x_coor);
			senders.addParam("y_coor",$carReceipt.y_coor);
			senders.addParam("page",page);
			senders.addParam("count",15);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/addrSearchList.json"
			})
			
		},
		
		//주소검색
		searchAddrPage : function(page,searchWord){
			$carReceipt.searchAddrList = new Array();
			$carReceipt.map.ui.removeAllMarker();
			$carReceipt.map.ui.removeAllOverLay();
			
			var senders = new tscp.searchAddrList.api();
			senders.addParam("addr",searchWord);
			senders.addParam("x_coor",$carReceipt.x_coor);
			senders.addParam("y_coor",$carReceipt.y_coor);
			senders.addParam("page",page);
			senders.addParam("count",15);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/addrSearchList.json"
			})
			
		},
		
		startAddrWrite : function(index){
			//addrObject.x_coor = list[i].latitude;
			//addrObject.y_coor = list[i].longitude;
			//addrObject.address = list[i].address;
			
			var addrObject = $carReceipt.searchAddrList[index];
			$carReceipt.startAddrObject = addrObject;
			
			$carReceipt.map.ui.moveCenter(addrObject.x_coor,addrObject.y_coor,index);
			
			$("#startAddr").val(addrObject.address);
		},
		
		endAddrWrite : function(index){
			var addrObject = $carReceipt.searchAddrList[index];
			$carReceipt.endAddrObject = addrObject;
			
			$carReceipt.map.ui.moveCenter(addrObject.x_coor,addrObject.y_coor,index);
			
			$("#endAddr").val(addrObject.address);
		},
		
		saveDirection : function(){
			//$carReceipt.startAddrObject
			//$carReceipt.endAddrObject
			//DEPT_DESC1
			//DEPT_X1
			//DEPT_Y1
			
			$("#DEPT_DESC1").val($carReceipt.startAddrObject.address);
			$("#DEPT_X1").val($carReceipt.startAddrObject.x_coor);
			$("#DEPT_Y1").val($carReceipt.startAddrObject.y_coor);
			
			$("#DEST_DESC1").val($carReceipt.endAddrObject.address);
			$("#DEST_X1").val($carReceipt.endAddrObject.x_coor);
			$("#DEST_Y1").val($carReceipt.endAddrObject.y_coor);
			$("#mapSearchPopUp").hide();
			//reset
			
		},
		
		// commonCDMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.carReceiptlist.api();
			senders.addParam("page", page);
			if($("#ALLOC_STAT_CD").val() != "") {
				senders.addParam("ALLOC_STAT_CD", $("#ALLOC_STAT_CD").val());
			}
			if($("#NAME").val() != "") {
				senders.addParam("NAME", $("#NAME").val());
			}
			if($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
				if($("#ST_DT").val() <= $("#EN_DT").val()) {
					senders.addParam("ST_DT", $("#ST_DT").val());
					senders.addParam("EN_DT", $("#EN_DT").val());
				} else {
					alert("예약 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
					$("#ST_DT").focus();
					return false;
				}
			}
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/carReceiptList.json",
			});
		},
		carReceiptAdd : function() {	
			if(validationReceiptAdd()) {
				if(confirm("예약접수를 등록하시겠습니까?")) {
					var st_hh = $("#ST_HH2").val();
					var st_mm = $("#ST_MM2").val();
					
					if(st_hh < 10) { st_hh = "0" + st_hh; }
					if(st_mm < 10) { st_mm = "0" + st_mm; }
					
					var senders = new tscp.carReceiptAdd.api();
					senders.addParam("USER_ID", $("#USER_ID2").val());
					
					//테스트 데이터
					senders.addParam("DEPT_X", $("#DEPT_X1").val()); //서울시청
					senders.addParam("DEPT_Y", $("#DEPT_Y1").val());
					senders.addParam("DEST_X", $("#DEST_X1").val()); //서울역
					senders.addParam("DEST_Y", $("#DEST_Y1").val());
					
					//출발지와 목적지
					senders.addParam("DEPT_DESC",$("#DEPT_DESC1").val());
					senders.addParam("DEST_DESC",$("#DEST_DESC1").val());
					
					senders.addParam("RECEIPT_DATETIME", $("#ST_DT2").val()+" "+st_hh+":"+st_mm);
					senders.addParam("PURPOSE_CD", $("#PURPOSE_CD2").val());
					senders.addParam("PASSENSERS", $("#PASSENSERS2").val());
					if($("#EXP_TRAVEL_DISTANCE2").val() != "") {
						senders.addParam("EXP_TRAVEL_DISTANCE", $("#EXP_TRAVEL_DISTANCE2").val());
					}
					if($("#EXP_TRAVEL_HOURS2").val() != "") {
						senders.addParam("EXP_TRAVEL_HOURS", $("#EXP_TRAVEL_HOURS2").val());
					}
					if($("#EXP_TRAVEL_HOURS2").val() != "") {
						senders.addParam("EXP_SERICE_FEE", $("#EXP_SERICE_FEE2").val());
					}
					if($("#EXP_TRAVEL_HOURS2").val() != "") {
						senders.addParam("REMARK", $("#REMARK").val());
					}
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/alloc/carReceiptAdd.json",
					});
				}
			}
		},
		carAllocPopList : function(str1, str2, str3, str4, str5, str6, str7) {	//배정가능목록
			var senders = new tscp.carAllocPopList.api();
			senders.addParam("DEPT_X", str1);
			senders.addParam("DEPT_Y", str2);
			senders.addParam("PASSENSERS", str3);
			senders.addParam("USER_ID", str4);
			senders.addParam("RECEIPT_DATETIME", str5);
			senders.addParam("SUPPORTER_CD", str6);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/carAllocPopList.json",
				options : {
					word : str7
				}
			});
		},
		carReceiptUpdate : function(str1, str2, str3, str4) {	//차량예약
			if(confirm("선택하신 차량으로 예약하시겠습니까?")) {
				var senders = new tscp.carReceiptUpdate.api();
				senders.addParam("CAR_NO", str1);
				senders.addParam("USER_ID", str2);
				senders.addParam("RECEIPT_DATETIME", str3);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/alloc/carReceiptUpdate.json",
					options : {
						word1 : str3, word2 : str4
					}
				});
			}
		}
	};
	
	/*********** 회원관리 목록 API 호출 Start **********/
	(function() {
		$class("tscp.carReceiptlist.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='70' />";
				html += "<col width='70' />";
				html += "<col width='70' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='70' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>회원ID</th>";
				html += "<th>예약자명</th>";
				html += "<th>예약일시</th>";
				html += "<th>배정일시</th>";
				html += "<th>출발지</th>";
				html += "<th>목적지</th>";
				html += "<th>이동목적</th>";
				html += "<th>휠체어</br/>유무</th>";
				html += "<th>이동보조원</br/>신청여부</th>";
				html += "<th>배차상태</th>";
				html += "<th>배차</th>";
				html += "</tr>";

				if(result.carAllocList.length == 0) {
					html += "<tr><td colspan='12'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.carAllocList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.carAllocList[i].rnum+"</td>";
						html += "<td>"+result.carAllocList[i].USER_ID+"</td>";
						html += "<td>"+result.carAllocList[i].NAME+"</td>";
						html += "<td>"+result.carAllocList[i].RECEIPT_DATETIME+"</td>";
						html += "<td>"+result.carAllocList[i].ALLOC_DATETIME+"</td>";
						html += "<td>"+result.carAllocList[i].DEPT_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].DEST_DESC+"</td>";
//						html += "<td>"+result.carAllocList[i].DEPT_X+":"+result.carAllocList[i].DEPT_Y+"</td>";
//						html += "<td>"+result.carAllocList[i].DEST_X+":"+result.carAllocList[i].DEST_Y+"</td>";
						html += "<td>"+result.carAllocList[i].PURPOSE_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].WHEELCHAIR_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].SUPPORTER_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].ALLOC_STAT_DESC+"</td>";
						if(result.carAllocList[i].ALLOC_STAT_CD == "10") {
							html += "<td><a href=\"javascript:$carReceipt.carAllocPopList(\'"
								+result.carAllocList[i].DEPT_X+"\',\'"
								+result.carAllocList[i].DEPT_Y+"\',\'"
								+result.carAllocList[i].PASSENSERS+"\',\'"
								+result.carAllocList[i].USER_ID+"\',\'"
								+result.carAllocList[i].RECEIPT_DATETIME+"\',\'"
								+result.carAllocList[i].SUPPORTER_DESC+"\',\'"
								+result.carAllocList[i].DEPT_DESC+"\');\" class='btn02 CRUDBtn'>배차</a></td>";
						} else {
							html += "<td></td>";
						}
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.carAllocListCount[0].CNT+" 건");
				$carReceipt.totalCnt = result.carAllocListCount[0].CNT;
				paging($carReceipt,"carAllocList");
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원관리 API 호출 End **********/
	
	/*********** 예약접수등록 API 호출 Start **********/
	(function() {
		$class("tscp.carReceiptAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("예약접수가 등록되었습니다.");
					$(".dialog").hide();
					$carReceipt.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 예약접수등록 API 호출 End **********/
	
	/*********** 배정가능목록 API 호출 Start **********/
	(function() {
		$class("tscp.carAllocPopList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#allocPopupTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<table class='listTable t01'>";
				html += "<colgroup>";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='40' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='70' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>가능순위</th>";
				html += "<th>차량번호</th>";
				html += "<th>차량유형</th>";
				html += "<th>운전자</th>";
				html += "<th>모델명</th>";
				html += "<th>연식</th>";
				html += "<th>최대</br>탑승인원</th>";
				html += "<th>휠체어</br>가능</th>";
				html += "<th>보조인</br>유무</th>";
				html += "<th>예약가능</br>상태</th>";
				html += "</tr>";
				html += "</table>";
				html += "<div class='popTableScroll' style='overflow: auto;'>";
				html += "<table class='listTable t01'>";
				html += "<colgroup>";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='40' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='70' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				
				if(result.carAllocPopList.length == 0) {
					html += "<tr><td colspan='10'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.carAllocPopList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.carAllocPopList[i].rnum+"</td>";
						html += "<td>"+result.carAllocPopList[i].CAR_NO+"</td>";
						html += "<td>"+result.carAllocPopList[i].CAR_TYPE_DESC+"</td>";
						html += "<td>"+result.carAllocPopList[i].NAME+"</td>";
						html += "<td>"+result.carAllocPopList[i].MODEL+"</td>";
						html += "<td>"+result.carAllocPopList[i].PROD_YEAR+"</td>";
						html += "<td>"+result.carAllocPopList[i].MAX_PASSENGERS+"</td>";
						html += "<td>"+result.carAllocPopList[i].WHEELCHAIR_YN+"</td>";
						html += "<td>"+result.SUPPORTER_CD+"</td>";
						html += "<td><a href=\"javascript:$carReceipt.carReceiptUpdate(\'"
								+result.carAllocPopList[i].CAR_NO+"\',\'"
								+result.USER_ID+"\',\'"
								+result.RECEIPT_DATETIME+"\',\'"
								+options.word+"\');\" class='btn02'>예약</a></td>";
						html += "</tr>";
					}
				}
				
				$("#allocForm").html(html);
				$("#allocPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배정가능목록 API 호출 End **********/
	
	/*********** 차량예약 API 호출 Start **********/
	(function() {
		$class("tscp.carReceiptUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("귀하가 접수하신 "+options.word1+" "+options.word2+"의 차량예약이 완료되어 알려드립니다.\n이용해주셔서 감사합니다.");
					$(".dialog").hide();
					$carReceipt.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량예약 API 호출 End **********/
	
	(function(){
		$class("tscp.searchAddrList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status,res,options){
				if(res.errCd =="0"){
					//res.result
					//result.keyword : 시청역
					//result.totalCount
					//result.page
					//result.list 
					//result.status
					//list.title
					//list.address
					//list.latitude
					//list.longitude
					$carReceipt.searchAddrList = new Array();
					var result = res.result;
					
					if(result.status == "success"){
						$("#resultSubject").text("검색기준 : ");
						$("#resultKeyWord").text(result.keyword);
						var html ="";
						var list = result.list;
						for(var i = 0; i < list.length; i++){
							
							var addrObject = new Object();
							addrObject.x_coor = list[i].latitude;
							addrObject.y_coor = list[i].longitude;
							addrObject.address = list[i].address;
							$carReceipt.searchAddrList.push(addrObject);
							
							html +="<div style='border:1px solid #e2e2e2'>";
							html +="	<div>";
							html +="		<span>"+list[i].title+"</span>";
							html +="	</div>";
							html +="	<div>";
							html +="		<span>주소 : </span>";
							html +="		<span>"+list[i].address+"</span>";
							html +="	</div>";
							html +="	<div>";
							html +="		<span><a href='javascript:$carReceipt.startAddrWrite("+i+")' class='btn02'>출발지</a></span>";
							html +="		&nbsp;"
							html +="		<span><a href='javascript:$carReceipt.endAddrWrite("+i+")' class='btn02'>도착지</a></span>";
							html +="		&nbsp;"
							html +="		<span><a href='javascript:$carReceipt.map.ui.moveCenter("+list[i].latitude+","+list[i].longitude+","+i+");' class='btn02'>위치 보기</a></span>";
							html +="	</div>";
							html +="</div>";
							
							var popUpHtml = "";
							popUpHtml += '<div class="infoLayer" style="z-index:20000;top:-230px;left:-190px;">';
							popUpHtml += 	'<div class="rela">';
							popUpHtml += 		'<p class="subj">'+list[i].title+'</p>';
							popUpHtml += 		'<a href="javascript:void(0)" class="btnClose" onclick="$carReceipt.map.ui.closeOverLay('+i+');"><img src="/img/ico/ico_close01.png" /></a>';
							popUpHtml += 		'<table class="layerTable">';
							popUpHtml += 			'<colgroup>';
							popUpHtml += 				'<col width="60" />';
							popUpHtml += 				'<col width="" />';
							popUpHtml += 				'<col width="60" />';
							popUpHtml += 				'<col width="" />';
							popUpHtml += 				'</colgroup>';
							popUpHtml += 				'<tr>';
							popUpHtml += 					'<th>주소</th>';
							popUpHtml += 					'<td colspan="3"><input type="text" name="" value="'+list[i].address+'"class="inp" /></td>';
							popUpHtml += 				'</tr>';
							popUpHtml += 			'</table>';
							popUpHtml += 			'<div class="btnbox">';
							popUpHtml += 				'<a href="javascript:$carReceipt.startAddrWrite('+i+');" class="btn01">출발지</a> &nbsp;';
							popUpHtml += 				'<a href="javascript:$carReceipt.endAddrWrite('+i+');" class="btn01">도착지</a>';
							popUpHtml += 			'</div>';
							popUpHtml += 	'</div>';
							popUpHtml += '</div>';
							$carReceipt.map.ui.createMarker(list[i].latitude,list[i].longitude);
							$carReceipt.map.ui.contentPopup(popUpHtml,i);
							
						}
						$("#gpsTable").html(html);
						
						if(list.length > 0){
							$carReceipt.map.ui.moveCenter(list[0].latitude,list[0].longitude,0);
						}
						
						
						var totalCount = result.totalCount;
						var showListCount = result.count;
						var pageList = totalCount / showListCount;
						/*if(pageList > 5){
							pageList = 5
						}*/
						
						//"mapPageList"
						$("#mapPageList").paging({
							current : result.page,
							max : pageList,
							itemClass : 'page',
							itemCurrent : 'current',
							format : "{0}",
							next : '&gt;',
							prev : '&lt;',
							first : '&lt;&lt;',
							last : '&gt;&gt;',
							onclick : function(e, page) { // 페이지 선택 시
								$carReceipt.searchAddrPage(page,result.keyword);
							}	
								
						});
					}else{
						
					}
					
				}
			},
			onFail : function(status){
				
			}
		});
	}());
}(window, document));

//예약접수 등록 체크
function validationReceiptAdd() {
	if ($("#USER_ID2").val() == "" || $("#NAME2").val() == "") {
		alert("예약자를 조회하세요.");
		$("#USER_ID2").focus();
		return false;
	}
	
	if ($("#DEPT_DESC2").val() == "") {
		alert("출발지를 조회하세요.");
		$("#DEPT_DESC2").focus();
		return false;
	}
	
	if ($("#DEST_DESC2").val() == "") {
		alert("목적지를 조회하세요.");
		$("#DEST_DESC2").focus();
		return false;
	}
	
	if ($("#ST_DT2").val() == "") {
		alert("예약시작일자를 입력하세요.");
		$("#ST_DT2").focus();
		return false;
	}
	
	if ($("#ST_HH2").val() == "") {
		alert("예약시작시간(시)을 입력하세요.");
		$("#ST_HH2").focus();
		return false;
	}
	
	if ($("#ST_MM2").val() == "") {
		alert("예약시작시간(분)을 입력하세요.");
		$("#ST_MM2").focus();
		return false;
	}
	
	if ($("#PASSENSERS2").val() == "") {
		alert("탑승인원을 입력하세요.");
		$("#PASSENSERS2").focus();
		return false;
	}
	
	return true;
}

function userSearchPopup(str) {
	document.getElementById('listTablePop').reset();
	$("#userSearchPopup").show();
	$("#USER_GBN_CD_POP").val(str);
	$common.list(1);
}

//회원정보 조회 팝업 값 넘기기
function parentValMove(str1, str2) {
	$("#USER_ID2").val(str1);
	$("#NAME2").val(str2);
	
	document.getElementById('listTablePop').reset();
	$("#userSearchTable").empty(); //리스트 삭제
	$("#paging2").empty();
	$("#userSearchPopup").hide();
}