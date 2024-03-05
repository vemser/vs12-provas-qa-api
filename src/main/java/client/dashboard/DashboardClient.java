package client.dashboard;

import client.BaseClient;
import io.restassured.response.Response;
import specs.dashboard.DashboardSpecs;

import static io.restassured.RestAssured.given;

public class DashboardClient extends BaseClient<Object> {

    private final String COMPLEMENTO_EMPRESA_PATH = "/empresa/";
    public DashboardClient() {
        super(new DashboardSpecs());
    }

    public Response buscarDashboardEmpresa(String authToken){
        return given()
                .spec(SPECS.requestSpec())
                .header("Authorization", authToken)
        .when()
                .get(COMPLEMENTO_EMPRESA_PATH)
        ;
    }
    public Response buscarDashboardGlobal(String authToken){
        return given()
                .spec(SPECS.requestSpec())
                .header("Authorization", authToken)
        .when()
                .get()
        ;
    }
}
