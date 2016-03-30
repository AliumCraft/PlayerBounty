package com.aliumcraft;

import java.text.DecimalFormat;

public class NumberFormatting {
	public static String format(double value){
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###,###.##");
		return df.format(value);
	}
}
