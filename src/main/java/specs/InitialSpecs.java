package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InitialSpecs {
    private static final String BASE_URL = "http://26.73.130.213:8080";

    private InitialSpecs(){};
    public static RequestSpecification setup() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }
}
