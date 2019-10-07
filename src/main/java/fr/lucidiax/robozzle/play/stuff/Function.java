package fr.lucidiax.robozzle.play.stuff;

import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.action.EmptyAction;
import fr.lucidiax.robozzle.ui.Color;

import java.util.ArrayList;
import java.util.List;

public class Function {

    private int id;
    private int maxActions;
    private List<ActionStep> steps;

    public Function(int id, int maxActions) {
        this.id = id;
        this.maxActions = maxActions;
        this.steps = new ArrayList<>(maxActions);
        for(int i = 0; i < maxActions; i++)
            steps.add(new EmptyAction(Color.NONE));
    }

    public int getId() {
        return id;
    }

    public int getMaxActions() {
        return maxActions;
    }

    public List<ActionStep> getSteps() {
        return steps;
    }
}
