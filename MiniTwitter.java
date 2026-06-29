import java.util.HashMap;

public class MiniTwitter {
    private static MiniTwitter instance;

    private TwitterGroup mainGroup;
    private HashMap<String, TwitterUser> users;
    private HashMap<String, TwitterGroup> groups;

    private MiniTwitter() {
        mainGroup = new TwitterGroup("Root");

        users = new HashMap<String, TwitterUser>();
        groups = new HashMap<String, TwitterGroup>();

        groups.put("Root", mainGroup);
    }

    public static MiniTwitter getInstance() {
        if (instance == null) {
            instance = new MiniTwitter();
        }

        return instance;
    }

    public TwitterGroup getMainGroup() {
        return mainGroup;
    }

    public boolean addUser(String userId, TwitterGroup group) {
        userId = userId.trim();

        if (userId.isEmpty()) {
            return false;
        }

        if (users.containsKey(userId) || groups.containsKey(userId)) {
            return false;
        }

        TwitterUser newUser = new TwitterUser(userId);

        users.put(userId, newUser);
        group.addItem(newUser);

        return true;
    }

    public boolean addGroup(String groupId, TwitterGroup group) {
        groupId = groupId.trim();

        if (groupId.isEmpty()) {
            return false;
        }

        if (users.containsKey(groupId) || groups.containsKey(groupId)) {
            return false;
        }

        TwitterGroup newGroup = new TwitterGroup(groupId);

        groups.put(groupId, newGroup);
        group.addItem(newGroup);

        return true;
    }

    public TwitterUser getUser(String userId) {
        return users.get(userId);
    }

    public TwitterGroup getGroup(String groupId) {
        return groups.get(groupId);
    }
}