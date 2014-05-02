package com.jamessoft.pacman;

import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Clase del fantasma Clyde que hereda de Ghost.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class Clyde extends Ghost {

	private static final Icon GHOST_NARANJA_ARRIBA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmanaranja_arriba.gif"));
	private static final Icon GHOST_NARANJA_ABAJO_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmanaranja_abajo.gif"));
	private static final Icon GHOST_NARANJA_IZQUIERDA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmanaranja_izquierda.gif"));
	private static final Icon GHOST_NARANJA_DERECHA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmanaranja_derecha.gif"));
	private static final Icon GHOST_NARANJA_ESPECIAL_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasma_asustado.gif"));

	/**
	 * Constructor que nos da la imagen de inicio del personaje.
	 */

	public Clyde() {
		ghostLabel.setIcon(GHOST_NARANJA_ARRIBA_IMAGE);
	}

	/**
	 * M�todo que nos permite averiguar las posibles direcciones que puede tomar
	 * el fantasma en su celda actual.
	 */

	protected void calcularDireccion() {
		// Si la posicion no es modulo de 16 o no esta en una interseccion no
		// tomamos ninguna decision.
		if ((x % 16 != 0 || y % 16 != 0) || !interseccion()) {
			return;
		}
		// Nos traemos la lista con las posibles direcciones a tomar.
		List<Integer> direcciones = getDireccionesInterseccion(planoX, planoY,
				direccion);
		// Cuando llega a la intersecci�n genera la direccion aleatoriamente de
		// las posibles a tomar del array.
		direccion = direcciones.get(rnd.nextInt(direcciones.size()));
	}

	/**
	 * M�todo que nos permite cambiar la imagen del fantasma en funci�n a su
	 * direcci�n y su estado (asustado o normal).
	 */

	public void muestraGrafico() {
		if (PacmanGame.getInstace().estadoEspecial()) {
			ghostLabel.setIcon(GHOST_NARANJA_ESPECIAL_IMAGE);
		} else {
			if (direccion == GameCharacterGraphic.NORTE) {
				ghostLabel.setIcon(GHOST_NARANJA_ARRIBA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.SUR) {
				ghostLabel.setIcon(GHOST_NARANJA_ABAJO_IMAGE);
			}
			if (direccion == GameCharacterGraphic.ESTE) {
				ghostLabel.setIcon(GHOST_NARANJA_DERECHA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.OESTE) {
				ghostLabel.setIcon(GHOST_NARANJA_IZQUIERDA_IMAGE);
			}
		}
		ghostLabel.setLocation(x, y);
	}

}
