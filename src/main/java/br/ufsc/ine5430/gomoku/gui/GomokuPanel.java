package br.ufsc.ine5430.gomoku.gui;
import javax.swing.JPanel;

public class GomokuPanel extends JPanel {
	private final int MARGIN = 5;
	private final double PIECE_FRAC = 0.9;

	private int size = 15;
	// FIXME add import
	// private GomokuState state;

	public GomokuPanel() {
		this(15);
	}

	public GomokuPanel(int size) {
		super();
		this.size = size;
		// FIXME add construtor
		// state = new GomokuState(size);
		// FIXME add import
		// addMouseListener(new GomokuListener());
	}
}
