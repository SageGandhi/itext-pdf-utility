package edu.prajit.gandhi.pdf.creator.service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import edu.prajit.gandhi.pdf.creator.model.QuizQuestionResponse;
import edu.prajit.gandhi.pdf.creator.model.QuizQuestionResponses;
import edu.prajit.gandhi.pdf.creator.util.Font;
import lombok.SneakyThrows;

@Service
public class PdfExporterService {
	@Autowired
	@Qualifier("restTemplateSslByPass")
	private RestTemplate restTemplate;

	@SneakyThrows
	public byte[] generateQuizPdf(Integer quizId) {
		final ByteArrayOutputStream contents = new ByteArrayOutputStream();
		// Added For TestOnly
		URL json = HomeworkGeneratePdfService.class.getClassLoader().getResource("quiz-response.json");
		QuizQuestionResponses quiz = new ObjectMapper().readValue(json, QuizQuestionResponses.class);
		// Document Creation Start
		Document document = new Document(PageSize.A4, 36f, 36f, 36f, 36f);
		PdfWriter writer = PdfWriter.getInstance(document, contents);
		document.open();

		try {
			PdfPTable rootTable = new PdfPTable(new float[] { 1f });
			addQuizMetaData(document, "clarifaitech", quiz.getQuizDescription());
			createQuizHeader(quiz, rootTable);
			addQuizDescription(quiz.getQuizDescription(), rootTable);
			addQuizQuestion(quiz, rootTable);
			document.add(rootTable);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			writer.close();
			document.close();
		}
		return contents.toByteArray();
	}

	private void addQuizQuestion(QuizQuestionResponses quiz, PdfPTable rootTable) {
		PdfPTable questionTable = new PdfPTable(new float[] { 1.0f, 9.0f });
		for (QuizQuestionResponse quizQuestion : quiz.getQuizQuestion()) {
			Paragraph paragraph = new Paragraph(quizQuestion.getQuestionText());
			paragraph.setFont(Font.getHelvetica(10f, com.itextpdf.text.Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_LEFT);

			PdfPCell question = new PdfPCell();
			question.addElement(paragraph);
			question.setHorizontalAlignment(Element.ALIGN_CENTER);
			question.setBackgroundColor(BaseColor.WHITE);
			question.setBorder(PdfPCell.NO_BORDER);
			question.setColspan(2);

			questionTable.addCell(question);
		}
		rootTable.addCell(questionTable);
	}

	@SneakyThrows
	private void addQuizDescription(String quizDescription, PdfPTable rootTable) {
		Paragraph quizDescPara = new Paragraph(quizDescription);
		quizDescPara.setFont(Font.getHelvetica(12f, com.itextpdf.text.Font.BOLDITALIC));
		PdfPCell quizDescCell = new PdfPCell(quizDescPara);
		quizDescCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		quizDescCell.setBorder(PdfPCell.NO_BORDER);
		rootTable.addCell(quizDescCell);
	}

	@SneakyThrows
	private void createQuizHeader(QuizQuestionResponses quiz, PdfPTable rootTable) {
		byte[] logoImg = restTemplate.getForObject("https://services.clarifaitech.com/assets/img/logo.jpg",
				byte[].class);
		Image clarifaiLogo = Image.getInstance(logoImg);
		PdfPCell logo = new PdfPCell(clarifaiLogo);
		logo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		logo.setBorder(PdfPCell.NO_BORDER);
		rootTable.addCell(logo);
	}

	private void addQuizMetaData(Document document, String author, String quizDescription) {
		document.addAuthor(author);
		document.addCreationDate();
		document.addCreator(author);
		document.addSubject(quizDescription);
		document.addTitle(quizDescription);
	}
	
	private Paragraph withSuperScript(String text) {
		final Pattern pattern = Pattern.compile("(<sup>)(.*)(<\\/sup>)");
		final Matcher matcher = pattern.matcher(text);
		matcher.find();
		return null;
	}
}
