<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.multi.withPuppy.report.ReportVO"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Insert title here</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>


</head>
<body>
	<div class="form-group d-flex; row;" style ="border: 1px solid gray; border-radius: 5px; padding: 10px; margin-bottom: 15px; text-align: center;">
		<h2>
		<div>${vo.title} ${vo.location1}</div>
		<div>${vo.writer}</div>
		</h2>
			<span>
				<img src="${pageContext.request.contextPath}/resources/picture1/${vo.picture1}" alt="동물사진" width="1000" height="100">
				<img src="${pageContext.request.contextPath}/resources/picture1/${vo.picture2}" alt="동물사진" width="100" height="100">
				<img src="${pageContext.request.contextPath}/resources/picture1/${vo.picture3}" alt="동물사진" width="100" height="100">
						</span>
						<div>반려종 : ${vo.animal}</div>
						<div>실종위치 : ${vo.location1}</div>
						<div>내용 : ${vo.content}</div>
					</div>
						
					
	</div>
</body>
</html>