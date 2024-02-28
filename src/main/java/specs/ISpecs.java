package specs;

import io.restassured.specification.RequestSpecification;

public interface ISpecs<Model> {
    RequestSpecification requestSpec();
    RequestSpecification requestSpec(Model bodyData);
}
