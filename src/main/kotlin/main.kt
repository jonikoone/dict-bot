import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.network.fold
import kotlinx.atomicfu.AtomicInt


fun main() {

    val users = HashSet<Long>()
    val t = System.getenv("token")

    val bot = bot {
        token = t

        dispatch {
            command("start") {
                users.add(message.chat.id)
                bot.sendMessage(message.chat.id, "welcome")
            }

            command("users") {
                val idx = Incrementer(0)
                val m = users.map { "user #${idx.inc()} = $it" }.toString()
                bot.sendMessage(message.chat.id, m)
            }
        }

    }

    bot.startPolling()
}


private class Incrementer(private var value: Int) {
    fun inc() = value++
}
