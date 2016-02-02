public class EnumDayTest {
    
    public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    Day day;

    public EnumDayTest(Day day) {
        this.day = day;
   }

   public void tellItLikeItIs() {
        switch (day) {
            case MONDAY:
                System.out.println("Mondays are bad.");
                break;
                    
            case FRIDAY:
                System.out.println("Fridays are better.");
                break;
                         
            case SATURDAY: case SUNDAY:
                System.out.println("Weekends are best.");
                break;
                        
            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }

    public static void main(String[] args) {
        EnumDayTest firstDay = new EnumDayTest(Day.MONDAY);
        firstDay.tellItLikeItIs();
        EnumDayTest thirdDay = new EnumDayTest(Day.WEDNESDAY);
        thirdDay.tellItLikeItIs();
        EnumDayTest fifthDay = new EnumDayTest(Day.FRIDAY);
        fifthDay.tellItLikeItIs();
        EnumDayTest sixthDay = new EnumDayTest(Day.SATURDAY);
        sixthDay.tellItLikeItIs();
        EnumDayTest seventhDay = new EnumDayTest(Day.SUNDAY);
        seventhDay.tellItLikeItIs();

        for (Day d : Day.values()) {
            System.out.println(d);
        }
    }
}
