/**
 * Index Class
 * 
 * history : NSsoft, 1.0, 2016/03/10 
 * author : ksh
 * version : 1.0
 * see : 
 *
 */
var noticeList = "";
var reportList = "";
var QAList = "";
(function(W, D) {
	W.main = W.$main || {};
	$(document).ready(function() {
		$main.mainList(); //공지사항등 리스트 조회
		
		$('#bookMarkBtn').on('click', function(e) {
			var bookmarkURL = window.location.href;
		    var bookmarkTitle = document.title;
		    var triggerDefault = false;

		    if (window.sidebar && window.sidebar.addPanel) {
		        // Firefox version &lt; 23
		        window.sidebar.addPanel(bookmarkTitle, bookmarkURL, '');
		    } else if ((window.sidebar && (navigator.userAgent.toLowerCase().indexOf('firefox') < -1)) || (window.opera && window.print)) {
		        // Firefox version &gt;= 23 and Opera Hotlist
		        var $this = $(this);
		        $this.attr('href', bookmarkURL);
		        $this.attr('title', bookmarkTitle);
		        $this.attr('rel', 'sidebar');
		        $this.off(e);
		        triggerDefault = true;
		    } else if (window.external && ('AddFavorite' in window.external)) {
		        // IE Favorite
		        window.external.AddFavorite(bookmarkURL, bookmarkTitle);
		    } else {
		        // WebKit - Safari/Chrome
		        alert((navigator.userAgent.toLowerCase().indexOf('mac') != -1 ? 'Cmd' : 'Ctrl') + '+D 를 이용해 이 페이지를 즐겨찾기에 추가할 수 있습니다.');
		    }

		    return triggerDefault;	
		});
		$('#replyAdd').on('click', function(e) {
			$main.replyAdd();
		});

		$('#pop-close').on('click', function(e) {
			$main.closeNoticePopup(0);
		});

		$('#pop-close02').on('click', function(e) {
			$main.closeNoticePopup(1);
		});


		// $('#popupNoticeImg').on('click', function(e) {
		// 	$("#popupNotice02").show();
		// });

		// KOBUS_테스트
		// $('#KOBUS_TEST').on('click', function(e) {
		// 	// KOBUS_테스트
		// 	window.open('about:blank','popup','width=500,height=500');
		// 	$("#RESVE_INFO").submit();
		// });
	});
	
	$main = {
		mainList : function(page) {	
			var senders = new tscp.mainList.api();
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/main/mainList.json",
			});
		},
		noticeDetail : function(str1, str2, str3, str4, str5, str6) {
			var senders = new tscp.noticeDetail.api();
			senders.addParam("TYPE",str6);
			senders.addParam("CNTNTS_TYPE2_CD", str1);
			senders.addParam("CNTNTS_CLS_CD", str2);
			senders.addParam("CNTNTS_NO", str3);
			senders.addParam("HIT_CNT", str4);
			senders.addParam("NOTICE_NO", str5);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/notice/noticeDetail.json",
				options : {
					gubn : str2
				}
			});
		},
		replyList : function(str1, str2, str3, str4, str5) {	
			$("#replayArea, .replydl").hide();
			var senders = new tscp.replyList.api();
			senders.addParam("CNTNTS_TYPE2_CD", str1);
			senders.addParam("CNTNTS_CLS_CD", str2);
			senders.addParam("CNTNTS_NO", str3);
			senders.addParam("HIT_CNT", str4);
			senders.addParam("NOTICE_NO", str5);
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			$("#CNTNTS_TYPE2_CD2").val(str1);
			$("#NOTICE_CLS_CD2").val(str2);
			$("#U_CNTNTS_NO").val(str3);
			$("#CNTNTS_NO2").val(str5);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/replyList.json",
			});
		},
		replyAdd : function() {	
			if ($("#replayCtnt").val() == "") {
				alert("댓글을 입력하세요.");
				$("#replayCtnt").focus();
				return false;
			} else {
				var senders = new tscp.replyAdd.api();
				senders.addParam("CNTNTS_TYPE2_CD", $("#CNTNTS_TYPE2_CD2").val());
				senders.addParam("CNTNTS_CLS_CD", $("#NOTICE_CLS_CD2").val());
				senders.addParam("CNTNTS_NO", $("#U_CNTNTS_NO").val());
				senders.addParam("NOTICE_NO", $("#CNTNTS_NO2").val());
				senders.addParam("REPLAY", $("#replayCtnt").val());
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/notice/replyAdd.json",
				});
			}
		}, 
		format : function date2str(x, y) {
		    var z = {
		        M: x.getMonth() + 1,
		        d: x.getDate(),
		        h: x.getHours(),
		        m: x.getMinutes(),
		        s: x.getSeconds()
		    };
		    y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
		        return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2)
		    });

		    return y.replace(/(y+)/g, function(v) {
		        return x.getFullYear().toString().slice(-v.length)
		    });
		},
		closeNoticePopup : function (idx) {

			if(idx == 1) {
				$("#popupNotice02").hide();
			}
			else {
				$("#popupNotice").hide();
			}

			closeCookiePopup(idx);
		}
	};	
	
	/*********** 메인공지사항등 API 호출 Start **********/
	(function() {
		$class("tscp.mainList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".mBoardList").empty(); //리스트 삭제
				
				var result = res.result;
				var html1 = "";
				var html2 = "";
				var html3 = "";
				if(result.noticeList.length == 0) {
					html1 += "<li>";
					html1 += "<a href='javascript:void(0)'>조회된 결과가 없습니다.</a>";
					html1 += "<span></span>";
					html1 += "</li>";
				} else {

					if(result.noticeList.length > 0) {
						$main.noticeDetail('60',result.noticeList[0].NOTICE_CLS_CD,result.noticeList[0].CNTNTS_NO,	result.noticeList[0].HIT_CNT,result.noticeList[0].NOTICE_NO,"P");
					}

					for(var i=0; i<result.noticeList.length; i++) {
						var title = result.noticeList[i].TITLE;
						html1 += "<li>";
						html1 += "<a href=\"javascript:$main.noticeDetail(\'60\',\'"
							+result.noticeList[i].NOTICE_CLS_CD+"\',\'"
							+result.noticeList[i].CNTNTS_NO+"\',\'"
							+result.noticeList[i].HIT_CNT+"\',\'"
							+result.noticeList[i].NOTICE_NO+"\',\'N\');\">"+result.noticeList[i].TITLE+"</a>";
						html1 += "<span>"+result.noticeList[i].REG_TS+"</span>";
						html1 += "</li>";
					}
				}
				
				noticeList = html1;
				
				if(result.reportList.length == 0) {
					html2 += "<li>";
					html2 += "<a href='javascript:void(0)'>조회된 결과가 없습니다.</a>";
					html2 += "<span></span>";
					html2 += "</li>";
				} else {
					for(var i=0; i<result.reportList.length; i++) {
						var title = result.reportList[i].TITLE;
						html2 += "<li>";
						html2 += "<a href=\"javascript:$main.noticeDetail(\'60\',\'"
							+result.reportList[i].NOTICE_CLS_CD+"\',\'"
							+result.reportList[i].CNTNTS_NO+"\',\'"
							+result.reportList[i].HIT_CNT+"\',\'"
							+result.reportList[i].NOTICE_NO+"\');\">"+result.reportList[i].TITLE+"</a>";
						html2 += "<span>"+result.reportList[i].REG_TS+"</span>";
						html2 += "</li>";
					}
				}
				
				reportList = html2;
				
				if(result.QAList.length == 0) {
					html3 += "<li>";
					html3 += "<a href='javascript:void(0)'>조회된 결과가 없습니다.</a>";
					html3 += "<span></span>";
					html3 += "</li>";
				} else {
					for(var i=0; i<result.QAList.length; i++) {
						var title = result.QAList[i].TITLE;
						html3 += "<li>";
						html3 += "<a href=\"javascript:$main.replyList(\'40\',\'"
							+result.QAList[i].NOTICE_CLS_CD+"\',\'"
							+result.QAList[i].CNTNTS_NO+"\',\'"
							+result.QAList[i].HIT_CNT+"\',\'"
							+result.QAList[i].NOTICE_NO+"\');\">"+result.QAList[i].TITLE+"</a>";
						html3 += "<span>"+result.QAList[i].REG_TS+"</span>";
						html3 += "</li>";
					}
				}
				
				QAList = html3;
				
				changeTab(1); //공지사항 탭 선택
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메인공지사항등 API 호출 End **********/
	
	/*********** 공지사항상세 API 호출 Start **********/
	(function() {
		$class("tscp.noticeDetail.api").extend($d.api.ajaxAPI).define({
				onSuccess : function(status, res, options) {
				var result = res.result;

				if(result.type == 'N') {

					$("#downloadUl").html(""); //파일첨부리스트 초기화

					if(options.gubn == "10") {
						$("#popTitleA").html("공지사항 상세");
					} else if(options.gubn == "20") {
						$("#popTitleA").html("보도자료 상세");
					}


					$("#val1").html(result.noticeDetail.TITLE);
					$("#val2").html(result.noticeDetail.NAME);
					$("#val3").html(result.noticeDetail.REG_TS);
					$("#val4").html(result.noticeDetail.HIT_CNT);
					$("#val5").html("<pre>"+result.noticeDetail.CTNT+"</pre>");

					var fileList = result.fileInfoList;
					var html ="";
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
				}
				else {

					$("#downloadUl01").html(""); //파일첨부리스트 초기화

					// $("#value1").html(result.noticeDetail.TITLE);
					// $("#value2").html(result.noticeDetail.NAME);
					// $("#value3").html(result.noticeDetail.REG_TS);
					// $("#value4").html(result.noticeDetail.HIT_CNT);
					$("#value5").html("<pre>"+result.noticeDetail.CTNT+"</pre>");

					var fileList = result.fileInfoList;
					var html ="";
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
						$("#downloadUl01").append(html);
						$("#area1").show();
					}

					// console.log("cash=",getCookie("JBSTC_NOTICE"));
                    if(getCookie("JBSTC_NOTICE_0") == "") {
                        //	$("#noticeDragPopup").show();
                        // 	$("#noticeDragPopup").draggable();
                        // $("#popupNotice").show();
                    }

                    if(getCookie("JBSTC_NOTICE_1") == "") {
                        $("#popupNotice02").show();
                    }
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지사항상세 API 호출 End **********/
	
	/*********** QA답글작성 API 호출 Start **********/
	(function() {
		$class("tscp.replyList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				document.getElementById('replyForm').reset();
				
				var result = res.result;
				var replyList = result.replyList;
				var html1 ="";
				var html2 ="";
				var cnt = 0;
				
				for(var i = 0; i < replyList.length; i++){
					if(i == 0) {
						$("#val11").html(result.replyList[i].NAME);
						$("#val12").html(result.replyList[i].REG_TS);
						$("#val13").html((Number(result.replyList[i].HIT_CNT)+1)+"");
						$("#val14").html("<pre>"+result.replyList[i].CTNT+"</pre>");
					} else {
						if(session.user_id == replyList[i].USER_ID) {
							cnt++;
						}
						html2 += "<dd>";
						html2 += "<div class='fl'>"+replyList[i].NAME+"</div>";
						html2 += "<div class='fc'>"+replyList[i].REPLAY+"</div>";
						html2 += "<div class='fr'>"+replyList[i].REG_TS+"</div>";
						html2 += "</dd>";
					}
				}
				html1 += "<dt>나의의견 <span>(총 <strong>"+cnt+"</strong> 개)</span></dt>";
				html1 += html2;
				$(".replydl").html(html1);
				$("#replyPopup").show();
				
				if(session.user_id != "null" && session.user_id != '' && session.user_id != undefined) { //QA
					$("#replayArea,  .replydl").show();
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** QA답글작성 API 호출 End **********/
	/*********** QA답글등록 API 호출 Start **********/
	(function() {
		$class("tscp.replyAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록되었습니다.");
					$(".dialog").hide();
					$main.mainList();
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** QA답글등록 API 호출 End **********/

	// 공지사항 다시보지않기 체크(하루이상띄우지않기)
	function setCookie( name, value, expiredays ){
		var todayDate = new Date();
		todayDate.setDate( todayDate.getDate() + expiredays );
		document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";";
	}

	function getCookie(cookie_name) {
		var isCookie = false;
		var start, end;
		var i = 0;

		// cookie 문자열 전체를 검색
		while(i <= document.cookie.length) {
			start = i;
			end = start + cookie_name.length;
			// cookie_name과 동일한 문자가 있다면
			if(document.cookie.substring(start, end) == cookie_name) {
				isCookie = true;
				break;
			}
			i++;
		}

		// cookie_name 문자열을 cookie에서 찾았다면
		if(isCookie) {
			start = end + 1;
			end = document.cookie.indexOf(";", start);
			// 마지막 부분이라는 것을 의미(마지막에는 ";"가 없다)
			if(end < start)
				end = document.cookie.length;
			// cookie_name에 해당하는 value값을 추출하여 리턴한다.
			return document.cookie.substring(start, end);
		}
		// 찾지 못했다면
		return "";
	}

	function closeCookiePopup(idx){
		if(idx == 1) {
			if ( $("#chEvent02").is(":checked")) {
				setCookie("JBSTC_NOTICE_1", "no", 1); // 하루 쿠키 적용 (  마지막 인자값이 날자임 )
			}
		}
		else {
			if ( $("#chEvent").is(":checked")) {
				setCookie("JBSTC_NOTICE_0", "no", 1); // 하루 쿠키 적용 (  마지막 인자값이 날자임 )
			}
		}
	}
}(window, document));

function changeTab(str) {
	var html = "";
	var htmlData = "";
	
	if(str == 1) {
		html += "<li class='on'><a href='javascript:changeTab(1);'>공지사항</a></li>";
		html += "<li><a href='javascript:changeTab(2);'>보도자료</a></li>";
		html += "<li><a href='javascript:changeTab(3);'>질문답변</a></li>";
		html += "<li><a href='/view/userSite.do?gubn=infoSqare401' class='more'>더 보기</a></li>";
		
		htmlData += "<ul class='list01'>"+noticeList+"</ul>";
	} else if(str == 2) {
		html += "<li><a href='javascript:changeTab(1);'>공지사항</a></li>";
		html += "<li class='on'><a href='javascript:changeTab(2);'>보도자료</a></li>";
		html += "<li><a href='javascript:changeTab(3);'>질문답변</a></li>";
		html += "<li><a href='/view/userSite.do?gubn=infoSqare402' class='more'>더 보기</a></li>";

		htmlData += "<ul class='list01'>"+reportList+"</ul>";
	} else {
		html += "<li><a href='javascript:changeTab(1);'>공지사항</a></li>";
		html += "<li><a href='javascript:changeTab(2);'>보도자료</a></li>";
		html += "<li class='on'><a href='javascript:changeTab(3);'>질문답변</a></li>";
		html += "<li><a href='/view/userSite.do?gubn=cmttSqare501' class='more'>더 보기</a></li>";

		htmlData += "<ul class='list01'>"+QAList+"</ul>";
	}
	
	$(".tabs").html(html);
	$("#boardData").html(htmlData);

}
