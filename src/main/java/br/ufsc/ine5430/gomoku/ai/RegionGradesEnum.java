package br.ufsc.ine5430.gomoku.ai;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegionGradesEnum {

	CANTO(10),
	LATERAL(41),
	CENTRO(165);

	private int grade;

	public static int calculate(List<int[]> extremes) {
		int score = 0;
		for (int[] extreme : extremes) {
			score += RegionGradesEnum.getScoreByRegion(extreme);
		}
		return score;
	}

	private static int getScoreByRegion(int[] extreme) {
		int score = 0;
		int row = extreme[0];
		int col = extreme[1];
		if (4 < row && row < 10) {
			if (4 < col && col < 10) {
				score += RegionGradesEnum.CENTRO.getGrade();
			} else {
				score += RegionGradesEnum.LATERAL.getGrade();
			}
		} else {
			if (4 < col && col < 10) {
				score += RegionGradesEnum.LATERAL.getGrade();
			} else {
				score += RegionGradesEnum.CANTO.getGrade();
			}
		}
		return score;
	}
}
