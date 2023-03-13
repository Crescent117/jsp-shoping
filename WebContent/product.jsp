<%@page import="util.BoardPage"%>
<%@page import="dao.MVCBoardDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.ProductDTO"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="dao.ProductRepository"%>
<%@ page errorPage="exceptionNoProductId.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
if(request.getAttribute("sessionId")!=null){
	String m_id = session.getAttribute("sessionId").toString();
}
String p_id = request.getParameter("p_id");
int totalCount = (int)request.getAttribute("totalCount");
int pageSize =(int)request.getAttribute("pageSize");
int blockPage =(int)request.getAttribute("blockPage");
int pageNum =(int)request.getAttribute("pageNum");
%>

<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<title>상품 상세 정보</title>
<style type="text/css">
#select {
	font-family: TmonMonsori, "GodoB", "굴림";
	font-weight: bold;
}

#myform fieldset{
    display: inline-block;
    direction: rtl;
    border:0;
}
#myform fieldset legend{
    text-align: right;
}
#myform input[type=radio]{
    display: none;
}
#myform label{
    font-size: 3em;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
}
#myform label:hover{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform label:hover ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform input[type=radio]:checked ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#reviewContents {
    width: 100%;
    height: 150px;
    padding: 10px;
    box-sizing: border-box;
    border: solid 1.5px #D3D3D3;
    border-radius: 5px;
    font-size: 16px;
    resize: none;
}
</style>


<script type="text/javascript">
	
	
	function addToCart() {
		
		if (confirm("상품을 장바구니에 추가하시겠습니까?")) {
			document.addForm.submit();
		} else {
			document.addForm.reset();
		}
	}
	
	
	
	  
	  //댓글등록이벤트
	  
	  //댓글등록이벤트
	  $(function (){
		  $('#review_insert').on("click",function(){
			 if($('#reviewContents').val()==''){
				 //alert("11");
				 return false;
			 }
			 commentInsert();
		  });
	  });
	  

	    // 댓글 등록
	    function commentInsert(){
	        $.ajax({
	             url:'./ReviewInsert.po',
	            type:'post',
	            data:{
	            	"p_id":"<%=p_id%>",
	            	"star": $('input[name="reviewStar"]:checked').val(),
	            	"title" : $('#review_title').val(),
	            	"content": $('#reviewContents').val()
	            },
	            success:function(data){
	                if(data=='1'){
	                    $('#reviewContents').val('');
	                    $('#review_title').val('');
	                    $( '#table' ).empty();
	                    review_view();
	                } 
	            }, 
	        }); 
	    }; 
	  
	  
	    
	  //리뷰 출력
	  function review_view(){
	    $.ajax({
	        type:"POST",
	        url:"./ReviewController.po",
	        dataType:"json",
	        data:{
	        		"p_id" : "<%=p_id%>",
	        		"pageNum" : "<%=pageNum%>"
	        	},
	        success : function(data){
	            //alert('ajax POST 통신 성공'); 
	        	var userList = data.result;
	            //alert(userList[0]);
	           // alert(userList[0][0]["m_id"]);
	        	 var tr = '<thead>'+
	        	    '<tr>' +
	                      '<th>아이디</th>'+
	                      '<th>평점 </th>'+
	                      '<th>내용 </th>'+
	                      '<th>작성날짜 </th>'+
	               	    '</tr>'+
	                  '</thead>';
	                  
	                  for(let i=0; i<userList.length;i++){
	      				 tr += '<tr><td>' +  userList[i][0]["m_id"]
	      				 tr += '</td><td>';
	      				 for(let j=0;j<userList[i][1]["star"];j++){
	      					tr+='★';
	      				 }
	      				 tr += '</td><td>';
	      				 tr += '<a href="./ReviewDetailController.po?index='+userList[i][4]["index"]+'&&p_id='+userList[i][6]["p_id"]+'">'+userList[i][5]["title"] + '</a></td><td>';	
	      				 tr += userList[i][3]["time"] + '</td></tr>';
	      				 
	      				
	      			}
	            $("#table").append(tr);	// 테이블에 추가
	        },
	       
	    });
	    };
	    
	    $(document).ready(function(){
			
	  	  		review_view();
				 
		  });
	
	

</script>
</head>
<body>

	<jsp:include page="menu.jsp" />
	<c:set var="size" value="${size }" />
	<c:set var="color" value="${color }" />
	
	
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 정보</h1>
		</div>
	</div>
	<p>
	<form name="addForm" action="/WebMarket/addCart.do?p_id=${dto.p_id }" method="post">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<img src="/WebMarket/resources/images/" style="width: 100%" />
				</div>
				<div class="col-md-6">
					<h3>${dto.p_name}</h3>
					<h4>
						<b>가격</b> ${dto.p_unitPrice}:원
					</h4>
					<br> 사이즈 : <select style="width: 150px" id="select" name="size">
						<c:forEach items="${size}" var="size">
							<option id="select" name="size" value="${size.p_size}">${size.p_size}</option>
						</c:forEach>
					</select><br> <br> 
					
					색상 : <select style="width: 150px" id="select" name="color">
						<c:forEach items="${color }" var="color">
							<option id="select" name="color" value="${color.p_color}">${color.p_color}</option>
						</c:forEach>
					</select><br> <br>

					<!-- 상품주문 -->
					<a href="#" class="btn btn-info" onclick="addToCart()"> 상품 담기&raquo;</a> 
					<a href="./cart.jsp" class="btn btn-warning"> 장바구니&raquo;</a> 
					<a href="./products.jsp" class="btn btn-secondary"> 상품목록 &raquo;</a><br> <br>

					<!-- 테스트중 서버때문에 안될거같음 -->
					<a href="http://facebook.com"> <img
						src="/WebMarket/resources/images/facebooklogo.png"
						style="width: 30px; height: 30px">
					</a> <a href="http://twitter.com"> <img
						src="/WebMarket/resources/images/twitter.png"
						style="width: 30px; height: 30px">
					</a> <a href="http://facebook.com"> <img
						src="/WebMarket/resources/images/instagram.jpg"
						style="width: 30px; height: 30px">
					</a>


					<p>
						<b>상품 코드 : </b><span class="badge badge-danger">
							${dto.p_id}</span>
					<p>
						<b>브랜드</b> :${dto.p_manufacturer}
					<p>
						<b>분류</b> :${dto.p_category}
				</div>
			</div>
			<hr>
		</div>
	</form>
			<form class="mb-3" name="myform" id="myform" method="post">
	<fieldset>
		<span class="text-bold">별점을 선택해주세요</span>
		<input type="radio" name="reviewStar" value="5" id="rate1" ><label
			for="rate1" >★</label>
		<input type="radio" name="reviewStar" value="4" id="rate2"><label
			for="rate2">★</label>
		<input type="radio" name="reviewStar" value="3" id="rate3" checked><label
			for="rate3">★</label>
		<input type="radio" name="reviewStar" value="2" id="rate4"><label
			for="rate4">★</label>
		<input type="radio" name="reviewStar" value="1" id="rate5"><label
			for="rate5">★</label>
	</fieldset>
	
	
		&nbsp;제목 : <input type="text" id="review_title" style="width:300px;"><br>
	
	<div>
	
		<textarea class="col-auto form-control" type="text" id="reviewContents"
				  placeholder="좋은 리뷰을 남겨주시면 판매자에게 큰 힘이 됩니다!!"></textarea>
	</div>
	 <button type="button" name="review_insert" id="review_insert">등록</button>
</form>

	<table class="table" id="table"style="text-align:center; border:1px solid #dddddd">
	<!-- ajax를 통해 리뷰 출력공간 -->
	</table>
	<%=BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "./Bcontroller.po?p_id=" + p_id)%>
	<jsp:include page="footer.jsp" />
</body>
</html>
