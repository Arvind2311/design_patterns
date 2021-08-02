package behavioral.strategy;

import java.util.List;
import java.util.function.Supplier;

public class StaticStrategy {
    public static void main(String[] args) {
        TextProcessor1<MarkdownListStrategy1> tp = new TextProcessor1<>(MarkdownListStrategy1::new);
        tp.appendList(List.of("alpha","beta","gamma"));
        System.out.println(tp);
        TextProcessor1<HTMLListStrategy1> tp1 = new TextProcessor1<>(HTMLListStrategy1::new);
        tp1.appendList(List.of("alpha","beta","gamma"));
        System.out.println(tp1);
    }
}

// Lists
// HTML
// <ul><li></li></ul>
// Markdown
// *Foo
// *bar

interface ListStrategy1{
    default void start(StringBuilder sb) {}
    void addListItem(StringBuilder sb, String item);
    default void end(StringBuilder sb) {}
}

class MarkdownListStrategy1 implements ListStrategy1{
    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" * ").append(item)
                .append(System.lineSeparator());
    }
}

class HTMLListStrategy1 implements ListStrategy1{
    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" <li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }
}

class TextProcessor1<LS extends ListStrategy1>{
    private StringBuilder sb = new StringBuilder();
    private LS listStrategy;

    public TextProcessor1(Supplier<? extends LS> ctor){
        listStrategy = ctor.get();
    }

    public void appendList(List<String> items){
        listStrategy.start(sb);
        for (String item:items){
            listStrategy.addListItem(sb, item);
        }
        listStrategy.end(sb);
    }

    public void clear(){
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}