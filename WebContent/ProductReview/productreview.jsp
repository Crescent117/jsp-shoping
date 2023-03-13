<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
	String p_id=request.getParameter("p_id");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	.table{
		width:90%;
		border:1px solid black;
		height:400px
	}
	th,td{
		border:1px solid black;
	}
</style>

<script type="text/javascript">
	function product(){
		window.location.href="./Bcontroller.po?p_id=<%=p_id%>";
	}
</script>
</head>
<body>
<table class="table">
	<tr style="text-align:center;">
		<th style="height:50px">제목</th>
		<td>${review.title}</td>
		<th>별점</th>
		<td>
			<c:forEach   var="i" begin="0" end="${review.star}">
				★
			</c:forEach>
		
		</td>
	</tr>
	<tr style="text-align:center;">
		<th style="height:50px">작성날짜</th>
		<td colspan="3" >${review.postdate_ymd} ${review.postdate_hms }</td>
	</tr>
	<tr>
		<th style="height:400px;">내용</th>
		<td colspan="3" style="vertical-align : top; padding:10px;">${review.content }</td>
	</tr>
	<tr style="text-align:right">
		<td colspan="4" style="height:50px;" >
			<button id="updateBtn">수정</button>
			<button id="deleteBtn">삭제</button>
			<button id="deleteBtn" onclick="product()">목록</button>
			
		</td>
	</tr>
</table>
</body>
</html>