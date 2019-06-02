package com.example.m3000j.backgammon;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.m3000j.backgammon.GameActivity.ShowDialog;
import static com.example.m3000j.backgammon.GameActivity.difficulty;
import static com.example.m3000j.backgammon.GameActivity.isMove;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;
import static com.example.m3000j.backgammon.GameActivity.numMoves;


public class AiClass {

    Context context;
    private DiceClass ai_Dices;
    private GameState ai_gameState;
    private HitFunctionClass ai_hitFunctionClass;
    private ControlMovementClass ai_controlMovementClass;
    private BoardClass ai_boardClass;
    private GameNode ai_gameNode;

    public boolean isRemoving = false;

    private int parentId;

    public GameNode getAi_gameNode() {
        return ai_gameNode;
    }

    public void setAi_gameNode(GameNode ai_gameNode) {
        this.ai_gameNode.secondDice_PossibleMoves.clear();
        this.ai_gameNode.firstDice_PossibleMoves.clear();
        this.ai_gameNode.firstDice_PossibleMoves.addAll(ai_gameNode.firstDice_PossibleMoves);
        this.ai_gameNode.secondDice_PossibleMoves.addAll(ai_gameNode.secondDice_PossibleMoves);
    }

    public DiceClass getAi_Dices() {
        return ai_Dices;
    }

    public AiClass(Context context, DiceClass mydice, GameState gameState, HitFunctionClass hitFunctionClass, ControlMovementClass controlMovementClass, BoardClass myBoard) {
        this.context = context;
        ai_Dices = mydice;
        ai_gameState = gameState;
        ai_boardClass = myBoard;
        ai_gameNode = new GameNode();
        ai_controlMovementClass = controlMovementClass;
        ai_hitFunctionClass = hitFunctionClass;
    }

    public void MovePossibleMove(boolean first, PossibleMove possibleMove) {
        switch (GameActivity.difficulty) {
            case Easy:
                EasyGet(first);
                break;
            case Medium:
                MediumGet(first, possibleMove);
                break;
            case Hard:
                HardGet(first, possibleMove);
        }
    }

    private void HardGet(boolean first, PossibleMove possibleMove) {
        byte index = 0;
        byte from = 0;
        byte to = 0;
        View v = null;
        View view = null;
        if (ai_hitFunctionClass.getBlack_Hits() == 0) {
            if (possibleMove != null) {
                from = possibleMove.getFrom();
                to = possibleMove.getTo();
                ColorTextView(from, to, Color.GREEN);
                //calculate index
                parentId = ReturnId(from);
                index = (byte) ReturnIndex(to);
                v = ReturnView(to);
                if (first) {
                    ai_Dices.setFirstDiceisUsed(true);
                } else {
                    ai_Dices.setFirstDiceisUsed(false);
                }

                view = ReturnChecker(from, parentId);

                if (view != null) {
                    if (view.getParent() instanceof FrameLayout) {
                        FrameLayout vparent = (FrameLayout) view.getParent();
                        int count = Integer.valueOf(((TextView) vparent.getChildAt(1)).getText().toString());
                        count = count - 1;
                        if (count < 0) {
                            vparent.setVisibility(View.INVISIBLE);
                            count = 0;
                        } else if (count == 0) {
                            vparent.getChildAt(1).setVisibility(View.INVISIBLE);
                        }
                        isRedTurn = false;
                        ((TextView) vparent.getChildAt(1)).setText(String.valueOf(count));
                    } else {
                        isRedTurn = false;
                        view.setVisibility(View.INVISIBLE);
                    }
                    ai_controlMovementClass.ControlBlackBeads(parentId, v, view, index, ai_hitFunctionClass, ai_Dices, ai_gameState, ai_boardClass);
                    if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
                        ShowDialog(15, "Black");
                    }
                }
//                else {
//                    if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
//                        ShowDialog(15, "Black");
//                    }
//// else {
////                        view.setVisibility(View.INVISIBLE);
////                    }
//                }
                if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
                    ShowDialog(15, "Black");
                }
            } else {
                isMove = true;
                GameActivity.numMoves = 1;
                ai_Dices.ChangeTurn(true);
            }
        } else {
            HardRemove();
        }
    }

    private void HardRemove() {
        int location1 = 0;
        int location2 = 0;
        location1 = (36 - ai_Dices.getDiceTemp().getFirstDice() * 6);
        location2 = (36 - ai_Dices.getDiceTemp().getSecondDice() * 6);
        isRemoving = true;
        isRedTurn = false;
        boolean removed = ai_hitFunctionClass.RemoveHits(ai_boardClass._Grid_Top_Right, ai_boardClass._Img_Black_Hit, location1, ai_controlMovementClass, ai_boardClass, ai_gameState, ai_Dices);
        if (removed) {
            isRemoving = false;
            ai_Dices.setFirstDiceisUsed(true);
            ai_Dices.setFirstdicechoosed(true);
            ai_Dices.HardAiFunctions();
        } else {
            boolean d = ai_hitFunctionClass.RemoveHits(ai_boardClass._Grid_Top_Right, ai_boardClass._Img_Black_Hit, location2, ai_controlMovementClass, ai_boardClass, ai_gameState, ai_Dices);
            if (d) {
                isRemoving = false;
                ai_Dices.setFirstdicechoosed(false);
                ai_Dices.setFirstDiceisUsed(false);
                ai_Dices.HardAiFunctions();
            } else {
                isRemoving = false;
                numMoves = 1;
                isMove = true;
                ai_Dices.ChangeTurn(true);
            }
        }
    }

    private void EasyGet(boolean first) {
        byte index = 0;
        byte from = 0;
        byte to = 0;
        View v = null;
        View view = null;
        Log.d("THIT", "EasyGet: " + ai_hitFunctionClass.getBlack_Hits());

        if (ai_hitFunctionClass.getBlack_Hits() == 0) {
            if (first) {
                if (ai_gameNode.firstDice_PossibleMoves.size() != 0) {
                    do_Movement_First_Moves_Easy(index, from, to, v, view);
                } else {
                    isMove = true;
                    GameActivity.numMoves = 1;
                    ai_Dices.ChangeTurn(true);
                    Toast.makeText(context, "No Possible Moves", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (ai_gameNode.secondDice_PossibleMoves.size() != 0) {
                    do_Movement_Second_Moves_Easy(index, from, to, v, view);
                } else {
                    GameActivity.numMoves = 1;
                    isMove = true;
                    ai_Dices.ChangeTurn(true);
                    Toast.makeText(context, "No Possible Moves", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (!GameActivity.isRedTurn)
                ai_Remove();
        }
    }

    private void MediumGet(boolean first_dice, PossibleMove possibleMove) {
        byte index = 0;
        byte from = 0;
        byte to = 0;
        View v = null;
        View view = null;
        if (ai_hitFunctionClass.getBlack_Hits() == 0) {
            if (possibleMove != null) {
                from = possibleMove.getFrom();
                to = possibleMove.getTo();
                ColorTextView(from, to, Color.GREEN);
                //calculate index
                parentId = ReturnId(from);
                index = (byte) ReturnIndex(to);
                v = ReturnView(to);
                if (ai_Dices.isFirstdicechoosed()) {
                    ai_Dices.setFirstDiceisUsed(true);
                } else {
                    ai_Dices.setFirstDiceisUsed(false);
                }
                view = ReturnChecker(from, parentId);
                if (view != null) {
                    view.setVisibility(View.INVISIBLE);
                    ai_controlMovementClass.ControlBlackBeads(parentId, v, view, index, ai_hitFunctionClass, ai_Dices, ai_gameState, ai_boardClass);
                }
                if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
                    ShowDialog(15, "Black");
                }
            } else {
                isMove = true;
                GameActivity.numMoves = 1;
                ai_Dices.ChangeTurn(true);
            }
        } else {
            ai_Remove();
        }
    }

    private void do_Movement_First_Moves_Easy(byte index, byte from, byte to, View v, View view) {
        index = (byte) (Math.random() * ai_gameNode.firstDice_PossibleMoves.size());
        from = ai_gameNode.firstDice_PossibleMoves.get(index).getFrom();
        to = ai_gameNode.firstDice_PossibleMoves.get(index).getTo();
        ColorTextView(from, to, Color.GREEN);
        //calculate index
        parentId = ReturnId(from);
        index = (byte) ReturnIndex(to);
        v = ReturnView(to);
        ai_Dices.setFirstDiceisUsed(true);
        view = ReturnChecker(from, parentId);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
            ai_controlMovementClass.ControlBlackBeads(parentId, v, view, index, ai_hitFunctionClass, ai_Dices, ai_gameState, ai_boardClass);
        }

        if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
            ShowDialog(15, "Black");
        }
    }

    private void do_Movement_Second_Moves_Easy(byte index, byte from, byte to, View v, View view) {
        index = (byte) (Math.random() * ai_gameNode.secondDice_PossibleMoves.size());
        from = ai_gameNode.secondDice_PossibleMoves.get(index).getFrom();
        to = ai_gameNode.secondDice_PossibleMoves.get(index).getTo();
        ColorTextView(from, to, Color.RED);
        parentId = ReturnId(from);
        ai_Dices.setFirstDiceisUsed(false);
        index = (byte) ReturnIndex(to);
        v = ReturnView(to);
        view = ReturnChecker(from, parentId);
        if (view != null) {
            if (view.getParent() instanceof FrameLayout) {
                FrameLayout vparent = (FrameLayout) view.getParent();
                int count = Integer.valueOf(((TextView) vparent.getChildAt(1)).getText().toString());
                count = count - 1;
                if (count < 0) {
                    vparent.setVisibility(View.INVISIBLE);
                    count = 0;
                } else if (count == 0) {
                    vparent.getChildAt(1).setVisibility(View.INVISIBLE);
                }
                ((TextView) vparent.getChildAt(1)).setText(String.valueOf(count));
            }
            view.setVisibility(View.INVISIBLE);
            ai_controlMovementClass.ControlBlackBeads(parentId, v, view, index, ai_hitFunctionClass, ai_Dices, ai_gameState, ai_boardClass);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
        if (ai_gameState.CountBlackOnes(ai_boardClass) == 0) {
            ShowDialog(15, "Black");
        }
    }

    private void ColorTextView(byte from, byte to, int color) {
        from--;
        to--;
        if (from >= 0 && from <= 5)
            ((TextView) ai_boardClass._Linear_positions_Top_Left.getChildAt(from)).setTextColor(color);
        else if (from >= 6 && from <= 11)
            ((TextView) ai_boardClass._Linear_positions_Top_Right.getChildAt(from - 6)).setTextColor(color);
        else if (from >= 12 && from <= 17)
            ((TextView) ai_boardClass._Linear_positions_Btm_Left.getChildAt(from - 12)).setTextColor(color);
        else
            ((TextView) ai_boardClass._Linear_positions_Btm_Right.getChildAt(from - 18)).setTextColor(color);

        //to
        if (to < 0) {
            Log.d("Removing", "ColorTextView: " + to);
        } else if (to >= 0 && to <= 5)
            ((TextView) ai_boardClass._Linear_positions_Top_Left.getChildAt(to)).setTextColor(color);
        else if (to >= 6 && to <= 11)
            ((TextView) ai_boardClass._Linear_positions_Top_Right.getChildAt(to - 6)).setTextColor(color);
        else if (to >= 12 && to <= 17)
            ((TextView) ai_boardClass._Linear_positions_Btm_Left.getChildAt(to - 12)).setTextColor(color);
        else
            ((TextView) ai_boardClass._Linear_positions_Btm_Right.getChildAt(to - 18)).setTextColor(color);

    }

    private int ReturnId(byte fr) {
        fr--;
        if (fr >= 0 && fr <= 5)
            return R.id._Grid_Top_Right;
        else if (fr >= 6 && fr <= 11)
            return R.id._Grid_Top_Left;
        else if (fr >= 12 && fr <= 17)
            return R.id._Grid_Btm_Left;
        else
            return R.id._Grid_Btm_Right;
    }

    private int ReturnIndex(byte to) {
        to--;
        if (to >= 0 && to <= 5)
            return to * (-6) + 30;
        else if (to >= 6 && to <= 11)
            return (to - 6) * (-6) + 30;
        else if (to >= 12 && to <= 17)
            return (to - 12) * 6;
        else
            return (to - 18) * 6;
    }

    private View ReturnView(byte to) {
        to--;
        if (to < 0) {
            return ai_boardClass._Rel_Black_Remove;
        } else if (to <= 5)
            return ai_boardClass._Grid_Top_Right;
        else if (to <= 11)
            return ai_boardClass._Grid_Top_Left;
        else if (to <= 17)
            return ai_boardClass._Grid_Btm_Left;
        else
            return ai_boardClass._Grid_Btm_Right;
    }

    private View ReturnChecker(byte fr, int parentId) {
        int ind = 0;
        int tempindex;
        fr--;
        FrameLayout vparent;
        if (fr >= 0 && fr <= 5)
            ind = fr * (-6) + 30;
        else if (fr >= 6 && fr <= 11)
            ind = (fr - 6) * (-6) + 30;
        else if (fr >= 12 && fr <= 17)
            ind = (fr - 12) * 6;
        else
            ind = (fr - 18) * 6;

        GameActivity.primeIndex = ind;
        if (parentId == R.id._Grid_Top_Right) {
            tempindex = ind;
            while (tempindex < ind + 5 && ai_boardClass._Grid_Top_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                tempindex++;
            }
            if (ai_boardClass._Grid_Top_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                vparent = (FrameLayout) ai_boardClass._Grid_Top_Right.getChildAt(tempindex);
//                decrease_Extra_FrameLayout_Count(vparent);
                return vparent.getChildAt(0);
            } else {
                return ai_boardClass._Grid_Top_Right.getChildAt(tempindex - 1);
            }
        } else if (parentId == R.id._Grid_Top_Left) {
            tempindex = ind;
            while (tempindex < ind + 5 && ai_boardClass._Grid_Top_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                tempindex++;
            }
            if (ai_boardClass._Grid_Top_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                vparent = (FrameLayout) ai_boardClass._Grid_Top_Left.getChildAt(tempindex);
//                decrease_Extra_FrameLayout_Count(vparent);
                return vparent.getChildAt(0);
            } else {
                return ai_boardClass._Grid_Top_Left.getChildAt(tempindex - 1);
            }
        } else if (parentId == R.id._Grid_Btm_Right) {
            tempindex = ind + 5;
            while (tempindex > ind && ai_boardClass._Grid_Btm_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                tempindex--;
            }

            if (ai_boardClass._Grid_Btm_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                vparent = (FrameLayout) ai_boardClass._Grid_Btm_Right.getChildAt(tempindex);
//                decrease_Extra_FrameLayout_Count(vparent);
                return vparent.getChildAt(0);
            } else {
                return ai_boardClass._Grid_Btm_Right.getChildAt(tempindex + 1);
            }
        } else if (parentId == R.id._Grid_Btm_Left) {
            tempindex = ind + 5;
            while (tempindex > ind && ai_boardClass._Grid_Btm_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                tempindex--;
            }

            if (ai_boardClass._Grid_Btm_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                vparent = (FrameLayout) ai_boardClass._Grid_Btm_Left.getChildAt(tempindex);
//                decrease_Extra_FrameLayout_Count(vparent);
                return vparent.getChildAt(0);
            } else {
                return ai_boardClass._Grid_Btm_Left.getChildAt(tempindex + 1);
            }
        }
        return null;
    }

    public HitFunctionClass getAi_hitFunctionClass() {
        return ai_hitFunctionClass;
    }

    public void setAi_hitFunctionClass(HitFunctionClass ai_hitFunctionClass) {
        this.ai_hitFunctionClass = ai_hitFunctionClass;
    }

    private void ai_Remove() {
        int location1 = 0;
        int location2 = 0;
        location1 = (36 - ai_Dices.getDiceTemp().getFirstDice() * 6);
        location2 = (36 - ai_Dices.getDiceTemp().getSecondDice() * 6);
        isRemoving = true;
        boolean removed = ai_hitFunctionClass.RemoveHits(ai_boardClass._Grid_Top_Right, ai_boardClass._Img_Black_Hit, location1, ai_controlMovementClass, ai_boardClass, ai_gameState, ai_Dices);
        if (removed) {
            isRemoving = false;
            ai_Dices.setFirstDiceisUsed(true);
            ai_Dices.setFirstdicechoosed(true);
            ai_Dices.do_Ai_Functions(true);
        } else {
            boolean d = ai_hitFunctionClass.RemoveHits(ai_boardClass._Grid_Top_Right, ai_boardClass._Img_Black_Hit, location2, ai_controlMovementClass, ai_boardClass, ai_gameState, ai_Dices);
            if (d) {
                isRemoving = false;
                if (difficulty == Difficulty.Medium) {
                    ai_Dices.setFirstdicechoosed(false);
                    ai_Dices.setFirstDiceisUsed(false);
                    ai_Dices.do_Ai_Functions(false);
                } else {

                    ai_Dices.setFirstdicechoosed(false);
                    ai_Dices.setFirstDiceisUsed(false);
                    ai_Dices.do_Ai_Functions(false);
                }
            } else {
                isRemoving = false;
                numMoves = 1;
                isMove = true;
                ai_Dices.ChangeTurn(true);
            }
        }
    }

    public void RollDice() {
        ai_Dices.RollDice();
    }

    public void HandlPossibleState() {
        ai_gameState.HandlePossibleStates(ai_hitFunctionClass, ai_Dices, ai_controlMovementClass, ai_boardClass);
    }

    public GameState getAi_gameState() {
        return ai_gameState;
    }

    private double evaluate(byte[] states, byte to) { // Huristic
        double black_doors_eval = 0;
        double red_doors_eval = 0;
        double total_Evalue = 0;
        byte blackCount = 0;
        for (int i = 0; i < states.length; i++) {
            if (states[i] > 0) {
                blackCount += states[i];
            }
        }
        for (int i = 0; i < states.length; i++) {
            if (states[i] > 0) {
                if (i >= 17 && i <= 19) {
                    if (states[i] == 2 || states[i] == 3) {
                        black_doors_eval += 4;
                    } else {
                        black_doors_eval -= 4;
                    }
                } else if (i >= 5 && i <= 8) {
                    if (states[i] == 2 || states[i] == 3) {
                        black_doors_eval += 4;
                    } else {
                        black_doors_eval -= 4;
                    }
                } else {
                    if (states[i] == 2) {
                        black_doors_eval += 2;
                    } else if (states[i] == 3) {
                        black_doors_eval += 1;
                    } else {
                        black_doors_eval -= 4;
                    }
                }
            }
        }
        total_Evalue = 0.01 * (black_doors_eval + red_doors_eval) + 0.029 * (ai_hitFunctionClass.getRed_Hits() - ai_hitFunctionClass.getBlack_Hits());
        if (blackCount <= 15 && to == -1) {
            return 100;
        } else {
            return total_Evalue;
        }
    }

    public void MakeBoardFill(PossibleMove possibleMove) {
        if (possibleMove.getTo() >= 0) {
            byte from = possibleMove.getFrom();
            byte to = possibleMove.getTo();
            possibleMove.gameStateLists.getListState()[from]--;
            possibleMove.gameStateLists.getListState()[to]++;
            possibleMove.setWeight(evaluate(possibleMove.gameStateLists.getListState(), possibleMove.getTo()));
        } else {
            possibleMove.setWeight(100);
        }
    }

    public void setAi_gameState(GameState ai_gameState) {
        this.ai_gameState = ai_gameState;
    }
}
