/**
 * 접수현황 메소드
 *
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see :
 *
 */
(function (W, D) {

    //"use strict";
    W.recptSttus = W.$recptSttus || {};
    $(document).ready(function () {

        var status = $("#STATUS").val();

        if (status == "FAIL"){
            location.href = contextPath + "/view/login/";
            alert("사용자 정보가 없습니다.");
            return
        }

        var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
        var user_id = session.user_id;
        var cnter_cd = session.cnter_cd;
        var telno = session.cnter_tel;
        var resve_no = $("#RESVE_NO").val();
        var run_type = $("#RUN_TYPE").val();
        var boarding_time = $("#BOARDING_TIME").val();
        var require_time = $("#REQUIRE_TIME").val();

        if (varUA.match('android') != null) {
            location.href = 'handicapped://kr.co.neighbor.handicapped.handicappedformoving?resve_no=' + resve_no
                + "&run_type=" + run_type
                + "&boarding_time=" + boarding_time
                + "&require_time=" + require_time
                + "&user_id=" + user_id
                + "&cnter_cd=" + cnter_cd
                + "&telno=" + telno;
            return;
        } else if (varUA.indexOf("iphone")>-1||varUA.indexOf("ipad")>-1||varUA.indexOf("ipod")>-1) {
            if(confirm("'콜택시 시범운영'앱을 통해 예약을 진행 하시겠습니까?")){
                location.href = "HandicappedForMoving://?resve_no=" + resve_no
                    + "&run_type=" + run_type
                    + "&boarding_time=" + boarding_time
                    + "&require_time=" + require_time
                    + "&user_id=" + user_id
                    + "&cnter_cd=" + cnter_cd
                    + "&telno=" + telno;
                return;
            }
        }

        // var dateTommorrow =new Date(new Date().setDate(new Date().getDate() +1));
        var dateTommorrow = new Date();
        
        $("#ST_DT").val($.datepicker.formatDate('yy-mm-dd', new Date()));
        $("#EN_DT").val($.datepicker.formatDate('yy-mm-dd', new Date(new Date().setDate(new Date().getDate() + 7))));

        $("#recptDate1").addClass("datepicker");
        $("#recptDate2").addClass("datepicker");
        $("#recptDate3").addClass("datepicker");
        $("#recptDate1, #recptDate2, #recptDate3").datepicker({
                dateFormat: "yy-mm-dd",
                showOn: "button",
                buttonImage: "../../img/ico/ico_calendar.png",
                buttonImageOnly: true,
                minDate: dateTommorrow
            }
        );
        $("#receipForm1 #plusHand").hide();
        $("#receipForm1 #minusHand").hide();
        $("#receipForm2 #plusHand").hide();
        $("#receipForm2 #minusHand").hide();

        var inKobus = $("#JOIN_TYPE").val();
        if (inKobus == "KOBUS"){
            $recptSttus.openBookPop();
        }

        //조회조건 selectbox 이벤트
        $("#CARALC_STTUS_CD").change(function () {
            $recptSttus.list(1);
        });

        $("#searchList").click(function () {
            $recptSttus.list(1);
        });

        //접수예약 팝업
        $("#receiptOpen").click(function () {
            $recptSttus.openBookPop();
        });

        //접수예약 등록
        $("#carReceiptAdd").click(function () {
            $recptSttus.recptSttusAdd();
        });

        //접수예약 취소
        $("#carReceiptDelete").click(function () {
            /*for (i = 0; i < str.length; i++) {
                if (str[i].split("||")[0] == "20") {
                    alert("에약이 이미 취소 된항목이 포함되어있습니다. 다시 한번 확인 바랍니다.")
                    return false;
                } else if (str[i].split("||")[0] >= 100) {
                    alert("승차중 이거나 하차완료 된 목록이 포함되어 있습니다. 다시한번 확인 바랍니다..")
                    return false;
                } else {
                    result_array.push(str[i].split("||")[1]);
                }
            }*/
        	
        	var str = checkReturnVal("chk");
            if(str.length !=1 ){
            	alert("하나의 예약건을 선택해주세요.")
                return false;
            }
            
            var resve_no = $("input[type='checkbox'][name='chk']:checked").val();
            var caralc_sttus_cd = $("input[type='checkbox'][name='chk']:checked").next().val()*1;
            
            if(caralc_sttus_cd == 20){
            	alert("이미 취소된 예약입니다. 다시 한번 확인 바랍니다.")
                return false;
            }else if(caralc_sttus_cd >= 70){
            	alert("이미 배차된 예약입니다. 다시 한번 확인 바랍니다.")
                return false;
            }
            
            $recptSttus.carReceiptDelete(resve_no);
        });

        //접수취소내역삭제
        $("#carReceiptAllDelete").click(function () {
            var resve_dt_list = new Array;
            if (confirm("취소된 예약의 내역을 일괄 삭제하시겠습니까?")) {
                $(".listTable").find("tr").each(function () {
                    if ($(this).attr("id") == "caralcList") {
                        var h = $(this).children("td").attr("class");
                        if (h.split("||")[0] == "20") {
                            resve_dt_list.push(h.split("||")[1]);

                        }
                    }
                });
                var senders = new tscp.carReceiptAllDelete.api();
                senders.addParam("RESVE_DT", resve_dt_list);
                senders.addParam("MBER_ID", session.user_id);
                senders.addParam("CNTER_CD", session.cnter_cd);
                senders.request({
                    method: "POST",
                    async: true,
                    url: contextPath + "/ServiceAPI/carUseResve/recptSttusAllDelete.json",
                });
            }
        });

        $('#searchWord').on('keypress', function (e) {
            if (e.which == 13) {
                $recptSttus.searchAddr(1);
            }
        });
        $('#searchWordBtn').on('click', function (e) {
            $recptSttus.searchAddr(1);
        });


        $("#receipForm4 #form4Close").click(function () {
            $("#receipForm" + $("#formNo").val()).show();

            $("#receipForm4").hide();
        });

        $(".searchAddress1, .searchAddress2, .searchAddress3").click(function () {
            var className = $(this).attr("class");
            $("#formNo").val(className.slice(className.length - 1, className.length));

            $("#searchAddrE").css("display","none");
        	$("#searchAddrS").css("display","none");
        	$("#searchAddr").css("display","block");
        	
            $("#inputNo").val(1);

            $("#receipForm" + $("#formNo").val()).hide();
            $("#receipForm4 #startAddr").val($("#receipForm" + $("#formNo").val() + " #DEPT_DESC1").val());
            $("#receipForm4 #endAddr").val($("#receipForm" + $("#formNo").val() + " #DEST_DESC1").val());
            $("#receipForm4 #searchWord").val("");
            $("#receipForm4 .paging").empty();
            $("#receipForm4 .listTable").html("<colgroup><col width='250'/><col width=''/><col width='250'/></colgroup><tr><th>명칭</th><th>주소</th><th>선택</th><tr name='noData'><td colspan='3'>지역검색 목록에 데이터가 없습니다. 주소검색으로 찾아주세요.</td></tr>");

            var formNo = $("#formNo").val();
            var inputNo = $("#inputNo").val();

            var addrObject = new Object();
            addrObject.title = $("#receipForm" + formNo + " #DEPT_DESC" + inputNo).val();
            addrObject.adres = $("#receipForm" + formNo + " #DEPT_FULL_DESC" + inputNo).val();
            addrObject.x_coor = $("#receipForm" + formNo + " #DEPT_X" + inputNo).val();
            addrObject.y_coor = $("#receipForm" + formNo + " #DEPT_Y" + inputNo).val();
            $recptSttus.startAddrObject = addrObject;

            addrObject = new Object();
            addrObject.title = $("#receipForm" + formNo + " #DEST_DESC" + inputNo).val();
            addrObject.adres = $("#receipForm" + formNo + " #DEST_FULL_DESC" + inputNo).val();
            addrObject.x_coor = $("#receipForm" + formNo + " #DEST_X" + inputNo).val();
            addrObject.y_coor = $("#receipForm" + formNo + " #DEST_Y" + inputNo).val();
            $recptSttus.endAddrObject = addrObject;

            $("#receipForm4").show();
        });

        $(".searchAddress4").click(function () {
            $("#formNo").val(3);
            $("#inputNo").val(2);

            $("#searchAddrE").css("display","none");
        	$("#searchAddrS").css("display","none");
        	$("#searchAddr").css("display","block");
        	
            $("#receipForm" + $("#formNo").val()).hide();
            $("#receipForm4 #startAddr").val($("#receipForm" + $("#formNo").val() + " #DEPT_DESC2").val());
            $("#receipForm4 #endAddr").val($("#receipForm" + $("#formNo").val() + " #DEST_DESC2").val());
            $("#receipForm4 #startFullAddr").val($("#receipForm" + $("#formNo").val() + " #DEPT_FULL_DESC2").val());
            $("#receipForm4 #endFullAddr").val($("#receipForm" + $("#formNo").val() + " #DEST_FULL_DESC2").val());
            $("#receipForm4 #searchWord").val("");
            $("#receipForm4 .paging").empty();
            $("#receipForm4 .listTable").html("<colgroup><col width='250'/><col width=''/><col width='250'/></colgroup><tr><th>명칭</th><th>주소</th><th>선택</th><tr><td colspan='3'>지역검색 목록에 데이터가 없습니다. 주소검색으로 찾아주세요.</td></tr>");

            var formNo = $("#formNo").val();
            var inputNo = $("#inputNo").val();

            var addrObject = new Object();
            addrObject.title = $("#receipForm" + formNo + " #DEPT_DESC" + inputNo).val();
            addrObject.adres = $("#receipForm" + formNo + " #DEPT_FULL_DESC" + inputNo).val();
            addrObject.x_coor = $("#receipForm" + formNo + " #DEPT_X" + inputNo).val();
            addrObject.y_coor = $("#receipForm" + formNo + " #DEPT_Y" + inputNo).val();
            $recptSttus.startAddrObject = addrObject;

            addrObject = new Object();
            addrObject.title = $("#receipForm" + formNo + " #DEST_DESC" + inputNo).val();
            addrObject.adres = $("#receipForm" + formNo + " #DEST_FULL_DESC" + inputNo).val();
            addrObject.x_coor = $("#receipForm" + formNo + " #DEST_X" + inputNo).val();
            addrObject.y_coor = $("#receipForm" + formNo + " #DEST_Y" + inputNo).val();
            $recptSttus.endAddrObject = addrObject;


            $("#receipForm4").show();
        });

        $("#nowDay").click(function () {
            form1Open();
        });

        $("#bookDay").click(function () {
            $("#transCheck").val("N");
            $("#transCheck").removeClass("on");
            form2Open();
        });

        $("#transCheck").click(function k() {
            var isOn = $(this).attr("class").indexOf("on");
            if (isOn < 0) {
                form3Open();
            } else {
                form2Open();
            }
        });


        $(".startAddrSet1, .startAddrSet2").click(function () {
            var formNo = $("#formNo").val();
            var inputNo = $(this).attr("class");
            var start = 'startAddrSet';
            var no = inputNo.slice(inputNo.indexOf(start) + start.length, inputNo.indexOf(start) + start.length + 1);
            $("#inputNo").val(no);
            var isOn = $(this).attr("class").indexOf("on");
            if (isOn < 0) {
                var index = $("#receipForm" + formNo + " .searchStartList" + no).val();
                var data = $recptSttus.addrStartHistList[index];
                
                $("#receipForm" + formNo + " #DEPT_DESC" + no).val(data.STRTPNT_ADRES);
                if(data.STRTPNT_FULL_ADRES == undefined || data.STRTPNT_FULL_ADRES == null || data.STRTPNT_FULL_ADRES == "")
                	$("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val(data.STRTPNT_ADRES);
                else
                	$("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val(data.STRTPNT_FULL_ADRES);
                $("#receipForm" + formNo + " #DEPT_X" + no).val(data.START_LC_CRDNT_X);
                $("#receipForm" + formNo + " #DEPT_Y" + no).val(data.START_LC_CRDNT_Y);

                var addrObject = new Object();
                addrObject.x_coor = data.START_LC_CRDNT_X;
                addrObject.y_coor = data.START_LC_CRDNT_Y;
                addrObject.title = data.STRTPNT_ADRES;
                if(data.STRTPNT_FULL_ADRES == undefined || data.STRTPNT_FULL_ADRES == null || data.STRTPNT_FULL_ADRES == "")
                	addrObject.adres = data.STRTPNT_ADRES;
                else
                	addrObject.adres = data.STRTPNT_FULL_ADRES;
                $recptSttus.startAddrObject = addrObject;
            } else {
                $("#receipForm" + formNo + " #DEPT_DESC" + no).val('');
                $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val('');
                $("#receipForm" + formNo + " #DEPT_X" + no).val('');
                $("#receipForm" + formNo + " #DEPT_Y" + no).val('');
                $recptSttus.startAddrObject = null;
            }

            checkAddress(formNo, no);

        });

        $(".endAddrSet1, .endAddrSet2").click(function () {
            var formNo = $("#formNo").val();
            var inputNo = $(this).attr("class");
            var end = 'endAddrSet';
            var no = inputNo.slice(inputNo.indexOf(end) + end.length, inputNo.indexOf(end) + end.length + 1);
            $("#inputNo").val(no);
            var isOn = $(this).attr("class").indexOf("on");
            if (isOn < 0) {
                var index = $("#receipForm" + formNo + " .searchEndList" + no).val();
                var data = $recptSttus.addrEndHistList[index];

                $("#receipForm" + formNo + " #DEST_DESC" + no).val(data.ALOC_ADRES);
                if(data.ALOC_FULL_ADRES == undefined || data.ALOC_FULL_ADRES == null || data.ALOC_FULL_ADRES == "")
                	$("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val(data.ALOC_ADRES);
                else
                	$("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val(data.ALOC_FULL_ADRES);
                $("#receipForm" + formNo + " #DEST_X" + no).val(data.ARVL_LC_CRDNT_X);
                $("#receipForm" + formNo + " #DEST_Y" + no).val(data.ARVL_LC_CRDNT_Y);
                var addrObject = new Object();
                addrObject.x_coor = data.ARVL_LC_CRDNT_X;
                addrObject.y_coor = data.ARVL_LC_CRDNT_Y;
                addrObject.title = data.ALOC_ADRES;
                if(data.ALOC_FULL_ADRES == undefined || data.ALOC_FULL_ADRES == null || data.ALOC_FULL_ADRES == "")
                	addrObject.adres = data.ALOC_ADRES;
                else
                	addrObject.adres = data.ALOC_FULL_ADRES;
                $recptSttus.endAddrObject = addrObject;
            } else {
                $("#receipForm" + formNo + " #DEST_DESC" + no).val('');
                $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val('');
                $("#receipForm" + formNo + " #DEST_X" + no).val('');
                $("#receipForm" + formNo + " #DEST_Y" + no).val('');
                $recptSttus.endAddrObject = null;
            }
            checkAddress(formNo, no);
        });

        $(".searchStartList1, .searchStartList2").change(function () {
        	
            var formNo = $("#formNo").val();
            var inputNo = $(this).attr("class");
            var start = 'searchStartList';
            var no = inputNo.slice(inputNo.indexOf(start) + start.length, inputNo.indexOf(start) + start.length + 1);
            $("#inputNo").val(no);
            var isOn = $("#receipForm" + formNo + " .startAddrSet" + no).attr("class").indexOf("on");
            if (isOn < 0) {

            } else {
                var index = $("#receipForm" + formNo + " .searchStartList" + no).val();
                var data = $recptSttus.addrStartHistList[index];

                $("#receipForm" + formNo + " #DEPT_DESC" + no).val(data.STRTPNT_ADRES);
                $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val(data.STRTPNT_FULL_ADRES);
                $("#receipForm" + formNo + " #DEPT_X" + no).val(data.START_LC_CRDNT_X);
                $("#receipForm" + formNo + " #DEPT_Y" + no).val(data.START_LC_CRDNT_Y);

                var addrObject = new Object();
                addrObject.x_coor = data.START_LC_CRDNT_X;
                addrObject.y_coor = data.START_LC_CRDNT_Y;
                addrObject.title = data.STRTPNT_ADRES;
                addrObject.addres = data.STRTPNT_FULL_ADRES;
                $recptSttus.startAddrObject = addrObject;
            }
        });

        $(".searchEndList1, .searchEndList2").change(function () {
            var formNo = $("#formNo").val();
            var inputNo = $(this).attr("class");
            var end = 'searchEndList';
            var no = inputNo.slice(inputNo.indexOf(end) + end.length, inputNo.indexOf(end) + end.length + 1);
            $("#inputNo").val(no);
            var isOn = $("#receipForm" + formNo + " .endAddrSet" + no).attr("class").indexOf("on");
            if (isOn < 0) {

            } else {
                var index = $("#receipForm" + formNo + " .searchEndList" + no).val();
                var data = $recptSttus.addrEndHistList[index];

                $("#receipForm" + formNo + " #DEST_DESC" + no).val(data.ALOC_ADRES);
                $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val(data.ALOC_FULL_ADRES);
                $("#receipForm" + formNo + " #DEST_X" + no).val(data.ARVL_LC_CRDNT_X);
                $("#receipForm" + formNo + " #DEST_Y" + no).val(data.ARVL_LC_CRDNT_Y);
                var addrObject = new Object();
                addrObject.x_coor = data.ARVL_LC_CRDNT_X;
                addrObject.y_coor = data.ARVL_LC_CRDNT_Y;
                addrObject.title = data.ALOC_ADRES;
                addrObject.adres = data.ALOC_FULL_ADRES;
                $recptSttus.endAddrObject = addrObject;
            }
            checkAddress(formNo, no);
        });

        $(".changeStartEnd1, .changeStartEnd2").click(function () {
            var formNo = $("#formNo").val();
            var inputNo = $(this).attr("class");
            var str = 'changeStartEnd';
            var no = inputNo.slice(inputNo.indexOf(str) + str.length, inputNo.indexOf(str) + str.length + 1);
            $("#inputNo").val(no);
            var descTemp = $("#receipForm" + formNo + " #DEPT_DESC" + no).val();
            var descFTemp = $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val();
            var xTemp = $("#receipForm" + formNo + " #DEPT_X" + no).val();
            var yTemp = $("#receipForm" + formNo + " #DEPT_Y" + no).val();

            $("#receipForm" + formNo + " #DEPT_DESC" + no).val($("#receipForm" + formNo + " #DEST_DESC" + no).val());
            $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val($("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val());
            $("#receipForm" + formNo + " #DEPT_X" + no).val($("#receipForm" + formNo + " #DEST_X" + no).val());
            $("#receipForm" + formNo + " #DEPT_Y" + no).val($("#receipForm" + formNo + " #DEST_Y" + no).val());

            $("#receipForm" + formNo + " #DEST_DESC" + no).val(descTemp);
            $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val(descFTemp);
            $("#receipForm" + formNo + " #DEST_X" + no).val(xTemp);
            $("#receipForm" + formNo + " #DEST_Y" + no).val(yTemp);


            var tmpObject = $recptSttus.startAddrObject;
            $recptSttus.startAddrObject = $recptSttus.endAddrObject;
            $recptSttus.endAddrObject = tmpObject;
            checkAddress(formNo, no);
        });

        $("#recptDate1, #recptDate2, #recptDate3").on("change paste keyup", function () { //사전예약+ 예약1
            // if (this.id == "recptDate1") {
            //     $recptSttus.timeCheck(1);
            // } else if (this.id == "recptDate2") {
            //     $recptSttus.timeCheck(2);
            // } else if (this.id == "recptDate3") {
            //     $recptSttus.timeCheck(3);
            // }
        });

        $("#recptHour1, #recptHour2, #recptHour3").on("change", function () { //사전예약 + 예약1
            //
            // if (this.id == "recptHour1") {
            //     $recptSttus.timeCheck(1);
            // } else if (this.id == "recptHour2") {
            //     $recptSttus.timeCheck(2);
            // } else if (this.id == "recptHour3") {
            //     $recptSttus.timeCheck(3);
            // }
        });


        $("#recptMin1, #recptMin2, #recptMin3").on("change", function () {
            //
            // if (this.id == "#recptMin1") {
            //     $recptSttus.timeCheck(1);
            // } else if (this.id == "#recptMin2") {
            //     $recptSttus.timeCheck(2);
            // } else if (this.id == "#recptMin3") {
            //     $recptSttus.timeCheck(3);
            // }
        });

        $("#receipForm1 #SUPPORT_NUM, #receipForm1 #SUPPORT_NUM2, #receipForm1 #SUPPORT_NUM3").on("change", function () {
            if (!$recptSttus.maxHandCheck('1')){
                $("#receipForm1 #SUPPORT_NUM").val(0)
            }
        });

        $("#receipForm2 #SUPPORT_NUM, #receipForm2 #SUPPORT_NUM2, #receipForm2 #SUPPORT_NUM3").on("change", function () {
            if (!$recptSttus.maxHandCheck('2')){
                $("#receipForm2 #SUPPORT_NUM").val(0)
            }
        });

        $recptSttus.list(1); //목록 조회
       
        if(window.location.href.indexOf("carUseResve301")>-1){
        	$("#receiptOpen").click();
        }
        $("#searchAddr").click(function(){
        	$("#searchAddrE").css("display","block");
        	$("#searchAddrS").css("display","block");
        	$("#searchAddr").css("display","none");
		});
        $("#searchAddrS").click(function(){
        	sample5_execDaumPostcode("S");
        	$("#searchAddrE").css("display","none");
        	$("#searchAddrS").css("display","none");
        	$("#searchAddr").css("display","block");
		});
        $("#searchAddrE").click(function(){
        	sample5_execDaumPostcode("E");
        	$("#searchAddrE").css("display","none");
        	$("#searchAddrS").css("display","none");
        	$("#searchAddr").css("display","block");
		});
    });

    $recptSttus = {
        totalCnt: 0,
        currentPageIndex: 1,
        totalCntA: 0,
        currentPageIndexA: 1,
        page: 1,
        x_coor: 33.450701,
        y_coor: 126.570667,

        map: {
            ui: $map.ui,
            ajax: $map.ajax,
            event: $map.event,
            util: $map.util
        },

        searchAddrList: new Array(),
        startAddrObject: null,
        endAddrObject: null,
        addrStartHistList: null,
        addrEndHistList: null,
        checkResult: null,
        cnterOption: new Map(),
        //주소검색
        searchAddr: function (page) {
            $recptSttus.searchAddrList = new Array();
            var searchWord = $("#searchWord").val();
            var senders = new tscp.searchAddrList.api();
            senders.addParam("addr", searchWord);
            senders.addParam("x_coor", $recptSttus.x_coor);
            senders.addParam("y_coor", $recptSttus.y_coor);
            senders.addParam("page", page);
            senders.addParam("count", 15);
            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/alloc/addrSearchList.json"
            })
        },

        //주소검색
        searchAddrPage: function (page, searchWord) {
            $recptSttus.searchAddrList = new Array();

            var searchWord = $("#searchWord").val();
            var senders = new tscp.searchAddrList.api();
            senders.addParam("addr", searchWord);
            senders.addParam("x_coor", $recptSttus.x_coor);
            senders.addParam("y_coor", $recptSttus.y_coor);
            senders.addParam("page", page);
            senders.addParam("count", 15);
            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/alloc/addrSearchList.json"
            })
        },

        startAddrWrite: function (index) {
            //addrObject.x_coor = list[i].latitude;
            //addrObject.y_coor = list[i].longitude;
            //addrObject.title = list[i].title;

            var addrObject = $recptSttus.searchAddrList[index];
            $recptSttus.startAddrObject = addrObject;
            $("#startAddr").val(addrObject.title);
            $("#startFullAddr").val(addrObject.adres);
        },

        endAddrWrite: function (index) {
            var addrObject = $recptSttus.searchAddrList[index];
            $recptSttus.endAddrObject = addrObject;

            $("#endAddr").val(addrObject.title);
            $("#endFullAddr").val(addrObject.adres);
        },

        saveDirection: function () {
            //$carReceipt.startAddrObject
            //$carReceipt.endAddrObject
            //DEPT_DESC1
            //DEPT_X1
            //DEPT_Y1
            if ($("#startAddr").val() != '' && $("#endAddr").val() != '') {
                var senders = new tscp.findStartEndCnter.api();
                senders.addParam("MBER_ID", session.user_id);
                senders.addParam("CNTER_CD", session.cnter_cd);

                senders.addParam("DEPT_DESC", $recptSttus.startAddrObject.title);
                senders.addParam("DEPT_FULL_DESC", $recptSttus.startAddrObject.adres);
                senders.addParam("DEPT_X", $recptSttus.startAddrObject.x_coor);
                senders.addParam("DEPT_Y", $recptSttus.startAddrObject.y_coor);

                senders.addParam("DEST_DESC", $recptSttus.endAddrObject.title);
                senders.addParam("DEST_FULL_DESC", $recptSttus.endAddrObject.adres);
                senders.addParam("DEST_X", $recptSttus.endAddrObject.x_coor);
                senders.addParam("DEST_Y", $recptSttus.endAddrObject.y_coor);


                senders.request({
                    method: "POST",
                    async: true,
                    url: contextPath + "/ServiceAPI/carUseResve/findStartEndCnter.json",
                });

//				var formNo = $("#formNo").val();
//				var inputNo = $("#inputNo").val();
//				$("#receipForm4").hide();
//				$("#receipForm" + formNo).show();
//
//				$("#receipForm" + formNo +" #DEPT_DESC" + inputNo).val($recptSttus.startAddrObject.title);
//				$("#receipForm" + formNo +" #DEPT_X" + inputNo).val($recptSttus.startAddrObject.x_coor);
//				$("#receipForm" + formNo +" #DEPT_Y" + inputNo).val($recptSttus.startAddrObject.y_coor);
//
//				$("#receipForm" + formNo +" #DEST_DESC" + inputNo).val($recptSttus.endAddrObject.title);
//				$("#receipForm" + formNo +" #DEST_X" + inputNo).val($recptSttus.endAddrObject.x_coor);
//				$("#receipForm" + formNo +" #DEST_Y" + inputNo).val($recptSttus.endAddrObject.y_coor);
            } else {
                alert("출발지와 목적지를 모두 선택하여야합니다.");
            }
            //reset
        },
        saveDirection2: function (DEPT_DESC, DEPT_FULL_DESC, DEPT_X, DEPT_Y, DEST_DESC, DEST_FULL_DESC, DEST_X, DEST_Y) {
            //$carReceipt.startAddrObject
            //$carReceipt.endAddrObject
            //DEPT_DESC1
            //DEPT_X1
            //DEPT_Y1
            var senders = new tscp.findStartEndCnter.api();
            senders.addParam("MBER_ID", session.user_id);
            senders.addParam("CNTER_CD", session.cnter_cd);

            senders.addParam("DEPT_DESC", DEPT_DESC);
            senders.addParam("DEPT_FULL_DESC", DEPT_FULL_DESC);
            senders.addParam("DEPT_X", DEPT_X);
            senders.addParam("DEPT_Y", DEPT_Y);

            senders.addParam("DEST_DESC", DEST_DESC);
            senders.addParam("DEST_FULL_DESC", DEST_FULL_DESC);
            senders.addParam("DEST_X", DEST_X);
            senders.addParam("DEST_Y", DEST_Y);


            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/carUseResve/findStartEndCnter.json",
            });
            //reset
        },


        // commonCDMng API 호출
        list: function (page) {
            $(".paging").empty();
            var senders = new tscp.recptSttuslist.api();
            senders.addParam("page", page);
            $recptSttus.page = page;
            senders.addParam("CNTER_CD", session.cnter_cd);
            if ($("#CARALC_STTUS_CD").val() != "") {
                senders.addParam("CARALC_STTUS_CD", $("#CARALC_STTUS_CD").val());
            }
            if ($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
                if ($("#ST_DT").val() <= $("#EN_DT").val()) {
                    senders.addParam("ST_DT", $("#ST_DT").val());
                    senders.addParam("EN_DT", $("#EN_DT").val());
                } else {
                    alert("예약 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
                    $("#ST_DT").focus();
                    return false;
                }
            }

            $(".contents .listTable tbody").html("<tr><td colspan='9'>조회중입니다.</td></tr>");
            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/carUseResve/recptSttusList.json",
            });
        },
        recptSttusAdd: function () {
            if (validationReceiptAdd()) {
                if (confirm("예약접수를 등록하시겠습니까?")) {
                    var senders = new tscp.recptSttusAdd.api();
                    senders.addParam("MBER_ID", session.user_id);
                    senders.addParam("CNTER_CD", session.cnter_cd);


                    var formNo = $("#formNo").val();
                    var no = $("#inputNo").val();
                    var resve_dt = [];
                    if (formNo == 2) {
                        if ($("#receipForm" + formNo + " #recptDate1").val() != ''
                            && $("#receipForm" + formNo + " #recptHour1").val() != ''
                            && $("#receipForm" + formNo + " #recptMin1").val() != '') {
                        }
                        resve_dt.push($("#receipForm" + formNo + " #recptDate1").val()
                            + " " + $("#receipForm" + formNo + " #recptHour1").val()
                            + ":" + $("#receipForm" + formNo + " #recptMin1").val());

                        if(!$recptSttus.timeCheck(1)){
                            return;
                        }
                    } else if (formNo == 3) {
                        if ($("#receipForm" + formNo + " #recptDate2").val() !== ''
                            && $("#receipForm" + formNo + " #recptHour2").val() != ''
                            && $("#receipForm" + formNo + " #recptMin2").val() != '') {
                            resve_dt.push($("#receipForm" + formNo + " #recptDate2").val()
                                + " " + $("#receipForm" + formNo + " #recptHour2").val()
                                + ":" + $("#receipForm" + formNo + " #recptMin2").val());

                            if(!$recptSttus.timeCheck(2)){
                                return;
                            }
                        }
                        if ($("#receipForm" + formNo + " #recptDate3").val() !== ''
                            && $("#receipForm" + formNo + " #recptHour3").val() != ''
                            && $("#receipForm" + formNo + " #recptMin3").val() != '') {
                            resve_dt.push($("#receipForm" + formNo + " #recptDate3").val()
                                + " " + $("#receipForm" + formNo + " #recptHour3").val()
                                + ":" + $("#receipForm" + formNo + " #recptMin3").val());

                            if(!$recptSttus.timeCheck(3)){
                                return;
                            }
                        }
                    } else {
                        var today = new Date();
                        var yyyy = today.getFullYear();
                        var mm = today.getMonth()<9?"0"+(today.getMonth()+1):(today.getMonth()+1) ; //January is 0!
                        var dd = today.getDate()<10?"0"+today.getDate():today.getDate();
                        var HH = today.getHours()<10?"0"+today.getHours():today.getHours();
                        var MM = today.getMinutes()<10?"0"+today.getMinutes():today.getMinutes();
                        resve_dt.push(yyyy + "-" + mm + "-" + dd + " " + HH + ":" + MM);
                    }
                    senders.addParam("RESVE_DT", resve_dt);
                    var dept_y = [];
                    var dept_x = [];
                    var dept_desc = [];
                    var dept_full_desc = [];
                    var dest_y = [];
                    var dest_x = [];
                    var dest_desc = [];
                    var dest_full_desc = [];
                    var chrgCnterCd = [];
                    var grpId = [];
                    var mvmnTyCd = [];
                    var expectReqreTime = [];
                    var expectMvmnDstnc = [];
                    var expectExpectCychg = [];

                    if ($("#receipForm" + formNo + " #DEPT_DESC1").val() != '') {
                        dept_x.push($("#receipForm" + formNo + " #DEPT_X1").val());
                        dept_y.push($("#receipForm" + formNo + " #DEPT_Y1").val());
                        dept_desc.push($("#receipForm" + formNo + " #DEPT_DESC1").val());
                        dept_full_desc.push($("#receipForm" + formNo + " #DEPT_FULL_DESC1").val())
                    }
                    if ($("#receipForm" + formNo + " #DEST_DESC1").val() != '') {
                        dest_x.push($("#receipForm" + formNo + " #DEST_X1").val());
                        dest_y.push($("#receipForm" + formNo + " #DEST_Y1").val());
                        dest_desc.push($("#receipForm" + formNo + " #DEST_DESC1").val());
                        dest_full_desc.push($("#receipForm" + formNo + " #DEST_FULL_DESC1").val());
                    }
                    if ($("#receipForm" + formNo + " #CHRG_CNTER_CD1").val() != '') {
                        chrgCnterCd.push($("#receipForm" + formNo + " #CHRG_CNTER_CD1").val());
                    }
                    if ($("#receipForm" + formNo + " #GRP_ID1").val() != '') {
                        grpId.push($("#receipForm" + formNo + " #GRP_ID1").val());
                    }


                    if (formNo == 3) {
                        if ($("#receipForm" + formNo + " #DEPT_DESC2").val() == '') {
                            alert("예약2의 출발지는 필수 값입니다.");
                            return
                        } else {
                            dept_x.push($("#receipForm" + formNo + " #DEPT_X2").val());
                            dept_y.push($("#receipForm" + formNo + " #DEPT_Y2").val());
                            dept_desc.push($("#receipForm" + formNo + " #DEPT_DESC2").val());
                            dept_full_desc.push($("#receipForm" + formNo + " #DEPT_FULL_DESC2").val());
                        }
                        if ($("#receipForm" + formNo + " #DEST_DESC2").val() == '') {
                            alert("예약2의 목적지는 필수 값입니다.");
                            return
                        } else {
                            dest_x.push($("#receipForm" + formNo + " #DEST_X2").val());
                            dest_y.push($("#receipForm" + formNo + " #DEST_Y2").val());
                            dest_desc.push($("#receipForm" + formNo + " #DEST_DESC2").val());
                            dest_full_desc.push($("#receipForm" + formNo + " #DEST_FULL_DESC2").val());
                        }

                        if ($("#receipForm" + formNo + " #CHRG_CNTER_CD2").val() != '') {
                            chrgCnterCd.push($("#receipForm" + formNo + " #CHRG_CNTER_CD2").val());
                        }

                    }

                    senders.addParam("START_LC_CRDNT_X", dept_x);
                    senders.addParam("START_LC_CRDNT_Y", dept_y);
                    senders.addParam("STRTPNT_ADRES", dept_desc);
                    senders.addParam("STRTPNT_FULL_ADRES", dept_full_desc);

                    senders.addParam("ARVL_LC_CRDNT_X", dest_x);
                    senders.addParam("ARVL_LC_CRDNT_Y", dest_y);
                    senders.addParam("ALOC_ADRES", dest_desc);
                    senders.addParam("ALOC_FULL_ADRES", dest_full_desc);

                    senders.addParam("CHRG_CNTER_CD", chrgCnterCd);
                    senders.addParam("GRP_ID", grpId);

                    if ($("#receipForm" + formNo + " #MVMN_PURPS_CD").val() != '') {
                        senders.addParam("MVMN_PURPS_CD", $("#receipForm" + formNo + " #MVMN_PURPS_CD").val());
                    }
                    if ($("#receipForm" + formNo + " #WHEELCHAIR_SE_CD").val() != '') {
                        senders.addParam("WHEELCHAIR_SE_CD", $("#receipForm" + formNo + " #WHEELCHAIR_SE_CD").val());
                    }
                    if ($("#receipForm" + formNo + " #SUPPORT_NUM").val() != '') {
                        senders.addParam("SUPPORT_NUM", $("#receipForm" + formNo + " #SUPPORT_NUM").val());
                    }
                    /*if ($("#receipForm" + formNo + " #ROUNDTRIP_AT").val() != undefined) {
                        if ($("#receipForm" + formNo + " #ROUNDTRIP_AT").val() != '') {
                            senders.addParam("ROUNDTRIP_AT", $("#receipForm" + formNo + " #ROUNDTRIP_AT").val());
                        }
                    }*/
                    senders.addParam("ROUNDTRIP_AT", 'N');

                    if ($("#receipForm" + formNo + " #RM").val() != '') {
                        senders.addParam("RM", $("#receipForm" + formNo + " #RM").val());
                    }

                    if (formNo == 1) {
                        senders.addParam("RCEPT_SE_CD", 10);
                    } else {
                        senders.addParam("RCEPT_SE_CD", 20);
                    }
                    /*if (formNo == 3) {
                        senders.addParam("PBTRNSP_TRNSIT_AT", "Y");
                    } else {
                        senders.addParam("PBTRNSP_TRNSIT_AT", "N");
                    }*/
                    senders.addParam("PBTRNSP_TRNSIT_AT", "N");
                    
                    //추가 탑승 이용자 정보
                    var sharedTotal = ($("#receipForm" + formNo + " #addHandInfo").children("tbody").children("tr").length)-1;
                    senders.addParam("BRDNG_NMPR",sharedTotal);
                    sharedTotal = sharedTotal-1;
                    
                    if(sharedTotal >0){
                    	for(var i=2; i<(sharedTotal+2); i++){
                    		senders.addParam("MBER_NM"+i, $("#receipForm" + formNo + " #MBER_NM"+i).val());
                    		senders.addParam("MBTLNUM"+i, $("#receipForm" + formNo + " #MBTLNUM"+i).val());
                    		senders.addParam("WHEELCHAIR_SE_CD"+i, $("#receipForm" + formNo + " #WHEELCHAIR_SE_CD"+i).val());
                    		senders.addParam("SUPPORT_NUM"+i, $("#receipForm" + formNo + " #SUPPORT_NUM"+i).val());
                    	}
                    }
                    
                    mvmnTyCd.push($("#MVMN_TY_CD1").val());
                    mvmnTyCd.push($("#MVMN_TY_CD2").val());
                    senders.addParam("MVMN_TY_CD", mvmnTyCd);

                    expectReqreTime.push($("#EXPECT_REQRE_TIME1").val());
                    expectReqreTime.push($("#EXPECT_REQRE_TIME2").val());
                    senders.addParam("EXPECT_REQRE_TIME", expectReqreTime);

                    expectMvmnDstnc.push($("#EXPECT_MVMN_DSTNC1").val());
                    expectMvmnDstnc.push($("#EXPECT_MVMN_DSTNC2").val());
                    senders.addParam("EXPECT_MVMN_DSTNC", expectMvmnDstnc);

                    expectExpectCychg.push($("#EXPECT_CYCHG1").val());
                    expectExpectCychg.push($("#EXPECT_CYCHG2").val());
                    senders.addParam("EXPECT_CYCHG", expectExpectCychg);

                    var inKobus = $("#JOIN_TYPE").val();
                    var status = $("#STATUS").val();

                    if (status == "PASS" && inKobus == "KOBUS"){
                        senders.addParam("JOIN_TYPE","KOBUS");
                        senders.addParam("RESVE_NO",$("#RESVE_NO").val());
                        senders.addParam("RUN_TYPE",$("#RUN_TYPE").val());
                        senders.addParam("BOARDING_TIME",$("#BOARDING_TIME").val());
                        senders.addParam("REQUIRE_TIME",$("#REQUIRE_TIME").val());
                    }else{
                        senders.addParam("JOIN_TYPE","OTHER");
                    }

                    senders.request({
                        method: "POST",
                        async: true,
                        url: contextPath + "/ServiceAPI/carUseResve/recptSttusAdd.json",
                    });
                }
            }
        },
        carReceiptDelete: function (resve_no) {
//            var multiDelete = '';
//            if (str.length > 1) {
//                multiDelete = "포함 " + str.length + " 건의 "
//            }
//
//            var confrimText = "\"" + str[0].slice(0, str[0].length - 5) + "\"" + multiDelete + " 차량예약을 취소하시겠습니까?";
            if (confirm("선택한 예약을 취소하시겠습니까?")) {
            	var senders = new tscp.carReceiptDelete.api();
                senders.addParam("RESVE_NO", resve_no);
                senders.addParam("CARALC_STTUS_CD", '20');
                senders.request({
                    method : "POST",
                    async : true,
                    url : contextPath + "/ServiceAPI/carUseResve/recptSttusDelete.json",
                });
                
                /*var senders = new tscp.carReceiptAllDelete.api();
                senders.addParam("RESVE_DT", str);
                senders.addParam("MBER_ID", session.user_id);
                senders.addParam("CNTER_CD", session.cnter_cd);
                senders.request({
                    method: "POST",
                    async: true,
                    url: contextPath + "/ServiceAPI/carUseResve/recptSttusAllDelete.json",
                });
             
                var senders = new tscp.carReceiptDelete.api();
                senders.addParam("RESVE_DT", str);
                senders.addParam("MBER_ID", session.user_id);
                senders.addParam("CNTER_CD", session.cnter_cd);
                senders.request({
                    method : "POST",
                    async : true,
                    url : contextPath + "/ServiceAPI/carUseResve/recptSttusDelete.json",
                });*/
            }
        }, openBookPop: function () {
            var senders = new tscp.openBookPop.api();
            senders.addParam("MBER_ID", session.user_id);
            senders.addParam("CNTER_CD", session.cnter_cd);
            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/carUseResve/getPopUpInfo.json",
            });
        },
        resveAbleTimeList: function (inputIndex) {
            var senders = new tscp.resveAbleTimeList.api();
            var formNo = $("#formNo").val();
            var chrgCnterCd = [];
            var resve_dt = [];
            var mvmnTyCd = [];

            chrgCnterCd.push($recptSttus.cnterOption.get(inputIndex+"")[1].CNTER_CD);

            resve_dt.push($("#recptDate" + inputIndex).val()
                + " " + $("#recptHour" + inputIndex).val()
                + ":" + $("#recptMin" + inputIndex).val());

            senders.addParam("WHEELCHAIR_SE_CD", $("#receipForm" + formNo + " #WHEELCHAIR_SE_CD").val());
            senders.addParam("inputIndex", inputIndex);
            senders.addParam("CHRG_CNTER_CD", chrgCnterCd);
            senders.addParam("RESVE_DT", resve_dt);


            mvmnTyCd.push($("#MVMN_TY_CD" + inputIndex).val());
            senders.addParam("MVMN_TY_CD", mvmnTyCd);

            senders.request({
                method: "POST",
                async: true,
                url: contextPath + "/ServiceAPI/carUseResve/resveAbleTimeList.json",
            });
        },


        timeCheck: function (inputIndex) {
            var date = '';
            var hour = '';
            var min = '';

            if (inputIndex == 1) {
                date = $("#recptDate1").val();
                hour = $("#recptHour1").val();
                min = $("#recptMin1").val();
            } else if (inputIndex == 2) {
                date = $("#recptDate2").val();
                hour = $("#recptHour2").val();
                min = $("#recptMin2").val();
            } else if (inputIndex == 3) {
                date = $("#recptDate3").val();
                hour = $("#recptHour3").val();
                min = $("#recptMin3").val();
            }

            if (date == "") {
                return false;
            }

            // date = date + " " + "0" +hour + ":" +"0"+ min;
            var selectDate = new Date(date);
            selectDate.setHours(Number(hour));
            selectDate.setMinutes(Number(min));

            var nowDate = new Date();
            var ableHour = nowDate.getHours();
            var ableMin = nowDate.getMinutes();
            var nextDay = false;

            //일 수 비교
            var now =new Date();
            var select =new Date(date);
            now.setHours(1);
            now.setMinutes(0);
            now.setSeconds(0);
            select.setHours(1);
            select.setMinutes(0);
            
            var compareDay = (Math.round((select.getTime() - now.getTime())/(1000*60*60*24)));
            
            if (nowDate > selectDate) {
                if (15 > ableMin) {
                    ableMin = 15;
                } else if (30 > ableMin) {
                    ableMin = 30;
                } else if (45 > ableMin) {
                    ableMin = 45;
                } else if (59 > ableMin) {
                    ableMin = 0;

                    if ((ableHour + 1) > 23) {
                        nextDay = true;
                        ableHour = 0;
                    } else {
                        ableHour = ableHour + 1;
                    }
                }

                alert("현재 시간보다 빠른 시간은 예약 할 수 없습니다.");
                return false;
            } else {
                ableHour = Number(hour);
                ableMin = Number(min);
            }
        	//사전예약
            if(now < select){
	            if ($recptSttus.cnterOption.get(inputIndex+"") != null){
	            	var cnterPossibleDay = Number($recptSttus.cnterOption.get(inputIndex+"")[1].BEFFAT_RESVE_POSBL_DE);
	                var cnterBeginTime = Number($recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_BEGIN_TIME);
	                var cnterEndTime = Number($recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_END_TIME);
	                if(compareDay > cnterPossibleDay){
	                	alert($recptSttus.cnterOption.get(inputIndex+"")[1].CNTER_NM + "의 사전예약 가능 일자는 내일부터  " + $recptSttus.cnterOption.get(inputIndex+"")[1].BEFFAT_RESVE_POSBL_DE + "일 이전까지 입니다.");
	                    return false;
	                }
	                if (cnterBeginTime > ableHour) {
	                    alert($recptSttus.cnterOption.get(inputIndex+"")[1].CNTER_NM + "의 차량 운행 시간은 " + $recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_BEGIN_TIME + "시 부터 " + $recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_END_TIME + "시 까지 입니다.");
	                    return false;
	                }
	
	                if (ableHour > cnterEndTime) {
	                    alert($recptSttus.cnterOption.get(inputIndex+"")[1].CNTER_NM + "의 차량 운행 시간은 " + $recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_BEGIN_TIME + "시 부터 " + $recptSttus.cnterOption.get(inputIndex+"")[1].SCTN_END_TIME + "시 까지 입니다.");
	                    return false;
	                }
	            }
            }else { //당일예약
	            if ($recptSttus.cnterOption.get(inputIndex+"") != null){
	                var cnterBeginTime = Number($recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_BEGIN_TIME);
	                var cnterEndTime = Number($recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_END_TIME);
	                if (cnterBeginTime > hour) {
	                    alert($recptSttus.cnterOption.get(inputIndex+"")[0].CNTER_NM + "의 차량 운행 시간은 " + $recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_BEGIN_TIME + "시 부터 " + $recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_END_TIME + "시 까지 입니다.");
	                    return false;
	                }
	
	                if (hour > cnterEndTime) {
	                    alert($recptSttus.cnterOption.get(inputIndex+"")[0].CNTER_NM + "의 차량 운행 시간은 " + $recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_BEGIN_TIME + "시 부터 " + $recptSttus.cnterOption.get(inputIndex+"")[0].DAY_RESVE_END_TIME + "시 까지 입니다.");
	                    return false;
	                }
	            }
            }
            
            var dateString = "";

            if (nextDay) {
                selectDate.setDate(selectDate.getDate() + 1);
                dateString = selectDate.toISOString().substring(0, 10);
            }

            if (inputIndex == 1) {
                if (dateString != "") {
                    $("#recptDate1").val(dateString);
                }

                $("#recptHour1").val(ableHour);
                $("#recptMin1").val(ableMin);
            } else if (inputIndex == 2) {
                if (dateString != "") {
                    $("#recptDate2").val(dateString);
                }
                $("#recptHour2").val(ableHour);
                $("#recptMin2").val(ableMin);
            } else if (inputIndex == 3) {
                if (dateString != "") {
                    $("#recptDate3").val(dateString);
                }
                $("#recptHour3").val(ableHour);
                $("#recptMin3").val(ableMin);
            }

            return true;
            // if ($recptSttus.cnterOption.has(inputIndex + "")) {
            //     $recptSttus.resveAbleTimeList(inputIndex);
            // }
        },
        plusHand : function(formNo){
            if ($recptSttus.maxHandCheck(formNo, 1)) {
                var num = $("#receipForm"+formNo+" #addHandInfo").children("tbody").children("tr").length;
                var html = "";

                if (num < 4) {
                    html += "<tr id='hand" + num + "' name='hand" + num + "'>";
                    html += "	<td>" + num + "</td>";
                    html += "	<td><input type='text' id='MBER_NM" + num + "' class='inp'></td>";
                    html += "	<td><input type='text' id='MBTLNUM" + num + "' class='inp' placeholder='숫자만 입력해주세요. ex)01011112222'></td>";
                    html += "	<td><select class='select' id='WHEELCHAIR_SE_CD" + num + "'><option>선택</option></select></td>";
                    html += "	<td><select class='select' id='SUPPORT_NUM" + num + "'></select></td>";
                    html += "</tr>";

                    $("#receipForm"+formNo+" #addHandInfo").children("tbody").append(html);

                    $("#receipForm"+formNo+" #WHEELCHAIR_SE_CD" + num).html($("#WHEELCHAIR_SE_CD").html());
                    $("#receipForm"+formNo+" #SUPPORT_NUM" + num).html($("#SUPPORT_NUM").html());

                    $("#receipForm"+formNo+" #SUPPORT_NUM" + num).on("change", function () {
                        if (!$recptSttus.maxHandCheck(formNo)){
                            $("#receipForm"+formNo+" #SUPPORT_NUM" + num).val(0)
                        }
                    });
                }
            }
        	
        },

        maxHandCheck : function(formNo, count){
            var handCount = $('#receipForm'+formNo+' #addHandInfo tr').length - 1;
            var supporter = 0;

            if (count == null){
                count = 0;
            }

            if ($("#receipForm"+formNo+" #SUPPORT_NUM").val() != undefined){
                supporter = supporter + Number($("#receipForm"+formNo+" #SUPPORT_NUM").val());
            }

            if ($("#receipForm"+formNo+" #SUPPORT_NUM2").val() != undefined){
                supporter = supporter + Number($("#receipForm"+formNo+" #SUPPORT_NUM2").val());
            }

            if ($("#receipForm"+formNo+" #SUPPORT_NUM3").val() != undefined){
                supporter = supporter + Number($("#receipForm"+formNo+" #SUPPORT_NUM3").val());
            }

            var total = count + handCount + supporter;

            if (total > 6){
                alert("최대 탑승 인원은 6명입니다.");
                return false;
            }else{
                return true;
            }
        },

        minusHand : function(formNo){
            if ($recptSttus.maxHandCheck(formNo, -1)) {
                var num = $("#receipForm"+formNo+" #addHandInfo").children("tbody").children("tr").length;

                if (num > 2) {
                    $("#receipForm"+formNo+" #hand" + (num - 1)).remove();
                }
            }
        }
    };

    var optionHtml = "<option value='[OPTION_VALUE]'>[OPTION_TEXT]</option>";

    /*********** 접수현황 API 호출 Start **********/
    (function () {
        $class("tscp.recptSttuslist.api").extend($d.api.ajaxAPI).define({
            onSuccess: function (status, res, options) {
                $(".contents .listTable tbody").empty(); //리스트 삭제
                $(".contents .paging").empty();
                var result = res.result;
                var html = "";
                // html += "<colgroup>";
                // html += "<col width='60' />";
                // html += "<col width='80' />";
                // html += "<col width='180' />";
                // html += "<col width='220' />";
                // html += "<col width='220' />";
                // html += "<col width='' />";
                // html += "<col width='' />";
                // html += "<col width='' />";
                // html += "<col width='' />";
                // html += "</colgroup>";
                // html += "<tr>";
                // html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
                // html += "<th>No</th>";
                // html += "<th>예약일시</th>";
                // html += "<th>출발지</th>";
                // html += "<th>목적지</th>";
                // html += "<th>이동유형</th>";
                // //html += "<th>왕복여부</th>";
                // html += "<th>보조인수</th>";
                // html += "<th>구분</th>";
                // //html += "<th>대기자</br>수(명)</th>";
                // html += "<th>배차상태</th>";
                // html += "</tr>";
                if (result.recptSttusList.length == 0) {
                    html += "<tr><td colspan='9'>조회된 결과가 없습니다.</td></tr>";
                } else {
                    var rnum = 1;
                    rnum * $recptSttus.page
                    for (var i = 0; i < result.recptSttusList.length; i++) {
                       /* if (result.recptSttusList[i].PBTRNSP_TRNSIT_AT == 'Y') {
                            html += "<tr class='cell02' id='caralcList'>";
                        } else 
                        if (result.recptSttusList[i].RCEPT_SE_CD == '10') {
                            html += "<tr class='cell01' id='caralcList'>";
                        } else {*/
                        html += "<tr id='caralcList'>";
                        //}
                        //html += "<td class='" + result.recptSttusList[i].CARALC_STTUS_CD + "||" + result.recptSttusList[i].RESVE_DT + "'><input type='checkbox' name='chk' value='" + result.recptSttusList[i].CARALC_STTUS_CD + "||" + result.recptSttusList[i].RESVE_DT + "'/>";

                        html += "<td><input type='checkbox' name='chk' value='" +result.recptSttusList[i].RESVE_NO+ "'/>" +
                    			"<input type='hidden' name='caralc_sttus_cd' value='"+result.recptSttusList[i].CARALC_STTUS_CD+"'/></td>";
                        html += "<td>"+rnum+"</td>";
                        html += "<td>" + result.recptSttusList[i].RESVE_DT_FORMAT + "</td>";
                        html += "<td>" + result.recptSttusList[i].STRTPNT_ADRES + "</td>";
                        html += "<td>" + result.recptSttusList[i].ALOC_ADRES + "</td>";
                        html += "<td>" + result.recptSttusList[i].MVMN_TY_CD_DE + "</td>";
                        //html += "<td>" + result.recptSttusList[i].ROUNDTRIP_AT_DE + "</td>";
						html += "<td>"+result.recptSttusList[i].SUPPORT_NUM+"</td>";
                        html += "<td>" + result.recptSttusList[i].RCEPT_SE_CD_DE + "</td>";
                        //html += "<td>" + result.recptSttusList[i].WAIT_NMPR + "</td>";
                        if (result.recptSttusList[i].CARALC_STTUS_CD == 10) {
                            html += "<td>신청</td>";
                        } else if (result.recptSttusList[i].CARALC_STTUS_CD == 20 || result.recptSttusList[i].CARALC_STTUS_CD == 40) {
                            html += "<td>취소</td>";
                        } else if (result.recptSttusList[i].CARALC_STTUS_CD == 30 || result.recptSttusList[i].CARALC_STTUS_CD == 50) {
                            html += "<td><span class='cr'>처리중</span></td>";
                        } else if (result.recptSttusList[i].CARALC_STTUS_CD == 60) {
                            html += "<td>배차실패</td>";
                        } else if (result.recptSttusList[i].CARALC_STTUS_CD == 70 || result.recptSttusList[i].CARALC_STTUS_CD == 80
                            || result.recptSttusList[i].CARALC_STTUS_CD == 90 || result.recptSttusList[i].CARALC_STTUS_CD == 100
                            || result.recptSttusList[i].CARALC_STTUS_CD == 110) {
                            html += "<td><span class='cg'>배차완료</span></td>";
                        } else {
                            html += "<td>" + result.recptSttusList[i].CARALC_STTUS_CD_DE + "</td>";
                        }
//						if(result.recptSttusList[i].CARALC_STTUS_CD >= 100) {
//							html += "<td>"+result.recptSttusList[i].CARALC_STTUS_CD_DE+"</td>";
//						} else {
//							html += "<td><a href=\"javascript:$recptSttus.carReceiptDelete(\['"+ result.recptSttusList[i].RESVE_DT+"\']);\" class='btn01'>취소</a></td>";
//						}
                        html += "</tr>";
                        rnum++ ;
                    }
                }

                $(".contents .listTable tbody").html(html);
                $("#totalCnt").html(result.recptSttusListCount[0].CNT);
                $recptSttus.totalCnt = result.recptSttusListCount[0].CNT;
                paging($recptSttus, "recptSttusList");

                //CRUD 버튼 막기
                if (CRUDInfo == "10") {
                    $(".CRUDBtn").hide();
                } else {
                    $(".CRUDBtn").show();
                }
            },
            onFail: function (status) {
            }
        });
    }());
    /*********** 접수현황 API 호출 End **********/

    /*********** 예약접수등록 API 호출 Start **********/
    (function () {
        $class("tscp.recptSttusAdd.api").extend($d.api.ajaxAPI).define({
            onSuccess: function (status, res, options) {
                if (res.errCd == "0" && res.result.result.err_cd == "0") { //성공
                    alert("예약접수가 등록되었습니다.");
                    $(".dialog").hide();
                    $recptSttus.list(1);

                    $("#STATUS").val(null);
                    $("#JOIN_TYPE").val(null);
                    $("#RESVE_NO").val(null);
                    $("#RUN_TYPE").val(null);
                    $("#BOARDING_TIME").val(null);
                    $("#REQUIRE_TIME").val(null);
                } else {
                    if (res.result != undefined) {
                        // 배차가능 차량 없을 시 에러코드 9960
                        if (res.result.result.err_cd == "9960") {
                        	
                            var formNo = $("#formNo").val();
                            var no = $("#inputNo").val();

                            if (formNo == 2){
                                // $recptSttus.timeCheck(1);
                                $recptSttus.resveAbleTimeList(1);
                            }else if (formNo == 3){
                                if ($recptSttus.cnterOption.get("2")[1]["able"] == undefined || $recptSttus.cnterOption.get("2")[1]["able"] == false){
                                    // $recptSttus.timeCheck(2);
                                    $recptSttus.resveAbleTimeList(2);
                                }else if ($recptSttus.cnterOption.get("3")[1]["able"] == undefined || $recptSttus.cnterOption.get("3")[1]["able"] == false){
                                    // $recptSttus.timeCheck(3);
                                    $recptSttus.resveAbleTimeList(3);
                                }
                            }else if (formNo == 1){
                            	alert(res.result.result.err_msg);
                            }
                        }else if(res.result.result.err_cd == "9970"){
                        	var limit_ed_year = res.result.result.MAX_LIMIT_ED_DT.substring(0,4);
                        	var limit_ed_month = res.result.result.MAX_LIMIT_ED_DT.substring(4,6);
                        	var limit_ed_day = res.result.result.MAX_LIMIT_ED_DT.substring(6,8);
                        	var memo = res.result.result.MEMO;
                        	alert(limit_ed_year+"/"+limit_ed_month+"/"+limit_ed_day+" 까지 이용이 제한되셨습니다.\n제한사유 : "+memo);
                        }else{
                            alert(res.result.result.err_msg);
                            return;
                        }
                    } else {
                        alert(res.errMsg);
                    }
                }
            },
            onFail: function (status) {
            }
        });
    }());
    /*********** 예약접수등록 API 호출 End **********/

    /*********** 예약접수취소 API 호출 Start **********/
    (function () {
        $class("tscp.carReceiptDelete.api").extend($d.api.ajaxAPI).define({
            onSuccess: function (status, res, options) {
                if (res.errCd == "0") { //성공
                    alert("예약접수가 취소되었습니다.");
                    $recptSttus.list(1);
                } else {
                    alert(res.errMsg);
                }
            },
            onFail: function (status) {
            }
        });
    }());
    /*********** 예약접수취소 API 호출 End **********/

    /*********** 취소된 예약 내역 일괄삭제 API 호출 Start **********/
    (function () {
        $class("tscp.carReceiptAllDelete.api").extend($d.api.ajaxAPI).define({
            onSuccess: function (status, res, options) {
                if (res.errCd == "0") { //성공
//                    alert("취소된 예약의 내역이 모두 삭제되었습니다.");
                    alert("예약접수가 취소되었습니다.");
                    $recptSttus.list(1);
                } else {
                    alert(res.errMsg);
                }
            },
            onFail: function (status) {
            }
        });
    }());
    /*********** 취소된 예약 내역 일괄삭제 API 호출 End **********/

    /*********** 지역검색 API 호출 Start **********/
    (function () {
        $class("tscp.searchAddrList.api").extend($d.api.ajaxAPI).define({
            onSuccess: function (status, res, options) {
                if (res.errCd == "0") {
                    //res.result
                    //result.keyword : 시청역
                    //result.totalCount
                    //result.page
                    //result.list
                    //result.status
                    //list.title
                    //list.title
                    //list.latitude
                    //list.longitude
                    $recptSttus.searchAddrList = new Array();
                    var result = res.result;
                    
                    if (result.status == "success") {
//						$("#resultSubject").text("검색기준 : ");
//						$("#resultKeyWord").text(result.keyword);

                        $("#receipForm4 .listTable").empty();
                        var html = "";
                        var list = result.list;
                        html += '<colgroup>';
                        html += '<col width="250" />';
                        html += '<col width="" /> ';
                        html += '<col width="250" /> ';
                        html += '</colgroup>';
                        html += "<tr>";
                        html += "<th>명칭</th>";
                        html += "<th>주소</th>";
                        html += "<th>선택</th>";
                        html += "</tr>";

                        if (list.length != 0) { // kakao api 수정
                            for (var i = 0; i < list.length; i++) {
                                var addrObject = new Object();
                                addrObject.x_coor = list[i].x;
                                addrObject.y_coor = list[i].y;
                                
                                if(result.type == "1")
	                                addrObject.title = list[i].place_name;
                                else
                                	addrObject.title = list[i].address_name;

                                $recptSttus.searchAddrList.push(addrObject);

                                html += "<tr>";
                                if(result.type=="1"){
                                	html += "<td>" + list[i].place_name + "</td>";
	                                if(list[i].road_address_name != ""){
	                                	addrObject.adres = list[i].road_address_name;
	                                	html += "<td class='al'>" + list[i].road_address_name + "</th>";
	                                }else {
	                                	addrObject.adres = list[i].address_name;
	                                	html += "<td class='al'>" + list[i].address_name + "</th>";
	                                }
                                }else {
                                	html += "<td>"+list[i].address_name+"</td>";
                                	addrObject.adres = list[i].address_name;
                                	html += "<td class='al'>" + list[i].address_name + "</th>";
                                }
                                html += "<td>";
                                html += "<a href=\"javascript:$recptSttus.startAddrWrite(" + i + ")\" class='btn01'>출발지</a>&nbsp;";
                                html += "<a href=\"javascript:$recptSttus.endAddrWrite(" + i + ")\" class='btn01'>목적지</a>";
                                html += "</td>";
                                html += "</tr>";
                            }
                        } else {
                            html += "<tr name='noData'><td colspan='3'>지역검색 목록에 데이터가 없습니다. 주소검색으로 찾아주세요.</td></tr>";
                        }
                        $("#receipForm4 .listTable").html(html);
                        $recptSttus.totalCntA = result.count;
                        paging($recptSttus, "searchAddrList");
                        if ($("#receipForm4 .listTable ").find("tbody").find("tr").next().attr("name") == "noData") //조회내역이 없을 때
                            $("#receipForm4 .paging").empty();
                    }
                }
            },
            onFail: function (status) {

            }
        });
        /*********** 지역검색 API 호출 End **********/

        /*********** 예약팝업 호출전 센터정보 API 호출 Start **********/
        (function () {
            $class("tscp.openBookPop.api").extend($d.api.ajaxAPI).define({
                onSuccess: function (status, res, options) {
                    var result = res.result;
                    console.log(result);
                    $recptSttus.addrStartHistList = result.startPointList;
                    console.log($recptSttus.addrStartHistList);
                    $recptSttus.addrEndHistList = result.endPointList;
                    console.log($recptSttus.addrEndHistList);
                    var startAddrhtml = '';
                    var endAddrhtml = '';
                    var supportNumhtml = '';
                    var cnterInfo = result.cnterInfo; 
                    $("#receipForm1 #alocRequireTime").html("00");
                    $("#receipForm2 #alocRequireTime").html("00");
                    $("#hand1 #MBER_NM").text(session.name);
                    $("#hand1 #MBTLNUM").text(session.mobile);
                    $("#hand2").remove();
                    $("#hand3").remove();
                    for (var i = 0; i < result.startPointList.length; i++) {
                        startAddrhtml += optionHtml.replace("[OPTION_VALUE]", i)
                            .replace("[OPTION_TEXT]", result.startPointList[i].STRTPNT_ADRES);

                    }
                    for (var i = 0; i < result.endPointList.length; i++) {
                        endAddrhtml += optionHtml.replace("[OPTION_VALUE]", i)
                            .replace("[OPTION_TEXT]", result.endPointList[i].ALOC_ADRES);
                    }
                    
                    for (var i = 0; i < 4; i++) {
                    	supportNumhtml += optionHtml.replace("[OPTION_VALUE]", i)
                            .replace("[OPTION_TEXT]", i);
                    }
                    $("#receipForm1 #SUPPORT_NUM").html(supportNumhtml);
                    $("#receipForm1 .searchStartList1").html(startAddrhtml);
                    $("#receipForm1 .searchEndList1").html(endAddrhtml);
                    $("#receipForm1 #ROUNDTRIP_AT").attr("disabled", true);

                    $("#receipForm2 #SUPPORT_NUM").html(supportNumhtml);
                    $("#receipForm2 .searchStartList1").html(startAddrhtml);
                    $("#receipForm2 .searchEndList1").html(endAddrhtml);
                    $("#receipForm2 #ROUNDTRIP_AT").attr("disabled", true);

                    $("#receipForm3 .searchStartList1").html(startAddrhtml);
                    $("#receipForm3 .searchEndList1").html(endAddrhtml);
                    $("#receipForm3 .searchStartList2").html(startAddrhtml);
                    $("#receipForm3 .searchEndList2").html(endAddrhtml);

                    var wheelChairHtml = '';
                    for (var i = 0; i < result.wheelChairTpList.length; i++) {
                        wheelChairHtml += optionHtml.replace("[OPTION_VALUE]", result.wheelChairTpList[i].CD_VALUE)
                            .replace("[OPTION_TEXT]", result.wheelChairTpList[i].CD_VALUE_DE);
                    }
                    $("#receipForm1 #WHEELCHAIR_SE_CD").html(wheelChairHtml);
                    $("#receipForm2 #WHEELCHAIR_SE_CD").html(wheelChairHtml);
                    $("#receipForm3 #WHEELCHAIR_SE_CD").html(wheelChairHtml);

                    var moveTypeHtml = '';
                    for (var i = 0; i < result.moveTpList.length; i++) {
                        moveTypeHtml += optionHtml.replace("[OPTION_VALUE]", result.moveTpList[i].CD_VALUE)
                            .replace("[OPTION_TEXT]", result.moveTpList[i].CD_VALUE_DE);
                    }
                    $("#receipForm1 #MVMN_PURPS_CD").html(moveTypeHtml);
                    $("#receipForm2 #MVMN_PURPS_CD").html(moveTypeHtml);
                    $("#receipForm3 #MVMN_PURPS_CD").html(moveTypeHtml);
                    $("#receipForm1 #MVMN_PURPS_CD").val(20);
                    $("#receipForm2 #MVMN_PURPS_CD").val(20);
                    $("#receipForm3 #MVMN_PURPS_CD").val(20);

                    var now = new Date();
	                $("#nowDay").hide();
	                $("#bookDay").hide();
	                
                    if(cnterInfo.RESVE_LV == "00"){
                    	$("#nowDay").show();
    	                $("#bookDay").show();
    	                form2Open();
    	                form1Open();
                    }else if(cnterInfo.RESVE_LV == "10"){
                    	$("#nowDay").show();
                    	form1Open();
                    }else {
                    	$("#bookDay").show();
                    	form2Open();
                    }

                    var inKobus = $("#JOIN_TYPE").val();

                    if (inKobus == "KOBUS"){
                        var boardingTime = $("#BOARDING_TIME").val();
                        var runType = $("#RUN_TYPE").val();
                        var requireTime = $("#REQUIRE_TIME").val();

                        var year = parseInt(boardingTime.substr(0,4));
                        var month = parseInt(boardingTime.substr(4,2));
                        var day = parseInt(boardingTime.substr(6,2));
                        var hour = parseInt(boardingTime.substr(8,2));
                        var min = parseInt(boardingTime.substr(10,2));
                        var second = parseInt(boardingTime.substr(12,2));

                        var addrObject = {
                            adres : "전라북도 전주시 덕진구 전주천동로 469",
                            title : "전주고속버스터미널",
                            x_coor : "321517K",
                            y_coor : "359765K"
                        };

                        var formNo = $("#formNo").val();
                        var startTerm = 60;
                        var endTerm = 30;

                        if (runType == "S"){
                            $recptSttus.endAddrObject = addrObject;
                            $("#endAddr").val(addrObject.title);
                            $("#endFullAddr").val(addrObject.adres);
                            $("#receipForm" + formNo + " #DEST_DESC1").val(addrObject.title);
                            $("#receipForm" + formNo + " #DEST_FULL_DESC1").val(addrObject.adres);
                            $("#receipForm" + formNo + " #DEST_X1").val(addrObject.x_coor);
                            $("#receipForm" + formNo + " #DEST_Y1").val(addrObject.y_coor);

                            min = min + startTerm;
                        }else if (runType == "E"){
                            $recptSttus.startAddrObject = addrObject;
                            $("#startAddr").val(addrObject.title);
                            $("#startFullAddr").val(addrObject.adres);

                            $("#receipForm" + formNo + " #DEPT_DESC1").val(addrObject.title);
                            $("#receipForm" + formNo + " #DEPT_FULL_DESC1").val(addrObject.adres);
                            $("#receipForm" + formNo + " #DEPT_X1").val(addrObject.x_coor);
                            $("#receipForm" + formNo + " #DEPT_Y1").val(addrObject.y_coor);

                            min = min + requireTime + endTerm;
                        }

                        var selectDate = new Date(year,month - 1,day,hour,min,second);

                        var dateString = selectDate.toISOString().substring(0, 10);

                        var selectMin = selectDate.getMinutes();

                        if (0 <= selectMin && selectMin < 10){
                            selectMin = 0;
                        }else if (10 <= selectMin && selectMin < 20){
                            selectMin = 10;
                        }else if (10 <= selectMin && selectMin < 30){
                            selectMin = 20;
                        }else if (30 <= selectMin && selectMin < 40){
                            selectMin = 30;
                        }else if (40 <= selectMin && selectMin < 50){
                            selectMin = 40;
                        }else if (50 <= selectMin && selectMin <= 59){
                            selectMin = 50;
                        }

                        $("#recptDate1").val(dateString);
                        $("#recptHour1").val(selectDate.getHours());
                        $("#recptMin1").val(selectMin);
                    }
                },
                onFail: function (status) {
                }
            });
        }());
        /*********** 예약팝업 호출전 센터정보 API 호출 End **********/

        /*********** 출발지 목적지 센터정보 API 호출 Start **********/
        (function () {
            $class("tscp.findStartEndCnter.api").extend($d.api.ajaxAPI).define({
                onSuccess: function (status, res, options) {
                    var formNo = $("#formNo").val();
                    var no = $("#inputNo").val();
                    var result = res.result;
                    if (res.errCd != -1) {
                        if (result.checkResult.err_cd == 9999) {
                            alert(result.checkResult.err_msg);

                            var isStartOn = $("#receipForm" + formNo + " .startAddrSet" + no).attr("class").indexOf("on");
                            var isEndOn = $("#receipForm" + formNo + " .startAddrSet" + no).attr("class").indexOf("on");
                            if (isStartOn > 0 || isEndOn > 0) {
                                $("#receipForm" + formNo + " #DEPT_DESC" + no).val('');
                                $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val('');
                                $("#receipForm" + formNo + " #DEPT_X" + no).val('');
                                $("#receipForm" + formNo + " #DEPT_Y" + no).val('');
                                $("#receipForm" + formNo + " #DEST_DESC" + no).val('');
                                $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val('');
                                $("#receipForm" + formNo + " #DEST_X" + no).val('');
                                $("#receipForm" + formNo + " #DEST_Y" + no).val('');
                            }
                        } else {
                            $recptSttus.checkResult = result.checkResult.result[0];
                            // $recptSttus.cnterOption = result.cnterInfo;

                            if (result.checkResult.err_cd == 9990) {
                                alert(result.checkResult.err_msg);
                                if (formNo == 1) {
                                    $("#nowDay").removeClass("on");
                                    $("#bookDay").addClass("on");
                                    $("#transCheck").show();
                                    $("#transCheck").addClass("on");
                                    $("#receipForm1").hide();
                                } else if (formNo == 2) {
                                    $("#transCheck").addClass("on");
                                    $("#receipForm2").hide();
                                }

                                $("#formNo").val(3);
                                formNo = $("#formNo").val();
                                $("#receipForm3").show();
                                $("#receipForm" + formNo + " #DEPT_DESC1").val($recptSttus.startAddrObject.title);
                                $("#receipForm" + formNo + " #DEPT_FULL_DESC1").val($recptSttus.startAddrObject.adres);
                                $("#receipForm" + formNo + " #DEPT_X1").val($recptSttus.startAddrObject.x_coor);
                                $("#receipForm" + formNo + " #DEPT_Y1").val($recptSttus.startAddrObject.y_coor);

                                $("#receipForm" + formNo + " #DEST_DESC2").val($recptSttus.endAddrObject.title);
                                $("#receipForm" + formNo + " #DEST_FULL_DESC2").val($recptSttus.endAddrObject.adres);
                                $("#receipForm" + formNo + " #DEST_X2").val($recptSttus.endAddrObject.x_coor);
                                $("#receipForm" + formNo + " #DEST_Y2").val($recptSttus.endAddrObject.y_coor);
                            } else {
                                $recptSttus.startAddrObject.x_coor = result.startX;
                                $recptSttus.startAddrObject.y_coor = result.startY;
                                $recptSttus.endAddrObject.x_coor = result.endX;
                                $recptSttus.endAddrObject.y_coor = result.endY;

                                $("#receipForm" + formNo).show();
                                $("#receipForm" + formNo + " #DEPT_DESC" + no).val($recptSttus.startAddrObject.title);
                                $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val($recptSttus.startAddrObject.adres);
                                $("#receipForm" + formNo + " #DEPT_X" + no).val($recptSttus.startAddrObject.x_coor);
                                $("#receipForm" + formNo + " #DEPT_Y" + no).val($recptSttus.startAddrObject.y_coor);

                                $("#receipForm" + formNo + " #DEST_DESC" + no).val($recptSttus.endAddrObject.title);
                                $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val($recptSttus.endAddrObject.adres);
                                $("#receipForm" + formNo + " #DEST_X" + no).val($recptSttus.endAddrObject.x_coor);
                                $("#receipForm" + formNo + " #DEST_Y" + no).val($recptSttus.endAddrObject.y_coor);

                                $("#MVMN_TY_CD" + no).val(result.checkResult.result[0].mvmn_ty_cd);
                                if (result.checkResult.result[0].roundtrip_perm_at == 'Y') {
                                    $("#receipForm" + formNo + " #ROUNDTRIP_AT").attr("disabled", true);
                                }


                                $("#EXPECT_REQRE_TIME" + no).val(result.checkResult.result[0].expect_reqre_time);
                                $("#EXPECT_MVMN_DSTNC" + no).val(result.checkResult.result[0].expect_mvmn_dstnc);
                                $("#EXPECT_CYCHG" + no).val(result.checkResult.result[0].expect_cychg);
                            }


                            $("#receipForm" + formNo).show();

                            // 왕복 가능여부
                            /*if (formNo != 3) {
                            	// 왕복 운행 시 소요시간
                            	$("#receipForm"+ formNo +" #alocRequireTime").text(result.cnterInfo[0].ALOC_REQRE_TIME.split('.')[0]);
                            	// 왕복 가능 여부
                                if (result.cnterInfo[0].ROUNDTRIP_PERM_AT == 'Y') {
                                    $("#receipForm" + formNo + " #ROUNDTRIP_AT").attr("disabled", false);
                                } else {
                                    $("#receipForm" + formNo + " #ROUNDTRIP_AT").attr("disabled", true);
                                    $("#receipForm" + formNo + " #ROUNDTRIP_AT").val("N");
                                }
                            }*/

                            $("#receipForm4").hide();
                            $("#receipForm" + formNo + " #CHRG_CNTER_CD" + no).val(result.checkResult.result[0].chrg_cnter_cd);
                            $("#receipForm" + formNo + " #GRP_ID" + no).val(result.checkResult.result[0].grp_id);

                            $("#receipForm" + formNo + " #plusHand").hide();
                            $("#receipForm" + formNo + " #minusHand").hide();
                            /*if (result.checkResult.result[0].solati == "N"){
                                $("#receipForm" + formNo + " #plusHand").hide();
                                $("#receipForm" + formNo + " #minusHand").hide();

                                var num = $("#receipForm" + formNo + " #addHandInfo").children("tbody").children("tr").length;

                                if (num > 2) {
                                    for (var i=2; i<num; i++){
                                        $("#receipForm" + formNo + " #hand" + i).remove();
                                    }
                                }
                            }else if (result.checkResult.result[0].solati == "Y") {
                                $("#receipForm" + formNo + " #plusHand").show();
                                $("#receipForm" + formNo + " #minusHand").show();
                            }*/
                            if (formNo == 2) {
                                $recptSttus.cnterOption.set("1", result.cnterInfo);
                                // $recptSttus.timeCheck(1);
                            } else if (formNo == 3) {
                                if (no == 1) {
                                    $recptSttus.cnterOption.set("2", result.cnterInfo);
                                    // $recptSttus.timeCheck(2);
                                } else if (no == 2) {
                                    $recptSttus.cnterOption.set("3", result.cnterInfo);
                                    // $recptSttus.timeCheck(3);
                                }
                            }
                        }
                    } else {
                        alert(res.errMsg);
                    }
                },
                onFail: function (status) {
                }
            });
        }());
        /*********** 예약팝업 호출전 센터정보 API 호출 End **********/

        /*********** 예약저장 API 호출 Start **********/
        (function () {
            $class("tscp.saveRcept.api").extend($d.api.ajaxAPI).define({
                onSuccess: function (status, res, options) {

                },
                onFail: function (status) {
                }
            });
        }());
        /*********** 예약저장 API 호출 End **********/

        /*********** 예약접수 가능 시간 조회 API 호출 Start **********/
        (function () {
            $class("tscp.resveAbleTimeList.api").extend($d.api.ajaxAPI).define({
                onSuccess: function (status, res, options) {
                    var resultList = res.result.result;
                    var index = res.result.inputIndex.toString();

                    var selectHour = $("#recptHour" + index).val();
                    var selectMin = $("#recptMin" + index).val();

                    if (10 > Number(selectHour)) {
                        selectHour = "0" + selectHour;
                    }

                    if (Number(selectMin) == 0) {
                        selectMin = "0" + selectMin;
                    }

                    var selectTime = selectHour + ":" + selectMin;
                    var cnterTimeInfo = resultList[0];

                    if (cnterTimeInfo.timeList.indexOf(selectTime) != -1) {
                        $recptSttus.cnterOption.get(index)[1]["able"] = true;
                        //예약 가능
                        return;
                    } else {
                        $recptSttus.cnterOption.get(index)[1]["able"] = false;
                        //예약 불가능
                        if (confirm($recptSttus.cnterOption.get(index)[1].CNTER_NM+ "에 해당 시간 배차 가능한 차량이 없습니다.\n예약 가능 시간을 조회 하시겠습니까?")) {
                        } else {
                            return;
                        }
                    }

                    var html = "";
                    html += "<div class='pHeader' style='width: 100%'>";
                    html += "<span>예약가능 시간 현황</span>";
                    html += "<a href=\"javascript:void(0)\"><img src=\"/img/btn/btn_close01.png\" onclick=\"hideTimePopup()\" class=\"closeBtn\"/></a>";
                    html += "</div>";
                    html += "<div class='pCont' style='width: 596px; height: 100%; max-height: 700px;'>";
                    for (var i = 0; i < resultList.length; i++) {

                        var cnterTimeInfo = resultList[i];
                        var cnterName = cnterTimeInfo["cnter_name"].toString();
                        var cnterCd = cnterTimeInfo["cnter_cd"].toString();
                        var resveDate = cnterTimeInfo["resve_date"].toString();

                        html += "<table class='historyTable' style='margin-top: 0px; margin-bottom: 30px;'>";
                        html += "<colgroup>";
                        html += "<col width='30%'>";
                        html += "<col width='70%'>";
                        html += "<tbody>";
                        html += "<tr>" + "<th colspan='2'>" + cnterName + "<br>" + "</br>" + " 조회일 : " + resveDate + "</th>" + "</tr>";

                        var headString = "";

                        if (cnterTimeInfo.timeList.length == 0) {
                            html += "<tr>" + "<td colspan='2'>" + "배차 가능한 차량이 없습니다." + "</td>" + "</tr>";
                        } else {
                            for (var j = 0; j < cnterTimeInfo.timeList.length; j++) {
                                var time = cnterTimeInfo.timeList[j];

                                var num = time.substring(0, 2).toString();

                                if (headString == null || headString != num) {
                                    headString = num;
                                    html += "<tr>";

                                    html += "<th>" + headString + "시" + "</th>";
                                    html += "<td>";
                                    for (var z = 0; z < cnterTimeInfo.timeList.length; z++) {
                                        var a = cnterTimeInfo.timeList[z];
                                        var t = a.substring(0, 2).toString();
                                        var m = a.substring(3, 5).toString();

                                        if (t == headString) {
                                            html += "<span style='margin: 10px; cursor: pointer;' onclick=\"selectTime(" + t + "," + m + "," + index + ")\">" + a + "</span>";
                                        }
                                    }
                                    html += "</td>";
                                    html += "</tr>";
                                }
                            }
                        }

                        html += "</tbody>";
                        html += "</table>";

                    }

                    html += "</div>";
                    $("#pop0").html(html);
                    $("#timePop").show();
                },
                onFail: function (status) {
                }
            });
        }());
        /*********** 예약접수 가능 시간 조회 API 호출 End **********/
    }());

}(window, document));

//예약접수 등록 체크
function validationReceiptAdd() {
    var formNo = $("#formNo").val();
    var no = $("#inputNo").val();
    if (formNo == 2) {
        if ($("#receipForm" + formNo + " #recptDate1").val() == '') {
            alert("예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptHour1").val() == '') {
            alert("예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptMin1").val() == '') {
            alert("예약일시는 필수 값입니다.");
            return;
        }

    } else if (formNo == 3) {
        if ($("#receipForm" + formNo + " #recptDate2").val() == '') {
            alert("예약1의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptHour2").val() == '') {
            alert("예약1의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptMin2").val() == '') {
            alert("예약1의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptDate3").val() == '') {
            alert("예약2의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptHour3").val() == '') {
            alert("예약2의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptMin3").val() == '') {
            alert("예약2의 예약일시는 필수 값입니다.");
            return;
        }
        if ($("#receipForm" + formNo + " #recptDate2").val() != $("#receipForm" + formNo + " #recptDate3").val()) {
            alert("예약1과 예약2의 예약일시는 동일해야 합니다.");
            return;
        }
    }

    if ($("#receipForm" + formNo + " #DEPT_DESC1").val() == '') {
        alert("출발지는 필수 값입니다.");
        return
    }
    if ($("#receipForm" + formNo + " #DEST_DESC1").val() == '') {
        alert("목적지는 필수 값입니다.");
        return
    }

    if (formNo == 3) {
        if ($("#receipForm" + formNo + " #DEPT_DESC2").val() == '') {
            alert("예약2의 출발지는 필수 값입니다.");
            return
        }
        if ($("#receipForm" + formNo + " #DEST_DESC2").val() == '') {
            alert("예약2의 목적지는 필수 값입니다.");
            return
        }
    }

    if ($("#receipForm" + formNo + " #MVMN_PURPS_CD").val() == '') {
        alert("이동목적은 필수 값입니다.");
        return
    }
    if ($("#receipForm" + formNo + " #WHEELCHAIR_SE_CD").val() == '') {
        alert("휠체어구분은 필수 값입니다.");
        return
    }
    if ($("#receipForm" + formNo + " #BRDNG_NMPR").val() == '') {
        alert("탑승인원은 필수 값입니다.");
        return
    }
    if ($("#receipForm" + formNo + " #ROUNDTRIP_AT").val() != undefined) {
        if ($("#receipForm" + formNo + " #ROUNDTRIP_AT").val() == '') {
            alert("탑승인원은 필수 값입니다.");
            return
        }
    }

    return true;
}

function hideTimePopup() {
    $("#pop0").empty();
    $("#timePop").hide();
}

function selectTime(time, min, index) {

    $("#recptHour" + index).val(time);
    $("#recptMin" + index).val(min);
    // $recptSttus.timeCheck(index);
    hideTimePopup();
}


function hidePopup() { // 팝업창 종료시
    if ($("#transCheck").val() == "Y") { // 대중교통 환승여부 체크박스 값이 "Y"일때
        $("#transCheck").click(); // 체크박스 한번 더 클릭
    }
    $("#receiptPopup").hide();
    $recptSttus.list(1);
}

function saveRcept() {
    $recptSttus.saveRcept();
}

function form1Open() {
    document.getElementById('receiptForm').reset();
    $("#transCheck").hide();
    $("#transCheck").val("");
    $("#receipForm1").show();
    $("#receipForm2").hide();
    $("#receipForm3").hide();
    $("#receipForm4").hide();

    var formNo = $("#formNo").val();
    $("#receipForm" + formNo + " .startAddrSet1").removeClass("on");
    $("#receipForm" + formNo + " .endAddrSet1").removeClass("on");
    $("#formNo").val(1);

    $("#receiptPopup").show();
    $("#receipForm" + formNo + " #plusHand").hide();
    $("#receipForm" + formNo + " #minusHand").hide();
    $recptSttus.searchAddrList = null;
    $recptSttus.startAddrObject = null;
    $recptSttus.endAddrObject = null;
    $recptSttus.checkResult = null;
    $recptSttus.cnterOption = new Map();

    $("#nowDay").addClass("on");
    $("#bookDay").removeClass("on");
}

function form2Open() {
    document.getElementById('receiptForm').reset();

    $("#transCheck").show();
    $("#transCheck").val("N");
    $("#receipForm1").hide();
    $("#receipForm2").show();
    $("#receipForm3").hide();
    $("#receipForm4").hide();

    var formNo = $("#formNo").val();
    $("#receipForm" + formNo + " .startAddrSet1").removeClass("on");
    $("#receipForm" + formNo + " .endAddrSet1").removeClass("on");
    $("#formNo").val(2);

    $recptSttus.searchAddrList = null;
    $recptSttus.startAddrObject = null;
    $recptSttus.endAddrObject = null;
    $recptSttus.checkResult = null;
    $recptSttus.cnterOption = new Map();

    $("#receiptPopup").show();
    $("#receipForm" + formNo + " #plusHand").hide();
    $("#receipForm" + formNo + " #minusHand").hide();
    
    $("#nowDay").removeClass("on");
    $("#bookDay").addClass("on");
}

function form3Open() {
    var DEPT_DESC = '';
    var DEPT_X = '';
    var DEPT_Y = '';
    var CHRG_CNTER_CD = '';

    var DEST_DESC = '';
    var DEST_X = '';
    var DEST_Y = '';

    var recptDate = '';
    var recptHour = '';
    var recptMin = '';

    if ($("#receipForm2 #DEPT_DESC1").val() != '' && $("#receipForm2 #DEST_DESC1").val() != '') {
        DEPT_DESC = $("#receipForm2 #DEPT_DESC1").val();
        DEPT_X = $("#receipForm2 #DEPT_X1").val();
        DEPT_Y = $("#receipForm2 #DEPT_Y1").val();
        START_CNTER_C = $("#receipForm2 #CHRG_CNTER_CD1").val();

        DEST_DESC = $("#receipForm2 #DEST_DESC1").val();
        DEST_X = $("#receipForm2 #DEST_X1").val();
        DEST_Y = $("#receipForm2 #DEST_Y1").val();

    }

    if ($("#receipForm2 #recptDate1").val() != '') {
        recptDate = $("#receipForm2 #recptDate1").val();
    }
    if ($("#receipForm2 #recptHour1").val() != '') {
        recptHour = $("#receipForm2 #recptHour1").val();
    }
    if ($("#receipForm2 #recptMin1").val() != '') {
        recptMin = $("#receipForm2 #recptMin1").val();
    }
    document.getElementById('receiptForm').reset();

    if (DEPT_DESC != '' && DEST_DESC != '') {
        $("#receipForm3 #DEPT_DESC1").val(DEPT_DESC);
        $("#receipForm3 #DEPT_X1").val(DEPT_X);
        $("#receipForm3 #DEPT_Y1").val(DEPT_Y);
        $("#receipForm3 #CHRG_CNTER_CD1").val(CHRG_CNTER_CD);

        $("#receipForm3 #DEST_DESC2").val(DEST_DESC);
        $("#receipForm3 #DEST_X2").val(DEST_X);
        $("#receipForm3 #DEST_Y2").val(DEST_Y);

    }
    if (recptDate != '') {
        $("#receipForm3 #recptDate2").val(recptDate);
    }
    if (recptHour != '') {
        $("#receipForm3 #recptHour2").val(recptHour);
    }
    if (recptMin != '') {
        $("#receipForm3 #recptMin2").val(recptMin);
    }

    $("#transCheck").show();
    $("#transCheck").val("Y");
    $("#receipForm1").hide();
    $("#receipForm2").hide();
    $("#receipForm3").show();
    $("#receipForm4").hide();

    var formNo = $("#formNo").val();
    $("#receipForm" + formNo + " .startAddrSet1").removeClass("on");
    $("#receipForm" + formNo + " .endAddrSet1").removeClass("on");
    $("#receipForm" + formNo + " .startAddrSet2").removeClass("on");
    $("#receipForm" + formNo + " .endAddrSet2").removeClass("on");

    $("#formNo").val(3);

    $recptSttus.searchAddrList = null;
//	$recptSttus.startAddrObject = null;
//	$recptSttus.endAddrObject = null;
//	$recptSttus.checkResult = null;
//	$recptSttus.cnterOption = null;


}


function checkAddress(formNo, no) {
    if ($("#receipForm" + formNo + " #DEPT_DESC" + no).val() != ''
        && $("#receipForm" + formNo + " #DEPT_X" + no).val() != ''
        && $("#receipForm" + formNo + " #DEPT_Y" + no).val() != ''

        && $("#receipForm" + formNo + " #DEST_DESC" + no).val() != ''
        && $("#receipForm" + formNo + " #DEST_X" + no).val() != ''
        && $("#receipForm" + formNo + " #DEST_Y" + no).val() != '') {
        $recptSttus.saveDirection2($("#receipForm" + formNo + " #DEPT_DESC" + no).val()
        	, $("#receipForm" + formNo + " #DEPT_FULL_DESC" + no).val()
            , $("#receipForm" + formNo + " #DEPT_X" + no).val()
            , $("#receipForm" + formNo + " #DEPT_Y" + no).val()
            , $("#receipForm" + formNo + " #DEST_DESC" + no).val()
            , $("#receipForm" + formNo + " #DEST_FULL_DESC" + no).val()
            , $("#receipForm" + formNo + " #DEST_X" + no).val()
            , $("#receipForm" + formNo + " #DEST_Y" + no).val());
    }

}

