import static com.jayway.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import org.junit.Test;

/**
 * Created by lukasz.wojcik on 2015-01-07.
 */
public class AuthTest {

    @Test
    public void encryptionPerformanceTest() {

        /**
         * Cases:
         * sha1 : username = lukasz (lukasz123)
         * md5 : username = lukasz
         * bcrypt : username = lukasz,
         *                      test,
         *                      strong
         *
         */

        int tries = 10; // sha1 = 16773ms, md5 = 174, bcrypt (lukasz123) = 237, bcrypt (test) = 4200, bcrypt (strong)
        long startTime = currentTimeMillis();

        for(int i = 0; i < tries + 1; ++i) {
            out.println("try no. " + i);
            given()
                    .formParam("username", "lukasz")
                    .formParam("password", randomAlphanumeric(10))
            .when()
                    .post("http://localhost:9090/security-bcrypt/");
            //.then().log().everything();
            if (i == 0) {
                //first request as a warmup
                startTime = currentTimeMillis();
            }
        }

        long duration = currentTimeMillis() - startTime;

        out.println(format("Duration time for %d tries is %d", tries, duration));

    }
}
