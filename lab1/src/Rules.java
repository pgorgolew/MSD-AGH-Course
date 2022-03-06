import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Rules {
    DEFAULT(0),
    CITIES(1),
    CORAL(2);

    final int ruleNumber;

    Rules(int ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    public static Rules getRuleFromIndex(int givenNumber) {
        for(Rules rule: Rules.values()) {
            if (rule.ruleNumber == givenNumber) {
                return rule;
            }
        }

        return null;
    }

    public List<Integer> neighboursForDeadCell(){
        List<Integer> neighbours = new ArrayList<>();

        switch (this){
            case DEFAULT, CORAL ->
                    neighbours.addAll(Arrays.asList(3));
            case CITIES ->
                    neighbours.addAll(Arrays.asList(4,5,6,7,8));
        }

        return neighbours;
    }

    public List<Integer> neighboursForAliveCell(){
        List<Integer> neighbours = new ArrayList<>();

        switch (this){
            case DEFAULT ->
                    neighbours.addAll(Arrays.asList(2,3));
            case CITIES ->
                    neighbours.addAll(Arrays.asList(2,3,4,5));
            case CORAL ->
                    neighbours.addAll(Arrays.asList(4,5,6,7,8));
        }

        return neighbours;
    }
}
