package br.ufsc.ine5430.gomoku.utils;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import br.ufsc.ine5430.gomoku.gui.GuiGomoku;
import br.ufsc.ine5430.gomoku.model.FormError;

public class PositionValidatorTest {
	
	private static final String VALOR_INVALIDO = "Valor inválido! Escolha outro.";
	private static final String CASA_OCUPADA = "A casinha escolhida já está preenchida por uma peça! Escolha outra.";

	@Test
	public void verificaSePassarValorZeroRetornaFalso() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 0);
		assertEquals(check.getErrors().get(0), VALOR_INVALIDO);

	}

	@Test
	public void verificaSePassarValor256RetornaFalso() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 226);
		assertEquals(check.getErrors().get(0), VALOR_INVALIDO);
	}

	@Test
	public void verificaSePassarValorUmRetornaTrue() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 1);
		assertTrue(check.getErrors().isEmpty());

	}

	@Test
	public void verificaSePassarValor255RetornaTrue() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 225);
		assertTrue(check.getErrors().isEmpty());

	}

	@Test
	public void verificaSePassarValor254RetornaTrue() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 224);
		assertTrue(check.getErrors().isEmpty());

	}

	@Test
	public void verificaSeSistemaDeixaColocarPedrinhaEmCasaJaOcupada() {
		Map<Integer, String> tabuleiro = new GuiGomoku().getTabuleiro();
		tabuleiro.put(1, "x");
		FormError check = PositionValidator.check(tabuleiro, 1);
		assertEquals(check.getErrors().get(0), CASA_OCUPADA);
	}

}
