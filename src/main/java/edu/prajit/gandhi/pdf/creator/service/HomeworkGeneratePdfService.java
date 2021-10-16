package edu.prajit.gandhi.pdf.creator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import edu.prajit.gandhi.pdf.creator.model.QuizOptionResponse;
import edu.prajit.gandhi.pdf.creator.model.QuizQuestionResponse;
import edu.prajit.gandhi.pdf.creator.model.QuizQuestionResponses;

import java.io.FileOutputStream;
import java.net.URL;

public class HomeworkGeneratePdfService {
    public static void main(String[] args) throws Exception {
        URL json=HomeworkGeneratePdfService.class.getClassLoader().getResource("quiz-response.json");
        QuizQuestionResponses quiz = new ObjectMapper().readValue(json, QuizQuestionResponses.class);

        FileOutputStream byteArrayOutputStream = new FileOutputStream("01.pdf");
        Document document = new Document(PageSize.A4, 36f, 36f, 36f, 36f);
        PdfWriter writer = PdfWriter.getInstance(document,byteArrayOutputStream);
        document.open();
        PdfPTable rootTable=new PdfPTable(1);
        Paragraph paragraph = new Paragraph(quiz.getQuizDescription());
        paragraph.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(168,144,255));
        rootTable.addCell(cell);

        PdfPTable pdfPTable = new PdfPTable(new float[]{1.0f,9.0f});

        for(QuizQuestionResponse quizQuestion:quiz.getQuizQuestion()){
            paragraph = new Paragraph(quizQuestion.getQuestionText());
            paragraph.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL));
            paragraph.setAlignment(Element.ALIGN_LEFT);

            cell = new PdfPCell();
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setColspan(2);

            pdfPTable.addCell(cell);

            for(QuizOptionResponse quizOpt:quizQuestion.getQuizOptions()) {
                Paragraph para = new Paragraph(quizOpt.getOptionText());
                para.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL));
                para.setAlignment(Element.ALIGN_LEFT);

                PdfPCell cellOpt = new PdfPCell();
                cellOpt.addElement(para);
                cellOpt.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellOpt.setBackgroundColor(BaseColor.WHITE);
                cellOpt.setColspan(2);

                pdfPTable.addCell(cellOpt);
            }

        }
        rootTable.addCell(pdfPTable);
        document.add(rootTable);

        document.close();
        writer.close();

    }
    public static class TableBackground implements PdfPTableEvent {

        private final BaseColor backgroundColor;

        public TableBackground(BaseColor backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public void tableLayout(PdfPTable table, float[][] width, float[] height,
                                int headerRows, int rowStart, PdfContentByte[] canvas) {
            float xmin = width[0][0];
            float xmax = width[0][1];
            float ymin = height[height.length - 1];
            float ymax = height[0];
            float d = 3;

            PdfContentByte background = canvas[PdfPTable.BASECANVAS];
            background.saveState();
            background.setColorFill(backgroundColor);
            background.roundRectangle(
                    xmin - d,
                    ymin - d,
                    (xmax + d) - (xmin - d),
                    (ymax + d) - (ymin - d),
                    d + d);
            background.fill();
            background.restoreState();
        }

    }

}
