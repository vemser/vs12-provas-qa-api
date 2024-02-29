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

    private String basePath;

    @Override
    public RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .addRequestSpecification(InitialSpecs.setup())
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }
    @Override
    public RequestSpecification requestSpec(Model bodyData){
        return new RequestSpecBuilder()
                .addRequestSpecification(InitialSpecs.setup())
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setBody(bodyData)
                .build();
    }
}
