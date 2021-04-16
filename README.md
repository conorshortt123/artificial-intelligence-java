# Artificial Intelligence Assignment - Conor Shortt
## Autonomous Computer-controlled Game Characters 

A game created in Java that utilizes the encog_3.4 and JFuzzyLogic libraries. Both of which are used determine the appropriate actions for a set of autonomously controlled game characters.

This README details the purpose, design and implementation of the AI components contained within my project. The two artificial intelligence components implemented to control the autonomous game characters were fuzzy logic and neural network controllers. 
 
Fuzzy Logic Controller: 
The fuzzy logic is contained within the res/fuzzy/minotaur.fcl file within the fuzzy directory. This fuzzy logic contains one function block: Attack. This block contains three linguistic input variables: health, stamina, and rage. And one linguistic output variable: attack. Each input variable contains three fuzzy sets: low, medium and high, with a 
universe of discourse in the range zero to one hundred. The output variable attack contains three fuzzy sets: bad, good and best, also with a universe of discourse in the range of zero to one hundred. The attack variable is defuzzified using Center of Gravity. Although an expensive defuzzification algorithm, the total resource usage is still very low as only half of the enemies are using the fuzzy logic controller. There are a total of eight fuzzy rules in the rule block, more than enough to apply to every fuzzy set. 
 
Neural Network Controller: 
The neural network was asset stripped from the three-layer backpropagation neural network lab with a few tweaks to make it suitable for the game. There are four input variables into the neural network: Health (0-2), Stamina (0-2), Rage (0-2), and Player Range (0/1). The expected output variables of the network are Attack (0/1), Run (0/1) or Patrol (0/1). The network uses the sigmoid activation function, with four input layers corresponding to each input variable, two hidden layers, and three output layers corresponding to the three output variables. The neural network was trained with an alpha of 0.01, and 10’000 epochs. 
 
Extras: 
Instead of the network being trained every time the program is ran, the object was serialized and saved into the res/neural/minotaur.nn file, and loaded in at run time to reduce computational overhead. I also wrote a CSV file reader and created CSV files for data and expected values, this completely segregates the end user from the neural network, as they are not required to see any training data. 


