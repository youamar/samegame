package g54915.samegame.controller;

import g54915.samegame.command.Command;
import g54915.samegame.command.GiveUpCommand;
import g54915.samegame.model.Facade;
import g54915.samegame.model.State;
import g54915.samegame.view.AbstractView;

public class Controller {

    private final Facade facade;
    private final AbstractView view;

    public Controller(Facade facade, AbstractView view) {
        this.facade = facade;
        this.view = view;
        view.setController(this);
        facade.subscribe(view);

    }

    public void run() {
        Command com = new GiveUpCommand();
        while (com != null) {
            com = view.getCommand();
            facade.execute(com);
        }
    }

    public void executeCommand(Command com) {
        facade.execute(com);
    }

    public Facade getFacade() {
        return facade;
    }

    public AbstractView getView() {
        return view;
    }

    public State getState() {
        return getFacade().getState();
    }

}
