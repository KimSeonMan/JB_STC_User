package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("vhcleInfo")
public class VhcleInfo {

	private String vhcle_no;	
	private String resve_vhcle_at;	
	private String copertn_caralc_at;	
	private String vhcle_ty_cd;	
	private String modl_nm;	
	private String makr;	
	private String mxmm_brdng_nmpr;	
	private String yridnw;	
	private String vin;	
	private String use_at;	
	private String rm;	
	private String register_id;	
	private String regist_dt;	
	private String drver_nm;

	public String getVhcle_no() {
		return vhcle_no;
	}

	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}

	public String getResve_vhcle_at() {
		return resve_vhcle_at;
	}

	public void setResve_vhcle_at(String resve_vhcle_at) {
		this.resve_vhcle_at = resve_vhcle_at;
	}

	public String getCopertn_caralc_at() {
		return copertn_caralc_at;
	}

	public void setCopertn_caralc_at(String copertn_caralc_at) {
		this.copertn_caralc_at = copertn_caralc_at;
	}

	public String getVhcle_ty_cd() {
		return vhcle_ty_cd;
	}

	public void setVhcle_ty_cd(String vhcle_ty_cd) {
		this.vhcle_ty_cd = vhcle_ty_cd;
	}

	public String getModl_nm() {
		return modl_nm;
	}

	public void setModl_nm(String modl_nm) {
		this.modl_nm = modl_nm;
	}

	public String getMakr() {
		return makr;
	}

	public void setMakr(String makr) {
		this.makr = makr;
	}

	public String getMxmm_brdng_nmpr() {
		return mxmm_brdng_nmpr;
	}

	public void setMxmm_brdng_nmpr(String mxmm_brdng_nmpr) {
		this.mxmm_brdng_nmpr = mxmm_brdng_nmpr;
	}

	public String getYridnw() {
		return yridnw;
	}

	public void setYridnw(String yridnw) {
		this.yridnw = yridnw;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getUse_at() {
		return use_at;
	}

	public void setUse_at(String use_at) {
		this.use_at = use_at;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getRegister_id() {
		return register_id;
	}

	public void setRegister_id(String register_id) {
		this.register_id = register_id;
	}

	public String getRegist_dt() {
		return regist_dt;
	}

	public void setRegist_dt(String regist_dt) {
		this.regist_dt = regist_dt;
	}

	public String getDrver_nm() {
		return drver_nm;
	}

	public void setDrver_nm(String drver_nm) {
		this.drver_nm = drver_nm;
	}

	public void setVhcleInfo(VhcleInfo vhcleInfo) {
	// TODO Auto-generated method stub
		
		if(!vhcleInfo.getResve_vhcle_at().isEmpty()){
			this.resve_vhcle_at = vhcleInfo.getResve_vhcle_at();
		}
		
		if(!vhcleInfo.getCopertn_caralc_at().isEmpty()){
			this.copertn_caralc_at = vhcleInfo.getCopertn_caralc_at();
		}
		if(!vhcleInfo.getVhcle_no().isEmpty()){
			this.vhcle_ty_cd = vhcleInfo.getVhcle_no();
		}
		
		if(!vhcleInfo.getModl_nm().isEmpty()){
			this.modl_nm = vhcleInfo.getModl_nm();
		}
		
		if(!vhcleInfo.getMakr().isEmpty()){
			this.makr = vhcleInfo.getMakr();
		}
		
		if(!vhcleInfo.getMxmm_brdng_nmpr().isEmpty()){
			this.mxmm_brdng_nmpr = vhcleInfo.getMxmm_brdng_nmpr();
		}
		
		if(!vhcleInfo.getYridnw().isEmpty()){
			this.yridnw = vhcleInfo.getYridnw();
		}
		
		if(!vhcleInfo.getVin().isEmpty()){
			this.vin = vhcleInfo.getVin();
		}

		if(!vhcleInfo.getRm().isEmpty()){
			this.rm = vhcleInfo.getRm();
		}
	}
	
}
