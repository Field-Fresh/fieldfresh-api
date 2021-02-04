package integration

import com.fieldfreshmarket.api.ApiApplication
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
   classes = [ApiApplication::class],
   webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class ApplicationTest {

   @Autowired
   lateinit var testRestTemplate: TestRestTemplate

   @Test
   fun testSysInfo() {
      val result = testRestTemplate.getForEntity("/sysinfo", Map::class.java)

      assertNotNull(result)
      assertNotNull(result)
      assertEquals(HttpStatus.OK, result?.statusCode)
   }
}