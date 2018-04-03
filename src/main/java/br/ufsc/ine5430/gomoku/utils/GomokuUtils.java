package br.ufsc.ine5430.gomoku.utils;

public class GomokuUtils {

	public static int posInMap(int row, int col) {
		return (row - 1) * 15 + col;
	}

}
