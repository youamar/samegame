package g54915.samegame.view;

import g54915.samegame.command.*;
import g54915.samegame.controller.Controller;
import g54915.samegame.model.Ball;
import g54915.samegame.model.Position;
import g54915.samegame.model.State;

import java.util.Scanner;

public class View implements AbstractView {

    private Controller controller;
    private final char[] shapes = {'R', 'G', 'B', 'Y', 'P', 'D'};
    private final String[] color = {"\u001B[31m", "\u001B[32m", "\u001B[34m", "\u001B[33m", "\u001B[35m", "\033[0m"};

    @Override
    public void updateLeftBall(int newValue) {
        System.out.println("\nRemaining Balls : " + newValue);
    }

    @Override
    public void showEndGameMessage(State state) {
        if (state == State.WIN) {
            System.out.println("\n" + changeColorText(0) + "B" + changeColorText(1) + "R" + changeColorText(2)
                    + "A" + changeColorText(3) + "V" + changeColorText(4) + "O" + changeColorText(5) + "!");
        }
        if (state == State.LOST) {
            System.out.println("\n" + changeColorText(0) + "Game ended - No more moves !" + changeColorText(5));
        }
    }

    @Override
    public void impossibleMove() {
        System.out.println("Impossible move.");

    }

    @Override
    public void updateClickScore(int newValue) {
        System.out.println("This Click Score : " + newValue);
    }

    @Override
    public void updateScore(int newValue) {
        System.out.println("Total Score : " + newValue);

    }

    public String changeColorText(int i) {
        return color[i];
    }

    @Override
    public void updateBoard(Ball[][] newValue) {
        int spc = 0;
        System.out.println();
        System.out.print(space(3));
        for (int a = 0; a < newValue[0].length; a++) {
            spc = String.valueOf(a).length();
            System.out.print(a + space(spc));
        }
        System.out.println();
        int z = 0;
        for (int i = 0; i < newValue.length; i++) {
            for (Ball item : newValue[i]) {
                if (z == i) {
                    System.out.print(changeColorText(5));
                    spc = String.valueOf(z).length();
                    System.out.print(z + space(spc));
                    z++;
                }
                if (item == null) {
                    System.out.print("    "); // deleted ball in the console
                } else {
                    System.out.print(changeColorText(item.getColor()) + shapes[item.getColor()] + "   ");
                }
            }
            System.out.println();
        }
        System.out.print(changeColorText(5)); // sets back the console color to its default color
    }

    @Override
    public Position getPosition() {
        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter line number : ");
        int x = kbd.nextInt();
        System.out.println("Enter column number : ");
        int y = kbd.nextInt();
        return new Position(x, y);
    }

    public String space(int i) {
        String spc[] = {"", "   ", "  ", "    "};
        return spc[i];
    }

    @Override
    public Command getCommand() {
        boolean loop = true;
        Command command = null;
        while (loop) {
            loop = false;
            System.out.println("\nEnter the number of the command you want :  ");
            System.out.println("1 - Play a shot");
            System.out.println("2 - Configure the game");
            System.out.println("3 - Start new game");
            System.out.println("4 - Undo move");
            System.out.println("5 - Redo move");
            System.out.println("6 - Leave");
            Scanner kbd = new Scanner(System.in);
            int y = kbd.nextInt();

            switch (y) {
                case 1:
                    if (controller.getState() == State.PLAYING) {
                        Position pos = getPosition();
                        command = new PlayCommand(pos);
                    } else {
                        loop = true;
                        System.out.println("The game hasn't started yet.");
                    }
                    break;
                case 2:
                    System.out.print("Enter board height: ");
                    int height = kbd.nextInt();
                    System.out.print("Enter board width: ");
                    int width = kbd.nextInt();
                    System.out.print("Enter level: ");
                    int nbColors = kbd.nextInt();
                    command = new ConfigurationCommand(height, width, nbColors);

                    break;
                case 3:
                    command = new GiveUpCommand();
                    break;
                case 4:
                    command = new UndoCommand();
                    break;
                case 5:
                    command = new RedoCommand();
                    break;
                case 6:
                    command = null;
                    break;
                default:
                    System.out.println("Wrong input.");
                    loop = true;
                    break;
            }

        }

        return command;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

}
