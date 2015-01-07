package pl.muciu;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class NoteManager {

    private Path path = Paths.get("C:\\_projects\\security\\data.txt");

    private static Map<String, String> map = new HashMap<>();

    public void appendNote(HttpServletRequest request) throws IOException {

        if (request.getParameterValues("note") != null && request.getParameterValues("note").length > 0 && request.getParameterValues("note")[0] != null ) {
            String note = request.getParameterValues("note")[0];

            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
            writer.write("\n" + note);
            writer.flush();
        }
    }

    public String getContent() throws IOException {
        return Files.readAllLines(path).stream().reduce("", (a, b) -> a + "<li>" + b + "</li>");
    }

    public List<String> getLines() throws IOException {
        return Files.readAllLines(path);
    }

    public void processLoginRequest(HttpServletRequest request, HttpServletResponse response, String mode) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String username = Optional.ofNullable(request.getParameter("username")).orElse("-");
            String passwd = Optional.ofNullable(request.getParameter("password")).orElse("-");

            PasswdVerification pv = new PasswdVerification();
            boolean isValid = false;
            switch (mode.toLowerCase()) {
                case "md5" : isValid = pv.isValidByMd5(username, passwd); break;
                case "bcrypt" : isValid = pv.isValidByBcrypt(username, passwd); break;
                case "sha1" : isValid = pv.isValidBySHA1(username, passwd); break;
                default: throw new RuntimeException("Invalid algorithm");
            }

            if (isValid){
                UUID uid = appendTokenCookie(response);
                map.put(uid.toString(), username);
                String redir = Optional.ofNullable(request.getParameter("redirect")).orElse("");
                response.sendRedirect(redir + "?uniqueToken=" + uid);
            }
        }
    }

    public String renderLoggedUserHeader(HttpServletRequest request) {
        return Optional
                .ofNullable(getValidTokenCookie(request))
                .map(c -> "<h2>Witaj Uzytkowniku : " + map.get(c.getValue()) + "</h2>")
                .orElseGet(() -> "<h2>Jestes niezalogowany!!!!!!!!!!</h2>");
    }

    public static Cookie getValidTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if("MagicToken".equalsIgnoreCase(cookie.getName()) && map.containsKey(cookie.getValue())) {
                return cookie;
            }
        }
        return null;
    }

    public UUID appendTokenCookie(HttpServletResponse respone) {
        UUID uid = UUID.randomUUID();
        Cookie c = new Cookie("MagicToken", uid.toString());
//        c.setHttpOnly(true); or c.setPath("; HttpOnly;");
        respone.addCookie(c);
        return uid;
    }

}