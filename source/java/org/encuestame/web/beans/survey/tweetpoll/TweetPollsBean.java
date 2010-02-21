/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.web.beans.survey.tweetpoll;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.ISurveyService;
import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.survey.UnitAnswersBean;
import org.richfaces.component.html.HtmlDataTable;

/**
 * Tweet Polls Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 19, 2010 6:31:47 PM
 * @version $Id:$
 */
public class TweetPollsBean extends MasterBean {

    /** DataTable. **/
    private HtmlDataTable tweetDataTable;

    /** Constructor. **/
    public TweetPollsBean() {
    }

    /** List Tweets. **/
    public List<UnitTweetPoll> listTweets = new ArrayList<UnitTweetPoll>();

    /** Selected {@link UnitTweetPoll}. **/
    public UnitTweetPoll selectedTweetPoll = new UnitTweetPoll();

    /** Edit Answer. **/
    private Boolean editAnswer = false;

    /** Answer Id Update **/
    private Long answerIdUpdate;

    private String answerName;

    /**
     *
     */
    private void loadTweets() {
        try {
            this.listTweets = getServicemanager().getApplicationServices()
                    .getSecurityService().getSurveyService()
                    .getTweetsPollsByUserId(
                            getUsernameByName().getSecUser().getUid());
            log.info("loading tweet polls");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Change Edit.
     */
    public void changeEdit(){
         this.editAnswer = !this.editAnswer;
    }

    /**
     * Save Answer.
     */
    public void saveAnswer(){
        log.info("Answer Id "+getAnswerIdUpdate());
        getUpdateItem(getAnswerIdUpdate());
        if(getAnswerIdUpdate() != null && getUpdateItem(getAnswerIdUpdate())!= null){
            try {
                getServicemanager().getApplicationServices().getSecurityService().
                getSurveyService().updateAnswerByAnswerId(getAnswerIdUpdate(), getUpdateItem(getAnswerIdUpdate()));
                addInfoMessage("Updated Answer", " New name answer update to ["+getUpdateItem(getAnswerIdUpdate())+"]");
            } catch (EnMeExpcetion e) {
                log.error(e);
                e.printStackTrace();
                addErrorMessage(e.getMessage(), e.getMessage());
            }
        }else{
            addErrorMessage("Can not update Answer", "Can not update Answer");
        }
    }

    /**
     * Publish Tweeter.
     */
    public void publishTweet() {
        try {
            final ISurveyService survey = getServicemanager().getApplicationServices().getSecurityService()
            .getSurveyService();
            final String tweetText = survey.generateTweetPollText(getSelectedTweetPoll());
            final SecUsers user = getUsernameByName().getSecUser();
            survey.publicTweetPoll(tweetText, user.getTwitterAccount(), user.getTwitterPassword());
        } catch (EnMeExpcetion e) {
            addErrorMessage("Error Publishing Tweet Poll", e.getMessage());
            e.printStackTrace();
            log.error(e);
        }
    }

    /**
     *
     */
    public void saveChanges(){

    }

    /**
     * Get Update Answer by Id.
     * @param updateId
     * @return
     */
    private String getUpdateItem(final Long updateId){
        String updateName = null;
        for (UnitAnswersBean answer : getSelectedTweetPoll().getQuestionBean().getListAnswers()) {
            if(answer.getAnswerId().equals(updateId)){
                 updateName  = answer.getAnswers();
            }
        }
        return updateName;
    }

    /**
     * @return the listTweets
     */
    public List<UnitTweetPoll> getListTweets() {
        loadTweets();
        return listTweets;
    }

    /**
     * @param listTweets
     *            the listTweets to set
     */
    public void setListTweets(final List<UnitTweetPoll> listTweets) {
        this.listTweets = listTweets;
    }

    /**
     * See Details.
     */
    public void seeDetails() {
        final UnitTweetPoll item = (UnitTweetPoll) getTweetDataTable()
                .getRowData();
    }

    /**
     * @return the tweetDataTable
     */
    public HtmlDataTable getTweetDataTable() {
        return tweetDataTable;
    }

    /**
     * @param tweetDataTable
     *            the tweetDataTable to set
     */
    public void setTweetDataTable(final HtmlDataTable tweetDataTable) {
        this.tweetDataTable = tweetDataTable;
    }

    /**
     * @return the selectedTweetPoll
     */
    public UnitTweetPoll getSelectedTweetPoll() {
        return selectedTweetPoll;
    }

    /**
     * @param selectedTweetPoll
     *            the selectedTweetPoll to set
     */
    public void setSelectedTweetPoll(final UnitTweetPoll selectedTweetPoll) {
        this.selectedTweetPoll = selectedTweetPoll;
    }

    /**
     * @return the editAnswer
     */
    public Boolean getEditAnswer() {
        return editAnswer;
    }

    /**
     * @param editAnswer the editAnswer to set
     */
    public void setEditAnswer(final Boolean editAnswer) {
        this.editAnswer = editAnswer;
    }

    /**
     * @return the answerIdUpdate
     */
    public Long getAnswerIdUpdate() {
        return answerIdUpdate;
    }

    /**
     * @param answerIdUpdate the answerIdUpdate to set
     */
    public void setAnswerIdUpdate(Long answerIdUpdate) {
        this.answerIdUpdate = answerIdUpdate;
    }

    /**
     * @return the answerName
     */
    public String getAnswerName() {
        return answerName;
    }

    /**
     * @param answerName the answerName to set
     */
    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

}
