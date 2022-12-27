package g54915.samegame.command;

public class GiveUpCommand extends Command {

    @Override
    public void execute() {
        super.execute();
        getFacade().abandon();
    }

    @Override
    public void undo() {
        super.undo();
    }

}
