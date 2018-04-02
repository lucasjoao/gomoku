package br.ufsc.ine5430.gomoku;

import java.util.Map;

import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.gui.PositionValidatorGui;

public class App {

	public static void main(String[] args) {

		GuiGomoku guiHandler = new GuiGomoku();

		guiHandler.printaMap();

		while (true) {
			jogadorHumano(guiHandler);
			jogadorAutomatico(guiHandler);
		}

	}

	private static void jogadorHumano(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor = -1;
		while (new PositionValidatorGui().check(tabuleiro, valor) == false) {
			int linha = guiHandler.pecaLinha();
			int coluna = guiHandler.pecaColuna();
			valor = (linha - 1) * 15 + coluna;
		}
		tabuleiro.put(valor, "o");
		guiHandler.printaMap();
	}

	private static void jogadorAutomatico(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor = -1;
		while (new PositionValidatorGui().check(tabuleiro, valor) == false) {
			int linha = (int) (Math.random() * 15 + 1);
			int coluna = (int) (Math.random() * 15 + 1);
			valor = (linha - 1) * 15 + coluna;
		}
		System.out.println(valor);
		tabuleiro.put(valor, "x");
		guiHandler.printaMap();
	}

}
