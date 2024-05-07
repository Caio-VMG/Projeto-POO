package projeto;

import java.util.Scanner;

public class Leitor {

    public static int lerInt() {
        int entrada = 0;
        boolean leituraCorreta = false;
        do {
            try {
                Scanner scan = new Scanner(System.in);
                entrada = scan.nextInt();
                leituraCorreta = true;
            } catch (Exception InputMismatchException) {
                System.out.println("Comando Inválido");
            }
        } while (!leituraCorreta);
        return entrada;
    }
}
