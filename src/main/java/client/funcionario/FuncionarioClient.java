package client.funcionario;

import client.BaseClient;
import io.restassured.response.Response;
import model.Funcionario;
import specs.funcionario.FuncionarioSpecs;

import static io.restassured.RestAssured.given;

public class FuncionarioClient extends BaseClient<Funcionario> {

    private final String ID_FUNCIONARIO_PATH_PARAM = "{_idFuncionario}";
    private final String ID_EMPRESA_PATH_PARAM = "{_idEmpresa}";
    private final String COMPLEMENTO_PATH = "/funcionario/";

    public FuncionarioClient() {
        super(new FuncionarioSpecs());
    }


    public Response listar(Integer idEmpresa, Integer pagina, Integer quantidadeRegistro, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .pathParams("_idEmpresa", idEmpresa)
                        .param("pagina",pagina)
                        .param("quantidadeRegistros",quantidadeRegistro)
                .when()
                        .get(ID_EMPRESA_PATH_PARAM + COMPLEMENTO_PATH)
                ;
    }

    public Response excluir(Integer idEmpresa, Integer idFuncionario, String authToken){
        return
                given()
                        .spec(SPECS.requestSpec())
                        .pathParams("_idEmpresa", idEmpresa)
                        .pathParams("_idFuncionario", idFuncionario)
                        .header("Authorization", authToken)
                .when()
                        .delete(ID_EMPRESA_PATH_PARAM + COMPLEMENTO_PATH + ID_FUNCIONARIO_PATH_PARAM)
                ;
    }

    public Response buscarPorId(Integer idEmpresa, Integer idFuncionario, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .pathParams("_idEmpresa", idEmpresa)
                        .pathParams("_idFuncionario", idFuncionario)
                .when()
                        .get(ID_EMPRESA_PATH_PARAM + COMPLEMENTO_PATH + ID_FUNCIONARIO_PATH_PARAM)
                ;
    }

    @Override
    public Response atualizar(Funcionario bodyData, Integer idFuncionario, String authToken){
        return
                given()
                        .spec(SPECS.requestSpec(bodyData))
                        .pathParams("_idFuncionario", idFuncionario)
                        .header("Authorization", authToken)
                        .when()
                        .put(ID_FUNCIONARIO_PATH_PARAM);
    }
}
