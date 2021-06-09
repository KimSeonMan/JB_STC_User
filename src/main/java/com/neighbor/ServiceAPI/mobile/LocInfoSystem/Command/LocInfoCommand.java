package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Command;

import java.util.List;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Page.LocInfoPage;

public class LocInfoCommand {

	private String cnter_gubun;	//센터 구분
	private String drverSttusCd = "ALL";		//운전자 상태코드
	private String drver_name;			//운전자 명
	private String cnter_cd;		//센터코드
	private String sel_mber_id;
	private String sel_lc_crdnt_x;
	private String sel_lc_crdnt_y;
	private List<String> mber_id_list;
	private List<LocInfoDomain> drver_list;
	private LocInfoPage page = new LocInfoPage();
	
	public String getCnter_gubun() {
		return cnter_gubun;
	}
	public void setCnter_gubun(String cnter_gubun) {
		this.cnter_gubun = cnter_gubun;
	}
	public String getDrverSttusCd() {
		return drverSttusCd;
	}
	public void setDrverSttusCd(String drverSttusCd) {
		this.drverSttusCd = drverSttusCd;
	}
	public String getDrver_name() {
		return drver_name;
	}
	public void setDrver_name(String drver_name) {
		this.drver_name = drver_name;
	}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public LocInfoPage getPage() {
		return page;
	}
	public void setPage(LocInfoPage page) {
		this.page = page;
	}
	
	public String getSel_mber_id() {
		return sel_mber_id;
	}
	public void setSel_mber_id(String sel_mber_id) {
		this.sel_mber_id = sel_mber_id;
	}
	public List<String> getMber_id_list() {
		return mber_id_list;
	}
	public void setMber_id_list(List<String> mber_id_list) {
		this.mber_id_list = mber_id_list;
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
	public List<LocInfoDomain> getDrver_list() {
		return drver_list;
	}
	public void setDrver_list(List<LocInfoDomain> drver_list) {
		this.drver_list = drver_list;
	}
	@Override
	public String toString() {
		return "LocInfoCommand [cnter_gubun=" + cnter_gubun + ", drver_sttus_cd=" + drverSttusCd + ", drver_name="
				+ drver_name + ", cnter_cd=" + cnter_cd + ", sel_mber_id=" + sel_mber_id + ", sel_lc_crdnt_x="
				+ sel_lc_crdnt_x + ", sel_lc_crdnt_y=" + sel_lc_crdnt_y + ", mber_id_list=" + mber_id_list
				+ ", drver_list=" + drver_list + ", page=" + page + "]";
	}
	
}
