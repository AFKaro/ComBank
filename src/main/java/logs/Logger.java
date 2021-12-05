package logs;


import model.Cliente;
import model.enums.EmocaoCliente;
import model.enums.StatusCliente;
import util.CsvUtil;

import java.util.ArrayList;
import java.util.List;


public class Logger {

    private final List<Log> logs;
    private final int limiteLogs;

    public Logger(int limiteLogs) {
        this.limiteLogs = limiteLogs;
        this.logs = new ArrayList<>();
    }

    public void printAcao(Cliente cliente, Double valor, Double valorConta, int qtdClientes) {
        printNumLog();
        System.out.println("O cliente: " + getNomeCliente(cliente.getNome()) + " esta " + getEmocaoCliente(cliente.getEmocao()) + " e esta " +
                getStatusCliente(cliente.getStatus()) + " R$" + getValor(valor));
        System.out.println("A conta comunitaria está atualmente com R$" + getValor(valorConta) + " e " + qtdClientes + " clientes\n");

        addLog(cliente.getNome(), cliente.getEmocao().toString().toLowerCase(), cliente.getStatus().toString().toLowerCase(),
                valor.toString(), valorConta.toString(), String.valueOf(qtdClientes), false);
    }


    public void printSaida(Cliente cliente, Double valorConta, int qtdClientes) {
        printNumLog();
        System.out.println("O cliente: " + getNomeCliente(cliente.getNome()) + " esta " +
                getEmocaoCliente(cliente.getEmocao()) + " e foi embora\n");

        addLog(cliente.getNome(), cliente.getEmocao().toString().toLowerCase(), cliente.getStatus().toString().toLowerCase(),
                "0", valorConta.toString(), String.valueOf(qtdClientes), true);
    }

    public void printFalencia() {
        printNumLog();
        System.out.println(Cor.ANSI_RED.codigo + "O Banco Faliu!!!" + Cor.ANSI_RESET.codigo);
    }

    public void printNumLog(){
        System.out.println("LOG "+ logs.size() + ":");
    }

    private void addLog(String nomeCLiente, String emocaoCliente, String statusCliente, String valor, String valorConta, String qtdClientes, boolean saiu) {
        Log log;
        if (saiu) {
            log = new Log(nomeCLiente, emocaoCliente, statusCliente, valor, "S", valorConta, qtdClientes);
        } else {
            log = new Log(nomeCLiente, emocaoCliente, statusCliente, valor, "N", valorConta, qtdClientes);
        }

        if (logs.isEmpty()) {
            CsvUtil.cabecalho();
        }

        logs.add(log);

        if (logs.size() < limiteLogs) {
            CsvUtil.addLog(log);
        } else if (logs.size() == limiteLogs) {
            CsvUtil.finalizar();
        }
    }

    private String getNomeCliente(String nome) {
        return Cor.ANSI_CYAN.codigo + nome + Cor.ANSI_RESET.codigo;
    }

    private String getEmocaoCliente(EmocaoCliente emocao) {
        String emocaoAux = emocao.toString().toLowerCase() + Cor.ANSI_RESET.codigo;
        return switch (emocao) {
            case FELIZ -> Cor.ANSI_YELLOW.codigo + emocaoAux;
            case PACIENTE -> Cor.ANSI_CYAN.codigo + emocaoAux;
            case INDIFERENTE -> Cor.ANSI_PURPLE.codigo + emocaoAux;
            case IMPACIENTE -> Cor.ANSI_BLUE.codigo + emocaoAux;
            case IRRITADO -> Cor.ANSI_RED.codigo + emocaoAux;
        };
    }

    private String getStatusCliente(StatusCliente status) {
        String statusAux = status.toString().toLowerCase() + Cor.ANSI_RESET.codigo;
        return switch (status) {
            case AGUARDANDO -> Cor.ANSI_CYAN.codigo + statusAux + " para sacar";
            case DEPOSITANDO -> Cor.ANSI_PURPLE.codigo + statusAux;
            case SACANDO -> Cor.ANSI_GREEN.codigo + statusAux;
        };
    }

    private String getValor(Double valor) {
        return String.format(Cor.ANSI_BLUE.codigo + "%.2f" + Cor.ANSI_RESET.codigo, valor);
    }
}
