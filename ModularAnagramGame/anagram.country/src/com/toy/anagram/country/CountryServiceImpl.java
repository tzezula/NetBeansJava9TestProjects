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
package com.toy.anagram.country;

import com.toy.anagram.spi.CountryService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class CountryServiceImpl implements CountryService {
    private static final String URL_TEMPLATE;
    static {
        //URL_TEMPLATE = "https://raw.githubusercontent.com/tzezula/NetBeansJava9TestProjects/master/resources/countries/%s/%s";
        try {
            final URL base = new File(new File(
                    System.getProperty("user.dir")).getParentFile().getParentFile(),
                    "resources/countries").toURI().toURL();
            URL_TEMPLATE = base.toString() + "%s/%s";
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
    private static final String DESCRIPTOR = "Bundle.properties";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_LANG = "LANG";
    private static final String KEY_ICON = "ICON";

    @Override
    public Country findCountry(String id) {
        try {
            final URL url = new URL(String.format(URL_TEMPLATE, id, DESCRIPTOR));
            try (InputStream in = url.openStream()) {
                final Properties props = new Properties();
                props.load(in);
                String displayName = props.getProperty(KEY_NAME);
                if (displayName == null) {
                    displayName = id;
                }
                String lang = props.getProperty(KEY_LANG);
                if (lang == null) {
                    lang = id;
                }
                final String icon = props.getProperty(KEY_ICON);
                return new Country(
                        id,
                        displayName,
                        lang,
                        icon == null ?
                                null :
                                new URL(String.format(URL_TEMPLATE, id, icon)).toURI());
            }
        } catch (IOException | URISyntaxException ex) {
            //pass
        }
        return null;
    }
    
}
