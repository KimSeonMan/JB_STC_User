package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

import com.neighbor.ServiceAPI.mobile.util.vo.CmmnPage;

@Alias("cmmnCdInfo")
public class CmmnCdInfo {

	private CmmnPage page = new CmmnPage();
	private CmmnPage pageM = new CmmnPage();
	public CmmnPage getPageM() {
		return pageM;
	}
	public void setPageM(CmmnPage pageM) {
		this.pageM = pageM;
	}
	private String selec_cd_id;
	
	private String cd_id;
	private String cd_de;
	public String getCd_id() {
		return cd_id;
	}
	public void setCd_id(String cd_id) {
		this.cd_id = cd_id;
	}
	public String getCd_de() {
		return cd_de;
	}
	public void setCd_de(String cd_de) {
		this.cd_de = cd_de;
	}
	public CmmnPage getPage() {
		return page;
	}
	public void setPage(CmmnPage page) {
		this.page = page;
	}
	public String getSelec_cd_id() {
		return selec_cd_id;
	}
	public void setSelec_cd_id(String selec_cd_id) {
		this.selec_cd_id = selec_cd_id;
	}
	@Override
	public String toString() {
		return "CmmnCdInfo [page=" + page + ", pageM=" + pageM + ", selec_cd_id=" + selec_cd_id + ", cd_id=" + cd_id
				+ ", cd_de=" + cd_de + "]";
	}
	
}
