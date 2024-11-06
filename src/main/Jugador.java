package main;

/**
 * 
 * Todas las caracteristicas que tiene un jugador
 * 
 * @since 0.2
 * 
 */

public class Jugador {

	private String nombre;
	private int puntos = 0;
	private Boolean ganador;
	private int racha = 0;

	public Jugador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Boolean getGanador() {
		return ganador;
	}

	public void setGanador(Boolean ganador) {
		this.ganador = ganador;
	}

	
	
	public int getRacha() {
		return racha;
	}

	public void setRacha(int racha) {
		this.racha = racha;
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + "]";
	}

}
