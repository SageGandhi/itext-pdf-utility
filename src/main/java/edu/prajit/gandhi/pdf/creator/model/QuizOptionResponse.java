package edu.prajit.gandhi.pdf.creator.model;

import lombok.Data;

@Data
public class QuizOptionResponse {
    private Integer optionId;
    private String optionText;
    private String optionFormat;
    private String feedBack;
    private String feedBackFormat;
    private Integer optionSelected;
    private String optionImageS3Url;
}
