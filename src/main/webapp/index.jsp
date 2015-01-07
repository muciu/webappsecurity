<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.crypto.bcrypt.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.*" %>
<%@ page import="pl.muciu.*" %>

<%
NoteManager manager = new NoteManager();
manager.processLoginRequest(request, response, "sha1");
manager.appendNote(request);

PasswdVerification pv = new PasswdVerification();
pageContext.setAttribute("notes", manager.getLines());

%>

<html>
<body>
<h2>Hello World!</h2>
<form method="get" action="search.jsp">
Search box: <input type="text" value="Search..." name="q"> <input type="submit" value="go">
</form>
<br/>
<%= manager.renderLoggedUserHeader(request) %><br/>


<form method="post">
    <textarea name="note" rows="10" cols="40"></textarea><br/>
    <input type="submit" value="add Note">
</form>

<ul>
    <c:forEach items="${notes}" var="note">
        <li>
            ${note}
        </li>
    </c:forEach>
</ul>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<form method="post">
    Username <input name="username"><br/>
    Passwd <input name="password"><br/>
    <input type="submit" >
</form>
<a href="secured.jsp">Sekcja tylko dla zalogowanych</a>



</body>
</html>
