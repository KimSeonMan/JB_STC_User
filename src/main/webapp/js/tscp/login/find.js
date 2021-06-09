/**
 * 아이디/비밀번호찾기 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.find = W.$find || {};
	$(document).ready(function() {
		$("#cancle2").click(function(){
			$(location).attr('href', "/view/login/");
		});		
		$("#cancle1").click(function(){
			$(location).attr('href', "/view/login/");
		});		
		
		
		$("#findeId").click(function(){
			$find.findLoginId();
		});		
		
		$("#findPw").click(function(){
			$find.findLoginPw();
		});		
		
		
	});
	
	$find = {
		findLoginId : function(){
			if ($("#findIdName").val() == "") {
				alert("성명을 입력하세요.");
				$("#findIdName").focus();
				return false;
			}
			
			if ($("#findIdBirthDay").val() == "") {
				alert("생년월일을 입력하세요.");
				$("#findIdBirthDay").focus();
				return false;
			}else if(isNaN($("#findIdBirthDay").val())){
				alert("생년월일은 숫자 8자로 입력하세요.");
				$("#findIdBirthDay").focus();
				return false;
			}
			
			var senders = new tscp.findLoginId.api();			
			senders.addParam("MBER_NM", $("#findIdName").val());
			senders.addParam("BRTHDY", $("#findIdBirthDay").val());
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/login/findLoginId.json",
			});
		},	
		findLoginPw : function(){
			if ($("#findPwId").val() == "") {
				alert("아이디을 입력하세요.");
				$("#findPwId").focus();
				return false;
			}
			if ($("#findPwName").val() == "") {
				alert("성명을 입력하세요.");
				$("#findPwName").focus();
				return false;
			}
			
			if ($("#findPwBirthDay").val() == "") {
				alert("생년월일을 입력하세요.");
				$("#findPwBirthDay").focus();
				return false;
			}else if(isNaN($("#findPwBirthDay").val())){
				alert("생년월일은 숫자 8자로 입력하세요.");
				$("#findPwBirthDay").focus();
				return false;
			}
			
			var senders = new tscp.findLoginPw.api();			
			senders.addParam("MBER_NM", $("#findPwName").val());
			senders.addParam("BRTHDY", $("#findPwBirthDay").val());
			senders.addParam("MBER_ID", $("#findPwId").val());
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/login/findLoginPw.json",
			});
		}	
	};
	
	/*********** Login ID 찾기 Start **********/
	(function() {
		$class("tscp.findLoginId.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if (result.err_cd == "0") { //성공
					if(result.findId != null) {
						alert("회원님의 아이디는 SMS로 발송 되었습니다.");
					}
				} else if(result.err_cd == "99"){
					alert("기입하신 정보와 맞는 아이디가 여러개입니다.\n센터로 문의하여 정보를 확인해주세요.\n콜센터 : 063-227-0002");
				}else if(result.err_cd == "999"){
					alert("기입하신 정보와 맞는 아이디가 없습니다.\n센터로 문의하여 정보를 확인해주세요.\n콜센터 : 063-227-0002");
				}else{
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** Login ID 찾기 호출 End **********/
	
	/*********** Login PW 찾기 Start **********/
	(function() {
		$class("tscp.findLoginPw.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if (res.errCd == "0") { //성공
					if(result.findPw != null) {
						alert("회원님의 비밀번호는 SMS로 발송 되었습니다.");
					}
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** Login PW 찾기 호출 End **********/
}(window, document));
	
