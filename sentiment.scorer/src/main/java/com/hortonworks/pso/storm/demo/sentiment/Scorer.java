/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hortonworks.pso.storm.demo.sentiment;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Scorer implements Serializable{
//    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Scorer instance = new Scorer();

    private final String negativeFile = "/negative-words.txt";
    private final String positiveFile = "/positive-words.txt";
    private Set<String> positive = null;
    private Set<String> negative = null;

    private Scorer() {
        init();
    }

    private void init() {
        // Load sentiment dictionary files into sets.
        positive = loadSentimentDictionary(positiveFile);
        negative = loadSentimentDictionary(negativeFile);
    }

    public static Scorer getInstance() {
        return instance;
    }

    private Set<String> loadSentimentDictionary(String input) {
        Set<String> targetSet = new HashSet<String>();

        InputStream stream = Scorer.class.getResourceAsStream(input);

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String textTrimmed = text.trim();
                if (textTrimmed.length() > 0 && !textTrimmed.startsWith(";")) {
                    targetSet.add(textTrimmed);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return targetSet;
    }

    public int score(String text, String delimiter) {
        int score = 0; // neutral

        for (String word: text.split(delimiter)) {
            if (positive.contains(word.trim())) {
//                logger.debug("PLUS for '{}'", word);
                score++;
            }
            else if (negative.contains(word.trim())) {
//                logger.debug("MINUS for '{}'", word);
                score--;
            }
        }

        return score;
    }

    public int score(String text) {
        return score(text, " ");
    }

}
