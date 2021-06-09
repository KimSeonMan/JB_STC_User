<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type="text/javascript" src="/js/tscp/bassInfo/userMng.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script charset="UTF-8" type="text/javascript" src="//t1.daumcdn.net/cssjs/postcode/1513129253770/171213.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.table2excel.js"></script>
</head>
<body>
<script type="text/javascript">
function openDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
        	
        	 var fullAddr = data.address; 
             var extraAddr = ''; 

             
             if(data.addressType === 'R'){
                
                 if(data.bname !== ''){
                     extraAddr += data.bname;
                 }
                 
                 if(data.buildingName !== ''){
                     extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                 }
                 
                 fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
             }
            $("#val15").val(data.zonecode);
            $("#val16").val(fullAddr);
            $("#val17").focus();
            $("#ZIP").val(data.zonecode);
            $("#ADRES").val(fullAddr);
            $("#ADRES_DETAIL").focus();
        }
    }).open();
}

function changeMail(str){
	$("#val19").val(str);
	$("#mail2").val(str);
}

function changeSeCode(code){
	switch (code) {
		case "10" :
			$("#tab1").show();
			$("#tab2").hide(); 
			$("#tab3").hide();
			$("#tab4").hide();
			break;
		case "20" :
			$("#tab1").hide(); 
			$("#tab2").show();
			$("#tab3").hide();
			$("#tab4").hide();
			break;
		case "30" :
			$("#tab1").hide(); 
			$("#tab2").hide();
			$("#tab3").show();
			$("#tab4").hide();
			break;
		case "40" :
			$("#tab1").hide(); 
			$("#tab2").hide();
			$("#tab3").hide();
			$("#tab4").show();
			break;
		default :
			$("#tab1").hide(); 
			$("#tab2").hide();
			$("#tab3").hide();
			$("#tab4").hide();
	}
}

$(function(){
	$("#PASSWORD").focusout(function(){
		$("#checkPattern").text("");
		var pattern1 = /[0-9]/; // 숫자 
		var pattern2 = /[a-zA-Z]/; // 문자 
		var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
		
		if(!pattern1.test(this.value) || !pattern2.test(this.value) || !pattern3.test(this.value) || this.value.length < 8){
			$("#checkPattern").css("color","red");
			$("#checkPattern").html("비밀번호는 8자리 이상 영문,숫자,특수문자로 구성해야합니다.").fadeIn('slow');
		}else{
			$("#checkPattern").css("color","green");
			$("#checkPattern").html("비밀번호 패턴이 양호 합니다.").fadeIn('slow');
		}

	});
	
	$("#checkPassWord").focusout(function(){
		if($("#PASSWORD").val() != $("#checkPassWord").val()){
			$("#check").text("");
			$("#check").css("color","red");
			$("#check").html("암호가 다릅니다.").fadeIn('slow');
		}else{
			$("#check").text("");
			$("#check").css("color","green");
			$("#check").html("암호 확인 정상").fadeIn('slow');
		}
	});
	
});

$(function(){
	$("#BRTHDY").focusout(function(){
		$("#birthPattern").text("");
		var datePattern =  /^(19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$/;
		if(!datePattern.test(this.value)){
			$("#birthPattern").css("color","red");
			$("#birthPattern").html("생년월일은 숫자만 입력하세요.").fadeIn('slow');
		}else{
			$("#birthPattern").css("color","green");
			$("#birthPattern").html("생년월일 입력 정상!").fadeIn('slow');
		}
	});
});

$(function(){
	$("#checkId").click(function(){
		$userMng.idCheck($("#MBER_ID").val());
	});
	
	$("#checkCarNumber").click(function(){
		$userMng.carNumberCheck($("#VHCLE_NO").val());
	});
	
});

$(function(){
	$("#userExcelDown").click(function(){
		$userMng.userListExcelDown();
	});
});


$(function(){
	$("#mberDelete").click(function(){
		$userMng.userMngDelete($("#val5").val());
		$('.dialog').hide();
	});

	$("#modifyUser").click(function(){
		$userMng.userInfoUpdate();
		$('.dialog').hide();
	});
})


</script>

	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container">
			<div class="lnb"></div><!-- lnb -->

			<div class="subContent"><!-- subContent -->
				<ul class="location"></ul>
				<h3 class="subTitle"></h3>
				<table class="sTable01">
					<tr>
						<th>구분</th>
						<td>
							<select id="MBER_SE_CD" class="select">
								<option value="">선택</option>
							</select>
						</td>
						<th>상태</th>
						<td>
							<select id="MBER_STTUS_CD" class="select">
								<option value="">선택</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" id="MBER_NM" class="inp"/>
						</td>
						<td colspan="2" class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">이용자 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" onclick="$userMng.addUserForm()" id="userAdd" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" onclick="$userMng.userMngDelete(chkVal)" id="userMngDelete" class="btn02 CRUDBtn">탈퇴</a>
						<a href="javascript:void(0)" onclick="" id="userExcelDown" class="btn02 CRUDBtn">Excel저장</a>
					</div>
				</div>
				<form id="listTable">
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
		
		<!-- footer// -->
		<jsp:include page="/view/common/includeFooter"></jsp:include>
	</div>
	<script type="text/javascript">
	function checkValue(){
		$(":checkbox[name='chk']:checked").each(function(){
			var chkVal = $(this).val();
		});
	}
	</script>
	
	<div class="dialog" id="userMngDetailPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">이용자정보 상세보기</p>
			
			<div class="pCont">
			
			<div class="sthBox">
				<span class="t01">기본정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="120" />
					<col width="" />
				</colgroup>
				<tr>
					<th>센터구분<span>*</span></th>
					<td><input type="text" class="inp" id="val1" readonly="readonly"/></td>
					<th>회원구분<span>*</span></th>
					<td>
						<select id="val2">
							
						</select>
					</td>
				</tr>
				<tr>
					<th>이름<span>*</span></th>
					<td><input type="text" class="inp" id="val3"/></td>
					<th>국적<span>*</span></th>
					<td>
						<input type="radio" id="ntCode_1" value="1" name="ntCodeVal">내국인
						<input type="radio" id="ntCode_2" value="2" name="ntCodeVal">외국인
					 </td>
				</tr>
				<tr>
					<th>아이디<span>*</span></th>
					<td><input type="text" id="val5" readonly="readonly" class="inp"></td>
					<th>SMS수신여부<span>*</span></th>
					<td>
						<input type="radio" id="sms_1" value="Y" name="smsChk">예
						<input type="radio" id="sms_2" value="N" name="smsChk">아니오
					</td>
				</tr>
				<tr>
					<th>성별<span>*</span></th>
					<td>
						<input type="radio" id="gen_1" value="M" name="gender">남자
						<input type="radio" id="gen_2" value="F" name="gender">여자
					</td>
					<th>생년월일<span>*</span></th>
					<td><input type="text" id="val8"></td>
				</tr>
				<tr>
					<th>연락처<span>*</span></th>
					<td>
						<input type="text" class="inp t01"  id="val9"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="val10"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="val11"/>
						
					</td>
					<th>휴대폰<span>*</span></th>
					<td>
						<input type="text" class="inp t01"  id="val12"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="val13"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="val14"/>
					</td>
				</tr>
				<tr>
					<th>우편번호<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03"  id="val15" readonly="readonly"/>
						<a href="javascript:void(0)" onclick="openDaumPostcode()" class="btn02" style="float: none; overflow: visible;">우편번호 검색</a>
					</td> 
				</tr>
				<tr>
					<th>주소<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t02"  id="val16" readonly="readonly"/>
						<input type="text" class="inp t04"  id="val17"/>
					</td> 
				</tr>
				<tr>
					<th>이메일<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03"  id="val18"/>&nbsp;
						@&nbsp;
						<input type="text" class="inp t03"  id="val19"/>
						<select class="select t03"  id="val20" style="float: none; display: inline;" onchange="changeMail(this.value)">
							
						</select>
					</td> 
				</tr>
			</table>
			<div id="varea1">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>장애종류</th>
					<td>
						<select id="val21">
							
						</select>
					</td>
					<th>장애등급<span>*</span></th>
					<td><input type="text" class="inp" id="val22"/></td>
				</tr>
				<tr>
					<th>휠체어구분<span>*</span></th>
					<td>
						<select id="val23">
							
						</select>
					</td>
					<th>휠체어이용기간<span>*</span></th>
					<td><input type="text" class="inp"  id="val24"/></td>
				</tr>
				<tr>
					<th>보조인유무</th>
					<td>
						<input type="radio"  id="asstn_1" name="asstn" value="Y"/>Y
						<input type="radio"  id="asstn_2" name="asstn" value="N"/>N
					</td>
					<th>의사소통가능여부</th>
					<td>
						<input type="radio"  id="mlrd_1" name="mlrd" value="Y"/>Y
						<input type="radio"  id="mlrd_2" name="mlrd" value="N"/>N
					</td>
				</tr>
				<tr>
					<th>도움필여여부</th>
					<td>
						<input type="radio"  id="asstnNeed_1" name="asstnNeed" value="Y"/>Y
						<input type="radio"  id="asstnNeed_2" name="asstnNeed" value="N"/>N
					</td>
					<th>첨파부일</th>
					<td><a href="javascript:void(0)" class="link"  id="val25">장애인증명서.jpg</a></td>
				</tr> 
			</table>
			</div>
		</div>
		
		<!-- 이동 보조원 -->
			
			<div id="varea2">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>자격증 번호<span>*</span></th>
					<td colspan="3"><input type="text" id="val26" class="inp"/></td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="weekChk" id="chk1" class="ml20"/>
						<label for="">일요일</label>
						<input type="checkbox" name="weekChk" id="chk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="weekChk" id="chk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="weekChk" id="chk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="weekChk" id="chk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="weekChk" id="chk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="weekChk" id="chk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="time" id="timeChk_00"class="ml20" />
								<label for="">00</label>
								<input type="checkbox" name="time" id="timeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="time" id="timeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="time" id="timeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="time" id="timeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="time" id="timeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="time" id="timeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="time" id="timeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="time" id="timeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="time" id="timeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="time" id="timeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="time" id="timeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="time" id="timeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="time" id="timeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="time" id="timeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="time" id="timeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="time" id="timeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="time" id="timeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="time" id="timeChk_18"class="ml20" />
								<label for="">18</label>
								<input type="checkbox" name="time" id="timeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="time" id="timeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="time" id="timeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="time" id="timeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="time" id="timeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
			</table>
			</div>
			
			<div id="varea3"><!-- 운전자 -->
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>차량번호</th>
					<td><input type="text" id="val27" class="inp"/></td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="drWeekChk" id="drchk1" class="ml20" />
						<label for="">일요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="drWeekChk" id="drchk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="drTime" id="drtimeChk_00" class="ml20"/>
								<label for="">00</label>
								<input type="checkbox" name="drTime" id="drtimeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="drTime" id="drtimeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="drTime" id="drtimeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="drTime" id="drtimeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="drTime" id="drtimeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="drTime" id="drtimeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="drTime" id="drtimeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="drTime" id="drtimeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="drTime" id="drtimeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="drTime" id="drtimeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="drTime" id="drtimeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="drTime" id="drtimeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="drTime" id="drtimeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="drTime" id="drtimeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="drTime" id="drtimeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="drTime" id="drtimeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="drTime" id="drtimeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="drTime" id="drtimeChk_18" class="ml20"/>
								<label for="">18</label>
								<input type="checkbox" name="drTime" id="drtimeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="drTime" id="drtimeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="drTime" id="drtimeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="drTime" id="drtimeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="drTime" id="drtimeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td  colspan="3"><input type="text"  class="inp" id="val29"/></td>
				</tr> 
			</table>
			</div>
			
			
			<!-- 역무원 -->
			
			<div id="varea4">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>역명<span>*</span></th>
					<td><input type="text" id="val30" class="inp"/></td>
					<th>호선<span>*</span></th>
					<td><input type="text" id="val31" class="inp"/></td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="stweekChk" id="stchk1" class="ml20"/>
						<label for="">일요일</label>
						<input type="checkbox" name="stweekChk" id="stchk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="stweekChk" id="stchk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="stweekChk" id="stchk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="stweekChk" id="stchk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="stweekChk" id="stchk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="stweekChk" id="stchk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="sttime" id="sttimeChk_00" class="ml20"/>
								<label for="">00</label>
								<input type="checkbox" name="sttime" id="sttimeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="sttime" id="sttimeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="sttime" id="sttimeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="sttime" id="sttimeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="sttime" id="sttimeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="sttime" id="sttimeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="sttime" id="sttimeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="sttime" id="sttimeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="sttime" id="sttimeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="sttime" id="sttimeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="sttime" id="sttimeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="sttime" id="sttimeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="sttime" id="sttimeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="sttime" id="sttimeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="sttime" id="sttimeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="sttime" id="sttimeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="sttime" id="sttimeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="sttime" id="sttimeChk_18" class="ml20"/>
								<label for="">18</label>
								<input type="checkbox" name="sttime" id="sttimeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="sttime" id="sttimeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="sttime" id="sttimeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="sttime" id="sttimeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="sttime" id="sttimeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
			</table>
			</div>
			
			<br>
			<div class="btnBox" align="center">
				<a href="javascript:void(0)" class="btn01 CRUDBtn" id="modifyUser">회원정보수정</a>
				<a href="javascript:void(0)" onclick="" class="btn01 CRUDBtn" id="mberDelete">탈퇴</a>
				<a href="javascript:$('.dialog').hide();" class="btn01 CRUDBtn">취소</a>
			</div>
		</div>
	</div>
	
	
	
	<!-- 회원 등록 템플릿 -->
	
	<div class="dialog" id="userAddFrom" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">이용자 등록</p>
			
			<div class="pCont">
			
			<div class="sthBox">
				<span class="t01">기본정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<form id="addUserForm">
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="120" />
					<col width="" />
				</colgroup>
				<tr>
					<th>센터구분<span>*</span></th>
					<td>
						<select id="CNTER_CD"></select>
					</td>
					<th>회원구분<span>*</span></th>
					<td>
						<select id="MBER_CD" onchange="changeSeCode(this.value)"></select>
					</td>
				</tr>
				<tr>
					<th>이름<span>*</span></th>
					<td><input type="text" class="inp" id="MBER_NAME"/></td>
					<th>국적<span>*</span></th>
					<td>
						<input type="radio" id="ntCode_1" value="1" name="ntCode" checked="checked">내국인
						<input type="radio" id="ntCode_2" value="2" name="ntCode">외국인
					 </td>
				</tr>
				<tr>
					<th>아이디<span>*</span></th>
					<td>
					<input type="text" id="MBER_ID" class="inp t03">
					<a href="javascript:void(0)" class="btn02" style="float: none; overflow: visible;" id="checkId">아이디 중복검사</a><br>
					<span id="checkingId"></span>
					<input type="hidden" id="idCheckVal" value="0">
					</td>
					<th>SMS수신여부<span>*</span></th>
					<td>
						<input type="radio" id="sms_1" value="Y" name="smsYN" checked="checked">예
						<input type="radio" id="sms_2" value="N" name="smsYN">아니오
					</td>
				</tr>
				<tr>
					<th>비밀번호<span>*</span></th>
					<td>
					<input type="password" id="PASSWORD" name="PASSWORD" class="inp">
					<span id="checkPattern"></span>
					</td>
					
					<th>비밀번호 확인<span>*</span></th>
					<td>
					<input type="password" id="checkPassWord" name="checkPassWord" class="inp">
					<span id="check"></span>
					</td>
				</tr>
				<tr>
					<th>성별<span>*</span></th>
					<td>
						<input type="radio" id="gen_1" value="M" name="gen" checked="checked">남자
						<input type="radio" id="gen_2" value="F" name="gen">여자
					</td>
					<th>생년월일<span>*</span></th>
					<td>
					<input type="text" id="BRTHDY" placeholder="예) 19990101">
					<span id="birthPattern"></span>
					</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>
						<select id="tel1" class="inp t03"></select>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="tel2"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="tel3"/>
						
					</td>
					<th>휴대폰<span>*</span></th>
					<td>
						<select id="phone1" class="inp t03"></select>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="phone2"/>&nbsp; -&nbsp;
						<input type="text" class="inp t01"  id="phone3"/>
					</td>
				</tr>
				<tr>
					<th>우편번호<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03"  id="ZIP" readonly="readonly"/>
						<a href="javascript:void(0)" onclick="openDaumPostcode()" class="btn02" style="float: none; overflow: visible;">우편번호 검색</a>
					</td> 
				</tr>
				<tr>
					<th>주소<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t02"  id="ADRES" readonly="readonly"/>
						<input type="text" class="inp t04"  id="ADRES_DETAIL"/>
					</td> 
				</tr>
				<tr>
					<th>이메일<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t03"  id="mail1"/>&nbsp;
						@&nbsp;
						<input type="text" class="inp t03"  id="mail2"/>
						<select class="select t03"  id="domainList" style="float: none; display: inline;" onchange="changeMail(this.value)"></select>
					</td> 
				</tr>
			</table>
			<div id="tab1">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>장애종류</th>
					<td>
						<select id="TROBL_KND_CD"></select>
					</td>
					<th>장애등급<span>*</span></th>
					<td><input type="text" class="inp" id="TROBL_GRAD"/></td>
				</tr>
				<tr>
					<th>휠체어구분<span>*</span></th>
					<td>
						<select id="WHEELCHAIR_SE_CD"></select>
					</td>
					<th>휠체어이용기간<span>*</span></th>
					<td><input type="text" class="inp"  id="WHEELCHAIR_USE_PD"/></td>
				</tr>
				<tr>
					<th>보조인유무</th>
					<td>
						<input type="radio"  id="asstn_1" name="supporter" value="Y" checked="checked"/>Y
						<input type="radio"  id="asstn_2" name="supporter" value="N"/>N
					</td>
					<th>의사소통가능여부</th>
					<td>
						<input type="radio"  id="mlrd_1" name="posbl" value="Y" checked="checked"/>Y
						<input type="radio"  id="mlrd_2" name="posbl" value="N"/>N
					</td>
				</tr>
				<tr>
					<th>도움필여여부</th>
					<td>
						<input type="radio"  id="asstnNeed_1" name="supportNeed" value="Y" checked="checked"/>Y
						<input type="radio"  id="asstnNeed_2" name="supportNeed" value="N"/>N
					</td>
				</tr> 
			</table>
			</div>
		</div>
		
		<!-- 이동 보조원 -->
			
			<div id="tab2">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>자격증 번호<span>*</span></th>
					<td colspan="3"><input type="text" id="ASSTN_LCNSE_NO" class="inp"/></td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="addWeekChk" id="chk1" class="ml20"/>
						<label for="">일요일</label>
						<input type="checkbox" name="addWeekChk" id="chk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="addWeekChk" id="chk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="addWeekChk" id="chk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="addWeekChk" id="chk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="addWeekChk" id="chk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="addWeekChk" id="chk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="addTime" id="timeChk_00"class="ml20" />
								<label for="">00</label>
								<input type="checkbox" name="addTime" id="timeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="addTime" id="timeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="addTime" id="timeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="addTime" id="timeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="addTime" id="timeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="addTime" id="timeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="addTime" id="timeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="addTime" id="timeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="addTime" id="timeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="addTime" id="timeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="addTime" id="timeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="addTime" id="timeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="addTime" id="timeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="addTime" id="timeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="addTime" id="timeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="addTime" id="timeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="addTime" id="timeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="addTime" id="timeChk_18"class="ml20" />
								<label for="">18</label>
								<input type="checkbox" name="addTime" id="timeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="addTime" id="timeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="addTime" id="timeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="addTime" id="timeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="addTime" id="timeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
			</table>
			</div>
			
			<div id="tab3"><!-- 운전자 -->
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>차량번호</th>
					<td>
					<input type="text" id="VHCLE_NO" class="inp t03"/>
					<a href="javascript:void(0)" class="btn02" style="float: none; overflow: visible;" id="checkCarNumber">차량번호 중복검사</a><br>
					<span id="checkingCarNum"></span>
					<input type="hidden" id="carCheckVal" value="0">
					</td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="addDrWeekChk" id="drchk1" class="ml20" />
						<label for="">일요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="addDrWeekChk" id="drchk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="addDrTime" id="drtimeChk_00" class="ml20"/>
								<label for="">00</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="addDrTime" id="drtimeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="addDrTime" id="drtimeChk_18" class="ml20"/>
								<label for="">18</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="addDrTime" id="drtimeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td  colspan="3"><input type="text"  class="inp" id="RM"/></td>
				</tr> 
			</table>
			</div>
			
			
			<!-- 역무원 -->
			
			<div id="tab4">
			<div class="sthBox mt30">
				<span class="t01">부가정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="120" />
					<col width="" />
					<col width="140" />
					<col width="" />
				</colgroup>
				<tr>
					<th>역명<span>*</span></th>
					<td><input type="text" id="STATION_NM" class="inp"/></td>
					<th>호선<span>*</span></th>
					<td><input type="text" id="STATION_LINE" class="inp"/></td>
				</tr>
				<tr>
					<th>근무요일<span>*</span></th>
					<td colspan="3">
						<input type="checkbox" name="addStweekChk" id="stchk1" class="ml20"/>
						<label for="">일요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk2" class="ml20" />
						<label for="">월요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk3" class="ml20" />
						<label for="">화요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk4" class="ml20" />
						<label for="">수요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk5" class="ml20" />
						<label for="">목요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk6" class="ml20" />
						<label for="">금요일</label>
						<input type="checkbox" name="addStweekChk" id="stchk7" class="ml20" />
						<label for="">토요일</label>			
					</td>
				</tr>
				<tr>
					<th>근무시간대<span>*</span></th>
					<td colspan="3">
							<ul class="ckList">
							<li>
								<input type="checkbox" name="addSttime" id="sttimeChk_00" class="ml20"/>
								<label for="">00</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_01" class="ml20" />
								<label for="">01</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_02" class="ml20" />
								<label for="">02</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_03" class="ml20" />
								<label for="">03</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_04" class="ml20" />
								<label for="">04</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_05" class="ml20" />
								<label for="">05</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_06" class="ml20" />
								<label for="">06</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_07" class="ml20" />
								<label for="">07</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_08" class="ml20" />
								<label for="">08</label>
							</li>
							<li>
								<input type="checkbox" name="addSttime" id="sttimeChk_09" class="ml20"/>
								<label for="">09</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_10" class="ml20" />
								<label for="">10</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_11" class="ml20" />
								<label for="">11</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_12" class="ml20" />
								<label for="">12</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_13" class="ml20" />
								<label for="">13</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_14" class="ml20" />
								<label for="">14</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_15" class="ml20" />
								<label for="">15</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_16" class="ml20" />
								<label for="">16</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_17" class="ml20" />
								<label for="">17</label>
							</li>
							<li>
								<input type="checkbox" name="addSttime" id="sttimeChk_18" class="ml20"/>
								<label for="">18</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_19" class="ml20" />
								<label for="">19</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_20" class="ml20" />
								<label for="">20</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_21" class="ml20" />
								<label for="">21</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_22" class="ml20" />
								<label for="">22</label>
								<input type="checkbox" name="addSttime" id="sttimeChk_23" class="ml20" />
								<label for="">23</label> 
							</li>
						</ul>
					</td>
				</tr>
			</table>
			</form>
			</div>
			
			<br>
			<div class="btnBox" align="center">
				<a href="javascript:void(0)" onclick="$userMng.addUser()" class="btn01 CRUDBtn" id="mberInsert">등록</a>
				<a href="javascript:$('.dialog').hide();" class="btn01 CRUDBtn">취소</a>
			</div>
		</div>
	</div>
	
	<div id="excelArea" style="display: block;">
			<table id="excelTable"></table>
	</div>
</body>
</html>