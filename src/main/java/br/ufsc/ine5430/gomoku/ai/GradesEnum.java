package br.ufsc.ine5430.gomoku.ai;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GradesEnum {

	ZERO(0, 0),
	UNITARIO(1, 1),
	DUPLA(2, 114),
	TRIPLA(3, 6385),
	QUADRUPLA(4, 236246),
	QUINTUPLA(5, Integer.MAX_VALUE),
	// TODO: esta certo manter os tres proximos aqui?
	CANTO(6, 10),
	LATERAL(7, 41),
	CENTRO(8, 165);

	private int index;
	private int grade;

	private static GradesEnum getByIndex(int index) {
		for (GradesEnum grade : GradesEnum.values()) {
			if (grade.getIndex() == index) {
				return grade;
			}
		}
		return null;
	}

	public static int calculate(int sequence, List<int[]> extremes) {
		int score = 0;

		if (sequence > 5) {
			sequence = 5;
		}

		score += GradesEnum.getByIndex(sequence).getGrade();

		for (int[] extreme : extremes) {
			score += GradesEnum.getScoreByRegion(extreme);
		}

		return score;
	}

	// TODO: tem como deixar essa coisa mais bonita?
	private static int getScoreByRegion(int[] extreme) {
		int score = 0;
		int row = extreme[0];
		int col = extreme[1];
		if (4 < row && row < 10) {
			if (4 < col && col < 10) {
				score += GradesEnum.CENTRO.getGrade();
			} else {
				score += GradesEnum.LATERAL.getGrade();
			}
		} else {
			if (4 < col && col < 10) {
				score += GradesEnum.LATERAL.getGrade();
			} else {
				score += GradesEnum.CANTO.getGrade();
			}
		}
		return score;
	}
}
