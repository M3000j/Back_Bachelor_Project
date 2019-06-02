package com.example.m3000j.backgammon;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import static com.example.m3000j.backgammon.GameActivity.aiClass;
import static com.example.m3000j.backgammon.GameActivity.canBlackRemove;
import static com.example.m3000j.backgammon.GameActivity.canRedRemove;
import static com.example.m3000j.backgammon.GameActivity.difficulty;
import static com.example.m3000j.backgammon.GameActivity.isMove;
import static com.example.m3000j.backgammon.GameActivity.isPair;
import static com.example.m3000j.backgammon.GameActivity.isParentChanged;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;
import static com.example.m3000j.backgammon.GameActivity.numMoves;
import static com.example.m3000j.backgammon.GameActivity.primeIndex;
import static com.example.m3000j.backgammon.GameActivity.tempindex;

public class DiceClass {
    final Context context;
    TextView _Txt_Second_Dice, _Txt_First_Dice;
    LinearLayout _Linear_Red_Turn, _Linear_Black_Turn;
    private int red_moves = 278, black_moves = 278;
    TextView _Txt_Black_Move, _Txt_Red_Move;
    private DiceNumbers diceTemp = new DiceNumbers((byte) 0, (byte) 0);
    private boolean firstDiceisUsed = true;//easy and medium
    private boolean firstdicechoosed = false;//medium
    Handler handler;

    public boolean isFirstDiceisUsed() {
        return firstDiceisUsed;
    }

    public void setFirstDiceisUsed(boolean firstDiceisUsed) {
        this.firstDiceisUsed = firstDiceisUsed;
    }

    public DiceNumbers getDiceTemp() {
        return diceTemp;
    }

    public void setDiceTemp(DiceNumbers diceTemp) {
        this.diceTemp = diceTemp;
    }

    public int getRed_moves() {
        return red_moves;
    }

    public void setRed_moves(int red_moves) {
        this.red_moves = red_moves;
    }

    public int getBlack_moves() {
        return black_moves;
    }

    public void setBlack_moves(int black_moves) {
        this.black_moves = black_moves;
    }

    private Random random = new Random();

    public DiceClass(Context context) {
        this.context = context;
    }

    private int GetDiceNumber() {
        Float myNumber = random.nextFloat();
        Log.d("DICE", myNumber.toString());
        if (myNumber < 0.16)
            return 1;
        else if (myNumber > 0.16 && myNumber <= 0.32) {
            return 2;
        } else if (myNumber >= 0.32 && myNumber < 0.48) {
            return 3;
        } else if (myNumber >= 0.48 && myNumber < 0.64) {
            return 4;
        } else if (myNumber >= 0.64 && myNumber < 0.8) {
            return 5;
        } else
            return 6;
    }

    public void RollDice() {
        _Txt_Second_Dice.setTextColor(Color.WHITE);
        _Txt_First_Dice.setTextColor(Color.WHITE);
        diceTemp.setFirstDice((byte) GetDiceNumber());
        diceTemp.setSecondDice((byte) GetDiceNumber());
        diceTemp.setFirstTemp((byte) diceTemp.getFirstDice());
        diceTemp.setSecondTemp((byte) diceTemp.getSecondDice());
        if (diceTemp.HasEqualNumbers()) {
            numMoves = 4;
            isPair = true;
        } else {
            numMoves = 2;
            isPair = false;
        }
        _Txt_First_Dice.setText(String.valueOf(diceTemp.getFirstDice()));
        _Txt_Second_Dice.setText(String.valueOf(diceTemp.getSecondDice()));
    }

    public void CheckDice() {
        if (isPair) {
            if (isRedTurn) {
                red_moves = red_moves - diceTemp.getFirstTemp();
                _Txt_Red_Move.setText(String.valueOf(red_moves));
            } else {
                black_moves = black_moves - diceTemp.getFirstTemp();
                _Txt_Black_Move.setText(String.valueOf(black_moves));
            }
            if (numMoves == 3) {
                _Txt_First_Dice.setTextColor(Color.GRAY);
                diceTemp.setFirstDice((byte) -2);
            }
            if (numMoves == 1) {
                _Txt_Second_Dice.setTextColor(Color.GRAY);
                diceTemp.setSecondDice((byte) -2);
            }
        } else {
            if (firstDiceisUsed) {
                if (isRedTurn) {
                    red_moves = red_moves - diceTemp.getFirstDice();
                    _Txt_Red_Move.setText(String.valueOf(red_moves));
                } else {
                    black_moves = black_moves - diceTemp.getFirstDice();
                    _Txt_Black_Move.setText(String.valueOf(black_moves));
                }
                diceTemp.setFirstDice((byte) -2);
                _Txt_First_Dice.setTextColor(Color.GRAY);
            } else {
                if (isRedTurn) {
                    red_moves = red_moves - diceTemp.getSecondDice();
                    _Txt_Red_Move.setText(String.valueOf(red_moves));
                } else {
                    black_moves = black_moves - diceTemp.getSecondDice();
                    _Txt_Black_Move.setText(String.valueOf(black_moves));
                }
                diceTemp.setSecondDice((byte) -2);
                _Txt_Second_Dice.setTextColor(Color.GRAY);
            }
        }
    }

    ///here the ai functions should be sound
    public void ChangeTurn(boolean blackView) {
        if (blackView) {
            if (isMove) {
                if (tempindex == primeIndex) {
                    if (isParentChanged) {
                        numMoves--;
                        if (numMoves <= 0) {
                            isRedTurn = true;
                            _Linear_Red_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                            _Linear_Black_Turn.setBackground(null);
                        } else {
                            isRedTurn = false;
                            do_Ai_Functions(false);
                        }
                    } else {
                        isRedTurn = false;
                    }
                } else {
                    numMoves--;
                    if (numMoves <= 0) {
                        isRedTurn = true;
                        _Linear_Red_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                        _Linear_Black_Turn.setBackground(null);
                    } else {
                        isRedTurn = false;
                        do_Ai_Functions(false);
                    }
                }
            } else {
                isRedTurn = false;
            }
        } else {
            if (isMove) {
                if (tempindex == primeIndex) {
                    if (isParentChanged) {
                        numMoves--;
                        if (numMoves <= 0) {
                            isRedTurn = false;
                            _Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                            _Linear_Red_Turn.setBackground(null);
                            Log.d("turn", "ChooseView: " + "changed");

                            //ai related functions
                            do_Ai_Functions(true);
                        }
                    } else {
                        if (canRedRemove) {
                            numMoves--;
                            if (numMoves <= 0) {
                                isRedTurn = false;
                                _Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                                _Linear_Red_Turn.setBackground(null);
                                Log.d("turn", "ChooseView: " + "changed");

                                //ai related functions
                                do_Ai_Functions(true);
                            }
                        } else {
                            isRedTurn = true;
                        }
                    }
                } else {
                    numMoves--;
                    if (numMoves <= 0) {
                        isRedTurn = false;
                        _Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                        _Linear_Red_Turn.setBackground(null);
                        //ai related functions
                        Log.d("turn", "ChooseView: " + "changed");

                        do_Ai_Functions(true);
                    }
                }
            } else {
                isRedTurn = true;
            }
        }
    }

    private PossibleMove MaxWieghtPossibleMoves_FirstList(List<PossibleMove> possibleMoveList) {
        if (possibleMoveList.size() != 0) {
            for (int i = 0; i < possibleMoveList.size(); i++) {
                aiClass.MakeBoardFill(possibleMoveList.get(i));
            }
            double max = possibleMoveList.get(0).getWeight();
            int temp_Index = 0;
            for (int i = 0; i < possibleMoveList.size(); i++) {
                if (max < possibleMoveList.get(i).getWeight()) {
                    temp_Index = i;
                }
            }
            return possibleMoveList.get(temp_Index);
        }
        return null;
    }

    private PossibleMove MaxWieghtPossibleMoves_Main(PossibleMove possibleMove_First, PossibleMove possibleMove_Second) {
        if (possibleMove_First == null && possibleMove_Second == null)
            return null;
        else if (possibleMove_First == null) {

            firstdicechoosed = false;
            return possibleMove_Second;

        } else if (possibleMove_Second == null) {

            firstdicechoosed = true;
            return possibleMove_First;

        } else if (possibleMove_First.getWeight() > possibleMove_Second.getWeight()) {

            firstdicechoosed = true;
            return possibleMove_First;

        } else {

            firstdicechoosed = false;
            return possibleMove_Second;

        }
    }

    public boolean isFirstdicechoosed() {
        return firstdicechoosed;
    }

    public void setFirstdicechoosed(boolean firstdicechoosed) {
        this.firstdicechoosed = firstdicechoosed;
    }

    public void do_Ai_Functions(boolean first) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!GameState.ismanual) {
                    if (aiClass.getAi_Dices().getDiceTemp().getSecondDice() < 0 && aiClass.getAi_Dices().getDiceTemp().getFirstDice() < 0) {
                        if (!isRedTurn) {
                            GameActivity.aiClass.RollDice();
                            Log.d("dice", GameActivity.aiClass.getAi_Dices().getDiceTemp().getFirstDice() + " " + GameActivity.aiClass.getAi_Dices().getDiceTemp().getSecondDice());
                        }
                    }
                    if (!aiClass.isRemoving && difficulty == Difficulty.Easy && !isRedTurn) {
                        EasyAiFunctions(first);
                    } else if (!aiClass.isRemoving && difficulty == Difficulty.Medium) {
                        MediumAiFunctions();
                    } else if (!aiClass.isRemoving && difficulty == Difficulty.Hard) {
                        if (aiClass.getAi_Dices().getDiceTemp().getSecondTemp() > 0 && aiClass.getAi_Dices().getDiceTemp().getFirstDice() > 0) {
                            HardAiFunctions();
                        }
                    }
                }
            }
        }, 1000);
    }

    public void HardAiFunctions() {
        isRedTurn = false;
        if (isPair) {
            for (int i = 0; i < 2; i++) {
                aiClass.getAi_Dices().getDiceTemp().setFirstDice((byte) aiClass.getAi_Dices().getDiceTemp().getSecondTemp());
                GameActivity.aiClass.getAi_gameState().setCanMove(true);
                GameActivity.aiClass.HandlPossibleState();
                GameActivity.aiClass.getAi_gameNode().MakeNewStatesForPossibleMoves(aiClass);
                GameActivity.aiClass.getAi_gameNode().ChooseMove(GameActivity.aiClass);
            }
            aiClass.getAi_Dices().getDiceTemp().setFirstDice((byte) -2);
            aiClass.getAi_Dices().getDiceTemp().setSecondDice((byte) -2);
        } else {
            GameActivity.aiClass.getAi_gameState().setCanMove(true);
            GameActivity.aiClass.HandlPossibleState();
            GameActivity.aiClass.getAi_gameNode().MakeNewStatesForPossibleMoves(GameActivity.aiClass);//ta inja dar khrdan moshkeli nist
            GameActivity.aiClass.getAi_gameNode().ChooseMove(GameActivity.aiClass);
        }
        GameState.ismanual = true;
    }

    private void EasyAiFunctions(boolean first) {
        GameActivity.aiClass.getAi_gameState().setCanMove(true);
        GameActivity.aiClass.HandlPossibleState();
        GameActivity.aiClass.MovePossibleMove(first, null);
    }

    private void MediumAiFunctions() {

        GameActivity.aiClass.getAi_gameState().setCanMove(true);
        GameActivity.aiClass.HandlPossibleState();
        if (!isPair) {
            if (aiClass.getAi_Dices().getDiceTemp().getSecondDice() > 0 && aiClass.getAi_Dices().getDiceTemp().getFirstDice() > 0) {
                PossibleMove maxfirstmove = null;
                PossibleMove maxsecondmove = null;
                if (aiClass.getAi_hitFunctionClass().getBlack_Hits() == 0) {
                    maxfirstmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getFirstDice_PossibleMoves());
                    maxsecondmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getSecondDice_PossibleMoves());
                }
                aiClass.MovePossibleMove(firstdicechoosed, MaxWieghtPossibleMoves_Main(maxfirstmove, maxsecondmove));
            } else if (firstdicechoosed) {
                PossibleMove maxfirstmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getSecondDice_PossibleMoves());
                if (maxfirstmove == null && canBlackRemove) {
                    PossibleMove mixmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getFirstDice_PossibleMoves());
                    aiClass.MovePossibleMove(true, mixmove);
                } else {
                    aiClass.MovePossibleMove(true, maxfirstmove);
                }
            } else if (!firstdicechoosed) {
                PossibleMove maxfirstmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getFirstDice_PossibleMoves());
                if (maxfirstmove == null && canBlackRemove) {
                    PossibleMove mixmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getSecondDice_PossibleMoves());
                    aiClass.MovePossibleMove(true, mixmove);
                } else {
                    aiClass.MovePossibleMove(true, maxfirstmove);
                }
            }
        } else {
            if (aiClass.getAi_Dices().getDiceTemp().getFirstDice() > 0) {
                PossibleMove maxfirstmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getFirstDice_PossibleMoves());
                aiClass.MovePossibleMove(true, maxfirstmove);
            } else {
                PossibleMove maxfirstmove = MaxWieghtPossibleMoves_FirstList(aiClass.getAi_gameNode().getSecondDice_PossibleMoves());
                aiClass.MovePossibleMove(false, maxfirstmove);
            }
        }
    }


}