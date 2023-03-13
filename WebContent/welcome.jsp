<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Welcome</title>
<style>
html,
      body {
        height: 100%;
      }

      body {
        display: flex;
        flex-direction: column;

        text-align: center;
        margin: 0;
      }
      main {
        flex: 1;
      }
</style>
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
</head>
<body>

	<jsp:include page="menu2.jsp" />
	<main>
	<div class="jumbotron mx-4">
		<div class="container">
			<h1 class="display-3" align="center">
			</h1>
			<h1 align="center">
			<%
				response.setIntHeader("Refresh", 5);
				Date day = new java.util.Date();
				String am_pm;
				int hour = day.getHours();
				int minute = day.getMinutes();
				int second = day.getSeconds();
				if (hour / 12 == 0) {
					am_pm = "AM";
				} else {
					am_pm = "PM";
					hour = hour - 12;
				}
				String CT = hour + ":" + minute + ":" + second + " " + am_pm;
				out.println("현재 접속  시각: " + CT + "\n");
			%>
			</h1>
			<div class="row">
				<div class="col-lg-6">
					<div class="card" style="width: 650px; height: 70%;">
						<a href="/Project01/maleview.do"> <img
							src="
					https://media.matchesfashion.com/prehome/aw21/mens-hero-large.jpg?quality=60
					"
							class="card-img-top" alt="..."></a>
						<div class="card-body">
							<h5 class="card-title">남성 옷</h5>
							<p class="card-text">아주 좋은 옷</p>
							<a href="/Project01/maleview.do"
								class="btn btn-outline-secondary">보러가기</a>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="card" style="width: 650px; height: 70%;">
						<a href="#"> <img
							src="
					https://media.matchesfashion.com/prehome/aw21/womens-hero-large.jpg?quality=60
					"
							class="card-img-top" alt="..."></a>
						<div class="card-body">
							<h5 class="card-title">여성 옷</h5>
							<p class="card-text">소재가 짱짱해요</p>
							<a href="#" class="btn btn-outline-secondary">보러가기</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</main>
	<jsp:include page="footer.jsp" />
</body>
</html>