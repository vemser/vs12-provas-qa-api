package provas.func1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static provas.utils.TokenUtils.getToken;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Task01Test {

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
        public void testListarTodosOsProdutos(){

            given()
                    .log().all()
                    .header("Authorization", this.token)
            .when()
                    .get("/produtos")
             .then()
                    .log().all()
                    .statusCode(200)
            ;
       }

//=================GET /produtos/{_id}======================================================================================================================
    @Test
    public void testBuscarProdutoPorIdComSucesso(){
        String idProduto = "AVnS6DQxwnMtMkxk";

        given()
        .when()
                .get("/Produtos/" + idProduto)
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testBuscarProdutoPorIdInexistente(){
        String idProduto = "jqxHyFZl7sYUI6M8";

        given()

        .when()
                .get("/Produtos/" + idProduto)
        .then()
                .statusCode(400)
        ;
    }

//==============DELETE /produtos/{_id}====================================================================================================================
    @Test
    public void testExcluirProdutoComSucesso(){
        String _id = "AGfUTIoG6z17BRzQ";

        given()
                .header("Authorization", this.token)
                .pathParam("_id", _id)
        .when()
                .delete("/produtos/{_id}")
        .then()
                .statusCode(200)
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
                .statusCode(200)
        ;
    }
}

