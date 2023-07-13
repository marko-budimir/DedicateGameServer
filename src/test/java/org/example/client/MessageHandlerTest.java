package org.example.client;

import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;
import org.example.client.ui.model.Enemy;
import org.example.client.ui.model.Rectangle;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MessageHandlerTest {

    @Test
    public void testEncodeMessage() {
        String message = MessageHandler.encodeMessage(1.0f, 2.0f, 3.0f);
        assertEquals(message, "1.0,2.0,3.0");
        message = MessageHandler.encodeMessage(1, 2, 3);
        assertEquals(message, "1.0,2.0,3.0");
        message = MessageHandler.encodeMessage(0, -1, -5);
        assertEquals(message, "0.0,-1.0,-5.0");
    }

    @Test
    public void testDecodeMessage() {
        Enemy enemy = MessageHandler.decodeMessage("1:1.0,2.0,3.0");
        assertEquals(enemy.getClientID(), "1");
        assertEquals(enemy.getVertex(), Vector2.of(1.0f, 2.0f));
        assertEquals(
                enemy.getRectangle(),
                new Rectangle(Vector2.of(1.0f, 2.0f), 3.0f, Vector3.of(1.0f, 0.0f, 0.0f))
        );

        enemy = MessageHandler.decodeMessage("2:-5,2.0,0");
        assertEquals(enemy.getClientID(), "2");
        assertEquals(enemy.getVertex(), Vector2.of(-5.0f, 2.0f));
        assertEquals(
                enemy.getRectangle(),
                new Rectangle(Vector2.of(-5.0f, 2.0f), 0.0f, Vector3.of(1.0f, 0.0f, 0.0f))
        );

        enemy = MessageHandler.decodeMessage("");
        assertNull(enemy);

        enemy = MessageHandler.decodeMessage("Test");
        assertNull(enemy);

        enemy = MessageHandler.decodeMessage("1:1.0,2.0");
        assertNull(enemy);

        enemy = MessageHandler.decodeMessage("1:1.0,2.0,3.0,4.0");
        assertNull(enemy);

        enemy = MessageHandler.decodeMessage(null);
        assertNull(enemy);
    }
}
