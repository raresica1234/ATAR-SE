import tornadofx.launch
import client.Application
import utils.TEST

fun main(args: Array<String>) {
    println(TEST)
    launch<Application>(args)
}