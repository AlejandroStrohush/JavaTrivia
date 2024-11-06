package main;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Es una clase para utilizar todos los ficheros de la aplicación
 * 
 * @since 0.10
 * 
 */

public class Fichero {

	/**
	 * 
	 * Cuando acaba una partida la guarda en el historico
	 * 
	 * @param listaJugadores
	 */
	public static void addHistorico(ArrayList<Jugador> listaJugadores) {

		// Añadir un nuevo jugador al historico con sus puntos
		try {

			for (Jugador jugador : listaJugadores) {

				Files.writeString(Constante.PATH_HISTORICO, jugador.getNombre() + " " + jugador.getPuntos() + " ",
						StandardOpenOption.APPEND);

			}

			Files.writeString(Constante.PATH_HISTORICO, "\n", StandardOpenOption.APPEND);

		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}

	/**
	 * Elimina aun jugador
	 * 
	 * @since 0.2
	 * 
	 * @param eliminarJugador
	 */

	public static void removeJugador(String eliminarJugador) {

		Boolean jugadorExiste = false;

		// Revisar en el fichero si el jugador ya existe
		try {

			ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_JUGADORES);

			ArrayList<String> nuevasLineas = new ArrayList<String>();

			for (String linea : lineas) {

				// Guardar las lineas de todos los jugadores menos del que va a ser eliminado
				if (!linea.equals(eliminarJugador)) {

					nuevasLineas.add(linea);

				} else {

					jugadorExiste = true;

				}

			}

			// Se escribe el fichero entero con todas las lineas menos del jugador eliminado
			Files.write(Constante.PATH_JUGADORES, nuevasLineas);

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

		// Si el jugador que se quiere eliminar no existe, salta este mensaje
		if (jugadorExiste == false) {

			System.out.println(Constante.ELIMINAR_JUGADOR_NO_EXISTE_ERROR);

		} else {

			System.out.println(Constante.CONFIRMACION);

			Constante.LOGGER.info(Constante.JUGADOR_ELIMINADO + eliminarJugador);

			Fichero.removeRanking(eliminarJugador);

		}

	}

	/**
	 * 
	 * Añade un nuevo jugador a la aplicación
	 * 
	 * @since 0.2
	 * 
	 * @param nuevoJugador
	 */

	public static void addJugador(String nuevoJugador) {

		Boolean jugadorExiste = false;

		// Revisar el fichero si el jugador ya existe
		try {

			ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_JUGADORES);

			for (String linea : lineas) {

				if (linea.equals(nuevoJugador)) {

					jugadorExiste = true;

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

		// Si el jugador no existe se va a añadir
		if (jugadorExiste == false) {

			try {

				ArrayList<String> lineas;

				ArrayList<String> nuevasLineas = new ArrayList<String>();

				Files.writeString(Constante.PATH_JUGADORES, "\n" + nuevoJugador.replaceAll(" ", ""),
						StandardOpenOption.APPEND);

				lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_JUGADORES);

				for (String linea : lineas) {

					if (!linea.equals("")) {

						nuevasLineas.add(linea);

					}

				}

				Files.write(Constante.PATH_JUGADORES, nuevasLineas);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		} else {

			System.out.println(Constante.NUEVO_JUGADOR_EXISTE_ERROR);

		}

	}

	/**
	 * Para mostrar en pantalla el contenido de diferentes ficheros y en el caso de
	 * Ranking lo muestra ordenado de mayor a menor a los jugadores
	 * 
	 * @since 0.2
	 * 
	 */

	public static void mostrarFichero(Path rutaFichero) {

		if (rutaFichero != Constante.PATH_RANKING) {

			try {

				ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(rutaFichero);

				for (String linea : lineas) {
					System.out.println(linea);
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

		} else {

			try {

				ArrayList<String> lineas;

				lineas = (ArrayList<String>) Files.readAllLines(rutaFichero);

				Map<Integer, List<String>> map = new TreeMap<>(Collections.reverseOrder());

				for (String linea : lineas) {

					String[] partes = linea.split(",");

					if (partes.length == 2) {

						String nombre = partes[0];
						int puntos = Integer.parseInt(partes[1]);
						map.computeIfAbsent(puntos, k -> new ArrayList<>()).add(nombre);

					} else {
						System.out.println("Formato de línea incorrecto: " + linea);
					}
				}

				System.out.println("Resultados:");
				List<Integer> sortedKeys = new ArrayList<>(map.keySet());

				for (Integer puntos : sortedKeys) {

					List<String> nombres = map.get(puntos);

					for (String nombre : nombres) {

						System.out.println("Nombre: " + nombre + " -> puntos: " + puntos);

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * Aumenta la puntuación de los jugadores del ranking cuando acaba la partida y
	 * registra nuevos jugadores (HUMANOS) al ranking si no estaban
	 * 
	 * @since 0.11.0
	 * 
	 * @param listaJugadores
	 */
	public static void addRanking(ArrayList<Jugador> listaHumanos) {

		int cantidadJugadores = listaHumanos.size();

		Boolean jugadorNoExiste = true;

		// Añadir un nuevo jugador
		try {

			String nombre = "";

			int puntos = 0;

			int contador = 0;

			ArrayList<String> lineas;

			ArrayList<String> nuevasLineas = new ArrayList<String>();

			ArrayList<String> listaNombres = new ArrayList<String>();

			ArrayList<Integer> listaPuntos = new ArrayList<Integer>();

			lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_RANKING);

			long count = Files.lines(Constante.PATH_RANKING).count();

			if (count != 0) {

				// Añade a todos los jugadores del fichero
				for (String linea : lineas) {

					String[] partes = linea.split(",");

					nombre = partes[0];

					listaNombres.add(nombre);

					puntos = Integer.parseInt(partes[1]);

					listaPuntos.add(puntos);

				}

				// Compara los nombres del fichero por los de los jugadores, si ya existen en el
				// fichero solo aumenta su puntuación y si no existen los añaden al fichero
				for (int i = 0; i < listaHumanos.size(); i++) {

					for (String nombreFichero : listaNombres) {

						if (nombreFichero.equals(listaHumanos.get(i).getNombre())) {

							listaPuntos.set(contador, listaPuntos.get(contador) + listaHumanos.get(i).getPuntos());

							jugadorNoExiste = false;

						}

						contador++;

					}

					if (jugadorNoExiste == true) {

						listaNombres.add(listaHumanos.get(i).getNombre());
						listaPuntos.add(listaHumanos.get(i).getPuntos());

					}

					jugadorNoExiste = true;
					contador = 0;

				}

			} else {

				for (int i = 0; i < cantidadJugadores; i++) {

					listaNombres.add(listaHumanos.get(i).getNombre());
					listaPuntos.add(listaHumanos.get(i).getPuntos());

				}

			}

			// Añadir los jugadores y puntos al fichero
			for (int i = 0; i < listaNombres.size(); i++) {

				nuevasLineas.add(listaNombres.get(i) + "," + listaPuntos.get(i));

			}

			Files.write(Constante.PATH_RANKING, nuevasLineas);

		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}

	/**
	 * 
	 * Cuando eliminas un jugador lo elimina del fichero Ranking
	 * 
	 * @since 0.12.0
	 * 
	 * @param jugador
	 */
	
	public static void removeRanking(String jugador) {

		// Añadir un nuevo jugador
		try {

			String nombre = "";

			int puntos = 0;

			ArrayList<String> lineas;

			ArrayList<String> nuevasLineas = new ArrayList<String>();

			ArrayList<String> listaNombres = new ArrayList<String>();

			ArrayList<Integer> listaPuntos = new ArrayList<Integer>();

			lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_RANKING);

			long count = Files.lines(Constante.PATH_RANKING).count();

				// Añade a todos los jugadores del fichero
				for (String linea : lineas) {

					String[] partes = linea.split(",");

					nombre = partes[0];

					puntos = Integer.parseInt(partes[1]);

					if (nombre.equals(jugador)) {

					} else {

						listaNombres.add(nombre);

						listaPuntos.add(puntos);

					}

				}

				// Añadir los jugadores y puntos al fichero
				for (int i = 0; i < listaNombres.size(); i++) {

					nuevasLineas.add(listaNombres.get(i) + "," + listaPuntos.get(i));

				}

				Files.write(Constante.PATH_RANKING, nuevasLineas);

		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}
	
	public static void estadisticas(Estadisticas estadisticas) {
		
		String rutaFicheroEstadisticas = "src/estadisticas/estadisticas-preguntas-" + System.currentTimeMillis();

		Path pathEstadisticas = Paths.get(rutaFicheroEstadisticas);
		
		ArrayList<String> nuevasLineas = new ArrayList<String>();
		
		try {
			
			Files.createFile(pathEstadisticas);
			
			nuevasLineas.add("MATEMATICAS: total " + estadisticas.getCantidadMatematicas() + ", " + estadisticas.getCantidadMatematicasGanadas() + " contestadas correctamente");
			
			nuevasLineas.add("LENGUA: total " + estadisticas.getCantidadLengua() + ", " + estadisticas.getCantidadLenguaGanadas() + " contestadas correctamente");
			
			nuevasLineas.add("INGLES: total " + estadisticas.getCantidadIngles() + ", " + estadisticas.getCantidadInglesGanadas() + " contestadas correctamente");
			
			nuevasLineas.add("AZAR: total " + estadisticas.getCantidadAzar() + ", " + estadisticas.getCantidadAzarGanadas() + " contestadas correctamente");
			
			Files.write(pathEstadisticas, nuevasLineas);
			
			estadisticas.setCantidadMatematicas(0);
			estadisticas.setCantidadMatematicasGanadas(0);
			estadisticas.setCantidadLengua(0);
			estadisticas.setCantidadLenguaGanadas(0);
			estadisticas.setCantidadIngles(0);
			estadisticas.setCantidadInglesGanadas(0);
			estadisticas.setCantidadAzar(0);
			estadisticas.setCantidadAzarGanadas(0);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
