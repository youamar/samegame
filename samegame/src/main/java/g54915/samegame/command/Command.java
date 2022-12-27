package g54915.samegame.command;

import g54915.samegame.model.Facade;
import g54915.samegame.model.Game;

public class Command {

    private Facade facade;
    private Game previousGame;

    public void execute() {
        facade.addCommand(this);
        setPreviousGame(facade.getCopyGame());
    }

    public void undo() {
        facade.setNewGame(previousGame);
        facade.addUndoCommand(this);
    }

    protected Facade getFacade() {
        return facade;
    }

    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    protected void setPreviousGame(Game copyGame) {
        this.previousGame = copyGame;
    }

}
