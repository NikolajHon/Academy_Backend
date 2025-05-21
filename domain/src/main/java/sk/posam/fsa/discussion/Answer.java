package sk.posam.fsa.discussion;

public class Answer {

    private Long id;
    private String answer_body;

    public String getAnswer_body() {
        return answer_body;
    }

    public void setAnswer_body(String answer_body) {
        this.answer_body = answer_body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
