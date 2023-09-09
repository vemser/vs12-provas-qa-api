package provas.processoController;

import io.restassured.http.ContentType;
import model.EmpresaValida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.TokenUtils.getToken;

public class ProcessoControllerTest {
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

    @Test
    public void testListarProcessos() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "1")
                .param("quantidadeRegistros", "5")
           .when()
                .get("/processo")
           .then()
                .log().all()
                .statusCode(200)
        ;
    }

}
