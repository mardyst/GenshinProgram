import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    int turn = 0;
    boolean salvoPending = false;
    boolean coreExposed = false;
    boolean amberNormAtk = false;
    boolean electroCharged = false;
    boolean doubleDMG = false;
    int totalDMG = 0;

    Random random = new Random();
    ArrayList<String> battleLog = new ArrayList<>();

    Character c1;
    Character c2;
    Character first;
    Character second;
    Enemy ruinGuard;
    int playerHP;

    public Battle(Character c1, Character c2, Enemy ruinGuard){
        this.c1 = c1;
        this.c2 = c2;
        this.ruinGuard = ruinGuard;
        this.playerHP =  100;
    }

    public void hpBar(String name, int currentHP, int maxHP){
        System.out.print(name + " HP: [");

        int blackSquare = currentHP * 10 / maxHP; // this gets how many squares need to be filled
        int whiteSquare = 10 - blackSquare; // this gets how many squares are empty

        for(int i = 0; i < blackSquare; i++){
            System.out.print("■");
        }

        for(int i = 0; i < whiteSquare; i++){
            System.out.print("□");
        }

        System.out.println("] " + currentHP + "/" + maxHP);

    }

    public void showStatus(){

        c1.printStats();
        c2.printStats();
        hpBar("Player", playerHP, 100);
        hpBar("Ruin Guard", ruinGuard.hp, 15000);
        System.out.print("Current Aura: " + ruinGuard.getAura() + " \n");

    }

    public void showActionMenu(Character c){

        if(c.skillCD > 0){ //if they have cooldown

            System.out.println("[1] Normal Attack (physical, 10 dmg, +5 burst)");
            System.out.println("[2] Elemental Skill (" + c.getElement() + ", 2U, 20 dmg, +10 burst) (ON COOLDOWN!)");
            System.out.println("[3] Elemental Burst (" + c.getElement() + ", 2U, 30 dmg, requires " + c.maxEnergy + ")");

        } else {

            System.out.println("[1] Normal Attack (physical, 10 dmg, +5 burst)");
            System.out.println("[2] Elemental Skill (" + c.getElement() + ", 2U, 20 dmg, +10 burst)");
            System.out.println("[3] Elemental Burst (" + c.getElement() + ", 2U, 30 dmg, requires " + c.maxEnergy + ")");

        }
    }

    public int resolveReaction(String atkElement, int baseDMG){
        baseDMG = 1530;

        //vaporize
        if((ruinGuard.auraElement.equals("Pyro") && atkElement.equals("Hydro")) || (ruinGuard.auraElement.equals("Hydro") && atkElement.equals("Pyro"))){

            if((ruinGuard.auraElement.equals("Pyro") && atkElement.equals("Hydro"))){ //if hydro triggers

                ruinGuard.auraGU -= 1.0;

                if(ruinGuard.auraGU <= 0){

                    ruinGuard.clearAura();

                }

                battleLog.add("Turn " + turn + ", VAPORIZE! 1.5x, " + (int)(baseDMG * 1.5) + " DMG");

                return (int)(baseDMG * 1.5);

            } else { //if pyro triggers

                ruinGuard.clearAura();
                battleLog.add("Turn " + turn + ", VAPORIZE! 2.0x, " + (int)(baseDMG * 2.0) + " DMG");
                return (int)(baseDMG * 2.0);

            }


            //melt
        } else if((ruinGuard.auraElement.equals("Pyro") && atkElement.equals("Cryo")) || (ruinGuard.auraElement.equals("Cryo") && atkElement.equals("Pyro"))){

            if((ruinGuard.auraElement.equals("Pyro") && atkElement.equals("Cryo"))){ //if cryo triggers

                ruinGuard.auraGU -= 1.0;

                if(ruinGuard.auraGU <= 0){

                    ruinGuard.clearAura();


                }

                battleLog.add("Turn " + turn + ", MELT! 1.5x, " + (int)(baseDMG * 1.5) + " DMG");

                return (int)(baseDMG * 1.5);

            } else { //if pyro triggers

                ruinGuard.clearAura();
                battleLog.add("Turn " + turn + ", MELT! 2.0x, " + (int)(baseDMG * 2.0) + " DMG");
                return (int)(baseDMG * 2.0);

            }

            //overloaded
        } else if((ruinGuard.auraElement.equals("Pyro") && atkElement.equals("Electro")) || (ruinGuard.auraElement.equals("Electro") && atkElement.equals("Pyro"))){

            battleLog.add("Turn " + turn + ", OVERLOADED! 1.5x, " + (baseDMG + 25) + " DMG");
            ruinGuard.clearAura();
            return baseDMG + 25;

            //superconduct
        } else if((ruinGuard.auraElement.equals("Cryo") && atkElement.equals("Electro")) || (ruinGuard.auraElement.equals("Electro") && atkElement.equals("Cryo"))){

            ruinGuard.clearAura();
            battleLog.add("Turn " + turn + ", SUPERCONDUCT! 1.5x, " + (int)(baseDMG * 0.5)  + " DMG");
            return (int)(baseDMG * 0.5);

            //frozen
        } else if((ruinGuard.auraElement.equals("Cryo") && atkElement.equals("Hydro")) || (ruinGuard.auraElement.equals("Hydro") && atkElement.equals("Cryo"))){

            ruinGuard.clearAura();
            battleLog.add("Turn " + turn + ", FROZEN! " + baseDMG + " DMG");
            return baseDMG;

            //electro-charged
        } else if((ruinGuard.auraElement.equals("Electro") && atkElement.equals("Hydro")) || (ruinGuard.auraElement.equals("Hydro") && atkElement.equals("Electro"))){

            ruinGuard.clearAura();
            electroCharged = true;
            battleLog.add("Turn " + turn + ", ELECTRO-CHARGED! " + baseDMG + " DMG");
            return 0;

        } else{

            battleLog.add("Turn " + turn + ", NO REACTION!" + baseDMG + " DMG");
            return baseDMG;

        }

    }

    public int applyElement(String element, double gu, int baseDMG){

        if(ruinGuard.auraElement.isEmpty()){ //if the ruinguard doesnt have an aura

            ruinGuard.auraElement = element;
            ruinGuard.auraGU = gu;
            return baseDMG;

        } else if (ruinGuard.auraElement.equals(element)){ // if the aura is the same as the triggering element

            ruinGuard.auraGU = Math.min(ruinGuard.auraGU + (gu * 0.8), 4.0);
            return baseDMG;

        } else{ //if the aura and the triggering element r different

            return resolveReaction(element, baseDMG);

        }

    }

    public void doAction(Character c, Scanner input){

        System.out.println("\n--- " + c.getName() + "'s turn ---");
        showActionMenu(c);
        int action = input.nextInt();

        if(action == 1){

            if(c.getName().equals("Amber")){

                amberNormAtk = true; //for salvo turns
            }

            System.out.println("\n" + c.getName() + " is using a normal attack!\n");

            if(doubleDMG){ //for double damage turns where the core is exposed

                ruinGuard.takeDMG(10*2);
                totalDMG += 20;
                battleLog.add("Turn " + turn + ", " + c.getName() + "Normal Attack: physical, 20 DMG (DOUBLE DMG!)");

            } else {

                ruinGuard.takeDMG(10);
                totalDMG += 10;
                battleLog.add("Turn " + turn + ", " + c.getName() + " Normal Attack: physical, 10 DMG");

            }

            c.energy += 5;

        } else if (action == 2){

            if (c.skillCD > 0){

                System.out.println("Skill is on cooldown!");

            } else {

                System.out.println("\n" + c.getName() + " is using Elemental Skill!\n");
                int finalDMG = applyElement(c.getElement(), c.skillGU, 20);

                if(doubleDMG){

                    finalDMG *= 2;

                }

                ruinGuard.takeDMG(finalDMG);
                totalDMG += finalDMG;
                c.energy += 10;
                c.skillCD = 1;
                battleLog.add("Turn " + turn + ", " + c.getName() + " Elemental Skill: " + c.getElement() + ", " + finalDMG +" DMG");

            }
        } else {

            if(c.energy >= c.maxEnergy){

                System.out.println("\n" + c.getName() + " is using Elemental Burst!\n");
                int finalDMG = applyElement(c.getElement(), c.skillGU, 30);

                if(doubleDMG){

                    finalDMG *= 2;

                }

                ruinGuard.takeDMG(finalDMG);
                totalDMG += finalDMG;
                c.energy = 0;
                battleLog.add("Turn " + turn + ", " + c.getName() + " Elemental Burst: " + c.getElement() + ", " + finalDMG +" DMG");

            } else{

                System.out.println("Elemental burst energy is not full!");

            }
        }
    }

    public void start(Scanner input){

        while(playerHP > 0 && ruinGuard.getHP() > 0){

            doubleDMG = false;
            turn++;

            if(coreExposed){

                doubleDMG = true;
                coreExposed = false;
                System.out.println("\nCORE EXPOSED! Double damage this turn!\n");
                battleLog.add("Turn " + turn + ", CORE EXPOSED! DOUBLE DAMAGE TURN.");

            }

            showStatus();
            System.out.println("\nWho acts first?\n[1] " + c1.getName() + "\n[2] " + c2.getName());
            int firstChar = input.nextInt();

            if (firstChar == 1){

                first = c1;
                second = c2;

            } else {

                first = c2;
                second = c1;

            }

            amberNormAtk = false;

            System.out.println("\n" + first.getName() + " goes first! Choose how you will attack.");
            doAction(first, input);
            doAction(second, input);

            if(ruinGuard.hp <= 0){

                System.out.println("RUIN GUARD HAS BEEN DEFEATED!");
                break;

            }

            ruinGuard.auraGU -= 0.5;

            //aura decay
            if(ruinGuard.auraGU <= 0.0){

                ruinGuard.clearAura();
                System.out.println("\nAura has faded.\n");

            } else {

                System.out.println("Aura decays 0.5 GU. " + ruinGuard.auraGU + " GU remaining.");

            }

            if(salvoPending){ //if salvo turn

                System.out.println("Ruin Guard fires Rocket Salvo!");
                battleLog.add("Turn " + turn + ", RUIN GUARD FIRES ROCKET SALVO!!!");


                if(electroCharged){ //electrocharged disrupting salvo

                    System.out.println("Ruin Guard is electro-charged! 0 DMG done.");
                    battleLog.add("Turn " + turn + ", ELECTRO-CHARGED INTERCEPTS ROCKET SALVO! 0 DMG");
                    coreExposed = true;
                    electroCharged = false;

                } else if(amberNormAtk && random.nextDouble() < 0.50){ //amber using normal attack to stop salvo

                    System.out.println("Amber used a normal attack to stop the Rocket Salvo Attack! 0 DMG done.");
                    battleLog.add("Turn " + turn + ", AMBER'S NORMAL ATTACK INTERCEPTS ROCKET SALVO! 0 DMG");
                    coreExposed = true;

                } else{

                    playerHP -= 15;
                    coreExposed = true;
                    battleLog.add("Turn " + turn + ", Ruin Guard: Salvo fired!");

                }

                salvoPending = false;

            } else {

                if(amberNormAtk && random.nextDouble() < 0.33) { //chance for stun

                    System.out.println("Amber hit the Ruin Guard's eye! Attack skipped.");
                    battleLog.add("Turn " + turn + ", Amber stunned Ruin Guard!");

                } else {

                    if (random.nextInt(6) == 5) { //spin attack

                        System.out.println("\nRuin Guard does a spin attack and deals 20 DMG to the player.\n");
                        playerHP -= 20;
                        battleLog.add("Turn " + turn + ", Ruin Guard: Spin, 20 DMG");


                    } else { //stomp attack

                        System.out.println("\nRuin Guard does a stomp attack and deals 10 DMG to the player.\n");
                        playerHP -= 10;
                        battleLog.add("Turn " + turn + ", Ruin Guard: Stomp, 10 DMG");

                    }
                }

                //salvo roll (only happens every non-salvo turn)
                if(random.nextDouble() < 0.33){

                    salvoPending = true;
                    System.out.println("\nRuin Guard anchors to the ground. Rocket Salvo incoming!\n");

                }
            }

            //defeat check
            if(playerHP <= 0){

                System.out.println("PLAYER HAS BEEN DEFEATED!");
                break;

            }

            //skill cd reduction
            if(c1.skillCD > 0){

                c1.skillCD -= 1;

            }

            if(c2.skillCD > 0){

                c2.skillCD -= 1;

            }
        }

        System.out.println("\n====== GAME OVER ======");
        System.out.println("Turns taken: " + turn);
        System.out.println("Total damage dealt: " + totalDMG);

    }

    // prints what happened each turn
    public void printBattleLog(){

        System.out.println("\n BATTLE LOG");

        for(int i = 0; i < battleLog.size(); i++){

            System.out.println((i+1) + ". " + battleLog.get(i));

        }
    }
}