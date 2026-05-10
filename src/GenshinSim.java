import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class GenshinSim {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Character[] characters = new Character[4];
        characters[0] = new Character("Amber", "Pyro", 60);
        characters[1] = new Character("Kaeya", "Cryo", 60);
        characters[2] = new Character("Lisa", "Electro", 60);
        characters[3] = new Character("Xingqiu", "Hydro", 80);

        Enemy ruinGuard = new Enemy();

        Menu reactionMenu = new Menu(characters, input);

        if(reactionMenu.mainMenu()){
            Character[] chosen = reactionMenu.chooseReaction();
            Character c1 = chosen[0];
            Character c2 = chosen[1];

            Battle battle = new Battle(c1, c2, ruinGuard);

            battle.start(input);
            battle.printBattleLog();

        } else {
            System.out.println("\nThanks for playing!");
            System.exit(0);

        }
    }
}