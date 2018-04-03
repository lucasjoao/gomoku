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
			this.tabuleiro.put(i, "-");
		}
	}

	public void printaMap() {
		System.out.println();
		System.out.println("Tabuleiro atual:");
		System.out.print("01");
		for (int j = 1; j <= 225; j++) {

			System.out.print(" [ " + this.tabuleiro.get(j) + " ] ");
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

		System.out.println("\n    01     02     03     04     05     06     07     08     09     10     11     12     13     14     15");
	}

	public int pecaLinha() {
		return PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Qual linha humano (x) vai jogar?"));
	}

	public int pecaColuna() {
		return PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Qual coluna humano (x) vai jogar?"));
	}
}
