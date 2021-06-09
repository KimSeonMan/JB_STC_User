var contextPath = "";
var AuthInfo; // 세션정보
var CRUDInfo = ""; //버튼 권한정보

if (!AuthInfo) {
	AuthInfo = {
		authStatus : false
	};
}

var callbackSatuts = "";
var cntntsType2Cd = "";

$(document).ready(function () {
	$("#tabs01").show();
	$("#sbuTabs01").show();
	
	/*메인*/
	$(".mSlideArea").slick({
		autoplay: true, slidesToShow: 1, autoplaySpeed: 3000, pauseOnHover : true
	}); 
	$("body").on("click", "#autoPlay",function(ev){   
		var ck = $(this).hasClass("on");  
		if(!ck){ 
			$(this).addClass("on");  
			setTimeout(function(){
				$('.mSlideArea').slick("slickPause");
			},500); 
		}else{
			$(this).removeClass("on"); 
			$('.mSlideArea').slick("slickPlay");  
		} 
	}); 
	$('.mSlideArea').on('afterChange', function(){
		var num = $(".mSlideArea").slick('slickCurrentSlide');
		$(".slideController .num").removeClass("on");
		$(".slideController .num").eq(num).addClass("on");
	});
	$("body").on("click", ".slideController .num",function(ev){   
		var inx = $(this).index(".slideController .num");
		$(".slideController .num").removeClass("on");
		$(this).addClass("on"); 
		$('.mSlideArea').slick('slickGoTo', inx);
	});
	$(".mServiceSlide").slick({
		dots: true, slidesToShow: 1
	}); 
	$("body").on("click", ".mTabs a",function(ev){   
		$(".mTabs a").removeClass("on"); 
		$(this).addClass("on"); 
	});	
	/*메인*/ 
	
	/*서브*/
	$(".popTableScroll").mCustomScrollbar({ "autoExpandScrollbar":"true" }); 
	$("body").on("click", ".normalTabs a",function(ev){   
		var _id = $(this).attr("data-id"); 
		$(".normalTabs li").removeClass("on"); 
		$(this).parent("li").addClass("on"); 
		$(".tabArea").hide();
		$("#"+_id).show();
	});	
	$("body").on("click", ".defaultTabs a",function(ev){   
		var _id = $(this).attr("data-id"); 
		$(".defaultTabs a").removeClass("on"); 
		$(this).addClass("on"); 
		$(".stabox").hide();
		$("#"+_id).show();
	});	
	$("body").on("click", ".subTabs a",function(ev){   
		var _id = $(this).attr("data-id"); 
		$(".subTabs li").removeClass("on"); 
		$(this).parent("li").addClass("on"); 
		$(".dTable").hide();
		$("#"+_id).show();
	});	
	/*서브*/


	/*start 공통, 1차 slide 속도 수정, 2차 slide를 show, hide로 수정 */
	$("body").on("mouseover", ".gnb a",function(ev){  
		var inx = $(this).parent("li").index();
		$(".gnb li").removeClass("on");
		$(this).parent("li").addClass("on");
		$(".depthArea .item").removeClass("on");
		$(".depthArea .item").eq(inx).addClass("on");
		$(".depthArea").show();
//		$(".depthArea").stop().slideDown("slow", 'easeInCubic');

	});
	$("body").on("mouseover", ".depthArea .item",function(ev){  
		var inx = $(this).index(".depthArea .item");
		$(".gnb li").removeClass("on");
		$(".gnb li").eq(inx).addClass("on");
		$(".depthArea .item").removeClass("on");
		$(this).addClass("on"); 
	});
	$("body").on("mouseleave", ".depthArea",function(ev){   
		$(".gnb li").removeClass("on"); 
		$(".depthArea .item").removeClass("on"); 
		$(".depthArea").hide();
//		$(".depthArea").stop().slideUp("fast", 'easeInCubic');		
	});
	$("body").on("mouseenter", ".header>.rela",function(ev){    
		$(".gnb li").removeClass("on"); 
		$(".depthArea .item").removeClass("on"); 
		$(".depthArea").hide();
//		$(".depthArea").stop().slideUp("fast", 'easeInCubic');
	});
	
	$(".datepicker").datepicker({
		dateFormat: "yy-mm-dd",
		showOn: "button",
		buttonImage        : "../../img/ico/ico_calendar.png",
		buttonImageOnly: true 
	}); 
	 
	$("body").on("click", ".popClose",function(ev){  
		$(".dialog").hide();	
	}); 
	/* end 공통*/
	
	/*셀렉트박스
	$(".selectBox>a").hammer().on("tap", function(ev){
		$(this).next(".selectBoxOption").show();
	}); 
	$("body").on("mouseleave",".selectBoxOption",function(){
		$(this).hide();
	});
	$(".selectBoxOption>a").hammer().on("tap", function(ev){
		var target = $(this).parents(".selectBoxOption").prev("a").attr("data-name");
		var selectValue = $(this).attr("data-value"); 
		$(this).parents(".selectBoxOption").prev("a").text($(this).text());
		$("#"+target).val(selectValue);
		$(this).parents(".selectBoxOption").hide();
	});
	*/

	/*위치정보*/
	$(".resultArea").mCustomScrollbar({ "autoExpandScrollbar":"true" }); 
	$("body").on("click", ".resultTabs li",function(ev){   
		$(".resultTabs li").removeClass("on");
		$(this).addClass("on"); 
	});
	$("body").on("click", ".legendList li",function(ev){   
		$(".legendList li").removeClass("on");
		$(this).addClass("on"); 
	});
	$("body").on("click", ".switchBtn a",function(ev){   
		var inx = $(this).index(".switchBtn a"); 
		if(inx == 1){
			$(".switchBtn").removeClass("on");
		}else{
			$(".switchBtn").addClass("on");
		} 
	});
	$("body").on("click", ".leftToggle a",function(ev){   
		var ck = $(this).hasClass("on"); 
		if(!ck){
			$(this).addClass("on");
			$(".gpsLeft").stop().animate({"left":"-410px"},300, 'easeInCubic');
			$(".legendLayer").stop().animate({"left":"10px"},600, 'easeInCubic');
		}else{
			$(this).removeClass("on");
			$(".gpsLeft").stop().animate({"left":"0"},600, 'easeInCubic');
			$(".legendLayer").stop().animate({"left":"430px"},300, 'easeInCubic');
		} 
	});
	
//	sessionInfo(); //세션체크
	menuList();
	makeStamp(); //현재 일시
	$(".dialog").hide(); //팝업 초기값 숨김
	
	//회원조회 팝업
	//엔터키
	$('#USER_ID_POP').on('keypress', function(e) {
		if (e.which == 13) {
			$common.list(1);
		}
	});
	$('#NAME_POP').on('keypress', function(e) {
		if (e.which == 13) {
			$common.list(1);
		}
	});
	
	$("#userSearchList_pop").click(function(){
		$common.list(1);
	});
	
	
	$("body").on("mouseover",".location>ul>li>a",function(){
		var ck = $(this).parent("li").hasClass("on");
		$(".location>ul>li").removeClass("on"); 
		$(this).parent("li").addClass("on"); 
	});
	$("body").on("mouseleave",".location",function(){ 
		$(".location>ul>li").removeClass("on");  
		
	});
	
	$("body").on("click",".btnRadio",function(){ 
		$(".btnRadio").removeClass("on"); 
		$(this).addClass("on"); 
	}); 
	$("body").on("click",".btnChk",function(){ 
		var ck = $(this).hasClass("on"); 
		if(ck){
			$(this).removeClass("on"); 
		}else{
			$(this).addClass("on"); 
		}
	});
});

function sessionInfo() {
	var senders = new tscp.sessionInfo.api();
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/login/Session.json"
	});
};

function logoutProcess() {
	var senders = new tscp.logout.api();
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/login/LogOut.json"
	});
}

//코드목록 조회
function codeSearch(id_val) {
	var senders = new tscp.codeList.api();
	senders.addParam("id_val", id_val);
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/code/CodeSearch.json"
	});
}

//코드상세목록 조회
function codeDetailSearch(id_val, cd_id) {
	var senders = new tscp.codeDetailList.api();
	senders.addParam("id_val", id_val);
	senders.addParam("cd_id", cd_id);
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/code/CodeDetailSearch.json"
	});
}

//코드상세목록 조회2
function codeDetailSearch2(id_val, cd_id, word) {
	var senders = new tscp.codeDetailList2.api();
	senders.addParam("id_val", id_val);
	senders.addParam("cd_id", cd_id);
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/code/CodeDetailSearch.json",
		options : {
			word : word
		}
	});
}


//GNB메뉴 조회
function menuList() {
	var senders = new tscp.menuList.api();
	senders.addParam("CNTER_CD",session.cnter_cd);
	senders.request({
		method : "POST",
		async : false,
		url : contextPath + "/ServiceAPI/common/MenuList.json"
	});
}


/** ********* 세션 정보 조회 Start ********* */
(function() {
	$class("tscp.sessionInfo.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res) {
			var result = res.result;
			if (res.errCd == "0") {
				var Authobj;
				if (res.result.sessionId == null) {
					Authobj = {
						authStatus : false
					}
				} else {
					Authobj = {
						authStatus : true
					}
				}

				AuthInfo = Authobj;
//				setSession();
			} else {
				// messageAlert.open("알림", res.errMsg);
			}
		},
		onFail : function(status) {
		}
	});
}());
/** ********* 세션 정보 조회 END ********* */

/** ********* 로그 아웃 Start ********* */
(function() {
	$class("tscp.logout.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res) {
			var result = res.result;
			if (res.errCd == "0") {
				AuthInfo = {
					authStatus : false
				};
//				setSession();
				window.location.href = contextPath + "/view/index/"; //메인페이지 이동
			} else {
				messageAlert.open("알림", res.errMsg);
			}
		},
		onFail : function(status) {
		}
	});
}());
/** ********* 로그 아웃 END ********* */

/** ********* 코드목록 조회 Start ********* */
(function() {
	$class("tscp.codeList.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res) {
			var result = res.result;
			var optionList = "";
			for(var i=0; i<result.codeList.length; i++){
				optionList += "<option value='"+result.codeList[i].CD_ID+"'>"+result.codeList[i].CD_DE+"</option>";
			}
			$("#"+result.id_val).append(optionList);
		},
		onFail : function(status) {
		}
	});
}());
/** ********* 코드목록 조회 END ********* */

/** ********* 코드상세목록 조회 Start ********* */
(function() {
	$class("tscp.codeDetailList.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res) {
			var result = res.result;
			var optionList = "";
			for(var i=0; i<result.codeDetailList.length; i++){
				optionList += "<option value='"+result.codeDetailList[i].CD_VAL+"'>"+result.codeDetailList[i].CD_VAL_DESC+"</option>";
			}
			$("#"+result.id_val).append(optionList);
		},
		onFail : function(status) {
		}
	});
}());
/** ********* 코드상세목록 조회 END ********* */

/** ********* 코드상세목록 조회 Start ********* */
(function() {
	$class("tscp.codeDetailList2.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res,options) {
			var result = res.result;
			var optionList = "";
			for(var i=0; i<result.codeDetailList.length; i++){
				optionList += "<option value='"+result.codeDetailList[i].CD_VAL+"'>"+result.codeDetailList[i].CD_VAL_DESC+"</option>";
			}
			if(options.word == "cntntsType2Cd") {
				$("#"+result.id_val).html(optionList);
				cntntsType2Cd = optionList;
			} else {
				$("#"+result.id_val).append(optionList);
				$("#"+result.id_val).val(options.word);
				if(result.id_val == "NOTICE_CLS_CD") {
					$notice.list(1); //목록 조회
				} else if(result.id_val == "NOTICE_CLS_CD2") {
					$notice.noticeAddConfirm($("#CNTNTS_TYPE2_CD2").val(), "10");
				}
			}
		},
		onFail : function(status) {
		}
	});
}());
/** ********* 코드상세목록 조회 END ********* */


/** ********* GNB메뉴목록 조회 Start ********* */
(function() {
	$class("tscp.menuList.api").extend($d.api.ajaxAPI).define({
		onSuccess : function(status, res) {
			var result = res.result;
			
			var menuList1 = "";
			var menuList2 = "";
			var parent_menu_id = "";
			var url = document.location.href.replace("http://","");
			var tempUrl = url.split("/");
			url = url.replace(tempUrl[0],"");
			
			if(url.indexOf("?") != -1) {
				//정보컨텐츠 관리만 예외 처리
				if(url.split("?")[1].split("=")[0] == "CNTNTS_NO") {
					url = url.split("=")[0];
				}
			}
			var lnbList1 = "";
			var lnbList2 = "";
			var lnbList3 = "";
			var location = "";
			var tempName = "";
			var gnbName = "";
			var lnbName = "";
			var parentMenuId = "";
			var tempParentMenuId = "";
			var inx = 0;
			var count = 0;
			
			for(var i=0; i<result.menuList.length; i++){
				//권한 부분 체크 ---------------------------
				var access_cd = result.menuList[i].ACCESS_CD;
				var ctnt_url = "";
				
				if(access_cd == "00") { //접근불가
					ctnt_url = "<a href=\'javascript:alert(\"접근 권한이 없습니다.\");\'";
				} else if(access_cd == "10") { //조회만
					ctnt_url = "<a href='"+result.menuList[i].CTNT_URL+"'";
				} else { //CRUD
					ctnt_url = "<a href='"+result.menuList[i].CTNT_URL+"'";
				}
				
				if(gnbName == "") {
					if(result.menuList[i].PARENT_MENU_ID != tempParentMenuId) {
						lnbList1 = "";
					}
					if(result.menuList[i].MENU_LEVEL == "1") {
						menuList1 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
						if(i == 0) {
							menuList2 += "<div class='item first'>";
						} else {
							menuList2 += "</ul>";
							menuList2 += "</div>";
							menuList2 += "<div class='item'>";
						}
						menuList2 += "<ul>";
						
						tempName = result.menuList[i].MENU_NAME;
						tempParentMenuId = result.menuList[i].PARENT_MENU_ID;
					} else {
						var CTNT_URL = result.menuList[i].CTNT_URL;
						if(result.menuList[i].CTNT_URL.indexOf("?") != -1) {
							//정보컨텐츠 관리만 예외 처리
							if(result.menuList[i].CTNT_URL.split("?")[1].split("=")[0] == "CNTNTS_NO") {
								CTNT_URL = CTNT_URL.split("=")[0];
							}
						}
						
						var urlSplit = url.split("/");
						var ctntUrlSplit = CTNT_URL.split("/");
						var gubnSplit;
						var currentGubnSplit;
						
						
						if(ctntUrlSplit[1] == urlSplit[1] && ctntUrlSplit[2] == urlSplit[2] && ctntUrlSplit[3] == urlSplit[3]){
							lnbList1 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a>";
							lnbName = result.menuList[i].MENU_NAME;
							gnbName = tempName;
							lnbList2 += lnbList1 + "</li>";
							parentMenuId = result.menuList[i].PARENT_MENU_ID;
							CRUDInfo = access_cd;
						} else if(ctntUrlSplit[1] == urlSplit[1] && ctntUrlSplit[2] == urlSplit[2]){
							lnbList2 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
						} else if (ctntUrlSplit[2] != urlSplit[2]){
							if(ctntUrlSplit.length >= 3){
								gubnSplit = urlSplit[2].split("=");
								currentGubnSplit = ctntUrlSplit[2].split("=");
								
								if(currentGubnSplit.length >= 2 && gubnSplit.length >= 2){
//									if(gubnSplit[1] != currentGubnSplit[1]){
									if(gubnSplit[1].slice(0,gubnSplit[1].length-3) == currentGubnSplit[1].slice(0,currentGubnSplit[1].length-3)
											&& gubnSplit[1].slice(gubnSplit[1].length-3,gubnSplit[1].length) != currentGubnSplit[1].slice(currentGubnSplit[1].length, currentGubnSplit[1].length)){
										lnbList2 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
									}
								}
								
							}
						}
						menuList2 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
					}
				} else {
					if(result.menuList[i].MENU_LEVEL == "1") {
						menuList1 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
						if(i == 0) {
							menuList2 += "<div class='item first'>";
						} else {
							menuList2 += "</ul>";
							menuList2 += "</div>";
							menuList2 += "<div class='item'>";
						}
						menuList2 += "<ul>";
					} else {
						menuList2 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
						if(result.menuList[i].PARENT_MENU_ID == parentMenuId) {
							lnbList2 += "<li>"+ctnt_url+">"+result.menuList[i].MENU_NAME+"</a></li>";
						}
					}
				}
			}
			menuList2 += "</ul>";
			menuList2 += "</div>";
			
			$("#gnbMenu").html(menuList1);
			$("#subMenu").html(menuList2);
			
			lnbList3 += "<h2>"+gnbName+"<span>Local mobility support center</span></h2>"; 
			lnbList3 += "<ul>";
			lnbList3 += lnbList2;
			lnbList3 += "</ul>";
			
			if(lnbName != "") {
				$(".lnb").html(lnbList3);
				
				//header li class on
				$(".gnb li").each(function () {
					if($(this).text() == gnbName) {
						inx = count;
					}
					count++;
				});
				$(".gnb li").eq(inx).addClass("on");
				
				location += "<ul><li class='home'>홈</li>";
				location += lnbList1;
				location += "<ul>" + lnbList2 + "</ul>";
				location += "</li>";
				location += "<li class='last'><a>"+lnbName+"</a></li>";
				location += "</ul>";
				$(".location").html(location);
				lnbName = "<h2>" +lnbName+ "</h2>"
				$(".subTitle").html(lnbName);
			}
			
			/*//링크사이트
			var linkhtml = "";
			linkhtml += "<select class='select' onchange='jvascript:changeFamilyBox(this.value);'>";
			linkhtml += "<option>패밀리사이트</option>";
			if(result.linkSiteList.length > 0) {
				for(var i=0; i<result.linkSiteList.length; i++) {
					linkhtml += "<option value='"+result.linkSiteList[i].LINKED_URL+"'>"+result.linkSiteList[i].SITE_NAME+"</option>";
				}
			}
			linkhtml += "</select>";
			
			$(".familyBox").html(linkhtml);*/
		},
		onFail : function(status) {
		}
	});
}());
/** ********* GNB메뉴목록 조회 END ********* */


function layerClose(o){
	$(o).hide();
}

//세션받아오기 (사용확인필요)
function setSession() {
	var jsp = "";
	if (AuthInfo.authStatus) {
		jsp += "<a href='javascript:logoutProcess()'>" + '로그아웃' + "</a>";
	} 

	$(".nav").html(jsp);
//	var full = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
//	var url = "/view/fms/login.do";
//	var url2 ="/view/fms/login";

	// 함수 존재유무 판단
	if ($.isFunction(window.getSession)) {
		// 호출 페이지에 getSession 함수가 있으면 호출
		getSession();
	}
};

function loginProcess() {
	window.location.href = contextPath + "/view/login/"; //로그인 이동
}

//Timstamp 생성
function makeStamp() {
	var dt = new Date();

	var year = dt.getFullYear();
	var month = dt.getMonth()+1;
	var day = dt.getDate();
	var week = new Array("일","월","화","수","목","금","토");
	var hour = dt.getHours();
    var mi = dt.getMinutes();
    
	if(day < 10) { day = "0" + day; }
	if(month < 10) { month = "0" + month; }
	if(hour < 10) { hour = "0" + hour; }
	if(mi < 10) { mi = "0" + mi; }
	$("#makeStamp").html(year + ". " + month + ". " + day + " " + week[dt.getDay()] + "요일  " + hour + "시 " + mi + "분");
}

function currentDate() {
	var dt = new Date();

	var year = dt.getFullYear();
	var month = dt.getMonth()+1;
	var day = dt.getDate();
    
	if(day < 10) { day = "0" + day; }
	if(month < 10) { month = "0" + month; }
	
	return year + "-" + month + "-" + day;
}

//요일목록
function dayToWeek(str) {
	var html = "";
	html += "<option value='0'>일요일</option>";
	html += "<option value='1'>월요일</option>";
	html += "<option value='2'>화요일</option>";
	html += "<option value='3'>수요일</option>";
	html += "<option value='4'>목요일</option>";
	html += "<option value='5'>금요일</option>";
	html += "<option value='6'>토요일</option>";
	$("#"+str).append(html);
}

function dayToWeekHtml() {
	var html = "";
	html += "<input type='checkbox' name='chkWeek' value='0'/>&nbsp;일요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='1'/>&nbsp;월요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='2'/>&nbsp;화요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='3'/>&nbsp;수요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='4'/>&nbsp;목요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='5'/>&nbsp;금요일&nbsp;&nbsp;";
	html += "<input type='checkbox' name='chkWeek' value='6'/>&nbsp;토요일";
	return html;
}

function dayToWeekHWP(str) {
	var html = "";
	var tempText = "";
	var cnt = 0;
	for(var i=0; i<str.length; i++) {
		switch (i) {
		  case 0 : tempText = "일"; break;
		  case 1 : tempText = "월"; break;
		  case 2 : tempText = "화"; break;
		  case 3 : tempText = "수"; break;
		  case 4 : tempText = "목"; break;
		  case 5 : tempText = "금"; break;
		  case 6 : tempText = "토"; break;
		}
		
		if(str.charAt(i) == "1") {
			if(cnt > 0) {
				html += "," + tempText;
			} else {
				html += tempText;
			}
			cnt++;
		}
	}
	return html;
}

//시간목록
function timeList(str) {
	var html = "";
	for(var i=0; i<24; i++) {
		html += "<option value='"+i+"'>"+i+"</option>"
	}
	$("#"+str).append(html);
}

function timeListHtml() {
	var html = "";
	for(var i=0; i<24; i++) {
		if(i < 10) {
			html += "<input type='checkbox' name='chkTime' value='0"+i+"'/>&nbsp;0"+i+"&nbsp;&nbsp;&nbsp;";
		} else {
			html += "<input type='checkbox' name='chkTime' value='"+i+"'/>&nbsp;"+i+"&nbsp;&nbsp;&nbsp;";
		}
		if(i == 12) {
			html += "<br/><br/>";
		}
	}
	return html;
}

function timeListHwp(str) {
	var html = "";
	var tempText = "";
	var stHr = "";
	var enHr = "";
	var cnt = 0;
	for(var i=0; i<str.length; i++) {
		if(i < 10) {
			tempText = "0"+i;
		} else {
			tempText = i+"";
		}
		if(i == str.length-1) {
			if(str.charAt(i) == "1") {
				if(stHr == "") {
					if(cnt > 1) {
						html += "<br/>"+tempText + "~" + tempText;
					} else {
						html += tempText + "~" + tempText;
					}
				} else {
					html += "~"+tempText;
				}
			}
		} else {
			if(str.charAt(i) == "1") {
				if(stHr == "") {
					if(cnt > 0) {
						html += "<br/>"+tempText;
						stHr = tempText;
						cnt = 0;
					} else {
						html += tempText;
						stHr = tempText;
					}
					enHr = "";
				} else {
					enHr = tempText;
				}
			} else {
				if(stHr != "") {
					stHr = "";
					html += "~"+enHr;
					cnt++;
				}
			}
		}
	}
	return html;
}

//daum 우편번호 조회
function execDaumPostcode(str1, str2, str3) {
	//str1 : 우편번호 필드 ID, str2 : 주소 필드 ID, str3 : 상세주소 필드 ID
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $("#"+str1).val(data.zonecode); //5자리 새우편번호 사용
            $("#"+str2).val(fullAddr);

            // 커서를 상세주소 필드로 이동한다.
            $("#"+str3).focus();
        }
    }).open();
}
//다음우편번호
function sample5_execDaumPostcode(type) {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수
            // 주소 정보를 해당 필드에 넣는다.
           // 상세주소
           var  geocoder = new daum.maps.services.Geocoder();
            geocoder.addressSearch(data.address, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {
                    var result = results[0]; //첫번째 결과의 값을 활용
                    var addrObject=[];
                	addrObject.x_coor = result.x;
                	addrObject.y_coor = result.y;
                	addrObject.title = result.address_name;
                	addrObject.adres = result.address_name;
                	
                    if(type == "S"){
                    	$recptSttus.startAddrObject = addrObject;
                        $("#startAddr").val(addrObject.title);
                        $("#startFullAddr").val(addrObject.adres);
                    }else {
                    	$recptSttus.endAddrObject = addrObject;
                        $("#endAddr").val(addrObject.title);
                        $("#endFullAddr").val(addrObject.adres);
                    }
                }
            });
        }
    }).open();
}

//input number maxLength 체크
function maxLengthCheck(object) {
	var temp = object.value;
	if(isNaN(temp) == true) {
		alert("숫자만 입력해주세요!");
		object.value = "";
	}
	if(object.value.length > object.maxLength) {
		object.value = object.value.slice(0, object.maxLength);
	}
}
//input 년도 숫자만 입력
function chkYearNum(object) {
	object.value = object.value.replace(/[^0-9]/g,"");
	if(object.value.indexOf("0") == 0 && object.value.length > 1)
		object.value="0";
}
//input number만 입력
function chkNum(object) {
	object.value = object.value.replace(/[^0-9]/g,"");
}
function changeChkTime(str1, str2) {
	var temp = str2.value;
	if(isNaN(temp) == true) {
		alert("숫자만 입력해주세요!");
		str2.value = "";
		str2.focus();
	}
	
	if(str1 == "H") {
		if(temp > 23) {
			alert("예약시간(시)은 00~23으로 입력해주세요!");
			str2.focus();
		}
	} else {
		if(temp > 59) {
			alert("예약시간(분)은 00~59으로 입력해주세요!");
			str2.focus();
		}
	}
}

function paging(obj, str) {
	var pageSize = 10;
	if(str == "galleyList") {
		pageSize = 8;
	} else if(str == "userSearchList") {
		pageSize = 5;
	} else if(str == "commonCDMnglistA") {
		pageSize = 6;
	} else if(str == "commonCDMnglistB") {
		pageSize = 6;
	} else if(str == "searchAddrList") {
		pageSize = 15;
	} 
	
	var totalPage = Math.ceil(obj.totalCnt / pageSize);
	var totalPageA = Math.ceil(obj.totalCntA / pageSize);
	var totalPageB = Math.ceil(obj.totalCntB / pageSize);
	
	if (obj.totalCnt >= 1){
		$('.paging').paging({
			current : obj.currentPageIndex,
			max : totalPage,
			itemClass : 'page',
			itemCurrent : 'current',
			format : '{0}',
			next : '&gt;',
			prev : '&lt;',
			first : '&lt;&lt;',
			last : '&gt;&gt;',
			onclick : function(e, page) {
				obj.currentPageIndex = page;
				obj.list(page);
			}
		});
	}
	
	if(str == "commonCDMnglistA") {
		if (obj.totalCntA >= 1){
			$('#pagingA').paging({
				current : obj.currentPageIndexA,
				max : totalPageA,
				itemClass : 'page',
				itemCurrent : 'current',
				format : '{0}',
				next : '&gt;',
				prev : '&lt;',
				first : '&lt;&lt;',
				last : '&gt;&gt;',
				onclick : function(e, page) {
					obj.currentPageIndexA = page;
					obj.listA(page);
				}
			});
		}
	}
	
	if(str == "commonCDMnglistB") {
		if (obj.totalCntB >= 1){
			$('#pagingB').paging({
				current : obj.currentPageIndexB,
				max : totalPageB,
				itemClass : 'page',
				itemCurrent : 'current',
				format : '{0}',
				next : '&gt;',
				prev : '&lt;',
				first : '&lt;&lt;',
				last : '&gt;&gt;',
				onclick : function(e, page) {
					obj.currentPageIndexB = page;
					obj.listB(page, $("#CD_ID").val());
				}
			});
		}
	}
	if(str == "searchAddrList") {
		if (obj.totalCntA >= 1){
			$('#receipForm4 .paging').paging({
				current : obj.currentPageIndexA,
				max : totalPageA,
				itemClass : 'page',
				itemCurrent : 'current',
				format : '{0}',
				next : '&gt;',
				prev : '&lt;',
				first : '&lt;&lt;',
				last : '&gt;&gt;',
				onclick : function(e, page) {
					obj.currentPageIndexA = page;
					obj.searchAddrPage(page);
				}
			});
		}
	}
}

//checkbox all
function checkAll(form){
    var cbox = form.chk;
    if(cbox.length) {  // 여러 개일 경우
        for(var i = 0; i<cbox.length;i++) {
            cbox[i].checked=form.all.checked;
        }
    } else { // 한 개일 경우
        cbox.checked=form.all.checked;
    }
}

//checkbox val Checked
function checkedVal(str1, str2){
	var cbox = document.getElementsByName(str1);
    if(cbox.length) {  // 여러 개일 경우
        for(var i = 0; i<cbox.length;i++) {
        	if(str2.charAt(i) == "1") {
        		cbox[i].checked = true;
        	}
        }
    }
}

//checkbox value return
function checkReturnVal(str){
	var chklen = document.getElementsByName(str);
	var result_array = new Array;
	
	// 체크박스 갯수만큼 for 문을 돌려 체크된 넘의 value값을 가져온다.
 	for(i=0; i<chklen.length; i++){
			if(chklen[i].checked == true) {
				result_array.push(chklen[i].value);
	    }
 	}
 	return result_array;
}

(function(W, D) {
	//"use strict";
	W.common = W.$common || {};
	$(document).ready(function() {
		
	});
	
	$common = {
		totalCnt : 0,
		currentPageIndex : 1,
		// common API 호출 :회원 조회 팝업
		list : function(page) {
			$("#paging2").empty();
			var senders = new tscp.userSearch.api();
			senders.addParam("page", page);
			senders.addParam("USER_GBN_CD", $("#USER_GBN_CD_POP").val());
			if($("#NAME_POP").val() != "") {
				senders.addParam("NAME", $("#NAME_POP").val());
			}
			if($("#USER_ID_POP").val() != "") {
				senders.addParam("USER_ID", $("#USER_ID_POP").val());
			}
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/common/userSearch.json"
			});
		},
		attachFileFieldAdd : function(str1, str2, str3, str4){
			var html = "";
			html +="<input type='file' name='attachFiles[]' onchange=\"$common.itemAdd(this,"+str1+","+str2+",\'"+str3+"\',\'"+str4+"\')\">";
			$("#attachFileZone").append(html);
			$("input[name='attachFiles[]']:last").trigger("click");
		},
		attachFileValidate : function(){
			//추가 할때 value가 비어 있는 필드가 혹여 있는지 검사
			var attachFiles = $("input[name='attachFiles[]']");
			for(var i = 0; i < attachFiles.length; i++){
				if($(attachFiles[i]).val() ==""){
					$(attachFiles[i]).remove();
				}
			}
		},
		itemAdd : function(obj, str1, str2, str3, str4){
			var tempText = "";
			if(str4 == "A") {
				tempText = "\n정보컨텐츠 관리에서 설정할 수 있습니다.";
			}
			if($("#attachNameUl").children("li").size()+1 > str1) {
				alert("최대첨부파일 개수는 "+str1+"입니다."+tempText);
				$common.attachFileRemove(obj);
				return false;
			} else if($(obj)[0].files[0].size > str2*1000) {
				alert("파일최대용량은 "+str2+"kb입니다."+tempText);
				$common.attachFileRemove(obj);
				return false;
			}
			var fileValue = $(obj).val().split('\\').pop();
			if(fileAddExtCheck(fileValue, str3)) {
				var html = "<li>";
				html +=fileValue;
				html +="</li>";
				$("#attachNameUl").append(html);
				
				if($("#ATCHMNFL_REAL_NM").val() != undefined){
					$("#ATCHMNFL_REAL_NM").val($("#attachNameUl").html().split("<li>")[1].split("</li>")[0]);
					$("#attachNameUl").html('');
				}
			} else {
				alert("파일은 확장자("+str3+")만 등록 가능합니다."+tempText);
				$common.attachFileRemove(obj);
				return false;
			}
		},
		attachFileRemove : function(obj){
			$common.attachFileValidate(); // 짝 맞추기
			var delIdx = $(obj).parents("li").eq(0).index(); //attachNameUl			
			$('input[name="attachFiles[]"]').eq(delIdx).remove();
			$(obj).parents("li").eq(0).remove();
		},
		fileDown : function(gubn,saveName,name){
			var url = "/ServiceAPI/mngr/fileDown.download";
			
			var target = $('body');
			
	        target.prepend("<form id='fileDownLoad'></form>");
	        target = $('#fileDownLoad');
	        target.attr("method", "post");
	        target.attr("style", "top:-3333333333px;");
	        target.attr("action", url);
	        target.append("<input type='hidden' name='gubn' value='"+gubn+"'>");
	        target.append("<input type='hidden' name='saveName' value='"+encodeURIComponent(saveName)+"'>");
	        target.append("<input type='hidden' name='name' value='"+name+"'>");
	        $('#fileDownLoad').submit();
	        $('#fileDownLoad').remove();
		},
	};
	
	/** ********* 회원정보조회팝업 Start ********* */
	(function() {
		$class("tscp.userSearch.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res) {
				$("#userSearchTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='20' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='70' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>회원ID</th>";
				html += "<th>이름</th>";
				html += "<th>휴대폰</th>";
				html += "</tr>";

				if(result.userSearchList.length == 0) {
					html += "<tr><td colspan='4'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.userSearchList.length; i++) {
						html += "<tr style='cursor:pointer;' onclick=\"javascript:parentValMove(\'"+result.userSearchList[i].USER_ID+"\',\'"+result.userSearchList[i].NAME+"\');\">";
						html += "<td>"+result.userSearchList[i].rnum+"</td>";
						html += "<td>"+result.userSearchList[i].USER_ID+"</td>";
						html += "<td>"+result.userSearchList[i].NAME+"</td>";
						html += "<td>"+result.userSearchList[i].MOBILE+"</td>";
						html += "</tr>";
					}
				}
				
				$("#userSearchTable").html(html);
				$common.totalCnt = result.userSearchListCount[0].CNT;
				paging($common,"userSearchList");
			},
			onFail : function(status) {
			}
		});
	}());
	/** ********* 회원정보조회팝업 END ********* */
}(window, document));

function changeFamilyBox(str) {
	var newWindow = window.open("about:blank");
	newWindow.location.href = str; 
}

function checkReturnValBinary(str){
	var html = "";
	var chklen = document.getElementsByName(str);
	for(i=0; i<chklen.length; i++){
		if(chklen[i].checked == true) {
			html += "1";
		} else {
			html += "0";
		}
	}
 	return html;
}

function fileAddExtCheck(str1, str2) {
	var cnt = 0;
	//str1 파일명, str2 확장자체크
	var array = new Array();
	str1 = str1.slice(str1.indexOf(".")+1).toLowerCase();
	array = str2.trim().split(",");
	for(var i=0; i<array.length; i++) {
		if(str1 == array[i]) {
			cnt++;
		}
	}
	
	if(cnt > 0) {
		return true;
	} else {
		return false;
	}
}