package qsh.com.animalantiepidemic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import qsh.com.animalantiepidemic.contentprovider.LocalUserCursorLoader;
import qsh.com.animalantiepidemic.localstate.DataHolder;
import qsh.com.animalantiepidemic.models.UserComparator;
import qsh.com.animalantiepidemic.models.UserModel;
import qsh.com.animalantiepidemic.persistent.FarmerDbHelper;
import qsh.com.animalantiepidemic.persistent.UserDbHelper;
import qsh.com.animalantiepidemic.security.DesHelper;


/**
 * Android login screen Activity
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {
//public class LoginActivity extends Activity {

    //private static final String DUMMY_CREDENTIALS = "13565459883:iloveyou@1014";

    private UserLoginTask userLoginTask = null;
    private View loginFormView;
    private View progressView;
    private AutoCompleteTextView usernameTextView;
    private EditText passwordTextView;
    private TextView signUpTextView;
    private UserModel validateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadDataToSqlite();

        usernameTextView = (AutoCompleteTextView) findViewById(R.id.username);
        passwordTextView = (EditText) findViewById(R.id.password);

        usernameTextView.setFocusable(true);
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                Log.d("debug:", "Editor Action, id = " + Integer.toString(id));
                if (id == EditorInfo.IME_NULL) {
                    initLogin();
                    return true;
                }
                return false;
            }
        });

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

        //adding underline and link to signup textview
        signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Linkify.addLinks(signUpTextView, Linkify.ALL);

        signUpTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Sign Up Activity activated.");

                Toast toast = Toast.makeText(getBaseContext(), "系统提示", Toast.LENGTH_SHORT);
                toast.setText("暂未开放注册");
                toast.setGravity(Gravity.CENTER, 0, 0);
                //other setters
                toast.show();

                // this is where you should start the signup Activity
                // LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void loadDataToSqlite() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {
        if (userLoginTask != null) {
            return;
        }

        usernameTextView.setError(null);
        passwordTextView.setError(null);

        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordTextView.setError(getString(R.string.invalid_password));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(username)) {
            usernameTextView.setError(getString(R.string.field_required));
            focusView = usernameTextView;
            cancelLogin = true;
        } else if (!TextUtils.isDigitsOnly(username)) {
            usernameTextView.setError(getString(R.string.invalid_username));
            focusView = usernameTextView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            showProgress(true);
            userLoginTask = new UserLoginTask(username, password);
            userLoginTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //通过自定义的CusorLoader,实现从本地文件中加载用户数据到Cursor中。
        return new LocalUserCursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> phonese = new ArrayList<String>();
        List<UserModel> userModels = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            phonese.add(cursor.getString(cursor.getColumnIndex("name")));

            UserModel tempUser = UserModel.parseUser(cursor);
            userModels.add(tempUser);
            //Log.d("debug", "MatrixCursor中加载的用户数据：" + tempUser.getId().toString() + "," + tempUser.getName() + "," + tempUser.getFullName() + "," + tempUser.getAddress() + "," + tempUser.getPassword());
            cursor.moveToNext();
        }
        Log.d("debug", "phoneNumbers length = " + phonese.size());
        addPhoneNumbersToAutoComplete(phonese);

        FarmerDbHelper farmerDbHelper = new FarmerDbHelper(this);
        SQLiteDatabase farmerDb = farmerDbHelper.getReadableDatabase();
        farmerDbHelper.onCreate(farmerDb);
        farmerDb.close();

        UserDbHelper userDbHelper = new UserDbHelper(this);
        SQLiteDatabase userDb = userDbHelper.getReadableDatabase();
        userDbHelper.onCreate(userDb);
        userDb.close();

//        UserDbHelper userDbHelper = new UserDbHelper(this);
        //默认按id从小到大排序
        Log.i("database", "按id从小到大排序用户数据");
        Collections.sort(userModels, new UserComparator());

        Log.i("database", "找一下库中有没有JSON文件中最大ID的记录");
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        //找一下库中有没有JSON文件中最大ID的记录
        Cursor queryCursor = db.query(UserModel.TABLE_NAME, UserModel.COLUMN_NAMES,
                "id = ?",
                new String[] { userModels.get(userModels.size() - 1).getId().toString() },
                null, null, null);
        Integer recordCount = queryCursor.getCount();
        db.close();

        //如果有，则别同步了
        if (recordCount > 0) {
            Log.i("database", "如果有，则别同步了");
            Log.i("database", "关闭数据库连接");
        } else {
            Log.i("database", "如果没有JSON文件中最大ID的记录");
            Log.i("database", "开始同步用户数据到数据库中");
            db = userDbHelper.getWritableDatabase();
            try {
                //db.beginTransaction();
                for(UserModel user : userModels){
                    //String sql = "insert into " + UserModel.TABLE_NAME + " (" + UserModel.DATABASE_COLUMN_NAMES + ") values (" + user.getId() + ", '" + user.getName() + "', '" + user.getFullName() + "', '" + user.getAddress() + "', '" + user.getPassword() + "')";
                    //Log.d("database", "INSERT SQL:" + sql);
                    //db.execSQL(sql);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", user.getId());
                    contentValues.put("name", user.getName());
                    contentValues.put("fullName", user.getFullName());
                    contentValues.put("address", user.getAddress());
                    contentValues.put("password", user.getPassword());
                    db.insert(UserModel.TABLE_NAME, null, contentValues);
                }
                //db.endTransaction();
                Log.i("database", "完成同步用户数据到数据库，关闭数据库连接");
                db.close();
            } catch (Exception e) {
                Log.d("debug", "同步数据库失败，错误信息:" + e.getMessage());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addPhoneNumbersToAutoComplete(List<String> phoneNumberCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, phoneNumberCollection);
        usernameTextView.setAdapter(adapter);
    }

    /**
     * Async Login Task to authenticate
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String usernameStr;
        private final String passwordStr;

        UserLoginTask(String username, String password) {
            usernameStr = username;
            passwordStr = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            UserDbHelper userDbHelper = new UserDbHelper(getBaseContext());
            SQLiteDatabase db = userDbHelper.getReadableDatabase();
            //调试一下看库中是否有数据
            //Cursor testCursor = db.query(UserModel.TABLE_NAME, UserModel.COLUMN_NAMES,
            //        null, null, null, null, null);
            //Log.d("database", "数据库中的记录条数：" + testCursor.getCount());
            //db.close();

            Log.i("login", "查找数据库中是否有用户名为'" + usernameStr + "'的记录");
            db = userDbHelper.getReadableDatabase();
            Cursor cursor = db.query(UserModel.TABLE_NAME, UserModel.COLUMN_NAMES,
                    "name = ?",
                    new String[] {usernameStr},
                    null, null, null);
            Integer recordCount = cursor.getCount();
            if (recordCount > 0) {
                cursor.moveToFirst();
                validateUser = UserModel.parseUser(cursor);
                Log.i("login", "如果有，先获取记录并关联数据库连接");
                db.close();

                Log.i("login", "再判断密码是否正确");
                String databasePassword = validateUser.getPassword();
                Log.i("login", "库中用户密码：" + databasePassword);
                String encryptedPassword = DesHelper.encrypt(passwordStr, getResources().getString(R.string.security_key));
                Log.i("login", "加密过后的用户输入密码：" + encryptedPassword);
                if(databasePassword.trim().equals(encryptedPassword.trim())) {
                    Log.i("login", "1.密码正确,返回true");
                    return true;
                } else {
                    Log.i("login", "2.密码错误,返回false");
                    return false;
                }
            } else {
                Log.i("login", "3.如果没有，则关联数据库连接");
                db.close();
                Log.i("login", "4.密码错误,返回false");
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userLoginTask = null;
            //stop the progress spinner
            showProgress(false);

            if (success) {
                //  login success and move to main Activity here.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);

                DataHolder.setCurrentUser(validateUser);
            } else {
                // login failure
                passwordTextView.setError(getString(R.string.incorrect_password));
                passwordTextView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            userLoginTask = null;
            showProgress(false);
        }
    }
}