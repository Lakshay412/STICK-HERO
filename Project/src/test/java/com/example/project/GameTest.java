package com.example.project;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.ImageView;
import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    int val;

    @Test
    public void CheckSingleton() {
        Hero h1 = Hero.getInstance();
        Hero h2 = Hero.getInstance();
        assertEquals(h1, h2);
    }

    @Test
    public void CheckHero() {

        boolean thrown = false;

        try {
            throw new StickLengthException("Stick length exception");
        } catch (StickLengthException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }


}
