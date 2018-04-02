package br.ufsc.ine5430.gomoku.gui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Posicao {

	private int line;
	private int column;

	public Posicao(int line, int column) {
		this.line = line;
		this.column = column;
	}

}
