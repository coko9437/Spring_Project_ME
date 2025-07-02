<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 7. 1.
  Time: 오후 2:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--jstl 태그 빠짐--%>
<%--JSTL 설정 적용하기, 메타 태그 붙여넣기--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ex4.jsp</title>
</head>
<body>
<h1>ex4.jsp 임시 화면, 서버로 부터 전달 받은 데이터 가져오기 테스트 </h1>
<h2>${message}</h2>
<%--보안상 출력물을 , 특정 자바스크립트 포함 여부를 생각해서, 바로 문자열로 출력하는 기능 통해서 하기. --%>
<%--<h2><c:out value="${message}"></c:out></h2>--%>
</body>
</html>
