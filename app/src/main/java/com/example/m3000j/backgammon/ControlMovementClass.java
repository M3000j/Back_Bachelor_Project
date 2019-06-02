package com.example.m3000j.backgammon;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import static com.example.m3000j.backgammon.GameActivity.ShowDialog;
import static com.example.m3000j.backgammon.GameActivity.canBlackRemove;
import static com.example.m3000j.backgammon.GameActivity.canRedRemove;
import static com.example.m3000j.backgammon.GameActivity.isDrEqual;
import static com.example.m3000j.backgammon.GameActivity.isMove;
import static com.example.m3000j.backgammon.GameActivity.isParentChanged;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;
import static com.example.m3000j.backgammon.GameActivity.primeIndex;
import static com.example.m3000j.backgammon.GameActivity.tempindex;
import static com.example.m3000j.backgammon.GameActivity.tindex;

public class ControlMovementClass {

    TextView _Txt_Red_Remove, _Txt_Black_Remove;
    public LinearLayout _rel_red_remove, _rel_black_remove;
    final Context context;

    public ControlMovementClass(Context cont) {
        context = cont;
    }

    public void ControlBlackBeads(int parent, View v, View view, int index, HitFunctionClass hitfc, DiceClass myDice, GameState gameState, BoardClass boardClass) {
        if (hitfc.getBlack_Hits() == 0) {
            if (parent == R.id._Grid_Top_Right) {
                if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index < primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Grid_Btm_Right || v.getId() == R.id._Grid_Btm_Left) {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                } else {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                }
            } else if (parent == R.id._Grid_Top_Left) {
                if (v.getId() == R.id._Grid_Top_Right || v.getId() == R.id._Grid_Btm_Right) {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                } else if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index < primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                }
            } else if (parent == R.id._Grid_Btm_Left) {
                if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index > primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Grid_Btm_Right) {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                } else {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                }
            } else {
                if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index > primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Rel_Black_Remove) {
                    if (canBlackRemove) {
                        isParentChanged = true;
                        int indexLastFullRoom = LastFullRoom(gameState, false);
                        int newPrimeIndex = 6 - (primeIndex / 6);
                        int firstDice = myDice.getDiceTemp().getFirstDice();
                        int secondDice = myDice.getDiceTemp().getSecondDice();
                        if (newPrimeIndex == firstDice || (newPrimeIndex < firstDice && newPrimeIndex == indexLastFullRoom)) {
                            myDice.setFirstDiceisUsed(true);
                            int removecount = Integer.valueOf(_Txt_Black_Remove.getText().toString()) + 1;
                            _Txt_Black_Remove.setText(String.valueOf(removecount));

                            ShowDialog(removecount, "Black");

                            gameState.DecreaseListState(view, primeIndex / 6);
                            gameState.FillRBState();
                            myDice.CheckDice();
                            gameState.HandlePossibleStates(hitfc, myDice, this, boardClass);
                            if (gameState.isCanMove()) {
                                myDice.ChangeTurn(true);
                            }
                        } else if (newPrimeIndex == secondDice || (newPrimeIndex < secondDice && newPrimeIndex == indexLastFullRoom)) {
                            myDice.setFirstDiceisUsed(false);
                            int removecount = Integer.valueOf(_Txt_Black_Remove.getText().toString()) + 1;
                            ShowDialog(removecount, "Black");
                            _Txt_Black_Remove.setText(String.valueOf(removecount));
                            gameState.DecreaseListState(view, primeIndex / 6);
                            gameState.FillRBState();
                            myDice.CheckDice();
                            gameState.HandlePossibleStates(hitfc, myDice, this, boardClass);
                            if (gameState.isCanMove()) {
                                myDice.ChangeTurn(true);
                            }
                        } else {
                            MakeFramlayoutVisible(view);
                        }
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                    return;
                } else {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                }
            }
        } else {
            isMove = false;
            GameActivity.isParentChanged = false;
            MakeFramlayoutVisible(view);
        }
    }

    public void ControlRedBeads(int parent, View v, View view, int index, HitFunctionClass hitfc, DiceClass myDice, GameState gameState, BoardClass boardClass) {
        if (hitfc.getRed_Hits() == 0) {
            if (parent == R.id._Grid_Top_Right) {
                GameActivity.isParentChanged = false;
                if (v.getId() == parent) {
                    if (index > primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Rel_Red_Remove) {
                    if (canRedRemove) {
                        isParentChanged = true;
                        int indexLastFullRoom = LastFullRoom(gameState, true);
                        int newPrimeIndex = 6 - (primeIndex / 6);
                        int firstDice = myDice.getDiceTemp().getFirstDice();
                        int secondDice = myDice.getDiceTemp().getSecondDice();
                        if (newPrimeIndex == firstDice || (newPrimeIndex <= firstDice && newPrimeIndex == indexLastFullRoom)) {
                            myDice.setFirstDiceisUsed(true);
                            int removecount = Integer.valueOf(_Txt_Red_Remove.getText().toString()) + 1;
                            _Txt_Red_Remove.setText(String.valueOf(removecount));

                            ShowDialog(removecount, "Red");

                            gameState.DecreaseListState(view, primeIndex / 6);
                            gameState.FillRBState();
                            myDice.CheckDice();
                            gameState.HandlePossibleStates(hitfc, myDice, this, boardClass);

                            if (gameState.isCanMove()) {
                                myDice.ChangeTurn(false);
                            }
                        } else if (newPrimeIndex == secondDice || (newPrimeIndex <= secondDice && newPrimeIndex <= indexLastFullRoom)) {
                            myDice.setFirstDiceisUsed(false);
                            int removecount = Integer.valueOf(_Txt_Red_Remove.getText().toString()) + 1;
                            _Txt_Red_Remove.setText(String.valueOf(removecount));
                            ShowDialog(removecount, "Red");
                            gameState.DecreaseListState(view, primeIndex / 6);

                            gameState.FillRBState();
                            myDice.CheckDice();

                            gameState.HandlePossibleStates(hitfc, myDice, this, boardClass);

                            if (gameState.isCanMove()) {
                                myDice.ChangeTurn(false);
                            }

                        } else {
                            MakeFramlayoutVisible(view);
                        }
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                }
            } else if (parent == R.id._Grid_Top_Left) {
                if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index > primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Grid_Top_Right) {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                } else {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                }
            } else if (parent == R.id._Grid_Btm_Left) {
                if (v.getId() == parent) {
                    GameActivity.isParentChanged = false;
                    if (index < primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Grid_Btm_Right || v.getId() == R.id._Grid_Top_Right) {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                } else {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                }
            } else {
                if (parent == v.getId()) {
                    GameActivity.isParentChanged = false;
                    if (index < primeIndex) {
                        MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                } else if (v.getId() == R.id._Grid_Top_Right) {
                    isMove = false;
                    MakeFramlayoutVisible(view);
                } else {
                    GameActivity.isParentChanged = true;
                    MoveView(v, view, index, myDice, gameState, hitfc, boardClass);
                }
            }
        } else {
            MakeFramlayoutVisible(view);
            GameActivity.isParentChanged = false;
            isMove = false;
        }
    }

    public int LastFullRoom(GameState gameState, boolean isRed) {
        if (isRed) {
            for (int i = 6; i >= 1; i--) {
                if (gameState.getListState()[i] < 0) {
                    return i;
                }
            }
            return 6;
        } else {
            for (int i = 19; i < 24; i++) {
                if (gameState.getListState()[i] > 0) {
                    return 25 - i;
                }
            }
            return 1;
        }
    }

    public boolean MoveView(View v, View view, int index, DiceClass myDice, GameState gameState, HitFunctionClass hitFunctionClass, BoardClass myBoard) {
        boolean isRemoved;
        if (index == CalMoveIndex(view, myDice.getDiceTemp().getFirstDice(), myBoard)) {
            isMove = true;
            myDice.setFirstDiceisUsed(true);
            isRemoved = ChooseView(v, view, index, myBoard, hitFunctionClass, gameState, myDice);
        } else if (index == CalMoveIndex(view, myDice.getDiceTemp().getSecondDice(), myBoard)) {
            isMove = true;
            myDice.setFirstDiceisUsed(false);
            isRemoved = ChooseView(v, view, index, myBoard, hitFunctionClass, gameState, myDice);
        } else {
            MakeFramlayoutVisible(view);
            isMove = false;
            return false;
        }
        return isRemoved;
    }

    private int CalIndex(GridLayout gr, View view, int dinex, BoardClass myBoard) {
        if (isDrEqual(view.getBackground(), context.getResources().getDrawable(myBoard.personColor))) {
            if (gr.getId() == R.id._Grid_Btm_Left) {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex - dietemp < 0) {
                    int num = tindex / 6;
                    num = dinex - num;
                    return (num * 6) - 6;
                } else {
                    return tindex - dietemp;
                }
            } else if (gr.getId() == R.id._Grid_Btm_Right) {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex - dietemp < 0) {
                    return (tindex - dietemp) + 36;
                } else {
                    return tindex - dietemp;
                }
            } else {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex + dietemp > 30) {
                    return (tindex + dietemp) - 36;
                } else {
                    return tindex + dietemp;
                }
            }
        } else if (isDrEqual(view.getBackground(), context.getResources().getDrawable(myBoard.mashinColor))) {
            if (gr.getId() == R.id._Grid_Top_Right) {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex - dietemp >= 0) {
                    return tindex - dietemp;
                } else {
                    return (tindex - dietemp) + 36;
                }
            } else if (gr.getId() == R.id._Grid_Top_Left) {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex - dietemp < 0) {
                    int num = tindex / 6;
                    num = dinex - num;
                    return (num * 6) - 6;
                } else {
                    return tindex - dietemp;
                }
            } else {
                tindex = (primeIndex / 6) * 6;
                int dietemp = dinex * 6;
                if (tindex + dietemp > 30) {
                    return (tindex + dietemp) - 36;
                } else {
                    return tindex + dietemp;
                }
            }
        }
        return -1;
    }

    private int CalMoveIndex(View view, int dinex, BoardClass myBoard) {
        if (dinex < 0) {
            return -2;
        }
        if (view.getParent() instanceof GridLayout) {
            return CalIndex((GridLayout) view.getParent(), view, dinex, myBoard);
        } else if (view.getParent() instanceof FrameLayout) {
            FrameLayout tempFrame = ((FrameLayout) view.getParent());
            if (tempFrame.getId() == R.id._Frame_Black_Hit || tempFrame.getId() == R.id._Frame_Red_Hit) {
                return 36 - dinex * 6;
            } else {
                return CalIndex((GridLayout) view.getParent().getParent(), view, dinex, myBoard);
            }
        }
        return -1;
    }

    private boolean ChooseView(View v, View view, int index, BoardClass myBoard, HitFunctionClass hitFunctionClass, GameState gameState, DiceClass myDice) {
        switch (v.getId()) {
            case R.id._Grid_Btm_Left:
                tempindex = index + 5;
                if (myBoard._Grid_Btm_Left.getChildAt(tempindex).getVisibility() == View.INVISIBLE) {
                    myBoard._Grid_Btm_Left.getChildAt(tempindex).setVisibility(View.VISIBLE);
                    myBoard._Grid_Btm_Left.getChildAt(tempindex).setBackground(view.getBackground());
                } else {
                    while (tempindex > index && myBoard._Grid_Btm_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        tempindex--;
                    }
                    if (myBoard._Grid_Btm_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        String text = ((TextView) ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(1)).getText().toString();
                        int temp = Integer.valueOf(text);
                        temp++;
                        ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(1).setVisibility(View.VISIBLE);
                        ((TextView) ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(1)).setText(temp + "");
                        ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                    } else if (isDrEqual(myBoard._Grid_Btm_Left.getChildAt(tempindex + 1).getBackground(), view.getBackground())) {
                        if (myBoard._Grid_Btm_Left.getChildAt(tempindex) instanceof FrameLayout) {
                            ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(0).setBackground(view.getBackground());
                            ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(1).setVisibility(View.INVISIBLE);
                            ((FrameLayout) myBoard._Grid_Btm_Left.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                        } else {
                            myBoard._Grid_Btm_Left.getChildAt(tempindex).setBackground(view.getBackground());
                            myBoard._Grid_Btm_Left.getChildAt(tempindex).setOnTouchListener(new TouchMove());
                        }
                        myBoard._Grid_Btm_Left.getChildAt(tempindex).setVisibility(View.VISIBLE);
                    } else if (tempindex - index == 4) {
                        hitFunctionClass.Hits(view, v, index, gameState, myBoard);
                        myBoard._Grid_Btm_Left.getChildAt(index + 5).setBackground(view.getBackground());
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                }
                break;
            case R.id._Grid_Btm_Right:
                tempindex = index + 5;
                if (myBoard._Grid_Btm_Right.getChildAt(tempindex).getVisibility() == View.INVISIBLE) {
                    myBoard._Grid_Btm_Right.getChildAt(tempindex).setVisibility(View.VISIBLE);
                    myBoard._Grid_Btm_Right.getChildAt(tempindex).setBackground(view.getBackground());
                    if (gameState.CountBlackOnes(myBoard) >= 15 || canBlackRemove) {
                        _rel_black_remove.setVisibility(View.VISIBLE);
                        canBlackRemove = true;
                    } else {
                        canBlackRemove = false;
                        _rel_black_remove.setVisibility(View.INVISIBLE);
                    }
                } else {
                    while (tempindex > index && myBoard._Grid_Btm_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        tempindex--;
                    }
                    if (myBoard._Grid_Btm_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        String text = ((TextView) ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(1)).getText().toString();
                        int temp = Integer.valueOf(text);
                        temp++;
                        ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(1).setVisibility(View.VISIBLE);
                        ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                        ((TextView) ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(1)).setText(temp + "");
                        if (gameState.CountBlackOnes(myBoard) >= 15 || canBlackRemove) {
                            _rel_black_remove.setVisibility(View.VISIBLE);
                            canBlackRemove = true;
                        } else {
                            canBlackRemove = false;
                            _rel_black_remove.setVisibility(View.INVISIBLE);
                        }
                    } else if (isDrEqual(myBoard._Grid_Btm_Right.getChildAt(tempindex + 1).getBackground(), view.getBackground())) {
                        if (myBoard._Grid_Btm_Right.getChildAt(tempindex) instanceof FrameLayout) {
                            ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(0).setBackground(view.getBackground());
                            ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                            ((FrameLayout) myBoard._Grid_Btm_Right.getChildAt(tempindex)).getChildAt(1).setVisibility(View.INVISIBLE);
                        } else {
                            myBoard._Grid_Btm_Right.getChildAt(tempindex).setBackground(view.getBackground());
                            myBoard._Grid_Btm_Right.getChildAt(tempindex).setOnTouchListener(new TouchMove());
                        }
                        myBoard._Grid_Btm_Right.getChildAt(tempindex).setVisibility(View.VISIBLE);
                        if (gameState.CountBlackOnes(myBoard) >= 15 || canBlackRemove) {
                            _rel_black_remove.setVisibility(View.VISIBLE);
                            canBlackRemove = true;
                        } else {
                            canBlackRemove = false;
                            _rel_black_remove.setVisibility(View.INVISIBLE);
                        }
                    } else if (tempindex - index == 4) {
                        //Hits
                        hitFunctionClass.Hits(view, v, index, gameState, myBoard);
                        myBoard._Grid_Btm_Right.getChildAt(index + 5).setBackground(view.getBackground());
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                }
                break;
            case R.id._Grid_Top_Left:
                tempindex = index;
                if (myBoard._Grid_Top_Left.getChildAt(tempindex).getVisibility() == View.INVISIBLE) {
                    myBoard._Grid_Top_Left.getChildAt(tempindex).setBackground(view.getBackground());
                    myBoard._Grid_Top_Left.getChildAt(tempindex).setVisibility(View.VISIBLE);
                } else {
                    while (tempindex < index + 5 && myBoard._Grid_Top_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        tempindex++;
                    }
                    if (myBoard._Grid_Top_Left.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        String text = ((TextView) ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(1)).getText().toString();
                        int temp = Integer.valueOf(text);
                        temp++;
                        ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(1).setVisibility(View.VISIBLE);
                        ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                        ((TextView) ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(1)).setText(temp + "");
                    } else if (isDrEqual(myBoard._Grid_Top_Left.getChildAt(tempindex - 1).getBackground(), view.getBackground())) {
                        if (myBoard._Grid_Top_Left.getChildAt(tempindex) instanceof FrameLayout) {
                            ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(0).setBackground(view.getBackground());
                            ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(1).setVisibility(View.INVISIBLE);
                            ((FrameLayout) myBoard._Grid_Top_Left.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                        } else {
                            myBoard._Grid_Top_Left.getChildAt(tempindex).setBackground(view.getBackground());
                            myBoard._Grid_Top_Left.getChildAt(tempindex).setOnTouchListener(new TouchMove());
                        }
                        myBoard._Grid_Top_Left.getChildAt(tempindex).setVisibility(View.VISIBLE);
                    } else if (tempindex - index == 1) {
                        // hits
                        hitFunctionClass.Hits(view, v, index, gameState, myBoard);
                        myBoard._Grid_Top_Left.getChildAt(index).setBackground(view.getBackground());
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                    }
                }
                break;
            case R.id._Grid_Top_Right:
                tempindex = index;
                if (myBoard._Grid_Top_Right.getChildAt(tempindex).getVisibility() == View.INVISIBLE) {
                    myBoard._Grid_Top_Right.getChildAt(tempindex).setBackground(view.getBackground());
                    myBoard._Grid_Top_Right.getChildAt(tempindex).setVisibility(View.VISIBLE);
                    if (gameState.CountRedOnes(myBoard) >= 15 || canRedRemove) {
                        _rel_red_remove.setVisibility(View.VISIBLE);
                        canRedRemove = true;
                    } else {
                        canRedRemove = false;
                        _rel_red_remove.setVisibility(View.INVISIBLE);
                    }
                } else {
                    while (tempindex < index + 5 && myBoard._Grid_Top_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        tempindex++;
                    }
                    if (myBoard._Grid_Top_Right.getChildAt(tempindex).getVisibility() == View.VISIBLE) {
                        String text = ((TextView) ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(1)).getText().toString();
                        int temp = Integer.valueOf(text);
                        temp++;
                        ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(1).setVisibility(View.VISIBLE);
                        ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                        ((TextView) ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(1)).setText(temp + "");
                        if (gameState.CountRedOnes(myBoard) >= 15 || canRedRemove) {
                            _rel_red_remove.setVisibility(View.VISIBLE);
                            canRedRemove = true;
                        } else {
                            canRedRemove = false;
                            _rel_red_remove.setVisibility(View.INVISIBLE);
                        }
                    } else if (isDrEqual(myBoard._Grid_Top_Right.getChildAt(tempindex - 1).getBackground(), view.getBackground())) {
                        if (myBoard._Grid_Top_Right.getChildAt(tempindex) instanceof FrameLayout) {
                            ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(0).setBackground(view.getBackground());
                            ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(0).setOnTouchListener(new TouchMove());
                            ((FrameLayout) myBoard._Grid_Top_Right.getChildAt(tempindex)).getChildAt(1).setVisibility(View.INVISIBLE);
                        } else {
                            myBoard._Grid_Top_Right.getChildAt(tempindex).setBackground(view.getBackground());
                            myBoard._Grid_Top_Right.getChildAt(tempindex).setOnTouchListener(new TouchMove());
                        }
                        myBoard._Grid_Top_Right.getChildAt(tempindex).setVisibility(View.VISIBLE);
                        if (gameState.CountRedOnes(myBoard) >= 15 || canRedRemove) {
                            _rel_red_remove.setVisibility(View.VISIBLE);
                            canRedRemove = true;
                        } else {
                            canRedRemove = false;
                            _rel_red_remove.setVisibility(View.INVISIBLE);
                        }
                    } else if (tempindex - index == 1) {
                        //Hits
                        hitFunctionClass.Hits(view, v, index, gameState, myBoard);
                        myBoard._Grid_Top_Right.getChildAt(index).setBackground(view.getBackground());
                    } else {
                        isMove = false;
                        MakeFramlayoutVisible(view);
                        return false;/////return false if no remove operation happen.
                    }
                }
                break;
        }

        if (isMove) {
            gameState.IncreaseListState(v, index);
            gameState.DecreaseListState(view, primeIndex / 6);
            gameState.FillRBState();
            Log.d("MI", "ChooseView: " + Arrays.toString(gameState.getListState()));
            myDice.CheckDice();

            if ((isRedTurn && hitFunctionClass.getRed_Hits() == 0)) {
                gameState.HandlePossibleStates(hitFunctionClass, myDice, this, myBoard);
            }

            if (gameState.isCanMove()) {
                if (isRedTurn) {
                    myDice.ChangeTurn(false);
                } else {
                    myDice.ChangeTurn(true);
                }
            }

        }
        return true;
    }

    private void MakeFramlayoutVisible(View view) {
        if (view.getParent() instanceof FrameLayout) {
            FrameLayout vparent = (FrameLayout) view.getParent();
            if (vparent.getId() != R.id._Frame_Red_Hit && vparent.getId() != R.id._Frame_Black_Hit) {
                TextView vtext = (TextView) vparent.getChildAt(1);
                int count = Integer.valueOf(vtext.getText().toString());
                count += 1;
                vtext.setText(String.valueOf(count));
                if (count == 0) {
                    view.setVisibility(View.VISIBLE);
                } else if (count > 0) {
                    view.setVisibility(View.VISIBLE);
                    vtext.setVisibility(View.VISIBLE);
                }
            }
        } else
            view.setVisibility(View.VISIBLE);
    }

}
