package br.ufsc.ine5430.gomoku.ai;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Regiões possíveis do tabuleiro com suas respectivas pontuações
 */
@AllArgsConstructor
@Getter
public enum RegionGradesEnum {

	/**
	 * Região 5 x 5 superior esquerda, superior direita, inferior esquerda e inferior direita
	 */
	CANTO(10),
	/**
	 * Região 5 x 5 superior central, lateral esquerda central, lateral direita central e inferior central
	 */
	LATERAL(41),
	/**
	 * Região 5 x 5 no centro do tabuleiro
	 */
	CENTRO(165);

	/**
	 * Inteiro que representa a nota dada para o tabuleiro
	 */
	private int grade;

	/**
	 * Calcula a nota dos extremos de uma sequência. Cada extremidade recebe uma pontuação conforme a sua posição no tabuleiro
	 *
	 * @param extremes uma lista de coordenadas (array de inteiros) com os extremos de uma sequência
	 * @return a nota inteira que os extremos da sequência possui
	 * @see #getScoreByRegion(int[])
	 */
	public static int calculate(List<int[]> extremes) {
		int score = 0;
		for (int[] extreme : extremes) {
			score += RegionGradesEnum.getScoreByRegion(extreme);
		}
		return score;
	}

	/**
	 * Verifica em qual região do tabuleiro um determinado extremo se encontra, para assim determinar a sua nota
	 *
	 * @param extreme coordenadas (uma lista de inteiro com dois itens, linha e coluna, respectivamente)
	 * @return a nota que a posição possui conforme a sua posição no tabuleiro
	 */
	private static int getScoreByRegion(int[] extreme) {
		int score = 0;
		int row = extreme[0];
		int col = extreme[1];
		if (4 < row && row < 10) {
			if (4 < col && col < 10) {
				score += RegionGradesEnum.CENTRO.getGrade();
			} else {
				score += RegionGradesEnum.LATERAL.getGrade();
			}
		} else {
			if (4 < col && col < 10) {
				score += RegionGradesEnum.LATERAL.getGrade();
			} else {
				score += RegionGradesEnum.CANTO.getGrade();
			}
		}
		return score;
	}
}
