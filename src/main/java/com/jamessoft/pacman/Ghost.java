package com.jamessoft.pacman;

import java.util.Random;
import javax.swing.JLabel;

/**
 * All Ghosts inherit from this class
 * 
 */
public abstract class Ghost extends GameCharacterGraphic {

	public static final Integer initiaPlaneX = 14;
	public static final Integer initialPlaneY = 14;


	private static final int SPEED = 2;

	protected JLabel ghostLabel;

	protected boolean onBoard = false;
	protected Random rnd = new Random();
	protected long lastTimeEaten = 0;

	/**
	 * Initialize ghost
	 */

	public Ghost() {
		
		setPlanoX(initiaPlaneX);
		setPlanoY(initialPlaneY);

		
		direction = GameCharacterGraphic.WEST;
		
		ghostLabel = new JLabel();
		ghostLabel.setBounds(x, y, ancho, alto);
		ghostLabel.validate();

	}

	/**
	 * 
	 * Moves the ghost
         * 
	 * @return cell changed
	 */
	protected boolean movement() {
		int newY = 0;
		int newX = 0;
		int newPlanoX = 0;
		int newPlanoY = 0;
		
		switch (direction) {
		case GameCharacterGraphic.EAST:
			newX = x + SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX + 15);
			newPlanoY = planeY;
			break;
		case GameCharacterGraphic.WEST:
			newX = x - SPEED;
			newY = y;
			newPlanoX = labyrinth.getColumn(newX);
			newPlanoY = planeY;
			break;
		case GameCharacterGraphic.NORTH:
			newY = y - SPEED;
			newX = x;
			newPlanoX = planeX;
			newPlanoY = labyrinth.getColumn(newY);
			break;
		case GameCharacterGraphic.SOUTH:
			newY = y + SPEED;
			newX = x;
			newPlanoX = planeX;
			newPlanoY = labyrinth.getColumn(newY + 15);
			break;
		default:
			break;
		}
		planeX = newPlanoX;
		planeY = newPlanoY;
		x = newX;
		y = newY;
		if (planeX == -1) {
			planeX = 27;
			x = 27 * 16;
		} else if (planeX == 28) {
			planeX = 0;
			x = 0;
		}
		return true;
	}

	/**
	 * Tries to move the ghost
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
	 * Calculate ghost direcction based on algorithm
	 * 
	 */
	protected abstract void calculateDirection();

	/**
	 * 
	 * is Pacman right
	 * @return is pacman on the right
	 */
	protected boolean pacmanRight() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX > planeX;
	}

	/**
	 * 
	 * is pacman left
	 * 
	 * @return is pacman left
	 */
	protected boolean pacmanLeft() {
		Integer pacmanX = PacmanGame.getInstace().getPacman().getPlanoX();
		return pacmanX < planeX;
	}

	/**
	 * 
	 * is pacman up
	 * 
	 * @return is pacman up
	 */
	protected boolean pacmanUp() {
		Integer pacmanY = PacmanGame.getInstace().getPacman().getPlanoY();
		return pacmanY < planeY;
	}

	/**
	 * 
	 * is pacman down
	 * 
	 * @return is pacman down
	 */
	protected boolean pacmanDown() {
		Integer pacmanY = PacmanGame.getInstace().getPacman().getPlanoY();
		return pacmanY > planeY;
	}

	/**
	 * 
	 *  Shows ghost label
	 * 
	 */
	abstract public void showGraphic();

	/**
	 * 
	 * is on board or waiting to go in
	 * 
	 * @return is on board or waiting to go in
	 */
	public boolean isOnBoard() {
		return onBoard;
	}

	/**
	 * 
	 * sets onboard
	 * 
	 * @param boolean on board value
	 */

	public void setOnBoard(boolean onBoard) {
		this.onBoard = onBoard;
	}

	/**
	 * 
	 * last time eaten
	 * 
	 * @return last milliseconds ghost was eaten
	 */

	public long getLastTimeEaten() {
		return lastTimeEaten;
	}

	/**
	 * 
	 * set last time eaten
	 * 
	 * @param last time eaten
	 */

	public void setLastTimeEaten(long lastTimeEaten) {
		this.lastTimeEaten = lastTimeEaten;
	}

	/**
	 * changes to oppposite direction
	 */

	public void changeToOppositeDirection() {
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

}