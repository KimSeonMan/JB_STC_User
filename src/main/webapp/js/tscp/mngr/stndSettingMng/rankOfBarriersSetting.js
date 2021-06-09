/**
 * 배차관련 설정 관리
 * 
 * history : 웨이브엠(주), 1.0, 2018/01/04  초기 작성
 * author : 송용석
 * version : 1.0
 * see : 
 *
 */
var fileInfo = "";
(function(W, D) {
	//"use strict";
	W.rankOfBarriers = W.$rankOfBarriers || {};
	$(document).ready(function() {
		$rankOfBarriers.rankOfBarriersInfo();
		
		$("#plusBtn").click(function(){
			var subUpHtml = '';
			var subdownHtml = '';
			subUpHtml = upHtml.replace(/rowNum/gi, rowNum)
								.replace("SelectOption", option);
			subdownHtml = downHtml.replace(/rowNum/gi, rowNum);
			
			$("#tableTbody").append(subUpHtml);
			$("#tableTbody").append(subdownHtml);
			//console.log(rowNum);
			rowNum++;
		});
		$("#minusBtn").click(function(){
			if(rowNum > 1){
				//console.log(rowNum--);
				$(".sTable01 > tbody > tr[name='barrierUp_"+rowNum+"']").remove();
			}
		});
		
		$("#basicSave").click(function(){
			$rankOfBarriers.rankOfBarriersSave();
		});
	});
	
	$rankOfBarriers = {
		rankOfBarriersInfo : function (){
			var senders = new tscp.rankOfBarriersInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/rankOfBarriersInfo.json",
			});
		},
		
		rankOfBarriersSave : function(){
			var senders = new tscp.rankOfBarriersSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			var newTROBL_KND_CD =[];
			var newTROBL_GRAD =[];
			var newWHEELCHAIR_USE_YN =[];
			var newPRTCTOR_ACMPNY_AT =[];
			// 저장 할 정보 의 갯수만큼 FOR문 을 돔
			for(var i=1; i < rowNum; i++){
				
				// 장애등급의 입력여부를 확인
				if($("#TROBL_GRAD_"+i).val() == null || $("#TROBL_GRAD_"+i).val() == '') {
					alert("장애등급은 필수 값입니다. " + i + "번쨰 장애등급을 입력해주세요.");
					return;
				}
				
				
				for(var checkIndex=0; checkIndex < newTROBL_KND_CD.length; checkIndex++){
					if($("#TROBL_KND_CD_"+i).val() == newTROBL_KND_CD[checkIndex] 
							&& $("#TROBL_GRAD_"+i).val() == newTROBL_GRAD[checkIndex]) {
							var troblName = '';
							var troblCd = '';
							for(var optionIndex = 0; optionIndex < optionValue.length; optionIndex++){
								if(optionValue[optionIndex].CD_VALUE == newTROBL_KND_CD[checkIndex]){
									trobName = optionValue[optionIndex].CD_VALUE_DE;
									trobCd = optionValue[optionIndex].CD_VALUE;
									alert(trobName + "(" + trobCd + ") 중복정보가 있습니다. 다시 확인해주세요.");
									return;
								}
							}
						
					}
				}
				newTROBL_KND_CD.push($("#TROBL_KND_CD_"+i).val());
				newTROBL_GRAD.push($("#TROBL_GRAD_"+i).val());
				newWHEELCHAIR_USE_YN.push($("#WHEELCHAIR_USE_YN_"+i).prop("checked") ? 'Y' : 'N');
				newPRTCTOR_ACMPNY_AT.push($("#PRTCTOR_ACMPNY_AT_"+i).prop("checked") ? 'Y' : 'N');
			}
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				
				if(newTROBL_KND_CD.length != 0) {
					senders.addParam("newTROBL_KND_CD",newTROBL_KND_CD);
					senders.addParam("newTROBL_GRAD",newTROBL_GRAD);
					senders.addParam("newWHEELCHAIR_USE_YN",newWHEELCHAIR_USE_YN);
					senders.addParam("newPRTCTOR_ACMPNY_AT",newPRTCTOR_ACMPNY_AT);
				}
				if(originTROBL_KND_CD.length != 0) {
					senders.addParam("originTROBL_KND_CD",originTROBL_KND_CD);
					senders.addParam("originTROBL_GRAD",originTROBL_GRAD);
					senders.addParam("originWHEELCHAIR_USE_YN",originWHEELCHAIR_USE_YN);
					senders.addParam("originPRTCTOR_ACMPNY_AT",originPRTCTOR_ACMPNY_AT);
				}
				
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/rankOfBarriersSave.json",
				});
				
			} else {
				return;
			}
		}
	};
	
	var upHtml ="<tr name='barrierUp_rowNum'>"
				+ "<th rowspan='2'>rowNum</th>"
				+ "<th>장애종류<span>*</span></th>"
				+ "<td><select id='TROBL_KND_CD_rowNum' class='select'>SelectOption</select></td> "
				+ "<th>장애등급<span>*</span></th>"
				+ "<td><input type='text' id='TROBL_GRAD_rowNum'class='inp' placeholder='장애등급' /></td>"
				+ "</tr>";

	var downHtml = "<tr name='barrierUp_rowNum'>" 
					+ "<th>기타<span>*</span></th>"
					+ "<td colspan='3'>"
					+ "<input type='checkbox' id='WHEELCHAIR_USE_YN_rowNum' />"
					+ "<label for=''>휠체어 사용 시 가능</label>"
					+ "<input type='checkbox' id='PRTCTOR_ACMPNY_AT_rowNum' class='ml20' />"
					+ "<label for=''>보호자 동반 시 가능</label>"
					+ "</td></tr>";
	
	var rowNum = 1;
	
	var option = '';
	var optionValue = [];
	var originTROBL_KND_CD =[];
	var originTROBL_GRAD =[];
	var originWHEELCHAIR_USE_YN =[];
	var originPRTCTOR_ACMPNY_AT =[];
	
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.rankOfBarriersInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#tableTbody").empty();
				
				var rankOfBarriersInfo = result.rankOfBarriersInfo;
				var troblKndList = result.troblKndList;
				
				optionValue = troblKndList;
				// 옵션지정
				for(var trobIndex = 0; trobIndex < troblKndList.length; trobIndex++){
					var optionHtml = '<option value="Code">Name</option>';
					option += optionHtml.replace("Code", troblKndList[trobIndex].CD_VALUE)
											.replace("Name", troblKndList[trobIndex].CD_VALUE_DE);
				}
				
				// 데이터 지정
				if(rankOfBarriersInfo.length != 0) {
					// 데이터가 존재하는경우 정보를 나누어 표시한다.
//					originData = rankOfBarriersInfo;
					for(var index=0; index < rankOfBarriersInfo.length; index++){
						var subUpHtml = '';
						var subdownHtml = '';
						subUpHtml = upHtml.replace(/rowNum/gi, rowNum)
											.replace("SelectOption", option);
						subdownHtml = downHtml.replace(/rowNum/gi, rowNum);
						
						
						
						$("#tableTbody").append(subUpHtml);
						$("#tableTbody").append(subdownHtml);
						
						$("#TROBL_KND_CD_" + rowNum).val(rankOfBarriersInfo[index].TROBL_KND_CD);
						$("#TROBL_GRAD_" + rowNum).val(rankOfBarriersInfo[index].TROBL_GRAD);
						if (rankOfBarriersInfo[index].WHEELCHAIR_USE_YN == 'Y'){
							$("#WHEELCHAIR_USE_YN_" + rowNum).prop("checked", true);
						} else {
							$("#WHEELCHAIR_USE_YN_" + rowNum).prop("checked", false);
						}
						
						if (rankOfBarriersInfo[index].PRTCTOR_ACMPNY_AT == 'Y'){
							$("#PRTCTOR_ACMPNY_AT_" + rowNum).prop("checked", true);
						} else {
							$("#PRTCTOR_ACMPNY_AT_" + rowNum).prop("checked", false);
						}
						originTROBL_KND_CD.push(rankOfBarriersInfo[index].TROBL_KND_CD);
						originTROBL_GRAD.push(rankOfBarriersInfo[index].TROBL_GRAD);
						originWHEELCHAIR_USE_YN.push(rankOfBarriersInfo[index].WHEELCHAIR_USE_YN);
						originPRTCTOR_ACMPNY_AT.push(rankOfBarriersInfo[index].PRTCTOR_ACMPNY_AT);
						rowNum++;
					}
				} else {
					// 정보가 없을 경우 한개의 데이터를 입력 할 수 있는 부분을 만든다.
					var subUpHtml = '';
					var subdownHtml = '';
					subUpHtml = upHtml.replace(/rowNum/gi, rowNum)
										.replace("SelectOption", option);
					subdownHtml = downHtml.replace(/rowNum/gi, rowNum);
					
					$("#tableTbody").append(subUpHtml);
					$("#tableTbody").append(subdownHtml);
					rowNum++;
				}
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 저장 API 호출 End **********/
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.rankOfBarriersSave.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if(res.errCd == 0){
					alert("정상적으로 처리되었습니다.")
				} else {
					
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터이용 가능 장애등급 설정 정보 저장 API 호출 End **********/
	
}(window, document));

