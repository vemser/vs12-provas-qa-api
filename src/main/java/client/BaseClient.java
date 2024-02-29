package client;

import io.restassured.response.Response;
import model.Processos;
import specs.ISpecs;

import static io.restassured.RestAssured.given;

public abstract class BaseClient<Model> {
    private final ISpecs<Model> SPECS;
    private final String ID_PATH_PARAM = "{_id}";
    private final String PATH_ID_PATH_PARAM = "{_path}/{_id}";
    private final String ID_PATH_PATH_PARAM = "{_id}/{_path}";

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

    public Response cadastrar(Model bodyData, String path, Integer id, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec(bodyData))
                        .header("Authorization", authToken)
                        .pathParam("_path",path)
                        .pathParam("_id",id)
                        .when()
                        .post(PATH_ID_PATH_PARAM);
    }

    public Response cadastrar(Model bodyData, Integer id, String path, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec(bodyData))
                        .header("Authorization", authToken)
                        .pathParam("_id",id)
                        .pathParam("_path",path)
                        .when()
                        .post(ID_PATH_PATH_PARAM);
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

    public Response buscarPorId(String id, String authToken) {
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
                        .param("pagina",pagina)
                        .param("quantidadeRegistros",quantidadeRegistro)
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
