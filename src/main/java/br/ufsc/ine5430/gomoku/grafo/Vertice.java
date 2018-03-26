package br.ufsc.ine5430.gomoku.grafo;

import java.util.HashSet;
import java.util.Set;

public class Vertice {

	private Set<Vertice> adjacentes = new HashSet<Vertice>();
	private Object chave;

	public Vertice(Object chave) {
		this.chave = chave;
	}

	public Object getChave() {
		return chave;
	}

	public void removerAdjacentes() {
		for (Vertice vertice : adjacentes) {
			vertice.removerAdjacente(this);
		}
		adjacentes.clear();
	}

	public void removerAdjacente(Vertice vertice) {
		adjacentes.remove(vertice);
	}

	public Set<Vertice> getAdjacentes() {
		return adjacentes;
	}

	public boolean contemAdjacente(Vertice vertice) {
		return adjacentes.contains(vertice);
	}

	public void adicionaAdjacente(Vertice vertice) {
		adjacentes.add(vertice);
	}

	public int obterGrau() {
		return adjacentes.size();
	}
}