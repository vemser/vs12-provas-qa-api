package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InitialSpecs {
    public static final String BASE_URL = "http://vemser-dbc.dbccompany.com.br:39000/vemser/vs12-provas-back";
    private InitialSpecs(){};
    public static RequestSpecification setup() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }
}
