package com.sachin.game.api.beans.test;

import com.sachin.game.api.beans.Ladder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by C5203803 on 7/14/2014.
 */
public class LadderTest {

    @Test
    public void testInvalidLadder() throws Exception {

        try {
            Ladder ladder = new Ladder(-1, 10);
        }catch (IllegalArgumentException ex){
            Assert.assertTrue("IllegalArgumentException should be thrown", true);
            return;
        }

        Assert.assertTrue("IllegalArgumentException should be thrown", false);
    }
}
