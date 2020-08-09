package com.example.Text_Analysis.model;

import java.util.List;

public class AnalysisCase {


    private String startingInput;

    private List<MatchingString> letterCase;


    //constructors
    public AnalysisCase() {
        startingInput = null;
        letterCase = null;
    }

    public AnalysisCase(String startingInput, List<MatchingString> letterCase) {
        this.startingInput = startingInput;
        this.letterCase = letterCase;
    }



    public void addLetterCase(MatchingString letterCase) {
        this.letterCase.add(letterCase);
    }

    public String getStartingInput() {
        return startingInput;
    }

    public void setStartingInput(String startingInput) {
        this.startingInput = startingInput;
    }

    public List<MatchingString> getLetterCase() {
        return letterCase;
    }

    public void setLetterCase(List<MatchingString> letterCase) {
        this.letterCase = letterCase;
    }
}
