/*
 * $(document).ready(function(){
	var level = 3;
	if("${dataSet.total}">0){

	} else {
		var varlat = 33.450701;
		var varlon = 126.570667;
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
	}
	
	
	// 이벤트.
	initEvent();
});

var initEvent = function() {
	 
	// 검색.
	$("#btnSearch").on("click", function() {
		goPage(1); // 검색하면 1페이지로 이동.
	});
};	
var goPage = function(pageNo){
 	$("#frm #pageNo").val(pageNo);
 	$("#frm").action = "<c:url value='/view/counselor/carDispStatus.do'/>";
 	$("#frm").submit();
 };
 */

(function(W, D) {
	//"use strict";
	W.counselor = W.$counselor || {};
	$(document).ready(function() {
		var level = 3;
		var varlat = 33.450701;
		var varlon = 126.570667;
		$locmap.ui.drawMap("map_canvas", varlat, varlon, level);
		$counselor.list(1);

	});
	
	$counselor = {
		totalCnt : 0,
		currentPageIndex : 1,
		// counselor API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.counselorList.api();
			senders.addParam("page", page);
			if($(""))
			
			if($("#CAR_NO").val() != "") {
				senders.addParam("CAR_NO", $("#CAR_NO").val());
			}
			if($("#OPERABLE_WEEKDAYS").val() != "") {
				senders.addParam("OPERABLE_WEEKDAYS", $("#OPERABLE_WEEKDAYS").val());
			}
			if($("#OPERABLE_HOURS").val() != "") {
				senders.addParam("OPERABLE_HOURS", $("#OPERABLE_HOURS").val());
			}
			if($("#CAR_TYPE_CD").val() != "") {
				senders.addParam("CAR_TYPE_CD", $("#CAR_TYPE_CD").val());
			}
			if($("#CAR_STAT_CD").val() != "") {
				senders.addParam("CAR_STAT_CD", $("#CAR_STAT_CD").val());
			}
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/counselorList.json",
			});
		},	
		counselorDelete : function(str) {	
			if(confirm("차량 정보를 삭제하시겠습니까?")) {
				var senders = new tscp.counselorDelete.api();	
				senders.addParam("CAR_NO", str);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/bassInfo/counselorDelete.json",
				});
			}
		},
		
		counselorAdd : function() {	
			if (validation()) {
				if(confirm("차량정보를 등록하시겠습니까?")) {
					var senders = new tscp.counselorAdd.api();			
					senders.addParam("CAR_NO", $("#CAR_NO2").val());
					if($("#PROD_YEAR2").val() != "") {
						senders.addParam("PROD_YEAR", $("#PROD_YEAR2").val());
					}
					if($("#MODEL2").val() != "") {
						senders.addParam("MODEL", $("#MODEL2").val());
					}
					if($("#MAX_PASSENGERS2").val() != "") {
						senders.addParam("MAX_PASSENGERS", $("#MAX_PASSENGERS2").val());
					}
					if($("#CAR_TYPE_CD2").val() != "") {
						senders.addParam("CAR_TYPE_CD", $("#CAR_TYPE_CD2").val());
					}
					if($("#MAKER2").val() != "") {
						senders.addParam("MAKER", $("#MAKER2").val());
					}
					if($("#MFG_NO2").val() != "") {
						senders.addParam("MFG_NO", $("#MFG_NO2").val());
					}
					if($("#CAR_STAT_CD2").val() != "") {
						senders.addParam("CAR_STAT_CD", $("#CAR_STAT_CD2").val());
					}
					if($("#USER_ID2").val() != "") {
						senders.addParam("USER_ID", $("#USER_ID2").val());
					}
					if(document.getElementsByName("chkWeek").length > 0) {
						senders.addParam("OPERABLE_WEEKDAYS", checkReturnValBinary("chkWeek"));
					}
					if(document.getElementsByName("chkTime").length > 0) {
						senders.addParam("OPERABLE_HOURS", checkReturnValBinary("chkTime"));
					}
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/bassInfo/counselorAdd.json",
					});
				}
			}
		},
		counselorDetail : function(str) {	
			var senders = new tscp.counselorDetail.api();
			senders.addParam("CAR_NO", str);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/counselorDetail.json",
			});
		},
		plusHand : function(){
        	var num = $("#addHandInfo").children("tbody").children("tr").length;
        	var html ="";
        	
        	if(num < 4){
	        	html += "<tr id='hand"+num+"' name='hand"+num+"'>";
				html += "	<td>"+num+"</td>";
				html += "	<td><input type='text' id='MBER_NM"+num+"' class='inp'></td>";
				html += "	<td><input type='text' id='MBTLNUM"+num+"' class='inp' placeholder='숫자만 입력해주세요. ex)01011112222'></td>";
				html += "	<td><select class='select' id='WHEELCHAIR_SE_CD"+num+"'><option>선택</option></select></td>";
				html += "	<td><select class='select' id='SUPPORT_NUM"+num+"'></select></td>";
				html += "</tr>";
				
				$("#addHandInfo").children("tbody").append(html);
				
				$("#WHEELCHAIR_SE_CD"+num).html($("#WHEELCHAIR_SE_CD").html());
				$("#SUPPORT_NUM"+num).html($("#SUPPORT_NUM").html());
        	}
        	
        },
        minusHand : function(){
        	var num = $("#addHandInfo").children("tbody").children("tr").length;
        	
        	if(num > 2){
        		$("#hand"+(num-1)).remove();
        	}
        	
        }
	};
	
	/*********** 차량정보목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.counselorList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				
				html += "<colgroup>";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='70' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='70' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>예약자명</th>";
				html += "<th>접수<br />구분</th>";
				html += "<th>예약일시</th>";
				html += "<th>이동<br />유형</th>";
				html += "<th>대기시간<br />(분)</th>";
				html += "<th>접수<br />상태</th>";
				html += "</tr>";
				if(result.list.length==0) {
					html += "<tr><td colspan='6' align='center'> No Data </td></tr>";
				} else {
					for(var i=0; i<result.list.length; i++){
						html += "<tr>";
						html += "<td>"+result.list[i].MBER_NM+"</td>";
						html += "<td>"+result.list[i].RCEPT_SE_NM+"</td>";
						html += "<td>"+result.list[i].RESVE_DATE+"<br/>"+result.list[i].RESVE_TIME+"</td>";
						html += "<td>"+result.list[i].MVMN_TY_NM+"</td>";
						html += "<td>50</td>";
						html += "<td><a class='btn01'>신청</a></td>";
					}
				}

				$(".listTable").html(html);
				$counselor.totalCnt = result.counselorListCount[0].CNT;
				$("#TOTAL_CNT").val(result.counselorListCount[0].CNT);
				paging($counselor,"counselorList");
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보목록조회 API 호출 End **********/
	
	
}(window, document));

function codeDetailSearch(id_val, cd_id) {
	var senders = new tscp.codeDetailList.api();
	senders.addParam("id_val", id_val);
	senders.addParam("cd_id", cd_id);
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/code/CodeDetailSearch.json"
	});
}

function validation() {
	if ($("#CAR_NO2").val() == "") {
		alert("차량번호를 입력하세요.");
		$("#CAR_NO2").focus();
		return false;
	}
	
	if ($("#carNoCheckYN").val() == "N") {
		alert("차량번호 중복체크하세요.");
		$("#CAR_NO2").focus();
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