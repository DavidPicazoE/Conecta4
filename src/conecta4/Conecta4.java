package conecta4;

import java.util.Scanner;

public class Conecta4 {

    public static void main(String[] args) {
        Scanner teclat = new Scanner(System.in);

        // Variables utilizadas
        int ncol = 0, nfil = 0, tir, contador = 0;
        char fitxa1 = 'X', fitxa2 = 'O', fitxag = fitxa1, buit = ' ';
        boolean victoria = false, fipartida = false;
        String jugar;

        String[] nombres = obtenerNombres(teclat);

        nfil = 5;
        ncol = 10;
        char[][] matriu = inicializarMatriz(nfil, ncol);

        // Bucle principal para la partida
        do {
            // Repetir el juego hasta que alguien gane o haya empate
            victoria = jugarPartida(teclat, matriu, nombres[0], nombres[1], fitxag, contador, nfil, ncol);

            if (victoria) {
                // Si alguien gana, preguntar si quieren jugar de nuevo
                System.out.println("\n¿Quieres volver a jugar?");
                jugar = teclat.nextLine();
                jugar = teclat.nextLine();
                jugar = jugar.toLowerCase();
                if (jugar.equals("si") || jugar.equals("s")) {
                    // Reiniciar partida
                    fipartida = false;
                    contador = 0;
                    victoria = false;
                    matriu = inicializarMatriz(nfil, ncol);  // Reiniciar la matriz
                } else {
                    fipartida = true;
                }
            }

            // Alternar las fichas para el siguiente jugador
            fitxag = (fitxag == fitxa1) ? fitxa2 : fitxa1;
            contador++;

        } while (!fipartida);

        System.out.println("Fi Partida, Ben jugat.");
    }

    // Método para obtener los nombres de los jugadores
    private static String[] obtenerNombres(Scanner teclat) {
        System.out.println("  ██████╗  ██████╗ ███╗   ██╗███████╗ ██████╗█████████╗  █████╗     ██╗  ██╗");
        System.out.println(" ██╔════╝ ██╔═══██╗████╗  ██║██╔════╝██╔════╝   ██╔═══╝ ██╔══██╗    ██║  ██║");
        System.out.println(" ██║      ██║   ██║██╔██╗ ██║█████╗  ██║        ██║     ███████║    ███████║");
        System.out.println(" ██║      ██║   ██║██║╚██╗██║██╔══╝  ██║        ██║     ██╔══██║         ██║");
        System.out.println(" ╚██████╔╝╚██████╔╝██║ ╚████║███████╗╚██████    ██║     ██║  ██║         ██║");
        System.out.println("  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚══════╝ ╚═════╝   ╚═╝     ╚═╝  ╚═╝         ╚═╝");
        System.out.println();
        System.out.println("Introduce el nombre del primer jugador");
        String nom1 = teclat.next();
        System.out.println("Introduce el nombre del segundo jugador");
        String nom2 = teclat.next();
        return new String[]{nom1, nom2};
    }

    // Inicializa la matriz con espacios vacíos
    private static char[][] inicializarMatriz(int nfil, int ncol) {
        char[][] matriu = new char[nfil][ncol];
        for (int fil = 0; fil < matriu.length; fil++) {
            for (int col = 0; col < matriu[fil].length; col++) {
                matriu[fil][col] = ' ';
            }
        }
        return matriu;
    }

    // Imprime la matriz de forma legible
    private static void imprimirMatriz(char[][] matriu) {
        for (int fil = 0; fil < matriu.length; fil++) {
            System.out.print("|");
            for (int col = 0; col < matriu[fil].length; col++) {
                System.out.printf("%s |", matriu[fil][col]);
            }
            System.out.println();
            System.out.println("-".repeat(matriu[fil].length * 2 + 10));
        }
        System.out.print(" ");
        for (int col = 0; col < matriu[0].length; col++) {
            System.out.printf(" %d ", col + 1);
        }
        System.out.println("\n");
    }

    // Controla el flujo del juego
    private static boolean jugarPartida(Scanner teclat, char[][] matriu, String nom1, String nom2, char fitxag, int contador, int nfil, int ncol) {
        boolean victoria = false;
        int tir, f;
        boolean tirada;

        imprimirMatriz(matriu);
        tirada = false;

        // Determinar el turno del jugador
        if (contador % 2 == 0) {
            System.out.printf("\nTurno de %s", nom1);
        } else {
            System.out.printf("\nTurno de %s", nom2);
        }

        // Solicitar la columna donde colocar la ficha
        System.out.println("\nEn qué columna quieres poner la ficha?");
        tir = teclat.nextInt() - 1;
        f = matriu.length - 1;

        // Verificar que la tirada esté dentro de la matriz
        while (tir >= ncol || tir < 0) {
            System.out.println("Error, Tirada fuera del tablero. Intenta nuevamente.");
            tir = teclat.nextInt() - 1;
        }

        // Colocar la ficha en la primera fila disponible
        while (f >= 0 && !tirada) {
            if (matriu[f][tir] == ' ') {
                matriu[f][tir] = fitxag;
                tirada = true;
            } else {
                f--;
            }
        }

        // Verificar si alguien ganó
        victoria = verificarVictoria(matriu, fitxag, nom1, nom2, contador, nfil, ncol);
        if (victoria) {
            System.out.println("\n");
            imprimirMatriz(matriu);
        }
        return victoria;
    }

    // Verificar si hay una victoria
    private static boolean verificarVictoria(char[][] matriu, char fitxag, String nom1, String nom2, int contador, int nfil, int ncol) {
        // Comprobar verticalmente
        for (int fil = 0; fil < matriu.length - 3; fil++) {
            for (int col = 0; col < matriu[fil].length; col++) {
                if (matriu[fil][col] == fitxag && matriu[fil + 1][col] == fitxag && matriu[fil + 2][col] == fitxag && matriu[fil + 3][col] == fitxag) {
                    System.out.printf("\n%s ha ganado de forma vertical, %s más suerte la próxima partida\n", (contador % 2 == 0) ? nom1 : nom2, (contador % 2 == 0) ? nom2 : nom1);
                    return true;
                }
            }
        }

        // Comprobar horizontalmente
        for (int col = 0; col < matriu[0].length - 3; col++) {
            for (int fil = 0; fil < matriu.length; fil++) {
                if (matriu[fil][col] == fitxag && matriu[fil][col + 1] == fitxag && matriu[fil][col + 2] == fitxag && matriu[fil][col + 3] == fitxag) {
                    System.out.printf("\n%s ha ganado de forma horizontal, %s más suerte la próxima partida\n", (contador % 2 == 0) ? nom1 : nom2, (contador % 2 == 0) ? nom2 : nom1);
                    return true;
                }
            }
        }
        // Comprobar diagonal 
        for (int col = 0; col < matriu[0].length - 3; col++) {
            for (int fil = 0; fil < matriu.length - 3; fil++) { // Evita que fil+3 exceda el límite
                if ((matriu[fil][col] == fitxag
                        && matriu[fil + 1][col + 1] == fitxag
                        && matriu[fil + 2][col + 2] == fitxag
                        && matriu[fil + 3][col + 3] == fitxag)
                        || (matriu[fil][col] == fitxag
                        && matriu[fil + 1][col - 1] == fitxag
                        && matriu[fil + 2][col - 2] == fitxag
                        && matriu[fil + 3][col - 3] == fitxag)) {
                    System.out.printf("\n%s ha ganado de forma diagonal, %s más suerte la próxima partida",
                            (contador % 2 == 0) ? nom1 : nom2,
                            (contador % 2 == 0) ? nom2 : nom1);
                    return true;
                }
            }
        }

        // Verificar empate
        int empat = 0;
        for (int i = 0; i < matriu[0].length; i++) {
            if (matriu[0][i] != ' ') {
                empat++;
            } else {
                empat = 0;
            }
            if (empat == ncol) {
                System.out.println("Empate");
                return true;
            }
        }

        return false;
    }
}
