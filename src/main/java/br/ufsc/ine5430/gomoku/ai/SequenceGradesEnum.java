package br.ufsc.ine5430.gomoku.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sequências de peças possíveis com suas respectivas pontuações
 */
@AllArgsConstructor
@Getter
public enum SequenceGradesEnum {

	/**
	 * Sem sequência
	 */
	ZERO(0, 0),
	/**
	 * Uma peça
	 */
	UNITARIO(1, 1),
	/**
	 * Duas peças
	 */
	DUPLA(2, 114),
	/**
	 * Três peças
	 */
	TRIPLA(3, 6385),
	/**
	 * Quatro peças
	 */
	QUADRUPLA(4, 236246),
	/**
	 * Cinco peças. Possui a maior nota possível, já que representa o fim do jogo.
	 */
	QUINTUPLA(5, Integer.MAX_VALUE);

	/**
	 * Inteiro que representa o índice utilizado para identificar qual é a sequência.
	 * Propositalmente o índice é igual a quantidade de peças necessárias para formar a sequência.
	 */
	private int index;
	/**
	 * Inteiro que representa a nota dada para a sequência
	 */
	private int grade;

	/**
	 * Função que busca uma sequência a partir de um índice
	 *
	 * @param index que representa a sequência
	 * @return a sequência associada ao indíce, ou, null caso seja um índice inválido
	 */
	private static SequenceGradesEnum getByIndex(int index) {
		for (SequenceGradesEnum grade : SequenceGradesEnum.values()) {
			if (grade.getIndex() == index) {
				return grade;
			}
		}
		return null;
	}

	/**
	 * A partir de um índice de uma sequência calcula a sua nota.
	 * Se por algum efeito colateral acontecer de ser uma sequência com mais de 5 peças,
	 * ela deve representar a vitória e por isso tem o valor do seu índice alterado para 5.
	 *
	 * @param sequence inteiro que representa o índice de uma sequência
	 * @return nota que a sequência possui
	 * @see #getByIndex(int)
	 */
	public static int calculate(int sequence) {
		int score = 0;

		if (sequence > 5) {
			sequence = 5;
		}

		score += SequenceGradesEnum.getByIndex(sequence).getGrade();

		return score;
	}
}
