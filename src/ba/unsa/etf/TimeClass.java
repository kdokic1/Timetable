package ba.unsa.etf;

public class TimeClass {
   private int hours;
   private int minutes;

    public TimeClass(int hour, int minute) {
        this.hours = hour;
        this.minutes = minute;
    }

    public TimeClass(TimeClass timeClass){
        this.hours=timeClass.getHours();
        this.minutes=timeClass.getMinutes();
    }

    public TimeClass(){}

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void addMinutes(int minutes){
        if(this.minutes+minutes>59){
            hours=hours+1;
            this.minutes+=minutes;
            this.minutes-=60;
        }
        else{
            this.minutes+=minutes;
        }
    }
}
