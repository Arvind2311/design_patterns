package solid_principles;

/*
* ISP:- Interface Segregation Principle
* */

public class ISP {
    public static void main(String[] args) {

    }
}

class Document{

}

interface Machine{
    void print(Document d);
    void fax(Document d);
    void scan(Document d);
}

class MultiFunctionPrinter implements Machine{
    @Override
    public void print(Document d) {
        //
    }

    @Override
    public void fax(Document d) {
        //
    }

    @Override
    public void scan(Document d) {
        //
    }
}

/*
* ISP fails over here, cause as a client with just printing machine, I don't care about faxing
* and scanning, cause my hardware doesn't support so (thus forcing my client to use the methods).
* Either use exceptions/dummy code or segregate the functionality into smaller interfaces.
* */
class OldFashionedPrinter implements Machine{
    @Override
    public void print(Document d) {
        //
    }

    @Override
    public void fax(Document d) {

    }

    @Override
    public void scan(Document d) {
        //
    }
}

interface Printer{
    void print(Document d);
}

interface Scanner{
    void scan(Document d);
}

interface Faxing{
    void fax(Document d);
}

// Follow this rule:- YAGNI -> You Ain't Going to Need It

class JustAPrinter implements Printer{
    @Override
    public void print(Document d) {
        //
    }
}

class Photocopier implements Printer, Scanner{
    @Override
    public void print(Document d) {
        //
    }

    @Override
    public void scan(Document d) {
        //
    }
}

interface MultiFunctionDevice extends Printer,Scanner,Faxing{ }

class MultiFunctionMachine implements MultiFunctionDevice{
    @Override
    public void print(Document d) {
        //
    }

    @Override
    public void scan(Document d) {
        //
    }

    @Override
    public void fax(Document d) {
        //
    }
}