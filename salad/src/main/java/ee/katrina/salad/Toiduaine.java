package ee.katrina.salad;

public class Toiduaine {

    String nimetus;
    double valguProtsent;
    double rasvaProtsent;
    double susivesikuteProtsent;

    // Protsent kokku ei saa ületada 100
    public boolean checkPercentageSum() {
        return !(valguProtsent + rasvaProtsent + susivesikuteProtsent > 100);
    }

}
