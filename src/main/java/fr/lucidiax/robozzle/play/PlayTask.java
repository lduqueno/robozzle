package fr.lucidiax.robozzle.play;

import fr.lucidiax.robozzle.play.action.EmptyAction;
import fr.lucidiax.robozzle.play.action.FunctionAction;
import fr.lucidiax.robozzle.play.stuff.Case;
import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.play.stuff.Function;
import fr.lucidiax.robozzle.play.stuff.Puzzle;
import fr.lucidiax.robozzle.ui.PuzzleRenderer;

import java.util.Timer;
import java.util.TimerTask;

public class PlayTask {

    private PuzzleRenderer puzzle;
    private float animationSpeed = 1f;
    private Timer timer;
    private boolean playing = false;
    private Function currentFunction;
    private int currentStep, currentStars, totalStepsCount, followingFunctions;

    public PlayTask(PuzzleRenderer puzzle) {
        this.puzzle = puzzle;
    }

    public void start(){
        if(playing)
            stop("Fermeture de la partie actuelle..");
        puzzle.lockFunctions(true);
        playing = true;
        launchTimer();
    }

    public void stop(String reason){
        puzzle.setErrorMessage(reason + " (" + totalStepsCount + " étapes)");
        if(playing)
            timer.cancel();
        puzzle.lockFunctions(false);
        followingFunctions = 0;
        playing = false;
        currentFunction = null;
        currentStep = 0;
        currentStars = 0;
        totalStepsCount = 0;
        for(int x = 0; x < puzzle.getPuzzle().getWidth(); x++)
            for(int y = 0; y < puzzle.getPuzzle().getHeight(); y++) {
                Case oldCase = puzzle.getPuzzle().getCases()[x][y];
                oldCase.setHasStar(oldCase.isDefaultHasStar());
            }
        puzzle.getPuzzle().setCursor(puzzle.getPuzzle().getDefaultCursor());
        puzzle.getPuzzle().setCursorDirection(puzzle.getPuzzle().getDefaultDirection());
        puzzle.updatePuzzle(this);
    }

    private void launchTimer(){
        long speed = (int) (400 / animationSpeed);
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Case cursor = puzzle.getPuzzle().getCursor();
                if (currentFunction == null)
                    callFunction(puzzle.getPuzzle().getFunctions().get(0));
                for (int i = currentStep; i < currentFunction.getMaxActions(); i++) {
                    ActionStep step = currentFunction.getSteps().get(i);
                    if (step.getColor() == Color.NONE || step.getColor() == cursor.getColor()) {
                        currentStep = i + 1;
                        ++totalStepsCount;
                        step.call(PlayTask.this);
                        boolean isAction = step instanceof FunctionAction;
                        if (isAction || step instanceof EmptyAction) {
                            if(isAction)
                                ++followingFunctions;
                            if(followingFunctions > puzzle.getPuzzle().getFunctions().size()){
                                stop("Partie terminée, vous avez fait une boucle infinie !");
                                return;
                            }
                            run();
                            return;
                        }
                        followingFunctions = 0;
                        puzzle.updatePuzzle(PlayTask.this);
                        return;
                    }
                }
                stop("Partie terminée, aucune action à effectuer !");
            }
        }, speed, speed);
    }

    public void setAnimationSpeed(float newSpeed){
        animationSpeed = newSpeed;
        if(playing) {
            timer.cancel();
            launchTimer();
        }
    }

    public void callFunction(Function function){
        currentFunction = function;
        currentStep = 0;
    }

    public void incrementStarsCount() {
        if(++currentStars >= puzzle.getPuzzle().getStarsCount())
            stop("Partie terminée, felicitations !");
    }

    public Puzzle getPuzzle() {
        return puzzle.getPuzzle();
    }

    public float getAnimationSpeed() {
        return animationSpeed;
    }

}