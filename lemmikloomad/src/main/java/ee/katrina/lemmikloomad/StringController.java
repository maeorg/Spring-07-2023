package ee.katrina.lemmikloomad;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class StringController {

    List<String> cars = new ArrayList<>(Arrays.asList("BMW", "Nobe", "Tesla"));
//    String[] cars2 = {"BMW", "Nobe", "Tesla"};

    @GetMapping("car/add/{newCar}")
    public  List<String> addCar(@PathVariable String newCar) {
        cars.add(newCar);
        return cars;
    }

    @GetMapping("car/delete/{index}")
    public  List<String> deleteCar(@PathVariable int index) {
        cars.remove(index);
        return cars;
    }

    @GetMapping("car/view")
    public  List<String> viewCar() {
        return cars;
    }

    @GetMapping("hi") // localhost:8080/hi
    public String helloWorld() {
        return "Hello world at " + new Date();
    }

    @GetMapping("hi/{name}") // localhost:8080/hi/Katrina
    public String helloName(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("hi/{name}/{telephone}/{address}/{height}/{weight}")
    public String helloPerson(
            @PathVariable String name,
            @PathVariable String telephone,
            @PathVariable String address,
            @PathVariable String height,
            @PathVariable String weight
    ) {
        return "Hello " + name;
    }

    @GetMapping("hello") // localhost:8080/hello?name=Katrina&telephone=52352&address=Tammsaare11&height=170&weight=60
    public String helloPerson2(
            @RequestParam String name,
            @RequestParam String telephone,
            @RequestParam String address,
            @RequestParam String height,
            @RequestParam String weight
    ) {
        Person person = new Person();

        return "Hello " + name;
    }

}
