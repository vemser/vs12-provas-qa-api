# Projeto - Provas DBC

# Testes com RestAssured para API

Projeto de qualidade de software, desenvolvido pela equipe de estagiários de QA
durante o Vem Ser 13° edição, para a plataforma DBC Provas

## Rodando o projeto localmente

Clone o repositório no local desejado;
```bash
  git clone git@github.com:vemser/vs12-provas-qa-api.git
```

Inclua, no ambiente em que rodará a aplicação, as seguintes variáveis de ambiente utilizando credenciais válidas:

````properties
EMAIL_ADM={EMAIL DO ADMINISTRADOR}
SENHA_ADM={SENHA DO ADMINISTRADOR}
EMAIL_GESTOR={EMAIL DO GESTOR}
SENHA_GESTOR={SENHA DO GESTOR}
EMAIL_CANDIDATO={EMAIL DO CANDIDATO}
SENHA_CANDIDATO={SENHA DO CANDIDATO}
````

## Features cobertas

- Endpoint de Candidato
- Endpoint de Empresa
- Endpoint de Funcionário
- Endpoint de Processo
- Endpoint de Questão
- Endpoint de Tema
- Endpoint de Login
- Endpoint de Dashboard


## Referências

- [Rest Assured Documentation](https://rest-assured.io/)
- [Allure Documentation](https://allurereport.org/docs/junit5/)