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
	private int direccionPrevista = GameCharacterGraphic.OESTE;

	/**
	 * Constructor para inicializar objeto pacman. Las tareas que realiza son: -
	 * Indicamos posiciones iniciales x e y. - Creamos una instalacia a
	 * labyrinth. - Obtenemos la celda donde se ubica la label (columna y Fila).
	 * - Creamos la Label y le asignamos una imagen y una posici�n. - Le
	 * indicamos que empiece con la direcci�n OESTE. - Lo validamos.
	 */

	public Pacman() {
		//Posiciones incio juego pacman.
		x = 208;
		y = 320;
		//Instancia a labyrinth.
		labyrinth = PacmanGame.getInstace().getLaberinto();
		//Celda original de inicio de juego pacman.
		planoX = labyrinth.getColumna(x);
		planoY = labyrinth.getFila(y);
		//Creamos la label que contendra la imagen.
		personajePacman = new JLabel();
		personajePacman.setIcon(new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/personajes/pacman_izquierda_1.gif")));
		personajePacman.setBounds(x, y, ancho, alto);
		//Inicializamos la direccion hacia la izquierda.
		direccion = GameCharacterGraphic.OESTE;
		personajePacman.validate();

	}

	/**
	 * 
	 * M�todo que devuelve si es posible mover personaje y adem�s la realiza si
	 * es cierto actualizando coordenadas (x,y) y plano (columan,fila).
	 * 
	 * @return Devuelve si hay cambio de casilla
	 */

	public boolean movimiento() {
		// Creamos 4 variables para ver cuales son las siguientes coordenadas.
		int newY = 0;
		int newX = 0;
		int newPlanoX = 0;
		int newPlanoY = 0;

		// El codigo siguiente permite almacenar la direccion que le queremos
		// dar antes de llegar a
		// una intersecci�n y asi al llegar que automaticamente la tome si
		// existe la posibilidad.

		if ((x % 16) == 0 && (y % 16) == 0) {

			if (interseccion()
					&& getDireccionesInterseccion(planoX, planoY, direccion)
							.contains(direccionPrevista)) {
				
				direccion = direccionPrevista;
			}
		}

		// Este switch permite cambiar imagen y actualizar localizacion en
		// funci�n de la direcci�n.
		switch (direccion) {
		case GameCharacterGraphic.ESTE:
			if (planoX % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_derecha_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_derecha_2.gif")));
				personajePacman.validate();
			}
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumna(newX + 15);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.OESTE:
			if (planoX % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_izquierda_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_izquierda_2.gif")));
				personajePacman.validate();
			}
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumna(newX);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.NORTE:
			if (planoY % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_arriba_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_arriba_2.gif")));
				personajePacman.validate();
			}
			newY = y - SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumna(newY);
			break;
		case GameCharacterGraphic.SUR:
			if (planoY % 2 == 0) {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_abajo_1.gif")));
				personajePacman.validate();
			} else {
				personajePacman
						.setIcon(new ImageIcon(
								Ghost.class
										.getResource("/com/jamessoft/pacman/resources/personajes/pacman_abajo_2.gif")));
				personajePacman.validate();
			}
			newY = y + SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumna(newY + 15);
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
			Integer contenidoCasilla = labyrinth.getValueAt(newPlanoX,
					newPlanoY);
			if (contenidoCasilla == -1) {
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

		movimiento();

		if (x < 16) {
			x = 416;
		}
		if (x > 416) {
			x = 16;
		}

	}

	/**
	 * M�todo implementado por el keyListener que nos actualiza la direccion de
	 * pacman seg�n direcci�n que pulsemos con el teclado.
	 * 
	 * @param KeyEvent la tecla capturada al pulsar en el teclado.
	 */

	public void keyPressed(KeyEvent f) {
		int key = f.getKeyCode();

		// Si anteriormente tenia direccion izquierda y le doy a la derecha, va
		// hacia la derecha.
		// Si no tenia direccion derecha y no es multiplo de 16 (es el tama�o de
		// la celda y asi detectamos que esta justo en ella) va hacia la
		// derecha.
		// Esta l�gica la repetimos para arriba, abajo, izquierda y derecha.
		// Almacenamos la direccion previa
		int direccionActual = direccion;
		if (key == KeyEvent.VK_RIGHT) {
			direccionPrevista = GameCharacterGraphic.ESTE;
			if (direccion == GameCharacterGraphic.OESTE) {

				direccion = GameCharacterGraphic.ESTE;
			} else if (direccion != GameCharacterGraphic.ESTE) {
				if (y % 16 == 0) {
					direccion = GameCharacterGraphic.ESTE;
				}
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			direccionPrevista = GameCharacterGraphic.OESTE;
			if (direccion == GameCharacterGraphic.ESTE) {
				direccion = GameCharacterGraphic.OESTE;
			} else if (direccion != GameCharacterGraphic.OESTE) {
				if (y % 16 == 0) {
					direccion = GameCharacterGraphic.OESTE;
				}
			}
		}
		if (key == KeyEvent.VK_UP) {
			direccionPrevista = GameCharacterGraphic.NORTE;
			if (direccion == GameCharacterGraphic.SUR) {
				direccion = GameCharacterGraphic.NORTE;
			} else if (direccion != GameCharacterGraphic.SUR) {
				if (x % 16 == 0) {
					direccion = GameCharacterGraphic.NORTE;
				}
			}

		}
		if (key == KeyEvent.VK_DOWN) {
			direccionPrevista = GameCharacterGraphic.SUR;
			if (direccion == GameCharacterGraphic.NORTE) {
				direccion = GameCharacterGraphic.SUR;
			} else if (direccion != GameCharacterGraphic.NORTE) {
				if (x % 16 == 0) {
					direccion = GameCharacterGraphic.SUR;
				}
			}
		}

		// Si la direccion elegida por el jugardor no est� dentro de la
		// direcciones posibles, reestablecemos la direccion previa
		if (!getDireccionesPosibles(planoX, planoY).contains(direccion)) {
			direccion = direccionActual;
		}
	}

	
	/**
	 * M�todo calcula las direcciones posibles a partir de una coordenada del tablero.
	 * 
	 * @param Integer para el plano x (columna) y Integer para el plano y (fila).
	 * @return Lista con las posible direcciones que se puede tomar en las celdas contiguas.
	 */
	
	// 
	protected List<Integer> getDireccionesPosibles(Integer x, Integer y) {
		List<Integer> direcciones = new ArrayList<Integer>();
		Integer casillaDerecha = labyrinth.getValueAt(x + 1, y);
		Integer casillaIzquierda = labyrinth.getValueAt(x - 1, y);
		Integer casillaArriba = labyrinth.getValueAt(x, y - 1);
		Integer casillaAbajo = labyrinth.getValueAt(x, y + 1);
		if (casillaArriba != -1) {
			direcciones.add(GameCharacterGraphic.NORTE);
		}
		if (casillaAbajo != -1) {
			direcciones.add(GameCharacterGraphic.SUR);
		}
		if (casillaDerecha != -1) {
			direcciones.add(GameCharacterGraphic.ESTE);
		}
		if (casillaIzquierda != -1) {
			direcciones.add(GameCharacterGraphic.OESTE);
		}
		return direcciones;
	}

	/**
	 * M�todo para actualizar direcci�n de pacman.
	 * 
	 * @param int que indica la direcci�n nueva a actualizar.
	 */

	public void setDireccion(int direccion) {
		this.direccion = direccion;

	}

}// Cierre de la clase.
