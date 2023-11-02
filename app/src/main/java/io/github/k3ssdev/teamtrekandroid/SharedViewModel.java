package io.github.k3ssdev.teamtrekandroid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> selected = new MutableLiveData<>();

    public void select(String input) {
        selected.setValue(input);
    }

    public LiveData<String> getSelected() {
        return selected;
    }
}
