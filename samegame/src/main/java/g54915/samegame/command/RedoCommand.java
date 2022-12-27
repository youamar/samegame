package g54915.samegame.command;

import g54915.samegame.model.Facade;

public class RedoCommand extends Command {

    @Override
    public void execute() {
        Facade facade = getFacade();
        Command command = facade.popLastCommandUndo();
        if (command != null) {
            command.execute();
        }
    }

    @Override
    public void undo() {

    }
}
