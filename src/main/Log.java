package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * 
 * El funcionamiento de todos los logs de la aplicación
 * 
 * @since 0.9
 * 
 */

public class Log {

	/**
	 * 
	 * Genera los logs de la aplicación
	 * 
	 * @throws IOException
	 * 
	 * @since 0.9
	 * 
	 */

	public static void log() throws IOException {

		// Si la fecha del log es de una fecha anterior a la actual renombrar el log con
		// la fecha de ese log y crear uno nuevo para hoy
		ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Constante.PATH_LOG);

		String fecha = "";

		for (String linea : lineas) {

			String[] partes = linea.split("]");

			fecha = partes[0];

		}

		try {

			if (!fecha.substring(1, 11).equals(fechaActual())) {

				File fechaAntigua = new File("src/log/salida.log." + fecha.substring(1, 11));

				String dia = fecha.substring(1, 3);
				String mes = fecha.substring(4, 6);
				String age = fecha.substring(7, 11);

				String fechaNueva = age + mes + dia;

				String rutaFicheroLogNuevo = "src/log/salida.log." + fechaNueva;

				Path pathLogNuevo = Paths.get(rutaFicheroLogNuevo);

				try {
					Files.move(Constante.PATH_LOG, pathLogNuevo, StandardCopyOption.REPLACE_EXISTING);
					Files.createFile(Constante.PATH_LOG);

				} catch (IOException e) {

					Constante.LOGGER.log(Level.SEVERE,
							Constante.NO_SE_PUEDE_RENOMBRAR_FICHERO + Constante.ERROR_GENERICO_FICHERO, e);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Genera los logs de la aplicación
		try {

			// Desactivar que salga en la consola los logs
			Constante.LOGGER.setUseParentHandlers(false);

			FileHandler fileHandler = new FileHandler(Constante.RUTA_FICHERO_LOG, true);

			// Customizar el mensaje de los logs
			fileHandler.setFormatter(new MensajeCustom());

			Constante.LOGGER.addHandler(fileHandler);

			Constante.LOGGER.setLevel(Level.INFO);

		} catch (Exception e) {
			// TODO: handle exception

			Constante.LOGGER.log(Level.SEVERE, Constante.ERROR_GENERICO_FICHERO, e);

		}

		// Si se me duplica el log lo va a borrar
		try {

			Files.deleteIfExists(Constante.PATH_DUPLICADO);

		} catch (Exception e) {
			// TODO: handle exception

			Constante.LOGGER.info(Constante.LOG_ERROR + e);

		}

	}

	/**
	 * 
	 * Customizar los mensajes de los logs
	 * 
	 * @since 0.9
	 * 
	 */

	static class MensajeCustom extends Formatter {

		public String format(LogRecord record) {
			StringBuilder sb = new StringBuilder();
			sb.append("[").append(fechaActual()).append("]").append("[").append(horaActual()).append("]").append(": ")
					.append(record.getMessage()).append("\n");
			return sb.toString();
		}
	}

	/**
	 * 
	 * @since 0.9
	 * 
	 *        Indica la fecha actual
	 * 
	 * @return
	 */
	public static String fechaActual() {
		LocalDate fechaHoy = LocalDate.now();
		DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return fechaHoy.format(fechaFormateada);
	}

	/**
	 * 
	 * Indica la hora actual
	 * 
	 * @since 0.9
	 * 
	 * @return
	 */
	public static String horaActual() {
		LocalTime horaAhora = LocalTime.now();
		DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("HH:mm");
		return horaAhora.format(fechaFormateada);
	}

}
