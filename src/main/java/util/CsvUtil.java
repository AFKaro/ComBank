package util;

import logs.Log;
import lombok.SneakyThrows;

import java.io.*;

public class CsvUtil {

    private static final String CSV_SEPARATOR = ",";

    private static BufferedWriter bw;

    static {
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/java/logs/results/logs.csv"), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void cabecalho() {

        String oneLine = "nomeCliente" +
                CSV_SEPARATOR +
                "emocaoCliente" +
                CSV_SEPARATOR +
                "statusCliente" +
                CSV_SEPARATOR +
                "valor" +
                CSV_SEPARATOR +
                "foiEmbora" +
                CSV_SEPARATOR +
                "valorTotal" +
                CSV_SEPARATOR +
                "quantidadeClientes";
        bw.write(oneLine);
        bw.newLine();
    }

    @SneakyThrows
    public static void addLog(Log log) {

        String oneLine = log.getNomeCliente() +
                CSV_SEPARATOR +
                log.getEmocaoCliente() +
                CSV_SEPARATOR +
                log.getStatusCliente() +
                CSV_SEPARATOR +
                log.getValor() +
                CSV_SEPARATOR +
                log.getFoiEmbora() +
                CSV_SEPARATOR +
                log.getValorTotal() +
                CSV_SEPARATOR +
                log.getQuantidadeClientes();
        bw.write(oneLine);
        bw.newLine();
    }

    @SneakyThrows
    public static void finalizar() {
        bw.flush();
        bw.close();
    }

}
