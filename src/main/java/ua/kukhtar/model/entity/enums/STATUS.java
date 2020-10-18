package ua.kukhtar.model.entity.enums;

public enum  STATUS {
    WAITING_FOR_RESPONSE(true), WAITING_FOR_PAYMENT(true), PAID(true), CANCELED(false), IN_PROCESS(true), DONE(false);

    STATUS(boolean isActive){
        this.isActive = isActive;
    }
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }
}
