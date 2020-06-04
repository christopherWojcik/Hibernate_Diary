import diary.MessageGenerator;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.wojcik.entity.Post;
import pl.wojcik.entity.User;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Data
public class Diary {

    private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static MessageGenerator messages = new MessageGenerator();
    private static boolean isSomebodyLoggedIn = false;
    private static User loggedUser = null;
    public static Scanner scanner = new Scanner(System.in);

    {
        init();
    }

    public Diary() throws NoSuchAlgorithmException {
    }

    public void init() throws NoSuchAlgorithmException {
        messages.initMessage();
        messages.terminalMessage();
        terminalInterface();
    }

    private void terminalInterface() throws NoSuchAlgorithmException {
        System.out.println("Please enter Your choice:");
        String input = scanner.nextLine();
        while (true) {
            if (input.equals("1") && !isSomebodyLoggedIn) {
                logIn();
                break;
            } else if (input.equals("1")) {
                messages.twoUsersError();
                this.terminalInterface();
                break;
            } else if (input.equals("2")) {
                createUser();
                break;
            } else if (input.equals("0")) {
                messages.exitMessage();
                break;
            } else {
                messages.wrongInitInput();
                this.terminalInterface();
            }
        }
    }

    private void postInterface() throws NoSuchAlgorithmException {
        System.out.println("Please enter Your choice:");
        String input = scanner.nextLine();
        while (true) {
            if (input.equals("1")) {
                addPost();
                break;
            } else if (input.equals("2")) {
                readPost();
                break;
            } else if (input.equals("3")) {
                editPost();
                break;
            } else if (input.equals("4")) {
                deletePost();
                break;
            } else if (input.equals("0")) {
                logOut(loggedUser.getLogin());
                break;
            } else {
                messages.wrongPostInput();
                this.postInterface();
            }
        }
    }

    private void logOut(String login) throws NoSuchAlgorithmException {
        isSomebodyLoggedIn = false;
        loggedUser = null;
        messages.afterLogOut(login);
        terminalInterface();
    }

    private void deletePost() throws NoSuchAlgorithmException {
        messages.beforeDelete();
        loggedUserUpdate();

        loggedUser.getPosts().forEach(post -> System.out.println("Post id: " + post.getId() + " post title: " + post.getTitle()));

        messages.sayEnterPostId();
        String input = scanner.nextLine();
        boolean flag = false;

        if (!input.equals("0")) {
            long inputFromUser = Long.parseLong(input);

            List<Long> currentPostIdList = new LinkedList<>();
            loggedUser.getPosts().forEach(post -> currentPostIdList.add(post.getId()));

            for (Long aLong : currentPostIdList) {
                if (aLong == inputFromUser) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                deleteOnePost(inputFromUser);
                messages.afterDelete(input);
            } else {
                messages.wrongPostIdOrInvalidInputFormat();
            }
        }
        postInterface();
    }

    private void deleteOnePost(long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Post WHERE id = :id")
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    private void editPost() throws NoSuchAlgorithmException {
        messages.beforeEdit();
        loggedUserUpdate();

        loggedUser.getPosts().forEach(post -> System.out.println("Post id: " + post.getId() + " post title: " + post.getTitle()));

        messages.sayEnterPostId();
        String input = scanner.nextLine();
        boolean flag = false;

        if (!input.equals("0")) {
            long inputFromUser = Long.parseLong(input);

            List<Long> currentPostIdList = new LinkedList<>();
            loggedUser.getPosts().forEach(post -> currentPostIdList.add(post.getId()));

            for (Long aLong : currentPostIdList) {
                if (aLong == inputFromUser) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                editOnePost(inputFromUser);
            } else {
                messages.wrongPostIdOrInvalidInputFormat();
            }
        }
        postInterface();
    }

    private void editOnePost(long inputFromUser) throws NoSuchAlgorithmException {
        messages.askIfWantEditTitle();
        String titleDecisionInput = scanner.nextLine();

        boolean flag = false;

        if (titleDecisionInput.equals("y") || titleDecisionInput.equals("Y")) {
            messages.sayEditTitle();
            String newTitle = scanner.nextLine();
            flag = true;
            updateTitlePost(newTitle, inputFromUser);
        }
        messages.askIfWantEditContent();
        String contentDecision = scanner.nextLine();
        if (contentDecision.equals("y") || contentDecision.equals("Y")) {
            flag = true;
            messages.sayEditContent();
            String newContent = scanner.nextLine();
            updateContentPost(newContent, inputFromUser);
        }
        if (flag) {
            messages.afterUpdate();
        }
    }

    private void updateContentPost(String newContent, long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Post SET content = :title WHERE id = :id")
                .setParameter("title", newContent)
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    private void updateTitlePost(String newTitle, Long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Post SET title = :title WHERE id = :id")
                .setParameter("title", newTitle)
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    private void readPost() throws NoSuchAlgorithmException {
        messages.beforeReadPost();
        loggedUserUpdate();

        loggedUser.getPosts().forEach(post -> System.out.println("Post id:" + post.getId() + ", post title: " + post.getTitle()));

        messages.sayEnterPostId();
        String input = scanner.nextLine();
        boolean flag = false;

        if (!input.equals("0")) {
            long inputFromUser = Long.parseLong(input);

            List<Long> currentPostIdList = new LinkedList<>();
            loggedUser.getPosts().forEach(post -> currentPostIdList.add(post.getId()));

            for (Long aLong : currentPostIdList) {
                if (inputFromUser == aLong) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                printChosenPost(inputFromUser);
            } else {
                messages.wrongPostIdOrInvalidInputFormat();
            }
            messages.afterReadPost();
        }
        postInterface();
    }

    private void loggedUserUpdate() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        loggedUser = session.createQuery("FROM User AS u WHERE u.id = :id", User.class)
                .setParameter("id", Diary.loggedUser.getId())
                .getSingleResult();
        transaction.commit();
        session.close();
    }

    private void printChosenPost(long inputFromUser) throws NoSuchAlgorithmException {
        messages.sayThisIsTheContentOfChosenPost(inputFromUser);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("FROM Post AS p WHERE (p.id = :id)", Post.class)
                .setParameter("id", inputFromUser)
                .getResultList()
                .stream()
                .filter(post -> post.getUser().getId().equals(loggedUser.getId()))
                .forEach(post -> System.out.println(post.getContent()));
        transaction.commit();
        session.close();
    }

    private void addPost() throws NoSuchAlgorithmException {
        messages.sayCreatePostTitle();
        String title = scanner.nextLine();
        messages.sayCreatePostContent();
        String content = scanner.nextLine();

        addPostToDatabase(title, content);
        postInterface();
    }

    private void addPostToDatabase(String title, String content) {
        Post postToDatabase = new Post(title, content, loggedUser);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(postToDatabase);
        transaction.commit();
        session.close();
        messages.afterCreatePost();
    }

    private void logIn() throws NoSuchAlgorithmException {
        messages.sayEnterLogin();
        String login = scanner.nextLine();
        messages.sayEnterPassword();
        String password = scanner.nextLine();

        if (checkCredentials(login, password)) {
            messages.afterLogin(login);
            messages.postInitMessage();
            postInterface();
        } else {
            messages.wrongCredentials();
            terminalInterface();
        }
    }

    private void createUser() throws NoSuchAlgorithmException {
        messages.sayCreateLogin();
        String login = scanner.nextLine();
        messages.sayCreatePassword();
        String password = scanner.nextLine();

        if (isLoginInDatabase(login)) {
            messages.duplicateLogin(login);
            terminalInterface();
        } else {
            addUserToDatabase(login, password);
            messages.afterUserCreate();
            messages.postInitMessage();
            postInterface();
        }
    }

    private boolean isLoginInDatabase(String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<User> loginList = session.createQuery("FROM User AS u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getResultList();

        transaction.commit();
        session.close();

        return !loginList.isEmpty();
    }

    private boolean checkCredentials(String login, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> userFromDB = session.createQuery("FROM User AS u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getResultList();
        transaction.commit();
        session.close();
        if (userFromDB.isEmpty()) {
            return false;
        } else {
            if ((userFromDB.get(0).getLogin().equals(login) && userFromDB.get(0).getPassword().equals(password))) {
                loggedUser = userFromDB.get(0);
                isSomebodyLoggedIn = true;
            }
        }
        return (userFromDB.get(0).getLogin().equals(login) && userFromDB.get(0).getPassword().equals(password));
    }

    private void addUserToDatabase(String login, String password) throws NoSuchAlgorithmException {
        User userToDatabase = new User(login, password);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(userToDatabase);
        transaction.commit();
        session.close();
        loggedUser = userToDatabase;
    }
}
