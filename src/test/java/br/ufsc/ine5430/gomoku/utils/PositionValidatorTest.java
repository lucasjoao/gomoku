package br.ufsc.ine5430.gomoku.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import br.ufsc.ine5430.gomoku.gui.GuiGomoku;

public class PositionValidatorTest {

	@Test
	public void verificaSePassarValorZeroRetornaFalso() {
		boolean check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 0);
		assertFalse(check);

	}

	@Test
	public void verificaSePassarValor256RetornaFalso() {
		boolean check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 226);
		assertFalse(check);

	}

	@Test
	public void verificaSePassarValorUmRetornaTrue() {
		boolean check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 1);
		assertTrue(check);

	}

	@Test
	public void verificaSePassarValor255RetornaTrue() {
		boolean check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 225);
		assertTrue(check);

	}

	@Test
	public void verificaSePassarValor254RetornaTrue() {
		boolean check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 224);
		assertTrue(check);

	}

	@Test
	public void verificaSeSistemaDeixaColocarPedrinhaEmCasaJaOcupada() {
		Map<Integer, String> tabuleiro = new GuiGomoku().getTabuleiro();
		tabuleiro.put(1, "x");
		boolean check = PositionValidator.check(tabuleiro, 1);
		assertFalse(check);
	}

}
