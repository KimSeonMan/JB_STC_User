package com.neighbor.ServiceAPI.mobile.Counselor.Domain;

public class CaralcSetupInfoDomain {
	private String cnter_cd;					//센터코드
	private String roundtrip_perm_at;			//왕복허용여부
	private String aloc_reqre_time;				//도착지소요시간
	private String vhcle_search_radius;			//차량검색반경
	private String beffat_resve_time_intrvl;	//사전예약시간간격
	private String vhcle_mvmn_posbl_at;			//차량이동가능여부
	private String posbl_dstnc;					//가능거리
	private String wdr_sctn_othinst_mber_use_posbl_at;	//광역구간 타기간 회원 이용가능여부
	private String whthrc_othinst_mber_use_posbl_at;	//관내 타기관 회원 이용 가능 여부
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getRoundtrip_perm_at() {
		return roundtrip_perm_at;
	}
	public void setRoundtrip_perm_at(String roundtrip_perm_at) {
		this.roundtrip_perm_at = roundtrip_perm_at;
	}
	public String getAloc_reqre_time() {
		return aloc_reqre_time;
	}
	public void setAloc_reqre_time(String aloc_reqre_time) {
		this.aloc_reqre_time = aloc_reqre_time;
	}
	public String getVhcle_search_radius() {
		return vhcle_search_radius;
	}
	public void setVhcle_search_radius(String vhcle_search_radius) {
		this.vhcle_search_radius = vhcle_search_radius;
	}
	public String getBeffat_resve_time_intrvl() {
		return beffat_resve_time_intrvl;
	}
	public void setBeffat_resve_time_intrvl(String beffat_resve_time_intrvl) {
		this.beffat_resve_time_intrvl = beffat_resve_time_intrvl;
	}
	public String getVhcle_mvmn_posbl_at() {
		return vhcle_mvmn_posbl_at;
	}
	public void setVhcle_mvmn_posbl_at(String vhcle_mvmn_posbl_at) {
		this.vhcle_mvmn_posbl_at = vhcle_mvmn_posbl_at;
	}
	public String getPosbl_dstnc() {
		return posbl_dstnc;
	}
	public void setPosbl_dstnc(String posbl_dstnc) {
		this.posbl_dstnc = posbl_dstnc;
	}
	public String getWdr_sctn_othinst_mber_use_posbl_at() {
		return wdr_sctn_othinst_mber_use_posbl_at;
	}
	public void setWdr_sctn_othinst_mber_use_posbl_at(String wdr_sctn_othinst_mber_use_posbl_at) {
		this.wdr_sctn_othinst_mber_use_posbl_at = wdr_sctn_othinst_mber_use_posbl_at;
	}
	public String getWhthrc_othinst_mber_use_posbl_at() {
		return whthrc_othinst_mber_use_posbl_at;
	}
	public void setWhthrc_othinst_mber_use_posbl_at(String whthrc_othinst_mber_use_posbl_at) {
		this.whthrc_othinst_mber_use_posbl_at = whthrc_othinst_mber_use_posbl_at;
	}
	@Override
	public String toString() {
		return "CaralcSetupInfoDomain [cnter_cd=" + cnter_cd + ", roundtrip_perm_at=" + roundtrip_perm_at
				+ ", aloc_reqre_time=" + aloc_reqre_time + ", vhcle_search_radius=" + vhcle_search_radius
				+ ", beffat_resve_time_intrvl=" + beffat_resve_time_intrvl + ", vhcle_mvmn_posbl_at="
				+ vhcle_mvmn_posbl_at + ", posbl_dstnc=" + posbl_dstnc + ", wdr_sctn_othinst_mber_use_posbl_at="
				+ wdr_sctn_othinst_mber_use_posbl_at + ", whthrc_othinst_mber_use_posbl_at="
				+ whthrc_othinst_mber_use_posbl_at + "]";
	}
	
}
