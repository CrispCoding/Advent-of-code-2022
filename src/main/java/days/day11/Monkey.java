package days.day11;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Monkey {
    // counting the number of inspected items
    private int inspectionCount;

    // worry level operations
    boolean divideWorryLevel;
    private String operator;
    private Optional<Long> operand;
    private Long moduloReductionValue;

    // deciding where to throw the item
    private long divisionTestValue;
    private int recipientOnTestSuccess;
    private int recipientOnTestFailure;

    // list of currently held items
    private Deque<Long> myItems;

    public Monkey(String monkeyNotes){
        this(monkeyNotes, true);
    }

    public Monkey(String monkeyNotes, boolean divideWorryLevel){
        this.divideWorryLevel = divideWorryLevel;

        List<String> lines = monkeyNotes.lines().toList();
        // the ID is given implicitly by the monkey's position in the list, we can ignore the first line

        // record the starting items
        myItems = new ArrayDeque<>();
        String [] items = lines.get(1) // get the line that describes the starting items
                .replace("Starting items:", "").trim() // remove the flavor text
                .split(", "); // split into items
        myItems = Stream.of(items)
                .map(Long::parseLong)// turn into long
                .collect(Collectors.toCollection(ArrayDeque::new));

        // set up the worry level operation
        String[] operationComponents = lines.get(2).split(" ");
        operator = operationComponents[operationComponents.length - 2]; // just store the operator string as it is
        try { // parse the operand into the optional container
            operand = Optional.of(Long.parseLong(operationComponents[operationComponents.length - 1]));
        } catch(NumberFormatException e){
            operand = Optional.ofNullable(null);
        }

        // parse the division test components
        divisionTestValue = Long.parseLong(lines.get(3).replace("Test: divisible by", "").trim());
        recipientOnTestSuccess = Integer.parseInt(lines.get(4).replace("If true: throw to monkey", "").trim());
        recipientOnTestFailure = Integer.parseInt(lines.get(5).replace("If false: throw to monkey", "").trim());
    }

    private long computeNewWorryLevel(long currentWorryLevel){
        long newWorryLevel = switch(operator){
            case "+" -> currentWorryLevel + operand.orElse(currentWorryLevel);
            case "*" -> currentWorryLevel * operand.orElse(currentWorryLevel);
            default -> throw new IllegalArgumentException("Invalid operator");
        };
        newWorryLevel = newWorryLevel / 3L; // integer division, implicitly rounds down to the nearest integer
        return newWorryLevel;
    }

    private long computeNewWorryLevelWithModulo(long currentWorryLevel){
        long operandValue = operand.orElse(currentWorryLevel);
        long newWorryLevel = switch(operator){
            case "+" -> (currentWorryLevel % moduloReductionValue) + (operandValue % moduloReductionValue);
            case "*" -> (currentWorryLevel % moduloReductionValue) * (operandValue % moduloReductionValue);
            default -> throw new IllegalArgumentException("Invalid operator");
        };
        // sanity check to make sure we don't run into an overflow without noticing
        if (newWorryLevel < 0){
            throw new IllegalArgumentException("overflow");
        }
        return newWorryLevel;
    }

    private int findRecipient(long worryLevel){
        if (worryLevel % divisionTestValue == 0){
            return recipientOnTestSuccess;
        }
        else {
            return recipientOnTestFailure;
        }
    }

    public void processItems(List<Monkey> monkeys){
        while (!myItems.isEmpty()){
            inspectionCount++;
            long item = myItems.removeFirst(); // get the current item and remove it from the list at the same time
            long newWorryLevel;
            if (divideWorryLevel){
                newWorryLevel = computeNewWorryLevel(item);
            }
            else {
                newWorryLevel = computeNewWorryLevelWithModulo(item);
            }
            int recipient = findRecipient(newWorryLevel);
            monkeys.get(recipient).addItem(newWorryLevel);
        }
    }

    public int getInspectionCount() {
        return inspectionCount;
    }

    public long getDivisionTestValue() {
        return divisionTestValue;
    }

    public void setModuloReductionValue(Long moduloReductionValue) {
        this.moduloReductionValue = moduloReductionValue;
    }

    public void addItem(long item){
        myItems.addLast(item);
    }

    public Deque<Long> getMyItems() {
        return myItems;
    }
}
