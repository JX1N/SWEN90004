import java.util.Random;

public class Patch {
    private double grainHere;
    private double maxGrainHere;

    public Patch() {
        grainHere = 0;
        maxGrainHere = 0;
    }

    public double getGrainHere() {
        return grainHere;
    }

    public void setGrainHere(double grainHere) {
        this.grainHere = grainHere;
    }

    public void growGrain() {
        if (grainHere < maxGrainHere) {
            grainHere = grainHere + Parameter.NUM_GRAIN_GROWN;
        }
        if (grainHere >= maxGrainHere) {
            grainHere = maxGrainHere;
        }
    }

    public double getMaxGrainHere() {
        return maxGrainHere;
    }

    public void setMaxGrainHere(double maxGrainHere) {
        this.maxGrainHere = maxGrainHere;
    }

}
