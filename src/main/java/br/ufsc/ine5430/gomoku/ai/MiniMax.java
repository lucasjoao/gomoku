package br.ufsc.ine5430.gomoku.ai;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;
import br.ufsc.ine5430.gomoku.utils.GomokuUtils;
import br.ufsc.ine5430.gomoku.utils.PositionValidator;

public class MiniMax {

	private State state;
	private PlayersEnum turnOf;
	private PlayersEnum otherPlayer;
	@Getter
	private int loopCounter;

	public MiniMax(State state, PlayersEnum turnOf) {
		this.state = state;
		this.turnOf = turnOf;
		this.otherPlayer = turnOf == PlayersEnum.PC ? PlayersEnum.HUMAN : PlayersEnum.PC;
		this.loopCounter = 0;
	}

	// XXX: documentar, return int[2] of {row, col}
	public int[] move() {
		int[] result = this.algorithm(4, this.turnOf, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] {result[1], result[2]};
	}

	// XXX documentar, return int[3] of {score, row, col}
	private int[] algorithm(int depth, PlayersEnum player, int alpha, int beta) {
		List<int[]> nextMoves = this.generateNextMoves();

		this.turnOf = player;
		this.otherPlayer = this.turnOf == PlayersEnum.PC ? PlayersEnum.HUMAN : PlayersEnum.PC;

		int score;
		int bestRow = -1;
		int bestCol = -1;

		if (nextMoves.isEmpty() || depth == 0) {
			score = this.evaluate();
			score = this.turnOf == PlayersEnum.HUMAN ? -score : score;
			return new int[] {score, bestRow, bestCol};
		} else {
			for (int[] move : nextMoves) {
				this.loopCounter++;
				int posInMap = GomokuUtils.posInMap(move[0], move[1]);
				Position position = this.state.getBoard().get(posInMap);
				position.setPlayer(player);
				position.setEmpty(false);
				int[] lastMoveOriginal = this.state.getLastMove();
				this.state.setLastMove(move);

				if (player == PlayersEnum.PC) {
					score = this.algorithm(depth - 1, this.otherPlayer, alpha, beta)[0];
					if (score > alpha) {
						alpha = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else {
					score = this.algorithm(depth - 1, this.turnOf, alpha, beta)[0];
					if (score < beta) {
						beta = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}

				position.setPlayer(null);
				position.setEmpty(true);
				this.state.setLastMove(lastMoveOriginal);

				if (alpha >= beta) {
					break;
				}
			}
		}
		return new int[] {(player == PlayersEnum.PC) ? alpha : beta, bestRow, bestCol};
	}

	// XXX documentar, return nextMoves in a list of int[2] of {row, col}
	private List<int[]> generateNextMoves() {
		List<int[]> nextMoves = new ArrayList<>();

		if (this.hasWon(PlayersEnum.HUMAN) || this.hasWon(PlayersEnum.PC)) {
			return nextMoves;
		}

		for (int row = this.state.getLastMove()[0] - 4; row < this.state.getLastMove()[0] + 5; row++) {
			for (int col = this.state.getLastMove()[1] - 2; col < this.state.getLastMove()[1] + 3; col++) {
				this.loopCounter++;
				int posInMap = GomokuUtils.posInMap(row, col);
				Position position = this.state.getBoard().get(posInMap);
				if (position != null && PositionValidator.check(row, col) && position.isEmpty()) {
					nextMoves.add(new int[] {row, col});
				}
			}
		}

		return nextMoves;
	}

	private int evaluate() {
		int score = 0;
		score += this.evaluateLine(false);
		score += this.evaluateLine(true);
		score += this.evaluateDiagonal(false);
		score += this.evaluateDiagonal(true);
		return score;
	}

	// XXX: documentar essa suruba
	private int evaluateLine(boolean isVertical) {
		int sequence = 0;
		boolean enemyFound = false;
		boolean hasEmpty = false;
		List<int[]> extremes = new ArrayList<>();
		int index = isVertical ? 0 : 1;
		int[] lastMove = this.state.getLastMove();

		for (int i = lastMove[index] - 1; i > lastMove[index] - 5; i--) {
			this.loopCounter++;
			if (PositionValidator.check(i)) {
				int posInMap = isVertical ? GomokuUtils.posInMap(i, lastMove[1]) : GomokuUtils.posInMap(lastMove[0], i);
				Position position = this.state.getBoard().get(posInMap);

				if (position != null && !position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position != null && position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isVertical ? new int[] {i + 1, lastMove[1]} : new int[] {lastMove[0], i + 1};
						extremes.add(cordlastChecked);

						int posLastChecked = isVertical ? GomokuUtils.posInMap(i + 1, lastMove[1]) : GomokuUtils.posInMap(lastMove[0], i + 1);
						Position lastChecked = this.state.getBoard().get(posLastChecked);
						if (lastChecked.isEmpty()) {
							// seh a segunda vazia consecutiva, entao diminui da sequence
							sequence--;
						}

						break;
					}
				} else {
					// se eh pedra do inimigo
					enemyFound = true;
					break;
				}
			}
		}

		sequence++; // posicao que eu coloquei

		for (int j = lastMove[index] + 1; j < lastMove[index] + 5; j++) {
			this.loopCounter++;
			if (PositionValidator.check(j)) {
				int posInMap = isVertical ? GomokuUtils.posInMap(j, lastMove[1]) : GomokuUtils.posInMap(lastMove[0], j);
				Position position = this.state.getBoard().get(posInMap);

				if (position != null && !position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position != null && position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isVertical ? new int[] {j - 1, lastMove[1]} : new int[] {lastMove[0], j + 1};
						extremes.add(cordlastChecked);

						int posLastChecked = isVertical ? GomokuUtils.posInMap(j - 1, lastMove[1]) : GomokuUtils.posInMap(lastMove[0], j - 1);
						Position lastChecked = this.state.getBoard().get(posLastChecked);
						if (lastChecked.isEmpty()) {
							// seh a segunda vazia consecutiva, entao diminui da sequence
							sequence--;
						}

						break;
					}
				} else {
					// se eh pedra do inimigo
					if (enemyFound && sequence < 5) {
						// desconsidero se tem inimigo nas duas pontas e minha sequence eh menor que 5
						sequence = 0;
					}
					break;
				}
			}
		}

		return this.calculate(sequence, extremes);
	}

	private int evaluateDiagonal(boolean isLeftToRight) {
		int sequence = 0;
		boolean enemyFound = false;
		boolean hasEmpty = false;
		List<int[]> extremes = new ArrayList<>();
		int[] lastMove = this.state.getLastMove();
		int col = isLeftToRight ? lastMove[1] - 1 : lastMove[1] + 1;

		for (int row = lastMove[0] - 1; row > lastMove[0] - 5; row--) {
			this.loopCounter++;
			if (PositionValidator.check(row, col)) {
				int posInMap = GomokuUtils.posInMap(row, col);
				Position position = this.state.getBoard().get(posInMap);
				col += isLeftToRight ? -1 : 1;

				if (position != null && !position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position != null && position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isLeftToRight ? new int[] {row + 1, col + 1} : new int[] {row + 1, col - 1};
						extremes.add(cordlastChecked);

						int posLastChecked = isLeftToRight ? GomokuUtils.posInMap(row + 1, col + 1) : GomokuUtils.posInMap(row + 1, col - 1);
						Position lastChecked = this.state.getBoard().get(posLastChecked);
						if (lastChecked.isEmpty()) {
							// seh a segunda vazia consecutiva, entao diminui da sequence
							sequence--;
						}

						break;
					}
				} else {
					// se eh pedra do inimigo
					enemyFound = true;
					break;
				}
			}
		}

		sequence++; // posicao que eu coloquei

		col = isLeftToRight ? lastMove[1] + 1 : lastMove[1] - 1;
		for (int row = lastMove[0] + 1; row < lastMove[0] + 5; row++) {
			this.loopCounter++;
			if (PositionValidator.check(row, col)) {
				int posInMap = GomokuUtils.posInMap(row, col);
				Position position = this.state.getBoard().get(posInMap);
				col += isLeftToRight ? 1 : -1;

				if (position != null && !position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position != null && position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isLeftToRight ? new int[] {row - 1, col - 1} : new int[] {row - 1, col + 1};
						extremes.add(cordlastChecked);

						int posLastChecked = isLeftToRight ? GomokuUtils.posInMap(row - 1, col - 1) : GomokuUtils.posInMap(row - 1, col + 1);
						Position lastChecked = this.state.getBoard().get(posLastChecked);
						if (lastChecked.isEmpty()) {
							// seh a segunda vazia consecutiva, entao diminui da sequence
							sequence--;
						}

						break;
					}
				} else {
					// se eh pedra do inimigo
					if (enemyFound && sequence < 5) {
						// desconsidero se tem inimigo nas duas pontas e minha sequence eh menor que 5
						sequence = 0;
					}
					break;
				}
			}
		}

		return this.calculate(sequence, extremes);
	}

	public boolean hasWon(PlayersEnum player) {
		if (this.checkLine(false, player)) {
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
		for (int i = this.state.getLastMove()[index] - 4; i <= this.state.getLastMove()[index] + 4; i++) {
			this.loopCounter++;
			if (PositionValidator.check(i)) {
				int posInMap = isVertical ? GomokuUtils.posInMap(i, this.state.getLastMove()[1]) : GomokuUtils.posInMap(this.state.getLastMove()[0], i);
				Position position = this.state.getBoard().get(posInMap);
				if (position != null && !position.isEmpty() && player == position.getPlayer()) {
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
		for (int row = this.state.getLastMove()[0] - 4; row <= this.state.getLastMove()[0] + 4; row++) {
			this.loopCounter++;
			if (PositionValidator.check(row, col)) {
				int posInMap = GomokuUtils.posInMap(row, col);
				Position position = this.state.getBoard().get(posInMap);
				if (position != null && !position.isEmpty() && player == position.getPlayer()) {
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

	private int calculate(int sequence, List<int[]> extremes) {
		int score = SequenceGradesEnum.calculate(sequence);
		score += RegionGradesEnum.calculate(extremes);
		return score;
	}
}
