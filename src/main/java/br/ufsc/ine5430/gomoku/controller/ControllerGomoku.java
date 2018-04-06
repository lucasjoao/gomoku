package br.ufsc.ine5430.gomoku.controller;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufsc.ine5430.gomoku.ai.MiniMax;
import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.model.FormError;
import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;
import br.ufsc.ine5430.gomoku.utils.GomokuUtils;
import br.ufsc.ine5430.gomoku.utils.PositionValidator;

/**
 * Classe que representa o controlador do jogo Gomoku
 */
public class ControllerGomoku {

	/**
	 * Interface visual do jogo Gomoku
	 *
	 * @see GuiGomoku
	 */
	private GuiGomoku guiHandler;
	/**
	 * Último estado em que o jogo Gomoku se encontrou
	 *
	 * @see State
	 */
	private State lastState;

	/**
	 * Construtor que inicia o controlador do jogo e já apresenta para o usuário o tabuleiro
	 */
	public ControllerGomoku() {
		this.guiHandler = new GuiGomoku();
		this.guiHandler.printaMap();
		this.lastState = new State();
	}

	/**
	 * Método que aciona o início de todas as jogadas do Gomoku.
	 * Primeiro verifica-se quem irá começar o jogo, caso seja digitada uma opção inválida, o jogo acaba.
	 * Após isso alterna-se entre uma jogada do humano e do computador, ou vice versa. Enquanto não há um vencedor.
	 *
	 * @see PositionValidator#checkInput(String)
	 * @see PlayersEnum
	 * @see #pcPlayer()
	 * @see #hasWon(PlayersEnum)
	 * @see #humanPlayer()
	 */
	public void letsPlay() {
		int whoStart = PositionValidator.checkInput(
				JOptionPane.showInputDialog("Digite o número de quem irá começar o jogo: \n 1 - pc \n 2 - você"));
		boolean wannaPlay = true;
		while (wannaPlay) {
			if (PlayersEnum.PC.getIndex() == whoStart) {
				this.pcPlayer();
				if (this.hasWon(PlayersEnum.PC)) break;

				wannaPlay = this.humanPlayer();
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
				if (this.hasWon(PlayersEnum.HUMAN)) break;
			} else if (PlayersEnum.HUMAN.getIndex() == whoStart) {
				wannaPlay = this.humanPlayer();
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
				if (this.hasWon(PlayersEnum.HUMAN)) break;

				this.pcPlayer();
				if (this.hasWon(PlayersEnum.PC)) break;
			} else {
				JOptionPane.showMessageDialog(null, "Não brinque! Opção inválida, jogo finalizado.");
				break;
			}
		}
	}

	/**
	 * Utiliza do algoritmo no MiniMax para verificar se um determinado jogador venceu o jogo ou não.
	 * Caso haja um vencedor, apresenta essa informação para o usuário
	 *
	 * @param player que será verificado se ganhou o jogo ou não
	 * @return um boolean true se o player venceu o jogo, senão false
	 * @see MiniMax#hasWon(PlayersEnum)
	 */
	private boolean hasWon(PlayersEnum player) {
		MiniMax miniMax = new MiniMax(this.lastState, player);
		boolean hasWon = miniMax.hasWon(player);
		if (hasWon) {
			System.out.println(player.getText() + " VENCEU!!!");
		}
		return hasWon;
	}

	/**
	 * Aciona a jogada do desafiante humano. Questiona para ele qual a linha e coluna que ele deseja colocar a sua peça.
	 * Se essa jogada não for um número, o jogo acaba. Caso ela seja um número inválido, então o usuário terá uma nova
	 * oportunidade de dizer qual a linha e coluna que deseja colocar a sua peça. Caso tudo esteja correto, então a jogada é realizada,
	 * se atualiza o último estado e apresenta a nova configuração do tabuleiro para o usuário.
	 *
	 * @return true se a jogada tiver ocorrido perfeitamente e false caso o humano tenha entrado com algum valor que não seja um número,
	 * o que acarreta no fim do jogo
	 * @see #lastState
	 * @see #guiHandler
	 * @see GomokuUtils#posInMap(int, int)
	 * @see PositionValidator#check(Map, int)
	 */
	private boolean humanPlayer() {
		Map<Integer, String> tabuleiro = this.guiHandler.getTabuleiro();
		int valor, linha, coluna;
		FormError errors;
		do {
			linha = this.guiHandler.pecaLinha();
			if (linha == Integer.MIN_VALUE)
				return false;
			coluna = this.guiHandler.pecaColuna();
			if (coluna == Integer.MIN_VALUE)
				return false;
			valor = GomokuUtils.posInMap(linha, coluna);
			errors = PositionValidator.check(tabuleiro, valor);
			if (!errors.getErrors().isEmpty()) {
				JOptionPane.showMessageDialog(null, errors.getErrors().get(0));
			}
		} while (!errors.getErrors().isEmpty());

		this.lastState.getBoard().put(valor, new Position(PlayersEnum.HUMAN));
		this.lastState = new State(this.lastState.getBoard(), new int[] { linha, coluna });

		tabuleiro.put(valor, "x");
		this.guiHandler.printaMap();
		return true;
	}

	/**
	 * Aciona a jogada da inteligência artificial. A partir do último estado pede pro MiniMax qual a próxima melhor jogada possível.
	 * Se essa jogada for viável, realiza ela, atualiza o último estado, mostra quantas iterações foram necessárias para a realização da jogada no
	 * algoritmo do Minimax e apresenta a nova configuração do tabuleiro para o usuário.
	 *
	 * @see #lastState
	 * @see #guiHandler
	 * @see GomokuUtils#posInMap(int, int)
	 * @see PositionValidator#check(Map, int)
	 * @see MiniMax#move()
	 */
	private void pcPlayer() {
		Map<Integer, String> tabuleiro = this.guiHandler.getTabuleiro();
		int valor, linha, coluna;
		MiniMax miniMax;
		FormError errors;

		do {
			miniMax = new MiniMax(this.lastState, PlayersEnum.PC);
			int[] cord = miniMax.move();
			linha = cord[0];
			coluna = cord[1];
			valor = GomokuUtils.posInMap(linha, coluna);
			errors = PositionValidator.check(tabuleiro, valor);
			if (!errors.getErrors().isEmpty()) {
				JOptionPane.showMessageDialog(null, errors.getErrors().get(0));
			}
		} while (!errors.getErrors().isEmpty());

		this.lastState.getBoard().put(valor, new Position(PlayersEnum.PC));
		this.lastState = new State(this.lastState.getBoard(), new int[] { linha, coluna });

		System.out.println(
				"Quantidade de iterações necessárias na classe MiniMax para a jogada: " + miniMax.getLoopCounter());
		tabuleiro.put(valor, "o");
		this.guiHandler.printaMap();
	}
}
