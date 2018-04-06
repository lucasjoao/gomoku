package br.ufsc.ine5430.gomoku.utils;

/**
 * Classe destinada para centralizar funções auxiliares que são utilizadas em outras partes do projeto
 */
public class GomokuUtils {

	/**
	 * Função que calcula através de uma fórmula matemática a posição em que se encontra determinado item de uma matriz 15 x 15 em um HashMap.
	 * Considera que a adição no HashMap foi feita linha por linha. Ou seja, primeiro foi adicionado a primeira linha, depois a segunda e assim por diante.
	 *
	 * @param row o número da linha na matriz 15 x 15
	 * @param col o número da coluna na matriz 15 x 15
	 * @return inteiro que indica a posição que o elemento da [row, col] da matriz 15 x 15 está no HashMap
	 */
	public static int posInMap(int row, int col) {
		return (row - 1) * 15 + col;
	}

}
