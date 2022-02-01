package machine

var work = true

enum class States {
    ChoosingAnAction, ChoosingCoffee
}

enum class Recipes(val water: Int, val milk: Int, val beans: Int, val money: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6),
    NULL(0, 0, 0, 0)
}

object CoffeeMachine {
    var water: Int = 400
    var milk: Int = 540
    var beans: Int = 120
    var cups: Int = 9
    var money: Int = 550
    var state: States = States.ChoosingAnAction

    fun checkInput(input: String) {
        when (state) {
            States.ChoosingAnAction -> {
                when (input) {
                    "buy" -> buy()
                    "fill" -> fill()
                    "take" -> take()
                    "remaining" -> remaining()
                    "exit" -> work = false
                }
            }
            States.ChoosingCoffee -> {
                makeCoffee(input)
            }
        }
    }

    fun buy() {
        state = States.ChoosingCoffee
        print("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > ")
    }

    fun fill() {
        print("\nWrite how many ml of water do you want to add: > ")
        water += readLine()!!.toInt()
        print("Write how many ml of milk do you want to add: > ")
        milk += readLine()!!.toInt()
        print("Write how many grams of coffee beans do you want to add: > ")
        beans += readLine()!!.toInt()
        print("Write how many disposable cups of coffee do you want to add: > ")
        cups += readLine()!!.toInt()
        println()
        printAction()
    }

    fun take() {
        print("\nI gave you \$$money\n\n")
        money = 0
        printAction()
    }

    fun remaining() {
        println("""
            The coffee machine has:
            $water of water
            $milk of milk
            $beans of coffee beans
            $cups of disposable cups
            """.trimIndent())

        println(
            if (money == 0) "0 of money\n" else "\$$money of money\n"
        )

        printAction()
    }

    fun makeCoffee(input: String) {
        if (input == "back") {
            state = States.ChoosingAnAction
            printAction()
            return
        }

        val recipe = when (input) {
            "1" -> Recipes.ESPRESSO
            "2" -> Recipes.LATTE
            "3" -> Recipes.CAPPUCCINO
            else -> Recipes.NULL
        }

        checkResources(recipe)
        state = States.ChoosingAnAction
        printAction()
    }

    fun checkResources(recipe: Recipes) {
        when {
            water < recipe.water -> {
                println("Sorry, not enough water!\n")
            }
            milk < recipe.milk -> {
                println("Sorry, not enough milk!\n")
            }
            beans < recipe.beans -> {
                println("Sorry, not enough beans!\n")
            }
            cups < 1 -> {
                println("Sorry, not enough cups!\n")
            }
            else -> {
                println("I have enough resources, making you a coffee!\n")
                water -= recipe.water
                milk -= recipe.milk
                beans -= recipe.beans
                money += recipe.money
                cups -= 1
            }
        }
    }

    fun printAction() = print("Write action (buy, fill, take, remaining, exit): > ")
}

fun main() {
    print("Write action (buy, fill, take, remaining, exit): > ")
    while (work) CoffeeMachine.checkInput(readLine()!!)
}
