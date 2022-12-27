package g54915.samegame.command;

import g54915.samegame.model.Facade;
import g54915.samegame.model.Position;

public class PlayCommand extends Command {

    private final Position pos;

    public PlayCommand(Position pos) {
        this.pos = pos;
    }

    @Override
    public void execute() {
        super.execute();
        Facade facade = getFacade();
        int res = facade.playShot(pos);
        if (res == 0) {
            facade.popLastCommand();
        }
    }

    @Override
    public void undo() {
        super.undo();

    }
}
