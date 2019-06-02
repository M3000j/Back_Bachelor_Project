package com.example.m3000j.backgammon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoardClass {

    final Context context;
    public GridLayout _Grid_Btm_Left;
    public GridLayout _Grid_Btm_Right;
    public GridLayout _Grid_Top_Left;
    public GridLayout _Grid_Top_Right;
    public LinearLayout _Linear_positions_Top_Left;
    public LinearLayout _Linear_positions_Top_Right;
    public LinearLayout _Linear_positions_Btm_Left;
    public LinearLayout _Linear_positions_Btm_Right;
    public LinearLayout _Rel_Black_Remove;
    public int personColor = R.drawable.p1;
    public int mashinColor = R.drawable.p0;

    public ImageView get_Img_Black_Hit() {
        return _Img_Black_Hit;
    }

    public ImageView get_Img_Red_Hit() {
        return _Img_Red_Hit;
    }

    public ImageView _Img_Black_Hit, _Img_Red_Hit;

    private List<TextView> _Txt_List_Top_Right_Position = new ArrayList<>();
    private List<TextView> _Txt_List_Top_Left_Position = new ArrayList<>();
    private List<TextView> _Txt_List_Btm_Left_Position = new ArrayList<>();
    private List<TextView> _Txt_List_Btm_Right_Position = new ArrayList<>();

    private List<ImageView> _Img_List_Btm_left = new ArrayList<>();
    private List<ImageView> _Img_List_Btm_right = new ArrayList<>();
    private List<ImageView> _Img_List_Top_left = new ArrayList<>();
    private List<ImageView> _Img_List_Top_right = new ArrayList<>();

    public BoardClass(Context context) {
        this.context = context;
    }

    public List<ImageView> get_Img_List_Btm_left() {
        return _Img_List_Btm_left;
    }

    public List<ImageView> get_Img_List_Btm_right() {
        return _Img_List_Btm_right;
    }


    public List<ImageView> get_Img_List_Top_left() {
        return _Img_List_Top_left;
    }

    public List<ImageView> get_Img_List_Top_right() {
        return _Img_List_Top_right;
    }

    public GridLayout get_Grid_Btm_Left() {
        return _Grid_Btm_Left;
    }

    public GridLayout get_Grid_Btm_Right() {
        return _Grid_Btm_Right;
    }

    public GridLayout get_Grid_Top_Left() {
        return _Grid_Top_Left;
    }

    public GridLayout get_Grid_Top_Right() {
        return _Grid_Top_Right;
    }

    public void PreaperBoard() {
        NewTextViews();
        NewImageViews();
        Add_Txt_Top_Btm_List();
        AddGridBtmLeft();
        AddGridBtmRight();
        AddGridTopRight();
        AddGridTopLeft();

    }

    public void Remove_All_Textviews_Color() {
        for (int i = 0; i < 6; i++) {
            ((TextView) _Linear_positions_Top_Right.getChildAt(i)).setTextColor(Color.WHITE);
            ((TextView) _Linear_positions_Top_Left.getChildAt(i)).setTextColor(Color.WHITE);
            ((TextView) _Linear_positions_Btm_Right.getChildAt(i)).setTextColor(Color.WHITE);
            ((TextView) _Linear_positions_Btm_Left.getChildAt(i)).setTextColor(Color.WHITE);
        }
    }

    private void Add_Txt_Top_Btm_List() {
        TextView tv;
        for (int i = 0; i < 6; i++) {
            tv = _Txt_List_Top_Left_Position.get(i);
            if (tv.getParent() != null) {
                ((ViewGroup) tv.getParent()).removeView(tv); // <- fix
            }
            _Linear_positions_Top_Left.addView(tv);
        }

        for (int i = 0; i < 6; i++) {
            tv = _Txt_List_Top_Right_Position.get(i);
            if (tv.getParent() != null) {
                ((ViewGroup) tv.getParent()).removeView(tv); // <- fix
            }
            _Linear_positions_Top_Right.addView(tv);
        }
        for (int i = 0; i < 6; i++) {
            tv = _Txt_List_Btm_Right_Position.get(i);
            if (tv.getParent() != null) {
                ((ViewGroup) tv.getParent()).removeView(tv); // <- fix
            }
            _Linear_positions_Btm_Right.addView(tv);
        }
        for (int i = 0; i < 6; i++) {
            tv = _Txt_List_Btm_Left_Position.get(i);
            if (tv.getParent() != null) {
                ((ViewGroup) tv.getParent()).removeView(tv); // <- fix
            }
            _Linear_positions_Btm_Left.addView(tv);
        }
    }

    private void NewTextViews() {
        TextView _Txt_Position;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < 6; i++) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            _Txt_Position = new TextView(context.getApplicationContext());
            _Txt_Position.setTextColor(Color.WHITE);
            _Txt_Position.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            _Txt_Position.setPadding(30, 0, 30, 0);
            params.rightMargin = 10;
            _Txt_Position.setTextSize(14f);
            _Txt_Position.setLayoutParams(params);
            _Txt_Position.setText(i + "");
            _Txt_List_Top_Left_Position.add(_Txt_Position);
        }
        for (int i = 6; i < 12; i++) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            _Txt_Position = new TextView(context.getApplicationContext());
            _Txt_Position.setTextColor(Color.WHITE);
            _Txt_Position.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            _Txt_Position.setPadding(30, 0, 30, 0);
            _Txt_Position.setTextSize(14f);
            params.leftMargin = 10;
            _Txt_Position.setLayoutParams(params);
            _Txt_Position.setText(i + "");
            _Txt_List_Top_Right_Position.add(_Txt_Position);
        }
        for (int i = 12; i < 18; i++) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            _Txt_Position = new TextView(context.getApplicationContext());
            _Txt_Position.setTextColor(Color.WHITE);
            _Txt_Position.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            _Txt_Position.setPadding(30, 0, 15, 0);
            params.leftMargin = 15;
            _Txt_Position.setTextSize(14f);
            _Txt_Position.setLayoutParams(params);
            _Txt_Position.setText(i + "");
            _Txt_List_Btm_Left_Position.add(_Txt_Position);
        }

        for (int i = 18; i < 24; i++) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            _Txt_Position = new TextView(context.getApplicationContext());
            _Txt_Position.setTextColor(Color.WHITE);
            _Txt_Position.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            _Txt_Position.setPadding(20, 0, 30, 0);
            params.rightMargin = 10;
            _Txt_Position.setTextSize(14f);
            _Txt_Position.setLayoutParams(params);
            _Txt_Position.setText(i + "");
            _Txt_List_Btm_Right_Position.add(_Txt_Position);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void NewImageViews() {
        ImageView _Img;
        GridLayout.LayoutParams params;
        for (int i = 0; i < 36; i++) {
            _Img = new ImageView(context.getApplicationContext());
            _Img.setVisibility(View.INVISIBLE);
            _Img.setOnTouchListener(new TouchMove());
            _Img.setRotationX(180);
            params = new GridLayout.LayoutParams();
            params.rightMargin = 40;
            params.leftMargin = 9;
            _Img.setLayoutParams(params);
            _Img_List_Top_left.add(_Img);
        }
        for (int i = 0; i < 36; i++) {
            _Img = new ImageView(context.getApplicationContext());
            _Img.setVisibility(View.INVISIBLE);
            _Img.setOnTouchListener(new TouchMove());
            _Img.setRotationX(180);
            params = new GridLayout.LayoutParams();
            params.rightMargin = 40;
            params.leftMargin = 9;
            _Img.setLayoutParams(params);
            _Img_List_Btm_left.add(_Img);
        }
        for (int i = 0; i < 36; i++) {
            _Img = new ImageView(context.getApplicationContext());
            _Img.setVisibility(View.INVISIBLE);
            _Img.setRotationX(180);
            _Img.setOnTouchListener(new TouchMove());
            params = new GridLayout.LayoutParams();
            _Img.setLayoutParams(params);
            _Img_List_Top_right.add(_Img);
        }
        for (int i = 0; i < 36; i++) {
            _Img = new ImageView(context.getApplicationContext());
            _Img.setVisibility(View.INVISIBLE);
            _Img.setRotationX(180);
            _Img.setOnTouchListener(new TouchMove());
            params = new GridLayout.LayoutParams();
            _Img.setLayoutParams(params);
            _Img_List_Btm_right.add(_Img);
        }
        _Img_Red_Hit.setOnTouchListener(new TouchMove());
        _Img_Black_Hit.setOnTouchListener(new TouchMove());

    }

    private FrameLayout MakeFramLayout(int location) {
        FrameLayout frameLayout = new FrameLayout(context);
        GridLayout.LayoutParams gridLayout = new GridLayout.LayoutParams();
        switch (location) {
            case 1:
                gridLayout.rightMargin = 45;
        }
        frameLayout.setLayoutParams(gridLayout);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setText("0");
        ImageView img = new ImageView(context);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setOnTouchListener(new TouchMove());
        img.setBackground(context.getResources().getDrawable(mashinColor));
        if (location == 0)
            params1.leftMargin = 10;

        img.setLayoutParams(params1);

        frameLayout.addView(img);
        frameLayout.addView(textView);
        frameLayout.setVisibility(View.INVISIBLE);
        frameLayout.setRotationX(180);
        return frameLayout;
    }

    private void AddGridTopLeft() {

        _Grid_Top_Left.removeAllViews();
        for (int i = 0; i < 36; i++) {
            if (i < 5) {
                _Img_List_Top_left.get(i).setVisibility(View.VISIBLE);
            }
            if (i >= 24 && i <= 26) {
                _Img_List_Top_left.get(i).setVisibility(View.VISIBLE);
                _Img_List_Top_left.get(i).setBackground(context.getResources().getDrawable(personColor));
            } else {
                _Img_List_Top_left.get(i).setBackground(context.getResources().getDrawable(mashinColor));
            }
            if (i == 5 || i == 11 || i == 17 || i == 23 || i == 29 || i == 35) {
                _Grid_Top_Left.addView(MakeFramLayout(0), i);
            } else {

                _Grid_Top_Left.addView(_Img_List_Top_left.get(i), i);
            }
        }

    }

    private void AddGridTopRight() {

        _Grid_Top_Right.removeAllViews();

        for (int i = 0; i < 36; i++) {

            _Img_List_Top_right.get(i).setBackground(context.getResources().getDrawable(mashinColor));
            if (i < 5) {

                _Img_List_Top_right.get(i).setVisibility(View.VISIBLE);
                _Img_List_Top_right.get(i).setBackground(context.getResources().getDrawable(personColor));

            }
            if (i >= 30 && i <= 31) {

                _Img_List_Top_right.get(i).setVisibility(View.VISIBLE);
                _Img_List_Top_right.get(i).setBackground(context.getResources().getDrawable(mashinColor));

            }
            if (i == 5 || i == 11 || i == 17 || i == 23 || i == 29 || i == 35) {
                _Grid_Top_Right.addView(MakeFramLayout(1), i);
            } else {
                _Grid_Top_Right.addView(_Img_List_Top_right.get(i), i);
            }
        }
    }

    private void AddGridBtmLeft() {
        _Grid_Btm_Left.removeAllViews();
        for (int i = 0; i < 36; i++) {
            if (i <= 5) {
                _Img_List_Btm_left.get(i).setVisibility(View.VISIBLE);
            }
            _Img_List_Btm_left.get(i).setBackground(context.getResources().getDrawable(personColor));
            if (i >= 27 && i <= 29) {
                _Img_List_Btm_left.get(i).setVisibility(View.VISIBLE);
                _Img_List_Btm_left.get(i).setBackground(context.getResources().getDrawable(mashinColor));
            }
            if (i == 0 || i == 6 || i == 12 || i == 18 || i == 24 || i == 30) {
                _Grid_Btm_Left.addView(MakeFramLayout(0), i);
            } else {
                _Grid_Btm_Left.addView(_Img_List_Btm_left.get(i), i);
            }
        }
    }

    private void AddGridBtmRight() {
        _Grid_Btm_Right.removeAllViews();
        for (int i = 0; i < 36; i++) {

            _Img_List_Btm_right.get(i).setBackground(context.getResources().getDrawable(mashinColor));

            if (i <= 5) {
                _Img_List_Btm_right.get(i).setVisibility(View.VISIBLE);
            }

            if (i >= 34) {
                _Img_List_Btm_right.get(i).setVisibility(View.VISIBLE);
                _Img_List_Btm_right.get(i).setBackground(context.getResources().getDrawable(personColor));
            }
            if (i == 0 || i == 6 || i == 12 || i == 18 || i == 24 || i == 30) {
                _Grid_Btm_Right.addView(MakeFramLayout(1), i);
            } else {
                _Grid_Btm_Right.addView(_Img_List_Btm_right.get(i), i);
            }
        }
    }

}
