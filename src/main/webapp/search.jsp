<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.crypto.bcrypt.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.util.*" %>
<%@ page import="pl.muciu.*" %>


<h1>Search results for <%= request.getParameter("q") %> </h1>
Item 1 <br/>
...