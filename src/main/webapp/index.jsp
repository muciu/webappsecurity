<%@ page import="org.springframework.security.crypto.bcrypt.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.*" %>
<%@ page import="pl.muciu.*" %>

<%
NoteManager manager = new NoteManager();

PasswdVerification pv = new PasswdVerification();


pv.isValidByHash("username", "passwod");

manager.processLoginRequest(request, response);
String strong_salt = BCrypt.gensalt(4);
String passwd = "dupaBlada";
String hash = BCrypt.hashpw(passwd, strong_salt);



Path path = FileSystems.getDefault().getPath( "C:\\_projects\\security\\data.txt");
List<String> lista = Files.readAllLines(path);

manager.appendNote(request);
%>

<html>
<body>
<h2>Hello World!</h2>
<%= manager.renderLoggedUserHeader(request) %><br/>



<form method="post">
    <textarea name="note" rows="10" cols="40"></textarea><br/>
    <input type="submit" value="add Note">
</form>

<ul>
    <%= manager.getContent() %>
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
