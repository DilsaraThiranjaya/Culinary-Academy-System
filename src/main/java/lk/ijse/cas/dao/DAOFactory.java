package lk.ijse.cas.dao;


import lk.ijse.cas.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        ATTENDANCE,
        COURSE,
        COURSE_DETAILS,
        EMPLOYEE,
        EXAM,
        EXAM_DETAILS,
        LESSON_SCHEDULE,
        LESSON_SCHEDULE_DETAILS,
        NOTE,
        PAYMENT,
        PAYMENT_DETAILS,
        SALARY,
        STUDENT,
        STUDENT_DETAILS,
        USER,
        VEHICLE,
        VEHICLE_DETAILS,
        VEHICLE_MAINTENANCE,
        QUERY_DAO
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case COURSE:
                return new CourseDAOImpl();
            case COURSE_DETAILS:
                return new CourseDetailsDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case EXAM:
                return new ExamDAOImpl();
            case EXAM_DETAILS:
                return new ExamDetailsDAOImpl();
            case LESSON_SCHEDULE:
                return new LessonScheduleDAOImpl();
            case LESSON_SCHEDULE_DETAILS:
                return new LessonScheduleDetailsDAOImpl();
            case NOTE:
                return new NoteDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PAYMENT_DETAILS:
                return new PaymentDetailsDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case VEHICLE_DETAILS:
                return new VehicleDetailsDAOImpl();
            case VEHICLE_MAINTENANCE:
                return new VehicleMaintenanceDAOImpl();
            default:
                return null;
        }
    }


}
