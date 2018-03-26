package br.ufsc.ine5430.gomoku.grafo;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {

	private Map<Object, Vertice> vertices = new HashMap<Object, Vertice>();

	public Grafo() {
	}

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

	public int ordem() {
		return this.vertices.size();
	}

	public Collection<Vertice> getVertices() {
		return vertices.values();
	}

	public Vertice umVertice() throws Exception {
		if (vertices.isEmpty()) {
			throw new Exception("Não há nenhum vértice no grafo!");

		} else {
			return vertices.values().iterator().next();
		}
	}

	public Set<Vertice> adjacentes(Object chaveVertice) throws Exception {

		Vertice vertice = vertices.get(chaveVertice);
		if (vertice != null) {
			return vertice.getAdjacentes();
		} else {
			throw new Exception("o vértice não existe.");
		}

	}

	public int grau(Object chaveVertice) throws Exception {
		Vertice vertice = vertices.get(chaveVertice);
		if (vertice != null) {
			return vertice.obterGrau();
		} else {
			throw new Exception("Este vértice não existe!");
		}
	}

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

	public boolean ehConexo() throws Exception {
		Set<Vertice> fechoTransitivo = fechoTransitivo(umVertice().getChave());
		Set<Vertice> verticesSet = new HashSet<Vertice>(vertices.values());
		return verticesSet.equals(fechoTransitivo);
	}

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