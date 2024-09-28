package betline.core;

/*
 * Единичная позиция в линии
 */
public class LineUnit {
    private final String text;
    private final double yes;
    private final double no;

    public LineUnit(String text, double yes, double no) {
        this.text = text;
        this.yes = yes;
        this.no = no;
    }

    public String getText() {
        return text;
    }

    public double getYes() {
        return yes;
    }

    public double getNo() {
        return no;
    }

    @Override
    public String toString() {
        return text + ": YES = " + yes + " NO = " + no;
    }
}

