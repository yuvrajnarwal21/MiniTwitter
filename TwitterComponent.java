// This is the parent class for both users and groups.
// This helps with the Composite pattern because both can go inside the tree.

public abstract class TwitterComponent {
    protected String id;

    public TwitterComponent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void accept(TwitterVisitor visitor);

    public String toString() {
        return id;
    }
}