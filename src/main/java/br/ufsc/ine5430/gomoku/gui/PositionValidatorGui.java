package br.ufsc.ine5430.gomoku.gui;

import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

public class PositionValidatorGui {

	public static boolean check(Map<Integer, String> tabuleiro, int valor) {
		if (valor < 1 || valor > 225) {
			JOptionPane.showMessageDialog(null, "Valor inválido! Escolha outro.");
			return false;
		} else if (!tabuleiro.get(valor).contains("-")) {
			JOptionPane.showMessageDialog(null, "A casinha escolhida já está preenchida por uma peça! Escolha outra.");
			return false;

		} else {
			return true;

		}
	}

	public static int checkInput(String input) {
		return StringUtils.isNumeric(input) ? Integer.valueOf(input) : Integer.MIN_VALUE;
	}

	//TODO: botar isso em um Utils?
	public static int posInMap(int row, int col) {
		return (row - 1) * 15 + col;
	}


}
