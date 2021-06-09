<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%
/* -------------------------------------------------------------------------- */
/* 캐쉬 사용안함                                                              */
/* -------------------------------------------------------------------------- */
response.setHeader("cache-control","no-cache");
response.setHeader("expires","-1");
response.setHeader("pragma","no-cache");

request.setCharacterEncoding("utf-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="robots" content="noindex, nofollow" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
    window.onload = function()
    {
        /* UTF-8 사용가맹점의 경우 한글이 들어가는 값은 모두 decoding 필수 */
        var res_msg = urldecode( "<%=request.getParameter("EP_res_msg") %>" );
        if(window.opener != null)
        {
            window.opener.document.getElementById("EP_res_cd").value         = "<%=request.getParameter("EP_res_cd") %>";
            window.opener.document.getElementById("EP_res_msg").value        = res_msg;
            window.opener.document.getElementById("EP_tr_cd").value          = "<%=request.getParameter("EP_tr_cd") %>";
            window.opener.document.getElementById("EP_ret_pay_type").value   = "<%=request.getParameter("EP_ret_pay_type") %>";
            window.opener.document.getElementById("EP_card_req_type").value  = "<%=request.getParameter("EP_card_req_type") %>";
            window.opener.document.getElementById("EP_trace_no").value       = "<%=request.getParameter("EP_trace_no") %>";
            window.opener.document.getElementById("EP_sessionkey").value     = "<%=request.getParameter("EP_sessionkey") %>";
            window.opener.document.getElementById("EP_encrypt_data").value   = "<%=request.getParameter("EP_encrypt_data") %>";
            <%--window.opener.document.getElementById("EP_card_nick").value      = "<%=request.getParameter("EP_card_nick") %>";--%>

            if( "<%=request.getParameter("EP_res_cd") %>" == "0000" )
            {
                window.opener.f_submit();
                alert( "EP_trace_no = <%=request.getParameter("EP_trace_no") %> : ");
            }
            else
            {
               // alert("aaaaaaa");
                alert( "<%=request.getParameter("EP_res_cd") %> : " + res_msg );
            }
        
            self.close();
        }
        else
        {

            window.parent.document.getElementById("EP_res_cd").value         = "<%=request.getParameter("EP_res_cd") %>";
            window.parent.document.getElementById("EP_res_msg").value        = res_msg;
            window.parent.document.getElementById("EP_tr_cd").value          = "<%=request.getParameter("EP_tr_cd") %>";
            window.parent.document.getElementById("EP_ret_pay_type").value   = "<%=request.getParameter("EP_ret_pay_type") %>";
            window.parent.document.getElementById("EP_card_req_type").value  = "<%=request.getParameter("EP_card_req_type") %>";
            window.parent.document.getElementById("EP_trace_no").value       = "<%=request.getParameter("EP_trace_no") %>";
            window.parent.document.getElementById("EP_sessionkey").value     = "<%=request.getParameter("EP_sessionkey") %>";
            window.parent.document.getElementById("EP_encrypt_data").value   = "<%=request.getParameter("EP_encrypt_data") %>";
            <%--window.parent.document.getElementById("EP_card_nick").value      = "<%=request.getParameter("EP_card_nick") %>";--%>
            
            if( "<%=request.getParameter("EP_res_cd") %>" == "0000" )
            {
                window.parent.f_submit();
            }
            else
            {
                alert( "<%=request.getParameter("EP_res_cd") %> : <%=request.getParameter("EP_res_msg") %>");
            }
            
            window.parent.kicc_popup_close();
        
        }
    }
    
     function urldecode( str )
    {
        // 공백 문자인 + 를 처리하기 위해 +('%20') 을 공백으로 치환
        return decodeURIComponent((str + '').replace(/\+/g, '%20'));
    }
    
</script>
<title>webpay 가맹점 test page</title>
</head>
<body>
</body>
</html>