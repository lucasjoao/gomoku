package br.ufsc.ine5430.gomoku.ai;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;

public class MiniMax {

	private State state;
	// TODO: talvez o seed seja necessario para fazer a parada rodar, verificar se fiz certo
	private PlayersEnum turnOf;
	private PlayersEnum otherPlayer;

	public MiniMax(State state, PlayersEnum turnOf) {
		this.state = state;
		this.turnOf = turnOf;
		this.otherPlayer = turnOf == PlayersEnum.PC ? PlayersEnum.HUMAN : PlayersEnum.PC;
	}

	// TODO documentar, return int[2] of {row, col}
	public int[] move() {
		int[] result = this.algorithm(2, this.turnOf);
		return new int[] {result[1], result[2]};
	}

	// TODO documentar, return int[3] of {score, row, col}
	private int[] algorithm(int depth, PlayersEnum player) {
		List<int[]> nextMoves = this.generateNextMoves();

		int bestScore = player == PlayersEnum.PC ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;

		if (nextMoves.isEmpty() || depth == 0) {
			bestScore = this.evaluate();
		} else {
			for (int[] move : nextMoves) {
				Position position = state.getPieces().get(move[0]).get(move[1]);
				position.setPlayer(player);

				if (player == PlayersEnum.PC) {
					currentScore = this.algorithm(depth - 1, this.otherPlayer)[0]; // TODO: check this when running
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else {
					currentScore = this.algorithm(depth - 1, this.turnOf)[0]; // TODO: check this when running
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				}

				position.setPlayer(null);
			}
		}
		return new int[] {bestScore, bestRow, bestCol};
	}

	// TODO documentar, return nextMoves in a list of int[2] of {row, col}
	private List<int[]> generateNextMoves() {
		List<int[]> nextMoves = new ArrayList<>();

		if (this.hasWon(PlayersEnum.HUMAN) || this.hasWon(PlayersEnum.PC)) {
			return nextMoves;
		}

		for (int row = this.state.getLastMove()[0] - 2; row < row + 5; row++) {
			for (int col = this.state.getLastMove()[1] - 2; col < col + 5; col++) {
				if (row >= 0 && col >= 0 && this.state.getPieces().get(row).get(col).isEmpty()) {
					nextMoves.add(new int[] {row, col});
				}
			}
		}

		// TODO: vale testar adicionar na região central? vale adicionar?

		return nextMoves;
	}

	private int evaluate() {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean hasWon(PlayersEnum player) {
		// key == number of rounds
		if ((int) this.state.getChave() < 5) {
			return false;
		} else if (this.checkLine(false, player)) {
			// check horizontal line
			return true;
		} else if (this.checkLine(true, player)) {
			// check vertical line
			return true;
		} else if (this.checkDiagonal(true, player)) {
			// check diagonal from upper-left corner to lower-right corner
			return true;
		} else if (this.checkDiagonal(false, player)) {
			// check diagonal from upper-right corner to lower-left corner
			return true;
		} else {
			return false;
		}
	}

	private boolean checkLine(boolean isVertical, PlayersEnum player) {
		int sequence = 0;
		int index = isVertical ? 0 : 1;
		for (int i = this.state.getLastMove()[index] - 4; i <= i + 8; i++) {
			if (i >= 0) {
				Position position = isVertical ? this.state.getPieces().get(i).get(this.state.getLastMove()[1])
											   : this.state.getPieces().get(this.state.getLastMove()[0]).get(i);
				if (!position.isEmpty() && player == position.getPlayer()) {
					sequence++;
					if (sequence == 5) {
						return true;
					}
				} else {
					sequence = 0;
				}
			}
		}
		return false;
	}

	private boolean checkDiagonal(boolean isLeftToRight, PlayersEnum player) {
		int sequence = 0;
		int colLastMove = this.state.getLastMove()[1];
		int col = isLeftToRight ? colLastMove - 4 : colLastMove + 4;
		for (int row = this.state.getLastMove()[0] - 4; row <= row + 8; row++) {
			if (row >= 0 && col >= 0) {
				Position position = this.state.getPieces().get(row).get(col);
				if (!position.isEmpty() && player == position.getPlayer()) {
					sequence++;
					if (sequence == 5) {
						return true;
					}
				} else {
					sequence = 0;
				}
			}
			col += isLeftToRight ? 1 : -1;
		}
		return false;
	}
}
