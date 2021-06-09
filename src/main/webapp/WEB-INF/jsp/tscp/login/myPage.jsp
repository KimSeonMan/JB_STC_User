<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
<body onload="javascript:$memberShip.myPage();">
<div class="wrapper">
	<!-- header // -->
	<jsp:include page="/view/common/includeHeader"></jsp:include>
	<jsp:include page="/easypay/web/batch/cert.jsp"></jsp:include>
	<!-- body -->
	<div class="container">
		<div class="lnb"></div><!-- lnb -->
		<div class="location">
			<ul>
				<li class="home">홈</li>
				<li>
					<a href="javascript:void(0)">Account</a>
					<ul>
						<%if (user_id == null) {%>
						<li><a href="/view/memberShipAgree.do">회원가입</a></li>
						<li><a href="javascript:loginProcess();">로그인</a></li>
						<li><a href="/view/login/find.do">아이디/비밀번호찾기</a></li>
						<%} else { %>
						<li><a href="/view/myPage.do">마이페이지</a></li>
						<%}%>
					</ul>
				</li>
				<li class="last">
					<a href="javascript:void(0)">마이페이지</a>
				</li>
			</ul>
		</div>
		<div class="contents">
			<div class="subTitle">
				<h2>마이페이지</h2>
			</div>

			<div class="sthBox">
				<span class="t01">기본정보</span>
				<span class="t02">*필수입력 항목</span>
			</div>

			<form id="memberForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="TELNO" id="TELNO">
				<input type="hidden" name="MBTLNUM" id="MBTLNUM">
				<input type="hidden" name="EMAIL" id="EMAIL">
				<table class="sTable01 ptype">
					<colgroup>
						<col width="150" />
						<col width="" />
						<col width="170" />
						<col width="" />
					</colgroup>
					<tr>
						<th>센터구분<span>*</span></th>
						<td colspan="3" >
							<span id="CNTER_NM"></span>
							<input type="hidden" class="inp" name="CNTER_CD" id="CNTER_CD" />
						</td>
					</tr>
					<tr>
						<th>이름<span>*</span></th>
						<td><span id="NAME_DE"></span>
							<input type="hidden" id="NAME" name="MBER_NM"/></td>
						<th>국적<span>*</span></th>
						<td>
							<span id="MBER_NT_CD_DE"></span>
							<input type="hidden"  name="MBER_NT_CD" id="MBER_NT_CD"/>
						</td>
					</tr>
					<tr>
						<th>아이디<span>*</span></th>
						<td>
							<span id="USER_ID_DE"></span>
							<input type="hidden"  id="USER_ID" name="MBER_ID"/>
						</td>
						<th>성별<span>*</span></th>
						<td>
							<span id="SEXDSTN_DE"></span>
							<input type="hidden"  id="SEXDSTN" name="SEXDSTN"/>
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
							<select class="select t01" id="PHONE1"></select>
							<input type="text" class="inp t01" id="PHONE2" maxlength="4" oninput="javascript:chkNum(this);"/>
							<span class="etxt01">-</span>
							<input type="text" class="inp t01" id="PHONE3" maxlength="4" oninput="javascript:chkNum(this);"/>
						</td>
						<th>휴대폰<span>*</span></th>
						<td>
							<select class="select t01" id="MOBILE1"></select>
							<input type="text" class="inp t01" id="MOBILE2" maxlength="4" oninput="javascript:chkNum(this);"/>
							<span class="etxt01">-</span>
							<input type="text" class="inp t01" id="MOBILE3" maxlength="4" oninput="javascript:chkNum(this);"/>
						</td>
					</tr>
					<tr>
						<th>생년월일<span>*</span></th>
						<td>
							<span id="MY_BIRTH_DATE"></span>
							<input type="hidden" name="BRTHDY" id="BIRTH_DATE">
						</td>
						<th>SMS수신여부<span>*</span></th>
						<td>
							<select class="select" name="SMS_RECPTN_AT" id="SMS_RECEIVE_YN">
								<option value="Y" selected>예 (예약 신청 완료 등 문자 정보 수신)</option>
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
						<td colspan="3">
							<input type="text" class="inp" name="ADRES" id="ADDRESS" maxlength="30" placeholder="우편번호 검색 시 자동입력됩니다." readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<input type="text" class="inp" name="ADRES_DETAIL" id="ADDRESSDTL" maxlength="20" placeholder="상세 주소를 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>이메일<span>*</span></th>
						<td colspan="3">

							<input type="text" class="inp t02" id="EMAIL1" maxlength="30"/>
							<span class="etxt01">@</span>
							<input type="text" class="inp t02" id="EMAIL2" maxlength="20"/>
							<select class="select t03" id="EMAIL3" onchange="changeEmail(this.value);"></select>
						</td>
					</tr>
					<tr id="batchkeyInfo">
						<th>카드정보</th>
						<td colspan="3">
							<span id="CARD_INFO" class="etxt01" style="display: flow">등록된 카드정보가 있습니다.</span>
							<input type="hidden" id="store_pay_id" value="0">
							<a href="javascript:$memberShip.cardInfo()" id="batchkeyBtn" class="btn02">등록</a>
							<a href="javascript:$memberShip.deleteCardInfo()" id="batchkeyDelBtn" class="btn02"
							   style="display: none">삭제</a>
						</td>
					</tr>
					<tr>
						<th>사업자정보</th>
						<td colspan="3">
							<a href="javascript:$memberShip.showLicenceInfo()" id="LicenseeInfo" class="btn02">정보보기</a>
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
						<th>장애종류</th>
						<td id="DISABLE_TYPE1_CD_SET"></td>
						<select class="select" name="TROBL_KND_CD" id="DISABLE_TYPE1_CD" style="display:none"></select>
						<th>장애등급</th>
						<td id="DISABLE_TYPE2_CD_SET"></td>
						<input type="hidden" name="TROBL_GRAD" id="DISABLE_TYPE2_CD">
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
						<th>기초생활수급여부</th>
						<td id="LIVELIHOOD_YN_SET"></td>
						<input type="hidden" name="BASIC_LIVELIHOOD_AT" id="LIVELIHOOD_YN">
					</tr>
<%--					<tr>--%>
<%--						<th>사용유효기간</th>--%>
<%--						<td id="VALID_DT_SET" colspan='3' style="padding:10px 20px;">--%>
<%--							<span class="etxt02" id="VALID_DT"></span><br/>--%>
<%--							<span class="crTxt" style="height:10px;line-height:10px;">* 장애등급 갱신은 운영자에게 문의주세요.</span></td>--%>
<%--					</tr>--%>
				</table>
			</form>
			<div class="btnBox">
				<a href="javascript:void(0)" class="btnType01" id="memberUpdate">수정</a>
				<a href="javascript:void(0)" class="btnType02" id="memberDelete">탈퇴</a>
			</div>
		</div>


	</div>

	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>

	<div class="dialog" id="LicenseeInfoPopup" style="display:block;">
		<div class="pop">
			<div class="pHeader">
				<span>사업자정보</span>
				<a href="javascript:void(0);" class="popClose"><img src="/img/btn/btn_close01.png"/></a>
			</div>
			<div class="pCont">
				<form id="LicenseeForm">
					<div class="pActicle">
						<p class="etcTitle"> 특별교통수단 사업자정보</p>
						<table class="sTable01 ptype">
							<colgroup>
								<col width="100"/>
								<col width="315"/>
								<col width="100"/>
								<col width=""/>
								<col width="180"/>
								<col width="150"/>
							</colgroup>
							<tr>
								<th>기관명</th>
								<th>상호/단체명</th>
								<th>대표자명</th>
								<th>소재지</th>
								<th>사업자번호/고유번호</th>
								<th>연락처</th>
							</tr>
							<tr>
								<td>전주시</td>
								<td>전주시시설관리공단</td>
								<td>전성환</td>
								<td>전라북도 전주시 완산구 백제대로 310(중화산동2가)</td>
								<td>418-82-06007</td>
								<td>063-239-2731</td>
							</tr>
							<tr>
								<td>군산시</td>
								<td>사)전북지체장애인협회군산지회</td>
								<td>정경섭</td>
								<td>전라북도 군산시 소룡2길 51, 1층(소룡동)</td>
								<td>401-82-05859</td>
								<td>063-445-1190</td>
							</tr>
							<tr>
								<td>익산시</td>
								<td>전북지체장애인협회익산시지회</td>
								<td>최병철</td>
								<td>전라북도 익산시 익산대로30길 11-1 (남중동)</td>
								<td>403-82-08037</td>
								<td>063-853-1334</td>
							</tr>
							<tr>
								<td>정읍시</td>
								<td>(사)전북지체장애인협회 정읍시지회</td>
								<td>김택진</td>
								<td>전라북도 정읍시 수성5로 41-11(수성동)</td>
								<td>404-82-05203</td>
								<td>063-536-9870</td>
							</tr>
							<tr>
								<td>남원시</td>
								<td>사단법인 전북지체장애인협회 남원시지회</td>
								<td>양홍석</td>
								<td>전라북도 남원시 의총로 92(하정동 2층)</td>
								<td>407-82-04329</td>
								<td>063-626-1911</td>
							</tr>
							<tr>
								<td>김제시</td>
								<td>(사)전북지체장애인협회김제시지회</td>
								<td>이창원</td>
								<td>전라북도 김제시 갈공길 82(서암동)</td>
								<td>403-82-08911</td>
								<td>063-544-8270</td>
							</tr>
							<tr>
								<td>완주군</td>
								<td>(사)전북지체장애인협회 완주군지회</td>
								<td>심동택</td>
								<td>전라북도 완주군 봉동읍 봉동동서로 89, 1층</td>
								<td>402-82-12218</td>
								<td>063-261-7119</td>
							</tr>
							<tr>
								<td>진안군</td>
								<td>꿈드래장애인협회 진안군지부</td>
								<td>유태옥</td>
								<td>전라북도 진안군 진안읍 관산2길 11</td>
								<td>418-82-61368</td>
								<td>063-433-4222</td>
							</tr>
							<tr>
								<td>무주군</td>
								<td>전북지체장애인협회무주군지회</td>
								<td>이대수</td>
								<td>전라북도 무주군 무주읍 교동1길 13</td>
								<td>418-82-62858</td>
								<td>063-324-1418</td>
							</tr>
							<tr>
								<td>장수군</td>
								<td>사단법인장수군장애인연합회</td>
								<td>권성인</td>
								<td>전라북도 장수군 장수읍 신천로 79, 101호</td>
								<td>407-82-06778</td>
								<td>063-351-4967</td>
							</tr>
							<tr>
								<td>임실군</td>
								<td>(사)임실군장애인연합회</td>
								<td>손주완</td>
								<td>전라북도 임실군 임실읍 호국로1644</td>
								<td>407-82-06308</td>
								<td>063-642-5118</td>
							</tr>
							<tr>
								<td>순창군</td>
								<td>전북지체장애인협회 순창군지회</td>
								<td>최기순</td>
								<td>전라북도 순창군 순창읍 장류로 407-11, 1층 실내체육관 내</td>
								<td>407-82-07248</td>
								<td>063-653-4845</td>
							</tr>
							<tr>
								<td>고창군</td>
								<td>고창군교통약자이동지원센터</td>
								<td>최혜성</td>
								<td>전라북도 고창군 고창읍 월곡공원2길 28</td>
								<td>601-82-68788</td>
								<td>063-561-2338</td>
							</tr>
							<tr>
								<td>부안군</td>
								<td>전북지체장애인협회 부안군지회</td>
								<td>이준홍</td>
								<td>전라북도 부안군 부안읍 오리정로 89</td>
								<td>755-82-00033</td>
								<td>063-583-1010</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>	