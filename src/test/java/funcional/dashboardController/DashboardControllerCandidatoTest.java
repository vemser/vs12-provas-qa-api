package funcional.dashboardController;

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
@Feature("Dashboard - Fluxo Candidato")
public class DashboardControllerCandidatoTest {
    private final DashboardClient client = new DashboardClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-09.3.1 - Buscar dados dashboard como candidato sem sucesso")
    public void buscarDadosDashboardComoCandidatoSemSucesso(){
        client
                .buscarDashboard(token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }
}
