package g54915.samegame.command;

import g54915.samegame.model.Facade;

public class UndoCommand extends Command {

    @Override
    public void execute() {
        Facade facade = getFacade();
        Command command = facade.popLastCommand();
        if (command != null) {
            command.undo();
        }
    }

    @Override
    public void undo() {

    }
}
