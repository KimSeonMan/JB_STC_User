package com.neighbor.ServiceAPI.mobile.Counselor.Page;

public class CarDispStatusPage {
	/** 현재 페이지 번호. */
	private Integer pageNo = 1;
	/** 게시판 총 개수. */
	private Integer total= 0;
	/** 총 페이지 수. */
	private Integer pageCount = 0;
	/** 현재 페이지의 시작 index. */
	private Integer offset = 0;
	/** 현재 페이지의 끝 index. */
	private Integer limit = 5;
	
	/**
	 * Page Constructor.
	 */
	public CarDispStatusPage() {};
	
	/**
	 * Page Constructor.
	 * 
	 * @param pageNo 현재 페이지 번호.
	 * @param total 게시판 총 개수.
	 */
	public CarDispStatusPage(Integer pageNo, Integer total) {
		this.pageNo = pageNo;
		this.total = total;
		
		this.initPage();
	}
	
	/**
	 * 페이지 정보 생성.
	 */
	public final void initPage() {
		setPageCount();
		setOffset();
		setLimit();
	}
	
	/**
	 * @return the pageNo
	 */
	public final Integer getPageNo() {
		return pageNo;
	}
	
	/**
	 * @param pageNo the pageNo to set
	 */
	public final void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		initPage();
	}
	
	/**
	 * @return the total
	 */
	public final Integer getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public final void setTotal(Integer total) {
		this.total = total;
		
		setPageCount();
		
		// 조회할 페이지가 전체 페이지 수보다 크면 조회할 페이지를 마지막 페이지로 설정.
		if (this.pageNo > this.pageCount)
			this.pageNo = this.pageCount;
		
		setOffset();
		setLimit();
	}
	
	/**
	 * @return the pageCount
	 */
	public final Integer getPageCount() {
		return pageCount;
	}
	
	/**
	 * @return the offset
	 */
	public final Integer getOffset() {
		return offset;
	}
	
	/**
	 * @return the limit
	 */
	public final Integer getLimit() {
		return limit;
	}
	
	/**
	 * @param pageCount the pageCount to set
	 */
	private final void setPageCount() {
		this.pageCount = this.total / 5 + (this.total % 5 != 0 ? 1 : 0);
	}
	
	/**
	 * @param offset the offset to set
	 */
	private final void setOffset() {
		this.offset = ((this.pageNo - 1) * 5) + 1;
	}
	
	/**
	 * @param limit the limit to set
	 */
	private final void setLimit() {
		this.limit = this.offset + 4;
	}
}
