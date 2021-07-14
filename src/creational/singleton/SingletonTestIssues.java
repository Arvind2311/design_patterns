package creational.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SingletonTestIssues {
    @Test //Not a unit test!! It is an integration test(accessing live data -> in this case the txt file).
    public void singletonTotalPopulation(){
        SingletonRecordFinder srf = new SingletonRecordFinder();
        List<String> names = List.of("Seoul", "Mexico City");
        int tp = srf.getTotalPopulation(names);
        Assert.assertEquals(175000000+174000000, tp);
    }

    @Test
    public void dependentPopulationTest(){
        DummyDatabase db = new DummyDatabase();
        ConfigurableRecordFinder crf = new ConfigurableRecordFinder(db);
        Assert.assertEquals(4, crf.getTotalPopulation(List.of("alpha","gamma")));
    }
}

interface Database{
    int getPopulation(String name);
}

class SingletonDatabase implements Database{

    private Dictionary<String,Integer> capitals = new Hashtable<>();

    private static int instanceCount = 0;

    public static int getInstanceCount(){
        return instanceCount;
    };

    private SingletonDatabase(){
        instanceCount++;
        System.out.println("Initializing Database");

        try{
            File f = new File("./capitals.txt");
            Path fullPath = Paths.get(f.getPath());
            List<String> lines = Files.readAllLines(fullPath);
            for(int index=0;index<lines.size();index+=2){
                capitals.put(lines.get(index).trim(), Integer.parseInt(lines.get(index+1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance(){
        return INSTANCE;
    }

    public int getPopulation(String city){
        return capitals.get(city);
    }

}

class ConfigurableRecordFinder{
    private Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> names){
        int result = 0;
        for (String name:names){
            result+=database.getPopulation(name);
        }
        return result;
    }
}

class DummyDatabase implements Database{

    private Dictionary<String,Integer> data = new Hashtable<>();

    public DummyDatabase(){
        data.put("alpha",1);
        data.put("beta",2);
        data.put("gamma",3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }
}

class SingletonRecordFinder{
    public int getTotalPopulation(List<String> names){
        int result = 0;
        for (String name:names){
            result+=SingletonDatabase.getInstance().getPopulation(name);
        }
        return result;
    }
}