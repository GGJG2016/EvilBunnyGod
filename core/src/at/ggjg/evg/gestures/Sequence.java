package at.ggjg.evg.gestures;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by zelle on 30.01.2016.
 */
public class Sequence {

    private LinkedList<Integer> sequence;
    private SequenceName sequenceName;

    public Sequence(SequenceName sequenceName, int... sequence) {
        this.sequenceName = sequenceName;
        this.sequence = new LinkedList<Integer>();
        for (int sequ : sequence) {
            this.sequence.add(sequ);
        }
    }

   public int getSequenceMatch(LinkedList<Integer> draggedSequence){
       if(draggedSequence.size() == 0){
           return 0;
       }
       int hightestMatch = 0;
       int match = 0;
       int draggedSequItem = draggedSequence.get(0);
       for(int i=0, j=0; i < sequence.size() && j < draggedSequence.size(); i++){
           int sequItem = sequence.get(i);
           if(sequItem == draggedSequItem){
               match++;
               j++;
               draggedSequItem = j < draggedSequence.size() ? draggedSequence.get(j) : -1;
           } else {
               j=0;
               draggedSequItem = draggedSequence.get(j);
               if(hightestMatch < match){
                   hightestMatch = match;
               }
           }
//          comment in if exact match is needed
//           if(draggedSequence.size() >= (j+1)){
//               return 0;
//           }
       }
       if(hightestMatch < match){
           hightestMatch = match;
       }
       return hightestMatch;
   }

    public boolean isSequenceMatch(LinkedList<Integer> draggedSequence){
        int match = getSequenceMatch(draggedSequence);
        return match > 0 && match == sequence.size();
    }

    public SequenceName getSequenceName() {
        return sequenceName;
    }

    public enum SequenceName {
        SQUARE,
        CIRCLE,
        LINE, PENTAGON
    }
}
