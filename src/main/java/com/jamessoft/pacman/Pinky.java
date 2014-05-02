package com.jamessoft.pacman;

import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Clase del fantasma Pinky que hereda de Ghost.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class Pinky extends Ghost {

	private static final Icon GHOST_ROSA_ARRIBA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarosa_arriba.gif"));

	private static final Icon GHOST_ROSA_ABAJO_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarosa_abajo.gif"));

	private static final Icon GHOST_ROSA_DERECHA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarosa_derecha.gif"));

	private static final Icon GHOST_ROSA_IZQUIERDA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarosa_izquierda.gif"));

	private static final Icon GHOST_ROSA_ESPECIAL_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasma_asustado.gif"));

	public Pinky() {
		ghostLabel.setIcon(GHOST_ROSA_ARRIBA_IMAGE);

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
		// Si el tama�o de la lista es uno, solo hay una posibilidad y la
		// tomamos.
		if (direcciones.size() == 1) {
			direccion = direcciones.get(0);
			return;
		}
		// A continuaci�n dependiendo de donde este pacman, comprobamos en la
		// intereseccion si existe la posibilidad
		// de ir en su misma direcci�n y si es asi la tomamos, primero en
		// horizontal y luego en vertical.
		if (pacmanIzquierda() && direcciones.contains(GameCharacterGraphic.OESTE)) {

			direccion = GameCharacterGraphic.OESTE;
			return;
		}
		if (pacmanDerecha() && direcciones.contains(GameCharacterGraphic.ESTE)) {
			direccion = GameCharacterGraphic.ESTE;
			return;
		}
		if (pacmanArriba() && direcciones.contains(GameCharacterGraphic.NORTE)) {
			direccion = GameCharacterGraphic.NORTE;
			return;
		}
		if (pacmanAbajo() && direcciones.contains(GameCharacterGraphic.SUR)) {

			direccion = GameCharacterGraphic.SUR;
			return;
		}
		// En caso de no existir la posibilidad cogemos aleatoriamente una de
		// las direcciones.
		direccion = direcciones.get(rnd.nextInt(direcciones.size()));
	}

	/**
	 * M�todo que nos permite cambiar la imagen del fantasma en funci�n a su
	 * direcci�n y su estado (asustado o normal).
	 */
	public void muestraGrafico() {
		if (PacmanGame.getInstace().estadoEspecial()) {
			ghostLabel.setIcon(GHOST_ROSA_ESPECIAL_IMAGE);
		} else {
			if (direccion == GameCharacterGraphic.NORTE) {
				ghostLabel.setIcon(GHOST_ROSA_ARRIBA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.SUR) {
				ghostLabel.setIcon(GHOST_ROSA_ABAJO_IMAGE);
			}
			if (direccion == GameCharacterGraphic.ESTE) {
				ghostLabel.setIcon(GHOST_ROSA_DERECHA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.OESTE) {
				ghostLabel.setIcon(GHOST_ROSA_IZQUIERDA_IMAGE);
			}

		}
		ghostLabel.setLocation(x, y);

	}
}
