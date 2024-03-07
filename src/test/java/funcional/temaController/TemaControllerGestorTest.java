package funcional.temaController;

import client.tema.TemaClient;
import data.factory.TemaDataFactory;
import io.qameta.allure.Feature;
import model.tema.Tema;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

@DisplayName("CT-API-03 - Tema")
@Feature("Tema - Fluxo Gestor")
public class TemaControllerGestorTest {

    private final TemaClient client = new TemaClient();
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenGestor();
    }

    @Test
    @DisplayName("CT-API-03.2.1 - Listar temas como gestor com sucesso")
    public void testListarTemas() {

        client.listar(0,10,token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-03.2.2 - Adicionar tema como gestor com sucesso")
    public void testAdicionarTemaComSucesso() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), token)
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("CT-API-03.2.3 - Adicionar tema já cadastrado como gestor sem sucesso")
    public void testAdicionarTemaJaCadastrado() {

        client.cadastrar(new Tema("JAVA"), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}
