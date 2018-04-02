package br.ufsc.ine5430.gomoku.ai;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;

public class MiniMax {

	private State state;
	// TODO: talvez o seed seja necessario para fazer a parada rodar, verificar se fiz certo
	// TODO: revisar todos os lastsMoves
	// TODO: checks de negativos devem considerar extremo do tabuleiro (15)
	private PlayersEnum turnOf;
	private PlayersEnum otherPlayer;

	public MiniMax(State state, PlayersEnum turnOf) {
		this.state = state;
		this.turnOf = turnOf;
		this.otherPlayer = turnOf == PlayersEnum.PC ? PlayersEnum.HUMAN : PlayersEnum.PC;
	}

	// TODO documentar, return int[2] of {row, col}
	public int[] move() {
		int[] result = this.algorithm(2, this.turnOf, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] {result[1], result[2]};
	}

	// TODO documentar, return int[3] of {score, row, col}
	private int[] algorithm(int depth, PlayersEnum player, int alpha, int beta) {
		List<int[]> nextMoves = this.generateNextMoves();

		int score;
		int bestRow = -1;
		int bestCol = -1;

		if (nextMoves.isEmpty() || depth == 0) {
			score = this.evaluate();
			return new int[] {score, bestRow, bestCol};
		} else {
			for (int[] move : nextMoves) {
				Position position = this.state.getPieces().get(move[0]).get(move[1]);
				position.setPlayer(player);

				if (player == PlayersEnum.PC) {
					score = this.algorithm(depth - 1, this.otherPlayer, alpha, beta)[0]; // TODO: check this when running
					// TODO: como fazer para avaliar somente as minhas peças?
					if (score > alpha) {
						alpha = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else {
					score = this.algorithm(depth - 1, this.turnOf, alpha, beta)[0]; // TODO: check this when running
					if (score < beta) {
						beta = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}

				position.setPlayer(null);

				if (alpha >= beta) {
					break;
				}
			}
		}
		return new int[] {(player == PlayersEnum.PC) ? alpha : beta, bestRow, bestCol};
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
		int score = 0;
		score += this.evaluateLine(false);
		score += this.evaluateLine(true);
		score += this.evaluateDiagonal(false);
		score += this.evaluateDiagonal(true);
		return score; // TODO: realmente necessario usar numero de rodadas? por enquanto nao
	}

	// TODO: documentar essa suruba
	private int evaluateLine(boolean isVertical) {
		// TODO: como fazer avaliacao para inimigo, so seto negativo no final?
		int sequence = 0;
		boolean enemyFound = false;
		boolean hasEmpty = false;
		List<int[]> extremes = new ArrayList<>();
		int index = isVertical ? 0 : 1;
		int[] lastMove = this.state.getLastMove();

		for (int i = lastMove[index] - 1; i > i - 4; i--) {
			if (i >= 0) { // FIXME todo linha 15
				Position position = isVertical ? this.state.getPieces().get(i).get(lastMove[1])
						: this.state.getPieces().get(lastMove[0]).get(i);

				if (!position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isVertical ? new int[] {i + 1, lastMove[1]} : new int[] {lastMove[0], i + 1};
						extremes.add(cordlastChecked);

						Position lastChecked = isVertical ? this.state.getPieces().get(i + 1).get(lastMove[1]) : this.state.getPieces().get(lastMove[0]).get(i + 1);
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

		for (int j = lastMove[index] + 1; j < j + 4; j++) {
			if (j >= 0) { // FIXME todo linha 15
				Position position = isVertical ? this.state.getPieces().get(j).get(lastMove[1])
						: this.state.getPieces().get(lastMove[0]).get(j);

				if (!position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isVertical ? new int[] {j - 1, lastMove[1]} : new int[] {lastMove[0], j + 1};
						extremes.add(cordlastChecked);

						Position lastChecked = isVertical ? this.state.getPieces().get(j - 1).get(lastMove[1]) : this.state.getPieces().get(lastMove[0]).get(j - 1);
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

		return GradesEnum.calculate(sequence, extremes);
	}

	private int evaluateDiagonal(boolean isLeftToRight) {
		// TODO: como fazer avaliacao para inimigo, so seto negativo no final?
		int sequence = 0;
		boolean enemyFound = false;
		boolean hasEmpty = false;
		List<int[]> extremes = new ArrayList<>();
		int[] lastMove = this.state.getLastMove();
		int col = isLeftToRight ? lastMove[1] - 1 : lastMove[1] + 1;

		for (int row = lastMove[0] - 1; row > row - 4; row--) {
			if (row >= 0 && col >= 0) { // FIXME todo linha 15
				Position position = this.state.getPieces().get(row).get(col);
				col += isLeftToRight ? -1 : 1;

				if (!position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isLeftToRight ? new int[] {row + 1, col + 1} : new int[] {row + 1, col - 1};
						extremes.add(cordlastChecked);

						Position lastChecked = isLeftToRight ? this.state.getPieces().get(row + 1).get(col + 1) : this.state.getPieces().get(row + 1).get(col - 1);
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
		for (int row = lastMove[0] + 1; row < row + 4; row++) {
			if (row >= 0 && col >= 0) { // FIXME todo linha 15
				Position position = this.state.getPieces().get(row).get(col);
				col += isLeftToRight ? 1 : -1;

				if (!position.isEmpty() && position.getPlayer() == this.turnOf) {
					// se eh a minha pedra
					sequence++;
				} else if (position.isEmpty()) {
					if (!hasEmpty) {
						// se esta vazia e eh a primeira vazia
						sequence++;
						hasEmpty = true;
					} else {
						// se eh a segunda vazia add como extremo a peca verificada anteriormente
						int[] cordlastChecked = isLeftToRight ? new int[] {row - 1, col - 1} : new int[] {row - 1, col + 1};
						extremes.add(cordlastChecked);

						Position lastChecked = isLeftToRight ? this.state.getPieces().get(row - 1).get(col - 1) : this.state.getPieces().get(row - 1).get(col + 1);
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

		return GradesEnum.calculate(sequence, extremes);
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
