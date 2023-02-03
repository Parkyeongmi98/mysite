<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div id="navigation">
		<ul>
			<c:choose>
				<c:when test="${not empty authUser }">
					<li><a href="${pageContext.request.contextPath }">${authUser.name }</a></li>
					<li><a href="${pageContext.request.contextPath }/gb">방명록</a></li>
					<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath }">메인</a></li>
					<li><a href="${pageContext.request.contextPath }/gb">방명록</a></li>
					<li><a href="${pageContext.request.contextPath }/board?pageNo=1&keyword">게시판</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>