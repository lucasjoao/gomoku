package br.ufsc.ine5430.gomoku.grafo;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class Vertice {

	private Set<Vertice> adjacentes = new HashSet<Vertice>();
	private Object chave;

	public Vertice(Object chave) {
		this.chave = chave;
	}

	/**
	 * (Void) Remove todos os vertices adjacentes de um vértice
	 */
	public void removerAdjacentes() {
		for (Vertice vertice : adjacentes) {
			vertice.removerAdjacente(this);
		}
		adjacentes.clear();
	}

	/**
	 * (Void) Remove o vértice adjacente passado como parâmetro
	 * @param vertice
	 */
	public void removerAdjacente(Vertice vertice) {
		adjacentes.remove(vertice);
	}

	/**
	 * Verifica se um determinado vértice passado como parâmetro é adjacente a este vértice
	 * @param vertice
	 * @return true se for adjacente, false se não
	 */
	public boolean contemAdjacente(Vertice vertice) {
		return adjacentes.contains(vertice);
	}

	/**
	 * Adiciona um vértice adjacente ao vértice
	 * @param vertice
	 */
	public void adicionaAdjacente(Vertice vertice) {
		adjacentes.add(vertice);
	}

	/**
	 * O grau de um vértice indica a quantos outros vértices este está ligado por meio de arestas
	 * @return o grau do vértice (int)
	 */
	public int obterGrau() {
		return adjacentes.size();
	}
}