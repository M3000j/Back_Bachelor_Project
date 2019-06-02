package com.example.m3000j.backgammon;

class DiceNumbers {
    private byte firstDice;
    private byte secondDice;
    private byte firstTemp;
    private byte secondTemp;

    public int getFirstTemp() {
        return firstTemp;
    }

    public void setFirstTemp(byte firstTemp) {
        this.firstTemp = firstTemp;
    }

    public int getSecondTemp() {
        return secondTemp;
    }

    public void setSecondTemp(byte secondTemp) {
        this.secondTemp = secondTemp;
    }

    public boolean HasEqualNumbers() {
        if (firstDice == secondDice) {
            return true;
        }
        return false;
    }

    public int getFirstDice() {
        return firstDice;
    }

    public void setFirstDice(byte firstDice) {
        this.firstDice = firstDice;
    }

    public int getSecondDice() {
        return secondDice;
    }

    public void setSecondDice(byte secondDice) {
        this.secondDice = secondDice;
    }

    public DiceNumbers(byte firstDice, byte secondDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;
        firstTemp = firstDice;
        secondTemp = secondDice;
    }
}
