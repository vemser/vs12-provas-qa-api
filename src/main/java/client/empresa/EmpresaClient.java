package client.empresa;

import client.BaseClient;
import model.Empresa;
import model.Processos;
import specs.empresa.EmpresaSpecs;
import specs.processo.ProcessoSpecs;

public class EmpresaClient extends BaseClient<Empresa> {
    public EmpresaClient() {
        super(new EmpresaSpecs());
    }
}

