import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendra que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author andrescaballero
 **
 */
public class ActionBoton implements ActionListener{
	VentanaPrincipal ventanaPrincipal;
	int x;
	int y;
	
	
	public ActionBoton(VentanaPrincipal ventanaPrincipal, int fila, int columna) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.x = x;
		this.y = y;
	}

	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ventanaPrincipal.juego.abrirCasilla(x, y)) {
			ventanaPrincipal.mostrarNumMinasAlrededor(x, y);
			ventanaPrincipal.refrescarPantalla();
			ventanaPrincipal.actualizarPuntuacion();
			
		}else {
			if (ventanaPrincipal.juego.esFinJuego() == false) {
				ventanaPrincipal.mostrarFinJuego(true);
			}else {
				ventanaPrincipal.mostrarFinJuego(false);
			}
		}
	}

}
