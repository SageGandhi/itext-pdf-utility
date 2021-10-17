package edu.prajit.gandhi.pdf.creator.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font.FontFamily;

public class Font {
	public static final com.itextpdf.text.Font getHelvetica(float fontSize) {
		return getHelvetica(fontSize, com.itextpdf.text.Font.NORMAL);
	}

	public static final com.itextpdf.text.Font getHelvetica(float fontSize, int styleFont) {
		return getHelvetica(fontSize, styleFont, BaseColor.BLACK);
	}

	public static final com.itextpdf.text.Font getHelvetica(float fontSize, int styleFont, BaseColor color) {
		return new com.itextpdf.text.Font(FontFamily.HELVETICA, fontSize, styleFont, color);
	}
}
