package com.example.m3000j.backgammon;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import static com.example.m3000j.backgammon.GameActivity.aiClass;
import static com.example.m3000j.backgammon.GameActivity.canBlackRemove;
import static com.example.m3000j.backgammon.GameActivity.canRedRemove;
import static com.example.m3000j.backgammon.GameActivity.difficulty;
import static com.example.m3000j.backgammon.GameActivity.isDrEqual;
import static com.example.m3000j.backgammon.GameActivity.isParentChanged;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;

public class GameState {
    private byte[] ListState = {0, 2, 0, 0, 0, 0, -5, 0, -3, 0, 0, 0, 5, -5, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, -2};
    private byte[] RedState = new byte[24];
    private byte[] BlackState = new byte[24];
    private GameNode gameNode;
    public static boolean ismanual = false;


    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    private boolean canMove = true;
    final Context context;

    public GameState(Context context) {
        this.context = context;
        gameNode = new GameNode();
    }

    public byte[] getListState() {
        return ListState;
    }

    public byte[] getRedState() {
        return RedState;
    }

    public byte[] getBlackState() {
        return BlackState;
    }

    public void FillRBState() {

        for (int i = 0; i < RedState.length; i++) {
            RedState[i] = 0;
            BlackState[i] = 0;
        }

        int t = 0;
        for (int i = ListState.length - 1; i >= 1; i--) {
            if (ListState[i] < 0) {
                RedState[t] = (byte) i;
                t++;
            }
        }
        t = 0;

        for (int i = 1; i < ListState.length; i++) {
            if (ListState[i] > 0) {
                BlackState[t] = (byte) i;
                t++;
            }
        }
        Log.d("INI", Arrays.toString(this.getListState()));
    }

    public void DecreaseListState(View view, int index) {
        View parentState;
        int no = 0;
        if (view.getParent() instanceof FrameLayout) {
            if (((FrameLayout) view.getParent()).getId() == R.id._Frame_Black_Hit || ((FrameLayout) view.getParent()).getId() == R.id._Frame_Red_Hit)
                return;
            parentState = (GridLayout) view.getParent().getParent();
        } else {
            parentState = (GridLayout) view.getParent();
        }
        switch (parentState.getId()) {
            case R.id._Grid_Top_Right:
                if (isRedTurn) {
                    no = Math.abs(ListState[6 - index]);
                    no--;
                    ListState[6 - index] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[6 - index]);
                    no--;
                    ListState[6 - index] = (byte) no;
                }
                break;
            case R.id._Grid_Top_Left:
                if (isRedTurn) {
                    no = Math.abs(ListState[12 - index]);
                    no--;
                    ListState[12 - index] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[12 - index]);
                    no--;
                    ListState[12 - index] = (byte) no;
                }
                break;
            case R.id._Grid_Btm_Left:
                if (isRedTurn) {
                    no = Math.abs(ListState[23 - (10 - index)]);
                    no--;
                    ListState[23 - (10 - index)] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[23 - (10 - index)]);
                    no--;
                    ListState[23 - (10 - index)] = (byte) no;
                }
                break;
            case R.id._Grid_Btm_Right:
                if (isRedTurn) {
                    no = Math.abs(ListState[29 - (10 - index)]);
                    no--;
                    ListState[29 - (10 - index)] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[29 - (10 - index)]);
                    no--;
                    ListState[29 - (10 - index)] = (byte) no;
                }
                break;
        }

    }

    public void IncreaseListState(View v, int index) {
        int no = 0;
        switch (v.getId()) {
            case R.id._Grid_Top_Right:
                if (isRedTurn) {
                    no = Math.abs(ListState[6 - index / 6]);
                    no++;
                    ListState[6 - index / 6] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[6 - index / 6]);
                    no++;
                    ListState[6 - index / 6] = (byte) no;
                }
                break;
            case R.id._Grid_Top_Left:
                if (isRedTurn) {
                    no = Math.abs(ListState[12 - index / 6]);
                    no++;
                    ListState[12 - index / 6] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[12 - index / 6]);
                    no++;
                    ListState[12 - index / 6] = (byte) no;
                }
                break;
            case R.id._Grid_Btm_Left:
                if (isRedTurn) {
                    no = Math.abs(ListState[23 - (10 - index / 6)]);
                    no++;
                    ListState[23 - (10 - index / 6)] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[23 - (10 - index / 6)]);
                    no++;
                    ListState[23 - (10 - index / 6)] = (byte) no;
                }
                break;
            case R.id._Grid_Btm_Right:
                if (isRedTurn) {
                    no = Math.abs(ListState[29 - (10 - index / 6)]);
                    no++;
                    ListState[29 - (10 - index / 6)] = (byte) (no * (-1));
                } else {
                    no = Math.abs(ListState[29 - (10 - index / 6)]);
                    no++;
                    ListState[29 - (10 - index / 6)] = (byte) no;
                }
                break;
        }
    }

    public void DecreaseListStateHits(View v, int index) {
        switch (v.getId()) {
            case R.id._Grid_Top_Right:
                ListState[6 - index / 6] = 0;
                break;
            case R.id._Grid_Top_Left:
                ListState[12 - index / 6] = 0;
                break;
            case R.id._Grid_Btm_Left:
                ListState[23 - (10 - index / 6)] = 0;
                break;
            case R.id._Grid_Btm_Right:
                ListState[29 - (10 - index / 6)] = 0;
                break;
        }
    }

    public int CountPossibleState(DiceNumbers diceTemp, ControlMovementClass controlMovementClass) {
        int PossibleMoves = 0;
        int location1 = diceTemp.getFirstDice();
        int location2 = diceTemp.getSecondDice();

        gameNode.secondDice_PossibleMoves.clear();
        gameNode.firstDice_PossibleMoves.clear();
        int lastIndex = 0;

        if (canBlackRemove && !isRedTurn) {
            lastIndex = controlMovementClass.LastFullRoom(this, false);
            if (location1 > 0) {
                if (getListState()[25 - location1] > 0) {
                    PossibleMoves++;
                    gameNode.firstDice_PossibleMoves.add(new PossibleMove((byte) (25 - location1), (byte) -1, getListState()));
                } else if (getListState()[25 - location1] == 0 && location1 > lastIndex) {
                    PossibleMoves++;
                    gameNode.firstDice_PossibleMoves.add(new PossibleMove((byte) (25 - lastIndex), (byte) -1, getListState()));
                }
            }
            if (location2 > 0) {
                if (getListState()[25 - location2] > 0) {
                    PossibleMoves++;
                    gameNode.secondDice_PossibleMoves.add(new PossibleMove((byte) (25 - location2), (byte) -1, getListState()));
                } else if (getListState()[25 - location2] == 0 && location2 > lastIndex) {
                    PossibleMoves++;
                    gameNode.secondDice_PossibleMoves.add(new PossibleMove((byte) (25 - lastIndex), (byte) -1, getListState()));
                }
            }
        } else if (canRedRemove && isRedTurn) {
            lastIndex = controlMovementClass.LastFullRoom(this, true);
            if (location1 > 0) {
                if (getListState()[location1] < 0) {
                    PossibleMoves++;
                } else if (getListState()[location1] == 0 && location1 > lastIndex) {
                    PossibleMoves++;
                }
            }
            if (location2 > 0) {
                if (getListState()[location2] < 0) {
                    PossibleMoves++;
                } else if (getListState()[location2] == 0 && location2 > lastIndex) {
                    PossibleMoves++;
                }
            }
        }

        if (!isRedTurn) {
            for (int i = 0; i < getBlackState().length; i++) {
                if (getBlackState()[i] != 0) {
                    if (location1 > 0) {
                        if (getBlackState()[i] + location1 <= 24 && getListState()[location1 + getBlackState()[i]] >= -1) {
                            PossibleMoves++;
                            gameNode.firstDice_PossibleMoves.add(new PossibleMove((getBlackState()[i]), (byte) (location1 + getBlackState()[i]), getListState()));
                        }
                    }
                    if (location2 > 0) {
                        if (getBlackState()[i] + location2 <= 24 && getListState()[location2 + getBlackState()[i]] >= -1) {
                            PossibleMoves++;
                            gameNode.secondDice_PossibleMoves.add(new PossibleMove((getBlackState()[i]), (byte) (location2 + getBlackState()[i]), getListState()));
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < getRedState().length; i++) {
                if (getRedState()[i] != 0) {
                    if (location1 > 0) {
                        if (getRedState()[i] - location1 > 0 && getListState()[getRedState()[i] - location1] <= 1) {
                            PossibleMoves++;
                        }
                    }
                    if (location2 > 0) {
                        if (getRedState()[i] - location2 > 0 && getListState()[getRedState()[i] - location2] <= 1) {
                            PossibleMoves++;
                        }
                    }
                }
            }
        }
        aiClass.setAi_gameNode(gameNode);
        return PossibleMoves;
    }

    public int CountBlackOnes(BoardClass board) {
        int total = 0;
        for (int i = 0; i < board._Grid_Btm_Right.getChildCount(); i++) {
            View view = board._Grid_Btm_Right.getChildAt(i);
            if (view instanceof FrameLayout) {
                if (isDrEqual(context.getResources().getDrawable(board.mashinColor), ((FrameLayout) view).getChildAt(0).getBackground()) && view.getVisibility() == View.VISIBLE) {
                    total++;
                    TextView textView = (TextView) ((FrameLayout) view).getChildAt(1);
                    total += Integer.valueOf(textView.getText().toString());
                }
            } else {
                if (isDrEqual(context.getResources().getDrawable(board.mashinColor), view.getBackground()) && view.getVisibility() == View.VISIBLE) {
                    total++;
                }
            }
        }
        return total;
    }

    public int CountRedOnes(BoardClass board) {
        int total = 0;
        for (int i = 0; i < board._Grid_Top_Right.getChildCount();
             i++) {
            View view = board._Grid_Top_Right.getChildAt(i);
            if (view instanceof FrameLayout) {
                if (isDrEqual(context.getResources().getDrawable(board.personColor), ((FrameLayout) view).getChildAt(0).getBackground()) && view.getVisibility() == View.VISIBLE) {
                    total++;
                    TextView textView = (TextView) ((FrameLayout) view).getChildAt(1);
                    total += Integer.valueOf(textView.getText().toString());
                }
            } else {
                if (isDrEqual(context.getResources().getDrawable(board.personColor), view.getBackground()) && view.getVisibility() == View.VISIBLE) {
                    total++;
                }
            }
        }
        return total;
    }

    public void HandlePossibleStates(HitFunctionClass hitFunctionClass, DiceClass myDice, ControlMovementClass cms, BoardClass myBoard) {
        if (!isRedTurn) {
            if (hitFunctionClass.getBlack_Hits() > 0) {
                int location1 = 0;
                int location2 = 0;
                location1 = (36 - myDice.getDiceTemp().getFirstDice() * 6);
                location2 = (36 - myDice.getDiceTemp().getSecondDice() * 6);

                if (location1 <= 36 && location2 <= 36) {

                    if (myBoard._Grid_Top_Right.getChildAt(location1 + 1).getVisibility() == View.VISIBLE
                            && isDrEqual(context.getResources().getDrawable(myBoard.personColor), myBoard._Grid_Top_Right.getChildAt(location1 + 1).getBackground())) {

                        if (myBoard._Grid_Top_Right.getChildAt(location2 + 1).getVisibility() == View.VISIBLE
                                && isDrEqual(context.getResources().getDrawable(myBoard.personColor), myBoard._Grid_Top_Right.getChildAt(location2 + 1).getBackground())) {
                            Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                            GameActivity.isMove = true;
                            isParentChanged = true;

                            GameActivity.numMoves = 1;
                            canMove = false;
                            //this line added
                            myDice.ChangeTurn(true);
                        }
                    }

                } else if (location1 > 36) {
                    //check the second dice
                    if (myBoard._Grid_Top_Right.getChildAt(location2 + 1).getVisibility() == View.VISIBLE
                            && isDrEqual(context.getResources().getDrawable(myBoard.personColor), myBoard._Grid_Top_Right.getChildAt(location2 + 1).getBackground())) {
                        Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                        GameActivity.isMove = true;
                        isParentChanged = true;
                        GameActivity.numMoves = 1;
                        canMove = false;
                        //this line added
                        myDice.ChangeTurn(true);

                    }
                } else if (location2 > 36) {
                    //check the first dice
                    if (myBoard._Grid_Top_Right.getChildAt(location1 + 1).getVisibility() == View.VISIBLE
                            && isDrEqual(context.getResources().getDrawable(myBoard.personColor), myBoard._Grid_Top_Right.getChildAt(location1 + 1).getBackground())) {
                        Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                        GameActivity.isMove = true;
                        isParentChanged = true;
                        GameActivity.numMoves = 1;
                        //this line added
                        canMove = false;
                        myDice.ChangeTurn(true);

                    }
                }
            } else if (hitFunctionClass.getBlack_Hits() == 0) {
                if (CountPossibleState(myDice.getDiceTemp(), cms) == 0) {

                    GameActivity.isMove = true;
                    GameActivity.numMoves = 1;
                       /* cms._rel_black_remove.setVisibility(View.INVISIBLE);
                        canBlackRemove = false;*/
                    if (myDice.getDiceTemp().getFirstDice() > 0 || myDice.getDiceTemp().getSecondDice() > 0) {
                        Toast.makeText(context, "No Possible Moves", Toast.LENGTH_SHORT).show();
                        //this line added
                        canMove = false;
                        myDice.ChangeTurn(true);
                    }
                }
            }
        } else {
            if (hitFunctionClass.getRed_Hits() > 0) {
                Log.d("DDs", "HandlePossibleStates: " + hitFunctionClass.getRed_Hits());
                int location1 = 0;
                int location2 = 0;
                location1 = (36 - myDice.getDiceTemp().getFirstDice() * 6) + 5;
                location2 = (36 - myDice.getDiceTemp().getSecondDice() * 6) + 5;

                if (location1 <= 36 && location2 <= 36) {
                    if (myBoard._Grid_Btm_Right.getChildAt(location1 - 1) != null) {
                        if (myBoard._Grid_Btm_Right.getChildAt(location1 - 1).getVisibility() == View.VISIBLE
                                && isDrEqual(context.getResources().getDrawable(myBoard.mashinColor), myBoard._Grid_Btm_Right.getChildAt(location1 - 1).getBackground())) {

                            if (myBoard._Grid_Btm_Right.getChildAt(location2 - 1).getVisibility() == View.VISIBLE
                                    && isDrEqual(context.getResources().getDrawable(myBoard.mashinColor), myBoard.get_Grid_Btm_Right().getChildAt(location2 - 1).getBackground())) {
                                Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                                GameActivity.isMove = true;
                                isParentChanged = true;
                                canMove = false;
                                GameActivity.numMoves = 0;
                                ismanual = true;
                                isRedTurn = false;
                                myDice._Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                                myDice._Linear_Red_Turn.setBackground(null);
                                GameActivity.aiClass.RollDice();
                                myDice.HardAiFunctions();
                            }
                        }
                    } else {
                        if (!isRedTurn)
                            myDice.do_Ai_Functions(true);
                    }
                } else if (location1 > 36) {
                    //check the second dice
                    if (myBoard._Grid_Btm_Right.getChildAt(location2 - 1) != null) {
                        if (myBoard._Grid_Btm_Right.getChildAt(location2 - 1).getVisibility() == View.VISIBLE
                                && isDrEqual(context.getResources().getDrawable(myBoard.mashinColor), myBoard._Grid_Btm_Right.getChildAt(location2 - 1).getBackground())) {
                            Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                            GameActivity.isMove = true;
                            isParentChanged = true;
                            canMove = false;
                            GameActivity.numMoves = 0;
                            ismanual = true;
                            isRedTurn = false;
                            myDice._Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                            myDice._Linear_Red_Turn.setBackground(null);
                            GameActivity.aiClass.RollDice();
                            myDice.HardAiFunctions();

                        }
                    } else {
                        if (!isRedTurn) {
                            myDice.do_Ai_Functions(false);
                        }
                    }
                } else if (location2 > 36) {
                    //check the first dice
                    if (myBoard._Grid_Btm_Right.getChildAt(location1).getVisibility() == View.VISIBLE
                            && isDrEqual(context.getResources().getDrawable(myBoard.mashinColor), myBoard._Grid_Btm_Right.getChildAt(location1).getBackground())) {
                        Toast.makeText(context, "No Additional Move", Toast.LENGTH_SHORT).show();
                        GameActivity.isMove = true;
                        isParentChanged = true;
                        canMove = false;

                        GameActivity.numMoves = 0;
                        //this line added
                        ismanual = true;
                        isRedTurn = false;
                        myDice._Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                        myDice._Linear_Red_Turn.setBackground(null);
                        GameActivity.aiClass.RollDice();
                        myDice.HardAiFunctions();
                    }
                }
            } else if (hitFunctionClass.getRed_Hits() == 0) {
                if (CountPossibleState(myDice.getDiceTemp(), cms) == 0) {
                    GameActivity.isMove = true;
                    GameActivity.numMoves = 1;
                    if (myDice.getDiceTemp().getSecondDice() > 0 || myDice.getDiceTemp().getFirstDice() > 0) {
                        Toast.makeText(context, "No Possible Moves", Toast.LENGTH_SHORT).show();
                        //this line added
                        canMove = false;
                        GameActivity.isMove = true;
                        ismanual = true;
                        GameActivity.numMoves = 0;
                        isRedTurn = false;
                        myDice._Linear_Black_Turn.setBackgroundColor(context.getResources().getColor(R.color.colorTurnBackground));
                        myDice._Linear_Red_Turn.setBackground(null);
                        Log.d("turn", "ChooseView: " + "changed");
                        GameActivity.aiClass.RollDice();
                        myDice.HardAiFunctions();

                    }
                }
            }
        }
    }
}
