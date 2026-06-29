public class TwitterVisitor {
    private int userTotal;
    private int groupTotal;
    private int messageTotal;
    private int positiveTotal;

    private String[] positiveWords = {
            "good", "great", "excellent", "cool", "awesome", "nice", "love", "happy"
    };

    public TwitterVisitor() {
        userTotal = 0;
        groupTotal = 0;
        messageTotal = 0;
        positiveTotal = 0;
    }

    public void visitUser(TwitterUser user) {
        userTotal++;

        for (String message : user.getNewsFeed()) {
            messageTotal++;

            String lowerMessage = message.toLowerCase();

            for (String word : positiveWords) {
                if (lowerMessage.contains(word)) {
                    positiveTotal++;
                    break;
                }
            }
        }
    }

    public void visitGroup(TwitterGroup group) {
        groupTotal++;
    }

    public int getUserTotal() {
        return userTotal;
    }

    public int getGroupTotal() {
        return groupTotal;
    }

    public int getMessageTotal() {
        return messageTotal;
    }

    public double getPositivePercentage() {
        if (messageTotal == 0) {
            return 0;
        }

        return (positiveTotal * 100.0) / messageTotal;
    }
}