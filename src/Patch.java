/**
 * This class contains grain’s information and behaviors on one patch. The two attributes
 * represent the current amount of grain on one patch and the maximum amount of grain one patch can
 * hold.
 */
public class Patch {
    private double grainHere;
    private double maxGrainHere;

    public Patch() {
        grainHere = 0;
        maxGrainHere = 0;
    }

    //get current amount of grain
    public double getGrainHere() {
        return grainHere;
    }

    //set current amount of grain
    public void setGrainHere(double grainHere) {
        this.grainHere = grainHere;
    }

    //patch procedure
    public void growGrain() {

        // if a patch does not have it's maximum amount of grain, add
        //  num-grain-grown to its grain amount
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
