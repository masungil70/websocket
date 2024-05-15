<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<script type="text/javascript" src="<c:url value='/resources/sockjs-0.3.4.js'/>"></script>
<script type="text/javascript">
    window.addEventListener("load", e => {
        document.querySelector("#mForm").addEventListener("submit", e => {
        	e.preventDefault();
            sock.send(message.value);
            message.value = '';
            message.focus();
            return false;
        });
    });
    
    //spring framework에서 웹소켓 서버에 연결하기 위한 url 설정
    var sock = new SockJS("<c:url value="/echo"/>");

    sock.onopen = evt => {
    	console.log(evt);
    }
    
   //자바스크립트 안에 function을 집어넣을 수 있음.
    //데이터가 나한테 전달되읐을 때 자동으로 실행되는 function
    sock.onmessage = evt => {
    	console.log(evt);
    	let p = document.createElement("p");
    	p.innerHTML = evt.data;
        data.append(p);
    }

   //데이터를 끊고싶을때 실행하는 메소드
    sock.onclose = evt => {
    	console.log(evt);
        data.append("연결 끊김");
    }

</script>
</head>
<body>
	<form name="mForm" id="mForm" onsubmit="return false">
	    <input type="text" id="message" />
	    <input type="submit" value="전송" />
    </form>
    <div id="data"></div>
</body>
</html>
