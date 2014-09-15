package com.example.dragade.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
  public static final String EXTRA_ANSWER_IS_TRUE =
      "com.bignerdranch.android.geoquiz.answer_is_true";

  public static final String EXTRA_ANSWER_SHOWN =
      "com.bignerdranch.android.geoquiz.answer_shown";

  private boolean mAnswerIsTrue = false;
  private TextView mAnswerTextView;
  private Button mShowAnswer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

    setAnswerShownResult(false);

    mShowAnswer = (Button)findViewById(R.id.show_answer_button);
    mShowAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAnswerIsTrue) {
          mAnswerTextView.setText(R.string.true_button);
        } else {
          mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
      }
    });


  }

  private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }

}
