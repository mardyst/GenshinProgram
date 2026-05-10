import java.util.Scanner;

public class Menu {
    Character[] characters;
    Scanner input;

    public Menu(Character[] characters, Scanner input){
        this.characters = characters;
        this.input = input;
    }

    public boolean mainMenu(){
        System.out.println("""
                
                Melanny's Genshin Simulator Program
                Welcome to the Genshin Reaction Simulation Program!
                
                [1] Start
                [2] Quit
                
                Enter your option:\s""");

        int mmInput = input.nextInt();

        if(mmInput == 1){
            return true;
        } else if(mmInput == 2){
            return false;
        } else {
            System.out.println("Error: Must select 1 or 2.");
            return mainMenu();
        }

    }

    public Character[] chooseReaction(){
        System.out.println("""
                
                Here's your list of reactions to choose from:
                
                [1] Vaporize | Amber (Pyro) & Xinqiu (Hydro)
                [2] Melt | Amber (Pyro) & Kaeya (Cryo)
                [3] Overloaded | Amber (Pyro) & Lisa (Electro)
                [4] Superconduct | Kaeya (Cryo) & Lisa (Electro)
                [5] Frozen | Kaeya (Cryo) & Xingqiu (Hydro)
                [6] Electro-Charged | Lisa (Electro) & Xingqiu (Hydro)
                
                Enter your option:\s""");

        int reactionInput = input.nextInt();

        if (reactionInput == 1){
            System.out.println("\nYou picked Vaporize:\n");
            return new Character[]{characters[0], characters[3]};

        } else if (reactionInput == 2){
            System.out.println("\nYou picked Melt:\n");
            return new Character[]{characters[0], characters[1]};

        } else if (reactionInput == 3){
            System.out.println("\nYou picked Overloaded:\n");
            return new Character[]{characters[0], characters[2]};

        } else if (reactionInput == 4){
            System.out.println("\nYou picked Superconduct:\n");
            return new Character[]{characters[1], characters[2]};

        } else if (reactionInput == 5){
            System.out.println("\nYou picked Frozen:\n");
            return new Character[]{characters[1], characters[3]};

        } else if (reactionInput == 6){
            System.out.println("\nYou picked Electro-Charged:\n");
            return new Character[]{characters[2], characters[3]};

        } else {
            System.out.println("\nError: Must select 1-6.\n");
            return chooseReaction();

        }
    }
}
