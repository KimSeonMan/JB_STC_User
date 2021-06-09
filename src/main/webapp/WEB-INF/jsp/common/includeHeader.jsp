<%
/**************************************************************************************************************************
* Program Name  : Header JSP  
* File Name     : includeHeader.jsp
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
	
	//menuList();
</script>
<!-- header -->
<div class="header"> 
	<div class="rela"> 
		<%-- <%if(cnter_nm == null){%> --%>
		<h1><a href="/default.html">전라북도 광역 이동지원센터 <!-- <br />JeonBuk Service --></a></h1>
	    <%-- <%}else{%>
		<h1><a href="/default.html"><%=cnter_nm %><br /><%=cnter_nm_en %></a></h1>  
	    <%}%> --%>
		<%if(user_id == null){%>
		<ul class="tnb">
			<li><a></a></li>
			<li><a href="/view/memberShipAgree.do">회원가입</a></li>
			<li><a href="javascript:loginProcess();" class="last">로그인</a></li> 
		</ul> 			
	    <%}else{%>
	    <ul class="tnb">
			<li><a><%=name%>님  환영합니다.</a></li>
			<li><a href="/view/myPage.do">마이페이지</a></li>
			<li><a href="javascript:logoutProcess();" class="last">로그아웃</a></li> 
		</ul> 
	    <%}%>
 		<p class="date" id="makeStamp"></p>
	</div>
	<ul class="gnb" id="gnbMenu"></ul>
	<div class="depthArea">
		<div class="rela" id="subMenu"></div>
	</div>
</div>
<!-- end header -->