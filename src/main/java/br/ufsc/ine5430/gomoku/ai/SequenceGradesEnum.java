package br.ufsc.ine5430.gomoku.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SequenceGradesEnum {

	ZERO(0, 0),
	UNITARIO(1, 1),
	DUPLA(2, 114),
	TRIPLA(3, 6385),
	QUADRUPLA(4, 236246),
	QUINTUPLA(5, Integer.MAX_VALUE);

	private int index;
	private int grade;

	private static SequenceGradesEnum getByIndex(int index) {
		for (SequenceGradesEnum grade : SequenceGradesEnum.values()) {
			if (grade.getIndex() == index) {
				return grade;
			}
		}
		return null;
	}

	public static int calculate(int sequence) {
		int score = 0;

		if (sequence > 5) {
			sequence = 5;
		}

		score += SequenceGradesEnum.getByIndex(sequence).getGrade();

		return score;
	}
}
