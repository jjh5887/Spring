<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<metacharset-"UTF-8">
</head>
<body>
	<h1>AccountRegisterOk</h1>

	ID : ${account.id}
	<br /> PW : ${account.password}
	<br /> 잔액 : ${account.balance}
	<br />


	<a href="/test/resources/html/index.html"> 메인페이지로 돌아가기 </a>
</body>
</html>