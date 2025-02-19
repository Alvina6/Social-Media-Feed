📌 Social Media Feed Simulation
A simple social media feed system implemented in Java, utilizing hash tables for user management and linked lists for post and comment handling.

🚀 Features
✅ User Management: Create an account, login with user ID and password.
✅ Post System: Add and delete posts.
✅ Like System: Like and unlike posts.
✅ Comment System: Add and delete comments.
✅ Feed Display: View all user posts along with comments and likes.
✅ Dynamic Hash Table: Resizes dynamically when load factor exceeds 0.7.

🛠 Tech Stack
Java (Core Java, Data Structures)
Hash Table (for user storage)
Linked List (for posts and comments)

📦 Social-Media-Feed
 ┣ 📜 Main.java           # Entry point of the application
 ┣ 📜 UserHashTable.java  # Hash table implementation for users
 ┣ 📜 User.java           # User class with post management
 ┣ 📜 Post.java           # Post class with like/comment system
 ┣ 📜 Comment.java        # Comment handling
 ┗ 📜 README.md           # Project documentation

📌 Usage
Run the program and select:
1 to Login
2 to Create Account
3 to Exit
If logged in:
Add posts, like/unlike, comment, and delete posts/comments.
View all users' feeds with comments and likes.

📝 To-Do (Future Improvements)
🔹 Encrypt passwords instead of storing them as plain text.
🔹 Improve the UI with a graphical interface (Swing/JavaFX).
🔹 Implement a database (MySQL or SQLite) for persistence.

