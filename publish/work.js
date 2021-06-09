var work = [  
	/*메인*/
	{ "cate":"m01", "dep01":"메인", "dep02":"메인", "url":"./html/main/main.html", "date":"2017-02-19" },  
	{ "cate":"m01", "dep01":"메인", "dep02":"로그인", "url":"./html/main/login.html", "date":"2017-02-19" },  
	{ "cate":"m01", "dep01":"메인", "dep02":"약관동의", "url":"./html/main/agree.html", "date":"2017-02-19" },  
	{ "cate":"m01", "dep01":"메인", "dep02":"정보입력", "url":"./html/main/join01.html", "date":"2017-02-19" },  
	{ "cate":"m01", "dep01":"메인", "dep02":"가입완료", "url":"./html/main/join02.html", "date":"2017-02-19" },  

	/*센터소개*/
	{ "cate":"m02", "dep01":"센터소개", "dep02":"인사말", "url":".//html/about/greetings.html", "date":"2017-02-19" },  
	{ "cate":"m02", "dep01":"센터소개", "dep02":"조직도 & 연혁", "url":".//html/about/history.html", "date":"2017-02-19" },  
	{ "cate":"m02", "dep01":"센터소개", "dep02":"찾아오시는길", "url":".//html/about/location.html", "date":"2017-02-19" },  

	/*관리자관리*/
	{ "cate":"m03", "dep01":"관리자관리", "dep02":"공통코드관리", "url":"./html/admin/codeList.html", "date":"2017-02-19" },    
	{ "cate":"m03", "dep01":"관리자관리", "dep02":"설문조사 목록", "url":"./html/admin/pollList.html", "date":"2017-02-19" },    
	{ "cate":"m03", "dep01":"관리자관리", "dep02":"설문조사 상세", "url":"./html/admin/pollView.html", "date":"2017-02-19" },    
	{ "cate":"m03", "dep01":"관리자관리", "dep02":"설문조사 등록", "url":"./html/admin/pollWrite.html", "date":"2017-02-19" },    

	/*기본정보관리*/
	{ "cate":"m04", "dep01":"기본정보관리", "dep02":"이용자 정보 목록", "url":"./html/basicInfo/userList.html", "date":"2017-02-19" },     

	
	/*배차관리*/
	{ "cate":"m05", "dep01":"배차관리", "dep02":"배차 목록", "url":"./html/dispatch/baechaList.html", "date":"2017-02-19" },    
	{ "cate":"m05", "dep01":"배차관리", "dep02":"예약 현황", "url":"./html/dispatch/bookingList.html", "date":"2017-02-19" },    
	

	/*이용안내*/
	{ "cate":"m06", "dep01":"이용안내", "dep02":"차량 이용안내", "url":"./html/guide/carGuide.html", "date":"2017-02-19" },
	{ "cate":"m06", "dep01":"이용안내", "dep02":"차량 보유현황", "url":"./html/guide/presence.html", "date":"2017-02-19" },

	/*실시간위치*/
	{ "cate":"m07", "dep01":"실시간위치", "dep02":"위치관리1", "url":"./html/location/gpsInfo.html", "date":"2017-02-19" },
	{ "cate":"m07", "dep01":"실시간위치", "dep02":"위치관리2", "url":"./html/location/gpsInfo_find.html", "date":"2017-02-19" },

	/*민원광장*/
	{ "cate":"m08", "dep01":"민원광장", "dep02":"Q&A 목록", "url":"./html/minwon/qnaList.html", "date":"2017-02-19" },
	{ "cate":"m08", "dep01":"민원광장", "dep02":"Q&A 상세", "url":"./html/minwon/qnaView.html", "date":"2017-02-19" },
	{ "cate":"m08", "dep01":"민원광장", "dep02":"Q&A 등록", "url":"./html/minwon/qnaWrite.html", "date":"2017-02-19" },

	/*정보광장*/
	{ "cate":"m09", "dep01":"정보광장", "dep02":"포토갤러리 목록", "url":"./html/plaza/photoList.html", "date":"2017-02-19" },
	{ "cate":"m09", "dep01":"정보광장", "dep02":"포토갤러리 상세", "url":"./html/plaza/photoView.html", "date":"2017-02-19" },
	{ "cate":"m09", "dep01":"정보광장", "dep02":"포토갤러리 등록", "url":"./html/plaza/photoWrite.html", "date":"2017-02-19" },

	/*통계관리*/
	{ "cate":"m010", "dep01":"통계관리", "dep02":"운행 현황 통계", "url":"./html/statistics/raceList.html", "date":"2017-02-19" }
	
	
	
];

 
$(function(){   
	listTable(".siteNavi li", ".siteNavi li .num");
}); 
 
function listTable(cls, num){   
	var tr;
	for(i=0; i<work.length; i++){
		tr += "<tr class="+work[i].cate+">";
		tr += "<td>"+work[i].dep01+"</td>";
		tr += "<td>"+work[i].dep02+"</td>";
		tr += "<td><a href='./"+work[i].url+"' target='_blank'>"+work[i].url+"</a></td>";
		tr += "<td class='ac'>"+work[i].date+"</td>";
		tr += "</tr>"; 
	}  
	$("table tbody").append(tr);  
	
	$(num).each(function(z){
		if(z===0){
			$(num).eq(z).text("("+work.length+"p)");
		}else{
			$(num).eq(z).text("("+$('.m0'+z).length+"p)");
		} 
	}); 
	$("body").on("click",cls, function(){
		$(cls).removeClass("on"); 
		$(this).addClass("on");
		$("table tbody tr").hide();
		if($(this).index() === 0){
			$("table tbody tr").show();
		}else{
			$(".m0"+$(this).index()).show();
		} 
	});  
}  