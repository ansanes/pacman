package com.jamessoft.pacman;

import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Clase del ghost Clyde que hereda de Ghost.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class Clyde extends Ghost {

	private static final Icon GHOST_ORANGE_UP_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostorange_up.gif"));
	private static final Icon GHOST_ORANGE_DOWN_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostorange_down.gif"));
	private static final Icon GHOST_ORANGE_LEFT_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostorange_left.gif"));
	private static final Icon GHOST_ORANGE_RIGHT_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostorange_right.gif"));
	private static final Icon GHOST_ORANGE_ESPECIAL_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghost_frighten.gif"));

	/**
	 * Constructor que nos da la imagen de inicio del personaje.
	 */

	public Clyde() {
		ghostLabel.setIcon(GHOST_ORANGE_UP_IMAGE);
	}

	/**
	 * M�todo que nos permite averiguar las posibles directions que puede tomar
	 * el ghost en su celda actual.
	 */

	protected void calculateDirection() {
		// Si la posicion no es modulo de 16 o no esta en una intersection no
		// tomamos ninguna decision.
		if ((x % 16 != 0 || y % 16 != 0) || !intersection()) {
			return;
		}
		// Nos traemos la lista con las posibles directions a tomar.
		List<Integer> directions = getDirectionsIntersection(planoX, planoY,
				direction);
		// Cuando llega a la intersecci�n genera la direction aleatoriamente de
		// las posibles a tomar del array.
		direction = directions.get(rnd.nextInt(directions.size()));
	}

	/**
	 * M�todo que nos permite cambiar la imagen del ghost en funci�n a su
	 * direcci�n y su estado (asustado o normal).
	 */

	public void muestraGrafico() {
		if (PacmanGame.getInstace().estadoEspecial()) {
			ghostLabel.setIcon(GHOST_ORANGE_ESPECIAL_IMAGE);
		} else {
			if (direction == GameCharacterGraphic.NORTH) {
				ghostLabel.setIcon(GHOST_ORANGE_UP_IMAGE);
			}
			if (direction == GameCharacterGraphic.SOUTH) {
				ghostLabel.setIcon(GHOST_ORANGE_DOWN_IMAGE);
			}
			if (direction == GameCharacterGraphic.EAST) {
				ghostLabel.setIcon(GHOST_ORANGE_RIGHT_IMAGE);
			}
			if (direction == GameCharacterGraphic.WEST) {
				ghostLabel.setIcon(GHOST_ORANGE_LEFT_IMAGE);
			}
		}
		ghostLabel.setLocation(x, y);
	}

}
