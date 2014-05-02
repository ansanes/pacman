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
	// Direcciones que pueden tomar los personajes.
	protected static final int NORTE = 0;
	protected static final int SUR = 1;
	protected static final int ESTE = 2;
	protected static final int OESTE = 3;
	// Celda o casilla donde se encuentra nuestro personaje (Columna y fila).
	protected Integer planoX;
	protected Integer planoY;
	// posicion coordenada x y coordenada y.
	protected int x;
	protected int y;
	// Ancho y Alto de cada celda (JLABEL)
	protected static final int ancho = 16;
	protected static final int alto = 16;
	// Direccion que puede tomar el personaje.
	protected Integer direccion;
	//Instancia de labyrinth para poder realizar llamadas de consultas.
	protected Labyrinth labyrinth = PacmanGame.getInstace().getLaberinto();

	/**
	 * M�todo abstracto del movimiento de los personajes.
	 * 
	 */
	
	protected abstract boolean movimiento();
	
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
	 *         o fantasmas).
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
	 * M�todo que devuelve CIERTO si el personaje se encuentra en una casilla de
	 * instersecci�n.
	 * 
	 * @return Devuelve CIERTO si el personaje esta en una intersecci�n con 2 o
	 *         mas direcciones posibles.
	 */
	protected boolean interseccion() {
	
		Integer planox = labyrinth.getColumna(x);
		Integer planoy = labyrinth.getFila(y);
		//Esto evita desbordamiento cuando el fantasma esta en los limites.
		if (planox == 0 || planox == 27) {
			return false;
		}
		//Obtenemos los valores de las casillas contiguas para saber si son pisables.
		Integer casilladerecha = labyrinth.getValueAt(planox + 1, planoy);
		Integer casillaizquierda = labyrinth.getValueAt(planox - 1, planoy);
		Integer casillaarriba = labyrinth.getValueAt(planox, planoy - 1);
		Integer casillaabajo = labyrinth.getValueAt(planox, planoy + 1);
		//Detectamos las intersecciones.
		if (casilladerecha != -1 && casillaabajo != -1) {
			return true;
		}
		if (casillaizquierda != -1 && casillaabajo != -1) {
			return true;
		}
		if (casillaizquierda != -1 && casillaarriba != -1) {
			return true;
		}
		if (casilladerecha != -1 && casillaarriba != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * M�todo que nos permite averiguar las posibles direcciones que puede tomar
	 * un personaje en su celda.
	 * 
	 * @param x
	 *            (COLUMNA), y (FILA) y direccionActual(NORTE,SUR,ESTE,OESTE).
	 * @return Devuelve una LISTA con las posibles direcciones que puede tomar
	 *         el personaje desde su casilla en el pr�ximo movimiento.
	 */
	protected List<Integer> getDireccionesInterseccion(Integer x, Integer y,
			Integer direccionActual) {
				List<Integer> direcciones = new ArrayList<Integer>();
				Integer casillaDerecha = labyrinth.getValueAt(x + 1, y);
				Integer casillaIzquierda = labyrinth.getValueAt(x - 1, y);
				Integer casillaArriba = labyrinth.getValueAt(x, y - 1);
				Integer casillaAbajo = labyrinth.getValueAt(x, y + 1);
				if (casillaArriba != -1 && direccionActual != GameCharacterGraphic.SUR) {
					direcciones.add(GameCharacterGraphic.NORTE);
				}
				if (casillaAbajo != -1 && direccionActual != GameCharacterGraphic.NORTE) {
					direcciones.add(GameCharacterGraphic.SUR);
				}
				if (casillaDerecha != -1 && direccionActual != GameCharacterGraphic.OESTE) {
					direcciones.add(GameCharacterGraphic.ESTE);
				}
				if (casillaIzquierda != -1 && direccionActual != GameCharacterGraphic.ESTE) {
					direcciones.add(GameCharacterGraphic.OESTE);
				}
				return direcciones;
			}
}