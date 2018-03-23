package br.ufsc.ine5430.gomoku.grafo;
import java.util.HashMap;
import java.util.Map;

public class Grafo {

	// protected List<Vertice> vertices = new ArrayList<>();
	private Map<String, Vertice> vertices = new HashMap<String, Vertice>();

	public Grafo() {
	}

	public void adicionaVertice(Vertice vertice) {

		boolean auxiliar = false;

		if (vertices.size() == 0) {
			this.vertices.put(vertice.getNome(), vertice);
			System.out.println("Vértice " + vertice.getNome() + " adicionado com sucesso!");
		} else {

			for (String nomeVertice : vertices.keySet()) {
				if (vertice.getNome().equals(nomeVertice)) {
					auxiliar = true;
				}
			}

			if (auxiliar) {
				System.out.println("Não foi possível adicionar, este vértice já existe!");
			} else {

				this.vertices.put(vertice.getNome(), vertice);
				System.out.println("Vértice " + vertice.getNome() + " adicionado com sucesso2!");
			}

		}

	}

	public void removeVertice(String nome) {

		Vertice removido = vertices.get(nome);

		removido.adjacentes.remove(vertices);

		this.vertices.remove(nome);
		System.out.println("Vértice removido com sucesso!");

		// TODO PRECISA REMOVER LIGAÇÕES DOS VERTICES REMOVIDOS TB

	}

	public void conecta(String v1, String v2) {
		Vertice vertice1 = vertices.get(v1);
		Vertice vertice2 = vertices.get(v2);

		if (vertice1.adjacentes.contains(vertice2)) {
			System.out.println("Os dois vértices já estão conectados!");
		} else {
			vertice1.adjacentes.add(vertice2);
			vertice2.adjacentes.add(vertice1);
			System.out.println("Os vértices " + vertice1.getNome() + " e " + vertice2.getNome() + " foram conectados!");
		}
	}

	public void desconecta(Vertice v1, Vertice v2) {

		if (v1.adjacentes.contains(v2)) {
			v1.adjacentes.remove(v2);
			v2.adjacentes.remove(v1);
			System.out.println("Os vértices " + v1.getNome() + " e " + v2.getNome() + "foram desconectados!");
		} else {
			System.out.println("Não foi possível, pois os vértices não são ligados");
		}

		// TODO PRECISA VERIFICAR SE HÁ ESSA LIGAÇÃO

	}

	public void ordem() {
		System.out.println("A ordem do grafo é: " + this.vertices.size());
	}

	public void mostrarVertices() {

		String saida = "";

		if (vertices.size() > 0) {

			for (String nomeVertice : vertices.keySet()) {
				saida = saida + nomeVertice + ", ";
			}
			saida = saida.substring(0, saida.length() - 2);
		}
		System.out.println("V = {" + saida + "}");

	}

	public Vertice umVertice() {
		Vertice v1 = new Vertice("v1");
		return v1;
	}

	public String adjacentes(Vertice v) {

		return v.toString();

	}

	public void grau(String nomeVertice) {
		Vertice auxiliar = vertices.get(nomeVertice);
		int grau = auxiliar.adjacentes.size();
		System.out.println("O grau do vértice " + auxiliar.getNome() + " é " + grau);
	}

	public boolean ehRegular() {
		return true;
	}

	public boolean ehCompleto() {
		return true;
	}

	public int fechoTransitivo(Vertice v) {
		return 1;
	}

	public boolean ehConexo() {
		return true;

	}

	public boolean ehArvore() {
		return true;

	}

}
