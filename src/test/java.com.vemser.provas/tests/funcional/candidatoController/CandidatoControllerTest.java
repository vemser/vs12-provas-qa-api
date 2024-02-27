package tests.funcional.candidatoController;

import io.restassured.http.ContentType;
import model.Candidato;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CandidatoControllerTest extends Candidato {
    private static Faker faker = new Faker(new Locale("PT-BR"));
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testAdicionarCandidatoComSucessoComoAdmin(){
        Candidato candidato = new Candidato();
        candidato.setEmail("wataxik974@searpen.com");
        candidato.setNome(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
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
    public void testAdicionarCandidatoComEmailInvalidoComoAdmin(){
        Candidato candidato = new Candidato();
        candidato.setEmail("cadidatoteste");
        candidato.setNome("Marcos");

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(candidato)
        .when()
                .post("/candidato")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", Matchers.contains("email: must be a well-formed email address"))
        ;
    }

    @Test
    public void testAdicionarCandidatoComEmailJaCadastradoComoAdmin(){
        Candidato candidato = new Candidato();
        candidato.setEmail("wataxik974@searpen.com");
        candidato.setNome(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(candidato)
        .when()
                .post("/candidato")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("idCandidato", equalTo(48))
        ;
    }

    @Test
    public void testBuscarCandidatoPeloIdComSucessoComoAdmin(){
        Integer idCandidato = 2;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
        .when()
                .get("/candidato/" + idCandidato)

        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(2))
        ;
    }

    @Test
    public void testBuscarCandidatoComIdInvalidoSemSucessoComoAdmin(){
        String idCandidato = "asdasd";

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
        .when()
                .get("/candidato/" + idCandidato)

        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testBuscarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){
        Integer idCandidato = 20000;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
        .when()
                .get("/candidato/" + idCandidato)

        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Candidato não encontrado."))
        ;
    }

    @Test
    public void testListarCandidatosComSucessoComoAdmin(){
        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina",0)
                .param("quantidadeRegistros",10)

        .when()
                .get("/candidato")
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }
    @Test
    public void testListarCandidatosSemSucessoComoAdmin(){
        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina","asd")
                .param("quantidadeRegistros","asd")
        .when()
                .get("/candidato")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testDesativarCandidatoComSucessoComoAdmin(){
        Integer idCandidato = 2;
        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
        .when()
                .delete("/candidato/" + idCandidato)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

    }

    @Test
    public void testDesativarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){
        Integer idCandidato = 20000;
        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)

        .when()
                .delete("/candidato/" + idCandidato)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",equalTo("Candidado não existe."))
        ;

    }


}
