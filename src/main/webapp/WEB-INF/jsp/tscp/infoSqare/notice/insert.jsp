<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8" />
	<meta name="viewport" content="user-scalable=yes, width=1330px"/>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>이동지원센터</title>
	<script type="text/javascript" src="/js/common/includeSource.js"></script>
	<script type="text/javascript" src="/js/common/common.js"></script>
	<script type='text/javascript' src='/js/plugins/jquery.form.js'></script>
	<script type='text/javascript' src='/js/plugins/ckeditor/ckeditor.js'></script>
	<script type='text/javascript' src='/js/tscp/infoSqare/notice/insert.js'></script>
</head>
<body>
	<div id="wrap">
		<!-- header // -->
		<header>
			<!-- Top Include -->
			<jsp:include page="/view/common/includeHeader"></jsp:include>
		</header>

		<!-- body -->
		<div id="container">
			<h4>공지사항</h4>
			<form id="resetForm" method="post" enctype="multipart/form-data">
				<table>
					<caption>공지사항등록</caption>
					<colgroup>
						<col width="141"/>
						<col width=""/> 	
					</colgroup>
					<tbody>
						<tr>
							<th>구분</th>
							<td>										
								<select id="NOTICE_CLS_CD"></select>
							</td>	
						</tr>
						<tr>
							<th>제목</th>
							<td>										
								<input type="text" id="TITLE" maxlength="50"/>
							</td>	
						</tr>		
						<tr>
							<th>내용</th>
							<td>										
								<textarea rows="20" cols="100" id="CTNT" style="resize:none" maxlength="2000"></textarea>
							</td>	
						</tr>	
						<tr>
							<th>첨부파일</th>
							<td>
								<div class="noticeFont">( 첨부 용량 제한: 20 MB)</div>
								<div>
							 		<input type="file" name="file" id="fileUploader" />
								</div>
								<div id="fileList"></div>
								<div class="noticeFont"> * 첨부파일형식 : zip, hwp, doc, ppt, pptx, xls, xlsx, txt, bmp, jpg, jpeg, gif, png 가능함</div>
							</td>
						</tr>
					</tbody>
				</table>	
			</form>		
			<a href="javascript:noticeAdd();">등록</a>
		</div>

		<!-- footer// -->
	    <footer id="footer">
	    	<!-- Bottom Include -->
			<jsp:include page="/view/common/includeFooter"></jsp:include>
	    </footer>
	</div>
</body>
</html>