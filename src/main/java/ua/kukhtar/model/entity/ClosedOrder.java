package ua.kukhtar.model.entity;

public class ClosedOrder extends Order {
    private String feedBack;

    @Override
    public String getFeedBack() {
        return feedBack;
    }

    @Override
    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }
}
