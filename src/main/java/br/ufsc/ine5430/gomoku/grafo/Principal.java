package br.ufsc.ine5430.gomoku.grafo;
import java.util.Scanner;

public class Principal {

	private static final int SAIR = 15;
	private static final int GRAFO_ARVORE = 14;
	private static final int GRAFO_CONEXO = 13;
	private static final int FECHO_TRANSITIVO = 12;
	private static final int GRAFO_COMPLETO = 11;
	private static final int GRAFO_REGULAR = 10;
	private static final int GRAU_VERTICE = 9;
	private static final int CONJUNTO_VERTICES_ADJACENTES = 8;
	private static final int VERTICE_RANDOM = 7;
	private static final int CONJUNTO_VERTICES = 6;
	private static final int ORDEM_VERTICE = 5;
	private static final int DESCONECTA_VERTICE = 4;
	private static final int CONECTA_VERTICE = 3;
	private static final int REMOVE_VERTICE = 2;
	private static final int ADD_VERTICE = 1;

	public static void main(String[] args) {

		Grafo grafo = new Grafo();
		Usuario usuario = new Usuario();

		Scanner s = new Scanner(System.in);
		int opcao = 0;
		Vertice v = new Vertice("v");
		Vertice v1 = new Vertice("v1");
		Vertice v2 = new Vertice("v2");

		while (opcao < 15) {

			opcao = usuario.lerInt();
			
			switch (opcao) {
			case ADD_VERTICE:
				String nomeVerticeInserir = usuario.pecaNome();
				grafo.adicionaVertice(new Vertice(nomeVerticeInserir));
				break;
			case REMOVE_VERTICE:
				String nomeVerticeRemover = usuario.pecaNome();
				grafo.removeVertice(nomeVerticeRemover);
				break;

			case CONECTA_VERTICE:
				String nomeVerticeV1 = usuario.pecaNome();
				String nomeVerticeV2 = usuario.pecaNome();
				grafo.conecta(nomeVerticeV1, nomeVerticeV2);
				break;

			case DESCONECTA_VERTICE:
				grafo.desconecta(v1, v2);
				break;
				
			case ORDEM_VERTICE:
				grafo.ordem();
				break;

			case CONJUNTO_VERTICES:
				grafo.mostrarVertices();
				break;

			case VERTICE_RANDOM:
				grafo.umVertice();
				break;
				
			case CONJUNTO_VERTICES_ADJACENTES:
				String a = grafo.adjacentes(v);
				System.out.println(a);
				break;

			case GRAU_VERTICE:
				String nomeVerticeGrau = usuario.pecaNome();
				grafo.grau(nomeVerticeGrau);
				break;

			case GRAFO_REGULAR:
				grafo.ehRegular();
				break;

			case GRAFO_COMPLETO:
				grafo.ehCompleto();
				break;

			case FECHO_TRANSITIVO:
				grafo.fechoTransitivo(v);
				break;

			case GRAFO_CONEXO:
				grafo.ehConexo();
				break;
			case GRAFO_ARVORE:
				grafo.ehArvore();
				break;

			case SAIR:
				opcao = 16;

			default:
				break;

			}
	

		}

	}

}
