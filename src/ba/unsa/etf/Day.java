package ba.unsa.etf;

public enum Day {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT;

    private Day temp;

    public Day getDay(){
        return temp;
    }

    public void setDay(Day d){
        temp=d;
    }
}
