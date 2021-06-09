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
	<script type="text/javascript" src="/js/tscp/mngr/boardMng.js"></script>
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
					<colgroup>
						<col width="90" />
						<col width="" />
						<col width="100" />
					</colgroup>
					<tr>
						<th>분류</th>
						<td colspan="2">
							<select id="CNTNTS_TYPE2_CD" class="select w200" onchange="javascript:changeGubn('NOTICE_CLS_CD',this.value,'10');">
								<option value="40">Q&A</option>								
								<option value="60" selected>공지사항</option>
							</select>
							<select id="NOTICE_CLS_CD" class="select w200">
								<option value="">선택</option>
							</select>
						</td>
					</tr> 
<!-- 					<tr> -->
<!-- 						<th>메뉴명</th> -->
<!-- 						<td colspan="2"> -->
<!-- 							<select id="NOTICE_CLS_CD3" class="select w200"> -->
<!-- 								<option value="">선택</option> -->
<!-- 							</select> -->
<!-- 						</td> -->
<!-- 					</tr>  -->
					<tr>
						<th>조회조건</th>
						<td colspan="2">
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
							<input type="text" id="ST_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
							<span>~</span>
							<input type="text" id="EN_DT" class="inp datepicker" style="width:60px;" disabled="disabled" /> 
						</td>
						<td class="ar"><a href="javascript:void(0)" id="searchList" class="btn01">찾기</a></td>
					</tr> 
				</table>
	
				<div class="boardHeader">
					<div class="fl"></div>
					<div class="fr btn">
						<a href="javascript:void(0)" id="noticeOpen" class="btn02 CRUDBtn">등록</a>
						<a href="javascript:void(0)" id="noticeDelete" class="btn02 CRUDBtn">삭제</a>
					</div>
				</div>
				<form id="listTable">
					<input type="hidden" id="CNTNTS_NO2" value="${CNTNTS_NO }"/>
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
		<div class="popbox"> 
			<a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">등록</p>
			
			<div class="cont">
				<form id="noticeForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="NOTICE_NO" id="NOTICE_NO" value="10"/>
				<input type="hidden" name="CNTNTS_CLS_CD" id="CNTNTS_CLS_CD" value="10"/>
				<input type="hidden" name="U_CNTNTS_NO" id="U_CNTNTS_NO" value="10"/>
				<input type="hidden" name="CNTER_CD" id="CNTER_CD" value=""/>
					<table class="formTable t01 mt20" >
						<colgroup>
							<col width="90" />
							<col width="" />
							<col width="90" />
							<col width="" />  
						</colgroup>
						<tr id="area1">
							<th>분류<mark>*</mark></th>
							<td>
								<select name="CNTNTS_TYPE2_CD2" id="CNTNTS_TYPE2_CD2" class="select w100" onchange="javascript:changeGubn('NOTICE_CLS_CD2',this.value,'10');">
									<option value="40">Q&A</option>									
									<option value="60">공지사항</option>
								</select>
								<select name="NOTICE_CLS_CD2" id="NOTICE_CLS_CD2" class="select w150"></select>
							</td>
							<th>메뉴명<mark>*</mark></th>
							<td id="menuArea"></td>
						</tr>
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
				</form>
				
				<div class="btnbox" id="noticeViewBtn">
					<a href="javascript:$('.dialog').hide(); document.getElementById('noticeForm').reset();" class="btn03">닫기</a>
					<a href="javascript:void(0);" id="noticeAdd" class="btn03">등록</a>
					<a href="javascript:void(0);" id="noticeUpdate" class="btn03 CRUDBtn">수정</a>
					<a href="javascript:void(0);" id="replyBtn" class="btn03">댓글작성</a>
				</div>
			</div>  
		</div>
	</div>
	
	<div class="dialog" id="replyPopup" style="display:block;">
		<div class="popbox"> 
			<a href="javascript:void(0); document.getElementById('replyForm').reset();" class="popClose"><img src="/img/ico/ico_close.png" alt="닫기"></a>
			<p class="popTitle" id="popTitle">댓글작성</p>
			
			<div class="cont">
				<form id="replyForm">
				
					<table class="formTable t01 mt20" >
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
		
					<table class="writeTable mt0"> 
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
								<a href="javascript:void(0);" id="replyAdd" class="btn04 CRUDBtn">댓글입력</a>
							</td> 
						</tr> 
					</table>
				</form>
				
				<div class="btnbox" id="replyViewBtn">
					<a href="javascript:$('.dialog').hide(); document.getElementById('replyForm').reset();" class="btn03">닫기</a>
				</div>
			</div>  
		</div>
	</div>
</body>
</html>