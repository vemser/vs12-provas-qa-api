package provas.func2;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static provas.utils.TokenUtils.getToken;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Task02Test {
    private String token;

    @BeforeEach
    public void setup() {
        baseURI = "http://localhost:3000";

        try {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("config.properties")).getPath();
            this.token = getToken(filePath, baseURI, "/login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//======================(Get  /produtos)=====================================================================================================================
    @Test
    public void testListarTodosOsProdutos() {

        given()
                .header("Authorization", this.token)
        .when()
                .get("/produtos")
        .then()
                .log().all()
                .statusCode(200)
                .body("quantidade", Matchers.greaterThanOrEqualTo(39))
        ;
    }

//=================GET /produtos/{_id}======================================================================================================================
    @Test
    public void testBuscarProdutoPorIdComSucesso() {
        String idProduto = "AVnS6DQxwnMtMkxk";

        given()
        .when()
                .get("/Produtos/" + idProduto)
        .then()
                .statusCode(200)
                .body("_id", equalTo("AVnS6DQxwnMtMkxk"))
        ;
    }

    @Test
    public void testBuscarProdutoPorIdInexistente() {
        String idProduto = "jqxHyFZl7sYUI6M8";

        given()
        .when()
                .get("/Produtos/" + idProduto)
        .then()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;
    }

//==============DELETE /produtos/{_id}====================================================================================================================
    @Test
    public void testExcluirProdutoComSucesso(){
        String _id = "1RTVR3n6byWLJYXc";

        given()
                .header("Authorization", this.token)
                .pathParam("_id", _id)
        .when()
                .delete("/produtos/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testExcluirProdutoComIdInvalido(){
        String _id = "8888888888888888888";

        given()
                .header("Authorization", this.token)
                .pathParam("_id", _id)
        .when()
                .delete("/produtos/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }
}


