import java.util.Random;

/**
 * This class is used to describe one person’s (turtle in NetLogo model) behaviors and information.
 * It contains attributes like age, wealth, vision, location and so on. It has three methods to
 * describe person’s behaviors in each time tick:
 * 1) Determine the direction to go in next time tick.
 * 2) Harvest in this time tick.
 * 2) Move, consume grain, grow age and judge whether this person will die or not in this time tick
 * All entities will be given random initial values when it is created. When one person dies, the
 * entity will not be destroyed. It will be given new random values to its attributes except
 * the location.
 */
public class Person {
    private int age;
    private double wealth;
    private int lifeExpectancy;
    private int metabolism;
    private int vision;
    private int locX;
    private int locY;
    private World world;
    //face has 4 directions, 0 => south, 1 => east, 2 => north, 3 => west
    private int faceDirection;

    //set up the initial values for the turtle variables
    public Person() {
        Random ran = new Random();
        locX = ran.nextInt(Parameter.MAX_WORLD_X);
        locY = ran.nextInt(Parameter.MAX_WORLD_Y);
        initPerson(0);
        age = ran.nextInt(lifeExpectancy);
    }


    //set up the initial values for the turtle variables
    private void initPerson(double inheritedWealth) {
        Random ran = new Random();
        age = 0;
        faceDirection = ran.nextInt(4);
        lifeExpectancy = Parameter.LIFE_EXPECTANCY_MIN
                + ran.nextInt(Parameter.LIFE_EXPECTANCY_MAX
                - Parameter.LIFE_EXPECTANCY_MIN + 1);
        metabolism = 1 + ran.nextInt(Parameter.METABOLISM_MAX);
        wealth = inheritedWealth + metabolism + ran.nextInt(50);
        vision = 1 + ran.nextInt(Parameter.MAX_VISION);
    }

    //choose direction holding most grain within the turtle's vision
    public void turnTowardsGrain() {
        double bestTotal = 0;
        int bestDirection = 0;
        double total;

        //calculate face 0 north
        total = 0;
        for (int i = 0; i <= vision; i++) {
            if (locY + i < Parameter.MAX_WORLD_Y) {
                total = total + world.getGrain(locX, locY + i);
            } else {
                total = total + world.getGrain(locX, locY + i - Parameter.MAX_WORLD_Y);
            }
        }
        if (total > bestTotal) {
            bestDirection = 0;
            bestTotal = total;
        }

        //calculate face 1 east
        total = 0;
        for (int i = 0; i <= vision; i++) {
            if (locX + i < Parameter.MAX_WORLD_X) {
                total = total + world.getGrain(locX + i, locY);
            } else {
                total = total + world.getGrain(locX + i - Parameter.MAX_WORLD_X, locY);
            }
        }
        if (total > bestTotal) {
            bestDirection = 1;
            bestTotal = total;
        }

        //calculate face 2 south
        total = 0;
        for (int i = 0; i <= vision; i++) {
            if (locY - i >= 0) {
                total = total + world.getGrain(locX, locY - i);
            } else {
                total = total + world.getGrain(locX, locY - i + Parameter.MAX_WORLD_Y);
            }
        }
        if (total > bestTotal) {
            bestDirection = 2;
            bestTotal = total;
        }

        //calculate face 3 west
        total = 0;
        for (int i = 0; i <= vision; i++) {
            if (locX - i >= 0) {
                total = total + world.getGrain(locX - i, locY);
            } else {
                total = total + world.getGrain(locX - i + Parameter.MAX_WORLD_X, locY);
            }
        }
        if (total > bestTotal) {
            bestDirection = 3;
            bestTotal = total;
        }
        faceDirection = bestDirection;
    }

    // each turtle harvests the grain on its patch.  if there are multiple
    // turtles on a patch, divide the grain evenly among the turtles
    public void harvest() {
        wealth = wealth + (world.getGrain(locX, locY) / world.getNumPeople(locX, locY));
    }

    // turtle procedure
    public void moveEatAgeDie() {
        switch (faceDirection) {
            case 0:
                locY++;
                if (locY >= Parameter.MAX_WORLD_Y) {
                    locY = locY - Parameter.MAX_WORLD_Y;
                }
                break;
            case 1:
                locX++;
                if (locX >= Parameter.MAX_WORLD_X) {
                    locX = locX - Parameter.MAX_WORLD_X;
                }
                break;
            case 2:
                locY--;
                if (locY < 0) {
                    locY = locY + Parameter.MAX_WORLD_Y;
                }
                break;
            case 3:
                locX--;
                if (locX < 0) {
                    locX = locX + Parameter.MAX_WORLD_X;
                }
                break;
        }

        //consume some grain according to metabolism
        wealth = wealth - metabolism;

        //grow older
        age++;

        // check for death conditions: if you have no grain or
        //  you're older than the life expectancy or if some random factor
        //  holds, then you "die" and are "reborn" (in fact, your variables
        //  are just reset to new random values)
        if (wealth < 0 || age >= lifeExpectancy) {
            initPerson(wealth * Parameter.INHERITED_PERCENTAGE);
        }
    }

    public double getWealth() {
        return wealth;
    }

    public int getLocX() {
        return locX;
    }


    public int getLocY() {
        return locY;
    }


    public void setWorld(World world) {
        this.world = world;
    }

}
