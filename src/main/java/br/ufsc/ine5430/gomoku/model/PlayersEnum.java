package br.ufsc.ine5430.gomoku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Jogadores possíveis do Gomoku
 */
@Getter
@AllArgsConstructor
public enum PlayersEnum {
	/**
	 * Jogador da inteligência artificial
	 */
	PC(1, "PC"),
	/**
	 * Jogador humano
	 */
	HUMAN(2, "VOCÊ");

	/**
	 * Inteiro que representa o índice utilizado para identificar qual é o jogador
	 */
	private int index;
	/**
	 * String que possui o nome do jogador
	 */
	private String text;
}
