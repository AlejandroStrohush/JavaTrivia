package main;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * Tiene todos los metodos para la partida funcione correctamente, ordena
 * jugadores, consigue la preguntas, la cantidad etc...
 * 
 * @since 0.2
 * 
 */

public class Partida {

	/**
	 * 
	 * Cuando se activa este metodo pide los requisitos para la partida y si se
	 * cumplen la comienza
	 * 
	 * @since 0.2
	 * 
	 */

	public static void jugarPartida() {

		Scanner entradaUsuario = new Scanner(System.in);

		ArrayList<Jugador> listaJugadores = new ArrayList();

		ArrayList<Jugador> listaHumanos = new ArrayList();

		Estadisticas estadisticas = new Estadisticas();
		
		// Esta variable se usa en los Try cats para evitar errores
		String errorOpcion;
		int cantidadJugadores = 0;
		int cantidadHumanos = -1;
		int cantidadCpu = 0;
		int dificultad = 0;
		int cantidadPreguntas = 0;
		int numeroJugador = -1;
		int maxPuntos = 0;
		int indiceGanador = 0;
		Boolean humanoExiste = true;
		Boolean errorCpu = false;

		System.out.println(Constante.CANTIDAD_HUMANOS);

		// Elige la cantidad de humanos que jugaran
		try {

			errorOpcion = entradaUsuario.nextLine();

			cantidadHumanos = Integer.parseInt(errorOpcion);

		} catch (Exception e) {
			// TODO: handle exception

			Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

		}

		// Si hay menos de 0 o más de 4 humanos no siga ejecutando
		if (!(cantidadHumanos <= -1 || cantidadHumanos >= 5)) {

			if (cantidadHumanos == 0) {

			} else {
				humanoExiste = Partida.jugadoresHumanos(cantidadHumanos, listaJugadores, listaHumanos);
			}

			if (humanoExiste == true) {

				System.out.println(Constante.CANTIDAD_CPU);

				try {

					errorOpcion = entradaUsuario.nextLine();

					cantidadCpu = Integer.parseInt(errorOpcion);

					errorCpu = false;

					// Si se ponen cpu demás o negativas no siga ejecutando
					if (cantidadCpu <= -1 || cantidadCpu >= 5) {

						errorCpu = true;

					}

				} catch (Exception e) {
					// TODO: handle exception

					errorCpu = true;

					Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

				}

				if (errorCpu == true) {

					System.out.println(Constante.CANTIDAD_CPU_ERROR);

				} else {

					cantidadJugadores = cantidadHumanos + cantidadCpu;

					// Si son más de 4 jugadores vuelve al menu inicial

					if (cantidadJugadores >= 5) {

						System.out.println(Constante.JUGADORES_DEMAS_ERROR);

					} else {

						// Crea los jugadores cpu
						for (int i = 0; i < cantidadCpu; i++) {

							Jugador jugador = new Jugador("CPU" + i);

							listaJugadores.add(jugador);

						}

						// Ordena los jugadores aleatoriamente
						Partida.ordenarJugadores(listaJugadores);

						System.out.println(Constante.DIFICULTAD);

						try {

							errorOpcion = entradaUsuario.nextLine();

							dificultad = Integer.parseInt(errorOpcion);

						} catch (Exception e) {
							// TODO: handle exception

							System.out.println(Constante.MENU_ERROR);
							Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

						}

						// Elige la dificultad de la partida
						cantidadPreguntas = Partida.dificultad(dificultad);

						Constante.LOGGER.info("Inicio de partida con " + cantidadHumanos + " jugadores humanos, "
								+ cantidadCpu + " jugadores de CPU");

						while (cantidadPreguntas != 0) {

							for (Jugador jugador : listaJugadores) {

								numeroJugador++;

								System.out.println(jugador);

								Partida.preguntaAleatoria(listaJugadores, numeroJugador, estadisticas);

								if (listaJugadores.get(numeroJugador).getRacha() == 3) {
									
									System.out.println(Constante.RESPUESTA_RACHA);
									
									listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 5);
									
									listaJugadores.get(numeroJugador).setRacha(0);

									
								}
								
							
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}

								System.out.println();

							}

							numeroJugador = -1;

							cantidadPreguntas--;

							System.out.println(Constante.RONDA_ACABADA);

							// Muestra los puntos de cada jugador cuando acaba la ronda
							for (int i = 0; i < cantidadJugadores; i++) {

								System.out.println(listaJugadores.get(i).getNombre() + Constante.PUNTOS
										+ listaJugadores.get(i).getPuntos());

							}

							System.out.println();

							try {
								Thread.sleep(5000);

							} catch (InterruptedException e) {

								e.printStackTrace();
							}

						}

						// Consigue el ganador de la partida
						for (int i = 0; i < cantidadJugadores; i++) {
							if (listaJugadores.get(i).getPuntos() >= maxPuntos) {
								maxPuntos = listaJugadores.get(i).getPuntos();

							}
						}

						for (int i = 0; i < cantidadJugadores; i++) {

							if (listaJugadores.get(i).getPuntos() == maxPuntos) {

								listaJugadores.get(i).setGanador(true);
								indiceGanador++;
							} else {
								listaJugadores.get(i).setGanador(false);
							}

						}

						// Si solo hay un ganador se ejecuta sino saldra empate
						if (indiceGanador == 1) {

							for (int i = 0; i < cantidadJugadores; i++) {

								if (listaJugadores.get(i).getGanador()) {

									System.out.println("El ganador es: " + listaJugadores.get(i) + " puntos: "
											+ listaJugadores.get(i).getPuntos());

									Constante.LOGGER.info("Fin de partida con " + cantidadJugadores
											+ " jugadores. Ganador ha sido " + listaJugadores.get(i));

									Fichero.addHistorico(listaJugadores);

									Fichero.addRanking(listaHumanos);

								} else {

									System.out.println(
											listaJugadores.get(i) + " puntos: " + listaJugadores.get(i).getPuntos());

								}

							}

						} else {

							System.out.println("EMPATE");

							Constante.LOGGER.info("Fin de partida con " + cantidadJugadores
									+ " jugadores. Ha habido empate. Consulte el histórico para más información");

							Fichero.addHistorico(listaJugadores);

							Fichero.addRanking(listaHumanos);

							for (int i = 0; i < cantidadJugadores; i++) {

								System.out.println(
										listaJugadores.get(i) + " puntos: " + listaJugadores.get(i).getPuntos());

							}

						}
						
						Fichero.estadisticas(estadisticas);
						
						System.out.println(Constante.PARTIDA_FINALIZADA);

					}

				}

			}

		} else

		{

			System.out.println(Constante.CANTIDAD_HUMANOS_ERROR);

			Constante.LOGGER.warning(Constante.LOG_ERROR + Constante.CANTIDAD_HUMANOS_ERROR + " : " + cantidadHumanos);

		}

	}

	/**
	 * Crea a los jugadores humanos, comprobando si existen en el fichero
	 * jugadores.txt
	 * 
	 * @since 0.2
	 * @param cantidadHumanos
	 * @param listaJugadores
	 * @return
	 */
	public static boolean jugadoresHumanos(int cantidadHumanos, ArrayList<Jugador> listaJugadores,
			ArrayList<Jugador> listaHumanos) {

		Scanner entradaUsuario = new Scanner(System.in);

		String humano;

		Boolean jugadorExiste = false;

		for (int i = 0; i < cantidadHumanos; i++) {

			jugadorExiste = false;

			System.out.println(Constante.NOMBRE_JUGADOR);

			humano = entradaUsuario.nextLine().toLowerCase();

			// Revisa en el fichero si el jugador existe
			try {

				ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_JUGADORES);

				for (String linea : lineas) {

					if (linea.equals(humano)) {

						jugadorExiste = true;

						Jugador jugador = new Jugador(humano);

						listaJugadores.add(jugador);

						listaHumanos.add(jugador);
						
					}
					
				}

				// Comprobar que no repitan 2 jugadores con el mismo nombre
				for (int j = 0; j < listaHumanos.size(); j++) {
					
					for (int j2 = j + 1; j2 < listaHumanos.size(); j2++) {
						
						if (listaHumanos.get(i).getNombre().equals(listaHumanos.get(j).getNombre())) {
							
							System.out.println(Constante.JUGADOR_HUMANO_REPETIDO);
							Constante.LOGGER.warning(Constante.LOG_ERROR + Constante.JUGADOR_HUMANO_REPETIDO);
							return false;
							
						}
						
					}
					
				}
				
				if (jugadorExiste == false) {

					System.out.println(Constante.JUGADOR_HUMANO_NO_EXISTE);
					Constante.LOGGER.warning(Constante.LOG_ERROR + Constante.JUGADOR_HUMANO_NO_EXISTE);
					return false;
				}

			} catch (NoSuchFileException e) {
				System.err.println(Constante.ERROR_FICHERO_NO_EXISTE);
				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
			} catch (FileSystemException e) {
				System.err.println(Constante.ERROR_EXCEPCION_SISTEMA_FICHEROS + e);
				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
			} catch (IOException e) {
				System.err.println(Constante.ERROR_GENERICO_FICHERO + e);
				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
			}

		}

		if (jugadorExiste == true) {

		} else {

			System.out.println(Constante.JUGADOR_HUMANO_NO_EXISTE);
			Constante.LOGGER.warning(Constante.LOG_ERROR + Constante.JUGADOR_HUMANO_NO_EXISTE);
			return false;
		}

		return true;

	}

	/**
	 * Ordena a los jugadores aleatoriamente, antes de que empiece la partida
	 * 
	 * @since 0.3
	 * 
	 * @param listaJugadores
	 */

	public static void ordenarJugadores(ArrayList<Jugador> listaJugadores) {

		Random random = new Random();

		ArrayList<Jugador> listaJugadoresOrdenado = new ArrayList();

		int posicionJugador;

		int cantidadJugadores = listaJugadores.size();

		for (int i = 0; i < cantidadJugadores; i++) {

			posicionJugador = random.nextInt(listaJugadores.size());

			listaJugadoresOrdenado.add(listaJugadores.get(posicionJugador));

			listaJugadores.remove(posicionJugador);

		}

		listaJugadores.clear();

		for (Jugador jugador : listaJugadoresOrdenado) {

			listaJugadores.add(jugador);

		}

	}

	/**
	 * 
	 * Elige el tipo de pregunta aleatoriamente
	 * 
	 * @since 0.3
	 * 
	 */

	public static void preguntaAleatoria(ArrayList<Jugador> listaJugadores, int numeroJugador, Estadisticas estadisticas) {

		Random random = new Random();

		int aleatorio = random.nextInt(4);
		
		//int aleatorio = 1; // Testear un modo concreto

		switch (aleatorio) {
		case 0:

			Pregunta.matematicas(listaJugadores, numeroJugador, estadisticas);

			break;

		case 1:

			Pregunta.ingles(listaJugadores, numeroJugador, estadisticas);

			break;

		case 2:

			Pregunta.lengua(listaJugadores, numeroJugador, estadisticas);

			break;

		case 3:

			Pregunta.azar(listaJugadores, numeroJugador, estadisticas);

			break;
			
		default:
			break;
		}

	}

	/**
	 * 
	 * En la pregunta de mateticas genera el simbolo de la expresión matematica
	 * 
	 * @return
	 */
	public static String simboloAleatorio() {

		Random random = new Random();

		int aleatorio = random.nextInt(3);

		switch (aleatorio) {
		case 0:

			return "+";

		case 1:

			return "-";

		case 2:

			return "*";

		default:
			break;
		}

		return "error";

	}

	/**
	 * Para elegir la cantidad de preguntas que habra en la partida
	 * 
	 * @since 0.3
	 * @param dificultad
	 * @return
	 */

	public static int dificultad(int dificultad) {

		int cantidadPreguntas = 0;

		switch (dificultad) {
		case 1:

			cantidadPreguntas = 3;

			break;

		case 2:

			cantidadPreguntas = 5;

			break;

		case 3:

			cantidadPreguntas = 10;

			break;

		case 4:

			cantidadPreguntas = 20;

			break;

		default:

			break;
		}

		return cantidadPreguntas;

	}

}
