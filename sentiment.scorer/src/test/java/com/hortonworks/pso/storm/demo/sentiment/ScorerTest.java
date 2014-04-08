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

import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

public class ScorerTest {
//    final Logger logger = LoggerFactory.getLogger(this.getClass());

    final String negativeText1 = "He abused his position and is no longer allowed to participate";
    final String cnnSportsText = "The players' petition was a way to get a seat at the bargaining table in college sports and could change the landscape of the NCAA model.\n" +
            "Northwestern University fought the petition by saying its players are students, not employees.\n" +
            "But the board's decision indicates that there was enough evidence presented that the athletes are employees of the university -- getting paid in the form of scholarships, working between 20 and 50 hours per week and generating millions of dollars for their institutions.\n" +
            " Northwestern football players want union Jabbar: Pay student athletes Should college athletes be paid?\n" +
            "The athletes have said they're seeking better medical coverage, concussion testing, four-year scholarships and the possibility of being paid.\n" +
            "Richard Epstein, labor law professor at New York University, said the ruling has \"vast implications for the structure of the sport, if upheld.\"\n" +
            "But he noted an appeal would likely take years to resolve.\n" +
            "The regional NLRB office said any requests for review of its decision must be filed with the board's headquarters in Washington, D.C. by April 9.\n" +
            "The NCAA promptly said that while it wasn't party to the proceeding, it was \"disappointed\" with the board's ruling and disagreed \"with the notion that student-athletes are employees.\"\n" +
            "\"We frequently hear from student-athletes, across all sports, that they participate to enhance their overall college experience and for the love of their sport, not to be paid,\" said the statement from NCAA chief legal officer Donald Remy. \"While improvements need to be made, we do not need to completely throw away a system that has helped literally millions of students over the past decade alone attend college.\n" +
            "\"We want student-athletes -- 99 percent of whom will never make it to the professional leagues -- focused on what matters most -- finding success in the classroom, on the field and in life.\"\n" +
            "Last week, Northwestern University's president emeritus said that if the football players were successful forming a union, he could see the prestigious private institution giving up Division I football.\n" +
            "\"If we got into collective bargaining situations, I would not take for granted that the Northwesterns of the world would continue to play Division I sports,\" Henry Bienen said at the annual conference for the Knight Commission on Intercollegiate Athletics.\n" +
            "He further said that if the players won their fight, private institutions with high academic standards -- he specifically cited Duke and Stanford -- could abandon the current model in order to preserve academic integrity.\n" +
            "He compared it to the pullback of the Ivy League schools decades ago, when the Ivy League conference decided to opt out of postseason play and to end athletic scholarships, preserving the emphasis on academics for the players.\n" +
            "\"In the 1950s, the 'Ivies' had some of the highest-ranked football teams in the country. The Princeton teams were ranked in the top 5 or 10 at that time. They continue periodically to have ranked basketball teams, but they've given up a certain kind of model of sports,\" he said, adding that \"under certain conditions\" the same could happen at other private elite universities that \"continue to play big time sports.\"\n" +
            "Jerry Price, senior associate athletic director at Princeton, said that change for the Ivy League allowed those schools to maintain academic integrity in the sports where, at other schools, academics can often be compromised in the name of the game.\n" +
            "\"It was sort of a breaking point moment,\" Price said, saying the Ivy League schools made the decision not to move forward like the bigger conferences -- to \"draw the line with the commercialization of what football was becoming.\"\n" +
            "\"And the results have been that Ivy football is not what it was in the first half of the 20th century,\" Price said. \"Certainly not like Big Ten football, SEC football. Its crowds are generally less than 10,000 people. They play only 10 games a year. ... Certainly not what is going on at BCS level.\"\n" +
            "Bienen, who was president of Northwestern from 1995 to 2009, made his comments during a panel discussion that included a presentation from Ramogi Huma, the president of the National College Players Association (NCPA) and the man who helped organize former Northwestern quarterback Kain Colter to lead a unionization attempt.\n" +
            "Huma talked, as he has for months, about the issues his organization sees as great flaws in the current NCAA model. The NCPA believes that athletes in the revenue-generating sports of college football and men's basketball are taken advantage of by universities, conferences and the NCAA, making billions from games, while the players sometimes struggle with basic needs like medical care, concussion testing and guaranteed scholarships.\n" +
            "In March, the NCPA took its fight before the NLRB in Chicago and presented a case during a five-day hearing. Both sides recently submitted court briefs.\n" +
            "Northwestern's appeal could go as far as the U.S. Supreme Court, and it could take years before there is a definitive decision.\n" +
            "During his daylong testimony last week, Colter talked about year-round time requirements, at times 50 hours a week devoted to football.\n" +
            "Colter said he had to give up his major related to pre-med studies because he couldn't fit the classes into his schedule. The university countered that by bringing in students who were able to stay in rigorous classes, but Colter's sentiment was echoed by the NCAA itself in a 2012 survey that asked athletes what they would change about their college experience.\n" +
            "About 15% of men's football, baseball and basketball players said they would have had different majors had they not been athletes. Twelve percent of Division I football players said athletics prevented them from majoring in what they wanted. The average time spent on athletics in-season hovered around 40 hours per week for all three sports, according to the survey.\n" +
            "That flies in the face of the NCAA 20-hour rule, which states that, no matter the sport, coaches can't take up more than 20 hours of their players' time.";

    @Test
    public void positiveSentimentTest1() {

    }

    @Test
    public void cnnSportsTextTest() {
        Scorer scorer = Scorer.getInstance();
        int score = scorer.score(cnnSportsText);
        assertEquals("Wrong score", 17, score);
    }

    @Test
    public void negativeSentimentTest1() {
        Scorer scorer = Scorer.getInstance();
        int score = scorer.score(negativeText1);
        assertEquals("Score didn't match", -1, score);
    }
}
