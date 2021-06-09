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
	W.userMng = W.$userMng || {};
	$(document).ready(function() {
		$userMng.list(1); //목록 조회
		
		codeDetailSearch("MBER_SE_CD", "MBER_SE_CD"); //회원구분코드
		codeDetailSearch("MBER_STTUS_CD", "MBER_STTUS_CD"); //회원상태코드
		
		//엔터키
		$('#MBER_NM').on('keypress', function(e) {
			if (e.which == 13) {
				$userMng.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#MBER_SE_CD").change(function(){
			$userMng.list(1);
		});
		
		$("#MBER_STTUS_CD").change(function(){
			$userMng.list(1);
		});
		
		$("#searchList").click(function(){
			$userMng.list(1);
		});
		
		$("#userMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$userMng.userMngDelete(str);
		});
		
	});
	
	$userMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		// userMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.userMnglist.api();
			senders.addParam("page", page);
			if($("#MBER_SE_CD").val() != "") {
				senders.addParam("MBER_SE_CD", $("#MBER_SE_CD").val());
			}
			if($("#MBER_STTUS_CD").val() != "") {
				senders.addParam("MBER_STTUS_CD", $("#MBER_STTUS_CD").val());
			}
			if($("#MBER_NM").val() != "") {
				senders.addParam("MBER_NM", $("#MBER_NM").val());
			}
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/userMnglist.json",
			});
		},
		userMngDetail : function(str1, str2) {	
			var senders = new tscp.userMngDetail.api();
			senders.addParam("MBER_ID", str1);
			senders.addParam("MBER_SE_CD", str2);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/userMngDetail.json",
			});
		},
		addUserForm : function(){
			var senders = new tscp.addUserForm.api();
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/userCodeList.json",
			});
		},
		userMngUpdate : function(str1, str2) {	//회원가입 승인
			if(confirm(str2+"님의 회원가입을 승인하시겠습니까?")) {
				var senders = new tscp.userMngUpdate.api();
				senders.addParam("MBER_ID", str1);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/bassInfo/userMngUpdate.json",
				});
			}
		},
		userMngDelete : function(str) {	//회원강제 탈퇴
			if(confirm("선택하신 회원을 강제 탈퇴하시겠습니까?")) {
				var senders = new tscp.userMngDelete.api();
				senders.addParam("MBER_ID", str);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/login/MemberShipDelete.json",
				});
			}
		},
		userInfoUpdate : function(){
			if(confirm("정보를 수정하시겠습니까?")){
				var senders = new tscp.userInfoUpdate.api();
				
				// 기본정보
				senders.addParam("MBER_ID", $("#val5").val());//ID
				senders.addParam("CNTER_CD", $("#val1").val());//센터코드
				senders.addParam("MBER_SE_CD", $("#val2").val());//구분코드
				senders.addParam("MBER_NM", $("#val3").val());//이름
				
				var gender =  $('input[name="gender"]:checked').val();
				
				senders.addParam("SEXDSTN", gender);//성별
				senders.addParam("BRTHDY", $("#val8").val());//생년월일
				senders.addParam("ZIP", $("#val15").val());//우편번호
				senders.addParam("ADRES", $("#val16").val());//주소
				senders.addParam("ADRES_DETAIL", $("#val17").val());//상세주소
				
				var telNo = ""+ $("#val9").val() +"-"+$("#val10").val() + "-" +$("#val11").val() + "";
				
				senders.addParam("TELNO", telNo);//유선전화
				
				var phoneNo = "" + $("#val12").val()  + "-" + $("#val13").val()  + "-" +$("#val14").val()  +"";
				
				senders.addParam("MBTLNUM", phoneNo);//휴대전화
				
				var mail = "" + $("#val18").val() + "@" + $("#val19").val() + "";
				
				senders.addParam("EMAIL", mail);//이메일
				
				var sms =  $('input[name="smsChk"]:checked').val();
				
				senders.addParam("SMS_RECPTN_AT", sms);//SMS수신여부
				
				var userNt = $('input[name="ntCodeVal"]:checked').val();
				senders.addParam("MBER_NT_CD", userNt);//내외국인 체크
				
				//부가정보 : 이동약자
				if($("#val2").val() == 10){
					senders.addParam("TROBL_KND_CD", $("#val21").val());//장애구분
					senders.addParam("TROBL_GRAD", $("#val22").val());//장애등급
					
					var assntPerson = $('input[name="asstn"]:checked').val();
					
					senders.addParam("ASSTN_PERSON_ENNC", assntPerson);//보조인유무
					senders.addParam("WHEELCHAIR_SE_CD", $("#val23").val());//휠체어 구분
					senders.addParam("WHEELCHAIR_USE_PD", $("#val24").val());//휠체어 사용기간
					
					var assntNeed = $('input[name="asstnNeed"]:checked').val();
					
					senders.addParam("ASSTN_NEED_AT", assntNeed);//도움여부
					
					var mlrd = $('input[name="mlrd"]:checked').val();
					
					senders.addParam("DS_MLRD_POSBL_AT", mlrd);//의사소통 가능여부
				}else if($("#val2").val() == 20){
					
					//부가정보 : 이동보조원
					senders.addParam("ASSTN_LCNSE_NO", $("#val26").val());//자격증번호
					
					var weekDay = "";
					 $('input:checkbox[name="weekChk"]').each(function(){
						 if($(this).is(":checked")){
							 weekDay += "1";
						 }else{
							 weekDay += "0";
						 }
						
					 });
					
					senders.addParam("WORK_DE", weekDay);//근무요일
					
					var timeZon = "";
					 $('input:checkbox[name="time"]').each(function(){
						 if($(this).is(":checked")){
							 timeZon += "1";
						 }else{
							 timeZon += "0";
						 }
						
					 });
					
					senders.addParam("WORK_TMZON", timeZon);//근무시간
				}else if($("#val2").val() == 30){
					//부가정보 : 운전자
					senders.addParam("VHCLE_NO",  $("#val28").val());//차량번호
					
					var weekDay = "";
					 $('input:checkbox[name="drWeekChk"]').each(function(){
						 if($(this).is(":checked")){
							 weekDay += "1";
						 }else{
							 weekDay += "0";
						 }
					 });
					
					senders.addParam("WORK_DE", weekDay);
					
					var timeZon = "";
					 $('input:checkbox[name="drTime"]').each(function(){
						 if($(this).is(":checked")){
							 timeZon += "1";
						 }else{
							 timeZon += "0";
						 }
					 });
					
					senders.addParam("WORK_TMZON", timeZon);
					senders.addParam("RM", $("#val29").val());//비고
					
				}else{
					//부가정보 : 역무원
					senders.addParam("STATION_NM", $("#val30").val());//역명
					senders.addParam("STATION_LINE", $("#val31").val());//호선 구분
					
					var weekDay = "";
					 $('input:checkbox[name="stweekChk"]').each(function(){
						 if($(this).is(":checked")){
							 weekDay += "1";
						 }else{
							 weekDay += "0";
						 }
					 });
					
					senders.addParam("WORK_DE", weekDay);
					
					var timeZon = "";
					 $('input:checkbox[name="sttime"]').each(function(){
						 if($(this).is(":checked")){
							 timeZon += "1";
						 }else{
							 timeZon += "0";
						 }
					 });
					
					senders.addParam("WORK_TMZON", timeZon);
				}
				
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/login/MemberShipUpdate.json",
				});
			}
		},
		addUser : function(){
			
			if($("#idCheckVal").val() == 0){
				alert("아이디 중복 체크를 해주세요.");
				$("#MBER_ID").focus();
			}else if($("#MBER_NAME").val() == ""){
				alert("이름을 입력하세요.");
				$("#MBER_NAME").focus();
			}else if($("#PASSWORD").val() == "" || $("#checkPassWord").val() == ""){
				alert("비밀번호를 입력하세요.");
				$("#PASSWORD").focus();
			}else if($("#phone1").val() == "" || $("#phone2").val() == "" || $("#phone3").val() == ""){
				alert("휴대폰번호를 입력하세요.");
				$("#phone2").focus();
			}else if($("#ZIP").val() == "" || $("#ADRES").val() == ""){
				alert("주소를 입력하세요.");
			}else if($("#mail1").val() == "" || $("#mail2").val() == ""){
				alert("이메일을 입력하세요.");
				$("#mail1").focus();
			}else{
				
				if(confirm("회원을 등록하시겠습니까?")){
					var senders = new tscp.addUser.api();
					
					//기본정보
					senders.addParam("CNTER_CD", $("#CNTER_CD").val());
					senders.addParam("MBER_SE_CD", $("#MBER_CD").val());
					senders.addParam("MBER_NM", $("#MBER_NAME").val());
					
					var userNt = $('input[name="ntCode"]:checked').val();
					senders.addParam("MBER_NT_CD", userNt);
					
					senders.addParam("MBER_ID", $("#MBER_ID").val());
					
					var smsCheck =  $('input[name="smsYN"]:checked').val();
					senders.addParam("SMS_RECPTN_AT", smsCheck);
					
					senders.addParam("PASSWORD", $("#PASSWORD").val());
					
					var gender =  $('input[name="gen"]:checked').val();
					senders.addParam("SEXDSTN", gender);
					
					senders.addParam("BRTHDY", $("#BRTHDY").val());
					
					var telNo = ""+ $("#tel1").val() +"-"+$("#tel2").val() + "-" +$("#tel3").val() + "";
					senders.addParam("TELNO", telNo);
					
					var phoneNo = "" + $("#phone1").val()  + "-" + $("#phone2").val()  + "-" +$("#phone3").val()  +"";
					senders.addParam("MBTLNUM", phoneNo);
					
					senders.addParam("ZIP", $("#ZIP").val());
					senders.addParam("ADRES", $("#ADRES").val());
					senders.addParam("ADRES_DETAIL", $("#ADRES_DETAIL").val());
					
					var mail = "" + $("#mail1").val() + "@" + $("#mail2").val() + "";
					senders.addParam("EMAIL", mail);
					
					//부가정보 : 이동약자
					if($("#MBER_CD").val() == 10){
						senders.addParam("TROBL_KND_CD", $("#TROBL_KND_CD").val());
						senders.addParam("TROBL_GRAD", $("#TROBL_GRAD").val());
						senders.addParam("WHEELCHAIR_SE_CD", $("#WHEELCHAIR_SE_CD").val());
						senders.addParam("WHEELCHAIR_USE_PD", $("#WHEELCHAIR_USE_PD").val());
						
						var assntPerson = $('input[name="supporter"]:checked').val();
						senders.addParam("ASSTN_PERSON_ENNC", assntPerson);
						
						var mlrd = $('input[name="posbl"]:checked').val();
						senders.addParam("DS_MLRD_POSBL_AT", mlrd);
						
						var assntNeed = $('input[name="supportNeed"]:checked').val();
						senders.addParam("ASSTN_NEED_AT", assntNeed);
					}else if($("#MBER_CD").val() == 20){//이동보조원
						
						senders.addParam("ASSTN_LCNSE_NO", $("#ASSTN_LCNSE_NO").val());
						
						var weekDay = "";
						 $('input:checkbox[name="addWeekChk"]').each(function(){
							 if($(this).is(":checked")){
								 weekDay += "1";
							 }else{
								 weekDay += "0";
							 }
							
						 });
						
						senders.addParam("WORK_DE", weekDay);//근무요일
						
						var timeZon = "";
						 $('input:checkbox[name="addTime"]').each(function(){
							 if($(this).is(":checked")){
								 timeZon += "1";
							 }else{
								 timeZon += "0";
							 }
							
						 });
						
						senders.addParam("WORK_TMZON", timeZon);//근무시간
						
					}else if($("#MBER_CD").val() == 30){//운전자
						
						senders.addParam("VHCLE_NO", $("#VHCLE_NO").val());
						
						var weekDay = "";
						 $('input:checkbox[name="addDrWeekChk"]').each(function(){
							 if($(this).is(":checked")){
								 weekDay += "1";
							 }else{
								 weekDay += "0";
							 }
						 });
						
						senders.addParam("WORK_DE", weekDay);
						
						var timeZon = "";
						 $('input:checkbox[name="addDrTime"]').each(function(){
							 if($(this).is(":checked")){
								 timeZon += "1";
							 }else{
								 timeZon += "0";
							 }
						 });
						
						senders.addParam("WORK_TMZON", timeZon);
						senders.addParam("RM", $("#RM").val());//비고
						
					}else if($("#MBER_CD").val() == 40){//역무원
						
						senders.addParam("STATION_NM", $("#STATION_NM").val());
						senders.addParam("STATION_LINE", $("#STATION_LINE").val());
						
						var weekDay = "";
						 $('input:checkbox[name="addStweekChk"]').each(function(){
							 if($(this).is(":checked")){
								 weekDay += "1";
							 }else{
								 weekDay += "0";
							 }
						 });
						
						senders.addParam("WORK_DE", weekDay);
						
						var timeZon = "";
						 $('input:checkbox[name="addSttime"]').each(function(){
							 if($(this).is(":checked")){
								 timeZon += "1";
							 }else{
								 timeZon += "0";
							 }
						 });
						
						senders.addParam("WORK_TMZON", timeZon);
						
					}
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/login/MemberShipAdd.json",
					});
				}
				
			}
			
			
		},
		idCheck : function(id,cd){
			var senders = new tscp.idCheck.api();
			senders.addParam("MBER_ID", id);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/login/memberIDDoubleAddChk.json",
			});
		},
		carNumberCheck : function(carNo){
			var senders = new tscp.carNumberCheck.api();
			senders.addParam("VHCLE_NO", carNo);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/carNumberCheck.json",
			});
		},
		userListExcelDown : function(){
			var senders = new tscp.userListExcelDown.api();
			if($("#MBER_SE_CD").val() != "") {
				senders.addParam("MBER_SE_CD", $("#MBER_SE_CD").val());
			}
			if($("#MBER_STTUS_CD").val() != "") {
				senders.addParam("MBER_STTUS_CD", $("#MBER_STTUS_CD").val());
			}
			if($("#MBER_NM").val() != "") {
				senders.addParam("MBER_NM", $("#MBER_NM").val());
			}
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/userExcelDown.json",
			});
		}
	};
	/*********** 회원관리 목록 API 호출 Start **********/
	(function() {
		$class("tscp.userMnglist.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				var result = res.result;
				
				var html = "";
				
				html += "<colgroup>";
				html += "<col width='20' />";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='100' />";
				html += "<col width='100' />";
				html += "<col width='60' />";
				html += "<col width='90' />";
				html += "<col width='70' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>No</th>";
				html += "<th>회원구분</th>";
				html += "<th>아이디</th>";
				html += "<th>국적</th>";
				html += "<th>이름</th>";
				html += "<th>전화번호</th>";
				html += "<th>휴대폰번호</th>";
				html += "<th>승인자ID</th>";
				html += "<th>승인일</th>";
				html += "<th>회원상태</th>";
				html += "</tr>";
				
				if(result.userMngList.length == 0) {
					html += "<tr><td colspan='9'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.userMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.userMngList[i].MBER_ID+"' onclick='checkValue()'/></td>";
						html += "<td>"+result.userMngList[i].rnum+"</td>";
						html += "<td>"+result.userMngList[i].SE_CD+"</td>";
						html += "<td>"+result.userMngList[i].MBER_ID+"</td>";
						html += "<td>"+result.userMngList[i].MBER_NT_CD+"</td>";
						html += "<td class='link' onclick=\"javascript:$userMng.userMngDetail(\'"+result.userMngList[i].MBER_ID+"\',\'"+result.userMngList[i].MBER_SE_CD+"\');\">"+result.userMngList[i].MBER_NM+"</td>";
						html += "<td>"+result.userMngList[i].TELNO+"</td>";
						html += "<td>"+result.userMngList[i].PHONE+"</td>";
						html += "<td>"+result.userMngList[i].REGISTER_ID+"</td>";
						html += "<td>"+result.userMngList[i].REGIST_DT+"</td>";
						if(result.userMngList[i].CD_VALUE_DE == "요청") {
							html += "<td><a href=\"javascript:$userMng.userMngUpdate(\'"+result.userMngList[i].MBER_ID+"\',\'"+result.userMngList[i].MBER_NM+"\');\" class='btn01'>"+result.userMngList[i].CD_VALUE_DE+"</a></td>";
						} else {
							html += "<td>"+result.userMngList[i].CD_VALUE_DE+"</td>";
						}
						html += "</tr>";
					}
				}
				
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.userMngListCount[0].CNT+" 건");
				$userMng.totalCnt = result.userMngListCount[0].CNT;
				paging($userMng,"userMnglist");
				$("#excelTable").empty();
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원관리 API 호출 End **********/
	
	/*********** 회원관리 상세 API 호출 Start **********/
	(function() {
		$class("tscp.userMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;//회원정보
				var memberCode = result.memberCodeList;//회원구분코드리스트
				var emailList = result.emailList;//이메일계정 리스트
				var trblCodeList = result.troblList;//장애종류 리스트
				var wheelchairCodeList = result.wheelchairList;//휠체어구분 리스트
				var str = result.userMngDetail.MBER_SE_CD;
				
				if(result.userMngDetail.EMAIL != null && result.userMngDetail.EMAIL != ''){
					var email = result.userMngDetail.EMAIL.split("@");//이메일
				}else{
					var email = ["",""];
				}
				if(result.userMngDetail.TEL_NO != null && result.userMngDetail.TEL_NO != ''){
					var telNo = result.userMngDetail.TEL_NO.split("-");//유선 전화
				}else{
					var telNo = ["","",""];
				}
				if(result.userMngDetail.PHONE != null && result.userMngDetail.PHONE != ''){
					var phoneNo = result.userMngDetail.PHONE.split("-");//핸드폰번호
				}else{
					var phoneNo = ["","",""];
				}
				
				if(res.result.userMngDetail.WORK_DE != null && res.result.userMngDetail.WORK_TMZON != null){
					var workWeek = res.result.userMngDetail.WORK_DE.split("");
					var workTime = res.result.userMngDetail.WORK_TMZON.split("");
				}
				
				
				//회원 구분 코드
				var cdList = "";
				for(var i = 0; i < memberCode.length; i++){
					if(memberCode[i].CD_VALUE == result.userMngDetail.MBER_SE_CD){
						cdList += "<option value='"+memberCode[i].CD_VALUE+"'  selected='selected'>"+memberCode[i].CD_VALUE_DE+"</option>";
					}else{
						cdList += "<option value='"+memberCode[i].CD_VALUE+"'>"+memberCode[i].CD_VALUE_DE+"</option>";
					}
					
				}
				//emailList
				var mailList = "";
				for(var i = 0; i < emailList.length; i++){
					if(email[1] == emailList[i].CD_VALUE_DE){
						mailList += "<option value='"+emailList[i].CD_VALUE_DE+"'  selected='selected'>"+emailList[i].CD_VALUE_DE+"</option>";
					}else{
						mailList += "<option value='"+emailList[i].CD_VALUE_DE+"'>"+emailList[i].CD_VALUE_DE+"</option>";
					}
					
				}
				
				//장애종류 리스트
				var troblList = "";
				for(var i = 0; i < trblCodeList.length; i++){
					if(result.userMngDetail.TROBL_KND_CD == trblCodeList[i].CD_VALUE){
						troblList +=  "<option value='"+trblCodeList[i].CD_VALUE+"'  selected='selected'>"+trblCodeList[i].CD_VALUE_DE+"</option>";
					}else{
						troblList +=  "<option value='"+trblCodeList[i].CD_VALUE+"'>"+trblCodeList[i].CD_VALUE_DE+"</option>";
					}
				}
				
				//휠체어 구분
				var wheelList = "";
				for(var i = 0; i < wheelchairCodeList.length; i++){
					if(wheelchairCodeList[i].CD_VALUE_DE == result.userMngDetail.WHEELCHAIR){
						wheelList +=  "<option value='"+wheelchairCodeList[i].CD_VALUE+"'  selected='selected'>"+wheelchairCodeList[i].CD_VALUE_DE+"</option>";
					}else{
						wheelList +=  "<option value='"+wheelchairCodeList[i].CD_VALUE+"'>"+wheelchairCodeList[i].CD_VALUE_DE+"</option>";
					}
				}
				
				
				//공통
				$("#val1").val(result.userMngDetail.CNTER_NM);
				$("#val2").html(cdList);
				$("#val3").val(result.userMngDetail.MBER_NM);
					if(result.userMngDetail.MBER_NT_CD == 1){
						$("#ntCode_1").prop("checked", true);
						$("#ntCode_2").prop("checked", false);
					}else{
						$("#ntCode_1").prop("checked", false);
						$("#ntCode_2").prop("checked", true);
					}
				$("#val5").val(result.userMngDetail.MBER_ID);
				
					if(result.userMngDetail.SMS_RECPTN == "Y"){
						$("#sms_1").prop("checked", true);
						$("#sms_2").prop("checked", false);
					}else{
						$("#sms_1").prop("checked", false);
						$("#sms_2").prop("checked", true);
					}
					
					if(result.userMngDetail.GENDER == "M"){
						$("#gen_1").prop("checked", true);
						$("#gen_2").prop("checked", false);
					}else{
						$("#gen_1").prop("checked", false);
						$("#gen_2").prop("checked", true);
					}
				$("#val8").val(result.userMngDetail.BIRTHDY);
				$("#val9").val(telNo[0]);
				$("#val10").val(telNo[1]);
				$("#val11").val(telNo[2]);
				$("#val12").val(phoneNo[0]);
				$("#val13").val(phoneNo[1]);
				$("#val14").val(phoneNo[2]);
				$("#val15").val(result.userMngDetail.ZIP);
				$("#val16").val(result.userMngDetail.ADDRESS);
				$("#val17").val(result.userMngDetail.ADRES_DETAIL);
				$("#val18").val(email[0]);
				$("#val19").val(email[1]);
				$("#val20").html(mailList);
				
				if(str == "10") { //이동약자
					$("#varea1").show(); //이동약자 : 부가정보
					$("#varea2").hide(); //운전자 : 부가정보
					$("#varea3").hide();//운전자
					$("#varea4").hide();//역무원
					$("#val21").html(troblList);
					$("#val22").val(result.userMngDetail.GRAD);	
					$("#val23").html(wheelList);
					$("#val24").val(result.userMngDetail.WHEELCHAIR_USE_PD);
					if(result.userMngDetail.ASSTN_PERSON_ENNC == "Y"){
						$("#asstn_1").prop("checked", true);
						$("#asstn_2").prop("checked", false);
					}else{
						$("#asstn_1").prop("checked", false);
						$("#asstn_2").prop("checked", true);
					}
					
					if(result.userMngDetail.DS_MLRD_POSBL_AT == "Y"){
						$("#mlrd_1").prop("checked", true);
						$("#mlrd_2").prop("checked", false);
					}else{
						$("#mlrd_1").prop("checked", false);
						$("#mlrd_2").prop("checked", true);
					}
					
					if(result.userMngDetail.ASSTN_NEED_AT == "Y"){
						$("#asstnNeed_1").prop("checked", true);
						$("#asstnNeed_2").prop("checked", false);
					}else{
						$("#asstnNeed_1").prop("checked", false);
						$("#asstnNeed_2").prop("checked", true);
					}
					$("#val25").html(result.userMngDetail.ATCHMNFL_REAL_NM);
				} else if(str == "20") { //이동보조원
					$("#varea1").hide(); //이동약자 : 부가정보
					$("#varea2").show(); //이동보조원 : 부가정보
					$("#varea3").hide();//운전자
					$("#varea4").hide();//역무원
					$("#val26").val(result.userMngDetail.ASSTN_LCNSE_NO);
					
					for(var i = 0; i < workWeek.length; i++){
						if(workWeek[i] == 1){
							$("#chk"+(i+1)).prop("checked",true);
							$("#chk"+(i+1)).val("1");
						
						}else{
							$("#chk"+(i+1)).prop("checked",false);
							$("#chk"+(i+1)).val("0");
						}
					}
					
					for(var i = 0; i < workTime.length; i++){
						
						if(i == 0){
							
							if(workTime[i] == 1){
								$("#timeChk_00").prop("checked",true);
								$("#timeChk_00").val("1");
							}else{
								$("#timeChk_00").prop("checked",false);
								$("#timeChk_00").val("0");
							}
							
						}
						
						if(i != 0 && i < 10){
							
							if(workTime[i] == 1){
								$("#timeChk_0"+i).prop("checked",true);
								$("#timeChk_0"+i).val("1");
							}else{
								$("#timeChk_0"+i).prop("checked",false);
								$("#timeChk_0"+i).val("0");
							}
							
						}else if(i != 0 && i >= 10){
							
							if(workTime[i] == 1){
								$("#timeChk_"+i).prop("checked",true);
								$("#timeChk_"+i).val("1");
							}else{
								$("#timeChk_"+i).prop("checked",false);
								$("#timeChk_"+i).val("0");
							}
							
						}
						
					}
					
				
				} else if(str == "30") { //운전자
					$("#varea1").hide(); //이동약자 : 부가정보
					$("#varea2").hide(); //이동보조원 : 부가정보
					$("#varea3").show();//운전자
					$("#varea4").hide();//역무원
					$("#val27").val(result.userMngDetail.VHCLE_NO);
					$("#val29").val(result.userMngDetail.RM);
					
					for(var i = 0; i < workWeek.length; i++){
						if(workWeek[i] == 1){
							$("#drchk"+(i+1)).prop("checked",true);
							$("#drchk"+(i+1)).val("1");
						
						}else{
							$("#drchk"+(i+1)).prop("checked",false);
							$("#drchk"+(i+1)).val("0");
						}
					}
					
					for(var i = 0; i < workTime.length; i++){
						
						if(i == 0){
							
							if(workTime[i] == 1){
								$("#drtimeChk_00").prop("checked",true);
								$("#drtimeChk_00").val("1");
							}else{
								$("#drtimeChk_00").prop("checked",false);
								$("#drtimeChk_00").val("0");
							}
							
						}
						
						if(i != 0 && i < 10){
							
							if(workTime[i] == 1){
								$("#drtimeChk_0"+i).prop("checked",true);
								$("#drtimeChk_0"+i).val("1");
							}else{
								$("#drtimeChk_0"+i).prop("checked",false);
								$("#drtimeChk_0"+i).val("0");
							}
							
						}else if(i != 0 && i >= 10){
							
							if(workTime[i] == 1){
								$("#drtimeChk_"+i).prop("checked",true);
								$("#drtimeChk_"+i).val("1");
							}else{
								$("#drtimeChk_"+i).prop("checked",false);
								$("#drtimeChk_"+i).val("0");
							}
							
						}
						
					}
					
				} else if(str == "40") { //역무원
					$("#varea1").hide(); //이동약자 : 부가정보
					$("#varea2").hide(); //이동보조원 : 부가정보
					$("#varea3").hide();//운전자
					$("#varea4").show();//역무원
					$("#val30").val(result.userMngDetail.STATION_NM);
					$("#val31").val(result.userMngDetail.STATION_LINE);
					
					for(var i = 0; i < workWeek.length; i++){
						if(workWeek[i] == 1){
							$("#stchk"+(i+1)).prop("checked",true);
							$("#stchk"+(i+1)).val("1");
						
						}else{
							$("#stchk"+(i+1)).prop("checked",false);
							$("#stchk"+(i+1)).val("0");
						}
					}
					
					for(var i = 0; i < workTime.length; i++){
						
						if(i == 0){
							
							if(workTime[i] == 1){
								$("#sttimeChk_00").prop("checked",true);
								$("#sttimeChk_00").val("1");
							}else{
								$("#sttimeChk_00").prop("checked",false);
								$("#sttimeChk_00").val("0");
							}
							
						}
						
						if(i != 0 && i < 10){
							
							if(workTime[i] == 1){
								$("#sttimeChk_0"+i).prop("checked",true);
								$("#sttimeChk_0"+i).val("1");
							}else{
								$("#sttimeChk_0"+i).prop("checked",false);
								$("#sttimeChk_0"+i).val("0");
							}
							
						}else if(i != 0 && i >= 10){
							
							if(workTime[i] == 1){
								$("#sttimeChk_"+i).prop("checked",true);
								$("#sttimeChk_"+i).val("1");
							}else{
								$("#sttimeChk_"+i).prop("checked",false);
								$("#sttimeChk_"+i).val("0");
							}
							
						}
						
					}
					
				} else {
					$("#varea1").hide(); //이동약자 : 부가정보
					$("#varea2").hide(); //이동보조원 : 부가정보
					$("#varea3").hide();//운전자
					$("#varea4").hide();//역무원
				}
				
				$("#userMngDetailPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원관리 상세 API 호출 End **********/
	
	/*********** 회원가입승인 API 호출 Start **********/
	(function() {
		$class("tscp.userMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("회원가입이 승인되었습니다.");
					$userMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원가입승인 API 호출 End **********/
	
	
	/*********** 회원강제탈퇴 API 호출 Start **********/
	(function() {
		$class("tscp.userMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 탈퇴 처리되었습니다.");
					$userMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원강제탈퇴 API 호출 End **********/
	
	/*********** 회원정보수정 API 호출 Start **********/
	(function() {
		$class("tscp.userInfoUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정보가 수정되었습니다.");
					$userMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원정보수정 API 호출 End **********/
	
	/*********** 회원등록 API 호출 Start **********/
	(function() {
		$class("tscp.addUser.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("등록이 완료되었습니다.");
					$(".dialog").hide();
					$userMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원정등록 API 호출 End **********/
	
	/*********** ID CHECK API 호출 Start **********/
	(function() {
		$class("tscp.idCheck.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				
				if (res.result.confirm.CNT == 0) { //성공
					$("#checkingId").text("");
					$("#checkingId").css("color","green");
					$("#checkingId").html("사용 가능한 아이디 입니다.");
					$("#idCheckVal").val("1");
				} else {
					$("#checkingId").text("");
					$("#checkingId").css("color","red");
					$("#checkingId").html("이미 등록된 아이디 입니다.");
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** ID CHECK API 호출 End **********/

	/*********** VHCLE_NO CHECK API 호출 Start **********/
	(function() {
		$class("tscp.carNumberCheck.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				
				if (res.result.carNumberCheck.CAR_NO == 0) { //성공
					$("#checkingCarNum").text("");
					$("#checkingCarNum").css("color","green");
					$("#checkingCarNum").html("등록 가능한 차량 입니다.");
					$("#carCheckVal").val("1");
				} else {
					$("#checkingCarNum").text("");
					$("#checkingCarNum").css("color","red");
					$("#checkingCarNum").html("이미 등록된 차량 입니다.");
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** VHCLE_NO CHECK API 호출 End **********/
	
	/*********** 회원목록 Excel Down API 호출 Start **********/
	(function() {
		$class("tscp.userListExcelDown.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#excelTable").empty(); //리스트 삭제
				var result = res.result;
				
				var html = "";
				
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='100' />";
				html += "<col width='100' />";
				html += "<col width='60' />";
				html += "<col width='90' />";
				html += "<col width='70' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>회원구분</th>";
				html += "<th>아이디</th>";
				html += "<th>국적</th>";
				html += "<th>이름</th>";
				html += "<th>전화번호</th>";
				html += "<th>휴대폰번호</th>";
				html += "<th>승인자ID</th>";
				html += "<th>승인일</th>";
				html += "<th>회원상태</th>";
				html += "</tr>";
				
				if(result.userMngList.length == 0) {
					html += "<tr><td colspan='9'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.userMngList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.userMngList[i].rnum+"</td>";
						html += "<td>"+result.userMngList[i].SE_CD+"</td>";
						html += "<td>"+result.userMngList[i].MBER_ID+"</td>";
						html += "<td>"+result.userMngList[i].MBER_NT_CD+"</td>";
						html += "<td>"+result.userMngList[i].MBER_NM+"</td>";
						html += "<td>"+result.userMngList[i].TELNO+"</td>";
						html += "<td>"+result.userMngList[i].PHONE+"</td>";
						html += "<td>"+result.userMngList[i].REGISTER_ID+"</td>";
						html += "<td>"+result.userMngList[i].REGIST_DT+"</td>";
						html += "<td>"+result.userMngList[i].CD_VALUE_DE+"</td>";
						html += "</tr>";
					}
				}
				
				
					$("#excelTable").html(html);
				
					 $("#excelTable").table2excel({
					      exclude: ".noExl",
					      name: "Excel Document Name",
					      filename: "이용자 목록_" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
					      fileext: ".xls",
					      exclude_img: true,
					      exclude_links: true,
					      exclude_inputs: true
					   });
				 
				$userMng.totalCnt = result.userMngListCount[0].CNT;
				$userMng.list(1);
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원목록 Excel Down API 호출 End **********/
	
	/*********** 회원등록 FORM API 호출 Start **********/
	(function() {
		$class("tscp.addUserForm.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var centrList = result.centerList; //센터리스트
				var memberCode = result.memberCodeList;//회원구분코드리스트
				var emailList = result.emailList;//이메일계정 리스트
				var trblCodeList = result.troblList;//장애종류 리스트
				var wheelchairCodeList = result.wheelchairList;//휠체어구분 리스트
				var areaTelNo = result.areaTelNo; //지역번호 리스트
				var phoneNo = result.phoneNoList; //휴대폰 앞번호 리스트
				
				//센터 구분
				if(session.user_gbn_cd == 90){
					var cnList = "";
					cnList += "<option value=''>선택</option>";
					for(var i = 0; i < centrList.length; i++){
						cnList += "<option value='"+centrList[i].CNTER_CD+"'>"+centrList[i].CNTER_NM+"</option>";
					}
				}else{
					for(var i = 0; i < centrList.length; i++){
						if(session.cnter_cd == centrList[i].CNTER_CD){
							cnList += "<option value='"+centrList[i].CNTER_CD+"'>"+centrList[i].CNTER_NM+"</option>";
							$("#CNTER_CD").not(":selected").attr("disabled","disabled");
						}
						
					}
				}
				
				
				//회원 구분 코드
				var cdList = "";
				cdList += "<option value=''>선택</option>";
				for(var i = 0; i < memberCode.length; i++){
						cdList += "<option value='"+memberCode[i].CD_VALUE+"'>"+memberCode[i].CD_VALUE_DE+"</option>";
				}
				//emailList
				var mailList = "";
				for(var i = 0; i < emailList.length; i++){
						mailList += "<option value='"+emailList[i].CD_VALUE_DE+"'>"+emailList[i].CD_VALUE_DE+"</option>";
				}
				
				//장애종류 리스트
				var troblList = "";
				troblList += "<option value=''>선택</option>";
				for(var i = 0; i < trblCodeList.length; i++){
						troblList +=  "<option value='"+trblCodeList[i].CD_VALUE+"'>"+trblCodeList[i].CD_VALUE_DE+"</option>";
				}
				
				//휠체어 구분
				var wheelList = "";
				wheelList += "<option value=''>선택</option>";
				for(var i = 0; i < wheelchairCodeList.length; i++){
						wheelList +=  "<option value='"+wheelchairCodeList[i].CD_VALUE+"'>"+wheelchairCodeList[i].CD_VALUE_DE+"</option>";
				}
				
				//지역번호
				var area = "";
				area += "<option value=''>선택</option>";
				for(var i = 0; i < areaTelNo.length; i++){
					area +=  "<option value='"+areaTelNo[i].CD_VALUE+"'>"+areaTelNo[i].CD_VALUE_DE+"</option>";
				}
				
				//휴대폰 앞자리
				var phone = "";
				phone += "<option value=''>선택</option>";
				for(var i = 0; i < phoneNo.length; i++){
					phone +=  "<option value='"+phoneNo[i].CD_VALUE+"'>"+phoneNo[i].CD_VALUE_DE+"</option>";
				}
				
				$("#CNTER_CD").html(cnList);
				$("#MBER_CD").html(cdList);
				$("#tel1").html(area);
				$("#phone1").html(phone);
				$("#domainList").html(mailList);
				$("#TROBL_KND_CD").html(troblList);
				$("#WHEELCHAIR_SE_CD").html(wheelList);
				
				$("#addUserForm")[0].reset();
				$("#checkingId").empty();
				$("#checkPattern").empty();
				$("#check").empty();
				$("#checkingCarNum").empty();
				$("#birthPattern").empty();
				$("#userAddFrom").show();
				$("#tab1").hide(); //이동약자 : 부가정보
				$("#tab2").hide(); //이동보조원 : 부가정보
				$("#tab3").hide();//운전자
				$("#tab4").hide();//역무원
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 회원등록 FORM API 호출 End **********/
	
}(window, document));