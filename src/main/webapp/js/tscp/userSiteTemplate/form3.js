/**
 * 공지사항 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.notice = W.$notice || {};
	$(document).ready(function() {
		$notice.list(1); //목록 조회
		
		//엔터키
		$('#SEARCH_TEXT').on('keypress', function(e) {
			if (e.which == 13) {
				$notice.list(1);
			}
		});
		
		$("#NOTICE_CLS_CD").change(function(){
			$notice.list(1);
		});
		
		$("#searchList").click(function(){
			$notice.list(1);
		});
		
	});
	
	$notice = {
		totalCnt : 0,
		currentPageIndex : 1,
		// notice API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.noticeList.api();
			senders.addParam("page", page);
			senders.addParam("GUBN", $("#GUBN").val());
			senders.addParam("NOTICE_CLS_CD", $("#NOTICE_CLS_CD").val());
			senders.addParam("GALLERY_YN", "N");
			if($("#SEARCH_KEY").val() != "" && $("#SEARCH_TEXT").val() != "") {
				senders.addParam("SEARCH_KEY", $("#SEARCH_KEY").val());
				senders.addParam("SEARCH_TEXT", $("#SEARCH_TEXT").val());
			}
			/*if($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
				if($("#ST_DT").val() <= $("#EN_DT").val()) {
					senders.addParam("ST_DT", $("#ST_DT").val());
					senders.addParam("EN_DT", $("#EN_DT").val());
				} else {
					alert("등록 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
					$("#ST_DT").focus();
					return false;
				}
			}*/
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/notice/noticeList.json",
			});
		},
		noticeDetail : function(str1, str2, str3, str4, str5) {	
			var senders = new tscp.noticeDetail.api();
			senders.addParam("CNTNTS_TYPE2_CD", str1);
			senders.addParam("CNTNTS_CLS_CD", str2);
			senders.addParam("CNTNTS_NO", str3);
			senders.addParam("HIT_CNT", str4);
			senders.addParam("NOTICE_NO", str5);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/notice/noticeDetail.json",
			});
		}
	};
	
	/*********** 공지사항목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.noticeList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var user_gbn_cd = session.user_gbn_cd;
				
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='80' />";
				html += "<col width='' />";
				html += "<col width='150' />";
				html += "<col width='130' />";
				html += "<col width='130' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>번호</th>";
				html += "<th>제목</th>"; 
				html += "<th>작성자</th>";
				html += "<th>작성일</th>";
				html += "<th>조회</th>";
				html += "</tr>";

				if(result.noticeList.length == 0) {
					html += "<tr><td colspan='5'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.noticeList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.noticeList[i].rnum+"</td>";
						html += "<td class='link' onclick=\"javascript:$notice.noticeDetail(\'"
							+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
							+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
							+result.noticeList[i].CNTNTS_NO+"\',\'"
							+result.noticeList[i].HIT_CNT+"\',\'"
							+result.noticeList[i].NOTICE_NO+"\');\">"+result.noticeList[i].TITLE+"</td>";
						html += "<td>"+result.noticeList[i].NAME+"</td>";
						html += "<td>"+result.noticeList[i].REG_TS+"</td>";
						html += "<td>"+result.noticeList[i].HIT_CNT+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.noticeListCount[0].CNT+" 건");
				$("#listCnt").html(result.noticeListCount[0].CNT);
				
				$notice.totalCnt = result.noticeListCount[0].CNT;
				paging($notice,"noticeList");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지사항목록조회 API 호출 End **********/
	
	/*********** 공지사항상세 API 호출 Start **********/
	(function() {
		$class("tscp.noticeDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#downloadUl").html(""); //파일첨부리스트 초기화
				
				var result = res.result;
				$("#val1").html(result.noticeDetail.TITLE);
				$("#val2").html(result.noticeDetail.NAME);
				$("#val3").html(result.noticeDetail.REG_TS);
				$("#val4").html((Number(result.noticeDetail.HIT_CNT)+1)+"");
				$("#val5").html(result.noticeDetail.CTNT);
				
				var fileList = result.fileInfoList;
				var html ="";
				if(fileList.length == 0) {
					$("#area1").hide();
					$("#noticeDetail").css("border-bottom","2px solid #97999f");
				} else {
					for(var i = 0; i < fileList.length; i++){
						html += "<li style='cursor:pointer;' onclick=\"$common.fileDown(\'"+fileList[i].FILE_ATTCH_ID+"\',\'"+fileList[i].FILE_SAVE_NAME+"\',\'"+fileList[i].FILE_REAL_NAME+"\');\">";
						html +="파일명 : " +fileList[i].FILE_REAL_NAME;
						html += "</li>";
					}
					$("#noticeDetail").css("border-bottom","");
					$("#downloadUl").append(html);
					$("#area1").show();
				}
				
				$("#noticePopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지사항상세 API 호출 End **********/
	
}(window, document));