package control;

import lombok.Data;
import model.Cliente;
import model.enums.StatusCliente;
import util.FakerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Data
public class ClienteControl {

    private List<Cliente> clientes;

    public ClienteControl(Integer quantidadeUsuarios, ContaControl contaControl, Integer percentBanco) {
        initUsuarioList(quantidadeUsuarios, contaControl, percentBanco);
    }

    public void initUsuarioList(Integer qtd, ContaControl contaControl, Integer percentBanco) {
        clientes = new ArrayList<>();

        IntStream.range(0, qtd).forEach(i -> {
            Cliente cliente = new Cliente(FakerUtil.getInstance().faker.name().name(), contaControl, percentBanco);
            clientes.add(cliente);
        });
    }


    public boolean existeClienteSacando() {
        AtomicBoolean sacando = new AtomicBoolean(false);
        clientes.forEach(u -> {
            if (u.getStatus().equals(StatusCliente.SACANDO))
                sacando.set(true);
        });
        return sacando.get();
    }

    public boolean existeClienteDepositando() {
        AtomicBoolean sacando = new AtomicBoolean(false);
        clientes.forEach(u -> {
            if (u.getStatus().equals(StatusCliente.DEPOSITANDO))
                sacando.set(true);
        });
        return sacando.get();
    }

    public boolean existeClienteAguardando() {
        AtomicBoolean aguardando = new AtomicBoolean(false);
        clientes.forEach(u -> {
            if (u.getStatus().equals(StatusCliente.AGUARDANDO))
                aguardando.set(true);
        });
        return aguardando.get();
    }

    public int getQuantidadeClientes() {
        return this.getClientes().size();
    }
}
