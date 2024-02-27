package com.example.cupcake.data

import com.example.cupcake.R

object DataSource {
    val flavors = listOf(
      "vanilla",
        "chocolate",
        "red_velvet",
        "salted_caramel",
        "coffee"
    )

    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )
}
