package edu.prajit.gandhi.pdf.creator.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;

public class CellUtility {
    private static final CellUtility CELL_UTILITY= new CellUtility();
    public CellUtility columnSpan(PdfPCell cell,Integer columnSpan){
        cell.setColspan(columnSpan);
        return CELL_UTILITY;
    }
    public CellUtility backgroundColor(PdfPCell cell, BaseColor backgroundColor){
        cell.setBackgroundColor(backgroundColor);
        return CELL_UTILITY;
    }
}
