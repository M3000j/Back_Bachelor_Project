package com.example.m3000j.backgammon;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import static com.example.m3000j.backgammon.GameActivity.isDrEqual;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;

public class HitFunctionClass {
    final Context context;
    private int red_Hits = 0, black_Hits = 0;
    FrameLayout _Fram_Red_Hit, _Fram_Black_Hit;
    TextView _Txt_Red_Hit, _Txt_Black_Hit;
    boolean isHit = false;

    public HitFunctionClass(Context context) {
        this.context = context;
    }

    public int getRed_Hits() {
        return red_Hits;
    }

    public void setRed_Hits(int red_Hits) {
        this.red_Hits = red_Hits;
    }

    public int getBlack_Hits() {
        return black_Hits;
    }

    public void setBlack_Hits(int black_Hits) {
        this.black_Hits = black_Hits;
    }

    public void Hits(View v, View target, int index, GameState gs, BoardClass myboard) {
        if (isDrEqual(v.getBackground(), context.getResources().getDrawable(myboard.personColor))) {
            _Fram_Black_Hit.setVisibility(View.VISIBLE);
            setBlack_Hits(getBlack_Hits() + 1);
            _Txt_Black_Hit.setText(String.valueOf(getBlack_Hits()));
            gs.DecreaseListStateHits(target, index);
        } else {
            _Fram_Red_Hit.setVisibility(View.VISIBLE);
            setRed_Hits(getRed_Hits() + 1);
            _Txt_Red_Hit.setText(String.valueOf(getRed_Hits()));
            gs.DecreaseListStateHits(target, index);
        }
        gs.FillRBState();
    }

    public boolean RemoveHits(View v, View view, int index, ControlMovementClass controlMovement, BoardClass myBoard, GameState gameState, DiceClass myDice) {
        if (isDrEqual(view.getBackground(), context.getResources().getDrawable(myBoard.personColor))) {
            if (v.getId() == R.id._Grid_Btm_Right) {
                GameActivity.isParentChanged = true;
                isHit = controlMovement.MoveView(v, view, index, myDice, gameState, this, myBoard);
                if (isHit) {
                    setRed_Hits(getRed_Hits() - 1);
                    _Txt_Red_Hit.setText(String.valueOf(getRed_Hits()));
                }
                if (getRed_Hits() == 0) {
                    _Fram_Red_Hit.setVisibility(View.GONE);
                }
                gameState.HandlePossibleStates(this, myDice, controlMovement, myBoard);
            } else {
                GameActivity.isMove = false;
                GameActivity.isParentChanged = false;
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (v.getId() == R.id._Grid_Top_Right) {
                GameActivity.isParentChanged = true;
                isRedTurn = false;
                isHit = controlMovement.MoveView(v, view, index, myDice, gameState, this, myBoard);
                if (isHit) {
                    setBlack_Hits(getBlack_Hits() - 1);
                    _Txt_Black_Hit.setText(String.valueOf(getBlack_Hits()));
                }
                if (getBlack_Hits() == 0) {

                    _Fram_Black_Hit.setVisibility(View.GONE);
                }
                gameState.HandlePossibleStates(this, myDice, controlMovement, myBoard);
            } else {
                GameActivity.isMove = false;
                GameActivity.isParentChanged = false;
                view.setVisibility(View.VISIBLE);
            }
        }
        return isHit;
    }

}
