<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    /* 변경요청자 IP */
    String req_ip = request.getRemoteAddr(); 
%>
<html>
<head>  
<title>KICC EASYPAY 8.0 SAMPLE</title>
<meta name="robots" content="noindex, nofollow"> 
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/easypay/web/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/default.js" type="text/javascript"></script>
<script type="text/javascript">

    function f_submit() {
        var frm_mgr = document.frm_mgr;
        
        var bRetVal = false;
        
        /*  변경정보 확인 */
        if( !frm_mgr.org_cno.value ) {
            alert("PG거래번호를 입력하세요!!");
            frm_mgr.org_cno.focus();
            return;
        }
        
        if( !frm_mgr.req_id.value ) {
            alert("요청자ID를 입력하세요!!");
            frm_mgr.req_id.focus();
            return;
        }

        frm_mgr.submit();
    }
</script>
</head>
<body>
<form name="frm_mgr" method="post" action="../easypay_request.jsp">

<!-- [필수]거래구분(수정불가) -->
<input type="hidden" name="EP_tr_cd" value="00201000">
<!-- [필수]요청자 IP -->
<input type="hidden" name="req_ip" value="<%=req_ip%>">

<table border="0" width="910" cellpadding="10" cellspacing="0">
<tr>
    <td>
    <!-- title start -->
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" bgcolor="#FFFFFF" align="left">&nbsp;<img src="../img/arow3.gif" border="0" align="absmiddle">&nbsp;일반 > <b>변경</b></td>
    </tr>
    <tr>
        <td height="2" bgcolor="#2D4677"></td>
    </tr>
    </table>
    <!-- title end -->
    
    <!-- mgr start -->
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" bgcolor="#FFFFFF">&nbsp;<img src="../img/arow2.gif" border="0" align="absmiddle">&nbsp;<b>변경정보</b>(*필수)</td>
    </tr>
    </table>
    <table border="0" width="900" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">
    <tr height="25">
        <td bgcolor="#EDEDED" width="150">&nbsp;*가맹점아이디</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="EP_mall_id" value="T5102001" size="10" class="input_F"></td>
        <td bgcolor="#EDEDED" width="150">&nbsp;*거래구분</td>
        <td bgcolor="#FFFFFF" width="300" >&nbsp;
            <select name="mgr_txtype" class="input_F">
                <option value="20" >매입요청</option>
                <option value="30" >매입취소</option>
                <option value="31" >부분매입취소</option>
                <option value="32" >부분승인취소</option>
                <option value="40" selected>즉시취소</option>
            </select>
        </td>
    </tr>
    <tr height="25">
        <!-- [필수] PG거래번호 -->
        <td bgcolor="#EDEDED" width="150">&nbsp;*PG거래번호</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="org_cno" size="50" class="input_F"></td>
        <!-- [필수] 요청자ID -->
        <td bgcolor="#EDEDED" width="150">&nbsp;*요청자ID</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="req_id" size="50" class="input_F"></td>
    </tr>
    <tr height="25">
        <!-- [옵션] 변경사유 -->
        <td bgcolor="#EDEDED" width="150">&nbsp;변경사유</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="mgr_msg" size="50" class="input_A"></td>
        <!-- [옵션] 금액 -->
        <td bgcolor="#EDEDED" width="150">&nbsp;금액</td>
        <td bgcolor="#FFFFFF" width="300">&nbsp;<input type="text" name="mgr_amt" size="50" class="input_A"></td>
    </tr>
    </table>
    <!-- mgr Data END -->
    
    <table border="0" width="900" cellpadding="0" cellspacing="0">
    <tr>       
        <td height="30" align="center" bgcolor="#FFFFFF"><input type="button" value="변 경" class="input_D" style="cursor:hand;" onclick="javascript:f_submit();"></td>
    </tr>
    </table>
    </td>
</tr>
</table>
</form>
</body>
</html>