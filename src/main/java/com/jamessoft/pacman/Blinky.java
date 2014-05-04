package com.jamessoft.pacman;

import java.util.List;
import javax.swing.ImageIcon;

/**
 * Blinky Ghost
 * 
 */

public class Blinky extends Ghost {
	protected static final ImageIcon GHOST_RED_UP_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostred_up.gif"));
	protected static final ImageIcon GHOST_RED_DOWN_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostred_down.gif"));
	protected static final ImageIcon GHOST_RED_LEFT_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostred_left.gif"));
	protected static final ImageIcon GHOST_RED_RIGHT_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghostred_right.gif"));
	protected static final ImageIcon GHOST_RED_ESPECIAL_IMAGE = new ImageIcon(
			Ghost.class
					.getResource("/com/jamessoft/pacman/resources/personajes/ghost_frighten.gif"));

	/**
	 * Constructor
	 */

	public Blinky() {
		ghostLabel.setIcon(GHOST_RED_LEFT_IMAGE);
		ghostLabel.validate();
	}

	/**
	 * Calculate possible direcction for the ghost
	 */
	protected void calculateDirection() {

		// Si la posicion no es modulo de 16 o no esta en una intersection no
		// tomamos ninguna decision.
		if ((x % 16 != 0 || y % 16 != 0) || !intersection()) {
			return;
		}

		// Nos traemos la lista con las posibles directions a tomar.
		List<Integer> directions = getDirectionsIntersection(planeX, planeY,
				direction);
		// Si el tama�o de la lista es uno, solo hay una posibilidad y la
		// tomamos.
		if (directions.size() == 1) {
			direction = directions.get(0);
			return;
		}
		// A continuaci�n dependiendo de donde east pacman, comprobamos en la
		// intereseccion si existe la posibilidad
		// de ir en su misma direcci�n y si es asi la tomamos, primero en
		// vertical y luego en horizontal.
		if (pacmanUp() && directions.contains(GameCharacterGraphic.NORTH)) {
			direction = GameCharacterGraphic.NORTH;
			return;
		}
		if (pacmanDown() && directions.contains(GameCharacterGraphic.SOUTH)) {

			direction = GameCharacterGraphic.SOUTH;
			return;
		}
		if (pacmanLeft() && directions.contains(GameCharacterGraphic.WEST)) {

			direction = GameCharacterGraphic.WEST;
			return;
		}
		if (pacmanRight() && directions.contains(GameCharacterGraphic.EAST)) {
			direction = GameCharacterGraphic.EAST;
			return;
		}

		// Random direction
		direction = directions.get(rnd.nextInt(directions.size()));
	}

	/**
	 * shows ghost image based on game state
	 */

	public void showGraphic() {
		if (PacmanGame.getInstace().estadoEspecial()) {
			ghostLabel.setIcon(GHOST_RED_ESPECIAL_IMAGE);

		} else {
			if (direction == GameCharacterGraphic.NORTH) {
				ghostLabel.setIcon(GHOST_RED_UP_IMAGE);
			}
			if (direction == GameCharacterGraphic.SOUTH) {
				ghostLabel.setIcon(GHOST_RED_DOWN_IMAGE);
			}
			if (direction == GameCharacterGraphic.EAST) {
				ghostLabel.setIcon(GHOST_RED_RIGHT_IMAGE);
			}
			if (direction == GameCharacterGraphic.WEST) {
				ghostLabel.setIcon(GHOST_RED_LEFT_IMAGE);
			}
		}
		ghostLabel.setLocation(x, y);

	}

}
