package br.ufsc.ine5430.gomoku;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.gui.PositionValidatorGui;

public class App {

	private static final int PC_START = 1;
	private static final int HUMAN_START = 2;

	public static void main(String[] args) {

		GuiGomoku guiHandler = new GuiGomoku();

		guiHandler.printaMap();
		int whoStart = PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Digite o número de quem irá começar o jogo: \n 1 - pc \n 2 - você"));

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

	private static boolean humanPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor;
		do {
			int linha = guiHandler.pecaLinha();
			if (linha == Integer.MIN_VALUE) return false;
			int coluna = guiHandler.pecaColuna();
			if (coluna == Integer.MIN_VALUE) return false;
			valor = (linha - 1) * 15 + coluna;
		} while (PositionValidatorGui.check(tabuleiro, valor) == false);

		tabuleiro.put(valor, "o");
		guiHandler.printaMap();
		return true;
	}

	// TODO: associar com ia
	private static void pcPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor = -1;
		//		while (PositionValidatorGui.check(tabuleiro, valor) == false) {
		int linha = (int) (Math.random() * 15 + 1);
		int coluna = (int) (Math.random() * 15 + 1);
		valor = (linha - 1) * 15 + coluna;
		//		}
		System.out.println(valor);
		System.out.println("Quantidade de iterações necessárias para a jogada:"); // TODO: pegar do minimax
		tabuleiro.put(valor, "x");
		guiHandler.printaMap();
	}

}
