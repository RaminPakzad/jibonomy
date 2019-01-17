package sadad.com.jibonomy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import sadad.com.jibonomy.entities.Wish;
import sadad.com.jibonomy.services.WishService;
import sadad.com.jibonomy.utils.ImageSaver;
import sadad.com.jibonomy.utils.NavigationUtil;

import static android.app.Activity.RESULT_OK;

public class WishFragment extends android.support.v4.app.Fragment {
    private View rootView;
    private WishService wishService;
    private static final int PICK_IMAGE = 1900;
    private EditText wishTitle;
    private EditText wishDescription;
    private EditText neededBudget;
    private Button choosePic;
    private Button saveWish;
    private ImageView wishImage;
    private Bitmap bitmapImage = null;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.wish_fragment, container, false);
            wishService = new WishService(rootView.getContext());
            wishTitle = rootView.findViewById(R.id.wishTitle);
            wishDescription = rootView.findViewById(R.id.wishDescription);
            neededBudget = rootView.findViewById(R.id.neededBudget);
            choosePic = rootView.findViewById(R.id.choosePic);
            saveWish = rootView.findViewById(R.id.saveWish);
            wishImage = rootView.findViewById(R.id.wishImage);



            if (getArguments() != null) {
                Long wishId = getArguments().getLong(Wish.WISH_ID_LABEL);
                Wish wish = wishService.getWish(wishId);
                wishTitle.setText(wish.getName());
                wishDescription.setText(wish.getDescription());
                neededBudget.setText(wish.getBudget().toString());
                Bitmap image = new ImageSaver(rootView.getContext())
                        .setFileName(wish.getPicName())
                        .setExternal(false)//image save in external directory or app folder default value is false
                        .setDirectory("jibonomy")
                        .load();

                wishImage.setImageBitmap(image);


            }


            choosePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            });


            saveWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Wish wish = new Wish();
                    wish.setName(wishTitle.getText().toString());
                    wish.setDescription(wishDescription.getText().toString());
                    wish.setBudget(new BigDecimal(neededBudget.getText().toString()));
                    Integer rand = new Random().nextInt(100);
                    wish.setPicName(wish.getName() + rand.toString() + ".jpg");


                    ////////////////////////////////////////////////
                    new ImageSaver(rootView.getContext())
                            .setFileName(wish.getPicName())
                            .setExternal(false)//image save in external directory or app folder default value is false
                            .setDirectory("jibonomy")
                            .save(bitmapImage);
                    ////////////////////////////////////////////////

                    wishService.insert(wish);
                    NavigationUtil.changeFragment(new WishListFragment(),rootView);
                }
            });

            return rootView;
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri returnUri = data.getData();
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(rootView.getContext().getContentResolver(), returnUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmapImage != null) {
                    wishImage.setImageBitmap(bitmapImage);
                }
            }
        }

}
