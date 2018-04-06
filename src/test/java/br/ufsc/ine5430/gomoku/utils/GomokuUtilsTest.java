package br.ufsc.ine5430.gomoku.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GomokuUtilsTest {

	@Test
	public void verificaSeRetornoDaFuncaoPosInMapEh140Passando10RowE5Col() {
		int row = 10;
		int col = 5;		
		int posInMap = GomokuUtils.posInMap(row, col);
		assertEquals(140, posInMap);
	}
	
	@Test
	public void verificaSeRetornoDaFuncaoPosInMapEh1Passando1RowE1Col() {
		int row = 1;
		int col = 1;		
		int posInMap = GomokuUtils.posInMap(row, col);
		assertEquals(1, posInMap);
	}
	
	@Test
	public void verificaSeRetornoDaFuncaoPosInMapEh225Passando15RowE15Col() {
		int row = 15;
		int col = 15;		
		int posInMap = GomokuUtils.posInMap(row, col);
		assertEquals(225, posInMap);
	}
	
}
