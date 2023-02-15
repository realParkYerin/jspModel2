<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
response.sendRedirect("member?param=login"); //member : servlet링크 -> controller에 doProc함수로 이동하여 param값을 login으로 지정 --> login.jsp보여줌
//무조건 controller를 거쳐감!
%>

</body>
</html>