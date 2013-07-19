package pchelolo.matcher;


import org.junit.Test;

public class PerfTest {

    private static final int REPEATS = 20;

    @Test
    public void testLongClosure() throws Exception {

        String pattern = "(a|b)*";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("a").append("b");
        }
        String testCase = sb.append("b").toString();

        java.util.regex.Pattern javaPattern = null;
        Pattern p = null;

        // Warm up //
        for (int idx = 1; idx < 2*REPEATS; idx++) {
            javaPattern = java.util.regex.Pattern.compile(pattern);
            javaPattern.matcher(testCase).matches();
            p = Pattern.compile(pattern);
            p.matcher(testCase).matches();
        }


        System.out.println("Long closure test");
        System.out.println("\tJava");
        long start = System.nanoTime();
        for (int idx = 0; idx < REPEATS; idx ++) {
            javaPattern = java.util.regex.Pattern.compile(pattern);
        }
        long end = System.nanoTime();
        System.out.println("\t\tCompilation:\t" + (end - start) / REPEATS);
        start = System.nanoTime();
        for (int idx = 0; idx < REPEATS; idx ++) {
            javaPattern.matcher(testCase).matches();
        }
        end = System.nanoTime();
        System.out.println("\t\tExecution:\t" + (end - start) / REPEATS);

        System.out.println("\tCurrent implementation");
        start = System.nanoTime();
        for (int idx = 0; idx < REPEATS; idx ++) {
            p = Pattern.compile(pattern);
        }
        end = System.nanoTime();
        System.out.println("\t\tCompilation:\t" + (end - start) / REPEATS);
        start = System.nanoTime();
        for (int idx = 0; idx < REPEATS; idx ++) {
            p.matcher(testCase).matches();
        }
        end = System.nanoTime();
        System.out.println("\t\tExecution:\t" + (end - start) / REPEATS);
    }
}
