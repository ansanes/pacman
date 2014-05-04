package com.jamessoft.pacman;

import java.awt.EventQueue;
import java.util.Date;

/**
 * Inits the game.
 * This class is a singleton to make all global data available for all the classes
 * 
 */

public class PacmanGame {	
	private static final int ESPECIAL_STATE_DURATION = 5;

	
	private static PacmanGame instance;
	
	private Labyrinth labyrinth;
	
	private GameScreenFrame gameScreenFrame;
	
	private Pacman pacman;
	
	private long specialStateActivated = 0;

	/**
	 * Init labyrinth
	 */

	private PacmanGame() {
		labyrinth = new Labyrinth();

	}

	/**
	 *  Singleton pattern. Gets the instance
	 */

	public static PacmanGame getInstace() {		
		if (instance == null) {
			instance = new PacmanGame();
		}
		return instance;
	}

	/**
	 * Shows the initialFrame
	 */

	public void startGame() {
		gameScreenFrame = new GameScreenFrame();
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				gameScreenFrame.setVisible(true);
			}
		});
	}

	/**
	 * 	 
	 * Labyrinth getter
	 * @return labyrinth
	 */

	public Labyrinth getLaberinto() {
		return labyrinth;
	}

	/**
	 * 	 
	 * Pacman getter
	 * @return pacman
	 */
	public Pacman getPacman() {
		return pacman;
	}

	/**
	 * 
	 *  Pacman setter
	 * 
	 * @param pacman
	 */

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	/**
	 * 	 
	 * Activate special state
	 */

	public void activeSpecialState() {
		Date now = new Date();
		specialStateActivated = now.getTime();
	}

	/**
	 * 	
	 * Is special state activated?
	 * @return Is special state activated?
	 */

	public boolean estadoEspecial() {
		Date ahora = new Date();
		return ahora.getTime() - specialStateActivated < ESPECIAL_STATE_DURATION * 1000;
	}

	/**
	 * 	 .
	 * main method
	 */

	public static void main(String Args[]) {
		PacmanGame.getInstace().startGame();
	}
}
