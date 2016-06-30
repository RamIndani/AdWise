package com.learninghorizon.adwise.home.coupon;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.offers.OffersDataUtil;
import com.learninghorizon.adwise.home.spot.Spot;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ramnivasindani on 4/24/16.
 */
public class CouponsFragment extends Fragment {
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle saveInstanceState){
        super.onCreateView(layoutInflater,container, saveInstanceState);
        View view = layoutInflater.inflate(R.layout.coupon_fragment, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.qrcode);
        TextView couponDescriptionTextView = (TextView) view.findViewById(R.id.coupon_description);
        final TextView couponTimerTextView = (TextView) view.findViewById(R.id.coupon_timer);
        Spot currentSpot = OffersDataUtil.getInstance().getCurrentSpot();
        if(null != currentSpot && !currentSpot.getActiveCoupons().isEmpty()) {
            String couponCode = currentSpot.getActiveCoupons().get(0).getCode();
            String couponDescription = currentSpot.getActiveCoupons().get(0).getDescription();
            long couponStartTime = currentSpot.getActiveCoupons().get(0).getCouponActiveTime();
            int couponDuration = currentSpot.getActiveCoupons().get(0).getDuration();
            int discount = currentSpot.getActiveCoupons().get(0).getDiscount();
            long couponTimeElapsed =  new Date().getTime() - couponStartTime;
            long couponTimeRemaining = TimeUnit.HOURS.toMillis(couponDuration) - couponTimeElapsed;
            countDownTimer = new CountDownTimer(couponTimeRemaining, 1000) {

                public void onTick(long millisUntilFinished) {
                    couponTimerTextView.setText((millisUntilFinished /(1000*60*60)) % 24 +" : " + (millisUntilFinished / (1000*60)) % 60 +" : "+(millisUntilFinished / (1000) % 60));
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    couponTimerTextView.setText("Coupon Expired!");
                }

            };
            countDownTimer.start();
            couponDescriptionTextView.setText(couponDescription);
            try {
                WindowManager manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;
                Bitmap bitmap = encodeAsBitmap(couponCode, smallerDimension);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else{
            couponDescriptionTextView.setText(getString(R.string.no_coupon));
            couponTimerTextView.setText("");
        }



        return view;
    }

    Bitmap encodeAsBitmap(String str, int width) throws WriterException {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, width, width, null);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK: Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(null != countDownTimer){
            countDownTimer.cancel();
        }
    }
}
