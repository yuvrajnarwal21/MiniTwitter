import java.util.ArrayList;

public class TwitterUser extends TwitterComponent implements TweetSubject, TweetObserver {
    private ArrayList<TwitterUser> followerList;
    private ArrayList<TwitterUser> followingList;
    private ArrayList<String> newsFeed;
    private ArrayList<UserDashboard> openedDashboards;

    public TwitterUser(String id) {
        super(id);

        followerList = new ArrayList<TwitterUser>();
        followingList = new ArrayList<TwitterUser>();
        newsFeed = new ArrayList<String>();
        openedDashboards = new ArrayList<UserDashboard>();
    }

    public void followUser(TwitterUser userToFollow) {
        if (userToFollow == null) {
            return;
        }

        if (userToFollow == this) {
            return;
        }

        if (followingList.contains(userToFollow)) {
            return;
        }

        followingList.add(userToFollow);
        userToFollow.addFollower(this);
    }

    public void postTweet(String tweet) {
        if (tweet == null || tweet.trim().isEmpty()) {
            return;
        }

        String fullTweet = id + ": " + tweet;

        updateFeed(fullTweet);
        notifyFollowers(fullTweet);
    }

    public void addFollower(TweetObserver observer) {
        if (observer instanceof TwitterUser) {
            TwitterUser newFollower = (TwitterUser) observer;

            if (!followerList.contains(newFollower)) {
                followerList.add(newFollower);
            }
        }
    }

    public void notifyFollowers(String message) {
        for (TwitterUser follower : followerList) {
            follower.updateFeed(message);
        }
    }

    public void updateFeed(String message) {
        newsFeed.add(message);
        refreshDashboards();
    }

    public void addDashboard(UserDashboard dashboard) {
        openedDashboards.add(dashboard);
    }

    public void refreshDashboards() {
        for (UserDashboard dashboard : openedDashboards) {
            dashboard.refreshLists();
        }
    }

    public ArrayList<TwitterUser> getFollowingList() {
        return followingList;
    }

    public ArrayList<String> getNewsFeed() {
        return newsFeed;
    }

    public void accept(TwitterVisitor visitor) {
        visitor.visitUser(this);
    }
}