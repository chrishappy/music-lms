package com.themusicians.musiclms.nodeForms.addAttachments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addAttachmentsViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public addAttachmentsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is home fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
