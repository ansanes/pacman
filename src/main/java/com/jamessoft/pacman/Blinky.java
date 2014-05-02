package com.jamessoft.pacman;

import java.util.List;
import javax.swing.ImageIcon;

/**
 * Clase del fantasma Blinky que hereda de Ghost.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class Blinky extends Ghost {
	protected static final ImageIcon GHOST_ROJO_ARRIBA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarojo_arriba.gif"));
	protected static final ImageIcon GHOST_ROJO_ABAJO_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarojo_abajo.gif"));
	protected static final ImageIcon GHOST_ROJO_IZQUIERDA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarojo_izquierda.gif"));
	protected static final ImageIcon GHOST_ROJO_DERECHA_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasmarojo_derecha.gif"));
	protected static final ImageIcon GHOST_ROJO_ESPECIAL_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/fantasma_asustado.gif"));

	/**
	 * Constructor que nos da la imagen de inicio del personaje.
	 */

	public Blinky() {
		ghostLabel.setIcon(GHOST_ROJO_IZQUIERDA_IMAGE);
		ghostLabel.validate();
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
		// vertical y luego en horizontal.
		if (pacmanArriba() && direcciones.contains(GameCharacterGraphic.NORTE)) {
			direccion = GameCharacterGraphic.NORTE;
			return;
		}
		if (pacmanAbajo() && direcciones.contains(GameCharacterGraphic.SUR)) {

			direccion = GameCharacterGraphic.SUR;
			return;
		}
		if (pacmanIzquierda() && direcciones.contains(GameCharacterGraphic.OESTE)) {

			direccion = GameCharacterGraphic.OESTE;
			return;
		}
		if (pacmanDerecha() && direcciones.contains(GameCharacterGraphic.ESTE)) {
			direccion = GameCharacterGraphic.ESTE;
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
			ghostLabel.setIcon(GHOST_ROJO_ESPECIAL_IMAGE);

		} else {
			if (direccion == GameCharacterGraphic.NORTE) {
				ghostLabel.setIcon(GHOST_ROJO_ARRIBA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.SUR) {
				ghostLabel.setIcon(GHOST_ROJO_ABAJO_IMAGE);
			}
			if (direccion == GameCharacterGraphic.ESTE) {
				ghostLabel.setIcon(GHOST_ROJO_DERECHA_IMAGE);
			}
			if (direccion == GameCharacterGraphic.OESTE) {
				ghostLabel.setIcon(GHOST_ROJO_IZQUIERDA_IMAGE);
			}
		}
		ghostLabel.setLocation(x, y);

	}

}
