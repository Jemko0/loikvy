package net.mcreator.loikvy.procedures;

import org.checkerframework.checker.units.qual.min;

public class ClampNumberProcedure {
	public static double execute(double max, double min, double num) {
		double baseRate = 0;
		double rateWithAmplifier = 0;
		return Math.max(min, Math.min(num, max));
	}
}