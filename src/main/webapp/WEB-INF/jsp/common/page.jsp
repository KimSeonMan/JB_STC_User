<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/publishCss/css/common.css" type="text/css" />
<!-- .paging -->
<div class="paging">
	<a href="javascript:goPage(1);"  class="first"></a>
	<c:if test="${dataSet.pageNo > 1}">
	<a href="javascript:goPage(${dataSet.pageNo - 1});" class="prev"></a>
	</c:if>
	<c:set var="i" value="1" />
	<c:forEach var="page" begin="1" end="${dataSet.pageCount}">
       <c:if test="${dataSet.pageNo == page}">
           <a href="javascript:goPage(${page});"><strong>${page}</strong></a>
       </c:if>
       <c:if test="${dataSet.pageNo != page}">
             <a href="javascript:goPage(${page});">${page}</a>
       </c:if>
       <c:set var="i" value="${i + 1}"/>
    </c:forEach>
    <c:if test="${dataSet.pageNo < dataSet.pageCount}">
    <a href="javascript:goPage(${dataSet.pageNo + 1});" class="next"></a>
    </c:if>
    <a href="javascript:goPage(${dataSet.pageCount});" class="last"></a>

 </div>
<!-- /.paging -->
