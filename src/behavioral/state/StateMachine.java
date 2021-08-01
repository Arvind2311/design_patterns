package behavioral.state;

import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine {
    private static Map<State1, List<Pair<Trigger, State1>>>
            rules = new HashMap<>();
    static {
        rules.put(State1.OFF_HOOK, List.of(
                new Pair<>(Trigger.CALL_DIAL, State1.CONNECTING),
                new Pair<>(Trigger.STOP_USING, State1.ON_HOOK)
        ));
        rules.put(State1.CONNECTING, List.of(
                new Pair<>(Trigger.HUNG_UP, State1.OFF_HOOK),
                new Pair<>(Trigger.CALL_CONNECTED, State1.CONNECTED)
        ));
        rules.put(State1.CONNECTED, List.of(
                new Pair<>(Trigger.LEFT_MESSAGE, State1.OFF_HOOK),
                new Pair<>(Trigger.HUNG_UP, State1.OFF_HOOK),
                new Pair<>(Trigger.PLACED_ON_HOLD, State1.ON_HOLD)
        ));
        rules.put(State1.ON_HOLD, List.of(
                new Pair<>(Trigger.TAKEN_OFF_HOLD, State1.CONNECTED),
                new Pair<>(Trigger.HUNG_UP, State1.OFF_HOOK)
        ));
    }

    private static State1 currentState = State1.OFF_HOOK;
    private static State1 exitState = State1.ON_HOOK;

    public static void main(String[] args) {
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("This phone is currently in: " + currentState);
            System.out.println("Select a trigger: ");
            for (int i=0; i<rules.get(currentState).size();++i){
                Trigger trigger = rules.get(currentState).get(i).getValue0();
                System.out.println(" "+ i + ". " + trigger);
            }

            boolean parseOk;
            int choice = 0;
            do {
                try{
                    System.out.println("Please enter your choice: ");
                    choice = Integer.parseInt(console.readLine());
                    parseOk = choice>=0 && choice<rules.get(currentState).size();
                } catch (Exception e){
                    parseOk = false;
                }
            }while(!parseOk);
            currentState = rules.get(currentState).get(choice).getValue1();
            if(currentState.equals(exitState)) break;
        }
        System.out.println("And we are done!!!");
    }
}

enum State1{
    OFF_HOOK, //Starting
    ON_HOOK, //Terminal
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Trigger{
    CALL_DIAL,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING
}
