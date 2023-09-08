package provas.temaController;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static util.TokenUtils.getToken;

public class TemaControllerTest {
    private String token;

    @BeforeEach
    public void setup() {
        baseURI = "http://vemser-hml.dbccompany.com.br:39000/vemser/vs12-provas-back";

        try {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("config.properties")).getPath();
            this.token = getToken(filePath, baseURI, "/auth/login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
