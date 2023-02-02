<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="keyword" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<c:set var="count" value="${fn:length(list) }" />
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>[${count - status.index }]</td>
							<td style="text-align:left; padding-left: ${vo.depth*10 }px; ">
								<c:if test="${vo.depth > 0 }">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath }/board/view/${vo.no}">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${authUser.name == vo.userName}">
									<a href="${pageContext.request.contextPath }/board/delete/${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${prePage }">
							<li><a href="${pageContext.request.contextPath }/board?pageNo=${startPage -1 }&keyword=${keyword }">◀</a></li>
						</c:if>

						<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
							<li><a href="${pageContext.request.contextPath }/board?pageNo=${i }&keyword=${keyword }">${i }</a></li>
						</c:forEach>

						<c:if test="${nextPage && endPage > 0 }">
							<li><a href="${pageContext.request.contextPath }/board?pageNo=${endPage +1 }&keyword=${keyword }">▶</a></li>
						</c:if>
					</ul>
				</div>			
				<!-- pager 추가 -->
				
				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>	
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>