package model;

import control.ContaControl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.enums.EmocaoCliente;
import model.enums.StatusCliente;
import util.FakerUtil;


@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Thread {

    private String nome;
    private StatusCliente status;
    private EmocaoCliente emocao;
    private ContaControl control;

    public Cliente(String nome, ContaControl control) {
        super(nome);
        this.nome = nome;
        this.status = StatusCliente.AGUARDANDO;
        this.emocao = EmocaoCliente.FELIZ;
        this.control = control;
    }

    public void proximaEmocao() {
        this.emocao = EmocaoCliente.getProximaEmocao(emocao);
    }

    public void run() {
        double valorDeposito, valorSaque;

        while (true) {
            double bancoPercent = (control.getConta().getValor() / 100) * 20;

            valorDeposito = FakerUtil.getInstance().faker.number().randomDouble(2, 50, (int) bancoPercent);
            valorSaque = FakerUtil.getInstance().faker.number().randomDouble(2, 50, (int) bancoPercent);

            control.sacar(this, valorSaque);
            control.depositar(this, valorDeposito);

        }
    }

    public void depositar(int tempo) {
        try {
            this.setStatus(StatusCliente.DEPOSITANDO);
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("O Cliente demorou muito para realizar o deposito!");
        }
    }

    public void sacar(int tempo) {
        try {
            this.setStatus(StatusCliente.SACANDO);
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("O Cliente demorou muito para realizar o saque!");
        }
    }
}
