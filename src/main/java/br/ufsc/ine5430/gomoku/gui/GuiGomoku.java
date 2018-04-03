package br.ufsc.ine5430.gomoku.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * Classe que compõe toda a interface (via linha de comando) do jogo Gomoku 
 */
public class GuiGomoku {

	private Map<Integer, String> tabuleiro = new HashMap<Integer, String>();

	/**
	 * Inicializa todas as 225 casas do tabuleiro com um identificador único sequencial e seus respectivos valores "vazios".
	 */
	public GuiGomoku() {
		for (int i = 1; i <= 225; i++) {
			this.tabuleiro.put(i, "-");
		}
	}

	/**
	 * Cria uma interface gráfica via linha de comando para interação com o usuário que representa o estado atual do tabuleiro do gomoku.
	 * Imprime o que seria uma matriz 15x15 com seus determinados valores preenchidos.
	 * Valor [ - ] indica uma casa sem nenhuma peça, ou seja, vazia.
	 * Valor [ x ] indica uma casa preenchida com peça humana.
	 * Valor [ o ] indica uma casa preenchida com peça colocada pela máquina.
	 * Ao redor, tem-se impresso, também, os valores correspondentes a linhas e colunas, enumerados de 01 a 15, para facilitar a visualização.
	 */
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

	/**
	 * InputDialog do JOptionPane para pedir ao usuário a linha desejada para colocar sua pecinha.
	 * @return o valor chave da posição x onde possivelmente será colocada a pecinha do humano (int)
	 */
	public int pecaLinha() {
		return PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Qual linha humano (x) vai jogar?"));
	}

	/**
	 * InputDialog do JOptionPane para pedir ao usuário a coluna desejada para colocar sua pecinha.
	 * @return o valor chave da posição y onde possivelmente será colocada a pecinha do humano (int)
	 */
	public int pecaColuna() {
		return PositionValidatorGui.checkInput(JOptionPane.showInputDialog("Qual coluna humano (x) vai jogar?"));
	}
}
