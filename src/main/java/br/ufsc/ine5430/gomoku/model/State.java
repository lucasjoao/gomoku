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
	private int[] lastMove; // TODO: sera que isso vai cair lo no miniMax, to achando que sim, na verdade, acho que sera preciso duplicar isso la
	private static int id;

	public State() {
		super(id);
		this.board = new HashMap<Integer, Position>();
		this.initializePieces();
		id++;
	}

	public State(Map<Integer, Position> board, int[] lastMove) {
		super(id);
		this.board = board;
		this.lastMove = lastMove;
	}

	private void initializePieces() {
		Position position = new Position();
		// TODO: deixar generico em um utils?
		for (int i = 1; i < 225; i++) {
			this.board.put(i, position);
		}
	}
}
