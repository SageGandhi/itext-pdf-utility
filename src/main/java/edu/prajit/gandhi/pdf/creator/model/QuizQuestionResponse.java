package edu.prajit.gandhi.pdf.creator.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionResponse {
    private Integer questionNo;
    private Integer questionId;
    private String questionText;
    private String questionImageFile;
    private Integer optionSelected;
    private boolean bookmarkedIndicator;
    private Integer quizQuestionAttemptId;
    private String questionImageS3Url;
    private List<QuizOptionResponse> quizOptions;
}
