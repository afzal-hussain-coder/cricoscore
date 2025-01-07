package com.cricoscore.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;

import com.cricoscore.R;
import com.cricoscore.model.BattingStyleModel;
import com.cricoscore.model.ShortAreaSubCategoryModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Global {


    //public static String BASE_URL = "";
   // public static String BASE_URL2 = "http://192.168.1.32:5000";
   public static String BASE_URL = "http://cricosocre.selectronicindia.com/";

    //"http://cricosocre.selectronicindia.com/"; Main Url


    /*API REQUEST END POINTS*/
    public static final String SIGNUP = "singup";
    public static final String VERIFY_PHONE_OTP = "user/verifyPhoneOTP";
    public static final String VERIFY_OTP = "verifyOTP";

    public static final String LOGIN = "login";
    public static final String USER_PROFILE = "user/myProfile";
    public static final String LOGIN_THROUGH_OTP = "loginWithMobile";
    public static final String FORGET_PASSWORD = "forgotpass";
    public static final String RESET_PASSWORD = "user/reSetPassword";
    public static final String VERIFY_EMAIL_OTP = "user/verifyEmailOTP";
    public static final String USER_SUBMIT_PROFILE = "user/submitProfile";
    public static final String ADDTOURNAMENTURL = "tournament/add";
    public static final String ALL_TOURNAMENT = "tournament/all";
    public static final String GET_MY_TOURNAMENT = "tournament/getMyTournament/1";
    public static final String GET_TOURNAMENT_DETAILS = "/tournament/getDetails";

    public static final String ADD_TEAM = "team/add";
    public static final String GET_MYTEAM = "team/getMyTeam";
    public static final String ADD_TEAM_IN_TOURNAMENT = "tournament/addTeamsInTournameant";

    public static final String GET_TEAM_DETAILS = "team/getTeamDetails";
    public static final String ADD_PLAYER = "player/add";
    public static final String MY_PLAYER_LIST = "player/myAllPlayers";
    public static final String SCHEDULE_MATCH_LIST = "schedule/myScheduleMatchList";


    public static final String ADD_PLAYER_INTO_TEAM ="team/addPlayerInTeam";
    public static final String CREATE_SCHEDULE ="schedule/new";
    public static final String UPDATE_SCHEDULE ="schedule/update";


    public static final String REMOVE_PLAYER_FROM_TEAM ="team/removePlayersFromTeam";
    public static final String UPDATE_SELECTED_TEAM_PLAYER ="schedule/updateSelectedTeamPlayer";
    public static final String TOSS_API = "schedule/updateSeduleMatchTossInfo";

    public static final String INNING_NEW ="inning/new";
    public static final String INNING_UPDATE ="inning/update";
    public static final String INNING_BALL ="inning/ball";
    public static final String UPDATE_SCHEDULE_INNING ="schedule/updateSeduleInning";

    public static final String GET_STATE ="tournament/getStates";
    public static final String GET_CITY ="tournament/getCities";

    public static final String GET_LEADER_BOARD ="player/getLeasderBoard";






    private static LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();

    public static void showLoader(FragmentManager supportFragmentManager) {
        if (!loadingDialogFragment.isAdded()) {
            loadingDialogFragment.show(supportFragmentManager, "show");
        }
    }

    public static void hideLoder() {
        if (loadingDialogFragment.isAdded()) {
            loadingDialogFragment.dismissAllowingStateLoss();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    return true;
                }
            }
        }
        return false;
    }


    public static void showDialog(final Activity ac) {
        android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(ac);
        alertbox.setTitle(ac.getResources().getString(R.string.app_name));
        alertbox.setMessage(R.string.no_internet);
        alertbox.setPositiveButton(ac.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //arg0.cancel();
                        ac.finish();
                    }
                });
        if (!ac.isFinishing()) {
            //show dialog
            alertbox.show();
        }


    }

    //Sorry, your username must be between 6 and 30 characters long.

    public static boolean isPasswordValidMethod(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isValidPhoneNumber(String testString) {
        //testString.length() >= 8 &&
        return (testString.length() == 10 && android.util.Patterns.PHONE.matcher(testString).matches());
    }

    public static boolean isMatchPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        return false;
    }

    public static String capitalizeFirstLatterOfString(String name) {
        if (name == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(name);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }

    }

    public static void Sleep(int sec) {
        try {
            //set time in mili
            Thread.sleep(sec * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // navigating user to app settings
    private static void openSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }

    public static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static Bitmap CheckRotationImage(String selectedImage) {

        String picturePath = selectedImage;
        Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);

        ExifInterface exif = null;
        try {
            File pictureFile = new File(picturePath);
            exif = new ExifInterface(pictureFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = ExifInterface.ORIENTATION_NORMAL;

        if (exif != null)
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                loadedBitmap = rotateBitmap(loadedBitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                loadedBitmap = rotateBitmap(loadedBitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                loadedBitmap = rotateBitmap(loadedBitmap, 270);
                break;
        }
        return loadedBitmap;
    }

    public static Bitmap rotateImage(Bitmap bmp, String imageUrl) {
        if (bmp != null) {
            ExifInterface ei;
            int orientation = 0;
            try {
                ei = new ExifInterface(imageUrl);
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

            int bmpWidth = bmp.getWidth();
            int bmpHeight = bmp.getHeight();
            Matrix matrix = new Matrix();

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;

                default:
                    break;
            }

            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);

            return resizedBitmap;

        } else {
            return bmp;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static String returnDate(String dateToConvert) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd, hh:mm a");
        //2022 05 10, 01:53 PM
        String temp = dateToConvert;
        try {
            date = formatter.parse(temp);
            Log.e("formated date ", date + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formateDateMonthName = new SimpleDateFormat("dd MMM yyyy").format(date);
        Log.v("output_date ", formateDateMonthName);
        String formateTime = new SimpleDateFormat("hh:mm a").format(date);
        Log.v("output_time ", formateDateMonthName + "--" + formateTime);
        String finalDateTime = formateDateMonthName;

        return formateDateMonthName + " , " + formateTime;
    }

    public static String validateDateFormat(String dateToValdate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy, HH:mm");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateToValdate);

        } catch (ParseException e) {
            //Handle exception
        }
        System.out.println("++validated DATE TIME ++" + formatter.format(parsedDate));
        String formateDateMonthName = new SimpleDateFormat("EEEE , dd MMM yyyy").format(parsedDate);
        // Log.v("output_date ", formateDateMonthName);
        String formateTime = new SimpleDateFormat("hh:mm a").format(parsedDate);
        // Log.v("output_time ", formateDateMonthName + "," + formateTime);
        String finalDateTime = formateDateMonthName + " , " + formateTime;
        return finalDateTime;
    }

    public static String validateDateFormatt(String dateToValdate) {
        //2022-04-16
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateToValdate);

        } catch (ParseException e) {
            //Handle exception
        }
        //System.out.println("++validated DATE TIME ++" + formatter.format(parsedDate));
        String formateDateMonthName = new SimpleDateFormat("EEEE , dd MMM yyyy").format(parsedDate);
//        // Log.v("output_date ", formateDateMonthName);
//        String formateTime = new SimpleDateFormat("hh:mm a").format(parsedDate);
//        // Log.v("output_time ", formateDateMonthName + "," + formateTime);
//        String finalDateTime = formateDateMonthName + " , " + formateTime;
        return formateDateMonthName;
    }


    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 0];
    }

    public static void dismissDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();
    }

    public static Calendar convertStringToCalendar(String time) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateformat.parse(time);
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            return calendar;
        }
    }

    public static String getDateGot(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getDateGotCoach(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getDateGotCoachh(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getDateGott(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getMonth(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("MM").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getYear(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static String getDay(String dateTime) {
        Date date = null;
        String realdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        try {
            date = formatter.parse(dateTime);
            realdate = new SimpleDateFormat("dd").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return realdate;
    }

    public static int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static String convertUTCDateToLocalDate(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }

        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd");
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMMM yyyy");

           /* Date date = new SimpleDateFormat("yyyy-M-d").parse(date_string);
            String dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(newFormatter);

            Log.d("day",dayOfWeek);*/
            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date", dueDateAsNormal);

        return dueDateAsNormal;
    }

    public static String convertUTCDateToLocalDatee(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }
        SimpleDateFormat oldFormatter = new SimpleDateFormat("dd MMM yyyy,hh:mm:ss");
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy, h:mm a");
            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }

    public static String convertUTCDateToLocal(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }
        //02 Jun 2021,01:20:00
        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-mm-dd");
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy");

           /* Date date = new SimpleDateFormat("yyyy-M-d").parse(date_string);
            String dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(newFormatter);

            Log.d("day",dayOfWeek);*/
            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date", dueDateAsNormal);

        return dueDateAsNormal;
    }

    public static String convertUTCDateToLocall(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }
        //02 Jun 2021,01:20:00
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dueDateAsNormal = "";
        try {
            SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
            Date d = dateFormatprev.parse(date_string);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            dueDateAsNormal = dateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date", dueDateAsNormal);

        return dueDateAsNormal;
    }

    public static String convertUTCDateToMM(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }
        //02 Jun 2021,01:20:00
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dueDateAsNormal = "";
        try {
            SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
            Date d = dateFormatprev.parse(date_string);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            dueDateAsNormal = dateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date", dueDateAsNormal);

        return dueDateAsNormal;
    }

    public static void printKeyHash(Activity context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.pb.criconet", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
        }
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static String getCurrentDatee() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    public static String getCurrentDateAcademyPanel() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static String capitizeString(String name) {
        String captilizedString = "";
        try {

            if (!name.trim().equals("")) {
                captilizedString = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return captilizedString;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd MMM yyyy", cal).toString();
        return date;
    }

    public static String convertSecondsToHMmSs(long seconds) {

        long s = seconds % 60;

        long m = (seconds / 60) % 60;

        long h = (seconds / (60 * 60)) % 24;

        return String.format("%d:%02d:%02d", h, m, s);

    }

    @SuppressLint("DefaultLocale")
    public static String convertSecondsTomSs(long seconds) {

        long s = seconds % 60;

        long m = (seconds / 60) % 60;

        //long h = (seconds / (60 * 60)) % 24;

        return String.format("%02d:%02d", m, s);

    }

    //2021-06-28 16:00:00
    public static String getCurrentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String getURLForResource(int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    //Todo MidWicket List
    public static ArrayList<ShortAreaSubCategoryModel> getMidWicketSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.flick), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.pull), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.lofted_shot), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.slog_sweep), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.inside_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.helicopter), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));

        return midWicketList;
    }

    //Todo LongOn List
    public static ArrayList<ShortAreaSubCategoryModel> getLongOnSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.straight_drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.on_drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.lofted_shot), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.helicopter), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));

        return midWicketList;
    }

    //Todo LongOFF List
    public static ArrayList<ShortAreaSubCategoryModel> getLongOffSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.straight_drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.off_drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.lofted_shot), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.helicopter), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));

        return midWicketList;
    }

    //Todo DeepCover List
    public static ArrayList<ShortAreaSubCategoryModel> getDeepCoverSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.back_foot_punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.inside_out), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.switch_hit), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));

        return midWicketList;
    }

    //Todo DeepPoint List
    public static ArrayList<ShortAreaSubCategoryModel> getDeepPointSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.late_cut), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.back_foot_punch), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.cut_shot), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.square_drive), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.switch_hit), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.upper_cut), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.reverse_sweep), R.drawable.batting));


        return midWicketList;
    }

    //Todo ThirdMan List
    public static ArrayList<ShortAreaSubCategoryModel> getThirdManSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.late_cut), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.outside_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.top_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.reverse_scoop), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.upper_cut), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.reverse_sweep), R.drawable.batting));


        return midWicketList;
    }

    //Todo DeepFineLeg List
    public static ArrayList<ShortAreaSubCategoryModel> getDeepFineLegSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.leg_glance), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.inside_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.top_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.pull), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.hook), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.dilscoop_ramp_shot), R.drawable.batting));


        return midWicketList;
    }

    //Todo DeepSquareLeg List
    public static ArrayList<ShortAreaSubCategoryModel> getDeepSquareLegSubShortArea(Context mContext) {
        ArrayList<ShortAreaSubCategoryModel> midWicketList = new ArrayList<>();
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.flick), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.inside_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.top_edge), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.defence), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.sweep), R.drawable.batting));
        midWicketList.add(new ShortAreaSubCategoryModel(mContext.getResources().getString(R.string.dilscoop_ramp_shot), R.drawable.batting));


        return midWicketList;
    }

    //Todo BowlingStyle List
    public static ArrayList<String> getBowlingStyleList(Context mContext) {
        ArrayList<String> midWicketList = new ArrayList<>();
        midWicketList.add(mContext.getResources().getString(R.string.right_arm_fast));
        midWicketList.add(mContext.getResources().getString(R.string.right_arm_midium));
        midWicketList.add(mContext.getResources().getString(R.string.left_arm_fast));
        midWicketList.add(mContext.getResources().getString(R.string.left_arm_midium));
        midWicketList.add(mContext.getResources().getString(R.string.slow_left_arm_orthodox));
        midWicketList.add(mContext.getResources().getString(R.string.slow_leftc_arm_chinaman));
        midWicketList.add(mContext.getResources().getString(R.string.right_arm_off_break));
        midWicketList.add(mContext.getResources().getString(R.string.right_arm_leg_break));
        return midWicketList;
    }

    //Todo BattingStyle List
    public static ArrayList<BattingStyleModel> getBattingStyleList(Context mContext) {
        ArrayList<BattingStyleModel> midWicketList = new ArrayList<>();
        midWicketList.add(new BattingStyleModel(mContext.getResources().getString(R.string.left_hand_bat), R.drawable.batting));
        midWicketList.add(new BattingStyleModel(mContext.getResources().getString(R.string.right_hand_bat), R.drawable.batting));
        return midWicketList;
    }

}
