<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 7. 1.
  Time: 오전 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Register 임시 화면</h1>
<form action="/todo/register" method="post">
    <div>
        Title : <input type="text" name="title"></input>
    </div>
    <div>
        DueDate : <input type="date" name="dueDate"></input>
    </div>
    <div>
        Writer : <input type="text" name="writer"></input>
    </div>
    <div>
        Finished : <input type="checkbox" name="finished"></input>
    </div>
    <div>
        <button type="submit">등록하기</button>
    </div>

</form>
</body>
</html>
