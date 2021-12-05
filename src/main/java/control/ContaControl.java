package control;

import logs.Logger;
import lombok.Data;
import model.Cliente;
import model.Conta;
import model.enums.EmocaoCliente;
import model.enums.StatusCliente;
import util.FakerUtil;


@Data
public class ContaControl {

    private Conta conta;
    private Logger logger;
    private ClienteControl clienteControl;

    public ContaControl(Integer quantidadeClientes, Double valorInicial, Integer limiteLogs) {
        this.conta = cadastrar(valorInicial);
        this.clienteControl = new ClienteControl(quantidadeClientes, this);
        this.logger = new Logger(limiteLogs);
    }


    public Conta cadastrar(Double valorInicial) {
        Conta conta = new Conta(FakerUtil.getInstance().faker.number().digits(5), valorInicial);

        while (BancoControl.getInstance().contaExiste(conta.getNumero())) {
            conta.setNumero(FakerUtil.getInstance().faker.number().digits(5));
        }

        BancoControl.getInstance().cadastrarConta(conta);
        return BancoControl.getInstance().getConta(conta.getNumero());
    }


    public void iniciarAtendimento() {
        clienteControl.getClientes().forEach(Thread::start);
    }


    public synchronized void depositar(Cliente cliente, Double valor) {
        cliente.setStatus(StatusCliente.DEPOSITANDO);

        BancoControl.getInstance()
                .getConta(conta.getNumero())
                .adicionarValor(valor);

        if (clienteControl.existeClienteAguardando()) {
            notifyAll();
        }

        logger.printAcao(cliente, valor, conta.getValor(), clienteControl.getQuantidadeClientes());

        cliente.depositar((int) (Math.random() * 100));
        cliente.setEmocao(EmocaoCliente.FELIZ);
    }


    public synchronized void sacar(Cliente cliente, Double valor) {
        cliente.setStatus(StatusCliente.AGUARDANDO);

        Conta contaBanco = BancoControl.getInstance().getConta(conta.getNumero());
        while (clienteControl.existeClienteSacando() || contaBanco.getValor() - valor < 0) {
            try {
                cliente.proximaEmocao();
                wait();

                logger.printAcao(cliente, valor, conta.getValor(), clienteControl.getQuantidadeClientes());

                if (cliente.getEmocao().equals(EmocaoCliente.IRRITADO)) {
                    break;
                }
            } catch (InterruptedException ignored) {
            }
        }

        if (!cliente.getEmocao().equals(EmocaoCliente.IRRITADO)) {

            contaBanco.removerValor(valor);

            cliente.sacar((int) (Math.random() * 100));
            logger.printAcao(cliente, valor, conta.getValor(), clienteControl.getQuantidadeClientes());
            cliente.setEmocao(EmocaoCliente.FELIZ);

            if (clienteControl.existeClienteAguardando()) {
                notifyAll();
            }

        } else {
            logger.printSaida(cliente, conta.getValor(), clienteControl.getQuantidadeClientes());
            clienteControl.getClientes().remove(cliente);
            if (clienteControl.getClientes().isEmpty()) {
               logger.printFalencia();
            }

            cliente.stop();
        }
    }

}
