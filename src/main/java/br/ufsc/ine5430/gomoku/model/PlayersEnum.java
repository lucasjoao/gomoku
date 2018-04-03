package br.ufsc.ine5430.gomoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayersEnum {
	PC("PC"),
	HUMAN("VOCÊ");

	private String text;
}
