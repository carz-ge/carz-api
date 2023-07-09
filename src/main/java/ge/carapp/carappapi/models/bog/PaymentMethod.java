package ge.carapp.carappapi.models.bog;

public enum PaymentMethod {
    CARD, // payment by a bank card
    GOOGLE_PAY, //payment through Google Pay
    BOG_P2P, // transferring by a customer of the Bank of Georgia, internet or mobile banking
    BOG_LOYALTY, // payment by BoG MR/Plus points
    BNPL, // payment in installments
    BOG_LOAN,// standard bank installment plan.
}
