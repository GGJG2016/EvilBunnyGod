package at.ggjg.evg.gestures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zelle on 30.01.2016.
 */
public class SequenceHolder {

    private int maxItems;
    private LinkedList<Integer> lastAreas;
    private ArrayList<Sequence> sequenceList;

    public SequenceHolder(ArrayList<Sequence> sequenceList) {
        this(24, sequenceList);
    }

    public SequenceHolder(int maxItems, ArrayList<Sequence> sequenceList) {
        lastAreas = new LinkedList<Integer>();
        this.maxItems = maxItems;
        this.sequenceList = sequenceList;
    }

    public void addToLastAreas(Integer area) {
        if (lastAreas.isEmpty() || lastAreas.getLast() != area) {
            if (lastAreas.size() + 1 > maxItems) {
                lastAreas.removeFirst();
            }
            lastAreas.add(area);
            System.out.println("Area" + area);
        }
    }

    public LinkedList<Integer> getLastAreas() {
        return lastAreas;
    }

    public void clearLastArea() {
        this.lastAreas.clear();
    }

    public ArrayList<Sequence> getSequenceList() {
        return sequenceList;
    }

    public void setSequenceList(ArrayList<Sequence> sequenceList) {
        this.sequenceList = sequenceList;
    }

    public Sequence getMatch(){
        for(Sequence sequence : sequenceList){
            if(sequence.isSequenceMatch(getLastAreas())){
                return sequence;
            }
        }
        return null;
    }
}
