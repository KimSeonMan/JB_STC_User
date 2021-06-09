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
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type="text/javascript" src="/js/tscp/mngr/qestnarMng.js"></script>
</head>
<body>
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
						<th>제목</th>
						<td>
							<input type="text" id="SEARCH_TEXT" class="inp"/>
						</td>
						<th>메인보기유무</th>
						<td>
							<select id="SEARCH_KEY" class="select">
								<option value="" selected>선택</option>
								<option value="Y">예</option>
								<option value="N">아니오</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>설문시작일</th>
						<td colspan="2">
							<input type="text" id="ST_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
							<span>~</span>
							<input type="text" id="EN_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
						</td>
						<td class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr>
				</table>
	
				<p class="etcTitle">설문 목록</p>
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="qestnarMngOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="qestnarMngDelete" class="btn02 CRUDBtn">삭제</a>
					</div>
				</div>
				<form id="listTable">
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="qestnarMngPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">설문 등록</p>
			
			<div class="cont">
				<form id="qestnarMngForm">
				<input type="hidden" id="SURVEY_ID" name="SURVEY_ID" />
				<input type="reset" id="resetForm" onclick="this.form.reset()"/>
					<table class="formTable t01 mt20" >
						<tr>
							<th>제목<mark>*</mark></th>
							<td>
								<input type="text" class="inp" name="SURVEY_TITLE" id="SURVEY_TITLE" maxlength="50" />
							</td>   
						</tr>
						<tr> 
							<th>기간설정<mark>*</mark></th>
							<td>
								<span>시작</span>
								<input type="text" class="inp datepicker w100" name="SURVEY_ST_DATE" id="SURVEY_ST_DATE" readonly="readonly"/>
								<span>~ 종료</span>
								<input type="text" class="inp datepicker w100" name="SURVEY_EN_DATE" id="SURVEY_EN_DATE" readonly="readonly"/>
							</td> 
						</tr>
						<tr> 
							<th>대상선택<mark>*</mark></th>
							<td>
								<input type="radio" name="TARGET_USER_CD" value="01" checked/>
								<label for="targetRadio01">전체</label>
								<input type="radio" name="TARGET_USER_CD" value="02"/>
								<label for="targetRadio02">회원</label>
								<input type="radio" name="TARGET_USER_CD" value="03"/>
								<label for="targetRadio03">비회원</label>
							</td> 
						</tr>
						<tr> 
							<th>메인에 보기 표시<mark>*</mark></th>
							<td>
								<input type="radio" name="MAIN_VIEW_YN" value="Y"/>
								<label for="viewRadio01">예</label>
								<input type="radio" name="MAIN_VIEW_YN" value="N" checked/>
								<label for="viewRadio02">아니오</label> 
							</td> 
						</tr>
						<tr> 
							<th>설명<mark>*</mark></th>
							<td>
								<textarea name="SURVEY_REMARK" id="SURVEY_REMARK" class="textarea" maxlength="2000"></textarea>
							</td> 
						</tr>
						<tr> 
							<th>문항관리<mark>*</mark></th>
							<td>
								<table class="formTable subType">
									<colgroup>
										<col width="70" />
										<col width="" />
									</colgroup>
									<tr>
										<td colspan="2">
											<a href="javascript:optionsAdd('A','SURVEY_CTNT','');" class="btn04">문항추가</a>
											<a href="javascript:optionsDelete('A','SURVEY_CTNT');" class="btn04">문항삭제</a>
										</td>   
									</tr>
									<tr class="SETTINGS1">
										<th>형식</th>
										<td>
											<input type="radio" name="SURVEY_TYPE_CD1" value="01" checked onclick="javascript:changeType('SURVEY_TYPE_CD1', 'SURVEY_CHOICE_CD1', 'SURVEY_CTNT1');" />
											<label for="typeCk01">객관식</label>
											<input type="radio" name="SURVEY_TYPE_CD1" value="02" onclick="javascript:changeType('SURVEY_TYPE_CD1', 'SURVEY_CHOICE_CD1', 'SURVEY_CTNT1');" />
											<label for="typeCk02">주관식</label>
											<input type="radio" name="SURVEY_CHOICE_CD1" value="01" checked/>
											<label for="typeCk03">단일선택</label>
											<input type="radio" name="SURVEY_CHOICE_CD1" value="02" />
											<label for="typeCk04">중복선택</label>
										</td>   
									</tr>
									<tr class="SURVEY_CTNT">
										<th>문항</th>
										<td>
											<input type="text" class="inp" name="SURVEY_CTNT1" maxlength="250" placeholder="질문하실 내용을 입력하세요." />
										</td>   
									</tr>
									<tr class="SURVEY_CTNT1">
										<td class="ac">1</td>
										<td>
											<input type="text" class="inp t01" name="SURVEY_CTNT1" />
											<a href="javascript:optionsAdd('B','SURVEY_CTNT1','');" class="btn04">추가</a>
											<a href="javascript:optionsDelete('B','SURVEY_CTNT1')" class="btn04">삭제</a>
										</td>   
									</tr>
								</table>
							</td> 
						</tr> 
					</table>
				</form>
				
				<div class="btnbox">
					<a href="javascript:$('.dialog').hide();" class="btn03">닫기</a>
					<a href="javascript:void(0)" id="qestnarMngAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="qestnarMngUpdate" class="btn03 CRUDBtn">수정</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="qestnarMngResultPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0);" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">설문조사 결과</p>
			
			<div class="cont" id="qestnarResultHtml"></div>	
		</div>
	</div>
</body>
</html>