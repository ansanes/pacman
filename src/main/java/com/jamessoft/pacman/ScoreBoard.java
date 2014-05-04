package com.jamessoft.pacman;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBoard extends JPanel {

    // Creamos un arraylist donde coincide la posici�n con la imagen.
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

    // Creamos las 4 labels del marcador.
    private JLabel centenasLabel;
    private JLabel decenasLabel;
    private JLabel unidadesLabel;
    private JLabel unidadmillarLabel;

    // Contador de puntos.
    private int puntos;

    /**
     * Constructor que crea un panel con 4 labels para hacer el marcador.
     *
     * @param int con la puntuacion.
     */
    public ScoreBoard(int puntuacion) {
        puntos = puntuacion;
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

    /**
     * M�todo para dividir la puntuaci�n en unidades, decenas, centenas y unidad
     * de millar. Posteriormente a�ade la imagen correspondiente a cada
     * marcador.
     */
    private void cambiaMarcador() {
        String puntosAmostrar = "" + (puntos + 10000);
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
     * M�todo para incrementar los puntos y repintar el marcador, cuando comemos
     * un punto o un ghost.
     *
     * @param int como parametro la puntos incrementados.
     */
    public void actualizarMarcador(int puntos) {
        this.puntos += puntos;
        cambiaMarcador();
        repaint();

    }

}
