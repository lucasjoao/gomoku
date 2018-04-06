package br.ufsc.ine5430.gomoku.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa o formulário de erros que é utilizado para validações
 */
@Getter
@Setter
public class FormError {

	/**
	 * Lista de string que possui os erros que podem ser originados a partir de uma validação
	 *
	 * @see List
	 */
	private List<String> errors;

	/**
	 * Construtor que inicializa com um ArrayList vazio o atributo
	 *
	 * @see #errors
	 * @see ArrayList
	 */
	public FormError() {
		this.errors = new ArrayList<>();
	}

}
