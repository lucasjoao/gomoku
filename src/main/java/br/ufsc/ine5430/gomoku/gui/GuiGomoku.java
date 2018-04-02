package br.ufsc.ine5430.gomoku.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GuiGomoku {

	private Map<Integer, String> tabuleiro = new HashMap<Integer, String>();

	public GuiGomoku() {
		for (int i = 1; i <= 225; i++) {
			tabuleiro.put(i, "-");
		}
	}

	public void printaMap() {

		System.out.print("01");
		for (int j = 1; j <= 225; j++) {

			System.out.print(" [ " + tabuleiro.get(j) + " ] ");
			if(j == 225) {
				break;
			}
			if (j % 15 == 0) {
				System.out.println();
				if(j / 15 < 9) {
					System.out.print("0" + ((j/15)+1));
				} else {
					System.out.print(((j/15) + 1));
				}
			}

		}
		
		System.out.println("\n     A      B      C      D      F      G      H      I      J      K      L      M      N      O      P");

	}

	public int pecaLinha() {
		String linha = JOptionPane.showInputDialog("Qual linha humano (x) vai jogar?");
		return Integer.parseInt(linha);
	}

	public int pecaColuna() {
		String coluna = JOptionPane.showInputDialog("Qual coluna humano (x) vai jogar?");
		return Integer.parseInt(coluna);
	}

	public void printaMatrizNoTerminal() {
		for (int i = 1; i <= 15; i++) {
			if (i < 10) {
				System.out.print("0" + i);
			} else {
				System.out.print(i);
			}
			for (int j = 0; j < 15; j++) {
				System.out.print(" [-] ");
			}
			System.out.println("\n");
		}
		System.out.println("    A    B    C    D    F    G    H    I    J    K    L    M    N    O    P");
	}

}
