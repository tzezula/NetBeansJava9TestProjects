/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module AnagramGameGermanWords {
    requires anagram.spi;
    requires anagram.util;
    provides com.toy.anagram.spi.WordLibrary with anagram.lang.de.GermanWords;
}
