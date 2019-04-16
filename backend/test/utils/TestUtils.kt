package utils

import com.acomodeo.mining.backend.prom.PromRepository
import com.acomodeo.mining.model.Edge
import com.acomodeo.mining.model.Node
import com.acomodeo.mining.model.Prom
import com.acomodeo.semanticid.SemanticId
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.UUID

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestUtils {

    @Autowired
    lateinit var promRepository: PromRepository

    fun testProm(
        id: String = UUID.randomUUID().toString()
    ) = Prom(
        id = SemanticId(namespace = "aco", collection = "prom", id = id),
        nodes = listOf(
            Node(
                id = 1.toString(),
                label = "INTERNET",
                group = 1.toString(),
                level = 1.toString(),
                count = 100
            ),
            Node(
                id = 2.toString(),
                label = "gds",
                group = 2.toString(),
                level = 2.toString(),
                count = 100
            ),
            Node(
                id = 3.toString(),
                label = "stop",
                group = 3.toString(),
                level = 3.toString(),
                count = 100
            )
        ),
        edges = listOf(
            Edge(
                id = 1.toString(),
                from = 1.toString(),
                to = 2.toString(),
                label = 100.toString(),
                name = "gds"
            ),
            Edge(
                id = 2.toString(),
                from = 2.toString(),
                to = 3.toString(),
                label = 100.toString(),
                name = "stop"
            )
        )
    )
}