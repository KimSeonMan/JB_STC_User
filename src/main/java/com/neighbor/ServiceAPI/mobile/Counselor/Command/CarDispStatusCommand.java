package com.neighbor.ServiceAPI.mobile.Counselor.Command;

import com.neighbor.ServiceAPI.mobile.Counselor.Page.CarDispStatusPage;

public class CarDispStatusCommand {

	private String rceptSeCd = "ALL";
	private String mber_name;
	private String caralcSttusCd = "ALL";
	private String cnterCd = "ALL";
	private String chrgCnterCd = "ALL";
	private String mber_id;
	private String cnter_cd;
	private String resve_dt;
	private String resve_date;
	private String resve_time;
	private String resve_min;
	private String rm;
	private String brdng_nmpr;
	private String aloc_adres;
	private String strtpnt_adres;
	private String roundtrip_at = "N";
	private String mvmn_purps_cd="ALL";
	private String wheelchair_se_cd = "ALL";
	private String mvmnTyCd;
	
	private String vhcleSearchRadius;		//차량 반경
	private String sel_lc_crdnt_x;			//출발지 x
	private String sel_lc_crdnt_y;			//출발지 y
	
	private String search_cnter_cd;
	
	//오프라인 예약
	private String start_lc_crdnt_x;
	private String start_lc_crdnt_y;
	private String arvl_lc_crdnt_x;
	private String arvl_lc_crdnt_y;
	private String start_lc_crdnt_x2;
	private String start_lc_crdnt_y2;
	private String arvl_lc_crdnt_x2;
	private String arvl_lc_crdnt_y2;
	private String sel_rcept_se_cd;
	private String rbtrnsp_trnsit_at;
	private String sel_resve_date;
	private String sel_resve_time;
	private String sel_resve_min;
	private String sel_strtpnt_adres;
	private String sel_aloc_adres;
	private String sel_mvmn_purps_cd;
	private String sel_wheelchair_se_cd;
	private String sel_brdng_nmpr;
	private String sel_rm;
	private String sel_roundtrip_at;
	
	private String chrg_cnter_cd;
	private String mvmn_ty_cd;
	private String chrg_cnter_cd2;
	private String mvmn_ty_cd2;
	
	private String mber_se_nm;
	private String mber_nm;
	private String sms_recptn_at;
	private String area_no_se_cd;
	private String telMiddle;
	private String telLast;
	private String mbtlnum_se_cd;
	private String mbtlMiddle;
	private String mbtlLast;
	private String zip;
	private String adres;
	private String adres_detail;
	private String emailId;
	private String emailDomain;
	private String email_se_cd;
	private String trobl_knd_cd;
	private String trobl_grad;
	private String wheelchair_use_pd;
	private String asstn_person_ennc;
	private String asstn_need_at;
	private String asstn_person_ennc_nm;
	private String ds_mlrd_posbl_at;
	private String info_cnter_cd;
	private String searchWord;
	
	private String sessionCnterCd;
	
	private String sctn_begin_time;
	private String sctn_end_time;
	private String beffat_resve_posbl_de;
	private String today;
	
	private String vhcle_ty_cd = "ALL";
	private String drver_sttus_cd = "ALL";
	
	private String select_date;		//예약 요일
	private String select_time;		//예약시
	
	private String cnrs_cnter_cd;
	private String cnrs_se_cd;
	private String resve_dt_arr;		//예약 건의 도착 예상시간 + 기준설정시간 (예약할때 사용)
	private String resve_dt_def;		//예약시간 - 기준설정시간 (예약할때 사용)
	private String copertn_caralc_at;		//공동배차 차량 여부
	private String sel_vhcle_no;
	
	private CarDispStatusPage page = new CarDispStatusPage();
	public String getRceptSeCd() {
		return rceptSeCd;
	}
	public void setRceptSeCd(String rceptSeCd) {
		this.rceptSeCd = rceptSeCd;
	}
	public String getMber_name() {
		return mber_name;
	}
	public void setMber_name(String mber_name) {
		this.mber_name = mber_name;
	}
	public CarDispStatusPage getPage() {
		return page;
	}
	public void setPage(CarDispStatusPage page) {
		this.page = page;
	}
	public String getCaralcSttusCd() {
		return caralcSttusCd;
	}
	public void setCaralcSttusCd(String caralcSttusCd) {
		this.caralcSttusCd = caralcSttusCd;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getResve_dt() {
		return resve_dt;
	}
	public void setResve_dt(String resve_dt) {
		this.resve_dt = resve_dt;
	}
	public String getCnterCd() {
		return cnterCd;
	}
	public void setCnterCd(String cnterCd) {
		this.cnterCd = cnterCd;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getBrdng_nmpr() {
		return brdng_nmpr;
	}
	public void setBrdng_nmpr(String brdng_nmpr) {
		this.brdng_nmpr = brdng_nmpr;
	}
	public String getAloc_adres() {
		return aloc_adres;
	}
	public void setAloc_adres(String aloc_adres) {
		this.aloc_adres = aloc_adres;
	}
	public String getStrtpnt_adres() {
		return strtpnt_adres;
	}
	public void setStrtpnt_adres(String strtpnt_adres) {
		this.strtpnt_adres = strtpnt_adres;
	}
	public String getResve_date() {
		return resve_date;
	}
	public void setResve_date(String resve_date) {
		this.resve_date = resve_date;
	}
	public String getRoundtrip_at() {
		return roundtrip_at;
	}
	public void setRoundtrip_at(String roundtrip_at) {
		this.roundtrip_at = roundtrip_at;
	}
	public String getMvmn_purps_cd() {
		return mvmn_purps_cd;
	}
	public void setMvmn_purps_cd(String mvmn_purps_cd) {
		this.mvmn_purps_cd = mvmn_purps_cd;
	}
	public String getWheelchair_se_cd() {
		return wheelchair_se_cd;
	}
	public void setWheelchair_se_cd(String wheelchair_se_cd) {
		this.wheelchair_se_cd = wheelchair_se_cd;
	}
	public String getResve_min() {
		return resve_min;
	}
	public void setResve_min(String resve_min) {
		this.resve_min = resve_min;
	}
	public String getResve_time() {
		return resve_time;
	}
	public void setResve_time(String resve_time) {
		this.resve_time = resve_time;
	}
	public String getVhcleSearchRadius() {
		return vhcleSearchRadius;
	}
	public void setVhcleSearchRadius(String vhcleSearchRadius) {
		this.vhcleSearchRadius = vhcleSearchRadius;
	}
	public String getSel_lc_crdnt_x() {
		return sel_lc_crdnt_x;
	}
	public void setSel_lc_crdnt_x(String sel_lc_crdnt_x) {
		this.sel_lc_crdnt_x = sel_lc_crdnt_x;
	}
	public String getSel_lc_crdnt_y() {
		return sel_lc_crdnt_y;
	}
	public void setSel_lc_crdnt_y(String sel_lc_crdnt_y) {
		this.sel_lc_crdnt_y = sel_lc_crdnt_y;
	}
	public String getSearch_cnter_cd() {
		return search_cnter_cd;
	}
	public void setSearch_cnter_cd(String search_cnter_cd) {
		this.search_cnter_cd = search_cnter_cd;
	}
	public String getStart_lc_crdnt_x() {
		return start_lc_crdnt_x;
	}
	public void setStart_lc_crdnt_x(String start_lc_crdnt_x) {
		this.start_lc_crdnt_x = start_lc_crdnt_x;
	}
	public String getStart_lc_crdnt_y() {
		return start_lc_crdnt_y;
	}
	public void setStart_lc_crdnt_y(String start_lc_crdnt_y) {
		this.start_lc_crdnt_y = start_lc_crdnt_y;
	}
	public String getArvl_lc_crdnt_x() {
		return arvl_lc_crdnt_x;
	}
	public void setArvl_lc_crdnt_x(String arvl_lc_crdnt_x) {
		this.arvl_lc_crdnt_x = arvl_lc_crdnt_x;
	}
	public String getArvl_lc_crdnt_y() {
		return arvl_lc_crdnt_y;
	}
	public void setArvl_lc_crdnt_y(String arvl_lc_crdnt_y) {
		this.arvl_lc_crdnt_y = arvl_lc_crdnt_y;
	}

	public String getSel_rcept_se_cd() {
		return sel_rcept_se_cd;
	}
	public void setSel_rcept_se_cd(String sel_rcept_se_cd) {
		this.sel_rcept_se_cd = sel_rcept_se_cd;
	}
	public String getRbtrnsp_trnsit_at() {
		return rbtrnsp_trnsit_at;
	}
	public void setRbtrnsp_trnsit_at(String rbtrnsp_trnsit_at) {
		this.rbtrnsp_trnsit_at = rbtrnsp_trnsit_at;
	}
	public String getSel_resve_date() {
		return sel_resve_date;
	}
	public void setSel_resve_date(String sel_resve_date) {
		this.sel_resve_date = sel_resve_date;
	}
	public String getSel_resve_time() {
		return sel_resve_time;
	}
	public void setSel_resve_time(String sel_resve_time) {
		this.sel_resve_time = sel_resve_time;
	}
	public String getSel_resve_min() {
		return sel_resve_min;
	}
	public void setSel_resve_min(String sel_resve_min) {
		this.sel_resve_min = sel_resve_min;
	}
	public String getSel_strtpnt_adres() {
		return sel_strtpnt_adres;
	}
	public void setSel_strtpnt_adres(String sel_strtpnt_adres) {
		this.sel_strtpnt_adres = sel_strtpnt_adres;
	}
	public String getSel_aloc_adres() {
		return sel_aloc_adres;
	}
	public void setSel_aloc_adres(String sel_aloc_adres) {
		this.sel_aloc_adres = sel_aloc_adres;
	}
	public String getSel_mvmn_purps_cd() {
		return sel_mvmn_purps_cd;
	}
	public void setSel_mvmn_purps_cd(String sel_mvmn_purps_cd) {
		this.sel_mvmn_purps_cd = sel_mvmn_purps_cd;
	}
	public String getSel_wheelchair_se_cd() {
		return sel_wheelchair_se_cd;
	}
	public void setSel_wheelchair_se_cd(String sel_wheelchair_se_cd) {
		this.sel_wheelchair_se_cd = sel_wheelchair_se_cd;
	}
	public String getSel_brdng_nmpr() {
		return sel_brdng_nmpr;
	}
	public void setSel_brdng_nmpr(String sel_brdng_nmpr) {
		this.sel_brdng_nmpr = sel_brdng_nmpr;
	}
	public String getSel_rm() {
		return sel_rm;
	}
	public void setSel_rm(String sel_rm) {
		this.sel_rm = sel_rm;
	}
	public String getMber_se_nm() {
		return mber_se_nm;
	}
	public void setMber_se_nm(String mber_se_nm) {
		this.mber_se_nm = mber_se_nm;
	}
	public String getMber_nm() {
		return mber_nm;
	}
	public void setMber_nm(String mber_nm) {
		this.mber_nm = mber_nm;
	}
	public String getSms_recptn_at() {
		return sms_recptn_at;
	}
	public void setSms_recptn_at(String sms_recptn_at) {
		this.sms_recptn_at = sms_recptn_at;
	}
	public String getArea_no_se_cd() {
		return area_no_se_cd;
	}
	public void setArea_no_se_cd(String area_no_se_cd) {
		this.area_no_se_cd = area_no_se_cd;
	}
	public String getTelMiddle() {
		return telMiddle;
	}
	public void setTelMiddle(String telMiddle) {
		this.telMiddle = telMiddle;
	}
	public String getTelLast() {
		return telLast;
	}
	public void setTelLast(String telLast) {
		this.telLast = telLast;
	}
	public String getMbtlnum_se_cd() {
		return mbtlnum_se_cd;
	}
	public void setMbtlnum_se_cd(String mbtlnum_se_cd) {
		this.mbtlnum_se_cd = mbtlnum_se_cd;
	}
	public String getMbtlMiddle() {
		return mbtlMiddle;
	}
	public void setMbtlMiddle(String mbtlMiddle) {
		this.mbtlMiddle = mbtlMiddle;
	}
	public String getMbtlLast() {
		return mbtlLast;
	}
	public void setMbtlLast(String mbtlLast) {
		this.mbtlLast = mbtlLast;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getAdres_detail() {
		return adres_detail;
	}
	public void setAdres_detail(String adres_detail) {
		this.adres_detail = adres_detail;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailDomain() {
		return emailDomain;
	}
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
	public String getEmail_se_cd() {
		return email_se_cd;
	}
	public void setEmail_se_cd(String email_se_cd) {
		this.email_se_cd = email_se_cd;
	}
	public String getTrobl_knd_cd() {
		return trobl_knd_cd;
	}
	public void setTrobl_knd_cd(String trobl_knd_cd) {
		this.trobl_knd_cd = trobl_knd_cd;
	}
	public String getTrobl_grad() {
		return trobl_grad;
	}
	public void setTrobl_grad(String trobl_grad) {
		this.trobl_grad = trobl_grad;
	}
	public String getWheelchair_use_pd() {
		return wheelchair_use_pd;
	}
	public void setWheelchair_use_pd(String wheelchair_use_pd) {
		this.wheelchair_use_pd = wheelchair_use_pd;
	}
	public String getAsstn_person_ennc() {
		return asstn_person_ennc;
	}
	public void setAsstn_person_ennc(String asstn_person_ennc) {
		this.asstn_person_ennc = asstn_person_ennc;
	}
	public String getAsstn_need_at() {
		return asstn_need_at;
	}
	public void setAsstn_need_at(String asstn_need_at) {
		this.asstn_need_at = asstn_need_at;
	}
	public String getAsstn_person_ennc_nm() {
		return asstn_person_ennc_nm;
	}
	public void setAsstn_person_ennc_nm(String asstn_person_ennc_nm) {
		this.asstn_person_ennc_nm = asstn_person_ennc_nm;
	}
	public String getDs_mlrd_posbl_at() {
		return ds_mlrd_posbl_at;
	}
	public void setDs_mlrd_posbl_at(String ds_mlrd_posbl_at) {
		this.ds_mlrd_posbl_at = ds_mlrd_posbl_at;
	}
	public String getSel_roundtrip_at() {
		return sel_roundtrip_at;
	}
	public void setSel_roundtrip_at(String sel_roundtrip_at) {
		this.sel_roundtrip_at = sel_roundtrip_at;
	}
	public String getChrg_cnter_cd() {
		return chrg_cnter_cd;
	}
	public void setChrg_cnter_cd(String chrg_cnter_cd) {
		this.chrg_cnter_cd = chrg_cnter_cd;
	}
	public String getMvmn_ty_cd() {
		return mvmn_ty_cd;
	}
	public void setMvmn_ty_cd(String mvmn_ty_cd) {
		this.mvmn_ty_cd = mvmn_ty_cd;
	}
	public String getInfo_cnter_cd() {
		return info_cnter_cd;
	}
	public void setInfo_cnter_cd(String info_cnter_cd) {
		this.info_cnter_cd = info_cnter_cd;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getSessionCnterCd() {
		return sessionCnterCd;
	}
	public void setSessionCnterCd(String sessionCnterCd) {
		this.sessionCnterCd = sessionCnterCd;
	}
	public String getStart_lc_crdnt_x2() {
		return start_lc_crdnt_x2;
	}
	public void setStart_lc_crdnt_x2(String start_lc_crdnt_x2) {
		this.start_lc_crdnt_x2 = start_lc_crdnt_x2;
	}
	public String getStart_lc_crdnt_y2() {
		return start_lc_crdnt_y2;
	}
	public void setStart_lc_crdnt_y2(String start_lc_crdnt_y2) {
		this.start_lc_crdnt_y2 = start_lc_crdnt_y2;
	}
	public String getArvl_lc_crdnt_x2() {
		return arvl_lc_crdnt_x2;
	}
	public void setArvl_lc_crdnt_x2(String arvl_lc_crdnt_x2) {
		this.arvl_lc_crdnt_x2 = arvl_lc_crdnt_x2;
	}
	public String getArvl_lc_crdnt_y2() {
		return arvl_lc_crdnt_y2;
	}
	public void setArvl_lc_crdnt_y2(String arvl_lc_crdnt_y2) {
		this.arvl_lc_crdnt_y2 = arvl_lc_crdnt_y2;
	}
	public String getChrgCnterCd() {
		return chrgCnterCd;
	}
	public void setChrgCnterCd(String chrgCnterCd) {
		this.chrgCnterCd = chrgCnterCd;
	}
	public String getChrg_cnter_cd2() {
		return chrg_cnter_cd2;
	}
	public void setChrg_cnter_cd2(String chrg_cnter_cd2) {
		this.chrg_cnter_cd2 = chrg_cnter_cd2;
	}
	public String getMvmn_ty_cd2() {
		return mvmn_ty_cd2;
	}
	public void setMvmn_ty_cd2(String mvmn_ty_cd2) {
		this.mvmn_ty_cd2 = mvmn_ty_cd2;
	}
	public String getSctn_begin_time() {
		return sctn_begin_time;
	}
	public void setSctn_begin_time(String sctn_begin_time) {
		this.sctn_begin_time = sctn_begin_time;
	}
	public String getSctn_end_time() {
		return sctn_end_time;
	}
	public void setSctn_end_time(String sctn_end_time) {
		this.sctn_end_time = sctn_end_time;
	}
	public String getBeffat_resve_posbl_de() {
		return beffat_resve_posbl_de;
	}
	public void setBeffat_resve_posbl_de(String beffat_resve_posbl_de) {
		this.beffat_resve_posbl_de = beffat_resve_posbl_de;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getVhcle_ty_cd() {
		return vhcle_ty_cd;
	}
	public void setVhcle_ty_cd(String vhcle_ty_cd) {
		this.vhcle_ty_cd = vhcle_ty_cd;
	}
	public String getSelect_date() {
		return select_date;
	}
	public void setSelect_date(String select_date) {
		this.select_date = select_date;
	}
	public String getSelect_time() {
		return select_time;
	}
	public void setSelect_time(String select_time) {
		this.select_time = select_time;
	}
	public String getDrver_sttus_cd() {
		return drver_sttus_cd;
	}
	public void setDrver_sttus_cd(String drver_sttus_cd) {
		this.drver_sttus_cd = drver_sttus_cd;
	}
	public String getCnrs_cnter_cd() {
		return cnrs_cnter_cd;
	}
	public void setCnrs_cnter_cd(String cnrs_cnter_cd) {
		this.cnrs_cnter_cd = cnrs_cnter_cd;
	}
	public String getCnrs_se_cd() {
		return cnrs_se_cd;
	}
	public void setCnrs_se_cd(String cnrs_se_cd) {
		this.cnrs_se_cd = cnrs_se_cd;
	}
	public String getResve_dt_arr() {
		return resve_dt_arr;
	}
	public void setResve_dt_arr(String resve_dt_arr) {
		this.resve_dt_arr = resve_dt_arr;
	}
	public String getResve_dt_def() {
		return resve_dt_def;
	}
	public void setResve_dt_def(String resve_dt_def) {
		this.resve_dt_def = resve_dt_def;
	}
	public String getCopertn_caralc_at() {
		return copertn_caralc_at;
	}
	public void setCopertn_caralc_at(String copertn_caralc_at) {
		this.copertn_caralc_at = copertn_caralc_at;
	}
	public String getMvmnTyCd() {
		return mvmnTyCd;
	}
	public void setMvmnTyCd(String mvmnTyCd) {
		this.mvmnTyCd = mvmnTyCd;
	}
	public String getSel_vhcle_no() {
		return sel_vhcle_no;
	}
	public void setSel_vhcle_no(String sel_vhcle_no) {
		this.sel_vhcle_no = sel_vhcle_no;
	}
}
