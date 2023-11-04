package io.github.k3ssdev.teamtrekandroid.ui.employee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployeeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EmployeeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calendar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}