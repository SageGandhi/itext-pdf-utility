package edu.prajit.gandhi.pdf.creator.api;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.prajit.gandhi.pdf.creator.service.PdfExporterService;

@RestController
public class PdfExporterApi {
	private static DateTimeFormatter fileNamePattern = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

	@Autowired
	private PdfExporterService pdfExporterService;

	@GetMapping("/quiz/export/pdf")
	public ResponseEntity<byte[]> generateQuizPdf(@RequestParam("quizId") Integer quizId) {
		final String filename = MessageFormat.format("Quiz_{0}_{1}.pdf", String.valueOf(quizId),
				LocalDateTime.now().format(fileNamePattern));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		final byte[] pdfContents = pdfExporterService.generateQuizPdf(quizId);
		return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
	}
}
