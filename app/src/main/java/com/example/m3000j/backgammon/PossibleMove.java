package com.example.m3000j.backgammon;

import java.util.ArrayList;
import java.util.List;

import static com.example.m3000j.backgammon.GameActivity.canBlackRemove;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;

public class PossibleMove {

    private byte from = 0;
    private byte to = 0;
    private double weight;
    private double children_maxWieght = 0;

    public double getChildren_maxWieght() {
        return children_maxWieght;
    }

    public void setChildren_maxWieght(double children_maxWieght) {
        this.children_maxWieght = children_maxWieght;
    }

    GameStateLists gameStateLists;
    GameNode gameNode = new GameNode();

    public PossibleMove(byte from, byte to, byte[] newStateList) {
        this.from = from;
        this.to = to;
        if (GameActivity.difficulty == Difficulty.Medium || GameActivity.difficulty == Difficulty.Hard) {
            gameStateLists = new GameStateLists(newStateList);
        }
    }

    public List<PossibleMove> FindPossibleMoves(byte dice) {
        List<PossibleMove> temp_possibleMoves = new ArrayList<>();
        int lastIndex = 0;
        if (canBlackRemove && !isRedTurn) {
            lastIndex = LastFullRoom();
            if (gameStateLists.getListState()[25 - dice] > 0) {
                temp_possibleMoves.add(new PossibleMove((byte) (25 - dice), (byte) -1, gameStateLists.getListState()));
            } else if (gameStateLists.getListState()[25 - dice] == 0 && dice > lastIndex) {
                temp_possibleMoves.add(new PossibleMove((byte) (25 - lastIndex), (byte) -1, gameStateLists.getListState()));
            }
        }
        if (!isRedTurn) {
            for (int i = 0; i < gameStateLists.getBlackState().length; i++) {
                if (gameStateLists.getBlackState()[i] != 0) {
                    if (gameStateLists.getBlackState()[i] + dice <= 24 && gameStateLists.getListState()[dice + gameStateLists.getBlackState()[i]] >= -1) {
                        temp_possibleMoves.add(new PossibleMove((gameStateLists.getBlackState()[i]), (byte) (dice + gameStateLists.getBlackState()[i]), gameStateLists.getListState()));
                    }
                }
            }
        }
        return temp_possibleMoves;
    }

    public void evaluate(HitFunctionClass hitFunctionClass, byte to) { // Huristic
        double black_doors_eval = 0;
        double red_doors_eval = 0;
        double total_Evalue = 0;
        byte black_Count = 0;
        for (int i = 0; i < gameStateLists.getListState().length; i++) {
            if (gameStateLists.getListState()[i] > 0) {
                black_Count += gameStateLists.getListState()[i];
            }
        }
        for (int i = 0; i < gameStateLists.getListState().length; i++) {
            if (gameStateLists.getListState()[i] > 0) {
                if (i >= 17 && i <= 19) {
                    if (gameStateLists.getListState()[i] == 2 || gameStateLists.getListState()[i] == 3) {
                        black_doors_eval += 4;
                    } else {
                        black_doors_eval -= 4;
                    }
                } else if (i >= 5 && i <= 8) {
                    if (gameStateLists.getListState()[i] == 2 || gameStateLists.getListState()[i] == 3) {
                        black_doors_eval += 4;
                    } else {
                        black_doors_eval -= 4;
                    }
                } else {
                    if (gameStateLists.getListState()[i] == 2) {
                        black_doors_eval += 2;
                    } else if (gameStateLists.getListState()[i] == 3) {
                        black_doors_eval += 1;
                    } else {
                        black_doors_eval -= 4;
                    }
                }
            }
        }
        total_Evalue = 0.01 * (black_doors_eval - red_doors_eval) + 0.29 * (hitFunctionClass.getRed_Hits() - hitFunctionClass.getBlack_Hits());
        weight = total_Evalue;
        if (black_Count <= 15 && to == -1) {
            weight = 100;
        }
    }

    private double Count_Doors_In_Important_Places(int i) {
        double black_doors_eval = 0;
        if (i == 18 || i == 20) {
            black_doors_eval += 5;
        } else if (i >= 5 && i <= 7) {
            black_doors_eval += 4;
        } else if (i >= 23) {
            black_doors_eval += 0.5;
        } else {
            black_doors_eval += 1;
        }
        return black_doors_eval;
    }

    private double Count_Three_Doors_In_Important_Places(int i) {
        double black_doors_eval = 0;
        if (i == 18 || i == 20) {
            black_doors_eval += 4;
        } else if (i >= 5 && i <= 7) {
            black_doors_eval -= 1;
        } else {
            black_doors_eval += 0.5;
        }
        return black_doors_eval;
    }

    private int LastFullRoom() {
        for (int i = 19; i <= 24; i++) {
            if (gameStateLists.getListState()[i] > 0) {
                return 25 - i;
            }
        }
        return 25 - 24;
    }

    public void MakeNewState_For_One_PossibleMove() {
        gameStateLists.getListState()[from] -= 1;
        if (to >= 0) {
            if (gameStateLists.getListState()[to] == -1) {
                gameStateLists.getListState()[to] = 1;
            } else {
                gameStateLists.getListState()[to] += 1;
            }
        }

    }

    public byte getFrom() {
        return from;
    }

    public void setFrom(byte from) {
        this.from = from;
    }

    public byte getTo() {
        return to;
    }

    public void setTo(byte to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
