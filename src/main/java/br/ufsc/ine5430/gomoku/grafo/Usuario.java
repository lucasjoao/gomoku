package br.ufsc.ine5430.gomoku.grafo;

import java.util.Scanner;

public class Usuario {

	Scanner s = new Scanner(System.in);
	Scanner l = new Scanner(System.in);
	int opcao = 0;
	Vertice v = new Vertice("v");
	Vertice v1 = new Vertice("v1");
	Vertice v2 = new Vertice("v2");

	public int retornaOpcao() {
		return s.nextInt();
	}

	public String pecaNome() {
		System.out.println("Digite o nome do seu vértice: \n");
		return s.nextLine();

	}
	
	
	public int lerInt(){
		String leitura = l.nextLine();
		if(leitura.matches("\\d*"))
			return Integer.parseInt(leitura);
		System.out.println("Leitura invalida. Tente novamente.");
		return lerInt();
	}
	
	

}