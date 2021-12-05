package logs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Log {

    private String nomeCliente;
    private String emocaoCliente;
    private String statusCliente;
    private String valor;
    private String foiEmbora;

    private String valorTotal;
    private String quantidadeClientes;

}
