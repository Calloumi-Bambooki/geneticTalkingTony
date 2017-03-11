import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by bramreth on 3/10/17.
 */
public class TalkingTony {
    private String target;
    private int fitness, topFitness, gen = 0;
    private String[] oldFamily = new String[5];
    private String[] newFamily = new String[5];
    
    public TalkingTony(String target){
        this.target = target;
        Arrays.fill(oldFamily, "");
        for(int i = 0; i < oldFamily.length; i++) {
            for (int x = 0; x < target.length(); x++) {
                oldFamily[i] += ((char) (32 + (new Random()).nextInt(95)));
            }
        }
        while(fitness < target.length()*3){
            for (int i=0; i<newFamily.length; i++)
                newFamily[i] = mutate(oldFamily, fitness);
            newFamily = sort(newFamily);
            fitness = calcFitness(newFamily[0]);
            if(fitness > topFitness) {
                topFitness = fitness;
                gen++;
                System.out.println("Gen: " + (gen + 1) + " | Fitness: " + fitness + " | " + newFamily[0]);
                oldFamily = newFamily;
            }
        }
    }
    private int calcFitness(String tempGeneration){
        int fitnessResult = 0;
        for(int x = 0; x < tempGeneration.length(); x++) {
            if((int)tempGeneration.charAt(x) == (int) target.charAt(x)){
                fitnessResult += 3;
            }else if((int)tempGeneration.charAt(x) < ((int) target.charAt(x) + 5) &&
                    (int)tempGeneration.charAt(x) > ((int) target.charAt(x) - 5)){
                fitnessResult += 2;
            }else if((int)tempGeneration.charAt(x) < ((int) target.charAt(x) + 10) &&
                    (int)tempGeneration.charAt(x) > ((int) target.charAt(x) - 10)) {
                fitnessResult += 1;
            }
        }
        return fitnessResult;
    }

    private String mutate(String[] oldFamily, int rate){
        Random r = new Random();
        String mother = oldFamily[r.nextInt(3)];
        String father = oldFamily[r.nextInt(3)];
        String child = "";
        for(int x = 0; x < target.length(); x++) {
            if(r.nextInt(target.length()*3)<rate) {
                if (r.nextInt(1)==1)
                    child += mother.charAt(x);
                else
                    child += father.charAt(x);
                    
            }
            else
                child += (char) (new Random().nextInt(126) + 1);
        }
        return child;
    }
    
    private String[] sort(String[] in) {
        String[] result = new String[in.length];
        Boolean[] done = new Boolean[in.length];
        Arrays.fill(done, false);
        int best;
        for (int i=0; i<in.length; i++) {
            best=-1;
            for (int j=0; j<in.length; j++) {
                if (best==-1) {
                    if (done[j])
                        continue;
                    else {
                        best=j;
                        done[j]=true;
                    }
                }
                else if (calcFitness(in[best]) < calcFitness(in[j])) {
                    done[best] = false;
                    done[j] = true;
                    best = j;
                }
            }
            result[i]=in[best];
        }
        return result;
    }
}