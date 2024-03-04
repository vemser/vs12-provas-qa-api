package data.factory;

import model.questao.objetiva.AlternativaQuestaoObjetiva;
import model.questao.objetiva.QuestaoObjetiva;
import model.questao.tecnica.CasoTesteQuestaoProvaTecnica;
import model.questao.tecnica.QuestaoTecnica;
import model.questao.tecnica.TemplateQuestaoTecnica;

import java.util.ArrayList;
import java.util.List;

import static util.RandomData.FAKER;

public class QuestaoDataFactory {

    private static final String[] NIVEL_DIFICULDADE = {"FACIL", "MEDIO", "DIFICIL"};
    private static final String[] OPCOES_LINGUAGENS_PROVA_TECNICA = {"JAVA", "C"};

    public static QuestaoObjetiva gerarQuestaoObjetivaValida() {
        return gerarQuestaoObjetivaAleatoria();
    }

    public static QuestaoTecnica gerarQuestaoTecnicaValida() {
        return gerarQuestaoTecnicaAleatoria();
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemEnunciado() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setEnunciado("");

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemDificuldade() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setDificuldade(null);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemTipo() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setTipoQuestao(null);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemNenhumTema() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setIdTemas(new int[0]);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemAlternativas() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setAlternativaQuestaoObjetivas(new AlternativaQuestaoObjetiva[0]);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaComMaisDeUmaOpcaoCorreta() {
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        AlternativaQuestaoObjetiva[] alternativaQuestaoObjetivas = questaoObjetiva.getAlternativaQuestaoObjetivas();
        alternativaQuestaoObjetivas[0].setCorreta(true);
        alternativaQuestaoObjetivas[1].setCorreta(true);

        questaoObjetiva.setAlternativaQuestaoObjetivas(alternativaQuestaoObjetivas);
        return questaoObjetiva;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemEnunciado() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setEnunciado(null);

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemTitulo() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setTitulo(null);

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemDificuldade() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setDificuldade(null);

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemTipoQuestao() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setTipoQuestao(null);

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemNenhumTema() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setIdTemas(new ArrayList<>());

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemNenhumTemplate() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setTemplates(new ArrayList<>());

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaSemNenhumCasoTestes() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setCasoTestes(new ArrayList<>());

        return questao;
    }

    public static QuestaoTecnica gerarQuestaoTecnicaInvalidaComTituloMaiorQueOLimite() {
        QuestaoTecnica questao = gerarQuestaoTecnicaAleatoria();
        questao.setTitulo(FAKER.lorem().paragraph());

        return questao;
    }

    private static QuestaoObjetiva gerarQuestaoObjetivaAleatoria() {

        int quantidadeDeAlternativas = FAKER.number().numberBetween(2, 6);
        AlternativaQuestaoObjetiva[] listaAlternativaQuestaoObjetivas = new AlternativaQuestaoObjetiva[quantidadeDeAlternativas];

        for (int i = 0; i < quantidadeDeAlternativas; i++) {
            AlternativaQuestaoObjetiva alternativaQuestaoObjetiva = new AlternativaQuestaoObjetiva();
            alternativaQuestaoObjetiva.setAlternativa(FAKER.lorem().sentence());
            alternativaQuestaoObjetiva.setCorreta(false);

            listaAlternativaQuestaoObjetivas[i] = alternativaQuestaoObjetiva;
        }

        listaAlternativaQuestaoObjetivas[FAKER.number().numberBetween(0, listaAlternativaQuestaoObjetivas.length)].setCorreta(true);

        QuestaoObjetiva questaoObjetiva = new QuestaoObjetiva();
        questaoObjetiva.setTitulo(FAKER.lorem().word());
        questaoObjetiva.setEnunciado(FAKER.lorem().sentence());
        questaoObjetiva.setDificuldade(NIVEL_DIFICULDADE[FAKER.number().numberBetween(0, NIVEL_DIFICULDADE.length)]);
        questaoObjetiva.setIdTemas(new int[]{FAKER.number().numberBetween(1, 6)});
        questaoObjetiva.setAlternativaQuestaoObjetivas(listaAlternativaQuestaoObjetivas);
        questaoObjetiva.setTipoQuestao("OBJETIVA");
        questaoObjetiva.setIdEmpresa(0);

        return questaoObjetiva;
    }

    private static QuestaoTecnica gerarQuestaoTecnicaAleatoria() {
        QuestaoTecnica questao = new QuestaoTecnica();

        questao.setTitulo(FAKER.lorem().word());
        questao.setEnunciado(FAKER.lorem().sentence());
        questao.setTipoQuestao("TECNICA");
        questao.setDificuldade(NIVEL_DIFICULDADE[FAKER.number().numberBetween(0, NIVEL_DIFICULDADE.length)]);
        questao.setIdTemas(List.of(FAKER.number().numberBetween(1, 5)));
        questao.setIdEmpresa(0);

        int qtdCasoTestes = FAKER.number().numberBetween(2, 21);
        int qtdTemplates = FAKER.number().numberBetween(1, 6);
        List<CasoTesteQuestaoProvaTecnica> listaCasoTestes = new ArrayList<>();
        List<TemplateQuestaoTecnica> listaTemplates = new ArrayList<>();

        for (int i = 0; i < qtdCasoTestes; i++) {
            listaCasoTestes.add(gerarCasoDeTesteAleatorio());
        }
        for (int i = 0; i < qtdTemplates; i++) {
            listaTemplates.add(gerarTemplateAleatorio());
        }
        questao.setCasoTestes(listaCasoTestes);
        questao.setTemplates(listaTemplates);

        return questao;
    }

    private static CasoTesteQuestaoProvaTecnica gerarCasoDeTesteAleatorio() {
        return new CasoTesteQuestaoProvaTecnica(
                FAKER.lorem().word(),
                FAKER.lorem().word(),
                FAKER.number().numberBetween(0, 2)
        );
    }

    private static TemplateQuestaoTecnica gerarTemplateAleatorio() {
        return new TemplateQuestaoTecnica(
                OPCOES_LINGUAGENS_PROVA_TECNICA[FAKER.number().numberBetween(0, OPCOES_LINGUAGENS_PROVA_TECNICA.length - 1)],
                FAKER.lorem().sentence()
        );
    }
}
