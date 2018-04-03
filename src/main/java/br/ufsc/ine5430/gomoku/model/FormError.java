package br.ufsc.ine5430.gomoku.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormError {

	private List<String> errors;
	
	public FormError() {
		this.errors = new ArrayList<>();
	}
	
}
