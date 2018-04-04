package br.ufsc.ine5430.gomoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayersEnum {
	PC(1, "PC"),
	HUMAN(2, "VOCÊ");

	private int index;
	private String text;
}
