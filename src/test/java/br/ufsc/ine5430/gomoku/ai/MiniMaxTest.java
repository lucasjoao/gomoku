package br.ufsc.ine5430.gomoku.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.ufsc.ine5430.gomoku.model.PlayersEnum;
import br.ufsc.ine5430.gomoku.model.Position;
import br.ufsc.ine5430.gomoku.model.State;

public class MiniMaxTest {

	@Test
	public void verificaSeTamanhoDoArrayEh2() {
		MiniMax miniMax = new MiniMax(new State(), PlayersEnum.HUMAN);
		int[] move = miniMax.move();
		assertEquals(2, move.length);
	}

	@Test
	public void verificaValorCincoDoSegundoElementoArrayMoveParaPrimeiraInteracao() {
		MiniMax miniMax = new MiniMax(new State(), PlayersEnum.HUMAN);
		int[] move = miniMax.move();
		assertEquals(5, move[1]);
	}

	@Test
	public void verificaValorTresDoPrimeiroElementoArrayMoveParaPrimeiraInteracao() {
		MiniMax miniMax = new MiniMax(new State(), PlayersEnum.HUMAN);
		int[] move = miniMax.move();
		assertEquals(3, move[0]);
	}

	@Test
	public void verificaNumeroDeInteracoesIdealParaPrimeiraInteracao() {
		MiniMax miniMax = new MiniMax(new State(), PlayersEnum.HUMAN);
		miniMax.move();
		assertTrue(miniMax.getLoopCounter() < 2000);
	}

	@Test
	public void verificaSeSemNenhumaInteracaoNaoHaVitorioso() {
		MiniMax miniMax = new MiniMax(new State(), PlayersEnum.HUMAN);
		assertFalse(miniMax.hasWon(PlayersEnum.HUMAN));
	}

	@Test
	public void verificaSeDadoStateVitoriaEhDaMaquinaPC() {
		Map<Integer, Position> board = new HashMap<>();
		for (int i = 1; i <= 5; i++) {
			board.put(i, new Position(PlayersEnum.PC));
		}

		State stateVitoriaPc = new State();
		stateVitoriaPc.setLastMove(new int[] {1, 2});
		stateVitoriaPc.setBoard(board);
		MiniMax miniMax = new MiniMax(stateVitoriaPc, PlayersEnum.PC);
		assertTrue(miniMax.hasWon(PlayersEnum.PC));
	}
}
