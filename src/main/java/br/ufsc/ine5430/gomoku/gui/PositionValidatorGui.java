package br.ufsc.ine5430.gomoku.gui;

import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe de validação para verificar se o número passado como linha e coluna são vålidos
 */
public class PositionValidatorGui {

	/**
	 * O tabuleiro é composto de 225 casas, cada qual com sua chave e valor.
	 * A chave é o ID sequencial e único.
	 * O valor é o que está dentro da casa: - (vazio); x (humano) ou o (máquina).
	 * A validação contempla valores OK para linha e coluna, bem como avisa caso uma casa já fora ocupada anteriormente.
	 * @param tabuleiro
	 * @param valor
	 * @return true se nenhum erro for encontrado ou false caso contrário (boolean)
	 */
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

	/**
	 * Verifica se a determinada String passada como parâmetro é um numeral
	 * @param input
	 * @return o valor correspondente por essa String, caso seja um numeral; ou o menor valor Integer existente, caso contrário (int)
	 */
	public static int checkInput(String input) {
		return StringUtils.isNumeric(input) ? Integer.valueOf(input) : Integer.MIN_VALUE;
	}

	//TODO: botar isso em um Utils?
	public static int posInMap(int row, int col) {
		return (row - 1) * 15 + col;
	}


}
