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
	W.bookAble = W.$bookAble || {};
	$(document).ready(function() {
		$bookAble.bookAbleInfo();
		$(document).on("click", "#saveBtn", function(){
			$bookAble.bookAbleSave();
		});
	});
	
	$bookAble = {
		bookAbleInfo : function (){
			var senders = new tscp.bookAbleInfo.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/bookAbleInfo.json",
			});
		}, 
		
		bookAbleSave : function(){
			var senders = new tscp.bookAbleSave.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			var RCEPT_SE_CD = [];
			var SCTN_BEGIN_TIME = [];
			var SCTN_END_TIME = [];
			var BEFFAT_RESVE_POSBL_DE = [];
			
			RCEPT_SE_CD.push($("#RCEPT_SE_CD_1").val());
			RCEPT_SE_CD.push($("#RCEPT_SE_CD_2").val());
			
			SCTN_BEGIN_TIME.push($("#SCTN_BEGIN_TIME_1").val());
			SCTN_BEGIN_TIME.push($("#SCTN_BEGIN_TIME_2").val());
			
			SCTN_END_TIME.push($("#SCTN_END_TIME_1").val());
			SCTN_END_TIME.push($("#SCTN_END_TIME_2").val());
			
			BEFFAT_RESVE_POSBL_DE.push(0);
			BEFFAT_RESVE_POSBL_DE.push($("#BEFFAT_RESVE_POSBL_DE_2").val());
			
			if(confirm("해당 설정 정보를 저장하시겠습니까?")){	
				senders.addParam("RCEPT_SE_CD",RCEPT_SE_CD);
				senders.addParam("SCTN_BEGIN_TIME",SCTN_BEGIN_TIME);
				senders.addParam("SCTN_END_TIME",SCTN_END_TIME);
				senders.addParam("BEFFAT_RESVE_POSBL_DE",BEFFAT_RESVE_POSBL_DE);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/bookAbleSave.json",
				});
				
			} else {
				return;
			}
		}
	};
	
	
	/*********** 예약가능시간기준 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.bookAbleInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var bookAbleInfo = result.bookAbleInfo;
				var bookAbleOption = result.bookAbleOption;
				
				
				if(bookAbleInfo.length != 0) {
					for(var i =0; i < bookAbleInfo.length; i++) {
						if(bookAbleInfo[i].RCEPT_SE_CD == 10) {
							$("#RCEPT_SE_CD_1").val(bookAbleInfo[i].RCEPT_SE_CD);
							$("#SCTN_BEGIN_TIME_1").val(Number(bookAbleInfo[i].SCTN_BEGIN_TIME));
							$("#SCTN_END_TIME_1").val(Number(bookAbleInfo[i].SCTN_END_TIME));
							$("#BEFFAT_RESVE_POSBL_DE_1").val(Number(bookAbleInfo[i].BEFFAT_RESVE_POSBL_DE));
						} else if(bookAbleInfo[i].RCEPT_SE_CD == 20) {
							$("#RCEPT_SE_CD_2").val(bookAbleInfo[i].RCEPT_SE_CD);
							$("#SCTN_BEGIN_TIME_2").val(Number(bookAbleInfo[i].SCTN_BEGIN_TIME));
							$("#SCTN_END_TIME_2").val(Number(bookAbleInfo[i].SCTN_END_TIME));
							$("#BEFFAT_RESVE_POSBL_DE_2").val(Number(bookAbleInfo[i].BEFFAT_RESVE_POSBL_DE));
						}
					}
				} else {
					$("#RCEPT_SE_CD_1").val(bookAbleOption[0].CD_VALUE);
					$("#RCEPT_SE_CD_2").val(bookAbleOption[1].CD_VALUE);
				}
				
				
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 저장 API 호출 End **********/
	
	/*********** 센터이용 가능 장애등급 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.bookAbleSave.api").extend($d.api.ajaxAPI).define({
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

