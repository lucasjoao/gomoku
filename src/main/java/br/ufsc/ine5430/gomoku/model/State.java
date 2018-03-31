package br.ufsc.ine5430.gomoku.model;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5430.gomoku.grafo.Vertice;

public class State extends Vertice {

	private List<List<Position>> pieces;
	private static int id;

	public State() {
		super(id);
		this.pieces = new ArrayList<List<Position>>();
		id++;
	}
}
