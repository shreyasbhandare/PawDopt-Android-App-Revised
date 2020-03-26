package com.sbhandare.pawdopt.Component;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class PawDoptPasswordTransform extends PasswordTransformationMethod {
    private  char BIGGER_DOT = '●';

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;
        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }
        public char charAt(int index) {
            return BIGGER_DOT; // This is the important part
        }
        public int length() {
            return mSource.length(); // Return default
        }
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
};

