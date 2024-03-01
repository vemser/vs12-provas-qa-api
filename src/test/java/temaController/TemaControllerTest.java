package temaController;

import client.tema.TemaClient;
import dataFactory.TemaDataFactory;
import model.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

public class TemaControllerTest extends TemaDataFactory {

    private TemaClient client = new TemaClient();
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Listar temas")
    public void testListarTemas() {

        client.listar(0,10,token)
                .then()
                .statusCode(200)
        ;
    }
    @Test
    @DisplayName("Adicionar tema com sucesso")
    public void testAdicionarTemaComSucesso() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), token)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Adicionar tema já cadastrado")
    public void testAdicionarTemaJaCadastrado() {

        client.cadastrar(new Tema("JAVA"), token)
                .then()
                .statusCode(400);
    }
}
