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
	W.chargeForUsing = W.$chargeForUsing || {};
	$(document).ready(function() {
		$chargeForUsing.chargeForUsingInfo();
		
		$(document).on("click","#plusBtn", function(){
			var subHtml = '';
			subHtml = otherHtml.replace(/rowNum/gi, rowNum);
			
			$("#tableTbody > tr[name='addCharge_"+(rowNum-1)+"']").after(subHtml);
			$("#rowSpanTh").attr("rowspan", rowNum);
			$("#SN_" + rowNum).val(rowNum);
			rowNum++;
		});
		
		$(document).on("click","#minusBtn", function(){
			if(rowNum > 2){
				$(".sTable01 > tbody > tr[name='addCharge_"+rowNum+"']").remove();
				$("#rowSpanTh").attr("rowspan", rowNum-1);
			}
		});
		
		$(document).on("click", "#saveBtn", function(){
			$chargeForUsing.chargeForUsingSave();
		});
	});
	
	$chargeForUsing = {
			chargeForUsingInfo : function (){
			var senders = new tscp.chargeForUsingInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/chargeForUsingInfo.json",
			});
		}, 
		
		chargeForUsingSave : function(){
			var senders = new tscp.chargeForUsingSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			var SN =[];
			var CHRGE_SE_CD = [];
			var SCTN_BEGIN_DSTNC =[];
			var SCTN_END_DSTNC =[];
			var CYCHG = [];
			
			
			for(var i=0; i < rowNum; i++){
				if(($("#SCTN_BEGIN_DSTNC_" + i).val() == null || $("#SCTN_BEGIN_DSTNC_" + i).val() == '') && i != 0) {
					alert("구간시작거리는 필수 값입니다. " + i + " 번째 구간시작거리를 입력해주세요.");
					return;
				}
				if($("#SCTN_END_DSTNC_" + i).val() == null || $("#SCTN_END_DSTNC_" + i).val() == '') {
					if(i == 0) {
						alert("기본요금 구간종료거리는 필수 값입니다. " + i + " 번째 구간종료거리를 입력해주세요.");
					} else {
						alert("추가요금 구간종료거리는 필수 값입니다. " + i + " 번째 구간종료거리를 입력해주세요.");
					}
					return;
				}
				if($("#CYCHG_" + i).val() == null || $("#CYCHG_" + i).val() == '') {
					if(i == 0) {
						alert("기본요금 운임은 필수 값입니다. " + i + " 번째 운임은 입력해주세요.");
					} else {
						alert("추가요금 운임은 필수 값입니다. " + i + " 번째 운임은 입력해주세요.");
					}
					return;
				}
				
				if($("#CNTRYSIDE").val() == '') {
					alert("시외요금은 필수 값입니다.");
				}
				
				SN.push($("#SN_" + i).val());
				if(i==0){
					CHRGE_SE_CD.push(10);
				} else {
					CHRGE_SE_CD.push(20);
				}
				SCTN_BEGIN_DSTNC.push($("#SCTN_BEGIN_DSTNC_" + i).val());
				SCTN_END_DSTNC.push($("#SCTN_END_DSTNC_" + i).val());
				CYCHG.push($("#CYCHG_" + i).val());
			}
			SN.push(rowNum+1);
			CHRGE_SE_CD.push(30);
			SCTN_BEGIN_DSTNC.push(0);
			SCTN_END_DSTNC.push(0);
			CYCHG.push($("#CNTRYSIDE").val());
			
			
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				
				senders.addParam("SN",SN);
				senders.addParam("CHRGE_SE_CD",CHRGE_SE_CD);
				senders.addParam("SCTN_BEGIN_DSTNC",SCTN_BEGIN_DSTNC);
				senders.addParam("SCTN_END_DSTNC",SCTN_END_DSTNC);
				senders.addParam("CYCHG",CYCHG);
				
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/chargeForUsingSave.json",
				});
				
			} else {
				return;
			}
		}
	}
	
	var basicHtml = '<tr><th>기본요금<span>*</span></th><td class="br">'
		+ '<input type="hidden" id="SCTN_BEGIN_DSTNC_rowNum" />'
		+ '<input type="hidden" id="SN_rowNum" />'
		+ '<input type="text" class="inp w60 inp-float" id="SCTN_END_DSTNC_rowNum"/>'
		+ '<span class="etxt">km</span> '
		+ '<input type="text" class="inp w60 ml20 inp-float" id="CYCHG_rowNum"/>'
		+ '<span class="etxt">원</span>'
		+ '</td>'
		+ '<td class="bl ar">'
		+ '<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_plus.png" id="plusBtn"/></a>'
		+ '<a href="javascript:void(0)"><img src="/publishCss/img/ico/ico_mius.png" id="minusBtn"/></a>'
		+ '</td>'
		+ '</tr>'

		
	var firstHtml ="<tr name='addCharge_rowNum'><th id='rowSpanTh'rowspan='2'>추가요금<span>*</span></th>"
		+ "<td colspan='2'>"
		+ "<input type='hidden' id='SN_rowNum' />"
		+ "<input type'text' class='inp w60 inp-float' id='SCTN_BEGIN_DSTNC_rowNum'/>"
		+ "<span class='etxt'>~</span> "
		+ "<input type='text' class='inp w60 inp-float' id='SCTN_END_DSTNC_rowNum'/>"
		+ "<span class='etxt'>km</span>"
		+ "<input type='text' class='inp w60 ml20 inp-float' id='CYCHG_rowNum'/>"
		+ "<span class='etxt'>원/1km</span>"
		+ "</td></tr>";
	
	var otherHtml ="<tr name='addCharge_rowNum'><td colspan='2'>"
				+ "<input type='hidden' id='SN_rowNum' />"
				+ "<input type'text' class='inp w60 inp-float' id='SCTN_BEGIN_DSTNC_rowNum'/>"
				+ "<span class='etxt'>~</span> "
				+ "<input type='text' class='inp w60 inp-float' id='SCTN_END_DSTNC_rowNum'/>"
				+ "<span class='etxt'>km</span>"
				+ "<input type='text' class='inp w60 ml20 inp-float' id='CYCHG_rowNum'/>"
				+ "<span class='etxt'>원/1km</span>"
				+ "</td></tr>";

	
	var lastHtml = '<tr><th>시외요금<span>*</span></th><td colspan="2">'
				+ '<input type="text" class="inp w60 inp-float" id="CNTRYSIDE"/>'
				+ '<span class="etxt">%</span>  '
	var rowNum = 1;
	
	var option = '';
	var optionValue = [];
	var originTROBL_KND_CD =[];
	var originTROBL_GRAD =[];
	var originWHEELCHAIR_USE_YN =[];
	var originPRTCTOR_ACMPNY_AT =[];
	
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.chargeForUsingInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#tableTbody").empty();
				
				var chargeForUsingInfo = result.chargeForUsingInfo;
				// 데이터 지정
				if(chargeForUsingInfo.length != 0) {
					// 데이터가 존재하는경우 정보를 나누어 표시한다.
					for(var i = 0; i < chargeForUsingInfo.length; i++){
						var subHtml = '';
						if(i == 0) {
							subHtml = basicHtml.replace(/rowNum/gi, i);
							rowNum--;
						} else if (i == 1){
							subHtml = firstHtml.replace(/rowNum/gi, i);
						} else if(chargeForUsingInfo[i].CHRGE_SE_CD == 30){
//						} else if(i == chargeForUsingInfo.length -1){
							$("#tableTbody").append(lastHtml);
							$("#CNTRYSIDE").val(chargeForUsingInfo[i].CYCHG)
							rowNum--;
						} else {
							subHtml = otherHtml.replace(/rowNum/gi, i);
						}
						$("#tableTbody").append(subHtml);
						
						$("#SN_" + i).val(i);
						$("#SCTN_BEGIN_DSTNC_" + i).val(chargeForUsingInfo[i].SCTN_BEGIN_DSTNC);
						$("#SCTN_END_DSTNC_" + i).val(chargeForUsingInfo[i].SCTN_END_DSTNC);
						$("#CYCHG_" + i).val(chargeForUsingInfo[i].CYCHG);
						
						$("#rowSpanTh").attr("rowspan", rowNum);
						
						rowNum++;
					}
//					$("#tableTbody").append(lastHtml);
//					$("#CNTRYSIDE").val(chargeForUsingInfo[0].CNTRYSIDE)
				} else {
					$("#tableTbody").append(basicHtml.replace(/rowNum/gi, 0));
					$("#tableTbody").append(firstHtml.replace(/rowNum/gi, 1));
					$("#rowSpanTh").attr("rowspan", rowNum);
					$("#tableTbody").append(lastHtml);
					
					$("#SN_0").val(0);
					$("#SN_1").val(1)
					$("#SCTN_BEGIN_DSTNC_0").val(0)
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
		$class("tscp.chargeForUsingSave.api").extend($d.api.ajaxAPI).define({
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

