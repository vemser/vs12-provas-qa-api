package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

@Data
@AllArgsConstructor
public abstract class BaseSpecs<Model> implements ISpecs<Model>{

    private static final String BASE_URL_ONRENDER = "https://vs12-provas-back-release.onrender.com";
    private static final String BASE_URL_ALTERNATIVE = "http://26.73.130.213:8080";
    private static final String BASE_URL = "http://vemser-dbc.dbccompany.com.br:39000/vemser/vs12-provas-back";
    private String basePath;

    public static RequestSpecification setup() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }

    @Override
    public RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .addRequestSpecification(setup())
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }
    @Override
    public RequestSpecification requestSpec(Model bodyData){
        return new RequestSpecBuilder()
                .addRequestSpecification(setup())
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setBody(bodyData)
                .build();
    }

}
