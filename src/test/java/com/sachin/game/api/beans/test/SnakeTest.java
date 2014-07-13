package com.sachin.game.api.beans.test;

import com.sachin.game.api.beans.Snake;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by C5203803 on 7/14/2014.
 */
public class SnakeTest {

    @Test
    public void testInvalidSnake() throws Exception {

        try{
            Snake snake = new Snake(2,13);
        }catch (IllegalArgumentException ex){
            Assert.assertTrue("IllegalArgumentException should be thrown", true);
            return;
        }

        Assert.assertTrue("IllegalArgumentException should be thrown", false);

    }
}
