package com.jamessoft.pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Esta clase crea un JPanel con la pagina principal, la pagina de gameover y la de fase superada.
 * Adem�s crea el boton de inicio del juego.
 * 
 * @author: Francisco Javier Chisber Vila
 * @version: 16/04/2014
 */

public class InitialScreenPanel extends JPanel {
	//Boton para iniciar juego.
	private JButton accionButton;
	//Imagen que contendra el panel dependiendo del estado del juego.
	private static final ImageIcon IMAGEN_FONDO = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pantallainicio.gif"));
	private static final ImageIcon IMAGEN_GAME_OVER = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pacman-game-over.gif"));
	private static final ImageIcon IMAGEN_FASE_SUPERADA = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pacman-victoria.gif"));
	private static final ImageIcon IMAGEN_BOTON = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/boton_inicio.gif"));
	//Imagen del fondo del JPanel.
	private Image background;
	//Alto de la imagen.
	private Integer altoImagen;
	//Ancho de la imagen.
	private Integer anchoImagen;
	//Instancia a GameScreenFrame.
	private GameScreenFrame juegoFrame;
	
	/**
	 * 
	 * M�todo que se encarga de generar el JPanel y el boton de inicio.
	 * 
	 * @param GameScreenFrame del juego.
	 *           
	 */


	public InitialScreenPanel(GameScreenFrame gameScreenFrame) {
		this.juegoFrame=gameScreenFrame;
		setLayout(null);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		background = IMAGEN_FONDO.getImage();
		altoImagen = IMAGEN_FONDO.getIconHeight();
		anchoImagen = IMAGEN_FONDO.getIconWidth();
		Integer altoboton = IMAGEN_BOTON.getIconHeight();
		Integer anchoboton = IMAGEN_BOTON.getIconWidth();
		accionButton = new JButton("iniciar juego");
		accionButton.setBackground(Color.BLACK);
		accionButton.setBounds(150, 380, anchoboton, altoboton);
		accionButton.setIcon(IMAGEN_BOTON);
		
		accionButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				juegoFrame.inicioJuego();
				
			}
		});
		
		add(accionButton);

	}
	
	/**
	 * 
	 * M�todo que se encarga de pintar el panel.
	 * 
	 * @param Graphics
	 *           
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
	}

	/**
	 * 
	 * M�todo que se encarga de actualizar la imagen de GAME OVER.
	 *           
	 */
	
	public void setGameOver(){
		background=IMAGEN_GAME_OVER.getImage();			
	}
	
	/**
	 * 
	 * M�todo que se encarga de actualizar la imagen de FASE SUPERADA.
	 *           
	 */
	
	public void setVictoria() {
		background=IMAGEN_FASE_SUPERADA.getImage();
		
	}
	
	/**
	 * 
	 * M�todo que devuelve el alto de la imagen del JPanel.
	 *           
	 */
	
	public Integer getAltoImagen() {
		return altoImagen;
	}

	/**
	 * 
	 * M�todo que devuelve el ancho de la imagen del JPanel.
	 *           
	 */	
	public Integer getAnchoImagen() {
		return anchoImagen;
	}

}
