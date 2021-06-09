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
	W.carInfoShare = W.$carInfoShare || {};
	$(document).ready(function() {
		$carInfoShare.carInfoShareInfo();
		
		$("#basicSave").click(function(){
			$carInfoShare.carInfoShareSave();
		});
	});
	
	$carInfoShare = {
		carInfoShareInfo : function (){
			var senders = new tscp.carInfoShareInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/carInfoShareInfo.json",
			});
		},
		
		carInfoShareSave : function(){
			var senders = new tscp.carInfoShareSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			var CNRS_CNTER_CD =[];
			var CNRS_SE_CD =[];
			for(var i = 0; i < rowNum; i++){
				var cnrsCnterCd = $("input[name='CNRS_CNTER_CD_" + i +"']:checked").val();
				CNRS_CNTER_CD.push($("#CNRS_CNTER_CD_" + i).val());
				if(cnrsCnterCd != undefined && cnrsCnterCd != '') {
					CNRS_SE_CD.push($("input[name='CNRS_CNTER_CD_" + i +"']:checked").val());
				} else {
					alert("모든 센터의 차량정보공유 정보를 입력해야합니다.");
				}
				
			}
			
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				senders.addParam("CNRS_CNTER_CD",CNRS_CNTER_CD);
				senders.addParam("CNRS_SE_CD",CNRS_SE_CD);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/carInfoShareSave.json",
				});
				
			} else {
				return;
			}
		}
	};
	
	var rowNum = 0;
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.carInfoShareInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#tableTbody").empty();
				
				var carInfoShareInfo = result.carInfoShareInfo;
				var carShareOption = result.carShareOption;
				
				if(session.cnter_cd.indexOf("NAT") != -1) {
					carShareOption[1].CD_VALUE_DE = "공동배차차량만";
				}
				
				var html = '<tr><th>[CNTER_NM]</th>'
						+ '<td>'
						+ '<input type="hidden" id="CNRS_CNTER_CD_rowNum" />'
						+ '<input type="radio" name="CNRS_CNTER_CD_rowNum" value="' + carShareOption[0].CD_VALUE + '"/>'
						+ '<label for="">' + carShareOption[0].CD_VALUE_DE + '</label> '
						+ '<input type="radio" name="CNRS_CNTER_CD_rowNum" value="' + carShareOption[1].CD_VALUE + '" class="ml20" />'
						+ '<label for="">' + carShareOption[1].CD_VALUE_DE + '</label>'
						+ '</td></tr>'
						
						
				for(var i = 0; i < carInfoShareInfo.length; i++) {
					$("#tableTbody").append(html.replace(/rowNum/gi, i).replace("[CNTER_NM]", carInfoShareInfo[i].CNTER_NM));
					
					$("#CNRS_CNTER_CD_" + i).val(carInfoShareInfo[i].CNRS_CNTER_CD);
					if(carInfoShareInfo[i].CNRS_SE_CD != null && carInfoShareInfo[i].CNRS_SE_CD != undefined) {
						if(carInfoShareInfo[i].CNRS_SE_CD == 10) {
							$('input:radio[name="CNRS_CNTER_CD_' + i +'"]').filter('[value="10"]').attr('checked', true);
						} else if(carInfoShareInfo[i].CNRS_SE_CD == 20){
							$('input:radio[name="CNRS_CNTER_CD_' + i +'"]').filter('[value="20"]').attr('checked', true);
						}
					}
				}
				
				rowNum = carInfoShareInfo.length;
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 저장 API 호출 End **********/
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.carInfoShareSave.api").extend($d.api.ajaxAPI).define({
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

