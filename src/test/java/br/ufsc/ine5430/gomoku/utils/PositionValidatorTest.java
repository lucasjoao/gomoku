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
		assertEquals(VALOR_INVALIDO, check.getErrors().get(0));

	}

	@Test
	public void verificaSePassarValor256RetornaFalso() {
		FormError check = PositionValidator.check(new GuiGomoku().getTabuleiro(), 226);
		assertEquals(VALOR_INVALIDO, check.getErrors().get(0));
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
		assertEquals(CASA_OCUPADA, check.getErrors().get(0));
	}
	
	@Test
	public void checaRetornoDoCheckInputLevandoUmaLetra() {
		int menorInteiro = PositionValidator.checkInput("S");
		assertEquals(Integer.MIN_VALUE, menorInteiro);
	}
	
	@Test public void checaRetornoDoCheckInputLevandoUmValorValido() {
		int valor1Valido = PositionValidator.checkInput("1");
		assertEquals(1, valor1Valido);
	}
	
	@Test public void checaRetornoDoCheckInputLevandoUmValorInvalidoNegativo() {
		int valorInvalidoNegativo = PositionValidator.checkInput("-1");
		assertEquals(Integer.MIN_VALUE, valorInvalidoNegativo);
	}
	
	@Test public void checaRetornoDoCheckInputLevandoUmValorInvalidoZerado() {
		int valorInvalidoZerado = PositionValidator.checkInput("0");
		assertEquals(Integer.MIN_VALUE, valorInvalidoZerado);
	}
	
	@Test public void checaRetornoDoCheckInputLevandoUmValorInvalidoAcimaDe15() {
		int valorInvalido16 = PositionValidator.checkInput("16");
		assertEquals(Integer.MIN_VALUE, valorInvalido16);
	}
	
	@Test public void checaRetornoDoCheckInputLevandoUmValorValidoLimiteSuperior() {
		int valorValido15 = PositionValidator.checkInput("15");
		assertEquals(15, valorValido15);
	}
}
