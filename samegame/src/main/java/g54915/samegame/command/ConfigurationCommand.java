package g54915.samegame.command;

import g54915.samegame.model.Facade;

public class ConfigurationCommand extends Command {

    int width, height, nbColors;

    public ConfigurationCommand(int width, int height, int nbColors) {
        this.width = width;
        this.height = height;
        this.nbColors = nbColors;
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
