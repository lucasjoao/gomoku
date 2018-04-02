package br.ufsc.ine5430.gomoku.gui;

import java.util.Map;

import javax.swing.JOptionPane;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PositionValidatorGui {

	public boolean check(Map<Integer, String> tabuleiro, int valor) {
		if (valor < 1 || valor > 225) {
			return false;

		} else if (!tabuleiro.get(valor).contains("-")) {
			JOptionPane.showMessageDialog(null, "A casinha escolhida já está preenchida por uma peça! Escolha outra.");
			return false;

		} else {
			return true;

		}
	}
}
