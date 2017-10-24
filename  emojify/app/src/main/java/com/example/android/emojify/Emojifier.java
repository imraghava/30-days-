package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by shreya on 3/9/17.
 */

public class Emojifier {



    private static final String LOG_TAG=Emojifier.class.getSimpleName();
    private static final float EMOJI_SCALE_FACTOR = .9f;

    private enum Emoji{
        smile,
        frown,
        rightwinkfrown,
        rightwink,
        leftwinkfrown,
        leftwink,
        closed_smile,
        closed_frown
    }

    private static final double  LEFT_EYE__THRESHOLD = 0.48584923;
    private static final double  RIGHT_EYE__THRESHOLD = 0.48584923;
    private static final double  SMILING_THRESHOLD = 0.33531132;
    private static boolean smiling;
    private static boolean left_eye_closed;
    private static boolean right_eye_closed;

    public static Bitmap detectFacesAndOverlayEmoji(Context context, Bitmap bitmap){
        FaceDetector faceDetector;
        faceDetector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        if(!faceDetector.isOperational()){
            new AlertDialog.Builder(context).setMessage("Face detector not operational").show();
            return bitmap;
        }
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> face = faceDetector.detect(frame);

        Bitmap resultBitmap = bitmap;

        if(face.size() ==0){
            String msg = "No faces detected";
            Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else {

            Log.d(LOG_TAG, "NO of faces detected:" + face.size());

            for (int a = 0; a < face.size(); a++) {
                Face thisFace = face.valueAt(a);
                Emoji emoji = whichEmoji(thisFace);
                Bitmap emojiBitmap;
                switch (emoji) {
                    case smile:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.smile);
                        break;
                    case frown:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.frown);
                        break;
                    case rightwink:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightwink);
                        break;
                    case leftwink:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftwink);
                        break;
                    case rightwinkfrown:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightwinkfrown);
                        break;
                    case leftwinkfrown:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftwinkfrown);
                        break;
                    case closed_frown:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_frown);
                        break;
                    case closed_smile:
                        emojiBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed_smile);
                        break;
                    default:
                        emojiBitmap = null;
                        Toast.makeText(context, R.string.no_emoji, Toast.LENGTH_SHORT).show();
                }

                resultBitmap = addBitmapToFace(resultBitmap, emojiBitmap, thisFace);

            }
        }

        faceDetector.release();


        return resultBitmap;
    }



    public static Emoji whichEmoji(Face thisFace) {
        if (thisFace.getIsSmilingProbability()>SMILING_THRESHOLD){
            smiling = true;
        }else{
            smiling = false;
        }
        if(thisFace.getIsLeftEyeOpenProbability()<LEFT_EYE__THRESHOLD ){
            left_eye_closed = true;
        }else {
            left_eye_closed = false;
        }
        if(thisFace.getIsRightEyeOpenProbability() < RIGHT_EYE__THRESHOLD){
            right_eye_closed = true;
        }else {
            right_eye_closed = false;
        }
        Log.d(LOG_TAG,String.valueOf(thisFace.getIsLeftEyeOpenProbability()));
        Log.d(LOG_TAG,String.valueOf(thisFace.getIsRightEyeOpenProbability()));
        Log.d(LOG_TAG,String.valueOf(thisFace.getIsSmilingProbability()));
        Log.d(LOG_TAG,String.valueOf(smiling));
        Log.d(LOG_TAG,String.valueOf(left_eye_closed));
        Log.d(LOG_TAG,String.valueOf(right_eye_closed));
        Emoji emoji = Emoji.smile;

        if(smiling){
            if(!left_eye_closed && !right_eye_closed){
                emoji = Emoji.smile;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (!left_eye_closed && right_eye_closed){
                emoji = Emoji.rightwink;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (left_eye_closed && !right_eye_closed){
                emoji = Emoji.leftwink;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (left_eye_closed && right_eye_closed){
                emoji = Emoji.closed_smile;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }

        }else {
            if(!left_eye_closed && !right_eye_closed){
                emoji = Emoji.frown;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (!left_eye_closed && right_eye_closed){
                emoji = Emoji.rightwinkfrown;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (left_eye_closed && !right_eye_closed){
                emoji = Emoji.leftwinkfrown;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }else if (left_eye_closed && right_eye_closed){
                emoji = Emoji.closed_frown;
                Log.d(LOG_TAG,"Emoji:"+emoji.name());
            }

        }

        return emoji;

    }

    private static Bitmap addBitmapToFace(Bitmap backgroudBitmap, Bitmap emojiBitmap, Face thisFace){
        Bitmap resultBitmap = Bitmap.createBitmap(backgroudBitmap.getWidth(),backgroudBitmap.getHeight(),backgroudBitmap.getConfig());

        float scaleFactor = EMOJI_SCALE_FACTOR;

        int newEmojiWidth = (int) (thisFace.getWidth() * scaleFactor);
        int newEmojiHeight = (int) (emojiBitmap.getHeight() *
                newEmojiWidth / emojiBitmap.getWidth() * scaleFactor);

        emojiBitmap = emojiBitmap.createScaledBitmap(emojiBitmap,newEmojiWidth,newEmojiHeight,false);

        float emojiX = (thisFace.getPosition().x+thisFace.getWidth())/2 - (emojiBitmap.getWidth()/2);
        float emojiY = (thisFace.getPosition().y + thisFace.getHeight())/2 - (emojiBitmap.getHeight()/(float)(3));

        Canvas canvas
                = new Canvas(resultBitmap);

        canvas.drawBitmap(backgroudBitmap,0,0,null);
        canvas.drawBitmap(emojiBitmap,emojiX,emojiY,null);

        return resultBitmap;
    }


}
