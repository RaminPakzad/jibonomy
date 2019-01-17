package sadad.com.jibonomy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import sadad.com.jibonomy.biz.dto.UserDto;
import sadad.com.jibonomy.services.UserService;
import sadad.com.jibonomy.utils.NotifyUtil;

public class LoginActivity extends AppCompatActivity {

    LoginActivity loginActivity;
    Button loginButton, ssoLoginButton;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.into_slider_04,R.drawable.into_slider_01,R.drawable.into_slider_02,R.drawable.into_slider_03};

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        userService = new UserService(getBaseContext());


        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if(data!=null) {
            Log.d("DATA", "-> " + data.getQuery());
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putString("token", data.getQuery());
            Intent intentSsoLogin = new Intent(loginActivity, MainActivity.class);
            loginActivity.startActivity( intentSsoLogin );
        } else {
            Log.d("DATA", "-" );
        }

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDto user = userService.login();
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("username", user.getUsername());
                editor.putString("firstName", user.getFirstName());
                editor.putString("lastName", user.getLastName());
                editor.putString("email", user.getEmail());
                editor.putLong("appBalance", 500000 );
                editor.apply();
                Intent intent = new Intent(loginActivity, MainActivity.class);
                loginActivity.startActivity( intent );
            }
        });


        ssoLoginButton = findViewById(R.id.sso_login_button);
        ssoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pfm.myoxygen.ir/auth/realms/master/protocol/openid-connect/auth?response_type=code&state=&client_id=3ccbab92-4b93-4bf4-82bb-0ccd5c88&client_secret=b5facf85-9428-4702-bf89-64c3b7a5ebad&scope=&redirect_uri=http://192.168.25.135/jibonomy/"));
                startActivity(browserIntent);
            }
        });

        // Sample notification view
        NotifyUtil.viewNotify(getApplicationContext());

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}
