package client.dashboard;

import client.BaseClient;
import io.restassured.response.Response;
import specs.dashboard.DashboardSpecs;

import static io.restassured.RestAssured.given;

public class DashboardClient extends BaseClient<Object> {
    
    public DashboardClient() {
        super(new DashboardSpecs());
    }
    
    public Response buscarDashboard(String authToken){
        return given()
                .spec(SPECS.requestSpec())
                .header("Authorization", authToken)
        .when()
                .get()
        ;
    }
}
