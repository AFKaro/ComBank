import control.ContaControl;

public class ComBank {

    public static void main(String[] args) {

        ContaControl control = new ContaControl(10, 100000.0, 2000, 40);
        control.iniciarAtendimento();
    }
}
