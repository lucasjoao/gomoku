package br.ufsc.ine5430.gomoku.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma posição no tabuleiro do Gomoku
 */
@Setter
@Getter
public class Position {

	/**
	 * boolean que indica se a posição está vazia ou não
	 */
	private boolean empty;
	/**
	 * enum que indica qual jogador ocupa a posição
	 *
	 * @see PlayersEnum
	 */
	private PlayersEnum player;

	/**
	 * Construtor que cria uma posição vazia
	 */
	public Position() {
		this.empty = true;
	}

	/**
	 * Construtor que cria uma posição não vazia que possui o jogador recebido como parâmetro
	 *
	 * @param player que ocupará a posição
	 */
	public Position(PlayersEnum player) {
		this.player = player;
		this.empty = false;
	}
}
