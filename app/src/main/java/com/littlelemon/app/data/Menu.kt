package com.littlelemon.app.data

/** A single dish shown in the food menu list. */
data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    val imageUrl: String
)

object MenuRepository {

    val categories = listOf("Starters", "Mains", "Desserts", "Drinks")

    // Sample menu used to populate the Home screen's food menu list.
    val items = listOf(
        MenuItem(
            id = 1,
            title = "Greek Salad",
            description = "The famous Greek salad of crispy lettuce, peppers, olives and our Chicago-style feta cheese, garnished with crunchy garlic and rosemary croutons.",
            price = "12.99",
            category = "Starters",
            imageUrl = "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400&q=80"
        ),
        MenuItem(
            id = 2,
            title = "Bruschetta",
            description = "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
            price = "7.99",
            category = "Starters",
            imageUrl = "https://images.unsplash.com/photo-1572695157366-5e585ab2b69f?w=400&q=80"
        ),
        MenuItem(
            id = 3,
            title = "Grilled Fish",
            description = "Fantastic grilled fish seasoned to perfection with a side of seasonal greens and lemon butter sauce.",
            price = "20.00",
            category = "Mains",
            imageUrl = "https://images.unsplash.com/photo-1485921325833-c519f76c4927?w=400&q=80"
        ),
        MenuItem(
            id = 4,
            title = "Pasta",
            description = "Delicious freshly made pasta tossed in a rich tomato basil sauce with shaved parmesan.",
            price = "18.99",
            category = "Mains",
            imageUrl = "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400&q=80"
        ),
        MenuItem(
            id = 5,
            title = "Lemon Dessert",
            description = "Light and fluffy traditional homemade Italian lemon ricotta cake, dusted with icing sugar.",
            price = "6.99",
            category = "Desserts",
            imageUrl = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&q=80"
        ),
        MenuItem(
            id = 6,
            title = "Sparkling Lemonade",
            description = "House-made sparkling lemonade with fresh mint and a hint of ginger, served over ice.",
            price = "4.50",
            category = "Drinks",
            imageUrl = "https://images.unsplash.com/photo-1621263764928-df1444c5e859?w=400&q=80"
        )
    )
}
