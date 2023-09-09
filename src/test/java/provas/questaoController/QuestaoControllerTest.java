package provas.questaoController;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import provas.pojo.AlternativaPojo;
import provas.pojo.QuestaoPojo;
import static org.hamcrest.Matchers.equalTo;


import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.TokenUtils.getToken;

public class QuestaoControllerTest {
    private String token;

//    @BeforeEach
//    public void setup() {
//        baseURI = "http://vemser-hml.dbccompany.com.br:39000/vemser/vs12-provas-back";
//
//        try {
//            String filePath = "src/properties/dados.properties";
//            this.token = getToken(filePath, baseURI, "/auth/login");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

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
    public void buscarQuestaoPorId(){
        String idQuestao = "19";
        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param(idQuestao)
           .when()
                .get("/questao/20" )
           .then()
                .log().all()
                .statusCode(200)
        ;
    }
    @Test
    public void buscarQuestaoPorIdInexistente(){

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param("aaaa")
           .when()
                .get("/questao/aaaa" )
           .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test //CT-API-008.1 - Validar cadastrar questão com sucesso
    public void cadastrarQuestaoComSucesso() {

        AlternativaPojo alternativaPojo1 = new AlternativaPojo();
        alternativaPojo1.setAlternativa("esta é uma alternativa verdadeira");
        alternativaPojo1.setCorreta(true);
        AlternativaPojo alternativaPojo2 = new AlternativaPojo();
        alternativaPojo2.setAlternativa("esta é uma alternativa falsa");

        QuestaoPojo questaoPojo = new QuestaoPojo();
        questaoPojo.setTitulo("Titulo de questão teste");
        questaoPojo.setEnunciado("Enunciado de questão teste");
        questaoPojo.setDificuldade("FACIL");
        questaoPojo.setIdTemas(new int[]{1});
        questaoPojo.setAlternativas(new AlternativaPojo[]{alternativaPojo1,alternativaPojo2});
        questaoPojo.setIdEmpresa(1);

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(questaoPojo)
        .when()
                .post("/questao")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }
    @Test
    public void cadastrarQuestaoSemEnunciadoeDificuldade() {
        AlternativaPojo alternativaPojo1 = new AlternativaPojo();
        alternativaPojo1.setAlternativa("esta é uma alternativa verdadeira");
        alternativaPojo1.setCorreta(true);
        AlternativaPojo alternativaPojo2 = new AlternativaPojo();
        alternativaPojo2.setAlternativa("esta é uma alternativa falsa");

        QuestaoPojo questaoPojo = new QuestaoPojo();
        questaoPojo.setIdTemas(new int[]{1});
        questaoPojo.setAlternativas(new AlternativaPojo[]{alternativaPojo1,alternativaPojo2});
        questaoPojo.setIdEmpresa(1);

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(questaoPojo)
                .when()
                .post("/questao")
                .then()
                .log().all()
                .statusCode(400)
        ;

    }

}