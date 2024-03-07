package funcional.temaController;

import client.tema.TemaClient;
import data.factory.TemaDataFactory;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

@DisplayName("CT-API-03 - Tema")
@Feature("Tema - Fluxo Candidato")
public class TemaControllerCandidatoTest {

    private final TemaClient client = new TemaClient();
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-03.3.1 - Listar temas como candidato sem sucesso")
    public void testListarTemas() {

        client.listar(0,10,token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-03.3.2 - Adicionar tema como candidato sem sucesso")
    public void testAdicionarTema() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
