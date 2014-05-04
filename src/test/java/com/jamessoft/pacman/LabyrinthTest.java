/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamessoft.pacman;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ACE
 */
public class LabyrinthTest {

    public LabyrinthTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void columnNumber() {
        Labyrinth l = new Labyrinth();
        assertEquals(l.getColumnNumber(), 36);
    }

    @Test
    public void ballsNumber() {
        Labyrinth l = new Labyrinth();
        Integer balls = 0;
        for (int[] rows : l.cellType) {
            for (int cell : rows) {
                if (cell == 1) {
                    balls++;
                }
            }
            assertEquals(balls, (Integer) 240);
        }
    }

    @Test
    public void wallsTest() {
        Labyrinth l = new Labyrinth();       
        Integer walls = 0;
        for (int[] rows : l.cellType) {
            for (int cell : rows) {                
                if (cell == -1) {
                    walls++;
                }
            }
        }
        
        assertEquals(walls, (Integer) 708);
        
    }
}
