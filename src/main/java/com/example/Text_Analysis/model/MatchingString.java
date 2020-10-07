package com.example.Text_Analysis.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchingString that = (MatchingString) o;
        return letter == that.letter &&
                amount == that.amount &&
                Objects.equals(phrases, that.phrases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, amount, phrases);
    }
}
