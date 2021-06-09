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
	W.memberStipulation = W.$memberStipulation || {};
	$(document).ready(function() {
		$memberStipulation.memberStipulationInfo();
		
		$("#saveBtn").click(function(){
			$memberStipulation.memberStipulationSave();
		});
	});
	
	$memberStipulation = {
		memberStipulationInfo : function (){
			var senders = new tscp.memberStipulationInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/memberStipulationInfo.json",
			});
		},
		
		memberStipulationSave : function(){
			var senders = new tscp.memberStipulationSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			var CN = [];
			var STPLAT_SE_CD = [10,20];
			senders.addParam("STPLAT_SE_CD",STPLAT_SE_CD);
			senders.addParam('MBER_CN', $("#MBER_CN").val());
			senders.addParam('OTHER_CN', $("#OTHER_CN").val());
			
			
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/memberStipulationSave.json",
				});
				
			} else {
				return;
			}
		}
	};
	/*********** 회원약관 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.memberStipulationInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#tableTbody").empty();
				
				var memberStipulationInfo = result.memberStipulationInfo;
				
				// 데이터 지정
				if(memberStipulationInfo.length != 0) {
					// 데이터가 존재하는경우 정보를 나누어 표시한다.
					for(var index=0; index < memberStipulationInfo.length; index++){
						if(memberStipulationInfo[index].STPLAT_SE_CD == 10) {
							$("#MBER_CN").val(memberStipulationInfo[index].CN);
						} else if (memberStipulationInfo[index].STPLAT_SE_CD == 20){
							$("#OTHER_CN").val(memberStipulationInfo[index].CN);
						}
					}
				} else {
					// 정보가 없을 경우 한개의 데이터를 입력 할 수 있는 부분을 만든다.
					$("#MBER_CN").val("");
					$("#OTHER_CN").val("");
				}
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 저장 API 호출 End **********/
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.memberStipulationSave.api").extend($d.api.ajaxAPI).define({
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

