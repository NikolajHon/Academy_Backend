package sk.posam.fsa.discussion;

public class AnswerResult {
    private Long questionId;
    private boolean correct;

    public AnswerResult() { }

    public AnswerResult(Long questionId, boolean correct) {
        this.questionId = questionId;
        this.correct = correct;
    }

    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
