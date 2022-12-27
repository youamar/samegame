package g54915.samegame.command;

import g54915.samegame.model.Facade;

public class StartNewGameCommand extends Command {

    int width, height, nbColors;

    public StartNewGameCommand(int width, int height, int nbColors) {
        this.width = width;
        this.height = height;
        this.nbColors = nbColors;
    }

    public StartNewGameCommand(int color) {
        this.width = -1;
        this.height = -1;
        this.nbColors = color;
    }

    @Override
    public void execute() {
        super.execute();
        Facade facade = getFacade();
        facade.setNewGame(width, height, nbColors);
    }

    @Override
    public void undo() {
        super.undo();

    }

}
