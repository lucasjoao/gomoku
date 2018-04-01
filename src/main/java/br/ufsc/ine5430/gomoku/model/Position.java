package br.ufsc.ine5430.gomoku.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Position {

	private boolean empty;
	private PlayersEnum player;

	public Position() {
		this.empty = true;
	}
}
