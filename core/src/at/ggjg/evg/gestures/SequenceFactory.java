package at.ggjg.evg.gestures;

/**
 * Created by jarhoax on 1/31/16.
 */
public class SequenceFactory {

    public static Sequence createSquare(int... areas) {
        return new Sequence(Sequence.SequenceName.SQUARE, areas);
    }

    public static Sequence createHorizontalLine(int... areas) {
        return new Sequence(Sequence.SequenceName.HORIZONTAL_LINE, areas);
    }

    public static Sequence createVerticalLine(int... areas) {
        return new Sequence(Sequence.SequenceName.VERTICAL_LINE, areas);
    }

    public static Sequence createCircle(int... areas) {
        return new Sequence(Sequence.SequenceName.CIRCLE, areas);
    }

    public static Sequence createPenta(int... areas) {
        return new Sequence(Sequence.SequenceName.PENTAGON, areas);
    }

    public static Sequence createTriangle(int... areas) {
        return new Sequence(Sequence.SequenceName.TRIANGLE, areas);
    }

    public static Sequence createHeart(int... areas) {
        return new Sequence(Sequence.SequenceName.HEART, areas);
    }
}
