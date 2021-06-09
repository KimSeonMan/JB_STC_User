<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>KICC EASYPAY 8.0 SAMPLE</title>
<meta name="robots" content="noindex, nofollow"> 
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<%--<link href="/easypay/web/css/style.css" rel="stylesheet" type="text/css">--%>
<script language="javascript" src="/easypay/web/js/default.js" type="text/javascript"></script>
<!-- EASYPAY Test -->
<%--<script type="text/javascript" src="https://testpg.easypay.co.kr/webpay/EasypayCard_Web.js"></script>--%>
<!-- EASYPAY Real -->
<script type="text/javascript" src="https://pg.easypay.co.kr/webpay/EasypayCard_Web.js"></script>
<%--<script type="text/javascript" src="/js/tscp/login/memberShip.js"></script>--%>
<script type="text/javascript">
    /* 입력 자동 Setting */
    function f_init()
    {
        var frm_pay = document.frm_pay;
        
        var today = new Date();
        var year  = today.getFullYear();
        var month = today.getMonth() + 1;
        var date  = today.getDate();
        var time  = today.getTime();
        
        if(parseInt(month) < 10) 
        {
            month = "0" + month;
        }

        if(parseInt(date) < 10) 
        {
            date = "0" + date;
        }

        frm_pay.EP_mall_id.value = "";
        frm_pay.EP_mall_nm.value = "이동지원센터";
        frm_pay.EP_pay_type.value = "81";
        frm_pay.EP_currency.value = "00";
        frm_pay.EP_order_no.value = "ORDER_" + year + month + date + time;   //가맹점주문번호
        // frm_pay.EP_user_id.value = "ksb8787";                           //고객ID
        // frm_pay.EP_user_nm.value = "유다연";
        // frm_pay.EP_user_mail.value = "yoo462@kicc.co.kr";
        // frm_pay.EP_user_phone1.value = "01067820416";testgw.easypay.co.kr
        // frm_pay.EP_user_phone2.value = "01067820416";
        // frm_pay.EP_user_addr.value = "서울시 송파구 가락동 78 ";
        frm_pay.EP_product_nm.value = "인증";
        frm_pay.EP_product_amt.value = "0";
        /* <!-- EASYPAY Test --> */
        //frm_pay.EP_return_url.value = "http://localhost:8081/easypay/web/batch/cert_res.jsp";
        /* <!-- EASYPAY Real --> */
        frm_pay.EP_return_url.value = "http://0632270002.com/easypay/web/batch/cert_res.jsp";
        frm_pay.EP_charset.value = "UTF-8";
        frm_pay.EP_window_type.value = "iframe";

    }
    
    function f_start_pay(user_id,mall_id)
    {
        f_init();

        var frm_pay = document.frm_pay;

        frm_pay.EP_user_id.value = user_id;
        frm_pay.EP_mall_id.value = mall_id;

        /* UTF-8 사용가맹점의 경우 EP_charset 값 셋팅 필수 */
        if( frm_pay.EP_charset.value == "UTF-8" )
        {
            // 한글이 들어가는 값은 모두 encoding 필수.
            frm_pay.EP_mall_nm.value      = encodeURIComponent( frm_pay.EP_mall_nm.value );
            frm_pay.EP_product_nm.value   = encodeURIComponent( frm_pay.EP_product_nm.value );
        }
        
        
        /* 가맹점에서 원하는 인증창 호출 방법을 선택 */

        if( frm_pay.EP_window_type.value == "iframe" ) 
        {
            easypay_webpay(frm_pay,"/easypay/web/batch/iframe_req.jsp","hiddenifr","0","0","iframe",30);
        }
        else if( frm_pay.EP_window_type.value == "popup" ) 
        {
            easypay_webpay(frm_pay,"/easypay/web/batch/popup_req.jsp","hiddenifr","","","popup",30);
        }
    }
    
    function f_submit()
    {
        //$memberShip.updateEasypayBatchkey();
        $easyPay.updateEasypayBatchkey();
    }
    
</script>
</head>
<body onload="f_init();">
<form name="frm_pay" method="post" action="">

<!--------------------------->
<!-- ::: 공통 인증 요청 값 -->
<!--------------------------->
<input type="hidden" id="EP_window_type"    name="EP_window_type"       value="iframe">
<input type="hidden" id="EP_mall_id"        name="EP_mall_id"           value="">
<input type="hidden" id="EP_mall_nm"        name="EP_mall_nm"           value="">         <!-- 가맹점명-->
<input type="hidden" id="EP_currency"       name="EP_currency"          value="00">       <!-- 통화코드 // 00 : 원화-->
<input type="hidden" id="EP_return_url"     name="EP_return_url"        value="">         <!-- 가맹점 CALLBACK URL // -->
<input type="hidden" id="EP_ci_url"         name="EP_ci_url"            value="">         <!-- CI LOGO URL // -->
<input type="hidden" id="EP_lang_flag"      name="EP_lang_flag"         value="">         <!-- 언어 // -->
<input type="hidden" id="EP_charset"        name="EP_charset"           value="UTF-8">   <!-- 가맹점 CharSet // -->
<input type="hidden" id="EP_user_id"        name="EP_user_id"           value="">         <!-- 가맹점 고객ID // -->
<input type="hidden" id="EP_memb_user_no"   name="EP_memb_user_no"      value="">         <!-- 가맹점 고객일련번호 // -->
<input type="hidden" id="EP_user_nm"        name="EP_user_nm"           value="">         <!-- 가맹점 고객명 // -->
<input type="hidden" id="EP_user_mail"      name="EP_user_mail"         value="">         <!-- 가맹점 고객 E-mail // -->
<input type="hidden" id="EP_user_phone1"    name="EP_user_phone1"       value="">         <!-- 가맹점 고객 연락처1 // -->
<input type="hidden" id="EP_user_phone2"    name="EP_user_phone2"       value="">         <!-- 가맹점 고객 연락처2 // -->
<input type="hidden" id="EP_user_addr"      name="EP_user_addr"         value="">         <!-- 가맹점 고객 주소 // -->
<input type="hidden" id="EP_user_define1"   name="EP_user_define1"      value="">         <!-- 가맹점 필드1 // -->
<input type="hidden" id="EP_user_define2"   name="EP_user_define2"      value="">         <!-- 가맹점 필드2 // -->
<input type="hidden" id="EP_user_define3"   name="EP_user_define3"      value="">         <!-- 가맹점 필드3 // -->
<input type="hidden" id="EP_user_define4"   name="EP_user_define4"      value="">         <!-- 가맹점 필드4 // -->
<input type="hidden" id="EP_user_define5"   name="EP_user_define5"      value="">         <!-- 가맹점 필드5 // -->
<input type="hidden" id="EP_user_define6"   name="EP_user_define6"      value="">         <!-- 가맹점 필드6 // -->
<input type="hidden" id="EP_product_type"   name="EP_product_type"      value="">         <!-- 상품정보구분 // -->
<input type="hidden" id="EP_product_expr"   name="EP_product_expr"      value="">         <!-- 서비스 기간 // (YYYYMMDD) -->

<input type="hidden" id="EP_pay_type"       name="EP_pay_type"          value="81">       <!-- 결제수단 // 81 : 배치인증 -->
<input type="hidden" id="EP_order_no"       name="EP_order_no"          value="">         <!-- 주문번호 -->

<input type="hidden" id="EP_product_nm"     name="EP_product_nm"        value="">         <!-- 상품명 -->
<input type="hidden" id="EP_product_amt"    name="EP_product_amt"       value="">         <!-- 상품금액 -->

<input type="hidden" id="EP_cardnick_useyn" name="EP_cardnick_useyn"    value="">         <!-- 카드 별칭 사용 유무(Y : 카드별칭 입력 UI 노출) -->
<input type="hidden" id="EP_batch_type"     name="EP_batch_type"    value="1">             <!-- 배치 인증 유형(1: 인증전용, "" or 기타: 기존화면) -->

<!--------------------------------->
<!-- ::: 인증응답용 인증 요청 값 -->
<!--------------------------------->

<input type="hidden" id="EP_res_cd"          name="EP_res_cd"         value="">      <!--  응답코드 // -->
<input type="hidden" id="EP_res_msg"         name="EP_res_msg"        value="">      <!--  응답메세지 // -->
<input type="hidden" id="EP_tr_cd"           name="EP_tr_cd"          value="">      <!--  결제창 요청구분 // -->
<input type="hidden" id="EP_ret_pay_type"    name="EP_ret_pay_type"   value="">      <!--  결제수단 // -->
<input type="hidden" id="EP_ret_complex_yn"  name="EP_ret_complex_yn" value="">      <!--  복합결제 여부 (Y/N) // -->
<input type="hidden" id="EP_card_code"       name="EP_card_code"      value="">      <!--  카드코드 (ISP:KVP카드코드 MPI:카드코드) // -->
<input type="hidden" id="EP_eci_code"        name="EP_eci_code"       value="">      <!--  MPI인 경우 ECI코드 // -->
<input type="hidden" id="EP_card_req_type"   name="EP_card_req_type"  value="">      <!--  거래구분 // -->
<input type="hidden" id="EP_save_useyn"      name="EP_save_useyn"     value="">      <!--  카드사 세이브 여부 (Y/N) // -->
<input type="hidden" id="EP_trace_no"        name="EP_trace_no"       value="">      <!--  추적번호 // -->
<input type="hidden" id="EP_sessionkey"      name="EP_sessionkey"     value="">      <!--  세션키 // -->
<input type="hidden" id="EP_encrypt_data"    name="EP_encrypt_data"   value="">      <!--  암호화전문 // -->
<input type="hidden" id="EP_pnt_cp_cd"       name="EP_pnt_cp_cd"      value="">      <!--  포인트 CP 코드 // -->
<input type="hidden" id="EP_spay_cp"         name="EP_spay_cp"        value="">      <!--  간편결제 CP 코드 // -->
<input type="hidden" id="EP_card_prefix"     name="EP_card_prefix"    value="">      <!--  신용카드prefix // -->
<input type="hidden" id="EP_card_no_7"       name="EP_card_no_7"      value="">      <!--  신용카드번호 앞7자리 // -->
<input type="hidden" id="EP_cardnick"        name="EP_cardnick"       value="">      <!--  카드별칭(고객이 인증창에서 입력한 카드별칭) -->


<%--<table border="0" width="910" cellpadding="10" cellspacing="0">--%>
<%--<tr>--%>
<%--    <td>--%>
<%--    <!-- title start -->--%>
<%--    <table border="0" width="900" cellpadding="0" cellspacing="0">--%>
<%--    <tr>--%>
<%--        <td height="30" bgcolor="#FFFFFF" align="left">&nbsp;<img src="/easypay/web/img/arow3.gif" border="0" align="absmiddle">&nbsp;배치 > <b>인증</b></td>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <td height="2" bgcolor="#2D4677"></td>--%>
<%--    </tr>--%>
<%--    </table>--%>
<%--    <!-- title end -->--%>

<%--    <!-- mallinfo start -->--%>
<%--    <table border="0" width="900" cellpadding="0" cellspacing="0">--%>
<%--    <tr>--%>
<%--        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="/easypay/web/img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>가맹점정보</b>(*필수)</td>--%>
<%--    </tr>--%>
<%--    </table>--%>

<%--    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">--%>
<%--    <tr height="25">--%>
<%--        <td bgcolor="#EDEDED" width="150">&nbsp; *가맹점ID</td>--%>
<%--        <td bgcolor="#FFFFFF" width="750" colspan="3">&nbsp;<input type="text" id="EP_mall_id" name="EP_mall_id" value="T5102001" size="50" maxlength="8" class="input_F"></td>--%>
<%--    </tr>--%>
<%--    </table>--%>
<%--    <!-- mallinfo end -->--%>

<%--    <!-- webpay start -->--%>
<%--    <table border="0" width="900" cellpadding="0" cellspacing="0">--%>
<%--    <tr>--%>
<%--        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="../img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>결제 정보</b>(*필수)</td>--%>
<%--    </tr>--%>
<%--    </table>--%>

<%--    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">--%>
<%--    <tr height="25">--%>
<%--        <td bgcolor="#EDEDED" width="150">&nbsp; 윈도우 타입</td>--%>
<%--        <td bgcolor="#FFFFFF" width="750" colspan="3">&nbsp;--%>
<%--            <select id="EP_window_type" name="EP_window_type" class="input_F">--%>
<%--                <option value="iframe" >iframe</option>--%>
<%--                <option value="popup" selected>popup</option>--%>
<%--            </select>--%>
<%--       </td>--%>
<%--    </tr>--%>
<%--    </table>--%>
<%--    <!-- webpay end -->--%>

<%--    <table border="0" width="900" cellpadding="0" cellspacing="0">--%>
<%--    <tr>--%>
<%--        <td height="30" align="center" bgcolor="#FFFFFF"><input type="button" value="결 제" class="input_D" style="cursor:hand;" onclick="javascript:f_start_pay();"></td>--%>
<%--    </tr>--%>
<%--    </table>--%>
<%--    </td>--%>
<%--</tr>--%>
<%--</table>--%>
</form>
</body>
</html>