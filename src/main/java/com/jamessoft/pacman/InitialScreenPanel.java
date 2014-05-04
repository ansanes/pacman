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
 * Initial Screen Panel
 */

public class InitialScreenPanel extends JPanel {
	
	private JButton accionButton;

	private static final ImageIcon BACKGROUND_IMAGE = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pantallainicio.gif"));
	private static final ImageIcon GAME_OVER_IMAGE = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pacman-game-over.gif"));
	private static final ImageIcon WIN_IMAGEN = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/pacman-victoria.gif"));
	private static final ImageIcon BUTTON_IMAGE = new ImageIcon(Ghost.class.getResource("/com/jamessoft/pacman/resources/InicioyFin/boton_inicio.gif"));
	
	private Image background;
	
	private Integer height;
	
	private Integer width;	
	private GameScreenFrame gameScreenFrame;
	
	


	public InitialScreenPanel(GameScreenFrame gameScreenFrame) {
		this.gameScreenFrame=gameScreenFrame;
		setLayout(null);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		background = BACKGROUND_IMAGE.getImage();
		height = BACKGROUND_IMAGE.getIconHeight();
		width = BACKGROUND_IMAGE.getIconWidth();
		Integer altoboton = BUTTON_IMAGE.getIconHeight();
		Integer anchoboton = BUTTON_IMAGE.getIconWidth();
		accionButton = new JButton("iniciar juego");
		accionButton.setBackground(Color.BLACK);
		accionButton.setBounds(150, 380, anchoboton, altoboton);
		accionButton.setIcon(BUTTON_IMAGE);
		
		accionButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				InitialScreenPanel.this.gameScreenFrame.inicioJuego();
				
			}
		});
		
		add(accionButton);

	}
	
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
	}

	public void setGameOver(){
		background=GAME_OVER_IMAGE.getImage();			
	}
	
	
	public void setVictoria() {
		background=WIN_IMAGEN.getImage();
		
	}
	
	public Integer getAltoImagen() {
		return height;
	}

	public Integer getAnchoImagen() {
		return width;
	}

}
