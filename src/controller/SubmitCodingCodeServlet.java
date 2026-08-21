package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dao.CodingQuestionDAO;
import dao.CodingSubmissionDAO;
import dao.CodingTestCaseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.CodingQuestion;
import model.CodingTestCase;
import model.User;

@WebServlet("/SubmitCodingCodeServlet")
public class SubmitCodingCodeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check login
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // ==========================================
        // 2. Only STUDENT can submit code
        // ==========================================

        User user = (User) session.getAttribute("user");

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // ==========================================
        // 3. Get form data
        // ==========================================

        String codingQuestionIdParameter =
                request.getParameter("codingQuestionId");

        String sourceCode =
                request.getParameter("sourceCode");

        if (codingQuestionIdParameter == null ||
            sourceCode == null ||
            sourceCode.trim().isEmpty()) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        int codingQuestionId;

        try {

            codingQuestionId =
                    Integer.parseInt(codingQuestionIdParameter);

        } catch (NumberFormatException e) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        // ==========================================
        // 4. Get coding question
        // ==========================================

        CodingQuestionDAO codingQuestionDAO =
                new CodingQuestionDAO();

        CodingQuestion codingQuestion =
                codingQuestionDAO.getCodingQuestionById(
                        codingQuestionId
                );

        if (codingQuestion == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        // ==========================================
        // 5. Get test cases
        // ==========================================

        CodingTestCaseDAO testCaseDAO =
                new CodingTestCaseDAO();

        List<CodingTestCase> testCases =
                testCaseDAO.getTestCasesByQuestionId(
                        codingQuestionId
                );

        // ==========================================
        // 6. No test cases
        // ==========================================

        if (testCases == null ||
            testCases.isEmpty()) {

            request.setAttribute(
                    "status",
                    "NO_TEST_CASES"
            );

            request.setAttribute(
                    "message",
                    "No test cases are available for this coding question."
            );

            request.setAttribute(
                    "passedTests",
                    0
            );

            request.setAttribute(
                    "totalTests",
                    0
            );

            request.setAttribute(
                    "score",
                    0
            );

            request.getRequestDispatcher(
                    "coding-result.jsp"
            ).forward(request, response);

            return;
        }

        // ==========================================
        // 7. Create temporary directory
        // ==========================================

        Path tempDirectory =
                Files.createTempDirectory(
                        "online_quiz_"
                );

        File workingDirectory =
                tempDirectory.toFile();

        File javaFile =
                new File(
                        workingDirectory,
                        "Main.java"
                );

        try {

            // ==========================================
            // 8. Save student's source code
            // ==========================================

            Files.writeString(
                    javaFile.toPath(),
                    sourceCode,
                    StandardCharsets.UTF_8
            );

            // ==========================================
            // 9. Compile Java program
            // ==========================================

            ProcessBuilder compileProcessBuilder =
                    new ProcessBuilder(
                            "javac",
                            "Main.java"
                    );

            compileProcessBuilder.directory(
                    workingDirectory
            );

            compileProcessBuilder.redirectErrorStream(true);

            Process compileProcess =
                    compileProcessBuilder.start();

            // Wait for compilation
            boolean compilationFinished =
                    compileProcess.waitFor(
                            10,
                            TimeUnit.SECONDS
                    );

            String compilationOutput =
                    readProcessOutput(
                            compileProcess
                    );

            // ==========================================
            // 10. Compilation timeout
            // ==========================================

            if (!compilationFinished) {

                compileProcess.destroyForcibly();

                saveSubmission(
                        codingQuestionId,
                        user.getId(),
                        sourceCode,
                        "COMPILATION_TIMEOUT",
                        0,
                        testCases.size(),
                        0
                );

                showResult(
                        request,
                        response,
                        "COMPILATION_TIMEOUT",
                        0,
                        testCases.size(),
                        0,
                        "Compilation took too long.",
                        ""
                );

                return;
            }

            // ==========================================
            // 11. Compilation error
            // ==========================================

            if (compileProcess.exitValue() != 0) {

                saveSubmission(
                        codingQuestionId,
                        user.getId(),
                        sourceCode,
                        "COMPILATION_ERROR",
                        0,
                        testCases.size(),
                        0
                );

                showResult(
                        request,
                        response,
                        "COMPILATION_ERROR",
                        0,
                        testCases.size(),
                        0,
                        "Your program could not be compiled.",
                        compilationOutput
                );

                return;
            }

            // ==========================================
            // 12. Run all test cases
            // ==========================================

            int passedTests = 0;

            String failureType = "";

            String errorMessage = "";

            for (CodingTestCase testCase : testCases) {

                TestExecutionResult result =
                        runTestCase(
                                workingDirectory,
                                testCase.getInputData(),
                                testCase.getExpectedOutput()
                        );

                if (result.passed) {

                    passedTests++;

                } else {

                    failureType =
                            result.status;

                    errorMessage =
                            result.message;

                    break;
                }
            }

            // ==========================================
            // 13. Calculate score
            // ==========================================

            int totalTests =
                    testCases.size();

            int score =
                    (passedTests * 100) / totalTests;

            String status;

            if (passedTests == totalTests) {

                status = "ACCEPTED";

            } else {

                status = failureType;
            }

            // ==========================================
            // 14. Save submission
            // ==========================================

            saveSubmission(
                    codingQuestionId,
                    user.getId(),
                    sourceCode,
                    status,
                    passedTests,
                    totalTests,
                    score
            );

            // ==========================================
            // 15. Show result
            // ==========================================

            showResult(
                    request,
                    response,
                    status,
                    passedTests,
                    totalTests,
                    score,
                    errorMessage,
                    ""
            );

        } catch (Exception e) {

            e.printStackTrace();

            saveSubmission(
                    codingQuestionId,
                    user.getId(),
                    sourceCode,
                    "SYSTEM_ERROR",
                    0,
                    testCases.size(),
                    0
            );

            showResult(
                    request,
                    response,
                    "SYSTEM_ERROR",
                    0,
                    testCases.size(),
                    0,
                    e.getMessage(),
                    ""
            );

        } finally {

            // ==========================================
            // 16. Delete temporary files
            // ==========================================

            deleteDirectory(
                    workingDirectory
            );
        }
    }


    // =================================================
    // Run one test case
    // =================================================

    private TestExecutionResult runTestCase(
            File workingDirectory,
            String input,
            String expectedOutput)
            throws IOException, InterruptedException {

        ProcessBuilder runProcessBuilder =
                new ProcessBuilder(
                        "java",
                        "-cp",
                        ".",
                        "Main"
                );

        runProcessBuilder.directory(
                workingDirectory
        );

        runProcessBuilder.redirectErrorStream(true);

        Process process =
                runProcessBuilder.start();

        // Send input
        try (OutputStream outputStream =
                     process.getOutputStream()) {

            outputStream.write(
                    input.getBytes(
                            StandardCharsets.UTF_8
                    )
            );

            outputStream.write(
                    System.lineSeparator()
                            .getBytes(
                                    StandardCharsets.UTF_8
                            )
            );

            outputStream.flush();
        }

        // Give program 5 seconds
        boolean finished =
                process.waitFor(
                        5,
                        TimeUnit.SECONDS
                );

        // ==========================================
        // Time limit exceeded
        // ==========================================

        if (!finished) {

            process.destroyForcibly();

            return new TestExecutionResult(
                    false,
                    "TIME_LIMIT_EXCEEDED",
                    "Your program took too long to execute."
            );
        }

        // ==========================================
        // Read program output
        // ==========================================

        String actualOutput =
                readProcessOutput(process);

        // ==========================================
        // Runtime error
        // ==========================================

        if (process.exitValue() != 0) {

            return new TestExecutionResult(
                    false,
                    "RUNTIME_ERROR",
                    actualOutput
            );
        }

        // ==========================================
        // Normalize output
        // ==========================================

        String normalizedActual =
                normalizeOutput(actualOutput);

        String normalizedExpected =
                normalizeOutput(expectedOutput);

        // ==========================================
        // Wrong answer
        // ==========================================

        if (!normalizedActual.equals(
                normalizedExpected)) {

            String message =
                    "Expected Output:\n"
                    + expectedOutput
                    + "\n\nYour Output:\n"
                    + actualOutput;

            return new TestExecutionResult(
                    false,
                    "WRONG_ANSWER",
                    message
            );
        }

        // ==========================================
        // Test passed
        // ==========================================

        return new TestExecutionResult(
                true,
                "PASSED",
                ""
        );
    }


    // =================================================
    // Read process output
    // =================================================

    private String readProcessOutput(
            Process process)
            throws IOException {

        StringBuilder output =
                new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     process.getInputStream(),
                                     StandardCharsets.UTF_8
                             )
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {

                output.append(line);
                output.append(System.lineSeparator());
            }
        }

        return output.toString().trim();
    }


    // =================================================
    // Normalize output
    // =================================================

    private String normalizeOutput(
            String output) {

        if (output == null) {
            return "";
        }

        return output
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim();
    }


    // =================================================
    // Save submission
    // =================================================

    private void saveSubmission(
            int codingQuestionId,
            int studentId,
            String sourceCode,
            String status,
            int passedTests,
            int totalTests,
            int score) {

        CodingSubmissionDAO submissionDAO =
                new CodingSubmissionDAO();

        submissionDAO.saveSubmission(
                codingQuestionId,
                studentId,
                sourceCode,
                status,
                passedTests,
                totalTests,
                score
        );
    }


    // =================================================
    // Send result to JSP
    // =================================================

    private void showResult(
            HttpServletRequest request,
            HttpServletResponse response,
            String status,
            int passedTests,
            int totalTests,
            int score,
            String message,
            String compilationError)
            throws ServletException, IOException {

        request.setAttribute(
                "status",
                status
        );

        request.setAttribute(
                "passedTests",
                passedTests
        );

        request.setAttribute(
                "totalTests",
                totalTests
        );

        request.setAttribute(
                "score",
                score
        );

        request.setAttribute(
                "message",
                message
        );

        request.setAttribute(
                "compilationError",
                compilationError
        );

        request.getRequestDispatcher(
                "coding-result.jsp"
        ).forward(
                request,
                response
        );
    }


    // =================================================
    // Delete temporary directory
    // =================================================

    private void deleteDirectory(
            File directory) {

        if (directory == null ||
            !directory.exists()) {

            return;
        }

        File[] files =
                directory.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isDirectory()) {

                    deleteDirectory(file);

                } else {

                    file.delete();
                }
            }
        }

        directory.delete();
    }


    // =================================================
    // Result class
    // =================================================

    private static class TestExecutionResult {

        boolean passed;

        String status;

        String message;

        TestExecutionResult(
                boolean passed,
                String status,
                String message) {

            this.passed = passed;
            this.status = status;
            this.message = message;
        }
    }
}