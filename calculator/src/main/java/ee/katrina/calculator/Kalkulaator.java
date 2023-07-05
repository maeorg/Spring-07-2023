package ee.katrina.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class Kalkulaator { // Tee uus class Kalkulaator, millele lisa üles annotationina @RestController (lisa sellele import!)

    // Tee Controlleri sisse massiiv numbritest (lisa uus muutuja):
    //	List<Integer> integers = new ArrayList<>(Arrays.asList(31, 52, 24));    (nb! impordid listidele!)
    List<Integer> integers = new ArrayList<>(Arrays.asList(31, 52, 24));

    // Tee GetMapping sees uus lehekülg, mis tagastab kõik numbrid. Kontrolli.
    @GetMapping("kalkulaator/allNumbers") // localhost:8080/kalkulaator/allNumbers
    public List<Integer> allNumbers() {
        return integers;
    }

    // Tee uus funktsioon mis lisab ühe numbri juurde. Kontrolli kõikide võtmise kaudu kas lisati.
    @GetMapping("kalkulaator/addNumber") // localhost:8080/kalkulaator/addNumber?number=3
    public List<Integer> addNumber(@RequestParam Integer number) {
        integers.add(number);
        return integers;
    }

    // Tee uus funktsioon mis kustutab ühe numbri. Kontrolli kõikide võtmise kaudu kas kustutati.
    @GetMapping("kalkulaator/removeNumber") // localhost:8080/kalkulaator/removeNumber?number=3
    public List<Integer> removeNumber(@RequestParam Integer number) {
        integers.remove(number);
        return integers;
    }

    // Tee uus funktsioon mis kustutab kõik numbrid. Kontrolli kõikide võtmise kas kustutati.
    @GetMapping("kalkulaator/removeAllNumbers") // localhost:8080/kalkulaator/removeAllNumbers
    public List<Integer> removeAllNumbers() {
        integers.removeAll(integers);
        return integers;
    }

    // Tee uus funktsioon mis arvutab kokku kõikide numbrite kogusumma ja tagastab selle ühe numbrina.
    @GetMapping("kalkulaator/sumOfAllNumbers") // localhost:8080/kalkulaator/sumOfAllNumbers
    public int sumOfAllNumbers() {
        int sum = 0;
        for (Integer number : integers) {
            sum += number;
        }
        return sum;
    }

    // Tee uus funktsioon mis arvutab kokku kõikide numbrite keskmise ja tagastab selle ühe numbrina.
    @GetMapping("kalkulaator/averageOfAllNumbers") // localhost:8080/kalkulaator/averageOfAllNumbers
    public int averageOfAllNumbers() {
        return sumOfAllNumbers()/integers.size();
    }

    // Tee uus funktsioon mis arvutab mitu numbrit listis on ja tagastab selle ühe numbrina.
    @GetMapping("kalkulaator/countOfNumbers") // localhost:8080/kalkulaator/countOfNumbers
    public int countOfNumbers() {
        return integers.size();
    }

    // Tee uus funktsioon mis suurendab kõiki numbreid täpselt 1 võrra.
    @GetMapping("kalkulaator/increaseByOne") // localhost:8080/kalkulaator/increaseByOne
    public List<Integer> increaseByOne() {
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, integers.get(i) + 1);
        }
        return integers;
    }

    // Tee uus funktsioon mis suurendab kõiki numbreid täpselt numbri võrra mida kaasa saadad.
    @GetMapping("kalkulaator/increaseByNumber") // localhost:8080/kalkulaator/increaseByNumber?number=10
    public List<Integer> increaseByNumber(@RequestParam int number) {
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, integers.get(i) + number);
        }
        return integers;
    }

    // Tee uus funktsioon mis jagab kõiki numbreid 10ga.
    @GetMapping("kalkulaator/divideByTen") // localhost:8080/kalkulaator/divideByTen
    public List<Integer> divideByTen() {
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, integers.get(i) / 10);
        }
        return integers;
    }

    // Tee uus funktsioon mis jagab kõiki numbreid sellega mis kaasa saadad.
    @GetMapping("kalkulaator/divideByNumber") // localhost:8080/kalkulaator/divideByNumber?number=5
    public List<Integer> divideByNumber(@RequestParam int number) {
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, integers.get(i) / number);
        }
        return integers;
    }

    // Tee 4 uut funktsiooni, üks liidab, teine lahutab, kolmas korrutab ja neljas jagab.


}