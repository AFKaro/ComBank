package model.enums;

public enum EmocaoCliente {

    FELIZ(1),
    PACIENTE(2),
    INDIFERENTE(3),
    IMPACIENTE(4),
    IRRITADO(5);

    int emocao;

    EmocaoCliente(int emocao){
        this.emocao = emocao;
    }

    public static EmocaoCliente getProximaEmocao(EmocaoCliente emocaoAtual){
        return getEmocao(emocaoAtual.emocao + 1);
    }

    private static EmocaoCliente getEmocao(int emocao){
        return switch (emocao) {
            case 1 -> FELIZ;
            case 2 -> PACIENTE;
            case 3 -> INDIFERENTE;
            case 4 -> IMPACIENTE;
            case 5 -> IRRITADO;
            default -> null;
        };
    }
}
