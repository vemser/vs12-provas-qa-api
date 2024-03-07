package funcional.processoController;

import client.processo.ProcessoClient;
import io.qameta.allure.Feature;
import model.processo.Processo;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.ProcessoDataFactory.gerarProcessoValido;
import static util.RandomData.FAKER;

@DisplayName("CT-API-06 - Processo")
@Feature("Processo - Fluxo Candidato")
public class ProcessoControllerCandidatoTest {
    private final ProcessoClient client = new ProcessoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-06.3.1 - Listar processos como candidato sem sucesso")
    public void testListarProcessos() {

        client
                .listar(FAKER.number().numberBetween(0, 20), FAKER.number().numberBetween(1, 20), token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-06.3.2 - Buscar processo por ID como candidato sem sucesso.")
    public void testBuscarProcessoPorId() {

        client
                .buscarPorId(FAKER.number().positive(), token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-06.3.3 - Deletar processo como candidato sem sucesso")
    public void testDeleteProcessoPorId() {

        client
                .excluir(FAKER.number().positive(), token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-06.3.4 - Adicionar processo como candidato sem sucesso")
    public void testAdicionarProcessoSemSucesso() {

        Processo processo = gerarProcessoValido();

        client
                .cadastrarProcesso(processo, processo.getIdEmpresa(), token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-06.3.5 - Atualizar processo como candidato sem sucesso")
    public void testAtualizarProcessoSemSucesso() {

        Processo processo = gerarProcessoValido();

                client
                        .atualizar(processo, processo.getIdEmpresa(), token)
                .then()
                        .statusCode(HttpStatus.SC_FORBIDDEN)
                ;
    }
}
