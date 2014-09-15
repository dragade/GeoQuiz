package com.example.dragade.geoquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GeoQuizActivity extends ActionBarActivity {

  private static final String TAG = GeoQuizActivity.class.getSimpleName();
  private static final String KEY_INDEX = "index";

  private Button mTrueButton;
  private Button mFalseButton;


  // can't use Button because landscape layout uses ImageViewButton and the normal layout uses Button
  private View mNextButton;
  private View mPreviousButton;

  private Button mCheatButton;

  private TextView mQuestionTextView;

  private TrueFalse[] mQuestionBank = new TrueFalse[] {
      new TrueFalse(R.string.question_oceans, true),
      new TrueFalse(R.string.question_mideast, false),
      new TrueFalse(R.string.question_africa, false),
      new TrueFalse(R.string.question_americas, true),
      new TrueFalse(R.string.question_asia, true),
  };

  private int mCurrentIndex = 0; //first call to nextQuestion will set to 0
  private boolean mIsCheater = false;

  private View.OnClickListener makeCheckAnswerListener(final boolean expectTrue) {
    return new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        TrueFalse question = mQuestionBank[mCurrentIndex];
        int toast_id;
        if (mIsCheater) {
          toast_id = R.string.judgment_toast;
        }
        else if (question.isTrueQuestion() && expectTrue || !question.isTrueQuestion() && !expectTrue) {
          toast_id = R.string.toast_correct;
        } else {
          toast_id = R.string.toast_incorrect;
        }
        Toast toast = Toast.makeText(GeoQuizActivity.this, toast_id, Toast.LENGTH_SHORT);
        toast.show();
      }
    };
  }

  private void restoreState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate ");
    setContentView(R.layout.activity_geo_quiz);
    restoreState(savedInstanceState);

    mQuestionTextView = (TextView) findViewById(R.id.question_text);
    updateQuestionText();
    mQuestionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nextQuestion();
      }
    });

    mTrueButton = (Button) findViewById(R.id.true_button);
    mTrueButton.setOnClickListener(makeCheckAnswerListener(true));

    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(makeCheckAnswerListener(false));

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nextQuestion();
      }
    });

    mPreviousButton = findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        previousQuestion();
      }
    });

    mCheatButton = (Button)findViewById(R.id.cheat_button);
    mCheatButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(GeoQuizActivity.this, CheatActivity.class);
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        startActivityForResult(i, 0);
      }
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.i(TAG, "onSaveInstanceState");
    outState.putInt(KEY_INDEX, mCurrentIndex);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }


  private void nextQuestion() {
    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    updateQuestionText();
  }

  private void previousQuestion() {
    if (mCurrentIndex == 0) {
      mCurrentIndex = mQuestionBank.length - 1;
    }
    else {
      mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
    }
    updateQuestionText();
  }

  private void updateQuestionText() {
    int questionTextId = mQuestionBank[mCurrentIndex].getQuestion();
    mQuestionTextView.setText(questionTextId);
    mIsCheater = false;
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.geo_quiz, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) {
      return;
    }
    mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
  }

}
