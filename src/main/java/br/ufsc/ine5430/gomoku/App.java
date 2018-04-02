package br.ufsc.ine5430.gomoku;

import java.util.Map;

import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.gui.PositionValidatorGui;

public class App {

	public static void main(String[] args) {

		GuiGomoku guiHandler = new GuiGomoku();

		guiHandler.printaMap();

		boolean wannaPlay = true;
		while (wannaPlay) {
			wannaPlay = humanPlayer(guiHandler);
			if (!wannaPlay) {
				System.out.println("Jogo finalizado!");
				break;
			}
			pcPlayer(guiHandler);
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

	private static void pcPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor = -1;
		//		while (PositionValidatorGui.check(tabuleiro, valor) == false) {
		int linha = (int) (Math.random() * 15 + 1);
		int coluna = (int) (Math.random() * 15 + 1);
		valor = (linha - 1) * 15 + coluna;
		//		}
		System.out.println(valor);
		tabuleiro.put(valor, "x");
		guiHandler.printaMap();
	}

}
