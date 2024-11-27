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
        COURSE,
        COURSE_DETAILS,
        PAYMENT,
        PAYMENT_DETAILS,
        STUDENT,
        STUDENT_DETAILS,
        USER,
        QUERY_DAO
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case COURSE:
                return new CourseDAOImpl();
            case COURSE_DETAILS:
                return new CourseDetailsDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PAYMENT_DETAILS:
                return new PaymentDetailsDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }


}
