package specs.candidato;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import model.Candidato;
import specs.ISpecs;
import specs.InitialSpecs;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

@Data
public class CandidatoSpecs implements ISpecs<Candidato> {
    private static final String BASE_PATH = "/candidato";
    public RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .addRequestSpecification(InitialSpecs.setup())
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .setConfig(config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }

    public RequestSpecification requestSpec(Candidato bodyData){
        return new RequestSpecBuilder()
                .addRequestSpecification(InitialSpecs.setup())
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .setBody(bodyData)
                .build();
    }
}
