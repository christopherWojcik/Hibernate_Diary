package diary;

public class MessageGeneratorImpl implements MessageGenerator{

    public void initMessage() {
        System.out.println("+=================================================================================+");
        System.out.println("|  --------         Hello, this is simple application - diary.Diary.    --------  |");
        System.out.println("+=================================================================================+");
        System.out.println("|          This App has been made by Krzysztof Wójcik during SDA course.          |");
        System.out.println("|    Stack used in this project: Hibernate, MySQL, Java 11, design patterns :)    |");
        System.out.println("+=================================================================================+");
    }

    public void sayEnterLogin() {
        System.out.println("Please enter your login:");
    }

    public void sayEnterPassword() {
        System.out.println("Please enter your password:");
    }

    public void sayCreateLogin() {
        System.out.println("Please enter your user login:");
    }

    public void sayCreatePassword() {
        System.out.println("Please enter your user password:");
    }

    public void sayCreatePostTitle() {
        System.out.println("Please enter title of a new Post:");
    }

    public void sayCreatePostContent() {
        System.out.println("Please enter content of a new Post:");
    }

    public void sayThisIsTheContentOfChosenPost(long inputFromUser) {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|  This is the content of chosen post with id: " + inputFromUser + "               ");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void sayEnterPostId() {
        System.out.println("Please enter post id:");
    }

    public void sayEditTitle() {
        System.out.println("Please enter new title for edited post:");
    }

    public void sayEditContent() {
        System.out.println("Please enter new content for edited post:");
    }

    public void askIfWantEditTitle() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                 Do You want to edit the post title? (y/n)                       |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void askIfWantEditContent() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                    Do You want to edit the post content?                        |");
        System.out.println("|               For YES enter (y) for NO enter something else :)                  |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterLogin(String login) {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("                       Hello " + login.toUpperCase() + " !");
        System.out.println("|                      You have successfully log in :)                            |");
        System.out.println("+---------------------------------------------------------------------------------+");

    }

    public void afterLogOut(String login) {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("                         Good Bye " + login.toUpperCase() + " !");
        System.out.println("|                      You have been successfully log out :)                      |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|          What can You do:  1 -> log in    2 -> create User     0 -> exit        |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterCreatePost() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                       New Post has been created!!!                              |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterReadPost() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterUpdate() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                       Some data has been updated!                               |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterDelete(String input) {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     Post of id: " + input + " has been successfully deleted!!                    ");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void afterUserCreate() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                          New User has been created!!!                           |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void beforeReadPost() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|  List of posts titles is below. Choose one, enter post id to read the content.  |");
        System.out.println("|  You can back to previous menu entering \"0\"                                     |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void beforeEdit() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|  List of posts titles is below. Choose one, enter post id to edit the post.     |");
        System.out.println("|  You can back to previous menu entering \"0\"                                     |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void beforeDelete() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|  List of posts titles is below. Choose one, enter post id to delete the post.   |");
        System.out.println("|  You can back to previous menu entering \"0\"                                     |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void duplicateLogin(String login) {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     Login duplicate error!!! User of login: \"" + login + "\" is already in database!!");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> Log in (Only for previously created users).                            |");
        System.out.println("|     2 -> create new User()                                                      |");
        System.out.println("|     0 -> exit the App                                                           |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void exitMessage() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                           You have exit the App.                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                          Thank's, and see You soon!                             |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void postInitMessage() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|   If You want to add diary entry, please enter \"1\".                             |");
        System.out.println("|   If You want to read diary entry, please enter \"2\".                            |");
        System.out.println("|   If You want to edit your diary entry, please enter \"3\".                       |");
        System.out.println("|   If You want to delete diary entry, please enter \"4\".                          |");
        System.out.println("|   If You want to log out, please enter \"0\".                                     |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void terminalMessage() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|   If You want to Log in, please enter \"1\".                                      |");
        System.out.println("|   If You want to create new User(), please enter \"2\".                           |");
        System.out.println("|   If You want to exit the App, please enter \"0\".                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void twoUsersError() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                       Someone is already logged in!!!                           |");
        System.out.println("|                           It's impossible ...                                   |");
        System.out.println("|                          You must log out ...                                   |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void wrongCredentials() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|                             Wrong credentials!!!                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     1 -> Log in (Only for previously created users).                            |");
        System.out.println("|     2 -> create new User()                                                      |");
        System.out.println("|     0 -> exit the App                                                           |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void wrongInitInput() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     You have choose wrong option. Check Once Again!.                            |");
        System.out.println("|     1 -> Log in (Only for previously created users).                            |");
        System.out.println("|     2 -> create new User()                                                      |");
        System.out.println("|     0 -> exit the App                                                           |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void wrongPostInput() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     You have choose wrong option. Check Once Again!!!                           |");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

    public void wrongPostIdOrInvalidInputFormat() {
        System.out.println("+---------------------------------------------------------------------------------+");
        System.out.println("|     Wrong post id or invalid format of your input!!!                            |");
        System.out.println("|     1 -> add Post   2 -> read Post   3 -> edit Post   4 - > delete Post         |");
        System.out.println("|     0 -> log out                                                                |");
        System.out.println("+---------------------------------------------------------------------------------+");
    }

}
