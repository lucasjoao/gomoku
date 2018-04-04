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

public class ControllerGomoku {

	private GuiGomoku guiHandler;
	private State lastState;

	public ControllerGomoku() {
		this.guiHandler = new GuiGomoku();
		this.guiHandler.printaMap();
		this.lastState = new State();
	}

	public void letsPlay() {
		int whoStart = PositionValidator.checkInput(
				JOptionPane.showInputDialog("Digite o número de quem irá começar o jogo: \n 1 - pc \n 2 - você"));
		boolean wannaPlay = true;
		while (wannaPlay) {
			if (PlayersEnum.PC.getIndex() == whoStart) {
				this.pcPlayer(this.guiHandler);
				if (this.hasWon(PlayersEnum.PC)) break;

				wannaPlay = this.humanPlayer(this.guiHandler);
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
				if (this.hasWon(PlayersEnum.HUMAN)) break;
			} else if (PlayersEnum.HUMAN.getIndex() == whoStart) {
				wannaPlay = this.humanPlayer(this.guiHandler);
				if (!wannaPlay) {
					System.out.println("Jogo finalizado!");
					break;
				}
				if (this.hasWon(PlayersEnum.HUMAN)) break;

				this.pcPlayer(this.guiHandler);
				if (this.hasWon(PlayersEnum.PC)) break;
			} else {
				JOptionPane.showMessageDialog(null, "Não brinque! Opção inválida, jogo finalizado.");
				break;
			}
		}
	}

	private boolean hasWon(PlayersEnum player) {
		MiniMax miniMax = new MiniMax(this.lastState, player);
		boolean hasWon = miniMax.hasWon(player);
		if (hasWon) {
			System.out.println(player.getText() + " VENCEU!!!");
		}
		return hasWon;
	}

	private boolean humanPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
		int valor, linha, coluna;
		FormError errors;
		do {
			linha = guiHandler.pecaLinha();
			if (linha == Integer.MIN_VALUE)
				return false;
			coluna = guiHandler.pecaColuna();
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
		guiHandler.printaMap();
		return true;
	}

	private void pcPlayer(GuiGomoku guiHandler) {
		Map<Integer, String> tabuleiro = guiHandler.getTabuleiro();
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
		guiHandler.printaMap();
	}
}
