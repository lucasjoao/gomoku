package br.ufsc.ine5430.gomoku.gui;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <code>Gomoku</code> - a simple GUI for playing basic Gomoku. See
 * http://en.wikipedia.org/wiki/Gomoku
 *
 * Usage: java Gomoku [<grid size>]
 * 
 * @author Todd W. Neller
 * @version 1.0
 */
public class Gomoku {
	/**
	 * <code>main</code> - Create the JFrame and start a new embedded Gomoku
	 * game. The grid size is 19 by default, but may be supplied in the first
	 * command line argument.
	 *
	 * @param args
	 *            a <code>String[]</code> value - command line arguments
	 */
	public static void main(String[] args) {

		int size = 19;
		if (args.length > 0)
			size = Integer.parseInt(args[0]);

		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 600;
		final int FRAME_HEIGHT = 650;
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Gomoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		GomokuPanel panel = new GomokuPanel(size);
		JPanel panel = new JPanel();
		frame.add(panel);

		frame.setVisible(true);
	}
}
