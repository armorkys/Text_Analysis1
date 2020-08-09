package com.example.Text_Analysis.model;

public class MatchingString {

    private char letter;
    private int amount;
    private String phrases;


    public MatchingString(char letter, String phrases) {
        this.letter = letter;
        this.amount = 1;
        this.phrases = phrases;
    }

    public MatchingString(char letter, int amount, String phrases) {
        this.letter = letter;
        this.amount = amount;
        this.phrases = phrases;
    }

    public void addMatch(String phrases) {
        this.amount++;
        this.phrases += " " + phrases;
    }

    public char getLetter() {
        return letter;
    }

    public int getAmount() {
        return amount;
    }

    public String getPhrases() {
        return phrases;
    }

    public String toString() {
        return this.letter + " " + this.amount + " " + phrases;
    }
}
