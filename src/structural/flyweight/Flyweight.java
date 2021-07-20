package structural.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Flyweight {

    public static void main(String[] args) {
        User1 user = new User1("John Smith");
        User1 user1 = new User1("Jane Smith");
    }

}

/* Consider a MMORPG game, where users' first/last name needs to be stored.
* Since there are many repetition of a name (John Smith, Jane Smith), we
* can save 5 bytes of memory using a stored map, db etc. and point the name to them.
* */

class User{
    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}

class User1{
    static List<String> strings = new ArrayList<>();
    private int[] names;
    public User1(String fullname){
        Function<String, Integer> getOrAdd = (String s) -> {
            int idx = strings.indexOf(s);
            if(idx!=-1) return idx;
            else{
                strings.add(s);
                return strings.size()-1;
            }
        };

        names = Arrays.stream(fullname.split(" "))
                .mapToInt(s -> getOrAdd.apply(s))
                .toArray();
    }
}