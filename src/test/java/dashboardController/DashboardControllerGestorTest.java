package dashboardController;

import client.dashboard.DashboardClient;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("CT-API-09 - Dashboard")
@Feature("Dashboard - Fluxo Gestor")
public class DashboardControllerGestorTest {
    private final DashboardClient client = new DashboardClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenGestor();
    }

    @Test
    @DisplayName("CT-API-09.2.1 - Buscar dados dashboard global como gestor sem sucesso")
    public void buscarDadosDashboardGlobalComoGestorSemSucesso(){
        client
                .buscarDashboardGlobal(token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("qtdCandidatos", notNullValue())
        ;
    }

    @Test
    @DisplayName("CT-API-09.2.2 - Buscar dados dashboard empresa como gestor sem sucesso")
    public void buscarDadosDashboardEmpresaComoGestorComSucesso(){
        client
                .buscarDashboardEmpresa(token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }
}
