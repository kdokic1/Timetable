package ba.unsa.etf;

public enum Day {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT;
    @Override
    public String toString() {
        if(this==Day.MON)
            return "MON";
        else if(this==Day.TUE)
            return "TUE";
        else if(this==Day.WED)
            return "WED";
        else if(this==Day.THU)
            return "THU";
        else if(this==Day.FRI)
            return "FRI";
        else
            return "SAT";
    }
}
