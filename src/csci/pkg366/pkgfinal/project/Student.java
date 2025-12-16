package csci.pkg366.pkgfinal.project;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * Console-driven "Student" features backed by PostgreSQL.
 *
 * Notes:
 * - This class intentionally uses JDBC (via DatabaseConnection) and avoids
 *   relying on JPA mappings, because the schema details in sql.txt are incomplete.
 * - It tries to be resilient to small column-name differences by using metadata
 *   and reflection where needed.
 * 
 * @author Hariom Pughat
 */
public class Student {

    private final Users user;
    private final Scanner scan;

    public Student(Users user) {
        this(user, new Scanner(System.in));
    }

    public Student(Users user, Scanner scan) {
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.scan = Objects.requireNonNull(scan, "scan cannot be null");
    }

    /**
     * Shows tests available (returns a formatted string).
     */
    public String ViewAvailableTests() {
        StringBuilder out = new StringBuilder();
        out.append("==== Available Tests ====\n");

        // Prefer a simple, likely schema; fall back to SELECT * if needed.
        List<String> queries = List.of(
            "SELECT test_id, title FROM tests ORDER BY test_id",
            "SELECT test_id, test_name FROM tests ORDER BY test_id",
            "SELECT * FROM tests ORDER BY 1"
        );

        try (Connection conn = DatabaseConnection.getConnection()) {
            boolean any = false;

            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    any = true;
                    ResultSetMetaData md = rs.getMetaData();
                    int cols = md.getColumnCount();

                    while (rs.next()) {
                        // If we got the "nice" columns, format as "id - name/title".
                        if (hasColumn(md, "test_id") && (hasColumn(md, "title") || hasColumn(md, "test_name"))) {
                            int id = rs.getInt(findColumn(md, "test_id"));
                            String name = null;
                            if (hasColumn(md, "title")) name = rs.getString(findColumn(md, "title"));
                            else if (hasColumn(md, "test_name")) name = rs.getString(findColumn(md, "test_name"));
                            out.append(id).append(" - ").append(name == null ? "" : name).append("\n");
                        } else {
                            // Generic formatting: print first few columns to avoid huge output.
                            out.append("- ");
                            int show = Math.min(cols, 4);
                            for (int i = 1; i <= show; i++) {
                                if (i > 1) out.append(" | ");
                                out.append(md.getColumnLabel(i)).append("=").append(rs.getString(i));
                            }
                            out.append("\n");
                        }
                    }
                    break; // success on this query
                } catch (SQLException ignored) {
                    // try next query
                }
            }

            if (!any) out.append("(No tests found or unable to query tests table.)\n");
        } catch (SQLException e) {
            out.append("Error loading tests: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    /**
     * Implements a user taking a test (interactive).
     */
    public void TakeTest() {
        System.out.println(ViewAvailableTests());
        System.out.print("Enter test_id to take (or blank to cancel): ");
        String raw = scan.nextLine().trim();
        if (raw.isEmpty()) return;

        int testId;
        try {
            testId = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            System.out.println("Invalid test_id.");
            return;
        }

        Integer userId = extractId(user, "getUserId", "getUser_id", "getUserID", "getId");
        if (userId == null) {
            System.out.println("Cannot determine current user id from Users object.");
            return;
        }

        recordAttempt(connector -> {
            // Fetch questions for test
            List<QuestionRow> questions = loadQuestionsForTest(connector, testId);
            if (questions.isEmpty()) {
                System.out.println("No questions found for test_id=" + testId);
                return;
            }

            int correct = 0;

            for (int qi = 0; qi < questions.size(); qi++) {
                QuestionRow q = questions.get(qi);
                System.out.println();
                System.out.println("Q" + (qi + 1) + ") " + q.text);

                List<AnswerRow> answers = loadAnswersForQuestion(connector, q.questionId);
                if (answers.isEmpty()) {
                    System.out.println("(No answers found for this question.)");
                    continue;
                }

                for (int i = 0; i < answers.size(); i++) {
                    System.out.println("  " + (i + 1) + ") " + answers.get(i).text);
                }

                System.out.print("Your choice (1-" + answers.size() + ", blank to skip): ");
                String choice = scan.nextLine().trim();
                if (choice.isEmpty()) continue;

                int idx;
                try {
                    idx = Integer.parseInt(choice) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice; skipped.");
                    continue;
                }
                if (idx < 0 || idx >= answers.size()) {
                    System.out.println("Out of range; skipped.");
                    continue;
                }

                AnswerRow selected = answers.get(idx);
                if (selected.isCorrect != null) {
                    if (selected.isCorrect) correct++;
                } else {
                    // If schema doesn't expose correctness, we can't score reliably.
                    // Still proceed; score will reflect only determinable questions.
                }
            }

            int total = questions.size();
            System.out.println();
            System.out.println("Finished! Your Score Is: " + correct + " / " + total);

            // Save result
            saveResult(connector, userId, testId, correct, total);
        });
    }

    /**
     * Shows the user's past test data (returns a formatted string).
     */
    public String ViewPastTests() {
        StringBuilder out = new StringBuilder();
        out.append("==== Past Tests ====\n");

        Integer userId = extractId(user, "getUserId", "getUser_id", "getUserID", "getId");
        if (userId == null) {
            out.append("Cannot determine current user id from Users object.\n");
            return out.toString();
        }

        List<String> queries = List.of(
            """
            SELECT r.result_id, r.test_id, t.title, r.score
            FROM results r
            JOIN tests t ON t.test_id = r.test_id
            WHERE r.user_id = ?
            ORDER BY r.result_id DESC
            """,
            "SELECT result_id, test_id, score FROM results WHERE user_id = ? ORDER BY result_id DESC"
        );

        try (Connection conn = DatabaseConnection.getConnection()) {
            boolean ran = false;

            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        ran = true;
                        ResultSetMetaData md = rs.getMetaData();

                        boolean hasTitle = hasColumn(md, "title");
                        int resultIdCol = findAnyColumn(md, "result_id");
                        int testIdCol = findAnyColumn(md, "test_id");
                        int scoreCol = findAnyColumn(md, "score");
                        int titleCol = hasTitle ? findAnyColumn(md, "title") : -1;

                        out.append(String.format("%-10s %-8s %-28s %-6s%n", "ResultID", "TestID", "Title", "Score"));
                        out.append(String.format("%-10s %-8s %-28s %-6s%n", "--------", "------", "-----", "-----"));

                        int rows = 0;
                        while (rs.next()) {
                            rows++;
                            String rid = rs.getString(resultIdCol);
                            String tid = rs.getString(testIdCol);
                            String score = rs.getString(scoreCol);
                            String title = hasTitle ? rs.getString(titleCol) : "";

                            if (title == null) title = "";
                            title = title.replaceAll("\\s+", " ").trim();
                            if (title.length() > 28) title = title.substring(0, 25) + "...";

                            out.append(String.format(
                                    "%-10s %-8s %-28s %-6s%n",
                                    rid == null ? "" : rid,
                                    tid == null ? "" : tid,
                                    title,
                                    score == null ? "" : score
                            ));
                        }

                        if (rows == 0) out.append("(No past results found.)\n");
                    }
                    break; // success
                } catch (SQLException ignored) {
                    // try next
                }
            }

            if (!ran) out.append("(Unable to query results table.)\n");
        } catch (SQLException e) {
            out.append("Error loading past tests: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    /**
     * Shows the result of the selected test for the current user (returns a formatted string).
     */
    public String ViewResults(Tests t) {
        StringBuilder out = new StringBuilder();
        out.append("==== Results ====\n");

        Integer userId = extractId(user, "getUserId", "getUser_id", "getUserID", "getId");
        if (userId == null) {
            out.append("Cannot determine current user id from Users object.\n");
            return out.toString();
        }

        Integer testId = extractId(t, "getTestId", "getTest_id", "getTestID", "getId");
        if (testId == null) {
            out.append("Cannot determine test id from Tests object.\n");
            return out.toString();
        }

        // Pull the most recent result for that test/user.
        List<String> queries = List.of(
                """
            SELECT r.result_id, r.test_id, t.title, r.score
            FROM results r
            JOIN tests t ON t.test_id = r.test_id
            WHERE r.user_id = ? AND r.test_id = ?
            ORDER BY r.result_id DESC
            LIMIT 1
            """,
            """
            SELECT r.*
            FROM results r
            WHERE r.user_id = ? AND r.test_id = ?
            ORDER BY r.result_id DESC
            LIMIT 1
                """
        );

        try (Connection conn = DatabaseConnection.getConnection()) {
            boolean found = false;

            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, testId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) continue;

                        found = true;
                        ResultSetMetaData md = rs.getMetaData();
                        int cols = md.getColumnCount();

                        for (int i = 1; i <= cols; i++) {
                            out.append(md.getColumnLabel(i)).append(": ").append(rs.getString(i)).append("\n");
                        }
                    }
                    break;
                } catch (SQLException ignored) {
                    // try next
                }
            }

            if (!found) out.append("(No result found for user_id=").append(userId)
                           .append(" and test_id=").append(testId).append(")\n");

        } catch (SQLException e) {
            out.append("Error loading results: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // -----------------------
    // Internal helpers
    // -----------------------

    private interface ConnectionConsumer {
        void accept(Connection conn) throws SQLException;
    }

    private void recordAttempt(ConnectionConsumer action) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            action.accept(conn);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static List<QuestionRow> loadQuestionsForTest(Connection conn, int testId) throws SQLException {
        List<String> queries = List.of(
                """
                SELECT q.question_id, q.question_text
            FROM test_questions tq
            JOIN questions q ON q.question_id = tq.question_id
                WHERE tq.test_id = ?
                ORDER BY q.question_id
                """,
                """
                SELECT q.question_id, q.text
            FROM test_questions tq
            JOIN questions q ON q.question_id = tq.question_id
                WHERE tq.test_id = ?
                ORDER BY q.question_id
                """
        );

        for (String sql : queries) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, testId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<QuestionRow> out = new ArrayList<>();
                    ResultSetMetaData md = rs.getMetaData();

                    int idCol = findAnyColumn(md, "question_id", "id");
                    int textCol = findAnyColumn(md, "question_text", "text", "prompt");

                    while (rs.next()) {
                        int qid = rs.getInt(idCol);
                        String qtext = rs.getString(textCol);
                        out.add(new QuestionRow(qid, qtext));
                    }
                    return out;
                }
            } catch (SQLException ignored) {
                // try next
            }
        }
        return List.of();
    }

    private static List<AnswerRow> loadAnswersForQuestion(Connection conn, int questionId) throws SQLException {
        // We attempt to read "is_correct" if present; if not, keep null (unknown scoring).
        String sql = "SELECT * FROM answers WHERE question_id = ? ORDER BY answer_id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();

                int textCol = findAnyColumn(md, "answer_text", "text", "answer", "choice");
                Integer correctCol = findAnyColumnOrNull(md, "is_correct", "correct", "isCorrect");

                List<AnswerRow> out = new ArrayList<>();
                while (rs.next()) {
                    String text = rs.getString(textCol);
                    Boolean isCorrect = null;

                    if (correctCol != null) {
                        Object v = rs.getObject(correctCol);
                        if (v instanceof Boolean b) isCorrect = b;
                        else if (v instanceof Number n) isCorrect = n.intValue() != 0;
                        else if (v != null) {
                            String s = v.toString().trim().toLowerCase(Locale.ROOT);
                            if (s.equals("t") || s.equals("true") || s.equals("1") || s.equals("yes")) isCorrect = true;
                            else if (s.equals("f") || s.equals("false") || s.equals("0") || s.equals("no")) isCorrect = false;
                        }
                    }

                    out.add(new AnswerRow(text, isCorrect));
                }
                return out;
            }
        }
    }

    private static void saveResult(Connection conn, int userId, int testId, int correct, int total) throws SQLException {
        List<String> inserts = List.of(
            "INSERT INTO results (user_id, test_id, score) VALUES (?, ?, ?)"
        );

        for (String sql : inserts) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, testId);
                ps.setInt(3, correct);
                ps.executeUpdate();
                return;
            } catch (SQLException ignored) {
                // try next
            }
        }

        throw new SQLException("Unable to insert into results.");
    }

    private static Integer extractId(Object obj, String... getterCandidates) {
        if (obj == null) return null;
        for (String m : getterCandidates) {
            try {
                Method method = obj.getClass().getMethod(m);
                Object v = method.invoke(obj);
                if (v instanceof Number n) return n.intValue();
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return null;
    }

    private static boolean hasColumn(ResultSetMetaData md, String columnName) throws SQLException {
        return findColumn(md, columnName) != -1;
    }

    private static int findColumn(ResultSetMetaData md, String columnName) throws SQLException {
        int cols = md.getColumnCount();
        for (int i = 1; i <= cols; i++) {
            String label = md.getColumnLabel(i);
            String name = md.getColumnName(i);
            if (columnName.equalsIgnoreCase(label) || columnName.equalsIgnoreCase(name)) return i;
        }
        return -1;
    }

    private static int findAnyColumn(ResultSetMetaData md, String... names) throws SQLException {
        for (String n : names) {
            int c = findColumn(md, n);
            if (c != -1) return c;
        }
        // If none match, return first column as a last resort (prevents hard crash).
        return 1;
    }

    private static Integer findAnyColumnOrNull(ResultSetMetaData md, String... names) throws SQLException {
        for (String n : names) {
            int c = findColumn(md, n);
            if (c != -1) return c;
        }
        return null;
    }

    private static final class QuestionRow {
        final int questionId;
        final String text;

        QuestionRow(int questionId, String text) {
            this.questionId = questionId;
            this.text = text == null ? "" : text;
        }
    }

    private static final class AnswerRow {
        final String text;
        final Boolean isCorrect; // null if unknown

        AnswerRow(String text, Boolean isCorrect) {
            this.text = text == null ? "" : text;
            this.isCorrect = isCorrect;
        }
    }
}