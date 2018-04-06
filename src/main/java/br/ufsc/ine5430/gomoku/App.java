package br.ufsc.ine5430.gomoku;

import br.ufsc.ine5430.gomoku.controller.ControllerGomoku;

/**
 * Classe que possui o método principal que da um start no jogo
 */
public class App {

	public static void main(String[] args) {
		ControllerGomoku controllerHandler = new ControllerGomoku();
		controllerHandler.letsPlay();
	}
}
