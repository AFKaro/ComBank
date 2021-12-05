package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Conta {

    private String numero;
    private Double valor;

    public void adicionarValor(Double valor) {
        this.setValor(this.getValor() + valor);
    }

    public void removerValor(Double valor) {
        this.setValor(this.getValor() - valor);
    }
}
