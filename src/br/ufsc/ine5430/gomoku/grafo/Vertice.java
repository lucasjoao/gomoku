package br.ufsc.ine5430.gomoku.grafo;
import java.util.ArrayList;
import java.util.List;

public class Vertice {

	protected List<Vertice> adjacentes = new ArrayList<>();
	private String nome;

	public Vertice(String n) {
		this.nome = n;
	}

	public void setNome(String n) {
		this.nome = n;
	}

	public String getNome() {

		return nome;
	}
	
	public String toString () {
		
		String s = "V = {";
		for (Vertice vertice : adjacentes) {

			s = s + vertice.getNome() + ", ";
		}

		s = s + "}";
		return s;
	}
}
