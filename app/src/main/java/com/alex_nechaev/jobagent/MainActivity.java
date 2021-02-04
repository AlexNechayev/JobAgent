package com.alex_nechaev.jobagent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

//    Context context;
//    Resources resources;

    int currentNumberOfAgents = 0;
    int numberOfAgents;
    LinearLayout linearLayout;
    ArrayList<Agent> agentArrayList = new ArrayList<Agent>();

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int currentDay = calendar.get(Calendar.DATE);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentYear = calendar.get(Calendar.YEAR);

    int spinnerItemIDAtr = -1;
    int radioItemIDAtr = -1;
    String calenderStringFormatAtr = currentDay+"/"+currentMonth+"/"+currentYear;;
    boolean mobilityAtr;
    int kilometerAtr = 5;
    String jobTypeAtr;
    float rate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.inScrollLayout);
        FloatingActionButton addButton = findViewById(R.id.addBtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAgentDialog();
            }
        });
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showAddAgentDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_agent_activity);

        dialog.show();

        Button setBtn = (Button) dialog.findViewById(R.id.set_btn);
        Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
        EditText userInput = (EditText) dialog.findViewById(R.id.user_input);
        TextView instructionText = (TextView) findViewById(R.id.instructions_text);

        setBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!userInput.getText().toString().equals("")) {
                    numberOfAgents = Integer.parseInt(userInput.getText().toString());
                    if (numberOfAgents > 0) {
                        numberOfAgents += currentNumberOfAgents;
                        instructionText.setVisibility(View.GONE);
                        createAgents();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.enter_a_number), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createAgents() {

        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.inScrollLayout);

        for (int i = currentNumberOfAgents; i < numberOfAgents; i++) {

            Agent agent = new Agent(i);
            agentArrayList.add(agent);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(linearParams);

            Space spaceStart = new Space(this);
            LinearLayout.LayoutParams spaceStartParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            spaceStartParams.weight = (float) 0.4;
            spaceStart.setLayoutParams(spaceStartParams);
            linearLayout.addView(spaceStart);

            TextView textView = new TextView(this);
            textView.setTextSize((65 / getApplicationContext().getResources().getDisplayMetrics().density));
            textView.setGravity(Gravity.CENTER | Gravity.START);
            textView.setId(i);
            textView.setText(getResources().getString(R.string.agent) + " " + (i + 1));
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            textParams.weight = 2;
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);

            Space spaceMiddle = new Space(this);
            LinearLayout.LayoutParams spaceMiddleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            spaceMiddleParams.weight = (float)0.2;
            spaceMiddle.setLayoutParams(spaceMiddleParams);
            linearLayout.addView(spaceMiddle);

            ImageButton imageButton = new ImageButton(this);
            imageButton.setTag(i);
            imageButton.setImageResource(R.drawable.ic_edit);
            imageButton.setBackgroundResource(R.drawable.p_button_selector);
            imageButton.setOnClickListener(new agentButtonListener());
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParams.weight = (float)0.9;
            buttonParams.setMargins(0, 20, 0, 20);
            imageButton.setLayoutParams(buttonParams);
            linearLayout.addView(imageButton);

            Space spaceEnd = new Space(this);
            LinearLayout.LayoutParams spaceEndParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            spaceEndParams.weight = (float) 0.4;
            spaceEnd.setLayoutParams(spaceEndParams);
            linearLayout.addView(spaceEnd);

            mainLinearLayout.addView(linearLayout);
        }
        currentNumberOfAgents = numberOfAgents;
    }

    public void showAgentDialog(int identification) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.agent_activity);
        dialog.show();

        int agentID = identification;

        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        EditText jobFieldEditText = (EditText) dialog.findViewById(R.id.job_field_et);
        Spinner locationSpinner = (Spinner) dialog.findViewById(R.id.location_spinner);
        CalendarView calendarView = (CalendarView) dialog.findViewById(R.id.calendar_view);
        CheckBox mobilityCheckBox = (CheckBox) dialog.findViewById(R.id.mobility_check_box);
        TextView kilometerTextView = (TextView) dialog.findViewById(R.id.kilometer_text_view);
        SeekBar kilometerSeekBar = (SeekBar) dialog.findViewById(R.id.kilometer_seek_bar);
        RadioGroup positionRadioGroup = (RadioGroup) dialog.findViewById(R.id.position_radio_group);
        Button submitButton = (Button) dialog.findViewById(R.id.submit_btn);

        kilometerTextView.setVisibility(View.GONE);
        kilometerSeekBar.setVisibility(View.GONE);

        if(agentArrayList.get(agentID).isInitialized()){
            Agent agent = new Agent(agentArrayList.get(agentID));
            boolean isMobilityHasChecked = agent.isMobility();
            progressBar.setMax(agent.getProgress());
            progressBar.setProgress(agent.getProgress());
            jobFieldEditText.setText(agent.getField());
            locationSpinner.setSelection(agent.getLocation(),true);
            try {
                calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(agent.getCalendar()).getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mobilityCheckBox.setChecked(isMobilityHasChecked);
            if(isMobilityHasChecked){
                kilometerTextView.setVisibility(View.VISIBLE);
                kilometerSeekBar.setVisibility(View.VISIBLE);
                kilometerTextView.setText(getString(R.string.kilometers)+agent.getKilometers());
                kilometerSeekBar.setProgress((agent.getKilometers())/5);
            }
            ((RadioButton)positionRadioGroup.getChildAt(agent.getJobType())).setChecked(true);
        }


        jobFieldEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    progressBar.setProgress((progressBar.getProgress()) + 1);
                }
            }
        });

        locationSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeKeyboard(v);
                return false;
            }
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                closeKeyboard(parentView);
                spinnerItemIDAtr = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            boolean isDatePressed = false;

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int selectedYear, int month, int selectedDay) {
                closeKeyboard(view);
                int selectedMount = month + 1;
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                int currentDay = calendar.get(Calendar.DATE);
                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                int currentYear = calendar.get(Calendar.YEAR);


                if (selectedYear == currentYear && selectedMount >= currentMonth) {
                    if(selectedDay >= currentDay){
                        //Succeed
                        calenderStringFormatAtr = selectedDay+"/"+selectedMount+"/"+selectedYear;
                    }else{
                        //Failed
                        Toast.makeText(MainActivity.this, getString(R.string.passedDate), Toast.LENGTH_SHORT).show();
                    }
                }else if(selectedYear > currentYear){
                    //Succeed
                    calenderStringFormatAtr = selectedDay+"/"+selectedMount+"/"+selectedYear;
                }else{
                    //Failed
                    Toast.makeText(MainActivity.this, getString(R.string.passedDate), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mobilityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                closeKeyboard(buttonView);
                if (isChecked) {
                    kilometerTextView.setVisibility(View.VISIBLE);
                    kilometerSeekBar.setVisibility(View.VISIBLE);
                    progressBar.setMax(progressBar.getMax() + 1);
                    progressBar.setProgress((progressBar.getProgress()) + 1);

                } else {
                    kilometerTextView.setVisibility(View.GONE);
                    kilometerSeekBar.setVisibility(View.GONE);
                    progressBar.setProgress((progressBar.getProgress()) - 1);
                    progressBar.setMax(progressBar.getMax() - 1);

                }
                mobilityAtr = buttonView.isChecked();
            }
        });


        kilometerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int totalProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                closeKeyboard(seekBar);
                totalProgress = (progress*5)+5;
                if (progress != 0) {
                    kilometerTextView.setText(totalProgress + " " + getString(R.string.kilometers));
                } else {
                    kilometerTextView.setText(getString(R.string.init_kilometers));
                }
                kilometerAtr = (totalProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        positionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                closeKeyboard(group);
                progressBar.setProgress((progressBar.getProgress()) + 1);
                radioItemIDAtr = (checkedId-1)%(group.getChildCount());
                RadioButton rb = (RadioButton) group.getChildAt(radioItemIDAtr);
                jobTypeAtr = rb.getText().toString();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(v);
                int currentProgress = progressBar.getProgress();
                String jobFieldAtr = jobFieldEditText.getText().toString().trim();
                String jobFieldTrim = jobFieldAtr.replaceAll("\\s","");

                if(currentProgress == progressBar.getMax()){
                    if(!(jobFieldTrim.equals(""))) {

                        if(!agentArrayList.get(agentID).isInitialized()){
                            Dialog summeryDialog = new Dialog(MainActivity.this);
                            summeryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            summeryDialog.setContentView(R.layout.agent_confirmation);
                            summeryDialog.show();

                            TextView jobFieldTextView = (TextView)summeryDialog.findViewById(R.id.job_field_summery);
                            TextView locationTextView = (TextView)summeryDialog.findViewById(R.id.location_summery);
                            TextView startDateTextView = (TextView)summeryDialog.findViewById(R.id.date_summery);
                            TextView mobilityTextView = (TextView)summeryDialog.findViewById(R.id.mobility_summery);
                            TextView distanceTextView = (TextView)summeryDialog.findViewById(R.id.distance_summery);
                            TextView jobTypeTextView = (TextView)summeryDialog.findViewById(R.id.job_type_summery);

                            TableRow distanceTableRow = (TableRow)summeryDialog.findViewById(R.id.distance_row);

                            Button okSummeryBtn = (Button)summeryDialog.findViewById(R.id.ok_summery_btn);
                            Button cancelSummeryBtn = (Button)summeryDialog.findViewById(R.id.cancel_summery_btn);
                            Button changeSummeryBtn = (Button)summeryDialog.findViewById(R.id.change_summery_btn);
                            Button rateSummeryBtn = (Button)summeryDialog.findViewById(R.id.rate_summery_btn);

                            RatingBar ratingBar = (RatingBar)summeryDialog.findViewById(R.id.rating_bar_summery);

                            distanceTableRow.setVisibility(View.GONE);

                            jobFieldTextView.setText(jobFieldAtr);
                            locationTextView.setText(locationSpinner.getItemAtPosition(spinnerItemIDAtr).toString());
                            startDateTextView.setText(calenderStringFormatAtr);
                            if(mobilityAtr){
                                distanceTableRow.setVisibility(View.VISIBLE);
                                mobilityTextView.setText(getString(R.string.yes));
                                distanceTextView.setText(kilometerAtr+" " + getString(R.string.kilometers));
                            }else{
                                mobilityTextView.setText(getString(R.string.no));
                            }
                            jobTypeTextView.setText(jobTypeAtr);

                            okSummeryBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    agentArrayList.get(agentID).setInitialized(true);
                                    agentArrayList.get(agentID).setProgress(currentProgress);
                                    agentArrayList.get(agentID).setField(jobFieldAtr);
                                    agentArrayList.get(agentID).setLocation(spinnerItemIDAtr);
                                    agentArrayList.get(agentID).setCalendar(calenderStringFormatAtr);
                                    agentArrayList.get(agentID).setMobility(mobilityAtr);
                                    agentArrayList.get(agentID).setKilometers(kilometerAtr);
                                    agentArrayList.get(agentID).setJobType(radioItemIDAtr);
                                    TextView tv = (TextView)findViewById(agentID);
                                    tv.setText(getString(R.string.agent_looking)+" "+jobFieldAtr);
                                    mobilityAtr =false;
                                    kilometerAtr =5;
                                    summeryDialog.dismiss();
                                    dialog.dismiss();
                                }
                            });

                            changeSummeryBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    summeryDialog.dismiss();
                                }
                            });

                            cancelSummeryBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mobilityAtr =false;
                                    kilometerAtr =5;
                                    summeryDialog.dismiss();
                                    dialog.dismiss();
                                }
                            });

                            rateSummeryBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(rate >= 0 && rate <=1){
                                        Toast.makeText(MainActivity.this, getString(R.string.we_will_try_better_next_time), Toast.LENGTH_SHORT).show();
                                    }else if(rate > 1 && rate <=2){
                                        Toast.makeText(MainActivity.this, getString(R.string.we_will_try_better_next_time), Toast.LENGTH_SHORT).show();
                                    }else if(rate > 2 && rate <=3){
                                        Toast.makeText(MainActivity.this, getString(R.string.thanks_we_are_getting_better), Toast.LENGTH_SHORT).show();
                                    }else if(rate > 3 && rate <=4){
                                        Toast.makeText(MainActivity.this, getString(R.string.almost_there), Toast.LENGTH_SHORT).show();
                                    }else if(rate > 4 && rate <=5) {
                                        Toast.makeText(MainActivity.this, getString(R.string.excellent), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                @Override
                                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                    rate = rating;
                                }
                            });


                        }else{
                            agentArrayList.get(agentID).setInitialized(true);
                            agentArrayList.get(agentID).setProgress(currentProgress);
                            agentArrayList.get(agentID).setField(jobFieldAtr);
                            agentArrayList.get(agentID).setLocation(spinnerItemIDAtr);
                            agentArrayList.get(agentID).setCalendar(calenderStringFormatAtr);
                            agentArrayList.get(agentID).setMobility(mobilityAtr);
                            agentArrayList.get(agentID).setKilometers(kilometerAtr);
                            agentArrayList.get(agentID).setJobType(radioItemIDAtr);
                            TextView tv = (TextView)findViewById(agentID);
                            tv.setText(getString(R.string.agent_looking)+" "+jobFieldAtr);
                            dialog.dismiss();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, getString(R.string.job_field_alert), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.submit_alert), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

//    public void changeToEnglish(View view) {
//
//        context = LocalHelper.setLocale(MainActivity.this,"en");
//        resources = context.getResources();
//
//    }
//
//    public void changeToHebrew(View view) {
//
//    }

    class agentButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = Integer.parseInt(v.getTag().toString());
            showAgentDialog(id);
        }
    }

//    public void onHandleClickSettings(MenuItem item) {
//        Dialog dialog = new Dialog(MainActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.settings_activity);
//        dialog.show();
//
//        Button closeButton = (Button)dialog.findViewById(R.id.close_btn);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onHandleClickAbout(MenuItem item) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_activity);
        dialog.show();

        pl.droidsonroids.gif.GifImageButton israelButton =(pl.droidsonroids.gif.GifImageButton) dialog.findViewById(R.id.il_btn);
        pl.droidsonroids.gif.GifImageButton usaButton =(pl.droidsonroids.gif.GifImageButton) dialog.findViewById(R.id.usa_btn);

        Button closeButton = (Button)dialog.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}