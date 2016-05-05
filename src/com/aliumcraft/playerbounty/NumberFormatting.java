package com.aliumcraft.playerbounty;

import java.text.DecimalFormat;

public class NumberFormatting {
	public static String format(double value){
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###,###.##");
		return df.format(value);
	}
	
	public static String timeFormat(int time) {
		time = time / 60;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return df.format(time);
	}
}
