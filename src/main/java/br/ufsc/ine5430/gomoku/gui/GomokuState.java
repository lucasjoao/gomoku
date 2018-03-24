package br.ufsc.ine5430.gomoku.gui;
public class GomokuState {

	private int size;
	private EstadoGomokuEnum estado;
	
	public GomokuState(int size, EstadoGomokuEnum state) {
		this.size = size;
		this.estado = state;
	}

	public EstadoGomokuEnum getEstado(){
		return this.estado;
	}
	
}
