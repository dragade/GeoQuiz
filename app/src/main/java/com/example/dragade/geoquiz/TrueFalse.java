package com.example.dragade.geoquiz;

/**
 * Represents a true/false question
 */
public class TrueFalse {
  final private int mQuestion; //question resourceId
  final private boolean mTrueQuestion; //true if the question is true
  private boolean mUserCheated = false;

  public TrueFalse(int mQuestion, boolean mTrueQuestion) {
    this.mQuestion = mQuestion;
    this.mTrueQuestion = mTrueQuestion;
  }

  public int getQuestion() {
    return mQuestion;
  }

  public boolean isTrueQuestion() {
    return mTrueQuestion;
  }

  public boolean isUserCheated() {
    return mUserCheated;
  }

  public void setUserCheated(boolean mUserCheated) {
    this.mUserCheated = mUserCheated;
  }
}
