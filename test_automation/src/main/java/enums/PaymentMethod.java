package enums;

import java.io.Serializable;


public enum PaymentMethod implements Serializable {

    CREDITCARD(0),
    DIRECTDEBIT(1),
    AFTERINVOICE(2),
    EPURSE(3);
    
    private int value;

    PaymentMethod(int value){
        this.value = value;
    }

    public int getPaymentMethod(){
        return value;
    }

    public static PaymentMethod fromInt(int DBId) {
          switch (DBId){
            case 0: return CREDITCARD;
            case 1: return DIRECTDEBIT;
            case 2: return AFTERINVOICE;
            case 3: return EPURSE;
            
        }
        return null;    
    }
}
