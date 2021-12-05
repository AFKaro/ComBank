package control;

import model.Banco;
import model.Conta;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class BancoControl {

    private Banco banco;
    private static BancoControl instance;

    private BancoControl(){
        banco = new Banco(new ArrayList<>());
    }

    public static synchronized BancoControl getInstance(){
        if(Objects.isNull(instance)){
            instance = new BancoControl();
        }
        return instance;
    }

    public void cadastrarConta(Conta conta){
        this.banco.getContas().add(conta);
    }

    public Conta getConta(String numero){
        AtomicReference<Conta> conta = new AtomicReference<>();
        banco.getContas().forEach(c -> {
            if(c.getNumero().equals(numero))
                conta.set(c);
        });
        return conta.get();
    }

    public Boolean contaExiste(String numero){
        return !Objects.isNull(getConta(numero));
    }
}
