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
 * This class creates the labyrinth, game characters and implements game logic
 */

public class GameScreenFrame extends JFrame implements KeyListener,
		ActionListener {
	
	private static final int REFRESH_RATE = 20;
	private Labyrinth labyrinth;
	
	private JLabel[][] labyrinthLabels = new JLabel[28][36];
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
	
	private JLayeredPane juegoPane;
	
	private int points;
	
	private boolean gamepaused;
	
	private ScoreBoard scoreBoard;
	
	private Integer cycle;
	
	private Integer lifes;
	
	private InitialScreenPanel initialScreenPanel;
	private CardLayout cardLayout;
	
	private Integer balls;

	/**
	 * Creates the Frame
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
	 * Inits the game
	 */

	private void initJuegoPane() {
		points = 0;
		cycle = 0;
		lifes = 1;
		balls = 244;

		if (juegoPane != null) {
			getContentPane().remove(juegoPane);
		}

		juegoPane = new JLayeredPane();

		getContentPane().add(juegoPane, "juegopane");

		labyrinth = PacmanGame.getInstace().getLaberinto();

		scoreBoard = new ScoreBoard(points);

		juegoPane.setPreferredSize(new Dimension(448, 614));
		juegoPane.setBounds(0, 0, 464, 614);

		pacman = new Pacman();
		blinky = new Blinky();
		pinky = new Pinky();
		clyde = new Clyde();

		gamepaused = false;

		PacmanGame.getInstace().setPacman(pacman);

		timer = new Timer(REFRESH_RATE, this);
		timer.start();

		createGraphicLabyrinth();

		cardLayout.show(getContentPane(), "juegopane");

	}

	/**
	 * Creates the graphic labyrinth
	 *
	 */

	private void createGraphicLabyrinth() {
		int a = labyrinth.getRowNumber();
		for (int i = 0; i < a; i++) {
			int k = labyrinth.getColumnNumber();

			for (int j = 0; j < k; j++) {
				labyrinthLabels[i][j] = new JLabel();
				labyrinthLabels[i][j]
						.setIcon(new ImageIcon(
								Ghost.class.getResource("/com/jamessoft/pacman/resources/LaberintoGIF/"
										+ labyrinth
												.getImageACell(i,
														j) + ".gif")));
				labyrinthLabels[i][j].setBounds(
						i * labyrinth.getImageWidth(), j
								* labyrinth.getImageHeight(),
						labyrinth.getImageWidth(),
						labyrinth.getImageHeight());
				labyrinthLabels[i][j].validate();

				juegoPane.add(labyrinthLabels[i][j], 3, 2);

			}

		}
		scoreBoard.setBounds(30, 15, 64, 16);
		juegoPane.add(scoreBoard, 3, 2);
		juegoPane.add(pacman.pacmanLabel, 3, 1);
	}

	/**
	 * Checks collisions between pacman, ghosts, and balls 
	 *
	 */
	private void checkCollisions() {
		Integer x = pacman.getPlanoX();
		Integer y = pacman.getPlanoY();
		Integer valor = labyrinth.getValueAt(x, y);
		if (valor == 1 && labyrinthLabels[x][y].isVisible() == true) {
			labyrinthLabels[x][y].setVisible(false);
			scoreBoard.updateScoreBoard(10);
			balls--;

		}
		if (valor == 2 && labyrinthLabels[x][y].isVisible() == true) {
			labyrinthLabels[x][y].setVisible(false);
			// laberintoVirtual.tipoCelda[x][y] = 0;
			PacmanGame.getInstace().activeSpecialState();
			balls--;
		}
		// Pantalla de fase superada.
		if (balls == 0) {
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

				ghost.setLastTimeEaten(new Date().getTime());
				// Lo metemos dentro de la casa
				ghost.setPlanoX(15);
				ghost.setPlanoY(17);
				// Ejecutamos metodos para ver o esconder la imagen del ghost
				// en casa.
				showPinkyHome();
				showBlinkyHome();
				showClydeHome();
				// Actualizo scoreBoard.
				scoreBoard.updateScoreBoard(100);
			} else {
				// En modo normal.
				if (lifes > 0) {
					lifes--;
					pacman.setPlanoX(13);
					pacman.setPlanoY(20);
					pacman.setDirection(GameCharacterGraphic.WEST);
					// Ponemos label de la vida en invisible.
					labyrinthLabels[2][34].setVisible(false);
					labyrinthLabels[2][35].setVisible(false);
					labyrinthLabels[3][34].setVisible(false);
					labyrinthLabels[3][35].setVisible(false);
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
			blinky.changeToOppositeDirection();
			clyde.changeToOppositeDirection();
		}
		if (blinky.getPlanoX() == pinky.getPlanoX()
				&& blinky.getPlanoY() == pinky.getPlanoY()
				&& blinky.direction != pinky.direction) {
			blinky.changeToOppositeDirection();
			pinky.changeToOppositeDirection();
		}
		if (pinky.getPlanoX() == clyde.getPlanoX()
				&& pinky.getPlanoY() == clyde.getPlanoY()
				&& pinky.direction != clyde.direction) {
			pinky.changeToOppositeDirection();
			clyde.changeToOppositeDirection();
		}
	}

	/**
	 * Show clyde at home
	 * 
	 */

	private void showClydeHome() {
		if ((clyde.ghostLabel.isVisible() == false)
				&& (clyde.isOnBoard() == true)) {
			labyrinthLabels[15][16].setVisible(true);
			labyrinthLabels[16][16].setVisible(true);
			labyrinthLabels[15][17].setVisible(true);
			labyrinthLabels[16][17].setVisible(true);
			labyrinthLabels[15][18].setVisible(true);
			labyrinthLabels[16][18].setVisible(true);
		}
		if ((clyde.ghostLabel.isVisible() == true)
				&& (clyde.isOnBoard() == true)) {
			labyrinthLabels[15][16].setVisible(false);
			labyrinthLabels[16][16].setVisible(false);
			labyrinthLabels[15][17].setVisible(false);
			labyrinthLabels[16][17].setVisible(false);
			labyrinthLabels[15][18].setVisible(false);
			labyrinthLabels[16][18].setVisible(false);
		}
		if ((clyde.ghostLabel.isVisible() == false)
				&& (clyde.isOnBoard() == false)) {
			labyrinthLabels[15][16].setVisible(true);
			labyrinthLabels[16][16].setVisible(true);
			labyrinthLabels[15][17].setVisible(true);
			labyrinthLabels[16][17].setVisible(true);
			labyrinthLabels[15][18].setVisible(true);
			labyrinthLabels[16][18].setVisible(true);
		}

	}

	/**
	 * 
	 * Show Blinky home
	 */
	private void showBlinkyHome() {
		if ((blinky.ghostLabel.isVisible() == false)
				&& (blinky.isOnBoard() == true)) {
			labyrinthLabels[11][16].setVisible(true);
			labyrinthLabels[12][16].setVisible(true);
			labyrinthLabels[11][17].setVisible(true);
			labyrinthLabels[12][17].setVisible(true);
			labyrinthLabels[11][18].setVisible(true);
			labyrinthLabels[12][18].setVisible(true);
		}
		if ((blinky.ghostLabel.isVisible() == true)
				&& (blinky.isOnBoard() == true)) {
			labyrinthLabels[11][16].setVisible(false);
			labyrinthLabels[12][16].setVisible(false);
			labyrinthLabels[11][17].setVisible(false);
			labyrinthLabels[12][17].setVisible(false);
			labyrinthLabels[11][18].setVisible(false);
			labyrinthLabels[12][18].setVisible(false);
		}
		if ((blinky.ghostLabel.isVisible() == false)
				&& (blinky.isOnBoard() == false)) {
			labyrinthLabels[11][16].setVisible(true);
			labyrinthLabels[12][16].setVisible(true);
			labyrinthLabels[11][17].setVisible(true);
			labyrinthLabels[12][17].setVisible(true);
			labyrinthLabels[11][18].setVisible(true);
			labyrinthLabels[12][18].setVisible(true);
		}

	}

	/**
	 * 
	 * Show Pinky home
	 */

	private void showPinkyHome() {
		if (pinky.ghostLabel.isVisible() == false
				&& (pinky.isOnBoard() == true)) {
			labyrinthLabels[13][16].setVisible(true);
			labyrinthLabels[14][16].setVisible(true);
			labyrinthLabels[13][17].setVisible(true);
			labyrinthLabels[14][17].setVisible(true);
			labyrinthLabels[13][18].setVisible(true);
			labyrinthLabels[14][18].setVisible(true);
		}
		if ((pinky.ghostLabel.isVisible() == true)
				&& (pinky.isOnBoard() == true)) {
			labyrinthLabels[13][16].setVisible(false);
			labyrinthLabels[14][16].setVisible(false);
			labyrinthLabels[13][17].setVisible(false);
			labyrinthLabels[14][17].setVisible(false);
			labyrinthLabels[13][18].setVisible(false);
			labyrinthLabels[14][18].setVisible(false);
		}
		if ((pinky.ghostLabel.isVisible() == false)
				&& (pinky.isOnBoard() == false)) {
			labyrinthLabels[13][16].setVisible(true);
			labyrinthLabels[14][16].setVisible(true);
			labyrinthLabels[13][17].setVisible(true);
			labyrinthLabels[14][17].setVisible(true);
			labyrinthLabels[13][18].setVisible(true);
			labyrinthLabels[14][18].setVisible(true);
		}
	}

	/**
	 * 
	 * Checks if there is a ghost in the specified coordinates
	 * 
	 * @param Integer x,y coordinate
	 *           
	 * @return blinky, pinky or clyde.
	 */
	public Ghost getGhostEnCoordenadas(Integer x, Integer y) {
		if (checkDistance(x, y, blinky.getX(), blinky.getY()) < 16) {
			return blinky;
		}
		if (checkDistance(x, y, pinky.getX(), pinky.getY()) < 16) {
			return pinky;
		}
		if (checkDistance(x, y, clyde.getX(), clyde.getY()) < 16) {
			return clyde;
		}
		return null;
	}

	/**
	 * 
	 * returns pacman
	 * 
	 * @return  pacman.
	 */

	public Pacman getPacman() {
		return pacman;
	}

	/**
	 * 
	 * Timer listnere. executed every cycle of the timer
	 * 
	 * @param ActionEvent
	 */

	public void actionPerformed(ActionEvent ke) {
		// Movemos a pacman.
		pacman.move();
		// Actualizamos el scoreBoard.
		scoreBoard.updateScoreBoard(points);
		// Actualizamos posici�n label de pacman.
		pacman.pacmanLabel.setLocation(pacman.x, pacman.y);
		// En el segundo ciclo creamos a Blinky.
		if (cycle == 2) {
			anyadeBlinky();
		}
		// En el segundo ciclo 80 creamos a Pinky.
		if (cycle == 80) {
			anyadePinky();
		}
		// En el segundo ciclo 160 creamos a Clyde.
		if (cycle == 160) {
			anyadeClyde();
		}

		// Compruebo el tiempo transcurrido desde que se comi� al ghost y
		// east desapareci� del juego.
		if (blinky.getLastTimeEaten()!= 0
				&& new Date().getTime() - blinky.getLastTimeEaten()> 5000) {
			// Pongo a 0 el valor de UltimaVezComido.
			blinky.setLastTimeEaten(0);
			// Pongo de nuevo el ghost en juego en su posici�n inicial
			blinky.setPlanoX(Ghost.initiaPlaneX);
			blinky.setPlanoY(Ghost.initialPlaneY);
			// Pongo al ghost en visible.
			blinky.ghostLabel.setVisible(true);
			blinky.direction = GameCharacterGraphic.WEST;
			// Quito los labels de Blinky en casa.
			showBlinkyHome();
		}
		// Idem que con blinky.
		if (clyde.getLastTimeEaten()!= 0
				&& new Date().getTime() - clyde.getLastTimeEaten()> 5000) {

			clyde.setLastTimeEaten(0);
			clyde.setPlanoX(Ghost.initiaPlaneX);
			clyde.setPlanoY(Ghost.initialPlaneY);
			clyde.ghostLabel.setVisible(true);
			clyde.direction = GameCharacterGraphic.WEST;
			showClydeHome();
		}
		// Idem que con blinky.
		if (pinky.getLastTimeEaten()!= 0
				&& new Date().getTime() - pinky.getLastTimeEaten()> 5000) {

			pinky.setLastTimeEaten(0);
			pinky.setPlanoX(Ghost.initiaPlaneX);
			pinky.setPlanoY(Ghost.initialPlaneY);
			pinky.ghostLabel.setVisible(true);
			pinky.direction = GameCharacterGraphic.WEST;
			showPinkyHome();
		}

		// Si esta blinky esta visible lo muevo y muestro su imagen.
		if (blinky.ghostLabel.isVisible() == true) {
			blinky.move();
			blinky.showGraphic();
		}
		// Si esta pinky esta visible lo muevo y muestro su imagen.
		if (pinky.ghostLabel.isVisible() == true) {
			pinky.move();
			pinky.showGraphic();
		}
		// Si esta clyde esta visible lo muevo y muestro su imagen.
		if (clyde.ghostLabel.isVisible() == true) {
			clyde.move();
			clyde.showGraphic();
		}

		// Procesamos colisiones del juego.
		checkCollisions();
		// Contamos el cliclo.
		cycle++;
	}

	/**
	 * puts Blinky on the board
	 */
	private void anyadeBlinky() {
		// Lo a�adimos al JLayerPane.
		juegoPane.add(blinky.ghostLabel, 3, 1);
		// Le indicamos que esta en el tablero.
		blinky.setOnBoard(true);
		// Le ponemos posiciones iniciales.
		blinky.setPlanoX(Ghost.initiaPlaneX);
		blinky.setPlanoY(Ghost.initialPlaneY);
		// Cuando sale Blinky de la casa ponemos invisible las JLABEL de Blinky
		// que hay en la casa.
		showBlinkyHome();

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
		clyde.setPlanoX(Ghost.initiaPlaneX);
		clyde.setPlanoY(Ghost.initialPlaneY);
		// Cuando sale Clyde de la casa ponemos invisible las JLABEL de Clyde
		// que hay en la casa.
		showClydeHome();

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
		pinky.setPlanoX(Ghost.initiaPlaneX);
		pinky.setPlanoY(Ghost.initialPlaneY);
		// Cuando sale Pinky de la casa ponemos invisible las JLABEL de Pinky
		// que hay en la casa.
		showPinkyHome();
	}

	/**
	 * 
	 * M�todo que se encarga de calcular la distancia entre 2 puntos.
	 * 
	 * @param int posiciones x1,x2,y1 y y2.
	 * @return Integer con la distancia entre los 2 puntos.
	 */

	private int checkDistance(int x1, int y1, int x2, int y2) {
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

		if (h == KeyEvent.VK_P && gamepaused == false) {
			timer.stop();
			gamepaused = true;
		} else {

			if (h == KeyEvent.VK_P && gamepaused == true) {
				timer.start();
				gamepaused = false;
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
