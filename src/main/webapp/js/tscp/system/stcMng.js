/**
 * 회원관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.stcMng = W.$stcMng || {};
	$(document).ready(function() {
		$stcMng.list(1);
		
		$("#search").click(function(){
			$stcMng.list(1);
		});
		
		$("#searchText").keydown(function (key) {
			if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$stcMng.list(1);
			}
		});
		
		$("#addBtn").click(function(){
			document.getElementById('stcForm').reset();
			$("#CNTER_CD_orgin").val("");
			settingMouAndLevel();
			$stcMng.getStcUpperCd();
			
//			$("#stcForm :input").attr("disabled", false);
			$("#addStc").show();
			$("#editDetail").hide();
			$("#deleteDetail").hide();
			$("#editStc").hide();
			$("#stcPopUp").show();
		});
		
		$("#closeImg").click(function(){
			$("#stcPopUp").hide();
		});
		
		$("#canclePopup").click(function(){
			$("#stcPopUp").hide();
		});
		
		$("#addStc").click(function(){
			$stcMng.stcMngAdd();
		});
		
		$("#delBtn").click(function(){
			var str = checkReturnVal("chk"); 
			$stcMng.stcMngDelete(str);
		});
		
		$("#deleteDetail").click(function(){
			$stcMng.stcMngDelete($("#CNTER_CD").val());
		});
		
		$("#mouYn").change(function(){
			settingMouAndLevel();
			$stcMng.getStcUpperCd();
		});
		
		$("#LEVEL").change(function(){
			settingMouAndLevel();
			$stcMng.getStcUpperCd();
		});
		
		$("#editDetail").click(function(){
			$stcMng.stcMngModify();
		});
		
	});
	
	$stcMng = {
		natCnterNo : 0,
		wdrCnterNo : 0,
		are2CnterNo : 0,
		are3CnterNo : 0,
		list : function(page){
			$("#paging").empty();
			var senders = new tscp.stcMngList.api();
			senders.addParam("page", page);
			if($("#searchText").val() != ''){
				if($("#searchType").val() == 'CNTER_NM') {
					senders.addParam("CNTER_NM", $("#searchText").val());
				} else if ($("#searchType").val() == 'CNTER_CD') {
					senders.addParam("CNTER_CD", $("#searchText").val());
				} else if ($("#searchType").val() == 'UPPER_CNTER_CD') {
					senders.addParam("UPPER_CNTER_CD", $("#searchText").val());
				}
			}
			if($("#searchMou").val() != '') {
				senders.addParam("searchMou", $("#searchMou").val());
			}
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/stcMngList.json",
			});
		},
		
		stcMngDetail : function(cnterCd) {
			var senders = new tscp.stcMngDetail.api();
			senders.addParam("CNTER_CD", cnterCd);
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/stcMngDetail.json",
			});
		},
		
		stcMngAdd : function() {
			var senders = new tscp.stcMngAdd.api();
			senders.addParam("LEVEL", $("#LEVEL").val());
			if($("#TELNO").val() != '' && $("#TELNO").val() != undefined) {
				senders.addParam("TELNO", $("#TELNO").val());
			} else {
				alert("대표번호는 필수 값입니다.");
				$("#TELNO").focus();
				return;
			}
			
			if($("#UPPER_CNTER_CD option:selected").val() != '') {
				senders.addParam("UPPER_CNTER_CD", $("#UPPER_CNTER_CD").val());
			} else {
				alert("상위센터코드는 필수 값입니다.");
				$("#UPPER_CNTER_CD").focus();
				return;
			}
			if($("#CNTER_NM").val() != '' && $("#CNTER_NM").val() != undefined) {
				senders.addParam("CNTER_NM", $("#CNTER_NM").val());
			} else {
				alert("센터명은 필수 값입니다.");
				$("#CNTER_NM").focus();
				return;
			}
			if($("#AREA_NM_EN").val() != '' && $("#AREA_NM_EN").val() != undefined) {
				senders.addParam("AREA_NM_EN", $("#AREA_NM_EN").val());
			} else {
				alert("지역명(영문)은 필수 값입니다.");
				$("#AREA_NM_EN").focus();
				return;
			}
			if($("#ZIP_CODE").val() != '' && $("#ZIP_CODE").val() != undefined) {
				senders.addParam("ZIP_CODE", $("#ZIP_CODE").val());
				senders.addParam("ADRES", $("#ADRES").val());
			} else {
				alert("우편번호는 필수 값입니다.");
				$("#ZIP_CODE").focus();
				return;
			}
			if(($("#ADRES_DETAIL").val() != '' && $("#ADRES_DETAIL").val() != undefined)){
				senders.addParam("ADRES_DETAIL", $("#ADRES_DETAIL").val());
			}else {
				alert("주소란의 두번째 칸에 상세주소를 입력해주세요 .");
				$("#ADRES_DETAIL").focus();
				return;
			}
			
			if($("#MANAGE_AREA").val() != '' && $("#MANAGE_AREA").val() != undefined) {
				senders.addParam("MANAGE_AREA", $("#MANAGE_AREA").val());
			} else {
				alert("관리지역은 필수 값입니다.");
				$("#MANAGE_AREA").focus();
				return;
			}
			
			senders.addParam("CNTER_CD", $("#CNTER_CD").val());
			
			
			if(confirm("입력한 STC 정보를 등록하시겠습니까?")){
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/system/stcMngAdd.json",
				});
			}
		},
		stcMngModify : function() {
			var senders = new tscp.stcMngAdd.api();
			senders.addParam("LEVEL", $("#LEVEL").val());
			if($("#TELNO").val() != '' && $("#TELNO").val() != undefined) {
				senders.addParam("TELNO", $("#TELNO").val());
			} else {
				alert("대표번호는 필수 값입니다.");
				$("#TELNO").focus();
				return;
			}
			
			if($("#UPPER_CNTER_CD option:selected").val() != '') {
				senders.addParam("UPPER_CNTER_CD", $("#UPPER_CNTER_CD").val());
			} else {
				alert("상위센터코드는 필수 값입니다.");
				$("#UPPER_CNTER_CD").focus();
				return;
			}
			if($("#CNTER_NM").val() != '' && $("#CNTER_NM").val() != undefined) {
				senders.addParam("CNTER_NM", $("#CNTER_NM").val());
			} else {
				alert("센터명은 필수 값입니다.");
				$("#CNTER_NM").focus();
				return;
			}
			if($("#AREA_NM_EN").val() != '' && $("#AREA_NM_EN").val() != undefined) {
				senders.addParam("AREA_NM_EN", $("#AREA_NM_EN").val());
			} else {
				alert("지역명(영문)은 필수 값입니다.");
				$("#AREA_NM_EN").focus();
				return;
			}
			if($("#ZIP_CODE").val() != '' && $("#ZIP_CODE").val() != undefined) {
				senders.addParam("ZIP_CODE", $("#ZIP_CODE").val());
				senders.addParam("ADRES", $("#ADRES").val());
			} else {
				alert("우편번호는 필수 값입니다.");
				$("#ZIP_CODE").focus();
				return;
			}
			
			if(($("#ADRES_DETAIL").val() != '' && $("#ADRES_DETAIL").val() != undefined)){
				senders.addParam("ADRES_DETAIL", $("#ADRES_DETAIL").val());
			}else {
				alert("주소란의 두번째 칸에 상세주소를 입력해주세요 .");
				$("#ADRES_DETAIL").focus();
				return;
			}
			if($("#MANAGE_AREA").val() != '' && $("#MANAGE_AREA").val() != undefined) {
				senders.addParam("MANAGE_AREA", $("#MANAGE_AREA").val());
			} else {
				alert("관리지역은 필수 값입니다.");
				$("#MANAGE_AREA").focus();
				return;
			}				
			senders.addParam("CNTER_CD", $("#CNTER_CD").val());
			if(confirm("입력한 STC 정보를 수정하시겠습니까?")){
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/system/stcMngModify.json",
				});
			}
		},
	
		getStcUpperCd : function() {
			var senders = new tscp.getStcUpperCd.api();
			senders.addParam("CNTER_CD", $("#CNTER_CD").val());
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/getStcUpperCd.json",
			});
		},
		
		stcMngDelete : function(str){
			if(confirm("선택하신 센터를 삭제하시겠습니까?")) {
				var senders = new tscp.stcMngDelete.api();
				senders.addParam("CNTER_CD", str);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/system/stcMngDelete.json",
				});
			}
		}
	};
	
	var basicOptionHtml = "<option value=''>선택</option>";
	var optionHtml = "<option value='[OPTION_VALUE]'>[OPTION_TEXT]</option>";
	
	/*********** 센터목록 API 호출 Start **********/
	(function() {
		$class("tscp.stcMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#list").empty(); //리스트 삭제
				
				var result = res.result;
				$stcMng.natCnterNo = result.cnterNo.NAT_NO;
				$stcMng.wdrCnterNo = result.cnterNo.WDR_NO;
				$stcMng.are2CnterNo = result.cnterNo.ARE_2_NO;
				$stcMng.are3CnterNo = result.cnterNo.ARE_3_NO;
				
				var html1 = "";
				var cd_id = "";
				
				
				html1 += "<colgroup>";
				html1 += "<col width='60' />";
				html1 += "<col width='80' />";
				html1 += "<col width='200' />";
				html1 += "<col width='' />";
				html1 += "<col width='' />";
				html1 += "<col width='' />";
				html1 += "<col width='' />";
				html1 += "</colgroup>";
				
				
				html1 += "<tr>";
				html1 += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html1 += "<th>레벨</th>";
				html1 += "<th>센터명</th>";
				html1 += "<th>센터코드</th>";
				html1 += "<th>상위센터코드</th>";
				html1 += "<th>연락처</th>";
				html1 += "<th>MOU여부</th>";
				html1 += "</tr>";
				
				if(result.stcMngList.length == 0) {
					html1 += "<tr><td colspan='7'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.stcMngList.length; i++) {
						if(i == 0) {
							cd_id = result.stcMngList[i].CD_ID;
						}
						
						var cnterCdArr = result.stcMngList[i].CNTER_CD.split("-");
						var clickEvent = "class='link' onclick=\"javascript:$stcMng.stcMngDetail(\'"+result.stcMngList[i].CNTER_CD+"\') \"";
						html1 += "<tr>";
						html1 += "<td><input type='checkbox' name='chk' value='"+result.stcMngList[i].CNTER_CD+"'/></td>";
						html1 += "<td>"+result.stcMngList[i].LEVEL+"</td>";
						html1 += "<td "+clickEvent+">"+result.stcMngList[i].CNTER_NM+"</td>";
						html1 += "<td>"+result.stcMngList[i].CNTER_CD+"</td>";
						html1 += "<td>"+result.stcMngList[i].UPPER_CNTER_CD+"</td>";
						html1 += "<td>"+result.stcMngList[i].TELNO+"</td>";
						if(cnterCdArr[0] == "NAT") {
							html1 += "<td>아니오</td>";
						} else if(cnterCdArr[0] == "ARE" && cnterCdArr[1] == "2") {
							html1 += "<td>아니오</td>";
						} else {
							html1 += "<td>예</td>";
						}
						
						html1 += "</tr>";
					}
				}
				
				$("#list").html(html1);
				$("#listTotal").val(result.stcMngListTotalCount[0].CNT);
				$stcMng.totalCnt = result.stcMngListTotalCount[0].CNT;
				paging($stcMng);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터목록 API 호출 End **********/
	
	/*********** 센터 상세정보 API 호출 Start **********/
	(function() {
		$class("tscp.stcMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#stcPopUp").show();
				var result = res.result;
				
				$("#CNTER_CD_orgin").val(result.stcMngDetail.CNTER_CD);
				$("#CNTER_CD").val(result.stcMngDetail.CNTER_CD);
				
				$("#LEVEL").val(result.stcMngDetail.LEVEL);
				$("#TELNO").val(result.stcMngDetail.TELNO);
				
				var upperOptionHtml = basicOptionHtml;
				for(var i = 0; i < result.upperCnterList.length; i++) {
					upperOptionHtml += optionHtml.replace("[OPTION_VALUE]", result.upperCnterList[i].CNTER_CD)
					.replace("[OPTION_TEXT]", result.upperCnterList[i].CNTER_CD + "(" + result.upperCnterList[i].CNTER_NM + ")")
				}
				
				$("#UPPER_CNTER_CD").empty();
				$("#UPPER_CNTER_CD").html(upperOptionHtml);
				$("#UPPER_CNTER_CD").val(result.stcMngDetail.UPPER_CNTER_CD);
				
				$("#CNTER_NM").val(result.stcMngDetail.CNTER_NM);
				$("#AREA_NM_EN").val(result.stcMngDetail.AREA_NM_EN);
				$("#DOMN").val(result.stcMngDetail.DOMN);
				$("#ZIP_CODE").val(result.stcMngDetail.ZIP_CODE);
				$("#ADRES").val(result.stcMngDetail.ADRES);
				$("#ADRES_DETAIL").val(result.stcMngDetail.ADRES_DETAIL);
				$("#MANAGE_AREA").val(result.stcMngDetail.MANAGE_AREA);
				
				if($("#CNTER_CD_orgin").val().indexOf("ARE-2") > 0){
					$("#mouYn").val("N");
				} else {
					$("#mouYn").val("Y");
				}
				
				$("#addStc").hide();
				$("#editDetail").show();
				$("#deleteDetail").show();
				$("#editStc").hide();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 상세정보 API 호출 End **********/
	
	/*********** 센터 정보 등록 API 호출 Start **********/
	(function() {
		$class("tscp.stcMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				if(res.errCd == 0){
					alert("정상적으로 처리되었습니다.");
					$("#stcPopUp").hide();
					$stcMng.list(1);
				} else {
					
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 정보 등록 API 호출 End **********/
	/*********** 센터 정보 수정 API 호출 Start **********/
	(function() {
		$class("tscp.stcMngModify.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				if(res.errCd == 0){
					alert("정상적으로 처리되었습니다.");
					$("#stcPopUp").hide();
					$stcMng.list(1);
				} else {
					
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 정보 수정 API 호출 End **********/
	/*********** 센터 정보 삭제 API 호출 Start **********/
	(function() {
		$class("tscp.stcMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				if(res.errCd == 0){
					alert("정상적으로 처리되었습니다.");
					$("#stcPopUp").hide();
					$stcMng.list(1);
				} else {
					
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 정보 삭제 API 호출 End **********/
	/*********** 상위 센터 정보 API 호출 Start **********/
	(function() {
		$class("tscp.getStcUpperCd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				var upperOptionHtml = basicOptionHtml;
				if(result.upperCnterList.length != 0) {
					if(result.upperCnterList[0].CNTER_CD == $("#CNTER_CD").val() && result.upperCnterList.length == 1) {
						upperOptionHtml += optionHtml.replace("[OPTION_VALUE]", result.upperCnterList[0].CNTER_CD)
						.replace("[OPTION_TEXT]", result.upperCnterList[0].CNTER_CD)
					} else {
						for(var i = 0; i < result.upperCnterList.length; i++) {
							upperOptionHtml += optionHtml.replace("[OPTION_VALUE]", result.upperCnterList[i].CNTER_CD)
							.replace("[OPTION_TEXT]", result.upperCnterList[i].CNTER_CD + "(" + result.upperCnterList[i].CNTER_NM + ")")
						}
					}
				}
				
				$("#UPPER_CNTER_CD").empty();
				$("#UPPER_CNTER_CD").html(upperOptionHtml);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 센터 상세정보 API 호출 End **********/
	
}(window, document));

// 센터 코드생성
function setCnterCd(){
	var level = $("#LEVEL").val();
	var mouYn = $("#mouYn").val();
	var orginCnterCd = $("#CNTER_CD_orgin").val().split("-");
	
	var cnterCd = '';
	if(level == 1) {
		if(orginCnterCd[0] != ''){
			if(orginCnterCd[0] == 'NAT' && orginCnterCd[1] == level){
				cnterCd = $("#CNTER_CD_orgin").val();
			} else {
				cnterCd = 'NAT-' + level + "-" + pad($stcMng.natCnterNo, 3);
			}
		} else {
			cnterCd = 'NAT-' + level + "-" + pad($stcMng.natCnterNo, 3);
		}
	} else if (level == 2) {
		if(orginCnterCd[0] != ''){ 
			if(orginCnterCd[0] == 'WDR' && orginCnterCd[1] == level){
				cnterCd = $("#CNTER_CD_orgin").val();
			} else if (orginCnterCd[0] == 'ARE' && orginCnterCd[1] == level) {
				cnterCd = $("#CNTER_CD_orgin").val();
			} else {
				if(mouYn == "Y") {
					cnterCd = 'WDR-' + level + "-" + pad($stcMng.wdrCnterNo, 3);
				} else if(mouYn == "N") {
					cnterCd = 'ARE-' + level + "-" + pad($stcMng.are2CnterNo, 3);
				}
			}
		} else {
			if(mouYn == "Y") {
				cnterCd = 'WDR-' + level + "-" + pad($stcMng.wdrCnterNo, 3);
			} else if(mouYn == "N") {
				cnterCd = 'ARE-' + level + "-" + pad($stcMng.are2CnterNo, 3);
			}
		}
	} else if (level == 3) {
		if(orginCnterCd[0] != ''){ 
			if(orginCnterCd[0] == 'ARE' && orginCnterCd[1] == level){
				cnterCd = $("#CNTER_CD_orgin").val();
			} else {
				cnterCd = 'ARE-' + level + "-" + pad($stcMng.are3CnterNo, 3);
			}
		} else {
			cnterCd = 'ARE-' + level + "-" + pad($stcMng.are3CnterNo, 3);
		}
	}
	$("#CNTER_CD").val(cnterCd);
}

function pad(n, width) {
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

// 주소호출
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('ZIP_CODE').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('ADRES').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('ADRES_DETAIL').focus();
            
        }
    }).open();
}


function settingMouAndLevel(){
	var level = $("#LEVEL").val();
	var mouYn = $("#mouYn").val();
	
	// level 이 1일 경우 mou여부를 '아니오'로 설정 한뒤 disable 시켜준다.
	if(level == 1) {
		$("#mouYn").val("N");
		$("#mouYn").css("background-color", "lightgray");
	    $("#mouYn").attr("disabled", true);
	} else if (level == 2) {
		$("#mouYn").css("background-color", "");
		$("#mouYn").attr("disabled", false);
	} else if(level == 3) {
		$("#mouYn").css("background-color", "");
		$("#mouYn").attr("disabled", false);
		if(mouYn != 'Y'){
			alert("단독 지역의 경우 level 값을 2로 설정하도록 합니다. level 값이 자동으로 변경됩니다.");
			$("#LEVEL").val(2);
		}
	}
	setCnterCd();
}
