package days.day06;

public class File extends Element{
    private long size;

    public File(String name, Directory parent, long size) {
        super(name, parent);
        this.size = size;
    }

    @Override
    public long getSize() {
        return size;
    }
}
