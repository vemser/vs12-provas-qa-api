package dashboardController;

import client.dashboard.DashboardClient;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static org.hamcrest.Matchers.equalTo;
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
    @DisplayName("CT-API-09.2.1 - Buscar dados dashboard como gestor com sucesso")
    public void buscarDadosDashboardComoGestorComSucesso(){
        client
                .buscarDashboard(token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("qtdCandidatos", notNullValue())
                .body("qtdEmpresas", equalTo(0))
        ;
    }
}
