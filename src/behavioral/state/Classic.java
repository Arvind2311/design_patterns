package behavioral.state;

public class Classic {
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();
    }
}

class State{
    void on(LightSwitch ls){
        System.out.println("Light is already on");
    }

    void off (LightSwitch ls){
        System.out.println("Light is already off");
    }
}

class OnState extends State{
    public OnState() {
        System.out.println("Light is turned on!!");
    }

    @Override
    void off(LightSwitch ls) {
        System.out.println("Switching Light off...");
        ls.setState(new OffState());
    }
}

class OffState extends State{
    public OffState() {
        System.out.println("Light is turned off!!");
    }

    @Override
    void on(LightSwitch ls) {
        System.out.println("Switching Light on...");
        ls.setState(new OnState());
    }
}

class LightSwitch{
    private State state; //OnState OffState

    public LightSwitch() {
        state = new OffState();
    }

    public void setState(State state) {
        this.state = state;
    }

    void on(){
        state.on(this);
    }

    void off(){
        state.off(this);
    }
}