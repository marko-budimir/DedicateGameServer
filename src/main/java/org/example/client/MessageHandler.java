package org.example.client;

import org.example.client.ui.model.Enemy;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MessageHandler {
    private MessageHandler() {
    }

    public static String encodeMessage(float x, float y, float size) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return String.format("%s,%s,%s", decimalFormat.format(x), decimalFormat.format(y), decimalFormat.format(size));
    }

    public static Enemy decodeMessage(String message) {
        String[] parts = message.split(":");
        String clientId = parts[0];
        if (clientId == null || parts.length != 2) {
            return null;
        }
        String[] data = parts[1].split(",");
        if (data.length != 3 || data[0] == null || data[1] == null || data[2] == null) {
            return null;
        }
        float x = Float.parseFloat(data[0]);
        float y = Float.parseFloat(data[1]);
        float size = Float.parseFloat(data[2]);

        return new Enemy(clientId, x, y, size);
    }
}
