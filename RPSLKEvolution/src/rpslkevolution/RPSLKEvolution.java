package rpslkevolution;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RPSLKEvolution
{
    
    public static void main(String[] args) throws IOException
    {
        
        PrintWriter fileOut = new PrintWriter(new FileWriter("output.csv"));
        
        fileOut.println("Generation,RockUtility,PaperUtility,ScissorsUtility,LizardUtility,SpockUtility,TOTALUTILITY,RockSpecialists,PaperSpecialists,ScissorsSpecialists,LizardSpecialists,SpockSpecialists");
        
        int populationSize = 200;
        int numGenerations = 30000;
        
        ArrayList<Strategy> Population = new ArrayList<>();
        ArrayList<Strategy> newGeneration;
        
        for (int i = 0; i < populationSize; i++)
        {
            Strategy newStrategy = new Strategy();
            Population.add(newStrategy);
        }
        
        for (int g = 0; g < numGenerations; g++)
        {
            
            for (int i = 0; i < populationSize; i++)
            {
                for (int j = 0; j < populationSize; j++)
                {
                    int winner = 0;
                    String play1 = "";
                    String play2 = "";
                    Strategy s1 = Population.get(i);
                    Strategy s2 = Population.get(j);
                    
                    if (i == j)
                    {
                        continue;
                    }
                    
                    play1 = s1.getPlay(s2);
                    play2 = s2.getPlay(s1);
                    
                    winner = getWinner(play1, play2);
                    
                    if (winner == 1)
                    {
                        s1.incrementUtility(play1, s2);
                    }
                    
                    if (winner == 2)
                    {
                        s2.incrementUtility(play2, s1);
                    }
                    
                }
            }
            
            Collections.sort(Population);
            
            writePopulationData(g, Population, fileOut);
            
            newGeneration = new ArrayList<>();
            
            for (int i = populationSize-1; i >= (3*populationSize)/4; i--)
            {
                Strategy newStrategy1 = new Strategy(Population.get(i));
                Strategy newStrategy2 = new Strategy(Population.get(i));
                
                newGeneration.add(newStrategy1);
                newGeneration.add(newStrategy2);
            }
            
            for (int i = (3*populationSize)/4 - 1; i >= populationSize/4; i--)
            {
                Strategy newStrategy = new Strategy(Population.get(i));
                
                newGeneration.add(newStrategy);
            }
            
            Population = newGeneration;
            
        }
        
        fileOut.close();
        
    }
    
    
    
    
    
    //Returns 1 if player 1 wins, 2 if player 2 wins, or 0 if they tie.
    public static int getWinner(String s1, String s2)
    {
        if (s1.equals(s2))
        {
            return 0;
        }
        else if (s1.equals("scissors") && s2.equals("paper") ||
                (s1.equals("paper") && s2.equals("rock")) ||
                (s1.equals("rock") && s2.equals("lizard")) ||
                (s1.equals("lizard") && s2.equals("spock")) ||
                (s1.equals("spock") && s2.equals("scissors")) ||
                (s1.equals("scissors") && s2.equals("lizard")) ||
                (s1.equals("paper") && s2.equals("spock")) ||
                (s1.equals("rock") && s2.equals("scissors")) ||
                (s1.equals("lizard") && s2.equals("paper")) ||
                (s1.equals("spock") && s2.equals("rock")))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }
    
    
    public static void writePopulationData (int generation, ArrayList<Strategy> Population, PrintWriter fileOut) throws IOException
    {
        
        try
        {
            
            double R_total = 0, P_total = 0, S_total = 0, L_total = 0, K_total = 0;
            int R_spec = 0, P_spec = 0, S_spec = 0, L_spec = 0, K_spec = 0;
            
            for (int i = 0; i < Population.size(); i++)
            {
                Strategy s = Population.get(i);
                
                R_total += s.utilityFromRock;
                P_total += s.utilityFromPaper;
                S_total += s.utilityFromScissors;
                L_total += s.utilityFromLizard;
                K_total += s.utilityFromSpock;
                
                if (s.utilityFromRock > s.utilityFromPaper &&
                        s.utilityFromRock > s.utilityFromScissors &&
                        s.utilityFromRock > s.utilityFromLizard &&
                        s.utilityFromRock > s.utilityFromSpock)
                {
                    R_spec++;
                }
                else if (s.utilityFromPaper > s.utilityFromScissors &&
                        s.utilityFromPaper > s.utilityFromLizard &&
                        s.utilityFromPaper > s.utilityFromSpock)
                {
                    P_spec++;
                }
                else if (s.utilityFromScissors > s.utilityFromLizard &&
                        s.utilityFromScissors > s.utilityFromSpock)
                {
                    S_spec++;
                }
                else if (s.utilityFromLizard > s.utilityFromSpock)
                {
                    L_spec++;
                }
                else
                {
                    K_spec++;
                }
            }
            
            double totalUtility = (R_total + P_total + S_total + L_total + K_total)/Population.size();
            
            R_total /= Population.size();
            P_total /= Population.size();
            S_total /= Population.size();
            L_total /= Population.size();
            K_total /= Population.size();
            
            fileOut.println(generation + "," + R_total + "," + P_total + "," + S_total + "," + L_total + "," + K_total + "," + totalUtility + "," + R_spec + "," + P_spec + "," + S_spec + "," + L_spec + "," + K_spec);
            
            System.out.println("\nGeneration " + generation + ": \n");
            
            System.out.println("U_R: " + R_total);
            System.out.println("U_P: " + P_total);
            System.out.println("U_S: " + S_total);
            System.out.println("U_L: " + L_total);
            System.out.println("U_K: " + K_total);
            
            System.out.println("\nTotal utility: " + totalUtility);
            
            System.out.println("\nS_R: " + R_spec);
            System.out.println("S_P: " + P_spec);
            System.out.println("S_S: " + S_spec);
            System.out.println("S_L: " + L_spec);
            System.out.println("S_K: " + K_spec + "\n");
            
        }
        catch (Exception e)
        {
            System.out.println("ERROR");
        }
        
    }
    

}
