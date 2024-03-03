package client;

import io.restassured.response.Response;
import specs.ISpecs;

import static io.restassured.RestAssured.given;

public abstract class BaseClient<Model> {
    protected final ISpecs<Model> SPECS;
    private final String ID_PATH_PARAM = "{_id}";

    public BaseClient(ISpecs<Model> specs) {
        this.SPECS = specs;
    }

    public Response cadastrar(Model bodyData, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec(bodyData))
                        .header("Authorization", authToken)
                .when()
                        .post();
    }
    public Response buscarPorId(Integer id, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .pathParams("_id", id)
                .when()
                        .get(ID_PATH_PARAM)
                ;
    }

    public Response listar(Integer pagina, Integer quantidadeRegistro, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .param("pagina", pagina)
                        .param("quantidadeRegistros", quantidadeRegistro)
                .when()
                        .get()
                ;
    }

    public Response atualizar(Model bodyData, Integer id, String authToken){
        return
                given()
                        .spec(SPECS.requestSpec(bodyData))
                        .pathParams("_id", id)
                        .header("Authorization", authToken)
                .when()
                        .put(ID_PATH_PARAM);
    }

    public Response excluir(Integer id, String authToken){
        return
                given()
                        .spec(SPECS.requestSpec())
                        .pathParams("_id", id)
                        .header("Authorization", authToken)
                .when()
                        .delete(ID_PATH_PARAM);
    }
}
