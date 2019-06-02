package com.example.m3000j.backgammon;

public class GameStateLists {
    private byte[] ListState = new byte[25];
    private byte[] RedState = new byte[24];
    private byte[] BlackState = new byte[24];


    public void Fill_RB_States() {
        Empty_RB_States();
        FillBlack_States();
        FillRed_States();
    }

    private void Empty_RB_States() {
        for (int i = 0; i < RedState.length; i++) {
            RedState[i] = 0;
        }
        for (int i = 0; i < BlackState.length; i++) {
            BlackState[i] = 0;
        }
    }

    private void FillRed_States() {
        int t = 0;
        for (int i = 0; i < ListState.length; i++) {
            if (ListState[i] < 0) {
                RedState[t] = (byte) i;
                t++;
            }
        }
    }

    private void FillBlack_States() {
        int t = 0;
        for (int i = 1; i < ListState.length; i++) {
            if (ListState[i] > 0) {
                BlackState[t] = (byte) i;
                t++;
            }
        }
    }

    public GameStateLists(byte[] listState) {
        System.arraycopy(listState, 0, this.ListState, 0, listState.length);
//        FillRed_States();
        FillBlack_States();
    }

    public byte[] getListState() {
        return ListState;
    }

    public void setListState(byte[] listState) {
        ListState = listState;
    }

    public byte[] getRedState() {
        return RedState;
    }

    public void setRedState(byte[] redState) {
        RedState = redState;
    }

    public byte[] getBlackState() {
        return BlackState;
    }

    public void setBlackState(byte[] blackState) {
        BlackState = blackState;
    }
}
