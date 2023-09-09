package provas.candidatoController;

import io.restassured.http.ContentType;
import model.Candidato;
import model.EmpresaValida;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.TokenUtils.getToken;

public class CandidatoControllerTest extends Candidato {
    private static Faker faker = new Faker(new Locale("PT-BR"));
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
    public void testAdicionarCandidatoComSucesso(){
        Candidato candidato = new Candidato();
        candidato.setEmail("wataxik974@searpen.com");
        candidato.setNome(faker.name().firstName());

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(candidato)
        .when()
                .post("/candidato")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    public void testAdicionarCandidatoComEmailInvalido(){
        Candidato candidato = new Candidato();
        candidato.setEmail("cadidatoteste");
        candidato.setNome("Marcos");

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(candidato)
        .when()
                .post("/candidato")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", Matchers.contains("email: must be a well-formed email address"))
        ;
    }

    @Test
    public void testAdicionarCandidatoComEmailJaCadastrado(){
        Candidato candidato = new Candidato();
        candidato.setEmail("wataxik974@searpen.com");
        candidato.setNome(faker.name().firstName());

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(candidato)
        .when()
                .post("/candidato")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .body("idCandidato", equalTo(48))
        ;
    }

    @Test
    public void testBuscarCandidatoPeloIdComSucesso(){
        Integer idCandidato = 2;

        given()
                .header("Authorization", this.token)
        .when()
                .get("/candidato/" + idCandidato)

        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(2))
                .body("email", equalTo("usuario2@email.com"))
        ;
    }

    @Test
    public void testBuscarCandidatoComIdInvalidoSemSucesso(){
        String idCandidato = "asdasd";

        given()
                .header("Authorization", this.token)
        .when()
                .get("/candidato/" + idCandidato)

        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testBuscarCandidatoComIdNaoCadastradoSemSucesso(){
        Integer idCandidato = 20000;

        given()
                .header("Authorization", this.token)
                .when()
                .get("/candidato/" + idCandidato)

                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Candidato não encontrado."))
        ;
    }



//    public void testBuscarEmpresaPorIdComSucesso() {
//        Integer idEmpresa = 5;
//
//        given()
//                .header("Authorization", this.token)
//                .when()
//                .get("/empresa/" + idEmpresa )
//                .then()
//                .statusCode(200)
//                .body("idEmpresa", equalTo(5))
//                .body("nome", equalTo("menor preco 02"))
//                .body("cnpj", equalTo("12352479000104"))
//        ;
//    }





}
