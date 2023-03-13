<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String sessionId = (String) session.getAttribute("sessionId");
%>

<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
	integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"
	integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2XofN4zfuZxLkoj1gXtW8ANNCe9d5Y3eG5eD"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
<link href="css/styles.css" rel="stylesheet" />
<link rel="stylesheet" href="./resources/css/product.css" />

<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarTogglerDemo03"
			aria-controls="navbarTogglerDemo03" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<a class="navbar-brand" href="/WebMarket/welcome.jsp">Home</a>
		<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="#">장바구니</a></li>
				<c:choose>
					<c:when test="${empty sessionId}">
						<li class="nav-item"><a class="nav-link"
							href="<c:url value="/member/loginMember.jsp"/>">로그인</a></li>
						<li class="nav-item"><a class="nav-link"
							href='<c:url value="/member/addMember.jsp"/>'>회원 가입</a></li>
					</c:when>
					<c:otherwise>
						<li style="padding-top: 7px; color: white">[<%=sessionId%>님]
						</li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value="/WebMarket/DeleteMember.do"/>">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value="/member/updateMember.jsp"/>">회원 수정</a></li>
					</c:otherwise>
					</c:choose>
					<li class="nav-item"><a class="nav-link" href="<c:url value="/products.jsp"/>">상품 목록</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/addProduct.jsp"/>">상품 등록</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/BoardListAction.do?pageNum=1"/>">게시판</a></li>
			</ul>
			<form class="d-flex" role="search">
				<input class="form-control me-2" type="search" placeholder="Search"
					aria-label="Search">
				<button class="btn btn-outline-success" type="submit">Search</button>
			</form>
		</div>
	</div>
</nav>
