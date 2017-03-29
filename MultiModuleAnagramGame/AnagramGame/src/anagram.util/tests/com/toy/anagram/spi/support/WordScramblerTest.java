/*
 * Copyright (c) 2017, Oracle.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package com.toy.anagram.spi.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

public class WordScramblerTest {
    
    public WordScramblerTest() {
    }       
    
    @Test
    public void testScrambleCorrectness() {
        final String testString = "testofscrambler";  //NOI18N
        final Map<Character,Integer> expectedHistogram = histogram(testString);
        Random r = new Random(1);
        final WordScrambler ws = WordScrambler.newInstance(r);
        final String result = ws.scramble(testString);
        final Map<Character,Integer> resultHistogram = histogram(result);
        assertEquals(expectedHistogram, resultHistogram);
    }

    @Test
    public void testScrambleConservative() {
        final String testString = "testofscrambler";  //NOI18N
        Random r = new Random(1);
        final WordScrambler ws1 = WordScrambler.newInstance(r);
        final String result1 = ws1.scramble(testString);
        r = new Random(1);
        final WordScrambler ws2 = WordScrambler.newInstance(r);
        final String result2 = ws2.scramble(testString);
        assertEquals(result1, result2);   
    }

    private static Map<Character, Integer> histogram(final String str) {
        final Map<Character,int[]> res = new HashMap<>();
        str.chars().forEach((c) -> {
            final int[] count = res.computeIfAbsent((char)c, (p) -> new int[1]);
            count[0]++;
        });
        return res.entrySet().stream()
                .collect(Collectors.toMap(
                        (e) -> e.getKey(),
                        (e) -> e.getValue()[0]
                ));
    }

}
