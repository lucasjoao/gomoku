package br.ufsc.ine5430.gomoku;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufsc.ine5430.gomoku.ai.MiniMax;
import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.gui.PositionValidatorGui;
import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;

public class App {

	private static final int PC_START = 1;
	private static final int HUMAN_START = 2;

	private static State lastState;

	public static void main(String[] args) {

		GuiGomoku guiHandler = new GuiGomoku();

		guiHandler.printaMap();
		int whoStart = PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Digite o número de quem irá começar o jogo: \n 1 - pc \n 2 - você"));

		lastState = new State();

		boolean wannaPlay = true;
		while (wannaPlay) {
			if (PC_START == whoStart) {
				pcPlayer(guiHandler);
				wannaPlay = humanPlayer(guiHandler);
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
			} else if (HUMAN_START == whoStart) {
				wannaPlay = humanPlayer(guiHandler);
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
				pcPlayer(guiHandler);
			} else {
				JOptionPane.showMessageDialog(null, "Não brinque! Opção inválida, jogo finalizado.");
				break;
			}
		}
	}

	// TODO: deixar em outro lugar
	public static boolean humanPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor, linha, coluna;
		do {
			linha = guiHandler.pecaLinha();
			if (linha == Integer.MIN_VALUE) return false;
			coluna = guiHandler.pecaColuna();
			if (coluna == Integer.MIN_VALUE) return false;
			valor = PositionValidatorGui.posInMap(linha, coluna);
		} while (!PositionValidatorGui.check(tabuleiro, valor));

		lastState.getBoard().put(valor, new Position(PlayersEnum.HUMAN));
		lastState = new State(lastState.getBoard(), new int[] {linha, coluna});

		tabuleiro.put(valor, "o");
		guiHandler.printaMap();
		return true;
	}

	// TODO: deixar em outro lugar
	public static void pcPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor, linha, coluna;

		do {
			MiniMax miniMax = new MiniMax(lastState, PlayersEnum.PC);
			int[] cord = miniMax.move();
			linha = cord[0];
			coluna = cord[1];
			valor = PositionValidatorGui.posInMap(linha, coluna);
		} while (!PositionValidatorGui.check(tabuleiro, valor));

		lastState.getBoard().put(valor, new Position(PlayersEnum.PC));
		lastState = new State(lastState.getBoard(), new int[] {linha, coluna});

		System.out.println("Quantidade de iterações necessárias para a jogada:"); // TODO: pegar do minimax
		tabuleiro.put(valor, "x");
		guiHandler.printaMap();
	}
}
