package com.jamessoft.pacman;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.*;

/**
 * Esta clase es la encargada de crear el labyrinth, los personajes y la logica
 * del juego.
 * 
 * @author:Francisco Javier Chisber Vila
 * @version:16/04/2014
 */

public class GameScreenFrame extends JFrame implements KeyListener,
		ActionListener {
	// Velocidad de refresco con el que actualizamos el estado del juego en
	// milisegundos.
	private static final int VELOCIDAD_REFRESCO = 20;
	// Es el labyrinth virtual que indica que contiene cada celda.
	private Labyrinth laberintoVirtual;
	// Es el labyrinth de JLABEL de 28 COLUMNAS x 36 FILAS.
	private JLabel[][] laberintoGrafico = new JLabel[28][36];
	// El refresco del juego.
	private Timer timer;
	// Personaje Pacman.
	private Pacman pacman;
	// Ghost Red
	private Ghost blinky;
	// Ghost Pink.
	private Pinky pinky;
	// Ghost Orange.
	private Ghost clyde;
	// Panel de capas.
	private JLayeredPane juegoPane;
	// Acumulador de puntos.
	private int puntuacion;
	// Indica si el juego esta en pause.
	private boolean juegopausado;
	// JPanel del scoreBoard de puntuaci�n.
	private ScoreBoard scoreBoard;
	// Contador de las veces que hemos pasado por un ciclo del ActionPerformer.
	private Integer ciclo;
	// Contador vidas de pacman.
	private Integer vidas;
	// Panel de inicio de juego.
	private InitialScreenPanel initialScreenPanel;
	// CardLayout para saltar del juego a las pantallas de inicio, GameOver y
	// fase superada.
	private CardLayout cardLayout;
	// Contador de las bolitas que quedan por comer.
	private Integer bolitas;

	/**
	 * Constructor que crea el Frame (ventana), el Panel (inicio de juego), el
	 * CardLayout y adem�s le a�adimos el foco.
	 * 
	 */

	public GameScreenFrame() {
		initialScreenPanel = new InitialScreenPanel(this);
		initialScreenPanel.setBounds(0, 0,
				initialScreenPanel.getAnchoImagen(),
				initialScreenPanel.getAltoImagen());
		setSize(464, 614);
		setLocationByPlatform(true);

		cardLayout = new CardLayout();
		getContentPane().setLayout(cardLayout);
		getContentPane().add(initialScreenPanel, "iniciopane");

		getContentPane().setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		setResizable(false);
		setTitle("PACMAN 2014");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * 
	 * M�todo que inicializa los valores del juego. Adem�s crea los personajes,
	 * el scoreBoard y el timer. Adem�s crea el JLayeredPane en donde se
	 * mostraran.
	 */

	private void initJuegoPane() {
		puntuacion = 0;
		ciclo = 0;
		vidas = 1;
		bolitas = 244;

		if (juegoPane != null) {
			getContentPane().remove(juegoPane);
		}

		juegoPane = new JLayeredPane();

		getContentPane().add(juegoPane, "juegopane");

		laberintoVirtual = PacmanGame.getInstace().getLaberinto();

		scoreBoard = new ScoreBoard(puntuacion);

		juegoPane.setPreferredSize(new Dimension(448, 614));
		juegoPane.setBounds(0, 0, 464, 614);

		pacman = new Pacman();
		blinky = new Blinky();
		pinky = new Pinky();
		clyde = new Clyde();

		juegopausado = false;

		PacmanGame.getInstace().setPacman(pacman);

		timer = new Timer(VELOCIDAD_REFRESCO, this);
		timer.start();

		GenerarLaberintoGrafico();

		cardLayout.show(getContentPane(), "juegopane");

	}

	/**
	 * 
	 * M�todo que genera el labyrinth de imagenes de la pantalla dentro de sus
	 * JLABEL y los a�ade al PANEL DE CAPAS. A�adimos adem�s el objeto pacman y
	 * le indicamos que lo ponga en la capa superior. Adem�s a�ade el JPanel de
	 * scoreBoard de puntos.
	 */

	private void GenerarLaberintoGrafico() {
		int a = laberintoVirtual.getRowNumber();
		for (int i = 0; i < a; i++) {
			int k = laberintoVirtual.getColumnNumber();

			for (int j = 0; j < k; j++) {
				laberintoGrafico[i][j] = new JLabel();
				laberintoGrafico[i][j]
						.setIcon(new ImageIcon(
								Ghost.class.getResource("/com/jamessoft/pacman/resources/LaberintoGIF/"
										+ laberintoVirtual
												.getImageACell(i,
														j) + ".gif")));
				laberintoGrafico[i][j].setBounds(
						i * laberintoVirtual.getImageWidth(), j
								* laberintoVirtual.getImageHeight(),
						laberintoVirtual.getImageWidth(),
						laberintoVirtual.getImageHeight());
				laberintoGrafico[i][j].validate();

				juegoPane.add(laberintoGrafico[i][j], 3, 2);

			}

		}
		scoreBoard.setBounds(30, 15, 64, 16);
		juegoPane.add(scoreBoard, 3, 2);
		juegoPane.add(pacman.personajePacman, 3, 1);
	}

	/**
	 * 
	 * M�todo que se encarga de procesar las colisiones. 1- Pacman con bola
	 * peque�a: La pone invisible, suma 10 puntos, actualiza scoreBoard y resta
	 * contador bolas. 2- Pacman con bola grande: La pone invisible, ejecuta
	 * modo especial, no suma puntos y resta contador bolas. 3- Si contador
	 * bolas es igual a 0 ejecuta pantalla de fase superada. 4- Pacman con
	 * ghost en modo especial: Lo pone invisible, lo envia a casa un tiempo y
	 * suma 100 puntos. 5- Pacman con ghost en modo normal: Resta una vida y
	 * si son 0 fin de juego.
	 */
	private void procesarColisiones() {
		Integer x = pacman.getPlanoX();
		Integer y = pacman.getPlanoY();
		Integer valor = laberintoVirtual.getValueAt(x, y);
		if (valor == 1 && laberintoGrafico[x][y].isVisible() == true) {
			laberintoGrafico[x][y].setVisible(false);
			scoreBoard.actualizarMarcador(10);
			bolitas--;

		}
		if (valor == 2 && laberintoGrafico[x][y].isVisible() == true) {
			laberintoGrafico[x][y].setVisible(false);
			// laberintoVirtual.tipoCelda[x][y] = 0;
			PacmanGame.getInstace().activaEstadoEspecial();
			bolitas--;
		}
		// Pantalla de fase superada.
		if (bolitas == 0) {
			timer.stop();
			initialScreenPanel.setVictoria();
			cardLayout.show(getContentPane(), "iniciopane");
		}
		Ghost ghost = getGhostEnCoordenadas(pacman.getX(), pacman.getY());
		if (ghost != null) {
			// En modo especial.
			if (PacmanGame.getInstace().estadoEspecial()) {
				// Lo ponemos invisible.
				ghost.ghostLabel.setVisible(false);

				ghost.setUltimaVezComido(new Date().getTime());
				// Lo metemos dentro de la casa
				ghost.setPlanoX(15);
				ghost.setPlanoY(17);
				// Ejecutamos metodos para ver o esconder la imagen del ghost
				// en casa.
				visualizarPinkyenCasa();
				visualizarBlinkyenCasa();
				visualizarClydeenCasa();
				// Actualizo scoreBoard.
				scoreBoard.actualizarMarcador(100);
			} else {
				// En modo normal.
				if (vidas > 0) {
					vidas--;
					pacman.setPlanoX(13);
					pacman.setPlanoY(20);
					pacman.setDirection(GameCharacterGraphic.WEST);
					// Ponemos label de la vida en invisible.
					laberintoGrafico[2][34].setVisible(false);
					laberintoGrafico[2][35].setVisible(false);
					laberintoGrafico[3][34].setVisible(false);
					laberintoGrafico[3][35].setVisible(false);
				} else {
					// Pantalla de GAME OVER.
					timer.stop();
					initialScreenPanel.setGameOver();
					cardLayout.show(getContentPane(), "iniciopane");
					return;
				}
			}

		}
		// Si chocan los ghosts cambian de direcci�n.
		if (blinky.getPlanoX() == clyde.getPlanoX()
				&& blinky.getPlanoY() == clyde.getPlanoY()
				&& blinky.direction != clyde.direction) {
			blinky.cambiaADirectionContraria();
			clyde.cambiaADirectionContraria();
		}
		if (blinky.getPlanoX() == pinky.getPlanoX()
				&& blinky.getPlanoY() == pinky.getPlanoY()
				&& blinky.direction != pinky.direction) {
			blinky.cambiaADirectionContraria();
			pinky.cambiaADirectionContraria();
		}
		if (pinky.getPlanoX() == clyde.getPlanoX()
				&& pinky.getPlanoY() == clyde.getPlanoY()
				&& pinky.direction != clyde.direction) {
			pinky.cambiaADirectionContraria();
			clyde.cambiaADirectionContraria();
		}
	}

	/**
	 * 
	 * M�todo que se encarga de mostrar o ocultar las labels de Clyde en casa.
	 */

	private void visualizarClydeenCasa() {
		if ((clyde.ghostLabel.isVisible() == false)
				&& (clyde.isOnBoard() == true)) {
			laberintoGrafico[15][16].setVisible(true);
			laberintoGrafico[16][16].setVisible(true);
			laberintoGrafico[15][17].setVisible(true);
			laberintoGrafico[16][17].setVisible(true);
			laberintoGrafico[15][18].setVisible(true);
			laberintoGrafico[16][18].setVisible(true);
		}
		if ((clyde.ghostLabel.isVisible() == true)
				&& (clyde.isOnBoard() == true)) {
			laberintoGrafico[15][16].setVisible(false);
			laberintoGrafico[16][16].setVisible(false);
			laberintoGrafico[15][17].setVisible(false);
			laberintoGrafico[16][17].setVisible(false);
			laberintoGrafico[15][18].setVisible(false);
			laberintoGrafico[16][18].setVisible(false);
		}
		if ((clyde.ghostLabel.isVisible() == false)
				&& (clyde.isOnBoard() == false)) {
			laberintoGrafico[15][16].setVisible(true);
			laberintoGrafico[16][16].setVisible(true);
			laberintoGrafico[15][17].setVisible(true);
			laberintoGrafico[16][17].setVisible(true);
			laberintoGrafico[15][18].setVisible(true);
			laberintoGrafico[16][18].setVisible(true);
		}

	}

	/**
	 * 
	 * M�todo que se encarga de mostrar o ocultar las labels de Blinky en casa.
	 */
	private void visualizarBlinkyenCasa() {
		if ((blinky.ghostLabel.isVisible() == false)
				&& (blinky.isOnBoard() == true)) {
			laberintoGrafico[11][16].setVisible(true);
			laberintoGrafico[12][16].setVisible(true);
			laberintoGrafico[11][17].setVisible(true);
			laberintoGrafico[12][17].setVisible(true);
			laberintoGrafico[11][18].setVisible(true);
			laberintoGrafico[12][18].setVisible(true);
		}
		if ((blinky.ghostLabel.isVisible() == true)
				&& (blinky.isOnBoard() == true)) {
			laberintoGrafico[11][16].setVisible(false);
			laberintoGrafico[12][16].setVisible(false);
			laberintoGrafico[11][17].setVisible(false);
			laberintoGrafico[12][17].setVisible(false);
			laberintoGrafico[11][18].setVisible(false);
			laberintoGrafico[12][18].setVisible(false);
		}
		if ((blinky.ghostLabel.isVisible() == false)
				&& (blinky.isOnBoard() == false)) {
			laberintoGrafico[11][16].setVisible(true);
			laberintoGrafico[12][16].setVisible(true);
			laberintoGrafico[11][17].setVisible(true);
			laberintoGrafico[12][17].setVisible(true);
			laberintoGrafico[11][18].setVisible(true);
			laberintoGrafico[12][18].setVisible(true);
		}

	}

	/**
	 * 
	 * M�todo que se encarga de mostrar o ocultar las labels de Pinky en casa.
	 */

	private void visualizarPinkyenCasa() {
		if (pinky.ghostLabel.isVisible() == false
				&& (pinky.isOnBoard() == true)) {
			laberintoGrafico[13][16].setVisible(true);
			laberintoGrafico[14][16].setVisible(true);
			laberintoGrafico[13][17].setVisible(true);
			laberintoGrafico[14][17].setVisible(true);
			laberintoGrafico[13][18].setVisible(true);
			laberintoGrafico[14][18].setVisible(true);
		}
		if ((pinky.ghostLabel.isVisible() == true)
				&& (pinky.isOnBoard() == true)) {
			laberintoGrafico[13][16].setVisible(false);
			laberintoGrafico[14][16].setVisible(false);
			laberintoGrafico[13][17].setVisible(false);
			laberintoGrafico[14][17].setVisible(false);
			laberintoGrafico[13][18].setVisible(false);
			laberintoGrafico[14][18].setVisible(false);
		}
		if ((pinky.ghostLabel.isVisible() == false)
				&& (pinky.isOnBoard() == false)) {
			laberintoGrafico[13][16].setVisible(true);
			laberintoGrafico[14][16].setVisible(true);
			laberintoGrafico[13][17].setVisible(true);
			laberintoGrafico[14][17].setVisible(true);
			laberintoGrafico[13][18].setVisible(true);
			laberintoGrafico[14][18].setVisible(true);
		}
	}

	/**
	 * 
	 * M�todo que se encarga de detectar si hay un ghost en una cell y
	 * cual es. Calcula la distancia entre 2 puntos y si es menor de 16, indica
	 * que se estan tocando.
	 * 
	 * @param Integer
	 *            con posicion x y Integer con posici�n y.
	 * @return blinky, pinky o clyde.
	 */
	public Ghost getGhostEnCoordenadas(Integer x, Integer y) {
		if (calcularDistancia(x, y, blinky.getX(), blinky.getY()) < 16) {
			return blinky;
		}
		if (calcularDistancia(x, y, pinky.getX(), pinky.getY()) < 16) {
			return pinky;
		}
		if (calcularDistancia(x, y, clyde.getX(), clyde.getY()) < 16) {
			return clyde;
		}
		return null;
	}

	/**
	 * 
	 * M�todo para hacer una instancia a pacman.
	 * 
	 * @return objeto pacman.
	 */

	public Pacman getPacman() {
		return pacman;
	}

	/**
	 * 
	 * M�todo que se ejecuta en cada llamada del timer y se encarga de
	 * actualizar las posiciones de los personajes. Adem�s borra los puntos
	 * comidos y actualiza el scoreBoard de los puntos. Se le ha a�adido un
	 * contador de ciclo para sacar los ghosts uno a uno. Tambien actualiza
	 * el tiempo del estado especial.
	 * 
	 * @param ActionEvent
	 */

	public void actionPerformed(ActionEvent ke) {
		// Movemos a pacman.
		pacman.move();
		// Actualizamos el scoreBoard.
		scoreBoard.actualizarMarcador(puntuacion);
		// Actualizamos posici�n label de pacman.
		pacman.personajePacman.setLocation(pacman.x, pacman.y);
		// En el segundo ciclo creamos a Blinky.
		if (ciclo == 2) {
			anyadeBlinky();
		}
		// En el segundo ciclo 80 creamos a Pinky.
		if (ciclo == 80) {
			anyadePinky();
		}
		// En el segundo ciclo 160 creamos a Clyde.
		if (ciclo == 160) {
			anyadeClyde();
		}

		// Compruebo el tiempo transcurrido desde que se comi� al ghost y
		// east desapareci� del juego.
		if (blinky.getUltimaVezComido() != 0
				&& new Date().getTime() - blinky.getUltimaVezComido() > 5000) {
			// Pongo a 0 el valor de UltimaVezComido.
			blinky.setUltimaVezComido(0);
			// Pongo de nuevo el ghost en juego en su posici�n inicial
			blinky.setPlanoX(Ghost.planoXInicial);
			blinky.setPlanoY(Ghost.planoYInicial);
			// Pongo al ghost en visible.
			blinky.ghostLabel.setVisible(true);
			blinky.direction = GameCharacterGraphic.WEST;
			// Quito los labels de Blinky en casa.
			visualizarBlinkyenCasa();
		}
		// Idem que con blinky.
		if (clyde.getUltimaVezComido() != 0
				&& new Date().getTime() - clyde.getUltimaVezComido() > 5000) {

			clyde.setUltimaVezComido(0);
			clyde.setPlanoX(Ghost.planoXInicial);
			clyde.setPlanoY(Ghost.planoYInicial);
			clyde.ghostLabel.setVisible(true);
			clyde.direction = GameCharacterGraphic.WEST;
			visualizarClydeenCasa();
		}
		// Idem que con blinky.
		if (pinky.getUltimaVezComido() != 0
				&& new Date().getTime() - pinky.getUltimaVezComido() > 5000) {

			pinky.setUltimaVezComido(0);
			pinky.setPlanoX(Ghost.planoXInicial);
			pinky.setPlanoY(Ghost.planoYInicial);
			pinky.ghostLabel.setVisible(true);
			pinky.direction = GameCharacterGraphic.WEST;
			visualizarPinkyenCasa();
		}

		// Si esta blinky esta visible lo muevo y muestro su imagen.
		if (blinky.ghostLabel.isVisible() == true) {
			blinky.move();
			blinky.muestraGrafico();
		}
		// Si esta pinky esta visible lo muevo y muestro su imagen.
		if (pinky.ghostLabel.isVisible() == true) {
			pinky.move();
			pinky.muestraGrafico();
		}
		// Si esta clyde esta visible lo muevo y muestro su imagen.
		if (clyde.ghostLabel.isVisible() == true) {
			clyde.move();
			clyde.muestraGrafico();
		}

		// Procesamos colisiones del juego.
		procesarColisiones();
		// Contamos el cliclo.
		ciclo++;
	}

	/**
	 * M�todo que a�ade a Blinky al Panel de capas.
	 */
	private void anyadeBlinky() {
		// Lo a�adimos al JLayerPane.
		juegoPane.add(blinky.ghostLabel, 3, 1);
		// Le indicamos que esta en el tablero.
		blinky.setOnBoard(true);
		// Le ponemos posiciones iniciales.
		blinky.setPlanoX(Ghost.planoXInicial);
		blinky.setPlanoY(Ghost.planoYInicial);
		// Cuando sale Blinky de la casa ponemos invisible las JLABEL de Blinky
		// que hay en la casa.
		visualizarBlinkyenCasa();

	}

	/**
	 * M�todo que a�ade a Clyde al Panel de capas.
	 */
	private void anyadeClyde() {
		// Lo a�adimos al JLayerPane.
		juegoPane.add(clyde.ghostLabel, 3, 1);
		// Le indicamos que esta en el tablero.
		clyde.setOnBoard(true);
		// Le ponemos posiciones iniciales.
		clyde.setPlanoX(Ghost.planoXInicial);
		clyde.setPlanoY(Ghost.planoYInicial);
		// Cuando sale Clyde de la casa ponemos invisible las JLABEL de Clyde
		// que hay en la casa.
		visualizarClydeenCasa();

	}

	/**
	 * M�todo que a�ade a Pinky al Panel de capas.
	 */
	private void anyadePinky() {
		// Lo a�adimos al JLayerPane.
		juegoPane.add(pinky.ghostLabel, 3, 1);
		// Le indicamos que esta en el tablero.
		pinky.setOnBoard(true);
		// Le ponemos posiciones iniciales.
		pinky.setPlanoX(Ghost.planoXInicial);
		pinky.setPlanoY(Ghost.planoYInicial);
		// Cuando sale Pinky de la casa ponemos invisible las JLABEL de Pinky
		// que hay en la casa.
		visualizarPinkyenCasa();
	}

	/**
	 * 
	 * M�todo que se encarga de calcular la distancia entre 2 puntos.
	 * 
	 * @param int posiciones x1,x2,y1 y y2.
	 * @return Integer con la distancia entre los 2 puntos.
	 */

	private int calcularDistancia(int x1, int y1, int x2, int y2) {
		Double distancia = Math.sqrt(Math.pow(x2 - x1, 2)
				+ Math.pow(y2 - y1, 2));
		return distancia.intValue();
	}

	/**
	 * 
	 * M�todo que detecta las teclas de movimiento de pacman y pasusa el juego
	 * si le damos a la tecla "P".
	 * 
	 * @param KeyEvent Pulsaciones del teclado.
	 */
	public void keyPressed(KeyEvent ke) {
		int h = ke.getKeyCode();

		if (h == KeyEvent.VK_P && juegopausado == false) {
			timer.stop();
			juegopausado = true;
		} else {

			if (h == KeyEvent.VK_P && juegopausado == true) {
				timer.start();
				juegopausado = false;
			}
		}

		pacman.keyPressed(ke);
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void inicioJuego() {
		initJuegoPane();
	}

}
