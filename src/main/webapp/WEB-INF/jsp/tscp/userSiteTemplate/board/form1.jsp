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
	<script type="text/javascript" src="/js/tscp/userSiteTemplate/form1.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- header // -->
		<jsp:include page="/view/common/includeHeader"></jsp:include>

		<!-- body -->
		<div class="container">

			<div class="location"></div>
			<div class="contents"><!-- subContent -->
				<div class="subTitle bg01"></div>
				<table class="sTable01">
					<colgroup>
						<col width="120" />
						<col width="" />
					</colgroup>
					<tr>
						<th>조회조건</th>
						<td >
							<select id="SEARCH_KEY" class="select w100">
								<option value="">선택</option>
								<option value="A">제목</option>
								<option value="B">작성자</option>
								<option value="C">내용</option>
							</select>
							<input type="text" class="inp w250 ml5" id="SEARCH_TEXT" />
						</td>
					</tr> 
					<tr>
						<th>등록일</th>
						<td>
							<input type="text" id="ST_DT" class="inp datepicker" disabled="disabled" />							
							<input type="text" id="EN_DT" class="inp datepicker ml20" disabled="disabled" /> 
							<a href="javascript:void(0)" id="searchList" class="btn01 t01">찾기</a>
						</td>
					</tr> 
				</table>
	
				<div class="boardHeader">
					<p>검색목록: 총 <span id="listCnt"></span>건</p>
					<div class="btn">
						<a href="javascript:void(0)" class="btnType01 hide" id="noticeOpen">등록</a>
						<!--<a href="javascript:void(0)" class="btnType02">삭제</a> -->
					</div>
				</div>
				<form id="listTable">
				<input type="hidden" id="GUBN" value="${contentsInfo.CNTNTS_TYPE2_CD }" /> 
				<input type="hidden" id="NOTICE_CLS_CD" value="${contentsInfo.CNTNTS_CLS_CD }" />
				<input type="hidden" id="CNTNTS_NO" value="${contentsInfo.CNTNTS_NO }" />
				<input type="hidden" id="FILE_YN" value=""/>
				<input type="hidden" id="FILE_CNT" value=""/>
				<input type="hidden" id="FILE_MAX_QTY" value=""/>
				<input type="hidden" id="FILE_EXT" value=""/>
					<table class="listTable"></table>
				</form>
				<div class="paging"></div>
			</div><!-- end subContent -->
		</div><!-- end  container -->
	</div>
	
	<!-- footer// -->
	<jsp:include page="/view/common/includeFooter"></jsp:include>
	
	<div class="dialog" id="noticePopup" style="display:block;">
		<div class="pop"> 
			<div class="pHeader">
				<span>등록</span>
				<a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img src="/img/btn/btn_close01.png" /></a>
			</div>
			<div class="pCont" style="height: 300px;">
				<form id="noticeForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="NOTICE_NO" id="NOTICE_NO" />
				<input type="hidden" name="NOTICE_CLS_CD2" id="NOTICE_CLS_CD2" />
				<input type="hidden" name="U_CNTNTS_NO" id="U_CNTNTS_NO" />
				<input type="hidden" name="CNTNTS_TYPE2_CD2" id="CNTNTS_TYPE2_CD2" />
				<input type="hidden" name="CNTNTS_NO" id="CNTNTS_NO2" />
				<input type="hidden" name="CNTNTS_CLS_CD" id="CNTNTS_CLS_CD" />
				<input type="hidden" name="CNTER_CD" id="CNTER_CD" />
				
					<table class="sTable01 ptype" style="margin-bottom: 10px">
						<colgroup>
							<col width="90" />
							<col width="" />
							<col width="90" />
							<col width="" />  
						</colgroup>
						<tr>
							<th>제목<mark>*</mark></th>
							<td colspan="3">
								<input type="text" name="TITLE" id="TITLE" class="inp" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<th>내용<mark>*</mark></th>
							<td colspan="3">
								<textarea class="textarea h150" name="CTNT" id="CTNT"></textarea> 
							</td>  
						</tr>
						<tr id="fileArea">
							<th>파일리스트</th>
							<td colspan="3">
								<div><a href="javascript:fileAdd();" class="btn02">첨부 파일 추가</a></div>
								<div id="attachNameZone">
									<ul id="attachNameUl"></ul>
								</div>
								<div id="attachFileZone" style="display:none;">
									<ul id="attachFileUl"></ul>
								</div>
							</td>
						</tr>
					</table>
					<label>
						<input id="SECRET_CHECK" name="SECRET_CHECK" onclick="$notice.secretSelect()" style="float:left; margin-left: 10px; margin-right: 3px; zoom: 2.5;" type="checkbox" value="N">
						<div style="padding-top: 4px; font-size: 17px;">비밀글</div>
					</label>
				</form>
				
				<div class="btnBox" id="noticeViewBtn">
					<a href="javascript:void(0);" id="noticeAdd" class="btnType01">등록</a>
					<a href="javascript:void(0);" id="noticeUpdate" class="btnType01 CRUDBtn">수정</a>
					<a href="javascript:$('.dialog').hide(); document.getElementById('noticeForm').reset();" class="btnType02">닫기</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="replyPopup" style="display:block;">
		<div class="pop"> 
			<div class="pHeader">
				<span>댓글작성</span>
				<a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img src="/img/btn/btn_close01.png" /></a>
			</div>
			<div class="pCont">
				<form id="replyForm">
					<table class="sTable01 ptype" >
						<colgroup>
							<col width="100" />
							<col width="" />
							<col width="100" />
							<col width="" />
							<col width="100" />
							<col width="" />
						</colgroup>
						<tr>
							<th>작성자</th>
							<td id="val1"></td>
							<th>작성일</th>
							<td id="val2"></td>
							<th>조회</th>
							<td id="val3"></td>
						</tr> 
						<tr> 
							<td class="cont" colspan="6"> 
								<div class="contText" id="val4"></div>
							</td>
						</tr> 
					</table>
					
					<dl class="replydl"></dl>
		
					<table class="writeTable mt0" id="replayArea"> 
						<colgroup>
							<col width="100">
							<col width="">
							<col width="150">
							<col width="">
						</colgroup> 
						<tr> 
							<td class="al">내용</td>
							<td colspan="3">
								<textarea class="reply" name="replayCtnt" id="replayCtnt"></textarea>
								<a href="javascript:void(0);" id="replyAdd" class="btn04">댓글입력</a>
							</td> 
						</tr> 
					</table>
				</form>
			</div>  
		</div>
	</div>
</body>
</html>