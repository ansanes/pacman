package com.jamessoft.pacman;

import java.util.Random;
import javax.swing.JLabel;

/**
 * De esta clase abstracta heredan todos los ghosts.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */
public abstract class Ghost extends GameCharacterGraphic {
	// Posici�n en columna y fila inicial del ghost.
	public static final Integer planoXInicial = 14;
	public static final Integer planoYInicial = 14;

	// Velocidad de los ghosts.
	private static final int SPEED = 2;
	// Todos los ghosts son JLabel.
	protected JLabel ghostLabel;
	// Indica si el ghost esta en el tablero.
	protected boolean onBoard = false;
	// Creamos variable random que se utilizara para elegir direction en
	// intersectiones.
	protected Random rnd = new Random();
	// Almacena un entero con los milisegundos de la fecha actual que hace que
	// se comio.
	protected long ultimaVezComido = 0;

	/**
	 * Constructor que nos da los parametros generales de los ghosts.
	 */

	public Ghost() {
		// Posici�n que toman por defecto al ser creados.
		setPlanoX(planoXInicial);
		setPlanoY(planoYInicial);

		// Direction que toman por defecto.
		direction = GameCharacterGraphic.WEST;
		// Creacion label del ghost.
		ghostLabel = new JLabel();
		ghostLabel.setBounds(x, y, ancho, alto);
		ghostLabel.validate();

	}

	/**
	 * 
	 * M�todo que devuelve si es posible mover personaje y adem�s la realiza si
	 * es cierto actualizando coordenadas (x,y) y plano (columan,fila).
	 * 
	 * @return Devuelve si hay cambio de cell
	 */
	protected boolean movement() {
		// Creamos 4 variables para ver cuales son las siguientes coordenadas.
		int newY = 0;
		int newX = 0;
		int newPlanoX = 0;
		int newPlanoY = 0;
		// Hacemos un switch con el parametro direcci�n que tiene el personaje.
		// Actualizamos su futura posicion.
		switch (direction) {
		case GameCharacterGraphic.EAST:
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX + 15);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.WEST:
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.NORTH:
			newY = y - SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumn(newY);
			break;
		case GameCharacterGraphic.SOUTH:
			newY = y + SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumn(newY + 15);
			break;
		default:
			break;
		}
		planoX = newPlanoX;
		planoY = newPlanoY;
		x = newX;
		y = newY;
		if (planoX == -1) {
			planoX = 27;
			x = 27 * 16;
		} else if (planoX == 28) {
			planoX = 0;
			x = 0;
		}
		return true;
	}

	/**
	 * M�todo para ejecutar movimiento si el personaje esta en el tablero.
	 * 
	 */
	protected void move() {
		if (onBoard) {
			calculateDirection();
			movement();
		}
	}

	/**
	 * 
	 * M�todo ABSTRACTO para calcular la direction que tomara el ghost.
	 * 
	 */
	protected abstract void calculateDirection();

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta a la RIGHT del ghost.
	 * 
	 * @return Devuelve CIERTO si pacman esta a la RIGHT del ghost.
	 */
	protected boolean pacmanRight() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX > planoX;
	}

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta a la LEFT del ghost.
	 * 
	 * @return Devuelve CIERTO si pacman esta a la LEFT del ghost.
	 */
	protected boolean pacmanLeft() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX < planoX;
	}// Cierre del m�todo

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta UP del ghost.
	 * 
	 * @return Devuelve CIERTO si pacman esta UP del ghost.
	 */
	protected boolean pacmanUp() {
		Integer pacmanY = PacmanGame.getInstace().getPacman().getPlanoY();
		return pacmanY < planoY;
	}

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta DOWN del ghost.
	 * 
	 * @return Devuelve CIERTO si pacman esta DOWN del ghost.
	 */
	protected boolean pacmanDown() {
		Integer pacmanY = PacmanGame.getInstace().getPacman().getPlanoY();
		return pacmanY > planoY;
	}// Cierre del m�todo

	/**
	 * 
	 * M�todo abstracto que nos permite mostrar la imagen del personaje en el
	 * JLABEL.
	 * 
	 */
	abstract public void muestraGrafico();

	/**
	 * 
	 * M�todo que nos permite averiguar si el ghost esta en la pantalla.
	 * 
	 * @return Devuelve TRUE si el ghost esta en la pantalla.
	 */
	public boolean isOnBoard() {
		return onBoard;
	}

	/**
	 * 
	 * M�todo que nos permite indicar que el ghost esta en la pantalla.
	 * 
	 * @param boolean pasamos si esta el personaje en tablero.
	 */

	public void setOnBoard(boolean onBoard) {
		this.onBoard = onBoard;
	}

	/**
	 * 
	 * M�todo que nos permite capturar el momento exacto en el que pacman se ha
	 * comido un ghost.
	 * 
	 * @return Devuelve un entero con la fecha y hora exactas cuando se ha
	 *         comido pacman un ghost.
	 */

	public long getUltimaVezComido() {
		return ultimaVezComido;
	}

	/**
	 * 
	 * M�todo que nos permite actualizar el momento exacto en el que pacman se
	 * ha comido un ghost.
	 * 
	 * @param long con la fecha y hora exactas en la que fu� comido .
	 */

	public void setUltimaVezComido(long ultimaVezComido) {
		this.ultimaVezComido = ultimaVezComido;
	}

	/**
	 * M�todo que realiza el cambio de direction del ghost y que lo
	 * utilizamos cuando chocan los ghosts.
	 */

	public void cambiaADirectionContraria() {
		if (direction == GameCharacterGraphic.EAST) {
			direction = GameCharacterGraphic.WEST;
			return;
		}
		if (direction == GameCharacterGraphic.WEST) {
			direction = GameCharacterGraphic.EAST;
			return;
		}
		if (direction == GameCharacterGraphic.NORTH) {
			direction = GameCharacterGraphic.SOUTH;
			return;
		}
		if (direction == GameCharacterGraphic.SOUTH) {
			direction = GameCharacterGraphic.NORTH;
			return;
		}
	}

}// Cierre de la clase.