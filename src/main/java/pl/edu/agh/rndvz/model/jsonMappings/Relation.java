package pl.edu.agh.rndvz.model.jsonMappings;

public class Relation {
    private Long from;
    private Long to;

    public Relation() {
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Relation(Long from, Long to) {
        this.from = from;

        this.to = to;
    }
}
