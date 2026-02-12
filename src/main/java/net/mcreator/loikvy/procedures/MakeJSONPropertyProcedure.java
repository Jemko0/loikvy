package net.mcreator.loikvy.procedures;

public class MakeJSONPropertyProcedure {
	public static String execute(boolean brackets, boolean comma, String inner, String outer) {
		if (inner == null || outer == null)
			return "";
		String temp = "";
		if (brackets) {
			temp = "\"" + "" + outer + "\":{" + inner + "}";
		} else {
			temp = "\"" + "" + outer + "\":\"" + inner + "\"";
		}
		if (comma) {
			return temp + ",";
		}
		return temp;
	}
}