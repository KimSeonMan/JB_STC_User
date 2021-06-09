/**
 * 로그인 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.login = W.$login || {};
	$(document).ready(function() {
		// 쿠키값을 가져온다.
		var cookie_user_id = getLogin();

		/**
		* 쿠키값이 존재하면 id에 쿠키에서 가져온 id를 할당한 뒤
		* 체크박스를 체크상태로 변경
		*/
		if(cookie_user_id != "") {
			$("#user_id").val(cookie_user_id);
			$("#autoLogin").attr("checked", true);
		}

		// 아이디 저장 체크시
		$("#autoLogin").on("click", function(){
			var _this = this;
			var isRemember;
			
			if($(_this).is(":checked")) {
				isRemember = confirm("이 PC에 로그인 정보를 저장하시겠습니까? 공공장소에서는 개인정보가 유출될 수 있으니 주의해주십시오.");
				if(!isRemember) {
					$(_this).attr("checked", false);
				}
			}
		});

		$("#user_id").focus();
		
		// 로그인 이벤트
		$("#login").click(function(){

			var curDate = new Date();
			var openDate = new Date(2019, 10, 1, 0, 0, 0);	// 월은 0부터 시작함
			
			if(curDate < openDate) {
				alert("11월 1일 00시부터 사용가능합니다.");
			}
			else {
				$login.loginProcess();
			}
		});
		
		$('#user_id').on('keypress', function(e) {
			if (e.which == 13) {
				$login.loginProcess();
			}
		});
		
		$('#pwd').on('keypress', function(e) {
			if (e.which == 13) {
				$login.loginProcess();
			}
		});
				
	});
	
	$login = {
		// Login API 호출
		loginProcess : function() {	
			if ($("#user_id").val() == "") {
				alert("아이디를 입력하세요.");
				$("#user_id").focus();
				return false;
			}

			if ($("#pwd").val() == "") {
				alert("비밀번호를 입력하세요.");
				$("#pwd").focus();
				return false;
			}
			
			if($("#autoLogin").is(":checked")){ // 저장 체크시
				saveLogin($("#user_id").val());
			}else{ // 체크 해제시는 공백
				saveLogin("");
			}
			
			var senders = new tscp.login.api();			
			senders.addParam("user_id", $("#user_id").val());
			senders.addParam("pwd", $("#pwd").val());
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/login/Login.json",
			});
		}
	};
	/*********** Login API 호출 Start **********/
	(function() {
		$class("tscp.login.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if (res.errCd == "0") { //성공
					if(result.msg != "Y") {
						alert(result.msg);
					} else {
						window.location.href = contextPath + "/view/index.do"; //이동
					}
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** Login API 호출 End **********/
}(window, document));
	
//쿠키 로그인 아이디 저장
function saveLogin(id) {
	if(id != "") {
		// userid 쿠키에 id 값을 7일간 저장
		setSave("userid", id, 7);
	}else{
		// userid 쿠키 삭제
		setSave("userid", id, -1);
	}
}

function setSave(name, value, expiredays) {
	var today = new Date();
	today.setDate( today.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";"
}

//쿠키값 가져오기	
function getLogin() {
	// userid 쿠키에서 id 값을 가져온다.
	var cook = document.cookie + ";";
	var idx = cook.indexOf("userid", 0);
	var val = "";
	
	if(idx != -1) {
		cook = cook.substring(idx, cook.length);
		begin = cook.indexOf("=", 0) + 1;
		end = cook.indexOf(";", begin);
		val = unescape(cook.substring(begin, end));
	}
	return val;
}