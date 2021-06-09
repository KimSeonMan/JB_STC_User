<%@ page contentType="text/html; charset=utf-8" %>
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
<link href="/easypay/web/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/default.js" type="text/javascript"></script>
<script type="text/javascript">

    /* 입력 자동 Setting */
    function f_init(){
        var frm_pay = document.frm_pay;
        
        var today = new Date();
        var year  = today.getFullYear();
        var month = today.getMonth() + 1;
        var date  = today.getDate();
        var time  = today.getTime();
        
        if(parseInt(month) < 10) {
            month = "0" + month;
        }

        if(parseInt(date) < 10) {
            date = "0" + date;
        }
        
        frm_pay.EP_mall_id.value = "";
        frm_pay.EP_mall_nm.value = "한국정보통신(주) 테스트";
        frm_pay.EP_order_no.value = "ORDER_" + year + month + date + time;   //가맹점주문번호
        frm_pay.EP_user_id.value = "USER_" + time;                           //고객ID
        frm_pay.EP_user_nm.value = "홍길동";
        frm_pay.EP_user_mail.value = "test@kicc.co.kr";
        frm_pay.EP_user_phone1.value = "0212344567";
        frm_pay.EP_user_phone2.value = "01012344567";
        frm_pay.EP_user_addr.value = "서울 금천구 가산동 459-9 ";
        frm_pay.EP_product_nm.value = "☆테스트상품☆";
        frm_pay.EP_product_amt.value = "0";
        
    }
    
    function f_submit() {
        var frm_pay = document.frm_pay;
        
        var bRetVal = false;
        
        /*  주문정보 확인 */
        if( !frm_pay.EP_order_no.value ) {
            alert("가맹점주문번호를 입력하세요!!");
            frm_pay.EP_order_no.focus();
            return;
        }
        
        if( !frm_pay.EP_product_amt.value ) {
            alert("상품금액을 입력하세요!!");
            frm_pay.EP_product_amt.focus();
            return;
        }
        
        /* 결제금액 설정 */
        frm_pay.EP_tot_amt.value = frm_pay.EP_card_amt.value = frm_pay.EP_product_amt.value;


        /* 결제 정보 확인 */
        if ( frm_pay.EP_card_no.value.length != 20 )
		{
            alert("배치키를 입력하세요.!!");
            frm_pay.EP_card_no.focus();
            return;
        }
        
        if( frm_pay.EP_card_amt.value < 50000 ) 
        {
            frm_pay.EP_install_period.value = "00";
            frm_pay.EP_noint.value = "00";
        }
        
        bRetVal = true;
        if ( bRetVal ) frm_pay.submit();
    }
</script>
</head>
<body onload="f_init();">
<form name="frm_pay" method="post" action="../easypay_request.jsp">
	


<!--------------------------->
<!-- ::: 공통 인증 요청 값 -->
<!--------------------------->

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

<!--------------------------->
<!-- ::: 결제 인증 요청 값 -->
<!--------------------------->

<input type="hidden" id="EP_tr_cd"          name="EP_tr_cd"             value="00101000"> <!-- 거래구분(수정불가) -->
<input type="hidden" id="EP_pay_type"       name="EP_pay_type"          value="batch">    <!-- 결제수단(수정불가) -->
<input type="hidden" id="EP_tot_amt"        name="EP_tot_amt"           value="">         <!-- 결제총금액 -->
<input type="hidden" id="EP_currency"       name="EP_currency"          value="00">       <!-- 통화코드 : 00(원), 01(달러)-->
<input type="hidden" id="EP_card_txtype"    name="EP_card_txtype"       value="41">       <!-- 신용카드 처리구분(수정불가) -->
<input type="hidden" id="EP_req_type"       name="EP_req_type"          value="0">        <!-- 신용카드 결제종류(수정불가) -->
<input type="hidden" id="EP_card_amt"       name="EP_card_amt"          value="">         <!-- 신용카드 결제금액 -->
<input type="hidden" id="EP_wcc"            name="EP_wcc"               value="@">        <!-- 신용카드 WCC(수정불가) -->
<input type="hidden" id="EP_noint"          name="EP_noint"             value="00">       <!-- 무이자 -->


<table border="0" width="910" cellpadding="10" cellspacing="0">
<tr>
    <td>
    <!-- title start -->
	<table border="0" width="900" cellpadding="0" cellspacing="0">
	<tr>
		<td height="30" bgcolor="#FFFFFF" align="left">&nbsp;<img src="../img/arow3.gif" border="0" align="absmiddle">&nbsp;배치 > <b>승인</td>
	</tr>
	<tr>
		<td height="2" bgcolor="#2D4677"></td>
	</tr>
	</table>
	<!-- title end -->
	    
    <!-- mallinfo start -->
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="../img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>가맹점정보</b>(*필수)</td>
    </tr>
    </table>
    
    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">
    <tr height="25">
        <td bgcolor="#EDEDED" width="150">&nbsp; *가맹점ID</td>        
        <td bgcolor="#FFFFFF" width="750" colspan="3">&nbsp;<input type="text" id="EP_mall_id" name="EP_mall_id" value="T5102001" size="50" maxlength="8" class="input_F"></td>      
    </tr>
    </table>
    <!-- mallinfo end -->
	    
    <!-- batch start -->
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="../img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>결제정보</b>(*필수)</td>
    </tr>
    </table>

    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">    
    <tr height="25">
    	<td bgcolor="#EDEDED" width="150">&nbsp;*배치키</td>
        <td bgcolor="#FFFFFF" width="750" colspan="3">&nbsp;<input type="text" name="EP_card_no" value="" size="30" maxlength="20" class="input_F">
        <td bgcolor="#EDEDED" width="150">&nbsp;*할부개월</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<select name="EP_install_period" class="input_F">
            <option value="00" selected>일시불</option>
            <option value="02">2개월</option>
            <option value="03">3개월</option>
            <option value="04">4개월</option>
            <option value="05">5개월</option>
            <option value="06">6개월</option>
            <option value="07">7개월</option>
            <option value="08">8개월</option>
            <option value="09">9개월</option>
            <option value="10">10개월</option>
            <option value="11">11개월</option>
            <option value="12">12개월</option>            
        </select></td>
    </tr>
    </table>
    <!-- batch end -->
   
    <!-- order start -->
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="../img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>주문정보</b>(*필수)</td>
    </tr>
    </table>
    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">
    <tr height="25">
        <td bgcolor="#EDEDED" width="150">&nbsp;*주문번호</td>
        <td bgcolor="#FFFFFF" width="750" colspan="3">&nbsp;<input type="text" name="EP_order_no" size="50" class="input_F"></td>
    </tr>
    <tr height="25">
        <td bgcolor="#EDEDED" width="150">&nbsp;상품명</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="EP_product_nm" size="50" class="input_A"></td>
        <td bgcolor="#EDEDED" width="150">&nbsp;상품금액</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="EP_product_amt" size="50" class="input_A"></td>
    </tr>
    </table>
    <!-- order Data END -->
    
    
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" align="center" bgcolor="#FFFFFF"><input type="button" value="결 제" class="input_D" style="cursor:hand;" onclick="javascript:f_submit();"></td>
    </tr>
    </table>
    </td>
</tr>
</table>
</form>
</body>
</html>