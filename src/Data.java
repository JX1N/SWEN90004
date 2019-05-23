public class Data {

    // The time tick of the system
    private int timeTick;

    private int poorNum;
    private int middleNum;
    private int richNum;




    public String get_data()
    {
        String data = this.timeTick + "," + this.poorNum + "," + this.middleNum +
                "," + this.richNum ;
        return data;
    }

}
