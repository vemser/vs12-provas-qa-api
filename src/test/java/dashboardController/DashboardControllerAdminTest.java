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
@Feature("Dashboard - Fluxo Admin")
public class DashboardControllerAdminTest {
    private final DashboardClient client = new DashboardClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-09.1.1 - Buscar dados dashboard global como admin com sucesso")
    public void buscarDadosDashboardAdminComSucesso(){
        client
                .buscarDashboardGlobal(token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("qtdEmpresas", notNullValue())
        ;
    }

    @Test
    @DisplayName("CT-API-09.1.2 - Buscar dados dashboard global com token inválido sem sucesso")
    public void buscarDadosDashboardAdminComTokenInvalidoComSucesso(){
        client
                .buscarDashboardGlobal("TOKEN_INVALIDO")
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-09.1.3 - Buscar dados dashboard empresa como admin com sucesso")
    public void buscarDadosDashboardEmpresaComoAdminComSucesso(){
        client
                .buscarDashboardEmpresa(token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("qtdColaboradores", notNullValue())
        ;
    }

    @Test
    @DisplayName("CT-API-09.1.4 - Buscar dados dashboard empresa como admin com token inválido sem sucesso")
    public void buscarDadosDashboardEmpresaComoAdminComTokenInvalidoComSucesso(){
        client
                .buscarDashboardEmpresa("TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }
}
