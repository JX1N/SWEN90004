public class Data {

    // The time tick of the system
    private int timeTick;
    private int poorNum;
    private int middleNum;
    private int richNum;
    private double giniValue;


    public String getData() {
        String data = this.timeTick + "," + this.poorNum + "," + this.middleNum +
                "," + this.richNum + "," + this.giniValue;
        return data;
    }

    public int getTimeTick() {
        return timeTick;
    }

    public void setTimeTick(int timeTick) {
        this.timeTick = timeTick;
    }

    public int getPoorNum() {
        return poorNum;
    }

    public void setPoorNum(int poorNum) {
        this.poorNum = poorNum;
    }

    public int getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(int middleNum) {
        this.middleNum = middleNum;
    }

    public int getRichNum() {
        return richNum;
    }

    public void setRichNum(int richNum) {
        this.richNum = richNum;
    }

    public double getGiniValue() {
        return giniValue;
    }

    public void setGiniValue(double giniValue) {
        this.giniValue = giniValue;
    }

}
