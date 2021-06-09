<!--KICC와 전문통신페이지-->
<!--메뉴얼 '승인페이지 작성' 승인요청/승인응답 파라미터 포함.-->

<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.security.Security"%>
<%@ page import="com.kicc.*" %>
<%!
    /**
     * 파라미터 체크 메소드
     */
    public String getNullToSpace(String param) {
        return (param == null) ? "" : param.trim();
    }
%>
<%
    /* -------------------------------------------------------------------------- */
    /* ::: 처리구분 설정                                                          */
    /* -------------------------------------------------------------------------- */
    final String TRAN_CD_NOR_PAYMENT    = "00101000";   // 승인(일반)
    final String TRAN_CD_NOR_MGR        = "00201000";   // 변경(일반)
    
    /* -------------------------------------------------------------------------- */
    /* ::: 쇼핑몰 지불 정보 설정                                                  */
    /* -------------------------------------------------------------------------- */
    final String GW_URL                 = "testgw.easypay.co.kr";  // Gateway URL ( test )
    //final String GW_URL               = "gw.easypay.co.kr";      // Gateway URL ( real )
    final String GW_PORT                = "80";                    // 포트번호(변경불가) 

    /* -------------------------------------------------------------------------- */ 
    /* ::: 지불 데이터 셋업 (업체에 맞게 수정)                                    */ 
    /* -------------------------------------------------------------------------- */ 
    /* ※ 주의 ※                                                                 */ 
    /* cert_file 변수 설정                                                        */
    /* - pg_cert.pem 파일이 있는 디렉토리의  절대 경로 설정                       */ 
    /* log_dir 변수 설정                                                          */
    /* - log 디렉토리 설정                                                        */
    /* log_level 변수 설정                                                        */
    /* - log 레벨 설정  (1 to 99 (높을수록 상세) )                                */
    /* -------------------------------------------------------------------------- */
    final String CERT_FILE = "D://Users/dayeo/Desktop/user/user-master/src/main/webapp/easypay/cert";
    final String LOG_DIR = "D://Users/dayeo/Desktop/user/user-master/src/main/webapp/easypay/log";
    final int LOG_LEVEL                 = 1;
    
    /* -------------------------------------------------------------------------- */
    /* ::: 승인요청 정보 설정                                                     */
    /* -------------------------------------------------------------------------- */
    //[헤더]
    String tr_cd            = getNullToSpace(request.getParameter("EP_tr_cd"));           // [필수]요청구분
    String trace_no         = getNullToSpace(request.getParameter("EP_trace_no"));        // [필수]추적고유번호
    String order_no         = getNullToSpace(request.getParameter("EP_order_no"));        // [필수]주문번호
    String mall_id          = getNullToSpace(request.getParameter("EP_mall_id"));         // [필수]몰아이디
    //[공통]
    String sessionkey       = getNullToSpace(request.getParameter("EP_sessionkey"));      // [필수]암호화키
    String encrypt_data     = getNullToSpace(request.getParameter("EP_encrypt_data"));    // [필수]암호화 데이타  
    
    /* -------------------------------------------------------------------------- */
    /* ::: 결제요청 - 가맹점 주문정보                                             */
    /* -------------------------------------------------------------------------- */    
    String memb_user_no     = getNullToSpace(request.getParameter("EP_memb_user_no"));     // [선택]가맹점 고객일련번호
    String user_id          = getNullToSpace(request.getParameter("EP_user_id"));          // [선택]고객 ID
    String user_nm          = getNullToSpace(request.getParameter("EP_user_name"));        // [필수]고객명
    String user_mail        = getNullToSpace(request.getParameter("EP_user_mail"));        // [필수]고객 E-mail
    String user_phone1      = getNullToSpace(request.getParameter("EP_user_phone1"));      // [필수]가맹점 고객 연락처1
    String user_phone2      = getNullToSpace(request.getParameter("EP_user_phone2"));      // [선택]가맹점 고객 연락처2
    String user_addr        = getNullToSpace(request.getParameter("EP_user_addr"));        // [선택]가맹점 고객 주소
    String product_type     = getNullToSpace(request.getParameter("EP_product_type"));     // [필수]상품정보구분[0:실물,1:컨텐츠]
    String product_nm       = getNullToSpace(request.getParameter("EP_product_nm"));       // [필수]상품명
    String product_amt      = getNullToSpace(request.getParameter("EP_product_amt"));      // [필수]상품금액

    /* -------------------------------------------------------------------------- */
    /* ::: 배치결제 정보 설정                                                     */
    /* -------------------------------------------------------------------------- */
    String pay_type         = getNullToSpace(request.getParameter("EP_pay_type"));        // [필수]결제수단
    String tot_amt          = getNullToSpace(request.getParameter("EP_tot_amt"));         // [필수]총결제금액
    String currency         = getNullToSpace(request.getParameter("EP_currency"));        // [필수]통화코드
    String client_ip        = request.getRemoteAddr();                                    // [필수]고객 IP
    String cli_ver          = "W8";                                                       // [필수]클라이언트 버젼(W8로 고정)
    String complex_yn       = "N";                                                        // [필수]복합결제유무(배치결제는 사용 안함)    
    String escrow_yn        = "N";                                                        // [필수]에스크로 여부(배치결제는 사용 안함)
    
    String card_txtype      = getNullToSpace(request.getParameter("EP_card_txtype"));     // [필수]처리구분
    String req_type         = getNullToSpace(request.getParameter("EP_req_type"));        // [필수]결제종류
    String card_amt         = getNullToSpace(request.getParameter("EP_card_amt"));        // [필수]결제금액
    String wcc              = getNullToSpace(request.getParameter("EP_wcc"));             // [필수]WCC
    String card_no          = getNullToSpace(request.getParameter("EP_card_no"));         // [필수]배치키
    String install_period   = getNullToSpace(request.getParameter("EP_install_period"));  // [필수]할부개월
    String noint            = getNullToSpace(request.getParameter("EP_noint"));           // [필수]무이자여부
        
    /* -------------------------------------------------------------------------- */
    /* ::: 변경관리 정보 설정                                                     */
    /* -------------------------------------------------------------------------- */
    String mgr_txtype       = getNullToSpace(request.getParameter("mgr_txtype"));       // [필수]거래구분
    String mgr_subtype      = getNullToSpace(request.getParameter("mgr_subtype"));      // [선택]변경세부구분
    String org_cno          = getNullToSpace(request.getParameter("org_cno"));          // [필수]원거래고유번호
    String mgr_amt          = getNullToSpace(request.getParameter("mgr_amt"));          // [선택]금액
    String mgr_rem_amt      = getNullToSpace(request.getParameter("mgr_rem_amt"));      // [선택]부분취소 잔액
    String mgr_msg          = getNullToSpace(request.getParameter("mgr_msg"));          // [선택]변경 사유
    
    /* -------------------------------------------------------------------------- */
    /* ::: 전문                                                                   */
    /* -------------------------------------------------------------------------- */
    String mgr_data    = "";     // 변경정보
    String tx_req_data = "";     // 요청전문
    
    /* -------------------------------------------------------------------------- */
    /* ::: 결제 결과                                                              */
    /* -------------------------------------------------------------------------- */
    String r_res_cd             = "";     //응답코드
    String r_res_msg            = "";     //응답메시지
    String r_cno                = "";     //PG거래번호
    String r_amount             = "";     //총 결제금액
    String r_order_no           = "";     //주문번호
    String r_auth_no            = "";     //승인번호
    String r_tran_date          = "";     //승인일시
    String r_escrow_yn          = "";     //에스크로 사용유무
    String r_complex_yn         = "";     //복합결제 유무
    String r_stat_cd            = "";     //상태코드
    String r_stat_msg           = "";     //상태메시지
    String r_pay_type           = "";     //결제수단
    String r_mall_id            = "";     //가맹점ID
    String r_card_no            = "";     //카드번호
    String r_issuer_cd          = "";     //발급사코드
    String r_issuer_nm          = "";     //발급사명
    String r_acquirer_cd        = "";     //매입사코드
    String r_acquirer_nm        = "";     //매입사명
    String r_install_period     = "";     //할부개월
    String r_noint              = "";     //무이자여부
    String r_part_cancel_yn     = "";     //부분취소 가능여부
    String r_card_gubun         = "";     //신용카드 종류
    String r_card_biz_gubun     = "";     //신용카드 구분
    String r_bk_pay_yn          = "";     //장바구니 결제여부
    String r_canc_acq_date      = "";     //매입취소일시
    String r_canc_date          = "";     //취소일시
    String r_refund_date        = "";     //환불예정일시
    
    /* -------------------------------------------------------------------------- */
    /* ::: EasyPayClient 인스턴스 생성 [변경불가 !!].                             */
    /* -------------------------------------------------------------------------- */
    EasyPayClient easyPayClient = new EasyPayClient();
    easyPayClient.easypay_setenv_init( GW_URL, GW_PORT, CERT_FILE, LOG_DIR, LOG_LEVEL );
    easyPayClient.easypay_reqdata_init();
    
    /* -------------------------------------------------------------------------- */
    /* ::: 승인요청                                                               */
    /* -------------------------------------------------------------------------- */
    if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) ){
    	
    	/* ---------------------------------------------------------------------- */
        /* ::: 인증요청                                                           */
        /* ---------------------------------------------------------------------- */
        if( pay_type.equals("81") ) { //배치인증
    	
            easyPayClient.easypay_set_trace_no(trace_no);
            easyPayClient.easypay_encdata_set(encrypt_data, sessionkey);
        
        } 
        else  //배치승인 
        {        	
        /* ---------------------------------------------------------------------- */
        /* ::: 승인요청                                                           */
        /* ---------------------------------------------------------------------- */         
	        // 결제 주문 정보 DATA
            int easypay_order_data_item;
            easypay_order_data_item = easyPayClient.easypay_item( "order_data" );
        
            easyPayClient.easypay_deli_us( easypay_order_data_item, "order_no"			, order_no 		);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "memb_user_no"		, memb_user_no 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_id"			, user_id 		);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_nm"			, user_nm 		);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_mail"			, user_mail 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_phone1"		, user_phone1 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_phone2"		, user_phone2 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "user_addr"			, user_addr 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "product_type"		, product_type 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "product_nm"		, product_nm 	);
            easyPayClient.easypay_deli_us( easypay_order_data_item, "product_amt"		, product_amt 	);
            
            // 결제정보 DATA
            int easypay_pay_data_item;
            easypay_pay_data_item = easyPayClient.easypay_item( "pay_data" );
            
            // 결제정보 DATA  - 공통
            int easypay_common_item;
            easypay_common_item = easyPayClient.easypay_item( "common" );
            
            easyPayClient.easypay_deli_us( easypay_common_item, "tot_amt"   , tot_amt       );
            easyPayClient.easypay_deli_us( easypay_common_item, "currency"  , currency      );
            easyPayClient.easypay_deli_us( easypay_common_item, "client_ip" , client_ip     );
            easyPayClient.easypay_deli_us( easypay_common_item, "cli_ver"   , cli_ver       );
            easyPayClient.easypay_deli_us( easypay_common_item, "escrow_yn" , escrow_yn     );
            easyPayClient.easypay_deli_us( easypay_common_item, "complex_yn", complex_yn    );
            easyPayClient.easypay_deli_rs( easypay_pay_data_item , easypay_common_item );  
            
            // 결제정보 DATA  - 신용카드
            int easypay_card_item;

            easypay_card_item = easyPayClient.easypay_item( "card" );

            easyPayClient.easypay_deli_us( easypay_card_item, "card_txtype"    , card_txtype       );
            easyPayClient.easypay_deli_us( easypay_card_item, "req_type"       , req_type          );
            easyPayClient.easypay_deli_us( easypay_card_item, "card_amt"       , card_amt          );
            easyPayClient.easypay_deli_us( easypay_card_item, "card_no"        , card_no           );
            easyPayClient.easypay_deli_us( easypay_card_item, "noint"          , noint             );
            easyPayClient.easypay_deli_us( easypay_card_item, "wcc"            , wcc               );
            easyPayClient.easypay_deli_us( easypay_card_item, "install_period" , install_period    );
            
            easyPayClient.easypay_deli_rs( easypay_pay_data_item , easypay_card_item );
            
        }
        
    /* -------------------------------------------------------------------------- */
    /* ::: 변경관리 요청                                                          */
    /* -------------------------------------------------------------------------- */
    }else if( TRAN_CD_NOR_MGR.equals( tr_cd ) ) {

        int easypay_mgr_data_item;
        easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );

        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype"			, mgr_txtype 	);
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype"			, mgr_subtype 	);
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno"				, org_cno 		);
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_amt"				, mgr_amt		);
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_rem_amt"			, mgr_rem_amt	);
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"				, request.getRemoteAddr() );
        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_msg"				, mgr_msg 		);
    }


    /* -------------------------------------------------------------------------- */
    /* ::: 실행                                                                   */
    /* -------------------------------------------------------------------------- */         
    if ( tr_cd.length() > 0 ) {
        easyPayClient.easypay_run( mall_id, tr_cd, order_no );
        
        r_res_cd = easyPayClient.res_cd;
        r_res_msg = easyPayClient.res_msg;
    } 
    else {
        r_res_cd  = "M114";
        r_res_msg = "연동 오류|tr_cd값이 설정되지 않았습니다.";
    }
    /* -------------------------------------------------------------------------- */
    /* ::: 결과 처리                                                              */
    /* -------------------------------------------------------------------------- */
    r_cno              = easyPayClient.easypay_get_res( "cno"             );     //PG거래번호
    r_amount           = easyPayClient.easypay_get_res( "amount"          );     //총 결제금액
    r_order_no         = easyPayClient.easypay_get_res( "order_no"        );     //주문번호
    r_auth_no          = easyPayClient.easypay_get_res( "auth_no"         );     //승인번호
    r_tran_date        = easyPayClient.easypay_get_res( "tran_date"       );     //승인일시
    r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //에스크로 사용유무
    r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //복합결제 유무
    r_stat_cd          = easyPayClient.easypay_get_res( "stat_cd"         );     //상태코드
    r_stat_msg         = easyPayClient.easypay_get_res( "stat_msg"        );     //상태메시지
    r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //결제수단
    r_mall_id          = easyPayClient.easypay_get_res( "memb_id"         );     //가맹점ID
    r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //카드번호
    r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //발급사코드
    r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //발급사명
    r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //매입사코드
    r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //매입사명
    r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //할부개월
    r_noint            = easyPayClient.easypay_get_res( "noint"           );     //무이자여부
    r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //부분취소 가능여부
    r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //신용카드 종류
    r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //신용카드 구분
    r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //장바구니 결제여부
    r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //매입취소일시
    r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //취소일시
    r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //환불예정일시

    
    /* -------------------------------------------------------------------------- */
    /* ::: 가맹점 DB 처리                                                         */
    /* -------------------------------------------------------------------------- */
    /* 응답코드(res_cd)가 "0000" 이면 정상승인 입니다.                            */
    /* r_amount가 주문DB의 금액과 다를 시 반드시 취소 요청을 하시기 바랍니다.     */
    /* DB 처리 실패 시 취소 처리를 해주시기 바랍니다.                             */
    /* -------------------------------------------------------------------------- */
    String bDBProc = "";  //가맹점 DB처리 성공여부
    
    if ( r_res_cd.equals("0000") )
    {
    	bDBProc = "true";     // DB처리 성공 시 "true", 실패 시 "false"
    	if ( bDBProc.equals("false") )
    	{
    	    // 승인요청이 실패 시 아래 실행
    	    if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) )
    	    {
    	        int easypay_mgr_data_item;
    	        
    	        easyPayClient.easypay_reqdata_init();
    	        
    	        tr_cd = TRAN_CD_NOR_MGR; 
    	        easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype", "40" 	);
    	        
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno" , r_cno 		);
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "order_no", order_no 		);
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"  , request.getRemoteAddr() );
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_id"  , "MALL_R_TRANS" );
    	        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_msg" , "DB 처리 실패로 망취소"  );
    	        
    	        easyPayClient.easypay_run( mall_id, tr_cd, order_no );
    	        
    	        r_res_cd = easyPayClient.res_cd;
    	        r_res_msg = easyPayClient.res_msg;
    	        r_cno       = easyPayClient.easypay_get_res( "cno"             );    // PG거래번호 
    	        r_canc_date = easyPayClient.easypay_get_res( "canc_date"       );    //취소일시
    	    }
    	}
    }
%>
<html>
<meta name="robots" content="noindex, nofollow">
<script type="text/javascript">
    function f_submit(){
        document.frm.submit();
    }
</script>

<body onload="f_submit();">
<form name="frm" method="post" action="./result.jsp">
    <input type="hidden" id="res_cd"           name="res_cd"          value="<%=r_res_cd%>">            <!-- 결과코드 //-->
    <input type="hidden" id="res_msg"          name="res_msg"         value="<%=r_res_msg%>">           <!-- 결과메시지 //-->
    <input type="hidden" id="cno"              name="cno"             value="<%=r_cno%>">               <!-- PG거래번호 //-->
    <input type="hidden" id="amount"           name="amount"          value="<%=r_amount%>">            <!-- 총 결제금액 //-->
    <input type="hidden" id="order_no"         name="order_no"        value="<%=r_order_no%>">          <!-- 주문번호 //-->
    <input type="hidden" id="auth_no"          name="auth_no"         value="<%=r_auth_no%>">           <!-- 승인번호 //-->
    <input type="hidden" id="tran_date"        name="tran_date"       value="<%=r_tran_date%>">         <!-- 승인일시 //-->
    <input type="hidden" id="escrow_yn"        name="escrow_yn"       value="<%=r_escrow_yn%>">         <!-- 에스크로 사용유무 //-->
    <input type="hidden" id="complex_yn"       name="complex_yn"      value="<%=r_complex_yn%>">        <!-- 복합결제 유무 //-->
    <input type="hidden" id="stat_cd"          name="stat_cd"         value="<%=r_stat_cd%>">           <!-- 상태코드 //-->
    <input type="hidden" id="stat_msg"         name="stat_msg"        value="<%=r_stat_msg%>">          <!-- 상태메시지 //-->
    <input type="hidden" id="pay_type"         name="pay_type"        value="<%=r_pay_type%>">          <!-- 결제수단 //-->
    <input type="hidden" id="mall_id"          name="mall_id"         value="<%=r_mall_id%>">           <!-- 가맹점ID //-->
    <input type="hidden" id="card_no"          name="card_no"         value="<%=r_card_no%>">           <!-- 카드번호 //-->
    <input type="hidden" id="issuer_cd"        name="issuer_cd"       value="<%=r_issuer_cd%>">         <!-- 발급사코드 //-->
    <input type="hidden" id="issuer_nm"        name="issuer_nm"       value="<%=r_issuer_nm%>">         <!-- 발급사명 //-->
    <input type="hidden" id="acquirer_cd"      name="acquirer_cd"     value="<%=r_acquirer_cd%>">       <!-- 매입사코드 //-->
    <input type="hidden" id="acquirer_nm"      name="acquirer_nm"     value="<%=r_acquirer_nm%>">       <!-- 매입사명 //-->
    <input type="hidden" id="install_period"   name="install_period"  value="<%=r_install_period%>">    <!-- 할부개월 //-->
    <input type="hidden" id="noint"            name="noint"           value="<%=r_noint%>">             <!-- 무이자여부 //-->
    <input type="hidden" id="part_cancel_yn"   name="part_cancel_yn"  value="<%=r_part_cancel_yn%>">    <!-- 부분취소 가능여부 //-->
    <input type="hidden" id="card_gubun"       name="card_gubun"      value="<%=r_card_gubun%>">        <!-- 신용카드 종류 //-->
    <input type="hidden" id="card_biz_gubun"   name="card_biz_gubun"  value="<%=r_card_biz_gubun%>">    <!-- 신용카드 구분 //-->
    <input type="hidden" id="bk_pay_yn"        name="bk_pay_yn"       value="<%=r_bk_pay_yn%>">         <!-- 장바구니 결제여부 //-->
    <input type="hidden" id="canc_acq_date"    name="canc_acq_date"   value="<%=r_canc_acq_date%>">     <!-- 매입취소일시 //-->
    <input type="hidden" id="canc_date"        name="canc_date"       value="<%=r_canc_date%>">         <!-- 취소일시 //-->
    <input type="hidden" id="refund_date"      name="refund_date"     value="<%=r_refund_date%>">       <!-- 환불예정일시 //-->
</form>
</body>
</html>