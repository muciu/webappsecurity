package pl.muciu;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by lukasz.wojcik on 2014-12-14.
 */
public class PasswdVerification {
    private static HashMap<String, String> mapBcrypt = new HashMap<String, String>();
    private static HashMap<String, String> mapSHA1 = new HashMap<String, String>();
    static {


        mapBcrypt.put("lukasz", "$2a$04$vEpl424YLv6PkxZyxgYzSO5L/iFLzbgljIB7las2I0IgC/ryU.j3e");//lukasz123
        mapSHA1.put("lukasz", "06375683e1de127e1d2ac4fa65462e2aa10b0f054ce0cb10cb76140b45bc1754334b40ed3be63dfb");//lukasz123
        mapBcrypt.put("test", "$2a$12$rc.ZAGAVxHNknLeS6MhMxeJSK3mbtPHNJtQsOTiAMfBwfdIGhmHu6"); //STRONG!!! test123
        mapBcrypt.put("strong", "$2a$15$3BEuPqTUasM0D5fo.BZTzufbtXhDJBgaiTRkBq.DoJ7d5/kebFZMC"); //STRONG!!! test123
        mapSHA1.put("test", "f0a340062e91777fcb9338d2ae4b5c29c2491f366353f17cf168bdca9459058f99647e6cd88d9910"); //test123

    }

    public boolean isValid(String username, String passwd) {
//        System.out.println(">>>>" + genBCryptHash("strong", 15));
        if (mapBcrypt.containsKey(username)) {
            String hash = mapBcrypt.get(username);
            return BCrypt.checkpw(passwd, hash);
        }
        return false;
    }

    public boolean isValidByHash(String username, String passwd) {
        if (mapSHA1.containsKey(username)) {
            String hash = mapSHA1.get(username);
            StandardPasswordEncoder c = new StandardPasswordEncoder();
//            System.out.println(">>>>" + genBCryptHash("lukasz123", 4));
//            System.out.println(">>>>" + genBCryptHash("test123", 12));
            System.out.println(">>>>" + genBCryptHash("strong", 14));
            return c.matches(passwd, hash);
        }
        return false;
    }

    private String genBCryptHash(String passwd, int salt) {
        String strong_salt = BCrypt.gensalt(salt);
        return BCrypt.hashpw(passwd, strong_salt);
    }


    public void ensureAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NoteManager.getValidTokenCookie(request) == null) {
            response.sendRedirect("index.jsp?redirect=index.jsp");
        }
    }

}
