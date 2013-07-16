package pchelolo.matcher;


import org.junit.Test;

public class PerfTest {

    @Test
    public void testLongClosure() throws Exception {
        String pattern = "a*";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }
        String testCase = sb.append("b").toString();

        System.out.println("Long closure test");
        System.out.println("\tJava");
        long start = System.nanoTime();
        java.util.regex.Pattern javaPattern = java.util.regex.Pattern.compile(pattern);
        long end = System.nanoTime();
        System.out.println("\t\tCompilation:\t" + (end - start));
        start = System.nanoTime();
        javaPattern.matcher(testCase).matches();
        end = System.nanoTime();
        System.out.println("\t\tExecution:\t" + (end - start));

        System.out.println("\tCurrent implementation");
        start = System.nanoTime();
        Pattern m = Pattern.compile(pattern);
        end = System.nanoTime();
        System.out.println("\t\tCompilation:\t" + (end - start));
        start = System.nanoTime();
        m.matcher().matches(testCase);
        end = System.nanoTime();
        System.out.println("\t\tExecution:\t" + (end - start));
    }
}
