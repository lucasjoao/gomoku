package br.ufsc.ine5430.gomoku;

import br.ufsc.ine5430.gomoku.controller.ControllerGomoku;

public class App {

	public static void main(String[] args) {
		ControllerGomoku controllerHandler = new ControllerGomoku();
		controllerHandler.letsPlay();
	}
}
