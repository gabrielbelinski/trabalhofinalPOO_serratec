package Main;

import Exceptions.DBException;
import Services.Entrada;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws DBException, IOException {
        Entrada entrada = new Entrada();
        entrada.cadastro();
    }
}
