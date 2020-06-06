package diary;

public interface MessageGenerator {

    void initMessage();

    void sayEnterLogin();

    void sayEnterPassword();

    void sayCreateLogin();

    void sayCreatePassword();

    void sayCreatePostTitle();

    void sayCreatePostContent();

    void sayThisIsTheContentOfChosenPost(long inputFromUser);

    void sayEnterPostId();

    void sayEditTitle();

    void sayEditContent();

    void sayPleaseEnterYourChoice();

    void askIfWantEditTitle();

    void askIfWantEditContent();

    void afterLogin(String login);

    void afterLogOut(String login);

    void afterCreatePost();

    void afterReadPost();

    void afterUpdate();

    void afterDelete(String input);

    void afterUserCreate();

    void beforeReadPost();

    void beforeEdit();

    void beforeDelete();

    void duplicateLogin(String login);

    void exitMessage();

    void postInitMessage();

    void terminalMessage();

    void twoUsersError();

    void wrongCredentials();

    void wrongInitInput();

    void wrongPostInput();

    void wrongPostIdOrInvalidInputFormat();

}
