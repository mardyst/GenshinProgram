public class Character {
    String name;
    String element;
    int energy;
    int maxEnergy;
    double skillGU;
    int skillCD;

    public Character(String name, String element, int maxEnergy) {
        this.name = name;
        this.element = element;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy/2;
        this.skillGU = 2.0;
        this.skillCD = 0;
    }

    public String getName() { return name; }

    public String getElement() { return element; }

    public int getEnergy() { return energy; }

    public double getSkillGU() { return skillGU; }

    public int getSkillCD() { return skillCD; }

    public void printStats(){
        System.out.println(name + " (" + element + ") - Energy: " + energy + "/" + maxEnergy);
    }
}