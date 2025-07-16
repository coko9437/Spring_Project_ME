<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 25. 7. 1.
  Time: 오전 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

        <!--        네비게이션바-->
        <div class="row">
            <div class="col">
                <nav class="navbar navbar-expand-lg bg-body-tertiary">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">게시판</a>
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
                                <!--  날씨 버튼 추가 -->
                                <button onclick="getWeather()" class="btn btn-outline-primary ms-2">추교문</button>
                            </ul>
                        </div>
                    </div>
                </nav>

            </div>
        </div>
        <!--        네비게이션바 끝-->

        <%--        검색 화면 위치 시작--%>
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">목록</h5>

                        <form action="/todo/list" method="get">
                            <input type="hidden" name="size" value="${pageRequestDTO.size}">
                            <div class="mb-3">
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
                                       value='<c:out value="${pageRequestDTO.keyword}"/>'>
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
    <%--        검색 화면  끝--%>



    <div class="row content">

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

                        <c:forEach items="${responseDTO.dtoList}" var="dto">
                            <tr>
                                <th scope="row"><c:out value="${dto.tno}"></c:out></th>
                                    <%--                                    클릭시 : /todo/read?tno=21--%>
                                <td>
                                    <a href="/todo/read?tno=${dto.tno}&${pageRequestDTO.link}"
                                       class="text-decoration-none">
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


                    <div>



                        <ul class="pagination flex-wrap justify-content-center">

                            <c:if test="${responseDTO.prev}">
                                <li class="page-item">
                                    <a class="page-link" data-num="${responseDTO.start - 1}">Prev</a>
                                </li>
                            </c:if>

                            <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}" var="num">
                                <li class="page-item ${responseDTO.page == num ? "active" : "" }">
                                    <a class="page-link" data-num="${num}">
                                            ${num}
                                    </a>
                                </li>
                            </c:forEach>

                            <c:if test="${responseDTO.next}">
                                <li class="page-item">
                                    <a class="page-link" data-num="${responseDTO.end + 1}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                        <%--                            페이징 네비게이션 화면 영역--%>


                    </div>
                    <%--                            페이징 이벤트 처리 하기.--%>
                    <script>
                        // 검색 화면 초기화 클릭시, 기본 전체 페이지 이동하기.
                        document.querySelector(".clearBtn").addEventListener("click", function (e){
                            e.preventDefault();
                            e.stopPropagation();
                            self.location = '/todo/list'
                        })

                        document.querySelector(".pagination").addEventListener("click", function (e) {
                            e.preventDefault();
                            e.stopPropagation();

                            const target = e.target; // 클래스가 pagination 선택자 하위 요소들 중에서,
                            if (target.tagName !== 'A') { // a 태그가 아닌 다른 태그를 클릭시, 이벤트 처리 안한다.
                                return
                            }

                            const num = target.getAttribute("data-num")


                            const formObj = document.querySelector("form")
                            //
                            formObj.innerHTML += `<input type='hidden' name = 'page' value='\${num}'>`
                            formObj.submit()



                            // self.location = `/todo/list?page=\${num}`

                        }, false)
                    </script>

                    <%--                        다음 버튼 표시--%>

                </div>
            </div>
            <!--        카드 끝 부분-->
        </div>

    </div>
    <!--        class="row content"-->
</div>

<div class="row footer">
    <!--        <h1>Footer</h1>-->
    <div class="row fixed-bottom" style="z-index: -100">
        <footer class="py-1 my-1">
            <p class="text-center text-muted">Footer</p>
        </footer>
    </div>
</div>
</div>
<!-- 날씨 모달 -->
<div id="weatherModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:2000;">
    <div style="background:white; width:300px; margin:100px auto; padding:20px; border-radius:10px; position:relative;">
        <button onclick="closeModal()" style="position:absolute; top:10px; right:10px;">X</button>
        <div id="weatherContent">날씨 정보 출력중</div>
    </div>
</div>

<!-- 스크립트 영역 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script>
    function getWeather() {
        document.getElementById("weatherModal").style.display = "block";
        document.getElementById("weatherContent").innerHTML = "날씨 정보 출력중";

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                fetch(`/todo/weather?lat=${lat}&lon=${lon}`)
                    .then(res => res.json())
                    .then(data => showWeather(data));
            }, function() {
                fetch(`/todo/weather`)
                    .then(res => res.json())
                    .then(data => showWeather(data));
            });
        }
    }

    function showWeather(data) {
        document.getElementById("weatherContent").innerHTML = `
            <h5>현재 날씨 정보</h5>
            <p> 온도: ${data.temp}°C</p>
            <p> 바람: ${data.windspeed} m/s</p>
            <p>API 호출 시간: ${data.apiCallDurationMs} ms</p>
        `;
    }

    function closeModal() {
        document.getElementById("weatherModal").style.display = "none";
    }

    document.querySelector(".clearBtn").addEventListener("click", function (e){
        e.preventDefault();
        location.href = '/todo/list';
    });

    document.querySelector(".pagination").addEventListener("click", function (e){
        e.preventDefault();
        const target = e.target;
        if (target.tagName !== 'A') return;
        const num = target.getAttribute("data-num");
        const formObj = document.querySelector("form");
        formObj.innerHTML += `<input type='hidden' name='page' value='${num}'>`;
        formObj.submit();
    });
</script>
</body>
</html>
