package com.example.m3000j.backgammon;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.m3000j.backgammon.GameActivity.isMove;
import static com.example.m3000j.backgammon.GameActivity.isRedTurn;
import static com.example.m3000j.backgammon.GameActivity.numMoves;

public class GameNode {
    List<PossibleMove> firstDice_PossibleMoves;
    List<PossibleMove> secondDice_PossibleMoves;

    public GameNode() {
        firstDice_PossibleMoves = new ArrayList<>();
        secondDice_PossibleMoves = new ArrayList<>();
    }

    public void MakeNewStatesForPossibleMoves(AiClass aiClass) {
        if (firstDice_PossibleMoves.size() > 0 && secondDice_PossibleMoves.size() > 0) {
            Functionsof_First_Second_And_Evalute_The_Childeren((byte) aiClass.getAi_Dices().getDiceTemp().getSecondTemp(), aiClass.getAi_hitFunctionClass());
            Functionsof_Second_First_And_Evalute_The_Childeren((byte) aiClass.getAi_Dices().getDiceTemp().getFirstTemp(), aiClass.getAi_hitFunctionClass());
        } else if (firstDice_PossibleMoves.size() > 0 && secondDice_PossibleMoves.size() == 0) {
            Functionsof_First_Second_And_Evalute_The_Childeren((byte) aiClass.getAi_Dices().getDiceTemp().getSecondTemp(), aiClass.getAi_hitFunctionClass());
        } else if (firstDice_PossibleMoves.size() == 0 && secondDice_PossibleMoves.size() > 0) {
            Functionsof_Second_First_And_Evalute_The_Childeren((byte) aiClass.getAi_Dices().getDiceTemp().getFirstTemp(), aiClass.getAi_hitFunctionClass());
        } else {
            ChangeTurn(aiClass);
        }
    }


    private void Functionsof_First_And_Evalute_OnlyFathers(HitFunctionClass hfc) {
        MakeNewStatesFor_firstDiceMoves();
        EvaluteOnlyFirst_Fathers(hfc);
    }

    private void Functionsof_Second_And_Evalute_OnlyFathers(HitFunctionClass hfc) {
        MakeNewStatesFor_secondDiceMoves();
        EvaluteOnlySecond_Fathers(hfc);
    }

    private void EvaluteOnlySecond_Fathers(HitFunctionClass hfc) {
        for (int i = 0; i < secondDice_PossibleMoves.size(); i++) {
            secondDice_PossibleMoves.get(i).evaluate(hfc, secondDice_PossibleMoves.get(i).getTo());
        }
    }

    private void EvaluteOnlyFirst_Fathers(HitFunctionClass hfc) {
        for (int i = 0; i < firstDice_PossibleMoves.size(); i++) {
            firstDice_PossibleMoves.get(i).evaluate(hfc, firstDice_PossibleMoves.get(i).getTo());
        }
    }

    public void ChooseMove(AiClass aiClass) {
        if (aiClass.getAi_hitFunctionClass().getBlack_Hits() == 0) {
            if (firstDice_PossibleMoves.size() > 0 && secondDice_PossibleMoves.size() > 0) {
                Set_Children_MaxWeight_For_Every_Father();
                Log.d("MY1", "ChooseMove: Hard do");
                PossibleMove selected_Father = Find_Max_Possible_Move_Between_first_And_Second_Moves_From_FirstLEVEL();
                aiClass.MovePossibleMove(true, selected_Father);
                PossibleMove selected_child_From_selected_Father;

                if (selected_Father.getChildren_maxWieght() != -2) {

                    if (selected_Father.gameNode.secondDice_PossibleMoves == null) {
                        if (selected_Father.gameNode.firstDice_PossibleMoves.size() != 0) {
                            selected_child_From_selected_Father = FindMaxPossibleMove_FromChildren(selected_Father.gameNode.firstDice_PossibleMoves);
                            aiClass.MovePossibleMove(false, selected_child_From_selected_Father);
                        } else {
                            ChangeTurn(aiClass);
                        }
                    } else {
                        if (selected_Father.gameNode.secondDice_PossibleMoves.size() != 0) {
                            selected_child_From_selected_Father = FindMaxPossibleMove_FromChildren(selected_Father.gameNode.secondDice_PossibleMoves);
                            aiClass.MovePossibleMove(false, selected_child_From_selected_Father);
                        } else {
                            ChangeTurn(aiClass);
                        }
                    }


                } else {
                    aiClass.MovePossibleMove(true, selected_Father);
                    numMoves = 1;
                    isMove = true;
                    aiClass.getAi_Dices().ChangeTurn(true);
                }

            } else if (firstDice_PossibleMoves.size() > 0 && secondDice_PossibleMoves.size() == 0) {
                Log.d("MY2", "ChooseMove: avali do");
                PossibleMove selected_Father = Find_Max_Possible_Move_Between_first_And_Second_Moves_From_FirstLEVEL();
                aiClass.MovePossibleMove(true, selected_Father);

                if (aiClass.getAi_Dices().getDiceTemp().getSecondDice() > 0) {
                    if (selected_Father.gameNode.secondDice_PossibleMoves.size() != 0) {
                        PossibleMove selected_child_From_selected_Father = FindMaxPossibleMove_FromChildren(selected_Father.gameNode.secondDice_PossibleMoves);
                        aiClass.MovePossibleMove(false, selected_child_From_selected_Father);
                    } else {
                        ChangeTurn(aiClass);
                    }
                } else {
                    ChangeTurn(aiClass);
                }

            } else if (firstDice_PossibleMoves.size() == 0 && secondDice_PossibleMoves.size() > 0) {
                Log.d("MY3", "ChooseMove: dovomi do");
                PossibleMove selected_Father = Find_Max_Possible_Move_Between_first_And_Second_Moves_From_FirstLEVEL();
                aiClass.MovePossibleMove(false, selected_Father);

                if (aiClass.getAi_Dices().getDiceTemp().getFirstDice() > 0) {
                    if (selected_Father.gameNode.firstDice_PossibleMoves.size() != 0) {
                        PossibleMove selected_child_From_selected_Father = FindMaxPossibleMove_FromChildren(selected_Father.gameNode.firstDice_PossibleMoves);
                        aiClass.MovePossibleMove(true, selected_child_From_selected_Father);
                    } else {
                        ChangeTurn(aiClass);
                    }
                } else {
                    ChangeTurn(aiClass);
                }

            } else {
                ChangeTurn(aiClass);
            }
        } else {
            if (aiClass.getAi_hitFunctionClass().getBlack_Hits() > 0) {
                aiClass.MovePossibleMove(true, null);
            } else {
                ChangeTurn(aiClass);
            }
        }
    }

    private void ChangeTurn(AiClass aiClass) {
        if (!isRedTurn && !aiClass.getAi_hitFunctionClass().isHit) {
            numMoves = 1;
            isMove = true;
            aiClass.getAi_Dices().ChangeTurn(true);
        }
    }

    private PossibleMove Find_Max_Possible_Move_From_Fathers_Only(List<PossibleMove> temp_Lists) {
        double max_wieght = 0;
        PossibleMove max_one = null;
        byte temp_size = (byte) temp_Lists.size();
        max_wieght = temp_Lists.get(0).getWeight();
        max_one = temp_Lists.get(0);
        for (int i = 1; i < temp_size; i++) {
            if (temp_Lists.get(i).getWeight() >= max_wieght) {
                max_wieght = temp_Lists.get(i).getWeight();
                max_one = temp_Lists.get(i);
            }
        }
        return max_one;
    }


    private PossibleMove Find_Max_Possible_Move_Between_first_And_Second_Moves_From_FirstLEVEL() {

        PossibleMove temp_First_List_Possiblemove = Find_Max_Possible_Move_Between_Fathers(firstDice_PossibleMoves);
        PossibleMove temp_Second_List_Possiblemove = Find_Max_Possible_Move_Between_Fathers(secondDice_PossibleMoves);

        if (temp_First_List_Possiblemove == null) {
            return temp_Second_List_Possiblemove;
        }
        if (temp_Second_List_Possiblemove == null) {
            return temp_First_List_Possiblemove;
        }

        if (temp_First_List_Possiblemove.getChildren_maxWieght() != -2 || temp_Second_List_Possiblemove.getChildren_maxWieght() != 2) {
            if (temp_First_List_Possiblemove.getChildren_maxWieght() == temp_Second_List_Possiblemove.getChildren_maxWieght()) {
                if (temp_First_List_Possiblemove.getWeight() >= temp_Second_List_Possiblemove.getWeight()) {
                    return temp_First_List_Possiblemove;
                }
                return temp_Second_List_Possiblemove;
            }
            if (temp_First_List_Possiblemove.getChildren_maxWieght() > temp_Second_List_Possiblemove.getChildren_maxWieght()) {
                return temp_First_List_Possiblemove;
            }
            return temp_Second_List_Possiblemove;
        }
        if (temp_First_List_Possiblemove.getWeight() >= temp_Second_List_Possiblemove.getWeight()) {
            return temp_First_List_Possiblemove;
        }
        return temp_Second_List_Possiblemove;
    }

    private PossibleMove Find_Max_Possible_Move_Between_Fathers(List<PossibleMove> temp_Lists) {
        if (temp_Lists.size() > 0) {
            double max_wieght = 0;
            PossibleMove max_one = null;
            byte temp_size = (byte) temp_Lists.size();
            max_wieght = temp_Lists.get(0).getChildren_maxWieght();
            max_one = temp_Lists.get(0);
            for (int i = 1; i < temp_size; i++) {
                if (temp_Lists.get(i).getChildren_maxWieght() >= max_wieght) {
                    max_wieght = temp_Lists.get(i).getChildren_maxWieght();
                    max_one = temp_Lists.get(i);
                }
            }
            if (max_wieght == -2) {

                max_one = Find_Max_Possible_Move_From_Fathers_Only(temp_Lists);
            }
            return max_one;
        }
        return null;
    }

    private PossibleMove FindMaxPossibleMove_FromChildren(List<PossibleMove> temp_Lists) {
        double max_wieght = 0;
        PossibleMove max_one = null;
        byte temp_size = (byte) temp_Lists.size();
        max_wieght = temp_Lists.get(0).getWeight();
        max_one = temp_Lists.get(0);
        for (int i = 1; i < temp_size; i++) {
            if (temp_Lists.get(i).getWeight() >= max_wieght) {
                max_wieght = temp_Lists.get(i).getWeight();
                max_one = temp_Lists.get(i);
            }
        }
        return max_one;
    }

    private void Set_Children_MaxWeight_For_Every_Father() {


        for (PossibleMove pm : firstDice_PossibleMoves
        ) {
            pm.setChildren_maxWieght(FindMaxWieght_In_Childeren_Of_OneFather_Of_FirstMoves(pm));
        }

        for (PossibleMove pm : secondDice_PossibleMoves
        ) {
            pm.setChildren_maxWieght(FindMaxWieght_In_Childeren_Of_OneFather_Of_SecondMoves(pm));
        }

    }


    private double FindMaxWieght_In_Childeren_Of_OneFather_Of_SecondMoves(PossibleMove possibleMove) {
        if (possibleMove.gameNode.firstDice_PossibleMoves.size() != 0) {
            double max_wieght = 0;
            byte temp_size = (byte) possibleMove.gameNode.firstDice_PossibleMoves.size();
            max_wieght = possibleMove.gameNode.firstDice_PossibleMoves.get(0).getWeight();
            for (int i = 1; i < temp_size; i++) {
                if (possibleMove.gameNode.getFirstDice_PossibleMoves().get(i).getWeight() >= max_wieght) {
                    max_wieght = possibleMove.gameNode.getFirstDice_PossibleMoves().get(i).getWeight();
                }
            }
            return max_wieght;
        }
        return -2;
    }

    private double FindMaxWieght_In_Childeren_Of_OneFather_Of_FirstMoves(PossibleMove possibleMove) {
        if (possibleMove.gameNode.secondDice_PossibleMoves.size() != 0) {
            double max_wieght = 0;
            byte temp_size = (byte) possibleMove.gameNode.secondDice_PossibleMoves.size();
            max_wieght = possibleMove.gameNode.secondDice_PossibleMoves.get(0).getWeight();
            for (int i = 1; i < temp_size; i++) {
                if (possibleMove.gameNode.getSecondDice_PossibleMoves().get(i).getWeight() >= max_wieght) {
                    max_wieght = possibleMove.gameNode.getSecondDice_PossibleMoves().get(i).getWeight();
                }
            }
            return max_wieght;
        }
        return -2;
    }

    private void Functionsof_Second_First_And_Evalute_The_Childeren(byte dice, HitFunctionClass hit) {
        Functionsof_Second_And_Evalute_OnlyFathers(hit);
        MakeNewStatesFor_second_FirstMove(dice);
        Evaluate_Second_First(hit);
    }

    private void Functionsof_First_Second_And_Evalute_The_Childeren(byte dice, HitFunctionClass hit) {
        Functionsof_First_And_Evalute_OnlyFathers(hit);
        MakeNewStatesFor_first_SecondMove(dice);
        Evaluate_First_Second(hit);
    }

    private void Evaluate_First_Second(HitFunctionClass hit) {
        for (int i = 0; i < firstDice_PossibleMoves.size(); i++) {
            PossibleMove possibleMove = firstDice_PossibleMoves.get(i);
            for (int j = 0; j < possibleMove.gameNode.secondDice_PossibleMoves.size(); j++) {
                possibleMove.gameNode.secondDice_PossibleMoves.get(j).evaluate(hit, possibleMove.gameNode.secondDice_PossibleMoves.get(j).getTo());
            }
        }
    }

    private void Evaluate_Second_First(HitFunctionClass hit) {
        for (int i = 0; i < secondDice_PossibleMoves.size(); i++) {
            PossibleMove possibleMove = secondDice_PossibleMoves.get(i);
            for (int j = 0; j < possibleMove.gameNode.firstDice_PossibleMoves.size(); j++) {
                possibleMove.gameNode.firstDice_PossibleMoves.get(j).evaluate(hit, possibleMove.gameNode.firstDice_PossibleMoves.get(j).getTo());

            }
        }
    }

    private void MakeNewStatesFor_second_FirstMove(byte dice) {
        PossibleMove temp;
        if (dice > 0) {
            for (int i = 0; i < secondDice_PossibleMoves.size(); i++) {
                temp = secondDice_PossibleMoves.get(i);
                temp.gameNode.secondDice_PossibleMoves = null;
                temp.gameNode.firstDice_PossibleMoves = temp.FindPossibleMoves(dice);
            }
            for (int i = 0; i < secondDice_PossibleMoves.size(); i++) {
                PossibleMove innertemp = secondDice_PossibleMoves.get(i);
                for (int j = 0; j < innertemp.gameNode.firstDice_PossibleMoves.size(); j++) {
                    innertemp.gameNode.firstDice_PossibleMoves.get(j).MakeNewState_For_One_PossibleMove();
                    innertemp.gameNode.firstDice_PossibleMoves.get(j).gameStateLists.Fill_RB_States();
                }
            }
        }
    }

    private void MakeNewStatesFor_first_SecondMove(byte dice) {
        PossibleMove temp;
        if (dice > 0) {
            for (int i = 0; i < firstDice_PossibleMoves.size(); i++) {
                temp = firstDice_PossibleMoves.get(i);
                temp.gameNode.firstDice_PossibleMoves = null;
                temp.gameNode.secondDice_PossibleMoves = temp.FindPossibleMoves(dice);
            }
            for (int i = 0; i < firstDice_PossibleMoves.size(); i++) {
                PossibleMove innertemp = firstDice_PossibleMoves.get(i);
                for (int j = 0; j < innertemp.gameNode.secondDice_PossibleMoves.size(); j++) {
                    innertemp.gameNode.secondDice_PossibleMoves.get(j).MakeNewState_For_One_PossibleMove();
                    innertemp.gameNode.secondDice_PossibleMoves.get(j).gameStateLists.Fill_RB_States();
                }
            }
        }
    }

    private void MakeNewStatesFor_firstDiceMoves() {
        for (int i = 0; i < firstDice_PossibleMoves.size(); i++) {
            firstDice_PossibleMoves.get(i).MakeNewState_For_One_PossibleMove();
            firstDice_PossibleMoves.get(i).gameStateLists.Fill_RB_States();
        }
    }

    private void MakeNewStatesFor_secondDiceMoves() {
        for (int i = 0; i < secondDice_PossibleMoves.size(); i++) {
            secondDice_PossibleMoves.get(i).MakeNewState_For_One_PossibleMove();
            secondDice_PossibleMoves.get(i).gameStateLists.Fill_RB_States();
        }
    }

    public List<PossibleMove> getFirstDice_PossibleMoves() {
        return firstDice_PossibleMoves;
    }

    public List<PossibleMove> getSecondDice_PossibleMoves() {
        return secondDice_PossibleMoves;
    }

}