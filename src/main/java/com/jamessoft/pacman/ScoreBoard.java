package com.jamessoft.pacman;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*ScoreBoard of the game
*/
public class ScoreBoard extends JPanel {
    
    
    private ImageIcon[] digitImages = {
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/0.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/1.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/2.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/3.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/4.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/5.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/6.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/7.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/8.gif")),
        new ImageIcon(
        Ghost.class
        .getResource("/com/jamessoft/pacman/resources/LaberintoGIF/9.gif")),};


    private JLabel centenasLabel;
    private JLabel decenasLabel;
    private JLabel unidadesLabel;
    private JLabel unidadmillarLabel;


    private int points;

    /**
     * Inits the score board with the appropiate points
     *
     * @param int con la puntuacion.
     */
    public ScoreBoard(int points) {
        points = points;
        setLayout(null);
        centenasLabel = new JLabel();
        decenasLabel = new JLabel();
        unidadesLabel = new JLabel();
        unidadmillarLabel = new JLabel();
        unidadmillarLabel.setBounds(0, 0, 16, 16);
        centenasLabel.setBounds(16, 0, 16, 16);
        decenasLabel.setBounds(32, 0, 16, 16);
        unidadesLabel.setBounds(48, 0, 16, 16);
        this.setBackground(Color.white);
        this.add(unidadmillarLabel);
        this.add(centenasLabel);
        this.add(decenasLabel);
        this.add(unidadesLabel);
        this.setVisible(true);
        this.validate();
        this.updateUI();

    }

   
    private void cambiaMarcador() {
        String puntosAmostrar = "" + (points + 10000);
        int unidades = Integer.parseInt(puntosAmostrar.substring(
                puntosAmostrar.length() - 1, puntosAmostrar.length()));
        int decenas = Integer.parseInt(puntosAmostrar.substring(
                puntosAmostrar.length() - 2, puntosAmostrar.length() - 1));
        int centenas = Integer.parseInt(puntosAmostrar.substring(
                puntosAmostrar.length() - 3, puntosAmostrar.length() - 2));
        int unidadmillar = Integer.parseInt(puntosAmostrar.substring(
                puntosAmostrar.length() - 4, puntosAmostrar.length() - 3));
        unidadesLabel.setIcon(digitImages[unidades]);
        decenasLabel.setIcon(digitImages[decenas]);
        centenasLabel.setIcon(digitImages[centenas]);
        unidadmillarLabel.setIcon(digitImages[unidadmillar]);

    }

    /**
     * Updates scoreboard with the given points
     * @param int number of points
     */
    public void updateScoreBoard(int points) {
        this.points += points;
        cambiaMarcador();
        repaint();

    }

}
