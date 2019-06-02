package com.example.m3000j.backgammon;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

    FrameLayout myGrid;
    public static Context context;
    public static boolean isRedTurn = true, isDrop = false;
    public static boolean isMove = false, isParentChanged = false;
    public static boolean canBlackRemove = false, canRedRemove = false;
    public static byte numMoves = 2;
    public static boolean isPair = false;
    public ImageView _Img_Change_Person_Picture, _Img_Change_Mashin_Picture, _Img_Red_Remove, _Img_Black_Remove;
    public static int tindex;
    public static Difficulty difficulty = Difficulty.Easy;
    public TextView _Txt_Diffculty;
    AlertDialog levelDialog;
    public static AiClass aiClass;
    CharSequence[] values = {"Easy", "Medium", "Hard"};
    int totalEnd = 0;
    public static Button _Btn_Restart;
    LinearLayout _Linear_Layout_Change_Color;

    public static int tempindex = 0;
    FrameLayout _Fram1, _Fram2, _Fram3;
    DiceClass myDice;
    Button _Btn_Dice;
    public static int primeIndex = 0;
    BoardClass myBoard;
    GameState gameState;
    ControlMovementClass controlMovement;
    HitFunctionClass hitFunctionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        myBoard = new BoardClass(getApplicationContext());
        gameState = new GameState(getApplicationContext());
        myDice = new DiceClass(getApplicationContext());
        controlMovement = new ControlMovementClass(getApplicationContext());
        hitFunctionClass = new HitFunctionClass(getApplicationContext());
        aiClass = new AiClass(getApplicationContext(), myDice, gameState, hitFunctionClass, controlMovement, myBoard);

        FindView();
        context = this;

        myDice._Txt_Red_Move.setText(String.valueOf(myDice.getRed_moves()));
        myDice._Txt_Black_Move.setText(String.valueOf(myDice.getBlack_moves()));

        gameState.FillRBState();
        myBoard.PreaperBoard();

    }

    //todo make the game end;
    public static void ShowDialog(int rc, String color) {
        if (rc >= 15) {
            _Btn_Restart.setVisibility(View.VISIBLE);
            isRedTurn = true;
            Toast.makeText(context, color + " won the game", Toast.LENGTH_SHORT).show();
        }
    }


    private void FindView() {
        _Btn_Restart = findViewById(R.id._Btn_Restart);
        _Btn_Restart.setVisibility(View.INVISIBLE);
        _Btn_Restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        _Txt_Diffculty = findViewById(R.id._Txt_Difficulty);
        _Txt_Diffculty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiffcultyDialog();
            }
        });
        _Fram1 = findViewById(R.id._Fram1);
        _Fram2 = findViewById(R.id._Fram2);
        _Fram3 = findViewById(R.id._Fram3);
        _Img_Black_Remove = findViewById(R.id._Img_Black_Remove);
        _Img_Red_Remove = findViewById(R.id._Img_Red_Remove);
        myBoard._Grid_Btm_Left = findViewById(R.id._Grid_Btm_Left);
        myBoard._Grid_Btm_Right = findViewById(R.id._Grid_Btm_Right);
        myBoard._Grid_Top_Left = findViewById(R.id._Grid_Top_Left);
        myBoard._Grid_Top_Right = findViewById(R.id._Grid_Top_Right);
        myBoard._Linear_positions_Top_Left = findViewById(R.id._Linear_positions_Top_Left);
        myBoard._Linear_positions_Top_Right = findViewById(R.id._Linear_positions_Top_Right);
        myBoard._Linear_positions_Btm_Left = findViewById(R.id._Linear_positions_Btm_Left);
        myBoard._Linear_positions_Btm_Right = findViewById(R.id._Linear_positions_Btm_Right);
        _Linear_Layout_Change_Color = findViewById(R.id._Linear_Change_Color);
        myBoard._Rel_Black_Remove = findViewById(R.id._Rel_Black_Remove);
        myGrid = findViewById(R.id._MyGrid);
        myBoard._Grid_Btm_Right.setOnDragListener(new DropToView());
        myBoard._Grid_Top_Right.setOnDragListener(new DropToView());
        myBoard._Grid_Top_Left.setOnDragListener(new DropToView());
        myBoard._Grid_Btm_Left.setOnDragListener(new DropToView());

        _Img_Change_Mashin_Picture = findViewById(R.id._Img_Change_Mashin_Picture);
        _Img_Change_Person_Picture = findViewById(R.id._Img_Change_Person_Picture);

        hitFunctionClass._Fram_Black_Hit = findViewById(R.id._Frame_Black_Hit);
        hitFunctionClass._Fram_Red_Hit = findViewById(R.id._Frame_Red_Hit);

        myBoard._Img_Black_Hit = findViewById(R.id._Img_Black_Hit);
        myBoard._Img_Red_Hit = findViewById(R.id._Img_Red_Hit);


        hitFunctionClass._Txt_Black_Hit = findViewById(R.id._Txt_Black_Hit);
        hitFunctionClass._Txt_Red_Hit = findViewById(R.id._Txt_Red_Hit);

        myDice._Txt_Black_Move = findViewById(R.id._Txt_Black_Moves);
        myDice._Txt_Red_Move = findViewById(R.id._Txt_Red_Moves);

        myDice._Txt_First_Dice = findViewById(R.id._Txt_First_Dice);
        myDice._Txt_Second_Dice = findViewById(R.id._Txt_Second_Dice);

        myDice._Linear_Black_Turn = findViewById(R.id._Linear_Black_Turn);
        myDice._Linear_Red_Turn = findViewById(R.id._Linear_Red_Turn);

        controlMovement._rel_black_remove = findViewById(R.id._Rel_Black_Remove);
        controlMovement._rel_red_remove = findViewById(R.id._Rel_Red_Remove);

        controlMovement._rel_red_remove.setOnDragListener(new DropToView());
        controlMovement._rel_black_remove.setOnDragListener(new DropToView());

        controlMovement._rel_red_remove.setVisibility(View.INVISIBLE);
        controlMovement._rel_black_remove.setVisibility(View.INVISIBLE);

        controlMovement._Txt_Black_Remove = findViewById(R.id._Txt_Black_Remove);
        controlMovement._Txt_Red_Remove = findViewById(R.id._Txt_Red_Remove);

        _Btn_Dice = findViewById(R.id._Btn_Roll);
        _Btn_Dice.setOnClickListener(v -> {
            myDice.RollDice();
            if (GameState.ismanual) {
                GameState.ismanual = false;
                isRedTurn = true;
                myDice._Linear_Black_Turn.setBackground(null);
                myDice._Linear_Red_Turn.setBackgroundColor(getResources().getColor(R.color.colorTurnBackground));
            }
            myBoard.Remove_All_Textviews_Color();
            gameState.setCanMove(true);

            gameState.HandlePossibleStates(hitFunctionClass, myDice, controlMovement, myBoard);
        });

        _Img_Change_Person_Picture.setOnClickListener(v -> {
            if (myDice.getRed_moves() == 278)
                _Linear_Layout_Change_Color.setVisibility(View.VISIBLE);
            else
                Toast.makeText(GameActivity.this, "can't change color of beads in middle of the game", Toast.LENGTH_SHORT).show();
        });

        _Fram1.setOnClickListener(v -> {
            _Linear_Layout_Change_Color.setVisibility(View.GONE);
            myBoard.personColor = R.drawable.p1;
            myBoard.mashinColor = R.drawable.p0;
            _Img_Red_Remove.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            _Img_Black_Remove.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            myBoard._Img_Red_Hit.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard._Img_Black_Hit.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Mashin_Picture.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Person_Picture.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard.PreaperBoard();

        });

        _Fram2.setOnClickListener(v -> {
            _Linear_Layout_Change_Color.setVisibility(View.GONE);
            myBoard.personColor = R.drawable.p11;
            myBoard.mashinColor = R.drawable.p12;
            _Img_Red_Remove.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            _Img_Black_Remove.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Red_Remove.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            _Img_Black_Remove.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            myBoard._Img_Red_Hit.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard._Img_Black_Hit.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Mashin_Picture.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Person_Picture.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard.PreaperBoard();
        });

        _Fram3.setOnClickListener(v -> {
            _Linear_Layout_Change_Color.setVisibility(View.GONE);
            myBoard.personColor = R.drawable.p111;
            myBoard.mashinColor = R.drawable.p112;
            _Img_Red_Remove.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            _Img_Black_Remove.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            myBoard._Img_Red_Hit.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard._Img_Black_Hit.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Mashin_Picture.setImageDrawable(getResources().getDrawable(myBoard.mashinColor));
            _Img_Change_Person_Picture.setImageDrawable(getResources().getDrawable(myBoard.personColor));
            myBoard.PreaperBoard();
        });

    }


    public void ShowDiffcultyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this, R.style.MyDialogTheme);
        builder.setTitle("Select The Difficulty Level");
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        _Txt_Diffculty.setText("Easy");
                        difficulty = Difficulty.Easy;
                        break;
                    case 1:
                        _Txt_Diffculty.setText("Medium");
                        difficulty = Difficulty.Medium;
                        break;
                    case 2:
                        _Txt_Diffculty.setText("Hard");
                        difficulty = Difficulty.Hard;
                        break;
                }
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public static boolean isDrEqual(Drawable r, Drawable r1) {
        if (r == null || r1 == null) {
            return false;
        }
        Drawable.ConstantState rcs = r.getConstantState();
        Drawable.ConstantState rcs1 = r1.getConstantState();
        if (rcs.equals(rcs1)) {
            return true;
        } else return false;
    }

    private int calculateStartIndex(GridLayout mGrid, float x) {
        // calculate which column to move to
        final float cellWidth = mGrid.getWidth() / mGrid.getColumnCount();
        final int column = (int) (x / cellWidth);

        // the items in the GridLayout is organized as a wrapping list
        // and not as an actual grid, so this is how to get the new index
        int index = column * 6;
        if (index >= mGrid.getChildCount()) {
            index = mGrid.getChildCount() - 1;
        }
        return index;
    }

    public class DropToView implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    isDrop = true;
                    View view = (View) event.getLocalState();
                    GridLayout target;
                    int index = 0;
                    if (v instanceof GridLayout) {
                        target = (GridLayout) v;
                        index = calculateStartIndex(target, event.getX());
                    }
                    if (view.getParent() instanceof FrameLayout) {
                        if (isDrEqual(view.getBackground(), getResources().getDrawable(myBoard.personColor)) && isRedTurn) {
                            if (hitFunctionClass.getRed_Hits() > 0) {
                                hitFunctionClass.RemoveHits(v, view, index, controlMovement, myBoard, gameState, myDice);
                            } else {
                                GridLayout parent = (GridLayout) view.getParent().getParent();
                                controlMovement.ControlRedBeads(parent.getId(), v, view, index, hitFunctionClass, myDice, gameState, myBoard);
                            }
                        } else if (isDrEqual(view.getBackground(), getResources().getDrawable(myBoard.mashinColor)) && !isRedTurn) {
                            if (hitFunctionClass.getBlack_Hits() > 0) {
                                hitFunctionClass.RemoveHits(v, view, index, controlMovement, myBoard, gameState, myDice);
                            } else {
                                GridLayout parent = (GridLayout) view.getParent().getParent();
                                controlMovement.ControlBlackBeads(parent.getId(), v, view, index, hitFunctionClass, myDice, gameState, myBoard);
                            }
                        } else {
                            view.setVisibility(View.VISIBLE);
                        }
                    } else {
                        GridLayout parent = (GridLayout) view.getParent();
                        if (isDrEqual(view.getBackground(), getResources().getDrawable(myBoard.personColor)) && isRedTurn) {
                            controlMovement.ControlRedBeads(parent.getId(), v, view, index, hitFunctionClass, myDice, gameState, myBoard);
                        } else {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!event.getResult()) {
                        View view1 = (View) event.getLocalState();
                        if (view1.getParent() instanceof FrameLayout) {
                            FrameLayout vparent = (FrameLayout) view1.getParent();
                            if (vparent.getId() != R.id._Frame_Red_Hit && vparent.getId() != R.id._Frame_Black_Hit) {
                                TextView vtext = (TextView) vparent.getChildAt(1);
                                int count = Integer.valueOf(vtext.getText().toString());
                                totalEnd++;
                                if (totalEnd == 4) {
                                    count += 1;
                                    totalEnd = 0;
                                }
                                vtext.setText(String.valueOf(count));
                                if (count == 0) {
                                    view1.setVisibility(View.VISIBLE);
                                } else if (count > 0) {
                                    view1.setVisibility(View.VISIBLE);
                                    vtext.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            view1.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
            return true;
        }
    }
}