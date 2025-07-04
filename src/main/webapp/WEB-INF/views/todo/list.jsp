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

        <%--        검색 화면 위치 시작--%>
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">목록</h5>
                        <%--          검색시 필요한 input 태그 첨부, 검색 로직, 화면에서 서버로 --%>
                        <%--          데이터 전달시(검색어 전달), get 방식, --%>
                        <form action="/todo/list" method="get">
                            <input type="hidden" name="size" value="${pageRequestDTO.size}">
                            <div class="mb-3">
                                <!-- 검색칸에 체크박스 다음페이지에도 유지되게 -->
                            <input type="checkbox" name="finished"
                            ${pageRequestDTO.finished ? "checked" : ""}
                            > 완료여부
                        </div>
                        <div class="mb-3">
                            <input type="checkbox" name="types" value="t"
                                ${pageRequestDTO.checkType("t") ? "checked" : ""}
                            >제목
                            <input type="checkbox" name="types" value="w"
                                ${pageRequestDTO.checkType("w") ? "checked" : ""}
                            >작성자
                            <input type="text" name="keyword" class="form-control"
                                value='<c:out value="${pageRequestDTO.keyword}"/>'
                            >
                        </div>
                        <div class="input-group mb-3 dueDateDiv">
                            <input type="date" name="from" class="form-control"
                                value="${pageRequestDTO.from}"
                            >
                            <input type="date" name="to" class="form-control"
                                value="${pageRequestDTO.to}"
                            >
                        </div>
                        <div class="input-group mb-3">
                            <div class="float-end">
                                <button class="btn btn-primary" type="submit">검색</button>
                                <button class="btn btn-info clearBtn" type="reset">초기화</button>
                            </div>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<%--        검색 화면 위치  끝--%>


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
                                        <%--                                    클릭시 : /todo/read?tno=21--%>
                                    <td>
                                        <a href="/todo/read?tno=${dto.tno}&${pageRequestDTO.link}" class="text-decoration-none">
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

                        <%--                        페이징을 가리키는 네비게이션 , 부트 스트랩에서 가져와서 이용--%>
                        <%--                        <div class="float-end">--%>
                        <div>


                            <%--                            페이징 네비게이션 화면 영역--%>
                            <ul class="pagination flex-wrap justify-content-center">
                                <%--       ui1 이전 버튼--%>
                                <%--                                화면에서는 ,서버로부터 전달받은 responseDTO 객체(상자) , 꺼내서 이용중--%>
                                <c:if test="${responseDTO.prev}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${responseDTO.start - 1}">Prev</a>
                                    </li>
                                </c:if>
                                <%--                                    ui2, 페이지네이션 번호 뷰--%>
                                <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}" var="num">
                                    <li class="page-item ${responseDTO.page == num ? "active" : "" }">
                                        <a class="page-link" data-num="${num}">
                                                ${num}
                                        </a>
                                    </li>
                                </c:forEach>
                                <%--          ui3, 다음 버튼 표시--%>
                                <c:if test="${responseDTO.next}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${responseDTO.end + 1}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                            <%--                                페이징 네비게이션 화면 영역--%>


                        </div>
                        <%--                            페이징 이벤트 처리 하기.--%>
                        <script>
                            // 검색 화면 초기화 클릭시, 기본 전체 페이지 이동하기.
                            document.querySelector(".clearBtn").addEventListener("click", function (e){
                                e.preventDefault();
                                e.stopPropagation();
                                self.location = '/todo/list'
                                })

                            document.querySelector(".pagination").addEventListener("click", function (e){
                                e.preventDefault();
                                e.stopPropagation();

                                const target = e.target; // 클래스가 pagination 선택자 하위 요소들 중에서,
                                if(target.tagName !== 'A') { // a 태그가 아닌 다른 태그를 클릭시, 이벤트 처리 안한다.
                                    return
                                }
                                // a 태그만 이벤트 처리하겠다. 의도.
                                // data-num : 페이지 정보,
                                const num = target.getAttribute("data-num")
                                    // 목록에서 -> 상세보기 전달시,
                                        // 이너html 이용해서, 히든으로 페이징 정보를 전달.
                                // 검색 폼 ------
                                const fromObj = document.querySelector("form")
                                formObj.innerHTML += `<input type = 'hidden' name = 'page' value = '\${num}'>`
                                    formObj.submit()

                                // ` 백틱 사용.
                                // self.location = `/todo/list?page=\${num}`

                            },false)
                        </script>

                        <%--                        다음 버튼 표시--%>

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
