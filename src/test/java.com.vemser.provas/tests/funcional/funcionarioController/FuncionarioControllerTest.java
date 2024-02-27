package tests.funcional.funcionarioController;

import dataFactory.FuncionarioDataFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FuncionarioControllerTest extends FuncionarioDataFactory {
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testListarFuncionarios() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/funcionario/1/funcionario")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testListarFuncionarioPeloId() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/funcionario/1/funcionario/8")
            .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testAtualizarFuncionario() {
        Response response = given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(novoFuncionarioNaEmpresa())
           .when()
                .post("/empresa/0/funcionario")
           .then()
                .statusCode(201)
                .extract().response();

        String idFuncionarioNovo = response.jsonPath().getString("idFuncionario");

        Funcionario funcionario = novoFuncionarioAtualizado();

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(funcionario)
            .when()
                .put("/funcionario/" + idFuncionarioNovo)
            .then()
                .statusCode(200)
                .body("email", equalTo(funcionario.getEmail()))
                .body("nome", equalTo(funcionario.getNome()))
                .body("cargo", equalTo(funcionario.getCargo()))
        ;
    }
}
