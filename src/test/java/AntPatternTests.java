import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class AntPatternTests {
    private AntPathMatcher matcher = new AntPathMatcher();

    @Test
    public void rootMatchIn() {
        assertMatch("/*", "/1");
        assertMatch("/*", "/abcd");
        assertNoMatch("/*", "/1/");
        assertNoMatch("/*", "/1/view");
    }

    @Test
    public void folderMatchAll() {
        assertNoMatch("/1/**","/2/view/this");
        assertMatch("/1/**","/1/view/this");
        assertMatch("/1/**","/1/view");
        assertMatch("/1/**","/1/");
        assertMatch("/1/**","/1");
        assertNoMatch("/1/**","/");
    }

    @Test
    public void endsWithCss() {
        assertMatch("/**/*.css","/styles/main.css");
        assertNoMatch("/**/*.css","/1");
        assertMatch("/**/*.css","/1.css");
    }

    private void assertMatch(String pattern, String path) {
        assertThat(matcher.match(pattern, path)).isTrue();
    }

    private void assertNoMatch(String pattern, String path) {
        assertThat(matcher.match(pattern, path)).isFalse();
    }
}
