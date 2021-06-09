package com.neighbor.ServiceAPI.api.bassInfo;

import com.kicc.*;
import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.exception.ApiException;
import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AutoPayment {

    private static final Log logger = LogFactory.getLog(AutoPayment.class);


    public Map doAutoPayment(String mber_id,String cnter_cd,String total_amt,String batchkey,String mber_ip) throws AbsException {
        // TODO Auto-generated method stub

            Map result = new HashMap();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date today = new Date();

           try {
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
               final String CERT_FILE = "D://Users/dayeo/Desktop/admin/src/main/webapp/easypay/cert";
               final String LOG_DIR = "D://Users/dayeo/Desktop/admin/src/main/webapp/easypay/log";
               final int LOG_LEVEL                 = 1;

               /* -------------------------------------------------------------------------- */
               /* ::: 승인요청 정보 설정                                                     */
               /* -------------------------------------------------------------------------- */
               //[헤더]
               String tr_cd            = "00101000";                                                 // [필수]요청구분 <!-- 거래구분(수정불가) -->
               String order_no         = "ORDER_" + today;         // [필수]주문번호
               String mall_id          = "T5102001";               // [필수]몰아이디

               /* -------------------------------------------------------------------------- */
               /* ::: 결제요청 - 가맹점 주문정보                                             */
               /* -------------------------------------------------------------------------- */
               String memb_user_no     = "";              // [선택]가맹점 고객일련번호
               String user_id          = mber_id;         // [선택]고객 ID
               String user_nm          = "";              // [필수]고객명
               String user_mail        = "";              // [필수]고객 E-mail
               String user_phone1      = "";              // [필수]가맹점 고객 연락처1
               String user_phone2      = "";              // [선택]가맹점 고객 연락처2
               String user_addr        = "";              // [선택]가맹점 고객 주소
               String product_type     = "1";             // [필수]상품정보구분[0:실물,1:컨텐츠]
               String product_nm       = "교통비";         // [필수]상품명
               String product_amt      = total_amt;         // [필수]상품금액

               /* -------------------------------------------------------------------------- */
               /* ::: 배치결제 정보 설정                                                     */
               /* -------------------------------------------------------------------------- */
               String pay_type         = "batch";                                                    // [필수]결제수단 <!-- 결제수단(수정불가) -->
               String tot_amt          = total_amt;                                                  // [필수]총결제금액
               String currency         = "00";                                                       // [필수]통화코드
               String client_ip        = mber_ip;                                                    // [필수]고객 IP
               String cli_ver          = "W8";                                                       // [필수]클라이언트 버젼(W8로 고정)
               String complex_yn       = "N";                                                        // [필수]복합결제유무(배치결제는 사용 안함)
               String escrow_yn        = "N";                                                        // [필수]에스크로 여부(배치결제는 사용 안함)

               String card_txtype      = "41";                                                       // [필수]처리구분
               String req_type         = "0";                                                        // [필수]결제종류
               String card_amt         = tot_amt;        // [필수]결제금액
               String wcc              = "@";             // [필수]WCC
               String card_no          = batchkey;         // [필수]배치키
               String install_period   = "00";  // [필수]할부개월
               String noint            = "00";           // [필수]무이자여부

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
                   easyPayClient.easypay_deli_us( easypay_order_data_item, "product_nm"		    , product_nm 	);
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
               // r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //에스크로 사용유무
               // r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //복합결제 유무
               r_stat_cd            = easyPayClient.easypay_get_res( "stat_cd"         );     //상태코드
               r_stat_msg           = easyPayClient.easypay_get_res( "stat_msg"        );     //상태메시지
               // r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //결제수단
               // r_mall_id          = easyPayClient.easypay_get_res( "memb_id"         );     //가맹점ID
               // r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //카드번호
//            r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //발급사코드
//            r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //발급사명
//            r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //매입사코드
//            r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //매입사명
//            r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //할부개월
//            r_noint            = easyPayClient.easypay_get_res( "noint"           );     //무이자여부
//            r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //부분취소 가능여부
//            r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //신용카드 종류
//            r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //신용카드 구분
//            r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //장바구니 결제여부
//            r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //매입취소일시
//            r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //취소일시
//            r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //환불예정일시


               /* -------------------------------------------------------------------------- */
               /* ::: 가맹점 DB 처리                                                         */
               /* -------------------------------------------------------------------------- */
               /* 응답코드(res_cd)가 "0000" 이면 정상승인 입니다.                            */
               /* r_amount가 주문DB의 금액과 다를 시 반드시 취소 요청을 하시기 바랍니다.     */
               /* DB 처리 실패 시 취소 처리를 해주시기 바랍니다.                             */
               /* -------------------------------------------------------------------------- */

               if ( r_res_cd.equals("0000") )
               {
                   result.put("err_cd","0");
                   result.put("err_msg",r_res_msg);
               }
               else {
                   result.put("err_cd",r_res_cd);
                   result.put("err_msg",r_res_msg);
               }

            /*
            String bDBProc = "";  //가맹점 DB처리 성공여부
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

                        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno" , r_cno 		); // pg거래번호
                        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "order_no", order_no 		); // 주문번호
                        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"  , mber_ip);
                        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_id"  , "MALL_R_TRANS" );
                        easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_msg" , "DB 처리 실패로 망취소"  );

                        easyPayClient.easypay_run( mall_id, tr_cd, order_no );

                        r_res_cd = easyPayClient.res_cd;
                        r_res_msg = easyPayClient.res_msg;
                        r_cno       = easyPayClient.easypay_get_res( "cno"             );    // PG거래번호
                        r_canc_date = easyPayClient.easypay_get_res( "canc_date"       );    //취소일시
                    }
                }
           */
           }
           catch (AbsAPIException e) {
               logger.error(e);
               throw e;
           } catch (IllegalArgumentException e) {
               logger.error(e);
               throw new ApiException("Input Error");
           } catch (Exception e) {
               logger.error(e);
               throw new ApiException("Error");
           }
           return  result;
    }

    public String getNullToSpace(String param) {
        return (param == null) ? "" : param.trim();
    }
}
