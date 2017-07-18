package com.app.helpers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.views.AnyEditTextView;
import com.app.views.AnyTextView;

import java.util.Calendar;

public class InputHelper {

    static int hour;
    static int min;

    static int year;
    static int month;
    static int day;
    // private int hour;
    static int minute;
    static StringBuilder date;
    static StringBuilder time;
    static String date_time;
    static FragmentManager ctx;

    private static onTimeSelectListener onImageSelect;


    public static boolean checkWhiteSpace(AnyEditTextView edtext,
                                          Activity activity) {

        String test = edtext.getText().toString();

        if (test.startsWith(" ")) {
            Funcs.showShortToast(
                    "Field should not contain white space in start", activity);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkWhiteSpace(TextView edtext, Activity activity) {

        String test = edtext.getText().toString();

        if (test.startsWith(" ")) {
            Funcs.showShortToast(
                    "Field should not contain white space in start", activity);
            return false;
        } else {
            return true;
        }
    }

    public static boolean matchPasswords(AnyEditTextView edtPassword,
                                         AnyEditTextView edtConfirmPassword, Activity activity) {
        if (edtPassword.getText().toString()
                .equals(edtConfirmPassword.getText().toString())) {
            return true;
        } else {
            UIHelper.showLongToastInCenter(activity,
                    "Password and Confirm Password do not match");
            return false;
        }
    }

    public static void showDatePickerDialogWithFutureCheck(
            final Activity activity, final AnyEditTextView editText) {

        // 0 for From date - 1 for To Date

        // Get Cuttent Date Instance
        final Calendar c = Calendar.getInstance();
        final Time currentTime = new Time();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        currentTime.set(mDay - 1, mMonth, mYear);

        DatePickerDialog dateDlg = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();

                        chosenDate.set(dayOfMonth, monthOfYear, year);

                        long dtDob = chosenDate.toMillis(true);

                        CharSequence strDate = DateFormat.format("yyyy-MM-dd",
                                dtDob);

                        Log.i(chosenDate + "", currentTime + "");

                        if (chosenDate.after(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Invalid Date Selected");

                        } else {

                            editText.setText(strDate);
                        }
                    }

                }, mYear, mMonth, mDay);

        dateDlg.show();

    }

    public static void showDatePickerDialogWithFutureCheck(
            final Activity activity, final AnyTextView textView, String date) {

        // 0 for From date - 1 for To Date

        // Get Cuttent Date Instance
        final Calendar c = Calendar.getInstance();
        final Time currentTime = new Time();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        currentTime.set(mDay, mMonth, mYear);

        DatePickerDialog dateDlg = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();

                        chosenDate.set(dayOfMonth, monthOfYear, year);

                        long dtDob = chosenDate.toMillis(true);

                        CharSequence strDate = DateFormat.format("yyyy-MM-dd",
                                dtDob);

                        if (chosenDate.after(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select future date");
                        } else if (chosenDate.equals(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select current date");
                        } else {

                            textView.setText(strDate);
                        }
                    }

                }, mYear, mMonth, mDay);

        dateDlg.show();

    }

    public static void showDatePickerDialogWithPastCheck(
            final Activity activity, final AnyEditTextView editText) {

        // 0 for From date - 1 for To Date

        // Get Cuttent Date Instance
        final Calendar c = Calendar.getInstance();
        final Time currentTime = new Time();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        currentTime.set(mDay, mMonth, mYear);

        DatePickerDialog dateDlg = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();

                        chosenDate.set(dayOfMonth, monthOfYear, year);

                        long dtDob = chosenDate.toMillis(true);

                        CharSequence strDate = DateFormat.format("yyyy-MM-dd",
                                dtDob);

                        if (chosenDate.before(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select past date");
                        } else if (chosenDate.equals(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select current date");
                        } else {

                            editText.setText(strDate);
                        }
                    }

                }, mYear, mMonth, mDay);

        dateDlg.show();
    }

    public static void showDatePickerDialogWithPastCheck(
            final Activity activity, final AnyTextView textView,final String time) {

        // 0 for From date - 1 for To Date
        // Get Cuttent Date Instance
        final Calendar c = Calendar.getInstance();
        final Time currentTime = new Time();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        currentTime.set(mDay, mMonth, mYear);


        DatePickerDialog dateDlg = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();

                        chosenDate.set(dayOfMonth, monthOfYear, year);

                        long dtDob = chosenDate.toMillis(true);

                        CharSequence strDate = DateFormat.format("yyyy-MM-dd",
                                dtDob);


                        if (chosenDate.before(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select past date");
                        } else if (chosenDate.equals(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select current date");
                        } else {
                           // CharSequence  date = strDate.toString();
                           // textView.setText(Utils.formatDate(strDate.toString()));
                            onImageSelect.onClicked(strDate.toString(),time);

                        }
                    }

                }, mYear, mMonth, mDay);

        dateDlg.show();

    }

    public static void showDateAndTimePickerDialogWithPastCheck(
            final Activity activity, final AnyTextView textView) {

        // 0 for From date - 1 for To Date

        // Get Cuttent Date Instance
        final Calendar c = Calendar.getInstance();
        final Time currentTime = new Time();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        boolean is24HourView = true;

        currentTime.set(mDay, mMonth, mYear);

        TimePickerDialog timeDlg = new TimePickerDialog(activity,
                new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int selectedMinute) {

                        hour = hourOfDay;
                        min = selectedMinute;

                    }
                }, hour, min, is24HourView);

        timeDlg.show();

        DatePickerDialog dateDlg = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Time chosenDate = new Time();

                        chosenDate.set(0, min, hour, dayOfMonth, monthOfYear,
                                year);

                        long dtDob = chosenDate.toMillis(true);

                        CharSequence strDate = DateFormat.format(
                                "yyyy-MM-dd hh:mm", dtDob);

                        if (chosenDate.before(currentTime)) {
                            UIHelper.showLongToastInCenter(activity,
                                    "Cannot select Past date");
                        } else if (chosenDate.equals(currentTime)) {
                            textView.setText(strDate);
                            // UIHelper.showLongToastInCenter(activity,
                            // "Cannot select current date");
                        } else {

                            textView.setText(strDate);
                        }
                    }

                }, mYear, mMonth, mDay);

        dateDlg.show();

    }

    public void setTimeSelect(onTimeSelectListener onclick) {
        this.onImageSelect = onclick;
    }

    public interface onTimeSelectListener {
        void onClicked(String value,String time);
    }

//	public static void showDateAndTimePickerDialogWithPastCheck1(
//			final Activity activity, final AnyTextView textView,FragmentManager fmg) {
//		// TODO Auto-generated method stub
//		// Use the current date as the default date in the picker
//
//		final Calendar c = Calendar.getInstance();
//		year = c.get(Calendar.YEAR);
//		month = c.get(Calendar.MONTH);
//		day = c.get(Calendar.DAY_OF_MONTH);
//
//		hour = c.get(Calendar.HOUR_OF_DAY);
//		minute = c.get(Calendar.MINUTE);
//		ctx=fmg;
//
//		DatePickerDialog dateDlg = new DatePickerDialog(activity,
//				datePickerListener, year, month, day) {
//			@Override
//			public void onDateChanged(DatePicker view, int selectedYear,
//					int selectedMonth, int selectedDay) {
//				if (selectedYear < year)
//					view.updateDate(year, month, day);
//
//				if (selectedMonth < month && selectedYear == year)
//					view.updateDate(year, month, day);
//
//				if (selectedDay < day && selectedYear == year
//						&& selectedMonth == month)
//					view.updateDate(year, month, day);
//
//			}
//		};
//		dateDlg.show();
//
//	}
//
//	@SuppressLint("ValidFragment")
//	public class StartTimePicker extends DialogFragment {
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//			// TODO Auto-generated method stub
//			// Use the current date as the default date in the picker
//
//			return new TimePickerDialog(getActivity(), timePickerListener,
//					hour, minute, false);
//
//		}
//
//	}
//
//	private static DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//		public void onDateSet(DatePicker view, int selectedYear,
//				int selectedMonth, int selectedDay) {
//			year = selectedYear;
//			month = selectedMonth;
//			day = selectedDay;
//
//			date = new StringBuilder().append(UIHelper.pad(year)).append("-")
//					.append(UIHelper.pad(month + 1)).append("-")
//					.append(UIHelper.pad(day));
//
//			new StartTimePicker().show(ctx,
//					"start_time_picker");
//
//		}
//	};
//
//	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
//		public void onTimeSet(TimePicker view, int selectedHour,
//				int selectedMinute) {
//			hour = selectedHour;
//			minute = selectedMinute;
//			time = new StringBuilder().append(UIHelper.pad(hour)).append(":")
//					.append(UIHelper.pad(minute));
//			date_time = date + " " + time;
//
//			edtPickUpDate.setText(date_time);
//
//		}
//	};

}
