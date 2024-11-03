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
        ADD_NOTE,
        ATTENDANCE,
        COURSE,
        DASHBOARD,
        EMPLOYEE,
        EXAM,
        FORGOT_PASSWORD,
        LESSON_SCHEDULE,
        LOGIN,
        PAYMENT,
        REGISTOR,
        SALARY,
        SETTINGS,
        STUDENT,
        TASKBAR,
        VEHICLE,
        VEHICLE_MAINTENANCE
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case ADD_NOTE:
                return new AddNoteBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case COURSE:
                return new CourseBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case EXAM:
                return new ExamBOImpl();
            case FORGOT_PASSWORD:
                return new ForgotPasswordBOImpl();
            case LESSON_SCHEDULE:
                return new LessonScheduleBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case REGISTOR:
                return new RegistorBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SETTINGS:
                return new SettingsBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case TASKBAR:
                return new TaskBarBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case VEHICLE_MAINTENANCE:
                return new VehicleMaintenanceBOImpl();
            default:
                return null;
        }
    }

}
