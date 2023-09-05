package client;

import static io.restassured.RestAssured.*;

public abstract class BaseClient {

    public  static void initConfig(){

        baseURI="http://localhost:3000";

        enableLoggingOfRequestAndResponseIfValidationFails();
    }
}




