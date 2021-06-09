<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String sessionId = (String) session.getAttribute("sessionId");
	String user_id = (String) session.getAttribute("user_id");
	
	HttpSession http_session = request.getSession();		
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/js/tscp/login/memberShip.js"></script>
</head>
<body>
<div class="wrapper">
	<!-- header // -->
	<jsp:include page="/view/common/includeHeader"></jsp:include>

	<!-- body -->
	<div class="container">
		<div class="lnb"></div><!-- lnb -->
		<div class="location">
			<ul>
				<li class="home">홈</li>
				<li>
					<a href="javascript:void(0)">Account</a> 
					<ul>
						<%if(user_id == null){%>
						<li><a href="/view/memberShipAgree.do">회원가입</a> </li>
						<li><a href="javascript:loginProcess();">로그인</a> </li>
						<li><a href="/view/login/find.do">아이디/비밀번호찾기</a> </li>
						<%}else{ %>
						<li><a href="/view/myPage.do">마이페이지</a> </li>
						<%}%>
					</ul>
				</li>
				<li class="last">
					<a href="javascript:void(0)">회원가입</a>  
				</li>
			</ul>
		</div>
		
		<div class="contents">
			<div class="subTitle">
				<h2>회원가입</h2>
			</div>

			<div class="sthBox">
				<span class="t01">기본정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>

			<form id="memberForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="TELNO" id="TELNO"> 
			<input type="hidden" name="MBTLNUM" id="MBTLNUM">
			<input type="hidden" name="EMAIL" id="EMAIL"> 
			<input type="hidden" name="CNTER_CD" id="CNTER_CD"> 
			<input type="hidden" id="ID_CHECK_YN" value="N"/>
			<table class="sTable01 ptype">
				<colgroup>
					<col width="150" />
					<col width="" />
					<col width="170" />
					<col width="" />
				</colgroup>
				<tr>
					<th>이름<span>*</span></th>
					<td><input type="text" class="inp" name="MBER_NM" id="NAME" maxlength="15"/></td>
					<th>국적<span>*</span></th>
					<td>
						<input type="radio"  name="MBER_NT_CD" value='1' checked="checked" />
						<label for="" >내국인</label>
						<input type="radio"  name="MBER_NT_CD" value='2' class="ml10" />
						<label for="">외국인</label>
					</td>
				</tr>
				<tr>
					<th>아이디<span>*</span></th>
					<td>
						<input type="text" class="inp p01" name="MBER_ID" id="USER_ID" maxlength="10" placeholder="10자리 이하"/>
						<a href="javascript:void(0)" class="btn02" id="idCheck">중복조회</a>
					</td>

					<th>성별<span>*</span></th>
					<td>
						<input type="radio"  name="SEXDSTN" value='M' checked="checked" />
						<label for="">남자</label>
						<input type="radio"  name="SEXDSTN" value='F' class="ml10" />
						<label for="">여자</label>
					</td>
				</tr>
				<tr>
					<th>비밀번호<span>*</span></th>
					<td><input type="password" class="inp" name="PASSWORD" id="PASSWORD" maxlength="64" placeholder="영문/숫자/특수문자 중 2종류 이상 조합(8자리이상)"/></td>
					<th>비밀번호 확인<span>*</span></th>
					<td><input type="password" class="inp" id="PW_CONFIRM" maxlength="64" placeholder="위 입력한 비밀번호와 동일하게 입력하세요!"/></td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>
						<select class="select t01" id="PHONE1"><option>02</option></select>
						<span class="etxt01">-</span>
						<input type="text" class="inp t06" id="PHONE2" maxlength="4" oninput="javascript:chkNum(this);"/>
						<span class="etxt01">-</span>
						<input type="text" class="inp t06" id="PHONE3" maxlength="4" oninput="javascript:chkNum(this);"/>
					</td> 
					<th>휴대폰<span>*</span></th>
					<td>
						<select class="select t01" id="MOBILE1"></select>
						<span class="etxt01">-</span>
						<input type="text" class="inp t06" id="MOBILE2" maxlength="4" oninput="javascript:chkNum(this);"/>
						<span class="etxt01">-</span>
						<input type="text" class="inp t06" id="MOBILE3" maxlength="4" oninput="javascript:chkNum(this);"/>
					</td> 
				</tr>
				<tr>
					<th>생년월일<span>*</span></th>
					<td>
						<select  class="select t02" id="BRTHDY_Y" ></select>
						<span class="etxt01">년</span>
						<select  class="select t02" id="BRTHDY_M" ></select>
						<span class="etxt01">월</span>
						<select  class="select t02" id="BRTHDY_D" ></select>
						<span class="etxt01">일</span>
						<input type="hidden" name="BRTHDY" id="BIRTH_DATE">
					</td>
					<th>SMS수신여부<span>*</span></th>
					<td>
						<select class="select" name="SMS_RECPTN_AT" id="SMS_RECEIVE_YN">
							<option value="Y" selected>예 (예약 신청 완료 등 문자 정보 수신) </option>
							<option value="N" >아니오 (문자 정보 수신 불가)</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>우편번호<span>*</span></th>
					<td colspan="3">
						<input type="text" class="inp t02" name="ZIP" id="ZIP" maxlength="6" placeholder="우편번호 검색하세요." readonly="readonly"/>
						<a href="javascript:void(0)" class="btn02" id="zipSearch">우편번호 검색</a>
					</td> 
				</tr>
				<tr>
					<th rowspan="2">주소<span>*</span></th>
					<td colspan="3"><input type="text" class="inp" name="ADRES" id="ADDRESS" maxlength="30" placeholder="우편번호 검색 시 자동입력됩니다." readonly="readonly"/></td>
				</tr>
				<tr>
					<td colspan="3"><input type="text" class="inp" name="ADRES_DETAIL" id="ADDRESSDTL" maxlength="20" placeholder="상세 주소를 입력해주세요." /></td> 
				</tr>
				<tr>
					<th>이메일<span>*</span></th>
					<td colspan="3">
						
						<input type="text" class="inp t02" id="EMAIL1" maxlength="30"/>
						<span class="etxt01">@</span>
						<input type="text" class="inp t02" id="EMAIL2" maxlength="20"/>
						<select class="select t03" id="EMAIL3" onchange="changeEmail(this.value);">></select>
					</td> 
				</tr> 
			</table>


			<div class="sthBox mt30">
				<span class="t01">필수입력정보</span>
			</div>

			<table class="sTable01 ptype">
				<colgroup>
					<col width="150" />
					<col width="" />
					<col width="170" />
					<col width="" />
				</colgroup> 
				<tr>
					<th>장애종류<span>*</span></th>
					<td><select class="select" name="TROBL_KND_CD" id="DISABLE_TYPE1_CD"></select></td>
					<th>장애등급<span>*</span></th>
					<td><select class="select" name="TROBL_GRAD" id="DISABLE_TYPE2_CD">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
					</select></td>
				</tr>
				<tr>
					<th>휠체어구분<span>*</span></th>
					<td><select class="select" name="WHEELCHAIR_SE_CD" id="WHEELCHAIR_CD" ></select></td>
					<th>휠체어이용기간</br>(년단위 입력 ex:10)</th>
					<td><input type="text" name="WHEELCHAIR_USE_PD" id="WHEELCHAIR_USE_YEAR" class="inp" maxlength="2" oninput="javascript:chkYearNum(this);" placeholder="년단위로 숫자만 기입(ex:10)"/></td>
				</tr>
				<tr>
					<th>보조인유무<span>*</span></th>
					<td>						
						<select class="select" name="ASSTN_PERSON_ENNC" id="SUPPORTER_YN">
							<option value="N" selected>아니오</option>
							<option value="Y">예</option>
						</select>
					</td>
					<th>의사소통가능여부<span>*</span></th>
					<td>
						<select class="select" name="DS_MLRD_POSBL_AT" id="COMMUNICALBE_YN">
							<option value="N" selected>아니오</option>
							<option value="Y">예</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>도움필요여부<span>*</span></br>(혼자 거동 불가)</th>
					<td>
						<select class="select" name="ASSTN_NEED_AT" id="HELPER_YN">
							<option value="N" selected>아니오</option>
							<option value="Y">예</option>
						</select>
					</td>

					<th>기초생활수급여부<span>*</span></th>
					<td>
						<select class="select" name="BASIC_LIVELIHOOD_AT" id="LIVELIHOOD_YN">
							<option value="N" selected>아니오</option>
							<option value="Y">예</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3" style="padding-top:10px;"> 
						<input type="text" id="ATCHMNFL_REAL_NM" class="inp p02" placeholder="장애인증명서 파일을 첨부해주세요." readonly="readonly">
						<a href="javascript:fileAdd()" class="btn02" id="btnFile">파일첨부</a>
						<span class="crTxt">* Fax 등 제출 가능</span>
						<div id="attachNameZone" >
<%-- 						<div id="attachNameZone" style="display:none;"> --%>
							<ul id="attachNameUl"></ul>
						</div>
						<div id="attachFileZone" style="display:none;">
							<ul id="attachFileUl"></ul>
						</div>
					</td> 
				</tr>
				 
			</table>
			</form>
			<div class="btnBox"> 
				<a href="#" id="memberAdd" class="btnType01">회원가입 요청</a>
			</div>
		</div>
		

	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
</div>
</body>
</html>	