<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 완료된 페이지</title>
</head>
<body>
<p>code = ${code}</p><br>
<p>accessToken = ${accessToken}</p><br>
<p>id = ${id}</p><br>
<p>name = ${name}</p><br>
<p>email = ${email}</p><br>

<a href="./logout.do">로그아웃</a>

</body>
</html>
