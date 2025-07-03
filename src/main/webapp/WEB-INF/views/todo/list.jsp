<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 7. 1.
  Time: 오전 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--resources/test.html, 베이스 레이아웃 , 가져와서, 카드 바디 부분에 --%>
<%--데이터를 추가 하는 중. --%>
<!--베이스 레이아웃 접근 주소 ,
http://localhost:8080/resources/test.html-->
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!--        <h1>Header</h1>-->
        <!--        네비게이션바 추가 시작-->
        <div class="row">
            <div class="col">
                <nav class="navbar navbar-expand-lg bg-body-tertiary">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">Navbar</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="/todo/list">목록가기</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/todo/register">글쓰기</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Pricing</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

            </div>
        </div>
        <!--        네비게이션바 추가 끝-->

        <!--        class="row content"-->
        <div class="row content">
            <!--        col-->
            <div class="col">
                <!--        카드 시작 부분-->
                <div class="card">
                    <div class="card-header">
                        Todo 목록
                    </div>
                    <div class="card-body">
                        <%--                        여기에 목록을 출력하기--%>
                        <h5 class="card-title">목록</h5>
                        <%--                        ${dtoList}--%>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">TNO</th>
                                <th scope="col">Title</th>
                                <th scope="col">Writer</th>
                                <th scope="col">DueDate</th>
                                <th scope="col">Finished</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--                            페이징 처리전--%>
                            <%--                            <c:forEach items="${dtoList}" var="dto">--%>
                            <%--                                페이징 처리 후--%>
                            <c:forEach items="${responseDTO.dtoList}" var="dto">
                                <tr>
                                    <th scope="row"><c:out value="${dto.tno}"></c:out></th>
                                        <%--      클릭시? : /todo/read?tno=21--%>
                                    <td>
                                        <a href="/todo/read?tno=${dto.tno}" class="text-decoration-none">
                                            <c:out value="${dto.title}"/>
                                        </a>
                                    </td>
                                    <td><c:out value="${dto.writer}"/></td>
                                    <td><c:out value="${dto.dueDate}"/></td>
                                    <td><c:out value="${dto.finished}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
<%--                        페이징을 가리키는 화면(네비게이션)을 부트스트랩에서 가져와서 이용하기              --%>
<%--                            <div class="float-end">--%>
                            <div>


                                <%--                            페이징 네비게이션 화면 영역--%>
                                <ul class="pagination flex-wrap justify-content-center">
                                    <%--                            이전 버튼--%>
                                    <c:if test="${responseDTO.prev}">
                                        <li class="page-item">
                                            <a class="page-link" data-num="${responseDTO.start - 1}">Prev</a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}" var="num">
                                        <li class="page-item" ${responseDTO.page == num ? "active" : ""}>
                                            <a class="page-link" data-num="${num}">
                                                ${num}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <%--                        다음 버튼 표시--%>
                                    <c:if test="${responseDTO.next}">
                                        <li class="page-item">
                                            <a class="page-link" data-num="${responseDTO.end + 1}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                                <%--                                페이징 네비게이션 화면 영역--%>


                            </div>
                                <script>
                                    document.querySelector(".pagination").addEventListener("click", function (e){
                                        e.preventDefault();
                                        e.stopPropagation();

                                        const target = e.target; // 클래스가 pagination 선택자 하위 요소들 중에서,
                                        if(target.tagName !== 'A') { // A태그가 아니라면 이벤트 처리 안하고 나간다.
                                            return
                                        }
                                        // A 태그만 처리하겠다.
                                        const num = target.getAttribute("data-num") // data-num : 페이지 정보
                                        self.location = `/todo/list?page=\${num}`
                                    }, false)
                                </script>

                            <%--                        다음 버튼 표시--%>

                    </div>
                    </div>
                </div>
                <!--        카드 끝 부분-->
            </div>
            <!--        col-->
        </div>
        <!--        class="row content"-->
    </div>
    <%--    <div class="row content">--%>
    <%--        <h1>Content</h1>--%>
    <%--    </div>--%>
    <div class="row footer">
        <!--        <h1>Footer</h1>-->
        <div class="row fixed-bottom" style="z-index: -100">
            <footer class="py-1 my-1">
                <p class="text-center text-muted">Footer</p>
            </footer>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
