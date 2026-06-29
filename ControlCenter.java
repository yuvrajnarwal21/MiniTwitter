import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class ControlCenter extends JFrame {
    private static ControlCenter instance;

    private MiniTwitter miniTwitter;

    private JTree groupTree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;

    private JTextField userIdBox;
    private JTextField groupIdBox;

    private ControlCenter() {
        miniTwitter = MiniTwitter.getInstance();

        setTitle("Mini Twitter - Control Center");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildWindow();

        setVisible(true);
    }

    public static ControlCenter getInstance() {
        if (instance == null) {
            instance = new ControlCenter();
        }

        return instance;
    }

    private void buildWindow() {
        rootNode = new DefaultMutableTreeNode(miniTwitter.getMainGroup());
        treeModel = new DefaultTreeModel(rootNode);
        groupTree = new JTree(treeModel);

        JScrollPane treePane = new JScrollPane(groupTree);

        userIdBox = new JTextField();
        groupIdBox = new JTextField();

        JButton addUserButton = new JButton("Add User");
        JButton addGroupButton = new JButton("Add Group");
        JButton openUserButton = new JButton("Open User View");

        JButton showUsersButton = new JButton("Show User Total");
        JButton showGroupsButton = new JButton("Show Group Total");
        JButton showMessagesButton = new JButton("Show Messages Total");
        JButton showPositiveButton = new JButton("Show Positive %");

        addUserButton.addActionListener(e -> addUser());
        addGroupButton.addActionListener(e -> addGroup());
        openUserButton.addActionListener(e -> openUserView());

        showUsersButton.addActionListener(e -> showUserTotal());
        showGroupsButton.addActionListener(e -> showGroupTotal());
        showMessagesButton.addActionListener(e -> showMessageTotal());
        showPositiveButton.addActionListener(e -> showPositivePercent());

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(6, 2, 10, 10));

        rightSide.add(userIdBox);
        rightSide.add(addUserButton);

        rightSide.add(groupIdBox);
        rightSide.add(addGroupButton);

        rightSide.add(openUserButton);
        rightSide.add(new JLabel(""));

        rightSide.add(new JLabel(""));
        rightSide.add(new JLabel(""));

        rightSide.add(showUsersButton);
        rightSide.add(showGroupsButton);

        rightSide.add(showMessagesButton);
        rightSide.add(showPositiveButton);

        setLayout(new GridLayout(1, 2, 10, 10));

        add(treePane);
        add(rightSide);
    }

    private DefaultMutableTreeNode getSelectedNode() {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) groupTree.getLastSelectedPathComponent();

        if (selectedNode == null) {
            return rootNode;
        }

        return selectedNode;
    }

    private TwitterComponent getSelectedItem() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        return (TwitterComponent) selectedNode.getUserObject();
    }

    private void addUser() {
        String userId = userIdBox.getText().trim();
        TwitterComponent selectedItem = getSelectedItem();

        if (!(selectedItem instanceof TwitterGroup)) {
            JOptionPane.showMessageDialog(this, "Select a group first.");
            return;
        }

        TwitterGroup selectedGroup = (TwitterGroup) selectedItem;

        boolean added = miniTwitter.addUser(userId, selectedGroup);

        if (!added) {
            JOptionPane.showMessageDialog(this, "User was not added. Check the ID.");
            return;
        }

        TwitterUser newUser = miniTwitter.getUser(userId);

        DefaultMutableTreeNode selectedNode = getSelectedNode();
        DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(newUser);

        treeModel.insertNodeInto(newUserNode, selectedNode, selectedNode.getChildCount());

        userIdBox.setText("");
    }

    private void addGroup() {
        String groupId = groupIdBox.getText().trim();
        TwitterComponent selectedItem = getSelectedItem();

        if (!(selectedItem instanceof TwitterGroup)) {
            JOptionPane.showMessageDialog(this, "Select a group first.");
            return;
        }

        TwitterGroup selectedGroup = (TwitterGroup) selectedItem;

        boolean added = miniTwitter.addGroup(groupId, selectedGroup);

        if (!added) {
            JOptionPane.showMessageDialog(this, "Group was not added. Check the ID.");
            return;
        }

        TwitterGroup newGroup = miniTwitter.getGroup(groupId);

        DefaultMutableTreeNode selectedNode = getSelectedNode();
        DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(newGroup);

        treeModel.insertNodeInto(newGroupNode, selectedNode, selectedNode.getChildCount());

        groupIdBox.setText("");
    }

    private void openUserView() {
        TwitterComponent selectedItem = getSelectedItem();

        if (!(selectedItem instanceof TwitterUser)) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }

        TwitterUser selectedUser = (TwitterUser) selectedItem;

        UserDashboard dashboard = new UserDashboard(selectedUser);
        selectedUser.addDashboard(dashboard);
    }

    private TwitterVisitor getFreshVisitor() {
        TwitterVisitor visitor = new TwitterVisitor();
        miniTwitter.getMainGroup().accept(visitor);

        return visitor;
    }

    private void showUserTotal() {
        TwitterVisitor visitor = getFreshVisitor();

        JOptionPane.showMessageDialog(this, "Total users: " + visitor.getUserTotal());
    }

    private void showGroupTotal() {
        TwitterVisitor visitor = getFreshVisitor();

        JOptionPane.showMessageDialog(this, "Total groups: " + visitor.getGroupTotal());
    }

    private void showMessageTotal() {
        TwitterVisitor visitor = getFreshVisitor();

        JOptionPane.showMessageDialog(this, "Total messages: " + visitor.getMessageTotal());
    }

    private void showPositivePercent() {
        TwitterVisitor visitor = getFreshVisitor();

        JOptionPane.showMessageDialog(this,
                String.format("Positive percentage: %.2f%%", visitor.getPositivePercentage()));
    }
}