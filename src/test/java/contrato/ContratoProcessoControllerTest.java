package contrato;

import client.processo.ProcessoClient;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static util.RandomData.FAKER;

@DisplayName("CT-API-06 - Processo")
@Feature("Processo - Teste de Contrato")
public class ContratoProcessoControllerTest {
    private final ProcessoClient client = new ProcessoClient();
    private static String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-06.2.1 - Validar contrato de listar processos com sucesso")
    public void testValidarContratoListarProcessos() {
        client
                .listar(FAKER.number().numberBetween(1, 20), FAKER.number().numberBetween(1, 20), token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        JsonSchemaValidator
                                .matchesJsonSchemaInClasspath(
                                        "schemas/lista_processos.json")
                );
    }
}
