package br.ufsc.ine5430.gomoku.grafo;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Grafo {

	private Map<Object, Vertice> vertices = new HashMap<Object, Vertice>();

	/**
	 * Cria um vértice no grafo
	 * @param chaveVertice
	 * @throws Exception
	 */
	public void criarVertice(Object chaveVertice) throws Exception {

		if (chaveVertice == null) {
			throw new Exception("o valor chaveVertice está nulo");
		}

		if (vertices.containsKey(chaveVertice)) {
			throw new Exception("já existe um vértice com essa chave");
		}

		Vertice vertice = new Vertice(chaveVertice);
		this.vertices.put(vertice.getChave(), vertice);
	}

	/**
	 * Remove um vértice do grafo
	 * @param chaveVertice
	 * @throws Exception
	 */
	public void removeVertice(Object chaveVertice) throws Exception {

		if (chaveVertice == null) {
			throw new Exception("o valor chaveVertice está nulo");
		}

		if (!vertices.containsKey(chaveVertice)) {
			throw new Exception("o grafo não contém esse vértice");
		}

		Vertice verticeRemover = vertices.get(chaveVertice);
		verticeRemover.removerAdjacentes();
		this.vertices.remove(chaveVertice);
	}

	/**
	 * Conecta (põe uma aresta) entre dois vértices do grafo
	 * @param chaveVertice1
	 * @param chaveVertice2
	 * @throws Exception
	 */
	public void conectar(Object chaveVertice1, Object chaveVertice2) throws Exception {
		Vertice vertice1 = vertices.get(chaveVertice1);
		Vertice vertice2 = vertices.get(chaveVertice2);

		if (vertice1 == null) {
			throw new Exception("o vértice " + chaveVertice1 + " não existe.");
		}

		if (vertice2 == null) {
			throw new Exception("o vértice " + chaveVertice2 + " não existe.");
		}

		vertice1.adicionaAdjacente(vertice2);
		vertice2.adicionaAdjacente(vertice1);
	}

	/**
	 * Desconecta (retira) a aresta de ligação entre dois vértices
	 * @param chaveVertice1
	 * @param chaveVertice2
	 * @throws Exception
	 */
	public void desconectar(Object chaveVertice1, Object chaveVertice2) throws Exception {

		Vertice vertice1 = vertices.get(chaveVertice1);
		Vertice vertice2 = vertices.get(chaveVertice2);

		if (vertice1 == null) {
			throw new Exception("o vértice " + chaveVertice1 + " não existe.");
		}

		if (vertice2 == null) {
			throw new Exception("o vértice " + chaveVertice2 + " não existe.");
		}

		vertice1.removerAdjacente(vertice2);
		vertice2.removerAdjacente(vertice1);

	}

	/**
	 * A ordem de um grafo é o número de vértices que este tem
	 * @return a ordem de um grafo (int)
	 */
	public int ordem() {
		return this.vertices.size();
	}

	/**
	 * Uma collection que agrupa múltiplos objetos vértice
	 * @return a collection de todos os vértices
	 */
	public Collection<Vertice> getVertices() {
		return vertices.values();
	}

	/**
	 * Um vértice de um grafo ou nó é a unidade fundamental pela qual os grafos são formados
	 * @return o primeiro vértice do grafo (Vertice)
	 * @throws Exception
	 */
	public Vertice umVertice() throws Exception {
		if (vertices.isEmpty()) {
			throw new Exception("Não há nenhum vértice no grafo!");

		} else {
			return vertices.values().iterator().next();
		}
	}

	/**
	 * Vértices adjacentes de um vértice são aqueles ligados imediatamente por uma aresta
	 * @param chaveVertice
	 * @return um conjunto de vértices adjacentes ao vértice passado como parâmentro (Set<Vertice>)
	 * @throws Exception
	 */
	public Set<Vertice> adjacentes(Object chaveVertice) throws Exception {

		Vertice vertice = vertices.get(chaveVertice);
		if (vertice != null) {
			return vertice.getAdjacentes();
		} else {
			throw new Exception("o vértice não existe.");
		}

	}

	/**
	 * O Grau de um vértice indica a quantos outros vértices este está ligado por arestas
	 * @param chaveVertice
	 * @return o número de ligações (arestas) que o vértice passado por parâmetro possui (int)
	 * @throws Exception
	 */
	public int grau(Object chaveVertice) throws Exception {
		Vertice vertice = vertices.get(chaveVertice);
		if (vertice != null) {
			return vertice.obterGrau();
		} else {
			throw new Exception("Este vértice não existe!");
		}
	}

	/**
	 * Um grafo regular é um grafo onde cada vértice tem o mesmo número de adjacências, i.e. cada vértice tem o mesmo grau ou valência
	 * @return true se o grafo for regular, false se não for (boolean)
	 */
	public boolean ehRegular() {

		if (vertices.isEmpty()) {
			return false;
		}

		Integer n = null;

		for (Vertice v : vertices.values()) {

			if (n == null) {
				n = v.obterGrau();
			}

			if (n.intValue() != v.obterGrau()) {
				return false;

			}
		}
		return true;

	}

	/**
	 * Um grafo completo é um grafo simples em que todo vértice é adjacente a todos os outros vértices
	 * @return true se o grafo for completo ou false se não (boolean)
	 */
	public boolean ehCompleto() {

		int ordem = this.ordem() - 1;

		for (Vertice vertice : vertices.values()) {
			try {
				if (this.grau(vertice.getChave()) != ordem) {
					return false;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	/**
	 * O fecho transitivo é um conjunto de vértices que pode ser atingido a partir de um determinado vértice
	 * @param chaveVertice
	 * @return um conjunto de vértices do fecho transitivo do vértice passado como parâmentro (Set<Vertice>)
	 * @throws Exception
	 */
	public Set<Vertice> fechoTransitivo(Object chaveVertice) throws Exception {
		Set<Vertice> jaVisitados = new HashSet<Vertice>();
		if (vertices.get(chaveVertice) == null) {
			throw new Exception("Este vértice não existe!");
		}
		return procuraFechoTransitivo(vertices.get(chaveVertice), jaVisitados);
	}

	private Set<Vertice> procuraFechoTransitivo(Vertice vertice, Set<Vertice> jaVisitados) {
		jaVisitados.add(vertice);
		for (Vertice adjacente : vertice.getAdjacentes()) {
			if (!jaVisitados.contains(adjacente)) {
				procuraFechoTransitivo(adjacente, jaVisitados);
			}
		}
		return jaVisitados;

	}

	/**
	 * Um grafo é conexo se há pelo menos uma cadeia ligando cada par de vértices deste grafo G
	 * @return true se há essa cadeia ou false se não (boolean)
	 * @throws Exception
	 */
	public boolean ehConexo() throws Exception {
		Set<Vertice> fechoTransitivo = fechoTransitivo(umVertice().getChave());
		Set<Vertice> verticesSet = new HashSet<Vertice>(vertices.values());
		return verticesSet.equals(fechoTransitivo);
	}

	/**
	 * Uma árvore é um grafo conexo sem ciclos
	 * @return true se o grafo for uma árvore ou false se não for (boolean)
	 * @throws Exception
	 */
	public boolean ehArvore() throws Exception {
		Vertice vertice = umVertice();
		return this.ehConexo() && !haCicloCom(vertice, vertice, new HashSet<Vertice>());
	}

	private boolean haCicloCom(Vertice vertice, Vertice verticeAnterior, Set<Vertice> jaVisitados) throws Exception {
		if (jaVisitados.contains(vertice)) {
			return true;
		}
		jaVisitados.add(vertice);

		for (Vertice adjacente : adjacentes(vertice.getChave())) {
			if (!adjacente.equals(verticeAnterior)) {
				return haCicloCom(adjacente, vertice, jaVisitados);
			}
		}
		jaVisitados.remove(vertice);

		return false;
	}

}