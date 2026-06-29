import java.util.ArrayList;

public class TwitterGroup extends TwitterComponent {
    private ArrayList<TwitterComponent> items;

    public TwitterGroup(String id) {
        super(id);
        items = new ArrayList<TwitterComponent>();
    }

    public void addItem(TwitterComponent item) {
        items.add(item);
    }

    public ArrayList<TwitterComponent> getItems() {
        return items;
    }

    public void accept(TwitterVisitor visitor) {
        visitor.visitGroup(this);

        for (TwitterComponent item : items) {
            item.accept(visitor);
        }
    }
}