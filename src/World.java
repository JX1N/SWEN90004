import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class World {
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<int[]> peopleLoc = new ArrayList<>();
    private Patch[][] patches;
    private ArrayList<Data> data = new ArrayList<>();

    public World() {
        initPeople();
        initPatches();
    }

    private void initPeople() {
        System.out.println("Start Init People");
        for (int i = 0; i < Parameter.NUM_PEOPLE; i++) {
            Person person = new Person();
            person.setWorld(this);
            people.add(person);
        }
        System.out.println("End Init People");
    }

    // set up the initial amounts of grain each patch has
    private void initPatches() {
        System.out.println("Start Init Patches");
        patches = new Patch[Parameter.MAX_WORLD_X][Parameter.MAX_WORLD_Y];

        // give some patches the highest amount of grain possible --
        // these patches are the "best land"
        System.out.println("Start Set Best Patches");
        for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
            for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                patches[x][y] = new Patch();
                Random ran = new Random();
                if (ran.nextInt(100) <= Parameter.PERCENT_BEST_LAND) {
                    patches[x][y].setMaxGrainHere(Parameter.MAX_GRAIN);
                    patches[x][y].setGrainHere(patches[x][y].getGrainHere());
                }
            }
        }
        System.out.println("End Set Best Patches");

        // spread that grain around the window a little and put a little back
        // into the patches that are the "best land" found above
        System.out.println("Start First Diffuse");
        for (int i = 0; i < 5; i++) {
            for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
                for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                    if (patches[x][y].getMaxGrainHere() != 0) {
                        patches[x][y].setGrainHere(patches[x][y].getMaxGrainHere());
                    }
                }
            }
            for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
                for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                    if (patches[x][y].getGrainHere() != 0) {
                        diffuseGrain(x, y);
                    }
                }
            }
        }
        System.out.println("End First Diffuse");

        //spread the grain around some more
        System.out.println("Start Second Diffuse");
        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
                for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                    if (patches[x][y].getGrainHere() != 0) {
                        diffuseGrain(x, y);
                    }
                }
            }
        }
        System.out.println("End Second Diffuse");

        for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
            for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                //round grain levels to whole numbers
                patches[x][y].setGrainHere(Math.floor(patches[x][y].getGrainHere()));

                //initial grain level is also maximum
                patches[x][y].setMaxGrainHere(patches[x][y].getGrainHere());
            }
        }
        System.out.println("End Init Patches");
    }


    private void updateLorenzAndGini(int time) {
        double totalWealth = 0;
        double wealthSumSoFar = 0;

        double giniIndexReserve = 0.0;
        double giniValue = 0.0;

        ArrayList<Double> sortedWealth = new ArrayList<>();

        int poorNum = 0;
        int midNum = 0;
        int richNum = 0;

        for (Person person : people) {
            sortedWealth.add(person.getWealth());
            //Add wealth
            totalWealth += person.getWealth();
        }
        Collections.sort(sortedWealth);

        double highestWealth = sortedWealth.get(sortedWealth.size() - 1 );

        for (int i = 0; i < people.size(); i++){
            if(sortedWealth.get(i) <= highestWealth /3){
                poorNum++;
            }else if (sortedWealth.get(i) >= highestWealth / 3 * 2 ){
                richNum++;
            }else{
                midNum++;
            }

            wealthSumSoFar = wealthSumSoFar + sortedWealth.get(i);

            giniIndexReserve = giniIndexReserve + (double)(i + 1)/people.size() -
                    (double) wealthSumSoFar/totalWealth;
        }

        giniValue = giniIndexReserve / people.size() * 2;

        Data record = new Data();
        record.setTimeTick(time);
        record.setPoorNum(poorNum);
        record.setMiddleNum(midNum);
        record.setRichNum(richNum);
        record.setGiniValue(giniValue);

        data.add(record);


    }

    public void worldGo() {
        for (int time = 0; time < Parameter.MAX_TIME_TICK; time++) {
            //get people's location
            peopleLoc = new ArrayList<int[]>();
            for (Person p : people) {
                int[] point = new int[2];
                point[0] = p.getLocX();
                point[1] = p.getLocY();
                peopleLoc.add(point);
            }

            //choose direction holding most grain within the turtle's vision
            for (Person p : people) {
                p.turnTowardsGrain();
            }

            //peopleHarvest
            for (Person p : people) {
                p.harvest();
            }
            for (int[] point : peopleLoc) {
                patches[point[0]][point[1]].setGrainHere(0);
            }

            //peopleMoveEatAgeDie
            for (Person p : people) {
                p.moveEatAgeDie();
            }

            if (time != 0 && (time % Parameter.GRAIN_GROWTH_INTERVAL) == 0) {
                patchesGrowGrain();
            }

            updateLorenzAndGini(time);

        }
        ExportCSV.exportCSV(data,"test.csv");


    }

    //Get num of people in (x,y)
    public int getNumPeople(int x, int y) {
        int numHere = 0;
        for (int[] p : peopleLoc) {
            if (x == p[0] && y == p[1]) numHere++;
        }
        return numHere;
    }

    //Gey num of grain in (x,y)
    public double getGrain(int x, int y) {
        return patches[x][y].getGrainHere();
    }

    public void patchesGrowGrain() {
        for (int x = 0; x < Parameter.MAX_WORLD_X; x++) {
            for (int y = 0; y < Parameter.MAX_WORLD_Y; y++) {
                patches[x][y].growGrain();
            }
        }
    }

    //diffuse grain to its 8 neighbors
    public void diffuseGrain(int x, int y) {
        int neighbors = 8;
        double grainDiffuseNum = (patches[x][y].getGrainHere() * Parameter.SPREAD_PERCENTAGE) / neighbors;
        patches[x][y].setGrainHere(patches[x][y].getGrainHere() * (1 - Parameter.SPREAD_PERCENTAGE));

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (x == 0 && y == 0) {
                    continue;
                }

                // set neighbor's location (currentX, currentY)
                // if the location is out of bound, it will be set to the other side
                int currentX, currentY;
                if (x + i >= 0) {
                    if (x + i < Parameter.MAX_WORLD_X) {
                        currentX = x + i;
                    } else {
                        currentX = x + i - Parameter.MAX_WORLD_X;
                    }
                } else {
                    currentX = x + i + Parameter.MAX_WORLD_X;
                }

                if (y + j >= 0) {
                    if (y + j < Parameter.MAX_WORLD_Y) {
                        currentY = y + j;
                    } else {
                        currentY = y + j - Parameter.MAX_WORLD_Y;
                    }
                } else {
                    currentY = y + j + Parameter.MAX_WORLD_Y;
                }

                patches[currentX][currentY].setGrainHere(patches[currentX][currentY].getGrainHere() + grainDiffuseNum);
            }
    }

}
