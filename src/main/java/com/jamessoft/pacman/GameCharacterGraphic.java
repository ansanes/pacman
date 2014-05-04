package com.jamessoft.pacman;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * De esta clase abstracta heredan todos los personajes del juego.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public abstract class GameCharacterGraphic {
	// Directions que pueden tomar los personajes.
	protected static final int NORTH = 0;
	protected static final int SOUTH = 1;
	protected static final int EAST = 2;
	protected static final int WEST = 3;
	// Celda o cell donde se encuentra nuestro personaje (Columna y fila).
	protected Integer planoX;
	protected Integer planoY;
	// posicion coordenada x y coordenada y.
	protected int x;
	protected int y;
	// Ancho y Alto de cada celda (JLABEL)
	protected static final int ancho = 16;
	protected static final int alto = 16;
	// Direction que puede tomar el personaje.
	protected Integer direction;
	//Instancia de labyrinth para poder realizar llamadas de consultas.
	protected Labyrinth labyrinth = PacmanGame.getInstace().getLaberinto();

	/**
	 * M�todo abstracto del movimiento de los personajes.
	 * 
	 */
	
	protected abstract boolean movement();
	
	/**
	 * M�todo que devuelve la posici�n x de nuestro personaje.
	 * 
	 * @return Devuelve posici�n x.
	 */
	
	public int getX() {
		return x;
	}// Cierre del m�todo

	/**
	 * M�todo que devuelve la posici�n y de nuestro personaje.
	 * 
	 * @return Devuelve posici�n y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * M�todo que crea un Rectangulo sobre el personaje. Lo utilizamos con la
	 * idea de posteriormente detectar colisiones entre personajes.
	 * 
	 * @return Devuelve un Rectangulo que envuelve a nuestro personaje (pacaman
	 *         o ghosts).
	 */
	public Rectangle ObtenerLimites() {
		return new Rectangle(x, y, ancho, alto);
	}

	/**
	 * M�todo para obtener la posici�n de la columna del array del labyrinth
	 * donde esta el personaje.
	 * 
	 * @return Devuelve la posici�n de la columna donde esta el personaje dentro
	 *         labyrinth.
	 */
	public Integer getPlanoX() {
		return planoX;
	}
	
	/**
	 * M�todo para actualizar la posici�n de la columna del array del labyrinth
	 * donde esta el personaje y la posici�n x.
	 * 
	 * @param Integer que corresponde con la columna.
	 */

	public void setPlanoX(Integer planoX) {	
		this.planoX = planoX;
		this.x=planoX*16;
	}

	/**
	 * M�todo para obtener la posici�n de la fila del array del labyrinth donde
	 * esta el personaje.
	 * 
	 * @return Devuelve la posici�n de la fila donde esta el personaje dentro
	 *         labyrinth.
	 */
	public Integer getPlanoY() {
		return planoY;
	}
	
	/**
	 * M�todo para actualizar la posici�n de la fila del array del labyrinth
	 * donde esta el personaje y la posici�n y.
	 * 
	 * @param Integer que corresponde con la la fila.
	 */

	public void setPlanoY(Integer planoY) {
		this.planoY = planoY;
		this.y=planoY*16;
	}

	/**
	 * 
	 * M�todo que devuelve CIERTO si el personaje se encuentra en una cell de
	 * instersecci�n.
	 * 
	 * @return Devuelve CIERTO si el personaje esta en una intersecci�n con 2 o
	 *         mas directions posibles.
	 */
	protected boolean intersection() {
	
		Integer planox = labyrinth.getColumn(x);
		Integer planoy = labyrinth.geRow(y);
		//Esto evita desbordamiento cuando el ghost esta en los limites.
		if (planox == 0 || planox == 27) {
			return false;
		}
		//Obtenemos los valores de las cells contiguas para saber si son pisables.
		Integer cellright = labyrinth.getValueAt(planox + 1, planoy);
		Integer cellleft = labyrinth.getValueAt(planox - 1, planoy);
		Integer cellup = labyrinth.getValueAt(planox, planoy - 1);
		Integer celldown = labyrinth.getValueAt(planox, planoy + 1);
		//Detectamos las intersectiones.
		if (cellright != -1 && celldown != -1) {
			return true;
		}
		if (cellleft != -1 && celldown != -1) {
			return true;
		}
		if (cellleft != -1 && cellup != -1) {
			return true;
		}
		if (cellright != -1 && cellup != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * M�todo que nos permite averiguar las posibles directions que puede tomar
	 * un personaje en su celda.
	 * 
	 * @param x
	 *            (COLUMNA), y (FILA) y directionActual(NORTH,SOUTH,EAST,WEST).
	 * @return Devuelve una LISTA con las posibles directions que puede tomar
	 *         el personaje desde su cell en el pr�ximo movimiento.
	 */
	protected List<Integer> getDirectionsIntersection(Integer x, Integer y,
			Integer directionActual) {
				List<Integer> directions = new ArrayList<Integer>();
				Integer cellRight = labyrinth.getValueAt(x + 1, y);
				Integer cellLeft = labyrinth.getValueAt(x - 1, y);
				Integer cellUp = labyrinth.getValueAt(x, y - 1);
				Integer cellDown = labyrinth.getValueAt(x, y + 1);
				if (cellUp != -1 && directionActual != GameCharacterGraphic.SOUTH) {
					directions.add(GameCharacterGraphic.NORTH);
				}
				if (cellDown != -1 && directionActual != GameCharacterGraphic.NORTH) {
					directions.add(GameCharacterGraphic.SOUTH);
				}
				if (cellRight != -1 && directionActual != GameCharacterGraphic.WEST) {
					directions.add(GameCharacterGraphic.EAST);
				}
				if (cellLeft != -1 && directionActual != GameCharacterGraphic.EAST) {
					directions.add(GameCharacterGraphic.WEST);
				}
				return directions;
			}
}