package sadad.com.jibonomy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    Button loginButton;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.into_slider_04,R.drawable.into_slider_01,R.drawable.into_slider_02,R.drawable.into_slider_03};

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        userService = new UserService();

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
                editor.apply();
                Intent intent = new Intent(loginActivity, MainActivity.class);
                loginActivity.startActivity( intent );
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
