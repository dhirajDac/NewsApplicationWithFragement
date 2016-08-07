package com.vodafone.com.newsapplicationwithfragement;

import com.vodafone.com.newsapplicationwithfragement.util.DateUtil;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dateConvert_isCorrect() throws  Exception
    {
        String correctInputDate1 = "2016-07-25T09:56:27Z";
        String correctOutputDate1 = "Mon, 25 Jul 2016 09:56";

        String corrOut=DateUtil.GetLocalDate(correctInputDate1);
        assertEquals(correctOutputDate1,corrOut);
    }
}