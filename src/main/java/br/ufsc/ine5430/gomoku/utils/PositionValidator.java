package br.ufsc.ine5430.gomoku.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.ufsc.ine5430.gomoku.model.FormError;

/**
 * Classe de validação para verificar se o número passado como linha e coluna
 * são vålidos
 */
public class PositionValidator {

	public static boolean check(int rowOrCol) {
		return PositionValidator.check(rowOrCol, rowOrCol);
	}

	public static boolean check(int row, int col) {
		int max = 16;
		int min = 0;
		return max > row && row > min && max > col && col > min;
	}

	/**
	 * O tabuleiro é composto de 225 casas, cada qual com sua chave e valor. A
	 * chave é o ID sequencial e único. O valor é o que está dentro da casa: -
	 * (vazio); x (humano) ou o (máquina). A validação contempla valores OK para
	 * linha e coluna, bem como avisa caso uma casa já fora ocupada
	 * anteriormente.
	 *
	 * @param tabuleiro
	 * @param valor
	 * @return true se nenhum erro for encontrado ou false caso contrário
	 *         (boolean)
	 */
	public static FormError check(Map<Integer, String> tabuleiro, int valor) {
		FormError errors = new FormError();
		if (valor < 1 || valor > 225) {
			List<String> listaDeErros = new ArrayList<>();
			listaDeErros.add("Valor inválido! Escolha outro.");
			errors.setErrors(listaDeErros);
			return errors;

		} else if (!tabuleiro.get(valor).contains("-")) {

			List<String> listaDeErros = new ArrayList<>();
			listaDeErros.add("A casinha escolhida já está preenchida por uma peça! Escolha outra.");
			errors.setErrors(listaDeErros);
			return errors;

		} else {

			return errors;

		}
	}

	/**
	 * Verifica se a determinada String passada como parâmetro é um numeral válido
	 *
	 * @param input
	 * @return o valor correspondente por essa String, caso seja um numeral válido; ou
	 *         o menor valor Integer existente, caso contrário (int)
	 */
	public static int checkInput(String input) {
		if (StringUtils.isNumeric(input)) {
			Integer result = Integer.valueOf(input);
			return PositionValidator.check(result) ? result : Integer.MIN_VALUE;
		}

		return Integer.MIN_VALUE;
	}
}
