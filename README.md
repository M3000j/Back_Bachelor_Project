# Back_Bachelor_Project
Bachelor Programm
It has 3 level AI->Easy,Medium,Hard
For Easy level, I used random selection from possible moves which was for each dice
For Medium level, I used Evaluation Function and selection based on it
For Hard level,I used Selection based on tree:
instead of making the whole tree, the tree is made from each dice and possible moves from each dice based on the number of dice 
is made.

for example if the number of dices are 3 & 4
First:
Possible moves of 3 is found, then based on these possible moves possible moves of 4 are found.(The left branch of tree)
Second:
Possible moves of 4 is found, then based on these possible moves possible moves of 3 are found.(The right branch of tree)

by using evaluation function, each of the possible moves are scored and the max ones are selected from the each branch,after
finding the max ones, from these two one of them,which has the most score,is selected and the following actions will be choosen
from the branch which has the first selected possible move.
