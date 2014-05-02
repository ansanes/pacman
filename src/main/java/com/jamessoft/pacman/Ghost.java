package com.jamessoft.pacman;

import java.util.Random;
import javax.swing.JLabel;

/**
 * De esta clase abstracta heredan todos los fantasmas.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */
public abstract class Ghost extends GameCharacterGraphic {
	// Posici�n en columna y fila inicial del fantasma.
	public static final Integer planoXInicial = 14;
	public static final Integer planoYInicial = 14;

	// Velocidad de los fantasmas.
	private static final int SPEED = 2;
	// Todos los fantasmas son JLabel.
	protected JLabel ghostLabel;
	// Indica si el fantasma esta en el tablero.
	protected boolean enTablero = false;
	// Creamos variable random que se utilizara para elegir direccion en
	// intersecciones.
	protected Random rnd = new Random();
	// Almacena un entero con los milisegundos de la fecha actual que hace que
	// se comio.
	protected long ultimaVezComido = 0;

	/**
	 * Constructor que nos da los parametros generales de los fantasmas.
	 */

	public Ghost() {
		// Posici�n que toman por defecto al ser creados.
		setPlanoX(planoXInicial);
		setPlanoY(planoYInicial);

		// Direccion que toman por defecto.
		direccion = GameCharacterGraphic.OESTE;
		// Creacion label del fantasma.
		ghostLabel = new JLabel();
		ghostLabel.setBounds(x, y, ancho, alto);
		ghostLabel.validate();

	}

	/**
	 * 
	 * M�todo que devuelve si es posible mover personaje y adem�s la realiza si
	 * es cierto actualizando coordenadas (x,y) y plano (columan,fila).
	 * 
	 * @return Devuelve si hay cambio de casilla
	 */
	protected boolean movimiento() {
		// Creamos 4 variables para ver cuales son las siguientes coordenadas.
		int newY = 0;
		int newX = 0;
		int newPlanoX = 0;
		int newPlanoY = 0;
		// Hacemos un switch con el parametro direcci�n que tiene el personaje.
		// Actualizamos su futura posicion.
		switch (direccion) {
		case GameCharacterGraphic.ESTE:
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumna(newX + 15);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.OESTE:
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumna(newX);
			newPlanoY = planoY;
			break;
		case GameCharacterGraphic.NORTE:
			newY = y - SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumna(newY);
			break;
		case GameCharacterGraphic.SUR:
			newY = y + SPEED;
			newX = x;
			newPlanoX = planoX;
			newPlanoY = labyrinth.getColumna(newY + 15);
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
		if (enTablero) {
			calcularDireccion();
			movimiento();
		}
	}

	/**
	 * 
	 * M�todo ABSTRACTO para calcular la direccion que tomara el fantasma.
	 * 
	 */
	protected abstract void calcularDireccion();

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta a la DERECHA del fantasma.
	 * 
	 * @return Devuelve CIERTO si pacman esta a la DERECHA del fantasma.
	 */
	protected boolean pacmanDerecha() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX > planoX;
	}

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta a la IZQUIERDA del fantasma.
	 * 
	 * @return Devuelve CIERTO si pacman esta a la IZQUIERDA del fantasma.
	 */
	protected boolean pacmanIzquierda() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX < planoX;
	}// Cierre del m�todo

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta ARRIBA del fantasma.
	 * 
	 * @return Devuelve CIERTO si pacman esta ARRIBA del fantasma.
	 */
	protected boolean pacmanArriba() {
		Integer pacmanY = PacmanGame.getInstace().getPacman().getPlanoY();
		return pacmanY < planoY;
	}

	/**
	 * 
	 * M�todo que devuelve CIERTO si pacman esta ABAJO del fantasma.
	 * 
	 * @return Devuelve CIERTO si pacman esta ABAJO del fantasma.
	 */
	protected boolean pacmanAbajo() {
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
	 * M�todo que nos permite averiguar si el fantasma esta en la pantalla.
	 * 
	 * @return Devuelve TRUE si el fantasma esta en la pantalla.
	 */
	public boolean isEnTablero() {
		return enTablero;
	}

	/**
	 * 
	 * M�todo que nos permite indicar que el fantasma esta en la pantalla.
	 * 
	 * @param boolean pasamos si esta el personaje en tablero.
	 */

	public void setEnTablero(boolean enTablero) {
		this.enTablero = enTablero;
	}

	/**
	 * 
	 * M�todo que nos permite capturar el momento exacto en el que pacman se ha
	 * comido un fantasma.
	 * 
	 * @return Devuelve un entero con la fecha y hora exactas cuando se ha
	 *         comido pacman un fantasma.
	 */

	public long getUltimaVezComido() {
		return ultimaVezComido;
	}

	/**
	 * 
	 * M�todo que nos permite actualizar el momento exacto en el que pacman se
	 * ha comido un fantasma.
	 * 
	 * @param long con la fecha y hora exactas en la que fu� comido .
	 */

	public void setUltimaVezComido(long ultimaVezComido) {
		this.ultimaVezComido = ultimaVezComido;
	}

	/**
	 * M�todo que realiza el cambio de direccion del fantasma y que lo
	 * utilizamos cuando chocan los fantasmas.
	 */

	public void cambiaADireccionContraria() {
		if (direccion == GameCharacterGraphic.ESTE) {
			direccion = GameCharacterGraphic.OESTE;
			return;
		}
		if (direccion == GameCharacterGraphic.OESTE) {
			direccion = GameCharacterGraphic.ESTE;
			return;
		}
		if (direccion == GameCharacterGraphic.NORTE) {
			direccion = GameCharacterGraphic.SUR;
			return;
		}
		if (direccion == GameCharacterGraphic.SUR) {
			direccion = GameCharacterGraphic.NORTE;
			return;
		}
	}

}// Cierre de la clase.