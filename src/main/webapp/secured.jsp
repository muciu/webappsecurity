<%@ page import="pl.muciu.*" %>
<%
PasswdVerification pv = new PasswdVerification();
pv.ensureAuthenticatedUser(request, response);

NoteManager manager = new NoteManager();

%>


<%= manager.renderLoggedUserHeader(request) %><br/>


To jest sekcja z tajemnymi danymi... tylko zalogowani maja tutaj wjazd.
