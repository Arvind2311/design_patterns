package creational.factory;

import org.javatuples.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AbstractFactory {
    public static void main(String[] args) throws Exception {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();
    }
}

interface HotDrink{
    void consume();
}

class Tea implements HotDrink{
    @Override
    public void consume() {
        System.out.println("This tea is delicious");
    }
}

class Coffee implements HotDrink{
    @Override
    public void consume() {
        System.out.println("Coffee is amazing");
    }
}

interface HotDrinkFactory{
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory{
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Put in tea bag, boil water, pour "
                + amount + "ml, add lemon, enjoy!!");
        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory{
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Grind some beans, boil water, pour "
                + amount + "ml, add cream and sugar, enjoy!!");
        return new Coffee();
    }
}

class HotDrinkMachine{
    private List<Pair<String, HotDrinkFactory>> nameFactories = new ArrayList<>();

    public HotDrinkMachine() throws Exception {
        Set<Class<? extends HotDrinkFactory>> types =
                new Reflections("creational").getSubTypesOf(HotDrinkFactory.class);
        for (Class<? extends HotDrinkFactory> type: types){
            nameFactories.add(new Pair<>(
               type.getSimpleName().replace("Factory",""), type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink() throws Exception{
        System.out.println("Available Drinks: ");
        for (int index = 0; index< nameFactories.size(); index++){
            Pair<String, HotDrinkFactory> item = nameFactories.get(index);
            System.out.println(""+ index + ": " + item.getValue0());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            String s;
            int i, amount;
            if((s=reader.readLine())!=null && (i = Integer.parseInt(s))>=0 && i<nameFactories.size()){
                System.out.println("Specify Amount: ");
                s = reader.readLine();
                if(s!=null && (amount = Integer.parseInt(s))>0){
                    return nameFactories.get(i).getValue1().prepare(amount);
                }
            }
            System.out.println("Incorrect Input, Try again!!");
        }
    }
}