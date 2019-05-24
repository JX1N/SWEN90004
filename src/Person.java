import java.util.Random;

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
        initPerson();
        age = ran.nextInt(lifeExpectancy);
    }


    //set up the initial values for the turtle variables
    private void initPerson() {
        Random ran = new Random();
        age = 0;
        faceDirection = ran.nextInt(4);
        lifeExpectancy = Parameter.LIFE_EXPECTANCY_MIN
                + ran.nextInt(Parameter.LIFE_EXPECTANCY_MAX - Parameter.LIFE_EXPECTANCY_MIN + 1);
        metabolism = 1 + ran.nextInt(Parameter.METABOLISM_MAX);
        wealth = metabolism + ran.nextInt(50);
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
        wealth = wealth - metabolism;
        age++;
        if (wealth < 0 || age >= lifeExpectancy) {
            initPerson();
        }
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWealth() {
        return wealth;
    }

    public void setWealth(double wealth) {
        this.wealth = wealth;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getMetabolism() {
        return metabolism;
    }

    public void setMetabolism(int metabolism) {
        this.metabolism = metabolism;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(int faceDirection) {
        this.faceDirection = faceDirection;
    }
}
