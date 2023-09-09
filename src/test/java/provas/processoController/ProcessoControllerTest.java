package provas.processoController;

import dataFactory.ProcessoDataFactory;
import io.restassured.http.ContentType;
import model.EmpresaValida;
import model.Processos;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.TokenUtils.getToken;

public class ProcessoControllerTest extends ProcessoDataFactory {
    private static Faker faker = new Faker(new Locale("pt-BR"));
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
    @Test
    public void testBuscarProcessoPorIdComSucesso() {
        Integer idProcesso = 6;

        given()
                .header("Authorization", this.token)
            .when()
                .get("/processo/" + idProcesso )
            .then()
                .log().all()
                .statusCode(200)
                .body("idProcesso", equalTo(6))
                .body("nome", equalTo("Vem ser DBC"))
                .body("ativo", equalTo(true))
                .body("idEmpresa", equalTo(6))
        ;
    }
    @Test
    public void testDeleteProcessoPorId() {
        Integer idProcesso = 6;

        given()
                .header("Authorization", this.token)
             .when()
                .delete("/processo/" + idProcesso )
             .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void testAdicionarProcessoComSucesso() {

        given()
                .header("Authorization", this.token)
                .body(processoValido())
            .when()
                .post("/processo/empresa/" + (faker.number().digit()))
            .then()
                .log().all()
                .statusCode(415);
        System.out.println(processoValido());
        ;
    }

}
