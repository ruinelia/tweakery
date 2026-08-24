package com.ruineko.tweakery.record

import java.util.UUID

data class PlayerPresence(
    val username: String,
    val profileId: UUID,
    val online: Boolean,
)