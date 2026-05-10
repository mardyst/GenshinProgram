public class Enemy {
    int hp;
    String auraElement;
    double auraGU;

    public Enemy(){
        this.hp = 15000;
        this.auraElement = "";
        this.auraGU = 0.0;
    }

    public int getHP() { return hp; }

    public String getAura() { return auraElement; }

    public double getAuraGU() { return auraGU; }

    public void takeDMG(int dmg) { hp -= dmg; }

    public void clearAura(){
        auraElement = "";
        auraGU = 0.0;
    }

    public void printStats(){
        System.out.println("Ruin Guard: " + hp + "/15000 HP" + " | Aura Element: " + auraElement + " | Aura GU: " + auraGU);
    }

}