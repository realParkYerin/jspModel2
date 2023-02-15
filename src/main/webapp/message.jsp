<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
String message = (String)request.getAttribute("message");
if(message != null && !message.equals("")){
	if(message.equals("MEMBER_YES")){
		%>
		<script type="text/javascript">
		alert("성공적으로 가입되었습니다");
		location.href = "member?param=login";
		</script>
		<%
	}else{
		%>
		<script type="text/javascript">
		alert("가입되지 않았습니다 다시 가입해 주십시오");
		location.href = "member?param=regi";
		</script>g
		<%		
	}
}

String bbswrite = (String)request.getAttribute("bbswrite");
if(bbswrite != null && !bbswrite.equals("")){
	if(bbswrite.equals("BBS_ADD_OK")){
		%>
		<script type="text/javascript">
		alert("성공적으로 작성되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("다시 작성해 주십시오");
		location.href = "bbs?param=bbswrite";
		</script>
		<%
	}
}

String answer = (String)request.getAttribute("answer");
int seq = (Integer)request.getAttribute("seq");
if(answer != null && !answer.equals("")){
	if(answer.equals("BBS_ANSWER_OK")){
		%>
		<script type="text/javascript">
		alert("답글이 성공적으로 작성되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("답글을 다시 작성해 주십시오");
		let seq = "<%=seq %>";		
		location.href = "bbs?param=answer&seq=" + seq;
		</script>
		<%
	}	
}
%>





