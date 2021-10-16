package edu.prajit.gandhi.pdf.creator.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionResponses {
    private Integer cohortId;
    private Integer cohortQuizAssignmentId;
    private Integer quizId;
    private Integer quizAttemptId;
    private Integer noOfQuestion;
    private String quizStartedAt;
    private Integer timeLimit;
    private String quizDescription;
    private Long initiateTimeStamp;
    private List<QuizQuestionResponse> quizQuestion;
}
