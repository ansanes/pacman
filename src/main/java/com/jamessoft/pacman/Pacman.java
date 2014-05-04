package com.jamessoft.pacman;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Pacman Character
 */

public class Pacman extends GameCharacterGraphic {
	
	public JLabel pacmanLabel;
	private static final int SPEED = 2;
	private int directionPrevista = GameCharacterGraphic.WEST;

	/**
	 * Init Pacaman character
	 */

	public Pacman() {
		//Posiciones incio juego pacman.
		x = 208;
		y = 320;
		//Instancia a labyrinth.
		labyrinth = PacmanGame.getInstace().getLaberinto();
		//Celda original de inicio de juego pacman.
		planeX = labyrinth.getColumn(x);
		planeY = labyrinth.getRow(y);
		//Creamos la label que contendra la imagen.
		pacmanLabel = new JLabel();
		pacmanLabel.setIcon(new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_1.gif")));
		pacmanLabel.setBounds(x, y, ancho, alto);
		//Inicializamos la direction hacia la left.
		direction = GameCharacterGraphic.WEST;
		pacmanLabel.validate();

	}

	/**
	 * 
	 * Movement method
	 * 
	 * @return cell changed?
	 */

	public boolean movement() {
		// Creamos 4 variables para ver cuales son las siguientes coordenadas.
		int newY = 0;
		int newX = 0;
		int newPlanoX = 0;
		int newPlanoY = 0;

		// El codigo siguiente permite almacenar la direction que le queremos
		// dar antes de llegar a
		// una intersecci�n y asi al llegar que automaticamente la tome si
		// existe la posibilidad.

		if ((x % 16) == 0 && (y % 16) == 0) {

			if (intersection()
					&& getDirectionsIntersection(planeX, planeY, direction)
							.contains(directionPrevista)) {
				
				direction = directionPrevista;
			}
		}

		// EAST switch permite cambiar imagen y actualizar localizacion en
		// funci�n de la direcci�n.
		switch (direction) {
		case GameCharacterGraphic.EAST:
			if (planeX % 2 == 0) {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_right_1.gif")));
				pacmanLabel.validate();
			} else {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_right_2.gif")));
				pacmanLabel.validate();
			}
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX + 15);
			newPlanoY = planeY;
			break;
		case GameCharacterGraphic.WEST:
			if (planeX % 2 == 0) {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_1.gif")));
				pacmanLabel.validate();
			} else {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_2.gif")));
				pacmanLabel.validate();
			}
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX);
			newPlanoY = planeY;
			break;
		case GameCharacterGraphic.NORTH:
			if (planeY % 2 == 0) {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_up_1.gif")));
				pacmanLabel.validate();
			} else {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_up_2.gif")));
				pacmanLabel.validate();
			}
			newY = y - SPEED;
			newX = x;
			newPlanoX = planeX;
			newPlanoY = labyrinth.getColumn(newY);
			break;
		case GameCharacterGraphic.SOUTH:
			if (planeY % 2 == 0) {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_down_1.gif")));
				pacmanLabel.validate();
			} else {
				pacmanLabel
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_down_2.gif")));
				pacmanLabel.validate();
			}
			newY = y + SPEED;
			newX = x;
			newPlanoX = planeX;
			newPlanoY = labyrinth.getColumn(newY + 15);
			break;
		default:
			break;
		}

		// Si no hay cambio de plano indicado por el incremento de x e y,
		// seguimos moviendo a pacman.

		if (!(newPlanoX != planeX || newPlanoY != planeY)) {
			planeX = newPlanoX;
			planeY = newPlanoY;
			x = newX;
			y = newY;
			return true;
			// Si hay cambio de plano verificamos si es muro (-1) con lo que no
			// hay cambio de celda y si el plano no es muro (0,1 o 2) movemos y
			// actualizamos plano y posici�n.

		} else {
			Integer contenidoCell = labyrinth.getValueAt(newPlanoX,
					newPlanoY);
			if (contenidoCell == -1) {
				return false;
			} else {
				planeX = newPlanoX;
				planeY = newPlanoY;
				x = newX;
				y = newY;
				return true;
			}
		}

	}

	/**
	 *
         *  Moves pacman
	 * 
	 */
	public void move() {

		movement();

		if (x < 16) {
			x = 416;
		}
		if (x > 416) {
			x = 16;
		}

	}

	/**
	 * Check key pressed and determine direction
	 * @param KeyEvent la tecla capturada al pulsar en el teclado.
	 */

	public void keyPressed(KeyEvent f) {
		int key = f.getKeyCode();

		// Si anteriormente tenia direction left y le doy a la right, va
		// hacia la right.
		// Si no tenia direction right y no es multiplo de 16 (es el tama�o de
		// la celda y asi detectamos que esta justo en ella) va hacia la
		// right.
		// Esta l�gica la repetimos para up, down, left y right.
		// Almacenamos la direction previa
		int directionActual = direction;
		if (key == KeyEvent.VK_RIGHT) {
			directionPrevista = GameCharacterGraphic.EAST;
			if (direction == GameCharacterGraphic.WEST) {

				direction = GameCharacterGraphic.EAST;
			} else if (direction != GameCharacterGraphic.EAST) {
				if (y % 16 == 0) {
					direction = GameCharacterGraphic.EAST;
				}
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			directionPrevista = GameCharacterGraphic.WEST;
			if (direction == GameCharacterGraphic.EAST) {
				direction = GameCharacterGraphic.WEST;
			} else if (direction != GameCharacterGraphic.WEST) {
				if (y % 16 == 0) {
					direction = GameCharacterGraphic.WEST;
				}
			}
		}
		if (key == KeyEvent.VK_UP) {
			directionPrevista = GameCharacterGraphic.NORTH;
			if (direction == GameCharacterGraphic.SOUTH) {
				direction = GameCharacterGraphic.NORTH;
			} else if (direction != GameCharacterGraphic.SOUTH) {
				if (x % 16 == 0) {
					direction = GameCharacterGraphic.NORTH;
				}
			}

		}
		if (key == KeyEvent.VK_DOWN) {
			directionPrevista = GameCharacterGraphic.SOUTH;
			if (direction == GameCharacterGraphic.NORTH) {
				direction = GameCharacterGraphic.SOUTH;
			} else if (direction != GameCharacterGraphic.NORTH) {
				if (x % 16 == 0) {
					direction = GameCharacterGraphic.SOUTH;
				}
			}
		}

		// Si la direction elegida por el jugardor no est� dentro de la
		// directions posibles, reestablecemos la direction previa
		if (!getDirectionsPosibles(planeX, planeY).contains(direction)) {
			direction = directionActual;
		}
	}

	
	/**	 
	 * 
         * Possible pacman directions
	 * @param pacman current position
	 * @return possible directions list
	 */
	
	// 
	protected List<Integer> getDirectionsPosibles(Integer x, Integer y) {
		List<Integer> directions = new ArrayList<Integer>();
		Integer cellRight = labyrinth.getValueAt(x + 1, y);
		Integer cellLeft = labyrinth.getValueAt(x - 1, y);
		Integer cellUp = labyrinth.getValueAt(x, y - 1);
		Integer cellDown = labyrinth.getValueAt(x, y + 1);
		if (cellUp != -1) {
			directions.add(GameCharacterGraphic.NORTH);
		}
		if (cellDown != -1) {
			directions.add(GameCharacterGraphic.SOUTH);
		}
		if (cellRight != -1) {
			directions.add(GameCharacterGraphic.EAST);
		}
		if (cellLeft != -1) {
			directions.add(GameCharacterGraphic.WEST);
		}
		return directions;
	}

	/**
	 * 
         * Set pacman direction
	 * 
	 * @param int pacman direction
	 */

	public void setDirection(int direction) {
		this.direction = direction;

	}

}
