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
	W.allocateCar = W.$allocateCar || {};
	$(document).ready(function() {
		$allocateCar.allocateCarInfo();
		
		$("#basicSave").click(function(){
			$allocateCar.allocateCarInfoSave("basic");
		});
		
		$("#WDR_SCTNSave").click(function(){
			$allocateCar.allocateCarInfoSave("WDR_SCTN");
		});
		
		$("#WHTHRCSave").click(function(){
			$allocateCar.allocateCarInfoSave("WHTHRC");
		});
		
		
		
	});
	
	$allocateCar = {
		allocateCarInfo : function (){
			var senders = new tscp.allocateCarInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/allocateCarInfo.json",
			});
		},
		
		
		allocateCarInfoSave : function(option){
			var senders = new tscp.allocateCarInfoSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			if(option == "basic"){
				
					
				if($("#ALOC_REQRE_TIME").val() != '' ) {
					if($("#ALOC_REQRE_TIME").val().length > 3){
						alert("목적지 소요시간은 3자리를 넘을수 없습니다.");
						return;
					}
				} else {
					alert("목적지소요시간은 필수 값 입니다.");
					return;
				}
				
				if($("#VHCLE_SEARCH_RADIUS").val() != '' ) {
					if($("#VHCLE_SEARCH_RADIUS").val().length > 3){
						alert("차량검색반경 3자리를 넘을수 없습니다.");
						return;
					}
				} else {
					alert("차량검색반경은 필수 값 입니다.");
					return;
				}
				if($("#BEFFAT_RESVE_TIME_INTRVL").val() != '' ) {
					if($("#BEFFAT_RESVE_TIME_INTRVL").val().length > 3){
						alert("사전예약시간간격 3자리를 넘을수 없습니다.");
						return;
					}
				} else {
					alert("사전예약시간간격은 필수 값 입니다.");
					return;
				}
				senders.addParam("SAVE_OPTION", option);
				senders.addParam("ROUNDTRIP_PERM_AT", $("#ROUNDTRIP_PERM_AT").val());
				senders.addParam("ALOC_REQRE_TIME", $("#ALOC_REQRE_TIME").val());
				senders.addParam("VHCLE_SEARCH_RADIUS", $("#VHCLE_SEARCH_RADIUS").val());
				senders.addParam("BEFFAT_RESVE_TIME_INTRVL", $("#BEFFAT_RESVE_TIME_INTRVL").val());
			} else if(option == "WDR_SCTN") {
				if($("#POSBL_DSTNC").val() != '' ) {
					if($("#POSBL_DSTNC").val().length > 3){
						alert("가능한거리는 3자리를 넘을수 없습니다.");
						return;
					}
				} else {
					alert("가능한거리는 필수 값 입니다.");
					return;
				}
				
				senders.addParam("SAVE_OPTION", option);
				senders.addParam("VHCLE_MVMN_POSBL_AT", $("#VHCLE_MVMN_POSBL_AT").val());
				senders.addParam("POSBL_DSTNC", $("#POSBL_DSTNC").val());
				senders.addParam("WDR_SCTN_OTHINST_MBER_USE_POSBL_AT", $("#WDR_SCTN_OTHINST_MBER_USE_POSBL_AT").val());
			} else if(option == "WHTHRC") {
				senders.addParam("SAVE_OPTION", option);
				senders.addParam("WHTHRC_OTHINST_MBER_USE_POSBL_AT", $("#WHTHRC_OTHINST_MBER_USE_POSBL_AT").val());
			}
				
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/allocateCarInfoSave.json",
				});
			} else {
				return
			}
		}
	};
	
	/***********배차관련설정정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.allocateCarInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if(result.allocateCarInfo != null) {
					$("#ROUNDTRIP_PERM_AT").val(result.allocateCarInfo.ROUNDTRIP_PERM_AT);
					$("#ALOC_REQRE_TIME").val(Number(result.allocateCarInfo.ALOC_REQRE_TIME));
					$("#VHCLE_SEARCH_RADIUS").val(Number(result.allocateCarInfo.VHCLE_SEARCH_RADIUS));
					$("#BEFFAT_RESVE_TIME_INTRVL").val(Number(result.allocateCarInfo.BEFFAT_RESVE_TIME_INTRVL));
					$("#VHCLE_MVMN_POSBL_AT").val(result.allocateCarInfo.VHCLE_MVMN_POSBL_AT);
					$("#POSBL_DSTNC").val(Number(result.allocateCarInfo.POSBL_DSTNC));
					$("#WDR_SCTN_OTHINST_MBER_USE_POSBL_AT").val(result.allocateCarInfo.WDR_SCTN_OTHINST_MBER_USE_POSBL_AT);
					$("#WHTHRC_OTHINST_MBER_USE_POSBL_AT").val(result.allocateCarInfo.WHTHRC_OTHINST_MBER_USE_POSBL_AT);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 확인 API 호출 End **********/
	
	/***********배차관련설정정보 저장 API 호출 Start **********/
	(function() {
		$class("tscp.allocateCarInfoSave.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				if(res.errCd == 0){
					alert("정상적으로 처리되었습니다.");
				} else {
					
				}
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 저장 API 호출 End **********/
	
	
	
}(window, document));

