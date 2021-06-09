/**
 * 회원가입 메소드
 *
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see :
 *
 */
(function(W, D) {
	//"use strict";
	W.memberShip = W.$memberShip || {};
	$(document).ready(function() {
		var sel_cnter_cd ="";
		codeDetailSearch("USER_GBN_CD", "MBER_SE_CD"); //회원구분코드
		codeDetailSearch("PHONE1", "AREA_NO_SE_CD"); //지역번호구분코드
		codeDetailSearch("MOBILE1", "MBTLNUM_SE_CD"); //이동번호구분코드
		codeDetailSearch("EMAIL3", "EMAIL_SE_CD"); //이메일구분코드
		codeDetailSearch("DISABLE_TYPE1_CD", "TROBL_KND_CD"); //장애종류코드1
//		codeDetailSearch("DISABLE_TYPE2_CD", "TROBL_KND_CD"); //장애종류코드2
		codeDetailSearch("WHEELCHAIR_CD", "WHEELCHAIR_SE_CD"); //휠체어구분코드


		$("#agreeCancle").click(function(){
			window.location.href = contextPath + "/view/index.do"; //이동
		});

		//기존센터가입여부 확인 및 동의
		$("#centerHaveChk").click(function(){
			var cnterCd = window.location.href.split("cnterCd=")[1];
			if($("#agreeCk03").is(":checked")){
				window.location.href = contextPath + "/view/memberShip.do?cnterCd="+cnterCd; //이동
			}else
				alert("회원가입 진행을 위해 동의가 필요합니다.");
		});

		//약관동의 확인 이벤트
		$("#agreeChk").click(function(){
			if($("#agreeCk01").is(":checked")) {
				if( (($("#agreeCk02").is(":checked") && $("#agreeCk03").is(":checked"))
					&& $("#agreeCk04").is(":checked"))
					&& $("#agreeCk05").is(":checked")) {
					if ($("#CNTER_CD option:selected").val() == "") {
						alert("센터를 선택해 주세요.");
						$("#CNTER_CD").focus();
						return false;
					} else {
						window.location.href = contextPath + "/view/memberShip.do?cnterCd="+$("#CNTER_CD option:selected").val(); //이동
					}
				} else {
					alert("서비스 이용을 위해 개인보호정책 동의가 필요합니다.");
					$("#agreeCk02").focus();
					return false;
				}
			} else {
				alert("서비스 이용을 위해 이용약관 동의가 필요합니다.");
				$("#agreeCk01").focus();
				return false;
			}
		});

		//회원가입 이벤트
		$("#memberAdd").click(function(){
			$memberShip.memberAdd();
		});

		//회원정보 수정 이벤트
		$("#memberUpdate").click(function(){
			$memberShip.memberUpdate();
		});

		//탈퇴 이벤트
		$("#memberDelete").click(function(){
			$memberShip.memberDelete();
		});

		//우편번호조회 이벤트(daum)
		$("#zipSearch").click(function(){
			execDaumPostcode("ZIP","ADDRESS","ADDRESSDTL");
		});

		//id중복체크
		$("#idCheck").click(function(){
			if($("#USER_ID").val() != ''){
				$memberShip.memberIDDoubleAddChk();
			}
		});

		$memberShip.centerSelectList();

		$("#allCheck").click(function(){
			//만약 전체 선택 체크박스가 체크된상태일경우 
			if($("#allCheck").prop("checked")) {
				//해당화면에 전체 checkbox들을 체크해준다 
				$("input[type=checkbox]").prop("checked",true);
				// 전체선택 체크박스가 해제된 경우 
			} else {
				//해당화면에 모든 checkbox들의 체크를해제시킨다. 
				$("input[type=checkbox]").prop("checked",false);
			}
		})

//		if(window.location.href.split("cnterCd=")[1] != ''){
//			$("span[name=subTitleText]").html("회원가입");
//		} else {
//			$("span[name=subTitleText]").html("마이페이지");
//		}
		$("#CNTER_CD").change(function(){
			$memberStipulation.memberStipulationInfo();
		});

		$("#SMS_RECEIVE_YN").change(function(){
			if($("#SMS_RECEIVE_YN").val() =="N")
				if(confirm("예약 신청 완료 등 다양한 편의 정보를 받아보실 수 없습니다.\n그래도 SMS수신을 거부하시겠습니까?")){
					$("#SMS_RECEIVE_YN").val("N");
				}else{
					$("#SMS_RECEIVE_YN").val("Y");
				}
		});

		$memberShip.birthdayBasicSetting();
		// $memberShip.faxTelNoSetting();
		if ( window.location.href.indexOf("memberShipAgree.do") > 0)
			$memberStipulation.memberStipulationInfo();

	});

	$memberStipulation = {
		memberStipulationInfo: function () {
			var senders = new tscp.memberStipulationInfo.api();
			senders.addParam("CNTER_CD", "WDR-2-001");
			senders.request({
				method: "POST",
				async: false,
				url: contextPath + "/ServiceAPI/mngr/memberStipulationInfo.json",
			});
		}
	};

	$memberShip = {
		// memberShip API 호출
		myPage : function() {
//			lnbChange("마이페이지");	
			var senders = new tscp.myPage.api();
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/login/MyPage.json",
			});
		},
		memberUpdate : function() {
			var USER_GBN_CD = $("#USER_GBN_CD").val(); //회원구분
			if(updateValidation()){
				if(confirm("회원정보 수정하시겠습니까?")) {
					$("#TELNO").val($("#PHONE1").val() + "-" + $("#PHONE2").val() + "-" + $("#PHONE3").val());
					$("#MBTLNUM").val($("#MOBILE1").val() + "-" + $("#MOBILE2").val() + "-" + $("#MOBILE3").val());
					$("#EMAIL").val($("#EMAIL1").val() + "@" + $("#EMAIL2").val());

					if($("#WHEELCHAIR_USE_YEAR").val() == ""){
						$("#WHEELCHAIR_USE_YEAR").val(0);
					}

					if($("#ATCHMNFL_REAL_NM").val() == ''){
						$("#ATCHMNFL_REAL_NM").val(0);
					}

					$("#memberForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/login/MemberShipUpdate.json",
						dataType : "json",
						data : { },
						success : function(data){
							if(data.errCd == 0) {
								alert("회원정보가 수정되었습니다.");
								$(".dialog").hide();
								window.location.href = contextPath + "/view/index.do"; //이동
							} else {
								alert(data.errMsg);
							}
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},
		memberDelete : function() {
			if(confirm("회원탈퇴 하시겠습니까?")) {
				var senders = new tscp.memberDelete.api();
				senders.addParam("MBER_ID", $("#USER_ID").val());
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/login/MemberShipDelete.json",
				});
			}
		},
		memberAdd : function() {
			var USER_GBN_CD = $("#USER_GBN_CD").val(); //회원구분

			if (validation()) {
				if(confirm("회원가입 요청하시겠습니까?")) {
					$("#TELNO").val($("#PHONE1").val() + "-" + $("#PHONE2").val() + "-" + $("#PHONE3").val());
					$("#MBTLNUM").val($("#MOBILE1").val() + "-" + $("#MOBILE2").val() + "-" + $("#MOBILE3").val());
					$("#EMAIL").val($("#EMAIL1").val() + "@" + $("#EMAIL2").val());
					$("#CNTER_CD").val(window.location.href.split("cnterCd=")[1].split("#")[0]);

					$("#BIRTH_DATE").val($("#BRTHDY_Y").val()+""+$("#BRTHDY_M").val()+""+$("#BRTHDY_D").val());

					if($("#WHEELCHAIR_USE_YEAR").val() == ""){
						$("#WHEELCHAIR_USE_YEAR").val(0);
					}

					if($("#ATCHMNFL_REAL_NM").val() == ''){
						$("#ATCHMNFL_REAL_NM").val(0);
					}

					$("#memberForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/login/MemberShipAdd.json",
						dataType : "json",
						data : { },
						success : function(data){

							if(data.errCd == 0) {
								alert("회원가입이 완료되었습니다. 관리자 승인 후 로그인이 가능합니다.");
								$(".dialog").hide();
								window.location.href = contextPath + "/view/index.do"; //이동
							} else {
								alert(data.errMsg);
							}
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},
		memberIDDoubleAddChk : function() {
			var senders = new tscp.memberIDDoubleAddChk.api();
			senders.addParam("MBER_ID", $("#USER_ID").val());
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/login/memberIDDoubleAddChk.json",
			});
		},
		centerSelectList : function() {
			var senders = new tscp.centerSelectList.api();
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/userCodeList.json",
			});
		},
		birthdayBasicSetting : function(){
			var now = new Date();
			var html = "";
			var year = now.getFullYear();
			var month = "";
			var day = "";

			for(var i= year; i>(year-100); i--){
				html+="<option value='"+i+"'>"+i+"</option>"
			}
			$("#BRTHDY_Y").html(html);

			for(var i= 1; i<=12; i++){
				var monthSet = i;
				if(i<10)
					monthSet = "0"+i;
				month+="<option value='"+monthSet+"'>"+i+"</option>"
			}
			$("#BRTHDY_M").html(month);

			for(var i= 1; i<=31; i++){
				var monthSet = i;
				if(i<10)
					monthSet = "0"+i;
				day+="<option value='"+monthSet+"'>"+i+"</option>"
			}
			$("#BRTHDY_D").html(day);
		},
		faxTelNoSetting : function(){
			var senders = new tscp.cnterInfo.api();
			var cnterCd = window.location.href.split("cnterCd=")[1];
			senders.addParam("CNTER_CD", cnterCd);
			senders.request({
				method: "POST",
				async: false,
				url: contextPath + "/ServiceAPI/mngr/memberStipulationInfo.json",
			});

		},

		showLicenceInfo:function() {

			console.log("show!!");
			$("#LicenseeInfoPopup").show();

		},

		cardInfo : function(){
			var user_id = $("#USER_ID").val();
			var mall_id =$("#store_pay_id").val();

			console.log("userID ="+user_id);
			console.log("mall_id ="+mall_id);
			f_start_pay(user_id,mall_id);
		},

		deleteCardInfo : function(){
			var senders = new tscp.deleteEasypayBatchkey.api();

			var user_id = $("#USER_ID").val();
			senders.addParam("MBER_ID", user_id);

			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/easypayBatchkeyDelete.json",
			});
		}
		// updateEasypayBatchkey : function(){
		// 	var senders = new tscp.EasypayBatchkeyUpdate.api();
		// 	senders.addParam("EP_tr_cd", $("form[name='frm_pay'] #EP_tr_cd").val());
		// 	senders.addParam("EP_trace_no", $("form[name='frm_pay'] #EP_trace_no").val());
		// 	senders.addParam("EP_order_no", $("form[name='frm_pay'] #EP_order_no").val());
		// 	senders.addParam("EP_mall_id", $("form[name='frm_pay'] #EP_mall_id").val());
		// 	senders.addParam("EP_sessionkey", $("form[name='frm_pay'] #EP_sessionkey").val());
		// 	senders.addParam("EP_encrypt_data", $("form[name='frm_pay'] #EP_encrypt_data").val());
		// 	senders.addParam("EP_user_id", $("form[name='frm_pay'] #EP_user_id").val());
		// 	senders.addParam("EP_user_name", $("form[name='frm_pay'] #EP_user_name").val());
		// 	senders.addParam("EP_product_nm", $("form[name='frm_pay'] #EP_product_nm").val());
		// 	senders.addParam("EP_product_amt", $("form[name='frm_pay'] #EP_product_amt").val());
		// 	senders.addParam("EP_pay_type", $("form[name='frm_pay'] #EP_pay_type").val());
		// 	senders.addParam("EP_tot_amt", $("form[name='frm_pay'] #EP_tot_amt").val());
		// 	senders.addParam("EP_currency", $("form[name='frm_pay'] #EP_currency").val());
		// 	senders.addParam("EP_card_txtype", $("form[name='frm_pay'] #EP_card_txtype").val());
		// 	senders.addParam("EP_req_type", $("form[name='frm_pay'] #EP_req_type").val());
		// 	senders.addParam("EP_card_amt", $("form[name='frm_pay'] #EP_card_amt").val());
		// 	senders.addParam("EP_wcc", $("form[name='frm_pay'] #EP_wcc").val());
		// 	senders.addParam("EP_card_no", $("form[name='frm_pay'] #EP_card_no").val());
		// 	senders.addParam("EP_install_period", $("form[name='frm_pay'] #EP_install_period").val());
		// 	senders.addParam("EP_noint", $("form[name='frm_pay'] #EP_noint").val());
		// 	senders.request({
		// 		method : "POST",
		// 		async : true,
		// 		url : contextPath + "/ServiceAPI/bassInfo/easypayBatchkeyUpdate.json"
		//
		// 	});
		// }
	};

	/*********** 회원가입 API 호출 Start **********/
	(function() {
		$class("tscp.memberAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("회원가입을 요청했습니다. 운영자  승인 시 이용할 수 있습니다.");
					window.location.href = contextPath + "/view/login/"; //메인페이지 이동
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원가입 API 호출 End **********/

	/*********** 이지페이 배치키 추가 API 호출 Start **********/
	(function() {
		$class("tscp.deleteEasypayBatchkey.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("배치키가 삭제 되었습니다.");
					$("#CARD_INFO").hide();
					$("#batchkeyBtn").text("등록");
					$("#batchkeyDelBtn").hide();
				} else {
					//alert("입력 처리 실패");
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 이지페이 배치키 추가 API 호출 End **********/

	// /*********** 이지페이 배치키 업데이트 API 호출 Start **********/
	// (function() {
	// 	$class("tscp.EasypayBatchkeyUpdate.api").extend($d.api.ajaxAPI).define({
	// 		onSuccess : function(status, res, options) {
	// 			if (res.errCd == "0") { //성공
	// 				if(res.result.r_res_cd == "0" || res.result.r_res_cd == "0000") {
	//
	// 					console.log("res = ",res);
	// 					alert("배치키가 등록 되었습니다.");
	// 					// if (options.callback != undefined && typeof options.callback === "function") {
	// 					// 	options.callback.call(undefined, res);
	// 					// }
	// 					//$("#CARD_INFO").val(res.result.r_card_no);
	// 					$("#CARD_INFO").show();
	// 					$("#batchkeyBtn").text("변경");
	// 					$("#batchkeyDelBtn").show();
	// 				}
	// 				else {
	// 					alert(res.result.r_res_msg);
	// 				}
	//
	// 			} else {
	// 				//alert("입력 처리 실패");
	// 				alert(res.errMsg);
	// 			}
	// 		},
	// 		onFail : function(status) {
	// 		}
	// 	});
	// }());
	// /*********** 이지페이 배치키 업데이트 API 호출 End **********/

	/*********** 마이페이지 API 호출 Start **********/
	(function() {
		$class("tscp.myPage.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var USER_GBN_CD = result.userInfoDetail.USER_GBN_CD;
				var html1 = "";
				var html2 = "";
				var EP_batchkey = result.userInfoDetail.EP_BATCHKEY;
				// console.log("EP_batchkey = "+EP_batchkey);
				// console.log("STORE_ID = "+result.userInfoDetail.STORE_PAY_ID);

				//공통값
				$("#CNTER_CD").val(result.userInfoDetail.CNTER_CD);
				$("#CNTER_NM").html(result.userInfoDetail.CNTER_NM);
				$("#CNTER_NM").val(result.userInfoDetail.CNTER_NM);
				$("#USER_ID_DE").html(result.userInfoDetail.USER_ID);
				$("#USER_ID").val(result.userInfoDetail.USER_ID);
				$("#SMS_RECEIVE_YN").val(result.userInfoDetail.SMS_RECEIVE_YN);
				$("#NAME_DE").html(result.userInfoDetail.NAME);
				$("#NAME").val(result.userInfoDetail.NAME);
				var birth_date = result.userInfoDetail.BIRTH_DATE;
				$("#MY_BIRTH_DATE").html(birth_date.substring(0,4)+"년 "+birth_date.substring(4,6)+"월 "+birth_date.substring(6,8)+"일");
				$("#BIRTH_DATE").val(result.userInfoDetail.BIRTH_DATE);
				$("#MBER_NT_CD").val(result.userInfoDetail.MBER_NT_CD);
				// var valid_dt = result.userInfoDetail.VALID_DT.split("-");
				// $("#VALID_DT").html(valid_dt[0]+"년 "+valid_dt[1]+"월 "+valid_dt[2]+"일");
				if(result.userInfoDetail.MBER_NT_CD == 1) {
					$("#MBER_NT_CD_DE").html("내국인");
				} else {
					$("#MBER_NT_CD_DE").html("외국인");
				}
				$("#SEXDSTN").val(result.userInfoDetail.SEXDSTN);
				if(result.userInfoDetail.SEXDSTN == 'M') {
					$("#SEXDSTN_DE").html("남자");
				} else {
					$("#SEXDSTN_DE").html("여자");
				}

				if(result.userInfoDetail.PHONE != "") {
					var phoneArray = result.userInfoDetail.PHONE.split("-");
					$("#PHONE1").val(phoneArray[0]);
					$("#PHONE2").val(phoneArray[1]);
					$("#PHONE3").val(phoneArray[2]);
				}

				var mobileArray = result.userInfoDetail.MOBILE.split("-");
				$("#MOBILE1").val(mobileArray[0]);
				$("#MOBILE2").val(mobileArray[1]);
				$("#MOBILE3").val(mobileArray[2]);

				$("#ZIP").val(result.userInfoDetail.ZIP);
				$("#ADDRESS").val(result.userInfoDetail.ADDRESS);
				$("#ADDRESSDTL").val(result.userInfoDetail.ADDRESSDTL);

				var emailArray = result.userInfoDetail.EMAIL.split("@");
				$("#EMAIL1").val(emailArray[0]);
				$("#EMAIL2").val(emailArray[1]);

				if(USER_GBN_CD == "10") { //이동약자
//					$("#WELFAIR_CARD_NO").val(result.userInfoDetail.WELFAIR_CARD_NO);
					$("#DISABLE_TYPE1_CD").val(result.userInfoDetail.DISABLE_TYPE1_CD);
					$("#DISABLE_TYPE1_CD_SET").html($("#DISABLE_TYPE1_CD>option:selected").html());
					$("#DISABLE_TYPE2_CD").val(result.userInfoDetail.DISABLE_TYPE2_CD);
					$("#DISABLE_TYPE2_CD_SET").html(result.userInfoDetail.DISABLE_TYPE2_CD);
					$("#WHEELCHAIR_CD").val(result.userInfoDetail.WHEELCHAIR_CD);
					$("#WHEELCHAIR_USE_YEAR").val(result.userInfoDetail.WHEELCHAIR_USE_YEAR);
					$("#SUPPORTER_YN").val(result.userInfoDetail.SUPPORTER_YN);
					$("#COMMUNICALBE_YN").val(result.userInfoDetail.COMMUNICALBE_YN);
					$("#HELPER_YN").val(result.userInfoDetail.HELPER_YN);
					if(result.userInfoDetail.BASIC_LIVELIHOOD_AT_YN != undefined) {
						$("#LIVELIHOOD_YN").val(result.userInfoDetail.BASIC_LIVELIHOOD_AT_YN);
						if(result.userInfoDetail.BASIC_LIVELIHOOD_AT_YN =="Y")
							$("#LIVELIHOOD_YN_SET").html("예");
						else
							$("#LIVELIHOOD_YN_SET").html("아니오");
					} else {
						$("#LIVELIHOOD_YN").val("N");
						$("#LIVELIHOOD_YN_SET").html("아니오");
					}
				} else if(USER_GBN_CD == "20") { //이동보조원
					$("#SUPPORT_LICENSE_NO").val(result.userInfoDetail.SUPPORT_LICENSE_NO);
					html1 += "<th>지원요일<mark>*</mark></th>";
					html1 += "<td colspan='3'>"+checkedVal("chkWeek", result.userInfoDetail.SUPPORT_WEEKDAYS)+"</td>";
					html2 += "<th>지원시간대<mark>*</mark></th>";
					html2 += "<td colspan='3'>"+checkedVal("chkTime", result.userInfoDetail.SUPPORT_HOURS)+"</td>";
					$("#weekarea1").html(html1);
					$("#timearea1").html(html2);
					$("#REMARK1").val(result.userInfoDetail.REMARK);
				} else if(USER_GBN_CD == "30") { //운전자
					html1 += "<th>근무요일<mark>*</mark></th>";
					html1 += "<td colspan='3'>"+checkedVal("chkWeek", result.userInfoDetail.WORK_WEEKDAYS)+"</td>";
					html2 += "<th>근무시간대<mark>*</mark></th>";
					html2 += "<td colspan='3'>"+checkedVal("chkTime", result.userInfoDetail.WORK_HOURS)+"</td>";
					$("#weekarea2").html(html1);
					$("#timearea2").html(html2);
					$("#REMARK2").val(result.userInfoDetail.REMARK);
					$("#BUS_ROUTE_NO").val(result.userInfoDetail.BUS_ROUTE_NO);
				} else if(USER_GBN_CD == "40") { //역무원
					$("#STATION_NAME").val(result.userInfoDetail.STATION_NAME);
					html1 += "<th>근무요일<mark>*</mark></th>";
					html1 += "<td colspan='3'>"+checkedVal("chkWeek", result.userInfoDetail.WORK_WEEKDAYS)+"</td>";
					html2 += "<th>근무시간대<mark>*</mark></th>";
					html2 += "<td colspan='3'>"+checkedVal("chkTime", result.userInfoDetail.WORK_HOURS)+"</td>";
					$("#weekarea2").html(html1);
					$("#timearea2").html(html2);
					$("#REMARK2").val(result.userInfoDetail.REMARK);
				}

				//버튼 변경
				$("#memberAdd").hide();
				$("#memberUpdate").show();
				$("#memberDelete").show();

				if(EP_batchkey !== undefined ) {
					$("#CARD_INFO").show();
					$("#batchkeyBtn").text("변경");
					$("#store_pay_id").val(result.userInfoDetail.STORE_PAY_ID);
					$("#batchkeyDelBtn").show();
				}
				else {
					$("#CARD_INFO").hide();
					$("#batchkeyBtn").text("등록");
					$("#store_pay_id").val(result.userInfoDetail.STORE_PAY_ID);
					$("#batchkeyDelBtn").hide();
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 마이페이지 API 호출 End **********/

	/*********** 회원약관 설정 정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.memberStipulationInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;

				var memberStipulationInfo = result.memberStipulationInfo;

				// 데이터 지정
				if(memberStipulationInfo.length != 0) {
					// 데이터가 존재하는경우 정보를 나누어 표시한다.
					for(var index=0; index < memberStipulationInfo.length; index++){
						if(memberStipulationInfo[index].STPLAT_SE_CD == 10) {
							$("#agreeBox").empty();
							$("#agreeBox").text(memberStipulationInfo[index].CN);
						} else if (memberStipulationInfo[index].STPLAT_SE_CD == 20){
							$("#agreeBox2").empty();
							$("#agreeBox2").text(memberStipulationInfo[index].CN);
						}
						else if (memberStipulationInfo[index].STPLAT_SE_CD == 30){
							$("#agreeBox3").empty();
							$("#agreeBox3").text(memberStipulationInfo[index].CN);
						}
						else if (memberStipulationInfo[index].STPLAT_SE_CD == 40){
							$("#agreeBox4").empty();
							$("#agreeBox4").text(memberStipulationInfo[index].CN);
						}
						else if (memberStipulationInfo[index].STPLAT_SE_CD == 50){
							$("#agreeBox5").empty();
							$("#agreeBox5").text(memberStipulationInfo[index].CN);
						}
					}
				} else {
					// 정보가 없을 경우 한개의 데이터를 입력 할 수 있는 부분을 만든다.
					$("#agreeBox").val("");
					$("#agreeBox2").val("");
				}

			},
			onFail : function(status) {
			}
		});
	}());

	/*********** 회원정보수정 API 호출 Start **********/
	(function() {
		$class("tscp.memberUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("회원정보가 수정되었습니다.");
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원정보수정 API 호출 End **********/

	/*********** 회원탈퇴 API 호출 Start **********/
	(function() {
		$class("tscp.memberDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if (res.errCd == "0") { //성공
					alert("그동안 이용해주셔서 감사합니다.");
					logoutProcess();
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원탈퇴 API 호출 End **********/

	/*********** 아이디중복체크 API 호출 Start **********/
	(function() {
		$class("tscp.memberIDDoubleAddChk.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if (result.confirm.CNT == "0") { //성공
					if(confirm("사용 가능한 아이디입니다.\n등록하시겠습니까?")){
						$("#ID_CHECK_YN").val("Y");
						$("#USER_ID").attr("readonly", true);
					}else{
						$("#ID_CHECK_YN").val("N");
						$("#USER_ID").attr("readonly", false);
					}
				} else {
					alert("이미 사용하고 있는 아이디입니다.");
					$("#ID_CHECK_YN").val("N");
					$("#USER_ID").attr("readonly", false);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 아이디중복체크 API 호출 End **********/

	/*********** 센터 SELECT 리스트 API 호출 Start **********/
	(function() {
		$class("tscp.centerSelectList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				if(result.centerList != null) {
					//센터 구분
					var cnList = "";
					cnList += "<option value=''>선택</option>";
					for(var i = 0; i < result.centerList.length; i++){
						if(session.cnter_cd == result.centerList[i].CNTER_CD){
							cnList += "<option value='"+result.centerList[i].CNTER_CD+"' selected='selected'>"+result.centerList[i].CNTER_NM+"</option>";
						} else {
							cnList += "<option value='"+result.centerList[i].CNTER_CD+"'>"+result.centerList[i].CNTER_NM+"</option>";
						}
					}

					$("#CNTER_CD").html(cnList);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 SELECT 리스트 API 호출 End **********/
	/*********** 해당 센터 정보 API 호출 Start **********/
	(function() {
		$class("tscp.cnterInfo.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var cnterinfo = result.cnterInfo;
				var html = "";
				// 데이터 지정
				if(cnterinfo != null) {
					if(cnterinfo.fax_telno != undefined){
						html += "<br><span class='crTxt'> Fax."+cnterinfo.fax_telno+"</span>";
						$("#ATCHMNFL_REAL_NM").parents("td").append(html);
					}
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 해당 센터 정보 API 호출 End **********/
}(window, document));

function changeEmail(str) {
	if(str == "10") {
		document.getElementById("EMAIL2").disabled = false;
		$("#EMAIL2").val("");
	} else {
		document.getElementById("EMAIL2").disabled = true;
		$("#EMAIL2").val($("#EMAIL3>option:selected").html());
	}
}


function validation() {
	var USER_GBN_CD = $("#USER_GBN_CD").val(); //회원구분

	var pwd = $("#PASSWORD").val();
	var num = pwd.search(/[0-9]/g);
	var eng = pwd.search(/[a-zA-Z]/g);
	var spe = pwd.search(/[`~!@\#$%<>^&*\()\-=+_\|{};:,.?/]/gi);
	var space = pwd.search(/\s/);

	var brhValidation = /^(19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$/; //생년월일

	var id = $("#USER_ID").val();
	var idSpe = id.search(/[`~!@\#$%<>^&*\()\-=+_\|{};:,.?/]/gi);
	var idSpace = id.search(/\s/);
	var idKorean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

	if (idSpace >= 0){
		alert("아이디는 공백없이 입력하세요.");
		$("#USER_ID").focus();
		return false;
	}

	if (idSpe > 0){
		alert("아이디는 특수문자를 사용할 수 없습니다.");
		$("#USER_ID").focus();
		return false;
	}

	if (idKorean.test(id)){
		alert("아이디는 영문과 숫자만 사용할 수 있습니다.");
		$("#USER_ID").focus();
		return false;
	}


	if ($("#NAME").val() == "") {
		alert("이름을 입력하세요.");
		$("#NAME").focus();
		return false;
	}

	if ($("input[name=MBER_NT_CD]:checked").val() == undefined) {
		alert("국적을 선택하세요.");
		$("input[name=nationality]").focus();
		return false;
	}

	if ($("input[name=SEXDSTN]:checked").val() == undefined) {
		alert("성별을 선택하세요.");
		$("input[name=gender]").focus();
		return false;
	}

	if ($("#USER_ID").val() == "") {
		alert("아이디를 입력하세요.");
		$("#USER_ID").focus();
		return false;
	}

	if(space>=0){
		alert("비밀번호는 공백없이 입력하세요.");
		$("#PASSWORD").focus();
		$("#PW_CONFIRM").val("");
		return false;
	}

	if(num<0){
		if(eng<0 || spe<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(eng<0){
		if(num<0 || spe<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(spe<0){
		if(eng<0 || num<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(($("#PASSWORD").val()).length<8){
		alert("비밀번호는 8자리이상 입력하세요.");
		$("#PASSWORD").focus();
		$("#PW_CONFIRM").val("");
		return false;
	}

	if ($("#PASSWORD").val() != $("#PW_CONFIRM").val()) {
		alert("비밀번호가 일치하지 않습니다.");
		$("#PW_CONFIRM").focus();
		return false;
	}

	if ($("#MOBILE2").val() == "") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE2").focus();
		return false;
	}

	if ($("#MOBILE3").val() == "") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE3").focus();
		return false;
	}

	//유효날짜검사 
	var maxDay = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	var year = $("#BRTHDY_Y").val();
	var month = $("#BRTHDY_M").val()*1;
	var day = $("#BRTHDY_D").val();

	if(year % 4 ==0 && year % 100 !=0 || year % 400 == 0){
		maxDay[1]=29;
	}

	if(day > maxDay[month-1]){
		alert("생년월일이 유효하지 않습니다.");
		$("#BRTHDY_D").val("1");
		return false;
	}

	if ($("#ADDRESS").val() == "") {
		alert("주소를 입력하세요.");
		$("#ADDRESS").focus();
		return false;
	}

	if ($("#ADDRESSDTL").val() == "") {
		alert("상세 주소를 입력하세요.");
		$("#ADDRESSDTL").focus();
		return false;
	}

	if ($("#EMAIL1").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL1").focus();
		return false;
	}

	if ($("#EMAIL2").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL2").focus();
		return false;
	}

	if ($("#ID_CHECK_YN").val() == "N") {
		alert("아이디 중복조회를 해주세요.");
		$("#USER_ID").focus();
		return false;
	}

	return true;
}
function updateValidation() {
	var USER_GBN_CD = $("#USER_GBN_CD").val(); //회원구분

	var pwd = $("#PASSWORD").val();
	var num = pwd.search(/[0-9]/g);
	var eng = pwd.search(/[a-zA-Z]/g);
	var spe = pwd.search(/[`~!@\#$%<>^&*\()\-=+_\|{};:,.?/]/gi);
	var space = pwd.search(/\s/);

	if(space>=0){
		alert("비밀번호는 공백없이 입력하세요.");
		$("#PASSWORD").focus();
		$("#PW_CONFIRM").val("");
		return false;
	}

	if(num<0){
		if(eng<0 || spe<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(eng<0){
		if(num<0 || spe<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(spe<0){
		if(eng<0 || num<0){
			alert("비밀번호는 영문, 숫자, 특수문자중 두 종류 이상 사용하세요.");
			$("#PASSWORD").focus();
			$("#PW_CONFIRM").val("");
			return false;
		}
	}

	if(($("#PASSWORD").val()).length<8){
		alert("비밀번호는 8자리이상 입력하세요.");
		$("#PASSWORD").focus();
		$("#PW_CONFIRM").val("");
		return false;
	}

	if ($("#PASSWORD").val() != $("#PW_CONFIRM").val()) {
		alert("비밀번호가 일치하지 않습니다.");
		$("#PW_CONFIRM").focus();
		return false;
	}

	if ($("#MOBILE2").val() == "" || $("#MOBILE1").val()=="선택") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE2").focus();
		return false;
	}

	if ($("#MOBILE3").val() == "") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE3").focus();
		return false;
	}

	if ($("#ADDRESS").val() == "") {
		alert("주소를 입력하세요.");
		$("#ADDRESS").focus();
		return false;
	}

	if ($("#ADDRESSDTL").val() == "") {
		alert("상세 주소를 입력하세요.");
		$("#ADDRESSDTL").focus();
		return false;
	}

	if ($("#EMAIL1").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL1").focus();
		return false;
	}

	if ($("#EMAIL2").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL2").focus();
		return false;
	}

	return true;
}

function fileAdd() {
	$common.attachFileFieldAdd(1, 2048, "jpg,png,gif,tif","B");
}
