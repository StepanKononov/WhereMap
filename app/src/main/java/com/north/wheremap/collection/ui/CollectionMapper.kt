package com.north.wheremap.collection.ui

import com.north.wheremap.core.domain.collection.Collection

fun Collection.toUI() =
    CollectionUI(
        id = id ?: throw AssertionError("WTF get null collection id from domain layer"),
        city = city ?: "",
        description = description ?: "",
        name = name,
        isPrivate = isPrivate
    )