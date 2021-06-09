<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String sessionId = (String) session.getAttribute("sessionId");
    String user_id = (String) session.getAttribute("user_id");
    String cnter_tel = (String) session.getAttribute("cnter_tel");
    HttpSession http_session = request.getSession();
%>
<script type="text/javascript">
    var session = {
        sessionId: '<%=sessionId %>',
        user_id: '<%=user_id %>',
        cnter_tel: '<%=cnter_tel %>',
    };

    //menuList();
</script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="user-scalable=yes, width=1330px"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>이동지원센터</title>
    <script type="text/javascript" src="/js/common/includeSource.js"></script>
<%--    <script type="text/javascript"  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0d275dc10b1bb95131478c3be6433bd6&libraries=services"></script>--%>
<%--    <script type="text/javascript" src="/js/tscp/cntrl/map.js"></script>--%>
    <script type="text/javascript" src="/js/tscp/main.js"></script>
</head>
<body>
<div class="wrapper">
    <!-- header // -->
    <jsp:include page="/view/common/includeHeader"></jsp:include>
    <!-- container -->
    <div class="container">
        <div class="mainVisual">
            <div class="rela">
                <div class="mainVisualSlide">
                    <div class="item">
                        <img src="/img/etc/etc_mainSlide01.jpg"/>
                        <!-- < <p class="t01">「이동불편 교통체계 개선기술 개발」</p> -->
                        <p class="t02"> 전라북도 광역 이동지원센터 <br/>방문을 환영합니다.</p>
                        <!-- <p class="t03">본 시스템은 특별교통수단(장애인콜택시)의 효율적인 운영과 이용자에게 <br/>보다 나은 서비스를 제공하기 위한 신규 개발 시스템으로써,<br/> 전라북도 전주시와 완주군을 대상으로 시험서비스를 실시하고 있습니다.</p>
                         -->
                        <!-- 						<ul>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=cnterInfo101">
                                                            <img src="/img/ico/ico_serviceList01.png" />
                                                            <span>센터소개</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=useInfo201">
                                                            <img src="/img/ico/ico_serviceList02.png" />
                                                            <span>차량이용안내</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=useInfo202">
                                                            <img src="/img/ico/ico_serviceList03.png" />
                                                            <span>차량보유현황</span>
                                                        </a>
                                                    </li>
                                                </ul> -->
                        <div class="appDownloadArea" style="position: absolute;height: 200px;width: 500px;top:200px;padding:20px 0;">
                            <div style="height:130px;">
                                <div style="float:left;width:350px;">
                                    <span style="font-weight: bold;color:#545fde;font-size:20px;">전라북도 광역이동지원센터 앱</span>
                                    <span style="font-size:18px;color:#fff;">을 다운로드하면 특별교통수단을 편하게 예약할 수 있습니다.</span><br/>
                                    <span style="font-size:18px;color:#fff;">지금 체험해보세요.</span>
                                </div>
                                <img src="/img/etc/etc_qrcode.png" style="width:100px;float:right;"/>
                            </div>
                            <div class="downloadLinkArea" style="height:150px;">
                                <img src="/img/etc/etc_download01.jpg" style="cursor:pointer;margin-right: 10px;" onclick="javascript:window.open('https://apps.apple.com/kr/app/%EC%A0%84%EB%9D%BC%EB%B6%81%EB%8F%84-%EA%B4%91%EC%97%AD%EC%9D%B4%EB%8F%99%EC%A7%80%EC%9B%90%EC%84%BC%ED%84%B0-%EC%8A%B9%EA%B0%9D%EC%9A%A9%EC%95%B1/id1484432679', '_blank')"/>
                                <img src="/img/etc/etc_download02.jpg" style="cursor:pointer;" onclick="javascript:window.open('https://play.google.com/store/apps/details?id=kr.go.jeonbuk.stc.handicap.user', '_blank')"/>
                            </div>
                        </div>
                    </div>
                    <div class="item">
                        <img src="/img/etc/etc_mainSlide01.jpg"/>
                        <p class="t01">당신의 아름다운 삶을 함께할 </p>
                        <p class="t02">이동지원센터는 여러분의 이동 서비스를<br/>제공합니다. </p>
                        <p class="t03">고객 실험 서비스는 건설 대형실험시설을 쳬계적으로 운영하고
                            <br/>있는 국토교통연구인프라운영원의 실험시험 시스템으로 의뢰자 모두가
                            <br/>편리하게 이용할 수 있도록 최상의 서비스를 제공합니다.</p>
                        <!-- 						<ul>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=cnterInfo101">
                                                            <img src="/img/ico/ico_serviceList01.png" />
                                                            <span>센터소개</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=useInfo201">
                                                            <img src="/img/ico/ico_serviceList02.png" />
                                                            <span>차량이용안내</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="/view/userSite.do?gubn=useInfo202">
                                                            <img src="/img/ico/ico_serviceList03.png" />
                                                            <span>차량보유현황</span>
                                                        </a>
                                                    </li>
                                                </ul> -->
                    </div>
                </div>
                <!-- <div class="paging">
                    <a href="#" class="play">play</a>
                    <a href="#" class="num on">1</a>
                    <a href="#" class="num">2</a>
                </div> -->

            </div>
        </div>

        <div class="mainQuickList">
            <ul>
                <li>
                    <a href="/view/carUseResve/rceptSttus.do">
                        <img src="/img/ico/ico_quickList01_n.png"/>
                        <span>즉시콜</span>
                    </a>
                </li>
                <li>
                    <a href="/view/memberShipAgree.do">
                        <img src="/img/ico/ico_quickList02_n.png"/>
                        <span>회원가입</span>
                    </a>
                </li>
                <li>
                    <a href="/view/userSite.do?gubn=cnterInfo104">
                        <img src="/img/ico/ico_quickList03_n.png"/>
                        <span>센터위치</span>
                    </a>
                </li>
                <li>
                    <a href="/view/userSite.do?gubn=useInfo203">
                        <img src="/img/ico/ico_quickList04_n.png"/>
                        <span>이용안내</span>
                    </a>
                </li>
                <li>
                    <a href="/view/userSite.do?gubn=infoSqare401">
                        <img src="/img/ico/ico_quickList05_n.png"/>
                        <span>공지사항</span>
                    </a>
                </li>
                <li>
                    <a href="/view/userSite.do?gubn=cmttSqare501">
                        <img src="/img/ico/ico_quickList06_n.png"/>
                        <span>Q&A</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)" id="bookMarkBtn">
                        <img src="/img/ico/ico_quickList07_n.png"/>
                        <span>즐겨찾기 추가</span>
                    </a>
                </li>
            </ul>
        </div>


        <div class="mainCont">
            <div class="customerBox">
                <%-- <%if(user_id != null){%>
                    <p class="t01">고객 만족 센터</p>
                    <p class="t02">서비스에 대한 문의사항이 있으면 고객 만족 센터로 연락주세요</p>
                    <p class="t03">대표번호</p>
                    <p class="t04" id="cnter_tel"><%= cnter_tel %></p>
                <%}else{ %> --%>
                <p class="t01">고객 만족 센터</p>
                <p class="t02" style="margin-top:10px;">서비스에 대한 문의사항이 있으면 연락주세요</p>
                <p class="t03">전라북도 대표번호</p>
                <p class="t04" id="cnter_tel">063-227-0002</p>

                <%--KOBUS_테스트--%>
                <%--<a id="KOBUS_TEST" style="cursor: pointer;">KOBUS_링크테스트</a>--%>

                <%-- <%}%> --%>
            </div>

            <div class="latestBox">
                <ul class="tabs">
                    <li class="on"><a href="javascript:void(0)">공지사항</a></li>
                    <li><a href="javascript:void(0)">보도자료</a></li>
                    <li><a href="javascript:void(0)">질문답변</a></li>
                    <li><a href="javascript:void(0)" class="more">더보기</a></li>
                </ul>
                <div class="cont" id="boardData">
                    <ul class="list01">
                        <!-- 						<li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">2017년 01월 시간대별 이용현황 및 대기 시간</a>
                                                    <span>2017.0308</span>
                                                </li> -->
                    </ul>
                </div>
            </div>

            <div class="mainSlideArea">
                <div class="mainSlideBox">
                    <div class="item">
                        <img src="/img/etc/etc_slideList01.png"/>
                        <p class="t01">광역 이동지원센터 서비스</p>
                        <p class="t02">전라북도 교통약자를 위한<br/>이동 서비스를 제공합니다.</p>
                        <!-- <a href='/view/userSite.do?gubn=cnterInfo101'>자세히 보기</a> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end  container -->

    <!-- footer// -->
    <jsp:include page="/view/common/includeFooter"></jsp:include>
</div><!-- end cls:wrapper-->

<div class="dialog" id="noticePopup" style="display:block">
    <div class="pop">
        <div class="pHeader">
            <span>${noticeList.MENU_NAME } 상세</span>
            <a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img
                    src="/img/btn/btn_close01.png"/></a>
        </div>
        <div class="pCont">
            <form id="noticeForm" method="post" enctype="multipart/form-data">
                <div class="pActicle">
                    <p class="etcTitle" id="val1"></p>
                    <table class="sTable01 ptype">
                        <colgroup>
                            <col width="100"/>
                            <col width=""/>
                            <col width="100"/>
                            <col width=""/>
                            <col width="100"/>
                            <col width=""/>
                        </colgroup>
                        <tr>
                            <th>작성자</th>
                            <td id="val2"></td>
                            <th>작성일</th>
                            <td id="val3"></td>
                            <th>조회</th>
                            <td id="val4"></td>
                        </tr>
                        <tr>
                            <td class="cont" colspan="6">
                                <div class="contText" id="val5" style="padding-top:10px;padding-bottom:10px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <th>첨부파일 다운로드</th>
                            <td colspan="5">
                                <div class="fc">
                                    <ul id="downloadUl"></ul>
                                </div>
                            </td>
                        </tr>
                        </dl>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="dialog" id="replyPopup" style="display:block;">
    <div class="pop">
        <div class="pHeader">
            <span>댓글작성</span>
            <a href="javascript:void(0); document.getElementById('noticeForm').reset();" class="popClose"><img
                    src="/img/btn/btn_close01.png"/></a>
        </div>
        <div class="pCont">
            <form id="replyForm">
                <input type="hidden" name="NOTICE_NO" id="NOTICE_NO" value="10"/>
                <input type="hidden" name="CNTNTS_TYPE2_CD2" id="CNTNTS_TYPE2_CD2"
                       value="${contentsInfo.CNTNTS_TYPE2_CD }"/>
                <input type="hidden" name="NOTICE_CLS_CD2" id="NOTICE_CLS_CD2" value="${contentsInfo.CNTNTS_CLS_CD }"/>
                <input type="hidden" name="U_CNTNTS_NO" id="U_CNTNTS_NO" value="${contentsInfo.CNTNTS_NO }"/>
                <input type="hidden" name="CNTNTS_NO" id="CNTNTS_NO2" value="${contentsInfo.CNTNTS_NO }"/>

                <table class="sTable01 ptype">
                    <colgroup>
                        <col width="100"/>
                        <col width=""/>
                        <col width="100"/>
                        <col width=""/>
                        <col width="100"/>
                        <col width=""/>
                    </colgroup>
                    <tr>
                        <th>작성자</th>
                        <td id="val11"></td>
                        <th>작성일</th>
                        <td id="val12"></td>
                        <th>조회</th>
                        <td id="val13"></td>
                    </tr>
                    <tr>
                        <td class="cont" colspan="6">
                            <div class="contText" id="val14"></div>
                        </td>
                    </tr>
                </table>

                <dl class="replydl"></dl>

                <!-- 					<table class="writeTable mt0" id="replayArea">  -->
                <%-- 						<colgroup> --%>
                <%-- 							<col width="100"> --%>
                <%-- 							<col width=""> --%>
                <%-- 							<col width="150"> --%>
                <%-- 							<col width=""> --%>
                <%-- 						</colgroup>  --%>
                <!-- 						<tr>  -->
                <!-- 							<td class="al">내용</td> -->
                <!-- 							<td colspan="3"> -->
                <!-- 								<textarea class="reply" name="replayCtnt" id="replayCtnt"></textarea> -->
                <!-- 								<a href="javascript:void(0);" id="replyAdd" class="btn04">댓글입력</a> -->
                <!-- 							</td>  -->
                <!-- 						</tr>  -->
                <!-- 					</table> -->
            </form>
        </div>
    </div>
</div>

<div class="mainPopup" id="popupNotice" style="display: none;">
    <input type="hidden" name="popupData[]" id="popupData[]" value="1,3032">
    <a href="javascript:void(0)" id="popupNoticeImg">
        <img src="/img/etc/notice_popup_01.png" alt="공지사항">
    </a>
    <div>
				<span>
					<input type="checkbox" id="chEvent" name="chEvent">
					<label for="chEvent">오늘하루 이창을 열지않음</label>
				</span>
        <span class="pop-close" id="pop-close" data-popidx="3032"></span>
    </div>
</div>

<div class="mainPopup" id="popupNotice02" style="display: none; left: 623px">
    <a href="javascript:void(0)" id="popupNoticeImg02">
        <img src="/img/etc/notice_popup_03.png" alt="공지사항2">
    </a>
    <div>
				<span>
					<input type="checkbox" id="chEvent02" name="chEvent02">
					<label for="chEvent02">오늘하루 이창을 열지않음</label>
				</span>
        <span class="pop-close" id="pop-close02" data-popidx="3032"></span>
    </div>
</div>


<%--<div class="pop" id="noticeDragPopup"--%>
<%--	 style="z-index: 50; width: 800px; right: auto; height: 600px; display:none;">--%>
<%--	<div class="pHeader">--%>
<%--		<span>공지사항</span> <a href="javascript:void(0)" onclick=$main.closeNoticePopup()><img src="/img/btn/btn_close01.png" /></a>--%>
<%--	</div>--%>
<%--	<div class="pCont" style="width: 796px; height: 406px;">--%>
<%--		<form id="noticeForm2" method="post" enctype="multipart/form-data">--%>
<%--			<div class="pActicle">--%>
<%--				<p class="etcTitle" id="value1"></p>--%>
<%--				<table class="sTable01 ptype">--%>
<%--					<colgroup>--%>
<%--						<col width="100"/>--%>
<%--						<col width=""/>--%>
<%--					</colgroup>--%>
<%--					<tr>--%>
<%--						<td class="cont" colspan="6">--%>
<%--							<div class="contText" id="value5" style="padding-top:10px;padding-bottom:10px;"></div>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--					<tr>--%>
<%--						<th>첨부파일 다운로드</th>--%>
<%--						<td colspan="5">--%>
<%--							<div class="fc">--%>
<%--								<ul id="downloadUl01"></ul>--%>
<%--							</div>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--					</dl>--%>
<%--				</table>--%>
<%--                    <div id="todayChceckbox" style="padding-top: 10px">--%>
<%--                        <form name="todayCheckform">--%>
<%--                        <input type="checkbox" id="chEvent" name="chEvent" class="input_chk">--%>
<%--                        <checklabel for="">오늘 하루 열지 않습니다.</checklabel>--%>
<%--&lt;%&ndash;                        <a href="javascript:void(0)" onclick="layerClose('#noticeDragPopup')">[닫기]</a>&ndash;%&gt;--%>
<%--                        </form>--%>
<%--                    </div>--%>
<%--            </div>--%>
<%--		</form>--%>
<%--	</div>--%>
<%--</div>--%>

<%--KOBUS_테스트--%>
<%--<form id="RESVE_INFO" method="post" action="http://localhost:8080/view/reservation" target="popup">--%>
<%--<input type='hidden' name="tel_no" value="" />--%>
<%--<input type='hidden' name="resve_no" value="" />--%>
<%--<input type='hidden' name="run_type" value="" />--%>
<%--<input type='hidden' name="boarding_time" value="" />--%>
<%--<input type='hidden' name="require_time" value="" />--%>
<%--</form>--%>

</body>
</html> 