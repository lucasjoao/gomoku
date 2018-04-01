package br.ufsc.ine5430.gomoku.model;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5430.gomoku.grafo.Vertice;
import lombok.Getter;

@Getter
public class State extends Vertice {

	private List<List<Position>> pieces; // TODO: documentar {row, col}
	private int[] lastMove; // TODO: sera que isso vai cair lo no miniMax, to achando que sim, na verdade, acho que sera preciso duplicar isso la
	private static int id;

	public State() {
		super(id);
		this.pieces = new ArrayList<List<Position>>();
		id++;
	}

	// TODO: precisa inicializar o pieces?
	// TODO: fazer a parada rodar sem podas, para ter uma ideia
	// TODO: assign lastMove
}
