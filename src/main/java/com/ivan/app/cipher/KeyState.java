package com.ivan.app.cipher;

public class KeyState {

    private int k = 1;
    private final int minLength;
    private final int maxLength;

    public KeyState(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public int getK() {
        return k;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setK(int k) throws KeyStateException, KeyUnadmesibleValueException {
        if (k < minLength || k > maxLength) {
            String exceptionMessage =
                    String.format("The number should be between %d and %d", minLength, maxLength);
            throw new KeyStateException(exceptionMessage);
        }

        if (k == minLength || k == maxLength) {
            String exceptionMessage =
                    String.format("The key must not be equal: %d and %d", minLength, maxLength);
            throw new KeyUnadmesibleValueException(exceptionMessage);
        }

        this.k = k;
    }

    public static class KeyUnadmesibleValueException extends Exception {
        KeyUnadmesibleValueException(String msg) {
            super(msg);
        }

        private static final long serialVersionUID = 1L;

    }

    public static class KeyStateException extends Exception {
        KeyStateException(String msg) {
            super(msg);
        }

        private static final long serialVersionUID = 6902639857495020868L;
    }
}
