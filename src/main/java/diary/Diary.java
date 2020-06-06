package diary;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.wojcik.entity.Post;
import pl.wojcik.entity.User;
import service.PasswordService;
import service.SHA512_PasswordService;
import utils.MyHibernateUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Data
public class Diary {

    private PasswordService passwordService;
    private SessionFactory sessionFactory;
    private MessageGenerator messages;
    private Scanner scanner;
    private User loggedUser;
    private boolean isSomebodyLoggedIn;

    public Diary() {
        this.sessionFactory = new MyHibernateUtils().getSessionFactory();
        this.messages = new MessageGeneratorImpl();
        this.isSomebodyLoggedIn = false;
        this.loggedUser = null;
        this.scanner = new Scanner(System.in);
        this.passwordService = new SHA512_PasswordService();
        init();
    }

    // checked - OK
    private void init() {
        messages.initMessage();
        messages.terminalMessage();
        terminalInterface();
    }

    // checked - OK
    private void addPost() {
        messages.sayCreatePostTitle();
        String title = scanner.nextLine();
        messages.sayCreatePostContent();
        String content = scanner.nextLine();
        addPostToDatabase(title, content);
        postInterface();
    }

    // checked - OK
    private void addPostToDatabase(String title, String content) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(new Post(title, content, loggedUser));

        transaction.commit();
        session.close();

        messages.afterCreatePost();
    }

    // checked - OK
    private void addUserToDatabase(String login, String password) {
        User userToDatabase = new User(login, passwordService.hashPassword(password));
        addOneUserToDatabase(userToDatabase);
        loggedUser = userToDatabase;
        isSomebodyLoggedIn = true;
    }

    // checked - OK
    private void addOneUserToDatabase(User userToDatabase) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(userToDatabase);

        transaction.commit();
        session.close();
    }

    // checked - OK
    private boolean checkCredentials(String login, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User userFromDB = session.createQuery("FROM User AS u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();

        transaction.commit();
        session.close();

        if (userFromDB == null) {
            return false;
        } else {
            if ((userFromDB.getLogin().equals(login) && Arrays.equals(userFromDB.getPassword(), passwordService.hashPassword(password)))) {
                loggedUser = userFromDB;
                isSomebodyLoggedIn = true;
            }
            return (userFromDB.getLogin().equals(login) && Arrays.equals(userFromDB.getPassword(), passwordService.hashPassword(password)));
        }
    }

    // checked - OK
    private void createUser() {
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

    // checked - OK
    private void deletePost() {
        messages.beforeDelete();

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
                loggedUserUpdate();
                messages.afterDelete(input);
            } else {
                messages.wrongPostIdOrInvalidInputFormat();
            }
        }
        postInterface();
    }

    // checked - OK
    private void deleteOnePost(long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Post WHERE id = :id")
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    // checked - OK
    private void editPost() {
        messages.beforeEdit();

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
                loggedUserUpdate();
            } else {
                messages.wrongPostIdOrInvalidInputFormat();
            }
        }
        postInterface();
    }

    // checked - OK
    private void editOnePost(long inputFromUser) {
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

    // checked - OK
    private boolean isLoginInDatabase(String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User isValidUser = session.createQuery("FROM User AS u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();

        transaction.commit();
        session.close();

        return isValidUser != null;
    }

    // checked - OK
    private void logIn() {
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

    // checked - OK
    private void logOut(String login) {
        isSomebodyLoggedIn = false;
        loggedUser = null;
        messages.afterLogOut(login);
        terminalInterface();
    }

    // checked - OK
    private void loggedUserUpdate() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        loggedUser = session.createQuery("FROM User AS u WHERE u.id = :id", User.class)
                .setParameter("id", loggedUser.getId())
                .getSingleResult();
        transaction.commit();
        session.close();
    }

    // checked - OK
    private void postInterface() {
        messages.sayPleaseEnterYourChoice();
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

    // checked - OK
    private void printChosenPost(long inputFromUser) {
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

    // checked - OK
    private void readPost() {
        messages.beforeReadPost();

        loggedUserUpdate();

        loggedUser.getPosts()
                .forEach(post -> System.out.println("Post id:" + post.getId() + ", post title: " + post.getTitle()));

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

    // checked - OK
    private void terminalInterface() {
        messages.sayPleaseEnterYourChoice();
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

    // checked - OK
    private void updateContentPost(String newContent, long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Post SET content = :title WHERE id = :id")
                .setParameter("title", newContent)
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
        loggedUserUpdate();
    }

    // checked - OK
    private void updateTitlePost(String newTitle, Long inputFromUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Post SET title = :title WHERE id = :id")
                .setParameter("title", newTitle)
                .setParameter("id", inputFromUser)
                .executeUpdate();

        transaction.commit();
        session.close();
        loggedUserUpdate();
    }

}
