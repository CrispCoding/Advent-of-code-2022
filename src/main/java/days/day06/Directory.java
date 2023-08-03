package days.day06;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Directory extends Element{
    private List<Directory> containedDirectories;
    private List<File> containedFiles;

    public Directory(String name, Directory parent){
        super(name, parent);
        this.containedDirectories = new ArrayList<Directory>();
        this.containedFiles = new ArrayList<File>();
    }

    public List<Directory> getContainedDirectories() {
        return containedDirectories;
    }

    public void addFile(String name, long size){
        File file = new File(name, this, size);
        containedFiles.add(file);
    }

    public void addDirectory(String name){
        Directory dir = new Directory(name, this);
        containedDirectories.add(dir);
    }

    public Directory getChildDirectoryByName (String name){
        for (Directory dir : containedDirectories){
            if (dir.getName().equals(name)){
                return dir;
            }
        }
        throw new IllegalArgumentException("No child directory with name " + name + " available");
    }

    @Override
    public long getSize() {
        long size = containedFiles.stream().map(File::getSize).reduce(0L, Long::sum)
                + containedDirectories.stream().map(Directory::getSize).reduce(0L, Long::sum);
        return size;
    }

    public List<Pair<String, Long>> getAllSubdirectorySizes(){
        List<Pair<String, Long>> sizeList = new ArrayList<Pair<String, Long>>();
        this.addAllSubdirectorySizesAndGetSize(sizeList);
        return sizeList;
    }

    long addAllSubdirectorySizesAndGetSize(List<Pair<String, Long>> sizeList){
        // sum up the sizes of all contained files
        long mySize = this.containedFiles.stream().map(File::getSize).reduce(0L, Long::sum);

        // go through all contained directories:
        // ask each of them to add its own subdirectory sizes to the list, then add its total size to my size
        for (Directory subdir: this.containedDirectories){
            long subdirSize = subdir.addAllSubdirectorySizesAndGetSize(sizeList);
            mySize += subdirSize;
        }

        // add my own size to the list
        Pair<String, Long> myDescription = Pair.with(this.getName(), mySize);
        sizeList.add(myDescription);

        return mySize;
    }
}
