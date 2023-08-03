package days;

public abstract class Day {
    public abstract String getName();
    public abstract int part1(String input);
    public int part2(String input) throws NotImplementedException{
        String errorMessage = this.getName() + ": part 2 has not yet been implemented";
        throw new NotImplementedException(errorMessage);
    }
}
