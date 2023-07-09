package ge.carapp.carappapi.models.bog;

public enum PaymentOption{
   DIRECT_DEBIT,
   RECURRENT,
   SUBSCRIPTION;
    public String toLower() {
        return this.name().toLowerCase();
    }
}
