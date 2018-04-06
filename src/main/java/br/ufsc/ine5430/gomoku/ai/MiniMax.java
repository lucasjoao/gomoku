package br.ufsc.ine5430.gomoku.ai;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;
import br.ufsc.ine5430.gomoku.utils.GomokuUtils;
import br.ufsc.ine5430.gomoku.utils.PositionValidator;

/**
 * Classe que possui a inteligência artificial utilizada no Gomoku
 */
public class MiniMax {

	/**
	 * Estado em que se encontra o tabuleiro ao executar a inteligência artificial
	 *
	 * @see State
	 */
	private State state;
	/**
	 * Jogador que possui a vez de jogar
	 *
	 * @see PlayersEnum
	 */
	private PlayersEnum turnOf;
	/**
	 * Jogador que está no aguardo da jogada
	 *
	 * @see PlayersEnum
	 */
	private PlayersEnum otherPlayer;
	/**
	 * Inteiro que conta a quantidade de iterações necessárias para determinar a
	 * próxima jogada pela inteligência artifical
	 */
	@Getter
	private int loopCounter;

	/**
	 * Construtor que inicia a inteligência artificial a partir de um estado e
	 * de qual jogador tem a vez na rodada. Com essas informações é determinado o
	 * jogador que está no aguardo da jogada.
	 *
	 * @param state o estado atual do tabuleiro
	 * @param turnOf o jogador que tem a vez na rodada
	 * @see State
	 * @see PlayersEnum
	 */
	public MiniMax(State state, PlayersEnum turnOf) {
		this.state = state;
		this.turnOf = turnOf;
		this.otherPlayer = turnOf == PlayersEnum.PC ? PlayersEnum.HUMAN : PlayersEnum.PC;
		this.loopCounter = 0;
	}

	/**
	 * Determina o próximo movimento, provavelmente o melhor, para o computador
	 * com uma profundidade de 4 no algoritmo do miniMax
	 *
	 * @return array de inteiros que representa as coordenadas, linha e coluna
	 * (nessa ordem) do melhor movimento para a inteligência artificial
	 * @see MiniMax#algorithm(int, PlayersEnum, int, int)
	 */
	public int[] move() {
		int[] result = this.algorithm(4, this.turnOf, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] {result[1], result[2]};
	}

	/**
	 * Implementação recursiva do algoritmo miniMax baseada a partir da fonte https://goo.gl/XCLMg1
	 * com podas alpha e beta
	 *
	 * @param depth o inteiro que representa a profundidade que o algoritmo é executado
	 * @param player o que possui a vez na rodada
	 * @param alpha o inteiro associado ao jogador pc que é utilizado na poda
	 * @param beta o inteiro associado ao jogador humano que é utilizado na poda
	 * @return array de inteiros que representa a pontuação e as coordenadas,
	 * linha e coluna, (os três nessa ordem) do melhor movimento
	 * @see List
	 * @see PlayersEnum
	 * @see MiniMax#generateNextMoves()
	 * @see GomokuUtils#posInMap(int, int)
	 */
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

	/**
	 * Determina as possíveis próximas jogadas que o computador pode realizar.
	 * Se no atual estado do tabuleiro houver algum vencedor, o resultado será uma lista de coordenadas vazias,
	 * já que não há mais jogadas possíveis. Por uma questão de desempenho, optou-se por considerar como possíveis jogadas
	 * para o computador somente as posições não ocupadas que estão em uma região de aproximadamente
	 * 10 x 5 ao redor da última posição preenchida no tabuleiro
	 *
	 * @return uma lista de coordenadas (array de inteiros de tamanho 2, onde o primeiro elemento é a row, e,
	 * o segundo é a coluna) com as próximas possíveis jogadas para o computador
	 * @see MiniMax#hasWon(PlayersEnum)
	 * @see List
	 * @see ArrayList
	 * @see GomokuUtils#posInMap(int, int)
	 * @see PositionValidator#check(int)
	 */
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

	/**
	 * Determina a nota a partir da aplicação de uma heurística na "estrela" que possui como centro o último movimento no tabuleiro 
	 *
	 * @return inteiro que representa a nota
	 * @see MiniMax#evaluateDiagonal(boolean)
	 * @see MiniMax#evaluateLine(boolean)
	 */
	private int evaluate() {
		int score = 0;
		score += this.evaluateLine(false);
		score += this.evaluateLine(true);
		score += this.evaluateDiagonal(false);
		score += this.evaluateDiagonal(true);
		return score;
	}

	/**
	 * Determina a nota da sequência que uma linha de 9 peças têm, onde o centro dessa linha é a última peça movimentada no tabuleiro
	 *
	 * @param isVertical booleano que determina se a linha que será verificada é horizontal ou vertical  
	 * @return um inteiro que diz qual a nota da sequência encontrada na linha
	 * @see List
	 * @see ArrayList
	 * @see PositionValidator#check(int)
	 * @see GomokuUtils#posInMap(int, int)
	 * @see MiniMax#calculate(int, List)
	 */
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

	/**
	 * Determina a nota da sequência que uma diagonal de 9 peças tem, onde o centro dessa diagonal é a última peça movimentada no tabuleiro  
	 *
	 * @param isLeftToRight booleano que determina se a linha diagonal que será verifica irá começar da esquerda para direita ou da direita para esquerda
	 * @return um inteiro que diz qual a nota da sequência encontrada na linha
	 * @see List
	 * @see ArrayList
	 * @see PositionValidator#check(int)
	 * @see GomokuUtils#posInMap(int, int)
	 * @see MiniMax#calculate(int, List)
	 */
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

	/**
	 * Faz uma busca em estrela a partir do último movimento para verificar se o jogador venceu ou não a partida
	 *
	 * @param player que será verificado se venceu o jogo
	 * @return booleano que representa se houver um vencedor no jogo
	 * @see MiniMax#checkLine(boolean, PlayersEnum)
	 * @see MiniMax#checkDiagonal(boolean, PlayersEnum)
	 */
	public boolean hasWon(PlayersEnum player) {
		return this.checkLine(false, player) || this.checkLine(true, player) || this.checkDiagonal(true, player) || this.checkDiagonal(false, player);
	}

	/**
	 * Verifica se há uma sequência vencedora na linha
	 *
	 * @param isVertical booleano que determina se a linha que será verificada é horizontal ou vertical
	 * @param player que será verificado se venceu o jogo
	 * @return booleano que indica se há uma sequência vencedora na linha
	 * @see GomokuUtils#posInMap(int, int)
	 * @see PositionValidator#check(int, int)
	 */
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

	/**
	 * Verifica se há uma sequência vencedora na diagonal
	 *
	 * @param isLeftToRight booleano que determina se a linha diagonal que será verifica irá começar da esquerda para direita ou da direita para esquerda
	 * @param player que será verificado se venceu o jogo
	 * @return booleano que indica se há uma sequência vencedora na diagonal
	 * @see GomokuUtils#posInMap(int, int)
	 * @see PositionValidator#check(int, int)
	 */
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

	/**
	 * A partir de um número que representa a sequência e seus extremos, se houver, aplica a heurística e calcula a sua nota
	 *
	 * @param sequence inteiro que representa a quantidade de peças na sequência
	 * @param extremes lista de inteiros que possui as coordenadas dos extremos da sequência. Pode ser uma lista vazia, unitária ou possuir dois itens.
	 * @return inteiro que é a nota dada para a sequência
	 * @see RegionGradesEnum#calculate(List)
	 * @see SequenceGradesEnum#calculate(int)
	 */
	private int calculate(int sequence, List<int[]> extremes) {
		int score = SequenceGradesEnum.calculate(sequence);
		score += RegionGradesEnum.calculate(extremes);
		return score;
	}
}
