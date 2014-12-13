package rpslkevolution;

public class Strategy implements Comparable<Strategy>
{
    int numGenes = 31;
    
    int genes[] = new int [numGenes];
    double geneAverage;
    
    double utility;
    
    double utilityFromRock;
    double utilityFromPaper;
    double utilityFromScissors;
    double utilityFromLizard;
    double utilityFromSpock;
    
    //Constructor for creating a "child" Strategy given a "parent" Strategy
    public Strategy (Strategy parent)
    {
        geneAverage = 0;
        
        for (int i = 0; i < numGenes; i++)
        {
            double seed = 100*Math.random();
            
            if (seed <= 4)
            {
                genes[i] = parent.genes[i] - 2;
            }
            else if (seed <= 7)
            {
                genes[i] = parent.genes[i] - 1;
            }
            else if (seed >= 92)
            {
                genes[i] = parent.genes[i] + 1;
            }
            else if (seed >= 95)
            {
                genes[i] = parent.genes[i] + 2;
            }
            else
            {
                genes[i] = parent.genes[i];
            }
            
            if (genes[i] > 10)
            {
                genes[i] = 10;
            }
            
            if (genes[i] < 0)
            {
                genes[i] = 0;
            }
            
            geneAverage += genes[i];
        }
        
        utility = 0;
        
        utilityFromRock = 0;
        utilityFromPaper = 0;
        utilityFromScissors = 0;
        utilityFromLizard = 0;
        utilityFromSpock = 0;
        
        
        geneAverage = geneAverage / numGenes;
    }
    
    //Constructor for creating a completely random Strategy
    public Strategy ()
    {
        geneAverage = 0;
        
        for (int i = 0; i < numGenes; i++)
        {
            genes[i] = (int)(11*Math.random());
            
            if (genes[i] > 10)
            {
                genes[i] = 10;
            }
            
            if (genes[i] < 0)
            {
                genes[i] = 0;
            }
            
            geneAverage += genes[i];
            
        }
        
        utility = 0;
        
        geneAverage = geneAverage / numGenes;
    }
    
    public double R_chance(Strategy opponent)
    {
        return geneAverage / 40;
    }
    
    public double R_utility (Strategy opponent)
    {
        return (geneAverage + genes[28]) * genes[15];
    }
    
    public double P_chance (Strategy opponent)
    {
        return opponent.geneAverage / 40;
    }
    
    public double P_utility(Strategy opponent)
    {
        return Math.pow(2.01 + (10-opponent.geneAverage)/1000, geneAverage);
    }
    
    public double S_chance(Strategy opponent)
    {
        return opponent.L_chance(this);
    }
    
    public double S_utility (Strategy opponent)
    {
        return genes[3] + genes[29] + geneAverage * (10+genes[30]);
    }
    
    public double L_chance (Strategy opponent)
    {
        int countEven = 0;
        int countOdd = 0;
        int max = 0;
        
        for (int i = 0; i < numGenes; i++)
        {
            if (genes[i] % 2 == 0)
            {
                countEven++;
                countOdd = 0;
            }
            else if (genes[i] % 2 == 1)
            {
                countOdd++;
                countEven = 0;
            }
            
            if (Math.max(countEven, countOdd) > max)
            {
                max = Math.max(countEven, countOdd);
            }
        }
        
        return Math.min(0.25, Math.pow (1.75 + (geneAverage/20), max + 20 + genes[9] - opponent.geneAverage) / 10);
    }
    
    public double L_utility (Strategy opponent)
    {
        return 10 + genes[1] + genes[3] + genes[13] + (10-opponent.geneAverage) * geneAverage * genes[16] + genes[10] * genes[7] + genes[4] + (genes[14]*geneAverage);
    }
    
    public double K_chance (Strategy opponent)
    {
        return 1 - R_chance(opponent) - P_chance(opponent) - S_chance(opponent) - L_chance(opponent);
    }
    
    public double K_utility (Strategy opponent)
    {
        return (geneAverage * genes[0]) / ((K_chance(opponent) + opponent.geneAverage/90));
    }
    
    public String getPlay(Strategy opponent)
    {
        double seed = Math.random();
        
        if (seed < R_chance(opponent))
        {
            return "rock";
        }
        else if (seed < R_chance(opponent) + P_chance(opponent))
        {
            return "paper";
        }
        else if (seed < R_chance(opponent) + P_chance(opponent) + S_chance(opponent))
        {
            return "scissors";
        }
        else if (seed < R_chance(opponent) + P_chance(opponent) + S_chance(opponent) + L_chance(opponent))
        {
            return "lizard";
        }
        else
        {
            return "spock";
        }
    }
    
    public void incrementUtility(String play, Strategy opponent)
    {
        double incrementValue = 0;
        
        if (play.equals("rock"))
        {
            incrementValue = R_utility(opponent);
            utility += incrementValue;
            utilityFromRock += incrementValue;
        }
        
        if (play.equals("paper"))
        {
            incrementValue = P_utility(opponent);
            utility += incrementValue;
            utilityFromPaper += incrementValue;
        }
        
        if (play.equals("scissors"))
        {
            incrementValue = S_utility(opponent);
            utility += incrementValue;
            utilityFromScissors += incrementValue;
        }
        
        if (play.equals("lizard"))
        {
            incrementValue = L_utility(opponent);
            utility += incrementValue;
            utilityFromLizard += incrementValue;
        }
        
        if (play.equals("spock"))
        {
            incrementValue = K_utility(opponent);
            utility += incrementValue;
            utilityFromSpock += incrementValue;
        }
        
        
    }
    
    @Override
    public int compareTo(Strategy s) {
        return this.utility > s.utility ? 1 : (this.utility < s.utility ? -1 : 0);
    }
    
    public void displayGeneData()
    {
        for (int i = 0; i < 31; i++)
        {
            System.out.println("Gene " + i + ": " + genes[i]);
        }
    }

}

