package gr.softeng.team16.data;

public interface AuthCallback {
    void onSuccess(String result);
    void onFailure(String message);
}
