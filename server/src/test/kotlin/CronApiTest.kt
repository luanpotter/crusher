import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import xyz.luan.crusher.api.extractToken

class CronApiTest {
    @Test
    fun `test extract token regex`() {
        val header = "Bearer: foobar"
        val token = header.extractToken()
        assertThat(token, equalTo("foobar"))
    }
}