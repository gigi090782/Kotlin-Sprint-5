package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>) =
    list.withIndex()
        .filter { it.index % 3 == 0 }
        .sumOf { it.value }

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1), { Pair(it.second, it.first + it.second) }).map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.groupBy { it.city }
        .mapValues {
            it.value.sumOf { customer ->
                customer.orders.filter { order -> order.isDelivered }.map { order -> order.products.size }.sum()
            }
        }


// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product?> =
    customers.groupBy { it.city }.mapValues { customers ->
        customers.value.flatMap { customer -> customer.orders }.flatMap { order -> order.products }.groupBy { it }
            .maxByOrNull { it.value.size }?.key
    }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers.groupBy { it }
        .mapValues { customers -> customers.value.flatMap { it.orders }.flatMap { order -> order.products }.distinct() }
        .map { it.value.toMutableList() }
        .reduce { acc, it -> acc.apply { retainAll(it) } }.toSet()


