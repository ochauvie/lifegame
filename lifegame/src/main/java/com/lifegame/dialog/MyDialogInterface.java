package com.lifegame.dialog;

public class MyDialogInterface {

	DialogReturn dialogReturn;

    public interface DialogReturn {
        void onDialogCompleted(boolean answer);
    }

    public void setListener(DialogReturn dialogReturn) {
        this.dialogReturn = dialogReturn;
    }

    public DialogReturn getListener() {
        return dialogReturn;
    }
	
}
