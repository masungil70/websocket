<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.css">
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.js"></script>

</head>
<body>

<form name="searchForm" id="searchForm" method="post" onsubmit="$boardTable.draw(); return false;">                        
   <div class="panel search-panel">
       <div class="panel-body">
           <div class="row">
               <div class="col-md-11">
                   <input class="form-control" placeholder="이름을 입력해주세요" type="text" id="searchKeyword" name="searchKeyword" value="${searchVO.searchKeyword}">
                </div>
                <div class="col-md-1">
                    <input type="submit" class="btn btn-block btn-primary btnSearch" value="조회"/>
                </div>
            </div>
        </div>
    </div>
</form>
<!-- END Search Form -->

<table id="boardTable" class="display table-bordered boardTable" >
 <thead>
     <tr>
		<th>번호</th>
		<th>게시물번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일자</th>
     </tr>
 </thead>
</table>

<table id="boardTable2" class="display table-bordered boardTable2" >
 <thead>
     <tr>
		<th>번호</th>
		<th>게시물번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일자</th>
     </tr>
 </thead>
</table>
                                
<script type="text/javascript">
//상위코드목록의 row의 시작 번호

var $boardTable2 = $('.boardTable2').DataTable({
    "processing": true,
    "searching": false,
    "pagingType": "full_numbers",
    "ajax": {
        "url": "<c:url value='/board/listJSON2.do'/>",
        "type": "POST",
        "data": function ( d ) {
        	//추가 검색인자 설정
            d.searchKeyword = $("#searchKeyword").val();
        }
    },
    "select":  {style: 'single'},
    "columnDefs": [{
        "targets": 1,
        "render": function ( data, type, row, meta ) {
          return '<a onclick="jsUpdate(\''+data+'\')">'+data+'</a>';
        }
      }]                
});

var $boardTable = $('.boardTable').DataTable({
    "processing": true,
    "serverSide": true,
    "searching": false,
    "pagingType": "full_numbers",
    "ajax": {
        "url": "<c:url value='/board/listJSON.do'/>",
        "type": "POST",
        "data": function ( d ) {
        	//추가 검색인자 설정
            d.searchKeyword = $("#searchKeyword").val();
        }
    },
    "select":  {style: 'single'},
    "columnDefs": [{
        "targets": 1,
        "render": function ( data, type, row, meta ) {
        	let html = '<a onclick="jsUpdate(\''+data+'\')">'+data+'</a><select>';

        	for (let i=1;i<10;i++) {
        		if (data == i) {
                    html += '<option value=\'"'+i+'\' selected>'+i+'</option>';
        			
        		} else {
                    html += '<option value="'+i+'">'+i+'</option>';
        		}
        	}
          html += '</select>';
          return html ;
        }
      }]                
});


function jsUpdate(id) {
	alert(id);
}

</script>

</body>
</html>