package nadav.tasher.handasaim.teachers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

import nadav.tasher.lightool.Light;

public class Main extends Activity {
    private final String serviceProvider = "http://handasaim.co.il";
    private int color = Color.parseColor("#1b5c96");
    private int secolor = color + 0x333333;
    private String day;
    private Teacher currentClass;
    private int textColor = Color.WHITE;
    private int countheme = 0;
    private Theme[] themes = new Theme[]{new Theme("#000000"), new Theme("#562627"), new Theme("#773272"), new Theme("#9b8c36"), new Theme("#425166"), new Theme("#112233"), new Theme("#325947"), new Theme("#893768"), new Theme("#746764"), new Theme("#553311"), new Theme(color)};
    private String[] ees = new String[]{"Love is like the wind, you can't see it but you can feel it.", "I'm not afraid of death; I just don't want to be there when it happens.", "All you need is love. But a little chocolate now and then doesn't hurt.", "When the power of love overcomes the love of power the world will know peace.", "For every minute you are angry you lose sixty seconds of happiness.", "Yesterday is history, tomorrow is a mystery, today is a gift of God, which is why we call it the present.", "The fool doth think he is wise, but the wise man knows himself to be a fool.", "In three words I can sum up everything I've learned about life: it goes on.", "You only live once, but if you do it right, once is enough.", "Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.", "Life is pleasant. Death is peaceful. It's the transition that's troublesome.", "There are only two ways to live your life. One is as though nothing is a miracle. The other is as though everything is a miracle.", "We are not retreating - we are advancing in another Direction.", "The difference between fiction and reality? Fiction has to make sense.", "The right to swing my fist ends where the other man's nose begins.", "Denial ain't just a river in Egypt.", "Every day I get up and look through the Forbes list of the richest people in America. If I'm not there, I go to work.", "Advice is what we ask for when we already know the answer but wish we didn't", "The nice thing about egotists is that they don't talk about other people.", "Obstacles are those frightful things you see when you take your eyes off your goal.", "You can avoid reality, but you cannot avoid the consequences of avoiding reality.", "You may not be interested in war, but war is interested in you.", "Don't stay in bed, unless you can make money in bed.", "C makes it easy to shoot yourself in the foot; C++ makes it harder, but when you do, it blows away your whole leg.", "I have not failed. I've just found 10,000 ways that won't work.", "Black holes are where God divided by zero.", "The significant problems we face cannot be solved at the same level of thinking we were at when we created them.", "Knowledge speaks, but wisdom listens.", "Sleep is an excellent way of listening to an opera.", "Success usually comes to those who are too busy to be looking for it"};
    private String[] infact = new String[]{"Every year more than 2500 left-handed people are killed from using right-handed products.", "In 1895 Hampshire police handed out the first ever speeding ticket, fining a man for doing 6mph!", "Over 1000 birds a year die from smashing into windows.", "Squirrels forget where they hide about half of their nuts.", "The average person walks the equivalent of twice around the world in a lifetime.", "A company in Taiwan makes dinnerware out of wheat, so you can eat your plate!", "An apple, potato, and onion all taste the same if you eat them with your nose plugged.", "Dying is illegal in the Houses of Parliaments – This has been voted as the most ridiculous law by the British citizens.", "The first alarm clock could only ring at 4am.", "If you leave everything to the last minute… it will only take a minute.", "Every human spent about half an hour as a single cell.", "The Twitter bird actually has a name – Larry.", "Sea otters hold hands when they sleep so they don’t drift away from each other.", "The French language has seventeen different words for ‘surrender’.", "The Titanic was the first ship to use the SOS signal.", "A baby octopus is about the size of a flea when it is born.", "You cannot snore and dream at the same time.", "A toaster uses almost half as much energy as a full-sized oven.", "If you consistently fart for 6 years & 9 months, enough gas is produced to create the energy of an atomic bomb!", "An eagle can kill a young deer and fly away with it.", "Polar bears can eat as many as 86 penguins in a single sitting.", "If Pinokio says “My Nose Will Grow Now”, it would cause a paradox.", "Bananas are curved because they grow towards the sun.", "Human saliva has a boiling point three times that of regular water.", "Cherophobia is the fear of fun.", "When hippos are upset, their sweat turns red.", "Pteronophobia is the fear of being tickled by feathers!", "Banging your head against a wall burns 150 calories an hour."};

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            if (charSequence != null) {
                for (int c = 0; c < charSequence.length(); c++) {
                    boolean charAllowed = false;
                    String allowed = "0123456789ABCDEFabcdef";
                    for (int a = 0; a < allowed.length(); a++) {
                        if (charSequence.charAt(c) == allowed.charAt(a)) {
                            charAllowed = true;
                            break;
                        }
                    }
                    if (!charAllowed) return "";
                }
                return null;
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();
    }

    private void taskDesc() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), bm, secolor);
        setTaskDescription(taskDesc);
    }

    private void splash() {
        final SharedPreferences sp = getSharedPreferences("app", Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        color = sp.getInt("color", color);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        if (!hexColor.contains("D") && !hexColor.contains("E") && !hexColor.contains("F")) {
            secolor = color + 0x333333;
        } else {
            secolor = color;
        }
        final Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
        taskDesc();
        LinearLayout ll = new LinearLayout(this);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(color);
        final ImageView icon = new ImageView(this);
        icon.setImageDrawable(getDrawable(R.drawable.ic_icon));
        int is = (int) (Light.Device.screenX(getApplicationContext()) * 0.8);
        icon.setLayoutParams(new LinearLayout.LayoutParams(is, is));
        ll.addView(icon);
        final ProgressBar pb = new ProgressBar(this);
        pb.setIndeterminate(true);
        ll.addView(pb);
        pb.setVisibility(View.GONE);
        pb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (Light.Device.screenY(getApplicationContext()) * 0.2)));
        final TextView tv = new TextView(getApplicationContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(21);
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
        tv.setTypeface(custom_font);
        String versionin = "v" + Light.Device.getVersionName(getApplicationContext(), getPackageName());
        tv.setText(versionin);
        tv.setLayoutParams(new LinearLayout.LayoutParams(is, (int) (Light.Device.screenY(getApplicationContext()) * 0.2)));
        final Animation flip = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        flip.setDuration(300);
        final ObjectAnimator slideRC = ObjectAnimator.ofFloat(tv, View.TRANSLATION_X, Light.Animations.getSlideRight(getApplicationContext()));
        slideRC.setDuration(500);
        slideRC.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tv.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ObjectAnimator slideR = ObjectAnimator.ofFloat(tv, View.TRANSLATION_X, -Light.Device.screenX(getApplicationContext()), 0);
        slideR.setDuration(500);
        slideR.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tv.startAnimation(flip);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        flip.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        slideRC.start();
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        slideR.start();
        //Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ll.addView(tv);
        setContentView(ll);
    }

    private void welcome(final ArrayList<Teacher> classes, final boolean renew) {
        final SharedPreferences sp = getSharedPreferences("app", Context.MODE_PRIVATE);
        LinearLayout part1 = new LinearLayout(this);
        final LinearLayout part2 = new LinearLayout(this);
        final LinearLayout part3 = new LinearLayout(this);
        part1.setGravity(Gravity.CENTER);
        part2.setGravity(Gravity.CENTER);
        part3.setGravity(Gravity.CENTER);
        part1.setOrientation(LinearLayout.VERTICAL);
        part2.setOrientation(LinearLayout.VERTICAL);
        part3.setOrientation(LinearLayout.VERTICAL);
        part1.setBackgroundColor(color);
        part2.setBackgroundColor(color);
        part3.setBackgroundColor(color);
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
        //part1
        ImageView icon = new ImageView(this);
        final Button setup = new Button(this);
        final TextView welcome = new TextView(this);
        setup.setTypeface(custom_font);
        welcome.setTypeface(custom_font);
        setup.setText(R.string.set);
        setup.setAlpha(0);
        setup.setBackgroundColor(Color.TRANSPARENT);
        setup.setTextSize((float) 30);
        welcome.setAlpha(0);
        welcome.setGravity(Gravity.CENTER);
        welcome.setTextSize((float) 29);
        welcome.setTextColor(Color.WHITE);
        welcome.setText(R.string.wlc);
        icon.setImageDrawable(getDrawable(R.drawable.ic_icon));
        int is = (int) (Light.Device.screenX(getApplicationContext()) * 0.8);
        icon.setLayoutParams(new LinearLayout.LayoutParams(is, (int) (Light.Device.screenY(getApplicationContext()) * 0.7)));
        ObjectAnimator iconSlide = ObjectAnimator.ofFloat(icon, View.TRANSLATION_X, -Light.Device.screenX(getApplicationContext()), 0);
        iconSlide.setDuration(1000);
        iconSlide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator buttonAn = ObjectAnimator.ofFloat(setup, View.ALPHA, Light.Animations.INVISIBLE_TO_VISIBLE);
                buttonAn.setDuration(500);
                buttonAn.start();
                ObjectAnimator welAn = ObjectAnimator.ofFloat(welcome, View.ALPHA, Light.Animations.INVISIBLE_TO_VISIBLE);
                welAn.setDuration(500);
                welAn.start();

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        iconSlide.start();
        part1.addView(welcome);
        part1.addView(icon);
        part1.addView(setup);
        if (!renew) setContentView(part1);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(part2);
            }
        });
        //part2
        TextView selClas = new TextView(this);
        ScrollView clascroll = new ScrollView(this);
        final RadioGroup classs = new RadioGroup(this);
        clascroll.addView(classs);
        Button next = new Button(this);
        next.setTypeface(custom_font);
        selClas.setTypeface(custom_font);
        next.setBackgroundColor(Color.TRANSPARENT);
        next.setTextSize((float) 30);
        selClas.setGravity(Gravity.CENTER);
        selClas.setTextSize((float) 29);
        selClas.setTextColor(Color.WHITE);
        selClas.setText(R.string.scla);
        next.setText(R.string.nxt);
        part2.addView(selClas);
        part2.addView(clascroll);
        part2.addView(next);
        classs.setGravity(Gravity.CENTER);
        clascroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (Light.Device.screenY(getApplicationContext()) * 0.7)));
        for (int c = 0; c < classes.size(); c++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(classes.get(c).mainName);
            rb.setTextSize((float) 30);
            rb.setTypeface(custom_font);
            rb.setGravity(Gravity.CENTER);
            rb.setId(c);
            rb.setLayoutParams(new RadioGroup.LayoutParams(Light.Device.screenX(getApplicationContext()) / 10 * 8, ViewGroup.LayoutParams.WRAP_CONTENT));
            classs.addView(rb);
        }
        classs.check(0);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString("favorite_teacher", classes.get(classs.getCheckedRadioButtonId()).mainName).commit();
                setContentView(part3);
            }
        });
        //part3
        LinearLayout spcl = new LinearLayout(getApplicationContext());
        spcl.setOrientation(LinearLayout.VERTICAL);
        spcl.setGravity(Gravity.CENTER);
        TextView spclSet = new TextView(this);
        Button done = new Button(this);
        done.setTypeface(custom_font);
        spclSet.setTypeface(custom_font);
        done.setBackgroundColor(Color.TRANSPARENT);
        done.setTextSize((float) 30);
        spclSet.setGravity(Gravity.CENTER);
        spclSet.setTextSize((float) 29);
        spclSet.setTextColor(Color.WHITE);
        spclSet.setText(R.string.spclstt);
        done.setText(R.string.dn);
        final Switch showTimes = new Switch(this);
        showTimes.setChecked(sp.getBoolean("show_time", false));
        showTimes.setText(R.string.sct);
        showTimes.setTextSize((float) 30);
        showTimes.setTypeface(custom_font);
        showTimes.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenX(getApplicationContext()) / 10 * 8, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Switch textCo = new Switch(this);
        textCo.setChecked(sp.getBoolean("fontWhite", true));
        textCo.setText(R.string.white);
        textCo.setTextSize((float) 30);
        textCo.setTypeface(custom_font);
        textCo.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenX(getApplicationContext()) / 10 * 8, ViewGroup.LayoutParams.WRAP_CONTENT));
        textCo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    textCo.setText(R.string.white);
                    textCo.setTextColor(Color.WHITE);
                } else {
                    textCo.setText(R.string.black);
                    textCo.setTextColor(Color.BLACK);
                }
            }
        });
        final Switch showBreaks = new Switch(this);
        showBreaks.setChecked(sp.getBoolean("breaks", true));
        showBreaks.setText(R.string.showbrk);
        showBreaks.setTextSize((float) 30);
        showBreaks.setTypeface(custom_font);
        showBreaks.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenX(getApplicationContext()) / 10 * 8, ViewGroup.LayoutParams.WRAP_CONTENT));
        spcl.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenX(getApplicationContext()) / 10 * 9, (int) (Light.Device.screenY(getApplicationContext()) * 0.7)));
        part3.addView(spclSet);
        spcl.addView(showTimes);
        spcl.addView(showBreaks);
        spcl.addView(textCo);
        part3.addView(spcl);
        part3.addView(done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("show_time", showTimes.isChecked()).commit();
                sp.edit().putBoolean("fontWhite", textCo.isChecked()).commit();
                sp.edit().putBoolean("breaks", textCo.isChecked()).commit();
                sp.edit().putInt("last_recorded_version_code", Light.Device.getVersionCode(getApplicationContext(), getPackageName())).commit();
                sp.edit().putBoolean("first", false).commit();
                view(classes);
                //                view(classes);
            }
        });
        if (renew) {
            spclSet.setText(R.string.rnew);
            setContentView(part3);
        }
    }

    private void checkInternet() {
        if (Light.Device.isOnline(getApplicationContext())) {
            new Light.Net.Pinger(5000, new Light.Net.Pinger.OnEnd() {
                @Override
                public void onPing(String s, boolean b) {
                    if (s.equals(serviceProvider) && b) {
                        openApp();
                    } else if (s.equals(serviceProvider) && !b) {
                        //                        popup("Server Error: No Response From Service Provider.");
                        checkInternet();
                    }
                }
            }).execute(serviceProvider);
        } else {
            popup("No Internet Connection.");
        }
    }

    private void startApp() {
        splash();
        checkInternet();
    }

    void popup(String text) {
        AlertDialog.Builder pop = new AlertDialog.Builder(this);
        pop.setCancelable(true);
        pop.setMessage(text);
        pop.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startApp();
            }
        });
        pop.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                startApp();
            }
        });
        pop.show();
    }

    private void showEasterEgg() {
        int which = new Random().nextInt(2);
        if (which == 0) {
            int newrand = new Random().nextInt(infact.length);
            Toast.makeText(getApplicationContext(), infact[newrand], Toast.LENGTH_LONG).show();
        } else {
            int newrand = new Random().nextInt(ees.length);
            Toast.makeText(getApplicationContext(), "\"" + ees[newrand] + "\"", Toast.LENGTH_LONG).show();
        }
    }

    private void view(final ArrayList<Teacher> classes) {
        final SharedPreferences sp = getSharedPreferences("app", Context.MODE_PRIVATE);
        getWindow().setStatusBarColor(secolor);
        taskDesc();
        final LinearLayout sall = new LinearLayout(this);
        final LinearLayout all = new LinearLayout(this);
        final LinearLayout navbarAll = new LinearLayout(this);
        final ImageView nutIcon = new ImageView(this);
        final ImageView newsIcon = new ImageView(this);
        final int screenY = Light.Device.screenY(this);
        final int nutSize = (screenY / 8) - screenY / 30;
        final int newsSize = (screenY / 9) - screenY / 30;
        final int navY = screenY / 8;
        final LinearLayout.LayoutParams nutParms = new LinearLayout.LayoutParams(nutSize, nutSize);
        final LinearLayout.LayoutParams newsParms = new LinearLayout.LayoutParams(newsSize, newsSize);
        final LinearLayout.LayoutParams navParms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, navY);
        all.setOrientation(LinearLayout.VERTICAL);
        all.setGravity(Gravity.START | Gravity.CENTER_HORIZONTAL);
        all.setBackgroundColor(color);
        sall.setOrientation(LinearLayout.VERTICAL);
        sall.setGravity(Gravity.START | Gravity.CENTER_HORIZONTAL);
        sall.setBackgroundColor(color);
        navbarAll.setBackgroundColor(secolor);
        navbarAll.setOrientation(LinearLayout.HORIZONTAL);
        navbarAll.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        newsIcon.setLayoutParams(newsParms);
        newsIcon.setImageDrawable(getDrawable(R.drawable.ic_news));
        nutIcon.setLayoutParams(nutParms);
        nutIcon.setImageDrawable(getDrawable(R.drawable.ic_icon));
        nutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(Main.this);
                ab.setTitle(R.string.app_name);
                ab.setMessage("This Is The Teacher Edition Of The Handasaim+ App.\nThis App Was Made By NadavTasher\nVersion: " + Light.Device.getVersionName(getApplicationContext(), getPackageName()) + "\nBuild: " + Light.Device.getVersionCode(getApplicationContext(), getPackageName()));
                ab.setCancelable(true);
                ab.setPositiveButton("Close", null);
                ab.show();
            }
        });
        nutIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showEasterEgg();
                return true;
            }
        });
        navbarAll.addView(nutIcon);
        navbarAll.addView(newsIcon);
        navbarAll.setPadding(10, 10, 10, 10);
        navParms.gravity = Gravity.START;
        navbarAll.setLayoutParams(navParms);
        sall.addView(navbarAll);
        LinearLayout navSliderview = new LinearLayout(this);
        navSliderview.setGravity(Gravity.START);
        navSliderview.setOrientation(LinearLayout.HORIZONTAL);
        HorizontalScrollView navSliderviewscroll = new HorizontalScrollView(this);
        navSliderviewscroll.addView(navSliderview);
        navbarAll.addView(navSliderviewscroll);
        //
        final LinearLayout timeswitch = new LinearLayout(this);
        timeswitch.setBackground(getDrawable(R.drawable.back));
        timeswitch.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        ImageView clock_ic = new ImageView(getApplicationContext());
        clock_ic.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20));
        clock_ic.setImageDrawable(getDrawable(R.drawable.ic_clock));
        timeswitch.addView(clock_ic);
        timeswitch.setPadding(20, 20, 20, 20);
        timeswitch.setGravity(Gravity.CENTER);
        timeswitch.setOrientation(LinearLayout.HORIZONTAL);
        navSliderview.addView(timeswitch);
        //
        final LinearLayout breakswitch = new LinearLayout(this);
        breakswitch.setBackground(getDrawable(R.drawable.back));
        breakswitch.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        final ImageView breakswitch_ic = new ImageView(getApplicationContext());
        breakswitch_ic.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20));
        breakswitch_ic.setImageDrawable(getDrawable(R.drawable.ic_breaktime));
        breakswitch.addView(breakswitch_ic);
        breakswitch.setPadding(20, 20, 20, 20);
        breakswitch.setGravity(Gravity.CENTER);
        breakswitch.setOrientation(LinearLayout.HORIZONTAL);
        navSliderview.addView(breakswitch);
        //
        final LinearLayout bagswitch = new LinearLayout(this);
        bagswitch.setBackground(getDrawable(R.drawable.back));
        bagswitch.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        final ImageView bagswitch_ic = new ImageView(getApplicationContext());
        bagswitch_ic.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20));
        bagswitch_ic.setImageDrawable(getDrawable(R.drawable.ic_bag));
        bagswitch.addView(bagswitch_ic);
        bagswitch.setPadding(20, 20, 20, 20);
        bagswitch.setGravity(Gravity.CENTER);
        bagswitch.setOrientation(LinearLayout.HORIZONTAL);
        navSliderview.addView(bagswitch);
        //
        final LinearLayout colorText = new LinearLayout(this);
        colorText.setOrientation(LinearLayout.HORIZONTAL);
        colorText.setGravity(Gravity.CENTER);
        colorText.setBackground(getDrawable(R.drawable.back));
        colorText.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        //Switch col = new Switch(this);
        colorText.setPadding(20, 20, 20, 20);
        final ImageButton switchc = new ImageButton(this);
        switchc.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams colorTextp = new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20);
        switchc.setLayoutParams(colorTextp);
        if (sp.getBoolean("fontWhite", true)) {
            switchc.setImageDrawable(getDrawable(R.drawable.ic_white));
            textColor = Color.WHITE;
        } else {
            switchc.setImageDrawable(getDrawable(R.drawable.ic_black));
            textColor = Color.BLACK;
        }
        colorText.addView(switchc);
        navSliderview.addView(colorText);
        //
        final LinearLayout tsw = new LinearLayout(this);
        tsw.setBackground(getDrawable(R.drawable.back));
        tsw.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        final ImageView tsw_ic = new ImageView(getApplicationContext());
        tsw_ic.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20));
        tsw_ic.setImageDrawable(getDrawable(R.drawable.ic_paint));
        tsw.addView(tsw_ic);
        tsw.setPadding(20, 20, 20, 20);
        tsw.setGravity(Gravity.CENTER);
        tsw.setOrientation(LinearLayout.HORIZONTAL);
        navSliderview.addView(tsw);
        //
        LinearLayout fontS = new LinearLayout(this);
        fontS.setOrientation(LinearLayout.HORIZONTAL);
        fontS.setGravity(Gravity.CENTER);
        fontS.setBackground(getDrawable(R.drawable.back));
        fontS.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenX(getApplicationContext()) / 4, Light.Device.screenY(getApplicationContext()) / 12));
        ImageButton minus = new ImageButton(this);
        final TextView size = new TextView(this);
        ImageButton plus = new ImageButton(this);
        size.setTextColor(Color.WHITE);
        size.setText(String.valueOf(sp.getInt("font", 32)));
        plus.setImageDrawable(getDrawable(R.drawable.ic_plus));
        minus.setImageDrawable(getDrawable(R.drawable.ic_minus));
        plus.setBackgroundColor(Color.TRANSPARENT);
        minus.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams buttonp = new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 25, Light.Device.screenY(getApplicationContext()) / 25);
        plus.setLayoutParams(buttonp);
        minus.setLayoutParams(buttonp);
        size.setTextSize((float) 25);
        fontS.addView(minus);
        fontS.addView(size);
        fontS.addView(plus);
        //
        LinearLayout share = new LinearLayout(this);
        share.setOrientation(LinearLayout.HORIZONTAL);
        share.setGravity(Gravity.CENTER);
        ImageButton sr = new ImageButton(this);
        sr.setImageDrawable(getDrawable(R.drawable.ic_share));
        sr.setBackgroundColor(Color.TRANSPARENT);
        share.setBackground(getDrawable(R.drawable.back));
        sr.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 20, Light.Device.screenY(getApplicationContext()) / 20));
        share.addView(sr);
        sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(currentClass.mainName + "\n" + hourSystemForTeacherString(currentClass, sp.getBoolean("show_time", true)));
            }
        });
        share.setLayoutParams(new LinearLayout.LayoutParams(Light.Device.screenY(getApplicationContext()) / 12, Light.Device.screenY(getApplicationContext()) / 12));
        navSliderview.addView(share);
        navSliderview.addView(fontS);
        //
        int selectedClass = 0;
        if (sp.getString("favorite_teacher", null) != null) {
            if (classes != null) {
                for (int fc = 0; fc < classes.size(); fc++) {
                    if (sp.getString("favorite_teacher", "").equals(classes.get(fc).mainName)) {
                        selectedClass = fc;
                        break;
                    }
                }
            } else {
                //                popup("Downloaded Excel File Is Corrupted");
                openApp();
            }
        }
        ScrollView sv = new ScrollView(this);
        sv.addView(all);
        sall.addView(sv);
        final LinearLayout hsplace = new LinearLayout(this);
        hsplace.setGravity(Gravity.CENTER);
        hsplace.setOrientation(LinearLayout.VERTICAL);
        hsplace.setPadding(20, 20, 20, 20);
        all.addView(hsplace);
        if (!sp.getBoolean("show_time", false)) {
            timeswitch.setBackground(getDrawable(R.drawable.back));
        } else {
            timeswitch.setBackground(getDrawable(R.drawable.back_2));
        }
        View.OnClickListener timeONC = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("show_time", !sp.getBoolean("show_time", false)).commit();
                if (!sp.getBoolean("show_time", false)) {
                    timeswitch.setBackground(getDrawable(R.drawable.back));
                } else {
                    timeswitch.setBackground(getDrawable(R.drawable.back_2));
                }
                showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
            }
        };
        clock_ic.setOnClickListener(timeONC);
        timeswitch.setOnClickListener(timeONC);
        if (!sp.getBoolean("bagmake", false)) {
            bagswitch.setBackground(getDrawable(R.drawable.back));
        } else {
            bagswitch.setBackground(getDrawable(R.drawable.back_2));
        }
        View.OnClickListener bagONC = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("bagmake", !sp.getBoolean("bagmake", false)).commit();
                if (!sp.getBoolean("bagmake", false)) {
                    bagswitch.setBackground(getDrawable(R.drawable.back));
                } else {
                    bagswitch.setBackground(getDrawable(R.drawable.back_2));
                }
                showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
            }
        };
        bagswitch_ic.setOnClickListener(bagONC);
        bagswitch.setOnClickListener(bagONC);
        if (!sp.getBoolean("breaks", true)) {
            breakswitch.setBackground(getDrawable(R.drawable.back));
        } else {
            breakswitch.setBackground(getDrawable(R.drawable.back_2));
        }
        View.OnClickListener breakONC = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("breaks", !sp.getBoolean("breaks", true)).commit();
                if (!sp.getBoolean("breaks", true)) {
                    breakswitch.setBackground(getDrawable(R.drawable.back));
                } else {
                    breakswitch.setBackground(getDrawable(R.drawable.back_2));
                }
                showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
            }
        };
        breakswitch.setOnClickListener(breakONC);
        breakswitch_ic.setOnClickListener(breakONC);
        if (sp.getBoolean("fontWhite", true)) {
            colorText.setBackground(getDrawable(R.drawable.back));
        } else {
            colorText.setBackground(getDrawable(R.drawable.back_2));
        }
        View.OnClickListener textCONC = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("fontWhite", !sp.getBoolean("fontWhite", true)).commit();
                if (sp.getBoolean("fontWhite", true)) {
                    colorText.setBackground(getDrawable(R.drawable.back));
                } else {
                    colorText.setBackground(getDrawable(R.drawable.back_2));
                }
                if (sp.getBoolean("fontWhite", true)) {
                    switchc.setImageDrawable(getDrawable(R.drawable.ic_white));
                    textColor = Color.WHITE;
                } else {
                    switchc.setImageDrawable(getDrawable(R.drawable.ic_black));
                    textColor = Color.BLACK;
                }
                showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
            }
        };
        colorText.setOnClickListener(textCONC);
        switchc.setOnClickListener(textCONC);
        View.OnClickListener themeONC = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putInt("color", themes[countheme].color).commit();
                color = themes[countheme].color;
                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                if (!hexColor.contains("D") && !hexColor.contains("E") && !hexColor.contains("F")) {
                    secolor = color + 0x333333;
                } else {
                    secolor = color;
                }
                getWindow().setStatusBarColor(secolor);
                getWindow().setNavigationBarColor(color);
                sall.setBackgroundColor(color);
                all.setBackgroundColor(color);
                taskDesc();
                navbarAll.setBackgroundColor(secolor);
                if (countheme + 1 < themes.length) {
                    countheme++;
                } else {
                    countheme = 0;
                }
                showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
            }
        };
        View.OnLongClickListener themelong = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
                final int fontSize = sp.getInt("font", 32);
                final LinearLayout newsll = new LinearLayout(getApplicationContext());
                final LinearLayout bts = new LinearLayout(getApplicationContext());
                final LinearLayout layerer = new LinearLayout(getApplicationContext());
                newsll.setGravity(Gravity.CENTER);
                newsll.setOrientation(LinearLayout.HORIZONTAL);
                bts.setGravity(Gravity.CENTER);
                bts.setOrientation(LinearLayout.HORIZONTAL);
                layerer.setGravity(Gravity.CENTER);
                layerer.setOrientation(LinearLayout.VERTICAL);
                final EditText colorEditor = new EditText(getApplicationContext());
                colorEditor.setAllCaps(true);
                colorEditor.setFilters(new InputFilter[]{filter});
                TextView hashv = new TextView(getApplicationContext());
                hashv.setText("#");
                hashv.setTextSize((float) fontSize);
                hashv.setTypeface(custom_font);
                colorEditor.setTypeface(custom_font);
                colorEditor.setTextSize((float) fontSize);
                colorEditor.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 6) {
                            layerer.setBackgroundColor(Color.parseColor("#" + charSequence));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                hashv.setTextColor(textColor);
                colorEditor.setTextColor(textColor);
                newsll.addView(hashv);
                newsll.addView(colorEditor);
                layerer.addView(newsll);
                Button save, close;
                save = new Button(getApplicationContext());
                close = new Button(getApplicationContext());
                save.setText(R.string.sv);
                close.setText(R.string.close);
                save.setBackground(getDrawable(R.drawable.button));
                close.setBackground(getDrawable(R.drawable.button));
                final Dialog dialog = new Dialog(Main.this);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (colorEditor.getText().length() == 6) {
                            sp.edit().putInt("color", Color.parseColor("#" + colorEditor.getText().toString())).commit();
                            color = Color.parseColor("#" + colorEditor.getText().toString());
                            String hexColor = String.format("#%06X", (0xFFFFFF & color));
                            if (!hexColor.contains("D") && !hexColor.contains("E") && !hexColor.contains("F")) {
                                secolor = color + 0x333333;
                            } else {
                                secolor = color;
                            }
                            getWindow().setStatusBarColor(secolor);
                            getWindow().setNavigationBarColor(color);
                            sall.setBackgroundColor(color);
                            all.setBackgroundColor(color);
                            navbarAll.setBackgroundColor(secolor);
                            dialog.dismiss();
                            taskDesc();
                            showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
                        } else {
                            Toast.makeText(getApplicationContext(), "Color Has To Include 6 Characters.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bts.addView(close);
                bts.addView(save);
                layerer.addView(bts);
                dialog.setCancelable(true);
                dialog.setContentView(layerer);
                layerer.setPadding(20, 20, 20, 20);
                layerer.setBackgroundColor(color);
                dialog.show();
                return true;
            }
        };
        tsw.setOnClickListener(themeONC);
        tsw_ic.setOnClickListener(themeONC);
        tsw_ic.setOnLongClickListener(themelong);
        tsw.setOnLongClickListener(themelong);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fontSize = sp.getInt("font", 32);
                fontSize++;
                if (fontSize <= 50) {
                    sp.edit().putInt("font", fontSize).commit();
                    showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), fontSize, sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
                    size.setText(String.valueOf(fontSize));
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fontSize = sp.getInt("font", 32);
                fontSize--;
                if (fontSize >= 1) {
                    sp.edit().putInt("font", fontSize).commit();
                    showHS(currentClass, hsplace, classes, sp.getBoolean("show_time", false), fontSize, sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
                    size.setText(String.valueOf(fontSize));
                }
            }
        });
        newsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsIcon.setClickable(false);
                final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
                final int fontSize = sp.getInt("font", 32);
                final LinearLayout newsll = new LinearLayout(getApplicationContext());
                final LinearLayout filln = new LinearLayout(getApplicationContext());
                newsll.setGravity(Gravity.CENTER);
                newsll.setOrientation(LinearLayout.VERTICAL);
                filln.setGravity(Gravity.CENTER);
                filln.setOrientation(LinearLayout.VERTICAL);
                final Dialog dialog = new Dialog(Main.this);
                dialog.setCancelable(true);
                ScrollView news = new ScrollView(getApplicationContext());
                news.addView(newsll);
                filln.addView(news);
                dialog.setContentView(filln);
                final Animation rotating = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotating.setDuration(1000);
                rotating.setRepeatCount(ObjectAnimator.INFINITE);
                rotating.setRepeatMode(ObjectAnimator.RESTART);
                newsIcon.startAnimation(rotating);
                filln.setPadding(20, 20, 20, 20);
                news.setPadding(20, 20, 20, 20);
                filln.setBackgroundColor(color);
                news.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Light.Device.screenY(getApplicationContext()) / 10 * 6));
                new GetNews(serviceProvider, new GetNews.GotNews() {
                    @Override
                    public void onNewsGet(final ArrayList<Link> link) {
                        newsIcon.setClickable(true);
                        for (int i = 0; i < link.size(); i++) {
                            Button cls = new Button(getApplicationContext());
                            cls.setPadding(10, 10, 10, 10);
                            cls.setTextSize((float) fontSize);
                            cls.setGravity(Gravity.CENTER);
                            cls.setText(link.get(i).name);
                            cls.setTextColor(textColor);
                            cls.setEllipsize(TextUtils.TruncateAt.END);
                            cls.setLines(2);
                            //                            cls.setBackgroundColor(Color.TRANSPARENT);
                            cls.setBackground(getDrawable(R.drawable.button));
                            cls.setTypeface(custom_font);
                            cls.setLayoutParams(new LinearLayout.LayoutParams((int) (Light.Device.screenX(getApplicationContext()) * 0.8), (Light.Device.screenY(getApplicationContext()) / 6)));
                            newsll.addView(cls);
                            if (!link.get(i).url.equals("")) {
                                final int finalI = i;
                                cls.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = link.get(finalI).url;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                        Button cl = new Button(getApplicationContext());
                        cl.setText(R.string.cls);
                        cl.setAllCaps(false);
                        cl.setBackground(getDrawable(R.drawable.button));
                        cl.setTextSize((float) 22);
                        cl.setTextColor(textColor);
                        cl.setTypeface(custom_font);
                        cl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        cl.setLayoutParams(new LinearLayout.LayoutParams((int) (Light.Device.screenX(getApplicationContext()) * 0.8) + (Light.Device.screenX(getApplicationContext()) / 20), (Light.Device.screenY(getApplicationContext()) / 10)));
                        filln.addView(cl);
                        rotating.cancel();
                        dialog.show();
                    }

                    @Override
                    public void onFail(ArrayList<Link> e) {
                        newsIcon.setClickable(true);
                    }
                }).execute("");
            }
        });
        if (classes != null)
            showHS(classes.get(selectedClass), hsplace, classes, sp.getBoolean("show_time", true), sp.getInt("font", 32), sp.getBoolean("breaks", true), sp.getBoolean("bagmake", false));
        setContentView(sall);
    }

    private void share(String st) {
        Intent s = new Intent(Intent.ACTION_SEND);
        s.putExtra(Intent.EXTRA_TEXT, st);
        s.setType("text/plain");
        s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(s, "Share With"));
    }

    private void showHS(final Teacher c, final LinearLayout hsplace, final ArrayList<Teacher> classes, final boolean showTime, final int fontSize, final boolean breakTimes, final boolean showOrgCheckBox) {
        currentClass = c;
        hsplace.removeAllViews();
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
        final Button className = new Button(this);
        className.setTextSize((float) fontSize);
        //        className.setBackgroundColor(Color.TRANSPARENT);
        className.setGravity(Gravity.CENTER);
        className.setBackground(getDrawable(R.drawable.back));
        className.setText(c.mainName + " (" + day + ")");
        className.setTextColor(textColor);
        className.setTypeface(custom_font);
        className.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout classesll = new LinearLayout(getApplicationContext());
                classesll.setGravity(Gravity.CENTER);
                classesll.setOrientation(LinearLayout.VERTICAL);
                final Dialog dialog = new Dialog(Main.this);
                dialog.setCancelable(true);
                ScrollView classesllss = new ScrollView(getApplicationContext());
                classesllss.addView(classesll);
                dialog.setContentView(classesllss);
                for (int cs = 0; cs < classes.size(); cs++) {
                    if (classes.get(cs) != c) {
                        Button cls = new Button(getApplicationContext());
                        cls.setTextSize((float) fontSize);
                        cls.setGravity(Gravity.CENTER);
                        cls.setText(classes.get(cs).mainName);
                        cls.setTextColor(textColor);
                        cls.setBackgroundColor(Color.TRANSPARENT);
                        cls.setTypeface(custom_font);
                        cls.setLayoutParams(new LinearLayout.LayoutParams((int) (Light.Device.screenX(getApplicationContext()) * 0.8), (Light.Device.screenY(getApplicationContext()) / 8)));
                        classesll.addView(cls);
                        final int finalCs = cs;
                        cls.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final SharedPreferences sp = getSharedPreferences("app", Context.MODE_PRIVATE);
                                sp.edit().putString("favorite_teacher", classes.get(finalCs).mainName).commit();
                                showHS(classes.get(finalCs), hsplace, classes, showTime, fontSize, breakTimes, showOrgCheckBox);
                                dialog.dismiss();
                            }
                        });
                    }
                }
                dialog.show();
            }
        });
        hsplace.addView(className);
        hsplace.addView(hourSystemForTeacher(c, showTime, fontSize, breakTimes, showOrgCheckBox));
    }

    private LinearLayout hourSystemForTeacher(final Teacher fclass, boolean showTime, int fontSize, boolean breakTimes, boolean showOrgC) {
        LinearLayout all = new LinearLayout(this);
        all.setGravity(Gravity.START | Gravity.CENTER_HORIZONTAL);
        all.setOrientation(LinearLayout.VERTICAL);
        all.setPadding(10, 10, 10, 10);
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "gisha.ttf");
        for (int h = 0; h <=12; h++) {
            ArrayList<String> classesofhour=new ArrayList<>();
            for (int s = 0; s < fclass.teaching.size(); s++) {
                if (getBreak(fclass.teaching.get(s).hour - 1) != -1 && breakTimes) {
                    final Button breakt = new Button(this);
                    breakt.setText("הפסקה, " + getBreak(fclass.teaching.get(s).hour - 1) + " דקות");
                    breakt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    breakt.setTextSize((float) fontSize - 2);
                    breakt.setTextColor(textColor);
                    breakt.setBackground(getDrawable(R.drawable.button));
                    breakt.setPadding(20, 20, 20, 20);
                    breakt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Light.Device.screenY(getApplicationContext()) / 10));
                    breakt.setTypeface(custom_font);
                    breakt.setAllCaps(false);
                    if (fclass.teaching.get(s).lessonName != null && !fclass.teaching.get(s).lessonName.equals("")) {
                        all.addView(breakt);
                    }
                    final int finalS1 = s;
                    breakt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Dialog dialog = new Dialog(Main.this);
                            dialog.setCancelable(true);
                            LinearLayout di = new LinearLayout(getApplicationContext());
                            TextView subjName = new TextView(getApplicationContext());
                            TextView hours = new TextView(getApplicationContext());
                            TextView fullInfo = new TextView(getApplicationContext());
                            subjName.setText(R.string.brkk);
                            //fclass.classes.get(s)
                            hours.setText(getRealEndTimeForHourNumber(fclass.teaching.get(finalS1).hour - 1) + "-" + getRealTimeForHourNumber(fclass.teaching.get(finalS1).hour));
                            String fulltext = getBreak(fclass.teaching.get(finalS1).hour - 1) + " Minutes";
                            fullInfo.setText(fulltext);
                            di.setGravity(Gravity.CENTER);
                            di.setOrientation(LinearLayout.VERTICAL);
                            di.addView(subjName);
                            di.addView(hours);
                            di.addView(fullInfo);
                            di.setBackgroundColor(color);
                            subjName.setTextColor(textColor);
                            hours.setTextColor(textColor);
                            fullInfo.setTextColor(textColor);
                            subjName.setTextSize((float) 30);
                            hours.setTextSize((float) 30);
                            fullInfo.setTextSize((float) 30);
                            subjName.setGravity(Gravity.CENTER);
                            hours.setGravity(Gravity.CENTER);
                            fullInfo.setGravity(Gravity.CENTER);
                            subjName.setTypeface(custom_font, Typeface.BOLD);
                            hours.setTypeface(custom_font);
                            fullInfo.setTypeface(custom_font);
                            Button close = new Button(getApplicationContext());
                            close.setText(R.string.close);
                            close.setTextColor(textColor);
                            close.setTypeface(custom_font);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            close.setAllCaps(false);
                            close.setBackground(getDrawable(R.drawable.button));
                            close.setLayoutParams(new LinearLayout.LayoutParams((int) (Light.Device.screenX(getApplicationContext()) * 0.6) + (Light.Device.screenX(getApplicationContext()) / 20), (Light.Device.screenY(getApplicationContext()) / 10)));
                            close.setTextSize((float) 25);
                            di.addView(close);
                            dialog.setContentView(di);
                            dialog.show();
                        }
                    });
                }
                final LinearLayout fsubj = new LinearLayout(getApplicationContext());
                fsubj.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                fsubj.setGravity(Gravity.CENTER);
                //            fsubj.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                fsubj.setOrientation(LinearLayout.HORIZONTAL);
                Button subj = new Button(this);
                if (showOrgC) {
                    fsubj.setPadding(0, 0, 20, 0);
                    CheckBox che = new CheckBox(getApplicationContext());
                    che.setText(null);
                    che.setButtonDrawable(getDrawable(R.drawable.checkbox_b));
                    //                che.setButtonTintList(new ColorStateList());
                    fsubj.addView(che);
                    fsubj.setBackground(getDrawable(R.drawable.backasbutton));
                    subj.setBackground(getDrawable(R.drawable.button_alpha));
                } else {
                    subj.setBackground(getDrawable(R.drawable.button));

                }
                String before;
                if (showTime) {
                    before = "(" + getRealTimeForHourNumber(fclass.teaching.get(s).hour) + ") " + fclass.teaching.get(s).hour + ". ";
                } else {
                    before = fclass.teaching.get(s).hour + ". ";
                }
                String total = before + fclass.teaching.get(s).className;
                String main = "\u200F" + total;
                subj.setText(main);
                subj.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                subj.setTextSize((float) fontSize - 2);
                subj.setTextColor(textColor);
                subj.setPadding(20, 20, 20, 20);
                subj.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Light.Device.screenY(getApplicationContext()) / 10));
                subj.setTypeface(custom_font);
                subj.setSingleLine(true);
                subj.setEllipsize(TextUtils.TruncateAt.END);
                final int finalS = s;
                subj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(Main.this);
                        dialog.setCancelable(true);
                        LinearLayout di = new LinearLayout(getApplicationContext());
                        TextView subjName = new TextView(getApplicationContext());
                        TextView hours = new TextView(getApplicationContext());
                        TextView fullInfo = new TextView(getApplicationContext());
                        subjName.setText(fclass.teaching.get(finalS).className);
                        //fclass.classes.get(s)
                        hours.setText(getRealTimeForHourNumber(fclass.teaching.get(finalS).hour) + "-" + getRealEndTimeForHourNumber(fclass.teaching.get(finalS).hour));
                        fullInfo.setText(fclass.teaching.get(finalS).className);
                        di.setGravity(Gravity.CENTER);
                        di.setOrientation(LinearLayout.VERTICAL);
                        di.addView(subjName);
                        di.addView(hours);
                        di.addView(fullInfo);
                        di.setBackgroundColor(color);
                        subjName.setTextColor(textColor);
                        hours.setTextColor(textColor);
                        fullInfo.setTextColor(textColor);
                        subjName.setTextSize((float) 30);
                        hours.setTextSize((float) 30);
                        fullInfo.setTextSize((float) 30);
                        subjName.setGravity(Gravity.CENTER);
                        hours.setGravity(Gravity.CENTER);
                        fullInfo.setGravity(Gravity.CENTER);
                        subjName.setTypeface(custom_font, Typeface.BOLD);
                        hours.setTypeface(custom_font);
                        fullInfo.setTypeface(custom_font);
                        Button close = new Button(getApplicationContext());
                        close.setText(R.string.close);
                        close.setTextColor(textColor);
                        close.setTextSize((float) 25);
                        close.setTypeface(custom_font);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        close.setAllCaps(false);
                        close.setBackground(getDrawable(R.drawable.button));
                        close.setLayoutParams(new LinearLayout.LayoutParams((int) (Light.Device.screenX(getApplicationContext()) * 0.6) + (Light.Device.screenX(getApplicationContext()) / 20), (Light.Device.screenY(getApplicationContext()) / 10)));
                        di.addView(close);
                        dialog.setContentView(di);
                        dialog.show();
                    }
                });
                subj.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(getApplicationContext(), getRealTimeForHourNumber(fclass.teaching.get(finalS).hour) + "-" + getRealEndTimeForHourNumber(fclass.teaching.get(finalS).hour), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                if (fclass.teaching.get(s).hour==h) {
                    classesofhour.add(fclass.teaching.get(s).className);
                    fsubj.addView(subj);
                    all.addView(fsubj);
                }
            }
        }
        return all;
    }

    private String hourSystemForTeacherString(Teacher fclass, boolean showTime) {
        String allsubj = "";
        for (int s = 0; s < fclass.teaching.size(); s++) {
            String before;
            if (showTime) {
                before = "(" + getRealTimeForHourNumber(fclass.teaching.get(s).hour) + ") " + fclass.teaching.get(s).hour + ". ";
            } else {
                before = fclass.teaching.get(s).hour + ". ";
            }
            String total = before + fclass.teaching.get(s).className;
            if (fclass.teaching.get(s).lessonName != null && !fclass.teaching.get(s).lessonName.equals("")) {
                allsubj += total + "\n";
            }
        }
        return allsubj;
    }

    private String getRealTimeForHourNumber(int hour) {
        switch (hour) {
            case 0:
                return "07:45";
            case 1:
                return "08:30";
            case 2:
                return "09:15";
            case 3:
                return "10:15";
            case 4:
                return "11:00";
            case 5:
                return "12:10";
            case 6:
                return "12:55";
            case 7:
                return "13:50";
            case 8:
                return "14:35";
            case 9:
                return "15:25";
            case 10:
                return "16:10";
            case 11:
                return "17:00";
            case 12:
                return "17:45";
        }
        return null;
    }

    private String getRealEndTimeForHourNumber(int hour) {
        switch (hour) {
            case 0:
                return "08:30";
            case 1:
                return "09:15";
            case 2:
                return "10:00";
            case 3:
                return "11:00";
            case 4:
                return "11:45";
            case 5:
                return "12:55";
            case 6:
                return "13:40";
            case 7:
                return "14:35";
            case 8:
                return "15:20";
            case 9:
                return "16:10";
            case 10:
                return "16:55";
            case 11:
                return "17:45";
            case 12:
                return "18:30";
        }
        return null;
    }

    private int getBreak(int washour) {
        switch (washour) {
            case 2:
                return 15;
            case 4:
                return 25;
            case 6:
                return 10;
            case 8:
                return 5;
            case 10:
                return 5;
        }
        return -1;
    }

    private void openApp() {
        final SharedPreferences sp = getSharedPreferences("app", Context.MODE_PRIVATE);
        String service = "http://handasaim.co.il/2017/06/13/%D7%9E%D7%A2%D7%A8%D7%9B%D7%AA-%D7%95%D7%A9%D7%99%D7%A0%D7%95%D7%99%D7%99%D7%9D/index.php";
        new GetLink(service, new GetLink.GotLink() {
            @Override
            public void onLinkGet(String link) {
                if (link != null) {
                    //                    Log.i("LINK", link);
                    new FileDownloader(link, new File(getApplicationContext().getFilesDir(), "hs.xls"), new FileDownloader.OnDownload() {
                        @Override
                        public void onFinish(File file, boolean b) {
                            if (b) {
                                Log.i("H+ FT","Download Finished");
                                ArrayList<Teacher> classes = readExcelFile(file);
                                day = readExcelDay(file);
                                if (classes != null) {
                                    //                                    for (int cl = 0; cl < classes.size(); cl++) {
                                    //                                        for (int su = 0; su < classes.get(cl).classes.size(); su++) {
                                    //                                            Log.i(classes.get(cl).name + " " + classes.get(cl).classes.get(su).hour, classes.get(cl).classes.get(su).name);
                                    //                                        }
                                    //                                    }
                                    if (!sp.getBoolean("first", true)) {
                                        if (sp.getInt("last_recorded_version_code", 0) != Light.Device.getVersionCode(getApplicationContext(), getPackageName())) {
                                            welcome(classes, true);
                                        } else {
                                            view(classes);
                                        }
                                    } else {
                                        welcome(classes, false);
                                    }
                                }
                            } else {
                                //                                popup("Failed To Download Excel File");
                                openApp();
                            }
                        }

                        @Override
                        public void onProgressChanged(File file, int i) {
                            //                            Log.i("Downloader","Progress "+i);
                            //                            pb.setIndeterminate(false);
                            //                            pb.setMax(100);
                            //                            pb.setProgress(i);
                        }
                    }).execute();
                } else {
                    popup("Could Not Fetch Link, Please Try Disconnecting From Wi-Fi");
                }
            }

            @Override
            public void onFail(String e) {
                //                popup("Failed");
                openApp();
            }

        }).execute();
    }

    private ArrayList<Teacher> readExcelFile(File f) {
        try {
            ArrayList<Class> classes = new ArrayList<>();
            POIFSFileSystem myFileSystem = new POIFSFileSystem(new FileInputStream(f));
            Workbook myWorkBook = new HSSFWorkbook(myFileSystem);
            Sheet mySheet = myWorkBook.getSheetAt(0);
            int rows = mySheet.getLastRowNum();
            int cols = mySheet.getRow(1).getLastCellNum();
            for (int c = 1; c < cols; c++) {
                ArrayList<Subject> subs = new ArrayList<>();
                for (int r = 2; r < rows; r++) {
                    Row row = mySheet.getRow(r);
                    subs.add(new Subject(r - 2, row.getCell(c).getStringCellValue().split("\\r?\\n")[0], row.getCell(c).getStringCellValue()));
                }
                classes.add(new Class(mySheet.getRow(1).getCell(c).getStringCellValue(), subs));
            }
            return getTeacherSchudleForClasses(classes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readExcelDay(File f) {
        try {
            POIFSFileSystem myFileSystem = new POIFSFileSystem(new FileInputStream(f));
            Workbook myWorkBook = new HSSFWorkbook(myFileSystem);
            Sheet mySheet = myWorkBook.getSheetAt(0);
            return mySheet.getRow(0).getCell(0).getStringCellValue();
        } catch (Exception e) {
            return null;
        }
    }
    private ArrayList<Teacher> getTeacherSchudleForClasses(ArrayList<Class> classes){
        ArrayList<Teacher> allTeachers=new ArrayList<>();
        for(int classof=0;classof<classes.size();classof++) {
            Class c = classes.get(classof);
            ArrayList<Subject> subjs = c.classes;
            for (int subj = 0; subj < subjs.size(); subj++) {
                Subject subject=subjs.get(subj);
                Log.i("Subj",subject.fullName.split("\\r?\\n")[0]);
                String teachOrTcrs=subject.fullName.substring(subject.fullName.indexOf("\n") + 1).trim().split("\\r?\\n")[0];
                ArrayList<String> teacherNames= Light.Stringer.cutOnEvery(teachOrTcrs,",");
                for(int tcrName=0;tcrName<teacherNames.size();tcrName++) {
                    String teacherName = teacherNames.get(tcrName);
                    Teacher foundTeacher=null;
                    for (int teacher = 0; teacher < allTeachers.size(); teacher++) {
                        if(allTeachers.get(teacher).mainName.equals(teacherName)||allTeachers.get(teacher).mainName.contains(teacherName)){
                            foundTeacher=allTeachers.get(teacher);
                            break;
                        }
                    }
                    if(foundTeacher!=null){
                        if(subject.name!=null&&!subject.name.equals(""))
                        foundTeacher.teaching.add(new TeacherClass(c.name,subject.name,subject.hour));
                    }else{
                        foundTeacher=new Teacher();
                        foundTeacher.mainName=teacherName;
                        foundTeacher.teaching=new ArrayList<>();
                        if(subject.name!=null&&!subject.name.equals(""))
                            foundTeacher.teaching.add(new TeacherClass(c.name,subject.name,subject.hour));
                        allTeachers.add(foundTeacher);
                    }
                }
            }
        }
        return allTeachers;
    }
    static class Link {
        String url, name;
    }
    private class Theme {
        int color;

        Theme(String color) {
            this.color = Color.parseColor(color);
        }

        Theme(int color) {
            this.color = color;
        }
    }
    private class Class {
        String name;
        ArrayList<Subject> classes;

        Class(String name, ArrayList<Subject> classes) {
            this.name = name;
            this.classes = classes;
        }
    }
    private class Subject {
        int hour;
        String name, fullName;

        Subject(int hour, String name, String fullName) {
            this.hour = hour;
            this.name = name;
            this.fullName = fullName;
        }
    }
    private class Teacher{
        ArrayList<TeacherClass> teaching;
        String mainName;
        String teachingAt(int hour){
            return null;
        }
    }
    private class TeacherClass{
        String className,lessonName;
        int hour;
        TeacherClass(String className,String lessonName,int hour){
            this.hour=hour;
            this.className=className;
            this.lessonName=lessonName;
        }
    }
}
class GetLink extends AsyncTask<String, String, String> {
    private String ser;
    private GotLink gotlink;
    private boolean success;

    GetLink(String service, GotLink gl) {
        ser = service;
        gotlink = gl;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Document docu = Jsoup.connect(ser).get();
            Elements doc = docu.select("a");
            logAll("LINKGET", docu.outerHtml());
            String file = null;
            for (int i = 0; i < doc.size(); i++) {
                if (doc.get(i).attr("href").endsWith(".xls")) {
                    file = doc.get(i).attr("href");
                    Log.i("FILE", file);
                    break;
                }
            }
            success = true;
            return file;
        } catch (IOException e) {
            success = false;
            return e.getMessage();
        }
    }

    private void logAll(String TAG, String longString) {
        int splitSize = 300;
        if (longString.length() > splitSize) {
            int index = 0;
            while (index < longString.length() - splitSize) {
                Log.e(TAG, longString.substring(index, index + splitSize));
                index += splitSize;
            }
            Log.e(TAG, longString.substring(index, longString.length()));
        } else {
            Log.e(TAG, longString);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (success) {
            gotlink.onLinkGet(s);
        } else {
            gotlink.onFail(s);
        }
    }

    interface GotLink {
        void onLinkGet(String link);

        void onFail(String e);
    }
}

class GetNews extends AsyncTask<String, String, ArrayList<Main.Link>> {
    private String ser;
    private GotNews gotlink;
    private boolean success;

    GetNews(String service, GotNews gl) {
        ser = service;
        gotlink = gl;
    }

    @Override
    protected ArrayList<Main.Link> doInBackground(String... strings) {
        try {
            ArrayList<Main.Link> file = new ArrayList<>();
            Document docu = Jsoup.connect(ser).get();
            Elements ahs = docu.getAllElements().select("div.carousel-inner").select("div.item").select("a");
            for (int in = 0; in < ahs.size(); in++) {
                Main.Link link = new Main.Link();
                link.name = ahs.get(in).text();
                link.url = ahs.get(in).attr("href");
                if (!link.name.equals("")) file.add(link);
            }
            success = true;
            return file;
        } catch (IOException e) {
            success = false;
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Main.Link> s) {
        if (success) {
            if (gotlink != null) gotlink.onNewsGet(s);
        } else {
            if (gotlink != null) gotlink.onFail(s);
        }
    }

    interface GotNews {
        void onNewsGet(ArrayList<Main.Link> link);

        void onFail(ArrayList<Main.Link> e);
    }
}

class FileDownloader extends AsyncTask<String, String, String> {
    private String furl;
    private File fdpath;
    private boolean available;
    private OnDownload oe;

    FileDownloader(String url, File path, OnDownload onfile) {
        oe = onfile;
        furl = url;
        fdpath = path;
    }

    private boolean check() {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(furl).openConnection();
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected String doInBackground(String... comment) {
        int perc = 0;
        if (check()) {
            available = true;
            int count;
            try {
                URL url = new URL(furl);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(fdpath);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                    total += count;
                    if (perc < (int) (total * 100 / lenghtOfFile)) {
                        perc++;
                        oe.onProgressChanged(fdpath, (int) (total * 100 / lenghtOfFile));
                    }
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            available = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        if (oe != null) {
            oe.onFinish(fdpath, available);
        }
    }

    interface OnDownload {
        void onFinish(File output, boolean isAvailable);

        void onProgressChanged(File output, int percent);
    }
}