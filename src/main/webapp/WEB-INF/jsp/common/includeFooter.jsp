<%
	/**************************************************************************************************************************
	 * Program Name  : Header JSP
	 * File Name     : includeFooter.jsp
	 * Comment       :
	 * History       : Neighbor 2016-09-08
	 *
	 **************************************************************************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String sessionId = (String) session.getAttribute("sessionId");
	String user_id = (String) session.getAttribute("user_id");
	String name = (String) session.getAttribute("name");
	String mobile  = (String) session.getAttribute("mobile");
	String user_stat_cd = (String) session.getAttribute("user_stat_cd");
	String user_gbn_cd = (String) session.getAttribute("user_gbn_cd");
	String cnter_cd  = (String) session.getAttribute("cnter_cd");
	String user_gbn_nm  = (String) session.getAttribute("user_gbn_nm");
	String cnter_nm  = (String) session.getAttribute("cnter_nm");
	String cnter_addr  = (String) session.getAttribute("cnter_addr");
	String cnter_zip  = (String) session.getAttribute("cnter_zip");
	String cnter_tel  = (String) session.getAttribute("cnter_tel");
	String cnter_nm_en  = (String) session.getAttribute("cnter_nm_en");

	HttpSession http_session = request.getSession();
%>

<script type="text/javascript">
	var session = {
		sessionId : '<%=sessionId %>',
		user_id : '<%=user_id %>',
		name : '<%=name %>',
		mobile : '<%=mobile %>',
		user_stat_cd : '<%=user_stat_cd %>',
		user_gbn_cd : '<%=user_gbn_cd %>',
		cnter_cd : '<%=cnter_cd %>',
		user_gbn_nm : '<%=user_gbn_nm %>',
		cnter_nm : '<%=cnter_nm %>',
		cnter_addr : '<%=cnter_addr %>',
		cnter_zip : '<%=cnter_zip %>',
		cnter_tel : '<%=cnter_tel %>',
		cnter_nm_en : '<%=cnter_nm_en %>'
	};
</script>

<div class="footer"><!-- start : footer -->
	<div class="rela">
		<p class="flogo"><br>전북 이동지원센터</p>
		<br>
		<div>
			<p style="font-size: smaller;width: 550px;">콜센터&nbsp; 주소&nbsp; :&nbsp; 전주시&nbsp; 완산구&nbsp; 백제대로&nbsp; 315&nbsp; 2층</p>
			<p style="font-size: smaller;width: 600px;margin-top: -17px;margin-left: 650px;">대표자&nbsp; :&nbsp; 송하진</p>
			<p style="font-size: smaller;width: 550px;">콜센터&nbsp; 전화번호&nbsp; :&nbsp; 063-227-0002</p>
			<p style="font-size: smaller;width: 600px;margin-top: -17px;margin-left: 650px;">사업자&nbsp; 번호&nbsp; :&nbsp; 402-83-00010</p>
			<p style="font-size: smaller;width: 600px;margin-top: 0px;margin-left: 650px;">주소 &nbsp; : &nbsp; [54968] 전라북도 전주시 완산구 효자로 225&emsp; <img src="/img/etc/call.png">  063-227-0002</p>
		</div>
	</div>
</div><!-- end : footer -->
