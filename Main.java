import java.util.Scanner;

class UserNode {
    User user;
    UserNode next;

    public UserNode(User user) {
        this.user = user;
        this.next = null;
    }
}

class User {
    int userId;
    String password;
    Post postHead;

    public User(int userId, String password) {
        this.userId = userId;
        this.password = password;
        this.postHead = null;
    }

    public void addPost(String caption) {
        Post newPost = new Post(caption);
        if (postHead == null) {
            postHead = newPost;
        } else {
            Post temp = postHead;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newPost;
        }
    }

    public boolean deletePost(int postIndex) {
        if (postIndex == 1 && postHead != null) {
            postHead = postHead.next;
            return true;
        }

        Post temp = postHead;
        int currentIndex = 1;

        while (temp != null && temp.next != null) {
            if (currentIndex == postIndex - 1) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
            currentIndex++;
        }

        return false;
    }
}

class Post {
    String caption;
    int likes;
    int[] likedBy;
    int likedCount;
    Comment commentHead;
    Post next;

    public Post(String caption) {
        this.caption = caption;
        this.likes = 0;
        this.likedBy = new int[100];
        this.likedCount = 0;
        this.commentHead = null;
        this.next = null;
    }

    public boolean like(int userId) {
        for (int i = 0; i < likedCount; i++) {
            if (likedBy[i] == userId) {
                return false;
            }
        }
        if (likedCount < likedBy.length) {
            likedBy[likedCount++] = userId;
            likes++;
            return true;
        }
        return false;
    }

    public boolean removeLike(int userId) {
        for (int i = 0; i < likedCount; i++) {
            if (likedBy[i] == userId) {
                for (int j = i; j < likedCount - 1; j++) {
                    likedBy[j] = likedBy[j + 1];
                }
                likedBy[--likedCount] = 0;
                likes--;
                return true;
            }
        }
        return false;
    }

    public void addComment(String text, int userId) {
        Comment newComment = new Comment(text, userId);
        if (commentHead == null) {
            commentHead = newComment;
        } else {
            Comment temp = commentHead;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newComment;
        }
    }

    public boolean deleteComment(int userId) {
        if (commentHead == null) return false;

        if (commentHead.userId == userId) {
            commentHead = commentHead.next;
            return true;
        }

        Comment temp = commentHead;
        while (temp.next != null) {
            if (temp.next.userId == userId) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
        }

        return false;
    }
}

class Comment {
    String text;
    int userId;
    Comment next;

    public Comment(String text, int userId) {
        this.text = text;
        this.userId = userId;
        this.next = null;
    }
}

class UserHashTable {
    private UserNode[] table;
    private int size;
    private int elementCount;

    public UserHashTable(int initialSize) {
        this.size = initialSize;
        this.table = new UserNode[size];
        this.elementCount = 0;
    }

    private int hash(int userId) {
        return Math.abs(userId) % size;
    }

    public boolean addUser(int userId, String password) {
        if (getUser(userId) != null) return false;
        if ((double) elementCount / size > 0.7) resizeTable();

        int index = hash(userId);
        User newUser = new User(userId, password);
        UserNode newNode = new UserNode(newUser);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            UserNode current = table[index];
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        elementCount++;
        return true;
    }

    public User getUser(int userId) {
        int index = hash(userId);
        UserNode current = table[index];
        while (current != null) {
            if (current.user.userId == userId) return current.user;
            current = current.next;
        }
        return null;
    }

    public void displayAllFeeds() {
        System.out.println("\n===== All Feeds =====\n");
        for (UserNode node : table) {
            while (node != null) {
                System.out.println("User ID: " + node.user.userId);
                Post temp = node.user.postHead;
                int postNumber = 1;
                while (temp != null) {
                    System.out.println("  " + postNumber + ". " + temp.caption + " (Likes: " + temp.likes + ")");
                    if (temp.commentHead != null) {
                        System.out.println("    Comments:");
                        Comment commentTemp = temp.commentHead;
                        while (commentTemp != null) {
                            System.out.println("      - [User ID: " + commentTemp.userId + "] " + commentTemp.text);
                            commentTemp = commentTemp.next;
                        }
                    }
                    temp = temp.next;
                    postNumber++;
                }
                node = node.next;
            }
        }
    }

    private void resizeTable() {
        int newSize = size * 2;
        UserNode[] newTable = new UserNode[newSize];

        for (int i = 0; i < size; i++) {
            UserNode current = table[i];
            while (current != null) {
                int newIndex = hash(current.user.userId);
                UserNode newNode = new UserNode(current.user);
                if (newTable[newIndex] == null) {
                    newTable[newIndex] = newNode;
                } else {
                    UserNode temp = newTable[newIndex];
                    while (temp.next != null) temp = temp.next;
                    temp.next = newNode;
                }
                current = current.next;
            }
        }

        table = newTable;
        size = newSize;
    }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserHashTable userHashTable = new UserHashTable(5);
        User loggedInUser = null;

        while (true) {
            if (loggedInUser == null) {
                loggedInUser = loginMenu(userHashTable);
            } else {
                loggedInUser = userMenu(loggedInUser, userHashTable);
            }
        }
    }

    private static User loginMenu(UserHashTable userHashTable) {
        System.out.println("\n1. Login\n2. Create Account\n3. Exit");
        int choice = scanner.nextInt();
        return (choice == 1) ? loginUser(userHashTable) : (choice == 2) ? createAccount(userHashTable) : null;
    }

    private static User loginUser(UserHashTable userHashTable) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        User user = userHashTable.getUser(userId);
        if (user != null) {
            System.out.print("Enter Password: ");
            scanner.nextLine();
            return scanner.nextLine().equals(user.password) ? user : null;
        }
        return null;
    }

    private static User createAccount(UserHashTable userHashTable) {
        System.out.print("Enter New User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Password: ");
        scanner.nextLine();
        return userHashTable.addUser(userId, scanner.nextLine()) ? null : null;
    }
}
