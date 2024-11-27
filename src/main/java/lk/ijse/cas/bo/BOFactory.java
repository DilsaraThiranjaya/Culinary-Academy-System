package lk.ijse.cas.bo;


import lk.ijse.cas.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        COURSE,
        DASHBOARD,
        FORGOT_PASSWORD,
        LOGIN,
        PAYMENT,
        SETTINGS,
        STUDENT
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case COURSE:
                return new CourseBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case FORGOT_PASSWORD:
                return new ForgotPasswordBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SETTINGS:
                return new SettingsBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            default:
                return null;
        }
    }

}
