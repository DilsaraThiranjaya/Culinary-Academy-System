# Write a Join query for students who have registered for all culinary programs using HQL:

String hql = "SELECT s FROM Student s "
        + "JOIN s.programDetails pd "
        + "GROUP BY s.studentId "
        + "HAVING COUNT(DISTINCT pd.programId) = (SELECT COUNT(*) FROM Program)";

List<Student> studentsInAllPrograms = session.createQuery(hql, Student.class).list();

-----------------------------------------------------------------------------------------------------

# Retrieve students along with their enrolled courses using a relationship query:

String hql = "SELECT s, pd.programId, p.name "
        + "FROM Student s "
        + "JOIN s.programDetails pd "
        + "JOIN pd.program p";

List<Object[]> results = session.createQuery(hql).list();
for (Object[] row : results) {
    Student student = (Student) row[0];
    String programId = (String) row[1];
    String programName = (String) row[2];
// Process the student, programId, and programName
}