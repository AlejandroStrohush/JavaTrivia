package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * 
 * Los mensajes y rutas de toda la aplicación
 * 
 * @since 0.1
 * 
 */

public class Constante {

	// Log

	final static Logger LOGGER = Logger.getLogger(Log.class.getName());

	// Path log

	final static String RUTA_FICHERO_LOG = "src/log/salida.log";

	final static Path PATH_LOG = Paths.get(RUTA_FICHERO_LOG);

	// Path ficheroDuplicado

	final static String RUTA_FICHERO_DUPLICADO = "src/log/salida.log.lck";

	final static Path PATH_DUPLICADO = Paths.get(RUTA_FICHERO_DUPLICADO);

	// Mensajes log

	final static String LOG_ERROR = "ERROR. Se ha producido un error en la aplicación: ";

	// Path jugadores

	final static String RUTA_FICHERO_JUGADORES = "src/ficheros/jugadores.txt";

	final static Path PATH_JUGADORES = Paths.get(RUTA_FICHERO_JUGADORES);

	// Path lengua

	final static String RUTA_FICHERO_LENGUA = "src/ficheros/diccionario.txt";

	final static Path PATH_LANGUA = Paths.get(RUTA_FICHERO_LENGUA);

	// Path ingles

	final static String RUTA_FICHERO_INGLES = "src/ficheros/ingles.txt";

	final static Path PATH_INGLES = Paths.get(RUTA_FICHERO_INGLES);

	// Path historico

	final static String RUTA_FICHERO_HISTORICO = "src/ficheros/historico.txt";

	final static Path PATH_HISTORICO = Paths.get(RUTA_FICHERO_HISTORICO);

	// Path historico

	final static String RUTA_FICHERO_RANKING = "src/ficheros/ranking.txt";

	final static Path PATH_RANKING = Paths.get(RUTA_FICHERO_RANKING);

	// error logs

	final static String NO_SE_PUEDE_RENOMBRAR_FICHERO = "No se ha podido generar un log de la fecha anterior porque el actual salida.log otro proceso lo esta ejecutando";

	// Menu inicial

	final static String MENU_INICIAL = "Elige \n 1 Jugar partida \n 2 Ranking \n 3 Histórico \n 4 Jugadores \n 5 Salir";

	// Humanos

	final static String CANTIDAD_HUMANOS = "¿Cuantos humanos?";

	final static String NOMBRE_JUGADOR = "¿Como se llama el jugador?";

	// CPU

	final static String CANTIDAD_CPU = "¿Cuantos CPU?";

	// Elegir dificultad

	final static String DIFICULTAD = "Elige \n 1 Rapida \n 2 Corta \n 3 Normal \n 4 Larga";

	// Errores del menu principal

	final static String CANTIDAD_HUMANOS_ERROR = "Cantidad de humanos incorrecta";

	final static String JUGADOR_HUMANO_NO_EXISTE = "Este jugador no existe";
	
	final static String JUGADOR_HUMANO_REPETIDO = "Este jugador ha sido repetido 2 veces";

	final static String CANTIDAD_CPU_ERROR = "Cantidad de CPU incorrecta";

	final static String JUGADORES_DEMAS_ERROR = "No se pueden más de 4 jugadores o jugadores negativos";

	// Menu jugadores

	final static String MENU_JUGADOR = "Elige \n 1 Ver jugadores \n 2 Añadir jugador \n 3 Eliminar jugador \n 4 Volver al menu inicial";

	final static String NUEVO_JUGADOR = "¿Cual es el nombre del nuevo jugador?";

	final static String ELIMINAR_JUGADOR = "¿Cual es el nombre del jugador al que quieres eliminar?";

	// Errores de menu jugadores

	final static String NUEVO_JUGADOR_EXISTE_ERROR = "El jugador ya existe";

	final static String ELIMINAR_JUGADOR_NO_EXISTE_ERROR = "El jugador que quieres eliminar no existe";

	final static String ERROR_HUMANO_SE_LLAMA_CPU = "No puedes llamarte CPU, ponte otro nombre";

	// jugadores

	final static String JUGADOR_AÑADIDO = "Jugador añadido ";

	final static String JUGADOR_ELIMINADO = "Jugador eliminado ";

	final static String PUNTOS = " puntos: ";

	final static String JUGADOR_DUPLICADO = " este nombre ya fue elegido anteriormente, no puede ser usado 2 veces en la misma partida ";

	// Mensajes globales para la aplicación entera, no una clase concretra

	final static String MENU_ERROR = "Por favor elige una opcion del menu la proxima vez";

	final static String CONFIRMACION = "Realizado con exito";

	// Ficheros

	final static String ERROR_FICHERO_NO_EXISTE = "Ese fichero no existe";

	final static String ERROR_EXCEPCION_SISTEMA_FICHEROS = "Excepcion de sistema de ficheros ";

	final static String ERROR_GENERICO_FICHERO = "Excepcion generica al consultar el fichero ";

	// Preguntas

	final static String PREGUNTA_LENGUA = "Pregunta de letras, ¿Cual es la siguiente palabra?";

	final static String PREGUNTA_MATEMATICAS = "Pregunta de matematicas";

	final static String NUMERO_ERROR = "Pon un numero y no texto, para la proxima vez";
	
	final static String PREGUNTA_INGLES = "Pregunta de ingles";

	// Preguntas respuestas

	final static String RESPUESTA_CORRECTA = "Respuesta correcta! +1 punto!";

	final static String RESPUESTA_CORRECTA_DADO = "Respuesta correcta! has recibido esta cantidad de puntos: ";
	
	final static String RESPUESTA_RACHA = "HAS ACERTADO 3 SEGUIDAS, GANAS 5 PUNTOS";
	
	final static String RESPUESTA_INCORRECTA = "Incorrecta! La solucion es: ";

	final static String RESPUESTA_CPU = "Creo que la respuesta es: ";

	// Mensaje finalización

	final static String PROGRAMA_FINALIZADO = "PROGRAMA FINALIZADO";

	final static String RONDA_ACABADA = "Ronda acabada";

	final static String PARTIDA_FINALIZADA = "partida Finalizada";

}
