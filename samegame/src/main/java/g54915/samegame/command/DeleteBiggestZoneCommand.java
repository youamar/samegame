package g54915.samegame.command;

public class DeleteBiggestZoneCommand extends Command {

    @Override
    public void execute() {
        super.execute();
        getFacade().deleteBiggestZone();
    }

    @Override
    public void undo() {
        super.undo();
    }

}
