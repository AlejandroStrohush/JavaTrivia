package main;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * 
 * Genera cada tipo de preguntas
 * 
 * @since 0.3
 * 
 */

public class Pregunta {

	/**
	 *
	 * Genera las preguntas de lengua
	 *
	 * @since 0.6
	 *
	 */

	public static void lengua(ArrayList<Jugador> listaJugadores, int numeroJugador, Estadisticas estadisticas) {

		Random random = new Random();

		Scanner entradaUsuario = new Scanner(System.in);

		long numeroLinea = 0;
		int contador = 0;
		int cantidadLetras = 0;
		int letrasCensuradas = 0;
		String palabra = "";
		String respuestaCpu = "10110010";
		String respuestaJugador = "";
		String palabraMostrada = "";

		// Elige un numero de linea aletoario del total y averigua la palabra de esa
		// linea

		try {

			long count = Files.lines(Constante.PATH_LANGUA).count();

			numeroLinea = random.nextLong(0, count + 1);

		} catch (Exception e) {
			e.getStackTrace();
			Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
		}

		System.out.println(Constante.PREGUNTA_LENGUA);

		try {

			ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_LANGUA);

			for (String linea : lineas) {

				contador++;

				if (contador == numeroLinea) {

					palabra = linea;

				}

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

		// Calcula cuantas letras va a censurar

		cantidadLetras = palabra.length();

		letrasCensuradas = cantidadLetras / 3;

		ArrayList<Character> letras = new ArrayList();

		for (char c : palabra.toCharArray()) {
			letras.add(c);
		}

		// Censurar algunas letras de la palabra
		for (int i = 0; i < letrasCensuradas; i++) {

			int letraSustituida = random.nextInt(0, cantidadLetras);

			if (letras.get(letraSustituida).equals('-')) {

				i--;

			} else {
				letras.set(letraSustituida, '-');
			}

		}

		// Para enseñar la palabra al usuario de manera bonita
		for (int i = 0; i < cantidadLetras; i++) {

			palabraMostrada += letras.get(i);

		}

		System.out.println(palabraMostrada);

		try {

			// Para que la cpu pueda jugar
			if (listaJugadores.get(numeroJugador).getNombre().substring(0, 3).equals("CPU")) {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				System.out.println(Constante.RESPUESTA_CPU + respuestaCpu);

				respuestaJugador = respuestaCpu;

			} else {

				respuestaJugador = entradaUsuario.nextLine().toLowerCase();

			}

		} catch (Exception e) {
			// TODO: handle exception

			respuestaJugador = entradaUsuario.nextLine().toLowerCase();

			Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

		}

		// Comprobar si la respuesta es correcta
		if (respuestaJugador.equals(palabra)) {

			System.out.println(Constante.RESPUESTA_CORRECTA);

			listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

			listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

			letras.clear();

			estadisticas.setCantidadLengua(estadisticas.getCantidadLengua() + 1);

			estadisticas.setCantidadLenguaGanadas(estadisticas.getCantidadLenguaGanadas() + 1);

		} else {

			System.out.println(Constante.RESPUESTA_INCORRECTA + palabra);

			listaJugadores.get(numeroJugador).setRacha(0);

			letras.clear();

			estadisticas.setCantidadLengua(estadisticas.getCantidadLengua() + 1);

		}

	}

	/**
	 * 
	 * Genera la pregunta de matematicas
	 * 
	 * @since 0.4
	 * 
	 */

	public static void matematicas(ArrayList<Jugador> listaJugadores, int numeroJugador, Estadisticas estadisticas) {

		Scanner entradaUsuario = new Scanner(System.in);

		Random random = new Random();

		int cantidadNumeros = random.nextInt(4, 9);

		double numero = random.nextInt(2, 13);

		String simbolo = Partida.simboloAleatorio();

		String errorOpcion;

		double respuestaJugador = 0;

		StringBuilder expressionBuilder = new StringBuilder();

		System.out.println(Constante.PREGUNTA_MATEMATICAS);

		// Crea las expresiones matematicas entre 4 a 8 numeros enteros del 2 al 12 con
		// +,-,*

		for (int i = 0; i < cantidadNumeros - 1; i++) {

			numero = random.nextInt(2, 13);
			expressionBuilder.append(numero);

			simbolo = Partida.simboloAleatorio();
			expressionBuilder.append(" ").append(simbolo).append(" ");

		}

		// Para poner el ultimo numero de la expresión
		numero = random.nextInt(2, 13);
		expressionBuilder.append(numero);

		String expressionString = expressionBuilder.toString();

		Expression expression = new ExpressionBuilder(expressionString).build();

		double result = expression.evaluate();

		// Le enseña la pregunta al usuario
		System.out.println(expressionString);

		try {

			// Para que la cpu pueda jugar
			if (listaJugadores.get(numeroJugador).getNombre().substring(0, 3).equals("CPU")) {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				System.out.println(Constante.RESPUESTA_CPU + result);

				// La respuesta de la cpu
				respuestaJugador = result;

			} else {

				try {

					errorOpcion = entradaUsuario.nextLine();

					respuestaJugador = Integer.parseInt(errorOpcion);

				} catch (Exception e) {
					// TODO: handle exception

					System.out.println(Constante.NUMERO_ERROR);

					Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception

			try {

				errorOpcion = entradaUsuario.nextLine();

				respuestaJugador = Integer.parseInt(errorOpcion);

			} catch (Exception ee) {
				// TODO: handle exception

				System.out.println(Constante.MENU_ERROR);

				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

			}

		}

		// Comprobar si la respuesta es correcta
		if (respuestaJugador == result) {

			System.out.println(Constante.RESPUESTA_CORRECTA);

			listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

			listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

			estadisticas.setCantidadMatematicas(estadisticas.getCantidadMatematicas() + 1);

			estadisticas.setCantidadMatematicasGanadas(estadisticas.getCantidadMatematicasGanadas() + 1);

		} else {

			System.out.println(Constante.RESPUESTA_INCORRECTA + result);

			listaJugadores.get(numeroJugador).setRacha(0);

			estadisticas.setCantidadMatematicas(estadisticas.getCantidadMatematicas() + 1);

		}

	}

	/**
	 * 
	 * @since 0.7 Genera las preguntas de ingles
	 * 
	 * @param listaJugadores
	 * @param numeroJugador
	 */

	public static void ingles(ArrayList<Jugador> listaJugadores, int numeroJugador, Estadisticas estadisticas) {

		Scanner entradaUsuario = new Scanner(System.in);

		ArrayList<String> listaRespuestas = new ArrayList();

		ArrayList<String> listaRespuestasOrdenado = new ArrayList();

		Random random = new Random();

		long preguntasTotales = 0;
		int contador = 0;
		long numeroPregunta = 0;
		int posicionRespuesta = 0;

		String pregunta = "";
		String respuestaCorrecta = "";
		String respuesta2 = "";
		String respuesta3 = "";
		String respuesta4 = "";
		String respuestaJugador = "";
		char letraCorrecta = 'A';
		char letra = 'A';

		System.out.println(Constante.PREGUNTA_INGLES);

		// El total de lineas divido entre las preguntas con cuadriple respuesta da de
		// resultado el total de preguntas que habra en la partida

		try {

			long count = Files.lines(Constante.PATH_INGLES).count();

			preguntasTotales = count / 5;

			numeroPregunta = random.nextLong(0, preguntasTotales);

			// Linea del fichero donde esta la pregunta
			numeroPregunta = (numeroPregunta * 5) + 1;

		} catch (Exception e) {
			e.getStackTrace();
			Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);
		}

		// Extrae la preguntas y las respuestas del fichero
		try {

			ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_INGLES);

			for (String linea : lineas) {

				contador++;

				if (contador == numeroPregunta) {

					pregunta = linea;

					System.out.println(pregunta);

				} else {

					if (contador == numeroPregunta + 1) {

						respuestaCorrecta = linea;

						listaRespuestas.add(respuestaCorrecta);

					} else {

						if (contador == numeroPregunta + 2) {

							respuesta2 = linea;

							listaRespuestas.add(respuesta2);

						} else {

							if (contador == numeroPregunta + 3) {

								respuesta3 = linea;

								listaRespuestas.add(respuesta3);

							} else {

								if (contador == numeroPregunta + 4) {

									respuesta4 = linea;

									listaRespuestas.add(respuesta4);

								}

							}

						}

					}

				}

			}

			// Ordena las respuestas aleatoriamente
			while (listaRespuestas.size() != 0) {

				posicionRespuesta = random.nextInt(listaRespuestas.size());

				listaRespuestasOrdenado.add(listaRespuestas.get(posicionRespuesta));

				listaRespuestas.remove(posicionRespuesta);

			}

			// Sale en pantalla las respuestas
			for (int i = 0; i < listaRespuestasOrdenado.size(); i++) {

				System.out.println(letra + " " + listaRespuestasOrdenado.get(i));

				letra = (char) ('B' + i);

			}

			// Averiguar cual es la letra correcta
			for (int i = 0; i < listaRespuestasOrdenado.size(); i++) {
				if (listaRespuestasOrdenado.get(i).equals(respuestaCorrecta)) {
					letraCorrecta = (char) ('A' + i);

				}
			}

			try {

				// Para que la CPU pueda contestar
				if (listaJugadores.get(numeroJugador).getNombre().substring(0, 3).equals("CPU")) {

					String letraAleatoriaCpu = "";

					int aleatoriaCpu = random.nextInt(0, 4);

					switch (aleatoriaCpu) {
						case 0:

							letraAleatoriaCpu = "A";

							break;

						case 1:

							letraAleatoriaCpu = "B";

							break;

						case 2:

							letraAleatoriaCpu = "C";

							break;

						case 3:

							letraAleatoriaCpu = "D";

							break;

						default:
							break;
					}

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					System.out.println(Constante.RESPUESTA_CPU + letraAleatoriaCpu);

					respuestaJugador = letraAleatoriaCpu;

				} else {

					respuestaJugador = entradaUsuario.nextLine().toUpperCase();

				}

			} catch (Exception e) {
				// TODO: handle exception

				respuestaJugador = entradaUsuario.nextLine().toUpperCase();

			}

			// Evalua si el jugador a respondido correctamente
			switch (respuestaJugador.toUpperCase()) {
				case "A":

					if (listaRespuestasOrdenado.get(0).equals(respuestaCorrecta)) {

						System.out.println(Constante.RESPUESTA_CORRECTA);

						listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

						listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

						estadisticas.setCantidadInglesGanadas(estadisticas.getCantidadInglesGanadas() + 1);

					} else {

						System.out.println(Constante.RESPUESTA_INCORRECTA + letraCorrecta + " " + respuestaCorrecta);

						listaJugadores.get(numeroJugador).setRacha(0);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

					}

					break;

				case "B":

					if (listaRespuestasOrdenado.get(1).equals(respuestaCorrecta)) {

						System.out.println(Constante.RESPUESTA_CORRECTA);

						listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

						listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

						estadisticas.setCantidadInglesGanadas(estadisticas.getCantidadInglesGanadas() + 1);

					} else {

						System.out.println(Constante.RESPUESTA_INCORRECTA + letraCorrecta + " " + respuestaCorrecta);

						listaJugadores.get(numeroJugador).setRacha(0);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

					}

					break;

				case "C":

					if (listaRespuestasOrdenado.get(2).equals(respuestaCorrecta)) {

						System.out.println(Constante.RESPUESTA_CORRECTA);

						listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

						listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

						estadisticas.setCantidadInglesGanadas(estadisticas.getCantidadInglesGanadas() + 1);

					} else {

						System.out.println(Constante.RESPUESTA_INCORRECTA + letraCorrecta + " " + respuestaCorrecta);

						listaJugadores.get(numeroJugador).setRacha(0);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

					}

					break;

				case "D":

					if (listaRespuestasOrdenado.get(3).equals(respuestaCorrecta)) {

						System.out.println(Constante.RESPUESTA_CORRECTA);

						listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + 1);

						listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

						estadisticas.setCantidadInglesGanadas(estadisticas.getCantidadInglesGanadas() + 1);

					} else {

						System.out.println(Constante.RESPUESTA_INCORRECTA + letraCorrecta + " " + respuestaCorrecta);

						listaJugadores.get(numeroJugador).setRacha(0);

						estadisticas.setCantidadIngles(estadisticas.getCantidadIngles() + 1);

					}

			}
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static void azar(ArrayList<Jugador> listaJugadores, int numeroJugador, Estadisticas estadisticas) {

		System.out.println("azar");

		Scanner entradaUsuario = new Scanner(System.in);

		Random random = new Random();

		int dado = random.nextInt(1, 11);
		int aleatoriaCpu = 0;
		int respuestaJugador = 0;
		String errorOpcion;

		System.out.println(dado);

		System.out.println("Se ha tirado un dado del 1 al 10, que numero ha salido?");

		try {

			// Para que la CPU pueda contestar
			if (listaJugadores.get(numeroJugador).getNombre().substring(0, 3).equals("CPU")) {

				String letraAleatoriaCpu = "";

				aleatoriaCpu = random.nextInt(1, 11);

				respuestaJugador = aleatoriaCpu;

				System.out.println(Constante.RESPUESTA_CPU + aleatoriaCpu);

			} else {

				try {

					errorOpcion = entradaUsuario.nextLine();

					respuestaJugador = Integer.parseInt(errorOpcion);

				} catch (Exception e) {
					// TODO: handle exception

					System.out.println(Constante.NUMERO_ERROR);

					Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception

			try {

				errorOpcion = entradaUsuario.nextLine();

				respuestaJugador = Integer.parseInt(errorOpcion);

			} catch (Exception e1) {
				// TODO: handle exception

				System.out.println(Constante.NUMERO_ERROR);

				Constante.LOGGER.warning(Constante.LOG_ERROR + " " + e);

			}

		}

		try {

			if (respuestaJugador == dado) {

				listaJugadores.get(numeroJugador).setPuntos(listaJugadores.get(numeroJugador).getPuntos() + dado);

				listaJugadores.get(numeroJugador).setRacha(listaJugadores.get(numeroJugador).getRacha() + 1);

				System.out.println(Constante.RESPUESTA_CORRECTA_DADO + dado);

				estadisticas.setCantidadAzar(estadisticas.getCantidadAzar() + 1);

				estadisticas.setCantidadAzarGanadas(estadisticas.getCantidadAzarGanadas() + 1);

			} else {

				System.out.println(Constante.RESPUESTA_INCORRECTA + dado);

				listaJugadores.get(numeroJugador).setRacha(0);

				estadisticas.setCantidadAzar(estadisticas.getCantidadAzar() + 1);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
