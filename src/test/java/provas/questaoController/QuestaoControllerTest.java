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
    @Test //CT-API-008.2 - Validar cadastrar questão sem sucesso (titulo vazio)
    public void cadastrarQuestaoSemSucesso1() {

    }
    @Test //CT-API-008.3 - Validar cadastrar questão sem sucesso (titulo +30 caracteres)
    public void cadastrarQuestaoSemSucesso2() {

    }
    @Test //CT-API-008.4 - Validar cadastrar questão sem sucesso (enunciado vazio)
    public void cadastrarQuestaoSemSucesso3() {

    }
    @Test //CT-API-008.5 - Validar cadastrar questão sem sucesso (dificuldade vazio)
    public void cadastrarQuestaoSemSucesso4() {

    }
    @Test //CT-API-008.6 - Validar cadastrar questão sem sucesso (dificuldade inválida)
    public void cadastrarQuestaoSemSucesso5() {

    }
    @Test //CT-API-008.7 - Validar cadastrar questão sem sucesso (idTemas vazio)
    public void cadastrarQuestaoSemSucesso6() {

    }
    @Test //CT-API-008.8 - Validar cadastrar questão sem sucesso (idTemas não cadastrado)
    public void cadastrarQuestaoSemSucesso7() {

    }
    @Test //CT-API-008.9 - Validar cadastrar questão sem sucesso (array de alternativas vazio)
    public void cadastrarQuestaoSemSucesso8() {

    }
    @Test //CT-API-008.10 - Validar cadastrar questão sem sucesso (array de alternativas com 1 alternativa)
    public void cadastrarQuestaoSemSucesso9() {

    }
    @Test //CT-API-008.11 - CT-API-018 - Validar cadastrar questão sem sucesso (texto alternativa vazia)
    public void cadastrarQuestaoSemSucesso10() {

    }
    @Test //CT-API-008.12 - Validar cadastrar questão sem sucesso (2 alternativas sem correta)
    public void cadastrarQuestaoSemSucesso11() {

    }
    @Test //CT-API-008.13 - Validar cadastrar questão sem sucesso (2 alternativas corretas)
    public void cadastrarQuestaoSemSucesso12() {

    }
    @Test //CT-API-008.14 - Validar cadastrar questão sem sucesso (idEmpresa vazio)
    public void cadastrarQuestaoSemSucesso13() {

    }
    @Test //CT-API-008.15 - Validar cadastrar questão sem sucesso (idEmpresa não cadastrada)
    public void cadastrarQuestaoSemSucesso14() {

    }
}