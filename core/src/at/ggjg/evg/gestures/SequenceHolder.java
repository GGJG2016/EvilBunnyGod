package at.ggjg.evg.gestures;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zelle on 30.01.2016.
 */
public class SequenceHolder {

    private int maxItems;
    private LinkedList<Integer> tmpLastArea;
    private LinkedList<Integer> lastArea;
    private ArrayList<Sequence> sequenceList;

    public SequenceHolder(ArrayList<Sequence> sequenceList) {
        this(24, sequenceList);
    }

    public SequenceHolder(int maxItems, ArrayList<Sequence> sequenceList) {
        tmpLastArea = new LinkedList<Integer>();
        lastArea = new LinkedList<Integer>();
        this.maxItems = maxItems;
        this.sequenceList = sequenceList;
    }

    public void addToLastAreas(Integer area) {
        if (tmpLastArea.isEmpty() || !tmpLastArea.getLast().equals(area)) {
            if (tmpLastArea.size() + 1 > maxItems) {
                tmpLastArea.removeFirst();
            }
            tmpLastArea.add(area);
            System.out.println("Area: " + area);
        }
    }

    public LinkedList<Integer> getTmpLastArea() {
        return tmpLastArea;
    }

    public LinkedList<Integer> getLastArea() {
        return lastArea;
    }

    public void clearTmpLastArea() {
        this.tmpLastArea.clear();
    }

    public void clearLastArea() {
        this.lastArea.clear();
    }

    public void stopPanning() {
        this.lastArea.clear();
        this.lastArea.addAll(tmpLastArea);
    }

    public ArrayList<Sequence> getSequenceList() {
        return sequenceList;
    }

    public void setSequenceList(ArrayList<Sequence> sequenceList) {
        this.sequenceList = sequenceList;
    }

    public Sequence getMatch() {
        for (Sequence sequence : sequenceList) {
            if (sequence.isSequenceMatch(getLastArea())) {
                return sequence;
            }
        }
        return null;
    }
}
