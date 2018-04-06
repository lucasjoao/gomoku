package br.ufsc.ine5430.gomoku.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import br.ufsc.ine5430.gomoku.ai.MiniMax;
import br.ufsc.ine5430.gomoku.grafo.Vertice;

/**
 * Classe que representa o estado do tabuleiro e que se fosse preciso montar um grafo no MiniMax, serviria como nodo
 *
 * @see Vertice
 * @see MiniMax
 */
@Getter
@Setter
public class State extends Vertice {

	/**
	 * Map que tem como chave um inteiro (número da posição) e como valor a posição em si.
	 * Representa os 225 lugares do tabuleiro 15 x 15 do Gomoku
	 *
	 * @see Map
	 * @see Position
	 */
	private Map<Integer, Position> board;
	/**
	 * Array de inteiro que irá possuir dois itens, linha e coluna (nessa ordem), do último movimento executado no jogo
	 */
	private int[] lastMove;
	/**
	 * Inteiro que representa o id do estado
	 */
	private static int id;
	/**
	 * Uma array de inteiros constante que possui dois itens, linha e coluna (nessa ordem), que representa a posição central do tabuleiro
	 */
	private final int[] CENTRAL_POSITION = new int[] {7, 7};

	/**
	 * Construtor que cria um estado vazio e representa o começo da partida
	 * Se o computador começar o jogo, então a inteligência artificial irá considerar que a última jogada foi no centro do tabuleiro, e,
	 * com isso irá fazer uma jogada nessa região
	 *
	 * @see #initializePieces()
	 * @see HashMap
	 */
	public State() {
		super(id);
		this.board = new HashMap<Integer, Position>();
		this.initializePieces();
		this.lastMove = this.CENTRAL_POSITION;
		id++;
	}

	/**
	 * Construtor que cria um estado da partida em andamento
	 *
	 * @param board HashMap a configuração de todas as posições do tabuleiro
	 * @param lastMove array de inteiros com a linha e coluna da última jogada na partidade
	 */
	public State(Map<Integer, Position> board, int[] lastMove) {
		super(id);
		this.board = board;
		this.lastMove = lastMove;
	}

	/**
	 * Coloca uma posição vazia em todos os lugares do tabuleiro
	 *
	 * @see Position
	 * @see #board
	 */
	private void initializePieces() {
		Position position = new Position();
		for (int i = 1; i < 225; i++) {
			this.board.put(i, position);
		}
	}
}
