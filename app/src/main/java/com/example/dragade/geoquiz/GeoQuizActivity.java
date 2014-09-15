package com.example.dragade.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GeoQuizActivity extends ActionBarActivity {

  private Button mTrueButton;
  private Button mFalseButton;
  private Button mNextButton;
  private Button mPreviousButton;
  private TextView mQuestionTextView;

  private TrueFalse[] mQuestionBank = new TrueFalse[] {
      new TrueFalse(R.string.question_oceans, true),
      new TrueFalse(R.string.question_mideast, false),
      new TrueFalse(R.string.question_africa, false),
      new TrueFalse(R.string.question_americas, true),
      new TrueFalse(R.string.question_asia, true),
  };

  private int mCurrentIndex = 0; //first call to nextQuestion will set to 0

  private View.OnClickListener makeCheckAnswerListener(final boolean expectTrue) {
    return new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        TrueFalse question = mQuestionBank[mCurrentIndex];
        int toast_id;
        if (question.isTrueQuestion() && expectTrue || !question.isTrueQuestion() && !expectTrue) {
          toast_id = R.string.toast_correct;
        } else {
          toast_id = R.string.toast_incorrect;
        }
        Toast toast = Toast.makeText(GeoQuizActivity.this, toast_id, Toast.LENGTH_SHORT);
        toast.show();
      }
    };
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geo_quiz);

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

    mNextButton = (Button) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nextQuestion();
      }
    });

    mPreviousButton = (Button) findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        previousQuestion();
      }
    });
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
}