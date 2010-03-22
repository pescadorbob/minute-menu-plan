package com.mp
import com.mp.domain.*

class UtilController {
    def index = {
        render RecipeDifficulty.MEDIUM.name()
    }
}
