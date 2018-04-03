package br.ufsc.ine5430.gomoku.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import br.ufsc.ine5430.gomoku.grafo.Vertice;

@Getter
@Setter
public class State extends Vertice {

	private Map<Integer, Position> board;
	private int[] lastMove;
	private static int id;

	public State() {
		super(id);
		this.board = new HashMap<Integer, Position>();
		this.initializePieces();
		this.lastMove = new int[] {7, 7}; // XXX: refatorar e documentar que se computador começa ele busca jogadas a partir do centro --> verificar se da de fazer isso mais bonito
		id++;
	}

	public State(Map<Integer, Position> board, int[] lastMove) {
		super(id);
		this.board = board;
		this.lastMove = lastMove;
	}

	private void initializePieces() {
		Position position = new Position();
		// XXX: refatorar, deixar generico em um utils?
		for (int i = 1; i < 225; i++) {
			this.board.put(i, position);
		}
	}
}
