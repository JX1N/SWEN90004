/**
 * Data class stores and organizes the data gathered in each time tick, like Gini index and number
 * of people in each class. These data will be exported in a CSV when world runs out of time ticks.
 */
public class Data {

    // The time tick of the system
    private int timeTick;
    private int poorNum;
    private int middleNum;
    private int richNum;
    private double giniValue;

    //output a data string
    public String getData() {
        String data = this.timeTick + "," + this.poorNum + "," + this.middleNum +
                "," + this.richNum + "," + this.giniValue;
        return data;
    }

    public void setTimeTick(int timeTick) {
        this.timeTick = timeTick;
    }

    public void setPoorNum(int poorNum) {
        this.poorNum = poorNum;
    }

    public void setMiddleNum(int middleNum) {
        this.middleNum = middleNum;
    }

    public void setRichNum(int richNum) {
        this.richNum = richNum;
    }

    public void setGiniValue(double giniValue) {
        this.giniValue = giniValue;
    }

}
