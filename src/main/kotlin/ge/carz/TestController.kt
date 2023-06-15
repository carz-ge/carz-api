package ge.carz

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {


    @GetMapping("/test1")
    fun test(): String {
        return "test"
    }


    @GetMapping("/test2")
    fun test2(): Flow<String> {
        return flowOf(1, 2, 3)
            .map { it + 1 }
            .map { "$it" }
    }
}
