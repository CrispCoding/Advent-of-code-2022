package days.day06;

public abstract class Element {
    private String name;
    private Directory parent;

    public Element(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public abstract long getSize();
}
