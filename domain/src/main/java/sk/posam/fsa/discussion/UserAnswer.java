package sk.posam.fsa.discussion;

import java.util.List;

public class UserAnswer {
    private Long questionId;
    private List<Long> selectedOptionIds;

    public UserAnswer(Long questionId, List<Long> selectedOptionIds) {
        this.questionId = questionId;
        this.selectedOptionIds = selectedOptionIds;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public List<Long> getSelectedOptionIds() {
        return selectedOptionIds;
    }
}
