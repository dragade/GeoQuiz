package com.example.dragade.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
  private static final String TAG = CheatActivity.class.getSimpleName();

  public static final String EXTRA_ANSWER_IS_TRUE =
      "com.bignerdranch.android.geoquiz.answer_is_true";

  public static final String EXTRA_ANSWER_SHOWN =
      "com.bignerdranch.android.geoquiz.answer_shown";

  private TextView mAnswerTextView;
  private Button mShowAnswer;

  // State to save
  private static final String KEY_ANSWER_IS_TRUE = "answerIsTrue";
  private boolean mAnswerIsTrue = false;
  private static final String KEY_ANSWER_IS_SHOWN = "answerIsShown";
  private boolean mAnswerIsShown = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    restoreState(getIntent(), savedInstanceState);

    mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

    setAnswerShownResult();

    mShowAnswer = (Button) findViewById(R.id.show_answer_button);
    mShowAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAnswerIsTrue) {
          mAnswerTextView.setText(R.string.true_button);
        } else {
          mAnswerTextView.setText(R.string.false_button);
        }
        mAnswerIsShown = true;
        setAnswerShownResult();
      }
    });

    if (mAnswerIsShown) {
      if (mAnswerIsTrue) {
        mAnswerTextView.setText(R.string.true_button);
      } else {
        mAnswerTextView.setText(R.string.false_button);
      }
    }
  }

  private void setAnswerShownResult() {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, mAnswerIsShown);
    setResult(RESULT_OK, data);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(KEY_ANSWER_IS_TRUE, mAnswerIsTrue);
    outState.putBoolean(KEY_ANSWER_IS_SHOWN, mAnswerIsShown);
    Log.d(TAG, "onSaveInstanceState mAnswerIsTrue=" + mAnswerIsTrue + " mAnswerIsShown=" + mAnswerIsShown);
  }

  // try to restore the state from the saved state or the intent
  private void restoreState(Intent intent, Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE, false);
      mAnswerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN, false);
      Log.d(TAG, "restoreState from savedInstanceState. mAnswerIsTrue=" + mAnswerIsTrue + " mAnswerIsShown=" + mAnswerIsShown);
    } else if (intent != null) {
      mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
      mAnswerIsShown = intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
      Log.d(TAG, "restoreState from intent. mAnswerIsTrue=" + mAnswerIsTrue + " mAnswerIsShown=" + mAnswerIsShown);
    } else {
      Log.w(TAG, "restoreState called with null intent and bundle");
    }
  }
}
