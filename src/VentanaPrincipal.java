import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Andres Caballero
 *Esta clase es la ventana principal en la que inicializan 
 *todos los componentes del buscaminas
 */

public class VentanaPrincipal {

	JOptionPane panelFinal;
	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar despues los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotonEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Metodo que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
			}
		}
		botonEmpezar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.remove(panelEmpezar);
				ventana.remove(panelImagen);
				ventana.remove(panelJuego);
				ventana.remove(panelPuntuacion);
				juego = new ControlJuego();
				inicializar();
				refrescarPantalla();
			}
		});
	}

	/**
	 * Pinta en la pantalla el numero de minas que hay alrededor de la celda Saca el
	 * boton que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el numero de minas alrededor. Se pinta el color del texto segun
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 o más : rojo
	 * 
	 * @param i: posicion vertical de la celda.
	 * @param j: posicion horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		int mina = juego.getMinasAlrededor(i, j);
		for (int k = 0; k < correspondenciaColores.length; k++) {
			if (mina == k) {
				panelesJuego[i][j].remove(botonesJuego[i][j]);
				JLabel numeroMinas = new JLabel("" + mina);
				numeroMinas.setHorizontalAlignment(SwingConstants.CENTER);
				numeroMinas.setForeground(correspondenciaColores[k]);
				panelesJuego[i][j].add(numeroMinas);
				refrescarPantalla();
			}
		}
	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		panelFinal = new JOptionPane();
		if (porExplosion == true) {
			JOptionPane.showMessageDialog(null, "Has explotado una bomba!"); // Asi se usa el JOptionPane
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j].setEnabled(false);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Has ganado, �Felicidades!");
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j].setEnabled(false);
				}
			}
		}
	}

	/**
	 * Metodo que muestra la puntuacion por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(Integer.toString(juego.getPuntuacion()));
	}

	/**
	 * Metodo para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Metodo que devuelve el control del juego de una ventana
	 * 
	 * @return un Contro                                                                                                                                                                                                                            lJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Metodo para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
