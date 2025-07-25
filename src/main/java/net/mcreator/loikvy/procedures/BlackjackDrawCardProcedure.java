package net.mcreator.loikvy.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class BlackjackDrawCardProcedure {
	public static double execute() {
		double card = 0;
		card = Mth.nextInt(RandomSource.create(), 1, 13);
		if (card > 10) {
			card = 10;
		}
		return card;
	}
}