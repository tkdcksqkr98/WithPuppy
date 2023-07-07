<%-- 결제확인 페이지 (checkout.jsp) --%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.multi.withPuppy.shopping.Order_detailVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../../shoppingHeader.jsp"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 현황</title>
<link rel="stylesheet" href="../resources/css/order.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- jQuery -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- iamport.payment.js -->
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>

<script type="text/javascript">
let price_total = 0
let order_count = 0

var productTmp;
// cartlist 호출
$(function bringCartData() {
    $.ajax({
        url : "cartPriceTotal",
        data : {
            user_id : "${bag.user_id}",
        },
        success : function(data) {
        	
        	$('#cart_list').empty();
        	var total = 0;
        	for (i = 0; i < data.length; i++) {
        		
        		productTmp = data;
        		
        		total += data[i].product_cnt * data[i].product_price;
				var sen = 
					`<li class="list-group-item">
				<div class="d-flex  align-items-center">
					<img src= ` + data[i].product_img +
						` alt="image" />
					<div class="author ">
					<div class="cartPTitle">` +data[i].product_name+`</div>
							<div class="rating cntRow">상품 가격 :`
							+data[i].product_price+`원
							<div id='resultCnt`+ i +`' class="cntRow">갯수: `+data[i].product_cnt+`개</div>
							</div>
							<div class="rating cntRow">상품 총액 : `
								+data[i].product_price*data[i].product_cnt+
								`원</div>
							<div class="rating cntRow">배송 상태 : `
								+"배송중"+
								`</div>
							</div>
							</div>
							
							</li>`;
							$('#cart_list').append(sen);
						}
        	$('#cartTotal').append("결제한 금액 : " + total + "원");
        	price_total= total;
        	order_count= data.length;
        }
    })
})

 


	
</script>
<body>
	<div class="container-fluid">
		<div class="card">
			<div class="card-header">
				<h2>주문 현황 </h2>
			</div>
			<div class="cutting_line">
				<span class="ico_scissors"></span> <span class="shdw_left"></span> <span
					class="shdw_right"></span>
			</div>
			<table class="cart-list" id="cart_list"></table>


			
		
</body>
</html>