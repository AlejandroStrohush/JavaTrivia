package main;

import java.util.Scanner;

/**
 * 
 * @since 0.1
 * 
 */

public class Menu {

	/**
	 * 
	 * Es el menu inicial del juego
	 * 
	 * @since 0.1
	 * 
	 */

	public static void inicial() {

		Scanner entradaUsuario = new Scanner(System.in);

		int opcion = 0;

		// Esta variable se usa en los Try cats para evitar errores
		String errorOpcion;

		// MENU INICIAL
		do {

			System.out.println(Constante.MENU_INICIAL);

			try {

				errorOpcion = entradaUsuario.nextLine();

				opcion = Integer.parseInt(errorOpcion);

			} catch (Exception e) {
				// TODO: handle exception

				System.out.println(Constante.MENU_ERROR);
				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

			}

			switch (opcion) {

			case 1:

				Partida.jugarPartida();

				break;

			case 2:

				Fichero.mostrarFichero(Constante.PATH_RANKING);

				break;

			case 3:

				Fichero.mostrarFichero(Constante.PATH_HISTORICO);

				break;

			case 4:

				Menu.jugador();

				break;

			case 5:

				break;

			default:
				break;
			}

		} while (opcion != 5);

	}

	/**
	 * Crea un menu con el cual se interactua con los jugadores, ver, añadir,
	 * eliminar
	 * 
	 * @since 0.2
	 * 
	 */
	public static void jugador() {

		Scanner entradaUsuario = new Scanner(System.in);

		int opcion = 0;

		String errorOpcion;

		do {

			System.out.println(Constante.MENU_JUGADOR);

			try {

				errorOpcion = entradaUsuario.nextLine();

				opcion = Integer.parseInt(errorOpcion);

			} catch (Exception e) {
				// TODO: handle exception

				System.out.println(Constante.MENU_ERROR);
				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
			}

			switch (opcion) {

			case 1:

				Fichero.mostrarFichero(Constante.PATH_JUGADORES);

				break;

			case 2:

				System.out.println(Constante.NUEVO_JUGADOR);

				String nuevoJugador = entradaUsuario.nextLine().toLowerCase();

				try {

					if (!nuevoJugador.substring(0, 3).equals("cpu")) {

						Fichero.addJugador(nuevoJugador);

						Constante.LOGGER.info(Constante.JUGADOR_AÑADIDO + nuevoJugador);

					} else {

						System.out.println(Constante.ERROR_HUMANO_SE_LLAMA_CPU);

					}

				} catch (Exception e) {
					// TODO: handle exception

					Fichero.addJugador(nuevoJugador);

					Constante.LOGGER.info(Constante.JUGADOR_AÑADIDO + nuevoJugador);

				}

				break;

			case 3:

				System.out.println(Constante.ELIMINAR_JUGADOR);

				String eliminarJugador = entradaUsuario.nextLine().toLowerCase();

				Fichero.removeJugador(eliminarJugador);

				break;

			case 4:
				break;

			default:
				break;
			}

		} while (opcion != 4);

	}

}
