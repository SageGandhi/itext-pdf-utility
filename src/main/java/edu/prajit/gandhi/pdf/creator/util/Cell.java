package edu.prajit.gandhi.pdf.creator.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;

public class Cell extends PdfPCell {
	public static Cell create() {
		return new Cell();
	}

	public Cell columnSpan(Integer columnSpan) {
		this.setColspan(columnSpan);
		return this;
	}

	public Cell backgroundColor(BaseColor backgroundColor) {
		this.setBackgroundColor(backgroundColor);
		return this;
	}
}
