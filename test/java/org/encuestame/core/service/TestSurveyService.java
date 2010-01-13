/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.util.Collection;
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.test.config.AbstractBeanBaseTest;
import org.encuestame.web.beans.survey.UnitPatternBean;
import org.encuestame.web.beans.survey.UnitQuestionBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Test of {@link SurveyService}
 * @author Picado, Juan juan@encuestame.org
 * @since 05/12/2009 15:04:56
 * @version $Id$
 */

public class TestSurveyService  extends AbstractBeanBaseTest{

    /** {@link SurveyService} */
    @Autowired
    private ISurveyService surveyService;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    /** {@link Questions} */
    private Questions question;

    /** {@link QuestionPattern} **/
    private QuestionPattern pattern;

    /**
     * Before.
     */
    @Before
    public void setterBeans(){
        surveyService.setServiceMail(mailServiceImpl);
    }
    /**
     *
     */
    @Before
    public void serviceInit(){
            this.question = createQuestion("Why the sky is blue?","html");
            this.pattern = createQuestionPattern("html");
    }
    /**
     * Test Load All Questions without questions.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllQuestionsSizeZero() throws EnMeExpcetion{
        final List<UnitQuestionBean> alist = surveyService.loadAllQuestions();
        assertEquals("Should be equals",1, alist.size());
    }

    /**
     * Test Load All Questions.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllQuestions() throws EnMeExpcetion{
      //  this.serviceInit();
        final List<UnitQuestionBean> alist = surveyService.loadAllQuestions();
        assertEquals("Should be equals",1, alist.size());
    }

    /**
     * Load Patter Info Null.
     * @throws EnMeExpcetion exception
     */
    @Test
    @ExpectedException(EnMeExpcetion.class)
    public void testloadPatternInfoNull() throws EnMeExpcetion {
        surveyService.loadPatternInfo(null);
    }

    /**
     * Load Patter Info.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadPatternInfo() throws EnMeExpcetion {
      //  this.serviceInit();
        UnitPatternBean patternBean = new UnitPatternBean(this.pattern.getPatternId(),"descPattern","label",
                "patronType", "template","classpattern","levelpattern","finallity");

    //    patternBean.setId(createQuestionPattern("html").getPatternId());
        System.out.println("Pattern---->"+patternBean.getId());

        patternBean = surveyService.loadPatternInfo(patternBean);
        System.out.println("Pattern PRIMERO---->"+patternBean.getPatronType());
        System.out.println("Pattern SEGUNDO---->"+getPattern().getPatternType());
       // assertNotNull(patternBean);
       assertEquals("Should be equals",patternBean.getPatronType(), getPattern().getPatternType());
    }

    /**
     * Load All Patterns.
     * @throws EnMeExpcetion exception
     */
    @Test
    public void testloadAllPatrons() throws EnMeExpcetion {
       // this.serviceInit();
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
       // assertNotNull(patternList);
        assertEquals("Should be equals",2, patternList.size());
    }

    /**
     * Load All Patterns Zero Results.
     * @throws EnMeExpcetion exception
     */
  //  @Test
    public void testloadAllPatronsZeroResults() throws EnMeExpcetion {
        final Collection<UnitPatternBean> patternList = surveyService.loadAllPatrons();
        assertNotNull(patternList);
        assertEquals("Should be equals",0, patternList.size());
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(ISurveyService surveyService) {
        this.surveyService = surveyService;
    }
    /**
     * @return the question
     */
    public Questions getQuestion() {
        return question;
    }
    /**
     * @return the pattern
     */
    public QuestionPattern getPattern() {
        return pattern;
    }

    /**
     * @param mailServiceImpl the mailServiceImpl to set
     */
    public void setMailServiceImpl(MailServiceImpl mailServiceImpl) {
        this.mailServiceImpl = mailServiceImpl;
    }
}