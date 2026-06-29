import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private MiniTwitter miniTwitter;
    private TwitterUser currentUser;

    private JTextField followBox;
    private JTextField tweetBox;

    private DefaultListModel<String> followingModel;
    private DefaultListModel<String> feedModel;

    public UserDashboard(TwitterUser user) {
        miniTwitter = MiniTwitter.getInstance();
        currentUser = user;

        setTitle("User View - " + currentUser.getId());
        setSize(500, 450);
        setLocationRelativeTo(null);

        buildWindow();
        refreshLists();

        setVisible(true);
    }

    private void buildWindow() {
        followBox = new JTextField();
        tweetBox = new JTextField();

        JButton followButton = new JButton("Follow User");
        JButton postButton = new JButton("Post Tweet");

        followingModel = new DefaultListModel<String>();
        feedModel = new DefaultListModel<String>();

        JList<String> followingList = new JList<String>(followingModel);
        JList<String> feedList = new JList<String>(feedModel);

        followButton.addActionListener(e -> followUser());
        postButton.addActionListener(e -> postTweet());

        JPanel followPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        followPanel.add(followBox);
        followPanel.add(followButton);

        JPanel tweetPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        tweetPanel.add(tweetBox);
        tweetPanel.add(postButton);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        mainPanel.add(followPanel);
        mainPanel.add(new JScrollPane(followingList));
        mainPanel.add(tweetPanel);
        mainPanel.add(new JScrollPane(feedList));

        add(mainPanel);
    }

    private void followUser() {
        String userId = followBox.getText().trim();

        TwitterUser userToFollow = miniTwitter.getUser(userId);

        if (userToFollow == null) {
            JOptionPane.showMessageDialog(this, "User was not found.");
            return;
        }

        currentUser.followUser(userToFollow);

        followBox.setText("");
        refreshLists();
    }

    private void postTweet() {
        String tweet = tweetBox.getText().trim();

        if (tweet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tweet cannot be empty.");
            return;
        }

        currentUser.postTweet(tweet);

        tweetBox.setText("");
        refreshLists();
    }

    public void refreshLists() {
        followingModel.clear();
        feedModel.clear();

        for (TwitterUser user : currentUser.getFollowingList()) {
            followingModel.addElement(user.getId());
        }

        for (String message : currentUser.getNewsFeed()) {
            feedModel.addElement(message);
        }
    }
}