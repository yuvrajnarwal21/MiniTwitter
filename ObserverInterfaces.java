// These interfaces are for the Observer pattern.
// When one user posts, the followers get updated.

interface TweetObserver {
    void updateFeed(String message);
}

interface TweetSubject {
    void addFollower(TweetObserver observer);
    void notifyFollowers(String message);
}