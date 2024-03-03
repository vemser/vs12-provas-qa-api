package client.empresa;

import client.BaseClient;
import io.restassured.response.Response;
import model.Empresa;
import model.Funcionario;
import specs.empresa.EmpresaSpecs;

import static io.restassured.RestAssured.given;

public class EmpresaClient extends BaseClient<Empresa> {
    private static final String CNPJ_PATH_PARAM = "cnpj/{_cnpj}";
    private static final String ID_EMPRESA_FUNCIONARIO_PATH = "{_id}/funcionario";
    private static final EmpresaSpecs SPECS = new EmpresaSpecs();

    public EmpresaClient() {
        super(new EmpresaSpecs());
    }

    public Response buscarPorCNPJ(String cnpj, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .pathParams("_cnpj", cnpj)
                        .when()
                        .get(CNPJ_PATH_PARAM)
                ;
    }

    public Response cadastrarFuncionarioNaEmpresa(Funcionario funcionario, Integer idEmpresa, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .pathParams("_id", idEmpresa)
                        .body(funcionario)
                        .when()
                        .post(ID_EMPRESA_FUNCIONARIO_PATH)
                ;
    }
}

