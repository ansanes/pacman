package com.jamessoft.pacman;

import java.awt.EventQueue;
import java.util.Date;

/**
 * Clase con metodo main que inicia el juego y adem�s permite mediante el patron
 * de dise�o singelton que todas las clases contenidas sean accesibles al resto
 * de clases.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class PacmanGame {
	// Segundos que va a durar el estado especial.
	private static final int DURACION_ESTADO_ESPECIAL = 5;

	// Creamos una instancia a la clase.
	private static PacmanGame instance;
	// Creamos una instancia a labyrinth.
	private Labyrinth labyrinth;
	// Creamos una instancia a gameScreenFrame.
	private GameScreenFrame gameScreenFrame;
	// Creamos una instancia a pacman.
	private Pacman pacman;
	// instante en el tiempo en el cual se activ� el �ltimo estado especial.
	private long estadoEspecialActivado = 0;

	/**
	 * Constructor que crea el labyrinth.
	 */

	private PacmanGame() {
		labyrinth = new Labyrinth();

	}

	/**
	 * M�todo est�tico que permite acceder a las instancia de la clase desde
	 * cualquier punto de la aplicaci�n.
	 */

	public static PacmanGame getInstace() {
		// Esto evita que se haga mas de una llamada en la creaci�n del juego.
		if (instance == null) {
			instance = new PacmanGame();
		}
		return instance;
	}

	/**
	 * M�todo que lanza el Frame.
	 */

	public void iniciaJuego() {
		gameScreenFrame = new GameScreenFrame();
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				gameScreenFrame.setVisible(true);
			}
		});
	}

	/**
	 * 
	 * M�todo para hacer una instancia al labyrinth.
	 * 
	 * @return Devuelve el objeto labyrinth creado.
	 */

	public Labyrinth getLaberinto() {
		return labyrinth;
	}

	/**
	 * 
	 * M�todo para hacer una instancia a pacman.
	 * 
	 * @return Devuelve el personaje pacman creado.
	 */
	public Pacman getPacman() {
		return pacman;
	}

	/**
	 * 
	 * M�todo actualizar a pacman.
	 * 
	 * @param Pacman.
	 */

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	/**
	 * 
	 * M�todo que se invoca cuando pacman se come una bola grande y actualiza el
	 * instante de tiempo exacto en que se activ� el �ltimo estado especial
	 * 
	 */

	public void activaEstadoEspecial() {
		Date ahora = new Date();
		estadoEspecialActivado = ahora.getTime();
	}

	/**
	 * 
	 * Devuelve true si han transcurrido menos de 5 segundos desde la �ltima
	 * activaci�n del estado especial. Se invoca constantemente para decidir
	 * como pintar los fantasmas (azules en estado especcial o con su color
	 * normal cuando no).
	 * 
	 * @return TRUE si no ha finalizado el estado especial.
	 */

	public boolean estadoEspecial() {
		Date ahora = new Date();
		return ahora.getTime() - estadoEspecialActivado < DURACION_ESTADO_ESPECIAL * 1000;
	}

	/**
	 * 
	 * Metodo main que lanza el juego.
	 * 
	 */

	public static void main(String Args[]) {
		PacmanGame.getInstace().iniciaJuego();
	}
}
