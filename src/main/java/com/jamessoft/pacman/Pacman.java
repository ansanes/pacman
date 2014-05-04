package com.jamessoft.pacman;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Esta clase define el personaje de pacman que se compone de una JLabel con el
 * gr�fico del personaje y que ir� actualizando su posici�n en el JFrame. Esta
 * clase hereda de GameCharacterGraphic.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class Pacman extends GameCharacterGraphic {
	// Label del personaje
	public JLabel personajePacman;
	private static final int SPEED = 2;
	private int directionPrevista = GameCharacterGraphic.WEST;

	/**
	 * Constructor para inicializar objeto pacman. Las tareas que realiza son: -
	 * Indicamos posiciones iniciales x e y. - Creamos una instalacia a
	 * labyrinth. - Obtenemos la celda donde se ubica la label (columna y Fila).
	 * - Creamos la Label y le asignamos una imagen y una posici�n. - Le
	 * indicamos que empiece con la direcci�n WEST. - Lo validamos.
	 */

	public Pacman() {
		//Posiciones incio juego pacman.
		x = 208;
		y = 320;
		//Instancia a labyrinth.
		labyrinth = PacmanGame.getInstace().getLaberinto();
		//Celda original de inicio de juego pacman.
		planoX = labyrinth.getColumn(x);
		planoY = labyrinth.geRow(y);
		//Creamos la label que contendra la imagen.
		personajePacman = new JLabel();
		personajePacman.setIcon(new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_1.gif")));
		personajePacman.setBounds(x, y, ancho, alto);
		//Inicializamos la direction hacia la left.
		direction = GameCharacterGraphic.WEST;
		personajePacman.validate();

	}

	/**
	 * 
	 * M�todo que devuelve si es posible mover personaje y adem�s la realiza si
	 * es cierto actualizando coordenadas (x,y) y plano (columan,fila).
	 * 
	 * @return Devuelve si hay cambio de cell
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
					&& getDirectionsIntersection(planoX, planoY, direction)
							.contains(directionPrevista)) {
				
				direction = directionPrevista;
			}
		}

		// EAST switch permite cambiar imagen y actualizar localizacion en
		// funci�n de la direcci�n.
		switch (direction) {
		case GameCharacterGraphic.EAST:
			if (planoX % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_right_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_right_2.gif")));
				personajePacman.validate();
			}
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX + 15);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.WEST:
			if (planoX % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_left_2.gif")));
				personajePacman.validate();
			}
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.NORTH:
			if (planoY % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_up_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_up_2.gif")));
				personajePacman.validate();
			}
			newY = y - SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumn(newY);
			break;
		case GameCharacterGraphic.SOUTH:
			if (planoY % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_down_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_down_2.gif")));
				personajePacman.validate();
			}
			newY = y + SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumn(newY + 15);
			break;
		default:
			break;
		}

		// Si no hay cambio de plano indicado por el incremento de x e y,
		// seguimos moviendo a pacman.

		if (!(newPlanoX != planoX || newPlanoY != planoY)) {
			planoX = newPlanoX;
			planoY = newPlanoY;
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
				planoX = newPlanoX;
				planoY = newPlanoY;
				x = newX;
				y = newY;
				return true;
			}
		}

	}

	/**
	 * M�todo para ejecutar movimiento y adem�s realiza el cambio de coordenadas
	 * del tunel izquierdo y derecho de la pantalla.
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
	 * M�todo implementado por el keyListener que nos actualiza la direction de
	 * pacman seg�n direcci�n que pulsemos con el teclado.
	 * 
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
		if (!getDirectionsPosibles(planoX, planoY).contains(direction)) {
			direction = directionActual;
		}
	}

	
	/**
	 * M�todo calcula las directions posibles a partir de una coordenada del tablero.
	 * 
	 * @param Integer para el plano x (columna) y Integer para el plano y (fila).
	 * @return Lista con las posible directions que se puede tomar en las celdas contiguas.
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
	 * M�todo para actualizar direcci�n de pacman.
	 * 
	 * @param int que indica la direcci�n nueva a actualizar.
	 */

	public void setDirection(int direction) {
		this.direction = direction;

	}

}// Cierre de la clase.
